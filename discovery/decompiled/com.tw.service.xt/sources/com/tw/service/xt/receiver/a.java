package com.tw.service.xt.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
class a extends BroadcastReceiver {
    final /* synthetic */ EasyConnBroadcastReceiver j;

    a(EasyConnBroadcastReceiver easyConnBroadcastReceiver) {
        this.j = easyConnBroadcastReceiver;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("net.easyconn.bt.setting".equals(action)) {
            try {
                Intent intent2 = new Intent();
                intent2.setComponent(new ComponentName("com.tw.bt", "com.tw.bt.ATBluetoothActivity"));
                intent2.putExtra("open_bt", "open_bt_setting");
                intent2.setFlags(268435456);
                this.j.mContext.startActivity(intent2);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if ("net.easyconn.navi.checkstatus".equals(action)) {
            EasyConnBroadcastReceiver easyConnBroadcastReceiver = this.j;
            easyConnBroadcastReceiver.sendNaviType(easyConnBroadcastReceiver.isNight);
        } else if ("net.easyconn.bluetooth.call".equals(action)) {
            String stringExtra = intent.getStringExtra("phoneNum");
            Intent intent3 = new Intent("action.intent.AIOS_DIAL");
            intent3.putExtra("number", stringExtra);
            this.j.mContext.sendBroadcast(intent3);
        }
    }
}
