package com.dofun.carsetting.activity.apkinstall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class InstallEventReceiver extends BroadcastReceiver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final Object f91a = new Object();
    private static a b;

    private static a a(Context context) {
        synchronized (f91a) {
            if (b == null && Build.VERSION.SDK_INT >= 21) {
                b = new a(TemporaryFileManager.a(context));
            }
        }
        return b;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        a(context).a(context, intent);
    }
}
