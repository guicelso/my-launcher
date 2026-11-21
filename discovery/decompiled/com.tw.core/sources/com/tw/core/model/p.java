package com.tw.core.model;

import com.tw.core.utils.B;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class p implements InterfaceC0059m {
    final /* synthetic */ d hY;

    p(d dVar) {
        this.hY = dVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        this.hY.hM.hI.ks(this.hY.hL, this.hY.hM.hF);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("ThirdPartnerGpsHandler", "location upload %s 点个数 %s url %s", str, Integer.valueOf(this.hY.hL.size()), this.hY.hM.hK);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("code") == 0 || !jSONObject.optBoolean("reupload")) {
                return;
            }
            this.hY.hM.hI.ks(this.hY.hL, this.hY.hM.hF);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
