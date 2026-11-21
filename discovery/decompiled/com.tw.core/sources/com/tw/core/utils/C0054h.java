package com.tw.core.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.C0000a;
import com.tw.core.app.DoFunApplication;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tw.core.utils.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0054h {
    private static volatile C0054h H;
    private Context D;
    private double C = 6378.137d;
    private LocationListener E = new X(this);
    private List F = new ArrayList();
    private int G = C0064r.bD(DoFunApplication.nu(), "numberOfDistance", 0);

    private C0054h(Context context) {
        this.D = context;
        B.ci("CalculateDeviceActiveUtil", "mSumDistance %s", Integer.valueOf(this.G));
    }

    private void N(List list) {
        try {
            Location location = (Location) list.get(list.size() - 1);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Location location2 = (Location) list.get(0);
            double latitude2 = location2.getLatitude();
            double longitude2 = location2.getLongitude();
            Location location3 = (Location) list.get((list.size() / 2) + 1);
            double latitude3 = location3.getLatitude();
            double longitude3 = location3.getLongitude();
            double dO = O(latitude2, longitude2, latitude3, longitude3);
            double dO2 = O(latitude3, longitude3, latitude, longitude);
            C0065s.bK("CalculateDeviceActiveUtil", "跑了" + (dO + dO2) + "米的位移");
            if ((dO + dO2) / 1000.0d < 1.0d || this.G > 10) {
                return;
            }
            this.G++;
            C0065s.bK("CalculateDeviceActiveUtil", "第" + this.G + "次1公里位移");
            C0064r.bG(this.D, "numberOfDistance", this.G);
            if (this.G >= 10) {
                C0065s.bK("CalculateDeviceActiveUtil", "达到10次1公里位移=" + this.G);
                C0064r.bB(this.D, "ActiviAddress", new BigDecimal(String.valueOf(latitude)).setScale(6, 4) + "," + new BigDecimal(String.valueOf(longitude)).setScale(6, 4));
                C0064r.bH(this.D, "activateTime", location.getTime() / 1000);
                com.tw.core.model.b.ms(this.D).mt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double O(double d, double d2, double d3, double d4) {
        double dR = R(d);
        double dR2 = R(d3);
        return Math.round(((Math.asin(Math.sqrt(((Math.cos(dR) * Math.cos(dR2)) * Math.pow(Math.sin((R(d2) - R(d4)) / 2.0d), 2.0d)) + Math.pow(Math.sin((dR - dR2) / 2.0d), 2.0d))) * 2.0d) * this.C) * 1000000.0d) / 1000;
    }

    public static C0054h P() {
        if (H == null) {
            synchronized (C0054h.class) {
                if (H == null) {
                    H = new C0054h(DoFunApplication.nu());
                }
            }
        }
        return H;
    }

    private double R(double d) {
        return (3.141592653589793d * d) / 180.0d;
    }

    private void U() {
        LocationManager locationManager = (LocationManager) this.D.getSystemService("location");
        if (locationManager != null && locationManager.getAllProviders().contains("gps") && C0000a.qs(DoFunApplication.nu(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            locationManager.requestLocationUpdates("gps", 10000L, 0.0f, this.E);
        }
    }

    private void V() {
        LocationManager locationManager = (LocationManager) this.D.getSystemService("location");
        if (locationManager != null) {
            locationManager.removeUpdates(this.E);
        }
    }

    public void M(Location location) {
        C0065s.bK("CalculateDeviceActiveUtil", "已经跑了第" + this.G + "次1公里位移");
        if (this.G >= 10) {
            V();
            return;
        }
        this.F.add(location);
        if (Math.abs(((Location) this.F.get(this.F.size() - 1)).getTime() - ((Location) this.F.get(0)).getTime()) < 300000 || this.F.size() < 3) {
            return;
        }
        N(this.F);
        this.F.clear();
    }

    public boolean Q() {
        return this.G >= 10;
    }

    public void S(Context context) {
        if (com.tw.core.b.e.gh() && Q()) {
            Intent intent = new Intent("dofun.intent.SHOW_FLOW");
            intent.putExtra("ActivateTime", C0064r.bF(context, "activateTime"));
            context.sendBroadcast(intent);
            B.ci("CalculateDeviceActiveUtil", "sendDeviceActiveBroadcast", new Object[0]);
            com.tw.core.model.b.ms(context).mt();
        }
    }

    public void T(Context context) {
        if (com.tw.core.b.e.gh()) {
            return;
        }
        long jBE = C0064r.bE(context, "hwActivateTime", -1L);
        if (jBE > 0) {
            Intent intent = new Intent("dofun.intent.EMQ_CONNECT_TEN");
            intent.putExtra("ActivateTime", jBE);
            context.sendBroadcast(intent);
            B.ci("CalculateDeviceActiveUtil", "sendHwDeviceActiveBroadcast", new Object[0]);
        }
    }

    public void W() {
        if (Q()) {
            return;
        }
        U();
    }
}
