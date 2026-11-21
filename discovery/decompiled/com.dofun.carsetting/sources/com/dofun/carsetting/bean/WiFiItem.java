package com.dofun.carsetting.bean;

import android.net.wifi.ScanResult;

/* JADX INFO: loaded from: classes.dex */
public class WiFiItem {
    private static final String TAG = "WiFiItem";
    public String contentText;
    public int itemType;
    public int leftIconRes;
    public int rightIconRes;
    public ScanResult scanResult;
    public int signalLevel;
    public String tipText;

    public WiFiItem() {
    }

    public WiFiItem(int i, int i2, int i3, String str, ScanResult scanResult) {
        this.itemType = i;
        this.leftIconRes = i2;
        this.rightIconRes = i3;
        this.contentText = str;
        this.scanResult = scanResult;
    }

    public int getItemType() {
        return this.itemType;
    }
}
