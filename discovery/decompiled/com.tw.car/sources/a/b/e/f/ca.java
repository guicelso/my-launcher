package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;

/* JADX INFO: loaded from: classes.dex */
public class ca {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final ThreadLocal<TypedValue> f316a = new ThreadLocal<>();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int[] f317b = {-16842910};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final int[] f318c = {R.attr.state_focused};
    public static final int[] d;
    public static final int[] e;
    public static final int[] f;
    public static final int[] g;

    static {
        new int[1][0] = 16843518;
        d = new int[]{R.attr.state_pressed};
        e = new int[]{R.attr.state_checked};
        new int[1][0] = 16842913;
        int[] iArr = {-16842919, -16842908};
        f = new int[0];
        g = new int[1];
    }

    public static int a(Context context, int i) {
        ColorStateList colorStateListC = c(context, i);
        if (colorStateListC != null && colorStateListC.isStateful()) {
            return colorStateListC.getColorForState(f317b, colorStateListC.getDefaultColor());
        }
        TypedValue typedValue = f316a.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            f316a.set(typedValue);
        }
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        float f2 = typedValue.getFloat();
        int iB = b(context, i);
        return a.b.d.c.a.b(iB, Math.round(Color.alpha(iB) * f2));
    }

    public static int b(Context context, int i) {
        int[] iArr = g;
        iArr[0] = i;
        ha haVarA = ha.a(context, (AttributeSet) null, iArr);
        try {
            return haVarA.a(0, 0);
        } finally {
            haVarA.f337b.recycle();
        }
    }

    public static ColorStateList c(Context context, int i) {
        int[] iArr = g;
        iArr[0] = i;
        ha haVarA = ha.a(context, (AttributeSet) null, iArr);
        try {
            return haVarA.a(0);
        } finally {
            haVarA.f337b.recycle();
        }
    }
}
