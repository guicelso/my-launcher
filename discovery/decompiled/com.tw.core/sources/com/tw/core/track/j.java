package com.tw.core.track;

import com.tw.core.c.C0044x;
import com.tw.core.utils.C0065s;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class j implements com.tw.core.track.model.b {
    final /* synthetic */ e dI;
    final /* synthetic */ d dJ;
    final /* synthetic */ long dK;
    final /* synthetic */ boolean dL;
    final /* synthetic */ List dM;

    j(e eVar, d dVar, long j, boolean z, List list) {
        this.dI = eVar;
        this.dJ = dVar;
        this.dK = j;
        this.dL = z;
        this.dM = list;
    }

    @Override // com.tw.core.track.model.b
    public void hf(g gVar) {
        this.dJ.iI(this.dK);
        if (!this.dL && this.dI.dx != null) {
            this.dI.dx.kr(this.dM);
        }
        if (gVar.hi() == 201 && (this.dI.dx instanceof C0044x)) {
            this.dI.dt = null;
            this.dI.dA = 0L;
            ((C0044x) this.dI.dx).lr();
        }
        C0065s.bK(this.dI.dp, "发送失败: " + gVar.hh() + ", failedCounter : " + e.dq.incrementAndGet() + ", " + gVar);
    }

    @Override // com.tw.core.track.model.b
    public void hg(long j) {
        this.dJ.iI(this.dK);
        if (this.dL && this.dI.dx != null) {
            this.dI.dx.kl(this.dM);
        }
        C0065s.bK(this.dI.dp, "发送成功: " + j + ", successCounter : " + e.dB.incrementAndGet());
    }
}
