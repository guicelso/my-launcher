package com.tw.core.c;

import android.os.Message;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
class U implements com.tw.core.h.c {
    final /* synthetic */ C0033m go;
    final /* synthetic */ Message gp;

    U(C0033m c0033m, Message message) {
        this.go = c0033m;
        this.gp = message;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: lW, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        com.tw.core.utils.B.ci("AppMessageManager", "report mReportContent result %s ", bool);
        this.gp.arg1 = bool.booleanValue() ? 1 : -1;
        com.tw.core.utils.B.ci("AppMessageManager", "onMessage %s", this.gp);
        if (this.gp.replyTo != null) {
            try {
                this.gp.replyTo.send(this.gp);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
