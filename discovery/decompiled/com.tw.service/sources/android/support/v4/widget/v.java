package android.support.v4.widget;

import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class v implements Animation.AnimationListener {
    final /* synthetic */ SwipeRefreshLayout n;

    v(SwipeRefreshLayout swipeRefreshLayout) {
        this.n = swipeRefreshLayout;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        C c;
        SwipeRefreshLayout swipeRefreshLayout = this.n;
        if (!swipeRefreshLayout.qi) {
            swipeRefreshLayout.reset();
            return;
        }
        swipeRefreshLayout.Ii.setAlpha(255);
        this.n.Ii.start();
        SwipeRefreshLayout swipeRefreshLayout2 = this.n;
        if (swipeRefreshLayout2.Ni && (c = swipeRefreshLayout2.mListener) != null) {
            c.c();
        }
        SwipeRefreshLayout swipeRefreshLayout3 = this.n;
        swipeRefreshLayout3.yi = swipeRefreshLayout3.Ci.getTop();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }
}
