package com.tw.core.utils;

import android.content.Context;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.utils.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0047a {
    private static final String o = C0047a.class.getSimpleName();
    private static boolean p;

    public static void l(Context context) {
        B.ci(o, "car init", new Object[0]);
        if (p) {
            B.cj(o, "initing", new Object[0]);
        } else {
            p = true;
            DoFunApplication.nt().aa(new U(context));
        }
    }

    public static Map m(Context context, String str) throws Throwable {
        String str2;
        String str3;
        String str4;
        try {
            String strOptString = "0";
            String strOptString2 = "0";
            String strOptString3 = "0";
            String strBx = C0064r.bx(context, "mFirstLocation");
            if (!TextUtils.isEmpty(strBx)) {
                try {
                    JSONObject jSONObject = new JSONObject(strBx);
                    strOptString = jSONObject.optString("gpsx", "0");
                    strOptString2 = jSONObject.optString("gpsy", "0");
                    strOptString3 = jSONObject.optString("gpst", "0");
                } catch (Exception e) {
                    str2 = "0";
                    str3 = strOptString2;
                    str4 = strOptString;
                }
            }
            str2 = strOptString3;
            str3 = strOptString2;
            str4 = strOptString;
            String strGr = com.tw.core.b.f.gr();
            String strGs = com.tw.core.b.f.gs();
            String strFG = com.tw.core.b.b.fG();
            String str5 = com.tw.core.b.b.fH(context) + "";
            String strGt = com.tw.core.b.f.gt(context);
            String strGu = com.tw.core.b.f.gu(context);
            String strFB = com.tw.core.b.a.fB(context);
            String str6 = com.tw.core.b.c.fS(context) + "x" + com.tw.core.b.c.fT(context);
            String strFU = com.tw.core.b.c.fU(context);
            String str7 = com.tw.core.b.e.ge() + "";
            String strFI = com.tw.core.b.b.fI();
            String strFJ = com.tw.core.b.b.fJ();
            String strGf = com.tw.core.b.e.gf();
            String strFK = com.tw.core.b.b.fK();
            String strFL = com.tw.core.b.b.fL(context);
            com.tw.core.b.f.gr();
            String strFV = com.tw.core.b.c.fV(context);
            String strFM = com.tw.core.b.b.fM();
            String strFN = com.tw.core.b.b.fN(context);
            String strFO = com.tw.core.b.b.fO();
            String strGv = com.tw.core.b.f.gv(context);
            String strGw = com.tw.core.b.f.gw(context);
            String strGx = com.tw.core.b.f.gx(context);
            String strGy = com.tw.core.b.f.gy(context);
            String strGg = com.tw.core.b.e.gg();
            String strGz = com.tw.core.b.f.gz();
            String strGA = com.tw.core.b.f.gA();
            String strGB = com.tw.core.b.f.gB();
            String strBz = C0064r.by(context, "key_cid_change", false) ? C0064r.bz(context, "key_last_device_cid", null) : null;
            String str8 = TextUtils.isEmpty(strGg) ? "unknown" : strGg;
            String strY = DoFunApplication.nt().Y("");
            if (TextUtils.isEmpty(strY)) {
                strY = "unknown";
            }
            HashMap map = new HashMap();
            if (!"TW".equals(DoFunApplication.nt().X())) {
                map.put("solution", DoFunApplication.nt().X());
            }
            map.put("mcuType", str);
            map.put("cid", strGr);
            map.put("cidUnstable", strGs);
            map.put("platDetail", strFG);
            map.put("simId", strGx);
            map.put("imei", strGw);
            map.put("netType", strFB);
            map.put("screenResolution", str6);
            map.put("screenType", strFU);
            map.put("romSize", str7);
            map.put("plat", strFI);
            map.put("chipPartner", strFJ);
            map.put("androidVersion", strGf);
            map.put("sysVersion", str8);
            map.put("sensor", strFL);
            map.put("wifiAddr", strFM);
            map.put("ipAddr", strFN);
            map.put("bluetooth", strFO);
            map.put("simNetName", strGv);
            map.put("simCountryCode", strGy);
            map.put("brandId", strFK);
            map.put("screenSize", strFV);
            map.put("ramSize", str5);
            map.put("simNetId", strGt);
            map.put("simHeadingCode", strGu);
            map.put("fotaId", strY);
            map.put("initializeLongitude", str3);
            map.put("initializeLatitude", str4);
            map.put("initializeTime", str2);
            if (strBz != null) {
                map.put("cidOld", strBz);
            }
            if (strGz != null) {
                map.put("cidStable", strGz);
            }
            if (strGA != null) {
                map.put("mcuidStable", strGA);
            }
            if (strGB != null) {
                map.put("cpuidStable", strGB);
            }
            C0065s.bK(o, "初始化车机信息" + map.toString());
            return map;
        } catch (Exception e2) {
            C0050d.w().x(e2);
            return null;
        }
    }
}
