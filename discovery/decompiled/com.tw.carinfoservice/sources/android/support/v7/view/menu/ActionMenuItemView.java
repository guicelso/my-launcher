package android.support.v7.view.menu;

import a.b.e.e.c.g;
import a.b.e.e.c.h;
import a.b.e.e.c.n;
import a.b.e.e.c.o;
import a.b.e.f.c;
import a.b.e.f.m;
import a.b.e.f.s;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.widget.ActionMenuView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuItemView extends m implements n.a, View.OnClickListener, ActionMenuView.a {
    public h e;
    public CharSequence f;
    public Drawable g;
    public g.b h;
    public s i;
    public b j;
    public boolean k;
    public boolean l;
    public int m;
    public int n;
    public int o;

    public class a extends s {
        public a() {
            super(ActionMenuItemView.this);
        }

        @Override // a.b.e.f.s
        public o b() {
            c.a aVar;
            b bVar = ActionMenuItemView.this.j;
            if (bVar == null || (aVar = c.this.v) == null) {
                return null;
            }
            return aVar.a();
        }

        @Override // a.b.e.f.s
        public boolean c() {
            o oVarB;
            ActionMenuItemView actionMenuItemView = ActionMenuItemView.this;
            g.b bVar = actionMenuItemView.h;
            return bVar != null && bVar.b(actionMenuItemView.e) && (oVarB = b()) != null && oVarB.c();
        }
    }

    public static abstract class b {
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        Resources resources = context.getResources();
        this.k = e();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.b.e.b.b.f176c, 0, 0);
        this.m = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.o = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.n = -1;
        setSaveEnabled(false);
    }

    @Override // android.support.v7.widget.ActionMenuView.a
    public boolean a() {
        return d() && this.e.getIcon() == null;
    }

    @Override // android.support.v7.widget.ActionMenuView.a
    public boolean b() {
        return d();
    }

    @Override // a.b.e.e.c.n.a
    public void c(h hVar, int i) {
        this.e = hVar;
        setIcon(hVar.getIcon());
        setTitle(hVar.getTitleCondensed());
        setId(hVar.f228a);
        setVisibility(hVar.isVisible() ? 0 : 8);
        setEnabled(hVar.isEnabled());
        if (hVar.hasSubMenu() && this.i == null) {
            this.i = new a();
        }
    }

    public boolean d() {
        return !TextUtils.isEmpty(getText());
    }

    public final boolean e() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    public final void f() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.f);
        if (this.g != null) {
            if (!((this.e.y & 4) == 4) || (!this.k && !this.l)) {
                z = false;
            }
        }
        boolean z3 = z2 & z;
        setText(z3 ? this.f : null);
        CharSequence charSequence = this.e.q;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = z3 ? null : this.e.e;
        }
        setContentDescription(charSequence);
        CharSequence charSequence2 = this.e.r;
        if (TextUtils.isEmpty(charSequence2)) {
            a.b.e.b.a.r(this, z3 ? null : this.e.e);
        } else {
            a.b.e.b.a.r(this, charSequence2);
        }
    }

    @Override // a.b.e.e.c.n.a
    public h getItemData() {
        return this.e;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        g.b bVar = this.h;
        if (bVar != null) {
            bVar.b(this.e);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.k = e();
        f();
    }

    @Override // a.b.e.f.m, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        boolean zD = d();
        if (zD && (i3 = this.n) >= 0) {
            super.setPadding(i3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, this.m) : this.m;
        if (mode != 1073741824 && this.m > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2);
        }
        if (zD || this.g == null) {
            return;
        }
        super.setPadding((getMeasuredWidth() - this.g.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        s sVar;
        if (this.e.hasSubMenu() && (sVar = this.i) != null && sVar.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setCheckable(boolean z) {
    }

    public void setChecked(boolean z) {
    }

    public void setExpandedFormat(boolean z) {
        if (this.l != z) {
            this.l = z;
            h hVar = this.e;
            if (hVar != null) {
                hVar.n.o();
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.g = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i = this.o;
            if (intrinsicWidth > i) {
                intrinsicHeight = (int) (intrinsicHeight * (i / intrinsicWidth));
                intrinsicWidth = i;
            }
            if (intrinsicHeight > i) {
                intrinsicWidth = (int) (intrinsicWidth * (i / intrinsicHeight));
            } else {
                i = intrinsicHeight;
            }
            drawable.setBounds(0, 0, intrinsicWidth, i);
        }
        setCompoundDrawables(drawable, null, null, null);
        f();
    }

    public void setItemInvoker(g.b bVar) {
        this.h = bVar;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        this.n = i;
        super.setPadding(i, i2, i3, i4);
    }

    public void setPopupCallback(b bVar) {
        this.j = bVar;
    }

    public void setTitle(CharSequence charSequence) {
        this.f = charSequence;
        f();
    }
}
