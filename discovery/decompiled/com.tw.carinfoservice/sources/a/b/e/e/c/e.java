package a.b.e.e.c;

import a.b.e.f.w;
import a.b.e.f.x;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
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
import com.tw.carinfoservice.R;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class e extends k implements m, View.OnKeyListener, PopupWindow.OnDismissListener {
    public PopupWindow.OnDismissListener A;
    public boolean B;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f214c;
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
    public m.a y;
    public ViewTreeObserver z;
    public final List<g> i = new ArrayList();
    public final List<d> j = new ArrayList();
    public final ViewTreeObserver.OnGlobalLayoutListener k = new a();
    public final View.OnAttachStateChangeListener l = new b();
    public final w m = new c();
    public int n = 0;
    public int o = 0;
    public boolean w = false;

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (!e.this.c() || e.this.j.size() <= 0 || e.this.j.get(0).f220a.z) {
                return;
            }
            View view = e.this.q;
            if (view == null || !view.isShown()) {
                e.this.i();
                return;
            }
            Iterator<d> it = e.this.j.iterator();
            while (it.hasNext()) {
                it.next().f220a.f();
            }
        }
    }

    public class b implements View.OnAttachStateChangeListener {
        public b() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            ViewTreeObserver viewTreeObserver = e.this.z;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    e.this.z = view.getViewTreeObserver();
                }
                e eVar = e.this;
                eVar.z.removeGlobalOnLayoutListener(eVar.k);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    public class c implements w {

        public class a implements Runnable {

            /* JADX INFO: renamed from: b, reason: collision with root package name */
            public final /* synthetic */ d f218b;

            /* JADX INFO: renamed from: c, reason: collision with root package name */
            public final /* synthetic */ MenuItem f219c;
            public final /* synthetic */ g d;

            public a(d dVar, MenuItem menuItem, g gVar) {
                this.f218b = dVar;
                this.f219c = menuItem;
                this.d = gVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                d dVar = this.f218b;
                if (dVar != null) {
                    e.this.B = true;
                    dVar.f221b.c(false);
                    e.this.B = false;
                }
                if (this.f219c.isEnabled() && this.f219c.hasSubMenu()) {
                    this.d.q(this.f219c, 4);
                }
            }
        }

        public c() {
        }

        @Override // a.b.e.f.w
        public void a(g gVar, MenuItem menuItem) {
            e.this.h.removeCallbacksAndMessages(null);
            int size = e.this.j.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (gVar == e.this.j.get(i).f221b) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            int i2 = i + 1;
            e.this.h.postAtTime(new a(i2 < e.this.j.size() ? e.this.j.get(i2) : null, menuItem, gVar), gVar, SystemClock.uptimeMillis() + 200);
        }

        @Override // a.b.e.f.w
        public void b(g gVar, MenuItem menuItem) {
            e.this.h.removeCallbacksAndMessages(gVar);
        }
    }

    public static class d {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final x f220a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final g f221b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final int f222c;

        public d(x xVar, g gVar, int i) {
            this.f220a = xVar;
            this.f221b = gVar;
            this.f222c = i;
        }
    }

    public e(Context context, View view, int i, int i2, boolean z) {
        this.f214c = context;
        this.p = view;
        this.e = i;
        this.f = i2;
        this.g = z;
        int i3 = a.b.d.h.j.f151a;
        this.r = view.getLayoutDirection() != 1 ? 1 : 0;
        Resources resources = context.getResources();
        this.d = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.h = new Handler();
    }

    @Override // a.b.e.e.c.m
    public void a(g gVar, boolean z) {
        int i;
        int size = this.j.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (gVar == this.j.get(i2).f221b) {
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
            this.j.get(i3).f221b.c(false);
        }
        d dVarRemove = this.j.remove(i2);
        g gVar2 = dVarRemove.f221b;
        for (WeakReference<m> weakReference : gVar2.r) {
            m mVar = weakReference.get();
            if (mVar == null || mVar == this) {
                gVar2.r.remove(weakReference);
            }
        }
        if (this.B) {
            dVarRemove.f220a.m(null);
            dVarRemove.f220a.A.setAnimationStyle(0);
        }
        dVarRemove.f220a.i();
        int size2 = this.j.size();
        if (size2 > 0) {
            i = this.j.get(size2 - 1).f222c;
        } else {
            View view = this.p;
            int i4 = a.b.d.h.j.f151a;
            i = view.getLayoutDirection() == 1 ? 0 : 1;
        }
        this.r = i;
        if (size2 != 0) {
            if (z) {
                this.j.get(0).f221b.c(false);
                return;
            }
            return;
        }
        i();
        m.a aVar = this.y;
        if (aVar != null) {
            aVar.a(gVar, true);
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

    @Override // a.b.e.e.c.m
    public boolean b(q qVar) {
        for (d dVar : this.j) {
            if (qVar == dVar.f221b) {
                dVar.f220a.d.requestFocus();
                return true;
            }
        }
        if (!qVar.hasVisibleItems()) {
            return false;
        }
        qVar.b(this, this.f214c);
        if (c()) {
            x(qVar);
        } else {
            this.i.add(qVar);
        }
        m.a aVar = this.y;
        if (aVar != null) {
            aVar.b(qVar);
        }
        return true;
    }

    @Override // a.b.e.e.c.o
    public boolean c() {
        return this.j.size() > 0 && this.j.get(0).f220a.c();
    }

    @Override // a.b.e.e.c.o
    public ListView d() {
        if (this.j.isEmpty()) {
            return null;
        }
        List<d> list = this.j;
        return list.get(list.size() - 1).f220a.d;
    }

    @Override // a.b.e.e.c.o
    public void f() {
        if (c()) {
            return;
        }
        Iterator<g> it = this.i.iterator();
        while (it.hasNext()) {
            x(it.next());
        }
        this.i.clear();
        View view = this.p;
        this.q = view;
        if (view != null) {
            boolean z = this.z == null;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            this.z = viewTreeObserver;
            if (z) {
                viewTreeObserver.addOnGlobalLayoutListener(this.k);
            }
            this.q.addOnAttachStateChangeListener(this.l);
        }
    }

    @Override // a.b.e.e.c.m
    public boolean g() {
        return false;
    }

    @Override // a.b.e.e.c.o
    public void i() {
        int size = this.j.size();
        if (size > 0) {
            d[] dVarArr = (d[]) this.j.toArray(new d[size]);
            for (int i = size - 1; i >= 0; i--) {
                d dVar = dVarArr[i];
                if (dVar.f220a.c()) {
                    dVar.f220a.i();
                }
            }
        }
    }

    @Override // a.b.e.e.c.m
    public void j(boolean z) {
        Iterator<d> it = this.j.iterator();
        while (it.hasNext()) {
            ListAdapter adapter = it.next().f220a.d.getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
            }
            ((f) adapter).notifyDataSetChanged();
        }
    }

    @Override // a.b.e.e.c.m
    public void k(m.a aVar) {
        this.y = aVar;
    }

    @Override // a.b.e.e.c.k
    public void m(g gVar) {
        gVar.b(this, this.f214c);
        if (c()) {
            x(gVar);
        } else {
            this.i.add(gVar);
        }
    }

    @Override // a.b.e.e.c.k
    public boolean n() {
        return false;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        d dVar;
        int size = this.j.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                dVar = null;
                break;
            }
            dVar = this.j.get(i);
            if (!dVar.f220a.c()) {
                break;
            } else {
                i++;
            }
        }
        if (dVar != null) {
            dVar.f221b.c(false);
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        i();
        return true;
    }

    @Override // a.b.e.e.c.k
    public void p(View view) {
        if (this.p != view) {
            this.p = view;
            int i = this.n;
            int i2 = a.b.d.h.j.f151a;
            this.o = Gravity.getAbsoluteGravity(i, view.getLayoutDirection());
        }
    }

    @Override // a.b.e.e.c.k
    public void q(boolean z) {
        this.w = z;
    }

    @Override // a.b.e.e.c.k
    public void r(int i) {
        if (this.n != i) {
            this.n = i;
            View view = this.p;
            int i2 = a.b.d.h.j.f151a;
            this.o = Gravity.getAbsoluteGravity(i, view.getLayoutDirection());
        }
    }

    @Override // a.b.e.e.c.k
    public void s(int i) {
        this.s = true;
        this.u = i;
    }

    @Override // a.b.e.e.c.k
    public void t(PopupWindow.OnDismissListener onDismissListener) {
        this.A = onDismissListener;
    }

    @Override // a.b.e.e.c.k
    public void u(boolean z) {
        this.x = z;
    }

    @Override // a.b.e.e.c.k
    public void v(int i) {
        this.t = true;
        this.v = i;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:61:0x0141  */
    /* JADX WARN: Code duplicated, block: B:62:0x0143  */
    /* JADX WARN: Code duplicated, block: B:65:0x014d  */
    /* JADX WARN: Code duplicated, block: B:66:0x0152  */
    /* JADX WARN: Code duplicated, block: B:68:0x0165  */
    /* JADX WARN: Code duplicated, block: B:72:0x0188 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x018b  */
    /* JADX WARN: Code duplicated, block: B:75:0x0190 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:76:0x0192  */
    /* JADX WARN: Code duplicated, block: B:77:0x0196 A[PHI: r4
      0x0196: PHI (r4v14 int) = (r4v13 int), (r4v5 int) binds: [B:76:0x0192, B:72:0x0188] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:78:0x0198 A[PHI: r4
      0x0198: PHI (r4v15 int) = (r4v5 int), (r4v16 int) binds: [B:75:0x0190, B:74:0x018b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:80:0x01a3  */
    /* JADX WARN: Code duplicated, block: B:82:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:85:0x01af  */
    /* JADX WARN: Code duplicated, block: B:95:0x00ee A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public final void x(g gVar) {
        d dVar;
        Method method;
        int i;
        boolean z;
        int[] iArr;
        int[] iArr2;
        int i2;
        int i3;
        int i4;
        MenuItem item;
        f fVar;
        int headersCount;
        int firstVisiblePosition;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.f214c);
        f fVar2 = new f(gVar, layoutInflaterFrom, this.g, R.layout.abc_cascading_menu_item_layout);
        if (!c() && this.w) {
            fVar2.d = true;
        } else if (c()) {
            fVar2.d = k.w(gVar);
        }
        View childAt = null;
        int iO = k.o(fVar2, null, this.f214c, this.d);
        x xVar = new x(this.f214c, null, this.e, this.f);
        xVar.E = this.m;
        xVar.r = this;
        xVar.A.setOnDismissListener(this);
        xVar.q = this.p;
        xVar.m = this.o;
        xVar.j(true);
        xVar.A.setInputMethodMode(2);
        xVar.g(fVar2);
        xVar.h(iO);
        xVar.m = this.o;
        if (this.j.size() > 0) {
            List<d> list = this.j;
            dVar = list.get(list.size() - 1);
            g gVar2 = dVar.f221b;
            int size = gVar2.size();
            int i5 = 0;
            while (true) {
                if (i5 >= size) {
                    item = null;
                    break;
                }
                item = gVar2.getItem(i5);
                if (item.hasSubMenu() && gVar == item.getSubMenu()) {
                    break;
                } else {
                    i5++;
                }
            }
            if (item != null) {
                a.b.e.f.q qVar = dVar.f220a.d;
                ListAdapter adapter = qVar.getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                    headersCount = headerViewListAdapter.getHeadersCount();
                    fVar = (f) headerViewListAdapter.getWrappedAdapter();
                } else {
                    fVar = (f) adapter;
                    headersCount = 0;
                }
                int count = fVar.getCount();
                int i6 = 0;
                while (true) {
                    if (i6 >= count) {
                        i6 = -1;
                        break;
                    } else if (item == fVar.getItem(i6)) {
                        break;
                    } else {
                        i6++;
                    }
                }
                if (i6 != -1 && (firstVisiblePosition = (i6 + headersCount) - qVar.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < qVar.getChildCount()) {
                    childAt = qVar.getChildAt(firstVisiblePosition);
                }
            }
            if (childAt != null) {
                method = x.F;
                if (method != null) {
                    try {
                        method.invoke(xVar.A, Boolean.FALSE);
                    } catch (Exception unused) {
                        Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
                    }
                }
                xVar.l(null);
                List<d> list2 = this.j;
                a.b.e.f.q qVar2 = list2.get(list2.size() - 1).f220a.d;
                int[] iArr3 = new int[2];
                qVar2.getLocationOnScreen(iArr3);
                Rect rect = new Rect();
                this.q.getWindowVisibleDisplayFrame(rect);
                i = (this.r == 1 ? iArr3[0] - iO >= 0 : (qVar2.getWidth() + iArr3[0]) + iO > rect.right) ? 0 : 1;
                if (i == 1) {
                    z = true;
                } else {
                    z = false;
                }
                this.r = i;
                if (Build.VERSION.SDK_INT >= 26) {
                    xVar.q = childAt;
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
                        i4 = i2 + iO;
                    } else {
                        iO = childAt.getWidth();
                        i4 = i2 - iO;
                    }
                } else if (z) {
                    iO = childAt.getWidth();
                    i4 = i2 + iO;
                } else {
                    i4 = i2 - iO;
                }
                xVar.g = i4;
                xVar.l = true;
                xVar.k = true;
                xVar.k(i3);
            } else {
                if (this.s) {
                    xVar.g = this.u;
                }
                if (this.t) {
                    xVar.k(this.v);
                }
                xVar.y = this.f237b;
            }
            this.j.add(new d(xVar, gVar, this.r));
            xVar.f();
            a.b.e.f.q qVar3 = xVar.d;
            qVar3.setOnKeyListener(this);
            if (dVar == null || !this.x || gVar.l == null) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) layoutInflaterFrom.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) qVar3, false);
            TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
            frameLayout.setEnabled(false);
            textView.setText(gVar.l);
            qVar3.addHeaderView(frameLayout, null, false);
            xVar.f();
            return;
        }
        dVar = null;
        childAt = null;
        if (childAt != null) {
            method = x.F;
            if (method != null) {
                method.invoke(xVar.A, Boolean.FALSE);
            }
            xVar.l(null);
            List<d> list3 = this.j;
            a.b.e.f.q qVar4 = list3.get(list3.size() - 1).f220a.d;
            int[] iArr4 = new int[2];
            qVar4.getLocationOnScreen(iArr4);
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
                xVar.q = childAt;
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
                    i4 = i2 + iO;
                } else {
                    iO = childAt.getWidth();
                    i4 = i2 - iO;
                }
            } else if (z) {
                iO = childAt.getWidth();
                i4 = i2 + iO;
            } else {
                i4 = i2 - iO;
            }
            xVar.g = i4;
            xVar.l = true;
            xVar.k = true;
            xVar.k(i3);
        } else {
            if (this.s) {
                xVar.g = this.u;
            }
            if (this.t) {
                xVar.k(this.v);
            }
            xVar.y = this.f237b;
        }
        this.j.add(new d(xVar, gVar, this.r));
        xVar.f();
        a.b.e.f.q qVar5 = xVar.d;
        qVar5.setOnKeyListener(this);
        if (dVar == null) {
        }
    }
}
