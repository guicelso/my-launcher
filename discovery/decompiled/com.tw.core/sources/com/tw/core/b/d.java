package com.tw.core.b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0065s;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static final String cw = d.class.getSimpleName();

    public static String fX(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean fY(String str) {
        try {
            return (TextUtils.isEmpty(str) || DoFunApplication.nu().getPackageManager().getPackageInfo(str, 0) == null) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            C0065s.bK(cw, str + " 未安装");
        }
    }

    public static String fZ(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        return (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null) ? "unknow" : resolveInfoResolveActivity.activityInfo.packageName;
    }

    public static PackageInfo ga(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PackageInfo gb(String str) {
        return DoFunApplication.nu().getPackageManager().getPackageArchiveInfo(str, 1);
    }

    public static String gc(Context context, String str, String str2) {
        Object obj;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            return (applicationInfo == null || applicationInfo.metaData == null || (obj = applicationInfo.metaData.get(str2)) == null) ? "" : obj.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int gd(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
