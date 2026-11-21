package com.tw.core.utils;

import android.app.ActivityManager;
import com.tw.core.app.DoFunApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class M {
    public static String dr(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(String.format("pm path %s \n", str)).getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line + "\n");
                    } catch (Throwable th) {
                        bufferedReader.close();
                        throw th;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    bufferedReader.close();
                }
            }
            bufferedReader.close();
            B.ci("RuntimeCmdManager", "result %s", sb);
            String strReplace = sb.toString().replace("package:", "").replace("\n", "");
            File file = new File(strReplace);
            if (file.exists()) {
                B.ci("RuntimeCmdManager", "file %s", file);
                return strReplace;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static boolean ds(String str) {
        Iterator<T> it = ((ActivityManager) DoFunApplication.nu().getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
        while (it.hasNext()) {
            if (((ActivityManager.RunningServiceInfo) it.next()).service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dt(int i) {
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) DoFunApplication.nu().getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(DoFunApplication.nu().getPackageName()) && i == runningAppProcessInfo.pid) {
                return true;
            }
        }
        return false;
    }

    public static Process du(String str) {
        Process processExec;
        try {
            processExec = Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            C0065s.bK("RuntimeCmdManager", "exec Runtime commond:" + str + ", IOException" + e);
            processExec = null;
        }
        C0065s.bK("RuntimeCmdManager", "exec Runtime commond:" + str + ", Process:" + processExec);
        return processExec;
    }
}
