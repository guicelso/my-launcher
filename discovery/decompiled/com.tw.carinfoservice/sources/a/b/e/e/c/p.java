package a.b.e.e.c;

import a.b.e.f.x;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.tw.carinfoservice.R;

/* JADX INFO: loaded from: classes.dex */
public final class p extends k implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, m, View.OnKeyListener {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f242c;
    public final g d;
    public final f e;
    public final boolean f;
    public final int g;
    public final int h;
    public final int i;
    public final x j;
    public PopupWindow.OnDismissListener m;
    public View n;
    public View o;
    public m.a p;
    public ViewTreeObserver q;
    public boolean r;
    public boolean s;
    public int t;
    public boolean v;
    public final ViewTreeObserver.OnGlobalLayoutListener k = new a();
    public final View.OnAttachStateChangeListener l = new b();
    public int u = 0;

    public class a implements ViewTreeObserver.OnGlobalLayoutListener {
        public a() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (p.this.c()) {
                p pVar = p.this;
                if (pVar.j.z) {
                    return;
                }
                View view = pVar.o;
                if (view == null || !view.isShown()) {
                    p.this.i();
                } else {
                    p.this.j.f();
                }
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
            ViewTreeObserver viewTreeObserver = p.this.q;
            if (viewTreeObserver != null) {
                if (!viewTreeObserver.isAlive()) {
                    p.this.q = view.getViewTreeObserver();
                }
                p pVar = p.this;
                pVar.q.removeGlobalOnLayoutListener(pVar.k);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    }

    public p(Context context, g gVar, View view, int i, int i2, boolean z) {
        this.f242c = context;
        this.d = gVar;
        this.f = z;
        this.e = new f(gVar, LayoutInflater.from(context), z, R.layout.abc_popup_menu_item_layout);
        this.h = i;
        this.i = i2;
        Resources resources = context.getResources();
        this.g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.n = view;
        this.j = new x(context, null, i, i2);
        gVar.b(this, context);
    }

    @Override // a.b.e.e.c.m
    public void a(g gVar, boolean z) {
        if (gVar != this.d) {
            return;
        }
        i();
        m.a aVar = this.p;
        if (aVar != null) {
            aVar.a(gVar, z);
        }
    }

    @Override // a.b.e.e.c.m
    public boolean b(q qVar) {
        boolean z;
        if (qVar.hasVisibleItems()) {
            l lVar = new l(this.f242c, qVar, this.o, this.f, this.h, this.i);
            lVar.d(this.p);
            boolean zW = k.w(qVar);
            lVar.h = zW;
            k kVar = lVar.j;
            if (kVar != null) {
                kVar.q(zW);
            }
            lVar.k = this.m;
            this.m = null;
            this.d.c(false);
            x xVar = this.j;
            int width = xVar.g;
            int i = !xVar.j ? 0 : xVar.h;
            int i2 = this.u;
            View view = this.n;
            int i3 = a.b.d.h.j.f151a;
            if ((Gravity.getAbsoluteGravity(i2, view.getLayoutDirection()) & 7) == 5) {
                width += this.n.getWidth();
            }
            if (lVar.b()) {
                z = true;
            } else if (lVar.f == null) {
                z = false;
            } else {
                lVar.e(width, i, true, true);
                z = true;
            }
            if (z) {
                m.a aVar = this.p;
                if (aVar != null) {
                    aVar.b(qVar);
                }
                return true;
            }
        }
        return false;
    }

    @Override // a.b.e.e.c.o
    public boolean c() {
        return !this.r && this.j.c();
    }

    @Override // a.b.e.e.c.o
    public ListView d() {
        return this.j.d;
    }

    @Override // a.b.e.e.c.o
    public void f() {
        View view;
        boolean z = false;
        if (c()) {
            z = true;
        } else if (!this.r && (view = this.n) != null) {
            this.o = view;
            this.j.A.setOnDismissListener(this);
            x xVar = this.j;
            xVar.r = this;
            xVar.j(true);
            View view2 = this.o;
            boolean z2 = this.q == null;
            ViewTreeObserver viewTreeObserver = view2.getViewTreeObserver();
            this.q = viewTreeObserver;
            if (z2) {
                viewTreeObserver.addOnGlobalLayoutListener(this.k);
            }
            view2.addOnAttachStateChangeListener(this.l);
            x xVar2 = this.j;
            xVar2.q = view2;
            xVar2.m = this.u;
            if (!this.s) {
                this.t = k.o(this.e, null, this.f242c, this.g);
                this.s = true;
            }
            this.j.h(this.t);
            this.j.A.setInputMethodMode(2);
            x xVar3 = this.j;
            xVar3.y = this.f237b;
            xVar3.f();
            a.b.e.f.q qVar = this.j.d;
            qVar.setOnKeyListener(this);
            if (this.v && this.d.l != null) {
                FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f242c).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) qVar, false);
                TextView textView = (TextView) frameLayout.findViewById(android.R.id.title);
                if (textView != null) {
                    textView.setText(this.d.l);
                }
                frameLayout.setEnabled(false);
                qVar.addHeaderView(frameLayout, null, false);
            }
            this.j.g(this.e);
            this.j.f();
            z = true;
        }
        if (!z) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // a.b.e.e.c.m
    public boolean g() {
        return false;
    }

    @Override // a.b.e.e.c.o
    public void i() {
        if (c()) {
            this.j.i();
        }
    }

    @Override // a.b.e.e.c.m
    public void j(boolean z) {
        this.s = false;
        f fVar = this.e;
        if (fVar != null) {
            fVar.notifyDataSetChanged();
        }
    }

    @Override // a.b.e.e.c.m
    public void k(m.a aVar) {
        this.p = aVar;
    }

    @Override // a.b.e.e.c.k
    public void m(g gVar) {
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.r = true;
        this.d.c(true);
        ViewTreeObserver viewTreeObserver = this.q;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.q = this.o.getViewTreeObserver();
            }
            this.q.removeGlobalOnLayoutListener(this.k);
            this.q = null;
        }
        this.o.removeOnAttachStateChangeListener(this.l);
        PopupWindow.OnDismissListener onDismissListener = this.m;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
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
        this.n = view;
    }

    @Override // a.b.e.e.c.k
    public void q(boolean z) {
        this.e.d = z;
    }

    @Override // a.b.e.e.c.k
    public void r(int i) {
        this.u = i;
    }

    @Override // a.b.e.e.c.k
    public void s(int i) {
        this.j.g = i;
    }

    @Override // a.b.e.e.c.k
    public void t(PopupWindow.OnDismissListener onDismissListener) {
        this.m = onDismissListener;
    }

    @Override // a.b.e.e.c.k
    public void u(boolean z) {
        this.v = z;
    }

    @Override // a.b.e.e.c.k
    public void v(int i) {
        x xVar = this.j;
        xVar.h = i;
        xVar.j = true;
    }
}
