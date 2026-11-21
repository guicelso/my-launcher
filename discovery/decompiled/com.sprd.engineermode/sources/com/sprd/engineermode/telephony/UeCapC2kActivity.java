package com.sprd.engineermode.telephony;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapC2kActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int GET_C2K_UE_CAP = 0;
    private static final String KEY_BAND_CLASS = "band_class";
    private static final String KEY_BAND_SUB_CLASS = "band_sub_class";
    private static final String KEY_CLASS1_SUPPORTED = "rcclass1_supported";
    private static final String KEY_CLASS2_SUPPORTED = "rcclass2_supported";
    private static final String KEY_CLASS3_SUPPORTED = "rcclass3_supported";
    private static final String KEY_QPCH_SUPPORTED = "qpch_supported";
    private static final String KEY_RATE1M8_SUPPORTED = "rate1m8_supported";
    private static final String KEY_SR1_SUPPORTED = "sr1_supported";
    private static final String KEY_SR3_SUPPORTED = "sr3_supported";
    private static final String TAG = "UeCapC2kActivity";
    private SwitchPreference mBandClass;
    private SwitchPreference mBandSubClass;
    private MyHandler mHandler;
    private SwitchPreference mQpchSupported;
    private SwitchPreference mRate1m8Supported;
    private SwitchPreference mRcclass1Supported;
    private SwitchPreference mRcclass2Supported;
    private SwitchPreference mRcclass3Supported;
    private SwitchPreference mSr1Supported;
    private SwitchPreference mSr3Supported;
    private String mStrTmp;
    private Handler mUiThread = new Handler();
    private int simIdx = UeNwCapActivity.mSimIndex;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_uecap_cdma_2000);
        this.mSr1Supported = (SwitchPreference) findPreference(KEY_SR1_SUPPORTED);
        this.mSr1Supported.setOnPreferenceChangeListener(this);
        this.mSr3Supported = (SwitchPreference) findPreference(KEY_SR3_SUPPORTED);
        this.mSr3Supported.setOnPreferenceChangeListener(this);
        this.mRcclass1Supported = (SwitchPreference) findPreference(KEY_CLASS1_SUPPORTED);
        this.mRcclass1Supported.setOnPreferenceChangeListener(this);
        this.mRcclass2Supported = (SwitchPreference) findPreference(KEY_CLASS2_SUPPORTED);
        this.mRcclass2Supported.setOnPreferenceChangeListener(this);
        this.mRcclass3Supported = (SwitchPreference) findPreference(KEY_CLASS3_SUPPORTED);
        this.mRcclass3Supported.setOnPreferenceChangeListener(this);
        this.mQpchSupported = (SwitchPreference) findPreference(KEY_QPCH_SUPPORTED);
        this.mQpchSupported.setOnPreferenceChangeListener(this);
        this.mBandClass = (SwitchPreference) findPreference(KEY_BAND_CLASS);
        this.mBandClass.setOnPreferenceChangeListener(this);
        this.mBandSubClass = (SwitchPreference) findPreference(KEY_BAND_SUB_CLASS);
        this.mBandSubClass.setOnPreferenceChangeListener(this);
        this.mRate1m8Supported = (SwitchPreference) findPreference(KEY_RATE1M8_SUPPORTED);
        this.mRate1m8Supported.setOnPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHandler = new MyHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        Log.d(TAG, "onstart");
        Message mGetC2kUeCap = this.mHandler.obtainMessage(0);
        this.mHandler.sendMessage(mGetC2kUeCap);
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                UeCapC2kActivity ueCapC2kActivity = UeCapC2kActivity.this;
                ueCapC2kActivity.mStrTmp = ueCapC2kActivity.teleApi.c2kUeCapApi().getUeCapInfo(UeCapC2kActivity.this.simIdx);
                Log.d(UeCapC2kActivity.TAG, "GET_C2K_UE_CAP simIdx: " + UeCapC2kActivity.this.simIdx + "  mStrTmp: " + UeCapC2kActivity.this.mStrTmp);
                if (UeCapC2kActivity.this.mStrTmp == null || !UeCapC2kActivity.this.mStrTmp.contains("OK")) {
                    UeCapC2kActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapC2kActivity.MyHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapC2kActivity.this.mSr1Supported.setEnabled(false);
                            UeCapC2kActivity.this.mSr3Supported.setEnabled(false);
                            UeCapC2kActivity.this.mRcclass1Supported.setEnabled(false);
                            UeCapC2kActivity.this.mRcclass2Supported.setEnabled(false);
                            UeCapC2kActivity.this.mRcclass3Supported.setEnabled(false);
                            UeCapC2kActivity.this.mQpchSupported.setEnabled(false);
                            UeCapC2kActivity.this.mBandClass.setEnabled(false);
                            UeCapC2kActivity.this.mBandSubClass.setEnabled(false);
                            UeCapC2kActivity.this.mRate1m8Supported.setEnabled(false);
                        }
                    });
                    return;
                }
                try {
                    final String[] str = UeCapC2kActivity.this.mStrTmp.split("\n")[0].trim().split("-");
                    UeCapC2kActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapC2kActivity.MyHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapC2kActivity.this.mSr1Supported.setChecked(str[0].equals("1"));
                            UeCapC2kActivity.this.mSr3Supported.setChecked(str[1].equals("1"));
                            UeCapC2kActivity.this.mRcclass1Supported.setChecked(str[2].equals("1"));
                            UeCapC2kActivity.this.mRcclass2Supported.setChecked(str[3].equals("1"));
                            UeCapC2kActivity.this.mRcclass3Supported.setChecked(str[4].equals("1"));
                            UeCapC2kActivity.this.mQpchSupported.setChecked(str[9].equals("1"));
                            UeCapC2kActivity.this.mBandClass.setChecked(str[35].equals("1"));
                            UeCapC2kActivity.this.mBandSubClass.setChecked(str[36].equals("1"));
                            UeCapC2kActivity.this.mRate1m8Supported.setChecked(str[41].equals("1"));
                        }
                    });
                } catch (Exception e) {
                    Log.d(UeCapC2kActivity.TAG, "" + e);
                }
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "key = " + key);
        return false;
    }
}
