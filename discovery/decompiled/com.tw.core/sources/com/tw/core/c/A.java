package com.tw.core.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class A extends BroadcastReceiver {
    final /* synthetic */ C0046z fC;

    public A(C0046z c0046z) {
        this.fC = c0046z;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (this.fC.fy && "com.aispeech.action.CONTACTS_SYNC_SUCCESS".equals(action)) {
            this.fC.lK(intent);
        }
    }
}
