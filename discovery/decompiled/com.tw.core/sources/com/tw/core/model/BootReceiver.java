package com.tw.core.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tw.core.utils.C;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {
    private static final String ho = BootReceiver.class.getSimpleName();

    private void mo(Context context, String str) {
        Intent intent = new Intent(str + "_DOFUN");
        intent.setFlags(32);
        context.sendBroadcast(intent);
    }

    private void mp(Context context) {
        C.co(context);
        C.cp(context);
    }

    private void mq(Context context) {
        C.cq(context);
    }

    private void mr(Context context) {
        C.co(context);
        C.cp(context);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        C0065s.bK(ho, "onReceive:" + action);
        if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
            mp(context);
            return;
        }
        if ("com.tw.core.DESTROY_MAINSERVICE".equals(action)) {
            context.startService(new Intent(context, (Class<?>) MainService.class));
            return;
        }
        if ("com.tw.core.DESTROY_DAMEONSERVICE".equals(action)) {
            context.startService(new Intent(context, (Class<?>) DameonService.class));
            return;
        }
        if ("com.unisound.intent.action.DO_SHUTDOWN".equals(action)) {
            mo(context, "com.unisound.intent.action.DO_SHUTDOWN");
            mq(context);
            return;
        }
        if ("com.unisound.intent.action.ACC_OFF".equals(action)) {
            mo(context, "com.unisound.intent.action.ACC_OFF");
            return;
        }
        if ("com.unisound.intent.action.ACC_ON".equals(action)) {
            mo(context, "com.unisound.intent.action.ACC_ON");
            context.startService(new Intent(context, (Class<?>) MainService.class));
        } else if ("com.unisound.intent.action.DO_SLEEP".equals(action)) {
            mo(context, "com.unisound.intent.action.DO_SLEEP");
        } else if ("com.unisound.intent.action.DO_WAKEUP".equals(action)) {
            mo(context, "com.unisound.intent.action.DO_WAKEUP");
            mr(context);
            C0062p.aZ(context, null);
            context.startService(new Intent(context, (Class<?>) MainService.class));
        }
    }
}
