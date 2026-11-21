package com.tw.core.f;

import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import com.tw.core.utils.E;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class e extends b implements Runnable {
    private static final String ig = E.cz() + "/bootLog";
    private static final String ih = E.cz() + "/wakeupLog";
    private final boolean ii;
    private final boolean ij;

    public e(com.tw.core.a.b bVar, boolean z, boolean z2) {
        this.ic = bVar;
        this.ii = z2;
        this.ij = z;
        nb(12L);
    }

    public e(boolean z) {
        this(z, false);
    }

    public e(boolean z, boolean z2) {
        this(null, z, z2);
    }

    @Override // com.tw.core.f.b
    public void nd() {
        super.nd();
        if (this.ic != null) {
            if (this.ii) {
                f.nh().ni(this);
                return;
            }
            if (this.ij) {
                B.ci("BootLogCollector", "wakeup log command save", new Object[0]);
            } else {
                B.ci("BootLogCollector", "boot log command save", new Object[0]);
            }
            this.ic.eo(ng());
            return;
        }
        this.ic = com.tw.core.a.b.ej(ng());
        if (this.ij) {
            B.ci("BootLogCollector", "wakeup log command get %s", this.ic);
        } else {
            B.ci("BootLogCollector", "boot log command get %s", this.ic);
        }
        if (this.ic != null) {
            f.nh().ni(this);
        } else {
            B.cj(ng() == null ? "" : ng(), "no message", new Object[0]);
        }
    }

    @Override // com.tw.core.f.b
    String ne() {
        return this.ij ? ih : ig;
    }

    @Override // com.tw.core.f.b
    public void nf() {
        super.nf();
        for (File file : E.cH(new File(ne()), new k(this))) {
            B.ci("BootLogCollector", "want upload file path %s", file.getAbsolutePath());
            new g(file.getName(), file, ng()).nl();
        }
    }

    String ng() {
        return this.ij ? "WakeupLogCollector" : "BootLogCollector";
    }

    @Override // java.lang.Runnable
    public void run() {
        long j;
        try {
            if (nc() && E.cK(new File(ne()))) {
                String strEl = this.ic.el();
                if (new File(ne(), strEl).exists()) {
                    B.ci("BootLogCollector", "log exist %s", strEl);
                    return;
                }
                try {
                    j = Long.parseLong(this.ic.em());
                } catch (Exception e) {
                    e.printStackTrace();
                    j = 60;
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
