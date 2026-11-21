package com.tw.service;

/* JADX INFO: loaded from: classes.dex */
class P implements Runnable {
    final /* synthetic */ Q be;
    final /* synthetic */ boolean yk;

    P(Q q, boolean z) {
        this.be = q;
        this.yk = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z = this.yk;
        ka kaVar = this.be.zk;
        if (z) {
            kaVar.hide();
        } else {
            kaVar.show();
        }
    }
}
