package com.tw.core.track;

import com.tw.core.utils.B;
import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
class i implements InterfaceC0059m {
    final /* synthetic */ b dH;

    i(b bVar) {
        this.dH = bVar;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        B.ci("AliBatchListener", "ali report result %s", str);
    }
}
