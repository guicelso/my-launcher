package a.b.e.f;

import a.b.e.e.a.AbstractC0009b;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import java.util.ArrayList;

/* JADX INFO: renamed from: a.b.e.f.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0015f extends AbstractC0009b implements a.b.d.h.e.a {
    public final C0007f A;
    public d h;
    public Drawable i;
    public boolean j;
    public boolean k;
    public boolean l;
    public int m;
    public int n;
    public int o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s;
    public int t;
    public final SparseBooleanArray u;
    public View v;
    public e w;
    public a x;
    public c y;
    public b z;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.f$a */
    public class a extends a.b.e.e.a.s {
        public a(Context context, a.b.e.e.a.A a2, View view) {
            super(context, a2, view, false, a.b.e.b.a.actionOverflowMenuStyle, 0);
            if (!a2.A.d()) {
                View view2 = C0015f.this.h;
                this.f = view2 == null ? (View) C0015f.this.g : view2;
            }
            a(C0015f.this.A);
        }

        @Override // a.b.e.e.a.s
        public void d() {
            C0015f.this.x = null;
            this.j = null;
            PopupWindow.OnDismissListener onDismissListener = this.k;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.f$b */
    public class b extends ActionMenuItemView.b {
        public b() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.f$c */
    class c implements Runnable {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public e f325a;

        public c(e eVar) {
            this.f325a = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.b.e.e.a.k.a aVar;
            a.b.e.e.a.k kVar = C0015f.this.f210c;
            if (kVar != null && (aVar = kVar.f) != null) {
                ((ActionMenuView.d) aVar).a(kVar);
            }
            View view = (View) C0015f.this.g;
            if (view != null && view.getWindowToken() != null && this.f325a.e()) {
                C0015f.this.w = this.f325a;
            }
            C0015f.this.y = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.f$d */
    public class d extends C0023n implements ActionMenuView.a {
        public d(Context context) {
            super(context, null, a.b.e.b.a.actionOverflowButtonStyle);
            float[] fArr = new float[2];
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            a.b.b.a.e.a(this, getContentDescription());
            setOnTouchListener(new C0016g(this, this, C0015f.this));
        }

        @Override // android.support.v7.widget.ActionMenuView.a
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // android.support.v7.widget.ActionMenuView.a
        public boolean needsDividerBefore() {
            return false;
        }

        @Override // android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            C0015f.this.c();
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
                a.b.b.a.e.a(background, paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
            }
            return frame;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a.b.e.f.f$e */
    class e extends a.b.e.e.a.s {
        public e(Context context, a.b.e.e.a.k kVar, View view, boolean z) {
            super(context, kVar, view, z, a.b.e.b.a.actionOverflowMenuStyle, 0);
            this.g = 8388613;
            a(C0015f.this.A);
        }

        @Override // a.b.e.e.a.s
        public void d() {
            a.b.e.e.a.k kVar = C0015f.this.f210c;
            if (kVar != null) {
                kVar.a(true);
            }
            C0015f.this.w = null;
            this.j = null;
            PopupWindow.OnDismissListener onDismissListener = this.k;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
        }
    }

    public C0015f(Context context) {
        super(context, a.b.e.b.g.abc_action_menu_layout, a.b.e.b.g.abc_action_menu_item_layout);
        this.u = new SparseBooleanArray();
        this.A = new C0007f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // a.b.e.e.a.AbstractC0009b
    public View a(a.b.e.e.a.n nVar, View view, ViewGroup viewGroup) {
        View actionView = nVar.getActionView();
        if (actionView == null || nVar.c()) {
            a.b.e.e.a.u.a aVar = view instanceof a.b.e.e.a.u.a ? (a.b.e.e.a.u.a) view : (a.b.e.e.a.u.a) this.d.inflate(this.f, viewGroup, false);
            aVar.a(nVar, 0);
            ActionMenuItemView actionMenuItemView = (ActionMenuItemView) aVar;
            actionMenuItemView.setItemInvoker((ActionMenuView) this.g);
            if (this.z == null) {
                this.z = new b();
            }
            actionMenuItemView.setPopupCallback(this.z);
            actionView = (View) aVar;
        }
        actionView.setVisibility(nVar.D ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    public boolean b() {
        e eVar = this.w;
        return eVar != null && eVar.c();
    }

    public boolean c() {
        a.b.e.e.a.k kVar;
        if (!this.k || b() || (kVar = this.f210c) == null || this.g == null || this.y != null) {
            return false;
        }
        kVar.a();
        if (kVar.k.isEmpty()) {
            return false;
        }
        this.y = new c(new e(this.f209b, this.f210c, this.h, true));
        ((View) this.g).post(this.y);
        a.b.e.e.a.t.a aVar = this.e;
        if (aVar == null) {
            return true;
        }
        aVar.a(null);
        return true;
    }

    @Override // a.b.e.e.a.t
    public boolean flagActionItems() {
        ArrayList<a.b.e.e.a.n> arrayListC;
        int size;
        int i;
        int i2;
        int iA;
        int measuredWidth;
        boolean z;
        C0015f c0015f = this;
        a.b.e.e.a.k kVar = c0015f.f210c;
        int i3 = 0;
        if (kVar != null) {
            arrayListC = kVar.c();
            size = arrayListC.size();
        } else {
            arrayListC = null;
            size = 0;
        }
        int i4 = c0015f.o;
        int i5 = c0015f.n;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) c0015f.g;
        int i6 = i4;
        int i7 = 0;
        boolean z2 = false;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            i = 2;
            if (i7 >= size) {
                break;
            }
            a.b.e.e.a.n nVar = arrayListC.get(i7);
            if ((nVar.z & 2) == 2) {
                i8++;
            } else if ((nVar.z & 1) == 1) {
                i9++;
            } else {
                z2 = true;
            }
            if (c0015f.s && nVar.D) {
                i6 = 0;
            }
            i7++;
        }
        if (c0015f.k && (z2 || i9 + i8 > i6)) {
            i6--;
        }
        int i10 = i6 - i8;
        SparseBooleanArray sparseBooleanArray = c0015f.u;
        sparseBooleanArray.clear();
        if (c0015f.q) {
            int i11 = c0015f.t;
            iA = i5 / i11;
            i2 = i11 + ((i5 % i11) / iA);
        } else {
            i2 = 0;
            iA = 0;
        }
        int i12 = i5;
        int i13 = 0;
        int i14 = 0;
        while (i13 < size) {
            a.b.e.e.a.n nVar2 = arrayListC.get(i13);
            if ((nVar2.z & i) == i) {
                View viewA = c0015f.a(nVar2, c0015f.v, viewGroup);
                if (c0015f.v == null) {
                    c0015f.v = viewA;
                }
                if (c0015f.q) {
                    iA -= ActionMenuView.a(viewA, i2, iA, iMakeMeasureSpec, i3);
                } else {
                    viewA.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                }
                measuredWidth = viewA.getMeasuredWidth();
                i12 -= measuredWidth;
                if (i14 != 0) {
                    measuredWidth = i14;
                }
                int i15 = nVar2.f233b;
                if (i15 != 0) {
                    z = true;
                    sparseBooleanArray.put(i15, true);
                } else {
                    z = true;
                }
                nVar2.c(z);
                size = size;
            } else {
                if ((nVar2.z & 1) == 1) {
                    int i16 = nVar2.f233b;
                    boolean z3 = sparseBooleanArray.get(i16);
                    boolean z4 = (i10 > 0 || z3) && i12 > 0 && (!c0015f.q || iA > 0);
                    if (z4) {
                        boolean z5 = z4;
                        View viewA2 = c0015f.a(nVar2, c0015f.v, viewGroup);
                        if (c0015f.v == null) {
                            c0015f.v = viewA2;
                        }
                        if (c0015f.q) {
                            int iA2 = ActionMenuView.a(viewA2, i2, iA, iMakeMeasureSpec, 0);
                            iA -= iA2;
                            if (iA2 == 0) {
                                z5 = false;
                            }
                        } else {
                            viewA2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                        }
                        int measuredWidth2 = viewA2.getMeasuredWidth();
                        i12 -= measuredWidth2;
                        if (i14 == 0) {
                            i14 = measuredWidth2;
                        }
                        z4 = z5 & (!c0015f.q ? i12 + i14 <= 0 : i12 < 0);
                    }
                    if (z4 && i16 != 0) {
                        sparseBooleanArray.put(i16, true);
                    } else if (z3) {
                        sparseBooleanArray.put(i16, false);
                        for (int i17 = 0; i17 < i13; i17++) {
                            a.b.e.e.a.n nVar3 = arrayListC.get(i17);
                            if (nVar3.f233b == i16) {
                                if (nVar3.d()) {
                                    i10++;
                                }
                                nVar3.c(false);
                            }
                        }
                    }
                    if (z4) {
                        i10--;
                    }
                    nVar2.c(z4);
                    measuredWidth = i14;
                } else {
                    size = size;
                    nVar2.c(false);
                }
                i13++;
                i3 = 0;
                i = 2;
                c0015f = this;
                size = size;
            }
            i14 = measuredWidth;
            i13++;
            i3 = 0;
            i = 2;
            c0015f = this;
            size = size;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // a.b.e.e.a.t
    public void updateMenuView(boolean z) {
        int i;
        boolean z2;
        ViewGroup viewGroup = (ViewGroup) this.g;
        boolean z3 = false;
        ArrayList<a.b.e.e.a.n> arrayList = null;
        if (viewGroup != null) {
            a.b.e.e.a.k kVar = this.f210c;
            if (kVar != null) {
                kVar.a();
                ArrayList<a.b.e.e.a.n> arrayListC = this.f210c.c();
                int size = arrayListC.size();
                i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    a.b.e.e.a.n nVar = arrayListC.get(i2);
                    if (nVar.d()) {
                        View childAt = viewGroup.getChildAt(i);
                        a.b.e.e.a.n itemData = childAt instanceof a.b.e.e.a.u.a ? ((a.b.e.e.a.u.a) childAt).getItemData() : null;
                        View viewA = a(nVar, childAt, viewGroup);
                        if (nVar != itemData) {
                            viewA.setPressed(false);
                            viewA.jumpDrawablesToCurrentState();
                        }
                        if (viewA != childAt) {
                            ViewGroup viewGroup2 = (ViewGroup) viewA.getParent();
                            if (viewGroup2 != null) {
                                viewGroup2.removeView(viewA);
                            }
                            ((ViewGroup) this.g).addView(viewA, i);
                        }
                        i++;
                    }
                }
            } else {
                i = 0;
            }
            while (i < viewGroup.getChildCount()) {
                if (viewGroup.getChildAt(i) == this.h) {
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
        ((View) this.g).requestLayout();
        a.b.e.e.a.k kVar2 = this.f210c;
        if (kVar2 != null) {
            kVar2.a();
            ArrayList<a.b.e.e.a.n> arrayList2 = kVar2.j;
            int size2 = arrayList2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                a.b.d.h.e eVar = arrayList2.get(i3).B;
            }
        }
        a.b.e.e.a.k kVar3 = this.f210c;
        if (kVar3 != null) {
            kVar3.a();
            arrayList = kVar3.k;
        }
        if (this.k && arrayList != null) {
            int size3 = arrayList.size();
            if (size3 == 1) {
                z3 = !arrayList.get(0).D;
            } else if (size3 > 0) {
                z3 = true;
            }
        }
        if (z3) {
            if (this.h == null) {
                this.h = new d(this.f208a);
            }
            ViewGroup viewGroup3 = (ViewGroup) this.h.getParent();
            if (viewGroup3 != this.g) {
                if (viewGroup3 != null) {
                    viewGroup3.removeView(this.h);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.g;
                actionMenuView.addView(this.h, actionMenuView.b());
            }
        } else {
            d dVar = this.h;
            if (dVar != null) {
                Object parent = dVar.getParent();
                Object obj = this.g;
                if (parent == obj) {
                    ((ViewGroup) obj).removeView(this.h);
                }
            }
        }
        ((ActionMenuView) this.g).setOverflowReserved(this.k);
    }

    /* JADX INFO: renamed from: a.b.e.f.f$f, reason: collision with other inner class name */
    private class C0007f implements a.b.e.e.a.t.a {
        public C0007f() {
        }

        @Override // a.b.e.e.a.t.a
        public void a(a.b.e.e.a.k kVar, boolean z) {
            if (kVar instanceof a.b.e.e.a.A) {
                kVar.b().a(false);
            }
            a.b.e.e.a.t.a aVar = C0015f.this.e;
            if (aVar != null) {
                aVar.a(kVar, z);
            }
        }

        @Override // a.b.e.e.a.t.a
        public boolean a(a.b.e.e.a.k kVar) {
            if (kVar == null) {
                return false;
            }
            C0015f c0015f = C0015f.this;
            ((a.b.e.e.a.A) kVar).A.getItemId();
            a.b.e.e.a.t.a aVar = C0015f.this.e;
            if (aVar != null) {
                return aVar.a(kVar);
            }
            return false;
        }
    }

    public boolean a() {
        Object obj;
        c cVar = this.y;
        if (cVar != null && (obj = this.g) != null) {
            ((View) obj).removeCallbacks(cVar);
            this.y = null;
            return true;
        }
        e eVar = this.w;
        if (eVar == null) {
            return false;
        }
        if (eVar.c()) {
            eVar.j.dismiss();
        }
        return true;
    }

    @Override // a.b.e.e.a.t
    public void a(Context context, a.b.e.e.a.k kVar) {
        this.f209b = context;
        LayoutInflater.from(this.f209b);
        this.f210c = kVar;
        Resources resources = context.getResources();
        if (!this.l) {
            int i = Build.VERSION.SDK_INT;
            this.k = true;
        }
        int i2 = 2;
        if (!this.r) {
            this.m = context.getResources().getDisplayMetrics().widthPixels / 2;
        }
        if (!this.p) {
            Configuration configuration = context.getResources().getConfiguration();
            int i3 = configuration.screenWidthDp;
            int i4 = configuration.screenHeightDp;
            if (configuration.smallestScreenWidthDp > 600 || i3 > 600 || ((i3 > 960 && i4 > 720) || (i3 > 720 && i4 > 960))) {
                i2 = 5;
            } else if (i3 >= 500 || ((i3 > 640 && i4 > 480) || (i3 > 480 && i4 > 640))) {
                i2 = 4;
            } else if (i3 >= 360) {
                i2 = 3;
            }
            this.o = i2;
        }
        int measuredWidth = this.m;
        if (this.k) {
            if (this.h == null) {
                this.h = new d(this.f208a);
                if (this.j) {
                    this.h.setImageDrawable(this.i);
                    this.i = null;
                    this.j = false;
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.h.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.h.getMeasuredWidth();
        } else {
            this.h = null;
        }
        this.n = measuredWidth;
        this.t = (int) (resources.getDisplayMetrics().density * 56.0f);
        this.v = null;
    }

    @Override // a.b.e.e.a.t
    public void a(a.b.e.e.a.k kVar, boolean z) {
        a();
        a aVar = this.x;
        if (aVar != null) {
            aVar.a();
        }
        a.b.e.e.a.t.a aVar2 = this.e;
        if (aVar2 != null) {
            aVar2.a(kVar, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // a.b.e.e.a.t
    public boolean a(a.b.e.e.a.A a2) {
        boolean z = false;
        if (!a2.hasVisibleItems()) {
            return false;
        }
        a.b.e.e.a.A a3 = a2;
        while (a3.z != this.f210c) {
            a3 = (a.b.e.e.a.A) a3.z;
        }
        a.b.e.e.a.n nVar = a3.A;
        ViewGroup viewGroup = (ViewGroup) this.g;
        View view = null;
        view = null;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if ((childAt instanceof a.b.e.e.a.u.a) && ((a.b.e.e.a.u.a) childAt).getItemData() == nVar) {
                    view = childAt;
                    break;
                }
            }
        }
        if (view == null) {
            return false;
        }
        a2.A.getItemId();
        int size = a2.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = a2.getItem(i2);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
        }
        this.x = new a(this.f209b, a2, view);
        a aVar = this.x;
        aVar.h = z;
        a.b.e.e.a.q qVar = aVar.j;
        if (qVar != null) {
            qVar.a(z);
        }
        if (this.x.e()) {
            a.b.e.e.a.t.a aVar2 = this.e;
            if (aVar2 != null) {
                aVar2.a(a2);
            }
            return true;
        }
        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }
}
