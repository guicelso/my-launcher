package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.SimSelectHelper;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class SimSelectHelperActivity extends PreferenceActivity {
    public static final String INTENT_SIM_INDEX = "simindex";
    private static final String SIM = "SIM";
    private static final String TAG = "SimSelectHelperActivity";
    private int mPhoneCount;
    private Preference[] mSIMPref;
    private TelephonyManager mTelephonyManager;
    PreferenceGroup mPreGroup = null;
    private Intent mIntentForStart = null;
    private boolean mAutoDisable = false;
    private Runnable mOnChange = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mSIMPref = new Preference[this.mPhoneCount];
        this.mIntentForStart = SimSelectHelper.getIntentForStart();
        this.mAutoDisable = SimSelectHelper.isAutoDisable();
        this.mOnChange = SimSelectHelper.getOnChangeCB();
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mSIMPref[i] = new Preference(this);
            this.mSIMPref[i].setTitle(SIM + i);
            this.mSIMPref[i].setKey(SIM + i);
            this.mPreGroup.addPreference(this.mSIMPref[i]);
            this.mTelephonyManager = TelephonyManagerProxy.getService();
            if (this.mAutoDisable) {
                TelephonyManager telephonyManager = this.mTelephonyManager;
                if (telephonyManager != null && telephonyManager.getSimState(i) == 5) {
                    this.mSIMPref[i].setEnabled(true);
                } else {
                    this.mSIMPref[i].setEnabled(false);
                    this.mSIMPref[i].setSummary(R.string.input_card_to_test);
                }
            }
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        Runnable runnable = this.mOnChange;
        if (runnable != null) {
            runnable.run();
        }
        super.onResume();
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        String[] temp = key.split("M");
        int simIndex = Integer.valueOf(temp[1]).intValue();
        this.mIntentForStart.putExtra(INTENT_SIM_INDEX, simIndex);
        startActivity(this.mIntentForStart);
        return false;
    }
}
