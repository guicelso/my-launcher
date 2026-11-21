package com.tw.core.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class X extends Handler {
    final /* synthetic */ C0036p gu;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    X(C0036p c0036p, Looper looper) {
        super(looper);
        this.gu = c0036p;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        List listIi;
        super.dispatchMessage(message);
        com.tw.core.utils.B.ci("GpsDataManager", "exe timer isConn = %s", Boolean.valueOf(com.tw.core.h.b.nN().isConnected()));
        if (this.gu.kM() && (listIi = com.tw.core.track.b.a.ii(this.gu.kp(1000), 100)) != null) {
            Iterator it = listIi.iterator();
            while (it.hasNext()) {
                this.gu.kJ((List) it.next());
            }
        }
        sendEmptyMessageDelayed(9999, 10000L);
    }
}
