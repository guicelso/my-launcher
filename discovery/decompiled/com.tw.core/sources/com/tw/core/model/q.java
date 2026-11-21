package com.tw.core.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class q extends Handler {
    final /* synthetic */ c hZ;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    q(c cVar, Looper looper) {
        super(looper);
        this.hZ = cVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        if (message.what == 9999) {
            this.hZ.mQ();
        }
        this.hZ.hH.sendEmptyMessageDelayed(9999, 120000L);
    }
}
