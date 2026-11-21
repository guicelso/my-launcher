package com.tw.core.i;

import com.tw.core.utils.C0065s;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
class h implements com.tw.core.h.c {
    final /* synthetic */ b jE;
    final /* synthetic */ AtomicInteger jF;
    final /* synthetic */ List jG;
    final /* synthetic */ String jH;
    final /* synthetic */ List jI;

    h(b bVar, AtomicInteger atomicInteger, List list, String str, List list2) {
        this.jE = bVar;
        this.jF = atomicInteger;
        this.jG = list;
        this.jH = str;
        this.jI = list2;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: oA, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        C0065s.bK("BaseDataReport", "reportOfflineData result = " + bool);
        this.jF.incrementAndGet();
        if (bool.booleanValue()) {
            this.jG.add(this.jH);
        }
        if (this.jF.get() == this.jI.size()) {
            this.jE.kl(this.jG);
            this.jG.clear();
        }
    }
}
