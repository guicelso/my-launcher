package com.tw.core.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class L extends Handler {
    final /* synthetic */ C0025e ga;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    L(C0025e c0025e, Looper looper) {
        super(looper);
        this.ga = c0025e;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        super.dispatchMessage(message);
        com.tw.core.utils.B.ci("AliTrackDataManager", "start handler", new Object[0]);
        if (this.ga.dV != null) {
            this.ga.dV.iv();
        }
        sendEmptyMessageDelayed(999, 120000L);
    }
}
