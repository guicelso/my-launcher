package com.tw.core.track;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class c implements Runnable {
    private boolean dk;
    private List dl;
    final /* synthetic */ b dm;

    public c(b bVar, boolean z, List list) {
        this.dm = bVar;
        this.dk = z;
        this.dl = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.dm.iw(this.dk, this.dl);
    }
}
