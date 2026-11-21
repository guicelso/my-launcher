package com.dofun.carsetting.fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.config.ConfigService;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.BacklightPreference;
import com.dofun.carsetting.widget.EditTextPreference;
import com.dofun.carsetting.widget.ImportExportExtensionSettings;
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
import java.util.List;

/* JADX INFO: compiled from: FactoryFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class j extends ad<com.dofun.carsetting.presenter.e> implements com.dofun.carsetting.g.g, com.dofun.carsetting.g.h, com.dofun.carsetting.g.j, com.dofun.carsetting.g.l {
    private InfoPreference A;
    private InfoPreference B;
    private InfoPreference C;
    private InfoPreference D;
    private InfoPreference E;
    private MultiCheckPreference F;
    private EditTextPreference G;
    private SwitchPreference H;
    private SwitchPreference I;
    private SwitchPreference J;
    private SwitchPreference K;
    private SwitchPreference L;
    private int M;
    private int N;
    private ImportExportExtensionSettings O;
    private InfoPreference R;
    private InfoPreference S;
    private InfoPreference T;
    private InfoPreference U;
    private InfoPreference V;
    private InfoPreference W;
    private InfoPreference X;
    private InfoPreference Y;
    private InfoPreference Z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public com.dofun.carsetting.f.u f284a;
    private Dialog aA;
    private InfoPreference aa;
    private InfoPreference ab;
    private InfoPreference ac;
    private InfoPreference ad;
    private InfoPreference ae;
    private InfoPreference af;
    private InfoPreference ag;
    private InfoPreference ah;
    private InfoPreference ai;
    private InfoPreference aj;
    private InfoPreference ak;
    private InfoPreference al;
    private InfoPreference am;
    private InfoPreference an;
    private InfoPreference ao;
    private InfoPreference ap;
    private TextView aq;
    private LinearLayout ar;
    private ProgressDialog aw;
    private ConfigService.a ax;
    private BacklightPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
    private InfoPreference m;
    private InfoPreference n;
    private InfoPreference o;
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
    private boolean[] P = {true, true, true, true, true};
    public int b = -1;
    private int Q = 0;
    public String c = null;
    public boolean d = false;
    public boolean e = false;
    private String as = "0";
    private String at = Environment.getExternalStorageDirectory().getPath() + "";
    private String au = ".CarChoose";
    private long[] av = new long[5];
    private String ay = "persist.tw.launcherType";
    private String az = "persist.tw.launcherType.carMode";
    private int aB = 0;
    private final int aC = 0;
    private final int aD = 1;
    private int aE = 0;
    private final int aF = 0;
    private final int aG = 1;
    private final int aH = 2;
    private final int aI = 3;
    private Handler aJ = new Handler() { // from class: com.dofun.carsetting.fragment.j.22
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                int i = message.what;
                if (i == 265) {
                    j.this.V.setValue(String.valueOf((int) ((byte[]) message.obj)[0]));
                    return;
                }
                if (i != 267) {
                    if (i != 276) {
                        switch (i) {
                            case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                                if (j.this.aw != null) {
                                    j.this.aw.dismiss();
                                }
                                Toast.makeText(j.this.getActivity(), message.arg1 == 0 ? j.this.getString(R.string.complete_backup) : j.this.getString(R.string.complete_removal_of), 1).show();
                                break;
                            case 65281:
                                j.this.f284a.write(514, 1, 0);
                                break;
                            default:
                                break;
                        }
                        return;
                    }
                    j.this.M = message.arg1 - 5;
                    j.this.N = message.arg2 - 33;
                    return;
                }
                byte[] bArr = (byte[]) message.obj;
                if (bArr[0] <= 1 || bArr[0] >= 14) {
                    j.this.ae.setValue(String.valueOf((int) bArr[0]));
                } else {
                    j.this.ae.setValue("8");
                }
                j.this.R.setValue(String.valueOf((int) bArr[1]));
                if ("3".equals(String.valueOf((int) bArr[2]))) {
                    j.this.o.setValue("3");
                } else {
                    j.this.o.setValue(String.valueOf((int) bArr[2]));
                }
                j.this.p.setValue(String.valueOf((int) bArr[3]));
                j.this.u.setValue(String.valueOf((bArr[7] & 1) == 1 ? 1 : 0));
                j.this.v.setValue(String.valueOf((bArr[7] & 2) == 2 ? 1 : 0));
                j.this.H.setValue((bArr[7] & 4) == 4);
                j.this.t.setValue(String.valueOf((int) bArr[11]));
                j.this.w.setValue(String.valueOf((int) bArr[15]));
                j.this.aa.setValue(String.valueOf((int) bArr[8]));
                j.this.ai.setValue(String.valueOf((int) bArr[10]));
                j.this.al.setValue(String.valueOf((int) bArr[10]));
                j.this.q.setValue(String.valueOf((int) bArr[17]));
            } catch (Exception unused) {
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_factory, (ViewGroup) null);
        this.f284a = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            return;
        }
        b();
    }

    @Override // android.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        Log.d("FactoryFragment", "setUserVisibleHint:" + z);
        if (z) {
            b();
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.e d() {
        return new com.dofun.carsetting.presenter.e(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) throws Throwable {
        int iA;
        this.aq = (TextView) view.findViewById(R.id.knob_settings_tv);
        this.ar = (LinearLayout) view.findViewById(R.id.knob_settings_ll);
        this.x = (InfoPreference) view.findViewById(R.id.cfg_import);
        this.g = (InfoPreference) view.findViewById(R.id.panel_settings_title);
        this.k = (InfoPreference) view.findViewById(R.id.key_wheel_title);
        this.l = (InfoPreference) view.findViewById(R.id.area_key_title);
        this.m = (InfoPreference) view.findViewById(R.id.install_apk);
        this.D = (InfoPreference) view.findViewById(R.id.select_launcher);
        this.n = (InfoPreference) view.findViewById(R.id.screen_reference);
        this.o = (InfoPreference) view.findViewById(R.id.tv_mode_title);
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.o.a(getResources().getTextArray(R.array.tv_mode_DVB_t5_entries), getResources().getTextArray(R.array.tv_mode_values));
        }
        this.p = (InfoPreference) view.findViewById(R.id.tv_norm);
        this.p.setOnPreferenceChangeListener(this);
        this.q = (InfoPreference) view.findViewById(R.id.rotation_switch);
        this.q.setOnPreferenceChangeListener(this);
        this.r = (InfoPreference) view.findViewById(R.id.config_title);
        this.t = (InfoPreference) view.findViewById(R.id.resolution);
        this.t.setNeedPassword(true);
        this.Y = (InfoPreference) view.findViewById(R.id.ip_video_out_setting);
        this.Y.setOnPreferenceChangeListener(this);
        this.Z = (InfoPreference) view.findViewById(R.id.reverse_type);
        this.Z.setOnPreferenceChangeListener(this);
        this.f = (BacklightPreference) view.findViewById(R.id.backlight_current_regulation);
        this.u = (InfoPreference) view.findViewById(R.id.direction_of_the_knob1);
        this.v = (InfoPreference) view.findViewById(R.id.direction_of_the_knob2);
        this.H = (SwitchPreference) view.findViewById(R.id.switch_knobs1_and2);
        this.w = (InfoPreference) view.findViewById(R.id.switch_the_steering_wheel);
        this.F = (MultiCheckPreference) view.findViewById(R.id.cfg_export);
        this.G = (EditTextPreference) view.findViewById(R.id.rb_setting);
        this.y = (InfoPreference) view.findViewById(R.id.change_imei);
        this.z = (InfoPreference) view.findViewById(R.id.force_imei);
        this.C = (InfoPreference) view.findViewById(R.id.ap_channel_setting);
        this.A = (InfoPreference) view.findViewById(R.id.engineer_mode);
        this.s = (InfoPreference) view.findViewById(R.id.ip_multi_screen);
        this.E = (InfoPreference) view.findViewById(R.id.performance_mode);
        this.ac = (InfoPreference) view.findViewById(R.id.reverse_pattern);
        if (com.dofun.carsetting.f.b.f()) {
            this.ac.a(getResources().getTextArray(R.array.vr_entries), getResources().getTextArray(R.array.vr_t5_values));
        }
        this.ap = (InfoPreference) view.findViewById(R.id.tw_navibar_import_theme);
        if (!com.dofun.carsetting.f.b.p() && !com.dofun.carsetting.f.b.H() && !com.dofun.carsetting.f.b.F() && !com.dofun.carsetting.f.b.G() && !com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.J()) {
            if (com.dofun.carsetting.f.b.a().contains("WINDOW")) {
                this.s.setVisibility(0);
            } else {
                this.s.setVisibility(8);
            }
        }
        if (com.dofun.carsetting.f.b.a().contains("BBA")) {
            this.D.setVisibility(0);
        } else {
            this.D.setVisibility(8);
        }
        this.I = (SwitchPreference) view.findViewById(R.id.sp_google_public_dns);
        this.J = (SwitchPreference) view.findViewById(R.id.trusted_module);
        this.an = (InfoPreference) view.findViewById(R.id.microphone_parameter);
        this.ao = (InfoPreference) view.findViewById(R.id.mic_select);
        this.K = (SwitchPreference) view.findViewById(R.id.sp_disable_hw_overlays);
        this.L = (SwitchPreference) view.findViewById(R.id.spdif_switch);
        this.ap.setOnPreferenceChangeListener(this);
        this.x.setOnPreferenceChangeListener(this);
        this.g.setOnPreferenceChangeListener(this);
        this.k.setOnPreferenceChangeListener(this);
        this.l.setOnPreferenceChangeListener(this);
        this.m.setOnPreferenceChangeListener(this);
        this.D.setOnPreferenceChangeListener(this);
        this.n.setOnPreferenceChangeListener(this);
        this.o.setOnPreferenceChangeListener(this);
        this.r.setOnPreferenceChangeListener(this);
        this.t.setOnPreferenceChangeListener(this);
        this.u.setOnPreferenceChangeListener(this);
        this.v.setOnPreferenceChangeListener(this);
        this.H.setOnPreferenceChangeListener(this);
        this.w.setOnPreferenceChangeListener(this);
        this.s.setOnPreferenceChangeListener(this);
        this.G.a((com.dofun.carsetting.g.g) this);
        this.F.a((com.dofun.carsetting.g.l) this);
        this.F.setTvValue(this.P);
        this.y.setOnPreferenceChangeListener(this);
        this.z.setOnPreferenceChangeListener(this);
        this.C.setOnPreferenceChangeListener(this);
        this.A.setOnPreferenceChangeListener(this);
        this.I.setOnPreferenceChangeListener(this);
        this.J.setOnPreferenceChangeListener(this);
        this.E.setOnPreferenceChangeListener(this);
        this.K.setOnPreferenceChangeListener(this);
        this.L.setOnPreferenceChangeListener(this);
        this.ac.setOnPreferenceChangeListener(this);
        this.ao.setOnPreferenceChangeListener(this);
        this.O = new ImportExportExtensionSettings();
        this.O.a();
        this.O.a(getActivity(), this.b, this.c, this.d, this.e);
        this.R = (InfoPreference) view.findViewById(R.id.brake_mode);
        this.R.setOnPreferenceChangeListener(this);
        this.S = (InfoPreference) view.findViewById(R.id.logo_settings);
        this.S.setOnPreferenceChangeListener(this);
        this.T = (InfoPreference) view.findViewById(R.id.key_update);
        this.T.setOnPreferenceChangeListener(this);
        this.U = (InfoPreference) view.findViewById(R.id.system_ta);
        this.U.setOnPreferenceChangeListener(this);
        this.V = (InfoPreference) view.findViewById(R.id.radio_region);
        this.V.setOnPreferenceChangeListener(this);
        this.W = (InfoPreference) view.findViewById(R.id.backup_apk);
        this.W.setOnPreferenceChangeListener(this);
        this.W.setSaveValue(false);
        this.aa = (InfoPreference) view.findViewById(R.id.ant_pwr);
        this.aa.setOnPreferenceChangeListener(this);
        this.ad = (InfoPreference) view.findViewById(R.id.forward_looking_switch);
        this.ad.setOnPreferenceChangeListener(this);
        this.ae = (InfoPreference) view.findViewById(R.id.fan_temp);
        this.ae.setOnPreferenceChangeListener(this);
        this.ab = (InfoPreference) view.findViewById(R.id.front_view);
        this.ab.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.b.F()) {
            this.ab.a(getResources().getStringArray(R.array.front_view_entries_t100), getResources().getStringArray(R.array.front_in_aux_tv_t100));
        }
        this.af = (InfoPreference) view.findViewById(R.id.front_view_mirror);
        this.af.setOnPreferenceChangeListener(this);
        this.X = (InfoPreference) view.findViewById(R.id.btn_car_choose);
        this.X.setOnPreferenceChangeListener(this);
        this.X.setValueTip(k());
        this.ag = (InfoPreference) view.findViewById(R.id.io_key);
        this.ag.setOnPreferenceChangeListener(this);
        com.dofun.carsetting.f.h.a(new Handler(), view.findViewById(R.id.factory_settings), 3000L, new View.OnLongClickListener() { // from class: com.dofun.carsetting.fragment.j.1
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                ((MainActivity) j.this.getActivity()).a(new s());
                return true;
            }
        });
        this.ah = (InfoPreference) view.findViewById(R.id.eq_settings);
        this.ah.setOnPreferenceChangeListener(this);
        this.ah.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "EQ_SETTINGS", 0)));
        this.ai = (InfoPreference) view.findViewById(R.id.dsp_mode);
        this.ai.setOnPreferenceChangeListener(this);
        this.aj = (InfoPreference) view.findViewById(R.id.system_iis);
        this.aj.setOnPreferenceChangeListener(this);
        this.aj.setValue(SystemProperties.get("persist.tw.iis", "1"));
        this.ak = (InfoPreference) view.findViewById(R.id.spdif_out);
        this.ak.setOnPreferenceChangeListener(this);
        this.ak.setValue(SystemProperties.get("persist.spdif.out", "1"));
        this.al = (InfoPreference) view.findViewById(R.id.dsp_mode_public);
        this.al.setOnPreferenceChangeListener(this);
        this.B = (InfoPreference) view.findViewById(R.id.channel_balance_title);
        this.B.setOnPreferenceChangeListener(this);
        this.am = (InfoPreference) view.findViewById(R.id.reset_long_click);
        this.am.setOnPreferenceChangeListener(this);
        this.am.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_SHUTSCREEN_OPEN", 0)));
        this.I.setValue(SystemProperties.getInt("persist.tw.Brazil", 0) == 1);
        this.J.setValue(SystemProperties.getBoolean("persist.tw.verify", false));
        if (com.dofun.carsetting.f.b.G()) {
            this.ao.a(getResources().getStringArray(R.array.mic_t527_mode_entries), getResources().getStringArray(R.array.performance_mode_value));
        }
        if (com.dofun.carsetting.f.b.F()) {
            this.L.setValue(com.dofun.carsetting.f.b.d("/dev/snd/pcmC3D0c"));
        } else {
            this.L.setValue(SystemProperties.getBoolean("persist.tw.i2stospdif", false));
        }
        h();
        this.an.setOnPreferenceChangeListener(this);
        this.an.setValue(SystemProperties.get("persist.tw.microphone", "0"));
        if (this.j.ad) {
            if (com.dofun.carsetting.f.b.G()) {
                iA = SystemProperties.getInt("persist.mic.front", 0);
            } else {
                iA = com.dofun.carsetting.f.w.a("DeviceMic");
                if (iA == -1) {
                    iA = com.dofun.carsetting.f.b.bc();
                    SystemProperties.set("persist.tw.mic.select", iA + "");
                    com.dofun.carsetting.f.w.a(this.f284a, "DeviceMic", iA);
                } else if (iA == -2) {
                    if (SystemProperties.getInt("persist.tw.mic.select", -1) == -1) {
                        iA = com.dofun.carsetting.f.b.bc();
                        SystemProperties.set("persist.tw.mic.select", iA + "");
                    }
                } else {
                    com.dofun.carsetting.f.b.a(this.f284a, iA);
                    SystemProperties.set("persist.tw.mic.select", iA + "");
                }
            }
            Log.d("FactoryFragment", "mMic_select:" + iA);
            if (iA >= 0) {
                this.ao.setVisibility(0);
                this.ao.setValue(iA + "");
            }
        }
        if (com.dofun.carsetting.f.b.F()) {
            this.B.setVisibility(8);
            this.E.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.a().contains("-NaviBar")) {
            this.ap.setVisibility(0);
        }
    }

    private void h() {
        if (com.dofun.carsetting.f.b.T()) {
            this.aq.setVisibility(8);
            this.ar.setVisibility(8);
            this.aa.setVisibility(8);
            this.V.setVisibility(8);
            this.p.setVisibility(8);
            this.o.setVisibility(8);
            this.R.setVisibility(8);
            this.af.setVisibility(8);
            this.ad.setVisibility(8);
            this.f.setVisibility(8);
            this.ag.setVisibility(8);
            this.l.setVisibility(8);
            this.n.setVisibility(8);
            this.T.setVisibility(8);
            this.r.setVisibility(8);
        }
        this.af.setVisibility(this.j.ac ? 0 : 8);
        if (!com.dofun.carsetting.f.b.aF()) {
            this.R.setVisibility(8);
        }
        this.ae.setVisibility(((com.dofun.carsetting.f.b.O() && !com.dofun.carsetting.f.b.U()) || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) ? 0 : 8);
        this.ab.setVisibility(com.dofun.carsetting.f.b.aR() ? 0 : 8);
        if (com.dofun.carsetting.f.b.au()) {
            this.Z.setVisibility(0);
        } else {
            this.Z.setVisibility(8);
        }
        this.A.setVisibility(this.j.i ? 0 : 8);
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.G()) {
            this.z.a(getResources().getStringArray(R.array.cid_entries_t5), getResources().getStringArray(R.array.cid_values_t5));
        }
        switch (this.j.j) {
            case -1:
                this.y.setVisibility(0);
                this.z.setVisibility(0);
                break;
            case 0:
                this.y.setVisibility(8);
                this.z.setVisibility(8);
                break;
            case 1:
                this.y.setVisibility(0);
                this.z.setVisibility(8);
                break;
            case 2:
                this.y.setVisibility(8);
                this.z.setVisibility(0);
                break;
        }
        if (com.dofun.carsetting.f.b.a().contains("-HV")) {
            this.q.setVisibility(0);
        }
        if (this.f284a.write(65521) == 35) {
            this.al.setVisibility(8);
            this.L.setVisibility(8);
        } else {
            this.ah.setVisibility(8);
            this.ai.setVisibility(8);
            this.aj.setVisibility(8);
            this.ak.setVisibility(8);
            if (com.dofun.carsetting.f.b.s()) {
                this.al.setVisibility(0);
            }
            if (com.dofun.carsetting.f.b.G() || (com.dofun.carsetting.f.b.F() && this.f284a.write(65521) == 768)) {
                this.L.setVisibility(0);
            } else {
                this.L.setVisibility(8);
            }
        }
        this.am.setVisibility(com.dofun.carsetting.f.b.aB() ? 0 : 8);
        this.J.setVisibility(com.dofun.carsetting.f.b.aD() ? 0 : 8);
        this.g.setVisibility(com.dofun.carsetting.f.b.aH() ? 0 : 8);
        this.k.setVisibility(com.dofun.carsetting.f.b.aH() ? 0 : 8);
        this.m.setVisibility(com.dofun.carsetting.f.b.aI() ? 0 : 8);
        if (com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I()) {
            this.an.setVisibility(0);
        } else {
            this.an.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.aZ()) {
            this.K.setVisibility(0);
        } else {
            this.K.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.O()) {
            if (com.dofun.carsetting.f.b.aj()) {
                return;
            }
            this.ac.setVisibility(8);
            if (com.dofun.carsetting.f.b.e()) {
                this.ac.setVisibility(0);
                return;
            }
            return;
        }
        if (com.dofun.carsetting.f.b.at()) {
            this.ac.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        String str;
        String str2;
        c();
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) == 1 || com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.t.setVisibility(8);
        }
        this.f.a(new com.dofun.carsetting.g.b() { // from class: com.dofun.carsetting.fragment.j.12
            @Override // com.dofun.carsetting.g.b
            public void a(BacklightPreference backlightPreference, int i, int i2, boolean z) {
                switch (i) {
                    case 0:
                        if (z && i2 <= j.this.N + 27) {
                            j.this.M = i2;
                            break;
                        }
                        break;
                    case 1:
                        if (z && i2 >= j.this.M - 27) {
                            j.this.N = i2;
                            break;
                        }
                        break;
                }
            }
        });
        this.f.getBacklightEleAjustDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.j.20
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                j.this.f284a.write(276, 2, ((j.this.N + 33) << 8) | (j.this.M + 5));
                j.this.f284a.write(276, 3);
            }
        });
        this.f.getBacklightEleAjustDialog().setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.dofun.carsetting.fragment.j.21
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                j.this.f.a(false, 0, j.this.M, j.this.N);
            }
        });
        this.s.setValue(SystemProperties.get("persist.tw.forcepip", "0"));
        this.U.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_TA", 0)));
        this.ad.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0)));
        if (com.dofun.carsetting.f.b.F()) {
            this.as = String.valueOf(Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 2));
        } else {
            this.as = String.valueOf(Settings.System.getInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", 0));
        }
        this.ab.setValue(this.as);
        if (com.dofun.carsetting.f.b.O() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.Q = this.f284a.write(65523, 0, 0, "car_reverse\nmirror");
            this.af.setValue(String.valueOf((this.Q & 2) >> 1));
            this.ac.setValue(String.valueOf(this.f284a.write(65523, 0, 0, "car_reverse\nreverse_unused")));
        } else {
            this.ac.setValue(SystemProperties.get("persist.reverse.vr", "0"));
            switch (this.as) {
                case "2":
                case "0":
                    if (SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                        str = SystemProperties.get("persist.camerafront.mirror", "0");
                    } else {
                        str = SystemProperties.get("persist.camera.mirror2", "0");
                    }
                    this.af.setValue(str);
                    break;
                case "1":
                    if (SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                        str2 = SystemProperties.get("persist.camerafront.mirror", "0");
                    } else {
                        str2 = SystemProperties.get("persist.camera.mirror1", "0");
                    }
                    this.af.setValue(str2);
                    break;
            }
        }
        this.z.setValue(SystemProperties.get("persist.tw.cid", "0"));
        this.C.setValue(SystemProperties.get("persist.5g.channel", "36"));
        this.E.setValue(com.dofun.carsetting.f.b.aJ());
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.f284a != null) {
            this.f284a.addHandler("FactoryFragment", this.aJ);
            this.f284a.write(267, 255);
            this.f284a.write(265, 255);
            this.f284a.write(276, 255);
        }
        boolean zI = this.O.i();
        Log.d("FactoryFragment", "onResume:isAutomatic:" + zI + " cmd:" + this.b);
        if (zI) {
            return;
        }
        if (this.b != -1) {
            this.O.a(getActivity(), this.b, this.c, this.d, this.e);
        }
        switch (this.b) {
            case 2:
                com.dofun.carsetting.c.a aVar = new com.dofun.carsetting.c.a(getActivity());
                aVar.a(MyApp.a().getString(R.string.system_will_enter_update_mode));
                aVar.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.j.23
                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void a() {
                        if (TextUtils.isEmpty(j.this.c) || !new File(j.this.c).canRead()) {
                            return;
                        }
                        j.this.O.d(j.this.c);
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void b() {
                        j.this.O.j();
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void c() {
                        if (new File(j.this.c).canRead()) {
                            j.this.O.d(j.this.c);
                        }
                    }
                });
                aVar.show();
                break;
            case 3:
                com.dofun.carsetting.c.a aVar2 = new com.dofun.carsetting.c.a(getActivity());
                aVar2.a(MyApp.a().getString(R.string.system_will_enter_update_mode));
                aVar2.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.j.24
                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void a() {
                        j.this.O.d();
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void b() {
                        j.this.O.j();
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void c() {
                        j.this.O.d();
                    }
                });
                aVar2.show();
                break;
            case 4:
                com.dofun.carsetting.c.a aVar3 = new com.dofun.carsetting.c.a(getActivity());
                aVar3.a(MyApp.a().getString(R.string.system_will_import_config));
                aVar3.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.j.25
                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void a() {
                        j.this.O.b(j.this.c);
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void b() {
                        j.this.O.j();
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void c() {
                        j.this.O.b(j.this.c);
                    }
                });
                aVar3.show();
                break;
            case 5:
                com.dofun.carsetting.c.a aVar4 = new com.dofun.carsetting.c.a(getActivity());
                aVar4.a(MyApp.a().getString(R.string.system_will_reboot));
                aVar4.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.j.26
                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void a() {
                        j.this.O.h();
                        j.this.aJ.sendEmptyMessageDelayed(65281, 1000L);
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void b() {
                        j.this.O.j();
                    }

                    @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                    public void c() {
                        j.this.aJ.sendEmptyMessageDelayed(65281, 1000L);
                    }
                });
                aVar4.show();
                break;
        }
        this.b = -1;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.f284a != null) {
            this.f284a.removeHandler("FactoryFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.f284a != null) {
            this.f284a.removeHandler("FactoryFragment");
        }
        if (this.aA != null) {
            this.aA.dismiss();
            this.aA = null;
        }
        if (this.O != null) {
            this.O.j();
            this.O.b();
        }
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) throws Throwable {
        String str;
        String str2;
        Intent intent;
        Dialog hVar;
        try {
            int i = 1;
            try {
                switch (twPreference.getId()) {
                    case R.id.ant_pwr /* 2131230756 */:
                        this.f284a.write(267, 8, Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.ap_channel_setting /* 2131230757 */:
                        SystemProperties.set("persist.5g.channel", (String) obj);
                        return false;
                    case R.id.area_key_title /* 2131230761 */:
                        a("com.dofun.carsetting", "com.dofun.carsetting.activity.TouchActivity");
                        return false;
                    case R.id.backup_apk /* 2131230767 */:
                        int i2 = Integer.parseInt((String) obj);
                        if (i2 == 0) {
                            this.aw = a(getActivity(), getResources().getString(R.string.backup_apk));
                            this.aw.show();
                        }
                        a(i2);
                        this.W.c();
                        this.W.setSaveValue(false);
                        return false;
                    case R.id.brake_mode /* 2131230773 */:
                        this.f284a.write(267, 1, Integer.valueOf((String) obj).intValue());
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
                    case R.id.cfg_import /* 2131230799 */:
                        this.O.b((String) null);
                        return false;
                    case R.id.change_imei /* 2131230801 */:
                        if (com.dofun.carsetting.f.b.H()) {
                            a("com.tw.imei", "com.tw.imei.MainActivity");
                        } else {
                            a("com.sprd.engineermode", "com.sprd.engineermode.debuglog.ImeiActivity");
                        }
                        return false;
                    case R.id.channel_balance_title /* 2131230802 */:
                        ((MainActivity) getActivity()).a(new e());
                        return false;
                    case R.id.config_title /* 2131230832 */:
                        ((MainActivity) getActivity()).a(new g());
                        return false;
                    case R.id.direction_of_the_knob1 /* 2131230852 */:
                        this.f284a.write(267, 7, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.direction_of_the_knob2 /* 2131230853 */:
                        this.f284a.write(267, 7, Integer.valueOf(String.valueOf(obj)).intValue() == 0 ? 2 : 3);
                        return false;
                    case R.id.dsp_mode /* 2131230858 */:
                        SystemProperties.set("persist.dsp.out", "0");
                        this.f284a.write(267, 10, Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.dsp_mode_public /* 2131230859 */:
                        this.f284a.write(267, 10, Integer.valueOf((String) obj).intValue());
                        m();
                        return false;
                    case R.id.engineer_mode /* 2131230865 */:
                        a("com.sprd.engineermode", "com.sprd.engineermode.EngineerModeActivity");
                        return false;
                    case R.id.eq_settings /* 2131230866 */:
                        Settings.System.putInt(getActivity().getContentResolver(), "EQ_SETTINGS", Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.fan_temp /* 2131230873 */:
                        this.f284a.write(267, 0, Integer.parseInt((String) obj));
                        return false;
                    case R.id.force_imei /* 2131230881 */:
                        SystemProperties.set("persist.tw.cid", (String) obj);
                        b("tw.cid.type", (String) obj);
                        return false;
                    case R.id.forward_looking_switch /* 2131230883 */:
                        Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_FOWARD_SWITCH", Integer.parseInt((String) obj));
                        this.ad.setValue((String) obj);
                        return false;
                    case R.id.front_view /* 2131230884 */:
                        SystemProperties.set("SYSTEM_FOWARD_CHANNEL", (String) obj);
                        Settings.System.putInt(MyApp.a().getContentResolver(), "SYSTEM_FOWARD_CHANNEL", Integer.parseInt((String) obj));
                        this.as = (String) obj;
                        switch (this.as) {
                            case "2":
                            case "0":
                                if (com.dofun.carsetting.f.b.L() && SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                                    str = SystemProperties.get("persist.camerafront.mirror", "0");
                                } else {
                                    str = SystemProperties.get("persist.camera.mirror2", "0");
                                }
                                this.af.setValue(str);
                                return false;
                            case "1":
                                if (com.dofun.carsetting.f.b.L() && SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                                    str2 = SystemProperties.get("persist.camerafront.mirror", "0");
                                } else {
                                    str2 = SystemProperties.get("persist.camera.mirror1", "0");
                                }
                                this.af.setValue(str2);
                                return false;
                            default:
                                return false;
                        }
                    case R.id.front_view_mirror /* 2131230885 */:
                        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                            int i3 = Integer.parseInt((String) obj);
                            if (i3 == 0) {
                                this.Q &= -3;
                            } else {
                                this.Q |= 2;
                            }
                            Settings.System.putInt(this.i.getContentResolver(), "Front_mirror", i3);
                            this.aJ.postDelayed(new Runnable() { // from class: com.dofun.carsetting.fragment.j.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    String str3 = com.dofun.carsetting.f.b.G() ? "/soc" : "/soc@03000000";
                                    j.this.f284a.write(65282, 0, 0, String.format("run_command\nfdt set " + str3 + "/car_reverse mirror <0x%x>\nfdt save", Integer.valueOf(j.this.Q)));
                                }
                            }, 1500L);
                        } else {
                            switch (this.as) {
                                case "2":
                                case "0":
                                    if (SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                                        SystemProperties.set("persist.camerafront.mirror", (String) obj);
                                        break;
                                    } else {
                                        SystemProperties.set("persist.camera.mirror2", (String) obj);
                                        break;
                                    }
                                    break;
                                case "1":
                                    if (SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                                        SystemProperties.set("persist.camerafront.mirror", (String) obj);
                                        break;
                                    } else {
                                        SystemProperties.set("persist.camera.mirror1", (String) obj);
                                        break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        return false;
                    case R.id.install_apk /* 2131230909 */:
                        a("com.dofun.carsetting", "com.dofun.carsetting.activity.apkinstall.InstallActivity");
                        return false;
                    case R.id.io_key /* 2131230912 */:
                        Intent intent3 = new Intent();
                        intent3.setClassName("com.dofun.carsetting", "com.dofun.carsetting.activity.TouchActivity");
                        intent3.putExtra("key", 1);
                        intent3.setFlags(268435456);
                        startActivity(intent3);
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
                    case R.id.key_update /* 2131230982 */:
                        switch (Integer.valueOf((String) obj).intValue()) {
                            case 0:
                                this.f284a.write(264, 129);
                                return false;
                            case 1:
                                this.f284a.write(264, 128);
                                return false;
                            case 2:
                                this.f284a.write(259, 129);
                                return false;
                            case 3:
                                this.f284a.write(259, 128);
                                return false;
                            default:
                                return false;
                        }
                    case R.id.key_wheel_title /* 2131230983 */:
                        try {
                            Intent intent4 = new Intent();
                            intent4.setClassName("com.tw.keypad", "com.tw.keypad.SteeringWheelActivity");
                            startActivity(intent4);
                            break;
                        } catch (Exception unused2) {
                            Intent intent5 = new Intent();
                            intent5.setClassName("com.android.settings", "com.android.settings.KeypadActivity");
                            startActivity(intent5);
                        }
                        return false;
                    case R.id.logo_settings /* 2131231050 */:
                        try {
                            Intent intent6 = new Intent();
                            intent6.setComponent(new ComponentName("com.dofun.carsetting", "com.dofun.carsetting.activity.LogoActivity"));
                            startActivity(intent6);
                            break;
                        } catch (Exception unused3) {
                            Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                        }
                        return false;
                    case R.id.mic_select /* 2131231062 */:
                        if (com.dofun.carsetting.f.b.G()) {
                            SystemProperties.set("persist.mic.front", (String) obj);
                            if (TextUtils.equals((String) obj, "0")) {
                                this.f284a.write(40730, 1, 0, "tinymix -D 0 23 0");
                                this.f284a.write(40730, 1, 0, "tinymix -D 0 24 1");
                            } else {
                                this.f284a.write(40730, 1, 0, "tinymix -D 0 23 1");
                                this.f284a.write(40730, 1, 0, "tinymix -D 0 24 0");
                            }
                        } else {
                            SystemProperties.set("persist.tw.mic.select", (String) obj);
                            com.dofun.carsetting.f.b.a(this.f284a, Integer.valueOf((String) obj).intValue());
                            com.dofun.carsetting.f.w.a(this.f284a, "DeviceMic", Integer.valueOf((String) obj).intValue());
                        }
                        return false;
                    case R.id.microphone_parameter /* 2131231063 */:
                        SystemProperties.set("persist.tw.microphone", (String) obj);
                        String str3 = this.j.S + "/db54/sprd/*";
                        String str4 = this.j.S + "/db42/sprd/*";
                        if (TextUtils.equals((String) obj, "1")) {
                            this.f284a.write(40730, 1, 0, "mount -o rw,remount /vendor");
                            this.f284a.write(40730, 1, 0, "cp -rf " + str3 + " /vendor/etc/audio_params/sprd/");
                        } else {
                            this.f284a.write(40730, 1, 0, "mount -o rw,remount /vendor");
                            this.f284a.write(40730, 1, 0, "cp -rf " + str4 + " /vendor/etc/audio_params/sprd/");
                        }
                        return false;
                    case R.id.panel_settings_title /* 2131231090 */:
                        try {
                            Intent intent7 = new Intent();
                            intent7.setClassName("com.tw.keypad", "com.tw.keypad.PanelKeyActivity");
                            startActivity(intent7);
                            break;
                        } catch (Exception unused4) {
                            Intent intent8 = new Intent();
                            intent8.setClassName("com.android.settings", "com.android.settings.KeypadActivity");
                            intent8.putExtra("key", 1);
                            startActivity(intent8);
                        }
                        return false;
                    case R.id.performance_mode /* 2131231094 */:
                        com.dofun.carsetting.f.b.a(Integer.valueOf((String) obj).intValue() == 0 ? 576 : 672);
                        m();
                        return false;
                    case R.id.radio_region /* 2131231109 */:
                        this.f284a.write(265, 0, Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.reset_long_click /* 2131231138 */:
                        Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_SHUTSCREEN_OPEN", Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.resolution /* 2131231139 */:
                        this.f284a.write(267, 11, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.reverse_pattern /* 2131231149 */:
                        int i4 = Integer.parseInt((String) obj);
                        SystemProperties.set("persist.reverse.vr", (String) obj);
                        this.f284a.write(40730, 1, 0, "sync");
                        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                            this.f284a.write(267, 14, i4);
                            this.f284a.write(65282, 0, 0, String.format("run_command\nfdt set /soc@03000000/car_reverse reverse_unused <0x%x>\nfdt save", Integer.valueOf(i4)));
                        } else {
                            com.dofun.carsetting.f.u uVar = this.f284a;
                            if (Integer.parseInt((String) obj) != 2) {
                                i = 0;
                            }
                            uVar.write(267, 14, i);
                        }
                        return false;
                    case R.id.reverse_type /* 2131231151 */:
                        if (com.dofun.carsetting.f.b.o()) {
                            hVar = new com.dofun.carsetting.widget.g(getActivity());
                        } else if (com.dofun.carsetting.f.b.s()) {
                            hVar = new com.dofun.carsetting.widget.i(getActivity());
                        } else if (com.dofun.carsetting.f.b.F()) {
                            hVar = new com.dofun.carsetting.widget.c(getActivity());
                        } else if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                            hVar = new com.dofun.carsetting.widget.h(getActivity());
                        } else if (com.dofun.carsetting.f.b.H()) {
                            hVar = new com.dofun.carsetting.widget.f(getActivity());
                        } else if (com.dofun.carsetting.f.b.G()) {
                            hVar = new com.dofun.carsetting.widget.e(getActivity());
                        } else {
                            hVar = new com.dofun.carsetting.widget.d(getActivity());
                        }
                        hVar.create();
                        hVar.show();
                        return false;
                    case R.id.rotation_switch /* 2131231163 */:
                        this.f284a.write(267, 17, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.screen_reference /* 2131231173 */:
                        ((MainActivity) getActivity()).a(new f());
                        return false;
                    case R.id.select_launcher /* 2131231189 */:
                        this.aB = Integer.parseInt(SystemProperties.get(this.ay, "0"));
                        if (this.aB == 0) {
                            this.aE = Integer.parseInt(SystemProperties.get(this.az, "1"));
                        }
                        i();
                        return false;
                    case R.id.sp_disable_hw_overlays /* 2131231206 */:
                        SystemProperties.set("persist.tw.disable.hwc", ((Boolean) obj).booleanValue() ? "1" : "0");
                        this.f284a.write(40730, 1, 0, "sync");
                        com.dofun.carsetting.f.c.a(((Boolean) obj).booleanValue());
                        m();
                        return false;
                    case R.id.sp_google_public_dns /* 2131231207 */:
                        SystemProperties.set("persist.tw.Brazil", ((Boolean) obj).booleanValue() ? "1" : "0");
                        m();
                        return false;
                    case R.id.spdif_out /* 2131231218 */:
                        b("com.tw.music");
                        b("com.tw.video");
                        b("com.tw.bt");
                        SystemProperties.set("persist.spdif.out", (String) obj);
                        return false;
                    case R.id.spdif_switch /* 2131231219 */:
                        if (com.dofun.carsetting.f.b.F()) {
                            com.dofun.carsetting.f.u uVar2 = this.f284a;
                            StringBuilder sb = new StringBuilder();
                            sb.append("run_command\n");
                            sb.append("fdt set /soc/spdif-controller status ");
                            sb.append(((Boolean) obj).booleanValue() ? "okay" : "disabled");
                            sb.append(" \n\nfdt save");
                            uVar2.write(65282, 0, 0, sb.toString());
                        } else {
                            SystemProperties.set("persist.tw.i2stospdif", String.valueOf((Boolean) obj));
                        }
                        return false;
                    case R.id.switch_knobs1_and2 /* 2131231231 */:
                        this.f284a.write(267, 7, ((Boolean) obj).booleanValue() ? 5 : 4);
                        return false;
                    case R.id.switch_the_steering_wheel /* 2131231232 */:
                        this.f284a.write(267, 15, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.system_iis /* 2131231237 */:
                        SystemProperties.set("persist.tw.iis", (String) obj);
                        this.f284a.write(515, 255);
                        return false;
                    case R.id.system_ta /* 2131231245 */:
                        Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_TA", Integer.valueOf((String) obj).intValue());
                        return false;
                    case R.id.trusted_module /* 2131231269 */:
                        SystemProperties.set("persist.tw.verify", String.valueOf((Boolean) obj));
                        return false;
                    case R.id.tv_mode_title /* 2131231305 */:
                        this.f284a.write(267, 2, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.tv_norm /* 2131231306 */:
                        this.f284a.write(267, 3, Integer.valueOf(String.valueOf(obj)).intValue());
                        return false;
                    case R.id.tw_navibar_import_theme /* 2131231354 */:
                        com.dofun.carsetting.c.a aVar = new com.dofun.carsetting.c.a(getActivity());
                        aVar.a(MyApp.a().getString(R.string.navibar_import_theme));
                        aVar.a(new com.dofun.carsetting.c.a.InterfaceC0013a() { // from class: com.dofun.carsetting.fragment.j.3
                            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                            public void b() {
                            }

                            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                            public void c() {
                            }

                            @Override // com.dofun.carsetting.c.a.InterfaceC0013a
                            public void a() {
                                j.this.g();
                            }
                        });
                        aVar.show();
                        return false;
                    default:
                        return false;
                }
            } catch (Exception unused5) {
            }
        } catch (Exception e) {
            Log.e("FactoryFragment", "onPreferenceChange: " + e.getMessage());
        }
    }

    private void i() {
        this.aA = new Dialog(getActivity());
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_launcher, (ViewGroup) null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_dialog_close);
        RadioButton radioButton = (RadioButton) viewInflate.findViewById(R.id.radio_theme);
        RadioButton radioButton2 = (RadioButton) viewInflate.findViewById(R.id.radio_bba);
        RadioButton radioButton3 = (RadioButton) viewInflate.findViewById(R.id.radio_bm);
        RadioButton radioButton4 = (RadioButton) viewInflate.findViewById(R.id.radio_ad);
        RadioButton radioButton5 = (RadioButton) viewInflate.findViewById(R.id.radio_benz);
        RadioButton radioButton6 = (RadioButton) viewInflate.findViewById(R.id.radio_lexus);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) viewInflate.findViewById(R.id.scroll_view_bba);
        if (this.aB == 0) {
            horizontalScrollView.setVisibility(0);
            radioButton.setChecked(false);
            radioButton2.setChecked(true);
            switch (this.aE) {
                case 0:
                    radioButton3.setBackground(getResources().getDrawable(R.drawable.bg_selector_launcher));
                    break;
                case 1:
                    radioButton4.setBackground(getResources().getDrawable(R.drawable.bg_selector_launcher));
                    break;
                case 2:
                    radioButton5.setBackground(getResources().getDrawable(R.drawable.bg_selector_launcher));
                    break;
                case 3:
                    radioButton6.setBackground(getResources().getDrawable(R.drawable.bg_selector_launcher));
                    break;
            }
        } else {
            horizontalScrollView.setVisibility(8);
            radioButton.setChecked(true);
            radioButton2.setChecked(false);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                j.this.aA.dismiss();
            }
        });
        radioButton.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB == 1) {
                    return;
                }
                j.this.f284a.write(40730, 3, 0, "pm unhide com.dofun.variety");
                j.this.f284a.write(40730, 3, 0, "pm hide com.android.launcher");
                j.this.f284a.write(40730, 3, 0, "sync");
                j.this.aA.findViewById(R.id.scroll_view_bba).setVisibility(8);
                j.this.aB = 1;
                SystemProperties.set(j.this.ay, "" + j.this.aB);
                j.this.aA.dismiss();
                j.this.j();
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB == 0) {
                    return;
                }
                j.this.f284a.write(40730, 3, 0, "pm unhide com.android.launcher");
                j.this.f284a.write(40730, 3, 0, "pm hide com.dofun.variety");
                j.this.f284a.write(40730, 3, 0, "sync");
                j.this.aA.findViewById(R.id.scroll_view_bba).setVisibility(0);
                j.this.aB = 0;
                SystemProperties.set(j.this.ay, "" + j.this.aB);
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB != 0) {
                    return;
                }
                j.this.aE = 0;
                SystemProperties.set(j.this.az, "" + j.this.aE);
                Intent intent = new Intent("action.start.layout");
                intent.putExtra(j.this.az, j.this.aE);
                j.this.getActivity().sendBroadcast(intent);
                j.this.aA.dismiss();
                j.this.j();
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB != 0) {
                    return;
                }
                j.this.aE = 1;
                SystemProperties.set(j.this.az, "" + j.this.aE);
                Intent intent = new Intent("action.start.layout");
                intent.putExtra(j.this.az, j.this.aE);
                j.this.getActivity().sendBroadcast(intent);
                j.this.aA.dismiss();
                j.this.j();
            }
        });
        radioButton5.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB != 0) {
                    return;
                }
                j.this.aE = 2;
                SystemProperties.set(j.this.az, "" + j.this.aE);
                Intent intent = new Intent("action.start.layout");
                intent.putExtra(j.this.az, j.this.aE);
                j.this.getActivity().sendBroadcast(intent);
                j.this.aA.dismiss();
                j.this.j();
            }
        });
        radioButton6.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (j.this.aB != 0) {
                    return;
                }
                j.this.aE = 3;
                SystemProperties.set(j.this.az, "" + j.this.aE);
                Intent intent = new Intent("action.start.layout");
                intent.putExtra(j.this.az, j.this.aE);
                j.this.getActivity().sendBroadcast(intent);
                j.this.aA.dismiss();
                j.this.j();
            }
        });
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) getResources().getDimension(R.dimen.tw_dp_w560), -2);
        layoutParams.gravity = 17;
        this.aA.setContentView(viewInflate, layoutParams);
        this.aA.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        this.f284a.write(40730, 1, 0, "input keyevent 3");
    }

    @Override // com.dofun.carsetting.g.g
    public void a(EditTextPreference editTextPreference, Editable editable) {
        if (editTextPreference.getId() == R.id.rb_setting && TextUtils.equals(editable.toString(), "7890")) {
            try {
                getActivity().startActivity(new Intent("android.settings.SETTINGS"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            intent.setFlags(268435456);
            startActivity(intent);
        } catch (Exception unused) {
        }
    }

    @Override // com.dofun.carsetting.g.l
    public void a(MultiCheckPreference multiCheckPreference, int i, boolean[] zArr) {
        Settings.System.putInt(this.i.getContentResolver(), "CFG_EXPORT", i);
        if (multiCheckPreference.getId() != R.id.cfg_export) {
            return;
        }
        this.O.a(zArr);
    }

    public void a(int i, String str) {
        Log.d("FactoryFragment", "setAutoImportAndUpdateAction:cmd:" + i + " this.cmd:" + this.b + " path:" + str);
        if (i == -1 || this.b == i) {
            return;
        }
        this.b = i;
        this.c = str;
    }

    public void a(int i, String str, boolean z, boolean z2) {
        Log.d("FactoryFragment", "setAutoImportAndUpdateAction:cmd:" + i + " this.cmd:" + this.b + " path:" + str + " deleteUpdateFile:" + z + " wipeData:" + z2);
        if (i == -1 || this.b == i) {
            return;
        }
        this.b = i;
        this.c = str;
        this.d = z;
        this.e = z2;
    }

    private void a(final int i) {
        new Thread(new Runnable() { // from class: com.dofun.carsetting.fragment.j.11
            @Override // java.lang.Runnable
            public void run() {
                Message messageObtainMessage = j.this.aJ.obtainMessage();
                messageObtainMessage.what = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                messageObtainMessage.arg1 = i;
                j.this.f284a.write(40730, 2, 0, "rm -r /sdcard/.apk");
                if (i == 0) {
                    j.this.f284a.write(40730, 2, 0, "mkdir /sdcard/.apk");
                    ArrayList arrayList = new ArrayList();
                    arrayList.clear();
                    j.this.a("/data/app", arrayList);
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        j.this.c(((File) arrayList.get(i2)).getAbsolutePath());
                    }
                }
                j.this.aJ.sendMessage(messageObtainMessage);
            }
        }).start();
    }

    private String k() {
        try {
            String strL = l();
            HashMap map = new HashMap();
            for (String str : strL.split("\\+")) {
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
            Log.i("FactoryFragment", "getText  " + e.toString());
            return "";
        }
    }

    private String l() {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                if (new File("/storage/emulated/0/.CarChoose").exists()) {
                    FileUtils.setPermissions("/storage/emulated/0/.CarChoose", FrameMetricsAggregator.EVERY_DURATION, -1, -1);
                }
                Log.i("FactoryFragment", this.at + "/" + this.au + "     " + new File("/storage/emulated/0/.CarChoose").canRead());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.at);
                sb2.append("/");
                sb2.append(this.au);
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
                Log.i("FactoryFragment", " readSaveFile " + e.toString());
            }
        }
        return sb.toString();
    }

    private ProgressDialog a(Context context, String str) {
        ProgressDialog progressDialog = new ProgressDialog(context, 3);
        progressDialog.setMessage(str);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        Log.d("FactoryFragment", "showProgressDialog");
        return progressDialog;
    }

    public void c() {
        String str = this.j.t;
        boolean z = false;
        for (int i = 0; i < this.j.A.length; i++) {
            if (new File("/" + this.j.A[i] + "/" + str).exists()) {
                z = true;
            }
        }
        for (int i2 = 0; i2 < this.j.B.length; i2++) {
            if (new File("/" + this.j.B[i2] + "/" + str).exists()) {
                z = true;
            }
        }
        if (!z) {
            this.x.setVisibility(8);
        } else {
            this.x.setVisibility(0);
        }
    }

    private void a(final String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.13
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                SystemProperties.set("persist.tw.forcepip", str);
                if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    j.this.f284a.write(40730, 1, 0, "mount -o remount,rw /system_tw");
                } else {
                    j.this.f284a.write(40730, 1, 0, "mount -o remount,rw /");
                }
                if (TextUtils.equals(str, "1")) {
                    j.this.f284a.write(40730, 1, 0, "cp -rf " + j.this.j.S + "theme/sfp/theme.apk " + j.this.j.S + "theme/");
                    com.dofun.carsetting.f.u uVar = j.this.f284a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("cp -rf ");
                    sb.append(j.this.j.S);
                    sb.append("theme/sfp/theme.apk /data/tw/theme/");
                    uVar.write(40730, 1, 0, sb.toString());
                } else {
                    j.this.f284a.write(40730, 1, 0, "cp -rf " + j.this.j.S + "theme/public/theme.apk  " + j.this.j.S + "theme/");
                    com.dofun.carsetting.f.u uVar2 = j.this.f284a;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("cp -rf ");
                    sb2.append(j.this.j.S);
                    sb2.append("theme/public/theme.apk /data/tw/theme/");
                    uVar2.write(40730, 1, 0, sb2.toString());
                }
                j.this.f284a.write(40730, 3, 0, "chmod 755 " + j.this.j.S + "theme/theme.apk");
                j.this.f284a.write(40730, 3, 0, "chmod -R 777 /data/tw/theme");
                if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
                    j.this.f284a.write(40730, 3, 0, "mount -o remount,ro /system_tw");
                } else {
                    j.this.f284a.write(40730, 3, 0, "mount -o remount,ro /");
                }
                j.this.f284a.write(40730, 3, 0, "sync");
                j.this.aJ.sendEmptyMessageDelayed(65281, 1500L);
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.set_navi_app_dialog_title);
        builder.setMessage(R.string.set_navi_app_dialog_message);
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception unused) {
        }
        alertDialogCreate.show();
    }

    private void b(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (this.ax == null) {
            this.ax = ((MainActivity) getActivity()).d;
        }
        if (this.ax != null) {
            this.ax.b(str, str2);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void e() {
        h();
    }

    private void b(String str) {
        ((ActivityManager) this.i.getSystemService("activity")).forceStopPackage(str);
    }

    private void m() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.j.14
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                j.this.aJ.sendEmptyMessageDelayed(65281, 1000L);
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, (DialogInterface.OnClickListener) null);
        builder.setTitle(R.string.reboot_now);
        builder.setMessage(R.string.set_navi_app_dialog_message);
        builder.setCancelable(true);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.getWindow().setType(2003);
        try {
            WindowManagerGlobal.getWindowManagerService();
        } catch (Exception unused) {
        }
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, List<File> list) {
        File[] fileArrListFiles = new File(str).listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.j.15
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                if (file.isHidden() || file.getName().startsWith("vmd")) {
                    return false;
                }
                return file.isDirectory() || file.getName().endsWith("base.apk");
            }
        });
        for (int i = 0; i < fileArrListFiles.length; i++) {
            if (fileArrListFiles[i].isDirectory()) {
                a(fileArrListFiles[i].getAbsolutePath(), list);
            } else {
                list.add(fileArrListFiles[i]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        File file = new File(new File(str).getParent());
        File[] fileArrListFiles = file.listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.j.16
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                return !file2.isHidden() && file2.isFile() && file2.getName().endsWith(".apk");
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length != 1) {
            return;
        }
        Log.d("FactoryFragment", "checkAppNeedBackup:path:" + fileArrListFiles[0].getAbsolutePath());
        this.f284a.write(40730, 2, 0, "cp " + fileArrListFiles[0].getAbsolutePath() + " /sdcard/.apk/" + file.getName() + ".apk");
    }

    public boolean f() {
        if (this.O != null) {
            return this.O.i();
        }
        return false;
    }

    public void g() {
        this.f284a.write(40730, 1, 0, "mount -o remount,rw /");
        this.f284a.write(40730, 1, 0, "chmod 777 -R twdataconfig/NaviBar-theme");
        File[] fileArrListFiles = new File("/storage").listFiles(new FileFilter() { // from class: com.dofun.carsetting.fragment.j.17
            @Override // java.io.FileFilter
            public boolean accept(File file) {
                return file.canRead() && file.isDirectory() && file.getName().startsWith("usb");
            }
        });
        Log.e("tssdebug", "fileUSB = " + fileArrListFiles.length);
        int length = fileArrListFiles.length;
        for (int i = 0; i < length; i++) {
            String str = fileArrListFiles[i].getAbsolutePath() + "/NaviBar-theme";
            if (new File(str).exists()) {
                Log.e("tssdebug", "path = " + str);
                if (new File("/twdataconfig/NaviBar-theme").exists()) {
                    this.f284a.write(40730, 1, 0, "rm -r /twdataconfig/NaviBar-theme");
                }
                this.f284a.write(40730, 1, 0, "cp -rf " + str + "  /twdataconfig/");
                this.f284a.write(40730, 1, 0, "chmod 777 -R /twdataconfig/NaviBar-theme");
                this.aJ.postDelayed(new Runnable() { // from class: com.dofun.carsetting.fragment.j.18
                    @Override // java.lang.Runnable
                    public void run() {
                        if (new File("/twdataconfig/NaviBar-theme/SystemUITheme.apk").exists()) {
                            j.this.f284a.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUITheme.apk");
                        }
                        if (new File("/twdataconfig/NaviBar-theme/SystemUINavibar.apk").exists()) {
                            j.this.f284a.write(40730, 1, 0, "/system/bin/pm install -r /twdataconfig/NaviBar-theme/SystemUINavibar.apk");
                        }
                    }
                }, 1500L);
                this.aJ.postDelayed(new Runnable() { // from class: com.dofun.carsetting.fragment.j.19
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!new File("/twdataconfig/NaviBar-theme/").exists()) {
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_fail), 0).show();
                        } else {
                            j.this.f284a.write(40730, 3, 0, "cmd overlay enable-exclusive com.tw.systemui.theme_yellow");
                            Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_success), 0).show();
                        }
                    }
                }, 3000L);
            } else {
                Toast.makeText(MyApp.a(), MyApp.a().getString(R.string.cfg_import_fail), 0).show();
            }
        }
    }
}
