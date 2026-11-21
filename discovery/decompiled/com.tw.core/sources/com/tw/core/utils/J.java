package com.tw.core.utils;

import android.os.Environment;
import com.tw.core.app.DoFunApplication;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class J {
    public static boolean aI = false;
    public static boolean aJ;

    static {
        aJ = false;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            try {
                JSONObject jSONObject = new JSONObject(E.cL(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DoFun/core"));
                aJ = jSONObject.optBoolean("logEnable");
                C0064r.bH(DoFunApplication.nu(), "activateTime", jSONObject.optLong("activeTime") / 1000);
            } catch (JSONException e) {
            }
        }
    }
}
