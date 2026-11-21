package android.support.v7.widget;

import a.b.d.h.j;
import a.b.e.e.c.g;
import a.b.e.e.c.h;
import a.b.e.e.c.m;
import a.b.e.e.c.q;
import a.b.e.f.g0;
import a.b.e.f.h0;
import a.b.e.f.j0;
import a.b.e.f.m0;
import a.b.e.f.o;
import a.b.e.f.z;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
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
import com.tw.carinfoservice.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    public int A;
    public int B;
    public boolean C;
    public boolean D;
    public final ArrayList<View> E;
    public final ArrayList<View> F;
    public final int[] G;
    public e H;
    public final ActionMenuView.e I;
    public j0 J;
    public c K;
    public boolean L;
    public final Runnable M;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ActionMenuView f380b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public TextView f381c;
    public TextView d;
    public ImageButton e;
    public ImageView f;
    public Drawable g;
    public CharSequence h;
    public ImageButton i;
    public View j;
    public Context k;
    public int l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public z u;
    public int v;
    public int w;
    public int x;
    public CharSequence y;
    public CharSequence z;

    public class a implements ActionMenuView.e {
        public a() {
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ActionMenuView actionMenuView = Toolbar.this.f380b;
            if (actionMenuView != null) {
                a.b.e.f.c cVar = actionMenuView.t;
                if (cVar == null || !cVar.m()) {
                }
            }
        }
    }

    public class c implements m {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public g f384b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public h f385c;

        public c() {
        }

        @Override // a.b.e.e.c.m
        public void a(g gVar, boolean z) {
        }

        @Override // a.b.e.e.c.m
        public boolean b(q qVar) {
            return false;
        }

        @Override // a.b.e.e.c.m
        public boolean e(g gVar, h hVar) {
            Toolbar toolbar = Toolbar.this;
            if (toolbar.i == null) {
                a.b.e.f.h hVar2 = new a.b.e.f.h(toolbar.getContext(), null, R.attr.toolbarNavigationButtonStyle);
                toolbar.i = hVar2;
                hVar2.setImageDrawable(toolbar.g);
                toolbar.i.setContentDescription(toolbar.h);
                d dVarGenerateDefaultLayoutParams = toolbar.generateDefaultLayoutParams();
                dVarGenerateDefaultLayoutParams.f173a = (toolbar.o & 112) | 8388611;
                dVarGenerateDefaultLayoutParams.f386b = 2;
                toolbar.i.setLayoutParams(dVarGenerateDefaultLayoutParams);
                toolbar.i.setOnClickListener(new h0(toolbar));
            }
            ViewParent parent = Toolbar.this.i.getParent();
            Toolbar toolbar2 = Toolbar.this;
            if (parent != toolbar2) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(toolbar2.i);
                }
                Toolbar toolbar3 = Toolbar.this;
                toolbar3.addView(toolbar3.i);
            }
            Toolbar.this.j = hVar.getActionView();
            this.f385c = hVar;
            ViewParent parent2 = Toolbar.this.j.getParent();
            Toolbar toolbar4 = Toolbar.this;
            if (parent2 != toolbar4) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(toolbar4.j);
                }
                d dVarGenerateDefaultLayoutParams2 = Toolbar.this.generateDefaultLayoutParams();
                Toolbar toolbar5 = Toolbar.this;
                dVarGenerateDefaultLayoutParams2.f173a = 8388611 | (toolbar5.o & 112);
                dVarGenerateDefaultLayoutParams2.f386b = 2;
                toolbar5.j.setLayoutParams(dVarGenerateDefaultLayoutParams2);
                Toolbar toolbar6 = Toolbar.this;
                toolbar6.addView(toolbar6.j);
            }
            Toolbar toolbar7 = Toolbar.this;
            int childCount = toolbar7.getChildCount();
            while (true) {
                childCount--;
                if (childCount < 0) {
                    break;
                }
                View childAt = toolbar7.getChildAt(childCount);
                if (((d) childAt.getLayoutParams()).f386b != 2 && childAt != toolbar7.f380b) {
                    toolbar7.removeViewAt(childCount);
                    toolbar7.F.add(childAt);
                }
            }
            Toolbar.this.requestLayout();
            hVar.C = true;
            hVar.n.p(false);
            KeyEvent.Callback callback = Toolbar.this.j;
            if (callback instanceof a.b.e.e.a) {
                ((a.b.e.e.a) callback).a();
            }
            return true;
        }

        @Override // a.b.e.e.c.m
        public boolean g() {
            return false;
        }

        @Override // a.b.e.e.c.m
        public boolean h(g gVar, h hVar) {
            KeyEvent.Callback callback = Toolbar.this.j;
            if (callback instanceof a.b.e.e.a) {
                ((a.b.e.e.a) callback).c();
            }
            Toolbar toolbar = Toolbar.this;
            toolbar.removeView(toolbar.j);
            Toolbar toolbar2 = Toolbar.this;
            toolbar2.removeView(toolbar2.i);
            Toolbar toolbar3 = Toolbar.this;
            toolbar3.j = null;
            int size = toolbar3.F.size();
            while (true) {
                size--;
                if (size < 0) {
                    toolbar3.F.clear();
                    this.f385c = null;
                    Toolbar.this.requestLayout();
                    hVar.C = false;
                    hVar.n.p(false);
                    return true;
                }
                toolbar3.addView(toolbar3.F.get(size));
            }
        }

        @Override // a.b.e.e.c.m
        public void j(boolean z) {
            if (this.f385c != null) {
                g gVar = this.f384b;
                boolean z2 = false;
                if (gVar != null) {
                    int size = gVar.size();
                    for (int i = 0; i < size; i++) {
                        if (this.f384b.getItem(i) == this.f385c) {
                            z2 = true;
                            break;
                        }
                    }
                }
                if (z2) {
                    return;
                }
                h(this.f384b, this.f385c);
            }
        }

        @Override // a.b.e.e.c.m
        public void l(Context context, g gVar) {
            h hVar;
            g gVar2 = this.f384b;
            if (gVar2 != null && (hVar = this.f385c) != null) {
                gVar2.d(hVar);
            }
            this.f384b = gVar;
        }
    }

    public static class d extends a.b.e.a.a {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f386b;

        public d(int i, int i2) {
            super(i, i2);
            this.f386b = 0;
            this.f173a = 8388627;
        }

        public d(a.b.e.a.a aVar) {
            super(aVar);
            this.f386b = 0;
        }

        public d(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.f386b = 0;
        }

        public d(d dVar) {
            super((a.b.e.a.a) dVar);
            this.f386b = 0;
            this.f386b = dVar.f386b;
        }

        public d(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.f386b = 0;
        }

        public d(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.f386b = 0;
            ((ViewGroup.MarginLayoutParams) this).leftMargin = marginLayoutParams.leftMargin;
            ((ViewGroup.MarginLayoutParams) this).topMargin = marginLayoutParams.topMargin;
            ((ViewGroup.MarginLayoutParams) this).rightMargin = marginLayoutParams.rightMargin;
            ((ViewGroup.MarginLayoutParams) this).bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public interface e {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public static class f extends a.b.d.h.a {
        public static final Parcelable.Creator<f> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f387c;
        public boolean d;

        public static class a implements Parcelable.ClassLoaderCreator<f> {
            @Override // android.os.Parcelable.Creator
            public Object createFromParcel(Parcel parcel) {
                return new f(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public Object[] newArray(int i) {
                return new f[i];
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public f createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new f(parcel, classLoader);
            }
        }

        public f(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f387c = parcel.readInt();
            this.d = parcel.readInt() != 0;
        }

        public f(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // a.b.d.h.a, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f142a, i);
            parcel.writeInt(this.f387c);
            parcel.writeInt(this.d ? 1 : 0);
        }
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.toolbarStyle);
        this.x = 8388627;
        this.E = new ArrayList<>();
        this.F = new ArrayList<>();
        this.G = new int[2];
        this.I = new a();
        this.M = new b();
        g0 g0VarM = g0.m(getContext(), attributeSet, a.b.e.b.b.w, R.attr.toolbarStyle, 0);
        this.m = g0VarM.j(27, 0);
        this.n = g0VarM.j(18, 0);
        this.x = g0VarM.f278b.getInteger(0, this.x);
        this.o = g0VarM.f278b.getInteger(2, 48);
        int iD = g0VarM.d(21, 0);
        iD = g0VarM.l(26) ? g0VarM.d(26, iD) : iD;
        this.t = iD;
        this.s = iD;
        this.r = iD;
        this.q = iD;
        int iD2 = g0VarM.d(24, -1);
        if (iD2 >= 0) {
            this.q = iD2;
        }
        int iD3 = g0VarM.d(23, -1);
        if (iD3 >= 0) {
            this.r = iD3;
        }
        int iD4 = g0VarM.d(25, -1);
        if (iD4 >= 0) {
            this.s = iD4;
        }
        int iD5 = g0VarM.d(22, -1);
        if (iD5 >= 0) {
            this.t = iD5;
        }
        this.p = g0VarM.e(13, -1);
        int iD6 = g0VarM.d(9, Integer.MIN_VALUE);
        int iD7 = g0VarM.d(5, Integer.MIN_VALUE);
        int iE = g0VarM.e(7, 0);
        int iE2 = g0VarM.e(8, 0);
        c();
        z zVar = this.u;
        zVar.h = false;
        if (iE != Integer.MIN_VALUE) {
            zVar.e = iE;
            zVar.f334a = iE;
        }
        if (iE2 != Integer.MIN_VALUE) {
            zVar.f = iE2;
            zVar.f335b = iE2;
        }
        if (iD6 != Integer.MIN_VALUE || iD7 != Integer.MIN_VALUE) {
            zVar.a(iD6, iD7);
        }
        this.v = g0VarM.d(10, Integer.MIN_VALUE);
        this.w = g0VarM.d(6, Integer.MIN_VALUE);
        this.g = g0VarM.f(4);
        this.h = g0VarM.k(3);
        CharSequence charSequenceK = g0VarM.k(20);
        if (!TextUtils.isEmpty(charSequenceK)) {
            setTitle(charSequenceK);
        }
        CharSequence charSequenceK2 = g0VarM.k(17);
        if (!TextUtils.isEmpty(charSequenceK2)) {
            setSubtitle(charSequenceK2);
        }
        this.k = getContext();
        setPopupTheme(g0VarM.j(16, 0));
        Drawable drawableF = g0VarM.f(15);
        if (drawableF != null) {
            setNavigationIcon(drawableF);
        }
        CharSequence charSequenceK3 = g0VarM.k(14);
        if (!TextUtils.isEmpty(charSequenceK3)) {
            setNavigationContentDescription(charSequenceK3);
        }
        Drawable drawableF2 = g0VarM.f(11);
        if (drawableF2 != null) {
            setLogo(drawableF2);
        }
        CharSequence charSequenceK4 = g0VarM.k(12);
        if (!TextUtils.isEmpty(charSequenceK4)) {
            setLogoDescription(charSequenceK4);
        }
        if (g0VarM.l(28)) {
            setTitleTextColor(g0VarM.b(28, -1));
        }
        if (g0VarM.l(19)) {
            setSubtitleTextColor(g0VarM.b(19, -1));
        }
        g0VarM.f278b.recycle();
    }

    private MenuInflater getMenuInflater() {
        return new a.b.e.e.b(getContext());
    }

    public final void a(List<View> list, int i) {
        int i2 = j.f151a;
        boolean z = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, getLayoutDirection());
        list.clear();
        if (!z) {
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                d dVar = (d) childAt.getLayoutParams();
                if (dVar.f386b == 0 && q(childAt) && h(dVar.f173a) == absoluteGravity) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            View childAt2 = getChildAt(i4);
            d dVar2 = (d) childAt2.getLayoutParams();
            if (dVar2.f386b == 0 && q(childAt2) && h(dVar2.f173a) == absoluteGravity) {
                list.add(childAt2);
            }
        }
    }

    public final void b(View view, boolean z) {
        d dVarGenerateLayoutParams;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            dVarGenerateLayoutParams = generateDefaultLayoutParams();
        } else {
            dVarGenerateLayoutParams = !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (d) layoutParams;
        }
        dVarGenerateLayoutParams.f386b = 1;
        if (!z || this.j == null) {
            addView(view, dVarGenerateLayoutParams);
        } else {
            view.setLayoutParams(dVarGenerateLayoutParams);
            this.F.add(view);
        }
    }

    public final void c() {
        if (this.u == null) {
            this.u = new z();
        }
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof d);
    }

    public final void d() {
        if (this.f380b == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext(), null);
            this.f380b = actionMenuView;
            actionMenuView.setPopupTheme(this.l);
            this.f380b.setOnMenuItemClickListener(this.I);
            ActionMenuView actionMenuView2 = this.f380b;
            actionMenuView2.u = null;
            actionMenuView2.v = null;
            d dVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            dVarGenerateDefaultLayoutParams.f173a = 8388613 | (this.o & 112);
            this.f380b.setLayoutParams(dVarGenerateDefaultLayoutParams);
            b(this.f380b, false);
        }
        ActionMenuView actionMenuView3 = this.f380b;
        if (actionMenuView3.q == null) {
            g gVar = (g) actionMenuView3.getMenu();
            if (this.K == null) {
                this.K = new c();
            }
            this.f380b.setExpandedActionViewsExclusive(true);
            gVar.b(this.K, this.k);
        }
    }

    public final void e() {
        if (this.e == null) {
            this.e = new a.b.e.f.h(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            d dVarGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            dVarGenerateDefaultLayoutParams.f173a = 8388611 | (this.o & 112);
            this.e.setLayoutParams(dVarGenerateDefaultLayoutParams);
        }
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public d generateDefaultLayoutParams() {
        return new d(-2, -2);
    }

    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public d generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof d) {
            return new d((d) layoutParams);
        }
        if (layoutParams instanceof a.b.e.a.a) {
            return new d((a.b.e.a.a) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new d((ViewGroup.MarginLayoutParams) layoutParams) : new d(layoutParams);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new d(getContext(), attributeSet);
    }

    public int getContentInsetEnd() {
        z zVar = this.u;
        if (zVar != null) {
            return zVar.g ? zVar.f334a : zVar.f335b;
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int i = this.w;
        return i != Integer.MIN_VALUE ? i : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        z zVar = this.u;
        if (zVar != null) {
            return zVar.f334a;
        }
        return 0;
    }

    public int getContentInsetRight() {
        z zVar = this.u;
        if (zVar != null) {
            return zVar.f335b;
        }
        return 0;
    }

    public int getContentInsetStart() {
        z zVar = this.u;
        if (zVar != null) {
            return zVar.g ? zVar.f335b : zVar.f334a;
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i = this.v;
        return i != Integer.MIN_VALUE ? i : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        g gVar;
        ActionMenuView actionMenuView = this.f380b;
        return actionMenuView != null && (gVar = actionMenuView.q) != null && gVar.hasVisibleItems() ? Math.max(getContentInsetEnd(), Math.max(this.w, 0)) : getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        int i = j.f151a;
        return getLayoutDirection() == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        int i = j.f151a;
        return getLayoutDirection() == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.v, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        ImageView imageView = this.f;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.f;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        d();
        return this.f380b.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.e;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.e;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public a.b.e.f.c getOuterActionMenuPresenter() {
        return null;
    }

    public Drawable getOverflowIcon() {
        d();
        return this.f380b.getOverflowIcon();
    }

    public Context getPopupContext() {
        return this.k;
    }

    public int getPopupTheme() {
        return this.l;
    }

    public CharSequence getSubtitle() {
        return this.z;
    }

    public CharSequence getTitle() {
        return this.y;
    }

    public int getTitleMarginBottom() {
        return this.t;
    }

    public int getTitleMarginEnd() {
        return this.r;
    }

    public int getTitleMarginStart() {
        return this.q;
    }

    public int getTitleMarginTop() {
        return this.s;
    }

    public o getWrapper() {
        if (this.J == null) {
            this.J = new j0(this, true);
        }
        return this.J;
    }

    public final int h(int i) {
        int i2 = j.f151a;
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(i, layoutDirection) & 7;
        if (absoluteGravity == 1 || absoluteGravity == 3 || absoluteGravity == 5) {
            return absoluteGravity;
        }
        return layoutDirection == 1 ? 5 : 3;
    }

    public final int i(View view, int i) {
        d dVar = (d) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int i3 = dVar.f173a & 112;
        if (i3 != 16 && i3 != 48 && i3 != 80) {
            i3 = this.x & 112;
        }
        if (i3 == 48) {
            return getPaddingTop() - i2;
        }
        if (i3 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) dVar).bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i4 = ((ViewGroup.MarginLayoutParams) dVar).topMargin;
        if (iMax < i4) {
            iMax = i4;
        } else {
            int i5 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            int i6 = ((ViewGroup.MarginLayoutParams) dVar).bottomMargin;
            if (i5 < i6) {
                iMax = Math.max(0, iMax - (i6 - i5));
            }
        }
        return paddingTop + iMax;
    }

    public final int j(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
    }

    public final int k(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final boolean l(View view) {
        return view.getParent() == this || this.F.contains(view);
    }

    public final int m(View view, int i, int[] iArr, int i2) {
        d dVar = (d) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) dVar).leftMargin - iArr[0];
        int iMax = Math.max(0, i3) + i;
        iArr[0] = Math.max(0, -i3);
        int i4 = i(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, i4, iMax + measuredWidth, view.getMeasuredHeight() + i4);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) dVar).rightMargin + iMax;
    }

    public final int n(View view, int i, int[] iArr, int i2) {
        d dVar = (d) view.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) dVar).rightMargin - iArr[1];
        int iMax = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int i4 = i(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, i4, iMax, view.getMeasuredHeight() + i4);
        return iMax - (measuredWidth + ((ViewGroup.MarginLayoutParams) dVar).leftMargin);
    }

    public final int o(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i6) + Math.max(0, i5);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(ViewGroup.getChildMeasureSpec(i, getPaddingRight() + getPaddingLeft() + iMax + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.M);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.D = false;
        }
        if (!this.D) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.D = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.D = false;
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0236  */
    /* JADX WARN: Code duplicated, block: B:101:0x0259  */
    /* JADX WARN: Code duplicated, block: B:103:0x025c  */
    /* JADX WARN: Code duplicated, block: B:104:0x027e  */
    /* JADX WARN: Code duplicated, block: B:106:0x0281  */
    /* JADX WARN: Code duplicated, block: B:107:0x0286  */
    /* JADX WARN: Code duplicated, block: B:110:0x0297 A[LOOP:0: B:109:0x0295->B:110:0x0297, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:113:0x02b9 A[LOOP:1: B:112:0x02b7->B:113:0x02b9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:116:0x02de A[LOOP:2: B:115:0x02dc->B:116:0x02de, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:120:0x031f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:121:0x0321  */
    /* JADX WARN: Code duplicated, block: B:122:0x0325  */
    /* JADX WARN: Code duplicated, block: B:125:0x0330 A[LOOP:3: B:124:0x032e->B:125:0x0330, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:20:0x005f  */
    /* JADX WARN: Code duplicated, block: B:22:0x0063  */
    /* JADX WARN: Code duplicated, block: B:23:0x0068  */
    /* JADX WARN: Code duplicated, block: B:26:0x0074  */
    /* JADX WARN: Code duplicated, block: B:28:0x0078  */
    /* JADX WARN: Code duplicated, block: B:29:0x007d  */
    /* JADX WARN: Code duplicated, block: B:32:0x00af  */
    /* JADX WARN: Code duplicated, block: B:34:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:35:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:38:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:40:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:41:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:44:0x00df  */
    /* JADX WARN: Code duplicated, block: B:45:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:47:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:48:0x0115  */
    /* JADX WARN: Code duplicated, block: B:53:0x0122 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:54:0x0124  */
    /* JADX WARN: Code duplicated, block: B:55:0x0127  */
    /* JADX WARN: Code duplicated, block: B:57:0x012b  */
    /* JADX WARN: Code duplicated, block: B:58:0x012e  */
    /* JADX WARN: Code duplicated, block: B:61:0x013e  */
    /* JADX WARN: Code duplicated, block: B:63:0x0146 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:70:0x015f  */
    /* JADX WARN: Code duplicated, block: B:72:0x0163  */
    /* JADX WARN: Code duplicated, block: B:74:0x0175  */
    /* JADX WARN: Code duplicated, block: B:75:0x0178  */
    /* JADX WARN: Code duplicated, block: B:77:0x0183  */
    /* JADX WARN: Code duplicated, block: B:79:0x018f  */
    /* JADX WARN: Code duplicated, block: B:80:0x019d  */
    /* JADX WARN: Code duplicated, block: B:82:0x01ae A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:83:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:87:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:88:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:90:0x01ee  */
    /* JADX WARN: Code duplicated, block: B:91:0x0212  */
    /* JADX WARN: Code duplicated, block: B:93:0x0215  */
    /* JADX WARN: Code duplicated, block: B:95:0x021d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:96:0x021f  */
    /* JADX WARN: Code duplicated, block: B:97:0x0222  */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iM;
        int iN;
        int iMax;
        int iMin;
        boolean zQ;
        boolean zQ2;
        int measuredHeight;
        TextView textView;
        TextView textView2;
        d dVar;
        d dVar2;
        boolean z2;
        int i5;
        int i6;
        int paddingTop;
        int i7;
        int iMax2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int iMax3;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int size;
        int iM2;
        int i18;
        int i19;
        int size2;
        int i20;
        ArrayList<View> arrayList;
        int i21;
        int size3;
        int i22;
        int i23;
        int measuredWidth;
        int i24;
        int i25;
        int size4;
        int iM3;
        int i26;
        ImageView imageView;
        View view;
        ActionMenuView actionMenuView;
        ImageButton imageButton;
        int i27 = j.f151a;
        boolean z3 = getLayoutDirection() == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop2 = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i28 = width - paddingRight;
        int[] iArr = this.G;
        iArr[1] = 0;
        iArr[0] = 0;
        int minimumHeight = getMinimumHeight();
        int iMin2 = minimumHeight >= 0 ? Math.min(minimumHeight, i4 - i2) : 0;
        if (q(this.e)) {
            ImageButton imageButton2 = this.e;
            if (z3) {
                iN = n(imageButton2, i28, iArr, iMin2);
                iM = paddingLeft;
            } else {
                iM = m(imageButton2, paddingLeft, iArr, iMin2);
            }
            if (q(this.i)) {
                imageButton = this.i;
                if (z3) {
                    iN = n(imageButton, iN, iArr, iMin2);
                } else {
                    iM = m(imageButton, iM, iArr, iMin2);
                }
            }
            if (q(this.f380b)) {
                actionMenuView = this.f380b;
                if (z3) {
                    iM = m(actionMenuView, iM, iArr, iMin2);
                } else {
                    iN = n(actionMenuView, iN, iArr, iMin2);
                }
            }
            int currentContentInsetLeft = getCurrentContentInsetLeft();
            int currentContentInsetRight = getCurrentContentInsetRight();
            iArr[0] = Math.max(0, currentContentInsetLeft - iM);
            iArr[1] = Math.max(0, currentContentInsetRight - (i28 - iN));
            iMax = Math.max(iM, currentContentInsetLeft);
            iMin = Math.min(iN, i28 - currentContentInsetRight);
            if (q(this.j)) {
                view = this.j;
                if (z3) {
                    iMin = n(view, iMin, iArr, iMin2);
                } else {
                    iMax = m(view, iMax, iArr, iMin2);
                }
            }
            if (q(this.f)) {
                imageView = this.f;
                if (z3) {
                    iMin = n(imageView, iMin, iArr, iMin2);
                } else {
                    iMax = m(imageView, iMax, iArr, iMin2);
                }
            }
            zQ = q(this.f381c);
            zQ2 = q(this.d);
            if (zQ) {
                d dVar3 = (d) this.f381c.getLayoutParams();
                measuredHeight = this.f381c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) dVar3).topMargin + ((ViewGroup.MarginLayoutParams) dVar3).bottomMargin + 0;
            } else {
                measuredHeight = 0;
            }
            if (zQ2) {
                d dVar4 = (d) this.d.getLayoutParams();
                measuredHeight += this.d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) dVar4).topMargin + ((ViewGroup.MarginLayoutParams) dVar4).bottomMargin;
            }
            if (!zQ || zQ2) {
                if (zQ) {
                    textView = this.f381c;
                } else {
                    textView = this.d;
                }
                if (zQ2) {
                    textView2 = this.d;
                } else {
                    textView2 = this.f381c;
                }
                dVar = (d) textView.getLayoutParams();
                dVar2 = (d) textView2.getLayoutParams();
                z2 = (!zQ && this.f381c.getMeasuredWidth() > 0) || (zQ2 && this.d.getMeasuredWidth() > 0);
                i5 = this.x & 112;
                i6 = paddingLeft;
                if (i5 == 48) {
                    paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin + this.s;
                } else if (i5 != 80) {
                    iMax3 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                    i13 = ((ViewGroup.MarginLayoutParams) dVar).topMargin;
                    i14 = this.s;
                    if (iMax3 < i13 + i14) {
                        iMax3 = i13 + i14;
                    } else {
                        i15 = (((height - paddingBottom) - measuredHeight) - iMax3) - paddingTop2;
                        i16 = ((ViewGroup.MarginLayoutParams) dVar).bottomMargin;
                        i17 = this.t;
                        if (i15 < i16 + i17) {
                            iMax3 = Math.max(0, iMax3 - ((((ViewGroup.MarginLayoutParams) dVar2).bottomMargin + i17) - i15));
                        }
                    }
                    paddingTop = paddingTop2 + iMax3;
                } else {
                    paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) dVar2).bottomMargin) - this.t) - measuredHeight;
                }
                if (z3) {
                    if (z2) {
                        i10 = this.q;
                    } else {
                        i10 = 0;
                    }
                    int i29 = i10 - iArr[1];
                    iMin -= Math.max(0, i29);
                    iArr[1] = Math.max(0, -i29);
                    if (zQ) {
                        d dVar5 = (d) this.f381c.getLayoutParams();
                        int measuredWidth2 = iMin - this.f381c.getMeasuredWidth();
                        int measuredHeight2 = this.f381c.getMeasuredHeight() + paddingTop;
                        this.f381c.layout(measuredWidth2, paddingTop, iMin, measuredHeight2);
                        i11 = measuredWidth2 - this.r;
                        paddingTop = measuredHeight2 + ((ViewGroup.MarginLayoutParams) dVar5).bottomMargin;
                    } else {
                        i11 = iMin;
                    }
                    if (zQ2) {
                        int i30 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                        this.d.layout(iMin - this.d.getMeasuredWidth(), i30, iMin, this.d.getMeasuredHeight() + i30);
                        i12 = iMin - this.r;
                    } else {
                        i12 = iMin;
                    }
                    if (z2) {
                        iMin = Math.min(i11, i12);
                    }
                    iMax = iMax;
                } else {
                    if (z2) {
                        i7 = this.q;
                    } else {
                        i7 = 0;
                    }
                    int i31 = i7 - iArr[0];
                    iMax2 = Math.max(0, i31) + iMax;
                    iArr[0] = Math.max(0, -i31);
                    if (zQ) {
                        d dVar6 = (d) this.f381c.getLayoutParams();
                        int measuredWidth3 = this.f381c.getMeasuredWidth() + iMax2;
                        int measuredHeight3 = this.f381c.getMeasuredHeight() + paddingTop;
                        this.f381c.layout(iMax2, paddingTop, measuredWidth3, measuredHeight3);
                        i8 = measuredWidth3 + this.r;
                        paddingTop = measuredHeight3 + ((ViewGroup.MarginLayoutParams) dVar6).bottomMargin;
                    } else {
                        i8 = iMax2;
                    }
                    if (zQ2) {
                        int i32 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                        int measuredWidth4 = this.d.getMeasuredWidth() + iMax2;
                        this.d.layout(iMax2, i32, measuredWidth4, this.d.getMeasuredHeight() + i32);
                        i9 = measuredWidth4 + this.r;
                    } else {
                        i9 = iMax2;
                    }
                    if (z2) {
                        iMax = Math.max(i8, i9);
                    } else {
                        iMax = iMax2;
                    }
                }
            } else {
                i6 = paddingLeft;
                iMin2 = iMin2;
            }
            a(this.E, 3);
            size = this.E.size();
            iM2 = iMax;
            for (i18 = 0; i18 < size; i18++) {
                iM2 = m(this.E.get(i18), iM2, iArr, iMin2);
            }
            i19 = iMin2;
            a(this.E, 5);
            size2 = this.E.size();
            for (i20 = 0; i20 < size2; i20++) {
                iMin = n(this.E.get(i20), iMin, iArr, i19);
            }
            a(this.E, 1);
            arrayList = this.E;
            int i33 = iArr[0];
            i21 = iArr[1];
            size3 = arrayList.size();
            i22 = i33;
            i23 = 0;
            measuredWidth = 0;
            while (i23 < size3) {
                View view2 = arrayList.get(i23);
                d dVar7 = (d) view2.getLayoutParams();
                int i34 = ((ViewGroup.MarginLayoutParams) dVar7).leftMargin - i22;
                int i35 = ((ViewGroup.MarginLayoutParams) dVar7).rightMargin - i21;
                int iMax4 = Math.max(0, i34);
                int iMax5 = Math.max(0, i35);
                int iMax6 = Math.max(0, -i34);
                int iMax7 = Math.max(0, -i35);
                measuredWidth += view2.getMeasuredWidth() + iMax4 + iMax5;
                i23++;
                i21 = iMax7;
                i22 = iMax6;
            }
            i24 = ((((width - i6) - paddingRight) / 2) + i6) - (measuredWidth / 2);
            i25 = measuredWidth + i24;
            if (i24 >= iM2) {
                if (i25 > iMin) {
                    iM2 = i24 - (i25 - iMin);
                } else {
                    iM2 = i24;
                }
            }
            size4 = this.E.size();
            iM3 = iM2;
            for (i26 = 0; i26 < size4; i26++) {
                iM3 = m(this.E.get(i26), iM3, iArr, i19);
            }
            this.E.clear();
        }
        iM = paddingLeft;
        iN = i28;
        if (q(this.i)) {
            imageButton = this.i;
            if (z3) {
                iN = n(imageButton, iN, iArr, iMin2);
            } else {
                iM = m(imageButton, iM, iArr, iMin2);
            }
        }
        if (q(this.f380b)) {
            actionMenuView = this.f380b;
            if (z3) {
                iM = m(actionMenuView, iM, iArr, iMin2);
            } else {
                iN = n(actionMenuView, iN, iArr, iMin2);
            }
        }
        int currentContentInsetLeft2 = getCurrentContentInsetLeft();
        int currentContentInsetRight2 = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft2 - iM);
        iArr[1] = Math.max(0, currentContentInsetRight2 - (i28 - iN));
        iMax = Math.max(iM, currentContentInsetLeft2);
        iMin = Math.min(iN, i28 - currentContentInsetRight2);
        if (q(this.j)) {
            view = this.j;
            if (z3) {
                iMin = n(view, iMin, iArr, iMin2);
            } else {
                iMax = m(view, iMax, iArr, iMin2);
            }
        }
        if (q(this.f)) {
            imageView = this.f;
            if (z3) {
                iMin = n(imageView, iMin, iArr, iMin2);
            } else {
                iMax = m(imageView, iMax, iArr, iMin2);
            }
        }
        zQ = q(this.f381c);
        zQ2 = q(this.d);
        if (zQ) {
            d dVar8 = (d) this.f381c.getLayoutParams();
            measuredHeight = this.f381c.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) dVar8).topMargin + ((ViewGroup.MarginLayoutParams) dVar8).bottomMargin + 0;
        } else {
            measuredHeight = 0;
        }
        if (zQ2) {
            d dVar9 = (d) this.d.getLayoutParams();
            measuredHeight += this.d.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) dVar9).topMargin + ((ViewGroup.MarginLayoutParams) dVar9).bottomMargin;
        }
        if (zQ) {
            if (zQ) {
                textView = this.f381c;
            } else {
                textView = this.d;
            }
            if (zQ2) {
                textView2 = this.d;
            } else {
                textView2 = this.f381c;
            }
            dVar = (d) textView.getLayoutParams();
            dVar2 = (d) textView2.getLayoutParams();
            if (zQ) {
            }
            i5 = this.x & 112;
            i6 = paddingLeft;
            if (i5 == 48) {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin + this.s;
            } else if (i5 != 80) {
                iMax3 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                i13 = ((ViewGroup.MarginLayoutParams) dVar).topMargin;
                i14 = this.s;
                if (iMax3 < i13 + i14) {
                    iMax3 = i13 + i14;
                } else {
                    i15 = (((height - paddingBottom) - measuredHeight) - iMax3) - paddingTop2;
                    i16 = ((ViewGroup.MarginLayoutParams) dVar).bottomMargin;
                    i17 = this.t;
                    if (i15 < i16 + i17) {
                        iMax3 = Math.max(0, iMax3 - ((((ViewGroup.MarginLayoutParams) dVar2).bottomMargin + i17) - i15));
                    }
                }
                paddingTop = paddingTop2 + iMax3;
            } else {
                paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) dVar2).bottomMargin) - this.t) - measuredHeight;
            }
            if (z3) {
                if (z2) {
                    i10 = this.q;
                } else {
                    i10 = 0;
                }
                int i210 = i10 - iArr[1];
                iMin -= Math.max(0, i210);
                iArr[1] = Math.max(0, -i210);
                if (zQ) {
                    d dVar10 = (d) this.f381c.getLayoutParams();
                    int measuredWidth5 = iMin - this.f381c.getMeasuredWidth();
                    int measuredHeight4 = this.f381c.getMeasuredHeight() + paddingTop;
                    this.f381c.layout(measuredWidth5, paddingTop, iMin, measuredHeight4);
                    i11 = measuredWidth5 - this.r;
                    paddingTop = measuredHeight4 + ((ViewGroup.MarginLayoutParams) dVar10).bottomMargin;
                } else {
                    i11 = iMin;
                }
                if (zQ2) {
                    int i36 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                    this.d.layout(iMin - this.d.getMeasuredWidth(), i36, iMin, this.d.getMeasuredHeight() + i36);
                    i12 = iMin - this.r;
                } else {
                    i12 = iMin;
                }
                if (z2) {
                    iMin = Math.min(i11, i12);
                }
                iMax = iMax;
            } else {
                if (z2) {
                    i7 = this.q;
                } else {
                    i7 = 0;
                }
                int i37 = i7 - iArr[0];
                iMax2 = Math.max(0, i37) + iMax;
                iArr[0] = Math.max(0, -i37);
                if (zQ) {
                    d dVar11 = (d) this.f381c.getLayoutParams();
                    int measuredWidth6 = this.f381c.getMeasuredWidth() + iMax2;
                    int measuredHeight5 = this.f381c.getMeasuredHeight() + paddingTop;
                    this.f381c.layout(iMax2, paddingTop, measuredWidth6, measuredHeight5);
                    i8 = measuredWidth6 + this.r;
                    paddingTop = measuredHeight5 + ((ViewGroup.MarginLayoutParams) dVar11).bottomMargin;
                } else {
                    i8 = iMax2;
                }
                if (zQ2) {
                    int i38 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                    int measuredWidth7 = this.d.getMeasuredWidth() + iMax2;
                    this.d.layout(iMax2, i38, measuredWidth7, this.d.getMeasuredHeight() + i38);
                    i9 = measuredWidth7 + this.r;
                } else {
                    i9 = iMax2;
                }
                if (z2) {
                    iMax = Math.max(i8, i9);
                } else {
                    iMax = iMax2;
                }
            }
        } else {
            if (zQ) {
                textView = this.f381c;
            } else {
                textView = this.d;
            }
            if (zQ2) {
                textView2 = this.d;
            } else {
                textView2 = this.f381c;
            }
            dVar = (d) textView.getLayoutParams();
            dVar2 = (d) textView2.getLayoutParams();
            if (zQ) {
            }
            i5 = this.x & 112;
            i6 = paddingLeft;
            if (i5 == 48) {
                paddingTop = getPaddingTop() + ((ViewGroup.MarginLayoutParams) dVar).topMargin + this.s;
            } else if (i5 != 80) {
                iMax3 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                i13 = ((ViewGroup.MarginLayoutParams) dVar).topMargin;
                i14 = this.s;
                if (iMax3 < i13 + i14) {
                    iMax3 = i13 + i14;
                } else {
                    i15 = (((height - paddingBottom) - measuredHeight) - iMax3) - paddingTop2;
                    i16 = ((ViewGroup.MarginLayoutParams) dVar).bottomMargin;
                    i17 = this.t;
                    if (i15 < i16 + i17) {
                        iMax3 = Math.max(0, iMax3 - ((((ViewGroup.MarginLayoutParams) dVar2).bottomMargin + i17) - i15));
                    }
                }
                paddingTop = paddingTop2 + iMax3;
            } else {
                paddingTop = (((height - paddingBottom) - ((ViewGroup.MarginLayoutParams) dVar2).bottomMargin) - this.t) - measuredHeight;
            }
            if (z3) {
                if (z2) {
                    i10 = this.q;
                } else {
                    i10 = 0;
                }
                int i211 = i10 - iArr[1];
                iMin -= Math.max(0, i211);
                iArr[1] = Math.max(0, -i211);
                if (zQ) {
                    d dVar12 = (d) this.f381c.getLayoutParams();
                    int measuredWidth8 = iMin - this.f381c.getMeasuredWidth();
                    int measuredHeight6 = this.f381c.getMeasuredHeight() + paddingTop;
                    this.f381c.layout(measuredWidth8, paddingTop, iMin, measuredHeight6);
                    i11 = measuredWidth8 - this.r;
                    paddingTop = measuredHeight6 + ((ViewGroup.MarginLayoutParams) dVar12).bottomMargin;
                } else {
                    i11 = iMin;
                }
                if (zQ2) {
                    int i39 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                    this.d.layout(iMin - this.d.getMeasuredWidth(), i39, iMin, this.d.getMeasuredHeight() + i39);
                    i12 = iMin - this.r;
                } else {
                    i12 = iMin;
                }
                if (z2) {
                    iMin = Math.min(i11, i12);
                }
                iMax = iMax;
            } else {
                if (z2) {
                    i7 = this.q;
                } else {
                    i7 = 0;
                }
                int i310 = i7 - iArr[0];
                iMax2 = Math.max(0, i310) + iMax;
                iArr[0] = Math.max(0, -i310);
                if (zQ) {
                    d dVar13 = (d) this.f381c.getLayoutParams();
                    int measuredWidth9 = this.f381c.getMeasuredWidth() + iMax2;
                    int measuredHeight7 = this.f381c.getMeasuredHeight() + paddingTop;
                    this.f381c.layout(iMax2, paddingTop, measuredWidth9, measuredHeight7);
                    i8 = measuredWidth9 + this.r;
                    paddingTop = measuredHeight7 + ((ViewGroup.MarginLayoutParams) dVar13).bottomMargin;
                } else {
                    i8 = iMax2;
                }
                if (zQ2) {
                    int i311 = paddingTop + ((ViewGroup.MarginLayoutParams) ((d) this.d.getLayoutParams())).topMargin;
                    int measuredWidth10 = this.d.getMeasuredWidth() + iMax2;
                    this.d.layout(iMax2, i311, measuredWidth10, this.d.getMeasuredHeight() + i311);
                    i9 = measuredWidth10 + this.r;
                } else {
                    i9 = iMax2;
                }
                if (z2) {
                    iMax = Math.max(i8, i9);
                } else {
                    iMax = iMax2;
                }
            }
        }
        a(this.E, 3);
        size = this.E.size();
        iM2 = iMax;
        while (i18 < size) {
            iM2 = m(this.E.get(i18), iM2, iArr, iMin2);
        }
        i19 = iMin2;
        a(this.E, 5);
        size2 = this.E.size();
        while (i20 < size2) {
            iMin = n(this.E.get(i20), iMin, iArr, i19);
        }
        a(this.E, 1);
        arrayList = this.E;
        int i312 = iArr[0];
        i21 = iArr[1];
        size3 = arrayList.size();
        i22 = i312;
        i23 = 0;
        measuredWidth = 0;
        while (i23 < size3) {
            View view3 = arrayList.get(i23);
            d dVar14 = (d) view3.getLayoutParams();
            int i313 = ((ViewGroup.MarginLayoutParams) dVar14).leftMargin - i22;
            int i314 = ((ViewGroup.MarginLayoutParams) dVar14).rightMargin - i21;
            int iMax8 = Math.max(0, i313);
            int iMax9 = Math.max(0, i314);
            int iMax10 = Math.max(0, -i313);
            int iMax11 = Math.max(0, -i314);
            measuredWidth += view3.getMeasuredWidth() + iMax8 + iMax9;
            i23++;
            i21 = iMax11;
            i22 = iMax10;
        }
        i24 = ((((width - i6) - paddingRight) / 2) + i6) - (measuredWidth / 2);
        i25 = measuredWidth + i24;
        if (i24 >= iM2) {
            if (i25 > iMin) {
                iM2 = i24 - (i25 - iMin);
            } else {
                iM2 = i24;
            }
        }
        size4 = this.E.size();
        iM3 = iM2;
        while (i26 < size4) {
            iM3 = m(this.E.get(i26), iM3, iArr, i19);
        }
        this.E.clear();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        char c2;
        char c3;
        int iJ;
        int iMax;
        int iCombineMeasuredStates;
        int iJ2;
        int iCombineMeasuredStates2;
        int iMax2;
        int iK;
        int[] iArr = this.G;
        boolean z = true;
        if (m0.a(this)) {
            c2 = 1;
            c3 = 0;
        } else {
            c2 = 0;
            c3 = 1;
        }
        if (q(this.e)) {
            p(this.e, i, 0, i2, 0, this.p);
            iJ = j(this.e) + this.e.getMeasuredWidth();
            iMax = Math.max(0, k(this.e) + this.e.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.e.getMeasuredState());
        } else {
            iJ = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (q(this.i)) {
            p(this.i, i, 0, i2, 0, this.p);
            iJ = j(this.i) + this.i.getMeasuredWidth();
            iMax = Math.max(iMax, k(this.i) + this.i.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.i.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = Math.max(currentContentInsetStart, iJ) + 0;
        iArr[c2] = Math.max(0, currentContentInsetStart - iJ);
        if (q(this.f380b)) {
            p(this.f380b, i, iMax3, i2, 0, this.p);
            iJ2 = j(this.f380b) + this.f380b.getMeasuredWidth();
            iMax = Math.max(iMax, k(this.f380b) + this.f380b.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f380b.getMeasuredState());
        } else {
            iJ2 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax4 = Math.max(currentContentInsetEnd, iJ2) + iMax3;
        iArr[c3] = Math.max(0, currentContentInsetEnd - iJ2);
        if (q(this.j)) {
            iMax4 += o(this.j, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, k(this.j) + this.j.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.j.getMeasuredState());
        }
        if (q(this.f)) {
            iMax4 += o(this.f, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, k(this.f) + this.f.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (((d) childAt.getLayoutParams()).f386b == 0 && q(childAt)) {
                iMax4 += o(childAt, i, iMax4, i2, 0, iArr);
                iMax = Math.max(iMax, k(childAt) + childAt.getMeasuredHeight());
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        int i4 = this.s + this.t;
        int i5 = this.q + this.r;
        if (q(this.f381c)) {
            o(this.f381c, i, iMax4 + i5, i2, i4, iArr);
            int iJ3 = j(this.f381c) + this.f381c.getMeasuredWidth();
            iK = k(this.f381c) + this.f381c.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.f381c.getMeasuredState());
            iMax2 = iJ3;
        } else {
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iMax2 = 0;
            iK = 0;
        }
        if (q(this.d)) {
            iMax2 = Math.max(iMax2, o(this.d, i, iMax4 + i5, i2, iK + i4, iArr));
            iK = k(this.d) + this.d.getMeasuredHeight() + iK;
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.d.getMeasuredState());
        }
        int iMax5 = Math.max(iMax, iK);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop() + iMax5;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingRight + iMax4 + iMax2, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates2);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16);
        if (!this.L) {
            z = false;
            break;
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            View childAt2 = getChildAt(i6);
            if (q(childAt2) && childAt2.getMeasuredWidth() > 0 && childAt2.getMeasuredHeight() > 0) {
                z = false;
                break;
            }
        }
        setMeasuredDimension(iResolveSizeAndState, z ? 0 : iResolveSizeAndState2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof f)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        f fVar = (f) parcelable;
        super.onRestoreInstanceState(fVar.f142a);
        ActionMenuView actionMenuView = this.f380b;
        g gVar = actionMenuView != null ? actionMenuView.q : null;
        int i = fVar.f387c;
        if (i != 0 && this.K != null && gVar != null && (menuItemFindItem = gVar.findItem(i)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (fVar.d) {
            removeCallbacks(this.M);
            post(this.M);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        int i2;
        super.onRtlPropertiesChanged(i);
        c();
        z zVar = this.u;
        boolean z = i == 1;
        if (z == zVar.g) {
            return;
        }
        zVar.g = z;
        if (zVar.h) {
            if (z) {
                int i3 = zVar.d;
                if (i3 == Integer.MIN_VALUE) {
                    i3 = zVar.e;
                }
                zVar.f334a = i3;
                i2 = zVar.f336c;
                if (i2 == Integer.MIN_VALUE) {
                }
            } else {
                int i4 = zVar.f336c;
                if (i4 == Integer.MIN_VALUE) {
                    i4 = zVar.e;
                }
                zVar.f334a = i4;
                i2 = zVar.d;
                if (i2 == Integer.MIN_VALUE) {
                }
            }
            zVar.f335b = i2;
        }
        zVar.f334a = zVar.e;
        i2 = zVar.f;
        zVar.f335b = i2;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        h hVar;
        f fVar = new f(super.onSaveInstanceState());
        c cVar = this.K;
        if (cVar != null && (hVar = cVar.f385c) != null) {
            fVar.f387c = hVar.f228a;
        }
        ActionMenuView actionMenuView = this.f380b;
        boolean z = false;
        if (actionMenuView != null) {
            a.b.e.f.c cVar2 = actionMenuView.t;
            if (cVar2 != null && cVar2.i()) {
                z = true;
            }
        }
        fVar.d = z;
        return fVar;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.C = false;
        }
        if (!this.C) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.C = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.C = false;
        }
        return true;
    }

    public final void p(View view, int i, int i2, int i3, int i4, int i5) {
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

    public final boolean q(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public void setCollapsible(boolean z) {
        this.L = z;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.w) {
            this.w = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i) {
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

    public void setLogo(int i) {
        setLogo(a.b.e.c.a.a.b(getContext(), i));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            if (this.f == null) {
                this.f = new a.b.e.f.j(getContext(), null, 0);
            }
            if (!l(this.f)) {
                b(this.f, true);
            }
        } else {
            ImageView imageView = this.f;
            if (imageView != null && l(imageView)) {
                removeView(this.f);
                this.F.remove(this.f);
            }
        }
        ImageView imageView2 = this.f;
        if (imageView2 != null) {
            imageView2.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence) && this.f == null) {
            this.f = new a.b.e.f.j(getContext(), null, 0);
        }
        ImageView imageView = this.f;
        if (imageView != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            e();
        }
        ImageButton imageButton = this.e;
        if (imageButton != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int i) {
        setNavigationIcon(a.b.e.c.a.a.b(getContext(), i));
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            e();
            if (!l(this.e)) {
                b(this.e, true);
            }
        } else {
            ImageButton imageButton = this.e;
            if (imageButton != null && l(imageButton)) {
                removeView(this.e);
                this.F.remove(this.e);
            }
        }
        ImageButton imageButton2 = this.e;
        if (imageButton2 != null) {
            imageButton2.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        e();
        this.e.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(e eVar) {
        this.H = eVar;
    }

    public void setOverflowIcon(Drawable drawable) {
        d();
        this.f380b.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int i) {
        if (this.l != i) {
            this.l = i;
            if (i == 0) {
                this.k = getContext();
            } else {
                this.k = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    public void setSubtitle(int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.d;
            if (textView != null && l(textView)) {
                removeView(this.d);
                this.F.remove(this.d);
            }
        } else {
            if (this.d == null) {
                Context context = getContext();
                a.b.e.f.m mVar = new a.b.e.f.m(context, null);
                this.d = mVar;
                mVar.setSingleLine();
                this.d.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.n;
                if (i != 0) {
                    this.d.setTextAppearance(context, i);
                }
                int i2 = this.B;
                if (i2 != 0) {
                    this.d.setTextColor(i2);
                }
            }
            if (!l(this.d)) {
                b(this.d, true);
            }
        }
        TextView textView2 = this.d;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.z = charSequence;
    }

    public void setSubtitleTextColor(int i) {
        this.B = i;
        TextView textView = this.d;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public void setTitle(int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.f381c;
            if (textView != null && l(textView)) {
                removeView(this.f381c);
                this.F.remove(this.f381c);
            }
        } else {
            if (this.f381c == null) {
                Context context = getContext();
                a.b.e.f.m mVar = new a.b.e.f.m(context, null);
                this.f381c = mVar;
                mVar.setSingleLine();
                this.f381c.setEllipsize(TextUtils.TruncateAt.END);
                int i = this.m;
                if (i != 0) {
                    this.f381c.setTextAppearance(context, i);
                }
                int i2 = this.A;
                if (i2 != 0) {
                    this.f381c.setTextColor(i2);
                }
            }
            if (!l(this.f381c)) {
                b(this.f381c, true);
            }
        }
        TextView textView2 = this.f381c;
        if (textView2 != null) {
            textView2.setText(charSequence);
        }
        this.y = charSequence;
    }

    public void setTitleMarginBottom(int i) {
        this.t = i;
        requestLayout();
    }

    public void setTitleMarginEnd(int i) {
        this.r = i;
        requestLayout();
    }

    public void setTitleMarginStart(int i) {
        this.q = i;
        requestLayout();
    }

    public void setTitleMarginTop(int i) {
        this.s = i;
        requestLayout();
    }

    public void setTitleTextColor(int i) {
        this.A = i;
        TextView textView = this.f381c;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }
}
