package a.b.d.e;

import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
class c implements k.a<f.c> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a.b.d.b.a.j f91a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Handler f92b;

    public c(a.b.d.b.a.j jVar, Handler handler) {
        this.f91a = jVar;
        this.f92b = handler;
    }

    @Override // a.b.d.e.k.a
    public void a(f.c cVar) {
        int i;
        a.b.d.b.a.j jVar;
        f.c cVar2 = cVar;
        if (cVar2 == null) {
            jVar = this.f91a;
            i = 1;
        } else {
            i = cVar2.f103b;
            if (i == 0) {
                this.f91a.a(cVar2.f102a, this.f92b);
                return;
            }
            jVar = this.f91a;
        }
        jVar.a(i, this.f92b);
    }
}
