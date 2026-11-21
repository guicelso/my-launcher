package com.tw.service.xt;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class e implements ServiceConnection {
    final /* synthetic */ CommandService j;

    e(CommandService commandService) {
        this.j = commandService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.d("CommandService", "air onServiceConnected");
        try {
            this.j.mAircontroller = a.a.a.b.asInterface(iBinder);
            if (this.j.mBundle != null) {
                this.j.mAircontroller.extendedInterface(this.j.mBundle);
            }
        } catch (Exception e) {
            Log.e("CommandService", Log.getStackTraceString(e));
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("CommandService", "air onServiceDisconnected");
        this.j.mAircontroller = null;
    }
}
