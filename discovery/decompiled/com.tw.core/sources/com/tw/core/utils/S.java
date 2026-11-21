package com.tw.core.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: classes.dex */
public class S {
    public static String dO(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 64).signatures[0].toCharsString().substring(0, 32);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
