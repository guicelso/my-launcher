package com.tw.core.track.model;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private CoordType cS;
    protected long cT;
    private d cU;
    protected int direction;
    protected double height;
    protected double radius;
    protected double speed;

    public e() {
    }

    public e(d dVar, CoordType coordType, double d, long j, int i, double d2, double d3) {
        this.cU = dVar;
        this.cS = coordType;
        this.radius = d;
        this.cT = j;
        this.direction = i;
        this.speed = d2;
        this.height = d3;
    }

    public long hF() {
        return this.cT;
    }

    public d hG() {
        return this.cU;
    }

    public CoordType hH() {
        return this.cS;
    }

    public double hI() {
        return this.speed;
    }

    public int hJ() {
        return this.direction;
    }

    public double hK() {
        return this.height;
    }

    public double hL() {
        return this.radius;
    }

    public String toString() {
        return "Point [location=" + this.cU + ", coordType=" + this.cS + ", radius=" + this.radius + ", locTime=" + this.cT + ", direction=" + this.direction + ", speed=" + this.speed + ", height=" + this.height + "]";
    }
}
