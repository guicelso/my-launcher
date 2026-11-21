package com.tw.core.c;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class B extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String fE = com.tw.core.app.a.iC + "/cloud/request/imei";
    private Context fD;

    public B(Context context) {
        this.fD = context;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("ImeiWriteManager", "emqMessageChanged %s", eVar);
        try {
            String strOptString = new JSONObject(eVar.jk).optString("imei");
            if (TextUtils.isEmpty(strOptString)) {
                return;
            }
            if (TextUtils.equals(com.tw.core.b.f.gw(this.fD), strOptString) || strOptString.length() != 15) {
                com.tw.core.utils.B.ci("ImeiWriteManager", "车机的imei为 %s", com.tw.core.b.f.gw(this.fD));
            } else {
                com.tw.core.utils.B.ci("ImeiWriteManager", "设置的imei %s", strOptString);
                SystemProperties.set("persist.forceimei", strOptString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        C0065s.bK("ImeiWriteManager", "connected " + str);
        boolean zBy = C0064r.by(this.fD, "key_request_imei_state", false);
        C0065s.bK("ImeiWriteManager", "请求imei状态 " + zBy);
        if (zBy) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("plat", com.tw.core.b.b.fI());
            jSONObject.put("imei", com.tw.core.b.f.gw(this.fD));
            oe("0204" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new ai(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0403";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return fE;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
