package com.tw.service;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class ea extends Thread {
    final /* synthetic */ TWService n;

    /* synthetic */ ea(TWService tWService, HandlerC0060x handlerC0060x) {
        this.n = tWService;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Log.d("TWService", "+start");
        this.n.Za = 0;
        TWService tWService = this.n;
        tWService.Za = tWService.pa.write(65533, 0, 79, "119.23.146.62");
        Log.d("TWService", "-start");
    }
}
