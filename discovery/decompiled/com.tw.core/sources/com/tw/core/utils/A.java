package com.tw.core.utils;

import java.util.HashMap;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class A {
    public static String ch(HashMap map, String str) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put("device_no");
        jSONArray.put("partner_name");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jSONArray.length(); i++) {
            String strOptString = jSONArray.optString(i);
            String str2 = (String) map.get(strOptString);
            if (str2 == null) {
                B.cj("BaiDuSignUtil", "请求参数必须带 %s", strOptString);
            } else {
                sb.append(str2).append("|");
            }
        }
        map.put("_sign_key", jSONArray);
        String string = sb.append(str).toString();
        B.ci("BaiDuSignUtil", "md5之前的sign %s", string);
        String strDM = Q.dM(string);
        B.ci("BaiDuSignUtil", "最终sign %s", strDM);
        map.put("sign", strDM);
        return strDM;
    }
}
