package com.tw.reverse;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class RadarView extends View {
    private final int HORIZONTAL;
    private final int VERTICAL;
    private int adjust;
    private float anglePadding;
    int arcPadding;
    private float bottomStartAngle;
    private int brush_width;
    private float centerAngle;
    private int[] frontRadarValue;
    private int height;
    int iniArcPadding;
    private int mDefaultRadarSignalMax;
    private RadarArc[] mFrontRadarArcList;
    private RadarArc[] mLSRadarArcList;
    private int[] mLSRadarValue;
    private RadarArc[] mRSRadarArcList;
    private int[] mRSRadarValue;
    private RadarArc[] mRearRadarArcList;
    private int mRedRadar;
    private int orientation;
    private Paint paint;
    int pathWidth;
    private int[] rearRadarValue;
    private float sideAngle;
    private float sideCenterAngle;
    private int sidePathPadding;
    private Drawable src;
    private float topStartAngle;
    private int width;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.src = null;
        this.paint = new Paint(1);
        this.HORIZONTAL = 0;
        this.VERTICAL = 1;
        this.orientation = 0;
        this.brush_width = 0;
        this.adjust = 0;
        this.mDefaultRadarSignalMax = 10;
        this.mRedRadar = 2;
        this.mFrontRadarArcList = new RadarArc[4];
        this.mRearRadarArcList = new RadarArc[4];
        this.mLSRadarArcList = new RadarArc[4];
        this.mRSRadarArcList = new RadarArc[4];
        this.frontRadarValue = new int[4];
        this.rearRadarValue = new int[4];
        this.mLSRadarValue = new int[4];
        this.mRSRadarValue = new int[4];
        this.topStartAngle = -180.0f;
        this.bottomStartAngle = 0.0f;
        this.sideAngle = 40.0f;
        this.sideCenterAngle = 20.0f;
        this.centerAngle = 30.0f;
        this.anglePadding = 1.0f;
        this.iniArcPadding = (int) setdp2px(5);
        this.arcPadding = (int) setdp2px(4);
        this.pathWidth = (int) setdp2px(3);
        this.sidePathPadding = (int) setdp2px(1);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.pathWidth);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.radar);
            Drawable carDrawable = typedArray.getDrawable(3);
            this.orientation = typedArray.getInteger(2, 1);
            this.brush_width = typedArray.getInteger(1, 3);
            this.adjust = typedArray.getInteger(0, 0);
            this.iniArcPadding = (int) setdp2px(this.brush_width + 2);
            this.arcPadding = (int) setdp2px(this.brush_width + 1);
            this.pathWidth = (int) setdp2px(this.brush_width);
            if (this.orientation == 0) {
                this.topStartAngle = -270.0f;
                this.bottomStartAngle = -90.0f;
            } else {
                this.topStartAngle = -180.0f;
                this.bottomStartAngle = 0.0f;
            }
            if (carDrawable != null) {
                this.src = carDrawable;
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    private void initFrontRadar() {
        for (int i = 0; i < this.mFrontRadarArcList.length; i++) {
            RadarArc mRadarArc = new RadarArc();
            mRadarArc.setArc(getArcStartAngle(i, true), getArcSweepAngle(i, true), this.mRedRadar, this.frontRadarValue[i]);
            this.mFrontRadarArcList[i] = mRadarArc;
        }
    }

    private void initRearRadar() {
        for (int i = 0; i < this.mRearRadarArcList.length; i++) {
            RadarArc mRadarArc = new RadarArc();
            mRadarArc.setArc(getArcStartAngle((this.mRearRadarArcList.length - i) - 1, false), getArcSweepAngle((this.mRearRadarArcList.length - i) - 1, false), this.mRedRadar, this.rearRadarValue[i]);
            this.mRearRadarArcList[i] = mRadarArc;
        }
    }

    private void initLSRadar() {
        for (int i = 0; i < this.mLSRadarArcList.length; i++) {
            RadarArc mRadarArc = new RadarArc();
            if (i == 0) {
                mRadarArc.setArc(this.topStartAngle, this.sideAngle - this.anglePadding, this.mRedRadar, this.mLSRadarValue[i]);
            } else if (i == this.mLSRadarArcList.length - 1) {
                float f = this.bottomStartAngle;
                float f2 = this.sideCenterAngle;
                float f3 = this.sideAngle;
                float f4 = f + ((f2 + f3) * 2.0f) + f2;
                float f5 = this.anglePadding;
                mRadarArc.setArc(f4 + f5, f3 - f5, this.mRedRadar, this.mLSRadarValue[i]);
            } else {
                mRadarArc.setradarValue(this.mRedRadar, this.mLSRadarValue[i]);
            }
            this.mLSRadarArcList[i] = mRadarArc;
        }
    }

    private void initRSRadar() {
        for (int i = 0; i < this.mRSRadarArcList.length; i++) {
            RadarArc mRadarArc = new RadarArc();
            if (i == 0) {
                float f = this.topStartAngle;
                float f2 = this.sideCenterAngle;
                float f3 = this.sideAngle;
                float f4 = f + ((f2 + f3) * 2.0f) + f2;
                float f5 = this.anglePadding;
                mRadarArc.setArc(f4 + f5, f3 - f5, this.mRedRadar, this.mRSRadarValue[i]);
            } else if (i == this.mRSRadarArcList.length - 1) {
                mRadarArc.setArc(this.bottomStartAngle, this.sideAngle - this.anglePadding, this.mRedRadar, this.mRSRadarValue[i]);
            } else {
                mRadarArc.setradarValue(this.mRedRadar, this.mRSRadarValue[i]);
            }
            this.mRSRadarArcList[i] = mRadarArc;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.src != null) {
            initFrontRadar();
            initRearRadar();
            initLSRadar();
            initRSRadar();
            Matrix matrix = new Matrix();
            matrix.setTranslate((this.width / 2) - (this.src.getIntrinsicWidth() / 2), (this.height / 2) - (this.src.getIntrinsicHeight() / 2));
            canvas.drawBitmap(drawableToBitmap(this.src), matrix, this.paint);
            float left = (this.width / 2) - (this.src.getIntrinsicWidth() / 2);
            float top = (this.height / 2) - (this.src.getIntrinsicHeight() / 2);
            float right = this.src.getIntrinsicWidth() + left;
            float bottom = this.src.getIntrinsicHeight() + top;
            int i = this.iniArcPadding;
            float left2 = left - i;
            float top2 = top - i;
            float right2 = right + i;
            float right3 = i;
            float bottom2 = bottom + right3;
            Path path = new Path();
            int i2 = 0;
            while (true) {
                RadarArc[] radarArcArr = this.mFrontRadarArcList;
                if (i2 >= radarArcArr.length) {
                    break;
                }
                RadarArc mRadarArc = radarArcArr[i2];
                if (mRadarArc != null) {
                    if (this.orientation == 0) {
                        mRadarArc.drawR(canvas, path, left2, top2, (right2 - (this.src.getIntrinsicWidth() / 2)) - (this.adjust * 2), bottom2);
                    } else {
                        mRadarArc.drawR(canvas, path, left2, top2, right2, (bottom2 - (this.src.getIntrinsicHeight() / 2)) - (this.adjust * 2));
                    }
                }
                i2++;
            }
            int i3 = 0;
            while (true) {
                RadarArc[] radarArcArr2 = this.mRearRadarArcList;
                if (i3 >= radarArcArr2.length) {
                    break;
                }
                RadarArc mRadarArc2 = radarArcArr2[i3];
                if (mRadarArc2 != null) {
                    if (this.orientation == 0) {
                        mRadarArc2.drawR(canvas, path, (this.src.getIntrinsicWidth() / 2) + left2 + (this.adjust * 2), top2, right2, bottom2);
                    } else {
                        mRadarArc2.drawR(canvas, path, left2, (this.src.getIntrinsicHeight() / 2) + top2 + (this.adjust * 2), right2, bottom2);
                    }
                }
                i3++;
            }
            int i4 = 0;
            while (true) {
                RadarArc[] radarArcArr3 = this.mLSRadarArcList;
                if (i4 >= radarArcArr3.length) {
                    break;
                }
                RadarArc mRadarArc3 = radarArcArr3[i4];
                if (mRadarArc3 != null) {
                    if (i4 == 0) {
                        if (this.orientation == 0) {
                            mRadarArc3.drawR(canvas, path, left2, top2, (right2 - (this.src.getIntrinsicWidth() / 2)) - (this.adjust * 2), bottom2);
                        } else {
                            mRadarArc3.drawR(canvas, path, left2, top2, right2, (bottom2 - (this.src.getIntrinsicHeight() / 2)) - (this.adjust * 2));
                        }
                    } else if (i4 == radarArcArr3.length - 1) {
                        if (this.orientation == 0) {
                            mRadarArc3.drawR(canvas, path, (this.src.getIntrinsicWidth() / 2) + left2 + (this.adjust * 2), top2, right2, bottom2);
                        } else {
                            mRadarArc3.drawR(canvas, path, left2, (this.src.getIntrinsicHeight() / 2) + top2 + (this.adjust * 2), right2, bottom2);
                        }
                    } else if (this.orientation == 0) {
                        mRadarArc3.drawPath(canvas, path, i4, left2 - this.adjust, -1.0f, right2, bottom2);
                    } else {
                        mRadarArc3.drawPath(canvas, path, i4, left2, top2 - this.adjust, -1.0f, bottom2);
                    }
                }
                i4++;
            }
            int i5 = 0;
            while (true) {
                RadarArc[] radarArcArr4 = this.mRSRadarArcList;
                if (i5 < radarArcArr4.length) {
                    RadarArc mRadarArc4 = radarArcArr4[i5];
                    if (mRadarArc4 != null) {
                        if (i5 == 0) {
                            if (this.orientation == 0) {
                                mRadarArc4.drawR(canvas, path, left2, top2, (right2 - (this.src.getIntrinsicWidth() / 2)) - (this.adjust * 2), bottom2);
                            } else {
                                mRadarArc4.drawR(canvas, path, left2, top2, right2, (bottom2 - (this.src.getIntrinsicHeight() / 2)) - (this.adjust * 2));
                            }
                        } else if (i5 == radarArcArr4.length - 1) {
                            if (this.orientation == 0) {
                                mRadarArc4.drawR(canvas, path, (this.src.getIntrinsicWidth() / 2) + left2 + (this.adjust * 2), top2, right2, bottom2);
                            } else {
                                mRadarArc4.drawR(canvas, path, left2, (this.src.getIntrinsicHeight() / 2) + top2 + (this.adjust * 2), right2, bottom2);
                            }
                        } else if (this.orientation == 0) {
                            mRadarArc4.drawPath(canvas, path, i5, left2 - this.adjust, top2, right2, -1.0f);
                        } else {
                            mRadarArc4.drawPath(canvas, path, i5, -1.0f, top2 + this.adjust, right2, bottom2);
                        }
                    }
                    i5++;
                } else {
                    return;
                }
            }
        }
    }

    private float getArcStartAngle(int index, boolean top) {
        float startAngle = top ? this.topStartAngle : this.bottomStartAngle;
        if (index == 0) {
            return this.sideAngle + startAngle;
        }
        if (index == 1) {
            return this.sideAngle + startAngle + this.sideCenterAngle + (this.anglePadding / 2.0f);
        }
        if (index == 2) {
            return this.sideAngle + startAngle + this.sideCenterAngle + this.centerAngle + (this.anglePadding / 2.0f);
        }
        if (index == 3) {
            return this.sideAngle + startAngle + this.sideCenterAngle + (this.centerAngle * 2.0f);
        }
        return -1.0f;
    }

    private float getArcSweepAngle(int index, boolean top) {
        if (index == 0) {
            return this.sideCenterAngle;
        }
        if (index == 1) {
            return Math.abs(this.centerAngle - this.anglePadding);
        }
        if (index == 2) {
            return Math.abs(this.centerAngle - this.anglePadding);
        }
        if (index == 3) {
            return this.sideCenterAngle;
        }
        return -1.0f;
    }

    public void setRearRadar(int leftRadar, int centerLeftRadar, int centerRightRadar, int rightRadar) {
        int[] iArr = this.rearRadarValue;
        int i = this.mDefaultRadarSignalMax;
        if (leftRadar <= i) {
            i = leftRadar;
        }
        iArr[0] = i;
        int[] iArr2 = this.rearRadarValue;
        int i2 = this.mDefaultRadarSignalMax;
        if (centerLeftRadar <= i2) {
            i2 = centerLeftRadar;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.rearRadarValue;
        int i3 = this.mDefaultRadarSignalMax;
        if (centerRightRadar <= i3) {
            i3 = centerRightRadar;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.rearRadarValue;
        int i4 = this.mDefaultRadarSignalMax;
        if (rightRadar <= i4) {
            i4 = rightRadar;
        }
        iArr4[3] = i4;
        invalidate();
    }

    public void setFrontRadar(int leftRadar, int centerLeftRadar, int centerRightRadar, int rightRadar) {
        int[] iArr = this.frontRadarValue;
        int i = this.mDefaultRadarSignalMax;
        if (leftRadar <= i) {
            i = leftRadar;
        }
        iArr[0] = i;
        int[] iArr2 = this.frontRadarValue;
        int i2 = this.mDefaultRadarSignalMax;
        if (centerLeftRadar <= i2) {
            i2 = centerLeftRadar;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.frontRadarValue;
        int i3 = this.mDefaultRadarSignalMax;
        if (centerRightRadar <= i3) {
            i3 = centerRightRadar;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.frontRadarValue;
        int i4 = this.mDefaultRadarSignalMax;
        if (rightRadar <= i4) {
            i4 = rightRadar;
        }
        iArr4[3] = i4;
        invalidate();
    }

    public void setLSRadar(int leftRadar, int centerLeftRadar, int centerRightRadar, int rightRadar) {
        int[] iArr = this.mLSRadarValue;
        int i = this.mDefaultRadarSignalMax;
        if (leftRadar <= i) {
            i = leftRadar;
        }
        iArr[0] = i;
        int[] iArr2 = this.mLSRadarValue;
        int i2 = this.mDefaultRadarSignalMax;
        if (centerLeftRadar <= i2) {
            i2 = centerLeftRadar;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.mLSRadarValue;
        int i3 = this.mDefaultRadarSignalMax;
        if (centerRightRadar <= i3) {
            i3 = centerRightRadar;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.mLSRadarValue;
        int i4 = this.mDefaultRadarSignalMax;
        if (rightRadar <= i4) {
            i4 = rightRadar;
        }
        iArr4[3] = i4;
        invalidate();
    }

    public void setRSRadar(int leftRadar, int centerLeftRadar, int centerRightRadar, int rightRadar) {
        int[] iArr = this.mRSRadarValue;
        int i = this.mDefaultRadarSignalMax;
        if (leftRadar <= i) {
            i = leftRadar;
        }
        iArr[0] = i;
        int[] iArr2 = this.mRSRadarValue;
        int i2 = this.mDefaultRadarSignalMax;
        if (centerLeftRadar <= i2) {
            i2 = centerLeftRadar;
        }
        iArr2[1] = i2;
        int[] iArr3 = this.mRSRadarValue;
        int i3 = this.mDefaultRadarSignalMax;
        if (centerRightRadar <= i3) {
            i3 = centerRightRadar;
        }
        iArr3[2] = i3;
        int[] iArr4 = this.mRSRadarValue;
        int i4 = this.mDefaultRadarSignalMax;
        if (rightRadar <= i4) {
            i4 = rightRadar;
        }
        iArr4[3] = i4;
        invalidate();
    }

    private class RadarArc {
        int radarValue;
        int redRadar;
        float startAngle;
        float sweepAngle;

        private RadarArc() {
        }

        public void setArc(float startAngle, float sweepAngle, int redRadar, int radarValue) {
            this.startAngle = startAngle;
            this.sweepAngle = sweepAngle;
            this.redRadar = redRadar;
            this.radarValue = radarValue;
        }

        public void setradarValue(int redRadar, int radarValue) {
            this.redRadar = redRadar;
            this.radarValue = radarValue;
        }

        public void drawR(Canvas canvas, Path path, float left, float top, float right, float bottom) {
            for (int i = 1; i < this.radarValue + 1; i++) {
                if (i > 2) {
                    RadarView.this.paint.setColor(-16711936);
                } else {
                    RadarView.this.paint.setColor(-65536);
                }
                RectF mRectF = new RectF(left - (RadarView.this.arcPadding * i), top - (RadarView.this.arcPadding * i), (RadarView.this.arcPadding * i) + right, (RadarView.this.arcPadding * i) + bottom);
                path.reset();
                path.arcTo(mRectF, this.startAngle, this.sweepAngle);
                canvas.drawPath(path, RadarView.this.paint);
            }
        }

        public void drawPath(Canvas canvas, Path path, int pathIndex, float left, float top, float right, float bottom) {
            for (int i = 1; i < this.radarValue + 1; i++) {
                if (i > 2) {
                    RadarView.this.paint.setColor(-16711936);
                } else {
                    RadarView.this.paint.setColor(-65536);
                }
                drawPath(canvas, path, pathIndex, left, top, right, bottom, i);
            }
        }

        private void drawPath(Canvas canvas, Path path, int pathIndex, float left, float top, float right, float bottom, int i) {
            if (RadarView.this.orientation == 0) {
                float x = top > 0.0f ? top - (RadarView.this.arcPadding * i) : bottom + (RadarView.this.arcPadding * i);
                float pathMargin = (RadarView.this.src.getIntrinsicWidth() / 4) + RadarView.this.adjust;
                float beginLine = left + (RadarView.this.src.getIntrinsicWidth() / 4) + RadarView.this.iniArcPadding;
                RadarView radarView = RadarView.this;
                float padding = radarView.setdp2px(radarView.sidePathPadding);
                float pathMargin2 = ((pathMargin * 2.0f) - (3.0f * padding)) / 2.0f;
                path.reset();
                float beginLine2 = beginLine + padding;
                float pathLintY = beginLine2 + pathMargin2;
                if (pathIndex == 1) {
                    path.moveTo(beginLine2, x);
                    path.lineTo(pathLintY, x);
                    canvas.drawPath(path, RadarView.this.paint);
                    return;
                } else {
                    path.reset();
                    float beginLine3 = pathLintY + padding;
                    path.moveTo(beginLine3, x);
                    path.lineTo(beginLine3 + pathMargin2, x);
                    canvas.drawPath(path, RadarView.this.paint);
                    return;
                }
            }
            float x2 = left > 0.0f ? left - (RadarView.this.arcPadding * i) : right + (RadarView.this.arcPadding * i);
            float pathMargin3 = (RadarView.this.src.getIntrinsicHeight() / 4) + RadarView.this.adjust;
            float beginLine4 = top + (RadarView.this.src.getIntrinsicHeight() / 4) + RadarView.this.iniArcPadding;
            RadarView radarView2 = RadarView.this;
            float padding2 = radarView2.setdp2px(radarView2.sidePathPadding);
            float pathMargin4 = ((pathMargin3 * 2.0f) - (3.0f * padding2)) / 2.0f;
            path.reset();
            float beginLine5 = beginLine4 + padding2;
            float pathLintY2 = beginLine5 + pathMargin4;
            if (pathIndex == 1) {
                path.moveTo(x2, beginLine5);
                path.lineTo(x2, pathLintY2);
                canvas.drawPath(path, RadarView.this.paint);
            } else {
                path.reset();
                float beginLine6 = pathLintY2 + padding2;
                path.moveTo(x2, beginLine6);
                path.lineTo(x2, beginLine6 + pathMargin4);
                canvas.drawPath(path, RadarView.this.paint);
            }
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float setdp2px(int value) {
        return TypedValue.applyDimension(1, value, Resources.getSystem().getDisplayMetrics());
    }
}
