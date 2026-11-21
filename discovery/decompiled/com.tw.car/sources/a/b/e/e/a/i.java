package a.b.e.e.a;

import a.b.e.f.H;
import a.b.e.f.I;
import a.b.e.f.J;
import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class i extends q implements t, View.OnKeyListener, PopupWindow.OnDismissListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int f220b = a.b.e.b.g.abc_cascading_menu_item_layout;
    public PopupWindow.OnDismissListener A;
    public boolean B;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f221c;
    public final int d;
    public final int e;
    public final int f;
    public final boolean g;
    public final Handler h;
    public View p;
    public View q;
    public int r;
    public boolean s;
    public boolean t;
    public int u;
    public int v;
    public boolean x;
    public t.a y;
    public ViewTreeObserver z;
    public final List<k> i = new ArrayList();
    public final List<a> j = new ArrayList();
    public final ViewTreeObserver.OnGlobalLayoutListener k = new e(this);
    public final View.OnAttachStateChangeListener l = new f(this);
    public final I m = new h(this);
    public int n = 0;
    public int o = 0;
    public boolean w = false;

    /* JADX INFO: Access modifiers changed from: private */
    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final J f222a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final k f223b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f224c;

        public a(J j, k kVar, int i) {
            this.f222a = j;
            this.f223b = kVar;
            this.f224c = i;
        }
    }

    public i(Context context, View view, int i, int i2, boolean z) {
        this.f221c = context;
        this.p = view;
        this.e = i;
        this.f = i2;
        this.g = z;
        this.r = a.b.d.h.n.c(this.p) == 1 ? 0 : 1;
        Resources resources = context.getResources();
        this.d = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(a.b.e.b.d.abc_config_prefDialogWidth));
        this.h = new Handler();
    }

    @Override // a.b.e.e.a.w
    public ListView a() {
        if (this.j.isEmpty()) {
            return null;
        }
        List<a> list = this.j;
        return list.get(list.size() - 1).f222a.f;
    }

    @Override // a.b.e.e.a.q
    public void a(int i) {
        if (this.n != i) {
            this.n = i;
            this.o = a.b.b.a.e.a(i, a.b.d.h.n.c(this.p));
        }
    }

    @Override // a.b.e.e.a.q
    public void a(k kVar) {
        kVar.a(this, this.f221c);
        if (isShowing()) {
            c(kVar);
        } else {
            this.i.add(kVar);
        }
    }

    @Override // a.b.e.e.a.t
    public void a(t.a aVar) {
        this.y = aVar;
    }

    @Override // a.b.e.e.a.q
    public void a(View view) {
        if (this.p != view) {
            this.p = view;
            this.o = a.b.b.a.e.a(this.n, a.b.d.h.n.c(this.p));
        }
    }

    @Override // a.b.e.e.a.q
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.A = onDismissListener;
    }

    @Override // a.b.e.e.a.q
    public void a(boolean z) {
        this.w = z;
    }

    @Override // a.b.e.e.a.q
    public void b(int i) {
        this.s = true;
        this.u = i;
    }

    @Override // a.b.e.e.a.q
    public void b(boolean z) {
        this.x = z;
    }

    @Override // a.b.e.e.a.q
    public boolean b() {
        return false;
    }

    @Override // a.b.e.e.a.q
    public void c(int i) {
        this.t = true;
        this.v = i;
    }

    /* JADX WARN: Code duplicated, block: B:107:0x010d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x0109  */
    /* JADX WARN: Code duplicated, block: B:62:0x0128  */
    /* JADX WARN: Code duplicated, block: B:73:0x016a  */
    /* JADX WARN: Code duplicated, block: B:74:0x016c  */
    /* JADX WARN: Code duplicated, block: B:77:0x0176  */
    /* JADX WARN: Code duplicated, block: B:78:0x017b  */
    /* JADX WARN: Code duplicated, block: B:80:0x018e  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:86:0x01b4  */
    /* JADX WARN: Code duplicated, block: B:87:0x01b9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:88:0x01bb  */
    /* JADX WARN: Code duplicated, block: B:89:0x01bf A[PHI: r4
      0x01bf: PHI (r4v14 int) = (r4v13 int), (r4v5 int) binds: [B:88:0x01bb, B:84:0x01b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:90:0x01c1 A[PHI: r4
      0x01c1: PHI (r4v15 int) = (r4v5 int), (r4v16 int) binds: [B:87:0x01b9, B:86:0x01b4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:92:0x01cc  */
    /* JADX WARN: Code duplicated, block: B:94:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:97:0x01d8  */
    public final void c(k kVar) {
        a aVar;
        Method method;
        int i;
        boolean z;
        int[] iArr;
        int[] iArr2;
        int i2;
        int i3;
        int i4;
        MenuItem item;
        j jVar;
        int headersCount;
        int firstVisiblePosition;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f221c);
        j jVar2 = new j(kVar, layoutInflaterFrom, this.g, f220b);
        if (!isShowing() && this.w) {
            jVar2.f227c = true;
        } else if (isShowing()) {
            jVar2.f227c = q.b(kVar);
        }
        View childAt = null;
        int iA = q.a(jVar2, null, this.f221c, this.d);
        J j = new J(this.f221c, null, this.e, this.f);
        j.J = this.m;
        j.x = this;
        j.H.setOnDismissListener(this);
        j.v = this.p;
        j.o = this.o;
        j.a(true);
        j.H.setInputMethodMode(2);
        DataSetObserver dataSetObserver = j.u;
        if (dataSetObserver == null) {
            j.u = new H.b();
        } else {
            ListAdapter listAdapter = j.e;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
        j.e = jVar2;
        jVar2.registerDataSetObserver(j.u);
        a.b.e.f.B b2 = j.f;
        if (b2 != null) {
            b2.setAdapter(j.e);
        }
        j.a(iA);
        j.o = this.o;
        if (this.j.size() > 0) {
            List<a> list = this.j;
            aVar = list.get(list.size() - 1);
            k kVar2 = aVar.f223b;
            int size = kVar2.size();
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    item = null;
                    break;
                }
                item = kVar2.getItem(i5);
                if (item.hasSubMenu() && kVar == item.getSubMenu()) {
                    break;
                } else {
                    i5++;
                }
            }
            if (item != null) {
                a.b.e.f.B b3 = aVar.f222a.f;
                ListAdapter adapter = b3.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    jVar = (j) headerViewListAdapter.getWrappedAdapter();
                } else {
                    jVar = (j) adapter;
                    headersCount = 0;
                }
                int count = jVar.getCount();
                int i6 = 0;
                while (true) {
                    if (i6 >= count) {
                        i6 = -1;
                        break;
                    } else if (item == jVar.getItem(i6)) {
                        break;
                    } else {
                        i6++;
                    }
                }
                if (i6 != -1 && (firstVisiblePosition = (i6 + headersCount) - b3.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < b3.getChildCount()) {
                    childAt = b3.getChildAt(firstVisiblePosition);
                }
            }
            if (childAt != null) {
                method = J.I;
                if (method != null) {
                    try {
                        method.invoke(j.H, false);
                    } catch (Exception unused) {
                        Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
                    }
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    j.H.setEnterTransition(null);
                }
                List<a> list2 = this.j;
                a.b.e.f.B b4 = list2.get(list2.size() - 1).f222a.f;
                int[] iArr3 = new int[2];
                b4.getLocationOnScreen(iArr3);
                Rect rect = new Rect();
                this.q.getWindowVisibleDisplayFrame(rect);
                i = (this.r == 1 ? iArr3[0] - iA >= 0 : (b4.getWidth() + iArr3[0]) + iA > rect.right) ? 0 : 1;
                if (i == 1) {
                    z = true;
                } else {
                    z = false;
                }
                this.r = i;
                if (Build.VERSION.SDK_INT >= 26) {
                    j.v = childAt;
                    i3 = 0;
                    i2 = 0;
                } else {
                    iArr = new int[2];
                    this.p.getLocationOnScreen(iArr);
                    iArr2 = new int[2];
                    childAt.getLocationOnScreen(iArr2);
                    if ((this.o & 7) == 5) {
                        iArr[0] = this.p.getWidth() + iArr[0];
                        iArr2[0] = childAt.getWidth() + iArr2[0];
                    }
                    i2 = iArr2[0] - iArr[0];
                    i3 = iArr2[1] - iArr[1];
                }
                if ((this.o & 5) == 5) {
                    if (z) {
                        i4 = i2 + iA;
                    } else {
                        iA = childAt.getWidth();
                        i4 = i2 - iA;
                    }
                } else if (z) {
                    iA = childAt.getWidth();
                    i4 = i2 + iA;
                } else {
                    i4 = i2 - iA;
                }
                j.i = i4;
                j.n = true;
                j.m = true;
                j.b(i3);
            } else {
                if (this.s) {
                    j.i = this.u;
                }
                if (this.t) {
                    j.b(this.v);
                }
                j.F = this.f240a;
            }
            this.j.add(new a(j, kVar, this.r));
            j.show();
            a.b.e.f.B b5 = j.f;
            b5.setOnKeyListener(this);
            if (aVar == null || !this.x || kVar.o == null) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(a.b.e.b.g.abc_popup_menu_header_item_layout, (ViewGroup) b5, false);
            TextView textView = (TextView) frameLayout.findViewById(R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(kVar.o);
            b5.addHeaderView(frameLayout, null, false);
            j.show();
            return;
        }
        aVar = null;
        childAt = null;
        if (childAt != null) {
            method = J.I;
            if (method != null) {
                method.invoke(j.H, false);
            }
            if (Build.VERSION.SDK_INT >= 23) {
                j.H.setEnterTransition(null);
            }
            List<a> list3 = this.j;
            a.b.e.f.B b6 = list3.get(list3.size() - 1).f222a.f;
            int[] iArr4 = new int[2];
            b6.getLocationOnScreen(iArr4);
            Rect rect2 = new Rect();
            this.q.getWindowVisibleDisplayFrame(rect2);
            if (this.r == 1) {
            }
            if (i == 1) {
                z = true;
            } else {
                z = false;
            }
            this.r = i;
            if (Build.VERSION.SDK_INT >= 26) {
                j.v = childAt;
                i3 = 0;
                i2 = 0;
            } else {
                iArr = new int[2];
                this.p.getLocationOnScreen(iArr);
                iArr2 = new int[2];
                childAt.getLocationOnScreen(iArr2);
                if ((this.o & 7) == 5) {
                    iArr[0] = this.p.getWidth() + iArr[0];
                    iArr2[0] = childAt.getWidth() + iArr2[0];
                }
                i2 = iArr2[0] - iArr[0];
                i3 = iArr2[1] - iArr[1];
            }
            if ((this.o & 5) == 5) {
                if (z) {
                    i4 = i2 + iA;
                } else {
                    iA = childAt.getWidth();
                    i4 = i2 - iA;
                }
            } else if (z) {
                iA = childAt.getWidth();
                i4 = i2 + iA;
            } else {
                i4 = i2 - iA;
            }
            j.i = i4;
            j.n = true;
            j.m = true;
            j.b(i3);
        } else {
            if (this.s) {
                j.i = this.u;
            }
            if (this.t) {
                j.b(this.v);
            }
            j.F = this.f240a;
        }
        this.j.add(new a(j, kVar, this.r));
        j.show();
        a.b.e.f.B b7 = j.f;
        b7.setOnKeyListener(this);
        if (aVar == null) {
        }
    }

    @Override // a.b.e.e.a.w
    public void dismiss() {
        int size = this.j.size();
        if (size > 0) {
            a[] aVarArr = (a[]) this.j.toArray(new a[size]);
            for (int i = size - 1; i >= 0; i--) {
                a aVar = aVarArr[i];
                if (aVar.f222a.isShowing()) {
                    aVar.f222a.dismiss();
                }
            }
        }
    }

    @Override // a.b.e.e.a.t
    public boolean flagActionItems() {
        return false;
    }

    @Override // a.b.e.e.a.w
    public boolean isShowing() {
        return this.j.size() > 0 && this.j.get(0).f222a.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        a aVar;
        int size = this.j.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                aVar = null;
                break;
            }
            aVar = this.j.get(i);
            if (!aVar.f222a.isShowing()) {
                break;
            } else {
                i++;
            }
        }
        if (aVar != null) {
            aVar.f223b.a(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // a.b.e.e.a.w
    public void show() {
        if (isShowing()) {
            return;
        }
        Iterator<k> it = this.i.iterator();
        while (it.hasNext()) {
            c(it.next());
        }
        this.i.clear();
        this.q = this.p;
        if (this.q != null) {
            boolean z = this.z == null;
            this.z = this.q.getViewTreeObserver();
            if (z) {
                this.z.addOnGlobalLayoutListener(this.k);
            }
            this.q.addOnAttachStateChangeListener(this.l);
        }
    }

    @Override // a.b.e.e.a.t
    public void updateMenuView(boolean z) {
        Iterator<a> it = this.j.iterator();
        while (it.hasNext()) {
            q.a(it.next().f222a.f.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override // a.b.e.e.a.t
    public void a(k kVar, boolean z) {
        int i;
        int size = this.j.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (kVar == this.j.get(i2).f223b) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 < 0) {
            return;
        }
        int i3 = i2 + 1;
        if (i3 < this.j.size()) {
            this.j.get(i3).f223b.a(false);
        }
        a aVarRemove = this.j.remove(i2);
        k kVar2 = aVarRemove.f223b;
        for (WeakReference<t> weakReference : kVar2.v) {
            t tVar = weakReference.get();
            if (tVar == null || tVar == this) {
                kVar2.v.remove(weakReference);
            }
        }
        if (this.B) {
            aVarRemove.f222a.a((Object) null);
            aVarRemove.f222a.H.setAnimationStyle(0);
        }
        aVarRemove.f222a.dismiss();
        int size2 = this.j.size();
        if (size2 > 0) {
            i = this.j.get(size2 - 1).f224c;
        } else {
            i = a.b.d.h.n.c(this.p) == 1 ? 0 : 1;
        }
        this.r = i;
        if (size2 != 0) {
            if (z) {
                this.j.get(0).f223b.a(false);
                return;
            }
            return;
        }
        dismiss();
        t.a aVar = this.y;
        if (aVar != null) {
            aVar.a(kVar, true);
        }
        ViewTreeObserver viewTreeObserver = this.z;
        if (viewTreeObserver != null) {
            if (viewTreeObserver.isAlive()) {
                this.z.removeGlobalOnLayoutListener(this.k);
            }
            this.z = null;
        }
        this.q.removeOnAttachStateChangeListener(this.l);
        this.A.onDismiss();
    }

    @Override // a.b.e.e.a.t
    public boolean a(A a2) {
        for (a aVar : this.j) {
            if (a2 == aVar.f223b) {
                aVar.f222a.f.requestFocus();
                return true;
            }
        }
        if (!a2.hasVisibleItems()) {
            return false;
        }
        a2.a(this, this.f221c);
        if (isShowing()) {
            c(a2);
        } else {
            this.i.add(a2);
        }
        t.a aVar2 = this.y;
        if (aVar2 != null) {
            aVar2.a(a2);
        }
        return true;
    }
}
