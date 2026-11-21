package com.tw.core.g;

import com.tw.core.utils.C0065s;
import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
class d implements InterfaceC0059m {
    final /* synthetic */ a iP;
    final /* synthetic */ String iQ;

    d(a aVar, String str) {
        this.iP = aVar;
        this.iQ = str;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        C0065s.bK("AppStateWorker", "onFailure " + str + " " + this.iQ);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        try {
            this.iP.nB(str);
        } catch (Exception e) {
            C0065s.bK("AppStateWorker", e.getMessage());
        }
    }
}
