package com.sprd.engineermode.debuglog;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class BinEnableSimSeleActivity extends PreferenceActivity {
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String SIM = "SIM";
    private static final String TAG = "BinEnableSimSeleActivity";
    private int mPhoneCount;
    PreferenceGroup mPreGroup = null;
    private Preference[] mSIMPref;
    private TelephonyManager mTelephonyManager;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mSIMPref = new Preference[this.mPhoneCount];
        this.mTelephonyManager = TelephonyManagerProxy.getService();
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mSIMPref[i] = new Preference(this);
            this.mSIMPref[i].setTitle(SIM + i);
            this.mSIMPref[i].setKey(SIM + i);
            this.mPreGroup.addPreference(this.mSIMPref[i]);
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null && telephonyManager.getSimState(i) == 5) {
                this.mSIMPref[i].setEnabled(true);
            } else {
                this.mSIMPref[i].setEnabled(false);
                this.mSIMPref[i].setSummary(R.string.input_card_to_test);
            }
        }
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (key.startsWith(SIM)) {
            int simIndex = Integer.valueOf(key.substring(3, 4)).intValue();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(new ComponentName("com.android.phone", "com.sprd.phone.BinEnableActivity"));
            intent.putExtra("simindex", simIndex);
            startActivity(intent);
            return false;
        }
        return false;
    }
}
