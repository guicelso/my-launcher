package android.support.v4.app;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class A implements Runnable {
    final /* synthetic */ B Rc;

    A(B b2) {
        this.Rc = b2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Rc.m.getAnimatingAway() != null) {
            this.Rc.m.c((View) null);
            B b2 = this.Rc;
            O o = b2.n;
            ComponentCallbacksC0013n componentCallbacksC0013n = b2.m;
            o.a(componentCallbacksC0013n, componentCallbacksC0013n.getStateAfterAnimating(), 0, 0, false);
        }
    }
}
