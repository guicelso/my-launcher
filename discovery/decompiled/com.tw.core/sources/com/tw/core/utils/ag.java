package com.tw.core.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
final class ag extends BroadcastReceiver {
    final /* synthetic */ String bt;
    final /* synthetic */ InterfaceC0068v bu;

    ag(String str, InterfaceC0068v interfaceC0068v) {
        this.bt = str;
        this.bu = interfaceC0068v;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().startsWith(C0067u.ak) && TextUtils.equals(intent.getStringExtra("android.content.pm.extra.PACKAGE_NAME"), this.bt)) {
            context.unregisterReceiver(this);
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 1);
            C0067u.bT(intent, this.bt, intExtra != 0 ? intExtra : 1, this.bu);
        }
    }
}
