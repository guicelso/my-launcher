package a.b.e.f;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AutoCompleteTextView;

/* JADX INFO: loaded from: classes.dex */
public class d extends AutoCompleteTextView implements a.b.d.h.i {
    public static final int[] d = {R.attr.popupBackground};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final e f262b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final l f263c;

    public d(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.tw.carinfoservice.R.attr.autoCompleteTextViewStyle);
    }

    public d(Context context, AttributeSet attributeSet, int i) {
        super(d0.a(context), attributeSet, i);
        g0 g0VarM = g0.m(getContext(), attributeSet, d, i, 0);
        if (g0VarM.l(0)) {
            setDropDownBackgroundDrawable(g0VarM.f(0));
        }
        g0VarM.f278b.recycle();
        e eVar = new e(this);
        this.f262b = eVar;
        eVar.d(attributeSet, i);
        l lVar = new l(this);
        this.f263c = lVar;
        lVar.d(attributeSet, i);
        lVar.b();
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        e eVar = this.f262b;
        if (eVar != null) {
            eVar.a();
        }
        l lVar = this.f263c;
        if (lVar != null) {
            lVar.b();
        }
    }

    @Override // a.b.d.h.i
    public ColorStateList getSupportBackgroundTintList() {
        e eVar = this.f262b;
        if (eVar != null) {
            return eVar.b();
        }
        return null;
    }

    @Override // a.b.d.h.i
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        e eVar = this.f262b;
        if (eVar != null) {
            return eVar.c();
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        a.b.e.b.a.o(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        e eVar = this.f262b;
        if (eVar != null) {
            eVar.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        e eVar = this.f262b;
        if (eVar != null) {
            eVar.f(i);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(a.b.a.a.Z(this, callback));
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(a.b.e.c.a.a.b(getContext(), i));
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        e eVar = this.f262b;
        if (eVar != null) {
            eVar.h(colorStateList);
        }
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        e eVar = this.f262b;
        if (eVar != null) {
            eVar.i(mode);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        l lVar = this.f263c;
        if (lVar != null) {
            lVar.e(context, i);
        }
    }
}
