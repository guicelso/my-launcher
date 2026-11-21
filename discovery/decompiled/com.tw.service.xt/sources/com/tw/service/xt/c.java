package com.tw.service.xt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tw.service.xt.utils.DeviceUtils;

/* JADX INFO: loaded from: classes.dex */
class c extends BroadcastReceiver {
    final /* synthetic */ CommandService j;

    c(CommandService commandService) {
        this.j = commandService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (DeviceUtils.isLightFromMCU()) {
            return;
        }
        this.j.sendLightValue();
    }
}
