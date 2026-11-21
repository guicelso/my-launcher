package android.support.v4.widget;

import android.animation.ValueAnimator;

/* JADX INFO: renamed from: android.support.v4.widget.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0030b implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ C0032d af;
    final /* synthetic */ C0033e n;

    C0030b(C0033e c0033e, C0032d c0032d) {
        this.n = c0033e;
        this.af = c0032d;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.n.a(fFloatValue, this.af);
        C0033e.a(this.n, fFloatValue, this.af, false);
        this.n.invalidateSelf();
    }
}
