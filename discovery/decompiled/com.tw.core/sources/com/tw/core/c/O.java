package com.tw.core.c;

import com.tw.core.utils.C0047a;
import com.tw.core.utils.C0058l;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class O implements com.tw.core.e.d {
    final /* synthetic */ C0027g gd;

    O(C0027g c0027g) {
        this.gd = c0027g;
    }

    @Override // com.tw.core.e.d
    public void dS(String str) throws Throwable {
        com.tw.core.utils.B.ci("DeviceInfoReportManager", "onBackMcuVersion", new Object[0]);
        Map mapM = C0047a.m(this.gd.em, str);
        if (mapM != null) {
            mapM.remove("initializeLongitude");
            mapM.remove("initializeLatitude");
            mapM.remove("initializeTime");
            C0058l.an(mapM, com.tw.core.d.a.gL, new P(this));
        }
    }
}
