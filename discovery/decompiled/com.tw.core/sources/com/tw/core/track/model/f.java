package com.tw.core.track.model;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class f extends e {
    private Map cV;
    private String cW;
    private long cX;

    public f() {
    }

    public f(d dVar, CoordType coordType, double d, long j, int i, double d2, double d3, String str, Map map) {
        super(dVar, coordType, d, j, i, d2, d3);
        this.cW = str;
        this.cV = map;
    }

    public void hM(long j) {
        this.cX = j;
    }

    public void hN(Map map) {
        this.cV = map;
    }

    public String hO() {
        return this.cW;
    }

    public long hP() {
        return this.cX;
    }

    public Map hQ() {
        return this.cV;
    }

    @Override // com.tw.core.track.model.e
    public String toString() {
        return "TrackPoint [radius=" + this.radius + ", locTime=" + this.cT + ", direction=" + this.direction + ", speed=" + this.speed + ", height=" + this.height + ", objectKey=" + this.cW + ", columns=" + this.cV + ", sysTime=" + this.cX + "]";
    }
}
