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
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.MyListView;
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/* JADX INFO: compiled from: VideoOutSettingFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class ab extends ad<com.dofun.carsetting.presenter.b> implements View.OnClickListener, com.dofun.carsetting.g.j {
    private MyListView b;
    private com.dofun.carsetting.adapter.e c;
    private LinearLayout g;
    private LinearLayout k;
    private InfoPreference l;
    private com.dofun.carsetting.f.u o;
    private ArrayList<AppInfo> d = new ArrayList<>();
    private String e = "";
    private String f = "";
    private int m = 0;
    private int n = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f249a = "/soc@03000000";
    private Handler p = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.fragment.ab.2
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                if (message.what == 65280) {
                    ab.this.o.write(514, 1, 0);
                }
            } catch (Exception e) {
                Log.e("VideoOutSettingFragment", Log.getStackTraceString(e));
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
        View viewInflate = layoutInflater.inflate(R.layout.fragment_video_out_setting, (ViewGroup) null);
        this.o = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        c();
        this.g = (LinearLayout) view.findViewById(R.id.iv_back);
        this.g.setOnClickListener(this);
        this.k = (LinearLayout) view.findViewById(R.id.ll_list);
        this.b = (MyListView) view.findViewById(R.id.list_guide_soft);
        this.c = new com.dofun.carsetting.adapter.e(getActivity().getLayoutInflater());
        this.b.setAdapter((ListAdapter) this.c);
        this.b.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.dofun.carsetting.fragment.ab.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) throws Throwable {
                for (int i2 = 0; i2 < ab.this.d.size(); i2++) {
                    if (i2 == i) {
                        ((AppInfo) ab.this.d.get(i2)).setChoose(true);
                        ab.this.e = ((AppInfo) ab.this.d.get(i2)).getCurrentName();
                        ab.this.f = ((AppInfo) ab.this.d.get(i2)).getPackageName();
                    } else {
                        ((AppInfo) ab.this.d.get(i2)).setChoose(false);
                    }
                }
                ab.this.a();
                if (ab.this.d.size() > 0) {
                    ab.this.c.a(ab.this.d);
                    ab.this.k.setVisibility(0);
                }
            }
        });
        this.l = (InfoPreference) view.findViewById(R.id.ip_video_out_setting_t5q);
        this.l.setOnPreferenceChangeListener(this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.b.G()) {
            this.f249a = "/soc";
        }
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        PackageManager packageManager = getActivity().getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(intent, 0)) {
            try {
                String str = resolveInfo.activityInfo.packageName;
                if ((resolveInfo.activityInfo.applicationInfo.flags & 1) != 1) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(str);
                    appInfo.setCurrentName(resolveInfo.activityInfo.name);
                    appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                    appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                    this.d.add(appInfo);
                }
            } catch (Exception unused) {
            }
        }
        for (AppInfo appInfo2 : this.d) {
            if (appInfo2.getCurrentName().equals(this.e) && appInfo2.getPackageName().equals(this.f)) {
                appInfo2.setChoose(true);
            } else {
                appInfo2.setChoose(false);
            }
        }
        if (this.d.size() > 0) {
            this.c.a(this.d);
            this.k.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.G()) {
            this.l.a(getResources().getStringArray(R.array.lcd_type3_entries), getResources().getStringArray(R.array.lcd_type_valuses));
            if (new File("/sys/class/hdmi").exists()) {
                if (com.dofun.carsetting.f.b.c("/sys/class/hdmi/hdmi/attr/cvbs_used").equals("1")) {
                    this.l.setValue("2");
                    SystemProperties.set("persist.sys.video_out", "2");
                    return;
                } else {
                    this.l.setValue("1");
                    SystemProperties.set("persist.sys.video_out", "1");
                    return;
                }
            }
            this.l.setValue("0");
            SystemProperties.set("persist.sys.video_out", "0");
            return;
        }
        this.n = com.dofun.carsetting.f.b.b("/sys/class/boot_version/cpuversion");
        this.m = this.o.write(65523, 0, 0, "disp\nvideo_out");
        if (this.m == 2 && this.n == 2) {
            this.m = 0;
        }
        if (this.n == 2) {
            this.l.a(getResources().getTextArray(R.array.video_out_entries_ch_t5), getResources().getTextArray(R.array.video_out_values));
        }
        this.l.setValue(String.valueOf(this.m));
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
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("/data/tw/video_output_name"));
                    try {
                        bufferedWriter2.write(this.f);
                        bufferedWriter2.write(10);
                        bufferedWriter2.write(this.e);
                        bufferedWriter2.write(10);
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (Exception unused) {
                        bufferedWriter = bufferedWriter2;
                        new File("/data/tw/video_output_name").delete();
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
                FileUtils.setPermissions("/data/tw/video_output_name", 438, -1, -1);
                this.o.write(40730, 1, 0, "sync");
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
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/video_output_name"));
                try {
                    this.f = bufferedReader2.readLine();
                    this.e = bufferedReader2.readLine();
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

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        try {
            if (twPreference.getId() == R.id.ip_video_out_setting_t5q) {
                if (com.dofun.carsetting.f.b.G()) {
                    String str = "";
                    switch (Integer.valueOf((String) obj).intValue()) {
                        case 0:
                            str = ("fdt set /soc/hdmi status disabled \n") + "fdt set /soc/hdmi cvbs_used <0> \n";
                            SystemProperties.set("persist.sys.video_out", "0");
                            break;
                        case 1:
                            str = ("fdt set /soc/hdmi status okay \n") + "fdt set /soc/hdmi cvbs_used <0> \n";
                            SystemProperties.set("persist.sys.video_out", "1");
                            break;
                        case 2:
                            str = ("fdt set /soc/hdmi status okay \n") + "fdt set /soc/hdmi cvbs_used <1> \n";
                            SystemProperties.set("persist.sys.video_out", "2");
                            break;
                    }
                    this.o.write(65282, 0, 0, "run_command\n" + str + "\nfdt save");
                } else {
                    int i = Integer.parseInt((String) obj);
                    this.o.write(65282, 0, 0, String.format("run_command\nfdt set " + this.f249a + "/disp@01000000 video_out <0x%x>\nfdt save", Integer.valueOf(i)));
                }
            }
        } catch (Exception e) {
            Log.e("VideoOutSettingFragment", "onPreferenceChange: " + e.getMessage());
        }
        return false;
    }
}
