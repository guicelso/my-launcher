package a.b.e.e.a;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public class s implements l {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f242a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final k f243b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final boolean f244c;
    public final int d;
    public final int e;
    public View f;
    public boolean h;
    public t.a i;
    public q j;
    public PopupWindow.OnDismissListener k;
    public int g = 8388611;
    public final PopupWindow.OnDismissListener l = new r(this);

    public s(Context context, k kVar, View view, boolean z, int i, int i2) {
        this.f242a = context;
        this.f243b = kVar;
        this.f = view;
        this.f244c = z;
        this.d = i;
        this.e = i2;
    }

    public void a() {
        if (c()) {
            this.j.dismiss();
        }
    }

    public final void a(int i, int i2, boolean z, boolean z2) {
        q qVarB = b();
        qVarB.b(z2);
        if (z) {
            if ((a.b.b.a.e.a(this.g, a.b.d.h.n.c(this.f)) & 7) == 5) {
                i -= this.f.getWidth();
            }
            qVarB.b(i);
            qVarB.c(i2);
            int i3 = (int) ((this.f242a.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            qVarB.f240a = new Rect(i - i3, i2 - i3, i + i3, i2 + i3);
        }
        qVarB.show();
    }

    public void a(t.a aVar) {
        this.i = aVar;
        q qVar = this.j;
        if (qVar != null) {
            qVar.a(aVar);
        }
    }

    public q b() {
        if (this.j == null) {
            Display defaultDisplay = ((WindowManager) this.f242a.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            int i = Build.VERSION.SDK_INT;
            defaultDisplay.getRealSize(point);
            q iVar = Math.min(point.x, point.y) >= this.f242a.getResources().getDimensionPixelSize(a.b.e.b.d.abc_cascading_menus_min_smallest_width) ? new i(this.f242a, this.f, this.d, this.e, this.f244c) : new z(this.f242a, this.f243b, this.f, this.d, this.e, this.f244c);
            iVar.a(this.f243b);
            iVar.a(this.l);
            iVar.a(this.f);
            iVar.a(this.i);
            iVar.a(this.h);
            iVar.a(this.g);
            this.j = iVar;
        }
        return this.j;
    }

    public boolean c() {
        q qVar = this.j;
        return qVar != null && qVar.isShowing();
    }

    public void d() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public boolean e() {
        if (c()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }
}
