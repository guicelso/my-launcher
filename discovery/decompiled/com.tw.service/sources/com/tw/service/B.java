package com.tw.service;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
class B implements Runnable {
    final /* synthetic */ TWService n;
    final /* synthetic */ boolean wk;

    B(TWService tWService, boolean z) {
        this.n = tWService;
        this.wk = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        Intent intent = new Intent();
        intent.setAction(this.wk ? "com.zjinnova.zlink.action.OUT_DARK_START" : "com.zjinnova.zlink.action.OUT_DARK_STOP");
        this.n.sendBroadcast(intent);
    }
}
