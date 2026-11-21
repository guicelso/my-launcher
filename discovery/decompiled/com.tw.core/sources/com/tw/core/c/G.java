package com.tw.core.c;

import com.tw.core.utils.C0064r;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class G implements com.tw.core.h.c {
    final /* synthetic */ C0022b fO;
    final /* synthetic */ JSONObject fP;

    G(C0022b c0022b, JSONObject jSONObject) {
        this.fO = c0022b;
        this.fP = jSONObject;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: lS, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        if (bool.booleanValue()) {
            C0064r.bB(this.fO.dP, "all_card_info", this.fP.toString());
        }
    }
}
