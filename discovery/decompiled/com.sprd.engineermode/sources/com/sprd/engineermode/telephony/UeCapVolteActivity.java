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
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class UeCapVolteActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CLOSE = "0";
    private static final int CLOSE_ASRVCC = 2;
    private static final int CLOSE_BSRVCC = 4;
    private static final int CLOSE_EVS = 6;
    private static final int GET_ASRVCC = 7;
    private static final int GET_BSRVCC = 8;
    private static final int GET_EVS = 9;
    private static final String KEY_ASRVCC = "asrvcc";
    private static final String KEY_BSRVCC = "bsrvcc";
    private static final String KEY_EVS = "evs";
    private static final String OPEN = "1";
    private static final int OPEN_ASRVCC = 1;
    private static final int OPEN_BSRVCC = 3;
    private static final int OPEN_EVS = 5;
    private static final String TAG = "UeCapVolteActivity";
    private String mATCmd;
    private Context mContext;
    private MyHandler mHandler;
    private SwitchPreference mPreAsrvcc;
    private SwitchPreference mPreBsrvcc;
    private SwitchPreference mPreEvs;
    private String mStrTmp;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_uecap_volte);
        this.mContext = this;
        this.mPreAsrvcc = (SwitchPreference) findPreference(KEY_ASRVCC);
        this.mPreAsrvcc.setOnPreferenceChangeListener(this);
        this.mPreAsrvcc.setEnabled(false);
        this.mPreBsrvcc = (SwitchPreference) findPreference(KEY_BSRVCC);
        this.mPreBsrvcc.setOnPreferenceChangeListener(this);
        this.mPreBsrvcc.setEnabled(false);
        this.mPreEvs = (SwitchPreference) findPreference(KEY_EVS);
        this.mPreEvs.setOnPreferenceChangeListener(this);
        this.mPreEvs.setEnabled(false);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHandler = new MyHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() {
        Log.d(TAG, "onstart");
        if (this.mPreAsrvcc != null) {
            Message getAsrvcc = this.mHandler.obtainMessage(7);
            this.mHandler.sendMessage(getAsrvcc);
        }
        if (this.mPreBsrvcc != null) {
            Message getBsrvcc = this.mHandler.obtainMessage(8);
            this.mHandler.sendMessage(getBsrvcc);
        }
        if (this.mPreEvs != null) {
            Message getEvs = this.mHandler.obtainMessage(9);
            this.mHandler.sendMessage(getEvs);
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
            switch (msg.what) {
                case 1:
                    Log.d(UeCapVolteActivity.TAG, "OPEN_ASRVCC");
                    UeCapVolteActivity.this.mATCmd = "at+spengmdvolte = 25,1,1";
                    UeCapVolteActivity ueCapVolteActivity = UeCapVolteActivity.this;
                    ueCapVolteActivity.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreAsrvcc.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.2
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreAsrvcc.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 2:
                    Log.d(UeCapVolteActivity.TAG, "CLOSE_ASRVCC");
                    UeCapVolteActivity.this.mATCmd = "at+spengmdvolte = 25,1,0";
                    UeCapVolteActivity ueCapVolteActivity2 = UeCapVolteActivity.this;
                    ueCapVolteActivity2.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity2.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreAsrvcc.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreAsrvcc.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 3:
                    Log.d(UeCapVolteActivity.TAG, "OPEN_BSRVCC");
                    UeCapVolteActivity.this.mATCmd = "at+spengmdvolte = 24,1,1";
                    UeCapVolteActivity ueCapVolteActivity3 = UeCapVolteActivity.this;
                    ueCapVolteActivity3.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity3.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.8
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreBsrvcc.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.9
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreBsrvcc.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 4:
                    Log.d(UeCapVolteActivity.TAG, "CLOSE_BSRVCC");
                    UeCapVolteActivity.this.mATCmd = "at+spengmdvolte = 24,1,0";
                    UeCapVolteActivity ueCapVolteActivity4 = UeCapVolteActivity.this;
                    ueCapVolteActivity4.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity4.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.10
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreBsrvcc.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.11
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreBsrvcc.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 5:
                    Log.d(UeCapVolteActivity.TAG, "OPEN_EVS");
                    UeCapVolteActivity.this.mATCmd = "AT+SPENGMDVOLTE=12,1,1,1";
                    UeCapVolteActivity ueCapVolteActivity5 = UeCapVolteActivity.this;
                    ueCapVolteActivity5.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity5.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.15
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreEvs.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.16
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreEvs.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 6:
                    Log.d(UeCapVolteActivity.TAG, "CLOSE_EVS");
                    UeCapVolteActivity.this.mATCmd = "AT+SPENGMDVOLTE=12,1,1,0";
                    UeCapVolteActivity ueCapVolteActivity6 = UeCapVolteActivity.this;
                    ueCapVolteActivity6.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity6.mATCmd, UeCapActivity.saveName);
                    if (UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.17
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreEvs.setChecked(false);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Success", 0).show();
                            }
                        });
                    } else {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.18
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreEvs.setChecked(true);
                                Toast.makeText(UeCapVolteActivity.this.mContext, "Change status is not supported", 0).show();
                            }
                        });
                    }
                    break;
                case 7:
                    Log.d(UeCapVolteActivity.TAG, "GET_ASRVCC");
                    UeCapVolteActivity.this.mATCmd = engconstents.ENG_AT_GET_ASRVCC;
                    UeCapVolteActivity ueCapVolteActivity7 = UeCapVolteActivity.this;
                    ueCapVolteActivity7.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity7.mATCmd, UeCapActivity.saveName);
                    if (!UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.7
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreAsrvcc.setChecked(false);
                            }
                        });
                    } else {
                        int index = UeCapVolteActivity.this.mStrTmp.indexOf("25,0,");
                        if (index < 0 || !UeCapVolteActivity.this.mStrTmp.substring("25,0,".length() + index, "25,0,".length() + index + 1).equals(UeCapVolteActivity.OPEN)) {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreAsrvcc.setChecked(false);
                                }
                            });
                        } else {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreAsrvcc.setChecked(true);
                                }
                            });
                        }
                    }
                    break;
                case 8:
                    Log.d(UeCapVolteActivity.TAG, "GET_BSRVCC");
                    UeCapVolteActivity.this.mATCmd = engconstents.ENG_AT_GET_BSRVCC;
                    UeCapVolteActivity ueCapVolteActivity8 = UeCapVolteActivity.this;
                    ueCapVolteActivity8.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity8.mATCmd, UeCapActivity.saveName);
                    if (!UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.14
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreBsrvcc.setChecked(false);
                            }
                        });
                    } else {
                        int index2 = UeCapVolteActivity.this.mStrTmp.indexOf("24,0,");
                        if (index2 < 0 || !UeCapVolteActivity.this.mStrTmp.substring("24,0,".length() + index2, "24,0,".length() + index2 + 1).equals(UeCapVolteActivity.OPEN)) {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.13
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreBsrvcc.setChecked(false);
                                }
                            });
                        } else {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.12
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreBsrvcc.setChecked(true);
                                }
                            });
                        }
                    }
                    break;
                case 9:
                    Log.d(UeCapVolteActivity.TAG, "GET_EVS");
                    UeCapVolteActivity.this.mATCmd = engconstents.ENG_AT_GET_EVS;
                    UeCapVolteActivity ueCapVolteActivity9 = UeCapVolteActivity.this;
                    ueCapVolteActivity9.mStrTmp = IATUtils.sendATCmd(ueCapVolteActivity9.mATCmd, UeCapActivity.saveName);
                    if (!UeCapVolteActivity.this.mStrTmp.contains("OK")) {
                        UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.21
                            @Override // java.lang.Runnable
                            public void run() {
                                UeCapVolteActivity.this.mPreEvs.setChecked(false);
                            }
                        });
                    } else {
                        int index3 = UeCapVolteActivity.this.mStrTmp.indexOf("12,0,1,");
                        if (index3 < 0 || !UeCapVolteActivity.this.mStrTmp.substring("12,0,1,".length() + index3, "12,0,1,".length() + index3 + 1).equals(UeCapVolteActivity.OPEN)) {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.20
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreEvs.setChecked(false);
                                }
                            });
                        } else {
                            UeCapVolteActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.UeCapVolteActivity.MyHandler.19
                                @Override // java.lang.Runnable
                                public void run() {
                                    UeCapVolteActivity.this.mPreEvs.setChecked(true);
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (KEY_ASRVCC.equals(key)) {
            if (!this.mPreAsrvcc.isChecked()) {
                Message openAsrvcc = this.mHandler.obtainMessage(1);
                this.mHandler.sendMessage(openAsrvcc);
                return false;
            }
            Message closeAsrvcc = this.mHandler.obtainMessage(2);
            this.mHandler.sendMessage(closeAsrvcc);
            return false;
        }
        if (KEY_BSRVCC.equals(key)) {
            if (!this.mPreBsrvcc.isChecked()) {
                Message openBsrvcc = this.mHandler.obtainMessage(3);
                this.mHandler.sendMessage(openBsrvcc);
                return false;
            }
            Message closeBsrvcc = this.mHandler.obtainMessage(4);
            this.mHandler.sendMessage(closeBsrvcc);
            return false;
        }
        if (KEY_EVS.equals(key)) {
            if (!this.mPreEvs.isChecked()) {
                Message openEvs = this.mHandler.obtainMessage(5);
                this.mHandler.sendMessage(openEvs);
                return false;
            }
            Message closeEvs = this.mHandler.obtainMessage(6);
            this.mHandler.sendMessage(closeEvs);
            return false;
        }
        return false;
    }
}
