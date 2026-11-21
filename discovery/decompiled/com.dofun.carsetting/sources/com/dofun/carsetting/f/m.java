package com.dofun.carsetting.f;

import android.content.Context;

/* JADX INFO: compiled from: ResourceUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class m {
    public static int a(Context context, String str) {
        return a(context, str, "id");
    }

    public static int b(Context context, String str) {
        return a(context, str, "drawable");
    }

    public static int c(Context context, String str) {
        return a(context, str, "array");
    }

    private static int a(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, context.getPackageName());
    }
}
