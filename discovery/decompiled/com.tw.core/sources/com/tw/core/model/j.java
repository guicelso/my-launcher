package com.tw.core.model;

import com.tw.core.utils.B;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class j implements InterfaceC0059m {
    final /* synthetic */ b hS;

    j(b bVar) {
        this.hS = bVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        B.cj(b.hp, "uploadActivateResult onFailure= %s", str);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
        this.hS.ht.set(false);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci(b.hp, "uploadActivateResult onResponse= %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if ("0".equals(jSONObject.optString("code"))) {
                C0064r.bH(this.hS.hr, "server_activate_time", jSONObject.optJSONObject("data").optLong("activateTime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.hS.ht.set(false);
        }
    }
}
