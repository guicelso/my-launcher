package com.tw.core.track.model;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {
    private String cD;
    private long cE;
    private long cF;

    public a() {
    }

    public a(long j, String str, long j2) {
        this.cE = j;
        this.cD = str;
        this.cF = j2;
    }

    public String gC() {
        return this.cD;
    }

    public long gD() {
        return this.cE;
    }

    public long gE() {
        return this.cF;
    }

    public String toString() {
        return "BaseRequest [requestID=" + this.cE + ", ak=" + this.cD + ", serviceId=" + this.cF + "]";
    }
}
