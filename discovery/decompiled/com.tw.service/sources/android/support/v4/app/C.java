package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
class C extends AnimatorListenerAdapter {
    final /* synthetic */ ViewGroup j;
    final /* synthetic */ View k;
    final /* synthetic */ ComponentCallbacksC0013n m;
    final /* synthetic */ O n;

    C(O o, ViewGroup viewGroup, View view, ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.n = o;
        this.j = viewGroup;
        this.k = view;
        this.m = componentCallbacksC0013n;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.j.endViewTransition(this.k);
        Animator animator2 = this.m.getAnimator();
        this.m.a((Animator) null);
        if (animator2 == null || this.j.indexOfChild(this.k) >= 0) {
            return;
        }
        O o = this.n;
        ComponentCallbacksC0013n componentCallbacksC0013n = this.m;
        o.a(componentCallbacksC0013n, componentCallbacksC0013n.getStateAfterAnimating(), 0, 0, false);
    }
}
