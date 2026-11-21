package com.tw.service.a;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class b extends Handler {
    final /* synthetic */ f n;

    b(f fVar) {
        this.n = fVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws Throwable {
        super.handleMessage(message);
        if (message.what != 65282) {
            return;
        }
        int i = message.arg1;
        String string = message.getData().getString("data");
        Log.e("HYH", "UIAuthorizationCheck->enclosing_method:authorized=" + i + ",data=" + string);
        this.n.b(i, string);
        if (this.n.Rk != null) {
            this.n.Rk.a(i == 1);
        }
    }
}
