package com.sprd.engineermode.gcf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class AutoTestBySIMActivity extends PreferenceActivity {
    private static final String KEY_APC = "apc_key";
    private static final int MSG_GET_APC_STATUS = 1;
    private static final int MSG_SET_APC_DISABLE = 3;
    private static final int MSG_SET_APC_ENABLE = 2;
    private static final String TAG = "AutoTestBySIMActivity";
    private EMSwitchPreference mAPCTest;
    private int mPhoneCount;
    private String mStrTmp;
    private APCHandler mTHHandler;
    private TelephonyManager[] mTelephonyManager;
    private int mPhoneId = 0;
    private Handler mUiThread = new Handler();
    private String str = null;

    static /* synthetic */ String access$284(AutoTestBySIMActivity x0, Object x1) {
        String str = x0.str + x1;
        x0.str = str;
        return str;
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_delta_nv_auto);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mTelephonyManager = new TelephonyManager[this.mPhoneCount];
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mTHHandler = new APCHandler(ht.getLooper());
        this.mAPCTest = (EMSwitchPreference) findPreference(KEY_APC);
        this.mAPCTest.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.1
            @Override // android.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(Preference preference) {
                Log.d(AutoTestBySIMActivity.TAG, "mAPCTest onPreferenceClick");
                if (!AutoTestBySIMActivity.this.mAPCTest.isChecked()) {
                    Log.d(AutoTestBySIMActivity.TAG, "mAPCTest onPreferenceClick MSG_SET_APC_DISABLE");
                    Message setAPCEnable = AutoTestBySIMActivity.this.mTHHandler.obtainMessage(3);
                    AutoTestBySIMActivity.this.mTHHandler.sendMessage(setAPCEnable);
                    return false;
                }
                Log.d(AutoTestBySIMActivity.TAG, "mAPCTest onPreferenceClick MSG_SET_APC_ENABLE");
                Message setAPCEnable2 = AutoTestBySIMActivity.this.mTHHandler.obtainMessage(2);
                AutoTestBySIMActivity.this.mTHHandler.sendMessage(setAPCEnable2);
                return false;
            }
        });
    }

    @Override // android.app.Activity
    public void onStart() {
        Log.d(TAG, "onStart");
        Message getAPCState = this.mTHHandler.obtainMessage(1);
        this.mTHHandler.sendMessage(getAPCState);
        super.onStart();
    }

    class APCHandler extends Handler {
        public APCHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i == 2) {
                    AutoTestBySIMActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_SET_SPAPC_ENABLE, "atchannel0");
                    Log.d(AutoTestBySIMActivity.TAG, "MSG_SET_APC_ENABLE return : " + AutoTestBySIMActivity.this.mStrTmp);
                    if (AutoTestBySIMActivity.this.mStrTmp == null || !AutoTestBySIMActivity.this.mStrTmp.contains("OK")) {
                        AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.6
                            @Override // java.lang.Runnable
                            public void run() {
                                AutoTestBySIMActivity.this.mAPCTest.setChecked(false);
                            }
                        });
                        return;
                    } else {
                        AutoTestBySIMActivity.this.AlertDialogShow("");
                        AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                AutoTestBySIMActivity.this.mAPCTest.setChecked(true);
                            }
                        });
                        return;
                    }
                }
                if (i == 3) {
                    AutoTestBySIMActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_SET_SPAPC_DISABLE, "atchannel0");
                    Log.d(AutoTestBySIMActivity.TAG, "ENG_AT_SET_SPAPC_DISABLE return : " + AutoTestBySIMActivity.this.mStrTmp);
                    if (AutoTestBySIMActivity.this.mStrTmp == null || !AutoTestBySIMActivity.this.mStrTmp.contains("OK")) {
                        AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.8
                            @Override // java.lang.Runnable
                            public void run() {
                                AutoTestBySIMActivity.this.mAPCTest.setChecked(true);
                            }
                        });
                        return;
                    } else {
                        AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.7
                            @Override // java.lang.Runnable
                            public void run() {
                                AutoTestBySIMActivity.this.mAPCTest.setChecked(false);
                            }
                        });
                        return;
                    }
                }
                return;
            }
            AutoTestBySIMActivity.this.str = "SIM1: ";
            AutoTestBySIMActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPAPC, "atchannel0");
            Log.d(AutoTestBySIMActivity.TAG, "MSG_GET_APC_STATUS SIM1 return : " + AutoTestBySIMActivity.this.mStrTmp);
            if (AutoTestBySIMActivity.this.mStrTmp == null || !AutoTestBySIMActivity.this.mStrTmp.contains("OK") || !AutoTestBySIMActivity.this.mStrTmp.contains("1")) {
                AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        AutoTestBySIMActivity.this.mAPCTest.setChecked(false);
                        AutoTestBySIMActivity.this.mAPCTest.setSummary("");
                    }
                });
            } else {
                String[] res = AutoTestBySIMActivity.this.mStrTmp.split("\\,");
                for (int i2 = 0; i2 < res.length; i2++) {
                    Log.d(AutoTestBySIMActivity.TAG, "MSG_GET_APC_STATUS SIM1 res " + i2 + " = " + res[i2]);
                    if (res[i2].contains("\"")) {
                        AutoTestBySIMActivity.access$284(AutoTestBySIMActivity.this, res[i2] + ",");
                    }
                }
                AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        AutoTestBySIMActivity.this.mAPCTest.setChecked(true);
                        if (AutoTestBySIMActivity.this.str.length() > 1) {
                            AutoTestBySIMActivity.this.mAPCTest.setSummary(AutoTestBySIMActivity.this.str.substring(0, AutoTestBySIMActivity.this.str.length() - 1));
                        }
                    }
                });
            }
            AutoTestBySIMActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GET_SPAPC, "atchannel1");
            Log.d(AutoTestBySIMActivity.TAG, "MSG_GET_APC_STATUS SIM2 return : " + AutoTestBySIMActivity.this.mStrTmp);
            if (AutoTestBySIMActivity.this.mStrTmp == null || !AutoTestBySIMActivity.this.mStrTmp.contains("OK") || !AutoTestBySIMActivity.this.mStrTmp.contains("1")) {
                AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.4
                    @Override // java.lang.Runnable
                    public void run() {
                        AutoTestBySIMActivity.this.mAPCTest.setChecked(false);
                        AutoTestBySIMActivity.this.mAPCTest.setSummary("");
                    }
                });
                return;
            }
            AutoTestBySIMActivity.access$284(AutoTestBySIMActivity.this, "\nSIM2: ");
            String[] res2 = AutoTestBySIMActivity.this.mStrTmp.split("\\,");
            for (int i3 = 0; i3 < res2.length; i3++) {
                Log.d(AutoTestBySIMActivity.TAG, "MSG_GET_APC_STATUS SIM2 res " + i3 + " = " + res2[i3]);
                if (res2[i3].contains("\"")) {
                    AutoTestBySIMActivity.access$284(AutoTestBySIMActivity.this, res2[i3] + ",");
                }
            }
            AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.APCHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    AutoTestBySIMActivity.this.mAPCTest.setChecked(true);
                    if (AutoTestBySIMActivity.this.str.length() > 1) {
                        AutoTestBySIMActivity.this.mAPCTest.setSummary(AutoTestBySIMActivity.this.str.substring(0, AutoTestBySIMActivity.this.str.length() - 1));
                    }
                }
            });
        }
    }

    public void AlertDialogShow(String key) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.apc_enable_waring)).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PowerManager pm = (PowerManager) AutoTestBySIMActivity.this.getSystemService("power");
                pm.reboot("setAPCSwitch");
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                AutoTestBySIMActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.gcf.AutoTestBySIMActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                });
            }
        }).create();
        alertDialog.show();
    }
}
