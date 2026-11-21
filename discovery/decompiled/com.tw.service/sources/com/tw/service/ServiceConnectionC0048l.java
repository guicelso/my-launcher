package com.tw.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/* JADX INFO: renamed from: com.tw.service.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class ServiceConnectionC0048l implements ServiceConnection {
    final /* synthetic */ TWService n;

    ServiceConnectionC0048l(TWService tWService) {
        this.n = tWService;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.n.Ib) {
            if (this.n.Jb != this) {
                return;
            }
            Messenger messenger = new Messenger(iBinder);
            Message messageObtain = Message.obtain((Handler) null, 1);
            messageObtain.replyTo = new Messenger(new HandlerC0047k(this, this.n.mHandler.getLooper(), this));
            messageObtain.arg2 = 1;
            messageObtain.arg1 = 1;
            try {
                messenger.send(messageObtain);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }
}
