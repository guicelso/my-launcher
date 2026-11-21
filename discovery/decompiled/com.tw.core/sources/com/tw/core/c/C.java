package com.tw.core.c;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.utils.C0064r;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class C implements com.tw.core.h.d, com.tw.core.e.c {
    private Context fF;

    public C(Context context) {
        this.fF = context;
        com.tw.core.h.a.get().nK(this);
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
    }

    @Override // com.tw.core.e.c
    public void jF() {
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        try {
            com.tw.core.utils.B.ci("BaiDuMapBlackManager", "emqMessageChanged %s", eVar);
            String strOptString = new JSONObject(eVar.jk).optString("action");
            if (TextUtils.equals("bd_corp_gps_off", strOptString)) {
                C0064r.bA(this.fF, "no_upload_gps_device_key", true);
            } else if (TextUtils.equals("bd_corp_gps_on", strOptString)) {
                C0064r.bA(this.fF, "no_upload_gps_device_key", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0809";
    }

    @Override // com.tw.core.e.c
    public void jj() {
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
    }
}
