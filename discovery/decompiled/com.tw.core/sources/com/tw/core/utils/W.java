package com.tw.core.utils;

/* JADX INFO: loaded from: classes.dex */
class W implements InterfaceC0059m {
    final /* synthetic */ C0050d bb;

    W(C0050d c0050d) {
        this.bb = c0050d;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        B.cj("CrashHandlerUtils", "错误日志上报失败 %s", str);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("CrashHandlerUtils", "错误日志上报成功", new Object[0]);
        this.bb.B();
    }
}
