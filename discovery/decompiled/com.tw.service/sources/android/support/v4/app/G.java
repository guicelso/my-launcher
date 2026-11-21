package android.support.v4.app;

import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class G implements Animation.AnimationListener {
    private final Animation.AnimationListener mWrapped;

    /* synthetic */ G(Animation.AnimationListener animationListener, RunnableC0024z runnableC0024z) {
        this.mWrapped = animationListener;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        Animation.AnimationListener animationListener = this.mWrapped;
        if (animationListener != null) {
            animationListener.onAnimationEnd(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
        Animation.AnimationListener animationListener = this.mWrapped;
        if (animationListener != null) {
            animationListener.onAnimationRepeat(animation);
        }
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
        Animation.AnimationListener animationListener = this.mWrapped;
        if (animationListener != null) {
            animationListener.onAnimationStart(animation);
        }
    }
}
