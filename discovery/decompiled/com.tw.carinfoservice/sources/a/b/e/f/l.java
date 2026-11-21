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

/* JADX INFO: loaded from: classes.dex */
public class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final TextView f299a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public e0 f300b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public e0 f301c;
    public e0 d;
    public e0 e;
    public e0 f;
    public e0 g;
    public final n h;
    public int i = 0;
    public Typeface j;
    public boolean k;

    public class a extends a.b.d.b.b.g {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final /* synthetic */ WeakReference f302a;

        public a(WeakReference weakReference) {
            this.f302a = weakReference;
        }

        @Override // a.b.d.b.b.g
        public void c(Typeface typeface) {
            l lVar = l.this;
            WeakReference weakReference = this.f302a;
            if (lVar.k) {
                lVar.j = typeface;
                TextView textView = (TextView) weakReference.get();
                if (textView != null) {
                    textView.setTypeface(typeface, lVar.i);
                }
            }
        }
    }

    public l(TextView textView) {
        this.f299a = textView;
        this.h = new n(textView);
    }

    public static e0 c(Context context, f fVar, int i) {
        ColorStateList colorStateListL = fVar.l(context, i);
        if (colorStateListL == null) {
            return null;
        }
        e0 e0Var = new e0();
        e0Var.d = true;
        e0Var.f270a = colorStateListL;
        return e0Var;
    }

    public final void a(Drawable drawable, e0 e0Var) {
        if (drawable == null || e0Var == null) {
            return;
        }
        f.p(drawable, e0Var, this.f299a.getDrawableState());
    }

    public void b() {
        if (this.f300b != null || this.f301c != null || this.d != null || this.e != null) {
            Drawable[] compoundDrawables = this.f299a.getCompoundDrawables();
            a(compoundDrawables[0], this.f300b);
            a(compoundDrawables[1], this.f301c);
            a(compoundDrawables[2], this.d);
            a(compoundDrawables[3], this.e);
        }
        if (this.f == null && this.g == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = this.f299a.getCompoundDrawablesRelative();
        a(compoundDrawablesRelative[0], this.f);
        a(compoundDrawablesRelative[2], this.g);
    }

    @SuppressLint({"NewApi"})
    public void d(AttributeSet attributeSet, int i) {
        ColorStateList colorStateListC;
        ColorStateList colorStateListC2;
        boolean zA;
        boolean z;
        int i2;
        float fApplyDimension;
        int i3;
        int resourceId;
        ColorStateList colorStateListC3;
        int[] iArr = a.b.e.b.b.j;
        int[] iArr2 = a.b.e.b.b.v;
        Context context = this.f299a.getContext();
        f fVarG = f.g();
        g0 g0VarM = g0.m(context, attributeSet, a.b.e.b.b.i, i, 0);
        int iJ = g0VarM.j(0, -1);
        if (g0VarM.l(3)) {
            this.f300b = c(context, fVarG, g0VarM.j(3, 0));
        }
        if (g0VarM.l(1)) {
            this.f301c = c(context, fVarG, g0VarM.j(1, 0));
        }
        if (g0VarM.l(4)) {
            this.d = c(context, fVarG, g0VarM.j(4, 0));
        }
        if (g0VarM.l(2)) {
            this.e = c(context, fVarG, g0VarM.j(2, 0));
        }
        int i4 = Build.VERSION.SDK_INT;
        if (g0VarM.l(5)) {
            this.f = c(context, fVarG, g0VarM.j(5, 0));
        }
        if (g0VarM.l(6)) {
            this.g = c(context, fVarG, g0VarM.j(6, 0));
        }
        g0VarM.f278b.recycle();
        boolean z2 = this.f299a.getTransformationMethod() instanceof PasswordTransformationMethod;
        ColorStateList colorStateListC4 = null;
        if (iJ != -1) {
            g0 g0Var = new g0(context, context.obtainStyledAttributes(iJ, iArr2));
            if (z2 || !g0Var.l(12)) {
                zA = false;
                z = false;
            } else {
                zA = g0Var.a(12, false);
                z = true;
            }
            f(context, g0Var);
            if (i4 < 23) {
                colorStateListC3 = g0Var.l(3) ? g0Var.c(3) : null;
                colorStateListC2 = g0Var.l(4) ? g0Var.c(4) : null;
                if (g0Var.l(5)) {
                    colorStateListC4 = g0Var.c(5);
                }
            } else {
                colorStateListC3 = null;
                colorStateListC2 = null;
            }
            g0Var.f278b.recycle();
            colorStateListC = colorStateListC4;
            colorStateListC4 = colorStateListC3;
        } else {
            colorStateListC = null;
            colorStateListC2 = null;
            zA = false;
            z = false;
        }
        g0 g0Var2 = new g0(context, context.obtainStyledAttributes(attributeSet, iArr2, i, 0));
        if (!z2 && g0Var2.l(12)) {
            zA = g0Var2.a(12, false);
            z = true;
        }
        if (i4 < 23) {
            if (g0Var2.l(3)) {
                colorStateListC4 = g0Var2.c(3);
            }
            if (g0Var2.l(4)) {
                colorStateListC2 = g0Var2.c(4);
            }
            if (g0Var2.l(5)) {
                colorStateListC = g0Var2.c(5);
            }
        }
        ColorStateList colorStateList = colorStateListC4;
        ColorStateList colorStateList2 = colorStateListC2;
        if (i4 >= 28 && g0Var2.l(0) && g0Var2.e(0, -1) == 0) {
            this.f299a.setTextSize(0, 0.0f);
        }
        f(context, g0Var2);
        g0Var2.f278b.recycle();
        if (colorStateList != null) {
            this.f299a.setTextColor(colorStateList);
        }
        if (colorStateList2 != null) {
            this.f299a.setHintTextColor(colorStateList2);
        }
        if (colorStateListC != null) {
            this.f299a.setLinkTextColor(colorStateListC);
        }
        if (!z2 && z) {
            this.f299a.setAllCaps(zA);
        }
        Typeface typeface = this.j;
        if (typeface != null) {
            this.f299a.setTypeface(typeface, this.i);
        }
        n nVar = this.h;
        TypedArray typedArrayObtainStyledAttributes = nVar.j.obtainStyledAttributes(attributeSet, iArr, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            nVar.f310a = typedArrayObtainStyledAttributes.getInt(5, 0);
        }
        float dimension = typedArrayObtainStyledAttributes.hasValue(4) ? typedArrayObtainStyledAttributes.getDimension(4, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes.hasValue(2)) {
            fApplyDimension = typedArrayObtainStyledAttributes.getDimension(2, -1.0f);
            i2 = 1;
        } else {
            i2 = 1;
            fApplyDimension = -1.0f;
        }
        float dimension2 = typedArrayObtainStyledAttributes.hasValue(i2) ? typedArrayObtainStyledAttributes.getDimension(i2, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes.hasValue(3) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr3 = new int[length];
            if (length > 0) {
                for (int i5 = 0; i5 < length; i5++) {
                    iArr3[i5] = typedArrayObtainTypedArray.getDimensionPixelSize(i5, -1);
                }
                int[] iArrB = nVar.b(iArr3);
                nVar.f = iArrB;
                int length2 = iArrB.length;
                boolean z3 = length2 > 0;
                nVar.g = z3;
                if (z3) {
                    nVar.f310a = 1;
                    nVar.d = iArrB[0];
                    nVar.e = iArrB[length2 - 1];
                    nVar.f312c = -1.0f;
                }
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!nVar.i()) {
            nVar.f310a = 0;
        } else if (nVar.f310a == 1) {
            if (!nVar.g) {
                DisplayMetrics displayMetrics = nVar.j.getResources().getDisplayMetrics();
                if (fApplyDimension == -1.0f) {
                    i3 = 2;
                    fApplyDimension = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                } else {
                    i3 = 2;
                }
                if (dimension2 == -1.0f) {
                    dimension2 = TypedValue.applyDimension(i3, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                nVar.j(fApplyDimension, dimension2, dimension);
            }
            nVar.h();
        }
        if (a.b.d.i.b.f164a) {
            n nVar2 = this.h;
            if (nVar2.f310a != 0) {
                int[] iArr4 = nVar2.f;
                if (iArr4.length > 0) {
                    if (this.f299a.getAutoSizeStepGranularity() != -1.0f) {
                        this.f299a.setAutoSizeTextTypeUniformWithConfiguration(Math.round(this.h.d), Math.round(this.h.e), Math.round(this.h.f312c), 0);
                    } else {
                        this.f299a.setAutoSizeTextTypeUniformWithPresetSizes(iArr4, 0);
                    }
                }
            }
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, iArr);
        int dimensionPixelSize = typedArrayObtainStyledAttributes2.getDimensionPixelSize(6, -1);
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes2.getDimensionPixelSize(8, -1);
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes2.getDimensionPixelSize(9, -1);
        typedArrayObtainStyledAttributes2.recycle();
        if (dimensionPixelSize != -1) {
            a.b.a.a.H(this.f299a, dimensionPixelSize);
        }
        if (dimensionPixelSize2 != -1) {
            a.b.a.a.M(this.f299a, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != -1) {
            a.b.a.a.N(this.f299a, dimensionPixelSize3);
        }
    }

    public void e(Context context, int i) {
        ColorStateList colorStateListC;
        g0 g0Var = new g0(context, context.obtainStyledAttributes(i, a.b.e.b.b.v));
        if (g0Var.l(12)) {
            this.f299a.setAllCaps(g0Var.a(12, false));
        }
        if (Build.VERSION.SDK_INT < 23 && g0Var.l(3) && (colorStateListC = g0Var.c(3)) != null) {
            this.f299a.setTextColor(colorStateListC);
        }
        if (g0Var.l(0) && g0Var.e(0, -1) == 0) {
            this.f299a.setTextSize(0, 0.0f);
        }
        f(context, g0Var);
        g0Var.f278b.recycle();
        Typeface typeface = this.j;
        if (typeface != null) {
            this.f299a.setTypeface(typeface, this.i);
        }
    }

    public final void f(Context context, g0 g0Var) {
        String string;
        Typeface typeface;
        this.i = g0Var.h(2, this.i);
        boolean z = true;
        if (g0Var.l(10) || g0Var.l(11)) {
            this.j = null;
            int i = g0Var.l(11) ? 11 : 10;
            if (!context.isRestricted()) {
                try {
                    Typeface typefaceG = g0Var.g(i, this.i, new a(new WeakReference(this.f299a)));
                    this.j = typefaceG;
                    if (typefaceG != null) {
                        z = false;
                    }
                    this.k = z;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.j != null || (string = g0Var.f278b.getString(i)) == null) {
                return;
            }
            this.j = Typeface.create(string, this.i);
            return;
        }
        if (g0Var.l(1)) {
            this.k = false;
            int iH = g0Var.h(1, 1);
            if (iH == 1) {
                typeface = Typeface.SANS_SERIF;
            } else if (iH == 2) {
                typeface = Typeface.SERIF;
            } else if (iH != 3) {
                return;
            } else {
                typeface = Typeface.MONOSPACE;
            }
            this.j = typeface;
        }
    }
}
