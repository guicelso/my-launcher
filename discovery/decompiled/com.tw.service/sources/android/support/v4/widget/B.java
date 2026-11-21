package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
class B extends Animation {
    final /* synthetic */ SwipeRefreshLayout n;

    B(SwipeRefreshLayout swipeRefreshLayout) {
        this.n = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        SwipeRefreshLayout swipeRefreshLayout = this.n;
        float f2 = swipeRefreshLayout.Ei;
        swipeRefreshLayout.f(((-f2) * f) + f2);
        this.n.e(f);
    }
}
