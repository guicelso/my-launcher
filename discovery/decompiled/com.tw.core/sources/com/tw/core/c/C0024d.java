package com.tw.core.c;

import android.content.Context;
import android.util.Base64;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0072z;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0024d extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String dT = com.tw.core.app.a.iC + "/cloud/receipt/cutcard";
    private Context dS;

    public C0024d(Context context) {
        this.dS = context;
    }

    private void jq(String str) {
        C0048b.p().execute(new J(this, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jr(String str, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("time", System.currentTimeMillis() / 1000);
            jSONObject.put("code", i);
            oe("0506" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new K(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("SimCardManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            List listCg = C0072z.cg(jSONObject.optJSONArray("language"), String.class);
            String strOptString = jSONObject.optString("system_version_regex");
            if (listCg != null && listCg.size() > 0 && !listCg.contains(C0062p.aV(this.dS))) {
                com.tw.core.utils.B.cj("SimCardManager", "语言环境不匹配 %s", C0062p.aV(this.dS));
                jr(eVar.jl, 101);
                return;
            }
            String strGg = com.tw.core.b.e.gg();
            if (C0062p.aW(strOptString) || Pattern.matches(strOptString, strGg)) {
                jq(eVar.jl);
            } else {
                com.tw.core.utils.B.cj("SimCardManager", "系统版本不匹配 %s", strGg);
                jr(eVar.jl, 102);
            }
        } catch (Exception e) {
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
        return "0309";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return dT;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
