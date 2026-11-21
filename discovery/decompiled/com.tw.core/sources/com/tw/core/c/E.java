package com.tw.core.c;

import android.text.TextUtils;
import com.tw.core.utils.C0049c;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
class E implements Runnable {
    final /* synthetic */ C0021a fL;
    final /* synthetic */ JSONArray fM;

    E(C0021a c0021a, JSONArray jSONArray) {
        this.fL = c0021a;
        this.fM = jSONArray;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.fM != null) {
            for (int i = 0; i < this.fM.length(); i++) {
                String strOptString = this.fM.optString(i);
                if (!TextUtils.isEmpty(strOptString)) {
                    com.tw.core.utils.B.ci("AppConfigManager", "kill %s", strOptString);
                    C0049c.v(this.fL.dN, strOptString);
                }
            }
        }
    }
}
