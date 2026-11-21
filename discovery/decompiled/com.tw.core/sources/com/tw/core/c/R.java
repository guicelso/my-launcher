package com.tw.core.c;

import com.tw.core.utils.C0058l;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class R implements Runnable {
    final /* synthetic */ C0031k gg;
    final /* synthetic */ String gh;
    final /* synthetic */ HashMap gi;
    final /* synthetic */ InterfaceC0032l gj;

    R(C0031k c0031k, String str, HashMap map, InterfaceC0032l interfaceC0032l) {
        this.gg = c0031k;
        this.gh = str;
        this.gi = map;
        this.gj = interfaceC0032l;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0058l.av(this.gh, this.gi, new S(this, this.gj));
    }
}
