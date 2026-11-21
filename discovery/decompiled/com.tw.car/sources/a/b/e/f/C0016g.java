package a.b.e.f;

import android.view.View;

/* JADX INFO: renamed from: a.b.e.f.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0016g extends D {
    public final /* synthetic */ C0015f.d j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0016g(C0015f.d dVar, View view, C0015f c0015f) {
        super(view);
        this.j = dVar;
    }

    @Override // a.b.e.f.D
    public a.b.e.e.a.w b() {
        C0015f.e eVar = C0015f.this.w;
        if (eVar == null) {
            return null;
        }
        return eVar.b();
    }

    @Override // a.b.e.f.D
    public boolean c() {
        C0015f.this.c();
        return true;
    }

    @Override // a.b.e.f.D
    public boolean d() {
        C0015f c0015f = C0015f.this;
        if (c0015f.y != null) {
            return false;
        }
        c0015f.a();
        return true;
    }
}
