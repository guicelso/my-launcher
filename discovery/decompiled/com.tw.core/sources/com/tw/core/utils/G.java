package com.tw.core.utils;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class G {
    private static final String az = G.class.getSimpleName();
    private String aA = "";
    private final String token;

    public G(String str) {
        decode(str);
        this.token = str;
    }

    private String cT(String str) {
        try {
            return new String(Base64.decode(str, 11), Charset.defaultCharset());
        } catch (IllegalArgumentException e) {
            B.cj(az, "Received bytes didn't correspond to a valid Base64 encoded string.", new Object[0]);
            e.printStackTrace();
            return null;
        }
    }

    private String[] cU(String str) {
        String[] strArrSplit = str.split("\\.");
        String[] strArr = (strArrSplit.length == 2 && str.endsWith(".")) ? new String[]{strArrSplit[0], strArrSplit[1], ""} : strArrSplit;
        if (strArr.length == 3) {
            return strArr;
        }
        B.cj(az, String.format("The token was expected to have 3 parts, but got %s.", Integer.valueOf(strArr.length)), new Object[0]);
        return null;
    }

    private void decode(String str) {
        String strCT;
        String[] strArrCU = cU(str);
        if (strArrCU == null || (strCT = cT(strArrCU[1])) == null) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(strCT);
            this.aA = jSONObject.getString("sub");
            Log.d(az, "decode: " + jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String cS() {
        return this.aA;
    }
}
