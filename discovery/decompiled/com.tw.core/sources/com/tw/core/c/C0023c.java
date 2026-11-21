package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0049c;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0023c extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context dR;

    public C0023c(Context context) {
        this.dR = context;
    }

    private void jp(String str, String str2, String str3) {
        C0048b.p().execute(new H(this, str, str3, str2));
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("AppUploadManager", "emqMessageChanged %s", eVar);
        try {
            String str = eVar.jl;
            JSONObject jSONObject = new JSONObject(eVar.jk);
            String strOptString = jSONObject.optString("package");
            String strU = C0049c.u(this.dR, strOptString);
            String strOptString2 = jSONObject.optString("version");
            com.tw.core.utils.B.ci("AppUploadManager", "车机版本 %s 需要抓取的版本 %s", strU, strOptString2);
            if (strU == null || !TextUtils.equals(strU, strOptString2)) {
                return;
            }
            jp(strOptString, jSONObject.optString("path"), str);
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
        return "0504";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return null;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
