package a.b.e.f;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.ref.WeakReference;

/* JADX INFO: renamed from: a.b.e.f.v, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0030v {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final TextView f385a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public fa f386b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public fa f387c;
    public fa d;
    public fa e;
    public fa f;
    public fa g;
    public final C0032x h;
    public int i = 0;
    public Typeface j;
    public boolean k;

    public C0030v(TextView textView) {
        this.f385a = textView;
        this.h = new C0032x(this.f385a);
    }

    public static fa a(Context context, C0019j c0019j, int i) {
        ColorStateList colorStateListD = c0019j.d(context, i);
        if (colorStateListD == null) {
            return null;
        }
        fa faVar = new fa();
        faVar.d = true;
        faVar.f329a = colorStateListD;
        return faVar;
    }

    public void a() {
        if (this.f386b != null || this.f387c != null || this.d != null || this.e != null) {
            Drawable[] compoundDrawables = this.f385a.getCompoundDrawables();
            a(compoundDrawables[0], this.f386b);
            a(compoundDrawables[1], this.f387c);
            a(compoundDrawables[2], this.d);
            a(compoundDrawables[3], this.e);
        }
        int i = Build.VERSION.SDK_INT;
        if (this.f == null && this.g == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = this.f385a.getCompoundDrawablesRelative();
        a(compoundDrawablesRelative[0], this.f);
        a(compoundDrawablesRelative[2], this.g);
    }

    public final void a(Drawable drawable, fa faVar) {
        if (drawable == null || faVar == null) {
            return;
        }
        C0019j.a(drawable, faVar, this.f385a.getDrawableState());
    }

    @SuppressLint({"NewApi"})
    public void a(AttributeSet attributeSet, int i) {
        ColorStateList colorStateListA;
        ColorStateList colorStateListA2;
        boolean z;
        boolean zA;
        int resourceId;
        Context context = this.f385a.getContext();
        C0019j c0019jA = C0019j.a();
        ha haVarA = ha.a(context, attributeSet, a.b.e.b.j.AppCompatTextHelper, i, 0);
        int iF = haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_textAppearance, -1);
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableLeft)) {
            this.f386b = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableTop)) {
            this.f387c = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableRight)) {
            this.d = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableBottom)) {
            this.e = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableBottom, 0));
        }
        int i2 = Build.VERSION.SDK_INT;
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableStart)) {
            this.f = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (haVarA.e(a.b.e.b.j.AppCompatTextHelper_android_drawableEnd)) {
            this.g = a(context, c0019jA, haVarA.f(a.b.e.b.j.AppCompatTextHelper_android_drawableEnd, 0));
        }
        haVarA.f337b.recycle();
        boolean z2 = this.f385a.getTransformationMethod() instanceof PasswordTransformationMethod;
        ColorStateList colorStateListA3 = null;
        if (iF != -1) {
            ha haVar = new ha(context, context.obtainStyledAttributes(iF, a.b.e.b.j.TextAppearance));
            if (z2 || !haVar.e(a.b.e.b.j.TextAppearance_textAllCaps)) {
                z = false;
                zA = false;
            } else {
                zA = haVar.a(a.b.e.b.j.TextAppearance_textAllCaps, false);
                z = true;
            }
            a(context, haVar);
            if (Build.VERSION.SDK_INT < 23) {
                ColorStateList colorStateListA4 = haVar.e(a.b.e.b.j.TextAppearance_android_textColor) ? haVar.a(a.b.e.b.j.TextAppearance_android_textColor) : null;
                colorStateListA2 = haVar.e(a.b.e.b.j.TextAppearance_android_textColorHint) ? haVar.a(a.b.e.b.j.TextAppearance_android_textColorHint) : null;
                ColorStateList colorStateList = colorStateListA4;
                colorStateListA = haVar.e(a.b.e.b.j.TextAppearance_android_textColorLink) ? haVar.a(a.b.e.b.j.TextAppearance_android_textColorLink) : null;
                colorStateListA3 = colorStateList;
            } else {
                colorStateListA = null;
                colorStateListA2 = null;
            }
            haVar.f337b.recycle();
        } else {
            colorStateListA = null;
            colorStateListA2 = null;
            z = false;
            zA = false;
        }
        ha haVarA2 = ha.a(context, attributeSet, a.b.e.b.j.TextAppearance, i, 0);
        if (!z2 && haVarA2.e(a.b.e.b.j.TextAppearance_textAllCaps)) {
            zA = haVarA2.a(a.b.e.b.j.TextAppearance_textAllCaps, false);
            z = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (haVarA2.e(a.b.e.b.j.TextAppearance_android_textColor)) {
                colorStateListA3 = haVarA2.a(a.b.e.b.j.TextAppearance_android_textColor);
            }
            if (haVarA2.e(a.b.e.b.j.TextAppearance_android_textColorHint)) {
                colorStateListA2 = haVarA2.a(a.b.e.b.j.TextAppearance_android_textColorHint);
            }
            if (haVarA2.e(a.b.e.b.j.TextAppearance_android_textColorLink)) {
                colorStateListA = haVarA2.a(a.b.e.b.j.TextAppearance_android_textColorLink);
            }
        }
        if (Build.VERSION.SDK_INT >= 28 && haVarA2.e(a.b.e.b.j.TextAppearance_android_textSize) && haVarA2.c(a.b.e.b.j.TextAppearance_android_textSize, -1) == 0) {
            this.f385a.setTextSize(0, 0.0f);
        }
        a(context, haVarA2);
        haVarA2.f337b.recycle();
        if (colorStateListA3 != null) {
            this.f385a.setTextColor(colorStateListA3);
        }
        if (colorStateListA2 != null) {
            this.f385a.setHintTextColor(colorStateListA2);
        }
        if (colorStateListA != null) {
            this.f385a.setLinkTextColor(colorStateListA);
        }
        if (!z2 && z) {
            this.f385a.setAllCaps(zA);
        }
        Typeface typeface = this.j;
        if (typeface != null) {
            this.f385a.setTypeface(typeface, this.i);
        }
        C0032x c0032x = this.h;
        TypedArray typedArrayObtainStyledAttributes = c0032x.l.obtainStyledAttributes(attributeSet, a.b.e.b.j.AppCompatTextView, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(a.b.e.b.j.AppCompatTextView_autoSizeTextType)) {
            c0032x.f393c = typedArrayObtainStyledAttributes.getInt(a.b.e.b.j.AppCompatTextView_autoSizeTextType, 0);
        }
        float dimension = typedArrayObtainStyledAttributes.hasValue(a.b.e.b.j.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes.getDimension(a.b.e.b.j.AppCompatTextView_autoSizeStepGranularity, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes.hasValue(a.b.e.b.j.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes.getDimension(a.b.e.b.j.AppCompatTextView_autoSizeMinTextSize, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes.hasValue(a.b.e.b.j.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes.getDimension(a.b.e.b.j.AppCompatTextView_autoSizeMaxTextSize, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes.hasValue(a.b.e.b.j.AppCompatTextView_autoSizePresetSizes) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(a.b.e.b.j.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr = new int[length];
            if (length > 0) {
                for (int i3 = 0; i3 < length; i3++) {
                    iArr[i3] = typedArrayObtainTypedArray.getDimensionPixelSize(i3, -1);
                }
                c0032x.h = c0032x.a(iArr);
                int length2 = c0032x.h.length;
                c0032x.i = length2 > 0;
                if (c0032x.i) {
                    c0032x.f393c = 1;
                    int[] iArr2 = c0032x.h;
                    c0032x.f = iArr2[0];
                    c0032x.g = iArr2[length2 - 1];
                    c0032x.e = -1.0f;
                }
                boolean z3 = c0032x.i;
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!c0032x.h()) {
            c0032x.f393c = 0;
        } else if (c0032x.f393c == 1) {
            if (!c0032x.i) {
                DisplayMetrics displayMetrics = c0032x.l.getResources().getDisplayMetrics();
                if (dimension2 == -1.0f) {
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                }
                if (dimension3 == -1.0f) {
                    dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                c0032x.a(dimension2, dimension3, dimension);
            }
            c0032x.g();
        }
        if (a.b.d.i.b.f173a) {
            C0032x c0032x2 = this.h;
            if (c0032x2.f393c != 0) {
                int[] iArr3 = c0032x2.h;
                if (iArr3.length > 0) {
                    if (this.f385a.getAutoSizeStepGranularity() != -1.0f) {
                        this.f385a.setAutoSizeTextTypeUniformWithConfiguration(Math.round(this.h.f), Math.round(this.h.g), Math.round(this.h.e), 0);
                    } else {
                        this.f385a.setAutoSizeTextTypeUniformWithPresetSizes(iArr3, 0);
                    }
                }
            }
        }
        ha haVarA3 = ha.a(context, attributeSet, a.b.e.b.j.AppCompatTextView);
        int iC = haVarA3.c(a.b.e.b.j.AppCompatTextView_firstBaselineToTopHeight, -1);
        int iC2 = haVarA3.c(a.b.e.b.j.AppCompatTextView_lastBaselineToBottomHeight, -1);
        int iC3 = haVarA3.c(a.b.e.b.j.AppCompatTextView_lineHeight, -1);
        haVarA3.f337b.recycle();
        if (iC != -1) {
            a.b.b.a.e.a(this.f385a, iC);
        }
        if (iC2 != -1) {
            a.b.b.a.e.b(this.f385a, iC2);
        }
        if (iC3 != -1) {
            a.b.b.a.e.c(this.f385a, iC3);
        }
    }

    public void a(boolean z, int i, int i2, int i3, int i4) {
        if (a.b.d.i.b.f173a) {
            return;
        }
        this.h.a();
    }

    public void a(Context context, int i) {
        ColorStateList colorStateListA;
        ha haVar = new ha(context, context.obtainStyledAttributes(i, a.b.e.b.j.TextAppearance));
        if (haVar.e(a.b.e.b.j.TextAppearance_textAllCaps)) {
            this.f385a.setAllCaps(haVar.a(a.b.e.b.j.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && haVar.e(a.b.e.b.j.TextAppearance_android_textColor) && (colorStateListA = haVar.a(a.b.e.b.j.TextAppearance_android_textColor)) != null) {
            this.f385a.setTextColor(colorStateListA);
        }
        if (haVar.e(a.b.e.b.j.TextAppearance_android_textSize) && haVar.c(a.b.e.b.j.TextAppearance_android_textSize, -1) == 0) {
            this.f385a.setTextSize(0, 0.0f);
        }
        a(context, haVar);
        haVar.f337b.recycle();
        Typeface typeface = this.j;
        if (typeface != null) {
            this.f385a.setTypeface(typeface, this.i);
        }
    }

    public void a(int i, float f) {
        if (a.b.d.i.b.f173a) {
            return;
        }
        C0032x c0032x = this.h;
        if (c0032x.h() && c0032x.f393c != 0) {
            return;
        }
        this.h.a(i, f);
    }

    public final void a(Context context, ha haVar) {
        String string;
        Typeface typeface;
        this.i = haVar.d(a.b.e.b.j.TextAppearance_android_textStyle, this.i);
        boolean z = true;
        if (haVar.e(a.b.e.b.j.TextAppearance_android_fontFamily) || haVar.e(a.b.e.b.j.TextAppearance_fontFamily)) {
            this.j = null;
            int i = haVar.e(a.b.e.b.j.TextAppearance_fontFamily) ? a.b.e.b.j.TextAppearance_fontFamily : a.b.e.b.j.TextAppearance_android_fontFamily;
            if (!context.isRestricted()) {
                try {
                    this.j = haVar.a(i, this.i, new C0029u(this, new WeakReference(this.f385a)));
                    if (this.j != null) {
                        z = false;
                    }
                    this.k = z;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.j != null || (string = haVar.f337b.getString(i)) == null) {
                return;
            }
            this.j = Typeface.create(string, this.i);
            return;
        }
        if (haVar.e(a.b.e.b.j.TextAppearance_android_typeface)) {
            this.k = false;
            int iD = haVar.d(a.b.e.b.j.TextAppearance_android_typeface, 1);
            if (iD == 1) {
                typeface = Typeface.SANS_SERIF;
            } else if (iD == 2) {
                typeface = Typeface.SERIF;
            } else if (iD != 3) {
                return;
            } else {
                typeface = Typeface.MONOSPACE;
            }
            this.j = typeface;
        }
    }
}
