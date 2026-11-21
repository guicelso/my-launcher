package a.a.a.a;

/* JADX INFO: loaded from: classes.dex */
public class c extends e {
    private static volatile c sInstance;
    private e q = new d();
    private e p = this.q;

    static {
        new a();
        new b();
    }

    private c() {
    }

    public static c getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (c.class) {
            if (sInstance == null) {
                sInstance = new c();
            }
        }
        return sInstance;
    }

    @Override // a.a.a.a.e
    public void a(Runnable runnable) {
        this.p.a(runnable);
    }

    @Override // a.a.a.a.e
    public void b(Runnable runnable) {
        this.p.b(runnable);
    }

    @Override // a.a.a.a.e
    public boolean e() {
        return this.p.e();
    }
}
