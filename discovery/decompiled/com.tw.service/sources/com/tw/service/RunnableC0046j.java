package com.tw.service;

/* JADX INFO: renamed from: com.tw.service.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0046j implements Runnable {
    final /* synthetic */ TWService n;

    RunnableC0046j(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.n.Ib) {
            if (this.n.Jb != null) {
                this.n.unbindService(this.n.Jb);
                this.n.Jb = null;
            }
        }
    }
}
