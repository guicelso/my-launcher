package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class fa extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    /* synthetic */ fa(TWService tWService, HandlerC0060x handlerC0060x) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int i = SystemProperties.getInt("persist.tw.bt.module", 2);
        Log.d("TWService", "PackageReceiver~btIcModel:" + i);
        if (i == 0 && "android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            Log.d("TWService", "ACTION_PACKAGE_ADDED:" + schemeSpecificPart);
            if (!TextUtils.isEmpty(schemeSpecificPart) && "cn.cardoor.hicar".equals(schemeSpecificPart) && TextUtils.isEmpty(SystemProperties.get("ro.com.google.gmsversion"))) {
                this.n.pa.write(40730, 3, 0, b.a.a.a.a.a("touch /twdataconfig/", schemeSpecificPart));
                this.n.pa.write(40730, 3, 0, "sync");
                Log.d("TWService", "createNewFile ok：" + schemeSpecificPart);
            }
        }
    }
}
