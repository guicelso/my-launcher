package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
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
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/* JADX INFO: compiled from: AppChooseFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends ad<com.dofun.carsetting.presenter.b> implements View.OnClickListener, com.dofun.carsetting.g.j {
    private MyListView b;
    private com.dofun.carsetting.adapter.e c;
    private LinearLayout k;
    private LinearLayout l;
    private com.dofun.carsetting.f.u m;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f255a = "/data/tw/app_autostart_name";
    private ArrayList<AppInfo> d = new ArrayList<>();
    private String e = "";
    private String f = "";
    private String g = "";
    private Handler n = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.b.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                if (message.what == 65280) {
                    b.this.m.write(514, 1, 0);
                }
            } catch (Exception e) {
                Log.e("AppChooseFragment", Log.getStackTraceString(e));
            }
            return false;
        }
    });

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        return false;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public com.dofun.carsetting.presenter.b d() {
        return null;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_appchoose, (ViewGroup) null);
        this.m = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        c();
        this.k = (LinearLayout) view.findViewById(R.id.iv_back);
        this.k.setOnClickListener(this);
        this.l = (LinearLayout) view.findViewById(R.id.ll_list);
        this.b = (MyListView) view.findViewById(R.id.list_guide_soft);
        this.c = new com.dofun.carsetting.adapter.e(getActivity().getLayoutInflater());
        this.b.setAdapter((ListAdapter) this.c);
        this.b.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.fragment.b.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) throws Throwable {
                int i2 = 0;
                for (int i3 = 0; i3 < b.this.d.size(); i3++) {
                    if (i3 == i) {
                        if (((AppInfo) b.this.d.get(i3)).isChoose()) {
                            ((AppInfo) b.this.d.get(i3)).setChoose(false);
                            b.this.f = "";
                            b.this.g = "";
                            b.this.e = "";
                        } else {
                            ((AppInfo) b.this.d.get(i3)).setChoose(true);
                            b.this.f = ((AppInfo) b.this.d.get(i3)).getCurrentName();
                            b.this.g = ((AppInfo) b.this.d.get(i3)).getPackageName();
                            b.this.e = ((AppInfo) b.this.d.get(i3)).getAppName();
                            i2 = 1;
                        }
                    } else {
                        ((AppInfo) b.this.d.get(i3)).setChoose(false);
                    }
                }
                b.this.a();
                Settings.System.putInt(b.this.i.getContentResolver(), "SYSTEM_APP2", i2);
                if (b.this.d.size() > 0) {
                    b.this.c.a(b.this.d);
                    b.this.l.setVisibility(0);
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
                String str = resolveInfo.activityInfo.packageName;
                AppInfo appInfo = new AppInfo();
                appInfo.setPackageName(str);
                appInfo.setCurrentName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                this.d.add(appInfo);
            } catch (Exception unused) {
            }
        }
        int i = 0;
        for (AppInfo appInfo2 : this.d) {
            if (appInfo2.getCurrentName().equals(this.f) && appInfo2.getPackageName().equals(this.g)) {
                appInfo2.setChoose(true);
                i = 1;
            } else {
                appInfo2.setChoose(false);
            }
        }
        Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_APP2", i);
        if (this.d.size() > 0) {
            this.c.a(this.d);
            this.l.setVisibility(0);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.BufferedWriter] */
    public void a() throws Throwable {
        ?? bufferedWriter;
        ?? r0 = 0;
        ?? r1 = 0;
        try {
            try {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(this.f255a));
                    try {
                        bufferedWriter.write(this.g);
                        bufferedWriter.write(10);
                        bufferedWriter.write(this.f);
                        bufferedWriter.write(10);
                        bufferedWriter.write(this.e);
                        bufferedWriter.write(10);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (Exception unused) {
                        r1 = bufferedWriter;
                        new File(this.f255a).delete();
                        if (r1 != 0) {
                            r1.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedWriter != 0) {
                            bufferedWriter.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                    return;
                }
            } catch (Exception unused3) {
            }
            FileUtils.setPermissions(this.f255a, 438, -1, -1);
            r0 = 40730;
            this.m.write(40730, 1, 0, "sync");
        } catch (Throwable th2) {
            th = th2;
            bufferedWriter = r0;
        }
    }

    public void c() throws Throwable {
        BufferedReader bufferedReader;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader(this.f255a));
                try {
                    this.g = bufferedReader.readLine();
                    this.f = bufferedReader.readLine();
                    this.e = bufferedReader.readLine();
                } catch (Exception unused) {
                    if (bufferedReader == null) {
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                return;
            }
        } catch (Exception unused3) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        bufferedReader.close();
    }
}
