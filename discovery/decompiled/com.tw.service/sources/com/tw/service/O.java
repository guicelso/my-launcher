package com.tw.service;

import android.content.Intent;
import android.os.SystemProperties;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class O implements Runnable {
    final /* synthetic */ TWService n;

    O(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d("TWService", "BOOT_COMPLETED");
        if (SystemProperties.getInt("sys.boot_completed", 0) != 1) {
            this.n.mHandler.postDelayed(this, 3000L);
            return;
        }
        this.n.mHandler.postDelayed(new N(this), 4000L);
        Intent intent = new Intent("com.zjinnova.zlink");
        intent.putExtra("command", "ACTION_ENTER");
        this.n.sendBroadcast(intent);
        Log.d("TWService", "Z-LINK ACTION_ENTER+");
    }
}
