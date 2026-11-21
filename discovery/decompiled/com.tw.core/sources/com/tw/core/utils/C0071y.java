package com.tw.core.utils;

import com.tw.core.app.DoFunApplication;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.utils.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0071y {
    private static final String an = C0071y.class.getSimpleName();

    public static String ce(double d, double d2) {
        String strAl = "";
        try {
            strAl = C0057k.al(com.tw.core.R.string.default_province);
            for (String str : DoFunApplication.nu().getResources().getStringArray(com.tw.core.R.array.province_location_array)) {
                String[] strArrSplit = str.split("[,]");
                String[] strArrSplit2 = strArrSplit[1].split("[|]");
                Double dValueOf = Double.valueOf(Double.parseDouble(strArrSplit2[0]));
                Double dValueOf2 = Double.valueOf(Double.parseDouble(strArrSplit2[1]));
                String[] strArrSplit3 = strArrSplit[2].split("[|]");
                Double dValueOf3 = Double.valueOf(Double.parseDouble(strArrSplit3[0]));
                Double dValueOf4 = Double.valueOf(Double.parseDouble(strArrSplit3[1]));
                if (d > dValueOf.doubleValue() && d < dValueOf2.doubleValue() && d2 > dValueOf3.doubleValue() && d2 < dValueOf4.doubleValue()) {
                    return strArrSplit[0];
                }
            }
        } catch (Exception e) {
            C0065s.bL(an, e);
        }
        return strAl;
    }

    public static com.tw.core.a.a cf(String str) {
        JSONObject jSONObjectOptJSONObject;
        try {
            JSONObject jSONObject = new JSONObject(C0062p.aT(str));
            String string = jSONObject.getString("code");
            com.tw.core.a.a aVar = new com.tw.core.a.a();
            aVar.ec(string);
            if (!"0".equals(string) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                return aVar;
            }
            String strOptString = jSONObjectOptJSONObject.optString("title");
            String strOptString2 = jSONObjectOptJSONObject.optString("name");
            String strOptString3 = jSONObjectOptJSONObject.optString("path");
            String strOptString4 = jSONObjectOptJSONObject.optString("flag");
            String strOptString5 = jSONObjectOptJSONObject.optString("version");
            String strOptString6 = jSONObjectOptJSONObject.optString("describe");
            String strOptString7 = jSONObjectOptJSONObject.optString("appId");
            aVar.ea(strOptString2);
            aVar.eg(strOptString);
            aVar.ee(strOptString6);
            aVar.ed(strOptString4);
            aVar.ef(strOptString5);
            aVar.dZ(strOptString3);
            aVar.eb(strOptString7);
            return aVar;
        } catch (Exception e) {
            C0065s.bL(an, e);
            return null;
        }
    }
}
