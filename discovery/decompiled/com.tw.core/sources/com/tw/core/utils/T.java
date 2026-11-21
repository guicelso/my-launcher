package com.tw.core.utils;

import android.content.Context;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class T {
    private Context aU;
    private String aV;

    public T(Context context) {
        this.aU = context;
        this.aV = C0062p.aV(context);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x010a  */
    public void dP() {
        boolean z;
        boolean zGo;
        boolean z2 = false;
        try {
            String strBz = C0064r.bz(this.aU, "key_last_device_cid", null);
            if (TextUtils.isEmpty(strBz)) {
                z = false;
            } else {
                B.ci("ResetUtil", "lastCid %s currentCid %s", strBz, com.tw.core.b.f.gr());
                if (TextUtils.equals(com.tw.core.b.f.gr(), strBz)) {
                    z = false;
                } else {
                    z = true;
                }
            }
            if (z) {
                zGo = false;
            } else {
                zGo = com.tw.core.b.e.go(this.aU);
                B.ci("ResetUtil", "region change %s", Boolean.valueOf(zGo));
            }
            String strAV = C0062p.aV(this.aU);
            B.ci("ResetUtil", "current language %s", strAV);
            if (!z && !zGo) {
                String strBz2 = C0064r.bz(this.aU, "key_gms_version", "-1");
                String strBz3 = C0064r.bz(this.aU, "key_device_language", null);
                B.ci("ResetUtil", "lastGmsVersion %s lastDeviceLanguage %s", strBz2, strBz3);
                if (TextUtils.isEmpty(strBz2)) {
                    if (TextUtils.isEmpty(strBz3)) {
                        if (!"zh_CN".equals(strAV)) {
                            z2 = true;
                        }
                    } else if (!strAV.equals(strBz3) && ("zh_CN".equals(strAV) || "zh_CN".equals(strBz3))) {
                        z2 = true;
                    }
                }
            }
            if (z || zGo || z2) {
                B.ci("ResetUtil", "sp delete", new Object[0]);
                C0064r.bJ(this.aU);
                if (z) {
                    C0064r.bB(this.aU, "key_last_device_cid", strBz);
                    C0064r.bA(this.aU, "key_cid_change", true);
                }
            }
            C0064r.bB(this.aU, "key_gms_version", com.tw.core.b.e.gq());
            C0064r.bB(this.aU, "key_device_language", strAV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dQ(String str) {
        B.ci("ResetUtil", "init language %s new language %s", this.aV, str);
        if (!TextUtils.isEmpty(com.tw.core.b.e.gq()) || TextUtils.equals(this.aV, str)) {
            return;
        }
        if ("zh_CN".equals(this.aV) || "zh_CN".equals(str)) {
            C0064r.bJ(this.aU);
            C0064r.bB(this.aU, "key_device_language", str);
            new Thread(new an(this)).start();
        }
    }
}
