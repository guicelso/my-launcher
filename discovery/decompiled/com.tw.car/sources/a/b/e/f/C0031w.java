package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: renamed from: a.b.e.f.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0031w extends TextView implements a.b.d.h.m, a.b.d.i.b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0018i f388a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0030v f389b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Future<a.b.d.f.a> f390c;

    public C0031w(Context context) {
        this(context, null, R.attr.textViewStyle);
    }

    public C0031w(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public C0031w(Context context, AttributeSet attributeSet, int i) {
        super(ea.a(context), attributeSet, i);
        this.f388a = new C0018i(this);
        this.f388a.a(attributeSet, i);
        this.f389b = new C0030v(this);
        this.f389b.a(attributeSet, i);
        this.f389b.a();
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            c0018i.a();
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            c0030v.a();
        }
    }

    @Override // android.widget.TextView
    public int getAutoSizeMaxTextSize() {
        if (a.b.d.i.b.f173a) {
            return super.getAutoSizeMaxTextSize();
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            return c0030v.h.b();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeMinTextSize() {
        if (a.b.d.i.b.f173a) {
            return super.getAutoSizeMinTextSize();
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            return c0030v.h.c();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeStepGranularity() {
        if (a.b.d.i.b.f173a) {
            return super.getAutoSizeStepGranularity();
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            return c0030v.h.d();
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int[] getAutoSizeTextAvailableSizes() {
        if (a.b.d.i.b.f173a) {
            return super.getAutoSizeTextAvailableSizes();
        }
        C0030v c0030v = this.f389b;
        return c0030v != null ? c0030v.h.e() : new int[0];
    }

    @Override // android.widget.TextView
    public int getAutoSizeTextType() {
        if (a.b.d.i.b.f173a) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0;
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            return c0030v.h.f();
        }
        return 0;
    }

    @Override // android.widget.TextView
    public int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    @Override // android.widget.TextView
    public int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    @Override // a.b.d.h.m
    public ColorStateList getSupportBackgroundTintList() {
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            return c0018i.b();
        }
        return null;
    }

    @Override // a.b.d.h.m
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            return c0018i.c();
        }
        return null;
    }

    @Override // android.widget.TextView
    public CharSequence getText() {
        Future<a.b.d.f.a> future = this.f390c;
        if (future != null) {
            try {
                this.f390c = null;
                a.b.b.a.e.a((TextView) this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
        return super.getText();
    }

    public a.b.d.f.a.C0002a getTextMetricsParamsCompat() {
        return a.b.b.a.e.a(this);
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        a.b.b.a.e.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            c0030v.a(z, i, i2, i3, i4);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        Future<a.b.d.f.a> future = this.f390c;
        if (future != null) {
            try {
                this.f390c = null;
                a.b.b.a.e.a((TextView) this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        C0030v c0030v = this.f389b;
        if (c0030v == null || a.b.d.i.b.f173a) {
            return;
        }
        C0032x c0032x = c0030v.h;
        if (c0032x.h() && c0032x.f393c != 0) {
            this.f389b.h.a();
        }
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (a.b.d.i.b.f173a) {
            super.setAutoSizeTextTypeWithDefaults(i);
            return;
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            C0032x c0032x = c0030v.h;
            if (c0032x.h()) {
                if (i == 0) {
                    c0032x.f393c = 0;
                    c0032x.f = -1.0f;
                    c0032x.g = -1.0f;
                    c0032x.e = -1.0f;
                    c0032x.h = new int[0];
                    c0032x.d = false;
                    return;
                }
                if (i != 1) {
                    throw new IllegalArgumentException("Unknown auto-size text type: " + i);
                }
                DisplayMetrics displayMetrics = c0032x.l.getResources().getDisplayMetrics();
                c0032x.a(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                if (c0032x.g()) {
                    c0032x.a();
                }
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            c0018i.f341c = -1;
            c0018i.a((ColorStateList) null);
            c0018i.a();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            c0018i.a(i);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(a.b.b.a.e.a(this, callback));
    }

    @Override // android.widget.TextView
    public void setFirstBaselineToTopHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setFirstBaselineToTopHeight(i);
        } else {
            a.b.b.a.e.a(this, i);
        }
    }

    @Override // android.widget.TextView
    public void setLastBaselineToBottomHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setLastBaselineToBottomHeight(i);
        } else {
            a.b.b.a.e.b(this, i);
        }
    }

    @Override // android.widget.TextView
    public void setLineHeight(int i) {
        a.b.b.a.e.c(this, i);
    }

    public void setPrecomputedText(a.b.d.f.a aVar) {
        a.b.b.a.e.a((TextView) this, aVar);
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            c0018i.b(colorStateList);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0018i c0018i = this.f388a;
        if (c0018i != null) {
            c0018i.a(mode);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            c0030v.a(context, i);
        }
    }

    public void setTextFuture(Future<a.b.d.f.a> future) {
        this.f390c = future;
        requestLayout();
    }

    public void setTextMetricsParamsCompat(a.b.d.f.a.C0002a c0002a) {
        int i = Build.VERSION.SDK_INT;
        TextDirectionHeuristic textDirectionHeuristicC = c0002a.c();
        int i2 = 1;
        if (textDirectionHeuristicC != TextDirectionHeuristics.FIRSTSTRONG_RTL && textDirectionHeuristicC != TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            if (textDirectionHeuristicC == TextDirectionHeuristics.ANYRTL_LTR) {
                i2 = 2;
            } else if (textDirectionHeuristicC == TextDirectionHeuristics.LTR) {
                i2 = 3;
            } else if (textDirectionHeuristicC == TextDirectionHeuristics.RTL) {
                i2 = 4;
            } else if (textDirectionHeuristicC == TextDirectionHeuristics.LOCALE) {
                i2 = 5;
            } else if (textDirectionHeuristicC == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                i2 = 6;
            } else if (textDirectionHeuristicC == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                i2 = 7;
            }
        }
        setTextDirection(i2);
        if (Build.VERSION.SDK_INT >= 23) {
            getPaint().set(c0002a.f119a);
            setBreakStrategy(c0002a.a());
            setHyphenationFrequency(c0002a.b());
        } else {
            float textScaleX = c0002a.f119a.getTextScaleX();
            getPaint().set(c0002a.f119a);
            if (textScaleX == getTextScaleX()) {
                setTextScaleX((textScaleX / 2.0f) + 1.0f);
            }
            setTextScaleX(textScaleX);
        }
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        if (a.b.d.i.b.f173a) {
            super.setTextSize(i, f);
            return;
        }
        C0030v c0030v = this.f389b;
        if (c0030v != null) {
            c0030v.a(i, f);
        }
    }
}
