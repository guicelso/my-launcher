package com.tw.core.model;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;

/* JADX INFO: loaded from: classes.dex */
class e extends Handler {
    final /* synthetic */ a hN;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    e(a aVar, Looper looper) {
        super(looper);
        this.hN = aVar;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        if (message.what == 1) {
            B.ci("Remote360Handler", "停止网络唤醒 当前休眠状态:" + this.hN.hg, new Object[0]);
            DoFunApplication.nt().ae(false);
            if (this.hN.hg) {
                DoFunApplication.nt().af(false);
                return;
            }
            return;
        }
        if (message.what == 2) {
            B.ci("Remote360Handler", "打开5V电源 当前休眠状态:" + this.hN.hg, new Object[0]);
            DoFunApplication.nt().ag(true);
            sendEmptyMessageDelayed(2, 6000L);
        }
    }
}
