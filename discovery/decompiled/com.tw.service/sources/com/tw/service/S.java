package com.tw.service;

/* JADX INFO: loaded from: classes.dex */
class S implements Runnable {
    final /* synthetic */ U Rc;
    final /* synthetic */ ka zk;

    S(U u, ka kaVar) {
        this.Rc = u;
        this.zk = kaVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Rc.n.fb) {
            return;
        }
        this.zk.show();
    }
}
