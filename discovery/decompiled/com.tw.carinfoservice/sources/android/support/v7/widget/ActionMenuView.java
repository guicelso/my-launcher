package android.support.v7.widget;

import a.b.e.e.c.g;
import a.b.e.e.c.h;
import a.b.e.e.c.m;
import a.b.e.e.c.n;
import a.b.e.f.m0;
import a.b.e.f.t;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuView extends t implements g.b, n {
    public e A;
    public g q;
    public Context r;
    public int s;
    public a.b.e.f.c t;
    public m.a u;
    public g.a v;
    public boolean w;
    public int x;
    public int y;
    public int z;

    public interface a {
        boolean a();

        boolean b();
    }

    public static class b implements m.a {
        @Override // a.b.e.e.c.m.a
        public void a(g gVar, boolean z) {
        }

        @Override // a.b.e.e.c.m.a
        public boolean b(g gVar) {
            return false;
        }
    }

    public static class c extends t.a {

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public boolean f353c;

        @ViewDebug.ExportedProperty
        public int d;

        @ViewDebug.ExportedProperty
        public int e;

        @ViewDebug.ExportedProperty
        public boolean f;

        @ViewDebug.ExportedProperty
        public boolean g;
        public boolean h;

        public c(int i, int i2) {
            super(i, i2);
            this.f353c = false;
        }

        public c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public c(c cVar) {
            super(cVar);
            this.f353c = cVar.f353c;
        }

        public c(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public class d implements g.a {
        public d() {
        }

        public void a(g gVar) {
            g.a aVar = ActionMenuView.this.v;
            if (aVar != null) {
                ((d) aVar).a(gVar);
            }
        }
    }

    public interface e {
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.y = (int) (56.0f * f);
        this.z = (int) (f * 4.0f);
        this.r = context;
        this.s = 0;
    }

    public static int r(View view, int i, int i2, int i3, int i4) {
        c cVar = (c) view.getLayoutParams();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z = false;
        boolean z2 = actionMenuItemView != null && actionMenuItemView.d();
        int i5 = 2;
        if (i2 <= 0 || (z2 && i2 < 2)) {
            i5 = 0;
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2 * i, Integer.MIN_VALUE), iMakeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            int i6 = measuredWidth / i;
            if (measuredWidth % i != 0) {
                i6++;
            }
            if (!z2 || i6 >= 2) {
                i5 = i6;
            }
        }
        if (!cVar.f353c && z2) {
            z = true;
        }
        cVar.f = z;
        cVar.d = i5;
        view.measure(View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), iMakeMeasureSpec);
        return i5;
    }

    @Override // a.b.e.e.c.g.b
    public boolean b(h hVar) {
        return this.q.r(hVar, null, 0);
    }

    @Override // a.b.e.f.t, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof c);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // a.b.e.f.t
    /* JADX INFO: renamed from: g */
    public t.a generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    @Override // a.b.e.f.t, android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    public Menu getMenu() {
        if (this.q == null) {
            Context context = getContext();
            g gVar = new g(context);
            this.q = gVar;
            gVar.e = new d();
            a.b.e.f.c cVar = new a.b.e.f.c(context);
            this.t = cVar;
            cVar.l = true;
            cVar.m = true;
            m.a bVar = this.u;
            if (bVar == null) {
                bVar = new b();
            }
            cVar.f = bVar;
            this.q.b(cVar, this.r);
            a.b.e.f.c cVar2 = this.t;
            cVar2.h = this;
            this.q = cVar2.d;
        }
        return this.q;
    }

    public Drawable getOverflowIcon() {
        getMenu();
        a.b.e.f.c cVar = this.t;
        a.b.e.f.c.d dVar = cVar.i;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (cVar.k) {
            return cVar.j;
        }
        return null;
    }

    public int getPopupTheme() {
        return this.s;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // a.b.e.f.t, android.view.ViewGroup
    /* JADX INFO: renamed from: o, reason: merged with bridge method [inline-methods] */
    public c generateDefaultLayoutParams() {
        c cVar = new c(-2, -2);
        cVar.f324b = 16;
        return cVar;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a.b.e.f.c cVar = this.t;
        if (cVar != null) {
            cVar.j(false);
            if (this.t.i()) {
                this.t.d();
                this.t.m();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a.b.e.f.c cVar = this.t;
        if (cVar != null) {
            cVar.d();
            cVar.f();
        }
    }

    @Override // a.b.e.f.t, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        int paddingLeft;
        if (!this.w) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i5 = (i4 - i2) / 2;
        int dividerWidth = getDividerWidth();
        int i6 = i3 - i;
        int paddingRight = (i6 - getPaddingRight()) - getPaddingLeft();
        boolean zA = m0.a(this);
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                c cVar = (c) childAt.getLayoutParams();
                if (cVar.f353c) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (q(i9)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zA) {
                        paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) cVar).leftMargin;
                        width = paddingLeft + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) cVar).rightMargin;
                        paddingLeft = width - measuredWidth;
                    }
                    int i10 = i5 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i10, width, measuredHeight + i10);
                    paddingRight -= measuredWidth;
                    i7 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) cVar).leftMargin) + ((ViewGroup.MarginLayoutParams) cVar).rightMargin;
                    q(i9);
                    i8++;
                }
            }
        }
        if (childCount == 1 && i7 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i11 = (i6 / 2) - (measuredWidth2 / 2);
            int i12 = i5 - (measuredHeight2 / 2);
            childAt2.layout(i11, i12, measuredWidth2 + i11, measuredHeight2 + i12);
            return;
        }
        int i13 = i8 - (i7 ^ 1);
        int iMax = Math.max(0, i13 > 0 ? paddingRight / i13 : 0);
        if (zA) {
            int width2 = getWidth() - getPaddingRight();
            for (int i14 = 0; i14 < childCount; i14++) {
                View childAt3 = getChildAt(i14);
                c cVar2 = (c) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !cVar2.f353c) {
                    int i15 = width2 - ((ViewGroup.MarginLayoutParams) cVar2).rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i16 = i5 - (measuredHeight3 / 2);
                    childAt3.layout(i15 - measuredWidth3, i16, i15, measuredHeight3 + i16);
                    width2 = i15 - ((measuredWidth3 + ((ViewGroup.MarginLayoutParams) cVar2).leftMargin) + iMax);
                }
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        for (int i17 = 0; i17 < childCount; i17++) {
            View childAt4 = getChildAt(i17);
            c cVar3 = (c) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !cVar3.f353c) {
                int i18 = paddingLeft2 + ((ViewGroup.MarginLayoutParams) cVar3).leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i19 = i5 - (measuredHeight4 / 2);
                childAt4.layout(i18, i19, i18 + measuredWidth4, measuredHeight4 + i19);
                paddingLeft2 = measuredWidth4 + ((ViewGroup.MarginLayoutParams) cVar3).rightMargin + iMax + i18;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v33, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v39 */
    @Override // a.b.e.f.t, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        ?? r3;
        g gVar;
        boolean z2 = this.w;
        boolean z3 = View.MeasureSpec.getMode(i) == 1073741824;
        this.w = z3;
        if (z2 != z3) {
            this.x = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.w && (gVar = this.q) != null && size != this.x) {
            this.x = size;
            gVar.p(true);
        }
        int childCount = getChildCount();
        if (!this.w || childCount <= 0) {
            for (int i4 = 0; i4 < childCount; i4++) {
                c cVar = (c) getChildAt(i4).getLayoutParams();
                ((ViewGroup.MarginLayoutParams) cVar).rightMargin = 0;
                ((ViewGroup.MarginLayoutParams) cVar).leftMargin = 0;
            }
            super.onMeasure(i, i2);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        int size3 = View.MeasureSpec.getSize(i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int i5 = size2 - paddingRight;
        int i6 = this.y;
        int i7 = i5 / i6;
        int i8 = i5 % i6;
        if (i7 == 0) {
            setMeasuredDimension(i5, 0);
            return;
        }
        int i9 = (i8 / i7) + i6;
        int childCount2 = getChildCount();
        int i10 = 0;
        int iMax = 0;
        int iMax2 = 0;
        int i11 = 0;
        int i12 = 0;
        boolean z4 = false;
        long j = 0;
        while (i12 < childCount2) {
            View childAt = getChildAt(i12);
            int i13 = size3;
            int i14 = i5;
            if (childAt.getVisibility() != 8) {
                boolean z5 = childAt instanceof ActionMenuItemView;
                int i15 = i10 + 1;
                if (z5) {
                    int i16 = this.z;
                    r3 = 0;
                    childAt.setPadding(i16, 0, i16, 0);
                } else {
                    r3 = 0;
                }
                c cVar2 = (c) childAt.getLayoutParams();
                cVar2.h = r3;
                cVar2.e = r3;
                cVar2.d = r3;
                cVar2.f = r3;
                ((ViewGroup.MarginLayoutParams) cVar2).leftMargin = r3;
                ((ViewGroup.MarginLayoutParams) cVar2).rightMargin = r3;
                cVar2.g = z5 && ((ActionMenuItemView) childAt).d();
                int iR = r(childAt, i9, cVar2.f353c ? 1 : i7, childMeasureSpec, paddingBottom);
                iMax2 = Math.max(iMax2, iR);
                if (cVar2.f) {
                    i11++;
                }
                if (cVar2.f353c) {
                    z4 = true;
                }
                i7 -= iR;
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
                if (iR == 1) {
                    j |= (long) (1 << i12);
                }
                i10 = i15;
            }
            i12++;
            size3 = i13;
            i5 = i14;
        }
        int i17 = i5;
        int i18 = size3;
        boolean z6 = z4 && i10 == 2;
        boolean z7 = false;
        while (true) {
            if (i11 <= 0 || i7 <= 0) {
                i3 = iMax;
                z = z7;
                break;
            }
            int i19 = Integer.MAX_VALUE;
            int i20 = 0;
            int i21 = 0;
            long j2 = 0;
            while (i20 < childCount2) {
                int i22 = iMax;
                c cVar3 = (c) getChildAt(i20).getLayoutParams();
                boolean z8 = z7;
                if (cVar3.f) {
                    int i23 = cVar3.d;
                    if (i23 < i19) {
                        j2 = 1 << i20;
                        i19 = i23;
                        i21 = 1;
                    } else if (i23 == i19) {
                        i21++;
                        j2 |= 1 << i20;
                    }
                }
                i20++;
                z7 = z8;
                iMax = i22;
            }
            i3 = iMax;
            z = z7;
            j |= j2;
            if (i21 > i7) {
                break;
            }
            int i24 = i19 + 1;
            int i25 = 0;
            while (i25 < childCount2) {
                View childAt2 = getChildAt(i25);
                c cVar4 = (c) childAt2.getLayoutParams();
                int i26 = i11;
                long j3 = 1 << i25;
                if ((j2 & j3) == 0) {
                    if (cVar4.d == i24) {
                        j |= j3;
                    }
                    z6 = z6;
                } else {
                    if (z6 && cVar4.g && i7 == 1) {
                        int i27 = this.z;
                        childAt2.setPadding(i27 + i9, 0, i27, 0);
                    }
                    cVar4.d++;
                    cVar4.h = true;
                    i7--;
                }
                i25++;
                i11 = i26;
                z6 = z6;
            }
            iMax = i3;
            z7 = true;
        }
        boolean z9 = !z4 && i10 == 1;
        if (i7 > 0 && j != 0 && (i7 < i10 - 1 || z9 || iMax2 > 1)) {
            float fBitCount = Long.bitCount(j);
            if (!z9) {
                if ((j & 1) != 0 && !((c) getChildAt(0).getLayoutParams()).g) {
                    fBitCount -= 0.5f;
                }
                int i28 = childCount2 - 1;
                if ((j & ((long) (1 << i28))) != 0 && !((c) getChildAt(i28).getLayoutParams()).g) {
                    fBitCount -= 0.5f;
                }
            }
            int i29 = fBitCount > 0.0f ? (int) ((i7 * i9) / fBitCount) : 0;
            for (int i30 = 0; i30 < childCount2; i30++) {
                if ((j & ((long) (1 << i30))) != 0) {
                    View childAt3 = getChildAt(i30);
                    c cVar5 = (c) childAt3.getLayoutParams();
                    if (childAt3 instanceof ActionMenuItemView) {
                        cVar5.e = i29;
                        cVar5.h = true;
                        if (i30 == 0 && !cVar5.g) {
                            ((ViewGroup.MarginLayoutParams) cVar5).leftMargin = (-i29) / 2;
                        }
                    } else if (cVar5.f353c) {
                        cVar5.e = i29;
                        cVar5.h = true;
                        ((ViewGroup.MarginLayoutParams) cVar5).rightMargin = (-i29) / 2;
                    } else {
                        if (i30 != 0) {
                            ((ViewGroup.MarginLayoutParams) cVar5).leftMargin = i29 / 2;
                        }
                        if (i30 != childCount2 - 1) {
                            ((ViewGroup.MarginLayoutParams) cVar5).rightMargin = i29 / 2;
                        }
                    }
                    z = true;
                }
            }
        }
        if (z) {
            for (int i31 = 0; i31 < childCount2; i31++) {
                View childAt4 = getChildAt(i31);
                c cVar6 = (c) childAt4.getLayoutParams();
                if (cVar6.h) {
                    childAt4.measure(View.MeasureSpec.makeMeasureSpec((cVar6.d * i9) + cVar6.e, 1073741824), childMeasureSpec);
                }
            }
        }
        setMeasuredDimension(i17, mode != 1073741824 ? i3 : i18);
    }

    @Override // a.b.e.f.t
    /* JADX INFO: renamed from: p, reason: merged with bridge method [inline-methods] */
    public c generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        c cVar = layoutParams instanceof c ? new c((c) layoutParams) : new c(layoutParams);
        if (cVar.f324b <= 0) {
            cVar.f324b = 16;
        }
        return cVar;
    }

    public boolean q(int i) {
        boolean zB = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof a)) {
            zB = false | ((a) childAt).b();
        }
        return (i <= 0 || !(childAt2 instanceof a)) ? zB : zB | ((a) childAt2).a();
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.t.q = z;
    }

    public void setOnMenuItemClickListener(e eVar) {
        this.A = eVar;
    }

    public void setOverflowIcon(Drawable drawable) {
        getMenu();
        a.b.e.f.c cVar = this.t;
        a.b.e.f.c.d dVar = cVar.i;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
        } else {
            cVar.k = true;
            cVar.j = drawable;
        }
    }

    public void setOverflowReserved(boolean z) {
    }

    public void setPopupTheme(int i) {
        if (this.s != i) {
            this.s = i;
            if (i == 0) {
                this.r = getContext();
            } else {
                this.r = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setPresenter(a.b.e.f.c cVar) {
        this.t = cVar;
        cVar.h = this;
        this.q = cVar.d;
    }
}
