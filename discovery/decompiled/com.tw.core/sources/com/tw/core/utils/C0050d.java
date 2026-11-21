package com.tw.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* JADX INFO: renamed from: com.tw.core.utils.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0050d implements Thread.UncaughtExceptionHandler {
    private static C0050d s;
    private Context w;
    private Thread.UncaughtExceptionHandler x;
    private Map v = new HashMap();
    private DateFormat u = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    String y = "/.crash/";
    String t = "twcore_log.txt";

    private C0050d() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void B() {
        File file = new File(Environment.getExternalStorageDirectory() + this.y + this.t);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    private File C() {
        String str = Environment.getExternalStorageDirectory() + this.y + this.t;
        B.cj("CrashHandlerUtils", "文件读取路径 %s", str);
        File file = new File(str);
        if (!file.exists()) {
            B.cj("CrashHandlerUtils", "文件不存在", new Object[0]);
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "utf8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    B.cj("CrashHandlerUtils", "文件内容 %s", sb.toString());
                    bufferedReader.close();
                    fileInputStream.close();
                    return file;
                }
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean D(Throwable th) {
        if (th == null) {
            return false;
        }
        A(this.w);
        x(th);
        return true;
    }

    private void F() {
        File file = new File(Environment.getExternalStorageDirectory() + this.y + this.t);
        if (E.cB(file) > 1048576) {
            file.delete();
        }
    }

    public static C0050d w() {
        if (s == null) {
            synchronized (C0050d.class) {
                s = new C0050d();
            }
        }
        return s;
    }

    private void z() {
        try {
            String strBz = C0064r.bz(this.w, "KEY_CRASH_TIME", null);
            B.cj("CrashHandlerUtils", "异常时间 %s", strBz);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (TextUtils.isEmpty(strBz)) {
                C0064r.bC(this.w, "KEY_CRASH_TIME", String.valueOf(jCurrentTimeMillis));
                B.cj("CrashHandlerUtils", "add first crash %s", Long.valueOf(jCurrentTimeMillis));
            } else if (strBz.contains(",")) {
                String[] strArrSplit = strBz.split(",");
                if (strArrSplit.length > 2) {
                    long j = Long.parseLong(strArrSplit[0]);
                    long j2 = Long.parseLong(strArrSplit[1]);
                    long j3 = Long.parseLong(strArrSplit[2]);
                    B.ci("CrashHandlerUtils", "两次异常的时间 %s --- %s 结果 --- %s", Long.valueOf(j), Long.valueOf(j3), Long.valueOf(j3 - j));
                    if (j3 - j <= 60000) {
                        B.cj("CrashHandlerUtils", "pm clear %s", this.w.getPackageName());
                        C0064r.bC(this.w, "KEY_CRASH_TIME", null);
                        Runtime.getRuntime().exec("pm clear " + this.w.getPackageName());
                    } else {
                        String str = j2 + "," + j3 + "," + jCurrentTimeMillis;
                        C0064r.bC(this.w, "KEY_CRASH_TIME", str);
                        B.cj("CrashHandlerUtils", "replace third crash %s", str);
                    }
                } else {
                    String str2 = strBz + "," + jCurrentTimeMillis;
                    C0064r.bC(this.w, "KEY_CRASH_TIME", str2);
                    B.cj("CrashHandlerUtils", "add third crash %s", str2);
                }
            } else {
                String str3 = strBz + "," + jCurrentTimeMillis;
                C0064r.bC(this.w, "KEY_CRASH_TIME", str3);
                B.cj("CrashHandlerUtils", "add second crash %s", str3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void A(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                String str = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String str2 = packageInfo.versionCode + "";
                String str3 = this.u.format(new Date());
                this.v.put("versionName", str);
                this.v.put("versionCode", str2);
                this.v.put("crashTime", str3);
                this.v.put("deviceID", com.tw.core.b.f.gr());
            }
        } catch (Exception e) {
            B.cj("CrashHandlerUtils", "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.v.put(field.getName(), field.get(null).toString());
                B.cj("CrashHandlerUtils", "%s : %s", field.getName(), field.get(null));
            } catch (Exception e2) {
                B.cj("CrashHandlerUtils", "an error occured when collect crash info", e2);
            }
        }
    }

    public void E(Context context) {
        this.w = context;
        this.x = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        F();
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (!D(th) && this.x != null) {
            this.x.uncaughtException(thread, th);
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            z();
            B.cj("CrashHandlerUtils", "kill self", new Object[0]);
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    public String x(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry : this.v.entrySet()) {
            stringBuffer.append(((String) entry.getKey()) + "=" + ((String) entry.getValue()) + "\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        stringBuffer.append(stringWriter.toString());
        B.cj("CrashHandlerUtils", "异常日志 %s", stringBuffer.toString());
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                File file = new File(Environment.getExternalStorageDirectory() + this.y);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, this.t);
                B.cj("CrashHandlerUtils", "文件保存路径 %s", file2.getCanonicalPath());
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(stringBuffer.toString() + "\n");
                outputStreamWriter.flush();
                outputStreamWriter.close();
                fileOutputStream.close();
                B.cj("CrashHandlerUtils", "写入成功", new Object[0]);
            }
            return this.t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void y() throws Throwable {
        File fileC = C();
        if (fileC != null && fileC.exists() && fileC.isFile()) {
            HashMap map = new HashMap();
            String str = "twlog/" + com.tw.core.b.d.fX(this.w) + MqttTopic.TOPIC_LEVEL_SEPARATOR + C0066t.bN(System.currentTimeMillis(), "yyyy-MM-dd") + MqttTopic.TOPIC_LEVEL_SEPARATOR + com.tw.core.b.f.gr() + MqttTopic.TOPIC_LEVEL_SEPARATOR;
            map.put("key", str + System.currentTimeMillis() + fileC.getName());
            HashMap map2 = new HashMap();
            map2.put("file", fileC);
            B.ci("CrashHandlerUtils", "错误日志上报 %s", str);
            C0058l.ao("http://dofun-log.oss-cn-shenzhen.aliyuncs.com", map, map2, new W(this));
        }
    }
}
