package com.tw.core.utils;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class aa implements Runnable {
    final /* synthetic */ String bl;
    final /* synthetic */ Map bm;
    final /* synthetic */ Map bn;
    final /* synthetic */ InterfaceC0059m bo;

    aa(String str, Map map, Map map2, InterfaceC0059m interfaceC0059m) {
        this.bl = str;
        this.bm = map;
        this.bn = map2;
        this.bo = interfaceC0059m;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        C0058l.aD(this.bl, this.bm, this.bn, this.bo);
    }
}
