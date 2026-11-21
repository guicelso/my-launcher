package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.v, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0058v extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    C0058v(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws Throwable {
        String str;
        if (!"com.zjinnova.zlink".equals(intent.getAction())) {
            if ("com.zlink.intent.action.softApBlockStation".equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("cmd");
                String stringExtra2 = intent.getStringExtra("mac");
                Log.d("TWService", "ZLINK_WIFI_AP:" + stringExtra + " mac: " + stringExtra2);
                if (TextUtils.isEmpty(stringExtra)) {
                    return;
                }
                int iHashCode = stringExtra.hashCode();
                if (iHashCode != 64279661) {
                    if (iHashCode == 1371440500 && stringExtra.equals("Unblock")) {
                    }
                } else if (stringExtra.equals("Block")) {
                }
                if (r0 != 0) {
                    if (r0 != 1) {
                        return;
                    }
                    ((WifiManager) this.n.getSystemService("wifi")).softApUnblockStation(stringExtra2);
                    return;
                }
                ((WifiManager) this.n.getSystemService("wifi")).softApBlockStation(stringExtra2);
            }
            return;
        }
        String stringExtra3 = intent.getStringExtra("status");
        String stringExtra4 = intent.getStringExtra("phoneMode");
        Log.i("TWService", "phoneMode  = " + stringExtra4 + "status = " + stringExtra3);
        if (TextUtils.isEmpty(stringExtra3)) {
            return;
        }
        switch (stringExtra3) {
            case "ALT_AUDIO_START":
                this.n.d(true);
                break;
            case "ALT_AUDIO_STOP":
                this.n.d(false);
                break;
            case "CONNECTED":
                this.n.Pb = true;
                TWService tWService = this.n;
                tWService.mHandler.postDelayed(new A(tWService, tWService.nightMode), 500L);
                TWService tWService2 = this.n;
                tWService2.mHandler.postDelayed(new B(tWService2, tWService2.nightMode), 500L);
                this.n.Sb = stringExtra4;
                break;
            case "DISCONNECT":
                this.n.Pb = false;
                this.n.Qb = false;
                this.n.Rb = false;
                this.n.pa.write(770, 0);
                break;
            case "PHONE_CALL_ON":
                StringBuilder sbE = b.a.a.a.a.e("PHONE_CALL_ON isGTBT = ");
                sbE.append(this.n.db);
                Log.i("TWService", sbE.toString());
                this.n.Qb = true;
                if (this.n.db) {
                    TWService.f(this.n, true);
                    break;
                } else {
                    if (SystemProperties.getInt("persist.tw.bt.module", 2) == 0 && ("auto_wired".equals(this.n.Sb) || "auto_wireless".equals(this.n.Sb))) {
                        str = "bt auto";
                        Log.i("TWService", str);
                    } else {
                        this.n.pa.write(770, 1);
                    }
                    break;
                }
                break;
            case "PHONE_CALL_OFF":
                Log.i("TWService", "PHONE_CALL_OFF");
                this.n.Qb = false;
                if (this.n.db) {
                    TWService.f(this.n, false);
                    break;
                } else {
                    if (SystemProperties.getInt("persist.tw.bt.module", 2) == 0 && ("auto_wired".equals(this.n.Sb) || "auto_wireless".equals(this.n.Sb))) {
                        str = "bt auto off";
                        Log.i("TWService", str);
                        break;
                    }
                    this.n.pa.write(770, 0);
                    break;
                }
                break;
            case "MAIN_PAGE_SHOW":
                TWService tWService3 = this.n;
                tWService3.mHandler.postDelayed(new A(tWService3, tWService3.nightMode), 500L);
                TWService tWService4 = this.n;
                tWService4.mHandler.postDelayed(new B(tWService4, tWService4.nightMode), 500L);
                break;
            case "SIRI_ON":
                this.n.Rb = true;
                this.n.p(true);
                break;
            case "SIRI_OFF":
                this.n.Rb = true;
                this.n.p(false);
                break;
        }
    }
}
