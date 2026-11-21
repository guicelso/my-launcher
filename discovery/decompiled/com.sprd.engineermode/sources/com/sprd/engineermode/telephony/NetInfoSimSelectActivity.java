package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoSimSelectActivity extends PreferenceActivity {
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String SIM = "SIM";
    private static final String TAG = "NetInfoSimSelect";
    private int mPhoneCount;
    private PreferenceGroup mPreGroup = null;
    private Preference[] mSIMPref;
    private TelephonyManager mTelephonyManager;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        Log.d(TAG, "SIM counts is :" + this.mPhoneCount);
        this.mSIMPref = new Preference[this.mPhoneCount];
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mSIMPref[i] = new Preference(this);
            this.mSIMPref[i].setTitle(SIM + i);
            this.mSIMPref[i].setKey(SIM + i);
            this.mPreGroup.addPreference(this.mSIMPref[i]);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (TelephonyManagerProxy.getSimState(i) != 5) {
                this.mSIMPref[i].setEnabled(false);
                this.mSIMPref[i].setSummary(R.string.input_sim__warn);
            } else {
                this.mSIMPref[i].setEnabled(true);
            }
        }
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        int simIndex = Integer.valueOf(key.substring(3, 4)).intValue();
        Intent intent = new Intent("android.engineermode.action.UE_NW");
        intent.putExtra("simindex", simIndex);
        startActivity(intent);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
