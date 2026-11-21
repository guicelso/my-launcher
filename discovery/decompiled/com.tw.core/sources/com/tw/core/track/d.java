package com.tw.core.track;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static volatile d dn;

    /* JADX INFO: renamed from: do, reason: not valid java name */
    public Map f1do = null;

    private d() {
    }

    public static d iF() {
        if (dn == null) {
            synchronized (d.class) {
                if (dn == null) {
                    dn = new d();
                    dn.iG();
                }
            }
        }
        return dn;
    }

    public void iD(h hVar) {
        com.tw.core.track.a.b.ib(hVar);
    }

    public void iE(a aVar) {
        com.tw.core.track.a.b.ic(aVar);
    }

    public void iG() {
        this.f1do = new HashMap();
    }

    public void iH(long j, com.tw.core.track.model.b bVar) {
        synchronized (d.class) {
            this.f1do.put(Long.valueOf(j), bVar);
        }
    }

    public void iI(long j) {
        synchronized (d.class) {
            if (this.f1do != null && this.f1do.containsKey(Long.valueOf(j))) {
                this.f1do.remove(Long.valueOf(j));
            }
        }
    }
}
