package com.tw.core.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class N extends Handler {
    final /* synthetic */ C0026f gc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    N(C0026f c0026f, Looper looper) {
        super(looper);
        this.gc = c0026f;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        super.dispatchMessage(message);
        com.tw.core.utils.B.ci("BaiDuMapDataManager", "start handler", new Object[0]);
        if (this.gc.ef != null) {
            this.gc.ef.iL();
        }
        sendEmptyMessageDelayed(999, 120000L);
    }
}
