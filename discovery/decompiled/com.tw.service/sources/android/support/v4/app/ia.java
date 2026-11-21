package android.support.v4.app;

import android.os.Build;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ia implements Runnable {
    final /* synthetic */ ArrayList Gd;
    final /* synthetic */ Map Od;

    ia(ka kaVar, ArrayList arrayList, Map map) {
        this.Gd = arrayList;
        this.Od = map;
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        int size = this.Gd.size();
        for (int i = 0; i < size; i++) {
            View view = (View) this.Gd.get(i);
            String strH = android.support.v4.view.r.h(view);
            if (strH != null) {
                Iterator it = this.Od.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        str = null;
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (strH.equals(entry.getValue())) {
                        str = (String) entry.getKey();
                        break;
                    }
                }
                int i2 = Build.VERSION.SDK_INT;
                view.setTransitionName(str);
            }
        }
    }
}
