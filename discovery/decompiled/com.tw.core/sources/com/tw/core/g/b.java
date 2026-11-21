package com.tw.core.g;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0057k;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0066t;
import com.tw.core.utils.E;
import com.tw.core.utils.H;
import com.tw.core.utils.J;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class b implements Runnable {
    private volatile boolean iM = false;
    private Context iN;

    public b(Context context) {
        this.iN = context;
    }

    private void nE() {
        this.iM = true;
        HashMap map = new HashMap();
        map.put("cid", com.tw.core.b.f.gr());
        map.put("coreid", com.tw.core.b.b.fQ());
        map.put("params", com.tw.core.b.b.fP());
        C0058l.an(map, com.tw.core.d.a.gX, new e(this));
    }

    private void nF(File file, String str) throws Throwable {
        this.iM = true;
        B.cj("DailyReportWorker", "uploadLog %s", str);
        B.cj("DailyReportWorker", "system info %s", com.tw.core.b.b.fP());
        HashMap map = new HashMap();
        map.put("key", "twcore-log/" + C0066t.bN(System.currentTimeMillis(), C0066t.ac) + "-" + com.tw.core.b.f.gr() + ".txt");
        HashMap map2 = new HashMap();
        map2.put("file", file);
        C0058l.ao(str, map, map2, new f(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void nG(String str) throws Throwable {
        boolean z = !C0057k.am(str);
        if (!J.aJ) {
            C0064r.bA(this.iN, "key_log_status", z);
            C0065s.aa = z;
            B.ao = z;
        }
        File file = new File(E.cz() + File.separator + "log", "twcore.df");
        if (z) {
            if (file.exists()) {
                H.cV();
            }
            nF(file, str);
        } else if (file.exists()) {
            file.delete();
        }
    }

    public void nA() {
        C0048b.p().execute(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.iM) {
            return;
        }
        String strBN = C0066t.bN(System.currentTimeMillis(), C0066t.ai);
        long jBE = C0064r.bE(this.iN, "SP_LAST_REPORT_TIME_MILLIS", -1L);
        String strBN2 = jBE > 0 ? C0066t.bN(jBE, C0066t.ai) : null;
        B.cj("DailyReportWorker", "上次上报的时间：%s 当前的时间：%s", strBN2, strBN);
        if (jBE < 0 || !TextUtils.equals(strBN, strBN2)) {
            nE();
        }
    }
}
