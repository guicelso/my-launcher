package com.tw.core.j;

import android.content.pm.PackageInfo;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.app.DoFunApplication;
import com.tw.core.b.f;
import com.tw.core.c.C0031k;
import com.tw.core.utils.B;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0051e;
import com.tw.core.utils.C0052f;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.E;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b extends com.tw.core.i.b implements com.tw.core.h.d {
    private boolean ki;

    /* JADX INFO: Access modifiers changed from: private */
    public void oO() {
        List<C0052f> listH = C0051e.H(DoFunApplication.nu());
        if (C0065s.aa) {
            C0065s.bK("UserBehaviorManager", "uploadUsageStats ：" + listH);
        }
        if (listH == null || listH.size() <= 0) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (C0052f c0052f : listH) {
            try {
                PackageInfo packageInfoGa = com.tw.core.b.d.ga(DoFunApplication.nu(), c0052f.A);
                if (packageInfoGa != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("package", c0052f.A);
                    jSONObject.put("count", c0052f.z);
                    jSONObject.put("time", c0052f.B);
                    jSONObject.put("version", packageInfoGa.versionName);
                    jSONObject.put("flag", packageInfoGa.versionCode);
                    jSONObject.put("channel", com.tw.core.b.d.gc(DoFunApplication.nu(), c0052f.A, "UMENG_CHANNEL"));
                    jSONObject.put("name", DoFunApplication.nu().getPackageManager().getApplicationLabel(packageInfoGa.applicationInfo).toString());
                    jSONArray.put(jSONObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("app", jSONArray);
            jSONObject2.put("cid", f.gr());
            long jCurrentTimeMillis = System.currentTimeMillis();
            jSONObject2.put("up", (jCurrentTimeMillis - SystemClock.elapsedRealtime()) / 1000);
            jSONObject2.put("down", jCurrentTimeMillis / 1000);
            String strKu = C0031k.kt().ku();
            if (!TextUtils.isEmpty(strKu)) {
                jSONObject2.put("uid", strKu);
            }
            if (C0065s.aa) {
                C0065s.bK("UserBehaviorManager", "上传 user behavior 数据：" + jSONObject2.toString());
            }
            od("0203" + Base64.encodeToString(jSONObject2.toString().getBytes(), 8));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jF() {
        boolean zBy = C0064r.by(DoFunApplication.nu(), "UserBehaviorDataCollectEnable", false);
        B.ci("UserBehaviorManager", "onShutdown UserBehaviorDataCollect Enable = %s", Boolean.valueOf(zBy));
        if (zBy || this.ki) {
            C0048b.p().execute(new e(this));
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        B.ci("UserBehaviorManager", "emqMessageChanged = %s", eVar);
        try {
            this.ki = new JSONObject(eVar.jk).optBoolean("collect_on_off", false);
            if (com.tw.core.b.e.gh()) {
                C0064r.bA(DoFunApplication.nu(), "UserBehaviorDataCollectEnable", this.ki);
            }
            B.ci("UserBehaviorManager", "emqMessageChanged enable= %s", Boolean.valueOf(this.ki));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
        boolean zBy = C0064r.by(DoFunApplication.nu(), "UserBehaviorDataCollectEnable", false);
        C0065s.bK("UserBehaviorManager", "connected UserBehaviorDataCollect Enable = " + zBy);
        if (zBy) {
            super.jc(z, str);
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return E.cz() + "/statistisc/.cache_user_behavior.df";
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0304";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return com.tw.core.app.a.iC + "/cloud/collect/app/journey";
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void jj() {
        super.jj();
        com.tw.core.h.a.get().nL(this);
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
