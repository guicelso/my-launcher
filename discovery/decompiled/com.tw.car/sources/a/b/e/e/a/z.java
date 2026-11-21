package a.b.e.e.a;

import a.b.e.f.H;
import a.b.e.f.J;
import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public final class z extends q implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, t, View.OnKeyListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int f247b = a.b.e.b.g.abc_popup_menu_item_layout;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Context f248c;
    public final k d;
    public final j e;
    public final boolean f;
    public final int g;
    public final int h;
    public final int i;
    public final J j;
    public PopupWindow.OnDismissListener m;
    public View n;
    public View o;
    public t.a p;
    public ViewTreeObserver q;
    public boolean r;
    public boolean s;
    public int t;
    public boolean v;
    public final ViewTreeObserver.OnGlobalLayoutListener k = new x(this);
    public final View.OnAttachStateChangeListener l = new y(this);
    public int u = 0;

    public z(Context context, k kVar, View view, int i, int i2, boolean z) {
        this.f248c = context;
        this.d = kVar;
        this.f = z;
        this.e = new j(kVar, LayoutInflater.from(context), this.f, f247b);
        this.h = i;
        this.i = i2;
        Resources resources = context.getResources();
        this.g = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(a.b.e.b.d.abc_config_prefDialogWidth));
        this.n = view;
        this.j = new J(this.f248c, null, this.h, this.i);
        kVar.a(this, context);
    }

    @Override // a.b.e.e.a.w
    public ListView a() {
        return this.j.f;
    }

    @Override // a.b.e.e.a.q
    public void a(int i) {
        this.u = i;
    }

    @Override // a.b.e.e.a.q
    public void a(k kVar) {
    }

    @Override // a.b.e.e.a.t
    public void a(k kVar, boolean z) {
        if (kVar != this.d) {
            return;
        }
        dismiss();
        t.a aVar = this.p;
        if (aVar != null) {
            aVar.a(kVar, z);
        }
    }

    @Override // a.b.e.e.a.t
    public void a(t.a aVar) {
        this.p = aVar;
    }

    @Override // a.b.e.e.a.q
    public void a(View view) {
        this.n = view;
    }

    @Override // a.b.e.e.a.q
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.m = onDismissListener;
    }

    @Override // a.b.e.e.a.q
    public void b(int i) {
        this.j.i = i;
    }

    @Override // a.b.e.e.a.q
    public void b(boolean z) {
        this.v = z;
    }

    @Override // a.b.e.e.a.q
    public void c(int i) {
        J j = this.j;
        j.j = i;
        j.l = true;
    }

    @Override // a.b.e.e.a.w
    public void dismiss() {
        if (isShowing()) {
            this.j.dismiss();
        }
    }

    @Override // a.b.e.e.a.t
    public boolean flagActionItems() {
        return false;
    }

    @Override // a.b.e.e.a.w
    public boolean isShowing() {
        return !this.r && this.j.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.r = true;
        this.d.a(true);
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
        dismiss();
        return true;
    }

    @Override // a.b.e.e.a.w
    public void show() {
        View view;
        boolean z = true;
        if (!isShowing()) {
            if (this.r || (view = this.n) == null) {
                z = false;
            } else {
                this.o = view;
                this.j.H.setOnDismissListener(this);
                J j = this.j;
                j.x = this;
                j.a(true);
                View view2 = this.o;
                boolean z2 = this.q == null;
                this.q = view2.getViewTreeObserver();
                if (z2) {
                    this.q.addOnGlobalLayoutListener(this.k);
                }
                view2.addOnAttachStateChangeListener(this.l);
                J j2 = this.j;
                j2.v = view2;
                j2.o = this.u;
                if (!this.s) {
                    this.t = q.a(this.e, null, this.f248c, this.g);
                    this.s = true;
                }
                this.j.a(this.t);
                this.j.H.setInputMethodMode(2);
                this.j.a(c());
                this.j.show();
                a.b.e.f.B b2 = this.j.f;
                b2.setOnKeyListener(this);
                if (this.v && this.d.o != null) {
                    FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f248c).inflate(a.b.e.b.g.abc_popup_menu_header_item_layout, (ViewGroup) b2, false);
                    TextView textView = (TextView) frameLayout.findViewById(R.id.title);
                    if (textView != null) {
                        textView.setText(this.d.o);
                    }
                    frameLayout.setEnabled(false);
                    b2.addHeaderView(frameLayout, null, false);
                }
                J j3 = this.j;
                j jVar = this.e;
                DataSetObserver dataSetObserver = j3.u;
                if (dataSetObserver == null) {
                    j3.u = new H.b();
                } else {
                    ListAdapter listAdapter = j3.e;
                    if (listAdapter != null) {
                        listAdapter.unregisterDataSetObserver(dataSetObserver);
                    }
                }
                j3.e = jVar;
                if (jVar != null) {
                    jVar.registerDataSetObserver(j3.u);
                }
                a.b.e.f.B b3 = j3.f;
                if (b3 != null) {
                    b3.setAdapter(j3.e);
                }
                this.j.show();
            }
        }
        if (!z) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // a.b.e.e.a.t
    public void updateMenuView(boolean z) {
        this.s = false;
        j jVar = this.e;
        if (jVar != null) {
            jVar.notifyDataSetChanged();
        }
    }

    @Override // a.b.e.e.a.t
    public boolean a(A a2) {
        boolean z;
        if (a2.hasVisibleItems()) {
            s sVar = new s(this.f248c, a2, this.o, this.f, this.h, this.i);
            sVar.a(this.p);
            boolean zB = q.b(a2);
            sVar.h = zB;
            q qVar = sVar.j;
            if (qVar != null) {
                qVar.a(zB);
            }
            sVar.k = this.m;
            this.m = null;
            this.d.a(false);
            J j = this.j;
            int width = j.i;
            int i = !j.l ? 0 : j.j;
            if ((Gravity.getAbsoluteGravity(this.u, a.b.d.h.n.c(this.n)) & 7) == 5) {
                width += this.n.getWidth();
            }
            if (sVar.c()) {
                z = true;
            } else if (sVar.f == null) {
                z = false;
            } else {
                sVar.a(width, i, true, true);
                z = true;
            }
            if (z) {
                t.a aVar = this.p;
                if (aVar != null) {
                    aVar.a(a2);
                }
                return true;
            }
        }
        return false;
    }

    @Override // a.b.e.e.a.q
    public void a(boolean z) {
        this.e.f227c = z;
    }
}
