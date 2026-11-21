package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.CommonUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AppSettingsPrefActivity extends PreferenceActivity {
    public static final String CUSTOM_USER_AGENT_STRING = "custom_user_agent_string";
    public static final String OTHER_USER_AGENT_STRING = "other_user_agent_string";
    public static final int REQUEST_UA_SETTINGS = 1;
    private static final String TAG = "AppSettingsPrefActivity";
    private static final String UA_SETTING = "ua_setting";
    public static final String USER_AGENT_CHOICE = "user_agent_choice";
    private Preference mUAsetting;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_appsetting);
        this.mUAsetting = findPreference(UA_SETTING);
        if (!checkApkExist("com.sprd.uasetting")) {
            this.mUAsetting.setEnabled(false);
            this.mUAsetting.setSummary(R.string.apk_not_exist);
        }
        this.mUAsetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.telephony.AppSettingsPrefActivity.1
            @Override // android.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(Preference preference) {
                if (!CommonUtils.isCurrentUserDeviceOwner()) {
                    Toast.makeText(AppSettingsPrefActivity.this, R.string.not_support_visitor_or_user_mode, 0).show();
                    return false;
                }
                Intent intent = new Intent();
                intent.setClassName("com.sprd.uasetting", "com.sprd.uasetting.UASettingActivity");
                AppSettingsPrefActivity.this.startActivityForResult(intent, 1);
                return false;
            }
        });
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult");
        Log.d(TAG, "resultCode: " + resultCode + "requestCode: " + requestCode + "RESULT_OK:-1");
        if (resultCode == -1) {
            if (requestCode != 1 || intent == null) {
                return;
            }
            String customUaString = intent.getStringExtra(CUSTOM_USER_AGENT_STRING);
            if (!TextUtils.isEmpty(customUaString)) {
                Settings.System.putString(getContentResolver(), CUSTOM_USER_AGENT_STRING, customUaString);
            }
            int uaChoice = intent.getIntExtra(USER_AGENT_CHOICE, -1);
            if (uaChoice != -1) {
                Settings.System.putInt(getContentResolver(), USER_AGENT_CHOICE, uaChoice);
            }
            String otherUaString = intent.getStringExtra(OTHER_USER_AGENT_STRING);
            if (!TextUtils.isEmpty(otherUaString)) {
                Settings.System.putString(getContentResolver(), OTHER_USER_AGENT_STRING, otherUaString);
                return;
            }
            return;
        }
        Log.d(TAG, "onActivityResult,resultCode: " + resultCode);
    }

    public boolean checkApkExist(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            getPackageManager().getApplicationInfo(packageName, 8192);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
