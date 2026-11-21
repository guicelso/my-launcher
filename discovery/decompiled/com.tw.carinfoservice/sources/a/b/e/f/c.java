package a.b.e.f;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.tw.carinfoservice.R;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class c extends a.b.e.e.c.b {
    public d i;
    public Drawable j;
    public boolean k;
    public boolean l;
    public boolean m;
    public int n;
    public int o;
    public int p;
    public boolean q;
    public int r;
    public final SparseBooleanArray s;
    public View t;
    public e u;
    public a v;
    public RunnableC0014c w;
    public b x;
    public final f y;

    public class a extends a.b.e.e.c.l {
        public a(Context context, a.b.e.e.c.q qVar, View view) {
            super(context, qVar, view, false, R.attr.actionOverflowMenuStyle, 0);
            if (!qVar.v.m()) {
                View view2 = c.this.i;
                this.f = view2 == null ? (View) c.this.h : view2;
            }
            d(c.this.y);
        }

        @Override // a.b.e.e.c.l
        public void c() {
            c cVar = c.this;
            cVar.v = null;
            Objects.requireNonNull(cVar);
            super.c();
        }
    }

    public class b extends ActionMenuItemView.b {
        public b() {
        }
    }

    /* JADX INFO: renamed from: a.b.e.f.c$c, reason: collision with other inner class name */
    public class RunnableC0014c implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public e f256b;

        public RunnableC0014c(e eVar) {
            this.f256b = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.b.e.e.c.g.a aVar;
            a.b.e.e.c.g.a aVar2;
            a.b.e.e.c.g gVar = c.this.d;
            if (gVar != null && (aVar = gVar.e) != null && (aVar2 = ActionMenuView.this.v) != null) {
                ((ActionMenuView.d) aVar2).a(gVar);
            }
            View view = (View) c.this.h;
            if (view != null && view.getWindowToken() != null && this.f256b.f()) {
                c.this.u = this.f256b;
            }
            c.this.w = null;
        }
    }

    public class d extends j implements ActionMenuView.a {

        public class a extends s {
            public a(View view, c cVar) {
                super(view);
            }

            @Override // a.b.e.f.s
            public a.b.e.e.c.o b() {
                e eVar = c.this.u;
                if (eVar == null) {
                    return null;
                }
                return eVar.a();
            }

            @Override // a.b.e.f.s
            public boolean c() {
                c.this.m();
                return true;
            }

            @Override // a.b.e.f.s
            public boolean d() {
                c cVar = c.this;
                if (cVar.w != null) {
                    return false;
                }
                cVar.d();
                return true;
            }
        }

        public d(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            a.b.e.b.a.r(this, getContentDescription());
            setOnTouchListener(new a(this, c.this));
        }

        @Override // android.support.v7.widget.ActionMenuView.a
        public boolean a() {
            return false;
        }

        @Override // android.support.v7.widget.ActionMenuView.a
        public boolean b() {
            return false;
        }

        @Override // android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            c.this.m();
            return true;
        }

        @Override // android.widget.ImageView
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (drawable != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int iMax = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                a.b.a.a.J(background, paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
            }
            return frame;
        }
    }

    public class e extends a.b.e.e.c.l {
        public e(Context context, a.b.e.e.c.g gVar, View view, boolean z) {
            super(context, gVar, view, z, R.attr.actionOverflowMenuStyle, 0);
            this.g = 8388613;
            d(c.this.y);
        }

        @Override // a.b.e.e.c.l
        public void c() {
            a.b.e.e.c.g gVar = c.this.d;
            if (gVar != null) {
                gVar.c(true);
            }
            c.this.u = null;
            super.c();
        }
    }

    public class f implements a.b.e.e.c.m.a {
        public f() {
        }

        @Override // a.b.e.e.c.m.a
        public void a(a.b.e.e.c.g gVar, boolean z) {
            if (gVar instanceof a.b.e.e.c.q) {
                gVar.j().c(false);
            }
            a.b.e.e.c.m.a aVar = c.this.f;
            if (aVar != null) {
                aVar.a(gVar, z);
            }
        }

        @Override // a.b.e.e.c.m.a
        public boolean b(a.b.e.e.c.g gVar) {
            if (gVar == null) {
                return false;
            }
            c cVar = c.this;
            int i = ((a.b.e.e.c.q) gVar).v.f228a;
            Objects.requireNonNull(cVar);
            a.b.e.e.c.m.a aVar = c.this.f;
            if (aVar != null) {
                return aVar.b(gVar);
            }
            return false;
        }
    }

    public c(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
        this.s = new SparseBooleanArray();
        this.y = new f();
    }

    @Override // a.b.e.e.c.m
    public void a(a.b.e.e.c.g gVar, boolean z) {
        d();
        f();
        a.b.e.e.c.m.a aVar = this.f;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // a.b.e.e.c.m
    public boolean b(a.b.e.e.c.q qVar) {
        boolean z = false;
        if (!qVar.hasVisibleItems()) {
            return false;
        }
        a.b.e.e.c.q qVar2 = qVar;
        while (true) {
            a.b.e.e.c.g gVar = qVar2.u;
            if (gVar == this.d) {
                break;
            }
            qVar2 = (a.b.e.e.c.q) gVar;
        }
        a.b.e.e.c.h hVar = qVar2.v;
        ViewGroup viewGroup = (ViewGroup) this.h;
        View view = null;
        view = null;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if ((childAt instanceof a.b.e.e.c.n.a) && ((a.b.e.e.c.n.a) childAt).getItemData() == hVar) {
                    view = childAt;
                    break;
                }
            }
        }
        if (view == null) {
            return false;
        }
        int i2 = qVar.v.f228a;
        int size = qVar.size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItem item = qVar.getItem(i3);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
        }
        a aVar = new a(this.f210c, qVar, view);
        this.v = aVar;
        aVar.h = z;
        a.b.e.e.c.k kVar = aVar.j;
        if (kVar != null) {
            kVar.q(z);
        }
        if (!this.v.f()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
        a.b.e.e.c.m.a aVar2 = this.f;
        if (aVar2 != null) {
            aVar2.b(qVar);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public View c(a.b.e.e.c.h hVar, View view, ViewGroup viewGroup) {
        View actionView = hVar.getActionView();
        if (actionView == null || hVar.l()) {
            a.b.e.e.c.n.a aVar = view instanceof a.b.e.e.c.n.a ? (a.b.e.e.c.n.a) view : (a.b.e.e.c.n.a) this.e.inflate(this.g, viewGroup, false);
            aVar.c(hVar, 0);
            ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
            actionMenuItemView.setItemInvoker((ActionMenuView) this.h);
            if (this.x == null) {
                this.x = new b();
            }
            actionMenuItemView.setPopupCallback(this.x);
            actionView = (View) aVar;
        }
        actionView.setVisibility(hVar.C ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public boolean d() {
        Object obj;
        RunnableC0014c runnableC0014c = this.w;
        if (runnableC0014c != null && (obj = this.h) != null) {
            ((View) obj).removeCallbacks(runnableC0014c);
            this.w = null;
            return true;
        }
        e eVar = this.u;
        if (eVar == null) {
            return false;
        }
        if (eVar.b()) {
            eVar.j.i();
        }
        return true;
    }

    public boolean f() {
        a aVar = this.v;
        if (aVar == null) {
            return false;
        }
        if (!aVar.b()) {
            return true;
        }
        aVar.j.i();
        return true;
    }

    @Override // a.b.e.e.c.m
    public boolean g() {
        ArrayList<a.b.e.e.c.h> arrayListK;
        int size;
        int i;
        boolean z;
        a.b.e.e.c.g gVar = this.d;
        if (gVar != null) {
            arrayListK = gVar.k();
            size = arrayListK.size();
        } else {
            arrayListK = null;
            size = 0;
        }
        int i2 = this.p;
        int i3 = this.o;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) this.h;
        int i4 = 0;
        boolean z2 = false;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i = 2;
            z = true;
            if (i4 >= size) {
                break;
            }
            a.b.e.e.c.h hVar = arrayListK.get(i4);
            int i7 = hVar.y;
            if ((i7 & 2) == 2) {
                i6++;
            } else if ((i7 & 1) == 1) {
                i5++;
            } else {
                z2 = true;
            }
            if (this.q && hVar.C) {
                i2 = 0;
            }
            i4++;
        }
        if (this.l && (z2 || i5 + i6 > i2)) {
            i2--;
        }
        int i8 = i2 - i6;
        SparseBooleanArray sparseBooleanArray = this.s;
        sparseBooleanArray.clear();
        int i9 = 0;
        int i10 = 0;
        while (i9 < size) {
            a.b.e.e.c.h hVar2 = arrayListK.get(i9);
            int i11 = hVar2.y;
            if ((i11 & 2) == i) {
                View viewC = c(hVar2, this.t, viewGroup);
                if (this.t == null) {
                    this.t = viewC;
                }
                viewC.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredWidth = viewC.getMeasuredWidth();
                i3 -= measuredWidth;
                if (i10 == 0) {
                    i10 = measuredWidth;
                }
                int i12 = hVar2.f229b;
                if (i12 != 0) {
                    sparseBooleanArray.put(i12, z);
                }
                hVar2.q(z);
            } else {
                if ((i11 & 1) == z) {
                    int i13 = hVar2.f229b;
                    boolean z3 = sparseBooleanArray.get(i13);
                    boolean z4 = (i8 > 0 || z3) && i3 > 0;
                    if (z4) {
                        View viewC2 = c(hVar2, this.t, viewGroup);
                        if (this.t == null) {
                            this.t = viewC2;
                        }
                        viewC2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                        int measuredWidth2 = viewC2.getMeasuredWidth();
                        i3 -= measuredWidth2;
                        if (i10 == 0) {
                            i10 = measuredWidth2;
                        }
                        z4 &= i3 + i10 > 0;
                    }
                    if (z4 && i13 != 0) {
                        sparseBooleanArray.put(i13, true);
                    } else if (z3) {
                        sparseBooleanArray.put(i13, false);
                        for (int i14 = 0; i14 < i9; i14++) {
                            a.b.e.e.c.h hVar3 = arrayListK.get(i14);
                            if (hVar3.f229b == i13) {
                                if (hVar3.m()) {
                                    i8++;
                                }
                                hVar3.q(false);
                            }
                        }
                    }
                    if (z4) {
                        i8--;
                    }
                    hVar2.q(z4);
                } else {
                    hVar2.q(false);
                }
                i9++;
                i = 2;
                z = true;
            }
            i9++;
            i = 2;
            z = true;
        }
        return true;
    }

    public boolean i() {
        e eVar = this.u;
        return eVar != null && eVar.b();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // a.b.e.e.c.m
    public void j(boolean z) {
        int i;
        boolean z2;
        ViewGroup viewGroup = (ViewGroup) this.h;
        ArrayList<a.b.e.e.c.h> arrayList = null;
        boolean z3 = false;
        if (viewGroup != null) {
            a.b.e.e.c.g gVar = this.d;
            if (gVar != null) {
                gVar.i();
                ArrayList<a.b.e.e.c.h> arrayListK = this.d.k();
                int size = arrayListK.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    a.b.e.e.c.h hVar = arrayListK.get(i2);
                    if (hVar.m()) {
                        View childAt = viewGroup.getChildAt(i);
                        a.b.e.e.c.h itemData = childAt instanceof a.b.e.e.c.n.a ? ((a.b.e.e.c.n.a) childAt).getItemData() : null;
                        View viewC = c(hVar, childAt, viewGroup);
                        if (hVar != itemData) {
                            viewC.setPressed(false);
                            viewC.jumpDrawablesToCurrentState();
                        }
                        if (viewC != childAt) {
                            ViewGroup viewGroup2 = (ViewGroup) viewC.getParent();
                            if (viewGroup2 != null) {
                                viewGroup2.removeView(viewC);
                            }
                            ((ViewGroup) this.h).addView(viewC, i);
                        }
                        i++;
                    }
                }
            } else {
                i = 0;
            }
            while (i < viewGroup.getChildCount()) {
                if (viewGroup.getChildAt(i) == this.i) {
                    z2 = false;
                } else {
                    viewGroup.removeViewAt(i);
                    z2 = true;
                }
                if (!z2) {
                    i++;
                }
            }
        }
        ((View) this.h).requestLayout();
        a.b.e.e.c.g gVar2 = this.d;
        if (gVar2 != null) {
            gVar2.i();
            ArrayList<a.b.e.e.c.h> arrayList2 = gVar2.i;
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                a.b.d.h.c cVar = arrayList2.get(i3).A;
            }
        }
        a.b.e.e.c.g gVar3 = this.d;
        if (gVar3 != null) {
            gVar3.i();
            arrayList = gVar3.j;
        }
        if (this.l && arrayList != null) {
            int size3 = arrayList.size();
            if (size3 == 1) {
                z3 = !arrayList.get(0).C;
            } else if (size3 > 0) {
                z3 = true;
            }
        }
        d dVar = this.i;
        if (z3) {
            if (dVar == null) {
                this.i = new d(this.f209b);
            }
            ViewGroup viewGroup3 = (ViewGroup) this.i.getParent();
            if (viewGroup3 != this.h) {
                if (viewGroup3 != null) {
                    viewGroup3.removeView(this.i);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.h;
                d dVar2 = this.i;
                ActionMenuView.c cVarGenerateDefaultLayoutParams = actionMenuView.generateDefaultLayoutParams();
                cVarGenerateDefaultLayoutParams.f353c = true;
                actionMenuView.addView(dVar2, cVarGenerateDefaultLayoutParams);
            }
        } else if (dVar != null) {
            Object parent = dVar.getParent();
            Object obj = this.h;
            if (parent == obj) {
                ((ViewGroup) obj).removeView(this.i);
            }
        }
        ((ActionMenuView) this.h).setOverflowReserved(this.l);
    }

    @Override // a.b.e.e.c.m
    public void l(Context context, a.b.e.e.c.g gVar) {
        this.f210c = context;
        LayoutInflater.from(context);
        this.d = gVar;
        Resources resources = context.getResources();
        if (!this.m) {
            this.l = true;
        }
        int i = 2;
        this.n = context.getResources().getDisplayMetrics().widthPixels / 2;
        Configuration configuration = context.getResources().getConfiguration();
        int i2 = configuration.screenWidthDp;
        int i3 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600 || i2 > 600 || ((i2 > 960 && i3 > 720) || (i2 > 720 && i3 > 960))) {
            i = 5;
        } else if (i2 >= 500 || ((i2 > 640 && i3 > 480) || (i2 > 480 && i3 > 640))) {
            i = 4;
        } else if (i2 >= 360) {
            i = 3;
        }
        this.p = i;
        int measuredWidth = this.n;
        if (this.l) {
            if (this.i == null) {
                d dVar = new d(this.f209b);
                this.i = dVar;
                if (this.k) {
                    dVar.setImageDrawable(this.j);
                    this.j = null;
                    this.k = false;
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.i.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.i.getMeasuredWidth();
        } else {
            this.i = null;
        }
        this.o = measuredWidth;
        this.r = (int) (resources.getDisplayMetrics().density * 56.0f);
        this.t = null;
    }

    public boolean m() {
        a.b.e.e.c.g gVar;
        if (!this.l || i() || (gVar = this.d) == null || this.h == null || this.w != null) {
            return false;
        }
        gVar.i();
        if (gVar.j.isEmpty()) {
            return false;
        }
        RunnableC0014c runnableC0014c = new RunnableC0014c(new e(this.f210c, this.d, this.i, true));
        this.w = runnableC0014c;
        ((View) this.h).post(runnableC0014c);
        a.b.e.e.c.m.a aVar = this.f;
        if (aVar == null) {
            return true;
        }
        aVar.b(null);
        return true;
    }
}
