package android.support.design.widget;

import android.view.ViewTreeObserver;

/* JADX INFO: loaded from: classes.dex */
class f implements ViewTreeObserver.OnPreDrawListener {
    final /* synthetic */ CoordinatorLayout n;

    f(CoordinatorLayout coordinatorLayout) {
        this.n = coordinatorLayout;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        this.n.t(0);
        return true;
    }
}
