package com.tw.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class W implements ServiceConnection {
    final /* synthetic */ TWService n;

    W(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        StringBuilder sbE = b.a.a.a.a.e("onServiceConnected:");
        sbE.append(componentName.getPackageName());
        Log.d("TWService", sbE.toString());
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        StringBuilder sbE = b.a.a.a.a.e("onServiceDisconnected:");
        sbE.append(componentName.getPackageName());
        Log.d("TWService", sbE.toString());
        this.n.ta();
    }
}
