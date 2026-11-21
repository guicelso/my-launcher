package android.support.v4.app;

import android.os.Build;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class ha implements Runnable {
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ ArrayList Rd;
    final /* synthetic */ int Yd;
    final /* synthetic */ ArrayList Zd;
    final /* synthetic */ ArrayList _d;

    ha(ka kaVar, int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4) {
        this.Yd = i;
        this.Gd = arrayList;
        this.Zd = arrayList2;
        this.Rd = arrayList3;
        this._d = arrayList4;
    }

    @Override // java.lang.Runnable
    public void run() {
        for (int i = 0; i < this.Yd; i++) {
            android.support.v4.view.r.a((View) this.Gd.get(i), (String) this.Zd.get(i));
            View view = (View) this.Rd.get(i);
            String str = (String) this._d.get(i);
            int i2 = Build.VERSION.SDK_INT;
            view.setTransitionName(str);
        }
    }
}
