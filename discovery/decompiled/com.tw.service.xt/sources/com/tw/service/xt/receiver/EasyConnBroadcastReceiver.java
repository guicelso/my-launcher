package com.tw.service.xt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* JADX INFO: loaded from: classes.dex */
public class EasyConnBroadcastReceiver {
    private boolean isNight;
    private Context mContext;
    private BroadcastReceiver receiver = new a(this);

    public EasyConnBroadcastReceiver(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("net.easyconn.bt.setting");
        intentFilter.addAction("net.easyconn.bluetooth.call");
        intentFilter.addAction("net.easyconn.navi.checkstatus");
        this.mContext.registerReceiver(this.receiver, intentFilter);
    }

    public void sendNaviType(boolean z) {
        this.isNight = z;
        Intent intent = new Intent();
        intent.setAction(z ? "net.easyconn.navi.night" : "net.easyconn.navi.daytime");
        this.mContext.sendBroadcast(intent);
    }

    public void unregisterReceiver() {
        BroadcastReceiver broadcastReceiver = this.receiver;
        if (broadcastReceiver != null) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }
    }
}
