package com.tw.service;

import android.telephony.TelephonyManager;

/* JADX INFO: loaded from: classes.dex */
class J implements Runnable {
    final /* synthetic */ M Rc;

    J(M m) {
        this.Rc = m;
    }

    @Override // java.lang.Runnable
    public void run() {
        ((TelephonyManager) this.Rc.n.getSystemService("phone")).setDataEnabled(true);
    }
}
