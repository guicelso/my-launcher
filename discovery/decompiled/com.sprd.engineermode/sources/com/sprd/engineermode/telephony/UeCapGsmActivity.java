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
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapGsmActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CLOSE = "0";
    private static final int CLOSE_DIVERSITY = 4;
    private static final int CLOSE_VAMOS = 2;
    private static final int GET_DIVERSITY = 6;
    private static final int GET_VAMOS = 5;
    private static final String KEY_DIVERSITY = "GSM diversity";
    private static final String OPEN = "1";
    private static final int OPEN_DIVERSITY = 3;
    private static final int OPEN_VAMOS = 1;
    private static final String TAG = "UeCapGsmActivity";
    private String mATCmd;
    private Context mContext;
    private MyHandler mHandler;
    private SwitchPreference mPreDiversity;
    private String mStrTmp;
    private Handler mUiThread = new Handler();
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private int simIdx = UeNwCapActivity.mSimIndex;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_uecap_gsm);
        this.mContext = this;
        this.mPreDiversity = (SwitchPreference) findPreference(KEY_DIVERSITY);
        this.mPreDiversity.setOnPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHandler = new MyHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        if (this.mPreDiversity != null) {
            Message openIpv6 = this.mHandler.obtainMessage(6);
            this.mHandler.sendMessage(openIpv6);
        }
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
            int i = msg.what;
            if (i == 3) {
                Log.d(UeCapGsmActivity.TAG, "OPEN_DIVERSITY");
                if (UeCapGsmActivity.this.teleApi.gsmUeCapApi().openDiversity(UeCapGsmActivity.this.simIdx)) {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(true);
                            Toast.makeText(UeCapGsmActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(false);
                            Toast.makeText(UeCapGsmActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 4) {
                Log.d(UeCapGsmActivity.TAG, "CLOSE_DIVERSITY");
                if (UeCapGsmActivity.this.teleApi.gsmUeCapApi().closeDiversity(UeCapGsmActivity.this.simIdx)) {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(false);
                            Toast.makeText(UeCapGsmActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(true);
                            Toast.makeText(UeCapGsmActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 6) {
                Log.d(UeCapGsmActivity.TAG, "GET_DIVERSITY");
                if (UeCapGsmActivity.this.teleApi.gsmUeCapApi().getDiversity(UeCapGsmActivity.this.simIdx)) {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(false);
                        }
                    });
                } else {
                    UeCapGsmActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapGsmActivity.MyHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapGsmActivity.this.mPreDiversity.setChecked(true);
                        }
                    });
                }
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (KEY_DIVERSITY.equals(key)) {
            if (!this.mPreDiversity.isChecked()) {
                Message openDiversity = this.mHandler.obtainMessage(3);
                this.mHandler.sendMessage(openDiversity);
                return false;
            }
            Message closeDiversity = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(closeDiversity);
            return false;
        }
        return false;
    }
}
