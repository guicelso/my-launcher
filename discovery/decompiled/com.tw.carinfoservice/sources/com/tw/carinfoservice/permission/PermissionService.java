package com.tw.carinfoservice.permission;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class PermissionService extends Service {
    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.e("LS123", "requestPermission: ");
        if (Build.VERSION.SDK_INT >= 29) {
            Log.e("LS123", "requestPermission1: ");
            Intent intent = new Intent(this, (Class<?>) MainActivity.class);
            intent.setFlags(268435456);
            startActivity(intent);
        }
    }
}
