package com.dofun.carsetting.f;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: compiled from: PreferencesUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static String f227a = "DF_ASSISTANT_USER";

    public static void a(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f227a, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public static String b(Context context, String str, String str2) {
        return context.getSharedPreferences(f227a, 0).getString(str, str2);
    }

    public static void a(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(f227a, 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.apply();
    }

    public static int b(Context context, String str, int i) {
        return context.getSharedPreferences(f227a, 0).getInt(str, i);
    }
}
