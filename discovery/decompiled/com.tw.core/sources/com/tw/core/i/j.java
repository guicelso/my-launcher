package com.tw.core.i;

/* JADX INFO: loaded from: classes.dex */
class j implements Runnable {
    final /* synthetic */ c jL;
    final /* synthetic */ com.tw.core.h.e jM;

    j(c cVar, com.tw.core.h.e eVar) {
        this.jL = cVar;
        this.jM = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.jL.oo(this.jM.jm, this.jM.jl, this.jM.jk);
    }
}
