package com.dofun.carsetting.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v7.graphics.Palette;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.internal.graphics.ColorUtils;
import com.dofun.carsetting.R;
import com.dofun.carsetting.activity.BaseActivity;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.bean.ConfigBean;
import com.dofun.carsetting.bean.ConfigDateAsync;
import com.dofun.carsetting.bean.WallpaperInfo;
import com.dofun.carsetting.c.c;
import com.dofun.carsetting.c.d;
import com.dofun.carsetting.config.ConfigService;
import com.dofun.carsetting.f.b;
import com.dofun.carsetting.f.m;
import com.dofun.carsetting.f.p;
import com.dofun.carsetting.f.u;
import com.dofun.carsetting.fragment.aa;
import com.dofun.carsetting.fragment.ab;
import com.dofun.carsetting.fragment.ac;
import com.dofun.carsetting.fragment.ad;
import com.dofun.carsetting.fragment.h;
import com.dofun.carsetting.fragment.i;
import com.dofun.carsetting.fragment.j;
import com.dofun.carsetting.fragment.k;
import com.dofun.carsetting.fragment.l;
import com.dofun.carsetting.fragment.n;
import com.dofun.carsetting.fragment.q;
import com.dofun.carsetting.fragment.r;
import com.dofun.carsetting.fragment.t;
import com.dofun.carsetting.fragment.v;
import com.dofun.carsetting.fragment.w;
import com.dofun.carsetting.fragment.z;
import com.dofun.carsetting.g.f;
import com.dofun.carsetting.g.g;
import com.dofun.carsetting.widget.EditTextPreference;
import com.dofun.carsetting.widget.MyRadioButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends BaseActivity implements com.dofun.carsetting.fragment.a.InterfaceC0017a {
    public static boolean b = true;
    static final Palette.Filter h = new Palette.Filter() { // from class: com.dofun.carsetting.ui.MainActivity.2
        @Override // android.support.v7.graphics.Palette.Filter
        public boolean isAllowed(int i, float[] fArr) {
            return a(fArr) && b(fArr) && c(fArr);
        }

        private boolean a(float[] fArr) {
            return fArr[0] >= 15.0f && fArr[0] <= 345.0f;
        }

        private boolean b(float[] fArr) {
            return fArr[1] >= 0.2f && fArr[1] <= 0.62f;
        }

        private boolean c(float[] fArr) {
            return fArr[2] >= 0.05f && fArr[2] <= 0.95f;
        }
    };
    private com.dofun.carsetting.fragment.a A;
    private h B;
    private v C;
    private z D;
    private q E;
    private RadioButton F;
    private RadioButton G;
    private RadioButton H;
    private RadioButton I;
    private RadioButton J;
    private RadioButton K;
    private RadioButton L;
    private RadioButton M;
    private RadioButton N;
    private RadioButton O;
    private RadioButton P;
    private RadioButton Q;
    private String R;
    private String S;
    private List<ConfigBean> T;
    private int U;
    private String V;
    private String W;
    private View Y;
    private Drawable Z;
    private u ac;
    public List<ConfigBean> c;
    public ConfigService.a d;
    boolean g;
    private Context i;
    private d j;
    private c k;
    private FragmentManager l;
    private Fragment n;
    private l o;
    private i p;
    private r q;
    private k r;
    private w s;
    private ac t;
    private t u;
    private aa v;
    private j w;
    private n x;
    private com.dofun.carsetting.fragment.u y;
    private ab z;
    private ArrayList<Fragment> m = new ArrayList<>();
    private int X = 0;
    private boolean aa = false;
    private boolean ab = false;
    public Map<String, String> e = new LinkedHashMap();
    boolean f = false;
    private RadioGroup.OnCheckedChangeListener ad = new RadioGroup.OnCheckedChangeListener() { // from class: com.dofun.carsetting.ui.MainActivity.4
        @Override // android.widget.RadioGroup.OnCheckedChangeListener
        public void onCheckedChanged(RadioGroup radioGroup, final int i) {
            boolean zA = MainActivity.this.a(i);
            if (MainActivity.this.U == i) {
                return;
            }
            if (zA) {
                MainActivity.this.b(i);
                return;
            }
            if (i != R.id.rb_original) {
                switch (i) {
                    case R.id.rb_devices /* 2131231113 */:
                        if (MainActivity.this.p == null) {
                            MainActivity.this.p = new i();
                        }
                        MainActivity.this.c(MainActivity.this.p);
                        MainActivity.this.d(MainActivity.this.p);
                        MainActivity.this.U = i;
                        break;
                    case R.id.rb_devices_info /* 2131231114 */:
                        if (MainActivity.this.A == null) {
                            MainActivity.this.A = new com.dofun.carsetting.fragment.a();
                        }
                        MainActivity.this.c(MainActivity.this.A);
                        MainActivity.this.d(MainActivity.this.A);
                        MainActivity.this.U = i;
                        break;
                    case R.id.rb_factory /* 2131231115 */:
                        MainActivity.this.R = Settings.System.getString(MyApp.a().getContentResolver(), "USER_LOCK");
                        if (TextUtils.isEmpty(MainActivity.this.R)) {
                            MainActivity.this.R = "8888";
                        }
                        MainActivity.this.S = Settings.System.getString(MyApp.a().getContentResolver(), "FACTORY_LOCK");
                        if (TextUtils.isEmpty(MainActivity.this.S)) {
                            MainActivity.this.S = "123456";
                        }
                        if (MainActivity.this.j == null) {
                            int i2 = MainActivity.this.f34a.N ? 129 : 2;
                            MainActivity.this.j = new d(MainActivity.this.i);
                            MainActivity.this.j.a(MainActivity.this.i.getString(R.string.factory));
                            MainActivity.this.j.a(MainActivity.this.i.getString(R.string.enter_your_pin), i2, true);
                            MainActivity.this.j.a(new g() { // from class: com.dofun.carsetting.ui.MainActivity.4.1
                                @Override // com.dofun.carsetting.g.g
                                public void a(EditTextPreference editTextPreference, Editable editable) {
                                    if (TextUtils.equals(MainActivity.this.R, editable.toString()) || TextUtils.equals("61808683", editable.toString())) {
                                        if (MainActivity.this.q == null) {
                                            MainActivity.this.q = new r();
                                        }
                                        MainActivity.this.c(MainActivity.this.q);
                                        MainActivity.this.d(MainActivity.this.q);
                                        MainActivity.this.U = i;
                                        return;
                                    }
                                    if (TextUtils.equals(MainActivity.this.S, editable.toString())) {
                                        if (MainActivity.this.w == null) {
                                            MainActivity.this.w = new j();
                                        }
                                        MainActivity.this.c(MainActivity.this.w);
                                        MainActivity.this.d(MainActivity.this.w);
                                        MainActivity.this.U = i;
                                        return;
                                    }
                                    MainActivity.this.e();
                                }
                            });
                            MainActivity.this.j.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.ui.MainActivity.4.2
                                @Override // android.content.DialogInterface.OnDismissListener
                                public void onDismiss(DialogInterface dialogInterface) {
                                    MainActivity.this.e();
                                }
                            });
                        }
                        if (MainActivity.this.w == null) {
                            MainActivity.this.w = new j();
                        }
                        if (!MainActivity.this.w.isVisible()) {
                            MainActivity.this.j.show();
                        }
                        break;
                    case R.id.rb_general /* 2131231116 */:
                        if (MainActivity.this.r == null) {
                            MainActivity.this.r = new k();
                        }
                        MainActivity.this.c(MainActivity.this.r);
                        MainActivity.this.d(MainActivity.this.r);
                        MainActivity.this.U = i;
                        break;
                    case R.id.rb_internet /* 2131231117 */:
                        if (MainActivity.this.o == null) {
                            MainActivity.this.o = new l();
                        }
                        MainActivity.this.c(MainActivity.this.o);
                        MainActivity.this.d(MainActivity.this.o);
                        MainActivity.this.U = i;
                        break;
                    default:
                        switch (i) {
                            case R.id.rb_personal /* 2131231125 */:
                                if (MainActivity.this.u == null) {
                                    MainActivity.this.u = new t();
                                }
                                MainActivity.this.c(MainActivity.this.u);
                                MainActivity.this.d(MainActivity.this.u);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_reverse /* 2131231126 */:
                                if (MainActivity.this.s == null) {
                                    MainActivity.this.s = new w();
                                }
                                MainActivity.this.c(MainActivity.this.s);
                                MainActivity.this.d(MainActivity.this.s);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_setting /* 2131231127 */:
                                MainActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
                                break;
                            case R.id.rb_sound /* 2131231128 */:
                                if (MainActivity.this.D == null) {
                                    MainActivity.this.D = new z();
                                }
                                MainActivity.this.c(MainActivity.this.D);
                                MainActivity.this.d(MainActivity.this.D);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_system /* 2131231129 */:
                                if (MainActivity.this.v == null) {
                                    MainActivity.this.v = new aa();
                                }
                                MainActivity.this.c(MainActivity.this.v);
                                MainActivity.this.d(MainActivity.this.v);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_voice /* 2131231130 */:
                                if (MainActivity.this.t == null) {
                                    MainActivity.this.t = new ac();
                                }
                                MainActivity.this.c(MainActivity.this.t);
                                MainActivity.this.d(MainActivity.this.t);
                                MainActivity.this.U = i;
                                break;
                        }
                        break;
                }
            } else {
                if (MainActivity.this.E == null) {
                    MainActivity.this.E = new q();
                }
                MainActivity.this.c(MainActivity.this.E);
                MainActivity.this.d(MainActivity.this.E);
                MainActivity.this.U = i;
            }
            if (radioGroup != null) {
                radioGroup.postInvalidateDelayed(50L);
            }
        }
    };
    private ServiceConnection ae = new ServiceConnection() { // from class: com.dofun.carsetting.ui.MainActivity.7
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("XTCarSetting.MainActivity", "onServiceConnected: ");
            MainActivity.this.d = (ConfigService.a) iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("XTCarSetting.MainActivity", "onServiceDisconnected: ");
        }
    };
    private Handler af = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.ui.MainActivity.8
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) throws Exception {
            int i = message.what;
            if (i != 274) {
                switch (i) {
                    case 65281:
                        MainActivity.this.e();
                        break;
                    case 65282:
                        Log.d("XTCarSetting.MainActivity", "set background start");
                        MainActivity.this.a(MainActivity.this.Z);
                        Log.d("XTCarSetting.MainActivity", "set background end");
                        break;
                }
            } else {
                int i2 = message.arg1;
                MainActivity.this.g = (i2 & 65536) == 65536;
                MainActivity.this.a((Drawable) null);
            }
            if (MainActivity.this.f) {
                MainActivity.this.k();
            }
            return true;
        }
    });
    private Palette.PaletteAsyncListener ag = new Palette.PaletteAsyncListener() { // from class: com.dofun.carsetting.ui.MainActivity.3
        @Override // android.support.v7.graphics.Palette.PaletteAsyncListener
        public void onGenerated(Palette palette) {
            int dominantColor;
            if (palette != null) {
                palette.getDominantSwatch();
                dominantColor = palette.getDominantColor(0);
            } else {
                dominantColor = 0;
            }
            float fCalculateLuminance = (float) ColorUtils.calculateLuminance(dominantColor);
            Log.d("XTCarSetting.MainActivity", "onGenerated: dominantColor:" + Integer.toHexString(dominantColor) + " lum:" + fCalculateLuminance);
            MainActivity.this.a(fCalculateLuminance < 0.2f);
        }
    };

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws Exception {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        this.l = getFragmentManager();
        a(bundle);
        this.i = this;
        g();
        com.dofun.carsetting.f.c.a((Activity) this);
        d();
        this.ac = u.a();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.f = z;
        if (z) {
            i();
            sendBroadcast(new Intent("com.notification"));
            this.af.removeMessages(65283);
            this.af.sendEmptyMessageDelayed(65283, 100L);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.ac != null) {
            this.ac.addHandler("XTCarSetting.MainActivity", this.af);
            this.ac.write(274, 255);
        }
        Intent intent = getIntent();
        if (intent != null) {
            if (this.aa) {
                a(intent);
                setIntent(null);
            } else {
                this.ab = true;
            }
        }
        this.af.removeMessages(65281);
        if (this.n != null) {
            e();
        } else {
            this.af.sendEmptyMessageDelayed(65281, 50L);
        }
        if (b.at()) {
            this.H.setText("360");
        } else {
            this.H.setText(R.string.reverse);
        }
        if (b.T() && !b.as()) {
            this.H.setVisibility(8);
        }
        j();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        try {
            Iterator<Fragment> it = this.m.iterator();
            while (it.hasNext()) {
                this.l.beginTransaction().hide(it.next()).commitAllowingStateLoss();
            }
            if (this.o != null && this.o.isAdded()) {
                getFragmentManager().putFragment(bundle, "InternetFragment", this.o);
            }
            if (this.p != null && this.p.isAdded()) {
                getFragmentManager().putFragment(bundle, "DevicesFragment", this.p);
            }
            if (this.q != null && this.q.isAdded()) {
                getFragmentManager().putFragment(bundle, "OriginalFragment", this.q);
            }
            if (this.r != null && this.r.isAdded()) {
                getFragmentManager().putFragment(bundle, "GeneralFragment", this.r);
            }
            if (this.s != null && this.s.isAdded()) {
                getFragmentManager().putFragment(bundle, "ReverseFragment", this.s);
            }
            if (this.t != null && this.t.isAdded()) {
                getFragmentManager().putFragment(bundle, "VoiceFragment", this.t);
            }
            if (this.u != null && this.u.isAdded()) {
                getFragmentManager().putFragment(bundle, "PersonalFragment", this.u);
            }
            if (this.v != null && this.v.isAdded()) {
                getFragmentManager().putFragment(bundle, "SystemFragment", this.v);
            }
            if (this.w != null && this.w.isAdded()) {
                getFragmentManager().putFragment(bundle, "FactoryFragment", this.w);
            }
            if (this.x != null && this.x.isAdded()) {
                getFragmentManager().putFragment(bundle, "NaviChooseFragment", this.x);
            }
            if (this.y != null && this.y.isAdded()) {
                getFragmentManager().putFragment(bundle, "PipChooseFragment", this.y);
            }
            if (this.z != null && this.z.isAdded()) {
                getFragmentManager().putFragment(bundle, "VideoOutSettingFragment", this.z);
            }
            if (this.A != null && this.A.isAdded()) {
                getFragmentManager().putFragment(bundle, "AboutFragment", this.A);
            }
            if (this.B != null && this.B.isAdded()) {
                getFragmentManager().putFragment(bundle, "DefaultAppSelectFragment", this.B);
            }
            if (this.C != null && this.C.isAdded()) {
                getFragmentManager().putFragment(bundle, "XTFragment", this.C);
            }
            if (this.D != null && this.D.isAdded()) {
                getFragmentManager().putFragment(bundle, "SoundFragment", this.D);
            }
            if (this.E != null && this.E.isAdded()) {
                getFragmentManager().putFragment(bundle, "OriginalCarFragment", this.E);
            }
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivity", Log.getStackTraceString(e));
        }
        super.onSaveInstanceState(bundle);
    }

    private void a(Bundle bundle) {
        if (bundle != null) {
            this.o = (l) this.l.getFragment(bundle, "InternetFragment");
            this.p = (i) this.l.getFragment(bundle, "DevicesFragment");
            this.q = (r) this.l.getFragment(bundle, "OriginalFragment");
            this.r = (k) this.l.getFragment(bundle, "GeneralFragment");
            this.s = (w) this.l.getFragment(bundle, "ReverseFragment");
            this.t = (ac) this.l.getFragment(bundle, "VoiceFragment");
            this.u = (t) this.l.getFragment(bundle, "PersonalFragment");
            this.v = (aa) this.l.getFragment(bundle, "SystemFragment");
            this.w = (j) this.l.getFragment(bundle, "FactoryFragment");
            this.x = (n) this.l.getFragment(bundle, "NaviChooseFragment");
            this.y = (com.dofun.carsetting.fragment.u) this.l.getFragment(bundle, "PipChooseFragment");
            this.z = (ab) this.l.getFragment(bundle, "VideoOutSettingFragment");
            this.A = (com.dofun.carsetting.fragment.a) this.l.getFragment(bundle, "AboutFragment");
            this.B = (h) this.l.getFragment(bundle, "DefaultAppSelectFragment");
            this.C = (v) this.l.getFragment(bundle, "XTFragment");
            this.D = (z) this.l.getFragment(bundle, "SoundFragment");
            this.E = (q) this.l.getFragment(bundle, "OriginalCarFragment");
            b(this.o);
            b(this.p);
            b(this.q);
            b(this.r);
            b(this.s);
            b(this.t);
            b(this.u);
            b(this.v);
            b(this.w);
            b(this.x);
            b(this.y);
            b(this.z);
            b(this.A);
            b(this.B);
            b(this.C);
            b(this.D);
            b(this.E);
        } else {
            f();
        }
        this.Y = findViewById(R.id.main_layout_bg);
        com.dofun.carsetting.f.c.a(this.Y, this);
        this.F = (RadioButton) findViewById(R.id.rb_internet);
        this.I = (RadioButton) findViewById(R.id.rb_devices);
        this.J = (RadioButton) findViewById(R.id.rb_original);
        if (b.T()) {
            this.I.setVisibility(8);
            this.J.setVisibility(8);
        }
        this.K = (RadioButton) findViewById(R.id.rb_general);
        this.H = (RadioButton) findViewById(R.id.rb_reverse);
        this.G = (RadioButton) findViewById(R.id.rb_voice);
        this.L = (RadioButton) findViewById(R.id.rb_personal);
        this.M = (RadioButton) findViewById(R.id.rb_system);
        this.N = (RadioButton) findViewById(R.id.rb_factory);
        this.O = (RadioButton) findViewById(R.id.rb_devices_info);
        this.P = (RadioButton) findViewById(R.id.rb_setting);
        if (b.aw()) {
            this.P.setVisibility(8);
        }
        this.Q = (RadioButton) findViewById(R.id.rb_sound);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_setting_item);
        this.F.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this.ad);
        ConfigDateAsync configDateAsync = new ConfigDateAsync(this);
        configDateAsync.execute("/sdcard/.Tcfg/CarSettingConfig.json");
        configDateAsync.setOnCallBack(new ConfigDateAsync.OnCallBack() { // from class: com.dofun.carsetting.ui.MainActivity.1
            @Override // com.dofun.carsetting.bean.ConfigDateAsync.OnCallBack
            public void onKeyDate(ArrayList<ConfigBean> arrayList) {
                MainActivity.this.T = arrayList;
                for (int i = 0; i < MainActivity.this.T.size(); i++) {
                    ConfigBean configBean = (ConfigBean) MainActivity.this.T.get(i);
                    int identifier = MainActivity.this.getResources().getIdentifier(configBean.mId, "id", MainActivity.this.getPackageName());
                    View viewFindViewById = MainActivity.this.findViewById(identifier);
                    if (viewFindViewById != null) {
                        viewFindViewById.setVisibility(configBean.visibility.equals("1") ? 0 : 8);
                    }
                    if (MainActivity.this.U == identifier && MainActivity.this.n != null && (MainActivity.this.n instanceof ad)) {
                        ((ad) MainActivity.this.n).b(configBean.items);
                    }
                }
                MainActivity.this.j();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        Log.d("XTCarSetting.MainActivity", "initCheckBox: onResume:" + this.U);
        try {
            if (this.U != 0) {
                ((RadioButton) findViewById(this.U)).setChecked(true);
            }
            if (this.n != null) {
                d(this.n);
                return;
            }
            this.o = new l();
            c(this.o);
            d(this.o);
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivity", "initCheckBox: " + e.getMessage());
        }
    }

    private void b(Fragment fragment) {
        if (fragment != null) {
            this.m.add(fragment);
        }
    }

    private void f() {
        this.o = new l();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Fragment fragment) {
        try {
            this.l.executePendingTransactions();
            if (fragment.isAdded() || this.m.contains(fragment)) {
                return;
            }
            this.l.beginTransaction().add(R.id.main_frame_layout, fragment).commitAllowingStateLoss();
            this.m.add(fragment);
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivity", "addFragment: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Fragment fragment) {
        for (Fragment fragment2 : this.m) {
            if (fragment2 != fragment) {
                this.l.beginTransaction().hide(fragment2).commitAllowingStateLoss();
            }
        }
        for (int i = 0; i < this.l.getBackStackEntryCount(); i++) {
            try {
                this.l.popBackStack();
            } catch (Exception e) {
                Log.e("XTCarSetting.MainActivity", "showFragment popBackStack:" + e.getMessage());
            }
        }
        this.l.beginTransaction().show(fragment).commitAllowingStateLoss();
        this.n = fragment;
    }

    public void a(Fragment fragment) {
        FragmentTransaction fragmentTransactionBeginTransaction = this.l.beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
    }

    public void c() {
        Log.d("XTCarSetting.MainActivity", "popFragmentFromBackStack: ");
        onBackPressed();
    }

    private void a(Intent intent) {
        String stringExtra;
        if (intent == null || (stringExtra = intent.getStringExtra("carsetting.fragment")) == null) {
            return;
        }
        switch (stringExtra) {
            case "internetFragment":
                if (this.o == null) {
                    this.o = new l();
                }
                this.U = R.id.rb_internet;
                c(this.o);
                d(this.o);
                break;
            case "devicesFragment":
                if (this.p == null) {
                    this.p = new i();
                }
                this.U = R.id.rb_devices;
                c(this.p);
                d(this.p);
                break;
            case "originalFragment":
                if (this.q == null) {
                    this.q = new r();
                }
                this.U = R.id.rb_factory;
                c(this.q);
                d(this.q);
                break;
            case "generalFragment":
                if (this.r == null) {
                    this.r = new k();
                }
                this.U = R.id.rb_general;
                c(this.r);
                d(this.r);
                break;
            case "reverseFragment":
                if (this.s == null) {
                    this.s = new w();
                }
                this.U = R.id.rb_reverse;
                c(this.s);
                d(this.s);
                break;
            case "voiceFragment":
                if (this.t == null) {
                    this.t = new ac();
                }
                this.U = R.id.rb_voice;
                c(this.t);
                d(this.t);
                break;
            case "personalFragment":
                if (this.u == null) {
                    this.u = new t();
                }
                this.U = R.id.rb_personal;
                c(this.u);
                d(this.u);
                break;
            case "systemFragment":
                if (this.v == null) {
                    this.v = new aa();
                }
                if (getIntent() != null) {
                    this.v.a(getIntent().getIntExtra("cmd", -1), getIntent().getStringExtra("path"), getIntent().getBooleanExtra("autodel", false), getIntent().getBooleanExtra("wipeData", false));
                    setIntent(null);
                }
                this.U = R.id.rb_system;
                c(this.v);
                d(this.v);
                break;
            case "factoryFragment":
                if (this.w == null) {
                    this.w = new j();
                }
                if (getIntent() != null) {
                    this.w.a(getIntent().getIntExtra("cmd", -1), getIntent().getStringExtra("path"), getIntent().getBooleanExtra("autodel", false), getIntent().getBooleanExtra("wipeData", false));
                }
                c(this.w);
                d(this.w);
                this.U = R.id.rb_factory;
                if (this.w.isVisible()) {
                    this.w.c();
                }
                this.N.setChecked(true);
                break;
            case "NaviChooseFragment":
                this.K.setChecked(true);
                Fragment nVar = new n();
                c(nVar);
                d(nVar);
                break;
            case "PipChooseFragment":
                this.K.setChecked(true);
                Fragment uVar = new com.dofun.carsetting.fragment.u();
                c(uVar);
                d(uVar);
                break;
            case "VideoOutSettingFragment":
                this.K.setChecked(true);
                Fragment abVar = new ab();
                c(abVar);
                d(abVar);
                break;
            case "aboutFragment":
                if (this.A == null) {
                    this.A = new com.dofun.carsetting.fragment.a();
                }
                this.U = R.id.rb_devices_info;
                c(this.A);
                d(this.A);
                break;
            case "DefaultAppSelectFragment":
                String stringExtra2 = intent.getStringExtra("default_app_choose");
                if (TextUtils.equals(stringExtra2, "CONNECT")) {
                    this.L.setChecked(true);
                } else {
                    this.G.setChecked(true);
                }
                h hVar = new h();
                Bundle bundle = new Bundle();
                bundle.putString("type", stringExtra2);
                hVar.setArguments(bundle);
                c(hVar);
                d(hVar);
                break;
            case "RecorderChooseFragment":
                this.K.setChecked(true);
                Fragment vVar = new v();
                c(vVar);
                d(vVar);
                break;
            case "soundFragment":
                this.Q.setChecked(true);
                if (this.D == null) {
                    this.D = new z();
                }
                this.U = R.id.rb_sound;
                c(this.D);
                d(this.D);
                break;
            case "originalCarFragment":
                if (this.E == null) {
                    this.E = new q();
                }
                this.U = R.id.rb_original;
                c(this.E);
                d(this.E);
                break;
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private void g() {
        if (Build.VERSION.SDK_INT < 24 || !isInMultiWindowMode()) {
            return;
        }
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) == 1) {
            Intent intent = new Intent();
            intent.setAction("com.android.systemui.EXIT.SPLIT_SCREEN");
            intent.putExtra("package_name", "com.dofun.carsetting");
            sendBroadcast(intent);
            return;
        }
        if (!b.p() || b.I() || b.J()) {
            Toast.makeText(this.i, getString(R.string.dock_forced_resizable), 0).show();
            finish();
        }
    }

    public void d() throws Exception {
        Log.d("XTCarSetting.MainActivity", "initWallpaper:start");
        if (p.c(this)) {
            this.e.put("bg_0_bg_s", "bg_0_bg_long");
            this.e.put("bg_1_bg_s", "bg_1_bg_long");
            this.e.put("bg_2_bg_s", "bg_2_bg_long");
            this.e.put("bg_3_bg_s", "bg_3_bg_long");
            this.e.put("bg_4_bg_s", "bg_4_bg_long");
            this.e.put("bg_5_bg_s", "bg_5_bg_long");
            this.e.put("bg_6_bg_s", "bg_6_bg_long");
            if (b.a().contains("KDDZ")) {
                this.e.put("bg_7_bg_s", "bg_7_bg");
            }
            this.e.put("bg_8_bg_s", "bg_8_bg");
        } else {
            this.e.put("bg_0_bg_s", "bg_0_bg");
            this.e.put("bg_1_bg_s", "bg_1_bg");
            this.e.put("bg_2_bg_s", "bg_2_bg");
            this.e.put("bg_3_bg_s", "bg_3_bg");
            this.e.put("bg_4_bg_s", "bg_4_bg");
            this.e.put("bg_5_bg_s", "bg_5_bg");
            this.e.put("bg_6_bg_s", "bg_6_bg");
            if (b.a().contains("KDDZ")) {
                this.e.put("bg_7_bg_s", "bg_7_bg");
            }
            this.e.put("bg_8_bg_s", "bg_8_bg");
        }
        b(Settings.System.getString(this.i.getContentResolver(), "MAIN_BG_CHOOSE"));
        Log.d("XTCarSetting.MainActivity", "initWallpaper:end");
    }

    public void b(String str) throws Exception {
        WallpaperInfo wallpaperInfoC = c(str);
        int iA = a(this, wallpaperInfoC.wallpaperResId, "drawable");
        if (iA == 0) {
            try {
                if (p.c(this)) {
                    iA = a(this, wallpaperInfoC.wallpaperResId.substring(0, wallpaperInfoC.wallpaperResId.lastIndexOf(95)), "drawable");
                }
            } catch (Exception e) {
                Log.e("XTCarSetting.MainActivity", "setCarSettingBg: " + e.getMessage());
            }
        }
        try {
            if (iA != 0) {
                this.Y.setBackgroundResource(iA);
            } else {
                this.Y.setBackgroundResource(R.drawable.bg_0_bg);
            }
        } catch (Exception e2) {
            Log.w("XTCarSetting.MainActivity", "setCarSettingBg:fail:" + e2.getMessage());
        }
        this.af.removeMessages(65283);
        this.af.sendEmptyMessageDelayed(65283, 100L);
    }

    public boolean a(int i) {
        if (this.T == null) {
            return false;
        }
        boolean z = false;
        for (int i2 = 0; i2 < this.T.size(); i2++) {
            ConfigBean configBean = this.T.get(i2);
            int iA = m.a(this, configBean.mId);
            if (iA == i && configBean.isNeedPwd) {
                this.V = configBean.strPwd;
                this.W = configBean.strPwd2;
                z = true;
            }
            if (iA == i) {
                this.c = configBean.items;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final int i) {
        if (this.j == null) {
            this.j = new d(this.i);
        }
        MyRadioButton myRadioButton = (MyRadioButton) findViewById(i);
        String string = myRadioButton != null ? myRadioButton.getText().toString() : "";
        int i2 = this.f34a.N ? 129 : 2;
        this.j.a(string);
        this.j.a(this.i.getString(R.string.enter_your_pin), i2, true);
        this.j.a(new g() { // from class: com.dofun.carsetting.ui.MainActivity.5
            @Override // com.dofun.carsetting.g.g
            public void a(EditTextPreference editTextPreference, Editable editable) {
                if (i != R.id.rb_factory || !TextUtils.equals(MainActivity.this.W, editable.toString())) {
                    if (!TextUtils.equals(MainActivity.this.V, editable.toString())) {
                        MainActivity.this.e();
                        return;
                    }
                    int i3 = i;
                    if (i3 != R.id.rb_original) {
                        switch (i3) {
                            case R.id.rb_devices /* 2131231113 */:
                                if (MainActivity.this.p == null) {
                                    MainActivity.this.p = new i();
                                }
                                MainActivity.this.c(MainActivity.this.p);
                                MainActivity.this.d(MainActivity.this.p);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_devices_info /* 2131231114 */:
                                if (MainActivity.this.A == null) {
                                    MainActivity.this.A = new com.dofun.carsetting.fragment.a();
                                }
                                MainActivity.this.c(MainActivity.this.A);
                                MainActivity.this.d(MainActivity.this.A);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_factory /* 2131231115 */:
                                if (MainActivity.this.w == null) {
                                    MainActivity.this.w = new j();
                                }
                                MainActivity.this.c(MainActivity.this.w);
                                MainActivity.this.d(MainActivity.this.w);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_general /* 2131231116 */:
                                if (MainActivity.this.r == null) {
                                    MainActivity.this.r = new k();
                                }
                                MainActivity.this.c(MainActivity.this.r);
                                MainActivity.this.d(MainActivity.this.r);
                                MainActivity.this.U = i;
                                break;
                            case R.id.rb_internet /* 2131231117 */:
                                if (MainActivity.this.o == null) {
                                    MainActivity.this.o = new l();
                                }
                                MainActivity.this.c(MainActivity.this.o);
                                MainActivity.this.d(MainActivity.this.o);
                                MainActivity.this.U = i;
                                break;
                            default:
                                switch (i3) {
                                    case R.id.rb_personal /* 2131231125 */:
                                        if (MainActivity.this.u == null) {
                                            MainActivity.this.u = new t();
                                        }
                                        MainActivity.this.c(MainActivity.this.u);
                                        MainActivity.this.d(MainActivity.this.u);
                                        MainActivity.this.U = i;
                                        break;
                                    case R.id.rb_reverse /* 2131231126 */:
                                        if (MainActivity.this.s == null) {
                                            MainActivity.this.s = new w();
                                        }
                                        MainActivity.this.c(MainActivity.this.s);
                                        MainActivity.this.d(MainActivity.this.s);
                                        MainActivity.this.U = i;
                                        break;
                                    case R.id.rb_setting /* 2131231127 */:
                                        MainActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
                                        break;
                                    case R.id.rb_sound /* 2131231128 */:
                                        if (MainActivity.this.D == null) {
                                            MainActivity.this.D = new z();
                                        }
                                        MainActivity.this.c(MainActivity.this.D);
                                        MainActivity.this.d(MainActivity.this.D);
                                        MainActivity.this.U = i;
                                        break;
                                    case R.id.rb_system /* 2131231129 */:
                                        if (MainActivity.this.v == null) {
                                            MainActivity.this.v = new aa();
                                        }
                                        MainActivity.this.c(MainActivity.this.v);
                                        MainActivity.this.d(MainActivity.this.v);
                                        MainActivity.this.U = i;
                                        break;
                                    case R.id.rb_voice /* 2131231130 */:
                                        if (MainActivity.this.t == null) {
                                            MainActivity.this.t = new ac();
                                        }
                                        MainActivity.this.c(MainActivity.this.t);
                                        MainActivity.this.d(MainActivity.this.t);
                                        MainActivity.this.U = i;
                                        break;
                                }
                                break;
                        }
                        return;
                    }
                    if (MainActivity.this.E == null) {
                        MainActivity.this.E = new q();
                    }
                    MainActivity.this.c(MainActivity.this.E);
                    MainActivity.this.d(MainActivity.this.E);
                    MainActivity.this.U = i;
                    return;
                }
                if (MainActivity.this.q == null) {
                    MainActivity.this.q = new r();
                }
                MainActivity.this.c(MainActivity.this.q);
                MainActivity.this.d(MainActivity.this.q);
                MainActivity.this.U = i;
            }
        });
        this.j.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.ui.MainActivity.6
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                MainActivity.this.e();
            }
        });
        this.j.show();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        if (this.ac != null) {
            this.ac.close();
            this.ac.removeHandler("XTCarSetting.MainActivity");
        }
        if (this.w == null || !this.w.f()) {
            return;
        }
        a(this.i.getString(R.string.cfg_import_title) + "......");
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) throws Exception {
        if (this.w != null) {
            setIntent(null);
            boolean zF = this.w.f();
            Log.d("XTCarSetting.MainActivity", "onConfigurationChanged:isAutomatic:" + zF);
            if (zF) {
                super.onConfigurationChanged(configuration);
                return;
            }
        }
        setIntent(null);
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration2 = resources.getConfiguration();
        configuration2.locale = configuration.locale;
        getResources().updateConfiguration(configuration2, displayMetrics);
        Log.d("XTCarSetting.MainActivity", "onConfigurationChanged:conf.locale:" + configuration2.locale);
        h();
        setContentView(R.layout.activity_main);
        a((Bundle) null);
        this.o = new l();
        c(this.o);
        d(this.o);
        this.U = R.id.rb_internet;
        e();
        if (this.j != null) {
            this.j.dismiss();
            this.j = null;
        }
        d();
        if (this.H != null) {
            if (b.at()) {
                this.H.setText("360");
            } else {
                this.H.setText(R.string.reverse);
            }
        }
    }

    private void h() {
        this.o = null;
        this.p = null;
        this.D = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.E = null;
        if (this.l == null || this.l.getFragments() == null) {
            return;
        }
        this.l.getFragments().clear();
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Log.d("XTCarSetting.MainActivity", "onDestroy");
        try {
            this.n = null;
            setIntent(null);
            unbindService(this.ae);
        } catch (Exception e) {
            Log.d("XTCarSetting.MainActivity", "onDestroy:" + e.getMessage());
        }
        if (this.ac != null) {
            this.ac.removeHandler("XTCarSetting.MainActivity");
        }
        super.onDestroy();
        this.af.removeCallbacksAndMessages(null);
    }

    private void i() {
        Intent intent = new Intent();
        intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.config.ConfigService");
        getApplicationContext().bindService(intent, this.ae, 1);
    }

    @Override // com.dofun.carsetting.fragment.a.InterfaceC0017a
    public void b() {
        this.P.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Drawable drawable) throws Exception {
        if (this.g) {
            this.Y.setBackground(null);
            findViewById(R.id.main_sv_bg).setBackground(getDrawable(R.drawable.tl_bg2));
            getWindow().setBackgroundDrawableResource(R.color.key_normal);
        } else {
            findViewById(R.id.main_sv_bg).setBackground(getDrawable(R.drawable.tl_bg));
            d();
        }
    }

    @Override // com.dofun.carsetting.activity.BaseActivity
    protected void a() {
        Log.d("XTCarSetting.MainActivity", "onLoadPlatformConfigEnd: notify fragment start");
        for (int i = 0; i < this.m.size(); i++) {
            ((ad) this.m.get(i)).e();
        }
        this.aa = true;
        if (this.ab) {
            Intent intent = getIntent();
            StringBuilder sb = new StringBuilder();
            sb.append("onLoadPlatformConfigEnd:onResume wait for load platform config:Intent is null ? ");
            sb.append(intent == null);
            Log.w("XTCarSetting.MainActivity", sb.toString());
            if (intent != null) {
                a(intent);
                setIntent(null);
            }
        }
        Log.d("XTCarSetting.MainActivity", "onLoadPlatformConfigEnd: notify fragment end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (a(R.id.main_layout_bg)) {
            if (this.k == null) {
                this.k = new c(this.i);
            }
            int i = 129;
            if (this.f34a.N) {
                i = 2;
            } else if (this.f34a.M) {
                this.V = com.dofun.carsetting.f.r.b(this.i, "Main_PassWord", "PassWord", "UCAST.sg");
            }
            this.k.a(this.i.getString(R.string.app_name));
            this.k.a(i, true);
            this.k.a(new f() { // from class: com.dofun.carsetting.ui.MainActivity.9
                @Override // com.dofun.carsetting.g.f
                public void a(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_3));
                    } else if (TextUtils.equals(MainActivity.this.V, editable.toString())) {
                        MainActivity.this.k.b();
                        MainActivity.this.k.dismiss();
                    } else {
                        MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_0));
                    }
                }

                @Override // com.dofun.carsetting.g.f
                public void b(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_3));
                    } else if (TextUtils.equals(MainActivity.this.V, editable.toString())) {
                        MainActivity.this.k.a(2);
                    } else {
                        MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_0));
                    }
                }

                @Override // com.dofun.carsetting.g.f
                public void c(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_3));
                        return;
                    }
                    MainActivity.this.V = editable.toString();
                    com.dofun.carsetting.f.r.a(MainActivity.this.i, "Main_PassWord", "PassWord", MainActivity.this.V);
                    MainActivity.this.a(MainActivity.this.i.getString(R.string.enter_password_tips_2));
                    MainActivity.this.k.a(0);
                }
            });
            this.k.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.dofun.carsetting.ui.MainActivity.10
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    MainActivity.this.finish();
                }
            });
            this.k.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.ui.MainActivity.11
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    MainActivity.this.e();
                }
            });
            this.k.show();
        }
    }

    public WallpaperInfo c(String str) {
        for (Map.Entry<String, String> entry : this.e.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.equals(key, str)) {
                return new WallpaperInfo(key, value);
            }
        }
        if (b.a().contains("KDDZ")) {
            return new WallpaperInfo("bg_7_bg_s", "bg_7_bg");
        }
        return new WallpaperInfo("bg_0_bg_s", "bg_0_bg");
    }

    private int a(Context context, String str, String str2) throws Exception {
        try {
            return context.getResources().getIdentifier(str, str2, context.getPackageName());
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        View decorView;
        Log.i("XTCarSetting.MainActivity", "updateStatusBarLightDark:start");
        Window window = getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        int iA = com.dofun.carsetting.f.c.a((Context) this);
        int iB = com.dofun.carsetting.f.c.b((Context) this);
        Log.d("XTCarSetting.MainActivity", "updateStatusBarLightDark:screenWidth:" + iA + " statusBarHeight:" + iB);
        Palette.from(a(decorView, new Rect(0, 0, iA, iB), 1.0f)).clearFilters().generate(this.ag);
    }

    private Bitmap a(View view, Rect rect, float f) {
        int iRound = Math.round(rect.width() * f);
        int iRound2 = Math.round(rect.height() * f);
        if (view.getWidth() <= 0 || view.getHeight() <= 0 || iRound <= 0 || iRound2 <= 0) {
            throw new IllegalArgumentException("No screen available (width or height = 0)");
        }
        float f2 = (-rect.left) * f;
        float f3 = (-rect.top) * f;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iRound, iRound2, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        matrix.preScale(f, f);
        matrix.postTranslate(f2, f3);
        canvas.setMatrix(matrix);
        view.draw(canvas);
        return bitmapCreateBitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        View decorView;
        Window window = getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(z ? systemUiVisibility & (-8193) : systemUiVisibility | 8192);
        Log.i("XTCarSetting.MainActivity", "updateStatusBarLightDark:end");
    }
}
