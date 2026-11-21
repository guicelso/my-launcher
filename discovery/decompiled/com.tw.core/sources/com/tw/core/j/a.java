package com.tw.core.j;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.app.DoFunApplication;
import com.tw.core.c.C0031k;
import com.tw.core.h.f;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0066t;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a implements com.tw.core.e.c, f, com.tw.core.e.d, com.tw.core.h.d {
    private static final String kb = com.tw.core.app.a.iC + "/cloud/collect/app/list";
    private com.tw.core.g.a kc;
    private Context kd;
    private com.tw.core.g.b ke;
    private boolean kg;
    private long kh = 2592000000L;
    private AtomicBoolean kf = new AtomicBoolean(false);

    public a(Context context) {
        this.kd = context;
        this.kc = new com.tw.core.g.a(context);
        this.ke = new com.tw.core.g.b(context);
    }

    private void oI() {
        if (this.kg) {
            C0048b.p().execute(new c(this));
        }
    }

    private void oJ(JSONArray jSONArray, String str) {
        String strGr = com.tw.core.b.f.gr();
        String strFI = com.tw.core.b.b.fI();
        String strAc = DoFunApplication.nt().ac();
        long jEv = com.tw.core.a.c.ev();
        long jEy = com.tw.core.a.c.ey();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("app", jSONArray);
            jSONObject.put("cid", strGr);
            jSONObject.put("plat", strFI);
            jSONObject.put("sys_version", strAc);
            jSONObject.put("mcu_version", str);
            jSONObject.put("language", C0062p.aV(DoFunApplication.nu()));
            jSONObject.put("time", C0066t.bO() / 1000);
            jSONObject.put("4g", C0062p.bq(C0062p.bd(jEv)));
            jSONObject.put("wifi", C0062p.bq(C0062p.bd(jEy - jEv)));
            jSONObject.put("default_theme", com.tw.core.b.d.fZ(DoFunApplication.nu()));
            String strKu = C0031k.kt().ku();
            if (!TextUtils.isEmpty(strKu)) {
                jSONObject.put("uid", strKu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (C0065s.aa) {
            C0065s.bK("AppStatisticsManager", "上传 App 数据：" + jSONObject.toString());
        }
        com.tw.core.h.b.nN().nO("0202" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), kb, com.tw.core.h.b.nN().iV, new d(this));
    }

    private void oK(List list, String str) throws Throwable {
        JSONArray jSONArray = new JSONArray();
        int size = list.size();
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < size; i++) {
            String str2 = (String) list.get(i);
            PackageInfo packageInfoGa = com.tw.core.b.d.ga(DoFunApplication.nu(), str2);
            if (packageInfoGa != null) {
                String string = packageInfoGa.applicationInfo.loadLabel(DoFunApplication.nu().getPackageManager()).toString();
                long jEC = new com.tw.core.a.c(packageInfoGa.applicationInfo.uid).eC();
                String str3 = packageInfoGa.versionName;
                int i2 = packageInfoGa.versionCode;
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("package", str2);
                    jSONObject.put("name", string);
                    jSONObject.put("version", str3);
                    jSONObject.put("flag", i2);
                    String strBd = jEC > 0 ? C0062p.bd(jEC) : "0.00";
                    C0065s.bK("AppStatisticsManager", string + "使用流量 " + strBd + "mb");
                    jSONObject.put("flow", strBd);
                    jSONObject.put("channel", com.tw.core.b.d.gc(DoFunApplication.nu(), str2, "UMENG_CHANNEL"));
                    jSONObject.put("size", C0062p.bd(new File(packageInfoGa.applicationInfo.publicSourceDir).length()));
                    jSONArray.put(jSONObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            atomicInteger.incrementAndGet();
            if (atomicInteger.get() == size) {
                oJ(jSONArray, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void oL() {
        if (this.kf.get()) {
            C0065s.bK("AppStatisticsManager", "加载中");
            return;
        }
        long jBF = C0064r.bF(DoFunApplication.nu(), "LastTimeUploadAppStatistics");
        long jBO = C0066t.bO();
        if (C0065s.aa) {
            C0065s.bK("AppStatisticsManager", "lastTime：" + C0066t.bN(jBF, C0066t.ag));
            C0065s.bK("AppStatisticsManager", "currentTime：" + C0066t.bN(jBO, C0066t.ag));
            C0065s.bK("AppStatisticsManager", "currentTime - lastTime = " + (jBO - jBF) + ",interval = " + this.kh);
        }
        if (jBO - jBF < this.kh) {
            C0065s.bK("AppStatisticsManager", "时间未达到");
        } else {
            this.kf.set(true);
            DoFunApplication.nt().aa(this);
        }
    }

    @Override // com.tw.core.e.d
    public void dS(String str) throws Throwable {
        C0065s.bK("AppStatisticsManager", "onBackMcuVersion = " + str);
        PackageManager packageManager = DoFunApplication.nu().getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(8192);
        ArrayList arrayList = new ArrayList();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        for (PackageInfo packageInfo : installedPackages) {
            String str2 = packageInfo.packageName;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (str2.contains("com.dofun") || C0062p.bf(DoFunApplication.nu()).equals(str2) || str2.contains("launcher") || (applicationInfo.flags & 1) <= 0) {
                arrayList.add(str2);
            } else {
                intent.setPackage(packageInfo.packageName);
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
                if (listQueryIntentActivities != null && listQueryIntentActivities.size() > 0) {
                    arrayList.add(str2);
                }
            }
            if (C0065s.aa) {
                C0065s.bK("AppStatisticsManager", "label = " + packageInfo.applicationInfo.loadLabel(packageManager).toString() + ",packageName = " + packageInfo.packageName);
            }
        }
        oK(arrayList, str);
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
    }

    @Override // com.tw.core.e.c
    public void jF() {
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        C0065s.bK("AppStatisticsManager", "emqMessageChanged =" + eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            this.kg = jSONObject.getBoolean("collect_on_off");
            this.kh = jSONObject.getLong("collect_frequency") * 1000;
            C0065s.bK("AppStatisticsManager", "emqMessageChanged enable=" + this.kg + ",freq=" + this.kh);
            oI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.h.f
    public void jc(boolean z, String str) {
        this.kc.nA();
        this.ke.nA();
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0303";
    }

    @Override // com.tw.core.e.c
    public void jj() {
        com.tw.core.h.a.get().nL(this);
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        com.tw.core.h.a.get().nK(this);
        this.kc.nz();
    }
}
