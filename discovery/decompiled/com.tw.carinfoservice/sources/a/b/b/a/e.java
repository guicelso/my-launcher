package a.b.b.a;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.animation.Interpolator;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public class e implements Interpolator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float[] f17a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float[] f18b;

    public e(Context context, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray typedArrayY = a.b.a.a.y(context.getResources(), context.getTheme(), attributeSet, a.l);
        if (a.b.a.a.s(xmlPullParser, "pathData")) {
            String strP = a.b.a.a.p(typedArrayY, xmlPullParser, "pathData", 4);
            Path pathK = a.b.a.a.k(strP);
            if (pathK == null) {
                throw new InflateException("The path is null, which is created from " + strP);
            }
            a(pathK);
        } else {
            if (!a.b.a.a.s(xmlPullParser, "controlX1")) {
                throw new InflateException("pathInterpolator requires the controlX1 attribute");
            }
            if (!a.b.a.a.s(xmlPullParser, "controlY1")) {
                throw new InflateException("pathInterpolator requires the controlY1 attribute");
            }
            float f = !a.b.a.a.s(xmlPullParser, "controlX1") ? 0.0f : typedArrayY.getFloat(0, 0.0f);
            float f2 = !a.b.a.a.s(xmlPullParser, "controlY1") ? 0.0f : typedArrayY.getFloat(1, 0.0f);
            boolean zS = a.b.a.a.s(xmlPullParser, "controlX2");
            if (zS != a.b.a.a.s(xmlPullParser, "controlY2")) {
                throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
            }
            if (zS) {
                float f3 = !a.b.a.a.s(xmlPullParser, "controlX2") ? 0.0f : typedArrayY.getFloat(2, 0.0f);
                float f4 = !a.b.a.a.s(xmlPullParser, "controlY2") ? 0.0f : typedArrayY.getFloat(3, 0.0f);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
                a(path);
            } else {
                Path path2 = new Path();
                path2.moveTo(0.0f, 0.0f);
                path2.quadTo(f, f2, 1.0f, 1.0f);
                a(path2);
            }
        }
        typedArrayY.recycle();
    }

    public final void a(Path path) {
        int i = 0;
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        int iMin = Math.min(3000, ((int) (length / 0.002f)) + 1);
        if (iMin <= 0) {
            throw new IllegalArgumentException("The Path has a invalid length " + length);
        }
        this.f17a = new float[iMin];
        this.f18b = new float[iMin];
        float[] fArr = new float[2];
        for (int i2 = 0; i2 < iMin; i2++) {
            pathMeasure.getPosTan((i2 * length) / (iMin - 1), fArr, null);
            this.f17a[i2] = fArr[0];
            this.f18b[i2] = fArr[1];
        }
        if (Math.abs(this.f17a[0]) <= 1.0E-5d && Math.abs(this.f18b[0]) <= 1.0E-5d) {
            int i3 = iMin - 1;
            if (Math.abs(this.f17a[i3] - 1.0f) <= 1.0E-5d && Math.abs(this.f18b[i3] - 1.0f) <= 1.0E-5d) {
                float f = 0.0f;
                int i4 = 0;
                while (i < iMin) {
                    float[] fArr2 = this.f17a;
                    int i5 = i4 + 1;
                    float f2 = fArr2[i4];
                    if (f2 < f) {
                        throw new IllegalArgumentException("The Path cannot loop back on itself, x :" + f2);
                    }
                    fArr2[i] = f2;
                    i++;
                    f = f2;
                    i4 = i5;
                }
                if (pathMeasure.nextContour()) {
                    throw new IllegalArgumentException("The Path should be continuous, can't have 2+ contours");
                }
                return;
            }
        }
        StringBuilder sbB = c.a.a.a.a.b("The Path must start at (0,0) and end at (1,1) start: ");
        sbB.append(this.f17a[0]);
        sbB.append(",");
        sbB.append(this.f18b[0]);
        sbB.append(" end:");
        int i6 = iMin - 1;
        sbB.append(this.f17a[i6]);
        sbB.append(",");
        sbB.append(this.f18b[i6]);
        throw new IllegalArgumentException(sbB.toString());
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int i = 0;
        int length = this.f17a.length - 1;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.f17a[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float[] fArr = this.f17a;
        float f2 = fArr[length] - fArr[i];
        if (f2 == 0.0f) {
            return this.f18b[i];
        }
        float f3 = (f - fArr[i]) / f2;
        float[] fArr2 = this.f18b;
        float f4 = fArr2[i];
        return ((fArr2[length] - f4) * f3) + f4;
    }
}
