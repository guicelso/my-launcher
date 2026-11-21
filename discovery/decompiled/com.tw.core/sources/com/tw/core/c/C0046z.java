package com.tw.core.c;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tw.core.utils.C0048b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0046z extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String fB = com.tw.core.app.a.iC + "/cloud/collect/bluetooth/phone";
    private Context fA;
    private boolean fw;
    private JSONArray fx = null;
    private boolean fy;
    private A fz;

    public C0046z(Context context) {
        this.fA = context;
        lL();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lK(Intent intent) {
        C0048b.p().execute(new ag(this, intent));
    }

    private void lL() {
        if (this.fz == null) {
            this.fz = new A(this);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.aispeech.action.CONTACTS_SYNC_SUCCESS");
        this.fA.registerReceiver(this.fz, intentFilter);
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("PhoneContactUpload", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            this.fw = jSONObject.optBoolean("collect_on_off");
            this.fy = jSONObject.optBoolean("upload_on_off");
            if (this.fw) {
                return;
            }
            com.tw.core.utils.B.cj("PhoneContactUpload", "注销广播", new Object[0]);
            if (this.fz != null) {
                this.fA.unregisterReceiver(this.fz);
                this.fz = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        com.tw.core.utils.B.ci("PhoneContactUpload", "connected", new Object[0]);
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0307";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return fB;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
