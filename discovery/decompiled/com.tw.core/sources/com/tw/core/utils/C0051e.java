package com.tw.core.utils;

import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tw.core.utils.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0051e {
    public static List H(Context context) {
        return Build.VERSION.SDK_INT <= 19 ? J() : I(context);
    }

    public static List I(Context context) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jElapsedRealtime = jCurrentTimeMillis - SystemClock.elapsedRealtime();
            if (C0065s.aa) {
                C0065s.bK("UsageStatsUtils", "开机时间：" + C0066t.bN(jElapsedRealtime, C0066t.ac));
                C0065s.bK("UsageStatsUtils", "当前时间：" + C0066t.bN(jCurrentTimeMillis, C0066t.ac));
            }
            Object systemService = context.getSystemService("usagestats");
            Method declaredMethod = systemService.getClass().getDeclaredMethod("queryUsageStats", Integer.TYPE, Long.TYPE, Long.TYPE);
            declaredMethod.setAccessible(true);
            List list = (List) declaredMethod.invoke(systemService, 0, Long.valueOf(jElapsedRealtime), Long.valueOf(jCurrentTimeMillis));
            Class<?> cls = Class.forName("android.app.usage.UsageStats");
            ArrayList arrayList = new ArrayList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Object obj = list.get(i);
                String str = (String) cls.getDeclaredField("mPackageName").get(obj);
                int iIntValue = ((Integer) cls.getDeclaredField("mLaunchCount").get(obj)).intValue();
                long jLongValue = ((Long) cls.getDeclaredField("mTotalTimeInForeground").get(obj)).longValue() / 1000;
                long jLongValue2 = ((Long) cls.getDeclaredField("mTotalTimeVisible").get(obj)).longValue() / 1000;
                long jLongValue3 = ((Long) cls.getDeclaredField("mLastTimeUsed").get(obj)).longValue();
                long jLongValue4 = ((Long) cls.getDeclaredField("mLastTimeVisible").get(obj)).longValue();
                if (iIntValue > 0 && jLongValue > 0) {
                    arrayList.add(new C0052f(str, jLongValue, iIntValue));
                }
                C0065s.bK("UsageStatsUtils", "getUsageStatsBySystem pName = " + str + ",mLaunchCount = " + iIntValue + ",foregroundTotalTime = " + jLongValue + ",visibleTotalTime = " + jLongValue2 + ",lastTimeUsed = " + C0066t.bN(jLongValue3, C0066t.ac) + ",lastTimeVisible = " + C0066t.bN(jLongValue4, C0066t.ac));
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List J() {
        int i = 0;
        try {
            Object[] objArr = (Object[]) Class.forName("com.android.internal.app.IUsageStats").getMethod("getAllPkgUsageStats", (Class[]) null).invoke(Class.forName("com.android.internal.app.IUsageStats$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("getService", String.class).invoke(null, "usagestats")), (Object[]) null);
            Class<?> cls = Class.forName("com.android.internal.os.PkgUsageStats");
            ArrayList arrayList = new ArrayList();
            while (true) {
                int i2 = i;
                if (i2 >= objArr.length) {
                    return arrayList;
                }
                Object obj = objArr[i2];
                String str = (String) cls.getDeclaredField("packageName").get(obj);
                int i3 = cls.getDeclaredField("launchCount").getInt(obj);
                long j = cls.getDeclaredField("usageTime").getLong(obj) / 1000;
                if (i3 > 0 && j > 0) {
                    arrayList.add(new C0052f(str, j, i3));
                }
                C0065s.bK("UsageStatsUtils", "getUsageStatsBySystem packageName = " + str + ",launchCount = " + i3 + ",usageTime = " + j);
                i = i2 + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
