package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class AndroidUtilsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_STARTING_WINDOW = "startingwindow";
    private static final String STARTING_WINDOW__ENABLED = "persist.sys.startingwindow";
    private static final String TAG = "AndroidUtilsActivity";
    private TwoStatePreference mStartingWindow;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_android_utils);
        this.mStartingWindow = (TwoStatePreference) findPreference(KEY_STARTING_WINDOW);
        this.mStartingWindow.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        TwoStatePreference twoStatePreference = this.mStartingWindow;
        if (twoStatePreference != null) {
            twoStatePreference.setChecked(SystemPropertiesProxy.getBoolean(STARTING_WINDOW__ENABLED, false));
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object keyValue) throws IllegalAccessException, InvocationTargetException {
        if (preference == this.mStartingWindow) {
            SystemPropertiesProxy.set(STARTING_WINDOW__ENABLED, Boolean.toString(((Boolean) keyValue).booleanValue()));
            return true;
        }
        return false;
    }

    public String readFile(String path) {
        File file = new File(path);
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(file));
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "Read file error!!!");
                    sb.append("readError");
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.d(TAG, "read " + path + " value is " + sb.toString().trim());
            return sb.toString().trim();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }
}
