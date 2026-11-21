package com.tw.service;

import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0056t implements Runnable {
    final /* synthetic */ TWService n;

    RunnableC0056t(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.n.pa.write(40730, 3, 0, "cp -rf /system/etc/theme/ /data/tw/theme/");
            this.n.pa.write(40730, 1, 0, "chmod -R 777 /data/tw/theme");
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("copyThemeToDefaultDir:");
            sbE.append(e.getLocalizedMessage());
            Log.e("TWService", sbE.toString());
        }
    }
}
