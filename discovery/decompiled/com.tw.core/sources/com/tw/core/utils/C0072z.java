package com.tw.core.utils;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: renamed from: com.tw.core.utils.z, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0072z {
    public static List cg(JSONArray jSONArray, Class cls) {
        if (jSONArray != null && jSONArray.length() > 0) {
            try {
                ArrayList arrayList = new ArrayList(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        Object obj = jSONArray.get(i);
                        if (!(obj instanceof String) || !TextUtils.isEmpty((CharSequence) obj)) {
                            arrayList.add(obj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return arrayList;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
