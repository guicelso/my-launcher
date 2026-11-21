package com.tw.service;

import android.content.ComponentName;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
class A implements Runnable {
    final /* synthetic */ TWService n;
    final /* synthetic */ boolean wk;

    A(TWService tWService, boolean z) {
        this.n = tWService;
        this.wk = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        Intent intent = new Intent("com.zjinnova.zlink");
        intent.setComponent(new ComponentName("com.zjinnova.zlink", "com.texustek.speedplay.broadcast.VendorBroadcastReceiver"));
        intent.putExtra("command", this.wk ? "REQ_NIGHT_MODE_CMD" : "REQ_DAY_MODE_CMD");
        this.n.sendBroadcast(intent);
    }
}
