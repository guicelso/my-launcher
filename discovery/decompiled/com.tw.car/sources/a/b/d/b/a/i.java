package a.b.d.b.a;

/* JADX INFO: loaded from: classes.dex */
public class i implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f67a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ j f68b;

    public i(j jVar, int i) {
        this.f68b = jVar;
        this.f67a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f68b.a(this.f67a);
    }
}
