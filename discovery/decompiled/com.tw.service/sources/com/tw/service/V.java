package com.tw.service;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class V implements Runnable {
    final /* synthetic */ TWService n;

    V(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        for (int i = 0; i < this.n.Gb.length; i++) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(this.n.Fb[i], this.n.Gb[i]));
                this.n.startService(intent);
            } catch (Exception e) {
                StringBuilder sbE = b.a.a.a.a.e("TW-StartService:");
                sbE.append(e.toString());
                Log.d("TWService", sbE.toString());
            }
        }
    }
}
