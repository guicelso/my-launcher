package com.sprd.engineermode.utils;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: classes4.dex */
public class UiUtils {
    static final String TAG = "UiUtils";

    public static int getResIdByRowCol(Context ctx, String prefix, int row, int col) {
        String resId = prefix + row + col;
        Log.d(TAG, String.format("getResIdByRowCol, ResId=%s, row=%d, col=%d", resId, Integer.valueOf(row), Integer.valueOf(col)));
        return ctx.getResources().getIdentifier(resId, "id", ctx.getPackageName());
    }
}
