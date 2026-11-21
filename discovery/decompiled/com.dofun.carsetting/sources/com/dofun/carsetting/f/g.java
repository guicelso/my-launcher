package com.dofun.carsetting.f;

import android.util.Log;
import com.dofun.carsetting.app.MyApp;

/* JADX INFO: compiled from: LogUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static g f223a;

    public static g a() {
        g gVar;
        synchronized ("lock") {
            if (f223a == null) {
                f223a = new g();
            }
            gVar = f223a;
        }
        return gVar;
    }

    public void a(String str, String str2) {
        if (MyApp.f133a && b(str) && b(str2)) {
            Log.e(str, str2);
        }
    }

    public void b(String str, String str2) {
        if (MyApp.f133a && b(str) && b(str2)) {
            Log.i(str, str2);
        }
    }

    public void c(String str, String str2) {
        if (MyApp.f133a && b(str) && b(str2)) {
            Log.d(str, str2);
        }
    }

    public void a(String str) {
        if (MyApp.f133a && b(str)) {
            System.out.println(str);
        }
    }

    private boolean b(String str) {
        return (str == null || str.equals("")) ? false : true;
    }

    public static int a(Exception exc) {
        StackTraceElement[] stackTrace = exc.getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            return -1;
        }
        return stackTrace[0].getLineNumber();
    }
}
