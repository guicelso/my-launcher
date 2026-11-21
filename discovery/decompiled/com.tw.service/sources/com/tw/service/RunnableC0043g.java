package com.tw.service;

import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;

/* JADX INFO: renamed from: com.tw.service.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0043g implements Runnable {
    final /* synthetic */ HandlerC0060x Rc;

    RunnableC0043g(HandlerC0060x handlerC0060x) {
        this.Rc = handlerC0060x;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Rc.n.ta != 0) {
            Intent intent = new Intent();
            intent.setClassName("com.tw.reverse", "com.tw.reverse.ReverseActivity");
            intent.setFlags(268435456);
            this.Rc.n.startActivityAsUser(intent, UserHandle.CURRENT);
            Settings.System.putInt(this.Rc.n.getContentResolver(), "revserse_status", 1);
        }
    }
}
