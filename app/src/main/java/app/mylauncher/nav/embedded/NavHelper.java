package app.mylauncher.nav.embedded;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * Auxiliar privilegiado da navegação embutida (spec 008).
 *
 * NÃO é uma classe de app comum: roda num processo separado via
 * {@code CLASSPATH=<apk> app_process /system/bin app.mylauncher.nav.embedded.NavHelper <socket> <displayId>},
 * iniciado como root pelo {@code cmd_services} (ver RootShell). Por isso é Java puro, sem Context,
 * sem Kotlin runtime e sem dependências do app — só framework + reflexão para as APIs ocultas.
 *
 * O Lumia (app) cria o VirtualDisplay (API pública) e hospeda um LocalServerSocket; este auxiliar
 * conecta de volta nesse socket e faz só o que um app comum não pode:
 *  - abrir o app de navegação NAQUELE display ({@code am start --display});
 *  - injetar os toques do card no display ({@code InputManager.injectInputEvent} + {@code setDisplayId});
 *  - devolver ao card as telas do app que "fugirem" para o display principal, mandar para o display
 *    principal as telas de outros apps que caírem no card e reabrir o app se ele sumir (R8.6, R8.9);
 *  - devolver o foco do sistema ao display principal depois de cada toque (R8.8).
 *
 * Protocolo (linhas de texto, UTF-8, terminadas em \n), vindas do app:
 *  LAUNCH &lt;pkg&gt;                      abre a activity principal do pkg no display
 *  OPEN &lt;pkg&gt; &lt;uri&gt;                 ACTION_VIEW da uri no pkg, no display (deep link de navegação)
 *  TOUCH &lt;downTime&gt; &lt;evTime&gt; &lt;action&gt; &lt;n&gt; (&lt;id&gt; &lt;x&gt; &lt;y&gt;)*   injeta um MotionEvent
 *  KEY &lt;keycode&gt;                   injeta uma tecla (ex.: 4 = BACK)
 *  WATCH &lt;pkg&gt; &lt;on|off&gt;            liga/desliga a vigia de "fugas"
 *  PING                            responde PONG
 *  STOP                            encerra o auxiliar
 */
public final class NavHelper {

