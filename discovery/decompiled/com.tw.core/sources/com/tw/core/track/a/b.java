package com.tw.core.track.a;

import com.tw.core.track.IllegalTrackArgumentException;
import com.tw.core.track.d;
import com.tw.core.track.g;
import com.tw.core.track.h;
import com.tw.core.track.model.f;
import com.tw.core.utils.B;
import com.tw.core.utils.C0065s;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static void ib(h hVar) {
        StringBuilder sb = new StringBuilder();
        ie(hVar, sb);
        ig(hVar.gD(), "track/addpoint", a.ia("track/addpoint", sb.toString(), "POST"));
    }

    public static void ic(com.tw.core.track.a aVar) {
        StringBuilder sb = new StringBuilder();
        ie(aVar, sb);
        ig(aVar.gD(), "track/addpoints", a.ia("track/addpoints", sb.toString(), "POST"));
    }

    private static void id(com.tw.core.track.model.a aVar, StringBuilder sb) {
        if (com.tw.core.track.b.a.ij(aVar.gC())) {
            throw new IllegalTrackArgumentException("ak can not be null or empty string.");
        }
        if (aVar.gE() <= 0) {
            throw new IllegalTrackArgumentException("serviceId is lower than 0.");
        }
        sb.append("ak=").append(aVar.gC());
        sb.append("&service_id=").append(aVar.gE());
    }

    public static void ie(com.tw.core.track.model.a aVar, StringBuilder sb) {
        if (aVar == null) {
            throw new IllegalTrackArgumentException("request can not be null.");
        }
        id(aVar, sb);
        B.ci("TrackHandler", "packRequest %s", sb);
        if (!(aVar instanceof h)) {
            com.tw.core.track.b.c.ip(((com.tw.core.track.a) aVar).iq(), sb);
            return;
        }
        h hVar = (h) aVar;
        sb.append("&entity_name=").append(com.tw.core.track.b.b.ik(hVar.iZ()));
        f fVarJa = hVar.ja();
        if (fVarJa == null) {
            throw new IllegalTrackArgumentException("trackPoint can not be null.");
        }
        com.tw.core.track.b.c.io(fVarJa, sb);
    }

    /* JADX INFO: renamed from: if, reason: not valid java name */
    private static void m0if(com.tw.core.track.model.c cVar, String str) {
        if (C0065s.aa) {
            C0065s.bK("parseCommonResponse", "result=" + str);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status")) {
                cVar.hl(jSONObject.getInt("status"));
            }
            if (jSONObject.has("message")) {
                cVar.hk(jSONObject.getString("message"));
            }
            if (jSONObject.has("fail_info")) {
                cVar.hj(jSONObject.getString("fail_info"));
            }
        } catch (Exception e) {
            cVar.hl(-2);
            cVar.hk("解析失败");
        }
    }

    public static void ig(long j, String str, String str2) {
        Map map = d.iF().f1do;
        if (map == null) {
            return;
        }
        g gVar = new g(j, 0, "success");
        m0if(gVar, str2);
        com.tw.core.track.model.b bVar = (com.tw.core.track.model.b) map.get(Long.valueOf(j));
        if (bVar == null) {
            return;
        }
        if (gVar.hi() == 0) {
            bVar.hg(j);
        } else {
            bVar.hf(gVar);
        }
    }
}
