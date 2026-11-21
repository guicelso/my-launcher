package com.dofun.carsetting.app;

import android.app.Application;
import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class MyApp extends Application {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static boolean f133a = false;
    private static MyApp b;

    public static synchronized MyApp a() {
        return b;
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        b = this;
    }
}
