package com.tw.reverse;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ReverseLineRuler extends View {
    private static final String POINT1 = "Point1";
    private static final String POINT2 = "Point2";
    private static final String POINT3 = "Point3";
    private static final String POINT4 = "Point4";
    private static final String TAG = "ReversePathView";
    List<Point> allRectPointList;
    private Context context;
    private boolean defaultLineRuler;
    private float density;
    int echelonRecBottomWith;
    int echelonRecHeight;
    int echelonRecTopWith;
    private Paint lineRulerPaint;
    private Path lineRulerPath;
    private LineRulerState lineRulerState;
    int paddingBottom;
    private int paintColor;
    Point pl1;
    Point pl2;
    Point pl3;
    Point pointEnd;
    Point pointLeftBottom;
    Point pointLeftTop;
    Point pointMiddle;
    Point pointRightBottom;
    Point pointRightTop;
    Point pointStart;
    Point pr1;
    Point pr2;
    Point pr3;
    List<Point> rectPointList;
    List<Point> sidePointList;
    private SharedPreferences sp;
    private int strokeWidthIn;
    private int strokeWidthOut;
    private int viewHeight;
    private int viewWith;
    private static int DEFAULT_VIEW_WIDTH = 800;
    private static int DEFAULT_VIEW_HEIGHT = 480;
    private static int DEFAULT_ECHELON_REC_TOP_WIDTH = 203;
    private static int DEFAULT_ECHELON_REC_BOTTOM_WIDTH = 736;
    private static int DEFAULT_ECHELON_REC_HEIGHT = 220;
    private static int DEFAULT_ECHELON_REC_PADDING_BOTTOM = 56;
    private static int DEFAULT_STROKE_WIDTH_OUT = 5;
    private static int DEFAULT_STROKE_WIDTH_IN = 3;

    public enum LineRulerState {
        Adjust,
        Normal
    }

    public enum TouchAreaTypeEnum {
        LeftBottomPoint,
        LeftTopPoint,
        RightTopPoint,
        RightBottomPoint,
        TopLine,
        Illegal
    }

    public ReverseLineRuler(Context context) {
        super(context);
        this.rectPointList = new ArrayList();
        this.sidePointList = new ArrayList();
        this.allRectPointList = new ArrayList();
        this.paintColor = Color.argb(255, 255, 102, 0);
        this.echelonRecTopWith = DEFAULT_ECHELON_REC_TOP_WIDTH;
        this.echelonRecBottomWith = DEFAULT_ECHELON_REC_BOTTOM_WIDTH;
        this.echelonRecHeight = DEFAULT_ECHELON_REC_HEIGHT;
        this.paddingBottom = DEFAULT_ECHELON_REC_PADDING_BOTTOM;
        this.strokeWidthOut = DEFAULT_STROKE_WIDTH_OUT;
        this.strokeWidthIn = DEFAULT_STROKE_WIDTH_IN;
        this.density = 1.0f;
        this.pointLeftBottom = new Point();
        this.pointLeftTop = new Point();
        this.pointRightTop = new Point();
        this.pointRightBottom = new Point();
        this.pl1 = new Point();
        this.pl2 = new Point();
        this.pl3 = new Point();
        this.pr1 = new Point();
        this.pr2 = new Point();
        this.pr3 = new Point();
        this.context = context;
        init();
    }

    public ReverseLineRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.rectPointList = new ArrayList();
        this.sidePointList = new ArrayList();
        this.allRectPointList = new ArrayList();
        this.paintColor = Color.argb(255, 255, 102, 0);
        this.echelonRecTopWith = DEFAULT_ECHELON_REC_TOP_WIDTH;
        this.echelonRecBottomWith = DEFAULT_ECHELON_REC_BOTTOM_WIDTH;
        this.echelonRecHeight = DEFAULT_ECHELON_REC_HEIGHT;
        this.paddingBottom = DEFAULT_ECHELON_REC_PADDING_BOTTOM;
        this.strokeWidthOut = DEFAULT_STROKE_WIDTH_OUT;
        this.strokeWidthIn = DEFAULT_STROKE_WIDTH_IN;
        this.density = 1.0f;
        this.pointLeftBottom = new Point();
        this.pointLeftTop = new Point();
        this.pointRightTop = new Point();
        this.pointRightBottom = new Point();
        this.pl1 = new Point();
        this.pl2 = new Point();
        this.pl3 = new Point();
        this.pr1 = new Point();
        this.pr2 = new Point();
        this.pr3 = new Point();
        this.context = context;
        init();
    }

    public ReverseLineRuler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rectPointList = new ArrayList();
        this.sidePointList = new ArrayList();
        this.allRectPointList = new ArrayList();
        this.paintColor = Color.argb(255, 255, 102, 0);
        this.echelonRecTopWith = DEFAULT_ECHELON_REC_TOP_WIDTH;
        this.echelonRecBottomWith = DEFAULT_ECHELON_REC_BOTTOM_WIDTH;
        this.echelonRecHeight = DEFAULT_ECHELON_REC_HEIGHT;
        this.paddingBottom = DEFAULT_ECHELON_REC_PADDING_BOTTOM;
        this.strokeWidthOut = DEFAULT_STROKE_WIDTH_OUT;
        this.strokeWidthIn = DEFAULT_STROKE_WIDTH_IN;
        this.density = 1.0f;
        this.pointLeftBottom = new Point();
        this.pointLeftTop = new Point();
        this.pointRightTop = new Point();
        this.pointRightBottom = new Point();
        this.pl1 = new Point();
        this.pl2 = new Point();
        this.pl3 = new Point();
        this.pr1 = new Point();
        this.pr2 = new Point();
        this.pr3 = new Point();
        this.context = context;
        init();
    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            this.allRectPointList.add(new Point(0, 0));
        }
        this.lineRulerPath = new Path();
        this.lineRulerPaint = new Paint();
        this.lineRulerPaint.setAntiAlias(true);
        this.lineRulerPaint.setStyle(Paint.Style.STROKE);
        this.lineRulerPaint.setStrokeWidth(this.strokeWidthOut);
        this.lineRulerPaint.setColor(this.paintColor);
        this.lineRulerPaint.setStrokeCap(Paint.Cap.ROUND);
        this.lineRulerPaint.setStyle(Paint.Style.STROKE);
        this.lineRulerPaint.setColor(this.paintColor);
        this.pointStart = new Point();
        this.pointMiddle = new Point();
        this.pointEnd = new Point();
        this.sp = this.context.getSharedPreferences(TAG, 0);
    }

    private void initData() {
        DisplayMetrics dm = this.context.getResources().getDisplayMetrics();
        this.density = dm.density;
        int screenWith = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int i = this.viewHeight;
        int i2 = DEFAULT_VIEW_HEIGHT;
        this.strokeWidthOut = ((int) (i / i2)) * DEFAULT_STROKE_WIDTH_OUT;
        this.strokeWidthIn = ((int) (i / i2)) * DEFAULT_STROKE_WIDTH_IN;
        Log.d(TAG, "initData: density:" + this.density + " screenWith:" + screenWith + " screenHeight:" + screenHeight);
        float f = (float) DEFAULT_ECHELON_REC_TOP_WIDTH;
        int i3 = this.viewWith;
        int i4 = DEFAULT_VIEW_WIDTH;
        this.echelonRecTopWith = (int) ((f * ((float) i3)) / ((float) i4));
        this.echelonRecBottomWith = (int) ((((float) DEFAULT_ECHELON_REC_BOTTOM_WIDTH) * ((float) i3)) / ((float) i4));
        float f2 = (float) DEFAULT_ECHELON_REC_HEIGHT;
        int i5 = this.viewHeight;
        int i6 = DEFAULT_VIEW_HEIGHT;
        this.echelonRecHeight = (int) ((f2 * i5) / i6);
        this.paddingBottom = (int) ((DEFAULT_ECHELON_REC_PADDING_BOTTOM * i5) / i6);
        this.rectPointList.clear();
        this.pointLeftBottom = getDataFromSP(POINT1);
        if (this.pointLeftBottom == null || this.defaultLineRuler) {
            this.pointLeftBottom = new Point();
            Point point = this.pointLeftBottom;
            point.x = (this.viewWith / 2) - (this.echelonRecBottomWith / 2);
            point.y = this.viewHeight - this.paddingBottom;
        }
        this.rectPointList.add(this.pointLeftBottom);
        this.allRectPointList.set(0, this.pointLeftBottom);
        int tempW = (this.echelonRecBottomWith - this.echelonRecTopWith) / 2;
        this.pointLeftTop = getDataFromSP(POINT2);
        if (this.pointLeftTop == null || this.defaultLineRuler) {
            this.pointLeftTop = new Point();
            this.pointLeftTop.x = this.pointLeftBottom.x + tempW;
            this.pointLeftTop.y = this.pointLeftBottom.y - this.echelonRecHeight;
        }
        this.rectPointList.add(this.pointLeftTop);
        this.allRectPointList.set(4, this.pointLeftTop);
        this.pointRightTop = getDataFromSP(POINT3);
        if (this.pointRightTop == null || this.defaultLineRuler) {
            this.pointRightTop = new Point();
            this.pointRightTop.x = this.pointLeftTop.x + this.echelonRecTopWith;
            this.pointRightTop.y = this.pointLeftTop.y;
        }
        this.rectPointList.add(this.pointRightTop);
        this.allRectPointList.set(5, this.pointRightTop);
        this.pointRightBottom = getDataFromSP(POINT4);
        if (this.pointRightBottom == null || this.defaultLineRuler) {
            this.pointRightBottom = new Point();
            Point point2 = this.pointRightBottom;
            point2.x = (this.viewWith / 2) + (this.echelonRecBottomWith / 2);
            point2.y = this.viewHeight - this.paddingBottom;
        }
        this.rectPointList.add(this.pointRightBottom);
        this.allRectPointList.set(9, this.pointRightBottom);
        resetSidePoint(this.pointLeftBottom, this.pointLeftTop, this.pointRightTop, this.pointRightBottom);
        this.defaultLineRuler = false;
    }

    private void resetSidePoint(Point point1, Point point2, Point point3, Point point4) {
        this.sidePointList.clear();
        int tempWL = point2.x - point1.x;
        int echelonRecTopHeightL = point1.y - point2.y;
        int tempWR = point4.x - point3.x;
        int echelonRecTopHeightR = point4.y - point3.y;
        this.pl1.x = (int) (((double) point1.x) + (((double) tempWL) * 0.41d));
        this.pl1.y = (int) (((double) point1.y) - (((double) echelonRecTopHeightL) * 0.4d));
        this.sidePointList.add(this.pl1);
        this.allRectPointList.set(1, this.pl1);
        this.pl2.x = (int) (((double) point1.x) + (((double) tempWL) * 0.61d));
        this.pl2.y = (int) (((double) point1.y) - (((double) echelonRecTopHeightL) * 0.6d));
        this.sidePointList.add(this.pl2);
        this.allRectPointList.set(2, this.pl2);
        this.pl3.x = (int) (((double) point1.x) + (((double) tempWL) * 0.81d));
        this.pl3.y = (int) (((double) point1.y) - (((double) echelonRecTopHeightL) * 0.8d));
        this.sidePointList.add(this.pl3);
        this.allRectPointList.set(3, this.pl3);
        this.pr1.x = (int) (((double) point4.x) - (((double) tempWR) * 0.41d));
        this.pr1.y = (int) (((double) point4.y) - (((double) echelonRecTopHeightR) * 0.4d));
        this.sidePointList.add(this.pr1);
        this.allRectPointList.set(8, this.pr1);
        this.pr2.x = (int) (((double) point4.x) - (((double) tempWR) * 0.61d));
        this.pr2.y = (int) (((double) point4.y) - (((double) echelonRecTopHeightR) * 0.6d));
        this.sidePointList.add(this.pr2);
        this.allRectPointList.set(7, this.pr2);
        this.pr3.x = (int) (((double) point4.x) - (((double) tempWR) * 0.81d));
        this.pr3.y = (int) (((double) point4.y) - (((double) echelonRecTopHeightR) * 0.8d));
        this.sidePointList.add(this.pr3);
        this.allRectPointList.set(6, this.pr3);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWith = getMeasuredWidth();
        this.viewHeight = getMeasuredHeight();
        initData();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLineRulerPath(canvas);
        if (this.lineRulerState == LineRulerState.Adjust) {
            drawIndicate(canvas);
        }
    }

    private void drawIndicate(Canvas canvas) {
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.line_point);
        canvas.drawBitmap(mBitmap, this.rectPointList.get(0).x - (mBitmap.getWidth() / 2), this.rectPointList.get(0).y - (mBitmap.getHeight() / 2), new Paint());
        canvas.drawBitmap(mBitmap, this.rectPointList.get(1).x - (mBitmap.getWidth() / 2), this.rectPointList.get(1).y - (mBitmap.getHeight() / 2), new Paint());
        canvas.drawBitmap(mBitmap, this.rectPointList.get(2).x - (mBitmap.getWidth() / 2), this.rectPointList.get(2).y - (mBitmap.getHeight() / 2), new Paint());
        canvas.drawBitmap(mBitmap, this.rectPointList.get(3).x - (mBitmap.getWidth() / 2), this.rectPointList.get(3).y - (mBitmap.getHeight() / 2), new Paint());
    }

    private void drawLineRulerPath(Canvas canvas) {
        int color;
        if (this.rectPointList.size() == 0) {
            return;
        }
        this.lineRulerPath.reset();
        this.lineRulerPaint.setColor(this.paintColor);
        this.lineRulerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.lineRulerPaint.setStrokeWidth(this.strokeWidthIn);
        for (int i = 0; i < this.sidePointList.size(); i++) {
            this.lineRulerPath.moveTo(this.sidePointList.get(i).x, this.sidePointList.get(i).y);
            if (i < 3) {
                this.lineRulerPath.lineTo(this.sidePointList.get(i + 3).x, this.sidePointList.get(i + 3).y);
            }
        }
        canvas.drawPath(this.lineRulerPath, this.lineRulerPaint);
        for (int i2 = 0; i2 < this.allRectPointList.size() - 1; i2++) {
            if (i2 == 0 || i2 == 8) {
                color = Color.argb(255, 255, 0, 0);
            } else if (i2 == 1 || i2 == 2 || i2 == 6 || i2 == 7) {
                color = Color.argb(255, 255, 255, 0);
            } else if (i2 == 3 || i2 == 4 || i2 == 5) {
                color = Color.argb(255, 0, 255, 0);
            } else {
                color = -16711936;
            }
            drawLinePart(canvas, this.lineRulerPath, this.lineRulerPaint, color, this.allRectPointList.get(i2), this.allRectPointList.get(i2 + 1));
        }
    }

    private void drawLinePart(Canvas canvas, Path path, Paint paint, int lineColor, Point startPoint, Point endPoint) {
        path.reset();
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.strokeWidthOut);
        path.moveTo(startPoint.x, startPoint.y);
        path.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(path, paint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        int action = event.getAction();
        if (action == 0) {
            updateOnTouch(event);
        } else if (action == 1) {
            updateOnTouch(event);
            getParent().requestDisallowInterceptTouchEvent(false);
        } else if (action == 2) {
            updateOnTouch(event);
        } else if (action == 3) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        if (this.lineRulerState == LineRulerState.Adjust) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void updateOnTouch(MotionEvent event) {
        if (this.lineRulerState != LineRulerState.Adjust) {
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[validateTouch(event.getX(), event.getY()).ordinal()]) {
            case R.styleable.radar_brush_width /* 1 */:
                Point newPoint = new Point((int) event.getX(), (int) event.getY());
                this.rectPointList.set(0, newPoint);
                this.allRectPointList.set(0, newPoint);
                break;
            case R.styleable.radar_orientation /* 2 */:
                Point newPoint2 = new Point((int) event.getX(), (int) event.getY());
                this.rectPointList.set(1, newPoint2);
                this.allRectPointList.set(4, newPoint2);
                break;
            case R.styleable.radar_src /* 3 */:
                Point newPointRight = new Point((int) event.getX(), (int) event.getY());
                this.rectPointList.set(2, newPointRight);
                this.allRectPointList.set(5, newPointRight);
                break;
            case 4:
                Point newPoint3 = new Point((int) event.getX(), (int) event.getY());
                this.rectPointList.set(3, newPoint3);
                this.allRectPointList.set(9, newPoint3);
                break;
            case 5:
                Point newPointLeft = new Point(this.rectPointList.get(1).x, (int) event.getY());
                Point newPointRight2 = new Point(this.rectPointList.get(2).x, (int) event.getY());
                this.rectPointList.set(1, newPointLeft);
                this.rectPointList.set(2, newPointRight2);
                this.allRectPointList.set(4, newPointLeft);
                this.allRectPointList.set(5, newPointRight2);
                break;
            case 6:
                return;
        }
        resetSidePoint(this.rectPointList.get(0), this.rectPointList.get(1), this.rectPointList.get(2), this.rectPointList.get(3));
        invalidate();
    }

    /* JADX INFO: renamed from: com.tw.reverse.ReverseLineRuler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum = new int[TouchAreaTypeEnum.values().length];

        static {
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.LeftBottomPoint.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.LeftTopPoint.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.RightTopPoint.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.RightBottomPoint.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.TopLine.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$tw$reverse$ReverseLineRuler$TouchAreaTypeEnum[TouchAreaTypeEnum.Illegal.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    private TouchAreaTypeEnum validateTouch(float xPos, float yPos) {
        Point pointLeftBottom = this.rectPointList.get(0);
        Point pointLeftTop = this.rectPointList.get(1);
        Point pointRightTop = this.rectPointList.get(2);
        Point pointRightBottom = this.rectPointList.get(3);
        float plbMinX = pointLeftBottom.x - dp2px(this.context, 32.0f);
        float plbMaxX = pointLeftBottom.x + dp2px(this.context, 32.0f);
        float plbMinY = pointLeftBottom.y - dp2px(this.context, 32.0f);
        float plbMaxY = pointLeftBottom.y + dp2px(this.context, 32.0f);
        float pltMinX = pointLeftTop.x - dp2px(this.context, 32.0f);
        float pltMaxX = pointLeftTop.x + dp2px(this.context, 32.0f);
        float pltMinY = pointLeftTop.y - dp2px(this.context, 32.0f);
        float pltMaxY = pointLeftTop.y + dp2px(this.context, 32.0f);
        float prtMinX = pointRightTop.x - dp2px(this.context, 32.0f);
        float prtMaxX = pointRightTop.x + dp2px(this.context, 32.0f);
        float prtMinY = pointRightTop.y - dp2px(this.context, 32.0f);
        float prtMaxY = pointRightTop.y + dp2px(this.context, 32.0f);
        float prbMinX = pointRightBottom.x - dp2px(this.context, 32.0f);
        float prbMaxX = pointRightBottom.x + dp2px(this.context, 32.0f);
        float prbMinY = pointRightBottom.y - dp2px(this.context, 32.0f);
        float prbMaxY = pointRightBottom.y + dp2px(this.context, 32.0f);
        if (xPos > plbMinX && xPos < plbMaxX && yPos > plbMinY && yPos < plbMaxY) {
            return TouchAreaTypeEnum.LeftBottomPoint;
        }
        if (xPos > pltMinX && xPos < pltMaxX && yPos > pltMinY && yPos < pltMaxY) {
            return TouchAreaTypeEnum.LeftTopPoint;
        }
        if (xPos > prtMinX && xPos < prtMaxX && yPos > prtMinY && yPos < prtMaxY) {
            return TouchAreaTypeEnum.RightTopPoint;
        }
        if (xPos > prbMinX && xPos < prbMaxX && yPos > prbMinY && yPos < prbMaxY) {
            return TouchAreaTypeEnum.RightBottomPoint;
        }
        if (xPos > pltMinX && xPos < prtMinX && xPos > pltMinY && yPos < pltMaxY) {
            return TouchAreaTypeEnum.TopLine;
        }
        return TouchAreaTypeEnum.Illegal;
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxVal / scale;
    }

    private void saveData2SP() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putString(POINT1, this.rectPointList.get(0).toString());
        editor.putString(POINT2, this.rectPointList.get(1).toString());
        editor.putString(POINT3, this.rectPointList.get(2).toString());
        editor.putString(POINT4, this.rectPointList.get(3).toString());
        editor.apply();
    }

    public Point getDataFromSP(String key) {
        try {
            String string = this.sp.getString(key, null);
            if (string == null) {
                return null;
            }
            String dataTemp = string.substring(6, string.length() - 1);
            String[] data = dataTemp.split(",");
            return new Point(Integer.valueOf(data[0].trim()).intValue(), Integer.valueOf(data[1].trim()).intValue());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetLineRuler() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.clear();
        editor.commit();
        this.defaultLineRuler = true;
        initData();
        invalidate();
    }

    public void adjustLineRuler() {
        this.lineRulerState = LineRulerState.Adjust;
        invalidate();
    }

    public void saveLineRuler() {
        saveData2SP();
        this.lineRulerState = LineRulerState.Normal;
        invalidate();
    }
}
