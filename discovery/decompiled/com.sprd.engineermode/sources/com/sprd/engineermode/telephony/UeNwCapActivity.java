package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class UeNwCapActivity extends PreferenceActivity {
    private static final String KEY_NW_CAP = "nw_cap";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String KEY_UE_CAP = "ue_cap";
    private static final String TAG = "UeNwCapActivity";
    static int mSimIndex;
    private Preference mPreNwCap;
    private Preference mPreUeCap;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_ue_nw_cap);
        Intent intent = getIntent();
        mSimIndex = intent.getIntExtra("simindex", -1);
        Log.d(TAG, "mSimIndex: " + mSimIndex);
        this.mPreNwCap = findPreference(KEY_NW_CAP);
        this.mPreUeCap = findPreference(KEY_UE_CAP);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity
    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d(TAG, "key = " + key);
        if (key.equals(KEY_NW_CAP)) {
            Intent intent = new Intent("android.engineermode.action.NETINFOSIMNETWORKTYPE");
            intent.putExtra("simindex", mSimIndex);
            startActivity(intent);
        } else if (key.equals(KEY_UE_CAP)) {
            Intent intent1 = new Intent("android.engineermode.action.UECAP");
            intent1.putExtra("simindex", mSimIndex);
            startActivity(intent1);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
