package a.b.e.e.c;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.tw.carinfoservice.R;

/* JADX INFO: loaded from: classes.dex */
public class l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f238a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final g f239b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f240c;
    public final int d;
    public final int e;
    public View f;
    public boolean h;
    public m.a i;
    public k j;
    public PopupWindow.OnDismissListener k;
    public int g = 8388611;
    public final PopupWindow.OnDismissListener l = new a();

    public class a implements PopupWindow.OnDismissListener {
        public a() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            l.this.c();
        }
    }

    public l(Context context, g gVar, View view, boolean z, int i, int i2) {
        this.f238a = context;
        this.f239b = gVar;
        this.f = view;
        this.f240c = z;
        this.d = i;
        this.e = i2;
    }

    public k a() {
        if (this.j == null) {
            Display defaultDisplay = ((WindowManager) this.f238a.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getRealSize(point);
            k eVar = Math.min(point.x, point.y) >= this.f238a.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width) ? new e(this.f238a, this.f, this.d, this.e, this.f240c) : new p(this.f238a, this.f239b, this.f, this.d, this.e, this.f240c);
            eVar.m(this.f239b);
            eVar.t(this.l);
            eVar.p(this.f);
            eVar.k(this.i);
            eVar.q(this.h);
            eVar.r(this.g);
            this.j = eVar;
        }
        return this.j;
    }

    public boolean b() {
        k kVar = this.j;
        return kVar != null && kVar.c();
    }

    public void c() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void d(m.a aVar) {
        this.i = aVar;
        k kVar = this.j;
        if (kVar != null) {
            kVar.k(aVar);
        }
    }

    public final void e(int i, int i2, boolean z, boolean z2) {
        k kVarA = a();
        kVarA.u(z2);
        if (z) {
            int i3 = this.g;
            View view = this.f;
            int i4 = a.b.d.h.j.f151a;
            if ((Gravity.getAbsoluteGravity(i3, view.getLayoutDirection()) & 7) == 5) {
                i -= this.f.getWidth();
            }
            kVarA.s(i);
            kVarA.v(i2);
            int i5 = (int) ((this.f238a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            kVarA.f237b = new Rect(i - i5, i2 - i5, i + i5, i2 + i5);
        }
        kVarA.f();
    }

    public boolean f() {
        if (b()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        e(0, 0, false, false);
        return true;
    }
}
