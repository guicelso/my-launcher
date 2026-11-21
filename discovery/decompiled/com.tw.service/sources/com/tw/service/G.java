package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
class G extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    G(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.TIME_TICK") && !intent.getAction().equals("android.intent.action.TIME_SET") && !intent.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
            if (intent.getAction().equals("com.unisound.unicar.update.action")) {
                String stringExtra = intent.getStringExtra("apk");
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                this.n.c(stringExtra);
                return;
            }
            return;
        }
        if (TWService.E(this.n) != 0) {
            StringBuilder sbE = b.a.a.a.a.e("BUGBUG mTimeClick=");
            sbE.append(this.n.Ab);
            Log.e("TWService", sbE.toString());
        }
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(11);
        int i2 = calendar.get(12);
        int i3 = calendar.get(13);
        this.n.pa.write(263, (!DateFormat.is24HourFormat(context) ? 1 : 0) | (i << 8) | (i2 << 16) | (i3 << 24), (calendar.get(5) << 16) | (calendar.get(2) << 8) | (calendar.get(1) - 2000));
    }
}
