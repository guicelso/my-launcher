package com.tw.core.c;

import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ad implements InterfaceC0059m {
    final /* synthetic */ ac gD;

    ad(ac acVar) {
        this.gD = acVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        try {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "获取service id 结果 %s", str);
            JSONObject jSONObject = new JSONObject(str);
            if ("0".equals(jSONObject.optString("code"))) {
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                this.gD.gC.fk = jSONObjectOptJSONObject.optString("ak");
                this.gD.gC.fs = C0062p.aX(jSONObjectOptJSONObject.optString("serviceid"));
                if (this.gD.gC.fl != null) {
                    this.gD.gC.fl.iK(this.gD.gC.fk);
                    this.gD.gC.fl.iJ(this.gD.gC.fs);
                }
                C0064r.bH(this.gD.gC.fn, "BaiduServiceId", this.gD.gC.fs);
                C0064r.bB(this.gD.gC.fn, "BaiduAK", this.gD.gC.fk);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
