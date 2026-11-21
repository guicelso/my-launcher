package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
class z extends Animation {
    final /* synthetic */ SwipeRefreshLayout n;

    z(SwipeRefreshLayout swipeRefreshLayout) {
        this.n = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout swipeRefreshLayout = this.n;
        int iAbs = !swipeRefreshLayout.Pi ? swipeRefreshLayout.Gi - Math.abs(swipeRefreshLayout.Fi) : swipeRefreshLayout.Gi;
        SwipeRefreshLayout swipeRefreshLayout2 = this.n;
        int i = swipeRefreshLayout2.mFrom;
        this.n.z((i + ((int) ((iAbs - i) * f))) - swipeRefreshLayout2.Ci.getTop());
        this.n.Ii.a(1.0f - f);
    }
}
