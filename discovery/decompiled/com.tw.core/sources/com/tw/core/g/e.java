package com.tw.core.g;

import com.tw.core.utils.B;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class e implements InterfaceC0059m {
    final /* synthetic */ b iR;

    e(b bVar) {
        this.iR = bVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        C0065s.bK("DailyReportWorker", "onFailure " + str + " " + com.tw.core.d.a.gX);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
        this.iR.iM = false;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) throws Throwable {
        try {
            C0064r.bH(this.iR.iN, "SP_LAST_REPORT_TIME_MILLIS", System.currentTimeMillis());
            JSONObject jSONObject = new JSONObject(str);
            B.ci("DailyReportWorker", "daily report result %s", jSONObject.toString());
            if ("0".equals(jSONObject.optString("code"))) {
                this.iR.nG(jSONObject.optString("data"));
            }
        } catch (Exception e) {
            C0065s.bK("DailyReportWorker", e.getMessage());
            this.iR.iM = false;
        }
    }
}
