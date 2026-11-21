package com.tw.core.c;

import android.text.TextUtils;
import com.tw.core.utils.C0057k;
import com.tw.core.utils.C0064r;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class ab implements Runnable {
    final /* synthetic */ C0043w gA;
    final /* synthetic */ com.tw.core.h.e gB;

    ab(C0043w c0043w, com.tw.core.h.e eVar) {
        this.gA = c0043w;
        this.gB = eVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        try {
            JSONObject jSONObject = new JSONObject(this.gB.jk);
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("packageList");
            String strOptString = jSONObject.optString("lastUpdTime");
            if (TextUtils.equals(strOptString, C0064r.bx(this.gA.fg, "GpsFileUpdTime"))) {
                com.tw.core.utils.B.cj("GpsPermissionManager", "GPS文件数据没有更新", new Object[0]);
                return;
            }
            if (!this.gA.lo()) {
                com.tw.core.utils.B.cj("GpsPermissionManager", "系统不存在GPS文件", new Object[0]);
                return;
            }
            if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
                com.tw.core.utils.E.cA(this.gA.lm());
                com.tw.core.utils.B.cj("GpsPermissionManager", "删除GPS文件", new Object[0]);
                return;
            }
            boolean zLn = this.gA.ln();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                String string = jSONObject2.getString("packageName");
                String string2 = jSONObject2.getString("signatureDigest");
                String string3 = jSONObject2.getString("hashCode");
                stringBuffer.append(string);
                if (zLn) {
                    stringBuffer.append(" ");
                    stringBuffer.append(string2);
                    stringBuffer.append(" ");
                    stringBuffer.append(string3);
                }
                if (i < jSONArrayOptJSONArray.length() - 1) {
                    stringBuffer.append("\n");
                }
            }
            this.gA.lp(this.gA.lm(), stringBuffer.toString());
            if (C0057k.am(strOptString)) {
                return;
            }
            C0064r.bB(this.gA.fg, "GpsFileUpdTime", strOptString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
