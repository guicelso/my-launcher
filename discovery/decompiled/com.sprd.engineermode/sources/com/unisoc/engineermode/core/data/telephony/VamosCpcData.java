package com.unisoc.engineermode.core.data.telephony;

/* JADX INFO: loaded from: classes4.dex */
public class VamosCpcData {
    private int cpc;
    private int vamos;

    public VamosCpcData() {
    }

    public VamosCpcData(int vamos, int cpc) {
        this.vamos = vamos;
        this.cpc = cpc;
    }

    public int getVamos() {
        return this.vamos;
    }

    public void setVamos(int vamos) {
        this.vamos = vamos;
    }

    public int getCpc() {
        return this.cpc;
    }

    public void setCpc(int cpc) {
        this.cpc = cpc;
    }
}
