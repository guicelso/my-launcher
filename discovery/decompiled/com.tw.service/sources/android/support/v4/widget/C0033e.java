package android.support.v4.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* JADX INFO: renamed from: android.support.v4.widget.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0033e extends Drawable implements Animatable {
    private static final Interpolator Ec = new LinearInterpolator();
    private static final Interpolator Fc = new android.support.v4.view.b.a();
    private static final int[] Gc = {-16777216};
    private final C0032d Ac;
    private float Bc;
    private float Cc;
    private boolean Dc;
    private Animator mAnimator;
    private Resources mResources;

    public C0033e(Context context) {
        if (context == null) {
            throw new NullPointerException();
        }
        this.mResources = context.getResources();
        this.Ac = new C0032d();
        C0032d c0032d = this.Ac;
        c0032d.mColors = Gc;
        c0032d.l(0);
        this.Ac.setStrokeWidth(2.5f);
        invalidateSelf();
        C0032d c0032d2 = this.Ac;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new C0030b(this, c0032d2));
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setRepeatMode(1);
        valueAnimatorOfFloat.setInterpolator(Ec);
        valueAnimatorOfFloat.addListener(new C0031c(this, c0032d2));
        this.mAnimator = valueAnimatorOfFloat;
    }

    static /* synthetic */ void a(C0033e c0033e, float f, C0032d c0032d, boolean z) {
        float interpolation;
        float interpolation2;
        if (c0033e.Dc) {
            c0033e.a(f, c0032d);
            float fFloor = (float) (Math.floor(c0032d.jf / 0.8f) + 1.0d);
            c0032d.ef = (((c0032d.K() - 0.01f) - c0032d.hf) * f) + c0032d.hf;
            c0032d.ff = c0032d.K();
            float f2 = c0032d.jf;
            c0032d.Bc = ((fFloor - f2) * f) + f2;
            return;
        }
        if (f != 1.0f || z) {
            float f3 = c0032d.jf;
            if (f < 0.5f) {
                float f4 = c0032d.hf;
                interpolation = (Fc.getInterpolation(f / 0.5f) * 0.79f) + 0.01f + f4;
                interpolation2 = f4;
            } else {
                interpolation = c0032d.hf + 0.79f;
                interpolation2 = interpolation - (((1.0f - Fc.getInterpolation((f - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
            }
            float f5 = (0.20999998f * f) + f3;
            float f6 = (f + c0033e.Cc) * 216.0f;
            c0032d.ef = interpolation2;
            c0032d.ff = interpolation;
            c0032d.Bc = f5;
            c0033e.setRotation(f6);
        }
    }

    private void setRotation(float f) {
        this.Bc = f;
    }

    public void b(float f) {
        this.Ac.Bc = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.Bc, bounds.exactCenterX(), bounds.exactCenterY());
        C0032d c0032d = this.Ac;
        RectF rectF = c0032d.bf;
        float f = c0032d.nf;
        float fMin = (c0032d.mStrokeWidth / 2.0f) + f;
        if (f <= 0.0f) {
            fMin = (Math.min(bounds.width(), bounds.height()) / 2.0f) - Math.max((c0032d.of * c0032d.mf) / 2.0f, c0032d.mStrokeWidth / 2.0f);
        }
        rectF.set(bounds.centerX() - fMin, bounds.centerY() - fMin, bounds.centerX() + fMin, bounds.centerY() + fMin);
        float f2 = c0032d.ef;
        float f3 = c0032d.Bc;
        float f4 = (f2 + f3) * 360.0f;
        float f5 = ((c0032d.ff + f3) * 360.0f) - f4;
        c0032d.mPaint.setColor(c0032d.qf);
        c0032d.mPaint.setAlpha(c0032d.mAlpha);
        float f6 = c0032d.mStrokeWidth / 2.0f;
        rectF.inset(f6, f6);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, c0032d.df);
        float f7 = -f6;
        rectF.inset(f7, f7);
        canvas.drawArc(rectF, f4, f5, false, c0032d.mPaint);
        if (c0032d.kf) {
            Path path = c0032d.lf;
            if (path == null) {
                c0032d.lf = new Path();
                c0032d.lf.setFillType(Path.FillType.EVEN_ODD);
            } else {
                path.reset();
            }
            float fMin2 = Math.min(rectF.width(), rectF.height()) / 2.0f;
            float f8 = (c0032d.of * c0032d.mf) / 2.0f;
            c0032d.lf.moveTo(0.0f, 0.0f);
            c0032d.lf.lineTo(c0032d.of * c0032d.mf, 0.0f);
            Path path2 = c0032d.lf;
            float f9 = c0032d.of;
            float f10 = c0032d.mf;
            path2.lineTo((f9 * f10) / 2.0f, c0032d.pf * f10);
            c0032d.lf.offset((rectF.centerX() + fMin2) - f8, (c0032d.mStrokeWidth / 2.0f) + rectF.centerY());
            c0032d.lf.close();
            c0032d.cf.setColor(c0032d.qf);
            c0032d.cf.setAlpha(c0032d.mAlpha);
            canvas.save();
            canvas.rotate(f4 + f5, rectF.centerX(), rectF.centerY());
            canvas.drawPath(c0032d.lf, c0032d.cf);
            canvas.restore();
        }
        canvas.restore();
    }

    public void f(boolean z) {
        C0032d c0032d = this.Ac;
        if (c0032d.kf != z) {
            c0032d.kf = z;
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.Ac.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mAnimator.isRunning();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.Ac.mAlpha = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.Ac.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setStyle(int i) {
        float f;
        float f2;
        float f3;
        float f4;
        if (i == 0) {
            f = 11.0f;
            f2 = 3.0f;
            f3 = 12.0f;
            f4 = 6.0f;
        } else {
            f = 7.5f;
            f2 = 2.5f;
            f3 = 10.0f;
            f4 = 5.0f;
        }
        b(f, f2, f3, f4);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        Animator animator;
        long j;
        this.mAnimator.cancel();
        C0032d c0032d = this.Ac;
        float f = c0032d.ef;
        c0032d.hf = f;
        float f2 = c0032d.ff;
        c0032d.f0if = f2;
        c0032d.jf = c0032d.Bc;
        if (f2 != f) {
            this.Dc = true;
            animator = this.mAnimator;
            j = 666;
        } else {
            c0032d.l(0);
            C0032d c0032d2 = this.Ac;
            c0032d2.hf = 0.0f;
            c0032d2.f0if = 0.0f;
            c0032d2.jf = 0.0f;
            c0032d2.ef = 0.0f;
            c0032d2.ff = 0.0f;
            c0032d2.Bc = 0.0f;
            animator = this.mAnimator;
            j = 1332;
        }
        animator.setDuration(j);
        this.mAnimator.start();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mAnimator.cancel();
        this.Bc = 0.0f;
        this.Ac.h(false);
        this.Ac.l(0);
        C0032d c0032d = this.Ac;
        c0032d.hf = 0.0f;
        c0032d.f0if = 0.0f;
        c0032d.jf = 0.0f;
        c0032d.ef = 0.0f;
        c0032d.ff = 0.0f;
        c0032d.Bc = 0.0f;
        invalidateSelf();
    }

    private void b(float f, float f2, float f3, float f4) {
        C0032d c0032d = this.Ac;
        float f5 = this.mResources.getDisplayMetrics().density;
        float f6 = f2 * f5;
        c0032d.mStrokeWidth = f6;
        c0032d.mPaint.setStrokeWidth(f6);
        c0032d.nf = f * f5;
        c0032d.l(0);
        c0032d.of = (int) (f3 * f5);
        c0032d.pf = (int) (f4 * f5);
    }

    public void a(float f) {
        C0032d c0032d = this.Ac;
        if (f != c0032d.mf) {
            c0032d.mf = f;
        }
        invalidateSelf();
    }

    public void a(float f, float f2) {
        C0032d c0032d = this.Ac;
        c0032d.ef = f;
        c0032d.ff = f2;
        invalidateSelf();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(float f, C0032d c0032d) {
        int i;
        if (f > 0.75f) {
            float f2 = (f - 0.75f) / 0.25f;
            int i2 = c0032d.mColors[c0032d.gf];
            int[] iArr = c0032d.mColors;
            int i3 = iArr[(c0032d.gf + 1) % iArr.length];
            int i4 = (i2 >> 24) & 255;
            int i5 = (i2 >> 16) & 255;
            int i6 = (i2 >> 8) & 255;
            int i7 = i2 & 255;
            i = (i7 + ((int) (f2 * ((i3 & 255) - i7)))) | ((i4 + ((int) ((((i3 >> 24) & 255) - i4) * f2))) << 24) | ((i5 + ((int) ((((i3 >> 16) & 255) - i5) * f2))) << 16) | ((i6 + ((int) ((((i3 >> 8) & 255) - i6) * f2))) << 8);
        } else {
            i = c0032d.mColors[c0032d.gf];
        }
        c0032d.qf = i;
    }
}
