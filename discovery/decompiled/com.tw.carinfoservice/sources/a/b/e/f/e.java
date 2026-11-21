package a.b.e.f;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f267a;
    public e0 d;
    public e0 e;
    public e0 f;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f269c = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final f f268b = f.g();

    public e(View view) {
        this.f267a = view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a() {
        ColorStateList supportBackgroundTintList;
        Drawable background = this.f267a.getBackground();
        if (background != null) {
            int i = Build.VERSION.SDK_INT;
            boolean z = true;
            if (i <= 21 ? i == 21 : this.d != null) {
                if (this.f == null) {
                    this.f = new e0();
                }
                e0 e0Var = this.f;
                PorterDuff.Mode supportBackgroundTintMode = null;
                e0Var.f270a = null;
                e0Var.d = false;
                e0Var.f271b = null;
                e0Var.f272c = false;
                View view = this.f267a;
                int i2 = a.b.d.h.j.f151a;
                if (i >= 21) {
                    supportBackgroundTintList = view.getBackgroundTintList();
                } else {
                    supportBackgroundTintList = view instanceof a.b.d.h.i ? ((a.b.d.h.i) view).getSupportBackgroundTintList() : null;
                }
                if (supportBackgroundTintList != null) {
                    e0Var.d = true;
                    e0Var.f270a = supportBackgroundTintList;
                }
                View view2 = this.f267a;
                if (i >= 21) {
                    supportBackgroundTintMode = view2.getBackgroundTintMode();
                } else if (view2 instanceof a.b.d.h.i) {
                    supportBackgroundTintMode = ((a.b.d.h.i) view2).getSupportBackgroundTintMode();
                }
                if (supportBackgroundTintMode != null) {
                    e0Var.f272c = true;
                    e0Var.f271b = supportBackgroundTintMode;
                }
                if (e0Var.d || e0Var.f272c) {
                    f.p(background, e0Var, this.f267a.getDrawableState());
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            e0 e0Var2 = this.e;
            if (e0Var2 != null) {
                f.p(background, e0Var2, this.f267a.getDrawableState());
                return;
            }
            e0 e0Var3 = this.d;
            if (e0Var3 != null) {
                f.p(background, e0Var3, this.f267a.getDrawableState());
            }
        }
    }

    public ColorStateList b() {
        e0 e0Var = this.e;
        if (e0Var != null) {
            return e0Var.f270a;
        }
        return null;
    }

    public PorterDuff.Mode c() {
        e0 e0Var = this.e;
        if (e0Var != null) {
            return e0Var.f271b;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void d(AttributeSet attributeSet, int i) {
        int i2 = Build.VERSION.SDK_INT;
        g0 g0VarM = g0.m(this.f267a.getContext(), attributeSet, a.b.e.b.b.x, i, 0);
        try {
            if (g0VarM.l(0)) {
                this.f269c = g0VarM.j(0, -1);
                ColorStateList colorStateListL = this.f268b.l(this.f267a.getContext(), this.f269c);
                if (colorStateListL != null) {
                    g(colorStateListL);
                }
            }
            if (g0VarM.l(1)) {
                View view = this.f267a;
                ColorStateList colorStateListC = g0VarM.c(1);
                int i3 = a.b.d.h.j.f151a;
                if (i2 >= 21) {
                    view.setBackgroundTintList(colorStateListC);
                    if (i2 == 21) {
                        Drawable background = view.getBackground();
                        boolean z = (view.getBackgroundTintList() == null && view.getBackgroundTintMode() == null) ? false : true;
                        if (background != null && z) {
                            if (background.isStateful()) {
                                background.setState(view.getDrawableState());
                            }
                            view.setBackground(background);
                        }
                    }
                } else if (view instanceof a.b.d.h.i) {
                    ((a.b.d.h.i) view).setSupportBackgroundTintList(colorStateListC);
                }
            }
            if (g0VarM.l(2)) {
                View view2 = this.f267a;
                PorterDuff.Mode modeC = p.c(g0VarM.h(2, -1), null);
                int i4 = a.b.d.h.j.f151a;
                if (i2 >= 21) {
                    view2.setBackgroundTintMode(modeC);
                    if (i2 == 21) {
                        Drawable background2 = view2.getBackground();
                        boolean z2 = (view2.getBackgroundTintList() == null && view2.getBackgroundTintMode() == null) ? false : true;
                        if (background2 != null && z2) {
                            if (background2.isStateful()) {
                                background2.setState(view2.getDrawableState());
                            }
                            view2.setBackground(background2);
                        }
                    }
                } else if (view2 instanceof a.b.d.h.i) {
                    ((a.b.d.h.i) view2).setSupportBackgroundTintMode(modeC);
                }
            }
        } finally {
            g0VarM.f278b.recycle();
        }
    }

    public void e() {
        this.f269c = -1;
        g(null);
        a();
    }

    public void f(int i) {
        this.f269c = i;
        f fVar = this.f268b;
        g(fVar != null ? fVar.l(this.f267a.getContext(), i) : null);
        a();
    }

    public void g(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new e0();
            }
            e0 e0Var = this.d;
            e0Var.f270a = colorStateList;
            e0Var.d = true;
        } else {
            this.d = null;
        }
        a();
    }

    public void h(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new e0();
        }
        e0 e0Var = this.e;
        e0Var.f270a = colorStateList;
        e0Var.d = true;
        a();
    }

    public void i(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new e0();
        }
        e0 e0Var = this.e;
        e0Var.f271b = mode;
        e0Var.f272c = true;
        a();
    }
}
