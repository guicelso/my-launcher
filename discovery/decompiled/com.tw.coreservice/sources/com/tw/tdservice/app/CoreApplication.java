package com.tw.tdservice.app;

import android.app.Application;
import android.content.Intent;
import d.c;
import d.d;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class CoreApplication extends Application {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static CoreApplication f30a;

    public class a implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            try {
                for (String str : d.d(new File(d.c("launchCore"), "time").getAbsolutePath()).split("\n")) {
                    c.b("CoreApplication", " %s", str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        f30a = this;
        startService(new Intent("com.tw.coreservice.action.MainService"));
        new Thread(new a()).start();
    }
}
