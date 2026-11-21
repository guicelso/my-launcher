package com.dofun.carsetting.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.f.q;
import com.dofun.carsetting.f.u;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: AppSelectPresenter.java */
/* JADX INFO: loaded from: classes.dex */
public class a extends b<com.dofun.carsetting.g.a, com.dofun.carsetting.d.a> {
    private PackageManager d;
    private u e;
    private List<String> f;
    private List<String> g;
    private List<String> h;
    private List<AppInfo> i;
    private boolean j;
    private Handler k;

    public a(Context context) {
        super(context);
        this.d = null;
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = new ArrayList();
        this.i = new ArrayList();
        this.j = false;
        this.k = new Handler() { // from class: com.dofun.carsetting.presenter.a.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                String str;
                try {
                    if (message.what == 65280 && (str = (String) message.obj) != null) {
                        for (int i = 0; i < a.this.i.size(); i++) {
                            if (!TextUtils.equals(((AppInfo) a.this.i.get(i)).componentName.getPackageName(), str)) {
                                a.this.a(((AppInfo) a.this.i.get(i)).componentName.getPackageName(), false);
                            } else {
                                a.this.a(str, true);
                                Settings.System.putString(MyApp.a().getContentResolver(), "DEFAULT_PHONE_CONNECT_APP", str);
                            }
                        }
                        a.this.g();
                    }
                } catch (Exception unused) {
                }
            }
        };
        m();
        this.e = u.a();
    }

    @Override // com.dofun.carsetting.presenter.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.d.a i() {
        return com.dofun.carsetting.d.a.a();
    }

    private void m() {
        PackageInfo packageInfo;
        this.f.add("com.autonavi.amapauto");
        this.f.add("com.baidu.BaiduMap");
        this.f.add("com.baidu.BaiduMap.auto");
        this.f.add("com.baidu.naviauto");
        this.g.add("cn.kuwo.kwmusiccar");
        this.g.add("com.hongfans.rearview");
        try {
            packageInfo = this.f389a.getPackageManager().getPackageInfo("com.dofun.bridge", 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        if (packageInfo != null && packageInfo.versionCode >= 60) {
            Log.e("InternetPresenter", "initPackageList:dofun bridge versionCode: " + packageInfo.versionCode);
            this.g.add("cn.cardoor.kugou");
        }
        this.h.add("com.edog.car");
        this.h.add("com.hongfans.rearview");
    }

    public void a(String str) {
        q.a().a("action.setting.default.phone.connect.app", "packageName", str);
        Message messageObtainMessage = this.k.obtainMessage();
        messageObtainMessage.what = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        messageObtainMessage.obj = str;
        this.k.removeMessages(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        this.k.sendMessageDelayed(messageObtainMessage, 1500L);
    }

    public void b() {
        this.d = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
            appInfo.appName = (String) resolveInfo.loadLabel(this.d);
            appInfo.appIcon = resolveInfo.loadIcon(this.d);
            appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            arrayList.add(appInfo);
        }
        j().a(arrayList);
    }

    public void c() {
        this.d = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            if ((resolveInfo.activityInfo.applicationInfo.flags & 1) == 0) {
                AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
                appInfo.appName = (String) resolveInfo.loadLabel(this.d);
                appInfo.appIcon = resolveInfo.loadIcon(this.d);
                appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                arrayList.add(appInfo);
            }
        }
        j().a(arrayList);
    }

    public void b(String str) {
        try {
            switch (str) {
                case "ALL":
                    b();
                    break;
                case "NAVI":
                    d();
                    break;
                case "MUSIC":
                    e();
                    break;
                case "RADIO":
                    f();
                    break;
                case "CONNECT":
                    g();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void d() {
        this.d = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
            appInfo.appName = (String) resolveInfo.loadLabel(this.d);
            appInfo.appIcon = resolveInfo.loadIcon(this.d);
            appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            if (this.f.contains(appInfo.componentName.getPackageName())) {
                arrayList.add(appInfo);
            }
        }
        j().a(arrayList);
    }

    public void e() {
        this.d = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
            appInfo.appName = (String) resolveInfo.loadLabel(this.d);
            appInfo.appIcon = resolveInfo.loadIcon(this.d);
            appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            if (this.g.contains(appInfo.componentName.getPackageName())) {
                arrayList.add(appInfo);
            }
        }
        j().a(arrayList);
    }

    public void f() {
        this.d = this.f389a.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : this.d.queryIntentActivities(intent, 0)) {
            AppInfo appInfo = new AppInfo(resolveInfo, (String) null, (Drawable) null);
            appInfo.appName = (String) resolveInfo.loadLabel(this.d);
            appInfo.appIcon = resolveInfo.loadIcon(this.d);
            appInfo.componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            if (this.h.contains(appInfo.componentName.getPackageName())) {
                arrayList.add(appInfo);
            }
        }
        j().a(arrayList);
    }

    public void g() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        AppInfo appInfo = new AppInfo(new ComponentName("net.easyconn", "net.easyconn.ui.MainActivity"), this.f389a.getString(R.string.easy_conn), this.f389a.getDrawable(R.drawable.ic_easy_conn));
        appInfo.installed = c("net.easyconn") > -1;
        if (appInfo.installed) {
            arrayList.add(appInfo);
        }
        AppInfo appInfo2 = new AppInfo(new ComponentName("com.zjinnova.zlink", "com.suding.speedplay.ui.MainActivity"), this.f389a.getString(R.string.zlink), this.f389a.getDrawable(R.drawable.ic_carplay));
        appInfo2.installed = c("com.zjinnova.zlink") > -1;
        if (appInfo2.installed) {
            arrayList.add(appInfo2);
        }
        AppInfo appInfo3 = new AppInfo(new ComponentName("cn.cardoor.hicar", "cn.cardoor.hicar.activity.DeviceManagerActivity"), this.f389a.getString(R.string.hi_car), this.f389a.getDrawable(R.drawable.ic_hicar));
        appInfo3.installed = c("cn.cardoor.hicar") > -1;
        if (appInfo3.installed) {
            arrayList.add(appInfo3);
        }
        this.i.clear();
        this.i.addAll(arrayList);
        j().a(arrayList);
    }

    public AppInfo h() {
        String string = Settings.System.getString(this.f389a.getContentResolver(), "DEFAULT_PHONE_CONNECT_APP");
        if (this.i.size() == 0 || TextUtils.isEmpty(string)) {
            return null;
        }
        for (int i = 0; i < this.i.size(); i++) {
            if (TextUtils.equals(this.i.get(i).componentName.getPackageName(), string)) {
                return this.i.get(i);
            }
        }
        return null;
    }

    public void a(String str, boolean z) {
        try {
            PackageManager packageManager = this.f389a.getPackageManager();
            if (z) {
                packageManager.setApplicationEnabledSetting(str, 1, 0);
                SystemProperties.set("persist.phone_connect_app", str);
            } else {
                packageManager.setApplicationEnabledSetting(str, 2, 0);
            }
        } catch (Exception unused) {
        }
    }

    public int c(String str) {
        try {
            return this.f389a.getPackageManager().getApplicationEnabledSetting(str);
        } catch (Exception unused) {
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v8 */
    public void a(String str, List<AppInfo> list) throws Throwable {
        ?? bufferedWriter;
        Throwable th;
        try {
            this.e.write(40730, 3, 0, "rm -r " + str);
            if (!new File("/sdcard/.Tcfg/").exists()) {
                this.e.write(40730, 3, 0, "mkdir /sdcard/.Tcfg");
            }
            ?? r5 = 0;
            ?? r6 = 0;
            try {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(str));
                    try {
                        for (AppInfo appInfo : list) {
                            if (appInfo.selected) {
                                Log.d("InternetPresenter", "saveAppList: " + appInfo.appName);
                                bufferedWriter.write(appInfo.mInfo.activityInfo.packageName);
                                bufferedWriter.write(10);
                            }
                        }
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (Exception unused) {
                        r6 = bufferedWriter;
                        new File(str).delete();
                        if (r6 != 0) {
                            r6.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedWriter != 0) {
                            bufferedWriter.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    bufferedWriter = r5;
                    th = th3;
                }
            } catch (Exception unused2) {
            }
            r5 = 438;
            FileUtils.setPermissions(str, 438, -1, -1);
        } catch (Exception unused3) {
        }
    }

    public List<String> d(String str) throws Throwable {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                    while (true) {
                        try {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                break;
                            }
                            arrayList.add(line);
                        } catch (Exception e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }
}
