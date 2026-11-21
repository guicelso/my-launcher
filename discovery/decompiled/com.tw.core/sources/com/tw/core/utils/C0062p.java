package com.tw.core.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;
import org.json.JSONArray;

/* JADX INFO: renamed from: com.tw.core.utils.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0062p {
    private static int T;
    private static String U;
    public static final String S = Environment.getExternalStorageDirectory() + File.separator + "dofun" + File.separator;
    private static double R = 6378.137d;

    public static String aT(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
            } catch (Exception e) {
                C0065s.bL("Tools", e);
            }
        }
        return str;
    }

    public static boolean aU() {
        if (T > 0) {
            return T == 1;
        }
        try {
            Class.forName("android.tw.john.TWUtil");
            T = 1;
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            T = 2;
            return false;
        }
    }

    public static String aV(Context context) {
        if (U != null) {
            return U;
        }
        if (Build.VERSION.SDK_INT <= 23) {
            return context.getResources().getConfiguration().locale.toString();
        }
        Locale localeBg = bg(context);
        return localeBg != null ? localeBg.getLanguage() + "_" + localeBg.getCountry() : "";
    }

    public static boolean aW(String... strArr) {
        if (strArr == null) {
            return true;
        }
        for (String str : strArr) {
            if (TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static long aX(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0L;
        }
    }

    public static int aY(long j, long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(j2);
        int i = calendar.get(1);
        int i2 = calendar2.get(1);
        int i3 = calendar.get(6);
        int i4 = calendar2.get(6);
        if (i - i2 > 0) {
            return calendar2.getActualMaximum(6) + (i3 - i4);
        }
        if (i - i2 >= 0) {
            return i3 - i4;
        }
        return (i3 - i4) - calendar.getActualMaximum(6);
    }

    public static void aZ(Context context, String str) {
        try {
            String strBz = C0064r.bz(context, "startServiceApkName", null);
            B.ci("Tools", "startServices packages=%s ,pkgName=%s", strBz, str);
            if (aW(strBz)) {
                return;
            }
            JSONArray jSONArray = new JSONArray(strBz);
            for (int i = 0; i < jSONArray.length(); i++) {
                String[] strArrSplit = jSONArray.getString(i).split("\\|");
                if (strArrSplit.length == 2) {
                    String str2 = strArrSplit[0];
                    String str3 = strArrSplit[1];
                    B.ci("Tools", "startServices pName=%s ,serviceName=%s", str2, str3);
                    if (aW(str)) {
                        bp(context, str2, str3);
                    } else if (str.equals(str2)) {
                        bp(context, str2, str3);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ba() {
        U = null;
    }

    public static boolean bb(Closeable closeable) {
        if (closeable == null) {
            return true;
        }
        try {
            closeable.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String bc() {
        return Build.MODEL;
    }

    public static String bd(long j) {
        return new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA)).format(j / 1048576.0f);
    }

    public static String be() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }

    public static String bf(Context context) {
        return context != null ? context.getPackageName() : "";
    }

    public static Locale bg(Context context) {
        try {
            Configuration configuration = context.getResources().getConfiguration();
            Method declaredMethod = Configuration.class.getDeclaredMethod("getLocales", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(configuration, new Object[0]);
            Method declaredMethod2 = objInvoke.getClass().getDeclaredMethod("get", Integer.TYPE);
            declaredMethod2.setAccessible(true);
            return (Locale) declaredMethod2.invoke(objInvoke, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList bh(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            String strBz = C0064r.bz(context, "installAPKName", null);
            if (!TextUtils.isEmpty(strBz)) {
                JSONArray jSONArray = new JSONArray(strBz);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                return arrayList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static String bi(long j, String str) {
        try {
            return new SimpleDateFormat(str).format(Long.valueOf(j));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList bj(Context context) {
        ArrayList arrayList = new ArrayList();
        try {
            String strBz = C0064r.bz(context, "uploadAPKName", null);
            if (!TextUtils.isEmpty(strBz)) {
                JSONArray jSONArray = new JSONArray(strBz);
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(jSONArray.getString(i));
                }
                return arrayList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static int bk(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(bf(context), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            C0065s.bL("Tools", e);
            return -1;
        }
    }

    public static int bl(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            C0065s.bL("Tools", e);
            return -1;
        }
    }

    public static String bm(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            C0065s.bL("Tools", e);
            return "";
        }
    }

    public static void bn(Context context, String str, String str2) {
        try {
            if (com.tw.core.b.d.fY(str)) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(str, str2));
                intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bo(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strBz = C0064r.bz(context, "startActivityApkName", null);
        if (aW(strBz)) {
            return;
        }
        B.ci("Tools", "empty activity config %s", strBz);
        try {
            JSONArray jSONArray = new JSONArray(strBz);
            for (int i = 0; i < jSONArray.length(); i++) {
                String[] strArrSplit = jSONArray.getString(i).split("\\|");
                if (strArrSplit.length == 2) {
                    String str2 = strArrSplit[0];
                    String str3 = strArrSplit[1];
                    if (str.equals(str2)) {
                        B.ci("Tools", "startActivity %s/%s", str2, str3);
                        bn(context, str2, str3);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bp(Context context, String str, String str2) {
        try {
            if (com.tw.core.b.d.fY(str)) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(str, str2));
                ComponentName componentNameStartService = context.startService(intent);
                if (componentNameStartService == null) {
                    bo(context, str);
                } else {
                    B.ci("Tools", "startService success %s", componentNameStartService);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            bo(context, str);
        }
    }

    public static double bq(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static int br(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
