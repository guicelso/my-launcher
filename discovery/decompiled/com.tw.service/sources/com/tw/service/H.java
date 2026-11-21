package com.tw.service;

/* JADX INFO: loaded from: classes.dex */
class H implements Runnable {
    final /* synthetic */ I Rc;
    final /* synthetic */ int xk;

    H(I i, int i2) {
        this.Rc = i;
        this.xk = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Rc.n.pa.write(1026, 128, this.xk);
        la laVar = this.Rc.n.pa;
        int i = this.xk;
        laVar.write(40458, ((i >> 8) & 255) | 128, i & 255);
    }
}
