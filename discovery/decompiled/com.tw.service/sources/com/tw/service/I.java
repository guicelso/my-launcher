package com.tw.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
class I extends BroadcastReceiver {
    final /* synthetic */ TWService n;

    I(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra;
        la laVar;
        int i;
        if (intent.getAction().equals("com.unisound.unicar.action.mute")) {
            TWService.f(this.n, true);
            return;
        }
        if (intent.getAction().equals("com.unisound.unicar.action.unmute")) {
            TWService.f(this.n, false);
            return;
        }
        if (intent.getAction().equals("com.unisound.unicar.app.action.close")) {
            if (this.n.xa() && SystemProperties.getInt("persist.tw.forcepip", 0) == 1 && SystemProperties.getInt("sys.tw.forcepip", 0) == 1 && this.n.wa().equals(intent.getStringExtra("packageName"))) {
                return;
            }
            this.n.l(intent.getStringExtra("packageName"));
            return;
        }
        try {
            if (!intent.getAction().equals("com.unisound.unicar.broadcast.action")) {
                if (intent.getAction().equals("com.unisound.intent.action.GET_MUTE")) {
                    TWService tWService = this.n;
                    tWService.sendBroadcastAsUser(new Intent((tWService.va == 0 && this.n.ta == 0) ? "com.unisound.intent.action.DO_UNMUTE" : "com.unisound.intent.action.DO_MUTE").putExtra("call", this.n.va).putExtra("reverse", this.n.ta), UserHandle.CURRENT);
                    return;
                }
                if (intent.getAction().equals("com.unisound.intent.action.GET_POWER")) {
                    TWService tWService2 = this.n;
                    tWService2.sendBroadcastAsUser(new Intent(tWService2.Aa == 2 ? "com.unisound.intent.action.DO_SLEEP" : "com.unisound.intent.action.DO_WAKEUP"), UserHandle.CURRENT);
                    TWService tWService3 = this.n;
                    tWService3.sendBroadcastAsUser(new Intent(tWService3.Aa == 0 ? "com.unisound.intent.action.ACC_ON" : "com.unisound.intent.action.ACC_OFF"), UserHandle.CURRENT);
                    return;
                }
                if (intent.getAction().equals("com.unisound.intent.action.NET_MSG") && (intExtra = intent.getIntExtra("arg1", 0)) > 0 && this.n._a == intExtra) {
                    this.n.mHandler.removeMessages(65530);
                    this.n.mHandler.sendEmptyMessage(65530);
                    this.n._a = 0;
                    return;
                }
                return;
            }
            String stringExtra = intent.getStringExtra("type");
            if (stringExtra.equals("fm")) {
                if (this.n.mSource != 1 && this.n.mSource != 12) {
                    this.n.pa.write(33281, 1, 33);
                }
                this.n.mHandler.postDelayed(new H(this, ((int) (Float.parseFloat(intent.getStringExtra("freq")) * 100.0f)) & 65535), 1000L);
                return;
            }
            if (stringExtra.equals("am")) {
                if (this.n.mSource != 1) {
                    this.n.pa.write(33281, 1, 33);
                }
                this.n.pa.write(1026, 130, Integer.parseInt(intent.getStringExtra("freq")) & 65535);
                return;
            }
            if (stringExtra.equals("volumeup")) {
                this.n.pa.write(513, 1, 1);
                this.n.pa.write(513, 1, 1);
                this.n.pa.write(513, 1, 1);
                return;
            }
            if (stringExtra.equals("volumedown")) {
                this.n.pa.write(513, 1, 2);
                this.n.pa.write(513, 1, 2);
                this.n.pa.write(513, 1, 2);
                return;
            }
            if (stringExtra.equals("next")) {
                laVar = this.n.pa;
                i = 19;
            } else if (stringExtra.equals("pp")) {
                laVar = this.n.pa;
                i = 20;
            } else if (stringExtra.equals("prev")) {
                laVar = this.n.pa;
                i = 21;
            } else if (stringExtra.equals("ffwd")) {
                laVar = this.n.pa;
                i = 22;
            } else if (stringExtra.equals("rew")) {
                laVar = this.n.pa;
                i = 23;
            } else if (stringExtra.equals("home")) {
                laVar = this.n.pa;
                i = 16;
            } else {
                if (!stringExtra.equals("back")) {
                    return;
                }
                laVar = this.n.pa;
                i = 18;
            }
            laVar.write(33281, 1, i);
        } catch (Exception unused) {
        }
    }
}
