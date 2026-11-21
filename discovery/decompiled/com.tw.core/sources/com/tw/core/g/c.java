package com.tw.core.g;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class c extends Handler {
    final /* synthetic */ a iO;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c(a aVar, Looper looper) {
        super(looper);
        this.iO = aVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        if (message.what == 1) {
            this.iO.ny();
        }
    }
}
