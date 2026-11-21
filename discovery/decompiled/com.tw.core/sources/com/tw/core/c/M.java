package com.tw.core.c;

import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class M implements InterfaceC0059m {
    final /* synthetic */ C0026f gb;

    M(C0026f c0026f) {
        this.gb = c0026f;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        this.gb.ed = false;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(str);
            com.tw.core.utils.B.cl("百度注册结果", jSONObject.toString());
            if (jSONObject.optInt("errno") == 0 && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null) {
                this.gb.el = C0062p.aX(jSONObjectOptJSONObject.optString("service_id"));
                this.gb.ee = com.tw.core.utils.R.dN("174bbbc4", jSONObjectOptJSONObject.optString("ak"));
                com.tw.core.utils.B.ci("BaiDuMapDataManager", "解码后ak %s", this.gb.ee);
                if (this.gb.ef != null) {
                    this.gb.ef.iJ(this.gb.el);
                    this.gb.ef.iK(this.gb.ee);
                }
                C0064r.bB(this.gb.eh, "BaiduRecorderAK", this.gb.ee);
                C0064r.bH(this.gb.eh, "BaiduRecorderServiceId", this.gb.el);
                this.gb.ed = true;
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.gb.ed = false;
    }
}
