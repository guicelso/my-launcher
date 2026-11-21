package com.tw.core.i;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.tw.core.R;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0056j;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.C0067u;
import com.tw.core.utils.C0072z;
import com.tw.core.utils.D;
import com.tw.core.utils.E;
import com.tw.core.utils.InterfaceC0068v;
import com.tw.core.utils.Q;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c extends f {
    private d jx;
    private boolean jy;

    public c(Context context, a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ol(String str, com.tw.core.a.e eVar, boolean z) {
        String strOp = op(eVar);
        C0065s.bK("AppUpgradeMessageReceiv", "installApk apkFilePath = " + strOp);
        ou();
        l lVar = new l(this, eVar, str, strOp);
        C0065s.bK("AppUpgradeMessageReceiv", "installApk type = " + z);
        if (z) {
            C0067u.bR(this.jB, strOp, lVar);
            return;
        }
        ot(lVar);
        if (C0067u.bU(this.jB, new File(strOp))) {
            return;
        }
        oz(str, 0);
    }

    private boolean om(String str, com.tw.core.a.e eVar) {
        boolean zEquals = E.cF(str) ? TextUtils.equals(eVar.eR(), C0056j.aj(str)) : false;
        PackageInfo packageInfoGb = com.tw.core.b.d.gb(str);
        if (C0065s.aa) {
            C0065s.bK("AppUpgradeMessageReceiv", "checkLocalApkExists version = " + eVar + ",fileDownload = " + zEquals + ",calculateMd5 = " + C0056j.aj(str) + ",packageInfo.packageName = " + (packageInfoGb != null ? packageInfoGb.packageName : "null") + ",packageInfo.versionCode = " + (packageInfoGb != null ? Integer.valueOf(packageInfoGb.versionCode) : "null") + ",apkFilePath = " + str);
        }
        return zEquals && packageInfoGb != null && TextUtils.equals(eVar.fz(), packageInfoGb.packageName) && packageInfoGb.versionCode == eVar.fA();
    }

    private boolean on(String str, com.tw.core.a.e eVar) {
        try {
            C0048b.p().submit(new D(eVar.eL(), new File(str))).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean zOm = om(str, eVar);
        C0065s.bK("AppUpgradeMessageReceiv", "downloadApk downloadResult = " + zOm);
        return zOm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void oo(String str, String str2, String str3) {
        int iLastIndexOf;
        String strSubstring = null;
        com.tw.core.a.e eVarOs = os(str3);
        C0065s.bK("AppUpgradeMessageReceiv", "parsePushMessage = " + eVarOs);
        if (eVarOs == null) {
            oz(str2, 106);
            C0065s.bK("AppUpgradeMessageReceiv", "数据错误");
            return;
        }
        if (!eVarOs.eU() && !com.tw.core.b.d.fY(eVarOs.fz())) {
            oz(str2, 107);
            C0065s.bK("AppUpgradeMessageReceiv", "设备未安装对应应用，return");
            return;
        }
        String strFz = eVarOs.fz();
        int iFA = eVarOs.fA();
        int iGd = com.tw.core.b.d.gd(this.jB, strFz);
        C0065s.bK("AppUpgradeMessageReceiv", "verCode = " + iFA + ",appVerCode = " + iGd);
        if (iGd >= iFA) {
            oz(str2, 104);
            C0065s.bK("AppUpgradeMessageReceiv", "本地有新版本，return");
            return;
        }
        String strGg = com.tw.core.b.e.gg();
        if (!TextUtils.isEmpty(strGg) && strGg.contains("_") && strGg.length() > (iLastIndexOf = strGg.lastIndexOf("_") + 1)) {
            strSubstring = strGg.substring(iLastIndexOf);
        }
        C0065s.bK("AppUpgradeMessageReceiv", "systemVersion = " + strGg + " , fotaVersion = " + strSubstring);
        if (!TextUtils.isEmpty(strSubstring)) {
            List listEO = eVarOs.eO();
            if (listEO != null && listEO.size() > 0 && listEO.contains(strSubstring)) {
                oz(str2, 105);
                C0065s.bK("AppUpgradeMessageReceiv", "当前fota版本处于黑名单，return");
                return;
            }
            List listEP = eVarOs.eP();
            if (listEP != null && listEP.size() > 0 && !listEP.contains(strSubstring)) {
                oz(str2, 105);
                C0065s.bK("AppUpgradeMessageReceiv", "当前fota版本不在白名单，return");
                return;
            }
        }
        String strES = eVarOs.eS();
        C0065s.bK("AppUpgradeMessageReceiv", "systemVersion = " + strGg + " , systemRegular = " + strES);
        if (!C0062p.aW(strES) && !Pattern.matches(strES, strGg)) {
            oz(str2, 105);
            C0065s.bK("AppUpgradeMessageReceiv", "正则表达匹配失败，return");
            return;
        }
        List listEQ = eVarOs.eQ();
        String strAV = C0062p.aV(this.jB);
        C0065s.bK("AppUpgradeMessageReceiv", "language = " + strAV);
        if (listEQ != null && listEQ.size() > 0 && !listEQ.contains(strAV)) {
            oz(str2, 102);
            C0065s.bK("AppUpgradeMessageReceiv", "当前语言环境不在白名单，return");
            return;
        }
        List listEM = eVarOs.eM();
        if (listEM != null && listEM.size() > 0) {
            String strGc = com.tw.core.b.d.gc(this.jB, eVarOs.fz(), "UMENG_CHANNEL");
            C0065s.bK("AppUpgradeMessageReceiv", "apkChannel = " + strGc);
            if (!TextUtils.isEmpty(strGc) && !listEM.contains(strGc)) {
                oz(str2, 103);
                C0065s.bK("AppUpgradeMessageReceiv", "当前渠道版本不在白名单，return");
                return;
            } else {
                if ((!eVarOs.eU()) & TextUtils.isEmpty(strGc)) {
                    oz(str2, 107);
                    C0065s.bK("AppUpgradeMessageReceiv", "设备未安装对应渠道应用，且不能覆盖，return");
                    return;
                }
            }
        }
        oz(str2, 101);
        boolean zOr = or(eVarOs);
        oz(str2, zOr ? 100 : 99);
        if (!zOr) {
            C0065s.bK("AppUpgradeMessageReceiv", "loadApkFileResult 文件未下载完成，return");
            return;
        }
        int type = eVarOs.getType();
        if (type == 0) {
            ol(str2, eVarOs, true);
        } else if (type == 1) {
            C0065s.bK("AppUpgradeMessageReceiv", "sendNotification : taskId = " + str2 + ",version = " + eVarOs);
            com.tw.core.notification.b.get().oF(str2, this);
            com.tw.core.notification.a.get().oE(R.mipmap.ic_upgrade, str2, eVarOs.eT(), eVarOs.eN(), str3);
        }
    }

    private String op(com.tw.core.a.e eVar) {
        return E.cz() + "/push/apk/" + Q.dL(eVar.fz()) + eVar.eR();
    }

    private boolean or(com.tw.core.a.e eVar) {
        String strOp = op(eVar);
        boolean zOm = om(strOp, eVar);
        C0065s.bK("AppUpgradeMessageReceiv", "loadApkFileResult apkExists = " + zOm);
        if (!zOm) {
            zOm = on(strOp, eVar);
        }
        C0065s.bK("AppUpgradeMessageReceiv", "loadApkFileResult checkLocalApkExists = " + zOm);
        return zOm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.tw.core.a.e os(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("package");
            int i = jSONObject.getInt("flag");
            String string2 = jSONObject.getString("path");
            String strOptString = jSONObject.optString("version");
            String strOptString2 = jSONObject.optString("title");
            String strOptString3 = jSONObject.optString("describe");
            String strOptString4 = jSONObject.optString("name");
            String strOptString5 = jSONObject.optString("system_version_regular");
            boolean zOptBoolean = jSONObject.optBoolean("not_exist_install");
            List listCg = C0072z.cg(jSONObject.optJSONArray("target_channel"), String.class);
            List listCg2 = C0072z.cg(jSONObject.optJSONArray("language"), String.class);
            List listCg3 = C0072z.cg(jSONObject.optJSONArray("blacklist_full"), String.class);
            List listCg4 = C0072z.cg(jSONObject.optJSONArray("whitelist_full"), String.class);
            List listCg5 = C0072z.cg(jSONObject.optJSONArray("blacklist_flag"), String.class);
            List listCg6 = C0072z.cg(jSONObject.optJSONArray("whitelist_flag"), String.class);
            String strOptString6 = jSONObject.optString("key");
            return new com.tw.core.a.e(string, i, strOptString, strOptString4).eV(string2).fg(strOptString2).eX(strOptString3).fb(listCg2).fc(strOptString6).fh(jSONObject.optInt("type")).fa(zOptBoolean).eW(listCg).fd(listCg3).ff(listCg4).eY(listCg5).fe(strOptString5).eZ(listCg6);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void ot(InterfaceC0068v interfaceC0068v) {
        if (!this.jy) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            if (this.jx == null) {
                this.jx = new d(this, null);
            }
            this.jB.registerReceiver(this.jx, intentFilter);
            this.jy = true;
        }
        this.jx.oy(interfaceC0068v);
    }

    private void ou() {
        if (this.jy) {
            if (this.jx != null) {
                this.jx.oy(null);
                this.jB.unregisterReceiver(this.jx);
            }
            this.jy = false;
        }
    }

    @Override // com.tw.core.e.c
    public void jB() {
    }

    @Override // com.tw.core.e.c
    public void jC() {
    }

    @Override // com.tw.core.e.c
    public void jG() {
    }

    @Override // com.tw.core.e.c
    public void jH() {
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        C0065s.bK("AppUpgradeMessageReceiv", "emqMessageChanged = " + eVar);
        C0048b.p().execute(new j(this, eVar));
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0500";
    }

    @Override // com.tw.core.i.f, com.tw.core.notification.c
    public void ok(String str, String str2) {
        super.ok(str, str2);
        C0048b.p().execute(new k(this, str2, str));
    }

    @Override // com.tw.core.i.f
    public String oq() {
        return "0501";
    }
}
