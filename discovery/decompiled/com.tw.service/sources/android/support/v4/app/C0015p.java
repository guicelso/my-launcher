package android.support.v4.app;

import android.view.View;
import android.view.Window;

/* JADX INFO: renamed from: android.support.v4.app.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0015p extends AbstractC0019u {
    final /* synthetic */ r n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0015p(r rVar) {
        super(rVar);
        this.n = rVar;
    }

    @Override // android.support.v4.app.AbstractC0017s
    public View onFindViewById(int i) {
        return this.n.findViewById(i);
    }

    @Override // android.support.v4.app.AbstractC0017s
    public boolean onHasView() {
        Window window = this.n.getWindow();
        return (window == null || window.peekDecorView() == null) ? false : true;
    }
}
