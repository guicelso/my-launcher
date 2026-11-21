package com.tw.core.utils;

import android.content.Intent;
import android.os.Process;
import com.tw.core.model.DameonService;

/* JADX INFO: loaded from: classes.dex */
class an implements Runnable {
    final /* synthetic */ T bA;

    an(T t) {
        this.bA = t;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.bA.aU.startService(new Intent(this.bA.aU, (Class<?>) DameonService.class));
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
