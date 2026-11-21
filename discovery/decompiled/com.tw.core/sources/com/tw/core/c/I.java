package com.tw.core.c;

import com.tw.core.utils.InterfaceC0059m;

/* JADX INFO: loaded from: classes.dex */
class I implements InterfaceC0059m {
    final /* synthetic */ H fU;
    final /* synthetic */ String fV;
    final /* synthetic */ String fW;

    I(H h, String str, String str2) {
        this.fU = h;
        this.fV = str;
        this.fW = str2;
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aI(String str) {
        com.tw.core.utils.B.ci("AppUploadManager", "上传失败 %s", str);
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aJ() {
    }

    @Override // com.tw.core.utils.InterfaceC0059m
    public void aK(String str) {
        com.tw.core.utils.B.ci("AppUploadManager", "上传结果 %s %s %s", str, this.fV, this.fW);
    }
}
