package a.b.e.f;

import android.view.View;

/* JADX INFO: renamed from: a.b.e.f.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0025p extends D {
    public final /* synthetic */ C0026q.b j;
    public final /* synthetic */ C0026q k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0025p(C0026q c0026q, View view, C0026q.b bVar) {
        super(view);
        this.k = c0026q;
        this.j = bVar;
    }

    @Override // a.b.e.f.D
    public a.b.e.e.a.w b() {
        return this.j;
    }

    @Override // a.b.e.f.D
    public boolean c() {
        if (this.k.mPopup.isShowing()) {
            return true;
        }
        this.k.mPopup.show();
        return true;
    }
}
