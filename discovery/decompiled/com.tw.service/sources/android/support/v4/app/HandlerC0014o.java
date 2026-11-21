package android.support.v4.app;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: renamed from: android.support.v4.app.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class HandlerC0014o extends Handler {
    final /* synthetic */ r n;

    HandlerC0014o(r rVar) {
        this.n = rVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            r rVar = this.n;
            if (rVar.mStopped) {
                rVar.e(false);
                return;
            }
            return;
        }
        if (i != 2) {
            super.handleMessage(message);
        } else {
            this.n.mFragments.dispatchResume();
            this.n.mFragments.execPendingActions();
        }
    }
}
