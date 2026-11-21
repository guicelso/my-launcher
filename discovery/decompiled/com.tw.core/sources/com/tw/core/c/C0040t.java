package com.tw.core.c;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.tw.core.utils.C0065s;

/* JADX INFO: renamed from: com.tw.core.c.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0040t {
    public static String eW = "LocationClient";
    private int eX;
    private Context eZ;
    private long fa;
    private LocationManager fc;
    private InterfaceC0041u fd;
    private boolean fe = C0065s.aa;
    private GpsStatus.Listener fb = new Z(this);
    private LocationListener eY = new aa(this);

    public C0040t(Context context) {
        this.eZ = context;
    }

    public static Location le(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null || locationManager.getAllProviders() == null || !locationManager.getAllProviders().contains("gps")) {
            return null;
        }
        return locationManager.getLastKnownLocation("gps");
    }

    private void lg(long j) {
        if (this.fc == null) {
            this.fc = (LocationManager) this.eZ.getSystemService("location");
        }
        lf(this.eY);
        if (this.fc != null && this.fb != null) {
            this.fc.removeGpsStatusListener(this.fb);
        }
        if (this.fc != null && this.fc.getAllProviders().contains("gps")) {
            this.fc.requestLocationUpdates("gps", j, 0.0f, this.eY);
            this.fc.addGpsStatusListener(this.fb);
        }
        this.fa = j;
    }

    public void lc(InterfaceC0041u interfaceC0041u, long j) {
        if (j < 1000) {
            j = 5000;
        }
        this.fd = interfaceC0041u;
        lg(j);
    }

    public void ld(long j) {
        C0065s.bK(eW, "setCollectionInterval mCurInterval = " + this.fa + ",interval=" + j);
        if (j < 1000 || this.fa == j) {
            return;
        }
        lg(j);
    }

    public void lf(LocationListener locationListener) {
        if (this.fc == null || locationListener == null) {
            return;
        }
        this.fc.removeUpdates(locationListener);
    }

    public void release() {
        lf(this.eY);
        this.eY = null;
        this.fd = null;
        this.fc = null;
    }
}
