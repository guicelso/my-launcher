package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class BluetoothActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_SPEED_LIMIT = "speed_limit";
    private static final String TAG = "BluetoothActivity";
    private TwoStatePreference mSpeed_limit;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws IllegalAccessException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_cmcc_bluetooth);
        this.mSpeed_limit = (TwoStatePreference) findPreference(KEY_SPEED_LIMIT);
        int lowSpeed = SystemPropertiesProxy.getInt("persist.sys.bt.lowspeed", 110);
        Log.d(TAG, "lowSpeed=" + lowSpeed);
        if (lowSpeed > 0) {
            this.mSpeed_limit.setChecked(true);
        } else {
            this.mSpeed_limit.setChecked(false);
        }
        this.mSpeed_limit.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) throws IllegalAccessException, InvocationTargetException {
        if (pref == this.mSpeed_limit) {
            Log.d(TAG, "mSpeed_limit change ");
            if (this.mSpeed_limit.isChecked()) {
                SystemPropertiesProxy.set("persist.sys.bt.lowspeed", "0");
                Log.d(TAG, "SET_SPEED_LIMIT: set prop is " + SystemPropertiesProxy.get("persist.sys.bt.lowspeed"));
                return true;
            }
            SystemPropertiesProxy.set("persist.sys.bt.lowspeed", "110");
            Log.d(TAG, "SET_SPEED_LIMIT: set prop is " + SystemPropertiesProxy.get("persist.sys.bt.lowspeed"));
            return true;
        }
        return false;
    }
}
