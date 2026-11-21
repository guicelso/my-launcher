package android.support.v4.widget;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
class o extends F {
    final /* synthetic */ DrawerLayout n;
    private final int vf;
    private G wf;
    private final Runnable xf = new n(this);

    o(DrawerLayout drawerLayout, int i) {
        this.n = drawerLayout;
        this.vf = i;
    }

    private void db() {
        View viewV = this.n.v(this.vf == 3 ? 5 : 3);
        if (viewV != null) {
            this.n.s(viewV);
        }
    }

    void M() {
        View viewV;
        int width;
        int iP = this.wf.P();
        boolean z = this.vf == 3;
        if (z) {
            viewV = this.n.v(3);
            width = (viewV != null ? -viewV.getWidth() : 0) + iP;
        } else {
            viewV = this.n.v(5);
            width = this.n.getWidth() - iP;
        }
        if (viewV != null) {
            if (((!z || viewV.getLeft() >= width) && (z || viewV.getLeft() <= width)) || this.n.v(viewV) != 0) {
                return;
            }
            DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) viewV.getLayoutParams();
            this.wf.d(viewV, width, viewV.getTop());
            layoutParams.bk = true;
            this.n.invalidate();
            db();
            this.n.W();
        }
    }

    public void N() {
        this.n.removeCallbacks(this.xf);
    }

    @Override // android.support.v4.widget.F
    public int a(View view, int i, int i2) {
        if (this.n.k(view, 3)) {
            return Math.max(-view.getWidth(), Math.min(i, 0));
        }
        int width = this.n.getWidth();
        return Math.max(width - view.getWidth(), Math.min(i, width));
    }

    public void a(G g) {
        this.wf = g;
    }

    @Override // android.support.v4.widget.F
    public void a(View view, float f, float f2) {
        int i;
        float fX = this.n.x(view);
        int width = view.getWidth();
        if (this.n.k(view, 3)) {
            i = (f > 0.0f || (f == 0.0f && fX > 0.5f)) ? 0 : -width;
        } else {
            int width2 = this.n.getWidth();
            if (f < 0.0f || (f == 0.0f && fX > 0.5f)) {
                width2 -= width;
            }
            i = width2;
        }
        this.wf.f(i, view.getTop());
        this.n.invalidate();
    }

    @Override // android.support.v4.widget.F
    public void a(View view, int i, int i2, int i3, int i4) {
        int width = view.getWidth();
        float width2 = (this.n.k(view, 3) ? i + width : this.n.getWidth() - i) / width;
        this.n.e(view, width2);
        view.setVisibility(width2 == 0.0f ? 4 : 0);
        this.n.invalidate();
    }

    @Override // android.support.v4.widget.F
    public int b(View view, int i, int i2) {
        return view.getTop();
    }

    @Override // android.support.v4.widget.F
    public void c(int i, int i2) {
        DrawerLayout drawerLayout;
        int i3;
        if ((i & 1) == 1) {
            drawerLayout = this.n;
            i3 = 3;
        } else {
            drawerLayout = this.n;
            i3 = 5;
        }
        View viewV = drawerLayout.v(i3);
        if (viewV == null || this.n.v(viewV) != 0) {
            return;
        }
        this.wf.g(viewV, i2);
    }

    @Override // android.support.v4.widget.F
    public void d(int i, int i2) {
        this.n.postDelayed(this.xf, 160L);
    }

    @Override // android.support.v4.widget.F
    public void e(View view, int i) {
        ((DrawerLayout.LayoutParams) view.getLayoutParams()).bk = false;
        db();
    }

    @Override // android.support.v4.widget.F
    public boolean f(View view, int i) {
        return this.n.B(view) && this.n.k(view, this.vf) && this.n.v(view) == 0;
    }

    @Override // android.support.v4.widget.F
    public boolean n(int i) {
        return false;
    }

    @Override // android.support.v4.widget.F
    public void o(int i) {
        this.n.a(this.vf, i, this.wf.O());
    }

    @Override // android.support.v4.widget.F
    public int p(View view) {
        if (this.n.B(view)) {
            return view.getWidth();
        }
        return 0;
    }
}
