package com.tw.core.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Base64;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0072z;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.c.y, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0045y extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String fv = com.tw.core.app.a.iC + "/cloud/receipt/cutstarter";
    private Context ft;
    private PackageManager fu;

    public C0045y(Context context) {
        this.ft = context;
        this.fu = this.ft.getPackageManager();
    }

    private void lH(String str, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", str);
            jSONObject.put("cid", com.tw.core.b.f.gr());
            jSONObject.put("time", System.currentTimeMillis() / 1000);
            jSONObject.put("code", i);
            oe("0507" + Base64.encodeToString(jSONObject.toString().getBytes(), 8), new af(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String lI() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        String str = this.fu.resolveActivity(intent, 65536).activityInfo.packageName;
        com.tw.core.utils.B.ci("SwitchLauncherManager", "当前设置的启动器 %s", str);
        return str;
    }

    private void lJ(String str, String str2, String str3, List list) {
        if (!com.tw.core.b.d.fY(str2)) {
            com.tw.core.utils.B.cj("SwitchLauncherManager", "%s 未安装", str2);
            lH(str, 103);
            return;
        }
        String strLI = lI();
        if (TextUtils.equals(str2, strLI)) {
            com.tw.core.utils.B.cj("SwitchLauncherManager", "当前设置的Launcher为默认Launcher", new Object[0]);
            lH(str, 104);
            return;
        }
        if (list != null && !list.contains(strLI)) {
            com.tw.core.utils.B.cj("SwitchLauncherManager", "原Launcher不匹配", new Object[0]);
            lH(str, 105);
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        List<ResolveInfo> listQueryIntentActivities = this.fu.queryIntentActivities(intent, 0);
        ComponentName[] componentNameArr = new ComponentName[listQueryIntentActivities.size()];
        ComponentName componentName = new ComponentName(str2, str3);
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i3 >= listQueryIntentActivities.size()) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.MAIN");
                intentFilter.addCategory("android.intent.category.HOME");
                intentFilter.addCategory("android.intent.category.DEFAULT");
                this.fu.clearPackagePreferredActivities(strLI);
                this.fu.addPreferredActivity(intentFilter, i2, componentNameArr, componentName);
                lH(str, 1);
                com.tw.core.utils.B.ci("SwitchLauncherManager", "切换成功 %s", str2);
                return;
            }
            ResolveInfo resolveInfo = listQueryIntentActivities.get(i3);
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null) {
                componentNameArr[i3] = new ComponentName(activityInfo.packageName, activityInfo.name);
                if (componentName.getClassName().equals(activityInfo.name)) {
                    i2 = resolveInfo.match;
                }
            }
            i = i3 + 1;
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("SwitchLauncherManager", "emqMessageChanged %s", eVar);
        try {
            JSONObject jSONObject = new JSONObject(eVar.jk);
            List listCg = C0072z.cg(jSONObject.optJSONArray("language"), String.class);
            String strOptString = jSONObject.optString("system_version_regex");
            if (listCg != null && listCg.size() > 0 && !listCg.contains(C0062p.aV(this.ft))) {
                com.tw.core.utils.B.cj("SwitchLauncherManager", "语言环境不匹配 %s", C0062p.aV(this.ft));
                lH(eVar.jl, 101);
                return;
            }
            String strGg = com.tw.core.b.e.gg();
            if (!C0062p.aW(strOptString) && !Pattern.matches(strOptString, strGg)) {
                com.tw.core.utils.B.cj("SwitchLauncherManager", "系统版本不匹配 %s", strGg);
                lH(eVar.jl, 102);
                return;
            }
            String strOptString2 = jSONObject.optString("dest");
            List listCg2 = C0072z.cg(jSONObject.optJSONArray("source"), String.class);
            if (TextUtils.isEmpty(strOptString2) || !strOptString2.contains("|")) {
                return;
            }
            String[] strArrSplit = strOptString2.split("\\|");
            lJ(eVar.jl, strArrSplit[0], strArrSplit[1], listCg2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.i.b, com.tw.core.h.f
    public void jc(boolean z, String str) {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0312";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return fv;
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
