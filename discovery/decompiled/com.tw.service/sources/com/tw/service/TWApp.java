package com.tw.service;

import android.app.Application;
import android.content.Intent;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public class TWApp extends Application {
    @Override // android.app.Application
    public void onCreate() {
        startServiceAsUser(new Intent(this, (Class<?>) TWService.class), UserHandle.CURRENT);
    }
}
