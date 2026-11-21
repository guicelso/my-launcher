package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public class m extends TextView implements a.b.d.h.i, a.b.d.i.b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final e f307b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final l f308c;
    public Future<a.b.d.f.a> d;

    public m(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public m(Context context, AttributeSet attributeSet, int i) {
        super(d0.a(context), attributeSet, i);
        e eVar = new e(this);
        this.f307b = eVar;
        eVar.d(attributeSet, i);
        l lVar = new l(this);
        this.f308c = lVar;
        lVar.d(attributeSet, i);
        lVar.b();
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        e eVar = this.f307b;
        if (eVar != null) {
            eVar.a();
        }
        l lVar = this.f308c;
        if (lVar != null) {
            lVar.b();
        }
    }

    @Override // android.widget.TextView
    public int getAutoSizeMaxTextSize() {
        if (a.b.d.i.b.f164a) {
            return super.getAutoSizeMaxTextSize();
        }
        l lVar = this.f308c;
        if (lVar != null) {
            return Math.round(lVar.h.e);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeMinTextSize() {
        if (a.b.d.i.b.f164a) {
            return super.getAutoSizeMinTextSize();
        }
        l lVar = this.f308c;
        if (lVar != null) {
            return Math.round(lVar.h.d);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int getAutoSizeStepGranularity() {
        if (a.b.d.i.b.f164a) {
            return super.getAutoSizeStepGranularity();
        }
        l lVar = this.f308c;
        if (lVar != null) {
            return Math.round(lVar.h.f312c);
        }
        return -1;
    }

    @Override // android.widget.TextView
    public int[] getAutoSizeTextAvailableSizes() {
        if (a.b.d.i.b.f164a) {
            return super.getAutoSizeTextAvailableSizes();
        }
        l lVar = this.f308c;
        return lVar != null ? lVar.h.f : new int[0];
    }

    @Override // android.widget.TextView
    public int getAutoSizeTextType() {
        if (a.b.d.i.b.f164a) {
            return super.getAutoSizeTextType() == 1 ? 1 : 0;
        }
        l lVar = this.f308c;
        if (lVar != null) {
            return lVar.h.f310a;
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

    @Override // a.b.d.h.i
    public ColorStateList getSupportBackgroundTintList() {
        e eVar = this.f307b;
        if (eVar != null) {
            return eVar.b();
        }
        return null;
    }

    @Override // a.b.d.h.i
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        e eVar = this.f307b;
        if (eVar != null) {
            return eVar.c();
        }
        return null;
    }

    @Override // android.widget.TextView
    public CharSequence getText() {
        Future<a.b.d.f.a> future = this.d;
        if (future != null) {
            try {
                this.d = null;
                a.b.a.a.Q(this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
        return super.getText();
    }

    public a.b.d.f.a.C0004a getTextMetricsParamsCompat() {
        return a.b.a.a.r(this);
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        a.b.e.b.a.o(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        l lVar = this.f308c;
        if (lVar != null) {
            Objects.requireNonNull(lVar);
            if (a.b.d.i.b.f164a) {
                return;
            }
            lVar.h.a();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        Future<a.b.d.f.a> future = this.d;
        if (future != null) {
            try {
                this.d = null;
                a.b.a.a.Q(this, future.get());
            } catch (InterruptedException | ExecutionException unused) {
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        l lVar = this.f308c;
        if (lVar == null || a.b.d.i.b.f164a) {
            return;
        }
        n nVar = lVar.h;
        if (nVar.i() && nVar.f310a != 0) {
            this.f308c.h.a();
        }
    }

    @Override // android.widget.TextView
    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (a.b.d.i.b.f164a) {
            super.setAutoSizeTextTypeWithDefaults(i);
            return;
        }
        l lVar = this.f308c;
        if (lVar != null) {
            n nVar = lVar.h;
            if (nVar.i()) {
                if (i == 0) {
                    nVar.f310a = 0;
                    nVar.d = -1.0f;
                    nVar.e = -1.0f;
                    nVar.f312c = -1.0f;
                    nVar.f = new int[0];
                    nVar.f311b = false;
                    return;
                }
                if (i != 1) {
                    throw new IllegalArgumentException("Unknown auto-size text type: " + i);
                }
                DisplayMetrics displayMetrics = nVar.j.getResources().getDisplayMetrics();
                nVar.j(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                if (nVar.h()) {
                    nVar.a();
                }
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        e eVar = this.f307b;
        if (eVar != null) {
            eVar.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        e eVar = this.f307b;
        if (eVar != null) {
            eVar.f(i);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(a.b.a.a.Z(this, callback));
    }

    @Override // android.widget.TextView
    public void setFirstBaselineToTopHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setFirstBaselineToTopHeight(i);
        } else {
            a.b.a.a.H(this, i);
        }
    }

    @Override // android.widget.TextView
    public void setLastBaselineToBottomHeight(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            super.setLastBaselineToBottomHeight(i);
        } else {
            a.b.a.a.M(this, i);
        }
    }

    @Override // android.widget.TextView
    public void setLineHeight(int i) {
        a.b.a.a.N(this, i);
    }

    public void setPrecomputedText(a.b.d.f.a aVar) {
        a.b.a.a.Q(this, aVar);
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        e eVar = this.f307b;
        if (eVar != null) {
            eVar.h(colorStateList);
        }
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        e eVar = this.f307b;
        if (eVar != null) {
            eVar.i(mode);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        l lVar = this.f308c;
        if (lVar != null) {
            lVar.e(context, i);
        }
    }

    public void setTextFuture(Future<a.b.d.f.a> future) {
        this.d = future;
        requestLayout();
    }

    public void setTextMetricsParamsCompat(a.b.d.f.a.C0004a c0004a) {
        a.b.a.a.R(this, c0004a);
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        boolean z = a.b.d.i.b.f164a;
        if (z) {
            super.setTextSize(i, f);
            return;
        }
        l lVar = this.f308c;
        if (lVar != null) {
            Objects.requireNonNull(lVar);
            if (z) {
                return;
            }
            n nVar = lVar.h;
            if (nVar.i() && nVar.f310a != 0) {
                return;
            }
            lVar.h.g(i, f);
        }
    }
}
