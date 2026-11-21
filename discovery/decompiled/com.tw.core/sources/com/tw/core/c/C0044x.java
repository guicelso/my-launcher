package com.tw.core.c;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.File;

/* JADX INFO: renamed from: com.tw.core.c.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0044x extends AbstractC0030j implements com.tw.core.h.f, com.tw.core.h.d, com.tw.core.e.a, com.tw.core.e.c {
    private boolean fj;
    private com.tw.core.track.e fl;
    private com.tw.core.a.f fm;
    private Context fn;
    private String fo;
    private Handler fp;
    private boolean fq;
    private C0040t fr;
    private String fk = null;
    private long fs = 0;

    public C0044x(Context context) {
        this.fn = context;
        this.fm = com.tw.core.a.f.fi(C0064r.bx(this.fn, "NewBaiduConfig"));
    }

    private void lA() {
        if (!this.fq) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "timer is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "stop timer", new Object[0]);
        if (this.fp != null) {
            this.fp.removeCallbacksAndMessages(null);
            this.fp = null;
        }
        this.fq = false;
    }

    private void ls() {
        if (C0062p.aW(lw()) || lx() == 0) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "网络获取service 和 ak", new Object[0]);
            C0048b.p().execute(new ac(this));
        }
    }

    private void lt() {
        if (this.fq) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "timer is start", new Object[0]);
            return;
        }
        if (!lz()) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "can not upload gps", new Object[0]);
        } else {
            if (this.fm == null) {
                return;
            }
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "start timer", new Object[0]);
            this.fp = new ae(this, Looper.getMainLooper());
            this.fp.sendEmptyMessageDelayed(999, 10000L);
            this.fq = true;
        }
    }

    private String lw() {
        if (C0062p.aW(this.fk)) {
            this.fk = C0064r.bx(this.fn, "BaiduAK");
        }
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "返回的ak %s", this.fk);
        return this.fk;
    }

    private long lx() {
        if (this.fs == 0) {
            this.fs = C0064r.bE(this.fn, "BaiduServiceId", 0L);
        }
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "返回的serviceId %s", Long.valueOf(this.fs));
        return this.fs;
    }

    private boolean ly() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.fm != null) {
            return this.fm.fj();
        }
        return false;
    }

    private boolean lz() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.fm != null) {
            return this.fm.fk();
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
        if (C0065s.aa) {
            C0065s.bK("BaiduTrackDataManager", "onConfigChange =" + fVar + ",curConfig=" + this.fm);
        }
        if (fVar == null || fVar.equals(this.fm)) {
            return;
        }
        C0065s.bK("BaiduTrackDataManager", "onConfigChange");
        this.fm = fVar;
        if (ly()) {
            jI();
        } else {
            jJ();
        }
        if (lz()) {
            lt();
        } else {
            lA();
        }
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
        C0065s.bK("BaiduTrackDataManager", "onNetworkChange =" + z);
        if (!z) {
            lA();
        } else {
            lt();
            ls();
        }
    }

    @Override // com.tw.core.e.c
    public void jF() {
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "onShutdown uploadData", new Object[0]);
        if (this.fl != null) {
            this.fl.iM();
        }
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    public void jI() {
        if (!ly()) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "can not collect gps", new Object[0]);
            return;
        }
        if (this.fm == null) {
            return;
        }
        long jFm = this.fm.fm();
        C0065s.bK("BaiduTrackDataManager", "colFreq intervalTime=" + jFm);
        if (this.fl == null) {
            this.fl = new com.tw.core.track.e(this.fn, this, lw(), lx());
        }
        if (this.fr == null) {
            this.fr = new C0040t(this.fn);
            this.fr.lc(this.fl, jFm);
        } else {
            this.fr.ld(jFm);
        }
        this.fl.iN(lz());
        this.fl.iO(this.fm.fn());
        this.fl.iP(this.fm.fo());
        this.fj = true;
    }

    public void jJ() {
        if (!this.fj) {
            com.tw.core.utils.B.ci("BaiduTrackDataManager", "location is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "stop location", new Object[0]);
        if (this.fr != null) {
            this.fr.release();
            this.fr = null;
        }
        this.fj = false;
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected long jT() {
        int iFl = this.fm != null ? this.fm.fl() : 0;
        if (iFl <= 0) {
            iFl = 3;
        }
        return Math.min(iFl * 8 * 1024 * 1024, 26214400L);
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected boolean jV() {
        return true;
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.a.f fVarFi = com.tw.core.a.f.fi(eVar.jk);
        C0065s.bK("BaiduTrackDataManager", "dataCollectConfig =" + fVarFi);
        if (fVarFi != null) {
            C0064r.bB(this.fn, "NewBaiduConfig", eVar.jk);
            jD(fVarFi);
        }
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        if (this.fo == null) {
            this.fo = com.tw.core.utils.E.cz() + File.separator + ".bdgps.df";
        }
        return this.fo;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0306";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        jJ();
        lA();
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
        if (ly()) {
            jI();
        } else {
            jJ();
        }
        if (lz()) {
            lt();
        } else {
            lA();
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public GpsModel jw(String str) {
        return GpsModel.gG(str);
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    public void lr() {
        com.tw.core.utils.B.ci("BaiduTrackDataManager", "refreshAkAndServiceId", new Object[0]);
        this.fk = null;
        this.fs = 0L;
        C0064r.bB(this.fn, "BaiduAK", null);
        C0064r.bH(this.fn, "BaiduServiceId", 0L);
        ls();
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: lu, reason: merged with bridge method [inline-methods] */
    public boolean jM(GpsModel gpsModel) {
        if (gpsModel == null) {
            return false;
        }
        int iAY = C0062p.aY(System.currentTimeMillis(), C0062p.aX(gpsModel.gH()) * 1000);
        int iFl = this.fm != null ? this.fm.fl() : 0;
        if (iFl <= 0) {
            iFl = 3;
        }
        return iAY < iFl;
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: lv, reason: merged with bridge method [inline-methods] */
    public String jL(GpsModel gpsModel) {
        return gpsModel != null ? gpsModel.gF() : "";
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        ls();
        jI();
        com.tw.core.h.a.get().nK(this);
        C0031k.kt().kv(this);
    }
}
