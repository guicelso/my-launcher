package com.tw.core.c;

import android.content.Context;
import android.os.SystemClock;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class D extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context fJ;
    private long fK;
    private boolean fI = false;
    private final long fG = 120000;
    private final long fH = 60000;

    public D(Context context) {
        this.fJ = context;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jE(boolean z) {
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jF() {
        com.tw.core.utils.B.ci("SystemLogUploadManager", "onShutdown", new Object[0]);
        new com.tw.core.f.d().nd();
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jH() {
        com.tw.core.utils.B.ci("SystemLogUploadManager", "onWakeup", new Object[0]);
        this.fK = SystemClock.elapsedRealtime();
        new com.tw.core.f.e(true, true).nd();
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("SystemLogUploadManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            String strOptString = jSONObject.optString("instruct", "-v threadtime");
            String strOptString2 = jSONObject.optString("time", "30");
            String strOptString3 = jSONObject.optString("target", "com.tw.core");
            String strOptString4 = jSONObject.optString("logcatScene", "0");
            com.tw.core.a.b bVar = new com.tw.core.a.b();
            bVar.eq(eVar.jl);
            bVar.ep(strOptString);
            bVar.et(strOptString2);
            bVar.es(strOptString3);
            bVar.er(strOptString4);
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            com.tw.core.utils.B.ci("SystemLogUploadManager", "elapsedTime %s", Long.valueOf(jElapsedRealtime));
            long j = jElapsedRealtime - this.fK;
            com.tw.core.utils.B.ci("SystemLogUploadManager", "wakeupTimeUnitNowInterval %s", Long.valueOf(j));
            if ("0".equals(strOptString4)) {
                new com.tw.core.f.c(bVar).nd();
            } else if ("1".equals(strOptString4)) {
                new com.tw.core.f.d(bVar).nd();
            } else if ("2".equals(strOptString4)) {
                new com.tw.core.f.e(bVar, true, j <= 60000).nd();
            } else if ("3".equals(strOptString4)) {
                new com.tw.core.f.e(bVar, false, jElapsedRealtime <= 120000).nd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        new com.tw.core.f.d().nf();
        new com.tw.core.f.e(true).nf();
        new com.tw.core.f.e(false).nf();
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0703";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return "";
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
        if (SystemClock.elapsedRealtime() <= 120000) {
            new com.tw.core.f.e(false, true).nd();
        }
    }
}
