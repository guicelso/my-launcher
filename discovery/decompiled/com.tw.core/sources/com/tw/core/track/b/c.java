package com.tw.core.track.b;

import android.text.TextUtils;
import com.tw.core.track.IllegalTrackArgumentException;
import com.tw.core.track.model.d;
import com.tw.core.track.model.f;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c {
    public static boolean il(d dVar) {
        if (dVar == null) {
            throw new IllegalTrackArgumentException("latLng can not be null");
        }
        if (Math.abs(dVar.hm()) > 90.0d || Math.abs(dVar.hn()) > 180.0d) {
            throw new IllegalTrackArgumentException("latLng is invalid value");
        }
        return true;
    }

    public static void im(Map map, StringBuilder sb) {
        for (Map.Entry entry : map.entrySet()) {
            sb.append("&").append((String) entry.getKey()).append("=").append(b.ik((String) entry.getValue()));
        }
    }

    private static void in(Map map, JSONObject jSONObject) {
        for (Map.Entry entry : map.entrySet()) {
            if (!TextUtils.isEmpty((CharSequence) entry.getKey()) && !TextUtils.isEmpty((CharSequence) entry.getValue())) {
                try {
                    jSONObject.put((String) entry.getKey(), b.ik((String) entry.getValue()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void io(f fVar, StringBuilder sb) {
        d dVarHG = fVar.hG();
        if (il(dVarHG)) {
            sb.append("&latitude=").append(dVarHG.hm());
            sb.append("&longitude=").append(dVarHG.hn());
        }
        sb.append("&loc_time=").append(fVar.hF());
        if (fVar.hH() == null) {
            throw new IllegalTrackArgumentException("coordType can not be null.");
        }
        sb.append("&coord_type_input=").append(fVar.hH().name());
        sb.append("&speed=").append(fVar.hI());
        sb.append("&direction=").append(fVar.hJ());
        sb.append("&height=").append(fVar.hK());
        sb.append("&radius=").append(fVar.hL());
        sb.append("&sysT=").append(fVar.hP());
        if (!a.ij(fVar.hO())) {
            sb.append("&_obejct_key=").append(b.ik(fVar.hO()));
        }
        if (fVar.hQ() != null) {
            im(fVar.hQ(), sb);
        }
    }

    public static void ip(Map map, StringBuilder sb) {
        if (map == null) {
            throw new IllegalTrackArgumentException("track points can not be null.");
        }
        JSONArray jSONArray = new JSONArray();
        try {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                List<f> list = (List) ((Map.Entry) it.next()).getValue();
                if (list != null && list.size() > 0) {
                    for (f fVar : list) {
                        if (fVar != null) {
                            if (fVar.hH() == null) {
                                throw new IllegalTrackArgumentException("coordType can not be null.");
                            }
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("entity_name", fVar.hO());
                            jSONObject.put("loc_time", fVar.hF());
                            d dVarHG = fVar.hG();
                            if (il(dVarHG)) {
                                jSONObject.put("latitude", dVarHG.hm());
                                jSONObject.put("longitude", dVarHG.hn());
                            }
                            jSONObject.put("coord_type_input", fVar.hH().name());
                            jSONObject.put("speed", fVar.hI());
                            jSONObject.put("direction", fVar.hJ());
                            jSONObject.put("height", fVar.hK());
                            jSONObject.put("radius", fVar.hL());
                            Map mapHQ = fVar.hQ();
                            if (mapHQ != null) {
                                in(mapHQ, jSONObject);
                            }
                            jSONArray.put(jSONObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("&point_list=").append(jSONArray.toString());
    }
}
