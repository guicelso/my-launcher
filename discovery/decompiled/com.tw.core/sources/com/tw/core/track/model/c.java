package com.tw.core.track.model;

/* JADX INFO: loaded from: classes.dex */
public abstract class c {
    private String cI;
    private String cJ;
    private long cK;
    private int cL;

    public c(long j, int i, String str) {
        this.cK = j;
        this.cL = i;
        this.cJ = str;
    }

    public long hh() {
        return this.cK;
    }

    public int hi() {
        return this.cL;
    }

    public void hj(String str) {
        this.cI = str;
    }

    public void hk(String str) {
        this.cJ = str;
    }

    public void hl(int i) {
        this.cL = i;
    }

    public String toString() {
        return "BaseResponse [responseID=" + this.cK + ", status=" + this.cL + ", message=" + this.cJ + "]";
    }
}
