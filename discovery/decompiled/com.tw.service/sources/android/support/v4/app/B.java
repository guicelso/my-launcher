package android.support.v4.app;

import android.view.ViewGroup;
import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class B extends G {
    final /* synthetic */ ViewGroup j;
    final /* synthetic */ ComponentCallbacksC0013n m;
    final /* synthetic */ O n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    B(O o, Animation.AnimationListener animationListener, ViewGroup viewGroup, ComponentCallbacksC0013n componentCallbacksC0013n) {
        super(animationListener, null);
        this.n = o;
        this.j = viewGroup;
        this.m = componentCallbacksC0013n;
    }

    @Override // android.support.v4.app.G, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.j.post(new A(this));
    }
}
