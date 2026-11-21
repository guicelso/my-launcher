package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0064r;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0022b extends com.tw.core.i.b implements com.tw.core.h.d, com.tw.core.e.a {
    private static final String dQ = com.tw.core.app.a.iC + "/cloud/collect/card/flow";
    private Context dP;

    public C0022b(Context context) {
        this.dP = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ji(JSONObject jSONObject) {
        oe("0206" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new G(this, jSONObject));
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("SimCardManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            boolean zOptBoolean = jSONObject.optBoolean("change_on_off");
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("check_app_list");
            C0064r.bA(this.dP, "IccidsCollectSwitch", zOptBoolean);
            C0064r.bB(this.dP, "key_sim_card_upload_pkg", jSONArrayOptJSONArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        jh();
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0308";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return dQ;
    }

    public void jh() {
        if (C0064r.by(this.dP, "IccidsCollectSwitch", false)) {
            C0048b.p().execute(new F(this));
        } else {
            com.tw.core.utils.B.cj("SimCardManager", "上报卡信息开关关闭", new Object[0]);
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jj() {
        super.jj();
        com.tw.core.h.a.get().nL(this);
        C0031k.kt().kw(this);
    }

    @Override // com.tw.core.e.a
    public void jk(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        C0064r.bB(this.dP, "all_card_info", null);
        jh();
    }

    @Override // com.tw.core.e.a
    public void jl() {
    }

    @Override // com.tw.core.e.a
    public void jm(boolean z) {
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
        C0031k.kt().kv(this);
    }
}
