package com.tw.core.g;

import com.tw.core.utils.B;
import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
class f implements InterfaceC0059m {
    final /* synthetic */ b iS;

    f(b bVar) {
        this.iS = bVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        B.ci("DailyReportWorker", "onFailure " + str, new Object[0]);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("DailyReportWorker", "log upload success " + str, new Object[0]);
    }
}
