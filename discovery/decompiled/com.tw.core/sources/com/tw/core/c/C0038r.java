package com.tw.core.c;

import android.content.Context;
import android.util.Base64;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.r, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0038r extends com.tw.core.i.b {
    private static volatile C0038r eT;
    private static final String eU = com.tw.core.app.a.iC + "/cloud/collect/app/installinfo";
    private Context eS;

    public C0038r(Context context) {
        this.eS = context;
    }

    public static C0038r kZ(Context context) {
        if (eT == null) {
            synchronized (C0038r.class) {
                if (eT == null) {
                    eT = new C0038r(context);
                }
            }
        }
        return eT;
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        for (String str2 : C0062p.bj(this.eS)) {
            int iBD = C0064r.bD(this.eS, "key_install_report_cache" + str2, 0);
            com.tw.core.utils.B.ci("AppInstallReportManager", "%s 上报状态 %s", str2, Integer.valueOf(iBD));
            if (iBD != 0) {
                la(iBD, str2);
            }
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return eU;
    }

    public void la(int i, String str) {
        String[] strArrCs;
        try {
            String strBm = C0062p.bm(this.eS, str);
            int iBl = C0062p.bl(this.eS, str);
            String str2 = "0";
            String str3 = "0";
            if (!"dofunsoftpackage".equals(str) && (strArrCs = com.tw.core.utils.C.cs(str)) != null && strArrCs.length == 2) {
                str2 = strArrCs[0];
                str3 = strArrCs[1];
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("package", str);
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("code", i);
            jSONObject.put("plat", C0062p.bc());
            jSONObject.put("oldVersion", str2);
            jSONObject.put("oldFlag", str3);
            jSONObject.put("newVersion", strBm);
            jSONObject.put("newFlag", iBl);
            oe("0207" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new Y(this, str, i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
