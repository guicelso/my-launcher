package android.support.v7.widget;

import a.b.e.e.a.k;
import a.b.e.e.a.n;
import a.b.e.e.a.t;
import a.b.e.e.a.u;
import a.b.e.f.C0015f;
import a.b.e.f.C0015f.d;
import a.b.e.f.E;
import a.b.e.f.ta;
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
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuView extends E implements k.b, u {
    public k p;
    public Context q;
    public int r;
    public C0015f s;
    public t.a t;
    public k.a u;
    public boolean v;
    public int w;
    public int x;
    public int y;
    public e z;

    public interface a {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    private static class b implements t.a {
        @Override // a.b.e.e.a.t.a
        public void a(k kVar, boolean z) {
        }

        @Override // a.b.e.e.a.t.a
        public boolean a(k kVar) {
            return false;
        }
    }

    public static class c extends E.a {

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        @ViewDebug.ExportedProperty
        public boolean f413c;

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
            this.f413c = false;
        }

        public c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public c(c cVar) {
            super(cVar);
            this.f413c = cVar.f413c;
        }

        public c(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class d implements k.a {
        public d() {
        }

        public void a(k kVar) {
            k.a aVar = ActionMenuView.this.u;
            if (aVar != null) {
                ((d) aVar).a(kVar);
            }
        }
    }

    public interface e {
    }

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.x = (int) (56.0f * f);
        this.y = (int) (f * 4.0f);
        this.q = context;
        this.r = 0;
    }

    public static int a(View view, int i, int i2, int i3, int i4) {
        c cVar = (c) view.getLayoutParams();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z = actionMenuItemView != null && actionMenuItemView.a();
        int i5 = 2;
        if (i2 <= 0 || (z && i2 < 2)) {
            i5 = 0;
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2 * i, Integer.MIN_VALUE), iMakeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            int i6 = measuredWidth / i;
            if (measuredWidth % i != 0) {
                i6++;
            }
            if (!z || i6 >= 2) {
                i5 = i6;
            }
        }
        cVar.f = !cVar.f413c && z;
        cVar.d = i5;
        view.measure(View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), iMakeMeasureSpec);
        return i5;
    }

    public void a() {
        C0015f c0015f = this.s;
        if (c0015f != null) {
            c0015f.a();
            C0015f.a aVar = c0015f.x;
            if (aVar != null) {
                aVar.a();
            }
        }
    }

    public void a(k kVar) {
        this.p = kVar;
    }

    public void a(t.a aVar, k.a aVar2) {
        this.t = aVar;
        this.u = aVar2;
    }

    public c b() {
        c cVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        cVarGenerateDefaultLayoutParams.f413c = true;
        return cVarGenerateDefaultLayoutParams;
    }

    public boolean c() {
        C0015f c0015f = this.s;
        return c0015f != null && c0015f.b();
    }

    @Override // a.b.e.f.E, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof c);
    }

    public k d() {
        return this.p;
    }

    public boolean d(int i) {
        boolean zNeedsDividerAfter = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof a)) {
            zNeedsDividerAfter = false | ((a) childAt).needsDividerAfter();
        }
        return (i <= 0 || !(childAt2 instanceof a)) ? zNeedsDividerAfter : zNeedsDividerAfter | ((a) childAt2).needsDividerBefore();
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public boolean e() {
        C0015f c0015f = this.s;
        return c0015f != null && c0015f.c();
    }

    @Override // a.b.e.f.E, android.view.ViewGroup
    public c generateDefaultLayoutParams() {
        c cVar = new c(-2, -2);
        cVar.f272b = 16;
        return cVar;
    }

    @Override // a.b.e.f.E, android.view.ViewGroup
    public c generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    @Override // a.b.e.f.E, android.view.ViewGroup
    public c generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        c cVar = layoutParams instanceof c ? new c((c) layoutParams) : new c(layoutParams);
        if (cVar.f272b <= 0) {
            cVar.f272b = 16;
        }
        return cVar;
    }

    public Menu getMenu() {
        if (this.p == null) {
            Context context = getContext();
            this.p = new k(context);
            this.p.a(new d());
            this.s = new C0015f(context);
            C0015f c0015f = this.s;
            c0015f.k = true;
            c0015f.l = true;
            t.a bVar = this.t;
            if (bVar == null) {
                bVar = new b();
            }
            c0015f.e = bVar;
            this.p.a(this.s, this.q);
            C0015f c0015f2 = this.s;
            c0015f2.g = this;
            a(c0015f2.f210c);
        }
        return this.p;
    }

    public Drawable getOverflowIcon() {
        getMenu();
        C0015f c0015f = this.s;
        C0015f.d dVar = c0015f.h;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (c0015f.j) {
            return c0015f.i;
        }
        return null;
    }

    public int getPopupTheme() {
        return this.r;
    }

    public int getWindowAnimations() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        int i;
        boolean z;
        super.onConfigurationChanged(configuration);
        C0015f c0015f = this.s;
        if (c0015f != null) {
            ViewGroup viewGroup = (ViewGroup) c0015f.g;
            ArrayList<n> arrayList = null;
            boolean z2 = false;
            if (viewGroup != null) {
                k kVar = c0015f.f210c;
                if (kVar != null) {
                    kVar.a();
                    ArrayList<n> arrayListC = c0015f.f210c.c();
                    int size = arrayListC.size();
                    i = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        n nVar = arrayListC.get(i2);
                        if (nVar.d()) {
                            View childAt = viewGroup.getChildAt(i);
                            n itemData = childAt instanceof u.a ? ((u.a) childAt).getItemData() : null;
                            View viewA = c0015f.a(nVar, childAt, viewGroup);
                            if (nVar != itemData) {
                                viewA.setPressed(false);
                                viewA.jumpDrawablesToCurrentState();
                            }
                            if (viewA != childAt) {
                                ViewGroup viewGroup2 = (ViewGroup) viewA.getParent();
                                if (viewGroup2 != null) {
                                    viewGroup2.removeView(viewA);
                                }
                                ((ViewGroup) c0015f.g).addView(viewA, i);
                            }
                            i++;
                        }
                    }
                } else {
                    i = 0;
                }
                while (i < viewGroup.getChildCount()) {
                    if (viewGroup.getChildAt(i) == c0015f.h) {
                        z = false;
                    } else {
                        viewGroup.removeViewAt(i);
                        z = true;
                    }
                    if (!z) {
                        i++;
                    }
                }
            }
            ((View) c0015f.g).requestLayout();
            k kVar2 = c0015f.f210c;
            if (kVar2 != null) {
                kVar2.a();
                ArrayList<n> arrayList2 = kVar2.j;
                int size2 = arrayList2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayList2.get(i3).a();
                }
            }
            k kVar3 = c0015f.f210c;
            if (kVar3 != null) {
                kVar3.a();
                arrayList = kVar3.k;
            }
            if (c0015f.k && arrayList != null) {
                int size3 = arrayList.size();
                if (size3 == 1) {
                    z2 = !arrayList.get(0).D;
                } else if (size3 > 0) {
                    z2 = true;
                }
            }
            if (z2) {
                if (c0015f.h == null) {
                    c0015f.h = c0015f.new d(c0015f.f208a);
                }
                ViewGroup viewGroup3 = (ViewGroup) c0015f.h.getParent();
                if (viewGroup3 != c0015f.g) {
                    if (viewGroup3 != null) {
                        viewGroup3.removeView(c0015f.h);
                    }
                    ActionMenuView actionMenuView = (ActionMenuView) c0015f.g;
                    actionMenuView.addView(c0015f.h, actionMenuView.b());
                }
            } else {
                C0015f.d dVar = c0015f.h;
                if (dVar != null) {
                    Object parent = dVar.getParent();
                    Object obj = c0015f.g;
                    if (parent == obj) {
                        ((ViewGroup) obj).removeView(c0015f.h);
                    }
                }
            }
            ((ActionMenuView) c0015f.g).setOverflowReserved(c0015f.k);
            if (this.s.b()) {
                this.s.a();
                this.s.c();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a();
    }

    @Override // a.b.e.f.E, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int width;
        int paddingLeft;
        if (!this.v) {
            if (this.d == 1) {
                b(i, i2, i3, i4);
                return;
            } else {
                a(i, i2, i3, i4);
                return;
            }
        }
        int childCount = getChildCount();
        int i7 = (i4 - i2) / 2;
        int dividerWidth = getDividerWidth();
        int i8 = i3 - i;
        int paddingRight = (i8 - getPaddingRight()) - getPaddingLeft();
        boolean zA = ta.a(this);
        int measuredWidth = paddingRight;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                c cVar = (c) childAt.getLayoutParams();
                if (cVar.f413c) {
                    int measuredWidth2 = childAt.getMeasuredWidth();
                    if (d(i11)) {
                        measuredWidth2 += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zA) {
                        paddingLeft = getPaddingLeft() + ((ViewGroup.MarginLayoutParams) cVar).leftMargin;
                        width = paddingLeft + measuredWidth2;
                    } else {
                        width = (getWidth() - getPaddingRight()) - ((ViewGroup.MarginLayoutParams) cVar).rightMargin;
                        paddingLeft = width - measuredWidth2;
                    }
                    int i12 = i7 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i12, width, measuredHeight + i12);
                    measuredWidth -= measuredWidth2;
                    i9 = 1;
                } else {
                    measuredWidth -= (childAt.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) cVar).leftMargin) + ((ViewGroup.MarginLayoutParams) cVar).rightMargin;
                    d(i11);
                    i10++;
                }
            }
        }
        if (childCount == 1 && i9 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth3 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i13 = (i8 / 2) - (measuredWidth3 / 2);
            int i14 = i7 - (measuredHeight2 / 2);
            childAt2.layout(i13, i14, measuredWidth3 + i13, measuredHeight2 + i14);
            return;
        }
        int i15 = i10 - (i9 ^ 1);
        if (i15 > 0) {
            i6 = measuredWidth / i15;
            i5 = 0;
        } else {
            i5 = 0;
            i6 = 0;
        }
        int iMax = Math.max(i5, i6);
        if (zA) {
            int width2 = getWidth() - getPaddingRight();
            while (i5 < childCount) {
                View childAt3 = getChildAt(i5);
                c cVar2 = (c) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !cVar2.f413c) {
                    int i16 = width2 - ((ViewGroup.MarginLayoutParams) cVar2).rightMargin;
                    int measuredWidth4 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i7 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth4, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth4 + ((ViewGroup.MarginLayoutParams) cVar2).leftMargin) + iMax);
                }
                i5++;
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        while (i5 < childCount) {
            View childAt4 = getChildAt(i5);
            c cVar3 = (c) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !cVar3.f413c) {
                int i18 = paddingLeft2 + ((ViewGroup.MarginLayoutParams) cVar3).leftMargin;
                int measuredWidth5 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i19 = i7 - (measuredHeight4 / 2);
                childAt4.layout(i18, i19, i18 + measuredWidth5, measuredHeight4 + i19);
                paddingLeft2 = measuredWidth5 + ((ViewGroup.MarginLayoutParams) cVar3).rightMargin + iMax + i18;
            }
            i5++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:109:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:121:0x020a  */
    /* JADX WARN: Code duplicated, block: B:122:0x0210  */
    /* JADX WARN: Code duplicated, block: B:125:0x0216  */
    /* JADX WARN: Code duplicated, block: B:127:0x0222  */
    /* JADX WARN: Code duplicated, block: B:128:0x0224  */
    /* JADX WARN: Code duplicated, block: B:130:0x0232  */
    /* JADX WARN: Code duplicated, block: B:136:0x0244  */
    /* JADX WARN: Code duplicated, block: B:138:0x0248  */
    /* JADX WARN: Code duplicated, block: B:140:0x0254  */
    /* JADX WARN: Code duplicated, block: B:142:0x0257  */
    /* JADX WARN: Code duplicated, block: B:145:0x025f  */
    /* JADX WARN: Code duplicated, block: B:149:0x026a  */
    /* JADX WARN: Code duplicated, block: B:151:0x026d  */
    /* JADX WARN: Code duplicated, block: B:153:0x027b  */
    /* JADX WARN: Code duplicated, block: B:154:0x027e  */
    /* JADX WARN: Code duplicated, block: B:158:0x0296  */
    /* JADX WARN: Code duplicated, block: B:159:0x0299  */
    /* JADX WARN: Code duplicated, block: B:187:0x0263 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v18 */
    @Override // a.b.e.f.E, android.view.View
    public void onMeasure(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        boolean z2;
        int i5;
        int i6;
        View childAt;
        c cVar;
        float fBitCount;
        int i7;
        boolean z3;
        int i8;
        View childAt2;
        c cVar2;
        int i9;
        ?? r6;
        k kVar;
        boolean z4 = this.v;
        this.v = View.MeasureSpec.getMode(i) == 1073741824;
        if (z4 != this.v) {
            this.w = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.v && (kVar = this.p) != null && size != this.w) {
            this.w = size;
            kVar.b(true);
        }
        int childCount = getChildCount();
        if (!this.v || childCount <= 0) {
            for (int i10 = 0; i10 < childCount; i10++) {
                c cVar3 = (c) getChildAt(i10).getLayoutParams();
                ((ViewGroup.MarginLayoutParams) cVar3).rightMargin = 0;
                ((ViewGroup.MarginLayoutParams) cVar3).leftMargin = 0;
            }
            if (this.d == 1) {
                d(i, i2);
                return;
            } else {
                c(i, i2);
                return;
            }
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i);
        int size3 = View.MeasureSpec.getSize(i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
        int i11 = size2 - paddingRight;
        int i12 = this.x;
        int i13 = i11 / i12;
        int i14 = i11 % i12;
        if (i13 == 0) {
            setMeasuredDimension(i11, 0);
            return;
        }
        int i15 = (i14 / i13) + i12;
        int childCount2 = getChildCount();
        int i16 = i13;
        int iMax = 0;
        int i17 = 0;
        int i18 = 0;
        boolean z5 = false;
        int i19 = 0;
        int i20 = 0;
        long j = 0;
        while (i17 < childCount2) {
            View childAt3 = getChildAt(i17);
            if (childAt3.getVisibility() == 8) {
                size3 = size3;
            } else {
                boolean z6 = childAt3 instanceof ActionMenuItemView;
                i19++;
                if (z6) {
                    int i21 = this.y;
                    r6 = 0;
                    childAt3.setPadding(i21, 0, i21, 0);
                } else {
                    r6 = 0;
                }
                c cVar4 = (c) childAt3.getLayoutParams();
                cVar4.h = r6;
                cVar4.e = r6;
                cVar4.d = r6;
                cVar4.f = r6;
                ((ViewGroup.MarginLayoutParams) cVar4).leftMargin = r6;
                ((ViewGroup.MarginLayoutParams) cVar4).rightMargin = r6;
                cVar4.g = z6 && ((ActionMenuItemView) childAt3).a();
                int iA = a(childAt3, i15, cVar4.f413c ? 1 : i16, childMeasureSpec, paddingBottom);
                iMax = Math.max(iMax, iA);
                if (cVar4.f) {
                    i20++;
                }
                if (cVar4.f413c) {
                    z5 = true;
                }
                i16 -= iA;
                int iMax2 = Math.max(i18, childAt3.getMeasuredHeight());
                if (iA == 1) {
                    j |= (long) (1 << i17);
                }
                i18 = iMax2;
            }
            i17++;
            size3 = size3;
        }
        int i22 = size3;
        boolean z7 = z5 && i19 == 2;
        int i23 = i16;
        boolean z8 = false;
        while (true) {
            if (i20 <= 0 || i23 <= 0) {
                z = z8;
                i3 = i18;
                break;
            }
            int i24 = Integer.MAX_VALUE;
            int i25 = 0;
            int i26 = 0;
            long j2 = 0;
            while (i25 < childCount2) {
                boolean z9 = z8;
                c cVar5 = (c) getChildAt(i25).getLayoutParams();
                int i27 = i18;
                if (cVar5.f) {
                    int i28 = cVar5.d;
                    if (i28 < i24) {
                        i24 = i28;
                        j2 = 1 << i25;
                        i26 = 1;
                    } else if (i28 == i24) {
                        j2 |= 1 << i25;
                        i26++;
                    }
                }
                i25++;
                i18 = i27;
                z8 = z9;
            }
            z = z8;
            i3 = i18;
            j |= j2;
            if (i26 > i23) {
                break;
            }
            int i29 = i24 + 1;
            int i30 = i23;
            int i31 = 0;
            while (i31 < childCount2) {
                View childAt4 = getChildAt(i31);
                c cVar6 = (c) childAt4.getLayoutParams();
                int i32 = i11;
                int i33 = mode;
                long j3 = 1 << i31;
                if ((j2 & j3) == 0) {
                    if (cVar6.d == i29) {
                        j |= j3;
                    }
                    i29 = i29;
                } else {
                    if (z7 && cVar6.g && i30 == 1) {
                        int i34 = this.y;
                        childAt4.setPadding(i34 + i15, 0, i34, 0);
                    }
                    cVar6.d++;
                    cVar6.h = true;
                    i30--;
                }
                i31++;
                mode = i33;
                i29 = i29;
                i11 = i32;
            }
            i23 = i30;
            i18 = i3;
            z8 = true;
        }
        if (!z5) {
            i4 = 1;
            z2 = i19 == 1;
            if (i23 > 0 && j != 0 && (i23 < i19 - i4 || z2 || iMax > i4)) {
                fBitCount = Long.bitCount(j);
                if (!z2) {
                    if ((j & 1) != 0 && !((c) getChildAt(0).getLayoutParams()).g) {
                        fBitCount -= 0.5f;
                    }
                    i9 = childCount2 - 1;
                    if ((j & ((long) (1 << i9))) != 0 && !((c) getChildAt(i9).getLayoutParams()).g) {
                        fBitCount -= 0.5f;
                    }
                }
                if (fBitCount > 0.0f) {
                    i7 = (int) ((i23 * i15) / fBitCount);
                } else {
                    i7 = 0;
                }
                z3 = z;
                for (i8 = 0; i8 < childCount2; i8++) {
                    if ((j & ((long) (1 << i8))) == 0) {
                        childAt2 = getChildAt(i8);
                        cVar2 = (c) childAt2.getLayoutParams();
                        if (childAt2 instanceof ActionMenuItemView) {
                            cVar2.e = i7;
                            cVar2.h = true;
                            if (i8 == 0 && !cVar2.g) {
                                ((ViewGroup.MarginLayoutParams) cVar2).leftMargin = (-i7) / 2;
                            }
                        } else if (cVar2.f413c) {
                            cVar2.e = i7;
                            cVar2.h = true;
                            ((ViewGroup.MarginLayoutParams) cVar2).rightMargin = (-i7) / 2;
                        } else {
                            if (i8 != 0) {
                                ((ViewGroup.MarginLayoutParams) cVar2).leftMargin = i7 / 2;
                            }
                            if (i8 != childCount2 - 1) {
                                ((ViewGroup.MarginLayoutParams) cVar2).rightMargin = i7 / 2;
                            }
                        }
                        z3 = true;
                    }
                }
                z = z3;
            }
            if (z) {
                for (i6 = 0; i6 < childCount2; i6++) {
                    childAt = getChildAt(i6);
                    cVar = (c) childAt.getLayoutParams();
                    if (!cVar.h) {
                        childAt.measure(View.MeasureSpec.makeMeasureSpec((cVar.d * i15) + cVar.e, 1073741824), childMeasureSpec);
                    }
                }
            }
            if (mode != 1073741824) {
                i5 = i3;
            } else {
                i5 = i22;
            }
            setMeasuredDimension(i11, i5);
        }
        i4 = 1;
        if (i23 > 0) {
            fBitCount = Long.bitCount(j);
            if (!z2) {
                if ((j & 1) != 0) {
                    fBitCount -= 0.5f;
                }
                i9 = childCount2 - 1;
                if ((j & ((long) (1 << i9))) != 0) {
                    fBitCount -= 0.5f;
                }
            }
            if (fBitCount > 0.0f) {
                i7 = (int) ((i23 * i15) / fBitCount);
            } else {
                i7 = 0;
            }
            z3 = z;
            while (i8 < childCount2) {
                if ((j & ((long) (1 << i8))) == 0) {
                    childAt2 = getChildAt(i8);
                    cVar2 = (c) childAt2.getLayoutParams();
                    if (childAt2 instanceof ActionMenuItemView) {
                        cVar2.e = i7;
                        cVar2.h = true;
                        if (i8 == 0) {
                            ((ViewGroup.MarginLayoutParams) cVar2).leftMargin = (-i7) / 2;
                        }
                    } else if (cVar2.f413c) {
                        cVar2.e = i7;
                        cVar2.h = true;
                        ((ViewGroup.MarginLayoutParams) cVar2).rightMargin = (-i7) / 2;
                    } else {
                        if (i8 != 0) {
                            ((ViewGroup.MarginLayoutParams) cVar2).leftMargin = i7 / 2;
                        }
                        if (i8 != childCount2 - 1) {
                            ((ViewGroup.MarginLayoutParams) cVar2).rightMargin = i7 / 2;
                        }
                    }
                    z3 = true;
                }
            }
            z = z3;
        }
        if (z) {
            while (i6 < childCount2) {
                childAt = getChildAt(i6);
                cVar = (c) childAt.getLayoutParams();
                if (!cVar.h) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((cVar.d * i15) + cVar.e, 1073741824), childMeasureSpec);
                }
            }
        }
        if (mode != 1073741824) {
            i5 = i3;
        } else {
            i5 = i22;
        }
        setMeasuredDimension(i11, i5);
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.s.s = z;
    }

    public void setOnMenuItemClickListener(e eVar) {
        this.z = eVar;
    }

    public void setOverflowIcon(Drawable drawable) {
        getMenu();
        C0015f c0015f = this.s;
        C0015f.d dVar = c0015f.h;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
        } else {
            c0015f.j = true;
            c0015f.i = drawable;
        }
    }

    public void setOverflowReserved(boolean z) {
    }

    public void setPopupTheme(int i) {
        if (this.r != i) {
            this.r = i;
            if (i == 0) {
                this.q = getContext();
            } else {
                this.q = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setPresenter(C0015f c0015f) {
        this.s = c0015f;
        C0015f c0015f2 = this.s;
        c0015f2.g = this;
        a(c0015f2.f210c);
    }

    @Override // a.b.e.e.a.k.b
    public boolean a(n nVar) {
        return this.p.a(nVar, (t) null, 0);
    }
}
