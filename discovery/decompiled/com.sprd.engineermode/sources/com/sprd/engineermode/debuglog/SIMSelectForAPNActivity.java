package com.sprd.engineermode.debuglog;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.telephony.TelephonyManager;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class SIMSelectForAPNActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    private static final String KEY_SELECT_SIM = "select_sim";
    private static final String KEY_SIM_PREFERENCE = "sim_";
    private static final String TAG = "SIMSelectForAPNActivity";
    private int mPhoneCount;
    PreferenceGroup mPreGroup = null;
    private Preference[] mSIMSelectPre;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mSIMSelectPre = new Preference[this.mPhoneCount];
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mSIMSelectPre[i] = new Preference(this);
            this.mSIMSelectPre[i].setTitle("SIM" + i);
            this.mSIMSelectPre[i].setKey(KEY_SIM_PREFERENCE + i);
            if (isCardExist(i)) {
                this.mSIMSelectPre[i].setEnabled(true);
                this.mSIMSelectPre[i].setOnPreferenceClickListener(this);
            } else {
                this.mSIMSelectPre[i].setEnabled(false);
            }
            this.mPreGroup.addPreference(this.mSIMSelectPre[i]);
        }
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (pref == this.mSIMSelectPre[i]) {
                Intent intent = new Intent();
                intent.putExtra(KEY_SELECT_SIM, i);
                intent.setComponent(new ComponentName("com.sprd.engineermode", "com.sprd.engineermode.debuglog.APNSettingActivity"));
                startActivity(intent);
            }
        }
        return true;
    }

    private boolean isCardExist(int phoneId) {
        TelephonyManager telephonyManager = TelephonyManagerProxy.getService();
        if (telephonyManager != null && telephonyManager.getSimState(phoneId) == 5) {
            return true;
        }
        return false;
    }
}
