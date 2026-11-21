package com.tw.core.utils;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class Z implements Runnable {
    final /* synthetic */ Map bg;
    final /* synthetic */ Map bh;
    final /* synthetic */ String bi;
    final /* synthetic */ InterfaceC0059m bj;
    final /* synthetic */ boolean bk;

    Z(Map map, Map map2, String str, InterfaceC0059m interfaceC0059m, boolean z) {
        this.bg = map;
        this.bh = map2;
        this.bi = str;
        this.bj = interfaceC0059m;
        this.bk = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0058l.ay().aC("application/json", this.bg, this.bh, this.bi, this.bj, this.bk);
    }
}
