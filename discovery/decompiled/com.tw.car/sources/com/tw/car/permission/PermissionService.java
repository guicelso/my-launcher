package com.tw.car.permission;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public class PermissionService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT < 29 || Settings.System.getInt(getContentResolver(), "CARAPP_APPLICATION_AUTHORITY", 0) == 1) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MainActivity.class);
        intent.setFlags(268435456);
        startActivity(intent);
        Settings.System.putInt(getContentResolver(), "CARAPP_APPLICATION_AUTHORITY", 1);
    }
}
