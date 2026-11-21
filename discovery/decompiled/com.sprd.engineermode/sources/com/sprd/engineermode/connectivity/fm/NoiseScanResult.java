package com.sprd.engineermode.connectivity.fm;

/* JADX INFO: loaded from: classes4.dex */
public class NoiseScanResult {
    public String freq;
    public String rssi;
    public String snr;

    public NoiseScanResult(String freq, String rssi, String snr) {
        this.freq = freq;
        this.rssi = rssi;
        this.snr = snr;
    }
}
