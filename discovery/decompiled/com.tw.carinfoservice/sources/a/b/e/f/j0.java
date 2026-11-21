package a.b.e.f;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.tw.carinfoservice.R;

/* JADX INFO: loaded from: classes.dex */
public class j0 implements o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Toolbar f290a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f291b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public View f292c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public boolean g;
    public CharSequence h;
    public CharSequence i;
    public CharSequence j;
    public Window.Callback k;
    public int l;
    public Drawable m;

    public j0(Toolbar toolbar, boolean z) {
        Drawable drawable;
        this.l = 0;
        this.f290a = toolbar;
        this.h = toolbar.getTitle();
        this.i = toolbar.getSubtitle();
        this.g = this.h != null;
        this.f = toolbar.getNavigationIcon();
        g0 g0VarM = g0.m(toolbar.getContext(), null, a.b.e.b.b.f174a, R.attr.actionBarStyle, 0);
        int i = 15;
        this.m = g0VarM.f(15);
        if (z) {
            CharSequence charSequenceK = g0VarM.k(27);
            if (!TextUtils.isEmpty(charSequenceK)) {
                this.g = true;
                this.h = charSequenceK;
                if ((this.f291b & 8) != 0) {
                    this.f290a.setTitle(charSequenceK);
                }
            }
            CharSequence charSequenceK2 = g0VarM.k(25);
            if (!TextUtils.isEmpty(charSequenceK2)) {
                this.i = charSequenceK2;
                if ((this.f291b & 8) != 0) {
                    this.f290a.setSubtitle(charSequenceK2);
                }
            }
            Drawable drawableF = g0VarM.f(20);
            if (drawableF != null) {
                this.e = drawableF;
                h();
            }
            Drawable drawableF2 = g0VarM.f(17);
            if (drawableF2 != null) {
                this.d = drawableF2;
                h();
            }
            if (this.f == null && (drawable = this.m) != null) {
                this.f = drawable;
                g();
            }
            e(g0VarM.h(10, 0));
            int iJ = g0VarM.j(9, 0);
            if (iJ != 0) {
                View viewInflate = LayoutInflater.from(this.f290a.getContext()).inflate(iJ, (ViewGroup) this.f290a, false);
                View view = this.f292c;
                if (view != null && (this.f291b & 16) != 0) {
                    this.f290a.removeView(view);
                }
                this.f292c = viewInflate;
                if (viewInflate != null && (this.f291b & 16) != 0) {
                    this.f290a.addView(viewInflate);
                }
                e(this.f291b | 16);
            }
            int i2 = g0VarM.i(13, 0);
            if (i2 > 0) {
                ViewGroup.LayoutParams layoutParams = this.f290a.getLayoutParams();
                layoutParams.height = i2;
                this.f290a.setLayoutParams(layoutParams);
            }
            int iD = g0VarM.d(7, -1);
            int iD2 = g0VarM.d(3, -1);
            if (iD >= 0 || iD2 >= 0) {
                Toolbar toolbar2 = this.f290a;
                int iMax = Math.max(iD, 0);
                int iMax2 = Math.max(iD2, 0);
                toolbar2.c();
                toolbar2.u.a(iMax, iMax2);
            }
            int iJ2 = g0VarM.j(28, 0);
            if (iJ2 != 0) {
                Toolbar toolbar3 = this.f290a;
                Context context = toolbar3.getContext();
                toolbar3.m = iJ2;
                TextView textView = toolbar3.f381c;
                if (textView != null) {
                    textView.setTextAppearance(context, iJ2);
                }
            }
            int iJ3 = g0VarM.j(26, 0);
            if (iJ3 != 0) {
                Toolbar toolbar4 = this.f290a;
                Context context2 = toolbar4.getContext();
                toolbar4.n = iJ3;
                TextView textView2 = toolbar4.d;
                if (textView2 != null) {
                    textView2.setTextAppearance(context2, iJ3);
                }
            }
            int iJ4 = g0VarM.j(22, 0);
            if (iJ4 != 0) {
                this.f290a.setPopupTheme(iJ4);
            }
        } else {
            if (this.f290a.getNavigationIcon() != null) {
                this.m = this.f290a.getNavigationIcon();
            } else {
                i = 11;
            }
            this.f291b = i;
        }
        g0VarM.f278b.recycle();
        if (R.string.abc_action_bar_up_description != this.l) {
            this.l = R.string.abc_action_bar_up_description;
            if (TextUtils.isEmpty(this.f290a.getNavigationContentDescription())) {
                int i3 = this.l;
                this.j = i3 != 0 ? d().getString(i3) : null;
                f();
            }
        }
        this.j = this.f290a.getNavigationContentDescription();
        this.f290a.setNavigationOnClickListener(new i0(this));
    }

    @Override // a.b.e.f.o
    public void a(int i) {
        this.e = i != 0 ? a.b.e.c.a.a.b(d(), i) : null;
        h();
    }

    @Override // a.b.e.f.o
    public void b(CharSequence charSequence) {
        if (this.g) {
            return;
        }
        this.h = charSequence;
        if ((this.f291b & 8) != 0) {
            this.f290a.setTitle(charSequence);
        }
    }

    @Override // a.b.e.f.o
    public void c(Window.Callback callback) {
        this.k = callback;
    }

    public Context d() {
        return this.f290a.getContext();
    }

    public void e(int i) {
        View view;
        CharSequence charSequence;
        Toolbar toolbar;
        int i2 = this.f291b ^ i;
        this.f291b = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    f();
                }
                g();
            }
            if ((i2 & 3) != 0) {
                h();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.f290a.setTitle(this.h);
                    toolbar = this.f290a;
                    charSequence = this.i;
                } else {
                    charSequence = null;
                    this.f290a.setTitle((CharSequence) null);
                    toolbar = this.f290a;
                }
                toolbar.setSubtitle(charSequence);
            }
            if ((i2 & 16) == 0 || (view = this.f292c) == null) {
                return;
            }
            if ((i & 16) != 0) {
                this.f290a.addView(view);
            } else {
                this.f290a.removeView(view);
            }
        }
    }

    public final void f() {
        if ((this.f291b & 4) != 0) {
            if (TextUtils.isEmpty(this.j)) {
                this.f290a.setNavigationContentDescription(this.l);
            } else {
                this.f290a.setNavigationContentDescription(this.j);
            }
        }
    }

    public final void g() {
        Toolbar toolbar;
        Drawable drawable;
        if ((this.f291b & 4) != 0) {
            toolbar = this.f290a;
            drawable = this.f;
            if (drawable == null) {
                drawable = this.m;
            }
        } else {
            toolbar = this.f290a;
            drawable = null;
        }
        toolbar.setNavigationIcon(drawable);
    }

    @Override // a.b.e.f.o
    public CharSequence getTitle() {
        return this.f290a.getTitle();
    }

    public final void h() {
        Drawable drawable;
        int i = this.f291b;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) == 0 || (drawable = this.e) == null) {
            drawable = this.d;
        }
        this.f290a.setLogo(drawable);
    }

    @Override // a.b.e.f.o
    public void setIcon(int i) {
        this.d = i != 0 ? a.b.e.c.a.a.b(d(), i) : null;
        h();
    }

    @Override // a.b.e.f.o
    public void setIcon(Drawable drawable) {
        this.d = drawable;
        h();
    }
}
