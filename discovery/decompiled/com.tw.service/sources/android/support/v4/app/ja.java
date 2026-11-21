package android.support.v4.app;

import android.os.Build;
import android.view.View;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ja implements Runnable {
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ Map Od;

    ja(ka kaVar, ArrayList arrayList, Map map) {
        this.Gd = arrayList;
        this.Od = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        int size = this.Gd.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.Gd.get(i);
            String str = (String) this.Od.get(android.support.v4.view.r.h(view));
            int i2 = Build.VERSION.SDK_INT;
            view.setTransitionName(str);
        }
    }
}
