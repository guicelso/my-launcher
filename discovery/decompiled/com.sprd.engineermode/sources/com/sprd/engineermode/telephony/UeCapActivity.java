package com.sprd.engineermode.telephony;

import android.content.Context;
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
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CLOSE = "0";
    private static final int ClOSE_IPV6 = 2;
    private static final int GET_IPV6 = 3;
    private static final String KEY_GSM = "gsm";
    private static final String KEY_LTE = "lte";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String KEY_WCDMA = "wcdma";
    private static final String OPEN = "1";
    private static final int OPEN_IPV6 = 1;
    private static final String TAG = "UeCapActivity";
    static String saveName = "atchannel0";
    private String mATCmd;
    private Context mContext;
    private Preference mGsmPreference;
    private MyHandler mHandler;
    private Preference mLtePreference;
    private SwitchPreference mPreIpv6;
    private int mSimIndex;
    private String mStrTmp;
    private Handler mUiThread = new Handler();
    private Preference mWcdmaPreference;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            addPreferencesFromResource(R.xml.pref_uecap_wg);
        } else {
            addPreferencesFromResource(R.xml.pref_uecap);
        }
        this.mContext = this;
        saveName = "atchannel" + UeNwCapActivity.mSimIndex;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHandler = new MyHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        Log.d(TAG, "onstart");
        if (this.mPreIpv6 != null) {
            Message openIpv6 = this.mHandler.obtainMessage(3);
            this.mHandler.sendMessage(openIpv6);
        }
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "onPreferenceChange key = " + key);
        return false;
    }

    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 3) {
                Log.d(UeCapActivity.TAG, "GET_IPV6");
                UeCapActivity.this.mATCmd = "AT";
                UeCapActivity ueCapActivity = UeCapActivity.this;
                ueCapActivity.mStrTmp = IATUtils.sendATCmd(ueCapActivity.mATCmd, "atchannel0");
                if (UeCapActivity.this.mStrTmp.contains("")) {
                    UeCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapActivity.MyHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapActivity.this.mPreIpv6.setChecked(false);
                        }
                    });
                } else {
                    UeCapActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapActivity.MyHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapActivity.this.mPreIpv6.setChecked(true);
                        }
                    });
                }
            }
        }
    }
}
