package android.support.v4.widget;

import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class y implements Animation.AnimationListener {
    final /* synthetic */ SwipeRefreshLayout n;

    y(SwipeRefreshLayout swipeRefreshLayout) {
        this.n = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        SwipeRefreshLayout swipeRefreshLayout = this.n;
        if (swipeRefreshLayout.Ai) {
            return;
        }
        swipeRefreshLayout.a(null);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }
}
