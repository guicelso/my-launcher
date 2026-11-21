package com.tw.core.c;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.File;

/* JADX INFO: renamed from: com.tw.core.c.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0025e extends AbstractC0030j implements com.tw.core.h.f, com.tw.core.h.d, com.tw.core.e.a, com.tw.core.e.c {
    private boolean dU;
    private com.tw.core.track.b dV;
    private com.tw.core.a.f dW;
    private Context dX;
    private String dY;
    private Handler dZ;
    private boolean ea;
    private C0040t eb;

    public C0025e(Context context) {
        this.dX = context;
        this.dW = com.tw.core.a.f.fi(C0064r.bx(this.dX, "key_ali_config"));
    }

    private boolean jA() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.dW != null) {
            return this.dW.fk();
        }
        return false;
    }

    private void jK() {
        if (!this.ea) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "timer is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("AliTrackDataManager", "stop timer", new Object[0]);
        if (this.dZ != null) {
            this.dZ.removeCallbacksAndMessages(null);
            this.dZ = null;
        }
        this.ea = false;
    }

    private void ju() {
        if (this.ea) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "timer is start", new Object[0]);
            return;
        }
        if (!jA()) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "can not upload gps", new Object[0]);
        } else {
            if (this.dW == null) {
                return;
            }
            com.tw.core.utils.B.ci("AliTrackDataManager", "start timer", new Object[0]);
            this.dZ = new L(this, Looper.getMainLooper());
            this.dZ.sendEmptyMessageDelayed(999, 10000L);
            this.ea = true;
        }
    }

    private boolean jz() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.dW != null) {
            return this.dW.fj();
        }
        return false;
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    public void jD(com.tw.core.a.f fVar) {
        if (com.tw.core.utils.B.ao) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "onConfigChange = %s ,curConfig= %s", fVar, this.dW);
        }
        if (fVar == null || fVar.equals(this.dW)) {
            return;
        }
        C0065s.bK("AliTrackDataManager", "onConfigChange");
        this.dW = fVar;
        if (jz()) {
            jI();
        } else {
            jJ();
        }
        if (jA()) {
            ju();
        } else {
            jK();
        }
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
        C0065s.bK("AliTrackDataManager", "onNetworkChange =" + z);
        if (z) {
            ju();
        } else {
            jK();
        }
    }

    @Override // com.tw.core.e.c
    public void jF() {
        com.tw.core.utils.B.ci("AliTrackDataManager", "onShutdown uploadData", new Object[0]);
        if (this.dV != null) {
            this.dV.ir();
        }
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    public void jI() {
        if (this.dU) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "location is start", new Object[0]);
            return;
        }
        if (!jz()) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "can not collect gps", new Object[0]);
            return;
        }
        if (this.dW == null) {
            return;
        }
        long jFm = this.dW.fm();
        C0065s.bK("AliTrackDataManager", "colFreq intervalTime=" + jFm);
        if (this.dV == null) {
            this.dV = new com.tw.core.track.b(this.dX, this);
        }
        if (this.eb == null) {
            this.eb = new C0040t(this.dX);
            this.eb.lc(this.dV, jFm);
        } else {
            this.eb.ld(jFm);
        }
        this.dV.is(jA());
        this.dV.it(this.dW.fn());
        this.dV.iu(this.dW.fo());
        this.dU = true;
    }

    public void jJ() {
        if (!this.dU) {
            com.tw.core.utils.B.ci("AliTrackDataManager", "location is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("AliTrackDataManager", "stop location", new Object[0]);
        if (this.eb != null) {
            this.eb.release();
            this.eb = null;
        }
        this.dU = false;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.a.f fVarFi = com.tw.core.a.f.fi(eVar.jk);
        com.tw.core.utils.B.ci("AliTrackDataManager", "ali config %s", fVarFi);
        if (fVarFi != null) {
            C0064r.bB(this.dX, "key_gps_ali_url", fVarFi.fp());
            C0064r.bB(this.dX, "key_ali_config", eVar.jk);
            jD(fVarFi);
        }
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        if (this.dY == null) {
            this.dY = com.tw.core.utils.E.cz() + File.separator + ".aligps.df";
        }
        return this.dY;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0314";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        jJ();
        jK();
        com.tw.core.h.a.get().nL(this);
        C0031k.kt().kw(this);
    }

    @Override // com.tw.core.e.a
    public void jk(String str) {
    }

    @Override // com.tw.core.e.a
    public void jl() {
    }

    @Override // com.tw.core.e.a
    public void jm(boolean z) {
        if (jz()) {
            jI();
        } else {
            jJ();
        }
        if (jA()) {
            ju();
        } else {
            jK();
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: jv, reason: merged with bridge method [inline-methods] */
    public boolean jM(GpsModel gpsModel) {
        if (gpsModel == null) {
            return false;
        }
        int iAY = C0062p.aY(System.currentTimeMillis(), C0062p.aX(gpsModel.gH()) * 1000);
        int iFl = this.dW != null ? this.dW.fl() : 0;
        if (iFl <= 0) {
            iFl = 3;
        }
        C0065s.bK("AliTrackDataManager", "cacheDay=" + iFl + ",offectDay=" + iAY);
        return iAY < iFl;
    }

    @Override // com.tw.core.c.AbstractC0030j
    public GpsModel jw(String str) {
        return GpsModel.gG(str);
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: jx, reason: merged with bridge method [inline-methods] */
    public String jL(GpsModel gpsModel) {
        return gpsModel != null ? gpsModel.gF() : "";
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        jI();
        com.tw.core.h.a.get().nK(this);
        C0031k.kt().kv(this);
    }
}
