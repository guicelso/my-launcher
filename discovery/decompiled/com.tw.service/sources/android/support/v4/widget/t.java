package android.support.v4.widget;

import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
class t extends F {
    final /* synthetic */ SlidingPaneLayout n;

    t(SlidingPaneLayout slidingPaneLayout) {
        this.n = slidingPaneLayout;
    }

    @Override // android.support.v4.widget.F
    public int a(View view, int i, int i2) {
        SlidingPaneLayout.LayoutParams layoutParams = (SlidingPaneLayout.LayoutParams) this.n.hi.getLayoutParams();
        if (!this.n.ba()) {
            int paddingLeft = this.n.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
            return Math.min(Math.max(i, paddingLeft), this.n.ki + paddingLeft);
        }
        int width = this.n.getWidth() - (this.n.hi.getWidth() + (this.n.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin));
        return Math.max(Math.min(i, width), width - this.n.ki);
    }

    @Override // android.support.v4.widget.F
    public void a(View view, float f, float f2) {
        int paddingLeft;
        SlidingPaneLayout.LayoutParams layoutParams = (SlidingPaneLayout.LayoutParams) view.getLayoutParams();
        if (this.n.ba()) {
            int paddingRight = this.n.getPaddingRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
            if (f < 0.0f || (f == 0.0f && this.n.ii > 0.5f)) {
                paddingRight += this.n.ki;
            }
            paddingLeft = (this.n.getWidth() - paddingRight) - this.n.hi.getWidth();
        } else {
            paddingLeft = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + this.n.getPaddingLeft();
            if (f > 0.0f || (f == 0.0f && this.n.ii > 0.5f)) {
                paddingLeft += this.n.ki;
            }
        }
        this.n.mi.f(paddingLeft, view.getTop());
        this.n.invalidate();
    }

    @Override // android.support.v4.widget.F
    public void a(View view, int i, int i2, int i3, int i4) {
        this.n.y(i);
        this.n.invalidate();
    }

    @Override // android.support.v4.widget.F
    public int b(View view, int i, int i2) {
        return view.getTop();
    }

    @Override // android.support.v4.widget.F
    public void c(int i, int i2) {
        SlidingPaneLayout slidingPaneLayout = this.n;
        slidingPaneLayout.mi.g(slidingPaneLayout.hi, i2);
    }

    @Override // android.support.v4.widget.F
    public void e(View view, int i) {
        this.n.ea();
    }

    @Override // android.support.v4.widget.F
    public boolean f(View view, int i) {
        if (this.n.ch) {
            return false;
        }
        return ((SlidingPaneLayout.LayoutParams) view.getLayoutParams()).dk;
    }

    @Override // android.support.v4.widget.F
    public void o(int i) {
        SlidingPaneLayout slidingPaneLayout;
        boolean z;
        if (this.n.mi.Q() == 0) {
            SlidingPaneLayout slidingPaneLayout2 = this.n;
            if (slidingPaneLayout2.ii == 0.0f) {
                slidingPaneLayout2.J(slidingPaneLayout2.hi);
                SlidingPaneLayout slidingPaneLayout3 = this.n;
                slidingPaneLayout3.E(slidingPaneLayout3.hi);
                slidingPaneLayout = this.n;
                z = false;
            } else {
                slidingPaneLayout2.F(slidingPaneLayout2.hi);
                slidingPaneLayout = this.n;
                z = true;
            }
            slidingPaneLayout.ni = z;
        }
    }

    @Override // android.support.v4.widget.F
    public int p(View view) {
        return this.n.ki;
    }
}
