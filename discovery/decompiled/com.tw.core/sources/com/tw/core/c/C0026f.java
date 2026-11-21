package com.tw.core.c;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: renamed from: com.tw.core.c.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0026f extends AbstractC0030j implements com.tw.core.h.f, com.tw.core.h.d, com.tw.core.e.c {
    private static DecimalFormat ec = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA));
    private boolean ed;
    private com.tw.core.track.e ef;
    private com.tw.core.a.f eg;
    private Context eh;
    private String ei;
    private C0040t ej;
    private Handler ek;
    private String ee = null;
    private long el = 0;

    public C0026f(Context context) {
        this.eh = context;
        this.eg = com.tw.core.a.f.fi(C0064r.bx(this.eh, "BaiDuRecorderConfig"));
    }

    private void jO() {
        boolean zFY;
        if (this.ed) {
            com.tw.core.utils.B.cj("BaiDuMapDataManager", "已经注册", new Object[0]);
            return;
        }
        List listFq = this.eg.fq();
        if (listFq != null) {
            Iterator it = listFq.iterator();
            boolean z = false;
            while (true) {
                if (!it.hasNext()) {
                    zFY = z;
                    break;
                }
                String str = (String) it.next();
                zFY = com.tw.core.b.d.fY(str);
                if (zFY) {
                    com.tw.core.utils.B.ci("BaiDuMapDataManager", "包名 %s 存在", str);
                    break;
                }
                z = zFY;
            }
        } else {
            zFY = false;
        }
        if (!zFY) {
            com.tw.core.utils.B.cj("BaiDuMapDataManager", "不存在相关记录仪", new Object[0]);
            return;
        }
        this.ed = true;
        HashMap map = new HashMap();
        map.put("device_no", com.tw.core.b.f.gr());
        map.put("partner_name", "doufeng");
        map.put("device_imei", com.tw.core.b.f.gw(this.eh));
        map.put("channel_maker", "data_co-op");
        map.put("sys_maker", "doufeng");
        map.put("product", com.tw.core.b.b.fI());
        map.put("os_ver", com.tw.core.b.e.gg());
        try {
            map.put("app_ver", this.eh.getPackageManager().getPackageInfo(this.eh.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("authorized", "0");
        map.put("vehicle_type", "9000");
        List listDk = com.tw.core.utils.L.dk();
        if (listDk == null || listDk.size() <= 0) {
            map.put("iccid", "0");
        } else {
            map.put("iccid", ((com.tw.core.a.d) listDk.get(0)).eE());
        }
        map.put("device_ver_no", "0");
        map.put("hardware_maker", "TW");
        map.put("device_dpi", com.tw.core.b.c.fW() + "");
        map.put("total_space", ec.format(com.tw.core.b.e.gi() / 1024.0d) + " GB");
        map.put("free_space", ec.format(((com.tw.core.b.e.gj() / 1024) / 1024) / 1024.0d) + " GB");
        map.put("channel_id", "14159284");
        map.put("flow_plan", "");
        map.put("cpu", com.tw.core.b.e.gk()[0]);
        map.put("cpuz", com.tw.core.b.e.gl());
        com.tw.core.utils.A.ch(map, "99a16577e9bc4ef63d736402174bbbc4");
        C0058l.ap(map, "http://dedc.baidu.com/grey/register", new M(this));
    }

    private void jP() {
        if (this.eg == null || !this.eg.fk() || C0064r.bI(this.eh, "no_upload_gps_device_key") || this.ek != null) {
            return;
        }
        this.ek = new N(this, Looper.getMainLooper());
        this.ek.sendEmptyMessageDelayed(999, 10000L);
    }

    private String jS() {
        String strBz = C0064r.bz(this.eh, "BaiduRecorderAK", null);
        this.ee = strBz;
        return strBz;
    }

    private long jU() {
        long jBE = C0064r.bE(this.eh, "BaiduRecorderServiceId", 0L);
        this.el = jBE;
        return jBE;
    }

    private void jW() {
        if (this.ek != null) {
            this.ek.removeCallbacksAndMessages(null);
            this.ek = null;
        }
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    public void jD(com.tw.core.a.f fVar) {
        if (com.tw.core.utils.B.ao) {
            com.tw.core.utils.B.ci("BaiDuMapDataManager", "onConfigChange = %s curConfig = %s", fVar, this.eg);
        }
        this.eg = fVar;
        jO();
        if (fVar == null || fVar.equals(this.eg)) {
            return;
        }
        com.tw.core.utils.B.ci("BaiDuMapDataManager", "onConfigChange", new Object[0]);
        if (this.eg.fj()) {
            jI();
        } else {
            jJ();
        }
        if (this.eg.fk()) {
            jP();
        } else {
            jW();
        }
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
        if (z) {
            jP();
        } else {
            jW();
        }
    }

    @Override // com.tw.core.e.c
    public void jF() {
        com.tw.core.utils.B.ci("BaiDuMapDataManager", "onShutdown uploadData", new Object[0]);
        if (this.ef != null) {
            this.ef.iM();
        }
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    public void jI() {
        if (this.eg == null || !this.eg.fj() || C0064r.bI(this.eh, "no_upload_gps_device_key")) {
            return;
        }
        long jFm = this.eg.fm();
        C0065s.bK("BaiDuMapDataManager", "colFreq intervalTime=" + jFm);
        if (this.ef == null) {
            this.ef = new com.tw.core.track.e(this.eh, this, jS(), jU(), true);
        }
        if (this.ej == null) {
            this.ej = new C0040t(this.eh);
            this.ej.lc(this.ef, jFm);
        } else {
            this.ej.ld(jFm);
        }
        this.ef.iN(this.eg.fk());
        this.ef.iO(this.eg.fn());
        this.ef.iP(this.eg.fo());
    }

    public void jJ() {
        if (this.ej != null) {
            this.ej.release();
            this.ej = null;
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: jQ, reason: merged with bridge method [inline-methods] */
    public boolean jM(GpsModel gpsModel) {
        if (gpsModel == null) {
            return false;
        }
        int iAY = C0062p.aY(System.currentTimeMillis(), C0062p.aX(gpsModel.gH()) * 1000);
        int iFl = this.eg != null ? this.eg.fl() : 0;
        if (iFl <= 0) {
            iFl = 3;
        }
        if (com.tw.core.utils.B.ao) {
            com.tw.core.utils.B.ci("BaiDuMapDataManager", "cacheDay=%s offectDay=%s", Integer.valueOf(iFl), Integer.valueOf(iAY));
        }
        return iAY < iFl;
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: jR, reason: merged with bridge method [inline-methods] */
    public String jL(GpsModel gpsModel) {
        return gpsModel != null ? gpsModel.gF() : "";
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected long jT() {
        int iFl = this.eg != null ? this.eg.fl() : 0;
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
        com.tw.core.utils.B.ci("BaiDuMapDataManager", "dataCollectConfig = %s", fVarFi);
        if (fVarFi != null) {
            C0064r.bB(this.eh, "BaiDuRecorderConfig", eVar.jk);
            jD(fVarFi);
        }
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        if (this.ei == null) {
            this.ei = com.tw.core.utils.E.cz() + File.separator + ".bdrecordergps.df";
        }
        return this.ei;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0316";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        jJ();
        jW();
        com.tw.core.h.a.get().nL(this);
    }

    @Override // com.tw.core.c.AbstractC0030j
    public GpsModel jw(String str) {
        return GpsModel.gG(str);
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        jI();
        com.tw.core.h.a.get().nK(this);
    }
}
