package android.support.v7.widget;

import a.b.b.a.e;
import a.b.e.b.j;
import a.b.e.e.a.A;
import a.b.e.e.a.k;
import a.b.e.e.a.n;
import a.b.e.e.a.t;
import a.b.e.f.C0015f;
import a.b.e.f.C0021l;
import a.b.e.f.C0023n;
import a.b.e.f.C0031w;
import a.b.e.f.InterfaceC0034z;
import a.b.e.f.L;
import a.b.e.f.ha;
import a.b.e.f.ia;
import a.b.e.f.ja;
import a.b.e.f.ka;
import a.b.e.f.la;
import a.b.e.f.na;
import a.b.e.f.ta;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    public int A;
    public boolean B;
    public boolean C;
    public final ArrayList<View> D;
    public final ArrayList<View> E;
    public final int[] F;
    public final ActionMenuView.e G;
    public na H;
    public C0015f I;
    public a J;
    public t.a K;
    public k.a L;
    public boolean M;
    public final Runnable N;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ActionMenuView f428a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public TextView f429b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TextView f430c;
    public ImageButton d;
    public ImageView e;
    public Drawable f;
    public CharSequence g;
    public ImageButton h;
    public View i;
    public Context j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public L t;
    public int u;
    public int v;
    public int w;
    public CharSequence x;
    public CharSequence y;
    public int z;

    private class a implements t {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public k f431a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public n f432b;

        public a() {
        }

        @Override // a.b.e.e.a.t
        public void a(k kVar, boolean z) {
        }

        @Override // a.b.e.e.a.t
        public void a(Context context, k kVar) {
            n nVar;
            k kVar2 = this.f431a;
            if (kVar2 != null && (nVar = this.f432b) != null) {
                kVar2.a(nVar);
            }
            this.f431a = kVar;
        }

        @Override // a.b.e.e.a.t
        public boolean a(A a2) {
            return false;
        }

        @Override // a.b.e.e.a.t
        public boolean a(k kVar, n nVar) {
            Toolbar.this.c();
            ViewParent parent = Toolbar.this.h.getParent();
            Toolbar toolbar = Toolbar.this;
            if (parent != toolbar) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar.h);
                }
                Toolbar toolbar2 = Toolbar.this;
                toolbar2.addView(toolbar2.h);
            }
            Toolbar.this.i = nVar.getActionView();
            this.f432b = nVar;
            ViewParent parent2 = Toolbar.this.i.getParent();
            Toolbar toolbar3 = Toolbar.this;
            if (parent2 != toolbar3) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar3.i);
                }
                b bVarGenerateDefaultLayoutParams = Toolbar.this.generateDefaultLayoutParams();
                Toolbar toolbar4 = Toolbar.this;
                bVarGenerateDefaultLayoutParams.f183a = 8388611 | (toolbar4.n & j.AppCompatTheme_windowActionBarOverlay);
                bVarGenerateDefaultLayoutParams.f434b = 2;
                toolbar4.i.setLayoutParams(bVarGenerateDefaultLayoutParams);
                Toolbar toolbar5 = Toolbar.this;
                toolbar5.addView(toolbar5.i);
            }
            Toolbar.this.h();
            Toolbar.this.requestLayout();
            nVar.D = true;
            nVar.n.b(false);
            KeyEvent.Callback callback = Toolbar.this.i;
            if (callback instanceof a.b.e.e.a) {
                ((a.b.e.e.a) callback).onActionViewExpanded();
            }
            return true;
        }

        @Override // a.b.e.e.a.t
        public boolean b(k kVar, n nVar) {
            KeyEvent.Callback callback = Toolbar.this.i;
            if (callback instanceof a.b.e.e.a) {
                ((a.b.e.e.a) callback).onActionViewCollapsed();
            }
            Toolbar toolbar = Toolbar.this;
            toolbar.removeView(toolbar.i);
            Toolbar toolbar2 = Toolbar.this;
            toolbar2.removeView(toolbar2.h);
            Toolbar toolbar3 = Toolbar.this;
            toolbar3.i = null;
            toolbar3.a();
            this.f432b = null;
            Toolbar.this.requestLayout();
            nVar.D = false;
            nVar.n.b(false);
            return true;
        }

        @Override // a.b.e.e.a.t
        public boolean flagActionItems() {
            return false;
        }

        @Override // a.b.e.e.a.t
        public void updateMenuView(boolean z) {
            boolean z2;
            if (this.f432b != null) {
                k kVar = this.f431a;
                if (kVar == null) {
                    z2 = false;
                    break;
                }
                int size = kVar.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z2 = false;
                        break;
                    } else {
                        if (this.f431a.getItem(i) == this.f432b) {
                            z2 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (z2) {
                    return;
                }
                k kVar2 = this.f431a;
                n nVar = this.f432b;
                KeyEvent.Callback callback = Toolbar.this.i;
                if (callback instanceof a.b.e.e.a) {
                    ((a.b.e.e.a) callback).onActionViewCollapsed();
                }
                Toolbar toolbar = Toolbar.this;
                toolbar.removeView(toolbar.i);
                Toolbar toolbar2 = Toolbar.this;
                toolbar2.removeView(toolbar2.h);
                Toolbar toolbar3 = Toolbar.this;
                toolbar3.i = null;
                toolbar3.a();
                this.f432b = null;
                Toolbar.this.requestLayout();
                nVar.a(false);
            }
        }
    }

    public static class b extends a.b.e.a.a {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f434b;

        public b(int i, int i2) {
            super(i, i2);
            this.f434b = 0;
            this.f183a = 8388627;
        }

        public b(a.b.e.a.a aVar) {
            super(aVar);
            this.f434b = 0;
        }

        public b(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f434b = 0;
        }

        public b(b bVar) {
            super((a.b.e.a.a) bVar);
            this.f434b = 0;
            this.f434b = bVar.f434b;
        }

        public b(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f434b = 0;
        }

        public b(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f434b = 0;
            ((ViewGroup.MarginLayoutParams) this).leftMargin = marginLayoutParams.leftMargin;
            ((ViewGroup.MarginLayoutParams) this).topMargin = marginLayoutParams.topMargin;
            ((ViewGroup.MarginLayoutParams) this).rightMargin = marginLayoutParams.rightMargin;
            ((ViewGroup.MarginLayoutParams) this).bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public interface c {
    }

    public static class d extends a.b.d.h.c {
        public static final Parcelable.Creator<d> CREATOR = new la();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f435a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public boolean f436b;

        public d(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f435a = parcel.readInt();
            this.f436b = parcel.readInt() != 0;
        }

        public d(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // a.b.d.h.c, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(super.f157b, i);
            parcel.writeInt(this.f435a);
            parcel.writeInt(this.f436b ? 1 : 0);
        }
    }

    public Toolbar(Context context) {
        this(context, null, a.b.e.b.a.toolbarStyle);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.b.e.b.a.toolbarStyle);
    }

    private MenuInflater getMenuInflater() {
        return new a.b.e.e.c(getContext());
    }

    public final int a(int i) {
        int iC = a.b.d.h.n.c(this);
        int iA = e.a(i, iC) & 7;
        if (iA == 1 || iA == 3 || iA == 5) {
            return iA;
        }
        return iC == 1 ? 5 : 3;
    }

    public final int a(View view, int i) {
        b bVar = (b) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int i3 = bVar.f183a & j.AppCompatTheme_windowActionBarOverlay;
        if (i3 != 16 && i3 != 48 && i3 != 80) {
            i3 = this.w & j.AppCompatTheme_windowActionBarOverlay;
        }
        if (i3 == 48) {
            return getPaddingTop() - i2;
        }
        if (i3 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) bVar).bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i4 = ((ViewGroup.MarginLayoutParams) bVar).topMargin;
        if (iMax < i4) {
            iMax = i4;
        } else {
            int i5 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            int i6 = ((ViewGroup.MarginLayoutParams) bVar).bottomMargin;
            if (i5 < i6) {
                iMax = Math.max(0, iMax - (i6 - i5));
            }
        }
        return paddingTop + iMax;
    }

    public final int a(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i6) + Math.max(0, i5);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + iMax + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    public final int a(View view, int i, int[] iArr, int i2) {
        b bVar = (b) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) bVar).leftMargin - iArr[0];
        int iMax = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        int iA = a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, iA, iMax + measuredWidth, view.getMeasuredHeight() + iA);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) bVar).rightMargin + iMax;
    }

    public void a() {
        for (int size = this.E.size() - 1; size >= 0; size--) {
            addView(this.E.get(size));
        }
        this.E.clear();
    }

    public void a(int i, int i2) {
        d();
        this.t.a(i, i2);
    }

    public void a(Context context, int i) {
        this.m = i;
        TextView textView = this.f430c;
        if (textView != null) {
            textView.setTextAppearance(context, i);
        }
    }

    public final void a(View view, int i, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    public final void a(View view, boolean z) {
        b bVarGenerateLayoutParams;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            bVarGenerateLayoutParams = generateDefaultLayoutParams();
        } else {
            bVarGenerateLayoutParams = !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (b) layoutParams;
        }
        bVarGenerateLayoutParams.f434b = 1;
        if (!z || this.i == null) {
            addView(view, bVarGenerateLayoutParams);
        } else {
            view.setLayoutParams(bVarGenerateLayoutParams);
            this.E.add(view);
        }
    }

    public final void a(List<View> list, int i) {
        boolean z = a.b.d.h.n.c(this) == 1;
        int childCount = getChildCount();
        int iA = e.a(i, a.b.d.h.n.c(this));
        list.clear();
        if (!z) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                b bVar = (b) childAt.getLayoutParams();
                if (bVar.f434b == 0 && d(childAt) && a(bVar.f183a) == iA) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i3 = childCount - 1; i3 >= 0; i3--) {
            View childAt2 = getChildAt(i3);
            b bVar2 = (b) childAt2.getLayoutParams();
            if (bVar2.f434b == 0 && d(childAt2) && a(bVar2.f183a) == iA) {
                list.add(childAt2);
            }
        }
    }

    public final int b(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final int b(View view, int i, int[] iArr, int i2) {
        b bVar = (b) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) bVar).rightMargin - iArr[1];
        int iMax = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int iA = a(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, iA, iMax, view.getMeasuredHeight() + iA);
        return iMax - (measuredWidth + ((ViewGroup.MarginLayoutParams) bVar).leftMargin);
    }

    public void b() {
        a aVar = this.J;
        n nVar = aVar == null ? null : aVar.f432b;
        if (nVar == null || (nVar.z & 8) == 0 || nVar.A == null) {
            return;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = nVar.C;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(nVar)) {
            nVar.n.a(nVar);
        }
    }

    public void b(Context context, int i) {
        this.l = i;
        TextView textView = this.f429b;
        if (textView != null) {
            textView.setTextAppearance(context, i);
        }
    }

    public void c() {
        if (this.h == null) {
            this.h = new C0021l(getContext(), null, a.b.e.b.a.toolbarNavigationButtonStyle);
            this.h.setImageDrawable(this.f);
            this.h.setContentDescription(this.g);
            b bVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            bVarGenerateDefaultLayoutParams.f183a = 8388611 | (this.n & j.AppCompatTheme_windowActionBarOverlay);
            bVarGenerateDefaultLayoutParams.f434b = 2;
            this.h.setLayoutParams(bVarGenerateDefaultLayoutParams);
            this.h.setOnClickListener(new ka(this));
        }
    }

    public final boolean c(View view) {
        return view.getParent() == this || this.E.contains(view);
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof b);
    }

    public final void d() {
        if (this.t == null) {
            this.t = new L();
        }
    }

    public final boolean d(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public final void e() {
        if (this.f428a == null) {
            this.f428a = new ActionMenuView(getContext(), null);
            this.f428a.setPopupTheme(this.k);
            this.f428a.setOnMenuItemClickListener(this.G);
            this.f428a.a(this.K, this.L);
            b bVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            bVarGenerateDefaultLayoutParams.f183a = 8388613 | (this.n & j.AppCompatTheme_windowActionBarOverlay);
            this.f428a.setLayoutParams(bVarGenerateDefaultLayoutParams);
            a((View) this.f428a, false);
        }
        if (this.f428a.d() == null) {
            k kVar = (k) this.f428a.getMenu();
            if (this.J == null) {
                this.J = new a();
            }
            this.f428a.setExpandedActionViewsExclusive(true);
            kVar.a(this.J, this.j);
        }
    }

    public final void f() {
        if (this.d == null) {
            this.d = new C0021l(getContext(), null, a.b.e.b.a.toolbarNavigationButtonStyle);
            b bVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            bVarGenerateDefaultLayoutParams.f183a = 8388611 | (this.n & j.AppCompatTheme_windowActionBarOverlay);
            this.d.setLayoutParams(bVarGenerateDefaultLayoutParams);
        }
    }

    public boolean g() {
        ActionMenuView actionMenuView = this.f428a;
        return actionMenuView != null && actionMenuView.c();
    }

    @Override // android.view.ViewGroup
    public b generateDefaultLayoutParams() {
        return new b(-2, -2);
    }

    @Override // android.view.ViewGroup
    public b generateLayoutParams(AttributeSet attributeSet) {
        return new b(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public b generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof b) {
            return new b((b) layoutParams);
        }
        if (layoutParams instanceof a.b.e.a.a) {
            return new b((a.b.e.a.a) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new b((ViewGroup.MarginLayoutParams) layoutParams) : new b(layoutParams);
    }

    public int getContentInsetEnd() {
        L l = this.t;
        if (l != null) {
            return l.g ? l.f284a : l.f285b;
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int i = this.v;
        return i != Integer.MIN_VALUE ? i : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        L l = this.t;
        if (l != null) {
            return l.f284a;
        }
        return 0;
    }

    public int getContentInsetRight() {
        L l = this.t;
        if (l != null) {
            return l.f285b;
        }
        return 0;
    }

    public int getContentInsetStart() {
        L l = this.t;
        if (l != null) {
            return l.g ? l.f285b : l.f284a;
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i = this.u;
        return i != Integer.MIN_VALUE ? i : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        k kVarD;
        ActionMenuView actionMenuView = this.f428a;
        return actionMenuView != null && (kVarD = actionMenuView.d()) != null && kVarD.hasVisibleItems() ? Math.max(getContentInsetEnd(), Math.max(this.v, 0)) : getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        return a.b.d.h.n.c(this) == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        return a.b.d.h.n.c(this) == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.u, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        ImageView imageView = this.e;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.e;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        e();
        return this.f428a.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.d;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.d;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public C0015f getOuterActionMenuPresenter() {
        return this.I;
    }

    public Drawable getOverflowIcon() {
        e();
        return this.f428a.getOverflowIcon();
    }

    public Context getPopupContext() {
        return this.j;
    }

    public int getPopupTheme() {
        return this.k;
    }

    public CharSequence getSubtitle() {
        return this.y;
    }

    public CharSequence getTitle() {
        return this.x;
    }

    public int getTitleMarginBottom() {
        return this.s;
    }

    public int getTitleMarginEnd() {
        return this.q;
    }

    public int getTitleMarginStart() {
        return this.p;
    }

    public int getTitleMarginTop() {
        return this.r;
    }

    public InterfaceC0034z getWrapper() {
        if (this.H == null) {
            this.H = new na(this, true);
        }
        return this.H;
    }

    public void h() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (((b) childAt.getLayoutParams()).f434b != 2 && childAt != this.f428a) {
                removeViewAt(childCount);
                this.E.add(childAt);
            }
        }
    }

    public boolean i() {
        ActionMenuView actionMenuView = this.f428a;
        return actionMenuView != null && actionMenuView.e();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.N);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.C = false;
        }
        if (!this.C) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.C = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.C = false;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0291  */
    /* JADX WARN: Code duplicated, block: B:104:0x02a5 A[LOOP:0: B:103:0x02a3->B:104:0x02a5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:107:0x02c7 A[LOOP:1: B:106:0x02c5->B:107:0x02c7, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:110:0x02ed A[LOOP:2: B:109:0x02eb->B:110:0x02ed, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:114:0x032f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:115:0x0331  */
    /* JADX WARN: Code duplicated, block: B:116:0x0335  */
    /* JADX WARN: Code duplicated, block: B:119:0x033e A[LOOP:3: B:118:0x033c->B:119:0x033e, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:19:0x005f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:20:0x0061  */
    /* JADX WARN: Code duplicated, block: B:21:0x0068  */
    /* JADX WARN: Code duplicated, block: B:24:0x0076 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x0078  */
    /* JADX WARN: Code duplicated, block: B:26:0x007f  */
    /* JADX WARN: Code duplicated, block: B:29:0x00b3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:31:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:35:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:36:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:39:0x00e7  */
    /* JADX WARN: Code duplicated, block: B:40:0x0100  */
    /* JADX WARN: Code duplicated, block: B:42:0x0105  */
    /* JADX WARN: Code duplicated, block: B:43:0x011d  */
    /* JADX WARN: Code duplicated, block: B:48:0x012a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:49:0x012c  */
    /* JADX WARN: Code duplicated, block: B:50:0x012f  */
    /* JADX WARN: Code duplicated, block: B:52:0x0133  */
    /* JADX WARN: Code duplicated, block: B:53:0x0136  */
    /* JADX WARN: Code duplicated, block: B:56:0x0146  */
    /* JADX WARN: Code duplicated, block: B:58:0x014e A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:65:0x0169  */
    /* JADX WARN: Code duplicated, block: B:67:0x016d  */
    /* JADX WARN: Code duplicated, block: B:69:0x017f  */
    /* JADX WARN: Code duplicated, block: B:70:0x0182  */
    /* JADX WARN: Code duplicated, block: B:72:0x018d  */
    /* JADX WARN: Code duplicated, block: B:74:0x0199  */
    /* JADX WARN: Code duplicated, block: B:75:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:77:0x01b8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:78:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:79:0x01be  */
    /* JADX WARN: Code duplicated, block: B:82:0x01d2  */
    /* JADX WARN: Code duplicated, block: B:83:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:85:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:86:0x021f  */
    /* JADX WARN: Code duplicated, block: B:88:0x0222  */
    /* JADX WARN: Code duplicated, block: B:90:0x022a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:91:0x022c  */
    /* JADX WARN: Code duplicated, block: B:92:0x0230  */
    /* JADX WARN: Code duplicated, block: B:95:0x0244  */
    /* JADX WARN: Code duplicated, block: B:96:0x0267  */
    /* JADX WARN: Code duplicated, block: B:98:0x026a  */
    /* JADX WARN: Code duplicated, block: B:99:0x028e  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iA;
        int iB;
        int iMax;
        int iMin;
        boolean zD;
        boolean zD2;
        int measuredHeight;
        TextView textView;
        TextView textView2;
        b bVar;
        b bVar2;
        boolean z2;
        int i5;
        int i6;
        int paddingTop;
        int i7;
        int i8;
        int i9;
        int i10;
        char c2;
        int i11;
        int i12;
        int i13;
        int iMax2;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int size;
        int iA2;
        int i19;
        int i20;
        int size2;
        int i21;
        ArrayList<View> arrayList;
        int size3;
        int i22;
        int i23;
        int i24;
        int measuredWidth;
        int i25;
        int i26;
        int i27;
        int size4;
        boolean z3 = a.b.d.h.n.c(this) == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop2 = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i28 = width - paddingRight;
        int[] iArr = this.F;
        iArr[1] = 0;
        iArr[0] = 0;
        int iD = a.b.d.h.n.d(this);
        int iMin2 = iD >= 0 ? Math.min(iD, i4 - i2) : 0;
        if (d(this.d)) {
            if (z3) {
                iB = b(this.d, i28, iArr, iMin2);
                iA = paddingLeft;
            } else {
                iA = a(this.d, paddingLeft, iArr, iMin2);
            }
            if (d(this.h)) {
                if (z3) {
                    iB = b(this.h, iB, iArr, iMin2);
                } else {
                    iA = a(this.h, iA, iArr, iMin2);
                }
            }
            if (d(this.f428a)) {
                if (z3) {
                    iA = a(this.f428a, iA, iArr, iMin2);
                } else {
                    iB = b(this.f428a, iB, iArr, iMin2);
                }
            }
            int currentContentInsetLeft = getCurrentContentInsetLeft();
            int currentContentInsetRight = getCurrentContentInsetRight();
            iArr[0] = Math.max(0, currentContentInsetLeft - iA);
            iArr[1] = Math.max(0, currentContentInsetRight - (i28 - iB));
            iMax = Math.max(iA, currentContentInsetLeft);
            iMin = Math.min(iB, i28 - currentContentInsetRight);
            if (d(this.i)) {
                if (z3) {
                    iMin = b(this.i, iMin, iArr, iMin2);
                } else {
                    iMax = a(this.i, iMax, iArr, iMin2);
                }
            }
            if (d(this.e)) {
                if (z3) {
                    iMin = b(this.e, iMin, iArr, iMin2);
                } else {
                    iMax = a(this.e, iMax, iArr, iMin2);
                }
            }
            zD = d(this.f429b);
            zD2 = d(this.f430c);
            if (zD) {
                b bVar3 = (b) this.f429b.getLayoutParams();
                measuredHeight = this.f429b.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar3).topMargin + ((ViewGroup.MarginLayoutParams) bVar3).bottomMargin + 0;
            } else {
                measuredHeight = 0;
            }
            if (zD2) {
                b bVar4 = (b) this.f430c.getLayoutParams();
                measuredHeight += this.f430c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar4).topMargin + ((ViewGroup.MarginLayoutParams) bVar4).bottomMargin;
            }
            if (!zD || zD2) {
                if (zD) {
                    textView = this.f429b;
                } else {
                    textView = this.f430c;
                }
                if (zD2) {
                    textView2 = this.f430c;
                } else {
                    textView2 = this.f429b;
                }
                bVar = (b) textView.getLayoutParams();
                bVar2 = (b) textView2.getLayoutParams();
                z2 = (!zD && this.f429b.getMeasuredWidth() > 0) || (zD2 && this.f430c.getMeasuredWidth() > 0);
                i5 = this.w & j.AppCompatTheme_windowActionBarOverlay;
                i6 = iMin2;
                if (i5 == 48) {
                    paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) bVar).topMargin + this.r;
                } else if (i5 != 80) {
                    iMax2 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                    i14 = ((ViewGroup.MarginLayoutParams) bVar).topMargin;
                    i15 = this.r;
                    if (iMax2 < i14 + i15) {
                        iMax2 = i14 + i15;
                    } else {
                        i16 = (((height - paddingBottom) - measuredHeight) - iMax2) - paddingTop2;
                        i17 = ((ViewGroup.MarginLayoutParams) bVar).bottomMargin;
                        i18 = this.s;
                        if (i16 < i17 + i18) {
                            iMax2 = Math.max(0, iMax2 - ((((ViewGroup.MarginLayoutParams) bVar2).bottomMargin + i18) - i16));
                        }
                    }
                    paddingTop = paddingTop2 + iMax2;
                } else {
                    paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) bVar2).bottomMargin) - this.s) - measuredHeight;
                }
                if (z3) {
                    if (z2) {
                        i11 = this.p;
                        c2 = 1;
                    } else {
                        c2 = 1;
                        i11 = 0;
                    }
                    int i29 = i11 - iArr[c2];
                    iMin -= Math.max(0, i29);
                    iArr[c2] = Math.max(0, -i29);
                    if (zD) {
                        b bVar5 = (b) this.f429b.getLayoutParams();
                        int measuredWidth2 = iMin - this.f429b.getMeasuredWidth();
                        int measuredHeight2 = this.f429b.getMeasuredHeight() + paddingTop;
                        this.f429b.layout(measuredWidth2, paddingTop, iMin, measuredHeight2);
                        i12 = measuredWidth2 - this.q;
                        paddingTop = measuredHeight2 + ((ViewGroup.MarginLayoutParams) bVar5).bottomMargin;
                    } else {
                        i12 = iMin;
                    }
                    if (zD2) {
                        b bVar6 = (b) this.f430c.getLayoutParams();
                        int i30 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar6).topMargin;
                        this.f430c.layout(iMin - this.f430c.getMeasuredWidth(), i30, iMin, this.f430c.getMeasuredHeight() + i30);
                        i13 = iMin - this.q;
                        int i31 = ((ViewGroup.MarginLayoutParams) bVar6).bottomMargin;
                    } else {
                        i13 = iMin;
                    }
                    if (z2) {
                        iMin = Math.min(i12, i13);
                    }
                    iMax = iMax;
                } else {
                    if (z2) {
                        i8 = this.p;
                        i7 = 0;
                    } else {
                        i7 = 0;
                        i8 = 0;
                    }
                    int i32 = i8 - iArr[i7];
                    iMax = Math.max(i7, i32) + iMax;
                    iArr[i7] = Math.max(i7, -i32);
                    if (zD) {
                        b bVar7 = (b) this.f429b.getLayoutParams();
                        int measuredWidth3 = this.f429b.getMeasuredWidth() + iMax;
                        int measuredHeight3 = this.f429b.getMeasuredHeight() + paddingTop;
                        this.f429b.layout(iMax, paddingTop, measuredWidth3, measuredHeight3);
                        i9 = measuredWidth3 + this.q;
                        paddingTop = measuredHeight3 + ((ViewGroup.MarginLayoutParams) bVar7).bottomMargin;
                    } else {
                        i9 = iMax;
                    }
                    if (zD2 != 0) {
                        b bVar8 = (b) this.f430c.getLayoutParams();
                        int i33 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar8).topMargin;
                        int measuredWidth4 = this.f430c.getMeasuredWidth() + iMax;
                        this.f430c.layout(iMax, i33, measuredWidth4, this.f430c.getMeasuredHeight() + i33);
                        i10 = measuredWidth4 + this.q;
                        int i34 = ((ViewGroup.MarginLayoutParams) bVar8).bottomMargin;
                    } else {
                        i10 = iMax;
                    }
                    if (z2) {
                        iMax = Math.max(i9, i10);
                    }
                }
            } else {
                paddingLeft = paddingLeft;
                i6 = iMin2;
            }
            a(this.D, 3);
            size = this.D.size();
            iA2 = iMax;
            for (i19 = 0; i19 < size; i19++) {
                iA2 = a(this.D.get(i19), iA2, iArr, i6);
            }
            i20 = i6;
            a(this.D, 5);
            size2 = this.D.size();
            for (i21 = 0; i21 < size2; i21++) {
                iMin = b(this.D.get(i21), iMin, iArr, i20);
            }
            a(this.D, 1);
            arrayList = this.D;
            int i35 = iArr[0];
            int i36 = iArr[1];
            size3 = arrayList.size();
            i22 = i36;
            i23 = i35;
            i24 = 0;
            measuredWidth = 0;
            while (i24 < size3) {
                View view = arrayList.get(i24);
                b bVar9 = (b) view.getLayoutParams();
                int i37 = ((ViewGroup.MarginLayoutParams) bVar9).leftMargin - i23;
                int i38 = ((ViewGroup.MarginLayoutParams) bVar9).rightMargin - i22;
                int iMax3 = Math.max(0, i37);
                int iMax4 = Math.max(0, i38);
                int iMax5 = Math.max(0, -i37);
                int iMax6 = Math.max(0, -i38);
                measuredWidth += view.getMeasuredWidth() + iMax3 + iMax4;
                i24++;
                i22 = iMax6;
                i23 = iMax5;
            }
            i26 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (measuredWidth / 2);
            i27 = measuredWidth + i26;
            if (i26 >= iA2) {
                if (i27 > iMin) {
                    iA2 = i26 - (i27 - iMin);
                } else {
                    iA2 = i26;
                }
            }
            size4 = this.D.size();
            for (i25 = 0; i25 < size4; i25++) {
                iA2 = a(this.D.get(i25), iA2, iArr, i20);
            }
            this.D.clear();
        }
        iA = paddingLeft;
        iB = i28;
        if (d(this.h)) {
            if (z3) {
                iB = b(this.h, iB, iArr, iMin2);
            } else {
                iA = a(this.h, iA, iArr, iMin2);
            }
        }
        if (d(this.f428a)) {
            if (z3) {
                iA = a(this.f428a, iA, iArr, iMin2);
            } else {
                iB = b(this.f428a, iB, iArr, iMin2);
            }
        }
        int currentContentInsetLeft2 = getCurrentContentInsetLeft();
        int currentContentInsetRight2 = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft2 - iA);
        iArr[1] = Math.max(0, currentContentInsetRight2 - (i28 - iB));
        iMax = Math.max(iA, currentContentInsetLeft2);
        iMin = Math.min(iB, i28 - currentContentInsetRight2);
        if (d(this.i)) {
            if (z3) {
                iMin = b(this.i, iMin, iArr, iMin2);
            } else {
                iMax = a(this.i, iMax, iArr, iMin2);
            }
        }
        if (d(this.e)) {
            if (z3) {
                iMin = b(this.e, iMin, iArr, iMin2);
            } else {
                iMax = a(this.e, iMax, iArr, iMin2);
            }
        }
        zD = d(this.f429b);
        zD2 = d(this.f430c);
        if (zD) {
            b bVar10 = (b) this.f429b.getLayoutParams();
            measuredHeight = this.f429b.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar10).topMargin + ((ViewGroup.MarginLayoutParams) bVar10).bottomMargin + 0;
        } else {
            measuredHeight = 0;
        }
        if (zD2) {
            b bVar11 = (b) this.f430c.getLayoutParams();
            measuredHeight += this.f430c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) bVar11).topMargin + ((ViewGroup.MarginLayoutParams) bVar11).bottomMargin;
        }
        if (zD) {
            if (zD) {
                textView = this.f429b;
            } else {
                textView = this.f430c;
            }
            if (zD2) {
                textView2 = this.f430c;
            } else {
                textView2 = this.f429b;
            }
            bVar = (b) textView.getLayoutParams();
            bVar2 = (b) textView2.getLayoutParams();
            if (zD) {
            }
            i5 = this.w & j.AppCompatTheme_windowActionBarOverlay;
            i6 = iMin2;
            if (i5 == 48) {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) bVar).topMargin + this.r;
            } else if (i5 != 80) {
                iMax2 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                i14 = ((ViewGroup.MarginLayoutParams) bVar).topMargin;
                i15 = this.r;
                if (iMax2 < i14 + i15) {
                    iMax2 = i14 + i15;
                } else {
                    i16 = (((height - paddingBottom) - measuredHeight) - iMax2) - paddingTop2;
                    i17 = ((ViewGroup.MarginLayoutParams) bVar).bottomMargin;
                    i18 = this.s;
                    if (i16 < i17 + i18) {
                        iMax2 = Math.max(0, iMax2 - ((((ViewGroup.MarginLayoutParams) bVar2).bottomMargin + i18) - i16));
                    }
                }
                paddingTop = paddingTop2 + iMax2;
            } else {
                paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) bVar2).bottomMargin) - this.s) - measuredHeight;
            }
            if (z3) {
                if (z2) {
                    i11 = this.p;
                    c2 = 1;
                } else {
                    c2 = 1;
                    i11 = 0;
                }
                int i210 = i11 - iArr[c2];
                iMin -= Math.max(0, i210);
                iArr[c2] = Math.max(0, -i210);
                if (zD) {
                    b bVar12 = (b) this.f429b.getLayoutParams();
                    int measuredWidth5 = iMin - this.f429b.getMeasuredWidth();
                    int measuredHeight4 = this.f429b.getMeasuredHeight() + paddingTop;
                    this.f429b.layout(measuredWidth5, paddingTop, iMin, measuredHeight4);
                    i12 = measuredWidth5 - this.q;
                    paddingTop = measuredHeight4 + ((ViewGroup.MarginLayoutParams) bVar12).bottomMargin;
                } else {
                    i12 = iMin;
                }
                if (zD2) {
                    b bVar13 = (b) this.f430c.getLayoutParams();
                    int i39 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar13).topMargin;
                    this.f430c.layout(iMin - this.f430c.getMeasuredWidth(), i39, iMin, this.f430c.getMeasuredHeight() + i39);
                    i13 = iMin - this.q;
                    int i310 = ((ViewGroup.MarginLayoutParams) bVar13).bottomMargin;
                } else {
                    i13 = iMin;
                }
                if (z2) {
                    iMin = Math.min(i12, i13);
                }
                iMax = iMax;
            } else {
                if (z2) {
                    i8 = this.p;
                    i7 = 0;
                } else {
                    i7 = 0;
                    i8 = 0;
                }
                int i311 = i8 - iArr[i7];
                iMax = Math.max(i7, i311) + iMax;
                iArr[i7] = Math.max(i7, -i311);
                if (zD) {
                    b bVar14 = (b) this.f429b.getLayoutParams();
                    int measuredWidth6 = this.f429b.getMeasuredWidth() + iMax;
                    int measuredHeight5 = this.f429b.getMeasuredHeight() + paddingTop;
                    this.f429b.layout(iMax, paddingTop, measuredWidth6, measuredHeight5);
                    i9 = measuredWidth6 + this.q;
                    paddingTop = measuredHeight5 + ((ViewGroup.MarginLayoutParams) bVar14).bottomMargin;
                } else {
                    i9 = iMax;
                }
                if (zD2 != 0) {
                    b bVar15 = (b) this.f430c.getLayoutParams();
                    int i312 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar15).topMargin;
                    int measuredWidth7 = this.f430c.getMeasuredWidth() + iMax;
                    this.f430c.layout(iMax, i312, measuredWidth7, this.f430c.getMeasuredHeight() + i312);
                    i10 = measuredWidth7 + this.q;
                    int i313 = ((ViewGroup.MarginLayoutParams) bVar15).bottomMargin;
                } else {
                    i10 = iMax;
                }
                if (z2) {
                    iMax = Math.max(i9, i10);
                }
            }
        } else {
            if (zD) {
                textView = this.f429b;
            } else {
                textView = this.f430c;
            }
            if (zD2) {
                textView2 = this.f430c;
            } else {
                textView2 = this.f429b;
            }
            bVar = (b) textView.getLayoutParams();
            bVar2 = (b) textView2.getLayoutParams();
            if (zD) {
            }
            i5 = this.w & j.AppCompatTheme_windowActionBarOverlay;
            i6 = iMin2;
            if (i5 == 48) {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) bVar).topMargin + this.r;
            } else if (i5 != 80) {
                iMax2 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                i14 = ((ViewGroup.MarginLayoutParams) bVar).topMargin;
                i15 = this.r;
                if (iMax2 < i14 + i15) {
                    iMax2 = i14 + i15;
                } else {
                    i16 = (((height - paddingBottom) - measuredHeight) - iMax2) - paddingTop2;
                    i17 = ((ViewGroup.MarginLayoutParams) bVar).bottomMargin;
                    i18 = this.s;
                    if (i16 < i17 + i18) {
                        iMax2 = Math.max(0, iMax2 - ((((ViewGroup.MarginLayoutParams) bVar2).bottomMargin + i18) - i16));
                    }
                }
                paddingTop = paddingTop2 + iMax2;
            } else {
                paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) bVar2).bottomMargin) - this.s) - measuredHeight;
            }
            if (z3) {
                if (z2) {
                    i11 = this.p;
                    c2 = 1;
                } else {
                    c2 = 1;
                    i11 = 0;
                }
                int i211 = i11 - iArr[c2];
                iMin -= Math.max(0, i211);
                iArr[c2] = Math.max(0, -i211);
                if (zD) {
                    b bVar16 = (b) this.f429b.getLayoutParams();
                    int measuredWidth8 = iMin - this.f429b.getMeasuredWidth();
                    int measuredHeight6 = this.f429b.getMeasuredHeight() + paddingTop;
                    this.f429b.layout(measuredWidth8, paddingTop, iMin, measuredHeight6);
                    i12 = measuredWidth8 - this.q;
                    paddingTop = measuredHeight6 + ((ViewGroup.MarginLayoutParams) bVar16).bottomMargin;
                } else {
                    i12 = iMin;
                }
                if (zD2) {
                    b bVar17 = (b) this.f430c.getLayoutParams();
                    int i314 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar17).topMargin;
                    this.f430c.layout(iMin - this.f430c.getMeasuredWidth(), i314, iMin, this.f430c.getMeasuredHeight() + i314);
                    i13 = iMin - this.q;
                    int i315 = ((ViewGroup.MarginLayoutParams) bVar17).bottomMargin;
                } else {
                    i13 = iMin;
                }
                if (z2) {
                    iMin = Math.min(i12, i13);
                }
                iMax = iMax;
            } else {
                if (z2) {
                    i8 = this.p;
                    i7 = 0;
                } else {
                    i7 = 0;
                    i8 = 0;
                }
                int i316 = i8 - iArr[i7];
                iMax = Math.max(i7, i316) + iMax;
                iArr[i7] = Math.max(i7, -i316);
                if (zD) {
                    b bVar18 = (b) this.f429b.getLayoutParams();
                    int measuredWidth9 = this.f429b.getMeasuredWidth() + iMax;
                    int measuredHeight7 = this.f429b.getMeasuredHeight() + paddingTop;
                    this.f429b.layout(iMax, paddingTop, measuredWidth9, measuredHeight7);
                    i9 = measuredWidth9 + this.q;
                    paddingTop = measuredHeight7 + ((ViewGroup.MarginLayoutParams) bVar18).bottomMargin;
                } else {
                    i9 = iMax;
                }
                if (zD2 != 0) {
                    b bVar19 = (b) this.f430c.getLayoutParams();
                    int i317 = paddingTop + ((ViewGroup.MarginLayoutParams) bVar19).topMargin;
                    int measuredWidth10 = this.f430c.getMeasuredWidth() + iMax;
                    this.f430c.layout(iMax, i317, measuredWidth10, this.f430c.getMeasuredHeight() + i317);
                    i10 = measuredWidth10 + this.q;
                    int i318 = ((ViewGroup.MarginLayoutParams) bVar19).bottomMargin;
                } else {
                    i10 = iMax;
                }
                if (z2) {
                    iMax = Math.max(i9, i10);
                }
            }
        }
        a(this.D, 3);
        size = this.D.size();
        iA2 = iMax;
        while (i19 < size) {
            iA2 = a(this.D.get(i19), iA2, iArr, i6);
        }
        i20 = i6;
        a(this.D, 5);
        size2 = this.D.size();
        while (i21 < size2) {
            iMin = b(this.D.get(i21), iMin, iArr, i20);
        }
        a(this.D, 1);
        arrayList = this.D;
        int i319 = iArr[0];
        int i320 = iArr[1];
        size3 = arrayList.size();
        i22 = i320;
        i23 = i319;
        i24 = 0;
        measuredWidth = 0;
        while (i24 < size3) {
            View view2 = arrayList.get(i24);
            b bVar20 = (b) view2.getLayoutParams();
            int i321 = ((ViewGroup.MarginLayoutParams) bVar20).leftMargin - i23;
            int i322 = ((ViewGroup.MarginLayoutParams) bVar20).rightMargin - i22;
            int iMax7 = Math.max(0, i321);
            int iMax8 = Math.max(0, i322);
            int iMax9 = Math.max(0, -i321);
            int iMax10 = Math.max(0, -i322);
            measuredWidth += view2.getMeasuredWidth() + iMax7 + iMax8;
            i24++;
            i22 = iMax10;
            i23 = iMax9;
        }
        i26 = ((((width - paddingLeft) - paddingRight) / 2) + paddingLeft) - (measuredWidth / 2);
        i27 = measuredWidth + i26;
        if (i26 >= iA2) {
            if (i27 > iMin) {
                iA2 = i26 - (i27 - iMin);
            } else {
                iA2 = i26;
            }
        }
        size4 = this.D.size();
        while (i25 < size4) {
            iA2 = a(this.D.get(i25), iA2, iArr, i20);
        }
        this.D.clear();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        char c2;
        char c3;
        int measuredWidth;
        int iMax;
        int iCombineMeasuredStates;
        int measuredWidth2;
        int iCombineMeasuredStates2;
        int iMax2;
        int iB;
        int[] iArr = this.F;
        boolean z = true;
        if (ta.a(this)) {
            c2 = 1;
            c3 = 0;
        } else {
            c2 = 0;
            c3 = 1;
        }
        if (d(this.d)) {
            a(this.d, i, 0, i2, 0, this.o);
            measuredWidth = this.d.getMeasuredWidth() + a(this.d);
            iMax = Math.max(0, b(this.d) + this.d.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.d.getMeasuredState());
        } else {
            measuredWidth = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (d(this.h)) {
            a(this.h, i, 0, i2, 0, this.o);
            measuredWidth = this.h.getMeasuredWidth() + a(this.h);
            iMax = Math.max(iMax, b(this.h) + this.h.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.h.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = Math.max(currentContentInsetStart, measuredWidth) + 0;
        iArr[c2] = Math.max(0, currentContentInsetStart - measuredWidth);
        if (d(this.f428a)) {
            a(this.f428a, i, iMax3, i2, 0, this.o);
            measuredWidth2 = this.f428a.getMeasuredWidth() + a(this.f428a);
            iMax = Math.max(iMax, b(this.f428a) + this.f428a.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f428a.getMeasuredState());
        } else {
            measuredWidth2 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax4 = Math.max(currentContentInsetEnd, measuredWidth2) + iMax3;
        iArr[c3] = Math.max(0, currentContentInsetEnd - measuredWidth2);
        if (d(this.i)) {
            iMax4 += a(this.i, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, b(this.i) + this.i.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.i.getMeasuredState());
        }
        if (d(this.e)) {
            iMax4 += a(this.e, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, b(this.e) + this.e.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.e.getMeasuredState());
        }
        int childCount = getChildCount();
        int iMax5 = iMax;
        int iA = iMax4;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (((b) childAt.getLayoutParams()).f434b == 0 && d(childAt)) {
                iA += a(childAt, i, iA, i2, 0, iArr);
                iMax5 = Math.max(iMax5, b(childAt) + childAt.getMeasuredHeight());
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        int i4 = this.r + this.s;
        int i5 = this.p + this.q;
        if (d(this.f429b)) {
            a(this.f429b, i, iA + i5, i2, i4, iArr);
            int measuredWidth3 = this.f429b.getMeasuredWidth() + a(this.f429b);
            iB = this.f429b.getMeasuredHeight() + b(this.f429b);
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.f429b.getMeasuredState());
            iMax2 = measuredWidth3;
        } else {
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iMax2 = 0;
            iB = 0;
        }
        if (d(this.f430c)) {
            iMax2 = Math.max(iMax2, a(this.f430c, i, iA + i5, i2, iB + i4, iArr));
            iB = b(this.f430c) + this.f430c.getMeasuredHeight() + iB;
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.f430c.getMeasuredState());
        }
        int iMax6 = Math.max(iMax5, iB);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop() + iMax6;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingRight + iA + iMax2, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates2);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16);
        if (!this.M) {
            z = false;
            break;
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            View childAt2 = getChildAt(i6);
            if (d(childAt2) && childAt2.getMeasuredWidth() > 0 && childAt2.getMeasuredHeight() > 0) {
                z = false;
                break;
            }
        }
        if (z) {
            iResolveSizeAndState2 = 0;
        }
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof d)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        d dVar = (d) parcelable;
        super.onRestoreInstanceState(((a.b.d.h.c) dVar).f157b);
        ActionMenuView actionMenuView = this.f428a;
        k kVarD = actionMenuView != null ? actionMenuView.d() : null;
        int i = dVar.f435a;
        if (i != 0 && this.J != null && kVarD != null && (menuItemFindItem = kVarD.findItem(i)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (dVar.f436b) {
            removeCallbacks(this.N);
            post(this.N);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        int i2;
        int i3 = Build.VERSION.SDK_INT;
        super.onRtlPropertiesChanged(i);
        d();
        L l = this.t;
        boolean z = i == 1;
        if (z == l.g) {
            return;
        }
        l.g = z;
        if (l.h) {
            if (z) {
                int i4 = l.d;
                if (i4 == Integer.MIN_VALUE) {
                    i4 = l.e;
                }
                l.f284a = i4;
                i2 = l.f286c;
                if (i2 == Integer.MIN_VALUE) {
                }
            } else {
                int i5 = l.f286c;
                if (i5 == Integer.MIN_VALUE) {
                    i5 = l.e;
                }
                l.f284a = i5;
                i2 = l.d;
                if (i2 == Integer.MIN_VALUE) {
                }
            }
            l.f285b = i2;
        }
        l.f284a = l.e;
        i2 = l.f;
        l.f285b = i2;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        n nVar;
        d dVar = new d(super.onSaveInstanceState());
        a aVar = this.J;
        if (aVar != null && (nVar = aVar.f432b) != null) {
            dVar.f435a = nVar.f232a;
        }
        dVar.f436b = g();
        return dVar;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.B = false;
        }
        if (!this.B) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.B = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.B = false;
        }
        return true;
    }

    public void setCollapsible(boolean z) {
        this.M = z;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.v) {
            this.v = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.u) {
            this.u = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setLogo(int i) {
        setLogo(a.b.e.c.a.a.c(getContext(), i));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            if (this.e == null) {
                this.e = new C0023n(getContext(), null, 0);
            }
            if (!c(this.e)) {
                a((View) this.e, true);
            }
        } else {
            ImageView imageView = this.e;
            if (imageView != null && c(imageView)) {
                removeView(this.e);
                this.E.remove(this.e);
            }
        }
        ImageView imageView2 = this.e;
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence) && this.e == null) {
            this.e = new C0023n(getContext(), null, 0);
        }
        ImageView imageView = this.e;
        if (imageView != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            f();
        }
        ImageButton imageButton = this.d;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int i) {
        setNavigationIcon(a.b.e.c.a.a.c(getContext(), i));
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            f();
            if (!c(this.d)) {
                a((View) this.d, true);
            }
        } else {
            ImageButton imageButton = this.d;
            if (imageButton != null && c(imageButton)) {
                removeView(this.d);
                this.E.remove(this.d);
            }
        }
        ImageButton imageButton2 = this.d;
        if (imageButton2 != null) {
            imageButton2.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        f();
        this.d.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(c cVar) {
    }

    public void setOverflowIcon(Drawable drawable) {
        e();
        this.f428a.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int i) {
        if (this.k != i) {
            this.k = i;
            if (i == 0) {
                this.j = getContext();
            } else {
                this.j = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setSubtitle(int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.f430c;
            if (textView != null && c(textView)) {
                removeView(this.f430c);
                this.E.remove(this.f430c);
            }
        } else {
            if (this.f430c == null) {
                Context context = getContext();
                this.f430c = new C0031w(context);
                this.f430c.setSingleLine();
                this.f430c.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.m;
                if (i != 0) {
                    this.f430c.setTextAppearance(context, i);
                }
                int i2 = this.A;
                if (i2 != 0) {
                    this.f430c.setTextColor(i2);
                }
            }
            if (!c(this.f430c)) {
                a((View) this.f430c, true);
            }
        }
        TextView textView2 = this.f430c;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.y = charSequence;
    }

    public void setSubtitleTextColor(int i) {
        this.A = i;
        TextView textView = this.f430c;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public void setTitle(int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.f429b;
            if (textView != null && c(textView)) {
                removeView(this.f429b);
                this.E.remove(this.f429b);
            }
        } else {
            if (this.f429b == null) {
                Context context = getContext();
                this.f429b = new C0031w(context);
                this.f429b.setSingleLine();
                this.f429b.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.l;
                if (i != 0) {
                    this.f429b.setTextAppearance(context, i);
                }
                int i2 = this.z;
                if (i2 != 0) {
                    this.f429b.setTextColor(i2);
                }
            }
            if (!c(this.f429b)) {
                a((View) this.f429b, true);
            }
        }
        TextView textView2 = this.f429b;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.x = charSequence;
    }

    public void setTitleMarginBottom(int i) {
        this.s = i;
        requestLayout();
    }

    public void setTitleMarginEnd(int i) {
        this.q = i;
        requestLayout();
    }

    public void setTitleMarginStart(int i) {
        this.p = i;
        requestLayout();
    }

    public void setTitleMarginTop(int i) {
        this.r = i;
        requestLayout();
    }

    public void setTitleTextColor(int i) {
        this.z = i;
        TextView textView = this.f429b;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public Toolbar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.w = 8388627;
        this.D = new ArrayList<>();
        this.E = new ArrayList<>();
        this.F = new int[2];
        this.G = new ia(this);
        this.N = new ja(this);
        ha haVarA = ha.a(getContext(), attributeSet, j.Toolbar, i, 0);
        this.l = haVarA.f(j.Toolbar_titleTextAppearance, 0);
        this.m = haVarA.f(j.Toolbar_subtitleTextAppearance, 0);
        this.w = haVarA.f337b.getInteger(j.Toolbar_android_gravity, this.w);
        this.n = haVarA.f337b.getInteger(j.Toolbar_buttonGravity, 48);
        int iB = haVarA.b(j.Toolbar_titleMargin, 0);
        iB = haVarA.e(j.Toolbar_titleMargins) ? haVarA.b(j.Toolbar_titleMargins, iB) : iB;
        this.s = iB;
        this.r = iB;
        this.q = iB;
        this.p = iB;
        int iB2 = haVarA.b(j.Toolbar_titleMarginStart, -1);
        if (iB2 >= 0) {
            this.p = iB2;
        }
        int iB3 = haVarA.b(j.Toolbar_titleMarginEnd, -1);
        if (iB3 >= 0) {
            this.q = iB3;
        }
        int iB4 = haVarA.b(j.Toolbar_titleMarginTop, -1);
        if (iB4 >= 0) {
            this.r = iB4;
        }
        int iB5 = haVarA.b(j.Toolbar_titleMarginBottom, -1);
        if (iB5 >= 0) {
            this.s = iB5;
        }
        this.o = haVarA.c(j.Toolbar_maxButtonHeight, -1);
        int iB6 = haVarA.b(j.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int iB7 = haVarA.b(j.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int iC = haVarA.c(j.Toolbar_contentInsetLeft, 0);
        int iC2 = haVarA.c(j.Toolbar_contentInsetRight, 0);
        d();
        L l = this.t;
        l.h = false;
        if (iC != Integer.MIN_VALUE) {
            l.e = iC;
            l.f284a = iC;
        }
        if (iC2 != Integer.MIN_VALUE) {
            l.f = iC2;
            l.f285b = iC2;
        }
        if (iB6 != Integer.MIN_VALUE || iB7 != Integer.MIN_VALUE) {
            this.t.a(iB6, iB7);
        }
        this.u = haVarA.b(j.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.v = haVarA.b(j.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.f = haVarA.b(j.Toolbar_collapseIcon);
        this.g = haVarA.d(j.Toolbar_collapseContentDescription);
        CharSequence charSequenceD = haVarA.d(j.Toolbar_title);
        if (!TextUtils.isEmpty(charSequenceD)) {
            setTitle(charSequenceD);
        }
        CharSequence charSequenceD2 = haVarA.d(j.Toolbar_subtitle);
        if (!TextUtils.isEmpty(charSequenceD2)) {
            setSubtitle(charSequenceD2);
        }
        this.j = getContext();
        setPopupTheme(haVarA.f(j.Toolbar_popupTheme, 0));
        Drawable drawableB = haVarA.b(j.Toolbar_navigationIcon);
        if (drawableB != null) {
            setNavigationIcon(drawableB);
        }
        CharSequence charSequenceD3 = haVarA.d(j.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(charSequenceD3)) {
            setNavigationContentDescription(charSequenceD3);
        }
        Drawable drawableB2 = haVarA.b(j.Toolbar_logo);
        if (drawableB2 != null) {
            setLogo(drawableB2);
        }
        CharSequence charSequenceD4 = haVarA.d(j.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(charSequenceD4)) {
            setLogoDescription(charSequenceD4);
        }
        if (haVarA.e(j.Toolbar_titleTextColor)) {
            setTitleTextColor(haVarA.a(j.Toolbar_titleTextColor, -1));
        }
        if (haVarA.e(j.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(haVarA.a(j.Toolbar_subtitleTextColor, -1));
        }
        haVarA.f337b.recycle();
    }

    public final int a(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i = Build.VERSION.SDK_INT;
        int marginStart = marginLayoutParams.getMarginStart();
        int i2 = Build.VERSION.SDK_INT;
        return marginStart + marginLayoutParams.getMarginEnd();
    }
}
