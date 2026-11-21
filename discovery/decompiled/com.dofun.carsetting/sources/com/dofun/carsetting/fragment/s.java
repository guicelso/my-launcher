package com.dofun.carsetting.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.EditTextPreference;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: PasswordFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class s extends ad<com.dofun.carsetting.presenter.e> implements View.OnClickListener, com.dofun.carsetting.g.j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    String f342a;
    private LinearLayout c;
    private InfoPreference d;
    private InfoPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private InfoPreference k;
    private InfoPreference l;
    private InfoPreference m;
    private InfoPreference n;
    private InfoPreference o;
    private com.dofun.carsetting.c.h p;
    private com.dofun.carsetting.f.u s;
    private View t;
    private SwitchPreference u;
    private SwitchPreference v;
    private SwitchPreference w;
    private String[] y;
    private String q = "/sdcard/.Tcfg/password.cfg";
    private Map<String, String> r = new HashMap();
    String b = "";
    private Handler x = new Handler(Looper.getMainLooper()) { // from class: com.dofun.carsetting.fragment.s.1
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            if (message.what != 266) {
                return;
            }
            if (((String) message.obj) != null && ((String) message.obj).length() > 0) {
                try {
                    String str = ((String) message.obj).split("-")[3];
                    int i = Integer.parseInt(str.substring(4, 6), 16);
                    String strSubstring = str.substring(1, 2);
                    switch ((i & 28) >> 2) {
                        case 0:
                            s.this.f342a = s.this.i.getString(R.string.stm8);
                            break;
                        case 1:
                            s.this.f342a = s.this.i.getString(R.string.stm32);
                            break;
                        case 2:
                            s.this.f342a = s.this.i.getString(R.string.gd330);
                            break;
                        case 3:
                            s.this.f342a = s.this.i.getString(R.string.gd230);
                            break;
                        case 4:
                            s.this.f342a = s.this.i.getString(R.string.mm030);
                            break;
                        case 5:
                            s.this.f342a = s.this.i.getString(R.string.mm031);
                            break;
                        case 6:
                            s.this.f342a = s.this.i.getString(R.string.gd105);
                            break;
                        case 7:
                            s.this.f342a = com.dofun.carsetting.b.a.a().r.b().get(strSubstring);
                            if (TextUtils.isEmpty(s.this.f342a)) {
                                s.this.f342a = s.this.i.getString(R.string.unknown);
                            }
                            break;
                    }
                } catch (Exception unused) {
                }
            }
            if (message.arg1 == 0 && message.obj != null) {
                s.this.b = message.obj.toString();
                if (com.dofun.carsetting.f.b.aK()) {
                    s.this.b = "Ts8.4.5" + s.this.b.substring(s.this.b.indexOf("-"));
                }
            }
            String string = Settings.System.getString(MyApp.a().getContentResolver(), "MCU_LOCK");
            if (TextUtils.isEmpty(string)) {
                string = s.this.b + "(" + s.this.f342a + ")";
            }
            s.this.l.setValueTip(string);
        }
    };

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.e d() {
        return null;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.t = layoutInflater.inflate(R.layout.fragment_set_password, (ViewGroup) null);
        this.s = com.dofun.carsetting.f.u.a();
        f();
        return this.t;
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.s != null) {
            this.s.addHandler("PasswordFragment", this.x);
            this.s.write(266, 255);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.c = (LinearLayout) view.findViewById(R.id.iv_back);
        this.c.setOnClickListener(this);
        this.d = (InfoPreference) view.findViewById(R.id.user_lock);
        this.d.setOnPreferenceChangeListener(this);
        this.e = (InfoPreference) view.findViewById(R.id.factory_lock);
        this.e.setOnPreferenceChangeListener(this);
        this.f = (InfoPreference) view.findViewById(R.id.reset_factory_lock);
        this.f.setOnPreferenceChangeListener(this);
        this.g = (InfoPreference) view.findViewById(R.id.platforminfo_lock);
        this.g.setOnPreferenceChangeListener(this);
        this.g.setTitle(this.i.getString(R.string.about_platform_info).substring(0, this.i.getString(R.string.about_platform_info).length() - 1));
        this.k = (InfoPreference) view.findViewById(R.id.cpu_lock);
        this.k.setOnPreferenceChangeListener(this);
        this.k.setTitle(this.i.getString(R.string.about_cpu_info_t).substring(0, this.i.getString(R.string.about_cpu_info_t).length() - 1));
        this.m = (InfoPreference) view.findViewById(R.id.boot_lock);
        this.m.setOnPreferenceChangeListener(this);
        this.m.setTitle(this.i.getString(R.string.about_boot_info_t).substring(0, this.i.getString(R.string.about_boot_info_t).length() - 1));
        this.l = (InfoPreference) view.findViewById(R.id.mcu_lock);
        this.l.setOnPreferenceChangeListener(this);
        this.l.setTitle(this.i.getString(R.string.about_mcu_info_t).substring(0, this.i.getString(R.string.about_mcu_info_t).length() - 1));
        this.n = (InfoPreference) view.findViewById(R.id.system_lock);
        this.n.setOnPreferenceChangeListener(this);
        this.n.setTitle(this.i.getString(R.string.about_system_info_t).substring(0, this.i.getString(R.string.about_system_info_t).length() - 1));
        this.o = (InfoPreference) view.findViewById(R.id.app_version_lock);
        this.o.setOnPreferenceChangeListener(this);
        this.o.setTitle(this.i.getString(R.string.about_versioncode_info).substring(0, this.i.getString(R.string.about_versioncode_info).length() - 1));
        this.u = (SwitchPreference) view.findViewById(R.id.hardware_info);
        this.u.setOnPreferenceChangeListener(this);
        this.u.setValue(Settings.System.getInt(this.i.getContentResolver(), "hardware_info_visibility", 1) != 0);
        this.w = (SwitchPreference) view.findViewById(R.id.storage_info_switch);
        this.w.setOnPreferenceChangeListener(this);
        this.w.setValue(SystemProperties.getInt("persist.tw.storage.switch", 1) >= 1);
        if (com.dofun.carsetting.f.b.bd()) {
            this.w.setVisibility(0);
        }
        this.v = (SwitchPreference) view.findViewById(R.id.resolution_info);
        this.v.setOnPreferenceChangeListener(this);
        this.v.setValue(Settings.System.getInt(this.i.getContentResolver(), "resolution_info_visibility", 1) != 0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.s() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.o() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.k.setVisibility(0);
            this.m.setVisibility(0);
            this.l.setVisibility(0);
            this.n.setVisibility(0);
            this.v.setVisibility((i < 1280 || i2 < 720) ? 8 : 0);
        }
        if (com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.p() || com.dofun.carsetting.f.b.G() || com.dofun.carsetting.f.b.I() || com.dofun.carsetting.f.b.J()) {
            this.o.setVisibility(0);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() throws Throwable {
        this.r.clear();
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "USER_LOCK");
        if (TextUtils.isEmpty(string)) {
            string = "8888";
        }
        this.r.put("USER_LOCK", string);
        String string2 = Settings.System.getString(MyApp.a().getContentResolver(), "FACTORY_LOCK");
        if (TextUtils.isEmpty(string2)) {
            string2 = "123456";
        }
        this.r.put("FACTORY_LOCK", string2);
        String string3 = Settings.System.getString(MyApp.a().getContentResolver(), "RESET_FACTORY");
        if (TextUtils.isEmpty(string3)) {
            string3 = "7890";
        }
        this.r.put("RESET_FACTORY", string3);
        String string4 = Settings.System.getString(MyApp.a().getContentResolver(), "PLATFORMINFO");
        if (TextUtils.isEmpty(string4)) {
            string4 = com.dofun.carsetting.f.b.j(this.i);
        }
        this.r.put("PLATFORMINFO", string4);
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() throws Throwable {
        String string = Settings.System.getString(MyApp.a().getContentResolver(), "USER_LOCK");
        if (TextUtils.isEmpty(string)) {
            string = "8888";
        }
        this.d.setValueTip(this.i.getResources().getString(R.string.password) + string);
        String string2 = Settings.System.getString(MyApp.a().getContentResolver(), "FACTORY_LOCK");
        if (TextUtils.isEmpty(string2)) {
            string2 = "123456";
        }
        this.e.setValueTip(this.i.getResources().getString(R.string.password) + string2);
        String string3 = Settings.System.getString(MyApp.a().getContentResolver(), "RESET_FACTORY");
        if (TextUtils.isEmpty(string3)) {
            string3 = "7890";
        }
        this.f.setValueTip(this.i.getResources().getString(R.string.password) + string3);
        String string4 = Settings.System.getString(MyApp.a().getContentResolver(), "PLATFORMINFO");
        if (TextUtils.isEmpty(string4)) {
            string4 = com.dofun.carsetting.f.b.j(this.i);
        }
        this.g.setValueTip(string4);
        String string5 = Settings.System.getString(MyApp.a().getContentResolver(), "CPU_LOCK");
        if (TextUtils.isEmpty(string5)) {
            string5 = com.dofun.carsetting.f.b.f(this.i);
        }
        this.k.setValueTip(string5);
        String string6 = Settings.System.getString(MyApp.a().getContentResolver(), "BOOT_LOCK");
        if (TextUtils.isEmpty(string6)) {
            string6 = SystemProperties.get("ro.boot.lcd_describe");
            String strC = com.dofun.carsetting.f.b.c("/sys/class/boot_version/version");
            if (com.dofun.carsetting.f.b.f() && !strC.equals("")) {
                string6 = strC;
            } else if (string6 == null) {
                string6 = this.i.getResources().getString(R.string.unknown);
            }
        }
        this.m.setValueTip(string6);
        String string7 = Settings.System.getString(MyApp.a().getContentResolver(), "SYSTEM_LOCK");
        if (TextUtils.isEmpty(string7)) {
            string7 = SystemProperties.get("ro.tw.version");
            if (TextUtils.isEmpty(string7)) {
                string7 = this.i.getResources().getString(R.string.unknown);
            }
        }
        this.n.setValueTip(string7);
        String string8 = Settings.System.getString(MyApp.a().getContentResolver(), "APP_VERSION_LOCK");
        if (TextUtils.isEmpty(string8)) {
            string8 = com.dofun.carsetting.f.b.a();
            if (TextUtils.isEmpty(string8)) {
                string8 = this.i.getResources().getString(R.string.unknown);
            }
        }
        this.o.setValueTip(string8);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.iv_back) {
            return;
        }
        ((MainActivity) getActivity()).c();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:54:0x020f  */
    /* JADX WARN: Code duplicated, block: B:55:0x0212  */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        String str;
        int i = this.j.N ? 129 : 2;
        switch (twPreference.getId()) {
            case R.id.app_version_lock /* 2131230760 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_versioncode_info).substring(0, this.i.getString(R.string.about_versioncode_info).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.10
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("APP_VERSION_LOCK", editable.toString());
                            s.this.a(s.this.q);
                            s.this.c();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.11
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.boot_lock /* 2131230771 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_boot_info_t).substring(0, this.i.getString(R.string.about_boot_info_t).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.4
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("BOOT_LOCK", editable.toString());
                            s.this.a(s.this.q);
                            s.this.c();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.5
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.cpu_lock /* 2131230839 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_cpu_info_t).substring(0, this.i.getString(R.string.about_cpu_info_t).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.2
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("CPU_LOCK", editable.toString());
                            s.this.a(s.this.q);
                            s.this.c();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.3
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.factory_lock /* 2131230871 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.factory_settings_title));
                    this.p.a(this.i.getString(R.string.enter_your_pin), i, true, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.14
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            if (!TextUtils.equals(editable.toString(), (CharSequence) s.this.r.get("USER_LOCK"))) {
                                s.this.r.put("FACTORY_LOCK", editable.toString());
                                s.this.a(s.this.q);
                                s.this.c();
                                return;
                            }
                            Toast.makeText(s.this.i, s.this.i.getResources().getString(R.string.invalid_password), 0).show();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.15
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.hardware_info /* 2131230892 */:
                try {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    Log.d("PasswordFragment", "onPreferenceChange: hardware_info=" + zBooleanValue);
                    Settings.System.putInt(this.i.getContentResolver(), "hardware_info_visibility", zBooleanValue ? 1 : 0);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    boolean zBooleanValue2 = ((Boolean) obj).booleanValue();
                    Log.d("PasswordFragment", "onPreferenceChange: resolution_info=" + zBooleanValue2);
                    Settings.System.putInt(this.i.getContentResolver(), "resolution_info_visibility", zBooleanValue2 ? 1 : 0);
                    break;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (((Boolean) obj).booleanValue()) {
                    str = "1";
                } else {
                    str = "0";
                }
                SystemProperties.set("persist.tw.storage.switch", str);
                return false;
            case R.id.mcu_lock /* 2131231058 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_mcu_info_t).substring(0, this.i.getString(R.string.about_mcu_info_t).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.6
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("MCU_LOCK", editable.toString());
                            s.this.a(s.this.q);
                            if (s.this.s != null) {
                                s.this.s.write(266, 255);
                            }
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.7
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.platforminfo_lock /* 2131231097 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_platform_info).substring(0, this.i.getString(R.string.about_platform_info).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.18
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            String string = editable.toString();
                            if (!com.dofun.carsetting.f.b.a().contains("-GX") || !com.dofun.carsetting.f.b.F() || !string.startsWith("T5")) {
                                s.this.f();
                                if (!s.this.a(s.this.y, string) || com.dofun.carsetting.f.b.k(s.this.i).toLowerCase().equals(string.toLowerCase())) {
                                    s.this.r.put("PLATFORMINFO", string);
                                    s.this.a(s.this.q);
                                    s.this.c();
                                    return;
                                }
                                Toast.makeText(s.this.getActivity(), "Update failure!", 0).show();
                                return;
                            }
                            if ("T5L".equalsIgnoreCase(string)) {
                                s.this.r.put("PLATFORMINFO", string);
                                s.this.a(s.this.q);
                                s.this.c();
                                return;
                            }
                            Toast.makeText(s.this.getActivity(), "Update failure!", 0).show();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.19
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.reset_factory_lock /* 2131231137 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.reset_factory_title));
                    this.p.a(this.i.getString(R.string.enter_your_pin), i, true, 1);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.16
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("RESET_FACTORY", editable.toString());
                            s.this.a(s.this.q);
                            s.this.c();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.17
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.resolution_info /* 2131231140 */:
                boolean zBooleanValue3 = ((Boolean) obj).booleanValue();
                Log.d("PasswordFragment", "onPreferenceChange: resolution_info=" + zBooleanValue3);
                Settings.System.putInt(this.i.getContentResolver(), "resolution_info_visibility", zBooleanValue3 ? 1 : 0);
                if (((Boolean) obj).booleanValue()) {
                    str = "1";
                } else {
                    str = "0";
                }
                SystemProperties.set("persist.tw.storage.switch", str);
                return false;
            case R.id.storage_info_switch /* 2131231226 */:
                if (((Boolean) obj).booleanValue()) {
                    str = "1";
                } else {
                    str = "0";
                }
                SystemProperties.set("persist.tw.storage.switch", str);
                return false;
            case R.id.system_lock /* 2131231241 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.about_system_info_t).substring(0, this.i.getString(R.string.about_system_info_t).length() - 1));
                    this.p.a(this.i.getString(R.string.enter_your_info), 1, false, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.8
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            s.this.r.put("SYSTEM_LOCK", editable.toString());
                            s.this.a(s.this.q);
                            s.this.c();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.9
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            case R.id.user_lock /* 2131231384 */:
                if (this.p == null) {
                    this.p = new com.dofun.carsetting.c.h(getActivity());
                    this.p.a(this.i.getString(R.string.client_settings_title));
                    this.p.a(this.i.getString(R.string.enter_your_pin), i, true, 0);
                    if (this.p != null) {
                        this.p.show();
                    }
                    this.p.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.s.12
                        @Override // com.dofun.carsetting.g.g
                        public void a(EditTextPreference editTextPreference, Editable editable) throws Throwable {
                            if (!TextUtils.equals(editable.toString(), (CharSequence) s.this.r.get("FACTORY_LOCK"))) {
                                s.this.r.put("USER_LOCK", editable.toString());
                                s.this.a(s.this.q);
                                s.this.c();
                                return;
                            }
                            Toast.makeText(s.this.i, s.this.i.getResources().getString(R.string.invalid_password), 0).show();
                        }
                    });
                }
                this.p.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.s.13
                    @Override // android.content.DialogInterface.OnDismissListener
                    public void onDismiss(DialogInterface dialogInterface) {
                        s.this.p = null;
                    }
                });
                return false;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.BufferedWriter] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.BufferedWriter] */
    public void a(String str) throws Throwable {
        ?? bufferedWriter;
        try {
            if (!new File("/sdcard/.Tcfg/").exists()) {
                this.s.write(40730, 3, 0, "mkdir /sdcard/.Tcfg");
            }
            ?? r0 = 0;
            ?? r1 = 0;
            try {
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(str));
                    try {
                        for (Map.Entry<String, String> entry : this.r.entrySet()) {
                            Settings.System.putString(MyApp.a().getContentResolver(), entry.getKey(), entry.getValue());
                            bufferedWriter.write(entry.getKey() + "=" + entry.getValue());
                            bufferedWriter.write(10);
                            bufferedWriter.flush();
                        }
                        bufferedWriter.close();
                    } catch (Exception unused) {
                        r1 = bufferedWriter;
                        new File(str).delete();
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
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter = r0;
                }
            } catch (Exception unused2) {
            }
            r0 = -1;
            FileUtils.setPermissions(str, 438, -1, -1);
        } catch (Exception unused3) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.y = this.i.getResources().getStringArray(R.array.platform_list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.toLowerCase().contains(str2.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
