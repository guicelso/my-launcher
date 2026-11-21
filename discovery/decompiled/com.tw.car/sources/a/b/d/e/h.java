package a.b.d.e;

/* JADX INFO: loaded from: classes.dex */
public class h implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Object f105a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ i f106b;

    public h(i iVar, Object obj) {
        this.f106b = iVar;
        this.f105a = obj;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f106b.f109c.a(this.f105a);
    }
}
