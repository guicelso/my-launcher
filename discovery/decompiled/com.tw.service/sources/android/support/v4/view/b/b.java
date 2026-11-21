package android.support.v4.view.b;

import android.view.animation.Interpolator;

/* JADX INFO: loaded from: classes.dex */
abstract class b implements Interpolator {
    private final float _e;
    private final float[] mValues;

    protected b(float[] fArr) {
        this.mValues = fArr;
        this._e = 1.0f / (this.mValues.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.mValues;
        int iMin = Math.min((int) ((fArr.length - 1) * f), fArr.length - 2);
        float f2 = this._e;
        float f3 = (f - (iMin * f2)) / f2;
        float[] fArr2 = this.mValues;
        return ((fArr2[iMin + 1] - fArr2[iMin]) * f3) + fArr2[iMin];
    }
}
