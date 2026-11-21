package com.tw.service;

/* JADX INFO: loaded from: classes.dex */
class Q implements com.tw.service.a.e {
    final /* synthetic */ U Rc;
    final /* synthetic */ ka zk;

    Q(U u, ka kaVar) {
        this.Rc = u;
        this.zk = kaVar;
    }

    @Override // com.tw.service.a.e
    public void a(boolean z) {
        this.Rc.n.fb = z;
        this.Rc.n.mHandler.post(new P(this, z));
    }
}
