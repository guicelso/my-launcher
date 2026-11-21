package com.tw.core.utils;

/* JADX INFO: loaded from: classes.dex */
class ae implements InterfaceC0053g {
    final /* synthetic */ C0063q br;

    ae(C0063q c0063q) {
        this.br = c0063q;
    }

    @Override // com.tw.core.utils.InterfaceC0053g
    /* JADX INFO: renamed from: dT, reason: merged with bridge method [inline-methods] */
    public void L(String str) {
        C0065s.bK(C0063q.V, "请求失败：" + str);
    }

    @Override // com.tw.core.utils.InterfaceC0053g
    /* JADX INFO: renamed from: dU, reason: merged with bridge method [inline-methods] */
    public void K(com.tw.core.a.a aVar) {
        C0065s.bK(C0063q.V, "getVersionInfo onFinish:" + aVar);
        this.br.bu(aVar);
    }
}
