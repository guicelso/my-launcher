package com.tw.core.c;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
class aa implements LocationListener {
    final /* synthetic */ C0040t gz;

    aa(C0040t c0040t) {
        this.gz = c0040t;
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        if (this.gz.fd == null || location == null) {
            return;
        }
        double accuracy = location.getAccuracy();
        if (this.gz.eX < 5 || accuracy > 10.0d || accuracy <= 0.0d) {
            C0065s.bK(C0040t.eW, "数据有漂移，GPScount：" + this.gz.eX + "--精度：" + accuracy);
        } else {
            this.gz.fd.iB(location);
        }
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        if (i == 0) {
        }
    }
}
