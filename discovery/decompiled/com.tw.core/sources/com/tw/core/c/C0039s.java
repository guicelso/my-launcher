package com.tw.core.c;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import cn.cardoor.user.bean.Token;

/* JADX INFO: renamed from: com.tw.core.c.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0039s extends com.tw.core.i.b implements com.tw.core.h.d, com.tw.core.messenger.server.a {
    private Context eV;

    public C0039s(Context context) {
        this.eV = context;
    }

    @Override // com.tw.core.messenger.server.a
    public void a(Message message) {
        com.tw.core.utils.B.ci("UserBindManager", "receiveMessage %s %s current mainland %s", Integer.valueOf(message.what), message.obj, Boolean.valueOf(com.tw.core.b.e.gh()));
        if (!(message.what == 1002 && com.tw.core.b.e.gh()) && (message.what != 1003 || com.tw.core.b.e.gh())) {
            return;
        }
        if (!(message.obj instanceof Bundle)) {
            com.tw.core.utils.B.ci("UserBindManager", "login out", new Object[0]);
            C0031k.kt().kB(null);
            return;
        }
        Bundle bundle = (Bundle) message.obj;
        com.tw.core.utils.B.ci("UserBindManager", "classLoader before %s", bundle.getClassLoader());
        bundle.setClassLoader(Token.class.getClassLoader());
        com.tw.core.utils.B.ci("UserBindManager", "classLoader after %s", bundle.getClassLoader());
        Token token = (Token) bundle.getParcelable("data");
        com.tw.core.utils.B.ci("UserBindManager", "token %s", token);
        C0031k.kt().kB(token);
    }

    @Override // com.tw.core.messenger.server.a
    public int[] getMessageId() {
        return new int[]{1002, 1003};
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("UserBindManager", "emqMessageChanged %s", eVar);
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "1000";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return "";
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jj() {
        super.jj();
        com.tw.core.h.a.get().nL(this);
        com.tw.core.messenger.a.get().j(this);
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
        com.tw.core.messenger.a.get().h(this);
    }
}
