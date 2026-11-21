package com.dofun.carsetting.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.dofun.carsetting.R;
import com.dofun.carsetting.bean.AppInfo;
import com.dofun.carsetting.ui.MainActivity;
import com.dofun.carsetting.widget.EditTextPreference;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: compiled from: PersonalFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class t extends ad<com.dofun.carsetting.presenter.i> implements com.dofun.carsetting.g.j, com.dofun.carsetting.g.n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public com.dofun.carsetting.f.u f362a;
    private InfoPreference b;
    private InfoPreference c;
    private InfoPreference d;
    private InfoPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private SwitchPreference k;
    private InfoPreference l;
    private LinearLayout m;
    private com.dofun.carsetting.widget.b n;
    private com.dofun.carsetting.c.d o;
    private int p;
    private int q;
    private Handler r = new Handler() { // from class: com.dofun.carsetting.fragment.t.4
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            try {
                if (message.what == 65281) {
                    String string = Settings.System.getString(t.this.i.getContentResolver(), "DEFAULT_PHONE_CONNECT_APP");
                    boolean zB = com.dofun.carsetting.f.a.a().b(string);
                    Log.d("PersonalFragment", "0xff01:packageName:" + string + " isHiCarAppInstall:" + zB);
                    if (!TextUtils.equals(string, "cn.cardoor.hicar") || !zB) {
                        t.this.m.setVisibility(8);
                    } else {
                        t.this.m.setVisibility(0);
                    }
                }
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
        View viewInflate = layoutInflater.inflate(R.layout.fragment_personal, (ViewGroup) null);
        this.f362a = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.i d() {
        return new com.dofun.carsetting.presenter.i(getActivity());
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.b = (InfoPreference) view.findViewById(R.id.ip_account);
        this.c = (InfoPreference) view.findViewById(R.id.ip_language);
        this.d = (InfoPreference) view.findViewById(R.id.input_method_settings);
        this.e = (InfoPreference) view.findViewById(R.id.ip_phone_connect);
        this.f = (InfoPreference) view.findViewById(R.id.ip_hi_car);
        this.g = (InfoPreference) view.findViewById(R.id.ip_hicar_screen_size);
        this.m = (LinearLayout) view.findViewById(R.id.ll_hi_car);
        this.k = (SwitchPreference) view.findViewById(R.id.therr_d_door);
        this.l = (InfoPreference) view.findViewById(R.id.ip_gesture_control);
        this.b.setOnPreferenceChangeListener(this);
        this.c.setOnPreferenceChangeListener(this);
        this.d.setOnPreferenceChangeListener(this);
        this.e.setOnPreferenceChangeListener(this);
        this.f.setOnPreferenceChangeListener(this);
        this.g.setOnPreferenceChangeListener(this);
        this.k.setOnPreferenceChangeListener(this);
        this.l.setOnPreferenceChangeListener(this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.b.T()) {
            this.e.setVisibility(8);
        }
        String string = Settings.System.getString(this.i.getContentResolver(), "DEFAULT_PHONE_CONNECT_APP");
        boolean zB = com.dofun.carsetting.f.a.a().b(string);
        Log.d("PersonalFragment", "initData:packageName:" + string + " isHiCarAppInstall:" + zB);
        if (TextUtils.equals(string, "cn.cardoor.hicar") && zB) {
            this.m.setVisibility(0);
        } else {
            this.m.setVisibility(8);
        }
        int[] iArrA = com.dofun.carsetting.f.p.a(getContext());
        if (iArrA[0] > iArrA[1]) {
            this.p = com.dofun.carsetting.f.m.c(getContext(), "hicar_" + iArrA[0] + "x" + iArrA[1] + "_screen_size_entries");
            this.q = com.dofun.carsetting.f.m.c(getContext(), "hicar_" + iArrA[0] + "x" + iArrA[1] + "_screen_size_values");
        } else {
            this.p = com.dofun.carsetting.f.m.c(getContext(), "hicar_" + iArrA[1] + "x" + iArrA[0] + "_screen_size_entries");
            this.q = com.dofun.carsetting.f.m.c(getContext(), "hicar_" + iArrA[1] + "x" + iArrA[0] + "_screen_size_values");
        }
        if (this.p == 0 || this.q == 0) {
            this.g.a(getResources().getStringArray(R.array.hicar_default_screen_size_entries), getResources().getStringArray(R.array.hicar_default_screen_size_values));
        } else {
            this.g.a(getResources().getStringArray(this.p), getResources().getStringArray(this.q));
        }
        String string2 = Settings.System.getString(this.i.getContentResolver(), "hicar_screen_size");
        if (!TextUtils.isEmpty(string2)) {
            if (this.g.a(string2) == -1) {
                this.g.setItemValue(getResources().getStringArray(this.q).length - 1);
            } else {
                this.g.setValue(string2);
            }
            this.g.setValueTip(string2);
            return;
        }
        this.g.setAllSaveValue(false);
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onResume() {
        super.onResume();
        String string = Settings.System.getString(this.i.getContentResolver(), "DEFAULT_PHONE_CONNECT_APP");
        boolean zB = com.dofun.carsetting.f.a.a().b(string);
        Log.d("PersonalFragment", "onResume:packageName:" + string + " isHiCarAppInstall:" + zB);
        if (TextUtils.equals(string, "cn.cardoor.hicar") && zB) {
            this.m.setVisibility(0);
        } else {
            this.m.setVisibility(8);
        }
        if (SystemProperties.getInt("persist.sf.forcerotation", -1) != 1) {
            this.k.setVisibility(8);
            this.k.setValue(false);
        } else {
            this.k.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_USE_CAR_DOOR_TYPE", 1) == 1);
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Log.d("PersonalFragment", "onHiddenChanged:");
        if (z) {
            return;
        }
        b();
    }

    @Override // android.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        Log.d("PersonalFragment", "setUserVisibleHint:" + z);
        if (z) {
            b();
        }
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // com.dofun.carsetting.fragment.ad, android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.n != null) {
            try {
                this.n.dismiss();
            } catch (Exception e) {
                Log.d("PersonalFragment", "onDestroy:" + e.getMessage());
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.input_method_settings /* 2131230908 */:
                Intent intent = new Intent("android.settings.INPUT_METHOD_SETTINGS");
                intent.setFlags(268435456);
                this.i.startActivity(intent);
                return false;
            case R.id.ip_account /* 2131230913 */:
                Intent intent2 = new Intent();
                if (com.dofun.carsetting.f.b.A()) {
                    intent2 = new Intent("android.intent.action.MAIN");
                    intent2.setClassName("com.android.settings", "com.android.settings.Settings$UserAndAccountDashboardActivity");
                } else {
                    intent2.setAction("android.settings.SYNC_SETTINGS");
                }
                intent2.setFlags(268435456);
                this.i.startActivity(intent2);
                return false;
            case R.id.ip_gesture_control /* 2131230921 */:
                if (this.n == null) {
                    this.n = new com.dofun.carsetting.widget.b(getActivity());
                }
                this.n.show();
                return false;
            case R.id.ip_hi_car /* 2131230922 */:
                try {
                    Intent intent3 = new Intent();
                    intent3.setFlags(268435456);
                    intent3.setClassName("cn.cardoor.hicar", "cn.cardoor.hicar.activity.DeviceManagerActivity");
                    this.i.startActivity(intent3);
                    break;
                } catch (Exception unused) {
                }
                return false;
            case R.id.ip_hicar_screen_size /* 2131230923 */:
                String str = (String) obj;
                if ("-1".equals(str)) {
                    if (this.o == null) {
                        this.o = new com.dofun.carsetting.c.d(getActivity());
                        this.o.a(this.i.getString(R.string.screen_size));
                        this.o.a("", 8194, false);
                        this.o.a(new com.dofun.carsetting.g.g() { // from class: com.dofun.carsetting.fragment.t.2
                            @Override // com.dofun.carsetting.g.g
                            public void a(EditTextPreference editTextPreference, Editable editable) {
                                String string = editable.toString();
                                if (TextUtils.isEmpty(string)) {
                                    return;
                                }
                                Settings.System.putString(t.this.i.getContentResolver(), "hicar_screen_size", string);
                                t.this.g.setValue(string);
                                t.this.g.setItemValue(t.this.getResources().getStringArray(t.this.q).length - 1);
                                t.this.g.setValueTip(string);
                                t.this.f362a.write(40730, 1, 0, "killall -9 cn.cardoor.hicar");
                            }
                        });
                        this.o.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.dofun.carsetting.fragment.t.3
                            @Override // android.content.DialogInterface.OnDismissListener
                            public void onDismiss(DialogInterface dialogInterface) {
                            }
                        });
                    }
                    this.g.c();
                    this.o.show();
                } else {
                    Settings.System.putString(this.i.getContentResolver(), "hicar_screen_size", str);
                    this.f362a.write(40730, 1, 0, "killall -9 cn.cardoor.hicar");
                }
                String string = Settings.System.getString(this.i.getContentResolver(), "hicar_screen_size");
                if (!TextUtils.isEmpty(string)) {
                    if (this.g.a(string) == -1) {
                        this.g.setItemValue(getResources().getStringArray(this.q).length - 1);
                    } else {
                        this.g.setValue(string);
                    }
                    this.g.setValueTip(string);
                }
                this.g.c();
                return false;
            case R.id.ip_language /* 2131230926 */:
                Intent intent4 = new Intent();
                intent4.setClassName("com.android.settings", "com.android.settings.Settings$LanguageAndInputSettingsActivity");
                intent4.setFlags(268435456);
                this.i.startActivity(intent4);
                return false;
            case R.id.ip_phone_connect /* 2131230934 */:
                h hVar = new h();
                Bundle bundle = new Bundle();
                bundle.putString("type", "CONNECT");
                hVar.setArguments(bundle);
                hVar.a(new h.a() { // from class: com.dofun.carsetting.fragment.t.1
                    @Override // com.dofun.carsetting.fragment.h.a
                    public void a(String str2, AppInfo appInfo) {
                        if (((str2.hashCode() == 1669334218 && str2.equals("CONNECT")) ? (byte) 0 : (byte) -1) != 0) {
                            return;
                        }
                        t.this.r.sendEmptyMessageDelayed(65281, 2000L);
                    }
                });
                ((MainActivity) getActivity()).a(hVar);
                return false;
            case R.id.therr_d_door /* 2131231259 */:
                Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_USE_CAR_DOOR_TYPE", ((Boolean) obj).booleanValue() ? 1 : 0);
                return false;
            default:
                return false;
        }
    }
}
