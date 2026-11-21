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

/* JADX INFO: renamed from: a.b.e.f.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0017h extends AutoCompleteTextView implements a.b.d.h.m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final int[] f333a = {R.attr.popupBackground};

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0018i f334b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final C0030v f335c;

    public C0017h(Context context) {
        this(context, null, a.b.e.b.a.autoCompleteTextViewStyle);
    }

    public C0017h(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.b.e.b.a.autoCompleteTextViewStyle);
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            c0018i.a();
        }
        C0030v c0030v = this.f335c;
        if (c0030v != null) {
            c0030v.a();
        }
    }

    @Override // a.b.d.h.m
    public ColorStateList getSupportBackgroundTintList() {
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            return c0018i.b();
        }
        return null;
    }

    @Override // a.b.d.h.m
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            return c0018i.c();
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection inputConnectionOnCreateInputConnection = super.onCreateInputConnection(editorInfo);
        a.b.b.a.e.a(inputConnectionOnCreateInputConnection, editorInfo, this);
        return inputConnectionOnCreateInputConnection;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            c0018i.f341c = -1;
            c0018i.a((ColorStateList) null);
            c0018i.a();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            c0018i.a(i);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(a.b.b.a.e.a(this, callback));
    }

    @Override // android.widget.AutoCompleteTextView
    public void setDropDownBackgroundResource(int i) {
        setDropDownBackgroundDrawable(a.b.e.c.a.a.c(getContext(), i));
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            c0018i.b(colorStateList);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0018i c0018i = this.f334b;
        if (c0018i != null) {
            c0018i.a(mode);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0030v c0030v = this.f335c;
        if (c0030v != null) {
            c0030v.a(context, i);
        }
    }

    public C0017h(Context context, AttributeSet attributeSet, int i) {
        super(ea.a(context), attributeSet, i);
        ha haVarA = ha.a(getContext(), attributeSet, f333a, i, 0);
        if (haVarA.e(0)) {
            setDropDownBackgroundDrawable(haVarA.b(0));
        }
        haVarA.f337b.recycle();
        this.f334b = new C0018i(this);
        this.f334b.a(attributeSet, i);
        this.f335c = new C0030v(this);
        this.f335c.a(attributeSet, i);
        this.f335c.a();
    }
}
