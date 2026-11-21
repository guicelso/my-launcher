package com.tw.core.a;

import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0064r;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private String bK;
    private String bL;
    private String bM;
    private String bN;
    private String time;

    public static b ei(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            b bVar = new b();
            bVar.bL = jSONObject.optString("logId");
            bVar.time = jSONObject.optString("time");
            bVar.bK = jSONObject.optString("instruct");
            bVar.bN = jSONObject.optString("target");
            bVar.bM = jSONObject.optString("logcatScene");
            return bVar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static b ej(String str) {
        return ei(C0064r.bz(DoFunApplication.nu(), str, null));
    }

    public static void en(String str) {
        C0064r.bB(DoFunApplication.nu(), str, null);
    }

    public JSONObject eh() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("logId", this.bL);
            jSONObject.put("time", this.time);
            jSONObject.put("instruct", this.bK);
            jSONObject.put("target", this.bN);
            jSONObject.put("logcatScene", this.bM);
            return jSONObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String ek() {
        return this.bK;
    }

    public String el() {
        return this.bL;
    }

    public String em() {
        return this.time;
    }

    public void eo(String str) {
        JSONObject jSONObjectEh = eh();
        if (jSONObjectEh != null) {
            C0064r.bB(DoFunApplication.nu(), str, jSONObjectEh.toString());
        }
    }

    public void ep(String str) {
        this.bK = str;
    }

    public void eq(String str) {
        this.bL = str;
    }

    public void er(String str) {
        this.bM = str;
    }

    public void es(String str) {
        this.bN = str;
    }

    public void et(String str) {
        this.time = str;
    }

    public String toString() {
        return "LogMessage{logId='" + this.bL + "', time='" + this.time + "', instruct='" + this.bK + "', targetPkg='" + this.bN + "', logcatScene='" + this.bM + "'}";
    }
}
