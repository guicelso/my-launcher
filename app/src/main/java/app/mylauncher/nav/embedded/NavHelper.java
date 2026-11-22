package app.mylauncher.nav.embedded;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
 *  - (opcional) devolver ao card as telas do app que "fugirem" para o display principal.
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
    private static OutputStream out;

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
                injectTouch(rest);
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

    private static void injectTouch(String rest) throws Exception {
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
     * Devolve ao card as tarefas do app de navegação que caírem no display 0 (spec 008, R8.6).
     * Sonda a cada 500 ms por {@link Stacks} (reflexão, sem criar processos) e move só stacks cujas
     * tarefas sejam do {@code pkg}. A versão anterior lia o `dumpsys` e chegou a mover o YouTube Music.
     */
    private static final class EscapeWatcher extends Thread {
        private final String pkg;
        private volatile boolean running = true;

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
                } catch (InterruptedException e) {
                    return;
                } catch (Throwable t) {
                    log("watcher erro: " + t);
                }
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
        private static boolean reflectionFailed;

        /** Move para {@code target} toda stack do display 0 que tenha tarefa do {@code pkg}. @return quantas moveu. */
        static synchronized int moveAll(String pkg, int target) {
            if (!reflectionFailed) {
                try {
                    return moveAllReflection(pkg, target);
                } catch (Throwable t) {
                    reflectionFailed = true;
                    log("Stacks: reflexão falhou (" + t + "), usando am stack list");
                }
            }
            return moveAllShell(pkg, target);
        }

        private static int moveAllReflection(String pkg, int target) throws Exception {
            if (atm == null) {
                Class<?> atmCls = Class.forName("android.app.ActivityTaskManager");
                atm = atmCls.getMethod("getService").invoke(null);
                getAll = atm.getClass().getMethod("getAllStackInfos");
                moveToDisplay = atm.getClass().getMethod("moveStackToDisplay", int.class, int.class);
            }
            java.util.List<?> infos = (java.util.List<?>) getAll.invoke(atm);
            int moved = 0;
            for (Object info : infos) {
                Class<?> c = info.getClass();
                int stackId = c.getField("stackId").getInt(info);
                int disp = c.getField("displayId").getInt(info);
                if (disp != 0) continue;
                String[] names = (String[]) c.getField("taskNames").get(info);
                if (!hasPkg(names, pkg)) continue;
                moveToDisplay.invoke(atm, stackId, target);
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
