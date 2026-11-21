package com.tw.core.c;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.m, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0033m extends com.tw.core.i.b implements com.tw.core.h.d, com.tw.core.messenger.server.a, com.tw.core.messenger.a.f {
    private Context ey;
    private com.tw.core.model.a ez;

    public C0033m(Context context) {
        this.ey = context;
    }

    @Override // com.tw.core.messenger.server.a
    public void a(Message message) {
        com.tw.core.utils.B.ci("AppMessageManager", "onMessage %s", message);
        if (message.obj instanceof Bundle) {
            Bundle bundle = (Bundle) message.obj;
            String string = bundle.getString("data");
            String string2 = bundle.getString("topic");
            if (string == null || TextUtils.isEmpty(string2)) {
                return;
            }
            com.tw.core.h.b.nN().nO(string.replaceAll("\n", "").replaceAll("\r", ""), string2, com.tw.core.h.b.nN().iV, new U(this, Message.obtain(message)));
        }
    }

    @Override // com.tw.core.messenger.a.f
    public void g(Message message) {
        Bundle data = message.getData();
        if (data != null) {
            int i = data.getInt("code", -1);
            int i2 = data.getInt("status", -1);
            String string = data.getString("taskId");
            String string2 = data.getString("msgId");
            com.tw.core.utils.B.ci("AppMessageManager", "onApply code %s msgId %s status %s", Integer.valueOf(i), string2, Integer.valueOf(i2));
            if ("0700".equals(string2) && i == 0 && i2 >= 0) {
                com.tw.core.f.h.nq(string, i2 == 1);
            }
        }
    }

    @Override // com.tw.core.messenger.server.a
    public int[] getMessageId() {
        return new int[]{9999};
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jB() {
        super.jB();
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jC() {
        super.jC();
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jG() {
        super.jG();
        if (this.ez != null) {
            this.ez.mf();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jH() {
        super.jH();
        if (this.ez != null) {
            this.ez.mg();
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("AppMessageManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            String strOptString = jSONObject.optString("dest");
            String strOptString2 = jSONObject.optString("data");
            if (this.ez != null) {
                this.ez.me(strOptString2, strOptString);
            }
            com.tw.core.messenger.a.b.c().d(this.ey, strOptString, new T(this, strOptString2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        return "9999";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return null;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
        com.tw.core.messenger.a.get().h(this);
    }
}
