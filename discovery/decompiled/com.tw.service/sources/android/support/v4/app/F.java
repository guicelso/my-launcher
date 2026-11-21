package android.support.v4.app;

import android.os.Build;
import android.view.View;
import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
class F extends G {
    View mView;

    F(View view, Animation.AnimationListener animationListener) {
        super(animationListener, null);
        this.mView = view;
    }

    @Override // android.support.v4.app.G, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        if (!android.support.v4.view.r.k(this.mView)) {
            int i = Build.VERSION.SDK_INT;
        }
        this.mView.post(new E(this));
        super.onAnimationEnd(animation);
    }
}
