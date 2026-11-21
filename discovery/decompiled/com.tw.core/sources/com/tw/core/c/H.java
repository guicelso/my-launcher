package com.tw.core.c;

import com.tw.core.utils.C0056j;
import com.tw.core.utils.C0058l;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class H implements Runnable {
    final /* synthetic */ C0023c fQ;
    final /* synthetic */ String fR;
    final /* synthetic */ String fS;
    final /* synthetic */ String fT;

    H(C0023c c0023c, String str, String str2, String str3) {
        this.fQ = c0023c;
        this.fR = str;
        this.fS = str2;
        this.fT = str3;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        String strDr = com.tw.core.utils.M.dr(this.fR);
        com.tw.core.utils.B.ci("AppUploadManager", "APK路径 %s", strDr);
        if (strDr != null) {
            File file = new File(strDr);
            String strAh = C0056j.ah(file);
            HashMap map = new HashMap();
            map.put("catchId", this.fS);
            map.put("key", strAh);
            map.put("cid", com.tw.core.b.f.gr());
            HashMap map2 = new HashMap();
            map2.put("apkFile", file);
            C0058l.ao(this.fT, map, map2, new I(this, this.fR, this.fS));
        }
    }
}
