package com.sprd.engineermode.telephony;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class HspaSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int CLOSE_HSDPA = 2;
    private static final int CLOSE_HSUPA = 4;
    private static final int GET_HSPA_STATUS = 0;
    private static final String KEY_HSDPA = "hsdpa_setting";
    private static final String KEY_HSUPA = "hsupa_setting";
    private static final int OPEN_HSDPA = 1;
    private static final int OPEN_HSUPA = 3;
    private static final String TAG = "HspaSettingActivity";
    private Context mContext;
    private TwoStatePreference mHsdpa;
    private mHspaSettingHandler mHspaSettingHandler;
    private TwoStatePreference mHsupa;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_hspa_setting);
        this.mContext = this;
        this.mHsdpa = (EMSwitchPreference) findPreference(KEY_HSDPA);
        this.mHsdpa.setChecked(false);
        this.mHsdpa.setOnPreferenceChangeListener(this);
        this.mHsupa = (EMSwitchPreference) findPreference(KEY_HSUPA);
        this.mHsupa.setChecked(false);
        this.mHsupa.setOnPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHspaSettingHandler = new mHspaSettingHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    public void onResume() {
        TwoStatePreference twoStatePreference;
        TwoStatePreference twoStatePreference2 = this.mHsdpa;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled() && (twoStatePreference = this.mHsupa) != null && twoStatePreference.isEnabled()) {
            Message getHspaStatus = this.mHspaSettingHandler.obtainMessage(0);
            this.mHspaSettingHandler.sendMessage(getHspaStatus);
        }
        super.onResume();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mHspaSettingHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mHspaSettingHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        int logType;
        String key = preference.getKey();
        Log.d(TAG, "[change]onPreferenceChange  key=" + key);
        if (key == null) {
            return true;
        }
        TwoStatePreference twoStatePreference = this.mHsdpa;
        if (preference == twoStatePreference) {
            if (!twoStatePreference.isChecked()) {
                logType = 1;
            } else {
                logType = 2;
            }
        } else {
            TwoStatePreference twoStatePreference2 = this.mHsupa;
            if (preference != twoStatePreference2) {
                return true;
            }
            if (!twoStatePreference2.isChecked()) {
                logType = 3;
            } else {
                logType = 4;
            }
        }
        Message mes = this.mHspaSettingHandler.obtainMessage(logType);
        this.mHspaSettingHandler.sendMessage(mes);
        return true;
    }

    class mHspaSettingHandler extends Handler {
        public mHspaSettingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int mHsupaEnable;
            int mHsupaEnable2;
            int mHsdpaEnable;
            int mHsdpaEnable2;
            int i = msg.what;
            if (i == 0) {
                String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_GET_AT_HSPA, "atchannel0");
                Log.d(HspaSettingActivity.TAG, "HSPA Status: " + mStrTmp);
                if (mStrTmp == null || !mStrTmp.contains("OK")) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setEnabled(false);
                            HspaSettingActivity.this.mHsdpa.setSummary(R.string.feature_abnormal);
                            HspaSettingActivity.this.mHsupa.setEnabled(false);
                            HspaSettingActivity.this.mHsupa.setSummary(R.string.feature_abnormal);
                        }
                    });
                    return;
                }
                String[] str0 = mStrTmp.split("\n");
                String[] str1 = str0[0].split(":");
                String[] str2 = str1[1].split(",");
                int mHsdpaEnable3 = Integer.valueOf(str2[1].trim().substring(0, 1)).intValue();
                int mHsupaEnable3 = Integer.valueOf(str2[0].trim().substring(0, 1)).intValue();
                if (mHsdpaEnable3 == 1) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setChecked(true);
                        }
                    });
                }
                if (mHsupaEnable3 == 1) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsupa.setChecked(true);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 1) {
                if (HspaSettingActivity.this.mHsupa.isChecked()) {
                    mHsupaEnable = 1;
                } else {
                    mHsupaEnable = 0;
                }
                String mATCmd = engconstents.ENG_SET_AT_HSPA + mHsupaEnable + ",1";
                String mStrTmp2 = IATUtils.sendATCmd(mATCmd, "atchannel0");
                Log.d(HspaSettingActivity.TAG, "OPEN_HSDPA" + mStrTmp2);
                if (mStrTmp2 == null || !mStrTmp2.contains("OK")) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setChecked(false);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Open HSDPA Fail", 0).show();
                        }
                    });
                    return;
                } else {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setChecked(true);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Open HSDPA Success", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                if (HspaSettingActivity.this.mHsupa.isChecked()) {
                    mHsupaEnable2 = 1;
                } else {
                    mHsupaEnable2 = 0;
                }
                String mATCmd2 = engconstents.ENG_SET_AT_HSPA + mHsupaEnable2 + ",0";
                String mStrTmp3 = IATUtils.sendATCmd(mATCmd2, "atchannel0");
                Log.d(HspaSettingActivity.TAG, "CLOSE_HSDPA" + mStrTmp3);
                if (mStrTmp3 == null || !mStrTmp3.contains("OK")) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setChecked(true);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Close HSDPA Fail", 0).show();
                        }
                    });
                    return;
                } else {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsdpa.setChecked(false);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Close HSDPA Success", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 3) {
                if (HspaSettingActivity.this.mHsdpa.isChecked()) {
                    mHsdpaEnable = 1;
                } else {
                    mHsdpaEnable = 0;
                }
                String mATCmd3 = engconstents.ENG_SET_AT_HSPA + "1," + mHsdpaEnable;
                String mStrTmp4 = IATUtils.sendATCmd(mATCmd3, "atchannel0");
                Log.d(HspaSettingActivity.TAG, "OPEN_HSUPA" + mStrTmp4);
                if (mStrTmp4 == null || !mStrTmp4.contains("OK")) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.9
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsupa.setChecked(false);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Open HSUPA Fail", 0).show();
                        }
                    });
                    return;
                } else {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.8
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsupa.setChecked(true);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Open HSUPA Success", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 4) {
                if (HspaSettingActivity.this.mHsdpa.isChecked()) {
                    mHsdpaEnable2 = 1;
                } else {
                    mHsdpaEnable2 = 0;
                }
                String mATCmd4 = engconstents.ENG_SET_AT_HSPA + "0," + mHsdpaEnable2;
                String mStrTmp5 = IATUtils.sendATCmd(mATCmd4, "atchannel0");
                Log.d(HspaSettingActivity.TAG, "CLOSE_HSUPA" + mStrTmp5);
                if (mStrTmp5 == null || !mStrTmp5.contains("OK")) {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.11
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsupa.setChecked(true);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Close HSUPA Fail", 0).show();
                        }
                    });
                } else {
                    HspaSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.HspaSettingActivity.mHspaSettingHandler.10
                        @Override // java.lang.Runnable
                        public void run() {
                            HspaSettingActivity.this.mHsupa.setChecked(false);
                            Toast.makeText(HspaSettingActivity.this.mContext, "Close HSUPA Success", 0).show();
                        }
                    });
                }
            }
        }
    }
}
