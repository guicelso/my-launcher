package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0062z extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    C0062z(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            Log.d("TWService", "收广播：" + intent.getAction());
            if ("com.tw.openapk.action.Activity".equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("open_action");
                Log.d("TWService", "open_action：" + stringExtra);
                Intent intent2 = new Intent();
                intent2.addFlags(268435456);
                if (stringExtra == null || stringExtra.isEmpty()) {
                    String stringExtra2 = intent.getStringExtra("open_package");
                    String stringExtra3 = intent.getStringExtra("open_activity");
                    Log.d("TWService", "open_pkg：" + stringExtra2 + ",open_act:" + stringExtra3);
                    if (stringExtra2 != null && !stringExtra2.isEmpty() && stringExtra3 != null && !stringExtra3.isEmpty()) {
                        intent2.setClassName(stringExtra2, stringExtra3);
                    }
                } else {
                    intent2.setAction(stringExtra);
                }
                this.n.startActivity(intent2);
            }
        } catch (Exception e) {
            Log.e("TWService", Log.getStackTraceString(e));
        }
    }
}
