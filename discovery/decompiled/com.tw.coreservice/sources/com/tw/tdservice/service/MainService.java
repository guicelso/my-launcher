package com.tw.tdservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import c.b;
import c.d;
import c.h;
import d.c;

/* JADX INFO: loaded from: classes.dex */
public class MainService extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        c.b("MainService", "onCreate", new Object[0]);
        new b(this).b();
        new d(this);
        new h(this);
    }
}
