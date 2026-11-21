package android.support.v4.app;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;

/* JADX INFO: loaded from: classes.dex */
class J extends AnimationSet implements Runnable {
    private final View ik;
    private boolean jk;
    private boolean mEnded;
    private final ViewGroup mParent;

    J(Animation animation, ViewGroup viewGroup, View view) {
        super(false);
        this.mParent = viewGroup;
        this.ik = view;
        addAnimation(animation);
    }

    @Override // android.view.animation.AnimationSet, android.view.animation.Animation
    public boolean getTransformation(long j, Transformation transformation) {
        if (this.mEnded) {
            return !this.jk;
        }
        if (!super.getTransformation(j, transformation)) {
            this.mEnded = true;
            pa.add(this.mParent, this);
        }
        return true;
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long j, Transformation transformation, float f) {
        if (this.mEnded) {
            return !this.jk;
        }
        if (!super.getTransformation(j, transformation, f)) {
            this.mEnded = true;
            pa.add(this.mParent, this);
        }
        return true;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mParent.endViewTransition(this.ik);
        this.jk = true;
    }
}
