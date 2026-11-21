package android.support.v4.app;

import android.animation.Animator;
import android.view.View;

/* JADX INFO: renamed from: android.support.v4.app.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0011l {
    private Object Zc = null;
    private Object _c;
    private Object ad;
    private Object bd;
    private Object cd;
    private Object dd;
    private Boolean ed;
    private Boolean gd;
    View mAnimatingAway;
    Animator mAnimator;
    qa mEnterTransitionCallback;
    boolean mEnterTransitionPostponed;
    qa mExitTransitionCallback;
    boolean mIsHideReplaced;
    int mNextAnim;
    int mNextTransition;
    int mNextTransitionStyle;
    N mStartEnterTransitionListener;
    int mStateAfterAnimating;

    C0011l() {
        Object obj = ComponentCallbacksC0013n.pd;
        this._c = obj;
        this.ad = null;
        this.bd = obj;
        this.cd = null;
        this.dd = obj;
    }
}
