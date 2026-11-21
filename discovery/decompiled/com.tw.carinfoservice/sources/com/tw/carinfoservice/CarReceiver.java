package com.tw.carinfoservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public class CarReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || CarService.o) {
            return;
        }
        Intent intent2 = new Intent(context, (Class<?>) CarService.class);
        if (Build.VERSION.SDK_INT < 26) {
            context.startService(intent2);
        } else if (Settings.System.getInt(context.getApplicationContext().getContentResolver(), "BIND_SERVICE_CONFIG", 0) != 0) {
            context.startForegroundService(intent2);
        }
    }
}
