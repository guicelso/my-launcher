package com.tw.core.c;

import android.content.Context;
import android.os.Handler;
import com.tw.core.utils.C0064r;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0021a extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String dO = com.tw.core.app.a.iC + "/cloud/collect/app/installinfo";
    private Context dN;

    public C0021a(Context context) {
        this.dN = context;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("AppConfigManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("up_service_list");
            JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray("up_interface_list");
            JSONArray jSONArrayOptJSONArray3 = jSONObject.optJSONArray("silent_install_list");
            JSONArray jSONArrayOptJSONArray4 = jSONObject.optJSONArray("report_install_list");
            new Handler().postDelayed(new E(this, jSONObject.optJSONArray("kill_service_list")), 5000L);
            C0064r.bB(this.dN, "startServiceApkName", jSONArrayOptJSONArray == null ? null : jSONArrayOptJSONArray.toString());
            C0064r.bB(this.dN, "startActivityApkName", jSONArrayOptJSONArray2 == null ? null : jSONArrayOptJSONArray2.toString());
            C0064r.bB(this.dN, "installAPKName", jSONArrayOptJSONArray3 == null ? null : jSONArrayOptJSONArray3.toString());
            C0064r.bB(this.dN, "uploadAPKName", jSONArrayOptJSONArray4 != null ? jSONArrayOptJSONArray4.toString() : null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0310";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return dO;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
