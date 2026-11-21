package com.tw.core.messenger.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

/* JADX INFO: loaded from: classes.dex */
public class MessengerService extends Service {
    private Messenger a;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.a.getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.a = new Messenger(new b(this, com.tw.core.messenger.a.get()));
    }
}
