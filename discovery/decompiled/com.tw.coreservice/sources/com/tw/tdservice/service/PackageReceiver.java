package com.tw.tdservice.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import d.b;
import d.c;

/* JADX INFO: loaded from: classes.dex */
public class PackageReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        c.b("PackageReceiver", "onReceive %s", intent.getAction());
        String action = intent.getAction();
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        if ((action.equals("android.intent.action.PACKAGE_ADDED") || action.equals("android.intent.action.PACKAGE_REPLACED")) && "com.tw.core".equals(schemeSpecificPart)) {
            b.a();
        }
    }
}
