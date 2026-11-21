package com.tw.service.xt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tw.service.xt.aidl.ITWCommandCallbackAidl;
import com.tw.service.xt.utils.Constants;

/* JADX INFO: loaded from: classes.dex */
class d extends BroadcastReceiver {
    final /* synthetic */ CommandService j;

    d(CommandService commandService) {
        this.j = commandService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        CommandService commandService;
        try {
            if (intent.getAction().equals(Constants.YZS_ACC_OFF)) {
                int iBeginBroadcast = this.j.iTWCommandCallbackAidlLis.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    ((ITWCommandCallbackAidl) this.j.iTWCommandCallbackAidlLis.getBroadcastItem(i)).onSleepStatus(0);
                }
                commandService = this.j;
            } else if (!intent.getAction().equals(Constants.YZS_ACC_ON)) {
                if (intent.getAction().equals(Constants.CARSETTINGS_CHANGE)) {
                    this.j.sendCarSettingsType(intent.getStringExtra("changeType"));
                    return;
                }
                return;
            } else {
                int iBeginBroadcast2 = this.j.iTWCommandCallbackAidlLis.beginBroadcast();
                for (int i2 = 0; i2 < iBeginBroadcast2; i2++) {
                    ((ITWCommandCallbackAidl) this.j.iTWCommandCallbackAidlLis.getBroadcastItem(i2)).onSleepStatus(1);
                }
                commandService = this.j;
            }
            commandService.iTWCommandCallbackAidlLis.finishBroadcast();
        } catch (Exception unused) {
        }
    }
}
