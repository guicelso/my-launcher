package android.support.v4.widget;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: renamed from: android.support.v4.widget.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0032d {
    int gf;
    float hf;

    /* JADX INFO: renamed from: if, reason: not valid java name */
    float f0if;
    float jf;
    boolean kf;
    Path lf;
    int[] mColors;
    float nf;
    int of;
    int pf;
    int qf;
    final RectF bf = new RectF();
    final Paint mPaint = new Paint();
    final Paint cf = new Paint();
    final Paint df = new Paint();
    float ef = 0.0f;
    float ff = 0.0f;
    float Bc = 0.0f;
    float mStrokeWidth = 5.0f;
    float mf = 1.0f;
    int mAlpha = 255;

    C0032d() {
        this.mPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.cf.setStyle(Paint.Style.FILL);
        this.cf.setAntiAlias(true);
        this.df.setColor(0);
    }

    float K() {
        return this.f0if;
    }

    void h(boolean z) {
        if (this.kf != z) {
            this.kf = z;
        }
    }

    void l(int i) {
        this.gf = i;
        this.qf = this.mColors[this.gf];
    }

    void setStrokeWidth(float f) {
        this.mStrokeWidth = f;
        this.mPaint.setStrokeWidth(f);
    }
}
