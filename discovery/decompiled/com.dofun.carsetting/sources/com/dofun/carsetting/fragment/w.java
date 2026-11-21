package com.dofun.carsetting.fragment;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.config.ConfigService;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

/* JADX INFO: compiled from: ReverseFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class w extends ad<com.dofun.carsetting.presenter.k> implements View.OnClickListener, com.dofun.carsetting.g.j, com.dofun.carsetting.g.p {
    private SeekBar B;
    private TextView C;
    private LinearLayout D;
    private TextView E;
    private int F;
    private int G;
    private int H;
    boolean b;
    boolean c;
    private com.dofun.carsetting.f.u d;
    private InfoPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
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
    private ImageView x;
    private int y;
    private int z = 2;
    private int A = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f372a = "/soc@03000000";
    private int[] I = {R.id.forward_looking_switch, R.id.system_line, R.id.reverse_scale, R.id.reverse_delay, R.id.reverse_camera};
    private int[] J = {R.id.reverse_valive, R.id.reverse_camera};
    private int[] K = {R.id.reverse_delay, R.id.reverse_camera, R.id.forward_looking_switch, R.id.system_line, R.id.reverse_scale};
    private Handler L = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.fragment.w.1
        @Override // android.os.Handler
        public void handleMessage(Message message) throws Throwable {
            super.handleMessage(message);
            try {
                byte[] bArr = (byte[]) message.obj;
                int i = message.what;
                int i2 = 0;
                if (i != 260) {
                    if (i != 267) {
                        switch (i) {
                            case 65526:
                                w.this.c();
                                break;
                            case 65527:
                                TWUtil tWUtil = new TWUtil(17);
                                if (tWUtil.open((short[]) null) == 0) {
                                    tWUtil.write(65282, 0, 0, String.format("run_command\nfdt set " + w.this.f372a + "/car_reverse tvd_type <0x%x>\nfdt set " + w.this.f372a + "/car_reverse csi_used <0x%x>\nfdt save", Integer.valueOf(w.this.G), Integer.valueOf(w.this.H)));
                                    tWUtil.close();
                                }
                                break;
                        }
                    } else {
                        w.this.r.setValue(bArr[18] == 1);
                    }
                } else if (com.dofun.carsetting.f.b.f()) {
                    SeekBar seekBar = w.this.B;
                    if (bArr[2] != 1) {
                        i2 = 1;
                    }
                    seekBar.setProgress(i2);
                } else {
                    w.this.y = bArr[2];
                    w.this.x.getBackground().setLevel(w.this.y == 1 ? 1 : 0);
                    if (w.this.y == 1) {
                        w.this.B.setProgress(0);
                    } else if (w.this.y == 0) {
                        w.this.B.setProgress(10);
                    } else {
                        w.this.B.setProgress(w.this.y - 1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private ContentObserver M = new ContentObserver(new Handler()) { // from class: com.dofun.carsetting.fragment.w.3
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            w.this.p.setValue(Integer.valueOf(Settings.System.getInt(w.this.getActivity().getContentResolver(), "SYSTEM_LINE2", 0)).intValue() != 0);
        }
    };

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_reverse, (ViewGroup) null);
        this.d = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.k d() {
        return new com.dofun.carsetting.presenter.k(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.C = (TextView) view.findViewById(R.id.tv_reverse_voice_control);
        this.E = (TextView) view.findViewById(R.id.tv_reverse_title);
        this.D = (LinearLayout) view.findViewById(R.id.ll_reverse_voice_control);
        this.m = (SwitchPreference) view.findViewById(R.id.system_mirror);
        this.n = (SwitchPreference) view.findViewById(R.id.system_mirror_vertical);
        this.f = (InfoPreference) view.findViewById(R.id.forward_looking_switch);
        this.o = (SwitchPreference) view.findViewById(R.id.system_line);
        this.p = (SwitchPreference) view.findViewById(R.id.reverse_scale);
        this.r = (SwitchPreference) view.findViewById(R.id.reverse_valive);
        this.q = (SwitchPreference) view.findViewById(R.id.system_fradar);
        this.e = (InfoPreference) view.findViewById(R.id.reverse_camera);
        this.l = (InfoPreference) view.findViewById(R.id.system_line_direction);
        this.s = (SwitchPreference) view.findViewById(R.id.reverse_mute);
        this.g = (InfoPreference) view.findViewById(R.id.reverse_delay);
        this.k = (InfoPreference) view.findViewById(R.id.setting_360);
        this.t = (SwitchPreference) view.findViewById(R.id.reverse_double_flash);
        this.u = (SwitchPreference) view.findViewById(R.id.boot_360);
        this.u.setOnPreferenceChangeListener(this);
        this.u.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_360", 1) == 1);
        this.v = (SwitchPreference) view.findViewById(R.id.float_360);
        this.v.setOnPreferenceChangeListener(this);
        this.v.setValue(Settings.System.getInt(this.i.getContentResolver(), "FLOAT_360", 0) == 1);
        this.w = (SwitchPreference) view.findViewById(R.id.remote_monitoring);
        this.x = (ImageView) view.findViewById(R.id.tv_reverse_mute);
        this.x.setOnClickListener(this);
        this.B = (SeekBar) view.findViewById(R.id.reverse_mute_seek_bar);
        if (com.dofun.carsetting.f.b.f()) {
            this.B.setMax(1);
        } else {
            this.B.setMax(10);
        }
        this.m.setOnPreferenceChangeListener(this);
        this.n.setOnPreferenceChangeListener(this);
        this.f.setOnPreferenceChangeListener(this);
        this.o.setOnPreferenceChangeListener(this);
        this.p.setOnPreferenceChangeListener(this);
        this.r.setOnPreferenceChangeListener(this);
        this.q.setOnPreferenceChangeListener(this);
        this.e.setOnPreferenceChangeListener(this);
        this.l.setOnPreferenceChangeListener(this);
        this.g.setOnPreferenceChangeListener(this);
        this.k.setOnPreferenceChangeListener(this);
        this.t.setOnPreferenceChangeListener(this);
        this.w.setOnPreferenceChangeListener(this);
        b(view);
        this.i.getContentResolver().registerContentObserver(Settings.System.getUriFor("SYSTEM_LINE2"), false, this.M);
    }

    /* JADX WARN: Code duplicated, block: B:85:0x0213  */
    private void b(View view) {
        this.g.setVisibility(this.j.f ? 0 : 8);
        this.r.setVisibility(this.j.g ? 0 : 8);
        boolean z = true;
        if (com.dofun.carsetting.f.b.A() || com.dofun.carsetting.f.b.z()) {
            String[] stringArray = getActivity().getResources().getStringArray(R.array.reverse_camera_entries_t3p);
            String[] stringArray2 = getActivity().getResources().getStringArray(R.array.reverse_camera_values_t3p);
            Log.d("ReverseFragment", "initPlatformDiff:" + Arrays.toString(stringArray) + " values:" + Arrays.toString(stringArray2));
            this.e.a(stringArray, stringArray2);
        } else if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.e.a(getActivity().getResources().getStringArray(R.array.t100_ahd360_entries), getActivity().getResources().getStringArray(R.array.t100_ahd360_values));
            if (com.dofun.carsetting.f.b.am() != 1 && com.dofun.carsetting.f.b.am() != 2) {
                this.e.setVisibility(8);
            }
            if (com.dofun.carsetting.f.b.ba()) {
                this.o.setVisibility(8);
                this.l.setVisibility(8);
                this.p.setVisibility(8);
                this.e.setVisibility(8);
            }
        } else if (com.dofun.carsetting.f.b.H()) {
            String[] stringArray3 = getActivity().getResources().getStringArray(R.array.t919_camera_format_entries);
            String[] stringArray4 = getActivity().getResources().getStringArray(R.array.t919_camera_format_values);
            Log.d("ReverseFragment", "initPlatformDiff:" + Arrays.toString(stringArray3) + " values:" + Arrays.toString(stringArray4));
            this.e.a(stringArray3, stringArray4);
        }
        if (com.dofun.carsetting.f.b.at()) {
            this.u.setVisibility(0);
            this.E.setText(R.string.setting_360);
            this.v.setVisibility(0);
        } else {
            this.u.setVisibility(8);
            this.E.setText(R.string.reverse_setting);
            this.v.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.O()) {
            for (int i = 0; i < this.J.length; i++) {
                view.findViewById(this.J[i]).setVisibility(8);
            }
            if (!com.dofun.carsetting.f.b.aj()) {
                for (int i2 = 0; i2 < this.K.length; i2++) {
                    view.findViewById(this.K[i2]).setVisibility(8);
                }
                if (com.dofun.carsetting.f.b.e()) {
                    this.t.setVisibility(0);
                }
                if (com.dofun.carsetting.f.b.ak()) {
                    this.o.setVisibility(0);
                }
            }
            this.v.setVisibility(8);
        } else if (com.dofun.carsetting.f.b.at()) {
            for (int i3 = 0; i3 < this.I.length; i3++) {
                view.findViewById(this.I[i3]).setVisibility(8);
            }
            this.t.setVisibility(0);
            this.k.setVisibility(0);
        }
        int i4 = SystemProperties.getInt("persist.camera.reverse", -1);
        if ((com.dofun.carsetting.f.b.at() || i4 == 2 || i4 == 3) ? false : true) {
            this.n.setVisibility(0);
        } else {
            this.n.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.at() && (com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.H() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J())) {
            int i5 = SystemProperties.getInt("persist.camera.reverse", 0);
            if (com.dofun.carsetting.f.b.p() && i5 == 3) {
                z = false;
            }
        } else {
            z = false;
        }
        if (z) {
            this.m.setVisibility(8);
            this.n.setVisibility(8);
        }
        if (com.dofun.carsetting.f.b.p() && com.dofun.carsetting.f.b.a().contains("RMON")) {
            this.w.setVisibility(0);
        } else {
            this.w.setVisibility(8);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        Log.d("ReverseFragment", "initData:");
        if (com.dofun.carsetting.f.b.G()) {
            this.f372a = "/soc";
        }
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.A = f();
            this.b = (this.A & 16) == 16;
            this.c = (this.A & 1) == 1;
        }
        this.f.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0)));
        if (com.dofun.carsetting.f.b.a().contains("BMW")) {
            this.q.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FRADAR", 1)).intValue() == 0);
        } else {
            this.q.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FRADAR", 0)).intValue() == 0);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() throws Throwable {
        super.onResume();
        if (this.d != null) {
            this.d.addHandler("ReverseFragment", this.L);
            this.d.write(260, 255);
            this.d.write(267, 255);
            this.F = com.dofun.carsetting.f.b.an();
            this.G = com.dofun.carsetting.f.b.am();
            this.H = com.dofun.carsetting.f.b.a(this.d);
            this.L.removeMessages(65526);
            this.L.sendEmptyMessage(65526);
        }
        int i = SystemProperties.getInt("persist.camera.mirror", -1);
        if (com.dofun.carsetting.f.b.L()) {
            if (SystemProperties.getInt("persist.camera.reverse", 1) != 1) {
                this.m.setValue(Integer.valueOf(SystemProperties.get("persist.camerarear.mirror", "0")).intValue() != 0);
            } else {
                this.m.setValue(Integer.valueOf(SystemProperties.get("persist.camera.mirror", "0")).intValue() != 0);
            }
        } else if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.m.setValue(this.c);
        } else if (com.dofun.carsetting.f.b.H()) {
            this.c = i == 1;
            this.m.setValue(this.c);
        } else {
            this.m.setValue(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_MIRROR", 0) != 0);
        }
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.n.setValue(this.b);
        } else if (com.dofun.carsetting.f.b.H()) {
            this.b = i == 2;
            this.n.setValue(this.b);
        } else {
            this.n.setValue(TextUtils.equals(SystemProperties.get("persist.camera.angle", "90"), "270"));
        }
        this.f.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FOWARD_SWITCH", 0)));
        this.o.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_LINE", 0)).intValue() != 0);
        this.l.setValue("" + Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_LINE_DIRECTION", 0));
        this.p.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_LINE2", 0)).intValue() != 0);
        this.g.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "REVERSE_DELAY", 0)));
        this.t.setValue(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_LRREVERSE", 1) == 0);
        c();
        this.B.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.dofun.carsetting.fragment.w.2
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
                if (z) {
                    if (com.dofun.carsetting.f.b.f()) {
                        w.this.d.write(260, 2, i2 != 1 ? 1 : 0);
                    } else if (i2 == 10) {
                        w.this.d.write(260, 2, 0);
                    } else {
                        w.this.d.write(260, 2, i2 + 1);
                    }
                }
            }
        });
        this.u.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_360", 1) == 1);
        this.v.setValue(Settings.System.getInt(this.i.getContentResolver(), "FLOAT_360", 0) == 1);
        this.w.setValue(SystemProperties.getInt("persist.tw.sleepmode", 0) == 1);
        Log.d("ReverseFragment", "onResume:");
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Log.d("ReverseFragment", "onHiddenChanged:" + z);
        if (z) {
            return;
        }
        this.L.removeMessages(65526);
        this.L.sendEmptyMessage(65526);
        b();
    }

    @Override // android.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z) {
            this.L.removeMessages(65526);
            this.L.sendEmptyMessage(65526);
            b();
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.d != null) {
            this.d.removeHandler("ReverseFragment");
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.d != null) {
            this.d.close();
            this.d.removeHandler("ReverseFragment");
        }
        if (this.M != null) {
            getActivity().getContentResolver().unregisterContentObserver(this.M);
        }
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
        switch (twPreference.getId()) {
            case R.id.boot_360 /* 2131230770 */:
                Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_360", ((Boolean) obj).booleanValue() ? 1 : 0);
                this.d.write(40730, 1, 0, "sync");
                return false;
            case R.id.float_360 /* 2131230879 */:
                if (Settings.System.getInt(this.i.getContentResolver(), "FLOAT_360", 0) == 1) {
                    com.dofun.carsetting.widget.a.b(ConfigService.b, ConfigService.f180a);
                } else {
                    com.dofun.carsetting.widget.a.a(ConfigService.b, ConfigService.f180a);
                }
                Settings.System.putInt(this.i.getContentResolver(), "FLOAT_360", ((Boolean) obj).booleanValue() ? 1 : 0);
                this.d.write(40730, 1, 0, "sync");
                return false;
            case R.id.forward_looking_switch /* 2131230883 */:
                String str = (String) obj;
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_FOWARD_SWITCH", Integer.parseInt(str));
                this.f.setValue(str);
                return false;
            case R.id.remote_monitoring /* 2131231136 */:
                SystemProperties.set("persist.tw.sleepmode", ((Boolean) obj).booleanValue() ? "1" : "0");
                return false;
            case R.id.reverse_camera /* 2131231142 */:
                String str2 = (String) obj;
                int i = Integer.parseInt(str2);
                if (com.dofun.carsetting.f.b.A()) {
                    this.d.write(65282, 0, 0, String.format("run_command\nfdt set /soc@01c00000/car_reverse csi_used <0x%x>\nfdt save", Integer.valueOf(i)));
                } else if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                    this.d.write(40730, 1, 0, "rwrite /sys/class/tw/av/resolution " + i);
                    this.H = i;
                    g();
                } else if (com.dofun.carsetting.f.b.H()) {
                    SystemProperties.set("persist.camera.mode0", "" + i);
                } else {
                    SystemProperties.set("persist.camera.ahd360", str2);
                    if (i > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(i - 1);
                        SystemProperties.set("persist.camera.mode0", sb.toString());
                    }
                    if (i >= 3) {
                        SystemProperties.set("persist.camera.id", "0");
                    } else {
                        SystemProperties.set("persist.camera.id", "1");
                    }
                    this.L.removeMessages(65526);
                    this.L.sendEmptyMessageDelayed(65526, 1000L);
                }
                return false;
            case R.id.reverse_delay /* 2131231144 */:
                String str3 = (String) obj;
                Settings.System.putInt(getActivity().getContentResolver(), "REVERSE_DELAY", Integer.parseInt(str3));
                this.g.setValue(str3);
                return false;
            case R.id.reverse_double_flash /* 2131231145 */:
                Boolean bool = (Boolean) obj;
                SystemProperties.set("persist.can.lrreverse", bool.booleanValue() ? "0" : "1");
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_LRREVERSE", !bool.booleanValue() ? 1 : 0);
                return false;
            case R.id.reverse_mute /* 2131231147 */:
                this.d.write(260, 2, ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.reverse_scale /* 2131231150 */:
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_LINE2", ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.reverse_valive /* 2131231154 */:
                this.d.write(267, 18, ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.setting_360 /* 2131231191 */:
                ComponentName componentName = null;
                try {
                    Intent intent = new Intent();
                    if (com.dofun.carsetting.f.a.a().b("cn.cardoor.zt360")) {
                        componentName = new ComponentName("cn.cardoor.zt360", "cn.cardoor.zt360.ui.activity.RecordActivity");
                        intent.putExtra("TopNavi_Default_Frag", 2);
                    } else if (com.dofun.carsetting.f.a.a().b("com.baony.tw360")) {
                        componentName = new ComponentName("com.baony.tw360", "com.baony.ui.activity.NaviActivity");
                        intent.putExtra("TopNavi_Default_Frag", 2);
                    } else if (com.dofun.carsetting.f.a.a().b("com.percherry.roundadas")) {
                        componentName = new ComponentName("com.percherry.roundadas", "com.percherry.roundadas.SettingsActivity");
                    } else if (com.dofun.carsetting.f.a.a().b("com.tw.dvr")) {
                        componentName = new ComponentName("com.tw.dvr", "com.tw.dvr.ui.activity.SettingsActivity");
                    } else if (com.dofun.carsetting.f.a.a().b("com.softwinner.dvr")) {
                        componentName = new ComponentName("com.softwinner.dvr", "com.softwinner.dvr.ui.activity.SettingsActivity");
                    }
                    intent.setComponent(componentName);
                    startActivity(intent);
                    break;
                } catch (Exception unused) {
                    Toast.makeText(this.i, getResources().getString(R.string.apk_noinstall), 1).show();
                }
                return false;
            case R.id.system_fradar /* 2131231236 */:
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_FRADAR", !((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.system_line /* 2131231239 */:
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_LINE", ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            case R.id.system_line_direction /* 2131231240 */:
                String str4 = (String) obj;
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_LINE_DIRECTION", Integer.parseInt(str4));
                this.l.setValue(str4);
                return false;
            case R.id.system_mirror /* 2131231242 */:
                if (com.dofun.carsetting.f.b.L()) {
                    if ((SystemProperties.getInt("persist.camera.reverse", 1) == 1) != false) {
                        SystemProperties.set("persist.camera.mirror", ((Boolean) obj).booleanValue() ? "1" : "0");
                        SystemProperties.get("persist.camera.ahd360", "3");
                    } else {
                        Boolean bool2 = (Boolean) obj;
                        SystemProperties.set("persist.camerarear.mirror", bool2.booleanValue() ? "1" : "0");
                        a(1, bool2.booleanValue() ? 1 : 0);
                    }
                } else if (com.dofun.carsetting.f.b.H()) {
                    this.c = ((Boolean) obj).booleanValue();
                    this.b = false;
                    h();
                } else if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                    this.c = !this.c;
                    a(2, ((Boolean) obj).booleanValue() ? 1 : 0);
                } else {
                    Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_MIRROR", ((Boolean) obj).booleanValue() ? 1 : 0);
                }
                return false;
            case R.id.system_mirror_vertical /* 2131231243 */:
                if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                    this.b = !this.b;
                    a(2, ((Boolean) obj).booleanValue() ? 1 : 0);
                } else if (com.dofun.carsetting.f.b.H()) {
                    this.c = false;
                    this.b = ((Boolean) obj).booleanValue();
                    h();
                } else {
                    Boolean bool3 = (Boolean) obj;
                    SystemProperties.set("persist.camera.angle", bool3.booleanValue() ? "270" : "90");
                    if (SystemProperties.getInt("persist.camera.reverse", -1) != 1) {
                        a(3, bool3.booleanValue() ? 1 : 0);
                    }
                }
                return false;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() throws Throwable {
        if (com.dofun.carsetting.f.b.L()) {
            try {
                this.e.setValue(String.valueOf(SystemProperties.get("persist.camera.ahd360", "3")));
                return;
            } catch (Exception e) {
                Log.e("ReverseFragment", "reverseCamera: " + e.getMessage());
                return;
            }
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            if (this.F < 255) {
                if (this.G == 1) {
                    this.H = this.F;
                } else if (this.G == 2) {
                    this.H = 0;
                }
            }
            Log.d("ReverseFragment", "reverseCamera: ntvdResolution = " + this.H);
            this.e.setValue(String.valueOf(this.H));
            return;
        }
        if (com.dofun.carsetting.f.b.H()) {
            this.e.setValue(String.valueOf(SystemProperties.getInt("persist.camera.mode0", 0)));
            return;
        }
        if (this.d == null) {
            return;
        }
        int iWrite = this.d.write(65523, 0, 0, "car_reverse\ncsi_used");
        try {
            this.e.setValue(String.valueOf(iWrite));
        } catch (Exception e2) {
            Log.e("ReverseFragment", "reverseCamera: " + e2.getMessage());
        }
        if (iWrite >= 1) {
            return;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/tw/av/chip"));
                try {
                    if (Integer.parseInt(bufferedReader2.readLine()) <= 0) {
                        this.e.setVisibility(8);
                    }
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.tv_reverse_mute) {
            return;
        }
        this.d.write(260, 2, this.y == 0 ? 1 : 0);
    }

    private void a(final int i, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.w.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
                if (i == 1 || i == 3) {
                    w.this.d.write(514, 1, 0);
                    return;
                }
                if (i == 2) {
                    w.this.A &= -18;
                    if (w.this.c && !w.this.b) {
                        w.this.A |= 1;
                    } else if (!w.this.c && w.this.b) {
                        w.this.A |= 16;
                    } else if (!w.this.c || !w.this.b) {
                        w.this.A |= 0;
                    } else {
                        w.this.A |= 17;
                    }
                    w.this.d.write(65282, 0, 0, String.format("run_command\nfdt set " + w.this.f372a + "/car_reverse mirror <0x%x>\nfdt save", Integer.valueOf(w.this.A)));
                }
            }
        });
        builder.setNegativeButton(R.string.dlg_cancel, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.w.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.w.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
            }
        });
        builder.setTitle(R.string.reverse_mirror_reboot_tip);
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

    private int f() {
        return com.dofun.carsetting.f.b.b("/sys/class/car_reverse/car_mirror");
    }

    private void g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() { // from class: com.dofun.carsetting.fragment.w.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                w.this.L.removeMessages(65527);
                w.this.L.sendEmptyMessageDelayed(65527, 1000L);
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

    private void h() {
        int i;
        Log.d("ReverseFragment", "setMirror: horizontalMirror=" + this.c + ",verticalMirror=" + this.b);
        if (this.c) {
            i = 1;
        } else {
            i = this.b ? 2 : -1;
        }
        SystemProperties.set("persist.camera.mirror", i + "");
        this.m.setValue(this.c);
        this.n.setValue(this.b);
    }
}
