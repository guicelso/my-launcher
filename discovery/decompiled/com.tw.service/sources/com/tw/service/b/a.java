package com.tw.service.b;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.res.Configuration;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ String Xk;

    a(c cVar, String str) {
        this.Xk = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            IActivityManager iActivityManager = ActivityManagerNative.getDefault();
            Configuration configuration = iActivityManager.getConfiguration();
            float f = Float.parseFloat(this.Xk);
            if (configuration.fontScale != f) {
                configuration.fontScale = f;
                iActivityManager.updatePersistentConfiguration(configuration);
            }
        } catch (Exception unused) {
        }
    }
}
