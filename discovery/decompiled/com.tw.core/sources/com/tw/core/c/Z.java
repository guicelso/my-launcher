package com.tw.core.c;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class Z implements GpsStatus.Listener {
    final /* synthetic */ C0040t gy;

    Z(C0040t c0040t) {
        this.gy = c0040t;
    }

    @Override // android.location.GpsStatus.Listener
    public void onGpsStatusChanged(int i) {
        try {
            if (this.gy.fc == null || i != 4) {
                return;
            }
            this.gy.eX = 0;
            GpsStatus gpsStatus = this.gy.fc.getGpsStatus(null);
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();
            while (it.hasNext() && this.gy.eX <= maxSatellites) {
                if (it.next().usedInFix()) {
                    this.gy.eX++;
                }
            }
            com.tw.core.utils.B.ci(C0040t.eW, this + " 卫星数量：%s", Integer.valueOf(this.gy.eX));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
