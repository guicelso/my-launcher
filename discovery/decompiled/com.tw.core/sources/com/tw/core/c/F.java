package com.tw.core.c;

import android.text.TextUtils;
import com.tw.core.utils.C0064r;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class F implements Runnable {
    final /* synthetic */ C0022b fN;

    F(C0022b c0022b) {
        this.fN = c0022b;
    }

    @Override // java.lang.Runnable
    public void run() {
        int iDj = com.tw.core.utils.L.dj(this.fN.dP);
        if (iDj < 1) {
            com.tw.core.utils.B.cj("SimCardManager", "卡数量少于1 卡数量%s", Integer.valueOf(iDj));
            return;
        }
        List listDk = com.tw.core.utils.L.dk();
        if (listDk == null || listDk.size() <= 0) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator it = listDk.iterator();
        while (it.hasNext()) {
            jSONArray.put(((com.tw.core.a.d) it.next()).eD());
        }
        String strBz = C0064r.bz(this.fN.dP, "key_sim_card_upload_pkg", null);
        JSONArray jSONArray2 = new JSONArray();
        if (!TextUtils.isEmpty(strBz)) {
            try {
                JSONArray jSONArray3 = new JSONArray(strBz);
                if (jSONArray3.length() > 0) {
                    for (int i = 0; i < jSONArray3.length(); i++) {
                        String strOptString = jSONArray3.optString(i);
                        if (com.tw.core.b.d.fY(strOptString)) {
                            jSONArray2.put(strOptString);
                        }
                    }
                    com.tw.core.utils.B.ci("SimCardManager", "安装的包名 %s", jSONArray2.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("plat", com.tw.core.b.b.fI());
            jSONObject.put("card_list", jSONArray);
            jSONObject.put("competing_list", jSONArray2);
            String strKu = C0031k.kt().ku();
            if (!TextUtils.isEmpty(strKu)) {
                jSONObject.put("uid", strKu);
            }
            String strBz2 = C0064r.bz(this.fN.dP, "all_card_info", null);
            com.tw.core.utils.B.ci("SimCardManager", "currentCard %s lastCard %s", jSONObject.toString(), strBz2);
            if (TextUtils.equals(strBz2, jSONObject.toString())) {
                com.tw.core.utils.B.cj("SimCardManager", "卡信息没变", new Object[0]);
            } else {
                this.fN.ji(jSONObject);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}
