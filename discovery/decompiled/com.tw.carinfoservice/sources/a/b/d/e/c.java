package a.b.d.e;

import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public final class c implements f.c<e.d> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ a.b.d.b.b.g f88a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ Handler f89b;

    public c(a.b.d.b.b.g gVar, Handler handler) {
        this.f88a = gVar;
        this.f89b = handler;
    }

    @Override // a.b.d.e.f.c
    public void a(e.d dVar) {
        int i;
        a.b.d.b.b.g gVar;
        e.d dVar2 = dVar;
        if (dVar2 == null) {
            gVar = this.f88a;
            i = 1;
        } else {
            i = dVar2.f100b;
            if (i == 0) {
                this.f88a.b(dVar2.f99a, this.f89b);
                return;
            }
            gVar = this.f88a;
        }
        gVar.a(i, this.f89b);
    }
}
