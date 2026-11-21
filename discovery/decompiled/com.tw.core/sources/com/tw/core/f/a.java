package com.tw.core.f;

import android.os.Looper;
import com.tw.core.utils.B;
import com.tw.core.utils.E;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a ib;
    private Set ia = new HashSet();

    public static a mY() {
        if (ib == null) {
            synchronized (a.class) {
                if (ib == null) {
                    ib = new a();
                }
            }
        }
        return ib;
    }

    public boolean mZ(String str) {
        return this.ia.contains(str);
    }

    public boolean na(String str, long j, String str2, String str3) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            B.cj("LogcatManager", "当前主线程禁止抓日志", new Object[0]);
            return false;
        }
        if (!E.cC(new File(str2))) {
            return false;
        }
        try {
            this.ia.add(str3);
            String str4 = String.format("logcat %s -f %s & \n", str, str2 + File.separator + str3);
            B.ci("LogcatManager", "开始抓取日志 %s", str4);
            Process processExec = Runtime.getRuntime().exec(str4);
            if (processExec != null) {
                try {
                    Thread.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                B.ci("LogcatManager", "停止抓日志线程", new Object[0]);
                processExec.destroy();
                return true;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            this.ia.remove(str3);
        }
        return false;
    }
}
