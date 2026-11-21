package com.tw.service;

import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.u, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0057u implements Runnable {
    final /* synthetic */ TWService n;

    RunnableC0057u(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.n.pa.write(40730, 3, 0, "cp -rf system/etc/theme/ /sdcard/theme/");
        } catch (Exception e) {
            StringBuilder sbE = b.a.a.a.a.e("copyThemeToDefaultDir:");
            sbE.append(e.getLocalizedMessage());
            Log.e("TWService", sbE.toString());
        }
    }
}
