package com.dofun.carsetting.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.app.FrameMetricsAggregator;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.MultiCheckPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: compiled from: OriginalFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class r extends ad<com.dofun.carsetting.presenter.h> implements com.dofun.carsetting.g.j, com.dofun.carsetting.g.l, com.dofun.carsetting.g.m {
    private static String L;
    private InfoPreference A;
    private InfoPreference B;
    private InfoPreference C;
    private InfoPreference D;
    private LinearLayout E;
    private SeekBar F;
    private TextView G;
    private LinearLayout H;
    private SeekBar I;
    private TextView J;
    private ProgressDialog K;

    @SuppressLint({"HandlerLeak"})
    private Handler M = new Handler() { // from class: com.dofun.carsetting.fragment.r.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 267) {
                    byte[] bArr = (byte[]) message.obj;
                    r.this.d.setValue(String.valueOf((int) bArr[1]));
                    int i2 = 2;
                    if ("3".equals(String.valueOf((int) bArr[2]))) {
                        r.this.x.setValue("3");
                    } else {
                        r.this.x.setValue(String.valueOf((int) bArr[2]));
                    }
                    r.this.y.setValue(String.valueOf((int) bArr[3]));
                    r.this.e.setValue(Integer.valueOf(String.valueOf((int) bArr[15])).intValue() == 1);
                    if (bArr[16] != 1) {
                        i2 = 1;
                    }
                    SystemProperties.set("persist.tw.navibar", String.valueOf(i2));
                    return;
                }
                if (i != 40479) {
                    switch (i) {
                        case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                            if (r.this.K != null) {
                                r.this.K.dismiss();
                            }
                            break;
                        case 65281:
                            r.this.o.write(514, 1, 0);
                            break;
                        default:
                            break;
                    }
                    return;
                }
                ArrayList arrayListI = r.this.i();
                r.this.f332a = new String[arrayListI.size()];
                r.this.b = new String[arrayListI.size()];
                for (int i3 = 0; i3 < r.this.f332a.length; i3++) {
                    r.this.f332a[i3] = (String) arrayListI.get(i3);
                    r.this.b[i3] = String.valueOf(i3);
                }
                if (com.dofun.carsetting.f.a.a().b("com.tw.bug.report")) {
                    return;
                }
                r.this.r.d();
                r.this.r.a(r.this.f332a, r.this.b);
                r.this.r.setSaveValue(false);
            } catch (Exception unused) {
            }
        }
    };
    private String N = Environment.getExternalStorageDirectory().getPath() + "";
    private String O = ".CarChoose";

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String[] f332a;
    String[] b;
    private InfoPreference c;
    private InfoPreference d;
    private SwitchPreference e;
    private InfoPreference f;
    private SwitchPreference g;
    private SwitchPreference k;
    private InfoPreference l;
    private MultiCheckPreference m;
    private InfoPreference n;
    private com.dofun.carsetting.f.u o;
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

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_original, (ViewGroup) null);
        this.o = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.h d() {
        return new com.dofun.carsetting.presenter.h(this.i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.c = (InfoPreference) view.findViewById(R.id.btn_car_choose);
        this.c.setOnPreferenceChangeListener(this);
        this.c.setValueTip(h());
        this.d = (InfoPreference) view.findViewById(R.id.brake_mode);
        this.d.setOnPreferenceChangeListener(this);
        this.e = (SwitchPreference) view.findViewById(R.id.swap_last_next);
        this.e.setOnPreferenceChangeListener(this);
        this.f = (InfoPreference) view.findViewById(R.id.global_key);
        this.f.setOnPreferenceChangeListener(this);
        this.f.setValue(SystemProperties.get("persist.global.key", "0"));
        this.g = (SwitchPreference) view.findViewById(R.id.system_usb11);
        this.g.setOnPreferenceChangeListener(this);
        this.g.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_USB1", 1) == 0);
        this.k = (SwitchPreference) view.findViewById(R.id.system_usbhub);
        this.k.setOnPreferenceChangeListener(this);
        this.k.setValue(SystemProperties.getBoolean("persist.sys.usb.support_hub", true));
        this.l = (InfoPreference) view.findViewById(R.id.canboxupdate);
        this.l.setOnPreferenceChangeListener(this);
        this.m = (MultiCheckPreference) view.findViewById(R.id.door_info);
        this.m.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_DOOR", 0));
        this.m.a((com.dofun.carsetting.g.l) this);
        this.n = (InfoPreference) view.findViewById(R.id.left_right_seat);
        this.n.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.n.a(this.i.getResources().getStringArray(R.array.left_right_seat_reverse_normal_entries), this.i.getResources().getStringArray(R.array.left_right_seat_reverse_normal_values));
            this.n.invalidate();
        }
        this.n.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "LEFT_RIGHT_SEAT_REAL", Settings.System.getInt(this.i.getContentResolver(), "LEFT_RIGHT_SEAT", 0))));
        this.z = (InfoPreference) view.findViewById(R.id.getspeed_mode);
        this.z.setOnPreferenceChangeListener(this);
        this.z.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "PERSIST_CAN_SPEED", 0)));
        this.z.setVisibility((com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) ? 0 : 8);
        this.A = (InfoPreference) view.findViewById(R.id.shortcut_360);
        this.A.setOnPreferenceChangeListener(this);
        this.A.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_SHORTCUT360", 0)));
        this.p = (InfoPreference) view.findViewById(R.id.logo_settings);
        this.p.setOnPreferenceChangeListener(this);
        this.q = (InfoPreference) view.findViewById(R.id.ampvol);
        this.q.setOnPreferenceChangeListener(this);
        this.q.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "AMPVOL", 0)));
        this.r = (InfoPreference) view.findViewById(R.id.log2file);
        this.r.setOnPreferenceChangeListener(this);
        this.s = (InfoPreference) view.findViewById(R.id.key_wheel_title);
        this.s.setOnPreferenceChangeListener(this);
        this.t = (InfoPreference) view.findViewById(R.id.btn_sleep_white_list);
        this.t.setOnPreferenceChangeListener(this);
        this.u = (InfoPreference) view.findViewById(R.id.btn_auto_play_app_list);
        this.u.setOnPreferenceChangeListener(this);
        this.v = (InfoPreference) view.findViewById(R.id.ip_video_out_setting);
        this.v.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.p.d(this.i) || SystemProperties.getInt("persist.usb.monitor.enable", -1) == 1) {
            this.v.setVisibility(0);
        } else {
            this.v.setVisibility(8);
        }
        this.x = (InfoPreference) view.findViewById(R.id.tv_mode_title);
        this.x.setOnPreferenceChangeListener(this);
        this.y = (InfoPreference) view.findViewById(R.id.tv_norm);
        this.y.setOnPreferenceChangeListener(this);
        this.w = (InfoPreference) view.findViewById(R.id.ip_multi_screen);
        this.w.setOnPreferenceChangeListener(this);
        if (!com.dofun.carsetting.f.b.p() && !com.dofun.carsetting.f.b.F() && !com.dofun.carsetting.f.b.H() && !com.dofun.carsetting.f.b.G() && !com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.J()) {
            if (com.dofun.carsetting.f.b.a().contains("WINDOW")) {
                this.w.setVisibility(0);
            } else {
                this.w.setVisibility(8);
            }
        }
        this.B = (InfoPreference) view.findViewById(R.id.es_file_manager);
        this.B.setOnPreferenceChangeListener(this);
        this.C = (InfoPreference) view.findViewById(R.id.mic);
        this.C.setOnPreferenceChangeListener(this);
        this.C.setValue(SystemProperties.get("persist.cpbox.mic", "0"));
        this.C.setVisibility(com.dofun.carsetting.f.b.T() ? 0 : 8);
        this.E = (LinearLayout) view.findViewById(R.id.ll_screen_casting_code_rate);
        this.F = (SeekBar) view.findViewById(R.id.sb_screen_casting_code_rate);
        this.G = (TextView) view.findViewById(R.id.tv_screen_casting_code_rate_value);
        this.F.setMax(9);
        this.F.setProgress(SystemProperties.getInt("persist.suding.reverse.video.level", 8) - 1);
        this.G.setText(SystemProperties.get("persist.suding.reverse.video.level", "8"));
        this.F.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.r.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    int i2 = i + 1;
                    r.this.G.setText(String.valueOf(i2));
                    SystemProperties.set("persist.suding.reverse.video.level", String.valueOf(i2));
                }
            }
        });
        this.D = (InfoPreference) view.findViewById(R.id.echo_delay_param);
        this.D.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.s()) {
            this.D.setVisibility(0);
        } else {
            this.D.setVisibility(8);
        }
        this.D.setValue(SystemProperties.get("persist.btaec.delay", "0"));
        this.H = (LinearLayout) view.findViewById(R.id.ll_lcd_clk);
        this.I = (SeekBar) view.findViewById(R.id.sb_lcd_clk);
        this.J = (TextView) view.findViewById(R.id.tv_lcd_clk_value);
        this.I.setMax(50);
        int i = SystemProperties.getInt("persist.panel.lcdclk", k());
        this.I.setProgress(i >= 10 ? i - 10 : 0);
        this.J.setText(String.valueOf(i));
        this.I.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.r.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    r.this.J.setText(String.valueOf(i2 + 10));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                r.this.a(seekBar.getProgress() + 10);
            }
        });
        f();
    }

    private void f() {
        int i = 8;
        if (com.dofun.carsetting.f.b.T()) {
            this.g.setVisibility(8);
            this.c.setVisibility(8);
            this.t.setVisibility(8);
            this.d.setVisibility(8);
            this.q.setVisibility(8);
            this.m.setVisibility(8);
        }
        this.A.setVisibility((!com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.U()) ? 8 : 0);
        this.y.setVisibility(this.j.D ? 0 : 8);
        this.x.setVisibility(this.j.D ? 0 : 8);
        this.f.setVisibility(com.dofun.carsetting.f.b.O() ? 0 : 8);
        this.B.setVisibility(this.j.N ? 0 : 8);
        this.E.setVisibility(this.j.L ? 0 : 8);
        this.H.setVisibility(this.j.L ? 0 : 8);
        this.k.setVisibility(com.dofun.carsetting.f.b.s() ? 0 : 8);
        if (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.L() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G()) {
            this.u.setVisibility(0);
        }
        InfoPreference infoPreference = this.s;
        if (com.dofun.carsetting.f.b.aH() && !com.dofun.carsetting.f.b.T()) {
            i = 0;
        }
        infoPreference.setVisibility(i);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        ArrayList<String> arrayListI = i();
        this.f332a = new String[arrayListI.size()];
        this.b = new String[arrayListI.size()];
        for (int i = 0; i < this.f332a.length; i++) {
            this.f332a[i] = arrayListI.get(i);
            this.b[i] = String.valueOf(i);
        }
        if (!com.dofun.carsetting.f.a.a().b("com.tw.bug.report")) {
            this.r.a(this.f332a, this.b);
            this.r.setSaveValue(false);
        }
        this.w.setValue(SystemProperties.get("persist.tw.forcepip", "0"));
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.o != null) {
            this.o.addHandler("OriginalFragment", this.M);
            this.o.write(267, 255);
        }
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
        boolean z;
        Intent intent;
        Log.d("OriginalFragment", "onPreferenceChange: " + obj);
        switch (twPreference.getId()) {
            case R.id.ampvol /* 2131230755 */:
                String str = (String) obj;
                Settings.System.putInt(this.i.getContentResolver(), "AMPVOL", Integer.valueOf(str).intValue());
                this.o.write(267, 13, Integer.valueOf(str).intValue());
                return false;
            case R.id.brake_mode /* 2131230773 */:
                this.o.write(267, 1, Integer.valueOf((String) obj).intValue());
                return false;
            case R.id.btn_auto_play_app_list /* 2131230779 */:
                ((MainActivity) getActivity()).a(new c());
                return false;
            case R.id.btn_car_choose /* 2131230780 */:
                try {
                    Intent intent2 = new Intent();
                    intent2.setComponent(new ComponentName("com.tw.carchoose", "com.tw.carchoose.QueryCarActivity"));
                    startActivity(intent2);
                    break;
                } catch (Exception unused) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.btn_sleep_white_list /* 2131230786 */:
                ((MainActivity) getActivity()).a(new y());
                return false;
            case R.id.canboxupdate /* 2131230791 */:
                try {
                    Intent intent3 = new Intent();
                    intent3.setComponent(new ComponentName("com.tw.canboxupdate", "com.tw.canboxupdate.CanUpdateMainActivity"));
                    startActivity(intent3);
                    break;
                } catch (Exception unused2) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.echo_delay_param /* 2131230860 */:
                SystemProperties.set("persist.btaec.delay", (String) obj);
                return false;
            case R.id.es_file_manager /* 2131230867 */:
                b("com.estrongs.android.pop");
                return false;
            case R.id.getspeed_mode /* 2131230887 */:
                String str2 = (String) obj;
                SystemProperties.set("persist.can.speed", String.valueOf(Integer.valueOf(str2)));
                this.z.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "PERSIST_CAN_SPEED", Integer.valueOf(str2).intValue())));
                return false;
            case R.id.global_key /* 2131230888 */:
                SystemProperties.set("persist.global.key", (String) obj);
                return false;
            case R.id.ip_multi_screen /* 2131230932 */:
                a(String.valueOf(obj));
                return false;
            case R.id.ip_video_out_setting /* 2131230939 */:
                if (com.dofun.carsetting.f.b.A() || com.dofun.carsetting.f.b.z()) {
                    intent = new Intent("com.android.settings");
                    intent.setClassName("com.android.settings", "com.android.settings.display.VideoOutAdjustSettings");
                    intent.setFlags(268435456);
                } else {
                    if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.G()) {
                        ((MainActivity) getActivity()).a(new ab());
                    } else {
                        intent = new Intent("com.ms.ms2160.SettingActivity");
                        intent.setClassName("com.ms.ms2160", "com.ms.ms2160.myapplication.SettingActivityV2");
                        intent.setFlags(268435456);
                    }
                    return false;
                }
                startActivity(intent);
                return false;
            case R.id.key_wheel_title /* 2131230983 */:
                try {
                    try {
                        Intent intent4 = new Intent();
                        intent4.setClassName("com.tw.keypad", "com.tw.keypad.SteeringWheelActivity");
                        startActivity(intent4);
                    } catch (Exception unused3) {
                        Intent intent5 = new Intent();
                        intent5.setClassName("com.android.settings", "com.android.settings.KeypadActivity");
                        startActivity(intent5);
                    }
                    break;
                } catch (Exception unused4) {
                }
                return false;
            case R.id.left_right_seat /* 2131230989 */:
                int iIntValue = Integer.valueOf((String) obj).intValue();
                Settings.System.putInt(this.i.getContentResolver(), "LEFT_RIGHT_SEAT_REAL", iIntValue);
                this.n.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "LEFT_RIGHT_SEAT_REAL", 0)));
                if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    com.dofun.carsetting.f.c.a(this.i, iIntValue == 2);
                }
                if (iIntValue == 2) {
                    iIntValue = 1;
                }
                this.o.write(267, 16, iIntValue);
                SystemProperties.set("persist.tw.navibar", String.valueOf(iIntValue + 1));
                Settings.System.putInt(this.i.getContentResolver(), "LEFT_RIGHT_SEAT", iIntValue);
                return false;
            case R.id.log2file /* 2131231048 */:
                try {
                    Intent intent6 = new Intent();
                    intent6.setComponent(new ComponentName("com.tw.bug.report", "com.tw.bug.report.ReportActivity"));
                    intent6.setFlags(268435456);
                    startActivity(intent6);
                    z = true;
                } catch (Exception unused5) {
                    z = false;
                }
                Log.d("OriginalFragment", "log2file:hasBugReportApp:" + z);
                if (!z) {
                    L = this.f332a[Integer.valueOf((String) obj).intValue()];
                    if (L.startsWith("/usb")) {
                        L = "/storage" + L;
                    }
                    Toast.makeText(getActivity(), L, 1).show();
                    if (new File(L).exists()) {
                        try {
                            L += "/logcat.bin";
                            this.M.sendEmptyMessageDelayed(MotionEventCompat.ACTION_POINTER_INDEX_MASK, 5000L);
                            this.K = a(getActivity(), getResources().getString(R.string.log2file_title));
                            this.K.show();
                            new Thread(new Runnable() { // from class: com.dofun.carsetting.fragment.r.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        Runtime.getRuntime().exec("logcat -d -f " + r.L).waitFor();
                                        Runtime.getRuntime().exec("sync").waitFor();
                                    } catch (Exception e) {
                                        Log.e("OriginalFragment", "export logcat fail:" + e.getMessage());
                                    }
                                }
                            }).start();
                            break;
                        } catch (Exception unused6) {
                        }
                    }
                }
                try {
                    this.r.c();
                    break;
                } catch (Exception e) {
                    Log.d("OriginalFragment", "log2file:" + e.getMessage());
                }
                return false;
            case R.id.logo_settings /* 2131231050 */:
                try {
                    Intent intent7 = new Intent();
                    intent7.setComponent(new ComponentName("com.dofun.carsetting", "com.dofun.carsetting.activity.LogoActivity"));
                    startActivity(intent7);
                    break;
                } catch (Exception unused7) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.mic /* 2131231061 */:
                SystemProperties.set("persist.cpbox.mic", (String) obj);
                return false;
            case R.id.shortcut_360 /* 2131231197 */:
                String str3 = (String) obj;
                Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_SHORTCUT360", Integer.valueOf(str3).intValue());
                this.A.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_SHORTCUT360", Integer.valueOf(str3).intValue())));
                return false;
            case R.id.swap_last_next /* 2131231230 */:
                this.o.write(267, 15, ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.system_usb11 /* 2131231247 */:
                Boolean bool = (Boolean) obj;
                Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_USB1", !bool.booleanValue() ? 1 : 0);
                com.dofun.carsetting.f.u uVar = this.o;
                StringBuilder sb = new StringBuilder();
                sb.append("rwrite /sys/bus/platform/drivers/dwc_otg/udcspeed ");
                sb.append(bool.booleanValue() ? "0" : "1");
                uVar.write(40730, 1, 0, sb.toString());
                return false;
            case R.id.system_usbhub /* 2131231248 */:
                SystemProperties.set("persist.sys.usb.support_hub", ((Boolean) obj).booleanValue() ? "true" : "false");
                j();
                return false;
            case R.id.tv_mode_title /* 2131231305 */:
                this.o.write(267, 2, Integer.valueOf(String.valueOf(obj)).intValue());
                return false;
            case R.id.tv_norm /* 2131231306 */:
                this.o.write(267, 3, Integer.valueOf(String.valueOf(obj)).intValue());
                return false;
            default:
                return false;
        }
    }

    @Override // com.dofun.carsetting.g.l
    public void a(MultiCheckPreference multiCheckPreference, int i, boolean[] zArr) {
        if (multiCheckPreference.getId() != R.id.door_info) {
            return;
        }
        Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_DOOR", i);
    }

    private String g() {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                if (new File("/storage/emulated/0/.CarChoose").exists()) {
                    FileUtils.setPermissions("/storage/emulated/0/.CarChoose", FrameMetricsAggregator.EVERY_DURATION, -1, -1);
                }
                Log.i("OriginalFragment", this.N + "/" + this.O + "     " + new File("/storage/emulated/0/.CarChoose").canRead());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.N);
                sb2.append("/");
                sb2.append(this.O);
                FileInputStream fileInputStream = new FileInputStream(sb2.toString());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                fileInputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("OriginalFragment", " readSaveFile " + e.toString());
            }
        }
        return sb.toString();
    }

    private String h() {
        try {
            String strG = g();
            HashMap map = new HashMap();
            for (String str : strG.split("\\+")) {
                String[] strArrSplit = str.split("\\=");
                if (strArrSplit.length == 2) {
                    map.put(strArrSplit[0], strArrSplit[1]);
                } else if (strArrSplit.length == 1) {
                    map.put(strArrSplit[0], " ");
                }
            }
            String strSubstring = (String) map.get("tv_vendor");
            String strSubstring2 = (String) map.get("tv_cars");
            String strSubstring3 = (String) map.get("tv_models");
            String str2 = (String) map.get("tv_configuration");
            if (strSubstring.contains(":")) {
                strSubstring = strSubstring.substring(strSubstring.indexOf(":") + 2, strSubstring.length());
            }
            if (strSubstring2.contains(":")) {
                strSubstring2 = strSubstring2.substring(strSubstring2.indexOf(":") + 2, strSubstring2.length());
            }
            if (strSubstring3.contains(":")) {
                strSubstring3 = strSubstring3.substring(strSubstring3.indexOf(":") + 2, strSubstring3.length());
            }
            return strSubstring + "-" + strSubstring2 + "-" + strSubstring3 + "-" + str2;
        } catch (Exception e) {
            Log.i("OriginalFragment", "getText  " + e.toString());
            return "";
        }
    }

    public static ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        progressDialog.setMessage(str);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        return progressDialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<String> i() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("/sdcard");
        File[] fileArrListFiles = new File("/storage").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.r.5
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
        File[] fileArrListFiles2 = new File("/storage").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.r.6
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

    private void a(final String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.r.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                SystemProperties.set("persist.tw.forcepip", str);
                if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    r.this.o.write(40730, 1, 0, "mount -o remount,rw /system_tw");
                } else {
                    r.this.o.write(40730, 1, 0, "mount -o remount,rw /");
                }
                if (TextUtils.equals(str, "1")) {
                    r.this.o.write(40730, 1, 0, "cp -rf " + r.this.j.S + "theme/sfp/theme.apk " + r.this.j.S + "theme/");
                    com.dofun.carsetting.f.u uVar = r.this.o;
                    StringBuilder sb = new StringBuilder();
                    sb.append("cp -rf ");
                    sb.append(r.this.j.S);
                    sb.append("theme/sfp/theme.apk /data/tw/theme/");
                    uVar.write(40730, 1, 0, sb.toString());
                } else {
                    r.this.o.write(40730, 1, 0, "cp -rf " + r.this.j.S + "theme/public/theme.apk  " + r.this.j.S + "theme/");
                    com.dofun.carsetting.f.u uVar2 = r.this.o;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("cp -rf ");
                    sb2.append(r.this.j.S);
                    sb2.append("theme/public/theme.apk /data/tw/theme/");
                    uVar2.write(40730, 1, 0, sb2.toString());
                }
                r.this.o.write(40730, 3, 0, "chmod 755 " + r.this.j.S + "theme/theme.apk");
                r.this.o.write(40730, 3, 0, "chmod -R 777 /data/tw/theme");
                if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    r.this.o.write(40730, 3, 0, "mount -o remount,ro /system_tw");
                } else {
                    r.this.o.write(40730, 3, 0, "mount -o remount,ro /");
                }
                r.this.o.write(40730, 3, 0, "sync");
                r.this.M.sendEmptyMessageDelayed(65281, 1500L);
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.set_navi_app_dialog_message);
        builder.setMessage(R.string.reboot_now);
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception unused) {
        }
        alertDialogCreate.show();
    }

    private void b(String str) {
        try {
            startActivity(this.i.getPackageManager().getLaunchIntentForPackage(str));
        } catch (Exception unused) {
        }
    }

    private void j() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.r.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                r.this.M.sendEmptyMessageDelayed(65281, 1000L);
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.set_navi_app_dialog_message);
        builder.setMessage(R.string.reboot_now);
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception unused) {
        }
        alertDialogCreate.show();
    }

    private int k() {
        return (((SystemProperties.getInt("persist.panel.width", 1024) + 30) * (SystemProperties.getInt("persist.panel.height", 600) + 30)) * 35) / 1000000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final int i) {
        com.dofun.carsetting.c.a aVar = new com.dofun.carsetting.c.a(getActivity());
        aVar.a(getResources().getString(R.string.clk_info1) + "\n" + getResources().getString(R.string.clk_info2));
        aVar.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.r.9
            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void b() {
            }

            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void a() {
                SystemProperties.set("persist.panel.lcdclk", String.valueOf(i));
                r.this.o.write(40730, 1, 0, "sync");
                r.this.o.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/lcd0 lcd_dclk_freq <0x%x>\nfdt save", Integer.valueOf(i)));
            }

            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
            public void c() {
                SystemProperties.set("persist.panel.lcdclk", String.valueOf(i));
                r.this.o.write(40730, 1, 0, "sync");
                r.this.o.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/lcd0 lcd_dclk_freq <0x%x>\nfdt save", Integer.valueOf(i)));
            }
        });
        aVar.show();
    }
}
