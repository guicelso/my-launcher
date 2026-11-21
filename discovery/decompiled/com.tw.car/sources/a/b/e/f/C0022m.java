package a.b.e.f;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/* JADX INFO: renamed from: a.b.e.f.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0022m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final ImageView f352a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public fa f353b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public fa f354c;
    public fa d;

    public C0022m(ImageView imageView) {
        this.f352a = imageView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a() {
        ColorStateList supportImageTintList;
        PorterDuff.Mode supportImageTintMode;
        Drawable drawable = this.f352a.getDrawable();
        if (drawable != null) {
            A.b(drawable);
        }
        if (drawable != null) {
            int i = Build.VERSION.SDK_INT;
            boolean z = true;
            if (i <= 21 ? i == 21 : this.f353b != null) {
                if (this.d == null) {
                    this.d = new fa();
                }
                fa faVar = this.d;
                faVar.a();
                ImageView imageView = this.f352a;
                if (Build.VERSION.SDK_INT >= 21) {
                    supportImageTintList = imageView.getImageTintList();
                } else {
                    supportImageTintList = imageView instanceof a.b.d.i.i ? ((a.b.d.i.i) imageView).getSupportImageTintList() : null;
                }
                if (supportImageTintList != null) {
                    faVar.d = true;
                    faVar.f329a = supportImageTintList;
                }
                ImageView imageView2 = this.f352a;
                if (Build.VERSION.SDK_INT >= 21) {
                    supportImageTintMode = imageView2.getImageTintMode();
                } else {
                    supportImageTintMode = imageView2 instanceof a.b.d.i.i ? ((a.b.d.i.i) imageView2).getSupportImageTintMode() : null;
                }
                if (supportImageTintMode != null) {
                    faVar.f331c = true;
                    faVar.f330b = supportImageTintMode;
                }
                if (faVar.d || faVar.f331c) {
                    C0019j.a(drawable, faVar, this.f352a.getDrawableState());
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            fa faVar2 = this.f354c;
            if (faVar2 == null && (faVar2 = this.f353b) == null) {
                return;
            }
            C0019j.a(drawable, faVar2, this.f352a.getDrawableState());
        }
    }

    public void a(int i) {
        if (i != 0) {
            Drawable drawableC = a.b.e.c.a.a.c(this.f352a.getContext(), i);
            if (drawableC != null) {
                A.b(drawableC);
            }
            this.f352a.setImageDrawable(drawableC);
        } else {
            this.f352a.setImageDrawable(null);
        }
        a();
    }

    public void a(ColorStateList colorStateList) {
        if (this.f354c == null) {
            this.f354c = new fa();
        }
        fa faVar = this.f354c;
        faVar.f329a = colorStateList;
        faVar.d = true;
        a();
    }

    public void a(PorterDuff.Mode mode) {
        if (this.f354c == null) {
            this.f354c = new fa();
        }
        fa faVar = this.f354c;
        faVar.f330b = mode;
        faVar.f331c = true;
        a();
    }

    public boolean b() {
        return Build.VERSION.SDK_INT < 21 || !(this.f352a.getBackground() instanceof RippleDrawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(AttributeSet attributeSet, int i) {
        int iF;
        ha haVarA = ha.a(this.f352a.getContext(), attributeSet, a.b.e.b.j.AppCompatImageView, i, 0);
        try {
            Drawable drawable = this.f352a.getDrawable();
            if (drawable == null && (iF = haVarA.f(a.b.e.b.j.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = a.b.e.c.a.a.c(this.f352a.getContext(), iF)) != null) {
                this.f352a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                A.b(drawable);
            }
            boolean z = true;
            if (haVarA.e(a.b.e.b.j.AppCompatImageView_tint)) {
                ImageView imageView = this.f352a;
                ColorStateList colorStateListA = haVarA.a(a.b.e.b.j.AppCompatImageView_tint);
                if (Build.VERSION.SDK_INT >= 21) {
                    imageView.setImageTintList(colorStateListA);
                    if (Build.VERSION.SDK_INT == 21) {
                        Drawable drawable2 = imageView.getDrawable();
                        boolean z2 = (imageView.getImageTintList() == null || imageView.getImageTintMode() == null) ? false : true;
                        if (drawable2 != null && z2) {
                            if (drawable2.isStateful()) {
                                drawable2.setState(imageView.getDrawableState());
                            }
                            imageView.setImageDrawable(drawable2);
                        }
                    }
                } else if (imageView instanceof a.b.d.i.i) {
                    ((a.b.d.i.i) imageView).setSupportImageTintList(colorStateListA);
                }
            }
            if (haVarA.e(a.b.e.b.j.AppCompatImageView_tintMode)) {
                ImageView imageView2 = this.f352a;
                PorterDuff.Mode modeA = A.a(haVarA.d(a.b.e.b.j.AppCompatImageView_tintMode, -1), null);
                if (Build.VERSION.SDK_INT >= 21) {
                    imageView2.setImageTintMode(modeA);
                    if (Build.VERSION.SDK_INT == 21) {
                        Drawable drawable3 = imageView2.getDrawable();
                        if (imageView2.getImageTintList() == null || imageView2.getImageTintMode() == null) {
                            z = false;
                        }
                        if (drawable3 != null && z) {
                            if (drawable3.isStateful()) {
                                drawable3.setState(imageView2.getDrawableState());
                            }
                            imageView2.setImageDrawable(drawable3);
                        }
                    }
                } else if (imageView2 instanceof a.b.d.i.i) {
                    ((a.b.d.i.i) imageView2).setSupportImageTintMode(modeA);
                }
            }
        } finally {
            haVarA.f337b.recycle();
        }
    }
}
