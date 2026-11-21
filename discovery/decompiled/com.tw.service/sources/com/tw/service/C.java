package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class C extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    C(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        StringBuilder sbE = b.a.a.a.a.e("hiCarReceiver Action:");
        sbE.append(intent.getAction());
        Log.d("TWService", sbE.toString());
        if (!"cn.cardoor.hicar.action.connect.status".equals(intent.getAction())) {
            if (!"cn.cardoor.hicar.action.call.status".equals(intent.getAction())) {
                if ("cn.cardoor.hicar.action.voice.status".equals(intent.getAction())) {
                    this.n.ic = intent.getIntExtra("status", 0);
                    StringBuilder sbE2 = b.a.a.a.a.e("HICAR_VOICE_STATUS:");
                    sbE2.append(this.n.ic);
                    Log.d("TWService", sbE2.toString());
                    TWService tWService = this.n;
                    tWService.o(tWService.ic != 0);
                    return;
                }
                return;
            }
            this.n.hc = intent.getIntExtra("status", 0);
            StringBuilder sbE3 = b.a.a.a.a.e("HICAR_CALL_STATUS:");
            sbE3.append(this.n.hc);
            Log.d("TWService", sbE3.toString());
            if (this.n.hc == 1) {
                this.n.pa.write(65296, 0);
            }
            int unused = this.n.hc;
            TWService tWService2 = this.n;
            TWService.f(tWService2, tWService2.hc == 1);
            this.n.pa.write(770, this.n.hc);
            return;
        }
        String stringExtra = intent.getStringExtra("status");
        if (TextUtils.isEmpty(stringExtra)) {
            return;
        }
        byte b2 = -1;
        int iHashCode = stringExtra.hashCode();
        if (iHashCode != -2087582999) {
            if (iHashCode == 1015497884 && stringExtra.equals("DISCONNECT")) {
                b2 = 1;
            }
        } else if (stringExtra.equals("CONNECTED")) {
            b2 = 0;
        }
        if (b2 == 0) {
            Log.i("TWService", "Hicar Connect");
            TWService.o(this.n, 1);
            this.n.pa.write(65296, 0);
        } else {
            if (b2 != 1) {
                return;
            }
            Log.i("TWService", "Hicar DISCONNECT");
            TWService.o(this.n, 0);
            this.n.pa.write(65296, 1);
            this.n.hc = 0;
            this.n.ic = 0;
            this.n.o(false);
            this.n.pa.write(770, 0);
            this.n.mHandler.removeMessages(65315);
            this.n.mHandler.sendEmptyMessageDelayed(65315, 2000L);
        }
    }
}