    private static int displayId;
    private static volatile Object inputManager;
    private static volatile Method injectMethod;
    private static volatile Method setDisplayIdMethod;
    private static volatile EscapeWatcher watcher;
    private static final FocusKeeper focusKeeper = new FocusKeeper();
    private static OutputStream out;
    /** Último app/URI aberto no display: o vigia reabre com eles se o app sumir (R8.9). */
    private static volatile String lastPkg;
    private static volatile String lastUri;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("uso: NavHelper <socketName> <displayId>");
            return;
        }
        String socketName = args[0];
        displayId = Integer.parseInt(args[1]);
        log("iniciando: socket=" + socketName + " display=" + displayId);
        try {
            initReflection();
            lockRotation();
            focusKeeper.start();
            runLoop(socketName);
        } catch (Throwable t) {
            log("fatal: " + t);
        }
        log("encerrado");
        // Threads de binder mantêm o app_process vivo: sem isto o auxiliar ficaria órfão.
        System.exit(0);
    }

    private static void runLoop(String socketName) throws Exception {
        LocalSocket socket = new LocalSocket();
        socket.connect(new LocalSocketAddress(socketName, LocalSocketAddress.Namespace.ABSTRACT));
        out = socket.getOutputStream();
        reply("READY");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.isEmpty()) continue;
            try {
                if (!handle(line)) break; // STOP
            } catch (Throwable t) {
                log("erro no comando '" + line + "': " + t);
            }
        }
        if (watcher != null) watcher.stopWatching();
        socket.close();
    }

    /** @return false para encerrar (STOP). */
    private static boolean handle(String line) throws Exception {
        int sp = line.indexOf(' ');
        String cmd = sp < 0 ? line : line.substring(0, sp);
        String rest = sp < 0 ? "" : line.substring(sp + 1);
        switch (cmd) {
            case "LAUNCH":
                launch(rest.trim(), null);
                return true;
            case "OPEN": {
                int s2 = rest.indexOf(' ');
                String pkg = s2 < 0 ? rest : rest.substring(0, s2);
                String uri = s2 < 0 ? null : rest.substring(s2 + 1).trim();
                launch(pkg.trim(), uri);
                return true;
            }
            case "TOUCH":
                focusKeeper.onTouch(injectTouch(rest));
                return true;
            case "KEY":
                injectKey(Integer.parseInt(rest.trim()));
                return true;
            case "WATCH": {
                int s2 = rest.indexOf(' ');
                String pkg = s2 < 0 ? rest : rest.substring(0, s2);
                boolean on = s2 >= 0 && rest.substring(s2 + 1).trim().equals("on");
                setWatch(pkg.trim(), on);
                return true;
            }
            case "PING":
                reply("PONG");
                return true;
            case "STOP":
                return false;
            default:
                log("comando desconhecido: " + cmd);
                return true;
        }
    }

    /**
     * Trava a rotação do display em 0. Sem isso, quando o card fica em retrato (modo dividido) o
     * sistema gira o display para paisagem (o app pede essa orientação) e o conteúdo aparece
     * encolhido, com faixas pretas (visto no carro).
     */
    private static void lockRotation() {
        sh("wm set-fix-to-user-rotation -d " + displayId + " enabled");
        sh("wm set-user-rotation lock -d " + displayId + " 0");
    }

    // ---- Abrir o app no display -------------------------------------------------------------

    private static void launch(String pkg, String uri) {
        lastPkg = pkg;
        lastUri = uri;
        // Se o app já tem uma tarefa no display principal (aberto antes, fora do card), traz essa
        // tarefa para o card em vez de abrir uma segunda: o `am start --display` reaproveitaria a
        // tarefa antiga NO display 0 (visto no carro: "top resumed state loss timeout").
        int moved = Stacks.moveAll(pkg, displayId);
        if (moved > 0) log("launch: " + moved + " stack(s) de " + pkg + " trazida(s) para o display " + displayId);
        // `am start` é robusto e já roda como root; respeita --display neste firmware (spec 008).
        String am;
        if (uri != null && !uri.isEmpty()) {
            am = "am start --display " + displayId + " -a android.intent.action.VIEW -d " + shellQuote(uri) + " " + pkg;
        } else {
            am = "am start --display " + displayId + " -n " + resolveComponent(pkg);
        }
        String r = sh(am);
        log("launch " + pkg + " -> " + firstLine(r));
        reply("LAUNCHED " + pkg);
    }

    /** Descobre a activity de entrada do pkg; cai para o próprio pkg se não achar. */
    private static String resolveComponent(String pkg) {
        String r = sh("cmd package resolve-activity --brief -c android.intent.category.LAUNCHER " + pkg);
        if (r != null) {
            for (String l : r.split("\n")) {
                l = l.trim();
                if (l.startsWith(pkg + "/")) return l;
            }
        }
        return pkg + "/.MainActivity";
    }

    // ---- Injeção de toque ------------------------------------------------------------------

    /** @return a ação do evento (ACTION_DOWN, ACTION_UP…), para o [FocusKeeper]. */
    private static int injectTouch(String rest) throws Exception {
        String[] t = rest.trim().split("\\s+");
        long downTime = Long.parseLong(t[0]);
        long evTime = Long.parseLong(t[1]);
        int action = Integer.parseInt(t[2]);
        int count = Integer.parseInt(t[3]);
        MotionEvent.PointerProperties[] props = new MotionEvent.PointerProperties[count];
        MotionEvent.PointerCoords[] coords = new MotionEvent.PointerCoords[count];
        int i = 4;
        for (int p = 0; p < count; p++) {
            MotionEvent.PointerProperties pp = new MotionEvent.PointerProperties();
            pp.id = Integer.parseInt(t[i++]);
            pp.toolType = MotionEvent.TOOL_TYPE_FINGER;
            MotionEvent.PointerCoords pc = new MotionEvent.PointerCoords();
            pc.x = Float.parseFloat(t[i++]);
            pc.y = Float.parseFloat(t[i++]);
            pc.pressure = 1f;
            pc.size = 1f;
            props[p] = pp;
            coords[p] = pc;
        }
        MotionEvent ev = MotionEvent.obtain(
                downTime, evTime, action, count, props, coords,
                0, 0, 1f, 1f, 0, 0, InputDevice.SOURCE_TOUCHSCREEN, 0);
        inject(ev);
        ev.recycle();
        return action & MotionEvent.ACTION_MASK;
    }

    private static void injectKey(int keyCode) throws Exception {
        long now = SystemClock.uptimeMillis();
        // (downTime, eventTime, action, code, repeat, metaState, deviceId, scancode, flags, source)
        inject(new KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyCode, 0, 0,
                KeyEvent.FLAG_FROM_SYSTEM, 0, 0, InputDevice.SOURCE_KEYBOARD));
        inject(new KeyEvent(now, now, KeyEvent.ACTION_UP, keyCode, 0, 0,
                KeyEvent.FLAG_FROM_SYSTEM, 0, 0, InputDevice.SOURCE_KEYBOARD));
    }

    private static void inject(InputEvent ev) throws Exception {
        setDisplayIdMethod.invoke(ev, displayId);
        injectMethod.invoke(inputManager, ev, 0 /* INJECT_INPUT_EVENT_MODE_ASYNC */);
    }

    private static void initReflection() throws Exception {
        Class<?> imCls = Class.forName("android.hardware.input.InputManager");
        inputManager = imCls.getMethod("getInstance").invoke(null);
        injectMethod = imCls.getMethod("injectInputEvent", InputEvent.class, int.class);
        setDisplayIdMethod = InputEvent.class.getMethod("setDisplayId", int.class);
    }

    // ---- Vigia de "fugas" ------------------------------------------------------------------

    private static void setWatch(String pkg, boolean on) {
        if (watcher != null) {
            watcher.stopWatching();
            watcher = null;
        }
        if (on) {
            watcher = new EscapeWatcher(pkg);
            watcher.start();
            log("watcher ligado para " + pkg);
        }
    }

    /**
     * Vigia do display do card, a cada 500 ms, por {@link Stacks} (reflexão, sem criar processos):
     *  - devolve ao card as tarefas do app de navegação que caírem no display 0 (R8.6). Move só stacks
     *    cujas tarefas sejam do {@code pkg}: a versão que lia o `dumpsys` chegou a mover o YouTube Music;
     *  - manda para o display 0 as tarefas de **outros** apps que caírem no card (R8.8). Ex.: os
     *    Recentes da Interface do sistema abertos com o foco no card; de lá, o "Home" deles não tem
     *    onde abrir a HOME e a Interface do sistema travava ("Cannot launch home on display N");
     *  - reabre o app se ele sumir do card com a tela ligada (R8.9). Ex.: a TopWay faz
     *    {@code forceStopPackage} nos apps ao tirar a chave, e o card ficava preto ao religar.
     */
    private static final class EscapeWatcher extends Thread {
        private final String pkg;
        private volatile boolean running = true;
        private int missing;
        private long lastRelaunch;

        EscapeWatcher(String pkg) {
            super("nav-escape-watcher");
            this.pkg = pkg;
            setDaemon(true);
        }

        void stopWatching() { running = false; interrupt(); }

        @Override public void run() {
            while (running) {
                try {
                    Thread.sleep(500);
                    int n = Stacks.moveAll(pkg, displayId);
                    if (n > 0) log("fuga devolvida: " + n + " stack(s) de " + pkg + " -> display " + displayId);
                    int e = Stacks.evictForeign(pkg, displayId);
                    if (e > 0) log(e + " stack(s) de outro app tirada(s) do card -> display 0");
                    checkAlive();
                } catch (InterruptedException e) {
                    return;
                } catch (Throwable t) {
                    log("watcher erro: " + t);
                }
            }
        }

        /** Reabre o app se ele não aparecer no card por ~2 s seguidos com a tela ligada. */
        private void checkAlive() {
            int present = Stacks.countOnDisplay(pkg, displayId);
            if (present != 0 || !System2.isInteractive()) { missing = 0; return; }
            if (++missing < 4) return;
            long now = SystemClock.uptimeMillis();
            if (now - lastRelaunch < RELAUNCH_MIN_INTERVAL_MS) return;
            lastRelaunch = now;
            missing = 0;
            String p = lastPkg != null ? lastPkg : pkg;
            log("app sumiu do card; reabrindo " + p);
            launch(p, lastUri);
        }
    }

    private static final long RELAUNCH_MIN_INTERVAL_MS = 10_000;

    /**
     * Devolve o foco do sistema ao display principal depois de um toque no card (R8.8).
     *
     * No Android 10 há um único display com foco, e ele vai para onde foi o último toque: o toque
     * injetado no card põe o display virtual no topo ({@code mTopFocusedDisplayId}). Daí em diante as
     * teclas Home/Recentes da barra do Android agem no card: os Recentes abriam DENTRO do card e o
     * "Home" deles derrubava a Interface do sistema (medido no carro, 2026-11-22).
     *
     * Depois de {@link #RETURN_MS} sem toque, se o foco ainda estiver no card e o teclado não estiver
     * aberto (digitação no app embutido precisa do foco lá), põe em foco a stack visível do topo do
     * display 0 ({@code setFocusedStack}: ~13 ms, não reordena nem manda intent ao launcher).
     */
    private static final class FocusKeeper extends Thread {
        private static final long RETURN_MS = 1200;
        private static final long IME_RECHECK_MS = 1500;
        private long returnAt; // uptime; 0 = nada agendado
        private boolean pressed;

        FocusKeeper() {
            super("nav-focus-keeper");
            setDaemon(true);
        }

        synchronized void onTouch(int action) {
            if (action == MotionEvent.ACTION_DOWN) {
                pressed = true;
                returnAt = 0;
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                pressed = false;
                returnAt = SystemClock.uptimeMillis() + RETURN_MS;
                notifyAll();
            }
        }

        @Override public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        while (returnAt == 0 || pressed) wait();
                        long d = returnAt - SystemClock.uptimeMillis();
                        if (d > 0) { wait(d); continue; }
                        returnAt = 0;
                    }
                    if (System2.imeVisible()) {
                        synchronized (this) { if (returnAt == 0 && !pressed) returnAt = SystemClock.uptimeMillis() + IME_RECHECK_MS; }
                        continue;
                    }
                    if (Stacks.focusedDisplay() == displayId && Stacks.focusMainDisplay()) {
                        log("foco devolvido ao display 0");
                    }
                } catch (InterruptedException e) {
                    return;
                } catch (Throwable t) {
                    log("focus erro: " + t);
                }
            }
        }
    }

    /** Serviços do sistema por reflexão (estado da tela e do teclado). */
    private static final class System2 {
        private static Object power, imm;
        private static Method isInteractive, imeHeight;
        private static boolean powerFailed, immFailed;

        private static Object service(String name, String stub) throws Exception {
            IBinder b = (IBinder) Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class).invoke(null, name);
            return Class.forName(stub).getMethod("asInterface", IBinder.class).invoke(null, b);
        }

        /** Tela ligada? Na dúvida, true. */
        static synchronized boolean isInteractive() {
            if (powerFailed) return true;
            try {
                if (power == null) {
                    power = service("power", "android.os.IPowerManager$Stub");
                    isInteractive = power.getClass().getMethod("isInteractive");
                }
                return (Boolean) isInteractive.invoke(power);
            } catch (Throwable t) {
                powerFailed = true;
                log("isInteractive indisponível: " + t);
                return true;
            }
        }

        /** Teclado na tela? Na dúvida, false. */
        static synchronized boolean imeVisible() {
            if (immFailed) return false;
            try {
                if (imm == null) {
                    imm = service("input_method", "com.android.internal.view.IInputMethodManager$Stub");
                    imeHeight = imm.getClass().getMethod("getInputMethodWindowVisibleHeight");
                }
                return (Integer) imeHeight.invoke(imm) > 0;
            } catch (Throwable t) {
                immFailed = true;
                log("estado do teclado indisponível: " + t);
                return false;
            }
        }
    }

    /**
     * Stacks de atividades do sistema. Caminho principal: {@code IActivityTaskManager} por reflexão
     * ({@code getAllStackInfos} / {@code moveStackToDisplay}, Android 10). Reserva: {@code am stack list}
     * e {@code am display move-stack}, bem mais caros (cada um cria um processo).
     */
    private static final class Stacks {
        private static Object atm;
        private static Method getAll;
        private static Method moveToDisplay;
        private static Method getFocused;
        private static Method setFocused;
        private static boolean reflectionFailed;

        /** Move para {@code target} toda stack do display 0 que tenha tarefa do {@code pkg}. @return quantas moveu. */
        static synchronized int moveAll(String pkg, int target) {
            if (!reflectionFailed) {
                try {
                    return moveMatching(pkg, 0, target, true);
                } catch (Throwable t) {
                    if (!onReflectionError("moveAll", t)) return 0;
                }
            }
            return moveAllShell(pkg, target);
        }

        /** Manda para o display 0 toda stack do {@code display} que NÃO seja do {@code pkg}. */
        static synchronized int evictForeign(String pkg, int display) {
            if (reflectionFailed) return 0;
            try {
                return moveMatching(pkg, display, 0, false);
            } catch (Throwable t) {
                onReflectionError("evictForeign", t);
                return 0;
            }
        }

        /** Quantas stacks do {@code pkg} há no {@code display}; -1 se não der para saber. */
        static synchronized int countOnDisplay(String pkg, int display) {
            if (reflectionFailed) return -1;
            try {
                int n = 0;
                for (Object info : stackInfos()) {
                    if (displayOf(info) == display && hasPkg(namesOf(info), pkg)) n++;
                }
                return n;
            } catch (Throwable t) {
                onReflectionError("countOnDisplay", t);
                return -1;
            }
        }

        /** Display da stack com foco (o "top focused display"); -1 se não der para saber. */
        static synchronized int focusedDisplay() {
            if (reflectionFailed) return -1;
            try {
                ensureAtm();
                Object info = getFocused.invoke(atm);
                return info == null ? -1 : displayOf(info);
            } catch (Throwable t) {
                onReflectionError("focusedDisplay", t);
                return -1;
            }
        }

        /** Põe em foco a stack visível do topo do display 0. @return true se conseguiu. */
        static synchronized boolean focusMainDisplay() {
            if (reflectionFailed) return false;
            try {
                for (Object info : stackInfos()) { // do topo para baixo
                    if (displayOf(info) != 0 || !info.getClass().getField("visible").getBoolean(info)) continue;
                    setFocused.invoke(atm, info.getClass().getField("stackId").getInt(info));
                    return true;
                }
            } catch (Throwable t) {
                onReflectionError("focusMainDisplay", t);
            }
            return false;
        }

        /**
         * Erro numa chamada por reflexão. Só desiste da reflexão se a API não existir; uma exceção
         * lançada pelo sistema (ex.: stack que sumiu no meio da consulta) vale só para esta chamada.
         * Antes, qualquer erro desligava a reflexão para sempre (visto no carro às 18:23).
         * @return true se a reflexão foi desligada.
         */
        private static boolean onReflectionError(String what, Throwable t) {
            if (t instanceof InvocationTargetException) {
                log("Stacks." + what + ": " + ((InvocationTargetException) t).getTargetException());
                return false;
            }
            reflectionFailed = true;
            log("Stacks: reflexão falhou em " + what + " (" + t + "), usando am stack list");
            return true;
        }

        private static void ensureAtm() throws Exception {
            if (atm != null) return;
            Class<?> atmCls = Class.forName("android.app.ActivityTaskManager");
            Object a = atmCls.getMethod("getService").invoke(null);
            Class<?> c = a.getClass();
            getAll = c.getMethod("getAllStackInfos");
            moveToDisplay = c.getMethod("moveStackToDisplay", int.class, int.class);
            getFocused = c.getMethod("getFocusedStackInfo");
            setFocused = c.getMethod("setFocusedStack", int.class);
            atm = a;
        }

        private static java.util.List<?> stackInfos() throws Exception {
            ensureAtm();
            return (java.util.List<?>) getAll.invoke(atm);
        }

        private static int displayOf(Object info) throws Exception {
            return info.getClass().getField("displayId").getInt(info);
        }

        private static String[] namesOf(Object info) throws Exception {
            return (String[]) info.getClass().getField("taskNames").get(info);
        }

        /** Move do display {@code from} para {@code to} as stacks que são ({@code ofPkg}) ou não são do {@code pkg}. */
        private static int moveMatching(String pkg, int from, int to, boolean ofPkg) throws Exception {
            int moved = 0;
            for (Object info : stackInfos()) {
                if (displayOf(info) != from) continue;
                String[] names = namesOf(info);
                if (names == null || names.length == 0) continue;
                if (hasPkg(names, pkg) != ofPkg) continue;
                moveToDisplay.invoke(atm, info.getClass().getField("stackId").getInt(info), to);
                moved++;
            }
            return moved;
        }

        private static int moveAllShell(String pkg, int target) {
            String list = sh("am stack list");
            if (list == null) return 0;
            int moved = 0;
            int stack = -1, disp = -1;
            boolean done = false;
            for (String l : list.split("\n")) {
                String t = l.trim();
                if (t.startsWith("Stack id=")) {
                    stack = parseIntSafe(field(t, "Stack id="), -1);
                    disp = parseIntSafe(field(t, "displayId="), -1);
                    done = false;
                } else if (!done && disp == 0 && stack >= 0 && t.startsWith("taskId=") && t.contains(": " + pkg + "/")) {
                    sh("am display move-stack " + stack + " " + target);
                    moved++;
                    done = true;
                }
            }
            return moved;
        }

        private static boolean hasPkg(String[] names, String pkg) {
            if (names == null) return false;
            for (String n : names) if (n != null && n.startsWith(pkg + "/")) return true;
            return false;
        }

        private static String field(String line, String key) {
            int i = line.indexOf(key);
            if (i < 0) return "";
            int s = i + key.length(), e = s;
            while (e < line.length() && Character.isDigit(line.charAt(e))) e++;
            return line.substring(s, e);
        }
    }

    // ---- Utilidades ------------------------------------------------------------------------

    private static String sh(String cmd) {
        try {
            Process p = new ProcessBuilder("sh", "-c", cmd).redirectErrorStream(true).start();
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            String l;
            while ((l = r.readLine()) != null) sb.append(l).append('\n');
            p.waitFor();
            return sb.toString();
        } catch (Throwable t) {
            log("sh falhou: " + t);
            return null;
        }
    }

    private static String shellQuote(String s) { return "'" + s.replace("'", "'\\''") + "'"; }
    private static String firstLine(String s) { return s == null ? "null" : s.split("\n", 2)[0]; }
    private static int parseIntSafe(String s, int def) { try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; } }

    private static synchronized void reply(String msg) {
        try {
            if (out != null) { out.write((msg + "\n").getBytes(StandardCharsets.UTF_8)); out.flush(); }
        } catch (Throwable ignored) { }
    }

    /** Log no stdout (arquivo, para adb) e, se conectado, também para o app (vai ao FileLog). */
    private static void log(String msg) {
        System.out.println("NavHelper: " + msg);
        reply("LOG " + msg);
    }

    private NavHelper() { }
}
