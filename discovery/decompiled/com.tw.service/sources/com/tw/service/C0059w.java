package com.tw.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/* JADX INFO: renamed from: com.tw.service.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0059w implements LocationListener {
    final /* synthetic */ TWService n;

    C0059w(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        if (this.n.Zb) {
            this.n.Zb = false;
            TWService.Na(this.n);
            return;
        }
        float speed = location.getSpeed();
        if (this.n.mSpeed != speed) {
            this.n.mSpeed = speed;
            this.n.pa.write(520, (int) this.n.mSpeed);
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
    }
}
