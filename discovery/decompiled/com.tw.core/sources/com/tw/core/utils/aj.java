package com.tw.core.utils;

import cn.cardoor.user.bean.Token;
import com.tw.core.c.InterfaceC0032l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
final class aj implements InterfaceC0032l {
    final /* synthetic */ String bx;

    aj(String str) {
        this.bx = str;
    }

    @Override // com.tw.core.c.InterfaceC0032l
    public void dV(Token token) {
        if (token == null || C0057k.am(token.getToken())) {
            return;
        }
        HashMap map = new HashMap();
        map.put("cid", com.tw.core.b.f.gr());
        map.put("flag", this.bx);
        HashMap map2 = new HashMap();
        map2.put("Authorization", token.getToken());
        C0058l.aA(map, map2, com.tw.core.d.a.gW, new ak(this));
    }
}
