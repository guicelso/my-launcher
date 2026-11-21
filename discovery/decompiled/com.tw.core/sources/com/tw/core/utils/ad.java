package com.tw.core.utils;

/* JADX INFO: loaded from: classes.dex */
class ad implements InterfaceC0059m {
    final /* synthetic */ C0061o bp;
    final /* synthetic */ InterfaceC0053g bq;

    ad(C0061o c0061o, InterfaceC0053g interfaceC0053g) {
        this.bp = c0061o;
        this.bq = interfaceC0053g;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        if (C0065s.aa) {
            C0065s.bK(C0061o.O, "requestVersionUpdate onFailure：" + str);
        }
        if (this.bq != null) {
            this.bq.L(str);
        }
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        if (C0065s.aa) {
            C0065s.bK(C0061o.O, "requestVersionUpdate onResponse：" + str);
        }
        com.tw.core.a.a aVarCf = C0071y.cf(str);
        if (this.bq != null) {
            this.bq.K(aVarCf);
        }
    }
}
