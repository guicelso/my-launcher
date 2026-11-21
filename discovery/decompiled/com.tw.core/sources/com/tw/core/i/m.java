package com.tw.core.i;

import android.util.Base64;
import com.tw.core.utils.C0066t;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class m implements Runnable {
    final /* synthetic */ f jU;
    final /* synthetic */ int jV;
    final /* synthetic */ String jW;

    m(f fVar, int i, String str) {
        this.jU = fVar;
        this.jV = i;
        this.jW = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("time", C0066t.bO() / 1000);
            jSONObject.put("code", this.jV);
            jSONObject.put("id", this.jW);
            this.jU.jC.od(this.jU.oq() + Base64.encodeToString(jSONObject.toString().getBytes(), 8));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
