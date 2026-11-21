package a.b.d.h.b;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
public abstract class d implements Interpolator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final float[] f154a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final float f155b;

    public d(float[] fArr) {
        this.f154a = fArr;
        this.f155b = 1.0f / (this.f154a.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.f154a;
        int iMin = Math.min((int) ((fArr.length - 1) * f), fArr.length - 2);
        float f2 = this.f155b;
        float f3 = (f - (iMin * f2)) / f2;
        float[] fArr2 = this.f154a;
        return ((fArr2[iMin + 1] - fArr2[iMin]) * f3) + fArr2[iMin];
    }
}
