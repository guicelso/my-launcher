package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
class D extends AnimatorListenerAdapter {
    final /* synthetic */ ViewGroup j;
    final /* synthetic */ ComponentCallbacksC0013n m;
    final /* synthetic */ View o;

    D(O o, ViewGroup viewGroup, View view, ComponentCallbacksC0013n componentCallbacksC0013n) {
        this.j = viewGroup;
        this.o = view;
        this.m = componentCallbacksC0013n;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.j.endViewTransition(this.o);
        animator.removeListener(this);
        View view = this.m.mView;
        if (view != null) {
            view.setVisibility(8);
        }
    }
}
