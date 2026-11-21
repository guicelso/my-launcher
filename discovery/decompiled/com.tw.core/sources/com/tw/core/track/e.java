package com.tw.core.track;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import com.tw.core.c.AbstractC0030j;
import com.tw.core.c.C0031k;
import com.tw.core.c.InterfaceC0041u;
import com.tw.core.track.model.CoordType;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0065s;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes.dex */
public class e implements InterfaceC0041u {
    private long dA;
    private String dp;
    private List dr;
    private boolean ds;
    private String dt;
    private boolean du;
    private boolean dv;
    private Context dw;
    private AbstractC0030j dx;
    private int dy;
    private static AtomicLong dz = new AtomicLong();
    private static AtomicInteger dB = new AtomicInteger();
    private static AtomicInteger dq = new AtomicInteger();

    public e(Context context, AbstractC0030j abstractC0030j, String str, long j) {
        this(context, abstractC0030j, str, j, false);
    }

    public e(Context context, AbstractC0030j abstractC0030j, String str, long j, boolean z) {
        this.dp = "BaiduLbsBatchListener";
        this.dt = null;
        this.dA = 0L;
        this.dr = new ArrayList();
        this.dy = 30;
        this.dx = abstractC0030j;
        this.dw = context;
        this.dt = str;
        this.dA = j;
        this.ds = z;
        this.dp = "BaiduLbsBatchListener-" + this.dA;
    }

    private boolean iR() {
        if (C0065s.aa) {
            C0065s.bK(this.dp, "checkAgreeUpload mCanUpload=" + this.dv + ",NetworkInfoTools.isAvailable(mContext=" + com.tw.core.b.a.fE(this.dw) + ",getAk=" + this.dt + ",getServiceId=" + this.dA + ",mBindUpload=" + this.du + ",bind=" + ((this.du && TextUtils.isEmpty(C0031k.kt().ku())) ? false : true));
        }
        if (!this.dv || !com.tw.core.b.a.fE(this.dw) || C0062p.aW(this.dt) || this.dA == 0) {
            return false;
        }
        return (this.du && TextUtils.isEmpty(C0031k.kt().ku())) ? false : true;
    }

    @Override // com.tw.core.c.InterfaceC0041u
    public void iB(Location location) {
        GpsModel gpsModelGI = GpsModel.gI(this.dw, location);
        B.ci(this.dp, "onLocation gpsModel %s", gpsModelGI.toString());
        iS(gpsModelGI);
    }

    public void iJ(long j) {
        this.dA = j;
        this.dp = "BaiduLbsBatchListener-" + this.dA;
    }

    public void iK(String str) {
        this.dt = str;
    }

    public void iL() {
        C0065s.bK(this.dp, "============================进入缓存数据分发百度监听器============================");
        if (this.dx == null || !iR()) {
            return;
        }
        List<List> listIi = com.tw.core.track.b.a.ii(this.dx.kp(1000), 100);
        C0065s.bK(this.dp, "============================进入缓存数据分发百度监听器cachePointHandle============================" + (listIi != null ? Integer.valueOf(listIi.size()) : null));
        if (listIi == null || listIi.size() <= 0) {
            return;
        }
        for (List list : listIi) {
            if (C0065s.aa) {
                C0065s.bK(this.dp, "======================cachePointHandle，进入发送数据=====================" + list.size());
            }
            try {
                C0048b.p().execute(new f(this, true, list));
            } catch (Exception e) {
                e.printStackTrace();
                if (C0065s.aa) {
                    C0065s.bK(this.dp, "cachePointHandle 发送数据到百度出错>" + e + "============>" + list.toString());
                }
            }
        }
    }

    public void iM() {
        if (this.dr.size() > 0) {
            ArrayList arrayList = new ArrayList(this.dr);
            this.dr.clear();
            if (!iR()) {
                C0065s.bK(this.dp, "======================checkAgreeUpload 不滿足條件========");
                this.dx.kr(arrayList);
                return;
            }
            try {
                C0048b.p().execute(new f(this, false, arrayList));
            } catch (Exception e) {
                e.printStackTrace();
                this.dx.kr(arrayList);
                if (C0065s.aa) {
                    C0065s.bK(this.dp, "nowUpload发送数据到百度出错>" + e + "============>" + arrayList.toString());
                }
            }
        }
    }

    public void iN(boolean z) {
        this.dv = z;
    }

    public void iO(boolean z) {
        this.du = z;
    }

    public void iP(int i) {
        if (i > 0) {
            this.dy = i;
        }
    }

    public void iQ(boolean z, List list) {
        if (C0065s.aa) {
            C0065s.bK(this.dp, "===========================开始批量发送======================cache====" + z + ",gpsList=" + list);
        }
        d dVarIF = d.iF();
        long jIncrementAndGet = dz.incrementAndGet();
        dVarIF.iH(jIncrementAndGet, new j(this, dVarIF, jIncrementAndGet, z, list));
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            GpsModel gpsModel = (GpsModel) it.next();
            if (gpsModel != null) {
                double dBq = C0062p.bq(gpsModel.gN());
                double dBq2 = C0062p.bq(gpsModel.gO());
                if (dBq != 0.0d && dBq2 != 0.0d) {
                    com.tw.core.track.model.f fVar = new com.tw.core.track.model.f(new com.tw.core.track.model.d(dBq, dBq2), CoordType.wgs84, C0062p.bq(gpsModel.gP()), C0062p.aX(gpsModel.gH()), (int) C0062p.bq(gpsModel.gL()), C0062p.bq(gpsModel.gQ()), C0062p.bq(gpsModel.gM()), gpsModel.gK(), null);
                    fVar.hM(fVar.hF() * 1000);
                    if (!this.ds) {
                        String strGR = gpsModel.gR();
                        if (!C0062p.aW(strGR)) {
                            HashMap map = new HashMap();
                            map.put("uid", strGR);
                            fVar.hN(map);
                        }
                    }
                    if (B.ao) {
                        B.ci(this.dp, "上报的点 %s", fVar.toString());
                    }
                    arrayList.add(fVar);
                }
            }
        }
        if (arrayList.size() == 1) {
            dVarIF.iD(new h(jIncrementAndGet, this.dt, this.dA, ((com.tw.core.track.model.f) arrayList.get(0)).hO(), (com.tw.core.track.model.f) arrayList.get(0)));
            return;
        }
        HashMap map2 = new HashMap(16);
        map2.put("batch_upload_" + jIncrementAndGet, arrayList);
        dVarIF.iE(new a(jIncrementAndGet, this.dt, this.dA, map2));
    }

    public void iS(GpsModel gpsModel) {
        C0065s.bK(this.dp, "============================进入实时分发百度监听器============================");
        if (gpsModel == null) {
            C0065s.bK(this.dp, "========>解析gpsModelΪ为空");
            return;
        }
        this.dr.add(gpsModel);
        C0065s.bK(this.dp, "size = " + this.dr.size() + ",mRealSendSize=" + this.dy);
        if (this.dr.size() >= this.dy) {
            C0065s.bK(this.dp, "======================满足实时" + this.dy + "条，进入发送数据=====================");
            iM();
        }
    }
}
