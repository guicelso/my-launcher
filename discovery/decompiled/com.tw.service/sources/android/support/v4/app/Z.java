package android.support.v4.app;

import android.graphics.Rect;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class Z implements Runnable {
    final /* synthetic */ Object Cd;
    final /* synthetic */ ka Dd;
    final /* synthetic */ View Ed;
    final /* synthetic */ ComponentCallbacksC0013n Fd;
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ ComponentCallbacksC0013n Jd;
    final /* synthetic */ boolean Kd;
    final /* synthetic */ a.b.c.b.b Od;
    final /* synthetic */ Object Pd;
    final /* synthetic */ aa Qd;
    final /* synthetic */ ArrayList Rd;
    final /* synthetic */ Rect Sd;

    Z(ka kaVar, a.b.c.b.b bVar, Object obj, aa aaVar, ArrayList arrayList, View view, ComponentCallbacksC0013n componentCallbacksC0013n, ComponentCallbacksC0013n componentCallbacksC0013n2, boolean z, ArrayList arrayList2, Object obj2, Rect rect) {
        this.Dd = kaVar;
        this.Od = bVar;
        this.Pd = obj;
        this.Qd = aaVar;
        this.Gd = arrayList;
        this.Ed = view;
        this.Fd = componentCallbacksC0013n;
        this.Jd = componentCallbacksC0013n2;
        this.Kd = z;
        this.Rd = arrayList2;
        this.Cd = obj2;
        this.Sd = rect;
    }

    @Override // java.lang.Runnable
    public void run() {
        a.b.c.b.b bVarB = ba.b(this.Dd, this.Od, this.Pd, this.Qd);
        if (bVarB != null) {
            this.Gd.addAll(bVarB.values());
            this.Gd.add(this.Ed);
        }
        ba.b(this.Fd, this.Jd, this.Kd, bVarB, false);
        Object obj = this.Pd;
        if (obj != null) {
            this.Dd.b(obj, this.Rd, this.Gd);
            View viewB = ba.b(bVarB, this.Qd, this.Cd, this.Kd);
            if (viewB != null) {
                this.Dd.a(viewB, this.Sd);
            }
        }
    }
}
