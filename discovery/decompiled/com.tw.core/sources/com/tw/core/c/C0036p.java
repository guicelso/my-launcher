package com.tw.core.c;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0036p extends AbstractC0030j implements InterfaceC0041u, com.tw.core.h.d, com.tw.core.messenger.server.a, com.tw.core.e.a, com.tw.core.e.c {
    private boolean eE;
    private com.tw.core.a.f eG;
    private Context eH;
    private boolean eI;
    private String eJ;
    private Handler eL;
    private boolean eM;
    private C0040t eN;
    private boolean eP;
    private List eF = new ArrayList();
    private List eK = new ArrayList();
    private AtomicBoolean eO = new AtomicBoolean();
    private boolean eQ = true;

    public C0036p(Context context) {
        this.eH = context;
        this.eG = com.tw.core.a.f.fi(C0064r.bx(this.eH, "DefaultDoFunConfig"));
    }

    private void kI(boolean z) {
        ArrayList arrayList = new ArrayList(this.eK);
        this.eK.clear();
        if (z) {
            GpsModel gpsModelGI = GpsModel.gI(this.eH, C0040t.le(this.eH));
            com.tw.core.utils.B.ci("GpsDataManager", "shutdown last point %s", gpsModelGI);
            if (gpsModelGI != null) {
                String strBz = C0064r.bz(this.eH, "key_last_shut_down_point_time", null);
                String strGH = gpsModelGI.gH();
                com.tw.core.utils.B.ci("GpsDataManager", "lastShutDownPointTime %s shutDownPointTime %s", strBz, strGH);
                if (!TextUtils.equals(strBz, strGH)) {
                    arrayList.add(gpsModelGI);
                    C0064r.bB(this.eH, "key_last_shut_down_point_time", strGH);
                }
            }
        }
        JSONArray jSONArrayKP = kP(arrayList);
        com.tw.core.utils.B.ci("GpsDataManager", "online上传的gps %s", jSONArrayKP.toString());
        com.tw.core.h.b.nN().nU("0199" + Base64.encodeToString(jSONArrayKP.toString().getBytes(), 8), new V(this, arrayList));
    }

    private void kK() {
        if (this.eM) {
            com.tw.core.utils.B.ci("GpsDataManager", "timer is start", new Object[0]);
            return;
        }
        if (!kR()) {
            com.tw.core.utils.B.ci("GpsDataManager", "can not upload gps", new Object[0]);
            return;
        }
        if (this.eG == null) {
            return;
        }
        com.tw.core.utils.B.ci("GpsDataManager", "start timer", new Object[0]);
        HandlerThread handlerThread = new HandlerThread("handle_cache_point");
        handlerThread.start();
        this.eL = new X(this, handlerThread.getLooper());
        this.eL.sendEmptyMessageDelayed(9999, 10000L);
        this.eM = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean kM() {
        if (C0065s.aa) {
            C0065s.bK("GpsDataManager", "checkAgreeUpload mCanUpload= " + kR() + ",NetworkInfoTools.isAvailable " + com.tw.core.h.b.nN().isConnected() + ",uid " + C0031k.kt().ku());
        }
        if (com.tw.core.h.b.nN().isConnected() && this.eG != null && kQ()) {
            return (this.eG.fn() && TextUtils.isEmpty(C0031k.kt().ku())) ? false : true;
        }
        return false;
    }

    private JSONArray kP(List list) {
        JSONArray jSONArray = new JSONArray();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(((GpsModel) it.next()).gJ());
        }
        return jSONArray;
    }

    private boolean kQ() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.eG != null) {
            return this.eG.fj();
        }
        return false;
    }

    private boolean kR() {
        if (C0031k.kt().kx()) {
            return true;
        }
        if (this.eG != null) {
            return this.eG.fk();
        }
        return false;
    }

    private void kS() {
        if (this.eF == null || this.eF.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.eF);
        this.eF.clear();
        kr(arrayList);
    }

    private void kT(GpsModel gpsModel) {
        if (!kM()) {
            this.eF.add(gpsModel);
            if (this.eF.size() >= 5) {
                kS();
            }
            C0065s.bK("GpsDataManager", "sendOnlineMsg 上传开关关闭");
            return;
        }
        this.eK.add(gpsModel);
        com.tw.core.utils.B.cj("GpsDataManager", "mGpsList.size %s", Integer.valueOf(this.eK.size()));
        if (this.eK.size() >= this.eG.fo()) {
            kI(false);
        }
    }

    private void kU() {
        if (kM()) {
            kI(true);
        }
    }

    private void kV() {
        if (!this.eM) {
            com.tw.core.utils.B.ci("GpsDataManager", "timer is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("GpsDataManager", "stop timer", new Object[0]);
        if (this.eL != null) {
            this.eL.removeCallbacksAndMessages(null);
            this.eL = null;
        }
        this.eM = false;
    }

    private void kW() {
        if (C0065s.aa) {
            C0065s.bK("GpsDataManager", "uploadCarLine mUploadCarLineLoading：" + this.eP + ",mCurrentDayUploadFinish:" + this.eI);
        }
        if (this.eI || this.eP || !com.tw.core.b.a.fE(this.eH)) {
            return;
        }
        String strBe = C0062p.be();
        String strBx = C0064r.bx(this.eH, "LastUploadOnlineTime");
        if (C0065s.aa) {
            C0065s.bK("GpsDataManager", "uploadCarLine curDate：" + strBe + "-previousDate:" + strBx);
        }
        if (TextUtils.equals(strBe, strBx)) {
            this.eI = true;
            return;
        }
        this.eI = false;
        Location locationLe = C0040t.le(this.eH);
        if (C0065s.aa) {
            C0065s.bK("GpsDataManager", "uploadCarLine location：" + locationLe);
        }
        if (locationLe != null) {
            this.eP = true;
        }
    }

    @Override // com.tw.core.messenger.server.a
    public void a(Message message) {
        Message messageObtain = Message.obtain(message);
        List listKp = kp(1);
        messageObtain.arg1 = (listKp == null || listKp.size() <= 0) ? 0 : 1;
        try {
            message.replyTo.send(messageObtain);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.messenger.server.a
    public int[] getMessageId() {
        return new int[]{1000};
    }

    @Override // com.tw.core.c.InterfaceC0041u
    public void iB(Location location) {
        C0065s.bK("GpsDataManager", "onLocation getLatitude=" + location.getLatitude() + ",getLongitude" + location.getLongitude() + ",getAltitude" + location.getAltitude() + ",getAccuracy" + location.getAccuracy() + ",getBearing" + location.getBearing() + ",getSpeed" + location.getSpeed() + ",getTime" + location.getTime());
        kT(GpsModel.gT(location, kO(location.getTime())));
        if (this.eQ) {
            this.eQ = false;
            if (TextUtils.isEmpty(C0064r.bx(this.eH, "mFirstLocation"))) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("gpsx", location.getLatitude());
                    jSONObject.put("gpsy", location.getLongitude());
                    jSONObject.put("gpsh", location.getAltitude());
                    jSONObject.put("gpst", location.getTime() / 1000);
                    jSONObject.put("gpsp", location.getAccuracy());
                    jSONObject.put("gpss", location.getSpeed());
                    C0064r.bB(this.eH, "mFirstLocation", jSONObject.toString());
                } catch (Exception e) {
                    C0065s.bL("GpsDataManager", e);
                }
            }
        }
        kW();
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    public void jD(com.tw.core.a.f fVar) {
        if (C0065s.aa) {
            C0065s.bK("GpsDataManager", "onConfigChange =" + fVar + ",curConfig=" + this.eG);
        }
        if (fVar == null || fVar.equals(this.eG)) {
            return;
        }
        C0065s.bK("GpsDataManager", "onConfigChange");
        this.eG = fVar;
        if (kQ()) {
            jI();
        } else {
            jJ();
        }
        if (kR()) {
            kK();
        } else {
            kV();
        }
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
        C0065s.bK("GpsDataManager", "onNetworkChange = " + z);
        if (z) {
            kK();
        } else {
            kV();
        }
    }

    @Override // com.tw.core.e.c
    public void jF() {
        kU();
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    public void jI() {
        if (!kQ()) {
            com.tw.core.utils.B.ci("GpsDataManager", "can not collect gps", new Object[0]);
            return;
        }
        if (this.eG == null) {
            return;
        }
        long jFm = this.eG.fm();
        C0065s.bK("GpsDataManager", "startLocation colFreq=" + jFm);
        if (this.eN == null) {
            this.eN = new C0040t(this.eH);
            this.eN.lc(this, jFm);
        } else {
            this.eN.ld(jFm);
        }
        this.eE = true;
    }

    public void jJ() {
        if (!this.eE) {
            com.tw.core.utils.B.ci("GpsDataManager", "location is stop", new Object[0]);
            return;
        }
        com.tw.core.utils.B.ci("GpsDataManager", "stop location", new Object[0]);
        if (this.eN != null) {
            this.eN.release();
            this.eN = null;
        }
        this.eE = false;
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected long jT() {
        int iFl = this.eG != null ? this.eG.fl() : 0;
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
        C0065s.bK("GpsDataManager", "emqMessageChanged =" + eVar);
        com.tw.core.a.f fVarFi = com.tw.core.a.f.fi(eVar.jk);
        C0065s.bK("GpsDataManager", "dataCollectConfig =" + fVarFi);
        if (fVarFi != null) {
            C0064r.bB(this.eH, "DefaultDoFunConfig", eVar.jk);
            jD(fVarFi);
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        if (this.eJ == null) {
            this.eJ = com.tw.core.utils.E.cz() + File.separator + ".newGps.df";
        }
        return this.eJ;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0305";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        kV();
        jJ();
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
        if (kQ()) {
            jI();
        } else {
            jJ();
        }
        if (kR()) {
            kK();
        } else {
            kV();
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public GpsModel jw(String str) {
        return GpsModel.he(str);
    }

    public void kJ(List list) {
        com.tw.core.utils.B.ci("GpsDataManager", "sendOfflineMsg gpsDatas: %s", list);
        this.eO.set(false);
        if (list == null || list.size() <= 0) {
            return;
        }
        JSONArray jSONArrayKP = kP(list);
        com.tw.core.utils.B.ci("GpsDataManager", "sendOfflineMsg JsonArray: %s", jSONArrayKP.toString());
        com.tw.core.h.b.nN().nU("0199" + Base64.encodeToString(jSONArrayKP.toString().getBytes(), 8), new W(this, list));
        do {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!this.eO.get());
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: kL, reason: merged with bridge method [inline-methods] */
    public boolean jM(GpsModel gpsModel) {
        if (gpsModel == null) {
            return false;
        }
        int iAY = C0062p.aY(System.currentTimeMillis(), C0062p.aX(gpsModel.gH()) * 1000);
        int iFl = this.eG != null ? this.eG.fl() : 0;
        if (iFl <= 0) {
            iFl = 3;
        }
        C0065s.bK("GpsDataManager", "cacheDay=" + iFl + ",offectDay=" + iAY);
        return iAY < iFl;
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: kN, reason: merged with bridge method [inline-methods] */
    public String jL(GpsModel gpsModel) {
        return gpsModel != null ? gpsModel.gJ() : "";
    }

    public long kO(long j) {
        long jBE;
        if (Math.abs(j - C0064r.bE(this.eH, "KeyTrackTime", 0L)) < 600000) {
            jBE = C0064r.bE(this.eH, "KeyTrackId", 0L);
        } else {
            C0064r.bH(this.eH, "KeyTrackId", j);
            jBE = j;
        }
        C0064r.bH(this.eH, "KeyTrackTime", j);
        return jBE / 1000;
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        jI();
        com.tw.core.h.a.get().nK(this);
        com.tw.core.messenger.a.get().h(this);
        C0031k.kt().kv(this);
    }
}
