package com.tw.core.c;

import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.InterfaceC0059m;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class P implements InterfaceC0059m {
    final /* synthetic */ O ge;

    P(O o) {
        this.ge = o;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "周期性上报处理结果 %s", str);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        try {
            com.tw.core.utils.B.ci("DeviceInfoReportManager", "周期性上报处理结果: %s", str);
            if ("0".equals(new JSONObject(C0062p.aT(str)).getString("code"))) {
                C0064r.bH(this.ge.gd.em, "key_report_device_info_time", System.currentTimeMillis());
            }
        } catch (Exception e) {
            C0065s.bL("DeviceInfoReportManager", e);
        }
    }
}
