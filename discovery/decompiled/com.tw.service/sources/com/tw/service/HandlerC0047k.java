package com.tw.service;

import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: renamed from: com.tw.service.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class HandlerC0047k extends Handler {
    final /* synthetic */ ServiceConnection Qc;
    final /* synthetic */ ServiceConnectionC0048l Rc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    HandlerC0047k(ServiceConnectionC0048l serviceConnectionC0048l, Looper looper, ServiceConnection serviceConnection) {
        super(looper);
        this.Rc = serviceConnectionC0048l;
        this.Qc = serviceConnection;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        synchronized (this.Rc.n.Ib) {
            if (this.Rc.n.Jb == this.Qc) {
                this.Rc.n.unbindService(this.Rc.n.Jb);
                this.Rc.n.Jb = null;
                this.Rc.n.mHandler.removeCallbacks(this.Rc.n.Kb);
            }
        }
    }
}
