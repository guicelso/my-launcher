package com.sprd.engineermode.connectivity.BT;

/* JADX INFO: loaded from: classes4.dex */
public class BTRXResult {
    public String ber;
    public String per;
    public String rssi;

    public BTRXResult(String rssi, String per, String ber, boolean isOdd) {
        this.rssi = rssi;
        this.per = per;
        this.ber = ber;
    }
}
