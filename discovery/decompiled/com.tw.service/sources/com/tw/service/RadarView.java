package com.tw.service;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class RadarView extends View {
    private int[] Aj;
    private float Bj;
    private float Cj;
    private float Dj;
    private float Ej;
    private float Fj;
    private float Gj;
    int Hj;
    int Ij;
    int Jj;
    private int Kj;
    private int height;
    private int orientation;
    private Paint paint;
    private int pj;
    private int qj;
    private int rj;
    private int sj;
    private Drawable src;
    private C0042f[] tj;
    private C0042f[] uj;
    private C0042f[] vj;
    private int width;
    private C0042f[] wj;
    private int[] xj;
    private int[] yj;
    private int[] zj;

    public RadarView(Context context) {
        this(context, null, -1);
    }

    public RadarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public RadarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.src = null;
        this.paint = new Paint(1);
        this.orientation = 0;
        this.pj = 0;
        this.qj = 0;
        this.rj = 10;
        this.sj = 2;
        this.tj = new C0042f[4];
        this.uj = new C0042f[4];
        this.vj = new C0042f[4];
        this.wj = new C0042f[4];
        this.xj = new int[4];
        this.yj = new int[4];
        this.zj = new int[4];
        this.Aj = new int[4];
        this.Bj = -180.0f;
        this.Cj = 0.0f;
        this.Dj = 40.0f;
        this.Ej = 20.0f;
        this.Fj = 30.0f;
        this.Gj = 1.0f;
        this.Hj = (int) aa(5);
        this.Ij = (int) aa(4);
        this.Jj = (int) aa(3);
        this.Kj = (int) aa(1);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.Jj);
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0040d.rk);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(3);
            this.orientation = typedArrayObtainStyledAttributes.getInteger(2, 1);
            this.pj = typedArrayObtainStyledAttributes.getInteger(1, 3);
            this.qj = typedArrayObtainStyledAttributes.getInteger(0, 0);
            this.Hj = (int) aa(this.pj + 2);
            this.Ij = (int) aa(this.pj + 1);
            this.Jj = (int) aa(this.pj);
            if (this.orientation == 0) {
                this.Bj = -270.0f;
                this.Cj = -90.0f;
            } else {
                this.Bj = -180.0f;
                this.Cj = 0.0f;
            }
            if (drawable != null) {
                this.src = drawable;
            }
        }
    }

    private float Z(int i) {
        if (i == 0) {
            return this.Ej;
        }
        if (i == 1 || i == 2) {
            return Math.abs(this.Fj - this.Gj);
        }
        if (i != 3) {
            return -1.0f;
        }
        return this.Ej;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float aa(int i) {
        return TypedValue.applyDimension(1, i, Resources.getSystem().getDisplayMetrics());
    }

    private float b(int i, boolean z) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5 = z ? this.Bj : this.Cj;
        if (i == 0) {
            return f5 + this.Dj;
        }
        if (i != 1) {
            if (i == 2) {
                f = f5 + this.Dj + this.Ej;
                f2 = this.Fj;
            } else {
                if (i != 3) {
                    return -1.0f;
                }
                f3 = f5 + this.Dj + this.Ej;
                f4 = this.Fj * 2.0f;
            }
            return f4 + f3;
        }
        f = f5 + this.Dj;
        f2 = this.Ej;
        f3 = f + f2;
        f4 = this.Gj / 2.0f;
        return f4 + f3;
    }

    public void a(int i, int i2, int i3, int i4) {
        int[] iArr = this.xj;
        int i5 = this.rj;
        if (i > i5) {
            i = i5;
        }
        iArr[0] = i;
        int[] iArr2 = this.xj;
        int i6 = this.rj;
        if (i2 > i6) {
            i2 = i6;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.xj;
        int i7 = this.rj;
        if (i3 > i7) {
            i3 = i7;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.xj;
        int i8 = this.rj;
        if (i4 <= i8) {
            i8 = i4;
        }
        iArr4[3] = i8;
        invalidate();
    }

    public void b(int i, int i2, int i3, int i4) {
        int[] iArr = this.zj;
        int i5 = this.rj;
        if (i > i5) {
            i = i5;
        }
        iArr[0] = i;
        int[] iArr2 = this.zj;
        int i6 = this.rj;
        if (i2 > i6) {
            i2 = i6;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.zj;
        int i7 = this.rj;
        if (i3 > i7) {
            i3 = i7;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.zj;
        int i8 = this.rj;
        if (i4 <= i8) {
            i8 = i4;
        }
        iArr4[3] = i8;
        invalidate();
    }

    public void c(int i, int i2, int i3, int i4) {
        int[] iArr = this.Aj;
        int i5 = this.rj;
        if (i > i5) {
            i = i5;
        }
        iArr[0] = i;
        int[] iArr2 = this.Aj;
        int i6 = this.rj;
        if (i2 > i6) {
            i2 = i6;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.Aj;
        int i7 = this.rj;
        if (i3 > i7) {
            i3 = i7;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.Aj;
        int i8 = this.rj;
        if (i4 <= i8) {
            i8 = i4;
        }
        iArr4[3] = i8;
        invalidate();
    }

    public void d(int i, int i2, int i3, int i4) {
        int[] iArr = this.yj;
        int i5 = this.rj;
        if (i > i5) {
            i = i5;
        }
        iArr[0] = i;
        int[] iArr2 = this.yj;
        int i6 = this.rj;
        if (i2 > i6) {
            i2 = i6;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.yj;
        int i7 = this.rj;
        if (i3 > i7) {
            i3 = i7;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.yj;
        int i8 = this.rj;
        if (i4 <= i8) {
            i8 = i4;
        }
        iArr4[3] = i8;
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        C0041e c0041e;
        float f;
        float f2;
        float f3;
        float intrinsicHeight;
        float intrinsicWidth;
        float intrinsicWidth2;
        float intrinsicHeight2;
        int i;
        float f4;
        float f5;
        int i2;
        float f6;
        float intrinsicHeight3;
        float intrinsicWidth3;
        float intrinsicWidth4;
        float intrinsicHeight4;
        float intrinsicHeight5;
        float intrinsicWidth5;
        float intrinsicHeight6;
        float intrinsicWidth6;
        super.onDraw(canvas);
        if (this.src == null) {
            return;
        }
        int i3 = 0;
        int i4 = 0;
        while (true) {
            c0041e = null;
            if (i4 >= this.tj.length) {
                break;
            }
            C0042f c0042f = new C0042f(this, c0041e);
            c0042f.a(b(i4, true), Z(i4), this.sj, this.xj[i4]);
            this.tj[i4] = c0042f;
            i4++;
        }
        for (int i5 = 0; i5 < this.uj.length; i5++) {
            C0042f c0042f2 = new C0042f(this, c0041e);
            c0042f2.a(b((this.uj.length - i5) - 1, false), Z((this.uj.length - i5) - 1), this.sj, this.yj[i5]);
            this.uj[i5] = c0042f2;
        }
        for (int i6 = 0; i6 < this.vj.length; i6++) {
            C0042f c0042f3 = new C0042f(this, c0041e);
            if (i6 == 0) {
                c0042f3.a(this.Bj, this.Dj - this.Gj, this.sj, this.zj[i6]);
            } else if (i6 == this.vj.length - 1) {
                float f7 = this.Cj;
                float f8 = this.Ej;
                float f9 = this.Dj;
                float f10 = this.Gj;
                c0042f3.a(((f8 + f9) * 2.0f) + f7 + f8 + f10, f9 - f10, this.sj, this.zj[i6]);
            } else {
                c0042f3.i(this.sj, this.zj[i6]);
            }
            this.vj[i6] = c0042f3;
        }
        for (int i7 = 0; i7 < this.wj.length; i7++) {
            C0042f c0042f4 = new C0042f(this, c0041e);
            if (i7 == 0) {
                float f11 = this.Bj;
                float f12 = this.Ej;
                float f13 = this.Dj;
                float f14 = ((f12 + f13) * 2.0f) + f11 + f12;
                float f15 = this.Gj;
                c0042f4.a(f14 + f15, f13 - f15, this.sj, this.Aj[i7]);
            } else if (i7 == this.wj.length - 1) {
                c0042f4.a(this.Cj, this.Dj - this.Gj, this.sj, this.Aj[i7]);
            } else {
                c0042f4.i(this.sj, this.Aj[i7]);
            }
            this.wj[i7] = c0042f4;
        }
        Matrix matrix = new Matrix();
        matrix.setTranslate((this.width / 2) - (this.src.getIntrinsicWidth() / 2), (this.height / 2) - (this.src.getIntrinsicHeight() / 2));
        Drawable drawable = this.src;
        int intrinsicWidth7 = drawable.getIntrinsicWidth();
        int intrinsicHeight7 = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth7, intrinsicHeight7, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth7, intrinsicHeight7);
        drawable.draw(canvas2);
        canvas.drawBitmap(bitmapCreateBitmap, matrix, this.paint);
        float intrinsicWidth8 = (this.width / 2) - (this.src.getIntrinsicWidth() / 2);
        float intrinsicHeight8 = (this.height / 2) - (this.src.getIntrinsicHeight() / 2);
        float intrinsicWidth9 = this.src.getIntrinsicWidth() + intrinsicWidth8;
        float intrinsicHeight9 = this.src.getIntrinsicHeight() + intrinsicHeight8;
        int i8 = this.Hj;
        float f16 = intrinsicWidth8 - i8;
        float f17 = intrinsicHeight8 - i8;
        float f18 = intrinsicWidth9 + i8;
        float f19 = intrinsicHeight9 + i8;
        Path path = new Path();
        int i9 = 0;
        while (true) {
            C0042f[] c0042fArr = this.tj;
            if (i9 >= c0042fArr.length) {
                break;
            }
            C0042f c0042f5 = c0042fArr[i9];
            if (c0042f5 != null) {
                if (this.orientation == 0) {
                    intrinsicWidth6 = (f18 - (this.src.getIntrinsicWidth() / 2)) - (this.qj * 2);
                    intrinsicHeight6 = f19;
                } else {
                    intrinsicHeight6 = (f19 - (this.src.getIntrinsicHeight() / 2)) - (this.qj * 2);
                    intrinsicWidth6 = f18;
                }
                c0042f5.a(canvas, path, f16, f17, intrinsicWidth6, intrinsicHeight6);
            }
            i9++;
        }
        int i10 = 0;
        while (true) {
            C0042f[] c0042fArr2 = this.uj;
            if (i10 >= c0042fArr2.length) {
                break;
            }
            C0042f c0042f6 = c0042fArr2[i10];
            if (c0042f6 != null) {
                if (this.orientation == 0) {
                    intrinsicWidth5 = (this.src.getIntrinsicWidth() / 2) + f16 + (this.qj * 2);
                    intrinsicHeight5 = f17;
                } else {
                    intrinsicHeight5 = (this.src.getIntrinsicHeight() / 2) + f17 + (this.qj * 2);
                    intrinsicWidth5 = f16;
                }
                c0042f6.a(canvas, path, intrinsicWidth5, intrinsicHeight5, f18, f19);
            }
            i10++;
        }
        int i11 = 0;
        while (true) {
            C0042f[] c0042fArr3 = this.vj;
            if (i11 >= c0042fArr3.length) {
                break;
            }
            C0042f c0042f7 = c0042fArr3[i11];
            if (c0042f7 == null) {
                i = i11;
            } else {
                if (i11 == 0) {
                    if (this.orientation == 0) {
                        intrinsicWidth4 = (f18 - (this.src.getIntrinsicWidth() / 2)) - (this.qj * 2);
                        c0042f7 = c0042f7;
                        canvas = canvas;
                        path = path;
                        intrinsicWidth3 = f16;
                        intrinsicHeight3 = f17;
                    } else {
                        intrinsicHeight4 = (f19 - (this.src.getIntrinsicHeight() / 2)) - (this.qj * 2);
                        c0042f7 = c0042f7;
                        canvas = canvas;
                        path = path;
                        intrinsicWidth3 = f16;
                        intrinsicHeight3 = f17;
                        intrinsicWidth4 = f18;
                    }
                    c0042f7.a(canvas, path, intrinsicWidth3, intrinsicHeight3, intrinsicWidth4, intrinsicHeight4);
                    i = i11;
                } else if (i11 == c0042fArr3.length - 1) {
                    if (this.orientation == 0) {
                        intrinsicWidth3 = (this.src.getIntrinsicWidth() / 2) + f16 + (this.qj * 2);
                        intrinsicHeight3 = f17;
                    } else {
                        intrinsicHeight3 = (this.src.getIntrinsicHeight() / 2) + f17 + (this.qj * 2);
                        intrinsicWidth3 = f16;
                    }
                    intrinsicWidth4 = f18;
                } else {
                    if (this.orientation == 0) {
                        f6 = f16 - this.qj;
                        f4 = -1.0f;
                        i2 = i11;
                        f5 = f18;
                        i = i11;
                    } else {
                        i = i11;
                        f4 = f17 - this.qj;
                        f5 = -1.0f;
                        i2 = i;
                        f6 = f16;
                    }
                    c0042f7.a(canvas, path, i2, f6, f4, f5, f19);
                }
                intrinsicHeight4 = f19;
                c0042f7.a(canvas, path, intrinsicWidth3, intrinsicHeight3, intrinsicWidth4, intrinsicHeight4);
                i = i11;
            }
            i11 = i + 1;
        }
        while (true) {
            C0042f[] c0042fArr4 = this.wj;
            if (i3 >= c0042fArr4.length) {
                return;
            }
            C0042f c0042f8 = c0042fArr4[i3];
            if (c0042f8 != null) {
                if (i3 == 0) {
                    if (this.orientation == 0) {
                        intrinsicWidth2 = (f18 - (this.src.getIntrinsicWidth() / 2)) - (this.qj * 2);
                        c0042f8 = c0042f8;
                        canvas = canvas;
                        path = path;
                        intrinsicWidth = f16;
                        intrinsicHeight = f17;
                    } else {
                        intrinsicHeight2 = (f19 - (this.src.getIntrinsicHeight() / 2)) - (this.qj * 2);
                        c0042f8 = c0042f8;
                        canvas = canvas;
                        path = path;
                        intrinsicWidth = f16;
                        intrinsicHeight = f17;
                        intrinsicWidth2 = f18;
                    }
                    c0042f8.a(canvas, path, intrinsicWidth, intrinsicHeight, intrinsicWidth2, intrinsicHeight2);
                } else if (i3 == c0042fArr4.length - 1) {
                    if (this.orientation == 0) {
                        intrinsicWidth = (this.src.getIntrinsicWidth() / 2) + f16 + (this.qj * 2);
                        intrinsicHeight = f17;
                    } else {
                        intrinsicHeight = (this.src.getIntrinsicHeight() / 2) + f17 + (this.qj * 2);
                        intrinsicWidth = f16;
                    }
                    intrinsicWidth2 = f18;
                } else {
                    if (this.orientation == 0) {
                        f = f16 - this.qj;
                        f3 = -1.0f;
                        f2 = f17;
                    } else {
                        f = -1.0f;
                        f2 = f17 + this.qj;
                        f3 = f19;
                    }
                    c0042f8.a(canvas, path, i3, f, f2, f18, f3);
                }
                intrinsicHeight2 = f19;
                c0042f8.a(canvas, path, intrinsicWidth, intrinsicHeight, intrinsicWidth2, intrinsicHeight2);
            }
            i3++;
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.width = i;
        this.height = i2;
    }
}
