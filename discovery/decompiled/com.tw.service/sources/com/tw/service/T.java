package com.tw.service;

/* JADX INFO: loaded from: classes.dex */
class T implements Runnable {
    final /* synthetic */ U Rc;

    T(U u) {
        this.Rc = u;
    }

    @Override // java.lang.Runnable
    public void run() {
        new ka(this.Rc.n, false).show();
    }
}
