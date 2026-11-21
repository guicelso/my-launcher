package com.tw.core.utils;

import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class B {
    public static String ap = "DFLog";
    public static boolean ao = false;
    private static final String CLASS_NAME = B.class.getName();

    public static void ci(String str, String str2, Object... objArr) {
        if (ao) {
            String strCm = cm(str2, objArr);
            H.cW(str, strCm);
            Log.d(str, strCm);
        }
    }

    public static void cj(String str, String str2, Object... objArr) {
        if (ao) {
            String strCm = cm(str2, objArr);
            H.cW(str, strCm);
            Log.e(str, strCm);
        }
    }

    public static void ck(String str, Object... objArr) {
        if (ao) {
            cj(ap, str, objArr);
        }
    }

    public static void cl(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            cn("Empty/Null json content", new Object[0]);
            return;
        }
        try {
            String strTrim = str2.trim();
            if (strTrim.startsWith("{")) {
                cn(new JSONObject(strTrim).toString(2), new Object[0]);
            } else if (strTrim.startsWith("[")) {
                ci(str, new JSONArray(strTrim).toString(2), new Object[0]);
            } else {
                cj(str, "Invalid Json", new Object[0]);
            }
        } catch (JSONException e) {
            cj(str, "Invalid Json", new Object[0]);
        }
    }

    private static String cm(String str, Object... objArr) {
        String str2;
        if (objArr != null && objArr.length != 0) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (!stackTraceElement.getClassName().equals(CLASS_NAME)) {
                str2 = "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
                return String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), str2, str);
            }
        }
        str2 = "<unknown>";
        return String.format(Locale.US, "thread[%s, %d] %s: %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()), str2, str);
    }

    public static void cn(String str, Object... objArr) {
        if (ao) {
            ci(ap, str, objArr);
        }
    }
}
