package com.dofun.carsetting.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.activity.BaseActivity;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.bean.ConfigBean;
import com.dofun.carsetting.c.d;
import com.dofun.carsetting.config.ConfigService;
import com.dofun.carsetting.f.b;
import com.dofun.carsetting.f.c;
import com.dofun.carsetting.f.m;
import com.dofun.carsetting.fragment.aa;
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
import com.dofun.carsetting.fragment.u;
import com.dofun.carsetting.fragment.v;
import com.dofun.carsetting.fragment.w;
import com.dofun.carsetting.fragment.z;
import com.dofun.carsetting.g.g;
import com.dofun.carsetting.widget.EditTextPreference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainActivityV2 extends BaseActivity implements com.dofun.carsetting.fragment.a.InterfaceC0017a, NaviBarAdapter.b, NaviBarAdapter.c {
    public static boolean b = true;
    private String A;
    private List<ConfigBean> B;
    private int C;
    private String D;
    private String E;
    private RecyclerView G;
    private NaviBarAdapter H;
    private View J;
    private Drawable K;
    public List<ConfigBean> c;
    public ConfigService.a d;
    private Context e;
    private d f;
    private FragmentManager g;
    private Fragment i;
    private l j;
    private i k;
    private r l;
    private k m;
    private w n;
    private ac o;
    private t p;
    private aa q;
    private j r;
    private n s;
    private u t;
    private com.dofun.carsetting.fragment.a u;
    private h v;
    private v w;
    private z x;
    private q y;
    private String z;
    private ArrayList<Fragment> h = new ArrayList<>();
    private int F = 0;
    private List<a> I = new ArrayList();
    private boolean L = false;
    private boolean M = false;
    private ServiceConnection N = new ServiceConnection() { // from class: com.dofun.carsetting.ui.MainActivityV2.3
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("XTCarSetting.MainActivityV2", "onServiceConnected: ");
            MainActivityV2.this.d = (ConfigService.a) iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("XTCarSetting.MainActivityV2", "onServiceDisconnected: ");
        }
    };
    private Handler O = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.ui.MainActivityV2.4
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 65281:
                    MainActivityV2.this.e();
                    break;
                case 65282:
                    Log.d("XTCarSetting.MainActivityV2", "set background start");
                    MainActivityV2.this.J.setBackground(MainActivityV2.this.K);
                    Log.d("XTCarSetting.MainActivityV2", "set background end");
                    break;
            }
            return true;
        }
    });

    @Override // com.dofun.carsetting.ui.NaviBarAdapter.c
    public void d(int i) {
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_v2);
        this.g = getFragmentManager();
        a(bundle);
        this.e = this;
        g();
        c.b((Activity) this);
        c();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            this.O.removeMessages(65281);
            this.O.sendEmptyMessageDelayed(65281, 0L);
            sendBroadcast(new Intent("com.notification"));
            i();
            this.H.notifyDataSetChanged();
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            if (this.L) {
                a(intent);
                setIntent(null);
            } else {
                this.M = true;
            }
        }
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        try {
            Iterator<Fragment> it = this.h.iterator();
            while (it.hasNext()) {
                this.g.beginTransaction().hide(it.next()).commitAllowingStateLoss();
            }
            if (this.j != null && this.j.isAdded()) {
                getFragmentManager().putFragment(bundle, "InternetFragment", this.j);
            }
            if (this.k != null && this.k.isAdded()) {
                getFragmentManager().putFragment(bundle, "DevicesFragment", this.k);
            }
            if (this.l != null && this.l.isAdded()) {
                getFragmentManager().putFragment(bundle, "OriginalFragment", this.l);
            }
            if (this.m != null && this.m.isAdded()) {
                getFragmentManager().putFragment(bundle, "GeneralFragment", this.m);
            }
            if (this.n != null && this.n.isAdded()) {
                getFragmentManager().putFragment(bundle, "ReverseFragment", this.n);
            }
            if (this.o != null && this.o.isAdded()) {
                getFragmentManager().putFragment(bundle, "VoiceFragment", this.o);
            }
            if (this.p != null && this.p.isAdded()) {
                getFragmentManager().putFragment(bundle, "PersonalFragment", this.p);
            }
            if (this.q != null && this.q.isAdded()) {
                getFragmentManager().putFragment(bundle, "SystemFragment", this.q);
            }
            if (this.r != null && this.r.isAdded()) {
                getFragmentManager().putFragment(bundle, "FactoryFragment", this.r);
            }
            if (this.s != null && this.s.isAdded()) {
                getFragmentManager().putFragment(bundle, "NaviChooseFragment", this.s);
            }
            if (this.t != null && this.t.isAdded()) {
                getFragmentManager().putFragment(bundle, "PipChooseFragment", this.t);
            }
            if (this.u != null && this.u.isAdded()) {
                getFragmentManager().putFragment(bundle, "AboutFragment", this.u);
            }
            if (this.v != null && this.v.isAdded()) {
                getFragmentManager().putFragment(bundle, "DefaultAppSelectFragment", this.v);
            }
            if (this.w != null && this.w.isAdded()) {
                getFragmentManager().putFragment(bundle, "XTFragment", this.w);
            }
            if (this.x != null && this.x.isAdded()) {
                getFragmentManager().putFragment(bundle, "SoundFragment", this.x);
            }
            if (this.y != null && this.y.isAdded()) {
                getFragmentManager().putFragment(bundle, "OriginalCarFragment", this.y);
            }
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivityV2", Log.getStackTraceString(e));
        }
        super.onSaveInstanceState(bundle);
    }

    private void a(Bundle bundle) {
        if (bundle != null) {
            this.j = (l) this.g.getFragment(bundle, "InternetFragment");
            this.k = (i) this.g.getFragment(bundle, "DevicesFragment");
            this.l = (r) this.g.getFragment(bundle, "OriginalFragment");
            this.m = (k) this.g.getFragment(bundle, "GeneralFragment");
            this.n = (w) this.g.getFragment(bundle, "ReverseFragment");
            this.o = (ac) this.g.getFragment(bundle, "VoiceFragment");
            this.p = (t) this.g.getFragment(bundle, "PersonalFragment");
            this.q = (aa) this.g.getFragment(bundle, "SystemFragment");
            this.r = (j) this.g.getFragment(bundle, "FactoryFragment");
            this.s = (n) this.g.getFragment(bundle, "NaviChooseFragment");
            this.t = (u) this.g.getFragment(bundle, "PipChooseFragment");
            this.u = (com.dofun.carsetting.fragment.a) this.g.getFragment(bundle, "AboutFragment");
            this.v = (h) this.g.getFragment(bundle, "DefaultAppSelectFragment");
            this.w = (v) this.g.getFragment(bundle, "XTFragment");
            this.x = (z) this.g.getFragment(bundle, "SoundFragment");
            this.y = (q) this.g.getFragment(bundle, "OriginalCarFragment");
            a(this.j);
            a(this.k);
            a(this.l);
            a(this.m);
            a(this.n);
            a(this.o);
            a(this.p);
            a(this.q);
            a(this.r);
            a(this.s);
            a(this.t);
            a(this.u);
            a(this.v);
            a(this.w);
            a(this.x);
            a(this.y);
        } else {
            f();
        }
        this.J = findViewById(R.id.main_layout_bg);
        c.a(this.J, this);
        this.G = (RecyclerView) findViewById(R.id.rv_navi_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(1);
        this.G.setLayoutManager(linearLayoutManager);
        this.H = new NaviBarAdapter(this, this.I);
        this.G.setAdapter(this.H);
        this.H.a((NaviBarAdapter.b) this);
        this.H.a((NaviBarAdapter.c) this);
        d();
    }

    private void d() {
        this.I.clear();
        this.I.add(new a(R.id.rb_internet, R.drawable.rb_internet_bg, getString(R.string.internet), "internetFragment"));
        this.I.add(new a(R.id.rb_devices, R.drawable.rb_devices_bg, getString(R.string.display), "devicesFragment"));
        this.I.add(new a(R.id.rb_sound, R.drawable.rb_sound_bg, getString(R.string.sound_setting), "soundFragment"));
        this.I.add(new a(R.id.rb_general, R.drawable.rb_general_bg, getString(R.string.general), "generalFragment"));
        this.I.add(new a(R.id.rb_original, R.drawable.rb_original_bg, getString(R.string.original), "originalCarFragment"));
        this.I.add(new a(R.id.rb_reverse, R.drawable.rb_reverse_bg, getString(R.string.reverse), "reverseFragment"));
        this.I.add(new a(R.id.rb_voice, R.drawable.rb_voice_bg, getString(R.string.voice), "voiceFragment"));
        this.I.add(new a(R.id.rb_personal, R.drawable.rb_personal_bg, getString(R.string.personal), "personalFragment"));
        this.I.add(new a(R.id.rb_system, R.drawable.rb_system_bg, getString(R.string.system), "systemFragment"));
        this.I.add(new a(R.id.rb_factory, R.drawable.rb_factory_bg, getString(R.string.factory), "factoryFragment"));
        if (!b.aw()) {
            this.I.add(new a(R.id.rb_original_setting, R.drawable.rb_setting_bg, getString(R.string.key_more), "originalSettings"));
        }
        this.I.add(new a(R.id.rb_devices_info, R.drawable.rb_about_bg, getString(R.string.about), "aboutFragment"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        Log.d("XTCarSetting.MainActivityV2", "initCheckBox: onResume:" + this.C);
        try {
            if (this.C == 0) {
                this.C = this.I.get(0).f417a;
            }
            this.H.b(this.C);
            if (this.i != null) {
                c(this.i);
                return;
            }
            this.j = new l();
            b(this.j);
            c(this.j);
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivityV2", "initCheckBox: " + e.getMessage());
        }
    }

    private void a(Fragment fragment) {
        if (fragment != null) {
            this.h.add(fragment);
        }
    }

    private void f() {
        this.j = new l();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Fragment fragment) {
        try {
            this.g.executePendingTransactions();
            if (fragment.isAdded() || this.h.contains(fragment)) {
                return;
            }
            this.g.beginTransaction().add(R.id.main_frame_layout, fragment).commitAllowingStateLoss();
            this.h.add(fragment);
        } catch (Exception e) {
            Log.e("XTCarSetting.MainActivityV2", "addFragment: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Fragment fragment) {
        for (Fragment fragment2 : this.h) {
            if (fragment2 != fragment) {
                this.g.beginTransaction().hide(fragment2).commitAllowingStateLoss();
            }
        }
        for (int i = 0; i < this.g.getBackStackEntryCount(); i++) {
            try {
                this.g.popBackStack();
            } catch (Exception e) {
                Log.e("XTCarSetting.MainActivityV2", "showFragment popBackStack:" + e.getMessage());
            }
        }
        this.g.beginTransaction().show(fragment).commitAllowingStateLoss();
        this.i = fragment;
    }

    private void a(Intent intent) {
        String stringExtra;
        if (intent == null || (stringExtra = intent.getStringExtra("carsetting.fragment")) == null) {
            return;
        }
        a(intent, stringExtra);
    }

    private void a(Intent intent, String str) {
        switch (str) {
            case "internetFragment":
                if (this.j == null) {
                    this.j = new l();
                }
                this.C = R.id.rb_internet;
                b(this.j);
                c(this.j);
                break;
            case "devicesFragment":
                if (this.k == null) {
                    this.k = new i();
                }
                this.C = R.id.rb_devices;
                b(this.k);
                c(this.k);
                break;
            case "originalFragment":
                if (this.l == null) {
                    this.l = new r();
                }
                this.C = R.id.rb_factory;
                b(this.l);
                c(this.l);
                break;
            case "generalFragment":
                if (this.m == null) {
                    this.m = new k();
                }
                this.C = R.id.rb_general;
                b(this.m);
                c(this.m);
                break;
            case "reverseFragment":
                if (this.n == null) {
                    this.n = new w();
                }
                this.C = R.id.rb_reverse;
                b(this.n);
                c(this.n);
                break;
            case "voiceFragment":
                if (this.o == null) {
                    this.o = new ac();
                }
                this.C = R.id.rb_voice;
                b(this.o);
                c(this.o);
                break;
            case "personalFragment":
                if (this.p == null) {
                    this.p = new t();
                }
                this.C = R.id.rb_personal;
                b(this.p);
                c(this.p);
                break;
            case "systemFragment":
                if (this.q == null) {
                    this.q = new aa();
                }
                this.C = R.id.rb_system;
                b(this.q);
                c(this.q);
                break;
            case "factoryFragment":
                if (this.r == null) {
                    this.r = new j();
                }
                if (getIntent() != null) {
                    this.r.a(getIntent().getIntExtra("cmd", -1), getIntent().getStringExtra("path"));
                }
                b(this.r);
                c(this.r);
                this.C = R.id.rb_factory;
                if (this.r.isVisible()) {
                    this.r.c();
                }
                this.H.a("factoryFragment");
                break;
            case "NaviChooseFragment":
                Fragment nVar = new n();
                b(nVar);
                c(nVar);
                this.H.a("generalFragment");
                break;
            case "PipChooseFragment":
                Fragment uVar = new u();
                b(uVar);
                c(uVar);
                this.H.a("generalFragment");
                break;
            case "aboutFragment":
                if (this.u == null) {
                    this.u = new com.dofun.carsetting.fragment.a();
                }
                this.C = R.id.rb_devices_info;
                b(this.u);
                c(this.u);
                break;
            case "DefaultAppSelectFragment":
                String stringExtra = intent != null ? intent.getStringExtra("default_app_choose") : "";
                h hVar = new h();
                Bundle bundle = new Bundle();
                bundle.putString("type", stringExtra);
                hVar.setArguments(bundle);
                b(hVar);
                c(hVar);
                this.H.a("personalFragment");
                break;
            case "RecorderChooseFragment":
                Fragment vVar = new v();
                b(vVar);
                c(vVar);
                this.H.a("personalFragment");
                break;
            case "soundFragment":
                if (this.x == null) {
                    this.x = new z();
                }
                this.C = R.id.rb_sound;
                b(this.x);
                c(this.x);
                this.H.a("soundFragment");
                break;
            case "originalCarFragment":
                if (this.y == null) {
                    this.y = new q();
                }
                this.C = R.id.rb_original;
                b(this.y);
                c(this.y);
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
        Toast.makeText(this.e, getString(R.string.dock_forced_resizable), 0).show();
        finish();
    }

    public void c() {
        Log.d("XTCarSetting.MainActivityV2", "initWallpaper:start");
        if (b.a().contains("KDDZ")) {
            this.F = 7;
        } else {
            this.F = 0;
        }
        a(Settings.System.getInt(this.e.getContentResolver(), "MAIN_BG_CHOOSE", this.F));
        Log.d("XTCarSetting.MainActivityV2", "initWallpaper:end");
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.dofun.carsetting.ui.MainActivityV2$1] */
    public void a(int i) {
        final int iB = m.b(this, "bg_" + i + "_bg");
        new Thread() { // from class: com.dofun.carsetting.ui.MainActivityV2.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (iB != 0) {
                    MainActivityV2.this.K = MainActivityV2.this.getResources().getDrawable(iB);
                } else {
                    MainActivityV2.this.K = MainActivityV2.this.getResources().getDrawable(R.drawable.bg_0_bg);
                }
                MainActivityV2.this.O.sendEmptyMessage(65282);
            }
        }.start();
    }

    public boolean b(int i) {
        if (this.B == null) {
            return false;
        }
        boolean z = false;
        for (int i2 = 0; i2 < this.B.size(); i2++) {
            ConfigBean configBean = this.B.get(i2);
            int iA = m.a(this, configBean.mId);
            if (iA == i && configBean.isNeedPwd) {
                this.D = configBean.strPwd;
                this.E = configBean.strPwd2;
                z = true;
            }
            if (iA == i) {
                this.c = configBean.items;
            }
        }
        return z;
    }

    private void a(int i, String str) {
        if (this.f == null) {
            this.f = new d(this.e);
        }
        this.f.a(str);
        this.f.a(this.e.getString(R.string.enter_your_pin), 2, true);
        this.f.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.ui.MainActivityV2.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                MainActivityV2.this.e();
            }
        });
        this.f.show();
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        setIntent(null);
        super.onConfigurationChanged(configuration);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration2 = resources.getConfiguration();
        configuration2.locale = configuration.locale;
        getResources().updateConfiguration(configuration2, displayMetrics);
        Log.d("XTCarSetting.MainActivityV2", "onConfigurationChanged:conf.locale:" + configuration2.locale);
        h();
        setContentView(R.layout.activity_main_v2);
        a((Bundle) null);
        this.j = new l();
        b(this.j);
        c(this.j);
        this.C = R.id.rb_internet;
        e();
        if (this.f != null) {
            this.f.dismiss();
            this.f = null;
        }
    }

    private void h() {
        this.j = null;
        this.k = null;
        this.x = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.y = null;
        if (this.g == null || this.g.getFragments() == null) {
            return;
        }
        this.g.getFragments().clear();
    }

    @Override // com.dofun.carsetting.activity.BaseActivity, android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        Log.d("XTCarSetting.MainActivityV2", "onDestroy");
        try {
            this.i = null;
            setIntent(null);
            unbindService(this.N);
        } catch (Exception e) {
            Log.d("XTCarSetting.MainActivityV2", "onDestroy:" + e.getMessage());
        }
        super.onDestroy();
    }

    private void i() {
        Intent intent = new Intent();
        intent.setClassName("com.dofun.carsetting", "com.dofun.carsetting.config.ConfigService");
        getApplicationContext().bindService(intent, this.N, 1);
    }

    @Override // com.dofun.carsetting.fragment.a.InterfaceC0017a
    public void b() {
        int iC = this.H.c(R.id.rb_devices_info);
        if (iC == -1) {
            return;
        }
        this.I.add(iC, new a(R.id.rb_original_setting, R.drawable.rb_setting_bg, getString(R.string.key_more), "originalSettings"));
        this.H.b(this.C);
        this.H.notifyDataSetChanged();
    }

    @Override // com.dofun.carsetting.ui.NaviBarAdapter.b
    public void c(int i) {
        a aVar = this.I.get(i);
        if (aVar == null || TextUtils.isEmpty(aVar.e)) {
            return;
        }
        boolean zB = b(aVar.f417a);
        if (this.C == aVar.f417a) {
        }
        if (zB) {
            a(aVar.f417a, aVar.c);
            return;
        }
        switch (aVar.e) {
            case "factoryFragment":
                j();
                break;
            case "originalSettings":
                startActivity(new Intent("android.settings.SETTINGS"));
                break;
            default:
                a((Intent) null, aVar.e);
                this.C = aVar.f417a;
                break;
        }
    }

    private void j() {
        this.z = Settings.System.getString(MyApp.a().getContentResolver(), "USER_LOCK");
        if (TextUtils.isEmpty(this.z)) {
            this.z = "8888";
        }
        this.A = Settings.System.getString(MyApp.a().getContentResolver(), "FACTORY_LOCK");
        if (TextUtils.isEmpty(this.A)) {
            this.A = "123456";
        }
        if (this.f == null) {
            this.f = new d(this.e);
            this.f.a(this.e.getString(R.string.factory));
            this.f.a(this.e.getString(R.string.enter_your_pin), 2, true);
            this.f.a(new g() { // from class: com.dofun.carsetting.ui.MainActivityV2.5
                @Override // com.dofun.carsetting.g.g
                public void a(EditTextPreference editTextPreference, Editable editable) {
                    if (TextUtils.equals(MainActivityV2.this.z, editable.toString()) || TextUtils.equals("61808683", editable.toString())) {
                        if (MainActivityV2.this.l == null) {
                            MainActivityV2.this.l = new r();
                        }
                        MainActivityV2.this.b(MainActivityV2.this.l);
                        MainActivityV2.this.c(MainActivityV2.this.l);
                        MainActivityV2.this.C = R.id.rb_factory;
                        MainActivityV2.this.H.a("factoryFragment");
                        return;
                    }
                    if (TextUtils.equals(MainActivityV2.this.A, editable.toString())) {
                        if (MainActivityV2.this.r == null) {
                            MainActivityV2.this.r = new j();
                        }
                        MainActivityV2.this.b(MainActivityV2.this.r);
                        MainActivityV2.this.c(MainActivityV2.this.r);
                        MainActivityV2.this.C = R.id.rb_factory;
                        MainActivityV2.this.H.a("factoryFragment");
                        return;
                    }
                    MainActivityV2.this.e();
                }
            });
            this.f.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.ui.MainActivityV2.6
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    MainActivityV2.this.e();
                }
            });
        }
        if (this.r == null) {
            this.r = new j();
        }
        if (this.r.isVisible()) {
            return;
        }
        this.f.show();
    }

    @Override // com.dofun.carsetting.activity.BaseActivity
    protected void a() {
        Log.d("XTCarSetting.MainActivityV2", "onLoadPlatformConfigEnd: notify fragment start");
        for (int i = 0; i < this.h.size(); i++) {
            ((ad) this.h.get(i)).e();
        }
        this.L = true;
        if (this.M) {
            Intent intent = getIntent();
            StringBuilder sb = new StringBuilder();
            sb.append("onLoadPlatformConfigEnd:onResume wait for load platform config:Intent is null ? ");
            sb.append(intent == null);
            Log.w("XTCarSetting.MainActivityV2", sb.toString());
            if (intent != null) {
                a(intent);
                setIntent(null);
            }
        }
        Log.d("XTCarSetting.MainActivityV2", "onLoadPlatformConfigEnd: notify fragment end");
    }
}
