package com.sprd.engineermode.debuglog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.hardware.PhaseCheckParse;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.utils.ShellUtils;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class MIPILogSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CHANNEL_CLOSE = "0";
    private static final String CHANNEL_TRANNING = "1";
    private static final String CHANNEL_WTL = "2";
    private static final String KEY_MIPI = "mipi_log";
    private static final String MIPI_LOG_FILE_PATH = "/sys/class/modem/debug-log/channel";
    private static final String PROPERTIES_MIPI_CHANNEL = "persist.sys.mipi.channel";
    private static final String TAG = "MIPILogSettingActivity";
    private ListPreference mListMIPILog;
    private SharedPreferences mSharePref;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_mipi_log);
        this.mListMIPILog = (ListPreference) findPreference(KEY_MIPI);
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mListMIPILog.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    public void onStart() throws IllegalAccessException, InvocationTargetException {
        Log.d(TAG, "onStart...");
        super.onStart();
        if (this.mListMIPILog != null) {
            if (isSupportMipi()) {
                String value = SystemPropertiesProxy.get(PROPERTIES_MIPI_CHANNEL, CHANNEL_CLOSE);
                this.mListMIPILog.setValueIndex(Integer.parseInt(value.trim()));
                ListPreference listPreference = this.mListMIPILog;
                listPreference.setSummary(listPreference.getEntry());
                return;
            }
            this.mListMIPILog.setEnabled(false);
            this.mListMIPILog.setSummary(R.string.feature_not_support);
        }
    }

    private boolean isSupportMipi() {
        boolean isSupport = false;
        boolean isExist = isFileExist();
        if (!Const.isUser() && isExist) {
            isSupport = true;
        }
        Log.d(TAG, "isExist: " + isExist + "isSupport: " + isSupport);
        return isSupport;
    }

    private boolean isFileExist() {
        File file = new File(MIPI_LOG_FILE_PATH);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    private boolean writeToFile(String str) {
        Log.d(TAG, "writeToFile: " + str);
        if (ShellUtils.writeToFile(MIPI_LOG_FILE_PATH, str)) {
            Log.d(TAG, "write success");
            return true;
        }
        Log.d(TAG, "write fail");
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String setValue = newValue.toString();
        Log.d(TAG, "setValueIndex: " + setValue);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.MIPILogSettingActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (new PhaseCheckParse().writeMIPISwitch(Integer.parseInt(setValue))) {
                    MIPILogSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.MIPILogSettingActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() throws IllegalAccessException, InvocationTargetException {
                            SystemPropertiesProxy.set(MIPILogSettingActivity.PROPERTIES_MIPI_CHANNEL, setValue);
                            MIPILogSettingActivity.this.mListMIPILog.setValueIndex(Integer.parseInt(setValue.trim()));
                            MIPILogSettingActivity.this.mListMIPILog.setSummary(MIPILogSettingActivity.this.mListMIPILog.getEntry());
                        }
                    });
                } else {
                    MIPILogSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.MIPILogSettingActivity.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            MIPILogSettingActivity.this.mListMIPILog.setValueIndex(Integer.parseInt(SystemPropertiesProxy.get(MIPILogSettingActivity.PROPERTIES_MIPI_CHANNEL, MIPILogSettingActivity.CHANNEL_CLOSE)));
                            MIPILogSettingActivity.this.mListMIPILog.setSummary(MIPILogSettingActivity.this.mListMIPILog.getEntry());
                            Toast.makeText(MIPILogSettingActivity.this, "set fail!", 0).show();
                        }
                    });
                }
            }
        }).start();
        return true;
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
