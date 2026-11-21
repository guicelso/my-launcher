package com.tw.core.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class ae extends Handler {
    final /* synthetic */ C0044x gE;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ae(C0044x c0044x, Looper looper) {
        super(looper);
        this.gE = c0044x;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        super.dispatchMessage(message);
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "start handler", new Object[0]);
        if (this.gE.fl != null) {
            this.gE.fl.iL();
        }
        sendEmptyMessageDelayed(999, 120000L);
    }
}
