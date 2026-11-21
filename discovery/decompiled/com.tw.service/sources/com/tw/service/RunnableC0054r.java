package com.tw.service;

import android.content.ComponentName;
import android.content.Intent;

/* JADX INFO: renamed from: com.tw.service.r, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0054r implements Runnable {
    final /* synthetic */ TWService n;

    RunnableC0054r(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.dofun.carsetting", "com.dofun.carsetting.config.ConfigService"));
        intent.putExtra("carsetting.fragment", "factoryFragment");
        intent.setFlags(268451840);
        intent.putExtra("cmd", 4);
        intent.putExtra("path", "/sdcard/.Tcfg");
        intent.putExtra("isRestoreFactoryImport", true);
        this.n.startService(intent);
    }
}
