package com.tw.service.a;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class c implements ServiceConnection {
    final /* synthetic */ f n;

    c(f fVar) {
        this.n = fVar;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            Log.e(f.F(), "=onServiceConnected===================================");
            this.n.mService = new Messenger(iBinder);
            if (this.n.Qk) {
                this.n.ra();
            } else {
                Message messageObtain = Message.obtain(this.n.mHandler, 65283, 0, 0);
                messageObtain.replyTo = this.n.Sk;
                this.n.mService.send(messageObtain);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        try {
            this.n.mService = null;
            Log.e(f.F(), "=onServiceDisconnected ======================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
