package com.dofun.carsetting.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dofun.carsetting.R;
import com.dofun.carsetting.app.MyApp;
import com.dofun.carsetting.widget.InfoPreference;
import com.dofun.carsetting.widget.MultiCheckPreference;
import com.dofun.carsetting.widget.SwitchPreference;
import com.dofun.carsetting.widget.TwPreference;

/* JADX INFO: compiled from: OriginalCarFragment.java */
/* JADX INFO: loaded from: classes.dex */
public class q extends ad<com.dofun.carsetting.presenter.h> implements com.dofun.carsetting.g.j, com.dofun.carsetting.g.l, com.dofun.carsetting.g.m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private SwitchPreference f331a;
    private SwitchPreference b;
    private SwitchPreference c;
    private SwitchPreference d;
    private InfoPreference e;
    private InfoPreference f;
    private InfoPreference g;
    private MultiCheckPreference k;
    private com.dofun.carsetting.f.u l;

    @Override // com.dofun.carsetting.fragment.ad
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.dofun.carsetting.presenter.h d() {
        return null;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_original_car, (ViewGroup) null);
        this.l = com.dofun.carsetting.f.u.a();
        return viewInflate;
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void a(View view) {
        this.f = (InfoPreference) view.findViewById(R.id.tw_air_popup_window);
        this.f.setOnPreferenceChangeListener(this);
        this.g = (InfoPreference) view.findViewById(R.id.tw_air_style);
        this.g.setOnPreferenceChangeListener(this);
        if (com.dofun.carsetting.f.b.a().contains("X-WIN")) {
            this.g.setVisibility(0);
        }
        this.f331a = (SwitchPreference) view.findViewById(R.id.tw_show_temperature);
        this.f331a.setOnPreferenceChangeListener(this);
        this.b = (SwitchPreference) view.findViewById(R.id.disable_door);
        this.b.setOnPreferenceChangeListener(this);
        this.c = (SwitchPreference) view.findViewById(R.id.system_fradar);
        this.c.setOnPreferenceChangeListener(this);
        this.c.setVisibility(8);
        this.d = (SwitchPreference) view.findViewById(R.id.reverse_front_radar);
        this.d.setOnPreferenceChangeListener(this);
        this.e = (InfoPreference) view.findViewById(R.id.key_wheel_title);
        this.e.setOnPreferenceChangeListener(this);
        this.k = (MultiCheckPreference) view.findViewById(R.id.temp_mode);
        this.k.a((com.dofun.carsetting.g.l) this);
    }

    @Override // com.dofun.carsetting.fragment.ad
    public void b() {
        if (com.dofun.carsetting.f.b.a().contains("-DH")) {
            this.f331a.setVisibility(8);
            this.k.setVisibility(8);
        }
        this.e.setVisibility(Settings.System.getInt(MyApp.a().getContentResolver(), "ISSHOW_WHEEL", 1) == 1 ? 0 : 8);
        this.f.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "AIR_POPUP_WINDOW", 1)));
        if (Settings.System.getInt(this.i.getContentResolver(), "air.style.scene", -1) == -1) {
            this.g.setValue(String.valueOf(!SystemProperties.getBoolean("air.style.scene", true) ? 1 : 0));
        } else {
            this.g.setValue(String.valueOf(Settings.System.getInt(this.i.getContentResolver(), "air.style.scene", -1)));
        }
        this.f331a.setValue(Settings.System.getInt(this.i.getContentResolver(), "SHOW_TEMPERATURE", 1) == 1);
        if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
            this.b.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_DOOR2", 0) == 0);
        } else {
            this.b.setValue(Settings.System.getInt(this.i.getContentResolver(), "SYSTEM_DOOR2", 1) == 1);
        }
        MultiCheckPreference multiCheckPreference = this.k;
        boolean[] zArr = new boolean[3];
        zArr[0] = Settings.System.getInt(this.i.getContentResolver(), "TEMP_MODE", 0) == 0;
        zArr[1] = Settings.System.getInt(this.i.getContentResolver(), "TEMP_MODE", 0) == 1;
        zArr[2] = Settings.System.getInt(this.i.getContentResolver(), "TEMP_CONVERT", 0) == 1;
        multiCheckPreference.setTvValue(zArr);
        if (com.dofun.carsetting.f.b.a().contains("BMW")) {
            this.c.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FRADAR", 1)).intValue() == 0);
        } else {
            this.c.setValue(Integer.valueOf(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FRADAR", 0)).intValue() == 0);
        }
        this.d.setValue(Settings.System.getInt(getActivity().getContentResolver(), "SYSTEM_FRONT_FRADAR", 0) == 0);
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
        if (z) {
            b();
        }
    }

    @Override // com.dofun.carsetting.g.j
    public boolean a(TwPreference twPreference, Object obj) {
        switch (twPreference.getId()) {
            case R.id.disable_door /* 2131230855 */:
                if (com.dofun.carsetting.f.b.f() || com.dofun.carsetting.f.b.F() || com.dofun.carsetting.f.b.G()) {
                    Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_DOOR2", !((Boolean) obj).booleanValue() ? 1 : 0);
                } else {
                    Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_DOOR2", ((Boolean) obj).booleanValue() ? 1 : 0);
                }
                break;
            case R.id.key_wheel_title /* 2131230983 */:
                try {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName("com.tw.keypad", "com.tw.keypad.SteeringWheelActivity");
                        startActivity(intent);
                    } catch (Exception unused) {
                        Intent intent2 = new Intent();
                        intent2.setClassName("com.android.settings", "com.android.settings.KeypadActivity");
                        startActivity(intent2);
                        return false;
                    }
                } catch (Exception unused2) {
                    return false;
                }
                break;
            case R.id.reverse_front_radar /* 2131231146 */:
                Boolean bool = (Boolean) obj;
                SystemProperties.set("persist.can.fradar", bool.booleanValue() ? "0" : "1");
                Settings.System.putInt(getActivity().getContentResolver(), "SYSTEM_FRONT_FRADAR", !bool.booleanValue() ? 1 : 0);
                break;
            case R.id.system_fradar /* 2131231236 */:
                Boolean bool2 = (Boolean) obj;
                Settings.System.putInt(this.i.getContentResolver(), "SYSTEM_FRADAR", !bool2.booleanValue() ? 1 : 0);
                SystemProperties.set("persist.can.radar", String.valueOf(!bool2.booleanValue() ? 1 : 0));
                break;
            case R.id.tw_air_popup_window /* 2131231340 */:
                Settings.System.putInt(this.i.getContentResolver(), "AIR_POPUP_WINDOW", Integer.valueOf((String) obj).intValue());
                break;
            case R.id.tw_air_style /* 2131231341 */:
                Settings.System.putInt(this.i.getContentResolver(), "air.style.scene", Integer.valueOf((String) obj).intValue());
                break;
            case R.id.tw_show_temperature /* 2131231368 */:
                Settings.System.putInt(this.i.getContentResolver(), "SHOW_TEMPERATURE", ((Boolean) obj).booleanValue() ? 1 : 0);
                this.l.write(1288, 255);
                break;
        }
        return false;
    }

    @Override // com.dofun.carsetting.g.l
    public void a(MultiCheckPreference multiCheckPreference, int i, boolean[] zArr) {
        if (multiCheckPreference.getId() == R.id.temp_mode) {
            Settings.System.putInt(this.i.getContentResolver(), "TEMP_MODE", !zArr[0] ? 1 : 0);
            Settings.System.putInt(this.i.getContentResolver(), "TEMP_CONVERT", zArr[2] ? 1 : 0);
        }
    }
}
