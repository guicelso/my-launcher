package com.tw.core.f;

import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import com.tw.core.utils.E;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class d extends b implements Runnable {

    /* JADX INFO: renamed from: if, reason: not valid java name */
    private static final String f0if = E.cz() + "/shutDownLog";

    public d() {
        this(null);
    }

    public d(com.tw.core.a.b bVar) {
        this.ic = bVar;
        nb(12L);
    }

    @Override // com.tw.core.f.b
    public void nd() {
        super.nd();
        if (this.ic != null) {
            this.ic.eo(ng());
            B.ci("ShutDownLogCollector", "shut down log command save", new Object[0]);
            return;
        }
        this.ic = com.tw.core.a.b.ej(ng());
        B.ci("ShutDownLogCollector", "shut down log command get %s", this.ic);
        if (this.ic != null) {
            f.nh().ni(this);
        } else {
            B.cj(ng() == null ? "" : ng(), "no message", new Object[0]);
        }
    }

    @Override // com.tw.core.f.b
    String ne() {
        return f0if;
    }

    @Override // com.tw.core.f.b
    public void nf() {
        super.nf();
        for (File file : E.cH(new File(ne()), new j(this))) {
            new g(file.getName(), file, ng()).nl();
        }
    }

    String ng() {
        return "ShutDownLogCollector";
    }

    @Override // java.lang.Runnable
    public void run() {
        long j;
        try {
            if (nc() && E.cK(new File(ne()))) {
                String strEl = this.ic.el();
                if (new File(ne(), strEl).exists()) {
                    B.ci("ShutDownLogCollector", "log exist %s", strEl);
                    return;
                }
                try {
                    j = Long.parseLong(this.ic.em());
                } catch (Exception e) {
                    e.printStackTrace();
                    j = 120;
                }
                if (a.mY().na(this.ic.ek(), j * 1000, ne(), strEl) && com.tw.core.b.a.fE(DoFunApplication.nu())) {
                    nf();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
