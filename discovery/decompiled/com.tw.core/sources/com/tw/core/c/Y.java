package com.tw.core.c;

import com.tw.core.utils.C0064r;

/* JADX INFO: loaded from: classes.dex */
class Y implements com.tw.core.h.c {
    final /* synthetic */ C0038r gv;
    final /* synthetic */ String gw;
    final /* synthetic */ int gx;

    Y(C0038r c0038r, String str, int i) {
        this.gv = c0038r;
        this.gw = str;
        this.gx = i;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: lZ, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        com.tw.core.utils.B.ci("AppInstallReportManager", "包名 %s 安装上报状态 %s", this.gw, bool);
        if (bool.booleanValue()) {
            C0064r.bG(this.gv.eS, "key_install_report_cache" + this.gw, 0);
        } else {
            com.tw.core.utils.B.cj("AppInstallReportManager", "上报失败 缓存 %s %s", "key_install_report_cache" + this.gw, Integer.valueOf(this.gx));
            C0064r.bG(this.gv.eS, "key_install_report_cache" + this.gw, this.gx);
        }
    }
}
