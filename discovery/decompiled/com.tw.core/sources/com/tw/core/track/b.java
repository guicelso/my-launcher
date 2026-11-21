package com.tw.core.track;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import com.tw.core.c.AbstractC0030j;
import com.tw.core.c.C0031k;
import com.tw.core.c.InterfaceC0041u;
import com.tw.core.track.model.GpsModel;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0058l;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b implements InterfaceC0041u {
    private boolean de;
    private boolean df;
    private Context dg;
    private AbstractC0030j dh;
    private String dj;
    private static DecimalFormat dc = new DecimalFormat("#0.000000", new DecimalFormatSymbols(Locale.CHINA));
    private static DecimalFormat db = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA));
    private static DecimalFormat da = new DecimalFormat("#0.0", new DecimalFormatSymbols(Locale.CHINA));
    private List dd = new ArrayList();
    private int di = 30;

    public b(Context context, AbstractC0030j abstractC0030j) {
        this.dg = context;
        this.dh = abstractC0030j;
    }

    private String iA() {
        if (this.dj == null) {
            this.dj = C0064r.bz(this.dg, "key_gps_ali_url", null);
        }
        return this.dj;
    }

    private boolean ix() {
        if (C0065s.aa) {
            C0065s.bK("AliBatchListener", "checkAgreeUpload mCanUpload=" + this.df + ",NetworkInfoTools.isAvailable(mContext=" + com.tw.core.b.a.fE(this.dg) + ",getUrl=" + iA() + ",mBindUpload=" + this.de + ",bind=" + ((this.de && TextUtils.isEmpty(C0031k.kt().ku())) ? false : true));
        }
        if (this.df && com.tw.core.b.a.fE(this.dg) && !C0062p.aW(C0064r.bx(this.dg, "key_gps_ali_url"))) {
            return (this.de && TextUtils.isEmpty(C0031k.kt().ku())) ? false : true;
        }
        return false;
    }

    private JSONObject iy(GpsModel gpsModel) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("speed", db.format(Float.valueOf(gpsModel.gQ())));
        jSONObject.put("direction", String.valueOf((int) Float.parseFloat(gpsModel.gL())));
        jSONObject.put("longitude", dc.format(Double.valueOf(gpsModel.gO())));
        jSONObject.put("latitude", dc.format(Double.valueOf(gpsModel.gN())));
        jSONObject.put("height", db.format(Float.valueOf(gpsModel.gM())));
        jSONObject.put("radius", db.format(Float.valueOf(gpsModel.gP())));
        return jSONObject;
    }

    private JSONObject iz(GpsModel gpsModel) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!C0062p.aW(gpsModel.gR())) {
            jSONObject.put("uid", gpsModel.gR());
        }
        jSONObject.put("cid", com.tw.core.b.f.gr());
        return jSONObject;
    }

    @Override // com.tw.core.c.InterfaceC0041u
    public void iB(Location location) {
        GpsModel gpsModelGI = GpsModel.gI(this.dg, location);
        B.ci("AliBatchListener", "onLocation gpsModel %s", gpsModelGI.toString());
        iC(gpsModelGI);
    }

    public void iC(GpsModel gpsModel) {
        B.ci("AliBatchListener", "============================进入实时分发阿里监听器============================", new Object[0]);
        if (gpsModel == null) {
            C0065s.bK("AliBatchListener", "========>解析gpsModelΪ为空");
            return;
        }
        this.dd.add(gpsModel);
        C0065s.bK("AliBatchListener", "size = " + this.dd.size() + ",mRealSendSize=" + this.di);
        if (this.dd.size() >= this.di) {
            C0065s.bK("AliBatchListener", "======================满足实时" + this.di + "条，进入发送数据=====================");
            ir();
        }
    }

    public void ir() {
        if (this.dd.size() > 0) {
            ArrayList arrayList = new ArrayList(this.dd);
            this.dd.clear();
            if (!ix()) {
                C0065s.bK("AliBatchListener", "======================checkAgreeUpload 不滿足條件========");
                this.dh.kr(arrayList);
                return;
            }
            try {
                C0048b.p().execute(new c(this, false, arrayList));
            } catch (Exception e) {
                e.printStackTrace();
                this.dh.kr(arrayList);
                if (C0065s.aa) {
                    C0065s.bK("AliBatchListener", "nowUpload发送数据到百度出错>" + e + "============>" + arrayList.toString());
                }
            }
        }
    }

    public void is(boolean z) {
        this.df = z;
    }

    public void it(boolean z) {
        this.de = z;
    }

    public void iu(int i) {
        if (i > 0) {
            this.di = i;
        }
    }

    public void iv() {
        C0065s.bK("AliBatchListener", "============================进入缓存数据分发百度监听器============================");
        if (this.dh == null || !ix()) {
            return;
        }
        List<List> listIi = com.tw.core.track.b.a.ii(this.dh.kp(1000), 100);
        C0065s.bK("AliBatchListener", "============================进入缓存数据分发百度监听器cachePointHandle============================" + (listIi != null ? Integer.valueOf(listIi.size()) : null));
        if (listIi == null || listIi.size() <= 0) {
            return;
        }
        for (List list : listIi) {
            if (C0065s.aa) {
                C0065s.bK("AliBatchListener", "======================cachePointHandle，进入发送数据=====================" + list.size());
            }
            try {
                C0048b.p().execute(new c(this, true, list));
            } catch (Exception e) {
                e.printStackTrace();
                if (C0065s.aa) {
                    C0065s.bK("AliBatchListener", "cachePointHandle 发送数据到百度出错>" + e + "============>" + list.toString());
                }
            }
        }
    }

    public void iw(boolean z, List list) {
        if (C0065s.aa) {
            C0065s.bK("AliBatchListener", "===========================开始批量发送======================cache====" + z + ",gpsList=" + list);
        }
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                GpsModel gpsModel = (GpsModel) it.next();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("metric", "gps");
                jSONObject.put("fields", iy(gpsModel));
                jSONObject.put("tags", iz(gpsModel));
                jSONObject.put("timestamp", gpsModel.gH());
                jSONArray.put(jSONObject);
            }
            if (B.ao) {
                B.ci("AliBatchListener", "上报的数据 %s", jSONArray);
            }
            C0058l.az(jSONArray, this.dj, new i(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
