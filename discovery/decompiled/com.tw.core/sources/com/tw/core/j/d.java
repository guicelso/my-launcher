package com.tw.core.j;

import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0066t;

/* JADX INFO: loaded from: classes.dex */
class d implements com.tw.core.h.c {
    final /* synthetic */ a kk;

    d(a aVar) {
        this.kk = aVar;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: oQ, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        C0065s.bK("AppStatisticsManager", "上传结果：" + bool);
        this.kk.kf.set(false);
        if (bool.booleanValue()) {
            C0064r.bH(DoFunApplication.nu(), "LastTimeUploadAppStatistics", C0066t.bO());
        }
    }
}
