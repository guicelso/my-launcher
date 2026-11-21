package com.tw.core.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
class X implements LocationListener {
    final /* synthetic */ C0054h bc;

    X(C0054h c0054h) {
        this.bc = c0054h;
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        this.bc.M(location);
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
