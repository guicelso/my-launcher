package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* JADX INFO: renamed from: a.b.e.f.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0021l extends ImageButton implements a.b.d.h.m, a.b.d.i.i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0018i f350a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0022m f351b;

    public C0021l(Context context, AttributeSet attributeSet, int i) {
        super(ea.a(context), attributeSet, i);
        this.f350a = new C0018i(this);
        this.f350a.a(attributeSet, i);
        this.f351b = new C0022m(this);
        this.f351b.a(attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            c0018i.a();
        }
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a();
        }
    }

    @Override // a.b.d.h.m
    public ColorStateList getSupportBackgroundTintList() {
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            return c0018i.b();
        }
        return null;
    }

    @Override // a.b.d.h.m
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            return c0018i.c();
        }
        return null;
    }

    @Override // a.b.d.i.i
    public ColorStateList getSupportImageTintList() {
        fa faVar;
        C0022m c0022m = this.f351b;
        if (c0022m == null || (faVar = c0022m.f354c) == null) {
            return null;
        }
        return faVar.f329a;
    }

    @Override // a.b.d.i.i
    public PorterDuff.Mode getSupportImageTintMode() {
        fa faVar;
        C0022m c0022m = this.f351b;
        if (c0022m == null || (faVar = c0022m.f354c) == null) {
            return null;
        }
        return faVar.f330b;
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return this.f351b.b() && super.hasOverlappingRendering();
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            c0018i.f341c = -1;
            c0018i.a((ColorStateList) null);
            c0018i.a();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            c0018i.a(i);
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        this.f351b.a(i);
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a();
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            c0018i.b(colorStateList);
        }
    }

    @Override // a.b.d.h.m
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        C0018i c0018i = this.f350a;
        if (c0018i != null) {
            c0018i.a(mode);
        }
    }

    @Override // a.b.d.i.i
    public void setSupportImageTintList(ColorStateList colorStateList) {
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a(colorStateList);
        }
    }

    @Override // a.b.d.i.i
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        C0022m c0022m = this.f351b;
        if (c0022m != null) {
            c0022m.a(mode);
        }
    }
}
