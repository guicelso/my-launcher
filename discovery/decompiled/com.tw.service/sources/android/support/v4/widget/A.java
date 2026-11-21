package android.support.v4.widget;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
class A extends Animation {
    final /* synthetic */ SwipeRefreshLayout n;

    A(SwipeRefreshLayout swipeRefreshLayout) {
        this.n = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        this.n.e(f);
    }
}
