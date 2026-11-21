package com.tw.core.utils;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tw.core.utils.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0065s {
    public static boolean aa = B.ao;
    private static List ab = new ArrayList();

    public static void bK(String str, String str2) {
        if (aa) {
            Log.e(str, str2);
            H.cW(str, str2);
        }
    }

    public static void bL(String str, Exception exc) {
        if (aa) {
            Log.e(str, Log.getStackTraceString(exc));
        }
    }

    public static void bM(String str, String str2) {
        if (aa) {
            Log.d(str, str2);
        }
    }
}
