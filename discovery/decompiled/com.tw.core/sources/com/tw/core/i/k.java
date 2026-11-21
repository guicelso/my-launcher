package com.tw.core.i;

import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ c jN;
    final /* synthetic */ String jO;
    final /* synthetic */ String jP;

    k(c cVar, String str, String str2) {
        this.jN = cVar;
        this.jO = str;
        this.jP = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tw.core.a.e eVarOs = this.jN.os(this.jO);
        C0065s.bK("AppUpgradeMessageReceiv", "onNotificationClick version = " + eVarOs);
        if (eVarOs != null) {
            this.jN.ol(this.jP, eVarOs, false);
        }
    }
}
