package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class D extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    D(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("topway_ip_config")) {
            String stringExtra = intent.getStringExtra("topway_ip_config_net_ip");
            String stringExtra2 = intent.getStringExtra("topway_ip_config_net_netmask");
            String stringExtra3 = intent.getStringExtra("topway_ip_config_broadcast");
            String stringExtra4 = intent.getStringExtra("topway_ip_config_rule");
            String stringExtra5 = intent.getStringExtra("topway_ip_config_def_gateway");
            Log.i("xiang", "net_ip: " + stringExtra);
            Log.i("xiang", "netmask_ip: " + stringExtra2);
            Log.i("xiang", "broadcast_ip: " + stringExtra3);
            Log.i("xiang", "rule_ip: " + stringExtra4);
            Log.i("xiang", "def_gateway: " + stringExtra5);
            if (!TextUtils.isEmpty(stringExtra)) {
                this.n.lc.edit().putString("net_ip", stringExtra).apply();
            }
            if (!TextUtils.isEmpty(stringExtra2)) {
                this.n.lc.edit().putString("netmask_ip", stringExtra2).apply();
            }
            if (!TextUtils.isEmpty(stringExtra3)) {
                this.n.lc.edit().putString("broadcast_ip", stringExtra3).apply();
            }
            if (!TextUtils.isEmpty(stringExtra4)) {
                this.n.lc.edit().putString("rule_ip", stringExtra4).apply();
            }
            if (!TextUtils.isEmpty(stringExtra5)) {
                this.n.lc.edit().putString("def_gateway", stringExtra5).apply();
            }
            TWService.ga(this.n);
        }
    }
}
