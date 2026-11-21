package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.IThermal;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class ThermalSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String IPA_PATH = "/sys/class/thermal/thermal_zone0/thm_enable";
    private static final String KEY_IPA = "ipa";
    public static final String KEY_THERMAL_CHARGE_SWITCH = "thermal_charge_switch";
    private static final String KEY_THERMAL_PA_SWITCH = "thermal_pa_switch";
    private static final String KEY_THERMAL_SWITCH = "thermal_switch";
    private static final String TAG = "ThermalSettingActivity";
    private EMSwitchPreference mIpa;
    private EMSwitchPreference mThermal;
    private EMSwitchPreference mThermalCharge;
    private EMSwitchPreference mThermalPa;
    private IThermal thermal = CoreApi.getHardwareApi().thermalApi();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_thermal_settings);
        this.mThermal = (EMSwitchPreference) findPreference(KEY_THERMAL_SWITCH);
        this.mThermal.setOnPreferenceChangeListener(this);
        this.mThermalPa = (EMSwitchPreference) findPreference(KEY_THERMAL_PA_SWITCH);
        this.mThermalPa.setOnPreferenceChangeListener(this);
        this.mThermalCharge = (EMSwitchPreference) findPreference(KEY_THERMAL_CHARGE_SWITCH);
        this.mThermalCharge.setOnPreferenceChangeListener(this);
        this.mIpa = (EMSwitchPreference) findPreference(KEY_IPA);
        this.mIpa.setOnPreferenceChangeListener(this);
        File file = new File(IPA_PATH);
        if (!file.exists()) {
            this.mIpa.setEnabled(false);
            this.mIpa.setSummary(R.string.feature_not_support);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        updateThermalStatus();
        updateThermalPaStatus();
        updateThermalChargeStatus();
        updateThermalIpaStatus();
    }

    public void updateThermalStatus() {
        try {
            boolean state = this.thermal.getThermalState();
            Log.d(TAG, " thermal state:  " + state);
            if (this.mThermal.isChecked() != state) {
                this.mThermal.setChecked(state);
            } else {
                Log.w(TAG, "isCheck is same as state, maybe set failed");
            }
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    public void updateThermalPaStatus() {
        Log.d(TAG, "updateThermalPaStatus");
        try {
            boolean state = this.thermal.getThermalPaState();
            Log.d(TAG, "thermal pa state:  " + state);
            if (this.mThermalPa.isChecked() == state) {
                Log.w(TAG, "pa isCheck is same as state, maybe set failed");
            } else {
                this.mThermalPa.setChecked(state);
            }
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void updateThermalChargeStatus() {
        Log.d(TAG, "updateThermalChargeStatus");
        try {
            boolean state = this.thermal.getThermalChargeState();
            Log.d(TAG, "thermal charge state: " + state);
            if (this.mThermalCharge.isChecked() == state) {
                Log.w(TAG, "charge isCheck is same as state, maybe set failed");
            } else {
                this.mThermalCharge.setChecked(state);
            }
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void updateThermalIpaStatus() {
        Log.d(TAG, "updateThermalIpaStatus");
        if (!this.mIpa.isEnabled()) {
            return;
        }
        try {
            boolean state = this.thermal.getThermalIpaState();
            Log.d(TAG, "thermal ipa state: " + state);
            this.mIpa.setChecked(state);
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void onThermalPreferenceChange() {
        boolean checked = this.mThermal.isChecked();
        try {
            if (checked) {
                this.thermal.switchThermalOff();
            } else {
                this.thermal.switchThermalOn();
            }
            updateThermalStatus();
            updateThermalPaStatus();
            updateThermalChargeStatus();
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void onThermalPaPreferenceChange() {
        try {
            if (this.mThermalPa.isChecked()) {
                this.thermal.switchThermalPaOff();
            } else {
                this.thermal.switchThermalPaOn();
            }
            updateThermalPaStatus();
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void onThermalChargePreferenceChange() {
        try {
            if (this.mThermalCharge.isChecked()) {
                this.thermal.switchThermalChargeOff();
            } else {
                this.thermal.switchThermalChargeOn();
            }
            updateThermalChargeStatus();
        } catch (EmException e) {
            e.printStackTrace();
        }
    }

    private void onThermalIpaPreferenceChange() {
        try {
            if (this.mIpa.isChecked()) {
                this.thermal.switchThermalIpaOff();
            } else {
                this.thermal.switchThermalIpaOn();
            }
        } catch (EmException e) {
            e.printStackTrace();
        }
        updateThermalIpaStatus();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        if (pref == this.mThermal) {
            onThermalPreferenceChange();
            return false;
        }
        if (pref == this.mThermalPa) {
            onThermalPaPreferenceChange();
            return false;
        }
        if (pref == this.mThermalCharge) {
            onThermalChargePreferenceChange();
            return false;
        }
        if (pref == this.mIpa) {
            onThermalIpaPreferenceChange();
            return false;
        }
        return false;
    }
}
