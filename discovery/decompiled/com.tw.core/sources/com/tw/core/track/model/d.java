package com.tw.core.track.model;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private double latitude;
    private double longitude;

    public d() {
    }

    public d(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    public double hm() {
        return this.latitude;
    }

    public double hn() {
        return this.longitude;
    }

    public String toString() {
        return "LatLng [latitude=" + this.latitude + ", longitude=" + this.longitude + "]";
    }
}
