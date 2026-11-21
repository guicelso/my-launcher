package a.b.e.f;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageButton;

/* JADX INFO: loaded from: classes.dex */
public class h extends ImageButton implements a.b.d.h.i, a.b.d.i.h {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final e f280b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final i f281c;

    public h(Context context, AttributeSet attributeSet, int i) {
        super(d0.a(context), null, i);
        e eVar = new e(this);
        this.f280b = eVar;
        eVar.d(null, i);
        i iVar = new i(this);
        this.f281c = iVar;
        iVar.c(null, i);
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        e eVar = this.f280b;
        if (eVar != null) {
            eVar.a();
        }
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.a();
        }
    }

    @Override // a.b.d.h.i
    public ColorStateList getSupportBackgroundTintList() {
        e eVar = this.f280b;
        if (eVar != null) {
            return eVar.b();
        }
        return null;
    }

    @Override // a.b.d.h.i
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        e eVar = this.f280b;
        if (eVar != null) {
            return eVar.c();
        }
        return null;
    }

    @Override // a.b.d.i.h
    public ColorStateList getSupportImageTintList() {
        e0 e0Var;
        i iVar = this.f281c;
        if (iVar == null || (e0Var = iVar.f284b) == null) {
            return null;
        }
        return e0Var.f270a;
    }

    @Override // a.b.d.i.h
    public PorterDuff.Mode getSupportImageTintMode() {
        e0 e0Var;
        i iVar = this.f281c;
        if (iVar == null || (e0Var = iVar.f284b) == null) {
            return null;
        }
        return e0Var.f271b;
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return this.f281c.b() && super.hasOverlappingRendering();
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        e eVar = this.f280b;
        if (eVar != null) {
            eVar.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        e eVar = this.f280b;
        if (eVar != null) {
            eVar.f(i);
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        this.f281c.d(i);
    }

    @Override // android.widget.ImageView
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.a();
        }
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        e eVar = this.f280b;
        if (eVar != null) {
            eVar.h(colorStateList);
        }
    }

    @Override // a.b.d.h.i
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        e eVar = this.f280b;
        if (eVar != null) {
            eVar.i(mode);
        }
    }

    @Override // a.b.d.i.h
    public void setSupportImageTintList(ColorStateList colorStateList) {
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.e(colorStateList);
        }
    }

    @Override // a.b.d.i.h
    public void setSupportImageTintMode(PorterDuff.Mode mode) {
        i iVar = this.f281c;
        if (iVar != null) {
            iVar.f(mode);
        }
    }
}
