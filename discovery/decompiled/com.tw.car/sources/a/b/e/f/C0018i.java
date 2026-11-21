package a.b.e.f;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: renamed from: a.b.e.f.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0018i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f339a;
    public fa d;
    public fa e;
    public fa f;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f341c = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final C0019j f340b = C0019j.a();

    public C0018i(View view) {
        this.f339a = view;
    }

    public void a() {
        Drawable background = this.f339a.getBackground();
        if (background != null) {
            int i = Build.VERSION.SDK_INT;
            boolean z = true;
            if (i <= 21 ? i == 21 : this.d != null) {
                if (this.f == null) {
                    this.f = new fa();
                }
                fa faVar = this.f;
                faVar.a();
                ColorStateList colorStateListA = a.b.d.h.n.a(this.f339a);
                if (colorStateListA != null) {
                    faVar.d = true;
                    faVar.f329a = colorStateListA;
                }
                PorterDuff.Mode modeB = a.b.d.h.n.b(this.f339a);
                if (modeB != null) {
                    faVar.f331c = true;
                    faVar.f330b = modeB;
                }
                if (faVar.d || faVar.f331c) {
                    C0019j.a(background, faVar, this.f339a.getDrawableState());
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            fa faVar2 = this.e;
            if (faVar2 == null && (faVar2 = this.d) == null) {
                return;
            }
            C0019j.a(background, faVar2, this.f339a.getDrawableState());
        }
    }

    public void a(int i) {
        this.f341c = i;
        C0019j c0019j = this.f340b;
        a(c0019j != null ? c0019j.d(this.f339a.getContext(), i) : null);
        a();
    }

    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new fa();
            }
            fa faVar = this.d;
            faVar.f329a = colorStateList;
            faVar.d = true;
        } else {
            this.d = null;
        }
        a();
    }

    public void a(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new fa();
        }
        fa faVar = this.e;
        faVar.f330b = mode;
        faVar.f331c = true;
        a();
    }

    public ColorStateList b() {
        fa faVar = this.e;
        if (faVar != null) {
            return faVar.f329a;
        }
        return null;
    }

    public void b(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new fa();
        }
        fa faVar = this.e;
        faVar.f329a = colorStateList;
        faVar.d = true;
        a();
    }

    public PorterDuff.Mode c() {
        fa faVar = this.e;
        if (faVar != null) {
            return faVar.f330b;
        }
        return null;
    }

    public void a(AttributeSet attributeSet, int i) {
        ha haVarA = ha.a(this.f339a.getContext(), attributeSet, a.b.e.b.j.ViewBackgroundHelper, i, 0);
        try {
            if (haVarA.e(a.b.e.b.j.ViewBackgroundHelper_android_background)) {
                this.f341c = haVarA.f(a.b.e.b.j.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateListD = this.f340b.d(this.f339a.getContext(), this.f341c);
                if (colorStateListD != null) {
                    a(colorStateListD);
                }
            }
            if (haVarA.e(a.b.e.b.j.ViewBackgroundHelper_backgroundTint)) {
                a.b.d.h.n.a(this.f339a, haVarA.a(a.b.e.b.j.ViewBackgroundHelper_backgroundTint));
            }
            if (haVarA.e(a.b.e.b.j.ViewBackgroundHelper_backgroundTintMode)) {
                a.b.d.h.n.a(this.f339a, A.a(haVarA.d(a.b.e.b.j.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            haVarA.f337b.recycle();
        }
    }
}
