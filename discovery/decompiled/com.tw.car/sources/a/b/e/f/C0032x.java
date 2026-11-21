package a.b.e.f;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: renamed from: a.b.e.f.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0032x {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final RectF f391a = new RectF();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static ConcurrentHashMap<String, Method> f392b = new ConcurrentHashMap<>();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f393c = 0;
    public boolean d = false;
    public float e = -1.0f;
    public float f = -1.0f;
    public float g = -1.0f;
    public int[] h = new int[0];
    public boolean i = false;
    public TextPaint j;
    public final TextView k;
    public final Context l;

    public C0032x(TextView textView) {
        this.k = textView;
        this.l = this.k.getContext();
    }

    public final <T> T a(Object obj, String str, T t) {
        try {
            return (T) a(str).invoke(obj, new Object[0]);
        } catch (Exception e) {
            Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#" + str + "() method", e);
            return t;
        }
    }

    public final Method a(String str) {
        try {
            Method declaredMethod = f392b.get(str);
            if (declaredMethod == null && (declaredMethod = TextView.class.getDeclaredMethod(str, new Class[0])) != null) {
                declaredMethod.setAccessible(true);
                f392b.put(str, declaredMethod);
            }
            return declaredMethod;
        } catch (Exception e) {
            Log.w("ACTVAutoSizeHelper", "Failed to retrieve TextView#" + str + "() method", e);
            return null;
        }
    }

    public void a() {
        if (h() && this.f393c != 0) {
            if (this.d) {
                if (this.k.getMeasuredHeight() <= 0 || this.k.getMeasuredWidth() <= 0) {
                    return;
                }
                int measuredWidth = ((Boolean) a(this.k, "getHorizontallyScrolling", false)).booleanValue() ? 1048576 : (this.k.getMeasuredWidth() - this.k.getTotalPaddingLeft()) - this.k.getTotalPaddingRight();
                int height = (this.k.getHeight() - this.k.getCompoundPaddingBottom()) - this.k.getCompoundPaddingTop();
                if (measuredWidth <= 0 || height <= 0) {
                    return;
                }
                synchronized (f391a) {
                    f391a.setEmpty();
                    f391a.right = measuredWidth;
                    f391a.bottom = height;
                    float fA = a(f391a);
                    if (fA != this.k.getTextSize()) {
                        a(0, fA);
                    }
                }
            }
            this.d = true;
        }
    }

    public final void a(float f, float f2, float f3) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size text size (" + f + "px)");
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.f393c = 1;
        this.f = f;
        this.g = f2;
        this.e = f3;
        this.i = false;
    }

    public final int[] a(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return iArr;
        }
        Arrays.sort(iArr);
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            if (i > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i)) < 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        if (length == arrayList.size()) {
            return iArr;
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr2[i2] = ((Integer) arrayList.get(i2)).intValue();
        }
        return iArr2;
    }

    public int b() {
        return Math.round(this.g);
    }

    public int c() {
        return Math.round(this.f);
    }

    public int d() {
        return Math.round(this.e);
    }

    public int[] e() {
        return this.h;
    }

    public int f() {
        return this.f393c;
    }

    public final boolean g() {
        if (h() && this.f393c == 1) {
            if (!this.i || this.h.length == 0) {
                float fRound = Math.round(this.f);
                int i = 1;
                while (Math.round(this.e + fRound) <= Math.round(this.g)) {
                    i++;
                    fRound += this.e;
                }
                int[] iArr = new int[i];
                float f = this.f;
                for (int i2 = 0; i2 < i; i2++) {
                    iArr[i2] = Math.round(f);
                    f += this.e;
                }
                this.h = a(iArr);
            }
            this.d = true;
        } else {
            this.d = false;
        }
        return this.d;
    }

    public final boolean h() {
        return !(this.k instanceof C0020k);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public final int a(RectF rectF) {
        int i;
        StaticLayout staticLayout;
        CharSequence transformation;
        int length = this.h.length;
        if (length == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int i2 = 0;
        int i3 = length - 1;
        int i4 = 1;
        int i5 = 0;
        while (i4 <= i3) {
            int i6 = (i4 + i3) / 2;
            int i7 = this.h[i6];
            CharSequence text = this.k.getText();
            TransformationMethod transformationMethod = this.k.getTransformationMethod();
            if (transformationMethod != null && (transformation = transformationMethod.getTransformation(text, this.k)) != null) {
                text = transformation;
            }
            int i8 = Build.VERSION.SDK_INT;
            int maxLines = this.k.getMaxLines();
            TextPaint textPaint = this.j;
            if (textPaint == null) {
                this.j = new TextPaint();
            } else {
                textPaint.reset();
            }
            this.j.set(this.k.getPaint());
            this.j.setTextSize(i7);
            Layout.Alignment alignment = (Layout.Alignment) a(this.k, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL);
            if (Build.VERSION.SDK_INT >= 23) {
                staticLayout = StaticLayout.Builder.obtain(text, i2, text.length(), this.j, Math.round(rectF.right)).setAlignment(alignment).setLineSpacing(this.k.getLineSpacingExtra(), this.k.getLineSpacingMultiplier()).setIncludePad(this.k.getIncludeFontPadding()).setBreakStrategy(this.k.getBreakStrategy()).setHyphenationFrequency(this.k.getHyphenationFrequency()).setMaxLines(maxLines == -1 ? Integer.MAX_VALUE : maxLines).setTextDirection((TextDirectionHeuristic) a(this.k, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR)).build();
                i = -1;
            } else {
                int iRound = Math.round(rectF.right);
                int i9 = Build.VERSION.SDK_INT;
                i = -1;
                staticLayout = new StaticLayout(text, this.j, iRound, alignment, this.k.getLineSpacingMultiplier(), this.k.getLineSpacingExtra(), this.k.getIncludeFontPadding());
            }
            if ((maxLines == i || (staticLayout.getLineCount() <= maxLines && staticLayout.getLineEnd(staticLayout.getLineCount() - 1) == text.length())) && ((float) staticLayout.getHeight()) <= rectF.bottom) {
                int i10 = i6 + 1;
                i5 = i4;
                i4 = i10;
            } else {
                i5 = i6 - 1;
                i3 = i5;
            }
            i2 = 0;
        }
        return this.h[i5];
    }

    public void a(int i, float f) {
        Context context = this.l;
        float fApplyDimension = TypedValue.applyDimension(i, f, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics());
        if (fApplyDimension != this.k.getPaint().getTextSize()) {
            this.k.getPaint().setTextSize(fApplyDimension);
            int i2 = Build.VERSION.SDK_INT;
            boolean zIsInLayout = this.k.isInLayout();
            if (this.k.getLayout() != null) {
                this.d = false;
                try {
                    Method methodA = a("nullLayouts");
                    if (methodA != null) {
                        methodA.invoke(this.k, new Object[0]);
                    }
                } catch (Exception e) {
                    Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", e);
                }
                if (zIsInLayout) {
                    this.k.forceLayout();
                } else {
                    this.k.requestLayout();
                }
                this.k.invalidate();
            }
        }
    }
}
