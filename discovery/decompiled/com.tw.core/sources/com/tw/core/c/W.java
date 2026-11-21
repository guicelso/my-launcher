package com.tw.core.c;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class W implements com.tw.core.h.c {
    final /* synthetic */ C0036p gs;
    final /* synthetic */ List gt;

    W(C0036p c0036p, List list) {
        this.gs = c0036p;
        this.gt = list;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: lY, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        com.tw.core.utils.B.ci("GpsDataManager", "sendOfflineMsg gpsDatas: %s", bool);
        if (bool.booleanValue()) {
            this.gs.kl(this.gt);
        }
        this.gs.eO.set(true);
    }
}
