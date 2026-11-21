package a.b.e.d.a;

/* JADX INFO: loaded from: classes.dex */
public class c implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ d f196a;

    public c(d dVar) {
        this.f196a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f196a.a(true);
        this.f196a.invalidateSelf();
    }
}
