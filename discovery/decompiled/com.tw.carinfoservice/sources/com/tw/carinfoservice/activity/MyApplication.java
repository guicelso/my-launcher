package com.tw.carinfoservice.activity;

import a.b.c.a;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import c.b.a.k.i;
import java.io.File;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class MyApplication extends Application {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static MyApplication f493b;

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        ApplicationInfo applicationInfo;
        String str;
        super.attachBaseContext(context);
        Set<File> set = a.f34a;
        Log.i("MultiDex", "Installing application");
        try {
            if (a.f35b) {
                str = "VM has multidex support, MultiDex support library is disabled.";
            } else {
                try {
                    applicationInfo = getApplicationInfo();
                } catch (RuntimeException e) {
                    Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
                    applicationInfo = null;
                }
                if (applicationInfo == null) {
                    Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                } else {
                    a.d(this, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "");
                    str = "install done";
                }
            }
            Log.i("MultiDex", str);
        } catch (Exception e2) {
            Log.e("MultiDex", "MultiDex installation failure", e2);
            StringBuilder sbB = c.a.a.a.a.b("MultiDex installation failed (");
            sbB.append(e2.getMessage());
            sbB.append(").");
            throw new RuntimeException(sbB.toString());
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        f493b = this;
        try {
            i.a().b(this);
        } catch (Exception e) {
            Log.e("MyApplication", Log.getStackTraceString(e));
        }
    }
}
