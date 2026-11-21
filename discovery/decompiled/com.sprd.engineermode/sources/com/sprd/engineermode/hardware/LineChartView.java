package com.sprd.engineermode.hardware;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class LineChartView extends View {
    public ArrayList<String> Data;
    public String Title;
    public String TitleVol;
    public ArrayList<String> XLabel;
    public int XLength;
    public int XPoint;
    public int XScale;
    public String[] YLabel;
    public int YLength;
    public int YPoint;
    public int YScale;
    public String mShowChart;

    public LineChartView(Context context) {
        super(context);
        this.XPoint = 40;
        this.YPoint = 560;
        this.XScale = 115;
        this.YScale = 90;
        this.XLength = 1080;
        this.YLength = 530;
        this.XLabel = new ArrayList<>();
        this.Data = new ArrayList<>();
    }

    public LineChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.XPoint = 40;
        this.YPoint = 560;
        this.XScale = 115;
        this.YScale = 90;
        this.XLength = 1080;
        this.YLength = 530;
        this.XLabel = new ArrayList<>();
        this.Data = new ArrayList<>();
    }

    public void SetInfo(ArrayList<String> XLabels, String[] YLabels, ArrayList<String> AllData, String strTitle, String strTitleVol, int XWith, int XHeight, String showChart) {
        this.XLabel = XLabels;
        this.YLabel = YLabels;
        this.Data = AllData;
        this.Title = strTitle;
        this.TitleVol = strTitleVol;
        this.XLength = XWith - 100;
        this.YLength = XHeight - 130;
        this.mShowChart = showChart;
        if (CoulometerPowerTestActivity.testResults1.size() != 0) {
            this.XScale = this.XLength / CoulometerPowerTestActivity.testResults1.size();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int i;
        int i2;
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setColor(SupportMenu.CATEGORY_MASK);
        paint.setTextSize(20.0f);
        String str = this.mShowChart;
        if (str != null && str.equals("yes")) {
            int i3 = this.XPoint;
            int i4 = this.YPoint;
            canvas.drawLine(i3, i4 - this.YLength, i3, i4, paint);
            int i5 = 0;
            while (true) {
                int i6 = this.YScale;
                int i7 = i5 * i6;
                i = this.YLength;
                if (i7 >= i) {
                    break;
                }
                int i8 = this.XPoint;
                int i9 = this.YPoint;
                canvas.drawLine(i8, i9 - (i5 * i6), i8 + 5, i9 - (i6 * i5), paint);
                try {
                    canvas.drawText(this.YLabel[i5], this.XPoint - 22, (this.YPoint - (this.YScale * i5)) + 5, paint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i5++;
            }
            int i10 = this.XPoint;
            int i11 = this.YPoint;
            canvas.drawLine(i10, i11 - i, i10 - 3, (i11 - i) + 6, paint);
            int i12 = this.XPoint;
            int i13 = this.YPoint;
            int i14 = this.YLength;
            canvas.drawLine(i12, i13 - i14, i12 + 3, (i13 - i14) + 6, paint);
            int i15 = this.XPoint;
            int i16 = this.YPoint;
            canvas.drawLine(i15, i16, i15 + this.XLength, i16, paint);
            int i17 = 0;
            while (true) {
                int i18 = this.XScale;
                int i19 = i17 * i18;
                i2 = this.XLength;
                if (i19 >= i2) {
                    break;
                }
                int i20 = this.XPoint;
                int i21 = this.YPoint;
                canvas.drawLine((i17 * i18) + i20, i21, i20 + (i18 * i17), i21 - 5, paint);
                try {
                    canvas.drawText(this.XLabel.get(i17), (this.XPoint + (this.XScale * i17)) - 10, this.YPoint + 20, paint);
                    if (i17 > 0 && YCoord(this.Data.get(i17 - 1)) != -999 && YCoord(this.Data.get(i17)) != -999) {
                        canvas.drawLine(this.XPoint + ((i17 - 1) * this.XScale), YCoord(this.Data.get(i17 - 1)), this.XPoint + (this.XScale * i17), YCoord(this.Data.get(i17)), paint);
                    }
                    canvas.drawCircle(this.XPoint + (this.XScale * i17), YCoord(this.Data.get(i17)), 2.0f, paint);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                i17++;
            }
            int i22 = this.XPoint;
            int i23 = this.YPoint;
            canvas.drawLine(i22 + i2, i23, (i22 + i2) - 6, i23 - 3, paint);
            int i24 = this.XPoint;
            int i25 = this.XLength;
            int i26 = this.YPoint;
            canvas.drawLine(i24 + i25, i26, (i24 + i25) - 6, i26 + 3, paint);
        }
        paint.setTextSize(36.0f);
        paint1.setTextSize(36.0f);
        String str2 = this.Title;
        if (str2 != null) {
            canvas.drawText(str2, 70.0f, 50.0f, paint1);
        }
        String str3 = this.TitleVol;
        if (str3 != null) {
            canvas.drawText(str3, 70.0f, 100.0f, paint1);
        }
        invalidate();
    }

    private int YCoord(String y0) {
        try {
            int y = Integer.parseInt(y0);
            try {
                if (!this.YLabel[1].equals("0")) {
                    return this.YPoint - ((this.YScale * y) / Integer.parseInt(this.YLabel[1]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return y;
        } catch (Exception e2) {
            return -999;
        }
    }
}
