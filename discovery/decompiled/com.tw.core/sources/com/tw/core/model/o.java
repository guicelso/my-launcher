package com.tw.core.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tw.core.utils.B;
import com.tw.core.utils.C0062p;

/* JADX INFO: loaded from: classes.dex */
class o extends BroadcastReceiver {
    final /* synthetic */ MainService hX;

    o(MainService mainService) {
        this.hX = mainService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        B.ci("MainService", "接收到广播 %s", action);
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            if (C0062p.bj(context).contains(schemeSpecificPart)) {
                B.ci("MainService", "%s 安装成功", schemeSpecificPart);
                this.hX.mE(1, schemeSpecificPart);
            }
            C0062p.aZ(context, schemeSpecificPart);
        }
    }
}
