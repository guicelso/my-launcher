package com.dofun.carsetting.presenter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.bean.VoiceControlInfo;
import com.dofun.carsetting.d.n;
import com.dofun.carsetting.f.q;
import com.dofun.carsetting.g.t;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class VoicePresenter extends b<t, n> {
    public VoiceControlInfo d;
    private VoiceReceiver e;
    private boolean f;

    public VoicePresenter(Context context) {
        PackageInfo packageInfo;
        super(context);
        this.d = new VoiceControlInfo();
        this.e = new VoiceReceiver();
        this.f = false;
        try {
            packageInfo = this.f389a.getPackageManager().getPackageInfo("com.dofun.bridge", 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        if (packageInfo == null || packageInfo.versionCode < 60) {
            return;
        }
        Log.e("InternetPresenter", "VoicePresenter:dofun bridge versionCode: " + packageInfo.versionCode);
        this.f = true;
    }

    @Override // com.dofun.carsetting.presenter.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public n i() {
        return n.a();
    }

    public ComponentName b() throws Throwable {
        ComponentName componentName = new ComponentName("", "");
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/speech_name"));
                try {
                    ComponentName componentName2 = new ComponentName(bufferedReader2.readLine(), bufferedReader2.readLine());
                    try {
                        bufferedReader2.close();
                    } catch (Exception unused) {
                    }
                    return componentName2;
                } catch (Exception unused2) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return componentName;
                    }
                    bufferedReader.close();
                    return componentName;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused3) {
                return componentName;
            }
        } catch (Exception unused4) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v7 */
    public void a(ComponentName componentName) throws Throwable {
        if (componentName != null) {
            ?? r3 = 0;
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    try {
                        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("/data/tw/speech_name"));
                        try {
                            bufferedWriter2.write(componentName.getPackageName());
                            bufferedWriter2.write(10);
                            bufferedWriter2.write(componentName.getClassName());
                            bufferedWriter2.write(10);
                            bufferedWriter2.flush();
                            bufferedWriter2.close();
                        } catch (Exception unused) {
                            bufferedWriter = bufferedWriter2;
                            new File("/data/tw/speech_name").delete();
                            if (bufferedWriter != null) {
                                bufferedWriter.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            r3 = bufferedWriter2;
                            if (r3 != 0) {
                                r3.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception unused2) {
                }
                r3 = "/data/tw/speech_name";
                FileUtils.setPermissions("/data/tw/speech_name", 438, -1, -1);
            } catch (Exception unused3) {
            }
        }
    }

    public synchronized void c() {
        if (!TextUtils.isEmpty(r())) {
            b(r());
        } else {
            this.d.speed = "0.85";
            this.d.sensitivity = "1";
            this.d.awakeable = true;
            this.d.micIconShow = true;
            this.d.musicSoftware = "";
            this.d.radioSoftware = "";
            this.d.mapSoftware = "";
            this.d.micIcon = "0";
        }
    }

    private String r() {
        return com.dofun.carsetting.f.k.b(this.f389a, "voice_config", (String) null);
    }

    public void a(String str) {
        com.dofun.carsetting.f.g.a().b("InternetPresenter", "save data " + str);
        com.dofun.carsetting.f.k.a(this.f389a, "voice_config", str);
    }

    public synchronized void b(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                this.d.speed = (TextUtils.isEmpty(jSONObject.optString("speed")) || "null".equals(jSONObject.optString("speed"))) ? "0.85" : jSONObject.optString("speed");
                this.d.micIcon = jSONObject.optString("micIcon");
                this.d.sensitivity = (TextUtils.isEmpty(jSONObject.optString("sensitivity")) || "null".equals(jSONObject.optString("sensitivity"))) ? "1" : jSONObject.optString("sensitivity");
                String strOptString = jSONObject.optString("musicSoftware");
                String strOptString2 = jSONObject.optString("FMSoftware");
                String strOptString3 = jSONObject.optString("MapSoftware");
                this.d.awakeable = jSONObject.optBoolean("awakeable", true);
                this.d.micIconShow = jSONObject.optBoolean("micIconShow", true);
                this.d.canQuickAwake = jSONObject.optBoolean("canQuickAwake", true);
                this.d.screenControl = jSONObject.optBoolean("screenControl", false);
                if ((TextUtils.isEmpty(strOptString) || "null".equals(strOptString)) && e().size() > 0) {
                    this.d.musicSoftware = "";
                } else if (!this.f && TextUtils.equals(strOptString, "cn.cardoor.kugou")) {
                    this.d.musicSoftware = "";
                } else {
                    this.d.musicSoftware = strOptString;
                }
                if (TextUtils.isEmpty(strOptString2) || ("null".equals(strOptString2) && f().size() > 0)) {
                    this.d.radioSoftware = "";
                } else {
                    this.d.radioSoftware = strOptString2;
                }
                if (TextUtils.isEmpty(strOptString3) || ("null".equals(strOptString3) && g().size() > 0)) {
                    this.d.mapSoftware = "";
                } else {
                    this.d.mapSoftware = strOptString3;
                }
                Log.d("InternetPresenter", "parseVoiceConfig: " + str);
                a(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void d() {
        Log.d("InternetPresenter", "sendCarSettingToVoice: " + this.d.toString());
        a(this.d.toString());
        q.a().a("com.dofun.intent.action.CAR_SETTING", "voice_config", this.d.toString());
    }

    public List<String> e() {
        ArrayList arrayList = new ArrayList();
        if (com.dofun.carsetting.f.a.a().b("cn.kuwo.kwmusiccar")) {
            arrayList.add("cn.kuwo.kwmusiccar");
        }
        if (com.dofun.carsetting.f.a.a().b("com.hongfans.rearview")) {
            arrayList.add("com.hongfans.rearview");
        }
        if (this.f && com.dofun.carsetting.f.a.a().b("cn.cardoor.kugou")) {
            arrayList.add("cn.cardoor.kugou");
        }
        return arrayList;
    }

    public List<String> f() {
        ArrayList arrayList = new ArrayList();
        if (com.dofun.carsetting.f.a.a().b("com.edog.car")) {
            arrayList.add("com.edog.car");
        }
        if (com.dofun.carsetting.f.a.a().b("com.hongfans.rearview")) {
            arrayList.add("com.hongfans.rearview");
        }
        return arrayList;
    }

    public List<String> g() {
        ArrayList arrayList = new ArrayList();
        if (com.dofun.carsetting.f.a.a().b("com.autonavi.amapauto")) {
            arrayList.add("com.autonavi.amapauto");
        }
        if (com.dofun.carsetting.f.a.a().b("com.baidu.BaiduMap")) {
            arrayList.add("com.baidu.BaiduMap");
        }
        if (com.dofun.carsetting.f.a.a().b("com.baidu.BaiduMap.auto")) {
            arrayList.add("com.baidu.BaiduMap.auto");
        }
        return arrayList;
    }

    public ResolveInfo c(String str) {
        ResolveInfo resolveInfo = null;
        if (str == null) {
            return null;
        }
        PackageManager packageManager = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        if (listQueryIntentActivities != null && listQueryIntentActivities.size() > 0) {
            resolveInfo = listQueryIntentActivities.get(0);
        }
        Log.d("InternetPresenter", "getAppByPackageName: " + str);
        return resolveInfo;
    }

    public ResolveInfo h() {
        return c(this.d.mapSoftware);
    }

    public ResolveInfo m() {
        return c(this.d.musicSoftware);
    }

    public ResolveInfo n() {
        return c(this.d.radioSoftware);
    }

    public String d(String str) {
        PackageManager packageManager = this.f389a.getPackageManager();
        ResolveInfo resolveInfoC = c(str);
        return resolveInfoC != null ? (String) resolveInfoC.loadLabel(packageManager) : "";
    }

    public Drawable e(String str) {
        PackageManager packageManager = this.f389a.getPackageManager();
        ResolveInfo resolveInfoC = c(str);
        if (resolveInfoC != null) {
            return resolveInfoC.loadIcon(packageManager);
        }
        return null;
    }

    public class VoiceReceiver extends BroadcastReceiver {
        public VoiceReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("InternetPresenter", "VoiceReceiver:action:" + action);
            if ("com.dofun.intent.action.VOICE_SETTING".equals(action)) {
                VoicePresenter.this.b(intent.getStringExtra("voice_config"));
            } else if ("com.dofun.intent.action.GET_CAR_SETTING".equals(action)) {
                VoicePresenter.this.d();
            }
        }
    }

    public void o() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.dofun.intent.action.VOICE_SETTING");
        intentFilter.addAction("com.dofun.intent.action.GET_CAR_SETTING");
        this.f389a.registerReceiver(this.e, intentFilter);
    }

    public void p() {
        this.f389a.unregisterReceiver(this.e);
    }

    public List<AppInfo> q() {
        PackageManager packageManager = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            if ((resolveInfo.activityInfo.applicationInfo.flags & 1) == 0 || TextUtils.equals(resolveInfo.activityInfo.packageName, "com.google.android.googlequicksearchbox")) {
                AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
                appInfo.appName = (String) resolveInfo.loadLabel(packageManager);
                appInfo.appIcon = resolveInfo.loadIcon(packageManager);
                appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                arrayList.add(appInfo);
            }
        }
        Log.d("InternetPresenter", "loadAppByVoice: " + arrayList.size());
        return arrayList;
    }
}
