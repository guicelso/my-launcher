package android.support.v4.app;

import android.animation.Animator;
import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class H {
    public final Animation animation;
    public final Animator animator;

    /* synthetic */ H(Animator animator, RunnableC0024z runnableC0024z) {
        this.animation = null;
        this.animator = animator;
        if (animator == null) {
            throw new IllegalStateException("Animator cannot be null");
        }
    }

    /* synthetic */ H(Animation animation, RunnableC0024z runnableC0024z) {
        this.animation = animation;
        this.animator = null;
        if (animation == null) {
            throw new IllegalStateException("Animation cannot be null");
        }
    }
}
