package c;

import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f19a;

    public e(g gVar) {
        this.f19a = gVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Process process;
        g gVar = this.f19a;
        Objects.requireNonNull(gVar);
        try {
            try {
                String str = String.format("logcat %s -f %s & \n", "-v threadtime", gVar.f22b);
                d.c.b("LogModel", "开始抓取日志 %s", str);
                Process processExec = Runtime.getRuntime().exec(str);
                gVar.f24d = processExec;
                if (processExec != null) {
                    Thread.sleep(60000L);
                    d.c.b("LogModel", "停止抓日志", new Object[0]);
                    gVar.f24d.destroy();
                }
                d.c.b("LogModel", "finally", new Object[0]);
                process = gVar.f24d;
                if (process == null) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                d.c.b("LogModel", "finally", new Object[0]);
                process = gVar.f24d;
                if (process == null) {
                    return;
                }
            }
            process.destroy();
        } catch (Throwable th) {
            d.c.b("LogModel", "finally", new Object[0]);
            Process process2 = gVar.f24d;
            if (process2 != null) {
                process2.destroy();
            }
            throw th;
        }
    }
}
