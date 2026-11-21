package com.tw.core.c;

import android.content.Context;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0064r;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.g, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0027g extends com.tw.core.i.b implements com.tw.core.h.d {
    private Context em;

    public C0027g(Context context) {
        this.em = context;
    }

    private void ke() {
        DoFunApplication.nt().aa(new O(this));
    }

    private void kf(long j) {
        long jBE = C0064r.bE(this.em, "key_report_device_info_time", 0L);
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "lastReportInfoTime %s", Long.valueOf(jBE));
        long jAbs = Math.abs(System.currentTimeMillis() - jBE);
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "period %s", Long.valueOf(jAbs));
        if (jAbs >= 1000 * j) {
            com.tw.core.utils.B.ci("DeviceInfoReportManager", "report info", new Object[0]);
            ke();
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            boolean zOptBoolean = jSONObject.optBoolean("collect_on_off");
            long jOptLong = jSONObject.optLong("collect_frequency", -1L);
            C0064r.bA(this.em, "key_report_device_info_switch", zOptBoolean);
            C0064r.bH(this.em, "key_report_device_info_period", jOptLong);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        super.jc(z, str);
        boolean zBy = C0064r.by(this.em, "key_report_device_info_switch", false);
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "reportInfoSwitch %s", Boolean.valueOf(zBy));
        if (zBy) {
            long jBE = C0064r.bE(this.em, "key_report_device_info_period", -1L);
            com.tw.core.utils.B.ci("DeviceInfoReportManager", "reportInfoPeriod %s", Long.valueOf(jBE));
            if (jBE > 0) {
                kf(jBE);
            }
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0317";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return null;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
