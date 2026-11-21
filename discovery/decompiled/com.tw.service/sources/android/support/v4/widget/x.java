package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
class x extends Animation {
    final /* synthetic */ int gk;
    final /* synthetic */ int hk;
    final /* synthetic */ SwipeRefreshLayout n;

    x(SwipeRefreshLayout swipeRefreshLayout, int i, int i2) {
        this.n = swipeRefreshLayout;
        this.gk = i;
        this.hk = i2;
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        C0033e c0033e = this.n.Ii;
        int i = this.gk;
        c0033e.setAlpha((int) (((this.hk - i) * f) + i));
    }
}
