package com.dofun.carsetting.f;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.StatFs;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.tw.john.TWUtil;
import android.util.Log;
import android.util.Pair;
import com.android.internal.util.MemInfoReader;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

/* JADX INFO: compiled from: DeviceInfoUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static u f219a = null;
    public static int b = 20;
    public static int c = 80;
    private static TelephonyManager d = null;
    private static String e = "";

    public static boolean K() {
        return true;
    }

    public static Pair<String, String> a(Context context) {
        try {
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long totalSize = memInfoReader.getTotalSize();
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            long j = 0;
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                j = memoryInfo.availMem;
            }
            float f = ((totalSize / 1024.0f) / 1024.0f) / 1024.0f;
            StringBuilder sb = new StringBuilder();
            double d2 = f;
            sb.append(Math.ceil(d2));
            sb.append("GB");
            String string = sb.toString();
            if (!a().contains("MOHAWK") && o() && Math.ceil(d2) == 3.0d) {
                string = "4.0GB";
            }
            if (a().contains("THEME1-TPMS-360") && Math.ceil(d2) == 6.0d) {
                string = "4.0GB";
            }
            if (f() && d2 > 1.3d && d2 < 1.7d) {
                string = a(f);
            }
            if ((F() || G()) && d2 > 1.0d && d2 <= 1.5d) {
                string = a(f);
            }
            if (v() && d2 > 1.2d && d2 < 1.7d) {
                string = "1.5GB";
            }
            return new Pair<>(string, Formatter.formatShortFileSize(context, j) + " (" + String.valueOf((100 * j) / totalSize) + "%)");
        } catch (Exception e2) {
            e2.printStackTrace();
            return new Pair<>("", "");
        }
    }

    public static float b(Context context) {
        try {
            MemInfoReader memInfoReader = new MemInfoReader();
            memInfoReader.readMemInfo();
            long totalSize = memInfoReader.getTotalSize();
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                long j = memoryInfo.availMem;
            }
            float f = ((totalSize / 1024.0f) / 1024.0f) / 1024.0f;
            float f2 = (!a().contains("MOHAWK") && o() && Math.ceil((double) f) == 3.0d) ? 4.0f : f;
            if (a().contains("THEME1-TPMS-360") && Math.ceil(f) == 6.0d) {
                f2 = 4.0f;
            }
            if (f()) {
                double d2 = f;
                if (d2 > 1.3d && d2 < 1.7d) {
                    f2 = 1.5f;
                }
            }
            if ((F() || G()) && f <= 1.5d) {
                f2 = 1.5f;
            }
            if (!v()) {
                return f2;
            }
            double d3 = f;
            if (d3 <= 1.2d || d3 >= 1.7d) {
                return f2;
            }
            return 1.5f;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0.0f;
        }
    }

    public static String a(float f) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        return decimalFormat.format(f) + "GB";
    }

    public static Pair<String, String> c(Context context) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockCountLong = statFs.getBlockCountLong();
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        long j = availableBlocksLong * blockSizeLong;
        long j2 = blockCountLong * blockSizeLong;
        long j3 = ((j2 / 1024) / 1024) / 1024;
        String str = "";
        g.a().a("DeviceInfoUtil", "总共内存：" + j3);
        if (j3 <= 8) {
            str = "8.0GB";
        } else if (j3 <= 16) {
            str = "16.0GB";
        } else if (j3 <= 32) {
            str = "32.0GB";
        } else if (j3 <= 64) {
            str = "64.0GB";
        } else if (j3 <= 128) {
            str = "128.0GB";
        } else if (j3 <= 256) {
            str = "256.0GB";
        } else if (j3 <= 512) {
            str = "512.0GB";
        }
        return new Pair<>(str, Formatter.formatFileSize(context, j) + " (" + String.valueOf((100 * j) / j2) + "%)");
    }

    public static String a() throws Throwable {
        if (!TextUtils.isEmpty(e)) {
            return e;
        }
        String str = "";
        try {
            String str2 = SystemProperties.get("ro.systemtw.version");
            try {
                str = TextUtils.isEmpty(str2) ? SystemProperties.get("ro.tw.version") : str2;
                BufferedReader bufferedReader = null;
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/system/etc/version"));
                        try {
                            String line = bufferedReader2.readLine();
                            if (line != null && line.length() > 0) {
                                str = line;
                            }
                            bufferedReader2.close();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            g.a().a("DeviceInfoUtil", e.getMessage());
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e4) {
                e = e4;
                str = str2;
                g.a().a("DeviceInfoUtil", e.getMessage());
            }
        } catch (Exception e5) {
            e = e5;
        }
        e = str;
        return str;
    }

    public static String b() {
        String str = SystemProperties.get("ro.tw.version");
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String[] strArrSplit = str.split("_");
        Log.d("DeviceInfoUtil", "oSVersionInfo = " + strArrSplit);
        return strArrSplit[2];
    }

    public static long a(String str) {
        String[] strArrSplit = str.split("_");
        if (strArrSplit == null || strArrSplit.length <= 1) {
            return 0L;
        }
        try {
            return new SimpleDateFormat("yyyyMMdd.HHmmss").parse(strArrSplit[1]).getTime();
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    @SuppressLint({"HardwareIds"})
    public static String d(Context context) {
        try {
            if (d == null) {
                d = (TelephonyManager) context.getSystemService("phone");
            }
            String strC = null;
            if (d != null && ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
                strC = d.getSimSerialNumber();
            }
            int iIntValue = g(MyApp.a()).intValue();
            if (iIntValue != 1) {
                strC = c(iIntValue);
            }
            return TextUtils.isEmpty(strC) ? "0" : strC;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "0";
        }
    }

    public static String e(Context context) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String str = SystemProperties.get("ro.sys.cputype");
            if (str != null) {
                return str;
            }
            bufferedReader.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return context.getResources().getString(R.string.unknown);
    }

    public static String f(Context context) {
        if (o() || p()) {
            if (TextUtils.equals(SystemProperties.get("ro.product.cpu.type"), "T618")) {
                return e(context) + "(S)" + context.getString(R.string.core8_ts10s);
            }
            if (p()) {
                return e(context) + context.getString(R.string.core8_ts10a);
            }
            return e(context) + context.getString(R.string.core8);
        }
        if (s()) {
            return e(context) + context.getString(R.string.cpu_core_info_ts18);
        }
        if (f()) {
            if (o.e().equals("0")) {
                if (b("/sys/class/boot_version/cpuversion") == 2) {
                    return SystemProperties.get("ro.sys.cputype") + "-C";
                }
                if (b("/sys/class/boot_version/cpuversion") == 1) {
                    return SystemProperties.get("ro.sys.cputype") + "-CH";
                }
                return SystemProperties.get("ro.sys.cputype") + "-B";
            }
            if (b("/sys/class/boot_version/cpuversion") == 2) {
                return context.getString(R.string.about_cpu_info_c);
            }
            if (b("/sys/class/boot_version/cpuversion") == 1) {
                return context.getString(R.string.about_cpu_info_ch);
            }
            return context.getString(R.string.about_cpu_info_b);
        }
        if (F()) {
            return l(context);
        }
        if (I() || J()) {
            return e(context) + context.getString(R.string.core8_ts20);
        }
        return e(context);
    }

    public static boolean c() {
        return a().startsWith("V7.1.1") || a().startsWith("V7.2.1") || a().startsWith("V7.3.1") || a().startsWith("V7.3.2") || a().startsWith("V7.5.1") || a().startsWith("V7.5.2") || "QUAD-CORE t7 p1".equals(Build.MODEL);
    }

    public static boolean d() {
        String str = Build.MODEL;
        try {
            return f() && Integer.parseInt(a().substring(6, 7), 16) == 2;
        } catch (Exception e2) {
            Log.w("DeviceInfoUtil", e2.getMessage());
            return false;
        }
    }

    public static boolean e() {
        String str = Build.MODEL;
        try {
            return f() && Integer.parseInt(a().substring(6, 7), 16) == 1;
        } catch (Exception e2) {
            Log.w("DeviceInfoUtil", e2.getMessage());
            return false;
        }
    }

    public static boolean f() {
        return a().startsWith("T5Q") || "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean g() {
        return a().startsWith("T5Q.2") && "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean h() {
        return a().startsWith("T5Q.3") && "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean i() {
        return a().startsWith("T5Q.4") && "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean j() {
        return a().startsWith("T5Q.5") && "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean k() {
        return a().startsWith("T5Q.6.3") && "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean l() {
        return a().startsWith("V12.1.1");
    }

    public static boolean m() {
        return a().startsWith("V10.4.5") || "SP9832A".equals(Build.MODEL);
    }

    public static boolean n() {
        return a().startsWith("V11.1.1") || a().startsWith("V11.1.2") || "sp9853i_1h10_vmm".equals(Build.MODEL);
    }

    public static boolean o() {
        String str = Build.MODEL;
        return a().startsWith("TS10.");
    }

    public static boolean p() {
        String str = Build.MODEL;
        return a().startsWith("TS10S");
    }

    public static boolean q() {
        String str = Build.MODEL;
        return a().startsWith("TS10S.3.1");
    }

    public static boolean r() {
        String str = Build.MODEL;
        return a().startsWith("TS10.2.2");
    }

    public static boolean s() {
        String str = Build.MODEL;
        return a().startsWith("TS18.1.2") || a().startsWith("TS18.2.2") || a().startsWith("TS18A") || (!TextUtils.isEmpty(str) && str.contains("uis8581a2h10"));
    }

    public static boolean t() {
        String str = Build.MODEL;
        return a().startsWith("TS18.1.2") && a().contains("-US");
    }

    public static boolean u() {
        String str = Build.MODEL;
        return a().startsWith("TS18.2.2");
    }

    public static boolean v() {
        String str = Build.MODEL;
        return a().startsWith("TS18A");
    }

    public static boolean w() {
        return a().startsWith("V6.4.3");
    }

    public static boolean x() {
        return a().startsWith("V8.3.2");
    }

    public static boolean y() {
        return a().startsWith("V8.2.3");
    }

    public static boolean z() {
        return a().startsWith("V8.1.0") || a().startsWith("V8.1.2");
    }

    public static boolean A() {
        return a().startsWith("V8.1.8") || a().startsWith("V8.1.1");
    }

    public static boolean B() {
        return a().startsWith("V13.0.0") || a().startsWith("V13.0.0");
    }

    public static boolean C() {
        return a().startsWith("V9.4.1");
    }

    public static boolean D() {
        return a().startsWith("V9.3.1");
    }

    public static boolean E() {
        return a().startsWith("V9.2.2");
    }

    public static boolean F() {
        String str = Build.MODEL;
        return a().startsWith("V13") || "T100".equals(str) || "A100".equals(str) || "A133".equals(str) || "A133 pro".equals(str);
    }

    public static boolean G() {
        String str = Build.MODEL;
        return a().startsWith("V15");
    }

    public static boolean H() {
        return "trinket for arm64".equals(Build.MODEL);
    }

    public static boolean I() {
        return a().startsWith("V20") || "uis7870sc_2h10_nosec".equals(Build.MODEL);
    }

    public static boolean J() {
        return a().startsWith("V17") || Build.MODEL.startsWith("uis7865");
    }

    public static String a(Context context, SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null) {
            return context.getResources().getString(R.string.unknown);
        }
        CharSequence displayName = subscriptionInfo.getDisplayName();
        if (displayName == null || TextUtils.isEmpty(displayName.toString())) {
            return context.getResources().getString(R.string.unknown);
        }
        return displayName.toString();
    }

    public static Integer g(Context context) {
        Integer num = -1;
        try {
            Method declaredMethod = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, context);
            Method method = objInvoke.getClass().getMethod("getDefaultDataSubId", new Class[0]);
            return method != null ? Integer.valueOf(((Integer) method.invoke(objInvoke, new Object[0])).intValue()) : num;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return num;
        } catch (NoSuchMethodException e3) {
            try {
                try {
                    Method declaredMethod2 = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
                    declaredMethod2.setAccessible(true);
                    Object objInvoke2 = declaredMethod2.invoke(null, context);
                    Method method2 = objInvoke2.getClass().getMethod("getDefaultDataSubscriptionId", new Class[0]);
                    return method2 != null ? Integer.valueOf(((Integer) method2.invoke(objInvoke2, new Object[0])).intValue()) : num;
                } catch (IllegalAccessException unused) {
                    e3.printStackTrace();
                    return num;
                } catch (NoSuchMethodException unused2) {
                    e3.printStackTrace();
                    return num;
                } catch (InvocationTargetException unused3) {
                    e3.printStackTrace();
                    return num;
                } catch (Exception unused4) {
                    e3.printStackTrace();
                    return num;
                }
            } catch (IllegalAccessException unused5) {
                e3.printStackTrace();
                return num;
            } catch (NoSuchMethodException unused6) {
                Method declaredMethod3 = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("from", Context.class);
                declaredMethod3.setAccessible(true);
                Object objInvoke3 = declaredMethod3.invoke(null, context);
                Method method3 = objInvoke3.getClass().getMethod("getDefaultDataPhoneId", new Class[0]);
                if (method3 == null) {
                    return num;
                }
                Integer numValueOf = Integer.valueOf(((Integer) method3.invoke(objInvoke3, new Object[0])).intValue());
                try {
                    Log.d("DeviceInfoUtil", ((Integer) method3.invoke(objInvoke3, new Object[0])).intValue() + "");
                    return numValueOf;
                } catch (IllegalAccessException unused7) {
                    num = numValueOf;
                    e3.printStackTrace();
                    return num;
                } catch (NoSuchMethodException unused8) {
                    num = numValueOf;
                    e3.printStackTrace();
                    return num;
                } catch (InvocationTargetException unused9) {
                    num = numValueOf;
                    e3.printStackTrace();
                    return num;
                } catch (Exception unused10) {
                    num = numValueOf;
                    e3.printStackTrace();
                    return num;
                }
            } catch (InvocationTargetException unused11) {
                e3.printStackTrace();
                return num;
            } catch (Exception unused12) {
                e3.printStackTrace();
                return num;
            }
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return num;
        } catch (Exception e5) {
            e5.printStackTrace();
            return num;
        }
    }

    private static String c(int i) {
        String str = "0";
        try {
            int i2 = 0;
            Cursor cursorQuery = MyApp.a().getContentResolver().query(Uri.parse("content://telephony/siminfo"), new String[]{"_id", "sim_id", "icc_id", "display_name"}, "0=0", new String[0], null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("icc_id"));
                    String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
                    int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex("sim_id"));
                    int i4 = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
                    Log.d("Q_M", "icc_id-->" + string + "--cardSubId:" + i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("sim_id-->");
                    sb.append(i3);
                    Log.d("Q_M", sb.toString());
                    Log.d("Q_M", "display_name-->" + string2);
                    Log.d("Q_M", "subId或者说是_id->" + i4);
                    Log.d("Q_M", "---------------------------------" + cursorQuery.getCount());
                    i2++;
                    if (i2 != 1 && i2 == i) {
                        str = string;
                    }
                }
                cursorQuery.close();
            }
        } catch (Exception e2) {
            g.a().a("DeviceInfoUtil", g.a(e2) + "" + e2.getMessage());
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0083 A[Catch: Exception -> 0x0198, TryCatch #0 {Exception -> 0x0198, blocks: (B:3:0x0001, B:6:0x000b, B:8:0x0012, B:14:0x0056, B:113:0x0160, B:116:0x016a, B:119:0x0173, B:16:0x005e, B:19:0x0065, B:23:0x0070, B:24:0x0073, B:27:0x007a, B:30:0x0083, B:34:0x008e, B:37:0x0095, B:41:0x00a0, B:44:0x00a7, B:48:0x00b2, B:49:0x00b5, B:52:0x00bc, B:55:0x00c5, B:59:0x00d0, B:60:0x00d3, B:63:0x00da, B:67:0x00e5, B:68:0x00e8, B:71:0x00ef, B:75:0x00fa, B:78:0x0101, B:82:0x010c, B:83:0x010f, B:86:0x0116, B:89:0x011f, B:92:0x0128, B:97:0x0135, B:100:0x013c, B:103:0x0145, B:107:0x0150, B:110:0x0157, B:122:0x017a, B:124:0x0186, B:128:0x0191), top: B:135:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x008b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:63:0x00da A[Catch: Exception -> 0x0198, TryCatch #0 {Exception -> 0x0198, blocks: (B:3:0x0001, B:6:0x000b, B:8:0x0012, B:14:0x0056, B:113:0x0160, B:116:0x016a, B:119:0x0173, B:16:0x005e, B:19:0x0065, B:23:0x0070, B:24:0x0073, B:27:0x007a, B:30:0x0083, B:34:0x008e, B:37:0x0095, B:41:0x00a0, B:44:0x00a7, B:48:0x00b2, B:49:0x00b5, B:52:0x00bc, B:55:0x00c5, B:59:0x00d0, B:60:0x00d3, B:63:0x00da, B:67:0x00e5, B:68:0x00e8, B:71:0x00ef, B:75:0x00fa, B:78:0x0101, B:82:0x010c, B:83:0x010f, B:86:0x0116, B:89:0x011f, B:92:0x0128, B:97:0x0135, B:100:0x013c, B:103:0x0145, B:107:0x0150, B:110:0x0157, B:122:0x017a, B:124:0x0186, B:128:0x0191), top: B:135:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x00e2 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:71:0x00ef A[Catch: Exception -> 0x0198, TryCatch #0 {Exception -> 0x0198, blocks: (B:3:0x0001, B:6:0x000b, B:8:0x0012, B:14:0x0056, B:113:0x0160, B:116:0x016a, B:119:0x0173, B:16:0x005e, B:19:0x0065, B:23:0x0070, B:24:0x0073, B:27:0x007a, B:30:0x0083, B:34:0x008e, B:37:0x0095, B:41:0x00a0, B:44:0x00a7, B:48:0x00b2, B:49:0x00b5, B:52:0x00bc, B:55:0x00c5, B:59:0x00d0, B:60:0x00d3, B:63:0x00da, B:67:0x00e5, B:68:0x00e8, B:71:0x00ef, B:75:0x00fa, B:78:0x0101, B:82:0x010c, B:83:0x010f, B:86:0x0116, B:89:0x011f, B:92:0x0128, B:97:0x0135, B:100:0x013c, B:103:0x0145, B:107:0x0150, B:110:0x0157, B:122:0x017a, B:124:0x0186, B:128:0x0191), top: B:135:0x0001 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x00f7 A[RETURN] */
    public static boolean a(String str, int i, String str2) {
        try {
            if ("61808683".equals(str)) {
                return true;
            }
            u uVarA = u.a();
            if (uVarA != null) {
                int iWrite = uVarA.write(65521);
                uVarA.close();
                try {
                    if (String.format(Locale.US, "%05d", Integer.valueOf(Integer.parseInt(com.dofun.carsetting.activity.apkinstall.a.a.b(String.format(Locale.US, "%04x", Integer.valueOf(iWrite))).substring(0, 4), 16))).equals(str)) {
                        return true;
                    }
                } catch (Exception unused) {
                }
                switch (iWrite) {
                    case 6:
                        if (i == 5) {
                            if ("28012801".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 17:
                        if (i != 1) {
                            if (i == 5) {
                                if ("456789".equals(str)) {
                                    return true;
                                }
                            } else if (str2.equals(str)) {
                                return true;
                            }
                        } else if ("1234".equals(str)) {
                            return true;
                        }
                        break;
                    case 22:
                        if (i == 1) {
                            if ("668811".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                    if ("668822".equals(str)) {
                                        return true;
                                    }
                                    break;
                                case 4:
                                    if ("668833".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 34:
                        if (i == 5) {
                            if ("87654321".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 37:
                        if (i != 1) {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("333111".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        } else if ("333111".equals(str)) {
                            return true;
                        }
                        break;
                    case 38:
                    case 296:
                        if (i != 1) {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("8888".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        } else if ("8888".equals(str)) {
                            return true;
                        }
                        break;
                    case 48:
                        if (i == 1) {
                            if ("135790".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                case 4:
                                    if ("2468".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 101:
                        if (i == 3) {
                            if ("110601".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case R.styleable.AppCompatTheme_windowFixedWidthMinor /* 117 */:
                        if (i == 5) {
                            if ("33603360".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    case 121:
                        if (i == 1) {
                            if ("126".equals(str)) {
                                return true;
                            }
                        } else {
                            switch (i) {
                                case 3:
                                    if ("00000000".equals(str)) {
                                        return true;
                                    }
                                    break;
                                case 4:
                                    if ("126".equals(str)) {
                                        return true;
                                    }
                                    break;
                                default:
                                    if (str2.equals(str)) {
                                        return true;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 259:
                        if (i == 5) {
                            if ("12345678".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                    default:
                        if (u.c().startsWith("OLS") && i == 3) {
                            if ("1260".equals(str)) {
                                return true;
                            }
                        } else if (str2.equals(str)) {
                            return true;
                        }
                }
            } else if (u.c().startsWith("OLS")) {
                if ("1260".equals(str) && i == 3) {
                    return true;
                }
            } else if (str2.equals(str)) {
                return true;
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    public static boolean h(Context context) {
        long jBe = ((be() / 1024) / 1024) / 1024;
        return b(context) <= 1.0f && jBe > 16;
    }

    private static long be() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
    }

    public static boolean L() {
        return l() || m() || n() || o() || s() || p() || I() || J();
    }

    public static boolean M() {
        return B() || A() || z() || x() || y() || w();
    }

    public static boolean N() {
        return C() || D() || E();
    }

    public static boolean O() {
        return f();
    }

    public static boolean P() {
        return c();
    }

    public static boolean Q() {
        return Build.VERSION.SDK_INT >= 28;
    }

    public static boolean R() {
        return Build.VERSION.SDK_INT >= 29;
    }

    public static boolean S() {
        return Build.VERSION.SDK_INT >= 33;
    }

    public static boolean T() {
        return f() && U();
    }

    public static boolean U() {
        return a().contains("THEME1-CPbox") || a().contains("AUTOUI-CPbox");
    }

    public static com.dofun.carsetting.e.a.a V() {
        if (L()) {
            return new com.dofun.carsetting.e.a.f();
        }
        if (M()) {
            return new com.dofun.carsetting.e.a.b();
        }
        if (N()) {
            return new com.dofun.carsetting.e.a.e();
        }
        if (O() || F() || G()) {
            return new com.dofun.carsetting.e.a.c();
        }
        if (P()) {
            return new com.dofun.carsetting.e.a.d();
        }
        if (H()) {
            return new com.dofun.carsetting.e.a.f();
        }
        return new com.dofun.carsetting.e.a.a();
    }

    public static com.dofun.carsetting.e.b.a W() {
        if (s()) {
            if (t()) {
                return new com.dofun.carsetting.e.b.v();
            }
            if (u()) {
                return new com.dofun.carsetting.e.b.u();
            }
            if (v()) {
                return new com.dofun.carsetting.e.b.t();
            }
            return new com.dofun.carsetting.e.b.s();
        }
        if (o()) {
            if (r()) {
                return new com.dofun.carsetting.e.b.r();
            }
            return new com.dofun.carsetting.e.b.o();
        }
        if (n()) {
            return new com.dofun.carsetting.e.b.z();
        }
        if (m()) {
            return new com.dofun.carsetting.e.b.y();
        }
        if (l()) {
            return new com.dofun.carsetting.e.b.x();
        }
        if (B()) {
            return new com.dofun.carsetting.e.b.h();
        }
        if (A()) {
            return new com.dofun.carsetting.e.b.g();
        }
        if (z()) {
            return new com.dofun.carsetting.e.b.f();
        }
        if (x()) {
            return new com.dofun.carsetting.e.b.e();
        }
        if (y()) {
            return new com.dofun.carsetting.e.b.d();
        }
        if (w()) {
            return new com.dofun.carsetting.e.b.c();
        }
        if (!c()) {
            if (f()) {
                return new com.dofun.carsetting.e.b.j();
            }
            if (C()) {
                return new com.dofun.carsetting.e.b.m();
            }
            if (D()) {
                return new com.dofun.carsetting.e.b.l();
            }
            if (E()) {
                return new com.dofun.carsetting.e.b.k();
            }
            if (p()) {
                if (q()) {
                    return new com.dofun.carsetting.e.b.q();
                }
                return new com.dofun.carsetting.e.b.p();
            }
            if (F()) {
                return new com.dofun.carsetting.e.b.b();
            }
            if (H()) {
                return new com.dofun.carsetting.e.b.n();
            }
            if (G()) {
                return new com.dofun.carsetting.e.b.i();
            }
            if (I()) {
                return new com.dofun.carsetting.e.b.w();
            }
            if (J()) {
                return new com.dofun.carsetting.e.b.a();
            }
        }
        return new com.dofun.carsetting.e.b.a();
    }

    public static String X() {
        return com.dofun.carsetting.b.a.a().aa + ".cfg";
    }

    public static String Y() {
        return com.dofun.carsetting.b.a.a().aa + "_copyfiles";
    }

    public static String Z() {
        return com.dofun.carsetting.b.a.a().aa + "-e1a1";
    }

    public static String aa() {
        return com.dofun.carsetting.b.a.a().aa + "-e1a1-0";
    }

    public static String ab() {
        return com.dofun.carsetting.b.a.a().aa + "-e1a1-x";
    }

    public static String ac() {
        return com.dofun.carsetting.b.a.a().aa + "-e1a1-y";
    }

    public static String ad() {
        return com.dofun.carsetting.b.a.a().aa + "-e1a1-z";
    }

    public static String[] ae() {
        if (s()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (o()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (p()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (n()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (m()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (l()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (B()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (A()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (z()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (x()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (y()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (w()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (c()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (f()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (C()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (D()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (E()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (H()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        if (I() || J()) {
            return new String[]{"usb", "usb1", "usb2"};
        }
        return new String[]{"usb", "usb1", "usb2"};
    }

    public static String[] af() {
        if (s()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (o()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (p()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (n()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (m()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (l()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (B()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (A()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (z()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (x()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (y()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (w()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (c()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (f()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (C()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (D()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (E()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        if (I() || J()) {
            return new String[]{"extsd", "extsd1", "extsd2"};
        }
        return new String[]{"extsd", "extsd1", "extsd2"};
    }

    public static boolean ag() {
        if (n() || o() || p() || H() || I() || J()) {
            if (SystemProperties.getInt("persist.rf.type", 0) == 6) {
                return true;
            }
        } else if (s() && SystemProperties.getInt("ro.boot.wifionly", 0) == 1) {
            return true;
        }
        return false;
    }

    public static boolean ah() {
        return A() || s() || o() || f() || p() || F() || H() || G() || I() || J();
    }

    public static boolean ai() {
        return !f() || aj();
    }

    public static boolean aj() {
        int iAm = am();
        if (d()) {
            return iAm == 3;
        }
        return iAm == 1 || iAm == 2;
    }

    public static boolean ak() {
        int iAm = am();
        if (d()) {
            return iAm == 1 || iAm == 2;
        }
        return iAm == 5 || iAm == 6;
    }

    public static boolean al() {
        int iAm = am();
        return iAm == 0 || iAm == 5 || iAm == 6 || iAm == 8;
    }

    public static int am() {
        if (f219a == null) {
            f219a = u.a();
        }
        return f219a.write(65523, 0, 0, "car_reverse\ntvd_type");
    }

    public static int an() {
        return b("sys/class/car_reverse/csiuse");
    }

    public static int a(u uVar) {
        if (uVar != null) {
            return uVar.write(65523, 0, 0, "car_reverse\nntvd_resolution");
        }
        return 0;
    }

    public static boolean ao() {
        if (L() || H()) {
            if (SystemProperties.getInt("persist.camera.reverse", 1) == 1) {
                return false;
            }
        } else if (O()) {
            if (aj() || U()) {
                return false;
            }
        } else if (G()) {
            if (f219a == null || f219a.write(65523, 0, 0, "car_reverse\ntvd_type") == 1) {
                return false;
            }
        } else {
            if (F()) {
                return al();
            }
            return false;
        }
        return true;
    }

    public static int b(String str) throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                try {
                    int i = Integer.parseInt(bufferedReader2.readLine());
                    bufferedReader2.close();
                    return i;
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    return 1;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                return 1;
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String c(String str) throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                try {
                    String line = bufferedReader2.readLine();
                    try {
                        bufferedReader2.close();
                    } catch (Exception unused) {
                    }
                    return line;
                } catch (Exception unused2) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return "";
                    }
                    bufferedReader.close();
                    return "";
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused3) {
                return "";
            }
        } catch (Exception unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean ap() {
        if (p() || o() || I() || J() || a().contains("SteamMedia")) {
            if (SystemProperties.getInt("persist.camera.reverse", -1) == 1) {
                return true;
            }
        } else {
            if ((!f() && !F()) || ba()) {
                return false;
            }
            if (am() == 1 || am() == 2) {
                return true;
            }
        }
        return false;
    }

    public static int aq() {
        if (s() || o() || p() || H() || I() || J()) {
            return -1;
        }
        return (A() || f() || F() || G()) ? 2 : 0;
    }

    public static int ar() {
        return b("/sys/class/car_reverse/camtype");
    }

    public static boolean as() {
        return b("/sys/devices/tp9930/id") != 0;
    }

    public static boolean at() {
        String str = Build.MODEL;
        try {
            if (O()) {
                if (e() && !aj()) {
                    return true;
                }
            } else if (F()) {
                if (al()) {
                    return true;
                }
            } else {
                if (!G()) {
                    return SystemProperties.getInt("persist.camera.reverse", 1) != 1;
                }
                if (f219a == null) {
                    f219a = u.a();
                }
                if (f219a != null) {
                    int iWrite = f219a.write(65523, 0, 0, "car_reverse\ntvd_type");
                    Log.i("xiang", "aaa reverseType:" + iWrite);
                    return iWrite != 1;
                }
            }
            return false;
        } catch (Exception e2) {
            Log.w("DeviceInfoUtil", e2.getMessage());
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0031  */
    public static boolean au() throws Throwable {
        int iB = -1;
        if (H()) {
            iB = SystemProperties.getInt("sys.camera.tp28xx", -1);
        } else if (F() || G()) {
            if (aQ() > 1) {
                iB = 0;
            }
        } else if (f()) {
            iB = 0;
        } else {
            iB = b("sys/class/car_reverse/tvdtype");
        }
        return iB == 0 || a().contains("-360") || ba();
    }

    public static boolean av() throws Throwable {
        if (f() || G()) {
            return true;
        }
        if ((o() || s()) && !TextUtils.equals(SystemProperties.get("ro.boot.lcd.id", ""), "ID6202")) {
            return false;
        }
        String strA = a();
        Log.d("DeviceInfoUtil", "showSystemOnlineUpdate:systemName:" + strA);
        try {
            String[] strArrSplit = strA.split("_");
            if (strArrSplit.length <= 0) {
                return false;
            }
            if (TextUtils.equals("AUTOUI", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("THEME1", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("AUTOUI-360", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("THEME1-360", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("AUTOUI-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("THEME1-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-AUTOUI-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("AUTOUI-V2-360", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("THEME1-V2-360", strArrSplit[strArrSplit.length - 1])) {
                return true;
            }
            if ((o() || p() || F() || I() || J()) && (TextUtils.equals("WINDOW-AUTOUI", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1", strArrSplit[strArrSplit.length - 1]))) {
                return true;
            }
            if (s() && (TextUtils.equals("WINDOW-AUTOUI", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-AUTOUI-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1-ZST9", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1-ZST10", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("WINDOW-THEME1-ZST11", strArrSplit[strArrSplit.length - 1]))) {
                return true;
            }
            if (((p() || F() || H() || I() || J()) && (TextUtils.equals("OS-EN", b()) || TextUtils.equals("OS-CN", b()))) || TextUtils.equals("TW40-V-DH-V2", strArrSplit[strArrSplit.length - 1]) || TextUtils.equals("TW4-DH", strArrSplit[strArrSplit.length - 1])) {
                return true;
            }
            return SystemProperties.getBoolean("persist.sys.fota.enable", false);
        } catch (Exception e2) {
            Log.d("DeviceInfoUtil", "showSystemOnlineUpdate:" + e2.getMessage());
            return true;
        }
    }

    public static boolean aw() {
        return a().contains("POLICECAR-ZJ");
    }

    public static boolean i(Context context) {
        String strD = d(context);
        return (strD == null || strD.isEmpty() || strD.equals("0")) ? false : true;
    }

    public static String j(Context context) {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "PLATFORMINFO");
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        if (m()) {
            return "TS8";
        }
        if (c()) {
            return "T7";
        }
        if (n()) {
            return "TS9";
        }
        if (l()) {
            return "TS7";
        }
        if (o()) {
            return "TS10";
        }
        if (p()) {
            return "TS10S";
        }
        if (I()) {
            return "TS20";
        }
        if (J()) {
            return "TS101";
        }
        if (s()) {
            return v() ? "TS18A" : "TS18";
        }
        if (D()) {
            return "T8";
        }
        if (f()) {
            return "T5";
        }
        if (F()) {
            return m(context);
        }
        if (H()) {
            return "TQ919";
        }
        return G() ? "T9" : context.getString(R.string.device_info_default);
    }

    public static String k(Context context) {
        if (m()) {
            return "TS8";
        }
        if (c()) {
            return "T7";
        }
        if (n()) {
            return "TS9";
        }
        if (l()) {
            return "TS7";
        }
        if (o()) {
            return "TS10";
        }
        if (p()) {
            return "TS10S";
        }
        if (I()) {
            return "TS20";
        }
        if (J()) {
            return "TS101";
        }
        if (s()) {
            return "TS18";
        }
        if (D()) {
            return "T8";
        }
        if (f()) {
            return "T5";
        }
        if (F()) {
            return m(context);
        }
        if (H()) {
            return "TQ919";
        }
        return G() ? "T9" : context.getString(R.string.device_info_default);
    }

    public static boolean a(File file, String str) {
        File file2 = new File(file + "/" + str);
        StringBuilder sb = new StringBuilder();
        sb.append("((file.lastModified()/1000)<0x625F631C) .");
        sb.append(file2.lastModified() / 1000 < 1650418460);
        Log.d("DeviceInfoUtil", sb.toString());
        return file2.lastModified() / 1000 < 1650418460;
    }

    public static String ax() {
        if (o() || p()) {
            return "Dual-core ARM Mali-G52 3EE";
        }
        if (s()) {
            return "PowerVR Fentale GE8322 up to 550MHz";
        }
        return (I() || J()) ? "4Cores Arm Mali-G57" : "";
    }

    public static String ay() {
        if (I()) {
            return "IMG AX3596 @988 MHz, 8 TOPS";
        }
        return (!J() && G()) ? "2T" : "";
    }

    public static boolean az() {
        return u.c().contains("TW-CP1");
    }

    public static boolean aA() {
        return u.c().contains("ZST") && u.c().contains("-IVD");
    }

    public static boolean aB() {
        return u.c().contains("HST7") && u.c().contains("-RED");
    }

    public static boolean aC() {
        return u.c().contains("TW-CP1") || u.c().contains("CPbox");
    }

    public static boolean aD() {
        return u.c().contains("-POLICECAR");
    }

    public static int aE() {
        return b("/sys/devicesatform/soc/twi3/i2c-3/3-0036/ac107_debug/ac107");
    }

    public static boolean d(String str) {
        try {
            return new File(str).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean aF() {
        return !a().contains("KED8Y") || d("/usb/KED") || d("/usb0/KED") || d("/usb1/KED") || d("/usb2/KED");
    }

    public static boolean aG() {
        return u.c().contains("XRC1");
    }

    public static boolean aH() {
        return Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_WHEEL", 1) != 0;
    }

    public static boolean aI() {
        return SystemProperties.getInt("persist.sys.tw.apkinstall_hide", 1) != 0;
    }

    public static String l(Context context) throws Throwable {
        int iE = e("/sys/class/sunxi_info/sys_info");
        if (iE == 5120) {
            return "A133";
        }
        if (iE == 1024) {
            return "A100";
        }
        return iE == 32768 ? "A133 Pro" : context.getResources().getString(R.string.device_info_default);
    }

    public static String m(Context context) throws Throwable {
        if (b("/sys/class/twctrl/id300") == 1) {
            return "T6";
        }
        if (f219a == null) {
            f219a = u.a();
        }
        if (f219a.write(65521) == 1024) {
            return "A100";
        }
        int iE = e("/sys/class/sunxi_info/sys_info");
        if (iE == 5120) {
            return "T133";
        }
        if (iE == 1024) {
            return "T100";
        }
        if (iE == 32768) {
            return "T133 Pro";
        }
        if (f219a != null) {
            f219a.close();
            f219a = null;
        }
        return context.getResources().getString(R.string.device_info_default);
    }

    public static int e(String str) throws Throwable {
        BufferedReader bufferedReader;
        int i;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(str));
                i = 0;
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.contains("sunxi_chiptype")) {
                            i = Integer.parseInt(line.split(":")[1].trim(), 16);
                        }
                    } catch (Exception unused) {
                        if (bufferedReader != null) {
                            break;
                        }
                        return i;
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                }
            } catch (Exception unused2) {
                return 0;
            }
        } catch (Exception unused3) {
            bufferedReader = null;
            i = 0;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        bufferedReader.close();
        return i;
    }

    public static String aJ() {
        return b("/sys/kernel/debug/clk/pll_ddr/clk_rate") / 2000000 == 576 ? "0" : "1";
    }

    public static void a(int i) {
        if (f219a == null) {
            f219a = u.a();
        }
        if (f219a == null || b("/sys/kernel/debug/clk/pll_ddr/clk_rate") / 2000000 == i) {
            return;
        }
        Log.d("BJC", "setDDRFreq:" + i);
        f219a.write(40730, 1, 0, "dd if=/dev/block/mmcblk0 of=/data/tw/boot0.img bs=1k count=9");
        f219a.write(40730, 1, 0, "chmod 0755 /data/tw/boot0.img");
        if (new File("/data/tw/boot0.img").exists()) {
            try {
                if (a("/data/tw/boot0.img", "/data/tw/boot01.img", i) == 0 && new File("/data/tw/boot01.img").exists()) {
                    f219a.write(40730, 1, 0, "dd if=/data/tw/boot01.img of=/dev/block/mmcblk0 bs=1k count=9");
                    f219a.write(40730, 1, 0, "rm -rf /data/tw/boot01.img");
                }
            } catch (Exception e2) {
                Log.d("BJC", "modifyBoot0Freq:" + e2);
            }
            f219a.write(40730, 1, 0, "rm -rf /data/tw/boot0.img");
            f219a.write(40730, 1, 0, "sync");
        }
    }

    public static int a(String str, String str2, int i) {
        int i2;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            byte[] bArr = new byte[1024];
            int i3 = fileInputStream.read(bArr);
            for (int i4 = 0; i4 < 8; i4++) {
                fileOutputStream.write(bArr, 0, i3);
                i3 = fileInputStream.read(bArr);
            }
            int i5 = ((bArr[15] << 24) & ViewCompat.MEASURED_STATE_MASK) | 0 | ((bArr[14] << 16) & 16711680) | ((bArr[13] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[12] & 255);
            int i6 = ((bArr[59] << 24) & ViewCompat.MEASURED_STATE_MASK) | 0 | ((bArr[58] << 16) & 16711680) | ((bArr[57] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[56] & 255);
            Log.d("BJC", "modifyBoot0Freq:checksum:0x" + Integer.toHexString(i5) + " ddrclk:" + Integer.toHexString(i6));
            if (i6 > i) {
                i2 = i5 - (i6 - i);
            } else {
                if (i6 >= i) {
                    return 1;
                }
                i2 = i5 + (i - i6);
            }
            bArr[15] = (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24);
            bArr[14] = (byte) ((i2 & 16711680) >> 16);
            bArr[13] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr[12] = (byte) (i2 & 255);
            bArr[59] = (byte) ((i & ViewCompat.MEASURED_STATE_MASK) >> 24);
            bArr[58] = (byte) ((i & 16711680) >> 16);
            bArr[57] = (byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr[56] = (byte) (i & 255);
            Log.d("BJC", "modifyBoot0Freq:checksum:0x" + Integer.toHexString(i2) + " ddrclk:" + Integer.toHexString(i));
            fileOutputStream.write(bArr, 0, i3);
            fileOutputStream.flush();
            fileInputStream.close();
            fileOutputStream.close();
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static boolean aK() {
        return u.c().contains("CR2");
    }

    public static void a(int i, int i2, int i3, int i4, int i5) {
        if (f219a == null) {
            f219a = u.a();
        }
        f219a.write(40730, 1, 0, "chmod 777 twdataconfig");
        try {
            try {
                Log.d("DeviceInfoUtil", "saveEnhanceLcdValue: color_temperature：" + i + " enhance_saturation:" + i2 + " enhance_contrast:" + i3 + " enhance_brightness:" + i4 + " gamma:" + i5);
                int i6 = (i << 0) | (i2 << 6) | (i3 << 13) | ((i4 << 20) + (i5 << 27));
                byte[] bArr = {(byte) (i6 & 255), (byte) ((65280 & i6) >> 8), (byte) ((16711680 & i6) >> 16), (byte) ((i6 & ViewCompat.MEASURED_STATE_MASK) >> 24)};
                StringBuilder sb = new StringBuilder();
                sb.append("saveEnhanceLcdValue: enhance_lcd_value: ");
                sb.append(Arrays.toString(bArr));
                Log.d("DeviceInfoUtil", sb.toString());
                FileOutputStream fileOutputStream = new FileOutputStream("/twdataconfig/lcd_enhance.bin");
                fileOutputStream.write(bArr, 0, bArr.length);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                new File("/twdataconfig/lcd_enhance.bin").delete();
            }
            FileUtils.setPermissions("/twdataconfig/lcd_enhance.bin", 438, -1, -1);
            f219a.write(40730, 1, 0, "chmod 755 twdataconfig");
            f219a.write(40730, 1, 0, "sync");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static boolean aL() {
        return u.c().startsWith("PH22") && u.c().contains("-GAIN") && u.c().contains("-MIC") && f();
    }

    public static int aM() {
        if (f219a == null) {
            f219a = u.a();
        }
        f219a.write(40730, 1, 0, "chmod 666 /sys/class/disp/disp/attr/enhance_bright_tw");
        int iB = ((b("/sys/class/disp/disp/attr/enhance_bright_tw") - b) * 100) / (c - b);
        if (iB < 0) {
            return 0;
        }
        if (iB > 100) {
            return 100;
        }
        return iB;
    }

    public static int aN() {
        if (f219a == null) {
            f219a = u.a();
        }
        f219a.write(40730, 1, 0, "chmod 666 /sys/class/disp/disp/attr/enhance_contrast_tw");
        int iB = ((b("/sys/class/disp/disp/attr/enhance_contrast_tw") - b) * 100) / (c - b);
        if (iB < 0) {
            return 0;
        }
        if (iB > 100) {
            return 100;
        }
        return iB;
    }

    public static int aO() {
        if (f219a == null) {
            f219a = u.a();
        }
        f219a.write(40730, 1, 0, "chmod 666 /sys/class/disp/disp/attr/enhance_saturation_tw");
        int iB = ((b("/sys/class/disp/disp/attr/enhance_saturation_tw") - b) * 100) / (c - b);
        if (iB < 0) {
            return 0;
        }
        if (iB > 100) {
            return 100;
        }
        return iB;
    }

    public static int aP() {
        if (f219a == null) {
            f219a = u.a();
        }
        f219a.write(40730, 1, 0, "chmod 666 /sys/class/disp/disp/attr/color_temperature_tw");
        return b("/sys/class/disp/disp/attr/color_temperature_tw");
    }

    public static int aQ() {
        return b("/sys/class/tw/av/cameranum");
    }

    public static boolean aR() {
        int iAm = am();
        if (O()) {
            return false;
        }
        if (F() || G()) {
            return (iAm == 0 || iAm == 5 || iAm == 6) ? false : true;
        }
        return true;
    }

    public static boolean aS() {
        return u.c().contains("-CN");
    }

    public static boolean aT() {
        return o() || F() || p() || H() || s() || I() || J();
    }

    public static String aU() {
        if (s()) {
            return "ts18";
        }
        if (o()) {
            return "ts10";
        }
        if (p()) {
            return "ts10s";
        }
        if (n()) {
            return "ts9";
        }
        if (m()) {
            return "ts8";
        }
        if (l()) {
            return "ts7";
        }
        if (B()) {
            return "t3q";
        }
        if (A()) {
            return "t3p";
        }
        if (z()) {
            return "t3o";
        }
        if (x()) {
            return "t3n";
        }
        if (y()) {
            return "t3m";
        }
        if (w()) {
            return "t3l";
        }
        if (c()) {
            return "t7n";
        }
        if (f()) {
            return "t5q";
        }
        if (C()) {
            return "t8p";
        }
        if (D()) {
            return "t8o";
        }
        if (E()) {
            return "t8n";
        }
        if (F()) {
            return "t100";
        }
        if (H()) {
            return "tq919";
        }
        if (G()) {
            return "t9";
        }
        if (I()) {
            return "ts20";
        }
        return J() ? "ts101" : "tw";
    }

    public static boolean aV() {
        return s();
    }

    public static boolean aW() {
        return !TextUtils.isEmpty(SystemProperties.get("ro.com.google.gmsversion", ""));
    }

    public static boolean aX() {
        return p() || s() || o() || H() || F() || G() || I() || J() || a().contains("-NavBar");
    }

    public static boolean aY() {
        if (s() || o() || F() || p() || H()) {
            return true;
        }
        return (f() && !U()) || G() || I() || J();
    }

    public static boolean aZ() {
        if (p() || o() || s() || I() || J() || G() || f()) {
            return true;
        }
        return a().contains("PH") && f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r1v5, types: [boolean] */
    public static String n(Context context) throws Throwable {
        BufferedReader bufferedReader;
        ?? r0 = 0;
        strSubstring = null;
        String strSubstring = null;
        ?? r1 = 0;
        try {
            ?? F = F();
            try {
                if (F != 0 || G()) {
                    bufferedReader = new BufferedReader(new FileReader("/sys/class/sunxi_info/sys_info"));
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.contains("chipid")) {
                            strSubstring = line.substring(line.lastIndexOf(": ") + 2);
                        }
                    }
                } else if (p() || I() || J()) {
                    bufferedReader = new BufferedReader(new FileReader("/sys/firmware/devicetree/base/serial-number"));
                    strSubstring = bufferedReader.readLine();
                } else {
                    bufferedReader = null;
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception unused2) {
                r1 = F;
                if (r1 != 0) {
                    try {
                        r1.close();
                    } catch (Exception unused3) {
                    }
                }
                strSubstring = null;
            } catch (Throwable th) {
                th = th;
                r0 = F;
                if (r0 != 0) {
                    try {
                        r0.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        } catch (Exception unused5) {
        } catch (Throwable th2) {
            th = th2;
        }
        Log.d("DeviceInfoUtil", "chipid = " + strSubstring);
        if (!TextUtils.isEmpty(strSubstring)) {
            return context.getResources().getString(R.string.about_chipid1) + strSubstring;
        }
        return context.getResources().getString(R.string.about_chipid_unknown);
    }

    public static boolean ba() {
        return SystemProperties.getBoolean("persist.tw.fm.send", false) || SystemProperties.getBoolean("persist.tw.bt.send", false);
    }

    public static boolean bb() {
        return (T() || Settings.System.getInt(MyApp.a().getContentResolver(), "resolution_info_visibility", 1) == 0) ? false : true;
    }

    public static int bc() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/sensor_i2c/mic"));
                try {
                    int i = Integer.parseInt(bufferedReader2.readLine());
                    bufferedReader2.close();
                    return i;
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return -1;
                    }
                    bufferedReader.close();
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                return -1;
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void a(TWUtil tWUtil, int i) {
        if (tWUtil != null) {
            tWUtil.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/mic " + i);
        }
    }

    public static boolean b(int i) {
        if (!F()) {
            return true;
        }
        if (i == 5 || i == 8) {
            return au();
        }
        return !(i == 0 || i == 6) || aQ() >= 4;
    }

    public static boolean bd() {
        return o() || p() || s() || F() || G();
    }
}
