package com.sprd.engineermode.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import com.sprd.engineermode.R;

/* JADX INFO: loaded from: classes4.dex */
public class LocationFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private static final String KEY_Clock_Quality = "Clock_Quality";
    private static final String KEY_GNSS_Desense = "GNSS_Desense";
    private static final String KEY_GNSS_NoiseScan = "GNSS_NoiseScan";
    private static final String KEY_SGPS = "SGPS";
    private static final String TAG = "LocationFragment";
    private Preference mClockQuality;
    private Context mContext;
    private Preference mGNSSDesense;
    private Preference mGNSSNoiseScan;
    private Preference mSGPS;

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_location);
        this.mContext = getActivity();
        this.mClockQuality = findPreference(KEY_Clock_Quality);
        this.mClockQuality.setOnPreferenceClickListener(this);
        this.mGNSSDesense = findPreference(KEY_GNSS_Desense);
        this.mGNSSDesense.setOnPreferenceClickListener(this);
        this.mGNSSNoiseScan = findPreference(KEY_GNSS_NoiseScan);
        this.mGNSSNoiseScan.setOnPreferenceClickListener(this);
        this.mSGPS = findPreference(KEY_SGPS);
        this.mSGPS.setOnPreferenceClickListener(this);
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        ComponentName cn = null;
        try {
            Intent intent = new Intent();
            intent.putExtra("friendProcessName", this.mContext.getApplicationInfo().processName);
            intent.putExtra("friendPackageName", this.mContext.getPackageName());
            intent.setFlags(268435456);
            Log.d(TAG, pref.getKey());
            if (pref.getKey().equals(KEY_Clock_Quality)) {
                cn = new ComponentName("com.unisoc.gnsstool", "com.unisoc.gnsstool.ClockQualityActivity");
            } else if (pref.getKey().equals(KEY_GNSS_Desense)) {
                cn = new ComponentName("com.unisoc.gnsstool", "com.unisoc.gnsstool.GNSSDesenseActivity");
            } else if (pref.getKey().equals(KEY_GNSS_NoiseScan)) {
                cn = new ComponentName("com.unisoc.gnsstool", "com.unisoc.gnsstool.NoiseScanActivity");
            } else if (pref.getKey().equals(KEY_SGPS)) {
                cn = new ComponentName("com.spreadtrum.sgps", "com.spreadtrum.sgps.SgpsActivity");
            }
            intent.setComponent(cn);
            this.mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return false;
        }
    }
}
