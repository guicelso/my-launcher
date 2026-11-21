package com.tw.core.messenger.a;

import android.os.Handler;
import android.os.Message;
import com.tw.core.utils.B;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class e extends Handler {
    private final WeakReference l;

    public e(d dVar) {
        this.l = new WeakReference(dVar);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        B.ci("Connection", "handleMessage %s", message);
        d dVar = (d) this.l.get();
        if (dVar == null || dVar.g == null) {
            return;
        }
        dVar.g.g(message);
    }
}
