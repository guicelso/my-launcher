package com.dofun.carsetting.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;

/* JADX INFO: compiled from: GeneralFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class k extends ad<com.dofun.carsetting.presenter.f> implements com.dofun.carsetting.g.i, com.dofun.carsetting.g.j {
    private static String F;
    private InfoPreference A;
    private InfoPreference B;
    private InfoPreference C;
    private InfoPreference D;
    private InfoPreference E;
    private ProgressDialog G;
    private InfoPreference H;
    private InfoPreference I;
    private InfoPreference J;
    private InfoPreference K;
    private InfoPreference L;
    private InfoPreference M;
    private InfoPreference N;
    private InfoPreference O;
    private InfoPreference P;
    private InfoPreference Q;
    private SwitchPreference R;
    private InfoPreference S;
    private InfoPreference T;
    private SwitchPreference U;
    private TextView V;
    private LinearLayout W;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String[] f311a;
    String[] b;
    private com.dofun.carsetting.f.u c;
    private InfoPreference d;
    private InfoPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
    private SwitchPreference m;
    private SwitchPreference n;
    private SwitchPreference o;
    private InfoPreference p;
    private InfoPreference q;
    private InfoPreference r;
    private InfoPreference s;
    private InfoPreference t;
    private InfoPreference u;
    private InfoPreference v;
    private InfoPreference w;
    private InfoPreference x;
    private InfoPreference y;
    private InfoPreference z;
    private String X = "0";
    private String Y = "";
    private int Z = 2;

    @SuppressLint({"HandlerLeak"})
    private Handler aa = new Handler() { // from class: com.dofun.carsetting.fragment.k.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i != 260) {
                    if (i == 40479) {
                        ArrayList arrayListK = k.this.k();
                        k.this.f311a = new String[arrayListK.size()];
                        k.this.b = new String[arrayListK.size()];
                        for (int i2 = 0; i2 < k.this.f311a.length; i2++) {
                            k.this.f311a[i2] = (String) arrayListK.get(i2);
                            k.this.b[i2] = String.valueOf(i2);
                        }
                        if (com.dofun.carsetting.f.a.a().b("com.tw.bug.report")) {
                            return;
                        }
                        k.this.z.d();
                        k.this.z.a(k.this.f311a, k.this.b);
                        k.this.z.setSaveValue(false);
                        return;
                    }
                    if (i == 65280) {
                        if (k.this.G != null) {
                            k.this.G.dismiss();
                            return;
                        }
                        return;
                    }
                    switch (i) {
                        case 265:
                            break;
                        case 266:
                            if (message.arg1 == 0 && TextUtils.isEmpty(k.this.Y)) {
                                k.this.Y = (String) message.obj;
                                if (k.this.Y != null && k.this.Y.length() > 0) {
                                    try {
                                        String str = k.this.Y.split("-")[3];
                                        int i3 = Integer.parseInt(str.substring(4, 6), 16);
                                        Integer.parseInt(str.substring(0, 2), 16);
                                        if ((i3 & 128) == 128) {
                                            k.this.y.setVisibility(8);
                                            k.this.w.setVisibility(0);
                                        } else {
                                            k.this.y.setVisibility(0);
                                            k.this.w.setVisibility(8);
                                        }
                                        if (com.dofun.carsetting.f.b.T()) {
                                            k.this.w.setVisibility(8);
                                        }
                                    } catch (Exception unused) {
                                        return;
                                    }
                                    break;
                                }
                            }
                            break;
                        case 267:
                            byte[] bArr = (byte[]) message.obj;
                            boolean z = true;
                            k.this.d.setValue(String.valueOf((int) bArr[1]));
                            k.this.e.setValue(String.valueOf((int) bArr[4]));
                            SwitchPreference switchPreference = k.this.U;
                            if (bArr[18] != 1) {
                                z = false;
                            }
                            switchPreference.setValue(z);
                            k.this.k.setValue(String.valueOf((int) bArr[19]));
                            break;
                        default:
                            break;
                    }
                }
                byte[] bArr2 = (byte[]) message.obj;
                if (bArr2.length > 8) {
                    k.this.O.setValue(String.valueOf(bArr2[8] & 255));
                } else {
                    k.this.O.setValue(String.valueOf(bArr2[5] & 255));
                }
                if (k.this.c.write(65521) != 533) {
                    k.this.w.setValue(String.valueOf(bArr2[5] & 255));
                    k.this.y.setValue(String.valueOf(bArr2[5] & 255));
                }
                if (bArr2.length > 12) {
                    k.this.T.setValue(String.valueOf(bArr2[12] & 255));
                } else {
                    k.this.T.setValue("5");
                }
                k.this.l.setValue(String.valueOf((int) ((byte[]) message.obj)[0]));
            } catch (Exception e) {
                Log.e("GeneralFragment", "[" + com.dofun.carsetting.f.g.a(e) + "]" + e.getMessage());
            }
        }
    };
    private BroadcastReceiver ab = new BroadcastReceiver() { // from class: com.dofun.carsetting.fragment.k.6
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            k.this.R.setValue(Settings.System.getInt(MyApp.a().getContentResolver(), "FLOATING_SWITCH", 0) == 1);
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_general, (ViewGroup) null);
        this.c = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.f d() {
        return new com.dofun.carsetting.presenter.f(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        this.d = (InfoPreference) view.findViewById(R.id.brake_mode);
        this.d.setOnPreferenceChangeListener(this);
        this.e = (InfoPreference) view.findViewById(R.id.panel_light);
        this.e.setOnPreferenceChangeListener(this);
        this.K = (InfoPreference) view.findViewById(R.id.front_view);
        this.K.setOnPreferenceChangeListener(this);
        this.L = (InfoPreference) view.findViewById(R.id.front_view_mirror);
        this.L.setOnPreferenceChangeListener(this);
        this.f = (InfoPreference) view.findViewById(R.id.logo_settings);
        this.f.setOnPreferenceChangeListener(this);
        this.g = (InfoPreference) view.findViewById(R.id.app_info);
        this.g.setOnPreferenceChangeListener(this);
        this.k = (InfoPreference) view.findViewById(R.id.amplifier_control);
        this.k.setOnPreferenceChangeListener(this);
        this.l = (InfoPreference) view.findViewById(R.id.radio_region);
        this.l.setOnPreferenceChangeListener(this);
        this.m = (SwitchPreference) view.findViewById(R.id.system_floatvideo);
        this.m.setOnPreferenceChangeListener(this);
        this.p = (InfoPreference) view.findViewById(R.id.navi_settings);
        this.p.setOnPreferenceChangeListener(this);
        this.q = (InfoPreference) view.findViewById(R.id.ip_pip_function);
        this.q.setOnPreferenceChangeListener(this);
        this.r = (InfoPreference) view.findViewById(R.id.pip_app_settings);
        this.r.setOnPreferenceChangeListener(this);
        this.n = (SwitchPreference) view.findViewById(R.id.navi_autostart);
        this.n.setOnPreferenceChangeListener(this);
        this.o = (SwitchPreference) view.findViewById(R.id.sleep_display_boot_animation);
        this.o.setOnPreferenceChangeListener(this);
        this.s = (InfoPreference) view.findViewById(R.id.recorder_setting);
        this.s.setOnPreferenceChangeListener(this);
        this.t = (InfoPreference) view.findViewById(R.id.ktv_setting);
        this.t.setOnPreferenceChangeListener(this);
        this.w = (InfoPreference) view.findViewById(R.id.sleep);
        this.w.setOnPreferenceChangeListener(this);
        this.x = (InfoPreference) view.findViewById(R.id.sleep_mode);
        this.x.setOnPreferenceChangeListener(this);
        this.y = (InfoPreference) view.findViewById(R.id.ip_shutdown_delay);
        this.y.setOnPreferenceChangeListener(this);
        this.z = (InfoPreference) view.findViewById(R.id.log2file);
        this.z.setOnPreferenceChangeListener(this);
        this.A = (InfoPreference) view.findViewById(R.id.location_settings);
        this.A.setOnPreferenceChangeListener(this);
        this.B = (InfoPreference) view.findViewById(R.id.gps_settings);
        this.B.setOnPreferenceChangeListener(this);
        this.C = (InfoPreference) view.findViewById(R.id.btn_sleep_white_list);
        this.C.setOnPreferenceChangeListener(this);
        this.D = (InfoPreference) view.findViewById(R.id.phone_battery);
        this.D.setOnPreferenceChangeListener(this);
        this.E = (InfoPreference) view.findViewById(R.id.voltage_display);
        this.E.setOnPreferenceChangeListener(this);
        this.H = (InfoPreference) view.findViewById(R.id.key_update);
        this.H.setOnPreferenceChangeListener(this);
        this.I = (InfoPreference) view.findViewById(R.id.ampvol);
        this.I.setOnPreferenceChangeListener(this);
        this.J = (InfoPreference) view.findViewById(R.id.system_ta);
        this.J.setOnPreferenceChangeListener(this);
        this.M = (InfoPreference) view.findViewById(R.id.temp_mode);
        this.M.setOnPreferenceChangeListener(this);
        this.N = (InfoPreference) view.findViewById(R.id.ip_video_out_setting);
        this.N.setOnPreferenceChangeListener(this);
        this.O = (InfoPreference) view.findViewById(R.id.shutdown_monitor);
        this.O.setOnPreferenceChangeListener(this);
        this.u = (InfoPreference) view.findViewById(R.id.netmusic_setting);
        this.u.setOnPreferenceChangeListener(this);
        this.v = (InfoPreference) view.findViewById(R.id.netvideo_setting);
        this.v.setOnPreferenceChangeListener(this);
        this.U = (SwitchPreference) view.findViewById(R.id.gesture_recognition);
        this.U.setOnPreferenceChangeListener(this);
        this.S = (InfoPreference) view.findViewById(R.id.app_autostart);
        this.S.setOnPreferenceChangeListener(this);
        this.T = (InfoPreference) view.findViewById(R.id.low_voltage_protection);
        this.T.setOnPreferenceChangeListener(this);
        this.P = (InfoPreference) view.findViewById(R.id.navbar_setting);
        this.P.setOnPreferenceChangeListener(this);
        this.Q = (InfoPreference) view.findViewById(R.id.setting_floating);
        this.Q.setOnPreferenceChangeListener(this);
        this.R = (SwitchPreference) view.findViewById(R.id.floating_switch);
        this.R.setOnPreferenceChangeListener(this);
        this.V = (TextView) view.findViewById(R.id.suspension_button_title);
        this.W = (LinearLayout) view.findViewById(R.id.ll_suspension_button);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("FLOATING_SWITCH_STATUS_BAR");
        this.i.registerReceiver(this.ab, intentFilter);
        f();
    }

    private void f() throws Throwable {
        boolean z;
        this.x.setVisibility(this.j.c ? 0 : 8);
        this.E.setVisibility(this.j.d ? 0 : 8);
        this.D.setVisibility(this.j.e ? 0 : 8);
        boolean z2 = this.j.C;
        if (com.dofun.carsetting.f.b.H()) {
            z2 = com.dofun.carsetting.f.p.d(this.i) || SystemProperties.getInt("persist.usb.monitor.enable", -1) == 1;
        }
        this.N.setVisibility(z2 ? 0 : 8);
        this.O.setVisibility(this.j.E ? 0 : 8);
        this.m.setVisibility(this.j.H ? 0 : 8);
        this.g.setVisibility(this.j.J ? 0 : 8);
        if (com.dofun.carsetting.f.b.T()) {
            this.n.setVisibility(8);
        } else {
            this.n.setVisibility(com.dofun.carsetting.f.b.aT() ? 8 : 0);
        }
        this.S.setVisibility(com.dofun.carsetting.f.b.aT() ? 0 : 8);
        if (com.dofun.carsetting.f.b.T()) {
            this.e.setVisibility(8);
            this.s.setVisibility(8);
            this.E.setVisibility(8);
            this.w.setVisibility(8);
        }
        if (!com.dofun.carsetting.f.b.aF()) {
            this.N.setVisibility(8);
        }
        if (this.c.write(65521) != 533) {
            this.u.setVisibility(8);
            this.v.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.a().contains("-GX")) {
            this.O.a(this.i.getResources().getStringArray(R.array.shutdown_delay_entries4), this.i.getResources().getStringArray(R.array.shutdown_delay_values4));
            this.w.a(getResources().getStringArray(R.array.sleep_string_entries), getResources().getStringArray(R.array.sleepn_string_values));
        } else if (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.w.a(getResources().getStringArray(R.array.sleep_entries_t5), getResources().getStringArray(R.array.sleep_values_t5));
        } else {
            this.w.a(getResources().getStringArray(R.array.sleep_string_entries_normal), getResources().getStringArray(R.array.sleepn_string_values_normal));
        }
        String strA = com.dofun.carsetting.f.b.a();
        if (!strA.contains("AUTOUI-360-SP-FM1388")) {
            this.U.setVisibility(8);
        }
        this.T.setVisibility(this.j.Z ? 0 : 8);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) == 0 || SystemProperties.getInt("persist.sf.forcerotation", -1) == 2) {
            z = (displayMetrics.widthPixels > 1880 && displayMetrics.heightPixels > 1080) || com.dofun.carsetting.f.b.aX();
            Log.d("GeneralFragment", "initPlatformDiff:widthPixels:" + displayMetrics.widthPixels + " heightPixels:" + displayMetrics.heightPixels);
            if (((float) displayMetrics.widthPixels) / ((float) displayMetrics.heightPixels) > 2.0f) {
                this.P.a(getResources().getStringArray(R.array.navigation_bar_long_screen_entries), getResources().getStringArray(R.array.navigation_bar_long_screen_values));
            }
        } else {
            z = false;
        }
        if (strA.contains("X-WIN")) {
            this.P.a(getResources().getStringArray(R.array.navigation_bar_no_hide_entries), getResources().getStringArray(R.array.navigation_bar_no_hide_values));
        }
        int i = SystemProperties.getInt("persist.sf.forcerotation", -1);
        if (i == 0 || i == 3 || i == 4) {
            z = false;
        }
        if (z) {
            this.P.setVisibility(0);
        }
        if (strA.contains("-KTV")) {
            this.t.setVisibility(0);
        }
        this.o.setVisibility((!this.j.ab || com.dofun.carsetting.f.b.T()) ? 8 : 0);
        if (com.dofun.carsetting.f.b.aY()) {
            this.V.setVisibility(0);
            this.W.setVisibility(0);
        } else {
            this.V.setVisibility(8);
            this.W.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.P.a(getResources().getStringArray(R.array.navigation_bar_entries_ts20), getResources().getStringArray(R.array.navigation_bar_values_ts20));
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        int i;
        ArrayList<String> arrayListK = k();
        this.f311a = new String[arrayListK.size()];
        this.b = new String[arrayListK.size()];
        for (int i2 = 0; i2 < this.f311a.length; i2++) {
            this.f311a[i2] = arrayListK.get(i2);
            this.b[i2] = String.valueOf(i2);
        }
        if (!com.dofun.carsetting.f.a.a().b("com.tw.bug.report")) {
            this.z.a(this.f311a, this.b);
            this.z.setSaveValue(false);
        }
        if (com.dofun.carsetting.f.a.a().b("com.tw.devicefan") || com.dofun.carsetting.f.b.T()) {
            this.k.setVisibility(8);
        }
        this.X = String.valueOf(Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 0));
        this.K.setValue(this.X);
        switch (this.X) {
            case "0":
                this.L.setValue(SystemProperties.get("persist.camera.mirror2", "0"));
                break;
            case "1":
                this.L.setValue(SystemProperties.get("persist.camera.mirror1", "0"));
                break;
        }
        this.m.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_FLOATVIDEO", 0) == 1);
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) == 1) {
            this.m.c();
        }
        this.E.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SHOW_VOLTAGE", 1)));
        this.D.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "PHONE_BATTERY_LEVEL", 0)));
        this.x.setValue(SystemProperties.get("persist.tw.sleepmode", "0"));
        this.n.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_NAVI2", 0) == 1);
        this.o.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_DORMANCY_SHOW_BA", 0) == 1);
        this.I.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "AMPVOL", 0)));
        this.J.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_TA", 0)));
        this.M.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "TEMP_MODE", 0)));
        if (SystemProperties.getInt("persist.tw.forcepip", 0) == 1) {
            if (com.dofun.carsetting.f.b.a().contains("WINDOW-AUTOUI") || com.dofun.carsetting.f.b.a().contains("WINDOW-THEME") || com.dofun.carsetting.f.b.a().contains("APP-AUTOUI") || com.dofun.carsetting.f.b.a().contains("APP-THEME")) {
                this.q.setVisibility(0);
            } else {
                this.r.setVisibility(0);
            }
        } else {
            this.q.setVisibility(8);
            this.r.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            i = Settings.System.getInt(MyApp.a().getContentResolver(), "SETTING_NAVBAR", 1);
        } else {
            i = Settings.System.getInt(MyApp.a().getContentResolver(), "SETTING_NAVBAR", 0);
        }
        this.P.setValue(String.valueOf(i));
        this.Q.setValue(String.valueOf(Settings.System.getInt(MyApp.a().getContentResolver(), "SETTING_FLOATING", 0)));
        this.R.setValue(Settings.System.getInt(MyApp.a().getContentResolver(), "FLOATING_SWITCH", 0) == 1);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.c != null) {
            this.c.addHandler("GeneralFragment", this.aa);
            this.c.write(266, 255);
            this.c.write(267, 255);
            this.c.write(265, 255);
            this.c.write(260, 255);
        }
        this.s.setValueTip(i());
        this.t.setValueTip(j());
        this.p.setValueTip(g());
        this.m.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_FLOATVIDEO", 0) == 1);
        this.J.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_TA", 0)));
        this.u.setValueTip(l());
        this.v.setValueTip(m());
        this.r.setValueTip(h());
        this.S.setValueTip(n());
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        Object[] objArr;
        Intent intent;
        switch (twPreference.getId()) {
            case R.id.amplifier_control /* 2131230754 */:
                String str = (String) obj;
                this.c.write(267, 19, Integer.valueOf(str).intValue());
                Settings.System.putInt(this.i.getContentResolver(), "AMPLIFIER_CONTROL", Integer.valueOf(str).intValue());
                return false;
            case R.id.ampvol /* 2131230755 */:
                String str2 = (String) obj;
                Settings.System.putInt(this.i.getContentResolver(), "AMPVOL", Integer.valueOf(str2).intValue());
                this.c.write(267, 13, Integer.valueOf(str2).intValue());
                return false;
            case R.id.app_autostart /* 2131230758 */:
                ((MainActivity) getActivity()).a(new b());
                return false;
            case R.id.app_info /* 2131230759 */:
                Intent intent2 = new Intent("android.settings.APPLICATION_SETTINGS");
                intent2.setFlags(268435456);
                startActivity(intent2);
                return false;
            case R.id.brake_mode /* 2131230773 */:
                this.c.write(267, 1, Integer.valueOf((String) obj).intValue());
                return false;
            case R.id.btn_sleep_white_list /* 2131230786 */:
                try {
                    ((MainActivity) getActivity()).a(new y());
                    break;
                } catch (Exception unused) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.floating_switch /* 2131230880 */:
                Intent intent3 = new Intent();
                intent3.setAction("FLOATING_SWITCH");
                this.i.sendBroadcast(intent3);
                return false;
            case R.id.front_view /* 2131230884 */:
                String str3 = (String) obj;
                SystemProperties.set("SYSTEM_FOWARD_CHANNEL", str3);
                Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", Integer.parseInt(str3));
                this.X = str3;
                switch (this.X) {
                    case "0":
                        this.L.setValue(SystemProperties.get("persist.camera.mirror2", "0"));
                        break;
                    case "1":
                        this.L.setValue(SystemProperties.get("persist.camera.mirror1", "0"));
                        break;
                }
                this.c.write(40730, 1, 0, "sync");
                return false;
            case R.id.front_view_mirror /* 2131230885 */:
                String str4 = this.X;
                switch (str4.hashCode()) {
                    case 48:
                        if (str4.equals("0")) {
                        }
                        break;
                    case 49:
                        if (str4.equals("1")) {
                        }
                        break;
                }
                /*  JADX ERROR: Method code generation error
                    java.lang.NullPointerException: Switch insn not found in header
                    	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:90)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:291)
                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:270)
                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:420)
                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:345)
                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:299)
                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                    */
                /*
                    Method dump skipped, instruction units count: 1614
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.dofun.carsetting.fragment.k.a(com.dofun.carsetting.widget.TwPreference, java.lang.Object):boolean");
            }

            public static ProgressDialog a(Context context, String str) {
                ProgressDialog progressDialog = new ProgressDialog(context, 3);
                progressDialog.setMessage(str);
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.setOnCancelListener(null);
                return progressDialog;
            }

            private String g() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/navi_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            private String h() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/custom_pip_app_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            private String i() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/recorder_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            private String j() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/ktv_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public ArrayList<String> k() {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add("/sdcard");
                File[] fileArrListFiles = new File("/storage").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.k.3
                    @Override // java.io.FileFilter
                    public boolean accept(File file) {
                        return file.canRead() && file.isDirectory() && file.getName().startsWith("extsd");
                    }
                });
                if (fileArrListFiles != null) {
                    for (File file : fileArrListFiles) {
                        String absolutePath = file.getAbsolutePath();
                        arrayList.add(absolutePath.substring(absolutePath.lastIndexOf("/")));
                    }
                }
                File[] fileArrListFiles2 = new File("/storage").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.k.4
                    @Override // java.io.FileFilter
                    public boolean accept(File file2) {
                        return file2.canRead() && file2.isDirectory() && file2.getName().startsWith("usb");
                    }
                });
                if (fileArrListFiles2 != null) {
                    for (File file2 : fileArrListFiles2) {
                        String absolutePath2 = file2.getAbsolutePath();
                        arrayList.add(absolutePath2.substring(absolutePath2.lastIndexOf("/")));
                    }
                }
                return arrayList;
            }

            private String l() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/netmusic_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            private String m() throws Throwable {
                BufferedReader bufferedReader;
                BufferedReader bufferedReader2 = null;
                PackageInfo packageInfo = null;
                try {
                    try {
                        bufferedReader = new BufferedReader(new FileReader("/data/tw/netvideo_name"));
                        try {
                            PackageManager packageManager = getActivity().getPackageManager();
                            try {
                                packageInfo = packageManager.getPackageInfo(bufferedReader.readLine(), 1);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            String string = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                            bufferedReader.close();
                            return string;
                        } catch (Exception unused) {
                            bufferedReader2 = bufferedReader;
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                        return "";
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = null;
                }
            }

            private void a(final int i) {
                if (com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    SystemProperties.set("persist.navibar.position", String.valueOf(i - 1));
                    switch (i) {
                        case 0:
                            Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", -1);
                            break;
                        case 1:
                            Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", 4);
                            break;
                        case 2:
                            Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", 1);
                            break;
                        case 3:
                            Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", 2);
                            break;
                    }
                    return;
                }
                if (Settings.System.getInt(this.i.getContentResolver(), "navigationbar_config", 0) != 0) {
                    Settings.System.putInt(this.i.getContentResolver(), "navigationbar_config", 0);
                }
                if (Settings.System.getInt(this.i.getContentResolver(), "show_navigationbar", 0) != 0) {
                    Settings.System.putInt(this.i.getContentResolver(), "show_navigationbar", 0);
                }
                SystemProperties.set("persist.navibar.position", "0");
                if (i != 0) {
                    this.aa.postDelayed(new Runnable() { // from class: com.dofun.carsetting.fragment.k.5
                        @Override // java.lang.Runnable
                        public void run() {
                            SystemProperties.set("persist.navibar.position", String.valueOf(i - 1));
                            Settings.System.putInt(k.this.i.getContentResolver(), "navigationbar_config", 16);
                            Settings.System.putInt(k.this.i.getContentResolver(), "show_navigationbar", 1);
                        }
                    }, 300L);
                }
            }

            private String n() throws Throwable {
                BufferedReader bufferedReader = null;
                try {
                    try {
                        BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/app_autostart_name"));
                        try {
                            bufferedReader2.readLine();
                            bufferedReader2.readLine();
                            String line = bufferedReader2.readLine();
                            try {
                                bufferedReader2.close();
                            } catch (Exception unused) {
                            }
                            return line;
                        } catch (Exception unused2) {
                            bufferedReader = bufferedReader2;
                            if (bufferedReader == null) {
                                return "";
                            }
                            bufferedReader.close();
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused3) {
                        return "";
                    }
                } catch (Exception unused4) {
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
