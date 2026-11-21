package a.b.e.f;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public class na implements InterfaceC0034z {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Toolbar f359a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f360b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public View f361c;
    public Drawable d;
    public Drawable e;
    public Drawable f;
    public boolean g;
    public CharSequence h;
    public CharSequence i;
    public CharSequence j;
    public Window.Callback k;
    public boolean l;
    public int m;
    public Drawable n;

    public na(Toolbar toolbar, boolean z) {
        int i;
        Drawable drawable;
        int i2 = a.b.e.b.h.abc_action_bar_up_description;
        int i3 = a.b.e.b.e.abc_ic_ab_back_material;
        this.m = 0;
        this.f359a = toolbar;
        this.h = toolbar.getTitle();
        this.i = toolbar.getSubtitle();
        this.g = this.h != null;
        this.f = toolbar.getNavigationIcon();
        ha haVarA = ha.a(toolbar.getContext(), null, a.b.e.b.j.ActionBar, a.b.e.b.a.actionBarStyle, 0);
        this.n = haVarA.b(a.b.e.b.j.ActionBar_homeAsUpIndicator);
        if (z) {
            CharSequence charSequenceD = haVarA.d(a.b.e.b.j.ActionBar_title);
            if (!TextUtils.isEmpty(charSequenceD)) {
                this.g = true;
                a(charSequenceD);
            }
            CharSequence charSequenceD2 = haVarA.d(a.b.e.b.j.ActionBar_subtitle);
            if (!TextUtils.isEmpty(charSequenceD2)) {
                this.i = charSequenceD2;
                if ((this.f360b & 8) != 0) {
                    this.f359a.setSubtitle(charSequenceD2);
                }
            }
            Drawable drawableB = haVarA.b(a.b.e.b.j.ActionBar_logo);
            if (drawableB != null) {
                a(drawableB);
            }
            Drawable drawableB2 = haVarA.b(a.b.e.b.j.ActionBar_icon);
            if (drawableB2 != null) {
                this.d = drawableB2;
                d();
            }
            if (this.f == null && (drawable = this.n) != null) {
                this.f = drawable;
                c();
            }
            a(haVarA.d(a.b.e.b.j.ActionBar_displayOptions, 0));
            int iF = haVarA.f(a.b.e.b.j.ActionBar_customNavigationLayout, 0);
            if (iF != 0) {
                View viewInflate = LayoutInflater.from(this.f359a.getContext()).inflate(iF, (ViewGroup) this.f359a, false);
                View view = this.f361c;
                if (view != null && (this.f360b & 16) != 0) {
                    this.f359a.removeView(view);
                }
                this.f361c = viewInflate;
                if (viewInflate != null && (this.f360b & 16) != 0) {
                    this.f359a.addView(this.f361c);
                }
                a(this.f360b | 16);
            }
            int iE = haVarA.e(a.b.e.b.j.ActionBar_height, 0);
            if (iE > 0) {
                ViewGroup.LayoutParams layoutParams = this.f359a.getLayoutParams();
                layoutParams.height = iE;
                this.f359a.setLayoutParams(layoutParams);
            }
            int iB = haVarA.b(a.b.e.b.j.ActionBar_contentInsetStart, -1);
            int iB2 = haVarA.b(a.b.e.b.j.ActionBar_contentInsetEnd, -1);
            if (iB >= 0 || iB2 >= 0) {
                this.f359a.a(Math.max(iB, 0), Math.max(iB2, 0));
            }
            int iF2 = haVarA.f(a.b.e.b.j.ActionBar_titleTextStyle, 0);
            if (iF2 != 0) {
                Toolbar toolbar2 = this.f359a;
                toolbar2.b(toolbar2.getContext(), iF2);
            }
            int iF3 = haVarA.f(a.b.e.b.j.ActionBar_subtitleTextStyle, 0);
            if (iF3 != 0) {
                Toolbar toolbar3 = this.f359a;
                toolbar3.a(toolbar3.getContext(), iF3);
            }
            int iF4 = haVarA.f(a.b.e.b.j.ActionBar_popupTheme, 0);
            if (iF4 != 0) {
                this.f359a.setPopupTheme(iF4);
            }
        } else {
            if (this.f359a.getNavigationIcon() != null) {
                i = 15;
                this.n = this.f359a.getNavigationIcon();
            } else {
                i = 11;
            }
            this.f360b = i;
        }
        haVarA.f337b.recycle();
        if (i2 != this.m) {
            this.m = i2;
            if (TextUtils.isEmpty(this.f359a.getNavigationContentDescription())) {
                int i4 = this.m;
                this.j = i4 != 0 ? a().getString(i4) : null;
                b();
            }
        }
        this.j = this.f359a.getNavigationContentDescription();
        this.f359a.setNavigationOnClickListener(new ma(this));
    }

    public Context a() {
        return this.f359a.getContext();
    }

    public void a(int i) {
        View view;
        CharSequence charSequence;
        Toolbar toolbar;
        int i2 = this.f360b ^ i;
        this.f360b = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    b();
                }
                c();
            }
            if ((i2 & 3) != 0) {
                d();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    this.f359a.setTitle(this.h);
                    toolbar = this.f359a;
                    charSequence = this.i;
                } else {
                    charSequence = null;
                    this.f359a.setTitle((CharSequence) null);
                    toolbar = this.f359a;
                }
                toolbar.setSubtitle(charSequence);
            }
            if ((i2 & 16) == 0 || (view = this.f361c) == null) {
                return;
            }
            if ((i & 16) != 0) {
                this.f359a.addView(view);
            } else {
                this.f359a.removeView(view);
            }
        }
    }

    public void a(Drawable drawable) {
        this.e = drawable;
        d();
    }

    public final void a(CharSequence charSequence) {
        this.h = charSequence;
        if ((this.f360b & 8) != 0) {
            this.f359a.setTitle(charSequence);
        }
    }

    public final void b() {
        if ((this.f360b & 4) != 0) {
            if (TextUtils.isEmpty(this.j)) {
                this.f359a.setNavigationContentDescription(this.m);
            } else {
                this.f359a.setNavigationContentDescription(this.j);
            }
        }
    }

    public final void c() {
        Toolbar toolbar;
        Drawable drawable;
        if ((this.f360b & 4) != 0) {
            toolbar = this.f359a;
            drawable = this.f;
            if (drawable == null) {
                drawable = this.n;
            }
        } else {
            toolbar = this.f359a;
            drawable = null;
        }
        toolbar.setNavigationIcon(drawable);
    }

    public final void d() {
        Drawable drawable;
        int i = this.f360b;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) == 0 || (drawable = this.e) == null) {
            drawable = this.d;
        }
        this.f359a.setLogo(drawable);
    }
}
