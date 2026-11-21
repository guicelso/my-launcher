package android.support.v4.widget;

import android.animation.Animator;

/* JADX INFO: renamed from: android.support.v4.widget.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0031c implements Animator.AnimatorListener {
    final /* synthetic */ C0032d af;
    final /* synthetic */ C0033e n;

    C0031c(C0033e c0033e, C0032d c0032d) {
        this.n = c0033e;
        this.af = c0032d;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        C0033e.a(this.n, 1.0f, this.af, true);
        C0032d c0032d = this.af;
        c0032d.hf = c0032d.ef;
        c0032d.f0if = c0032d.ff;
        c0032d.jf = c0032d.Bc;
        c0032d.l((c0032d.gf + 1) % c0032d.mColors.length);
        if (!this.n.Dc) {
            this.n.Cc += 1.0f;
            return;
        }
        this.n.Dc = false;
        animator.cancel();
        animator.setDuration(1332L);
        animator.start();
        this.af.h(false);
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.n.Cc = 0.0f;
    }
}
