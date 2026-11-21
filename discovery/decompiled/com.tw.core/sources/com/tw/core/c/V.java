package com.tw.core.c;

import com.tw.core.utils.C0065s;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class V implements com.tw.core.h.c {
    final /* synthetic */ C0036p gq;
    final /* synthetic */ List gr;

    V(C0036p c0036p, List list) {
        this.gq = c0036p;
        this.gr = list;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: lX, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        C0065s.bK("GpsDataManager", "sendOnlineMsg :" + bool);
        if (bool.booleanValue()) {
            return;
        }
        this.gq.kr(this.gr);
    }
}
