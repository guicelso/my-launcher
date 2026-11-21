package com.tw.service;

import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class L implements Runnable {
    final /* synthetic */ M Rc;

    L(M m) {
        this.Rc = m;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.i("TWService", "-SLEEP");
        this.Rc.n.pa.write(514, 5);
        try {
            ((PowerManager) this.Rc.n.getSystemService("power")).wakeUp(SystemClock.uptimeMillis(), "TWService");
        } catch (Exception e) {
            Log.e("TWService", Log.getStackTraceString(e));
        }
        this.Rc.n.mHandler.postDelayed(new K(this), 2000L);
        TWService.Q(this.Rc.n);
    }
}
