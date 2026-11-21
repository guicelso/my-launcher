package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.SystemProperties;
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

/* JADX INFO: compiled from: NetMusicChooseFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends ad<com.dofun.carsetting.presenter.j> implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private MyListView f327a;
    private com.dofun.carsetting.adapter.e b;
    private ArrayList<AppInfo> c = new ArrayList<>();
    private String d = "";
    private String e = "";
    private LinearLayout f;
    private com.dofun.carsetting.f.u g;

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.j d() {
        return new com.dofun.carsetting.presenter.j(getActivity());
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_netmusicchoose, (ViewGroup) null);
        this.g = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        f();
        this.f = (LinearLayout) view.findViewById(R.id.iv_back);
        this.f.setOnClickListener(this);
        this.f327a = (MyListView) view.findViewById(R.id.list_guide_soft);
        this.b = new com.dofun.carsetting.adapter.e(getActivity().getLayoutInflater());
        this.f327a.setAdapter((ListAdapter) this.b);
        this.f327a.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.fragment.o.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) throws Throwable {
                for (int i2 = 0; i2 < o.this.c.size(); i2++) {
                    if (i2 == i) {
                        ((AppInfo) o.this.c.get(i2)).setChoose(true);
                        o.this.d = ((AppInfo) o.this.c.get(i2)).getCurrentName();
                        o.this.e = ((AppInfo) o.this.c.get(i2)).getPackageName();
                        SystemProperties.set("persist.tw.netmusic", o.this.e);
                    } else {
                        ((AppInfo) o.this.c.get(i2)).setChoose(false);
                    }
                    o.this.c();
                }
                o.this.b.a(o.this.c);
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
                String str = resolveInfo.activityInfo.packageName;
                if ((1 & resolveInfo.activityInfo.applicationInfo.flags) == 0) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(str);
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
        this.b.a(this.c);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    public void c() throws Throwable {
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("/data/tw/netmusic_name"));
                    try {
                        bufferedWriter2.write(this.e);
                        bufferedWriter2.write(10);
                        bufferedWriter2.write(this.d);
                        bufferedWriter2.write(10);
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        new File("/data/tw/netmusic_name").delete();
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
                SystemProperties.set("persist.tw.netmusic", this.e);
                FileUtils.setPermissions("/data/tw/netmusic_name", 438, -1, -1);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
    }

    public void f() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/netmusic_name"));
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
