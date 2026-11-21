package com.tw.service.c;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
class a extends Handler {
    final /* synthetic */ b n;

    a(b bVar) {
        this.n = bVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message.what == 1) {
            this.n.hide();
        }
    }
}
