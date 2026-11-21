package com.tw.core.c;

import android.content.Context;
import com.tw.core.utils.C0049c;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0067u;
import com.tw.core.utils.C0072z;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0028h extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String eo = com.tw.core.app.a.iC + "/cloud/receipt/unload";
    private Context en;

    public C0028h(Context context) {
        this.en = context;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("AppDeleteManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            String strOptString = jSONObject.optString("package");
            List listCg = C0072z.cg(jSONObject.optJSONArray("language"), String.class);
            String strOptString2 = jSONObject.optString("system_version_regex");
            String strOptString3 = jSONObject.optString("soft_version_regex");
            if (listCg != null && listCg.size() > 0 && !listCg.contains(C0062p.aV(this.en))) {
                com.tw.core.utils.B.cj("AppDeleteManager", "语言环境不匹配 %s", C0062p.aV(this.en));
                return;
            }
            String strGg = com.tw.core.b.e.gg();
            if (!C0062p.aW(strOptString2) && !Pattern.matches(strOptString2, strGg)) {
                com.tw.core.utils.B.cj("AppDeleteManager", "系统版本不匹配 %s", strGg);
                return;
            }
            String strU = C0049c.u(this.en, strOptString);
            if (strU == null) {
                com.tw.core.utils.B.cj("AppDeleteManager", "包名不存在", new Object[0]);
            } else if (C0062p.aW(strOptString3) || Pattern.matches(strOptString3, strU)) {
                C0067u.bP(this.en, strOptString, new C0029i(this, eVar.jl));
            } else {
                com.tw.core.utils.B.cj("AppDeleteManager", "找不到对应版本 %s", strOptString3);
            }
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
        return "0502";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return eo;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
