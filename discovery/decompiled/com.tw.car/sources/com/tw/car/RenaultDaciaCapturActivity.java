package com.tw.car;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import c.b.a.g;
import c.b.a.h;

/* JADX INFO: loaded from: classes.dex */
public class RenaultDaciaCapturActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public h f469a = null;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public String f470b = "renault_parking_auxiliary";

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public String f471c = "renault_language";
    public String d = "car_configuration_walkaway_cdosing_sound";
    public String e = "car_configuration_approach_unlocking";
    public String f = "car_configuration_hands_freekeyfunctions";
    public String g = "car_configuration_autodoorlock_when_driving";
    public String h = "renault_vehicle_selection";
    public ListPreference i = null;
    public ListPreference j = null;
    public SwitchPreference k = null;
    public SwitchPreference l = null;
    public SwitchPreference m = null;
    public SwitchPreference n = null;
    public ListPreference o = null;
    public Handler mHandler = new g(this);

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startService(new Intent(this, (Class<?>) CarService.class));
        addPreferencesFromResource(R.xml.renault_dacia_captur);
        this.f469a = h.a();
        this.f469a.addHandler("RenaultDaciaCapturActivity", this.mHandler);
        this.i = (ListPreference) findPreference(this.f470b);
        this.i.setOnPreferenceChangeListener(this);
        this.o = (ListPreference) findPreference(this.h);
        this.o.setOnPreferenceChangeListener(this);
        this.j = (ListPreference) findPreference(this.f471c);
        this.j.setOnPreferenceChangeListener(this);
        this.k = (SwitchPreference) findPreference(this.d);
        this.l = (SwitchPreference) findPreference(this.e);
        this.m = (SwitchPreference) findPreference(this.f);
        this.n = (SwitchPreference) findPreference(this.g);
        this.k.setOnPreferenceChangeListener(this);
        this.l.setOnPreferenceChangeListener(this);
        this.m.setOnPreferenceChangeListener(this);
        this.n.setOnPreferenceChangeListener(this);
        this.f469a.write(266, 255);
        this.f469a.write(267, 255, 0);
        try {
            Thread.sleep(30L);
        } catch (Exception unused) {
        }
        this.f469a.a(144, 37, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int i;
        h hVar;
        int iBooleanValue;
        h hVar2;
        h hVar3;
        if (preference.getKey().equals(this.h)) {
            this.f469a.write(267, 12, Integer.parseInt((String) obj));
        } else {
            if (preference.getKey().equals(this.f470b)) {
                i = 1;
                hVar3 = this.f469a;
            } else if (preference.getKey().equals(this.f471c)) {
                i = 3;
                hVar3 = this.f469a;
            } else {
                if (preference.getKey().equals(this.d)) {
                    i = 82;
                    hVar = this.f469a;
                } else if (preference.getKey().equals(this.e)) {
                    i = 35;
                    hVar = this.f469a;
                } else if (preference.getKey().equals(this.f)) {
                    i = 34;
                    hVar = this.f469a;
                } else if (preference.getKey().equals(this.g)) {
                    i = 33;
                    hVar = this.f469a;
                }
                hVar2 = hVar;
                iBooleanValue = Boolean.valueOf((String) obj).booleanValue();
                hVar2.a(198, i, iBooleanValue, 0);
            }
            hVar2 = hVar3;
            iBooleanValue = Integer.parseInt((String) obj);
            hVar2.a(198, i, iBooleanValue, 0);
        }
        return false;
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        Intent intent;
        if (preference.getKey().equals("fuel_mileage_key")) {
            intent = new Intent(this, (Class<?>) DaciaFuelActivity.class);
        } else {
            if (!preference.getKey().equals("sound_control")) {
                return false;
            }
            intent = new Intent(this, (Class<?>) EQActivity.class);
        }
        startActivity(intent);
        return false;
    }
}
