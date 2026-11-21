package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.MyListView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/* JADX INFO: compiled from: NaviChooseFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class n extends ad<com.dofun.carsetting.presenter.b> implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private MyListView f324a;
    private com.dofun.carsetting.adapter.e b;
    private LinearLayout f;
    private LinearLayout g;
    private com.dofun.carsetting.f.u k;
    private ArrayList<AppInfo> c = new ArrayList<>();
    private String d = "";
    private String e = "";
    private Handler l = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.n.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                if (message.what == 65280) {
                    n.this.k.write(514, 1, 0);
                }
            } catch (Exception e) {
                Log.e("NaviChooseFragment", Log.getStackTraceString(e));
            }
            return false;
        }
    });

    @Override // com.dofun.carsetting.fragment.ad
    public com.dofun.carsetting.presenter.b d() {
        return null;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_navichoose, (ViewGroup) null);
        this.k = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        c();
        this.f = (LinearLayout) view.findViewById(R.id.iv_back);
        this.f.setOnClickListener(this);
        this.g = (LinearLayout) view.findViewById(R.id.ll_list);
        this.f324a = (MyListView) view.findViewById(R.id.list_guide_soft);
        this.b = new com.dofun.carsetting.adapter.e(getActivity().getLayoutInflater());
        this.f324a.setAdapter((ListAdapter) this.b);
        this.f324a.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.fragment.n.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) throws Throwable {
                boolean z = !TextUtils.isEmpty(n.this.d) && TextUtils.equals(n.this.d, "com.nng.igo.primong.igoworld");
                for (int i2 = 0; i2 < n.this.c.size(); i2++) {
                    if (i2 == i) {
                        ((AppInfo) n.this.c.get(i2)).setChoose(true);
                        n.this.d = ((AppInfo) n.this.c.get(i2)).getCurrentName();
                        n.this.e = ((AppInfo) n.this.c.get(i2)).getPackageName();
                    } else {
                        ((AppInfo) n.this.c.get(i2)).setChoose(false);
                    }
                }
                n.this.a();
                if (n.this.c.size() > 0) {
                    n.this.b.a(n.this.c);
                    n.this.g.setVisibility(0);
                }
                if (z || TextUtils.equals(n.this.e, "com.nng.igo.primong.igoworld")) {
                    com.dofun.carsetting.f.a.a(n.this.e);
                }
            }
        });
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        PackageManager packageManager = getActivity().getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            try {
                String str = SystemProperties.get("ro.com.google.gmsversion", "");
                String str2 = resolveInfo.activityInfo.packageName;
                if (!"com.zjinnova.zlink".equals(str2) && ("com.google.android.apps.maps".equals(str2) || "com.baidu.BaiduMap".equals(str2) || "com.baidu.BaiduMap.auto".equals(str2) || "com.baidu.naviauto".equals(str2) || "com.tencent.map".equals(str2) || "com.tencent.wecarnavi".equals(str2) || "cld.navi.kgomap".equals(str2) || "cld.navi.c3551.mainframe".equals(str2) || "cld.navi.c2739.mainframe".equals(str2) || "com.autonavi.amapauto".equals(str2) || "com.sea.s1OBU".equals(str2) || ((str2 != null && str2.startsWith("com.cld")) || (!TextUtils.isEmpty(str) && (1 & resolveInfo.activityInfo.applicationInfo.flags) == 0)))) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(str2);
                    appInfo.setCurrentName(resolveInfo.activityInfo.name);
                    appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                    appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                    this.c.add(appInfo);
                }
            } catch (Exception unused) {
            }
        }
        for (AppInfo appInfo2 : this.c) {
            if (appInfo2.getCurrentName().equals(this.d) && appInfo2.getPackageName().equals(this.e)) {
                appInfo2.setChoose(true);
            } else {
                appInfo2.setChoose(false);
            }
        }
        if (this.c.size() > 0) {
            this.b.a(this.c);
            this.g.setVisibility(0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    public void a() throws Throwable {
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("/data/tw/navi_name"));
                    try {
                        bufferedWriter2.write(this.e);
                        bufferedWriter2.write(10);
                        bufferedWriter2.write(this.d);
                        bufferedWriter2.write(10);
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        new File("/data/tw/navi_name").delete();
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                }
                FileUtils.setPermissions("/data/tw/navi_name", 438, -1, -1);
                this.k.write(40730, 1, 0, "sync");
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
    }

    public void c() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/navi_name"));
                try {
                    this.e = bufferedReader2.readLine();
                    this.d = bufferedReader2.readLine();
                    bufferedReader2.close();
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
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
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
