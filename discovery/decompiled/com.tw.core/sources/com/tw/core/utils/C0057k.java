package com.tw.core.utils;

import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;

/* JADX INFO: renamed from: com.tw.core.utils.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0057k {
    public static String al(int i) {
        return DoFunApplication.nu().getResources().getString(i);
    }

    public static boolean am(String str) {
        return TextUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
    }
}
