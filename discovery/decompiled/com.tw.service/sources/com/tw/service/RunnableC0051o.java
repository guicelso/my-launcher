package com.tw.service;

/* JADX INFO: renamed from: com.tw.service.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0051o implements Runnable {
    final /* synthetic */ RunnableC0053q Rc;

    RunnableC0051o(RunnableC0053q runnableC0053q) {
        this.Rc = runnableC0053q;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.Rc.n.g("com.tw.ac", "com.tw.ac.AirService");
        this.Rc.n.g("com.tw.car", "com.tw.car.CarService");
        this.Rc.n.g("com.tw.canuart", "com.tw.canuart.MainService");
    }
}
