package com.tw.core.c;

import com.tw.core.utils.C0058l;

/* JADX INFO: loaded from: classes.dex */
class ac implements Runnable {
    final /* synthetic */ C0044x gC;

    ac(C0044x c0044x) {
        this.gC = c0044x;
    }

    @Override // java.lang.Runnable
    public void run() {
        C0058l.au(com.tw.core.d.a.gP + "cid=" + com.tw.core.b.f.gr(), new ad(this));
    }
}
