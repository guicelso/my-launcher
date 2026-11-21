package com.tw.core.model;

import android.location.Location;
import com.tw.core.c.C0040t;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;

/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ b hR;

    i(b bVar) {
        this.hR = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.hR.ht.set(true);
        String strValueOf = "";
        String strValueOf2 = "";
        String strBx = C0064r.bx(this.hR.hr, "ActiviAddress");
        if (!C0062p.aW(strBx) && strBx.contains(",")) {
            String[] strArrSplit = strBx.split(",");
            if (strArrSplit.length == 2) {
                strValueOf = strArrSplit[0];
                strValueOf2 = strArrSplit[1];
            }
        }
        if (C0062p.aW(strValueOf, strValueOf2)) {
            Location locationLe = C0040t.le(this.hR.hr);
            if (locationLe != null) {
                strValueOf = String.valueOf(locationLe.getLatitude());
                strValueOf2 = String.valueOf(locationLe.getLongitude());
            } else {
                strValueOf = "0";
                strValueOf2 = "0";
            }
        }
        this.hR.mu(strValueOf, strValueOf2, C0064r.bE(this.hR.hr, "activateTime", System.currentTimeMillis() / 1000));
    }
}
