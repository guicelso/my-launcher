package com.sprd.engineermode.debuglog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class TWFrequencyActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_TW_FREQUENCY = "tw_frequency";
    private static final String KEY_TW_QUERY_SIM = "tw_frequency_sim";
    private static final int LOCK_FREQUENCY = 2;
    private static final String TAG = "TWFrequencyActivity";
    private static final int UNLOCK_FREQUENCY = 1;
    private String mATCmd;
    private String mFrequency;
    private int mFrequencyIndex;
    private TwoStatePreference[] mFrequencySwitch;
    private String mTR;
    private TWFHandler mTWFHandler;
    private int mFrequencyCount = 0;
    private int mSIM = 0;
    private String mServerName = "atchannel0";
    PreferenceGroup mPreGroup = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mTWFHandler = new TWFHandler(ht.getLooper());
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        this.mTR = extras.getString(KEY_TW_FREQUENCY);
        this.mSIM = extras.getInt(KEY_TW_QUERY_SIM);
        this.mServerName = "atchannel" + this.mSIM;
        if (this.mTR.contains("OK")) {
            String[] str = this.mTR.split("\\+");
            this.mFrequencySwitch = new EMSwitchPreference[str.length];
            for (int i = 1; i < str.length; i++) {
                String[] str1 = str[i].split("\\:");
                String[] str2 = str1[1].split("\\,");
                this.mFrequency = str2[0].trim();
                if (!this.mFrequency.equals("0") && this.mFrequency != null) {
                    this.mFrequencySwitch[this.mFrequencyCount] = new EMSwitchPreference(this, null);
                    this.mFrequencySwitch[this.mFrequencyCount].setOnPreferenceChangeListener(this);
                    this.mFrequencySwitch[this.mFrequencyCount].setKey(this.mSIM + KEY_TW_FREQUENCY + this.mFrequencyCount);
                    this.mFrequencySwitch[this.mFrequencyCount].setTitle(this.mFrequency);
                    this.mFrequencySwitch[this.mFrequencyCount].setDefaultValue(false);
                    this.mFrequencyCount = this.mFrequencyCount + 1;
                }
            }
        }
        Log.d(TAG, "There are " + this.mFrequencyCount + "Frequency");
        if (this.mFrequencyCount != 0) {
            for (int i2 = 0; i2 < this.mFrequencyCount; i2++) {
                this.mPreGroup.addPreference(this.mFrequencySwitch[i2]);
            }
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.frequency_set)).setMessage(getString(R.string.empty_frequency)).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.TWFrequencyActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                TWFrequencyActivity.this.finish();
            }
        }).create();
        alertDialog.show();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mTWFHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mTWFHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        for (int i = 0; i < this.mFrequencyCount; i++) {
            TwoStatePreference[] twoStatePreferenceArr = this.mFrequencySwitch;
            if (pref == twoStatePreferenceArr[i]) {
                String title = (String) twoStatePreferenceArr[i].getTitle();
                if (this.mFrequencySwitch[i].isChecked()) {
                    Message unLockF = this.mTWFHandler.obtainMessage(1, i, this.mSIM, title);
                    this.mTWFHandler.sendMessage(unLockF);
                } else {
                    Message lockF = this.mTWFHandler.obtainMessage(2, i, this.mSIM, title);
                    this.mTWFHandler.sendMessage(lockF);
                }
            }
        }
        return true;
    }

    class TWFHandler extends Handler {
        public TWFHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            TWFrequencyActivity.this.mFrequencyIndex = msg.arg1;
            String frequency = (String) msg.obj;
            int i = msg.what;
            if (i == 1) {
                TWFrequencyActivity.this.mATCmd = "AT+SPFRQ=1," + TWFrequencyActivity.this.mFrequencyIndex + "," + frequency;
                String responValue = IATUtils.sendATCmd(TWFrequencyActivity.this.mATCmd, TWFrequencyActivity.this.mServerName);
                Log.d(TWFrequencyActivity.TAG, "<" + TWFrequencyActivity.this.mSIM + "> Channel is " + TWFrequencyActivity.this.mServerName + ",UNLOCK_FREQUENCY is " + TWFrequencyActivity.this.mATCmd + ", responValue is " + responValue);
                if (responValue.contains("OK")) {
                    TWFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.TWFrequencyActivity.TWFHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TWFrequencyActivity.this.mFrequencySwitch[TWFrequencyActivity.this.mFrequencyIndex].setChecked(false);
                            Toast.makeText(TWFrequencyActivity.this, "Success", 0).show();
                        }
                    });
                    return;
                } else {
                    TWFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.TWFrequencyActivity.TWFHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            TWFrequencyActivity.this.mFrequencySwitch[TWFrequencyActivity.this.mFrequencyIndex].setChecked(true);
                            Toast.makeText(TWFrequencyActivity.this, "Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                TWFrequencyActivity.this.mATCmd = "AT+SPFRQ=0," + TWFrequencyActivity.this.mFrequencyIndex + "," + frequency;
                String responValue2 = IATUtils.sendATCmd(TWFrequencyActivity.this.mATCmd, TWFrequencyActivity.this.mServerName);
                Log.d(TWFrequencyActivity.TAG, "<" + TWFrequencyActivity.this.mSIM + ">Channel is " + TWFrequencyActivity.this.mServerName + ",LOCK_FREQUENCY is " + TWFrequencyActivity.this.mATCmd + ", responValue is " + responValue2);
                if (responValue2.contains("OK")) {
                    TWFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.TWFrequencyActivity.TWFHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            TWFrequencyActivity.this.mFrequencySwitch[TWFrequencyActivity.this.mFrequencyIndex].setChecked(true);
                            Toast.makeText(TWFrequencyActivity.this, "Success", 0).show();
                        }
                    });
                } else {
                    TWFrequencyActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.TWFrequencyActivity.TWFHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            TWFrequencyActivity.this.mFrequencySwitch[TWFrequencyActivity.this.mFrequencyIndex].setChecked(false);
                            Toast.makeText(TWFrequencyActivity.this, "Fail", 0).show();
                        }
                    });
                }
            }
        }
    }
}
