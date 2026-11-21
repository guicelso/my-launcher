package com.sprd.engineermode.gcf;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes4.dex */
public class SharepreferenceUtils {
    private static final String TAG = "SharepreferenceUtils";

    public static void saveInfo(Context context, String keyrName, String value) {
        SharedPreferences sp = context.getSharedPreferences("gcf_info", 0);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keyrName, value);
        editor.commit();
    }

    public static String getInfo(Context context, String keyrName) {
        SharedPreferences sp = context.getSharedPreferences("gcf_info", 0);
        String content = sp.getString(keyrName, "");
        return content;
    }
}
