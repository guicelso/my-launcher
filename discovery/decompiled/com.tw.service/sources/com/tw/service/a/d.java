package com.tw.service.a;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class d extends Thread {
    final /* synthetic */ a _k;
    final /* synthetic */ f n;

    d(f fVar, a aVar) {
        this.n = fVar;
        this._k = aVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        super.run();
        try {
            String strOptString = SystemProperties.get("ro.tw.ui.softwareType");
            if (TextUtils.isEmpty(strOptString)) {
                File file = new File("/data/tw/theme/default/Launcher/LauncherTheme.apk");
                if (!file.exists() || !file.canRead()) {
                    file = new File("/system/etc/theme/default/Launcher/LauncherTheme.apk");
                }
                if (file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f.a(this.n.mContext, file).getAssets().open("theme_config.json"), "utf-8"));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        } else {
                            sb.append(line);
                        }
                    }
                    JSONObject jSONObject = new JSONObject(sb.toString());
                    jSONObject.optString("theme_id", "");
                    jSONObject.optString("oem_d", "");
                    strOptString = jSONObject.optString("theme_name", "");
                } else {
                    strOptString = "";
                }
            }
            Log.e("HYH", "UIAuthorizationCheck->getCurrentTheme:" + strOptString);
            if (this._k != null) {
                this._k.a(strOptString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
