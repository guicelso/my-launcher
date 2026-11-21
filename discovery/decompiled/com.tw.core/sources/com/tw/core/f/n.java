package com.tw.core.f;

import com.tw.core.utils.B;
import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
final class n implements InterfaceC0059m {
    n() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("LogMessageReporter", "reportReceiveAppLogMessage %s", str);
    }
}
