package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.SimSelectHelper;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PSRelatedPrefActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    private static final String AOCSETTING = "aocsetting";
    private static final String AUTOPOWERON = "autoattach_value";
    private static final String C2K_FREQUENCY = "c2k_frequency";
    private static final int GET_AUTO_ATTACH = 1;
    private static final int GET_PDPSTATE = 3;
    private static final int GET_SIM_INFO = 9;
    private static final int GET_SMSERVER = 5;
    private static final int GET_TIMECONF = 7;
    private static final String GSM_FREQUENCY = "gsm_frequency";
    private static final String HSPA_SETTING = "hspa_setting";
    private static final String OTHERS_FREQUENCY = "others_frequency";
    private static final String PDPACT = "pdpact";
    private static final int SET_AUTO_ATTACH = 2;
    private static final int SET_PDPSTATE = 4;
    private static final int SET_SIM_INFO = 10;
    private static final int SET_SMSERVER = 6;
    private static final int SET_TIMECONF = 8;
    private static final int SET_UNLOCKCELL = 15;
    private static final String SIMINFO = "siminfo";
    private static final String SMSSERVER = "smsserver";
    private static final int STATUS_OFF = 0;
    private static final int STATUS_ON = 1;
    private static final String TAG = "PSRelatedPrefActivity";
    private static final String TIMECONFLICT = "timeslotconflict";
    private static final String UNLOCKCELL = "unlockcell";
    private static final boolean debug = true;
    private EMSwitchPreference mAutoPowrPref;
    private Preference mHspa;
    private mPsRelativeHandler mPsRelativeHandler;
    private Preference mSimInfoPref;
    private ListPreference mSmsServerPref;
    private int mState;
    private EMSwitchPreference mTimeConflictPref;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_psrelative);
        this.mHspa = findPreference(HSPA_SETTING);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mPsRelativeHandler = new mPsRelativeHandler(ht.getLooper());
        SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        defaultPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    private boolean isCardExist() {
        int phoneCount = TelephonyManagerProxy.getPhoneCount();
        int cardExistNum = 0;
        TelephonyManager telephonyManager = TelephonyManagerProxy.getService();
        for (int i = 0; i < phoneCount; i++) {
            if (telephonyManager != null && telephonyManager.getSimState(i) == 5) {
                cardExistNum++;
            }
        }
        if (cardExistNum >= 1) {
            return debug;
        }
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "[change]onPreferenceChange  key=" + key);
        return key == null ? debug : debug;
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d(TAG, "[TreeCllik]onPreferenceTreeClick  key=" + key);
        int logType = 0;
        if (key == null) {
            return debug;
        }
        if (key.equals(SIMINFO)) {
            logType = 9;
        } else if (key.equals(SMSSERVER)) {
            logType = 5;
        } else if (key.equals(UNLOCKCELL)) {
            logType = 15;
        } else if (key.equals(AOCSETTING)) {
            Intent intent = new Intent(this, (Class<?>) AOCSettingsPrefActivity.class);
            SimSelectHelper.startSimSelect(intent, debug, null, this);
        } else if (key.equals(GSM_FREQUENCY)) {
            Intent intent2 = new Intent("android.engineermode.action.GSMFREQUENCY");
            startActivity(intent2);
        } else if (key.equals(OTHERS_FREQUENCY)) {
            Intent intent3 = new Intent("android.engineermode.action.LOCKFREQUENCY");
            startActivity(intent3);
        } else if (key.equals(C2K_FREQUENCY)) {
            Intent intent4 = new Intent("android.engineermode.action.LOCKC2KFREQUENCY");
            startActivity(intent4);
        } else {
            return false;
        }
        Message m = this.mPsRelativeHandler.obtainMessage(logType);
        this.mPsRelativeHandler.sendMessage(m);
        return debug;
    }

    private String analysisResponse(String response, int type) {
        if (response.contains("OK")) {
            if (type != 1) {
                if (type != 2) {
                    if (type == 3) {
                        String[] str = response.split("\n");
                        String[] str1 = str[0].split(",");
                        Log.d(TAG, type + "  " + str1[1]);
                        return str1[1].trim();
                    }
                    if (type != 5) {
                        if (type != 6) {
                            if (type == 7) {
                                String[] str2 = response.split("\n");
                                String[] str3 = str2[0].split(",");
                                for (String str4 : str3) {
                                    Log.d(TAG, type + "  " + str4);
                                }
                                Log.d(TAG, type + "  " + str3[2]);
                                return str3[2].trim();
                            }
                            if (type != 8 && type != 15) {
                                return IATUtils.AT_FAIL;
                            }
                        }
                    }
                }
                return "OK";
            }
            String[] str5 = response.split("\n");
            String[] str6 = str5[0].split(":");
            Log.d(TAG, type + "  " + str6[1]);
            return str6[1].trim();
        }
        return IATUtils.AT_FAIL;
    }

    class mPsRelativeHandler extends Handler {
        public mPsRelativeHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
        }
    }

    class PSRelativeATFailRunnable implements Runnable {
        private int arg1;
        private Object obj;

        public PSRelativeATFailRunnable(Message msgFailed) {
            this.arg1 = 0;
            this.obj = null;
            this.arg1 = msgFailed.arg1;
            this.obj = msgFailed.obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(PSRelatedPrefActivity.this, "Fail", 0).show();
            if (this.obj instanceof EMSwitchPreference) {
                boolean resetValue = this.arg1 == 0;
                ((EMSwitchPreference) this.obj).setChecked(resetValue);
                ((EMSwitchPreference) this.obj).setEnabled(PSRelatedPrefActivity.debug);
            }
        }
    }

    class PSRelativeATSuccessRunnable implements Runnable {
        private int arg1;
        private Object obj;

        public PSRelativeATSuccessRunnable(Message msgSuccessed) {
            this.arg1 = 0;
            this.obj = null;
            this.arg1 = msgSuccessed.arg1;
            this.obj = msgSuccessed.obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(PSRelatedPrefActivity.this, "Success", 0).show();
            Object obj = this.obj;
            if (obj instanceof EMSwitchPreference) {
                ((EMSwitchPreference) obj).setEnabled(PSRelatedPrefActivity.debug);
            }
        }
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mPsRelativeHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mPsRelativeHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
