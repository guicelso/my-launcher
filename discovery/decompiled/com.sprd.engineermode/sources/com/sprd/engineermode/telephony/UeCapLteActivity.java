package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapLteActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CLOSE = "0";
    private static final int CLOSE_ANR = 2;
    private static final int CLOSE_DLCA = 4;
    private static final int CLOSE_EICIC = 10;
    private static final int CLOSE_EMBMS = 12;
    private static final int CLOSE_EMFBI = 18;
    private static final int CLOSE_FBI = 20;
    private static final int CLOSE_MDT = 14;
    private static final int CLOSE_MTA = 16;
    private static final int CLOSE_TM9 = 8;
    private static final int CLOSE_ULCA = 6;
    private static final int CLOSE_UL_64QAM = 22;
    private static final int GET_ANR = 23;
    private static final int GET_DLCA = 24;
    private static final int GET_EICIC = 27;
    private static final int GET_EMBMS = 28;
    private static final int GET_EMFBI = 31;
    private static final int GET_FBI = 32;
    private static final int GET_MDT = 29;
    private static final int GET_MTA = 30;
    private static final int GET_TM9 = 26;
    private static final int GET_ULCA = 25;
    private static final int GET_UL_64QAM = 33;
    private static final String KEY_ANR = "anr";
    private static final String KEY_DLCA = "dlca";
    private static final String KEY_EICIC = "eicic";
    private static final String KEY_EMBMS = "embms";
    private static final String KEY_EMFBI = "emfbi";
    private static final String KEY_FBI = "fbi";
    private static final String KEY_MDT = "mdt";
    private static final String KEY_MTA = "mta";
    private static final String KEY_TM9 = "tm9";
    private static final String KEY_ULCA = "ulca";
    private static final String KEY_UL_64QAM = "ul_64qam";
    private static final String OPEN = "1";
    private static final int OPEN_ANR = 1;
    private static final int OPEN_DLCA = 3;
    private static final int OPEN_EICIC = 9;
    private static final int OPEN_EMBMS = 11;
    private static final int OPEN_EMFBI = 17;
    private static final int OPEN_FBI = 19;
    private static final int OPEN_MDT = 13;
    private static final int OPEN_MTA = 15;
    private static final int OPEN_TM9 = 7;
    private static final int OPEN_ULCA = 5;
    private static final int OPEN_UL_64QAM = 21;
    private static final String TAG = "UeCapLteActivity";
    private SwitchPreference[] LteUeCap;
    private String mATCmd;
    private Context mContext;
    private MyHandler mHandler;
    private SwitchPreference mPreDlca;
    private SwitchPreference mPreUl64qam;
    private SwitchPreference mPreUlca;
    private String mStrTmp;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private Handler mUiThread = new Handler();
    private int simIdx = UeNwCapActivity.mSimIndex;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_uecap_lte);
        this.mContext = this;
        this.mPreDlca = (SwitchPreference) findPreference(KEY_DLCA);
        this.mPreDlca.setOnPreferenceChangeListener(this);
        this.mPreUlca = (SwitchPreference) findPreference(KEY_ULCA);
        this.mPreUlca.setOnPreferenceChangeListener(this);
        this.mPreUl64qam = (SwitchPreference) findPreference(KEY_UL_64QAM);
        this.mPreUl64qam.setOnPreferenceChangeListener(this);
        this.LteUeCap = new SwitchPreference[]{this.mPreDlca, this.mPreUlca, this.mPreUl64qam};
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHandler = new MyHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        Log.d(TAG, "onstart");
        boolean[] status = {false, false, false};
        if (this.teleApi.lteUeCapApi().getCap(this.simIdx, status)) {
            for (int i = 0; i < 3; i++) {
                this.LteUeCap[i].setChecked(status[i]);
            }
        } else {
            for (SwitchPreference aLteUeCap : this.LteUeCap) {
                aLteUeCap.setEnabled(false);
            }
        }
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyHandler extends Handler {
        AlertDialog alertDialog;

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 3) {
                Log.d(UeCapLteActivity.TAG, "OPEN_DLCA");
                if (UeCapLteActivity.this.teleApi.lteUeCapApi().openDlca(UeCapLteActivity.this.simIdx)) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreDlca.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreDlca.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 4) {
                Log.d(UeCapLteActivity.TAG, "CLOSE_DLCA");
                if (UeCapLteActivity.this.teleApi.lteUeCapApi().closeDlca(UeCapLteActivity.this.simIdx)) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreDlca.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreDlca.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 5) {
                Log.d(UeCapLteActivity.TAG, "OPEN_ULCA");
                if (!UeCapLteActivity.this.teleApi.lteUeCapApi().openUlca(UeCapLteActivity.this.simIdx) || Const.isBoardCAT4()) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUlca.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapLteActivity.this.chooseToReboot(5);
                    return;
                }
            }
            if (i == 6) {
                Log.d(UeCapLteActivity.TAG, "CLOSE_ULCA");
                if (!UeCapLteActivity.this.teleApi.lteUeCapApi().closeUlca(UeCapLteActivity.this.simIdx) || Const.isBoardCAT4()) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUlca.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapLteActivity.this.chooseToReboot(6);
                    return;
                }
            }
            if (i == 21) {
                Log.d(UeCapLteActivity.TAG, "OPEN_UL_64QAM");
                if (!UeCapLteActivity.this.teleApi.lteUeCapApi().openUl64Qam(UeCapLteActivity.this.simIdx) || Const.isBoardCAT4()) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUl64qam.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                    return;
                } else {
                    UeCapLteActivity.this.chooseToReboot(21);
                    return;
                }
            }
            if (i == 22) {
                Log.d(UeCapLteActivity.TAG, "CLOSE_UL_64QAM");
                if (!UeCapLteActivity.this.teleApi.lteUeCapApi().closeUl64Qam(UeCapLteActivity.this.simIdx) || Const.isBoardCAT4()) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.MyHandler.8
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUl64qam.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Change status is not supported", 0).show();
                        }
                    });
                } else {
                    UeCapLteActivity.this.chooseToReboot(22);
                }
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (KEY_DLCA.equals(key)) {
            if (!this.mPreDlca.isChecked()) {
                Message openDlca = this.mHandler.obtainMessage(3);
                this.mHandler.sendMessage(openDlca);
                return false;
            }
            Message closeDlca = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(closeDlca);
            return false;
        }
        if (KEY_ULCA.equals(key)) {
            if (!this.mPreUlca.isChecked()) {
                Message openUlca = this.mHandler.obtainMessage(5);
                this.mHandler.sendMessage(openUlca);
                return false;
            }
            Message closeUlca = this.mHandler.obtainMessage(6);
            this.mHandler.sendMessage(closeUlca);
            return false;
        }
        if (KEY_UL_64QAM.equals(key)) {
            if (!this.mPreUl64qam.isChecked()) {
                Message openUl64qam = this.mHandler.obtainMessage(21);
                this.mHandler.sendMessage(openUl64qam);
                return false;
            }
            Message closeUl64qam = this.mHandler.obtainMessage(22);
            this.mHandler.sendMessage(closeUl64qam);
            return false;
        }
        return false;
    }

    public void chooseToReboot(final int type) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.mContext).setMessage(getString(R.string.choose_to_reboot)).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                int i = type;
                if (i == 5) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUlca.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                            PowerManager pm = (PowerManager) UeCapLteActivity.this.mContext.getSystemService("power");
                            pm.reboot("openULCA");
                        }
                    });
                    return;
                }
                if (i == 6) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUlca.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                            PowerManager pm = (PowerManager) UeCapLteActivity.this.mContext.getSystemService("power");
                            pm.reboot("closeULCA");
                        }
                    });
                } else if (i == 21) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.2.3
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUl64qam.setChecked(true);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                            PowerManager pm = (PowerManager) UeCapLteActivity.this.mContext.getSystemService("power");
                            pm.reboot("openUL64QAM");
                        }
                    });
                } else if (i == 22) {
                    UeCapLteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.2.4
                        @Override // java.lang.Runnable
                        public void run() {
                            UeCapLteActivity.this.mPreUl64qam.setChecked(false);
                            Toast.makeText(UeCapLteActivity.this.mContext, "Success", 0).show();
                            PowerManager pm = (PowerManager) UeCapLteActivity.this.mContext.getSystemService("power");
                            pm.reboot("closeUL64QAM");
                        }
                    });
                }
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.UeCapLteActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }
}
