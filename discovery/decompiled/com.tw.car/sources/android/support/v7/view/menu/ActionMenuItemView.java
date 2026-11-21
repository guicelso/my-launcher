package android.support.v7.view.menu;

import a.b.b.a.e;
import a.b.e.b.j;
import a.b.e.e.a.k;
import a.b.e.e.a.n;
import a.b.e.e.a.q;
import a.b.e.e.a.u;
import a.b.e.e.a.w;
import a.b.e.f.C0015f;
import a.b.e.f.C0031w;
import a.b.e.f.D;
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
public class ActionMenuItemView extends C0031w implements u.a, View.OnClickListener, ActionMenuView.a {
    public n d;
    public CharSequence e;
    public Drawable f;
    public k.b g;
    public D h;
    public b i;
    public boolean j;
    public boolean k;
    public int l;
    public int m;
    public int mMinWidth;

    private class a extends D {
        public a() {
            super(ActionMenuItemView.this);
        }

        @Override // a.b.e.f.D
        public w b() {
            C0015f.a aVar;
            b bVar = ActionMenuItemView.this.i;
            if (bVar == null || (aVar = C0015f.this.x) == null) {
                return null;
            }
            return aVar.b();
        }

        @Override // a.b.e.f.D
        public boolean c() {
            C0015f.a aVar;
            ActionMenuItemView actionMenuItemView = ActionMenuItemView.this;
            k.b bVar = actionMenuItemView.g;
            if (bVar == null || !bVar.a(actionMenuItemView.d)) {
                return false;
            }
            b bVar2 = ActionMenuItemView.this.i;
            q qVarB = null;
            if (bVar2 != null && (aVar = C0015f.this.x) != null) {
                qVarB = aVar.b();
            }
            return qVarB != null && qVarB.isShowing();
        }
    }

    public static abstract class b {
    }

    public ActionMenuItemView(Context context) {
        this(context, null, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = context.getResources();
        this.j = b();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, j.ActionMenuItemView, i, 0);
        this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(j.ActionMenuItemView_android_minWidth, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.m = (int) ((resources.getDisplayMetrics().density * 32.0f) + 0.5f);
        setOnClickListener(this);
        this.l = -1;
        setSaveEnabled(false);
    }

    @Override // a.b.e.e.a.u.a
    public void a(n nVar, int i) {
        this.d = nVar;
        setIcon(nVar.getIcon());
        setTitle(nVar.a(this));
        setId(nVar.f232a);
        setVisibility(nVar.isVisible() ? 0 : 8);
        setEnabled(nVar.isEnabled());
        if (nVar.hasSubMenu() && this.h == null) {
            this.h = new a();
        }
    }

    public boolean a() {
        return !TextUtils.isEmpty(getText());
    }

    public final boolean b() {
        Configuration configuration = getContext().getResources().getConfiguration();
        int i = configuration.screenWidthDp;
        return i >= 480 || (i >= 640 && configuration.screenHeightDp >= 480) || configuration.orientation == 2;
    }

    public final void c() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.e);
        if (this.f != null) {
            if (!((this.d.z & 4) == 4) || (!this.j && !this.k)) {
                z = false;
            }
        }
        boolean z3 = z2 & z;
        setText(z3 ? this.e : null);
        CharSequence charSequence = this.d.r;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = z3 ? null : this.d.e;
        }
        setContentDescription(charSequence);
        CharSequence charSequence2 = this.d.s;
        if (TextUtils.isEmpty(charSequence2)) {
            e.a(this, z3 ? null : this.d.e);
        } else {
            e.a(this, charSequence2);
        }
    }

    @Override // a.b.e.e.a.u.a
    public n getItemData() {
        return this.d;
    }

    @Override // android.support.v7.widget.ActionMenuView.a
    public boolean needsDividerAfter() {
        return a();
    }

    @Override // android.support.v7.widget.ActionMenuView.a
    public boolean needsDividerBefore() {
        return a() && this.d.getIcon() == null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        k.b bVar = this.g;
        if (bVar != null) {
            bVar.a(this.d);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.j = b();
        c();
    }

    @Override // a.b.e.f.C0031w, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        boolean zA = a();
        if (zA && (i3 = this.l) >= 0) {
            super.setPadding(i3, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int measuredWidth = getMeasuredWidth();
        int iMin = mode == Integer.MIN_VALUE ? Math.min(size, this.mMinWidth) : this.mMinWidth;
        if (mode != 1073741824 && this.mMinWidth > 0 && measuredWidth < iMin) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMin, 1073741824), i2);
        }
        if (zA || this.f == null) {
            return;
        }
        super.setPadding((getMeasuredWidth() - this.f.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(null);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        D d;
        if (this.d.hasSubMenu() && (d = this.h) != null && d.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // a.b.e.e.a.u.a
    public boolean prefersCondensedTitle() {
        return true;
    }

    public void setCheckable(boolean z) {
    }

    public void setChecked(boolean z) {
    }

    public void setExpandedFormat(boolean z) {
        if (this.k != z) {
            this.k = z;
            n nVar = this.d;
            if (nVar != null) {
                k kVar = nVar.n;
                kVar.l = true;
                kVar.b(true);
            }
        }
    }

    public void setIcon(Drawable drawable) {
        this.f = drawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int i = this.m;
            if (intrinsicWidth > i) {
                intrinsicHeight = (int) (intrinsicHeight * (i / intrinsicWidth));
                intrinsicWidth = i;
            }
            int i2 = this.m;
            if (intrinsicHeight > i2) {
                intrinsicWidth = (int) (intrinsicWidth * (i2 / intrinsicHeight));
                intrinsicHeight = i2;
            }
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        }
        setCompoundDrawables(drawable, null, null, null);
        c();
    }

    public void setItemInvoker(k.b bVar) {
        this.g = bVar;
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        this.l = i;
        super.setPadding(i, i2, i3, i4);
    }

    public void setPopupCallback(b bVar) {
        this.i = bVar;
    }

    public void setTitle(CharSequence charSequence) {
        this.e = charSequence;
        c();
    }
}
