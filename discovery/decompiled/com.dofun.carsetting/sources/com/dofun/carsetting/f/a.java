package com.dofun.carsetting.f;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.dofun.carsetting.app.MyApp;

/* JADX INFO: compiled from: APPUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static a f218a;
    private Context b = MyApp.a();

    public static synchronized a a() {
        if (f218a == null) {
            f218a = new a();
        }
        return f218a;
    }

    public static void a(String str) {
        try {
            MyApp myAppA = MyApp.a();
            MyApp.a();
            ((ActivityManager) myAppA.getSystemService("activity")).forceStopPackage(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean b(String str) {
        ApplicationInfo applicationInfo;
        boolean z = false;
        if (!TextUtils.isEmpty(str)) {
            PackageInfo packageInfo = null;
            try {
                PackageInfo packageInfo2 = this.b.getPackageManager().getPackageInfo(str, 0);
                applicationInfo = this.b.getPackageManager().getApplicationInfo(str, 0);
                packageInfo = packageInfo2;
            } catch (PackageManager.NameNotFoundException unused) {
                applicationInfo = null;
            }
            if (packageInfo != null && applicationInfo != null && applicationInfo.enabled) {
                z = true;
            }
        }
        g.a().b("AppUtil", str + " is installed ? " + z);
        return z;
    }

    public int c(String str) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) this.b.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(str)) {
                return runningAppProcessInfo.pid;
            }
        }
        return -1;
    }

    public static void a(Context context, String str, boolean z) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (z) {
                packageManager.setApplicationEnabledSetting(str, 1, 0);
            } else {
                packageManager.setApplicationEnabledSetting(str, 2, 0);
            }
        } catch (Exception unused) {
        }
    }
}
