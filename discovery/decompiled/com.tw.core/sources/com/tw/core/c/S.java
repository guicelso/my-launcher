package com.tw.core.c;

import cn.cardoor.user.bean.Token;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class S implements InterfaceC0059m {
    final /* synthetic */ R gk;
    final /* synthetic */ InterfaceC0032l gl;

    S(R r, InterfaceC0032l interfaceC0032l) {
        this.gk = r;
        this.gl = interfaceC0032l;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        JSONObject jSONObjectOptJSONObject;
        com.tw.core.utils.B.ci("UserManager", "refreshToken onSuccess %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!"0".equals(jSONObject.optString("code")) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                return;
            }
            Token token = new Token();
            token.setToken(jSONObjectOptJSONObject.optString("token")).setExpireTime(jSONObjectOptJSONObject.optInt(com.tw.core.b.e.gh() ? "expiresIn" : "expires_in")).setRefreshToken(jSONObjectOptJSONObject.optString("refresh_token")).setTokenTime(System.currentTimeMillis());
            this.gk.gg.kB(token);
            if (this.gl != null) {
                this.gl.dV(token);
            }
        } catch (Exception e) {
            C0065s.bK("UserManager", "refreshToken " + e.getMessage());
        }
    }
}
