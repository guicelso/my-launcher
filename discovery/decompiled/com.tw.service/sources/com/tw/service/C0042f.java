package com.tw.service;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: renamed from: com.tw.service.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0042f {
    final /* synthetic */ RadarView n;
    float sk;
    float tk;
    int uk;

    /* synthetic */ C0042f(RadarView radarView, C0041e c0041e) {
        this.n = radarView;
    }

    public void a(float f, float f2, int i, int i2) {
        this.sk = f;
        this.tk = f2;
        this.uk = i2;
    }

    public void a(Canvas canvas, Path path, float f, float f2, float f3, float f4) {
        Paint paint;
        int i;
        for (int i2 = 1; i2 < this.uk + 1; i2++) {
            if (i2 > 2) {
                paint = this.n.paint;
                i = -16711936;
            } else {
                paint = this.n.paint;
                i = -65536;
            }
            paint.setColor(i);
            int i3 = this.n.Ij;
            RectF rectF = new RectF(f - (i3 * i2), f2 - (i3 * i2), (i3 * i2) + f3, (i3 * i2) + f4);
            path.reset();
            path.arcTo(rectF, this.sk, this.tk);
            canvas.drawPath(path, this.n.paint);
        }
    }

    public void a(Canvas canvas, Path path, int i, float f, float f2, float f3, float f4) {
        Paint paint;
        int i2;
        for (int i3 = 1; i3 < this.uk + 1; i3++) {
            if (i3 > 2) {
                paint = this.n.paint;
                i2 = -16711936;
            } else {
                paint = this.n.paint;
                i2 = -65536;
            }
            paint.setColor(i2);
            if (this.n.orientation == 0) {
                float f5 = f2 > 0.0f ? f2 - (this.n.Ij * i3) : (this.n.Ij * i3) + f4;
                float intrinsicWidth = (this.n.src.getIntrinsicWidth() / 4) + this.n.qj;
                float intrinsicWidth2 = f + (this.n.src.getIntrinsicWidth() / 4);
                RadarView radarView = this.n;
                float f6 = intrinsicWidth2 + radarView.Hj;
                float fAa = radarView.aa(radarView.Kj);
                float f7 = ((intrinsicWidth * 2.0f) - (3.0f * fAa)) / 2.0f;
                path.reset();
                float f8 = f6 + fAa;
                float f9 = f8 + f7;
                if (i == 1) {
                    path.moveTo(f8, f5);
                    path.lineTo(f9, f5);
                } else {
                    path.reset();
                    float f10 = f9 + fAa;
                    path.moveTo(f10, f5);
                    path.lineTo(f7 + f10, f5);
                }
            } else {
                float f11 = f > 0.0f ? f - (this.n.Ij * i3) : (this.n.Ij * i3) + f3;
                float intrinsicHeight = (this.n.src.getIntrinsicHeight() / 4) + this.n.qj;
                float intrinsicHeight2 = f2 + (this.n.src.getIntrinsicHeight() / 4);
                RadarView radarView2 = this.n;
                float f12 = intrinsicHeight2 + radarView2.Hj;
                float fAa2 = radarView2.aa(radarView2.Kj);
                float f13 = ((intrinsicHeight * 2.0f) - (3.0f * fAa2)) / 2.0f;
                path.reset();
                float f14 = f12 + fAa2;
                float f15 = f14 + f13;
                if (i == 1) {
                    path.moveTo(f11, f14);
                    path.lineTo(f11, f15);
                } else {
                    path.reset();
                    float f16 = f15 + fAa2;
                    path.moveTo(f11, f16);
                    path.lineTo(f11, f13 + f16);
                }
            }
            canvas.drawPath(path, this.n.paint);
        }
    }

    public void i(int i, int i2) {
        this.uk = i2;
    }
}
