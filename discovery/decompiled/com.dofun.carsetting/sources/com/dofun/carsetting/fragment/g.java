package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dofun.carsetting.R;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: ConfigFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends ad<com.dofun.carsetting.presenter.f> implements View.OnClickListener, com.dofun.carsetting.g.j {
    private SwitchPreference A;
    private SwitchPreference B;
    private SwitchPreference C;
    private SwitchPreference D;
    private SwitchPreference E;
    private SwitchPreference F;
    private SwitchPreference G;
    private LinearLayout I;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f273a;
    private com.dofun.carsetting.f.u b;
    private InfoPreference c;
    private InfoPreference d;
    private InfoPreference e;
    private SwitchPreference f;
    private SwitchPreference g;
    private SwitchPreference k;
    private SwitchPreference l;
    private SwitchPreference m;
    private SwitchPreference n;
    private SwitchPreference o;
    private SwitchPreference p;
    private SwitchPreference q;
    private SwitchPreference r;
    private SwitchPreference s;
    private SwitchPreference t;
    private SwitchPreference u;
    private SwitchPreference v;
    private SwitchPreference w;
    private SwitchPreference x;
    private SwitchPreference y;
    private SwitchPreference z;
    private int H = -1;
    private Map<Integer, Boolean> J = new HashMap();
    private Handler K = new Handler() { // from class: com.dofun.carsetting.fragment.g.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                int i = message.what;
                if (i == 266) {
                    if (message.arg1 == 0) {
                        try {
                            g.this.f273a = Integer.parseInt(((String) message.obj).split("-")[3].substring(3, 4), 16);
                            Log.i("xiang", "eqType: " + g.this.f273a);
                            int i2 = g.this.f273a;
                            if (i2 == 1 || i2 == 3) {
                                if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                                    g.this.A.setVisibility(0);
                                }
                            } else if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F()) {
                                g.this.A.setVisibility(8);
                            }
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    return;
                }
                if (i != 274) {
                    return;
                }
                Log.d("ConfigFragment", "onPreferenceChange: 0x0112:mConfig:" + Integer.toHexString(g.this.H) + " msg.arg1:" + Integer.toHexString(message.arg1));
                if (g.this.H != message.arg1) {
                    g.this.H = message.arg1;
                    Log.d("ConfigFragment", "onPreferenceChange: mConfig:" + Integer.toHexString(g.this.H));
                    g.this.f.setValue((g.this.H & 1) == 1);
                    Log.d("ConfigFragment", "0x0112 & 0x02:" + (g.this.H & 2));
                    Log.d("ConfigFragment", "0x0112 & 0x04:" + (g.this.H & 4));
                    Log.d("ConfigFragment", "0x0112 & 0x06:" + (g.this.H & 6));
                    if ((g.this.H & 6) == 6) {
                        g.this.J.put(6, true);
                        g.this.J.put(4, false);
                        g.this.J.put(2, false);
                        g.this.c.setValue("6");
                        g.this.c.setValueTip(g.this.c.getValue());
                    } else if ((g.this.H & 4) == 4) {
                        g.this.J.put(6, false);
                        g.this.J.put(4, true);
                        g.this.J.put(2, false);
                        g.this.c.setValue("4");
                        g.this.c.setValueTip(g.this.c.getValue());
                    } else if ((g.this.H & 2) == 2) {
                        g.this.J.put(6, false);
                        g.this.J.put(4, false);
                        g.this.J.put(2, true);
                        g.this.c.setValue("2");
                        g.this.c.setValueTip(g.this.c.getValue());
                    } else {
                        g.this.J.put(6, false);
                        g.this.J.put(4, false);
                        g.this.J.put(2, false);
                        g.this.c.setValue("0");
                        g.this.c.setValueTip(g.this.c.getValue());
                    }
                    g.this.g.setValue((g.this.H & 8) == 8);
                    g.this.k.setValue((g.this.H & 16) != 16);
                    g.this.l.setValue((g.this.H & 32) == 32);
                    g.this.r.setValue((g.this.H & 64) == 64);
                    g.this.o.setValue((g.this.H & 256) != 256);
                    g.this.p.setValue((g.this.H & 512) != 512);
                    g.this.q.setValue((g.this.H & 1024) != 1024);
                    g.this.m.setValue((g.this.H & 2048) == 2048);
                    g.this.x.setValue((g.this.H & 4096) != 4096);
                    g.this.y.setValue((g.this.H & 8192) != 8192);
                    g.this.t.setValue((g.this.H & 16384) == 16384);
                    g.this.J.put(1, Boolean.valueOf((g.this.H & 1) == 1));
                    g.this.J.put(8, Boolean.valueOf((g.this.H & 8) == 8));
                    g.this.J.put(16, Boolean.valueOf((g.this.H & 16) == 16));
                    g.this.J.put(32, Boolean.valueOf((g.this.H & 32) == 32));
                    g.this.J.put(64, Boolean.valueOf((g.this.H & 64) == 64));
                    g.this.J.put(128, Boolean.valueOf((g.this.H & 128) == 128));
                    g.this.J.put(256, Boolean.valueOf((g.this.H & 256) == 256));
                    g.this.J.put(512, Boolean.valueOf((g.this.H & 512) == 512));
                    g.this.J.put(1024, Boolean.valueOf((g.this.H & 1024) == 1024));
                    g.this.J.put(2048, Boolean.valueOf((g.this.H & 2048) == 2048));
                    g.this.J.put(4096, Boolean.valueOf((g.this.H & 4096) == 4096));
                    g.this.J.put(8192, Boolean.valueOf((g.this.H & 8192) == 8192));
                    g.this.J.put(16384, Boolean.valueOf((g.this.H & 16384) == 16384));
                    g.this.J.put(32768, Boolean.valueOf((g.this.H & 32768) == 32768));
                    g.this.J.put(65536, Boolean.valueOf((g.this.H & 65536) == 65536));
                    g.this.A.setValue((g.this.H & 32768) == 32768);
                    g.this.B.setValue((g.this.H & 65536) == 65536);
                    if (g.this.f273a == 3) {
                        SystemProperties.set("persist.sys.eqtype.2313", (g.this.H & 32768) == 32768 ? "magic" : "pt2313");
                        g.this.b.write(40730, 3, 0, "sync");
                    } else if (g.this.f273a == 1) {
                        SystemProperties.set("persist.sys.eqtype.3702", (g.this.H & 32768) == 32768 ? "2313red_magic" : "asp3702");
                        g.this.b.write(40730, 3, 0, "sync");
                    }
                }
            } catch (Exception unused) {
            }
        }
    };

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.f d() {
        return null;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_config, (ViewGroup) null);
        this.b = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.c = (InfoPreference) view.findViewById(R.id.tw_bt_ic);
        if (com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            if (com.dofun.carsetting.f.b.v()) {
                this.c.a(getResources().getStringArray(R.array.bt_ic_entries_ts18a), getResources().getStringArray(R.array.bt_ic_value_ts18a));
            } else {
                this.c.a(getResources().getStringArray(R.array.bt_ic_entries2), getResources().getStringArray(R.array.bt_ic_value2));
            }
        } else {
            this.c.a(getResources().getStringArray(R.array.bt_ic_entries), getResources().getStringArray(R.array.bt_ic_value));
        }
        this.c.setOnPreferenceChangeListener(this);
        this.d = (InfoPreference) view.findViewById(R.id.tw_screen_display);
        this.d.setOnPreferenceChangeListener(this);
        this.f = (SwitchPreference) view.findViewById(R.id.tw_rds);
        this.f.setOnPreferenceChangeListener(this);
        this.g = (SwitchPreference) view.findViewById(R.id.tw_more_lights);
        this.g.setOnPreferenceChangeListener(this);
        this.G = (SwitchPreference) view.findViewById(R.id.tw_nfc);
        this.G.setOnPreferenceChangeListener(this);
        this.k = (SwitchPreference) view.findViewById(R.id.tw_no_dvd);
        this.k.setOnPreferenceChangeListener(this);
        this.l = (SwitchPreference) view.findViewById(R.id.tw_cursor_knob);
        this.l.setOnPreferenceChangeListener(this);
        this.m = (SwitchPreference) view.findViewById(R.id.tw_no_play);
        this.m.setOnPreferenceChangeListener(this);
        this.n = (SwitchPreference) view.findViewById(R.id.tw_panorama_360);
        this.n.setOnPreferenceChangeListener(this);
        this.o = (SwitchPreference) view.findViewById(R.id.tw_no_tv);
        this.o.setOnPreferenceChangeListener(this);
        this.p = (SwitchPreference) view.findViewById(R.id.tw_no_aux);
        this.p.setOnPreferenceChangeListener(this);
        this.q = (SwitchPreference) view.findViewById(R.id.tw_no_radio);
        this.q.setOnPreferenceChangeListener(this);
        this.r = (SwitchPreference) view.findViewById(R.id.tw_air_control);
        this.r.setOnPreferenceChangeListener(this);
        this.s = (SwitchPreference) view.findViewById(R.id.display_normal_button);
        this.s.setOnPreferenceChangeListener(this);
        this.t = (SwitchPreference) view.findViewById(R.id.tw_dialer_control);
        this.t.setOnPreferenceChangeListener(this);
        this.u = (SwitchPreference) view.findViewById(R.id.tw_no_streammedia);
        this.u.setOnPreferenceChangeListener(this);
        this.v = (SwitchPreference) view.findViewById(R.id.tw_tpms);
        this.v.setOnPreferenceChangeListener(this);
        this.I = (LinearLayout) view.findViewById(R.id.iv_back);
        this.I.setOnClickListener(this);
        this.w = (SwitchPreference) view.findViewById(R.id.tw_wheel);
        this.w.setOnPreferenceChangeListener(this);
        this.x = (SwitchPreference) view.findViewById(R.id.tw_no_dofunplay);
        this.x.setOnPreferenceChangeListener(this);
        this.y = (SwitchPreference) view.findViewById(R.id.tw_no_carmate);
        this.y.setOnPreferenceChangeListener(this);
        this.z = (SwitchPreference) view.findViewById(R.id.tw_install_apk);
        this.z.setOnPreferenceChangeListener(this);
        this.A = (SwitchPreference) view.findViewById(R.id.tw_magic_eq);
        this.A.setOnPreferenceChangeListener(this);
        this.B = (SwitchPreference) view.findViewById(R.id.tw_ips);
        this.B.setOnPreferenceChangeListener(this);
        this.e = (InfoPreference) view.findViewById(R.id.tw_screen_size);
        this.e.setOnPreferenceChangeListener(this);
        this.C = (SwitchPreference) view.findViewById(R.id.tw_hicar);
        this.C.setOnPreferenceChangeListener(this);
        this.D = (SwitchPreference) view.findViewById(R.id.tw_file);
        this.D.setOnPreferenceChangeListener(this);
        this.E = (SwitchPreference) view.findViewById(R.id.tw_chrome);
        this.E.setOnPreferenceChangeListener(this);
        this.F = (SwitchPreference) view.findViewById(R.id.tw_google);
        this.F.setOnPreferenceChangeListener(this);
        c();
    }

    private void c() {
        if (com.dofun.carsetting.activity.apkinstall.a.c.a().contains("HV")) {
            this.r.setVisibility(8);
        } else {
            this.r.setVisibility(0);
        }
        if (!com.dofun.carsetting.activity.apkinstall.a.c.a().contains("-TPMS")) {
            this.v.setVisibility(8);
        }
        this.u.setVisibility(this.j.F ? 0 : 8);
        this.t.setVisibility(com.dofun.carsetting.f.b.f() ? 8 : 0);
        if (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.d.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G()) {
            this.k.setVisibility(8);
            this.C.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.F()) {
            this.G.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.G()) {
            this.t.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.a().contains("BFD32") || com.dofun.carsetting.f.b.a().contains("KZS34")) {
            this.e.setVisibility(0);
        }
        if (com.dofun.carsetting.f.b.a().contains("AUTOUI") || com.dofun.carsetting.f.b.a().contains("DoFun")) {
            this.E.setVisibility(8);
            this.F.setVisibility(8);
        } else {
            this.E.setVisibility(0);
            this.F.setVisibility(0);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        this.J.put(1, false);
        this.J.put(2, false);
        this.J.put(4, false);
        this.J.put(6, false);
        this.J.put(8, false);
        this.J.put(16, false);
        this.J.put(32, false);
        this.J.put(64, false);
        this.J.put(128, false);
        this.J.put(256, false);
        this.J.put(512, false);
        this.J.put(1024, false);
        this.J.put(2048, false);
        this.J.put(4096, false);
        this.J.put(8192, false);
        this.J.put(16384, true);
        this.J.put(32768, false);
        this.J.put(65536, false);
        this.d.setValue(SystemProperties.get("persist.sf.forcerotation", "2"));
        this.e.setValue(SystemProperties.get("persist.sys.screen.size", "0"));
        if (com.dofun.carsetting.f.b.F()) {
            this.G.setValue(com.dofun.carsetting.f.b.b("/sys/class/car_reverse/twnfc") == 1);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        if (this.b != null) {
            this.b.addHandler("ConfigFragment", this.K);
            this.b.write(266, 255);
            this.b.write(274, 255);
        }
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) != 1 && SystemProperties.getInt("persist.sf.forcerotation", -1) != 3 && SystemProperties.getInt("persist.sf.forcerotation", -1) != 4) {
            this.s.setVisibility(8);
        }
        this.s.setValue(Settings.System.getInt(this.i.getContentResolver(), "BOTTOM_AREA_BUTTON", 0) != 0);
        this.u.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_STREAM_MEDIA_APK", 0) != 0);
        this.v.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_TPMS", 1) != 0);
        this.w.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_WHEEL", 1) != 0);
        this.z.setValue(SystemProperties.getInt("persist.sys.tw.apkinstall_hide", 1) != 0);
        this.B.setVisibility((com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.s()) ? 0 : 8);
        this.C.setValue(TextUtils.isEmpty(SystemProperties.get("persist.zlink.disable.features", "")));
        this.D.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_FILE", 1) != 0);
        this.E.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_CHROME", 1) != 0);
        this.F.setValue(Settings.System.getInt(this.i.getContentResolver(), "ISSHOW_GOOGLE", 1) != 0);
        super.onResume();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.b != null) {
            this.b.close();
            this.b.removeHandler("ConfigFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        this.b.removeHandler("ConfigFragment");
        super.onDestroy();
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        int id = twPreference.getId();
        if (id == R.id.display_normal_button) {
            Settings.System.putInt(this.i.getContentResolver(), "BOTTOM_AREA_BUTTON", ((Boolean) obj).booleanValue() ? 1 : 0);
        } else if (id != R.id.tw_air_control) {
            switch (id) {
                case R.id.tw_bt_ic /* 2131231342 */:
                    int i = Integer.parseInt((String) obj);
                    if (i == 0) {
                        this.J.put(2, false);
                        this.J.put(4, false);
                        this.J.put(6, false);
                        a(0);
                    } else if (i == 2) {
                        this.J.put(2, true);
                        this.J.put(4, false);
                        this.J.put(6, false);
                        a(2);
                    } else if (i == 4) {
                        this.J.put(2, false);
                        this.J.put(4, true);
                        this.J.put(6, false);
                        a(4);
                    } else if (i == 6) {
                        this.J.put(2, false);
                        this.J.put(4, false);
                        this.J.put(6, true);
                        a(6);
                    }
                    break;
                case R.id.tw_chrome /* 2131231343 */:
                    Boolean bool = (Boolean) obj;
                    com.dofun.carsetting.f.a.a(getActivity(), "com.android.chrome", bool.booleanValue());
                    Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_CHROME", bool.booleanValue() ? 1 : 0);
                    break;
                default:
                    switch (id) {
                        case R.id.tw_cursor_knob /* 2131231345 */:
                            this.J.put(32, (Boolean) obj);
                            break;
                        case R.id.tw_dialer_control /* 2131231346 */:
                            this.J.put(16384, (Boolean) obj);
                            break;
                        case R.id.tw_file /* 2131231347 */:
                            Boolean bool2 = (Boolean) obj;
                            com.dofun.carsetting.f.a.a(getActivity(), "com.tw.twfileexplore", bool2.booleanValue());
                            Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_FILE", bool2.booleanValue() ? 1 : 0);
                            break;
                        case R.id.tw_google /* 2131231348 */:
                            Boolean bool3 = (Boolean) obj;
                            com.dofun.carsetting.f.a.a(getActivity(), "com.google.android.googlequicksearchbox", bool3.booleanValue());
                            Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_GOOGLE", bool3.booleanValue() ? 1 : 0);
                            break;
                        case R.id.tw_hicar /* 2131231349 */:
                            Boolean bool4 = (Boolean) obj;
                            SystemProperties.set("persist.zlink.disable.features", bool4.booleanValue() ? "" : "hi");
                            SystemProperties.set("rw.zlink.disable.features", bool4.booleanValue() ? "" : "hi");
                            break;
                        case R.id.tw_install_apk /* 2131231350 */:
                            SystemProperties.set("persist.sys.tw.apkinstall_hide", ((Boolean) obj).booleanValue() ? "1" : "0");
                            break;
                        case R.id.tw_ips /* 2131231351 */:
                            this.J.put(65536, (Boolean) obj);
                            break;
                        case R.id.tw_magic_eq /* 2131231352 */:
                            Boolean bool5 = (Boolean) obj;
                            this.J.put(32768, bool5);
                            if (this.f273a == 3) {
                                SystemProperties.set("persist.sys.eqtype.2313", bool5.booleanValue() ? "magic" : "pt2313");
                            } else if (this.f273a == 1) {
                                SystemProperties.set("persist.sys.eqtype.3702", bool5.booleanValue() ? "2313red_magic" : "2313red");
                            }
                            break;
                        case R.id.tw_more_lights /* 2131231353 */:
                            this.J.put(8, (Boolean) obj);
                            break;
                        default:
                            switch (id) {
                                case R.id.tw_nfc /* 2131231355 */:
                                    com.dofun.carsetting.f.u uVar = this.b;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("run_command\n");
                                    sb.append("fdt set /soc/car_reverse twnfc ");
                                    sb.append(((Boolean) obj).booleanValue() ? "<0x1>" : "<0x0>");
                                    sb.append("\nfdt save");
                                    uVar.write(65282, 0, 0, sb.toString());
                                    break;
                                case R.id.tw_no_aux /* 2131231356 */:
                                    this.J.put(512, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_no_carmate /* 2131231357 */:
                                    this.J.put(8192, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_no_dofunplay /* 2131231358 */:
                                    this.J.put(4096, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_no_dvd /* 2131231359 */:
                                    this.J.put(16, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_no_play /* 2131231360 */:
                                    this.J.put(2048, (Boolean) obj);
                                    break;
                                case R.id.tw_no_radio /* 2131231361 */:
                                    this.J.put(1024, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_no_streammedia /* 2131231362 */:
                                    Boolean bool6 = (Boolean) obj;
                                    Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_STREAM_MEDIA_APK", bool6.booleanValue() ? 1 : 0);
                                    if (bool6.booleanValue()) {
                                        this.b.write(40730, 1, 0, "pm enable com.tw.reverse/com.tw.reverse.StreamMediaActivity");
                                    } else {
                                        this.b.write(40730, 1, 0, "pm disable com.tw.reverse/com.tw.reverse.StreamMediaActivity");
                                    }
                                    break;
                                case R.id.tw_no_tv /* 2131231363 */:
                                    this.J.put(256, Boolean.valueOf(!((Boolean) obj).booleanValue()));
                                    break;
                                case R.id.tw_panorama_360 /* 2131231364 */:
                                    this.J.put(128, (Boolean) obj);
                                    break;
                                case R.id.tw_rds /* 2131231365 */:
                                    this.J.put(1, (Boolean) obj);
                                    break;
                                case R.id.tw_screen_display /* 2131231366 */:
                                    String str = (String) obj;
                                    SystemProperties.set("persist.sf.forcerotation", str);
                                    if (!com.dofun.carsetting.f.b.I() && !com.dofun.carsetting.f.b.J()) {
                                        int i2 = Integer.parseInt(str);
                                        if (i2 == 1 || i2 == 3) {
                                            b(1);
                                        } else {
                                            b(0);
                                        }
                                    }
                                    this.b.write(40730, 1, 0, "rm -rf /twdataconfig/screen_rotaion_*");
                                    this.b.write(40730, 1, 0, "touch /twdataconfig/screen_rotaion_" + str);
                                    this.b.write(40730, 1, 0, "chmod 666 /twdataconfig/screen_rotaion_*");
                                    ((InfoPreference) twPreference).c();
                                    break;
                                case R.id.tw_screen_size /* 2131231367 */:
                                    String str2 = (String) obj;
                                    SystemProperties.set("persist.sys.screen.size", str2);
                                    Intent intent = new Intent("com.android.systemui.screen_size");
                                    intent.putExtra("screen_size", Integer.parseInt(str2));
                                    getActivity().sendBroadcast(intent);
                                    break;
                                default:
                                    switch (id) {
                                        case R.id.tw_tpms /* 2131231369 */:
                                            Boolean bool7 = (Boolean) obj;
                                            com.dofun.carsetting.f.a.a(getActivity(), "com.tw.tpms", bool7.booleanValue());
                                            Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_TPMS", bool7.booleanValue() ? 1 : 0);
                                            break;
                                        case R.id.tw_wheel /* 2131231370 */:
                                            Boolean bool8 = (Boolean) obj;
                                            com.dofun.carsetting.f.a.a(getActivity(), "com.tw.keypad", bool8.booleanValue());
                                            Settings.System.putInt(this.i.getContentResolver(), "ISSHOW_WHEEL", bool8.booleanValue() ? 1 : 0);
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            this.J.put(64, (Boolean) obj);
        }
        int iIntValue = 0;
        for (Map.Entry<Integer, Boolean> entry : this.J.entrySet()) {
            if (entry.getValue().booleanValue()) {
                iIntValue |= entry.getKey().intValue();
            }
        }
        this.b.write(40730, 3, 0, "sync");
        this.b.write(274, 1, iIntValue);
        Log.d("ConfigFragment", "onPreferenceChange: " + Integer.toHexString(iIntValue));
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            ((MainActivity) getActivity()).c();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.dofun.carsetting.fragment.g$2] */
    private void a(final int i) {
        new Thread() { // from class: com.dofun.carsetting.fragment.g.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.d("ConfigFragment", "toBCConfig:start");
                int iB = com.dofun.carsetting.f.u.b();
                if (com.dofun.carsetting.f.b.H() || iB == 3 || iB == 17 || iB == 35 || iB == 99 || iB == 613) {
                    g.this.b.write(40730, 1, 0, "mount -o remount,rw /");
                    if (i == 2) {
                        g.this.b.write(40730, 1, 0, "cp -rf /system/etc/goc/gocsdk6 /system/bin/gocsdk");
                    } else if (i == 4) {
                        g.this.b.write(40730, 1, 0, "cp -rf /system/etc/goc/gocsdk7 /system/bin/gocsdk");
                    } else if (i == 6) {
                        g.this.b.write(40730, 1, 0, "cp -rf /system/etc/goc/gocsdk8 /system/bin/gocsdk");
                    }
                    g.this.b.write(40730, 1, 0, "chown root.root /system/bin/gocsdk");
                    g.this.b.write(40730, 1, 0, "chmod 755 /system/bin/gocsdk");
                    g.this.b.write(40730, 1, 0, "restorecon /system/bin/gocsdk");
                    g.this.b.write(40730, 3, 0, "mount -o remount,ro /");
                }
                Log.d("ConfigFragment", "toBCConfig:end");
            }
        }.start();
    }

    private void b(final int i) {
        if (com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            if (i == 0) {
                Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", -1);
                return;
            } else {
                Settings.System.putInt(this.i.getContentResolver(), "navi_bar_show_position", 4);
                return;
            }
        }
        if (Settings.System.getInt(this.i.getContentResolver(), "navigationbar_config", 0) != 0) {
            Settings.System.putInt(this.i.getContentResolver(), "navigationbar_config", 0);
        }
        if (Settings.System.getInt(this.i.getContentResolver(), "show_navigationbar", 0) != 0) {
            Settings.System.putInt(this.i.getContentResolver(), "show_navigationbar", 0);
        }
        SystemProperties.set("persist.navibar.position", "0");
        if (i != 0) {
            this.K.postDelayed(new Runnable() { // from class: com.dofun.carsetting.fragment.g.3
                @Override // java.lang.Runnable
                public void run() {
                    SystemProperties.set("persist.navibar.position", String.valueOf(i - 1));
                    Settings.System.putInt(g.this.i.getContentResolver(), "navigationbar_config", 16);
                    Settings.System.putInt(g.this.i.getContentResolver(), "show_navigationbar", 1);
                }
            }, 300L);
        }
    }
}
