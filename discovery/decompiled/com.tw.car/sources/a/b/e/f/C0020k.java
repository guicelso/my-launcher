package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/* JADX INFO: renamed from: a.b.e.f.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0020k extends EditText implements a.b.d.h.m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0018i f347a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0030v f348b;

    @Override // android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            c0018i.a();
        }
        C0030v c0030v = this.f348b;
        if (c0030v != null) {
            c0030v.a();
        }
    }

    @Override // a.b.d.h.m
    public ColorStateList getSupportBackgroundTintList() {
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            return c0018i.b();
        }
        return null;
    }

    @Override // a.b.d.h.m
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            return c0018i.c();
        }
        return null;
    }

    @Override // android.widget.EditText, android.widget.TextView
    public Editable getText() {
        return Build.VERSION.SDK_INT >= 28 ? super.getText() : super.getEditableText();
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
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            c0018i.f341c = -1;
            c0018i.a((ColorStateList) null);
            c0018i.a();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            c0018i.a(i);
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(a.b.b.a.e.a(this, callback));
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            c0018i.b(colorStateList);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0018i c0018i = this.f347a;
        if (c0018i != null) {
            c0018i.a(mode);
        }
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        C0030v c0030v = this.f348b;
        if (c0030v != null) {
            c0030v.a(context, i);
        }
    }
}
