package com.tw.core.utils;

import android.location.Location;
import com.tw.core.app.DoFunApplication;
import com.tw.core.c.C0040t;
import java.text.SimpleDateFormat;

/* JADX INFO: renamed from: com.tw.core.utils.t, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0066t {
    public static String ac = "yyyy-MM-dd HH:mm:ss";
    public static String ag = "yyyyMMddHHmmss";
    public static String aj = "yyyyMMddHHmm";
    public static String ah = "yyyyMMdd";
    public static String ae = "MM-dd HH:mm";
    public static String ad = "yyyy-MM-dd HH:mm";
    public static String ai = "yyyy-MM-dd";
    public static String af = "yyyy-MM-dd";

    public static String bN(long j, String str) {
        try {
            return new SimpleDateFormat(str).format(Long.valueOf(j));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long bO() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Location locationLe = C0040t.le(DoFunApplication.nu());
        if (locationLe != null) {
            long time = locationLe.getTime();
            if (time >= jCurrentTimeMillis) {
                return time;
            }
            long jAX = C0062p.aX(bN(time, ah));
            long jAX2 = C0062p.aX(bN(jCurrentTimeMillis, ah));
            if (jAX2 > 0 && jAX >= jAX2) {
                return time;
            }
        }
        return jCurrentTimeMillis;
    }
}
