package android.support.v4.widget;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.animation.Animation;
import android.widget.ImageView;

/* JADX INFO: renamed from: android.support.v4.widget.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0029a extends ImageView {
    private Animation.AnimationListener mListener;
    int mShadowRadius;

    C0029a(Context context, int i) {
        super(context);
        float f = getContext().getResources().getDisplayMetrics().density;
        this.mShadowRadius = (int) (3.5f * f);
        int i2 = Build.VERSION.SDK_INT;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        android.support.v4.view.r.b(this, f * 4.0f);
        shapeDrawable.getPaint().setColor(i);
        int i3 = Build.VERSION.SDK_INT;
        setBackground(shapeDrawable);
    }

    @Override // android.view.View
    public void onAnimationEnd() {
        super.onAnimationEnd();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationEnd(getAnimation());
        }
    }

    @Override // android.view.View
    public void onAnimationStart() {
        super.onAnimationStart();
        Animation.AnimationListener animationListener = this.mListener;
        if (animationListener != null) {
            animationListener.onAnimationStart(getAnimation());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = Build.VERSION.SDK_INT;
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.mListener = animationListener;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (getBackground() instanceof ShapeDrawable) {
            ((ShapeDrawable) getBackground()).getPaint().setColor(i);
        }
    }
}
