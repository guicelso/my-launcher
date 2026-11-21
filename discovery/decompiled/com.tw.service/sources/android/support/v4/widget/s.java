package android.support.v4.widget;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ SlidingPaneLayout n;
    final View uf;

    s(SlidingPaneLayout slidingPaneLayout, View view) {
        this.n = slidingPaneLayout;
        this.uf = view;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.uf.getParent() == this.n) {
            this.uf.setLayerType(0, null);
            this.n.H(this.uf);
        }
        this.n.oi.remove(this);
    }
}
