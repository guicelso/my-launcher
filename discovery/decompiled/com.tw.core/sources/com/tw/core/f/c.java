package com.tw.core.f;

import com.tw.core.utils.B;
import com.tw.core.utils.E;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class c extends b implements Runnable {
    private static final String id = E.cz() + "/immediateLog";
    private File ie;

    public c(com.tw.core.a.b bVar) {
        this.ic = bVar;
        nb(10L);
    }

    @Override // com.tw.core.f.b
    public void nd() {
        super.nd();
        if (this.ic != null) {
            f.nh().ni(this);
        }
    }

    @Override // com.tw.core.f.b
    String ne() {
        return id;
    }

    @Override // com.tw.core.f.b
    public void nf() {
        if (this.ie != null) {
            new g(this.ie.getName(), this.ie).nl();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        long j;
        try {
            if (nc()) {
                File file = new File(ne());
                if (E.cK(file)) {
                    String strEl = this.ic.el();
                    if (new File(ne(), strEl).exists()) {
                        B.ci("ImmediateLogCollector", "log exist %s", strEl);
                        return;
                    }
                    try {
                        j = Long.parseLong(this.ic.em());
                    } catch (Exception e) {
                        e.printStackTrace();
                        j = 60;
                    }
                    if (a.mY().na(this.ic.ek(), j * 1000, ne(), strEl)) {
                        this.ie = new File(file.getAbsolutePath(), strEl);
                        nf();
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
