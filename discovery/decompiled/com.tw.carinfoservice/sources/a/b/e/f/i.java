package a.b.e.f;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ImageView f283a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public e0 f284b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public e0 f285c;

    public i(ImageView imageView) {
        this.f283a = imageView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a() {
        ColorStateList supportImageTintList;
        Drawable drawable = this.f283a.getDrawable();
        if (drawable != null) {
            p.b(drawable);
        }
        if (drawable != null) {
            int i = Build.VERSION.SDK_INT;
            boolean z = true;
            if (i <= 21 && i == 21) {
                if (this.f285c == null) {
                    this.f285c = new e0();
                }
                e0 e0Var = this.f285c;
                PorterDuff.Mode supportImageTintMode = null;
                e0Var.f270a = null;
                e0Var.d = false;
                e0Var.f271b = null;
                e0Var.f272c = false;
                ImageView imageView = this.f283a;
                if (i >= 21) {
                    supportImageTintList = imageView.getImageTintList();
                } else {
                    supportImageTintList = imageView instanceof a.b.d.i.h ? ((a.b.d.i.h) imageView).getSupportImageTintList() : null;
                }
                if (supportImageTintList != null) {
                    e0Var.d = true;
                    e0Var.f270a = supportImageTintList;
                }
                ImageView imageView2 = this.f283a;
                if (i >= 21) {
                    supportImageTintMode = imageView2.getImageTintMode();
                } else if (imageView2 instanceof a.b.d.i.h) {
                    supportImageTintMode = ((a.b.d.i.h) imageView2).getSupportImageTintMode();
                }
                if (supportImageTintMode != null) {
                    e0Var.f272c = true;
                    e0Var.f271b = supportImageTintMode;
                }
                if (e0Var.d || e0Var.f272c) {
                    f.p(drawable, e0Var, this.f283a.getDrawableState());
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            e0 e0Var2 = this.f284b;
            if (e0Var2 != null) {
                f.p(drawable, e0Var2, this.f283a.getDrawableState());
            }
        }
    }

    public boolean b() {
        return Build.VERSION.SDK_INT < 21 || !(this.f283a.getBackground() instanceof RippleDrawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void c(AttributeSet attributeSet, int i) {
        int iJ;
        int i2 = Build.VERSION.SDK_INT;
        boolean z = false;
        g0 g0VarM = g0.m(this.f283a.getContext(), attributeSet, a.b.e.b.b.h, i, 0);
        try {
            Drawable drawable = this.f283a.getDrawable();
            if (drawable == null && (iJ = g0VarM.j(1, -1)) != -1 && (drawable = a.b.e.c.a.a.b(this.f283a.getContext(), iJ)) != null) {
                this.f283a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                p.b(drawable);
            }
            if (g0VarM.l(2)) {
                ImageView imageView = this.f283a;
                ColorStateList colorStateListC = g0VarM.c(2);
                if (i2 >= 21) {
                    imageView.setImageTintList(colorStateListC);
                    if (i2 == 21) {
                        Drawable drawable2 = imageView.getDrawable();
                        boolean z2 = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true;
                        if (drawable2 != null && z2) {
                            if (drawable2.isStateful()) {
                                drawable2.setState(imageView.getDrawableState());
                            }
                            imageView.setImageDrawable(drawable2);
                        }
                    }
                } else if (imageView instanceof a.b.d.i.h) {
                    ((a.b.d.i.h) imageView).setSupportImageTintList(colorStateListC);
                }
            }
            if (g0VarM.l(3)) {
                ImageView imageView2 = this.f283a;
                PorterDuff.Mode modeC = p.c(g0VarM.h(3, -1), null);
                if (i2 >= 21) {
                    imageView2.setImageTintMode(modeC);
                    if (i2 == 21) {
                        Drawable drawable3 = imageView2.getDrawable();
                        if (imageView2.getImageTintList() != null && imageView2.getImageTintMode() != null) {
                            z = true;
                        }
                        if (drawable3 != null && z) {
                            if (drawable3.isStateful()) {
                                drawable3.setState(imageView2.getDrawableState());
                            }
                            imageView2.setImageDrawable(drawable3);
                        }
                    }
                } else if (imageView2 instanceof a.b.d.i.h) {
                    ((a.b.d.i.h) imageView2).setSupportImageTintMode(modeC);
                }
            }
        } finally {
            g0VarM.f278b.recycle();
        }
    }

    public void d(int i) {
        if (i != 0) {
            Drawable drawableB = a.b.e.c.a.a.b(this.f283a.getContext(), i);
            if (drawableB != null) {
                p.b(drawableB);
            }
            this.f283a.setImageDrawable(drawableB);
        } else {
            this.f283a.setImageDrawable(null);
        }
        a();
    }

    public void e(ColorStateList colorStateList) {
        if (this.f284b == null) {
            this.f284b = new e0();
        }
        e0 e0Var = this.f284b;
        e0Var.f270a = colorStateList;
        e0Var.d = true;
        a();
    }

    public void f(PorterDuff.Mode mode) {
        if (this.f284b == null) {
            this.f284b = new e0();
        }
        e0 e0Var = this.f284b;
        e0Var.f271b = mode;
        e0Var.f272c = true;
        a();
    }
}
