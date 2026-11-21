package com.tw.core.messenger.server;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.tw.core.utils.B;

/* JADX INFO: loaded from: classes.dex */
class b extends Handler {
    private Context b;
    private com.tw.core.messenger.a c;

    public b(Context context, com.tw.core.messenger.a aVar) {
        this.c = aVar;
        this.b = context;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        B.ci("MessengerService", "handleMessage %s", message);
        try {
            this.c.i(this.b, message);
        } catch (Exception e) {
            B.cj("MessengerService", "handleMessage " + e.getMessage(), new Object[0]);
        }
    }
}
