package com.tw.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: renamed from: com.tw.core.utils.r, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0064r {
    public static String Z = "DF_ASSISTANT_USER";

    private C0064r() {
        throw new AssertionError();
    }

    public static void bA(Context context, String str, boolean z) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.putBoolean(str, z);
        editorEdit.apply();
    }

    public static void bB(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public static boolean bC(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.putString(str, str2);
        return editorEdit.commit();
    }

    public static int bD(Context context, String str, int i) {
        return context.getSharedPreferences(Z, 0).getInt(str, i);
    }

    public static long bE(Context context, String str, long j) {
        return context.getSharedPreferences(Z, 0).getLong(str, j);
    }

    public static long bF(Context context, String str) {
        return bE(context, str, -1L);
    }

    public static void bG(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.apply();
    }

    public static void bH(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.putLong(str, j);
        editorEdit.apply();
    }

    public static boolean bI(Context context, String str) {
        return by(context, str, false);
    }

    public static void bJ(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(Z, 0).edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public static String bx(Context context, String str) {
        return bz(context, str, null);
    }

    public static boolean by(Context context, String str, boolean z) {
        return context.getSharedPreferences(Z, 0).getBoolean(str, z);
    }

    public static String bz(Context context, String str, String str2) {
        return context.getSharedPreferences(Z, 0).getString(str, str2);
    }
}
