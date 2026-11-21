package com.tw.core.track;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class f implements Runnable {
    private boolean dC;
    private List dD;
    final /* synthetic */ e dE;

    public f(e eVar, boolean z, List list) {
        this.dE = eVar;
        this.dC = z;
        this.dD = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.dE.iQ(this.dC, this.dD);
    }
}
