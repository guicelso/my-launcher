package com.tw.core.c;

import android.util.Base64;
import com.tw.core.utils.InterfaceC0053g;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0029i implements InterfaceC0053g {
    private String ep;
    final /* synthetic */ C0028h eq;

    public C0029i(C0028h c0028h, String str) {
        this.eq = c0028h;
        this.ep = str;
    }

    void kh(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", this.ep);
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("time", System.currentTimeMillis() / 1000);
            jSONObject.put("code", i);
            this.eq.oe("0503" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new Q(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.utils.InterfaceC0053g
    /* JADX INFO: renamed from: ki, reason: merged with bridge method [inline-methods] */
    public void L(Integer num) {
        kh(num.intValue());
    }

    @Override // com.tw.core.utils.InterfaceC0053g
    /* JADX INFO: renamed from: kj, reason: merged with bridge method [inline-methods] */
    public void K(String str) {
        kh(1);
    }
}
