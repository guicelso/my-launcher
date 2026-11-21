package com.sprd.engineermode.connectivity.wifi;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class WifiTestActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int DEINIT_TEST_STATUS = 2;
    private static final int DISABLED_NO_SLEEP = 0;
    private static final int ENABLED_NO_SLEEP = 8;
    private static final int GET_ADAPTIVE_STATUS = 11;
    private static final int GET_BEAMFORMING_STATUS = 17;
    private static final int GET_LNA_STATUS = 7;
    private static final int GET_SCAN_OFF_STATUS = 14;
    private static final int GET_STBC_RX_STATUS = 20;
    private static final int INIT_TEST_STATUS = 1;
    private static final String KEY_BEAMFORMING = "beamforming_key";
    private static final String KEY_LNA_BYPASS = "lna_bypass";
    private static final String KEY_MAX_POWER = "max_power";
    private static final String KEY_STBC_RX = "stbc_rx_key";
    private static final String KEY_WIFI_ADAPTIVE = "wifi_adaptive";
    private static final String KEY_WIFI_SCAN_OFF = "wifi_scan_off";
    private static final int SET_ADAPTIVE_OFF = 10;
    private static final int SET_ADAPTIVE_ON = 9;
    private static final int SET_BEAMFORMING_OFF = 16;
    private static final int SET_BEAMFORMING_ON = 15;
    private static final int SET_LNA_OFF = 6;
    private static final int SET_LNA_ON = 5;
    private static final int SET_MAX_POWER = 3;
    private static final int SET_SCAN_OFF = 13;
    private static final int SET_SCAN_ON = 12;
    private static final int SET_STBC_RX_OFF = 19;
    private static final int SET_STBC_RX_ON = 18;
    private static final String TAG = "WifiTestActivity";
    private static final int UNSET_MAX_POWER = 4;
    private TwoStatePreference beamforming;
    private TwoStatePreference lnaPref;
    private WTHandler mWTHandler;
    private TwoStatePreference maxPower;
    private TwoStatePreference scanOffPref;
    private TwoStatePreference stbcRx;
    private TwoStatePreference wifiAdaptive;
    private Handler mUiThread = new Handler();
    private IConnectivityApi.IWifiEut wifiEut = CoreApi.getConnectivityApi().wifiEut();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTestActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.net.wifi.STATE_CHANGE".equals(action)) {
                return;
            }
            Log.d(WifiTestActivity.TAG, "wifi changed: " + intent.getAction());
            WifiManager mWifiManager = (WifiManager) WifiTestActivity.this.getSystemService("wifi");
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            SupplicantState state = wifiInfo.getSupplicantState();
            if (state == SupplicantState.COMPLETED) {
                Message switchMsg = WifiTestActivity.this.mWTHandler.obtainMessage(8);
                WifiTestActivity.this.mWTHandler.sendMessage(switchMsg);
            } else {
                Message switchMsg2 = WifiTestActivity.this.mWTHandler.obtainMessage(0);
                WifiTestActivity.this.mWTHandler.sendMessage(switchMsg2);
            }
        }
    };

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_wifi_certification);
        this.lnaPref = (TwoStatePreference) findPreference(KEY_LNA_BYPASS);
        this.lnaPref.setOnPreferenceChangeListener(this);
        this.scanOffPref = (TwoStatePreference) findPreference(KEY_WIFI_SCAN_OFF);
        this.scanOffPref.setOnPreferenceChangeListener(this);
        this.maxPower = (TwoStatePreference) findPreference(KEY_MAX_POWER);
        this.maxPower.setOnPreferenceChangeListener(this);
        this.wifiAdaptive = (TwoStatePreference) findPreference(KEY_WIFI_ADAPTIVE);
        this.wifiAdaptive.setOnPreferenceChangeListener(this);
        if (Const.isMarlin()) {
            this.scanOffPref.setEnabled(true);
            this.wifiAdaptive.setEnabled(true);
            this.maxPower.setEnabled(true);
        } else {
            this.scanOffPref.setEnabled(false);
            this.wifiAdaptive.setEnabled(false);
            this.maxPower.setEnabled(false);
        }
        this.lnaPref.setEnabled(true);
        this.beamforming = (TwoStatePreference) findPreference(KEY_BEAMFORMING);
        this.beamforming.setOnPreferenceChangeListener(this);
        this.stbcRx = (TwoStatePreference) findPreference(KEY_STBC_RX);
        this.stbcRx.setOnPreferenceChangeListener(this);
        if (!Const.isMarlin3() && !Const.isMarlin3Lite() && !Const.isMarlin3E()) {
            getPreferenceScreen().removePreference(this.beamforming);
            getPreferenceScreen().removePreference(this.stbcRx);
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mWTHandler = new WTHandler(ht.getLooper());
        Message initStatus = this.mWTHandler.obtainMessage(1);
        this.mWTHandler.sendMessage(initStatus);
        if (!Const.isMarlin()) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.wifi.STATE_CHANGE");
            registerReceiver(this.mReceiver, filter);
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        if (Const.isMarlin()) {
            TwoStatePreference twoStatePreference = this.lnaPref;
            if (twoStatePreference != null && twoStatePreference.isEnabled()) {
                Message getLNAStatus = this.mWTHandler.obtainMessage(7);
                this.mWTHandler.sendMessage(getLNAStatus);
            }
        } else if (SystemPropertiesProxy.get("persist.sys.nosleep.enabled", "0").equals("0")) {
            this.lnaPref.setChecked(false);
        } else {
            this.lnaPref.setChecked(true);
        }
        TwoStatePreference twoStatePreference2 = this.wifiAdaptive;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message getAdaptiveStatus = this.mWTHandler.obtainMessage(11);
            this.mWTHandler.sendMessage(getAdaptiveStatus);
        }
        TwoStatePreference twoStatePreference3 = this.scanOffPref;
        if (twoStatePreference3 != null && twoStatePreference3.isEnabled()) {
            Message getScanOffStatus = this.mWTHandler.obtainMessage(14);
            this.mWTHandler.sendMessage(getScanOffStatus);
        }
        TwoStatePreference twoStatePreference4 = this.beamforming;
        if (twoStatePreference4 != null && twoStatePreference4.isEnabled()) {
            Message getBeamformingStatus = this.mWTHandler.obtainMessage(17);
            this.mWTHandler.sendMessage(getBeamformingStatus);
        }
        TwoStatePreference twoStatePreference5 = this.stbcRx;
        if (twoStatePreference5 != null && twoStatePreference5.isEnabled()) {
            Message getStbcRxStatus = this.mWTHandler.obtainMessage(20);
            this.mWTHandler.sendMessage(getStbcRxStatus);
        }
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (!Const.isMarlin()) {
            unregisterReceiver(this.mReceiver);
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        Message exitStatus = this.mWTHandler.obtainMessage(2);
        this.mWTHandler.sendMessage(exitStatus);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isWifiOn() {
        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        Log.d(TAG, "isWifiOn = " + wifiManager.isWifiEnabled());
        if (wifiManager.isWifiEnabled()) {
            return true;
        }
        return false;
    }

    private void showAlertDialog() {
        Log.d(TAG, " wifi close");
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Warning !").setMessage("Please open wifi first!").setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.wifi.WifiTestActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
        Log.d(TAG, "alertDialog.show");
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) {
        Message switchMsg;
        TwoStatePreference twoStatePreference = this.maxPower;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Message unSetPower = this.mWTHandler.obtainMessage(4);
                this.mWTHandler.sendMessage(unSetPower);
            } else {
                Message setPower = this.mWTHandler.obtainMessage(3);
                this.mWTHandler.sendMessage(setPower);
            }
            return false;
        }
        if (pref == this.lnaPref) {
            if (Const.isMarlin()) {
                if (this.lnaPref.isChecked()) {
                    Message setOff = this.mWTHandler.obtainMessage(6);
                    this.mWTHandler.sendMessage(setOff);
                } else {
                    Message setOn = this.mWTHandler.obtainMessage(5);
                    this.mWTHandler.sendMessage(setOn);
                }
                return false;
            }
            if (!checkWifiStatus()) {
                if (this.lnaPref.isChecked()) {
                    switchMsg = this.mWTHandler.obtainMessage(0);
                } else {
                    switchMsg = this.mWTHandler.obtainMessage(8);
                }
                this.mWTHandler.sendMessage(switchMsg);
            } else {
                Toast.makeText(this, "Please open wifi", 0).show();
            }
            return false;
        }
        if (pref == this.wifiAdaptive) {
            if (isWifiOn()) {
                if (this.wifiAdaptive.isChecked()) {
                    Message setOff2 = this.mWTHandler.obtainMessage(10);
                    this.mWTHandler.sendMessage(setOff2);
                } else {
                    Message setOn2 = this.mWTHandler.obtainMessage(9);
                    this.mWTHandler.sendMessage(setOn2);
                }
            } else {
                showAlertDialog();
            }
            return false;
        }
        if (pref == this.scanOffPref) {
            if (isWifiOn()) {
                if (!this.scanOffPref.isChecked()) {
                    Message setOn3 = this.mWTHandler.obtainMessage(12);
                    this.mWTHandler.sendMessage(setOn3);
                } else {
                    Message setOff3 = this.mWTHandler.obtainMessage(13);
                    this.mWTHandler.sendMessage(setOff3);
                }
            } else {
                showAlertDialog();
            }
            return false;
        }
        if (pref == this.beamforming) {
            if (isWifiOn()) {
                if (this.beamforming.isChecked()) {
                    Message setBeamformingOff = this.mWTHandler.obtainMessage(16);
                    this.mWTHandler.sendMessage(setBeamformingOff);
                } else {
                    Message setBeamformingOn = this.mWTHandler.obtainMessage(15);
                    this.mWTHandler.sendMessage(setBeamformingOn);
                }
            } else {
                showAlertDialog();
            }
            return false;
        }
        if (pref != this.stbcRx) {
            return false;
        }
        if (isWifiOn()) {
            if (this.stbcRx.isChecked()) {
                Message setStbcRxOff = this.mWTHandler.obtainMessage(19);
                this.mWTHandler.sendMessage(setStbcRxOff);
            } else {
                Message setStbcRxOn = this.mWTHandler.obtainMessage(18);
                this.mWTHandler.sendMessage(setStbcRxOn);
            }
        } else {
            showAlertDialog();
        }
        return false;
    }

    class WTHandler extends Handler {
        WTHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(WifiTestActivity.TAG, "handleMessage " + msg.what);
            switch (msg.what) {
                case 1:
                    if (!WifiTestActivity.this.isWifiOn()) {
                        if (WifiTestActivity.this.wifiEut.up()) {
                            Log.d(WifiTestActivity.TAG, "insmodWifi and wifiStart success");
                        } else {
                            Log.d(WifiTestActivity.TAG, "insmodWifi and wifiStart failed");
                        }
                    } else {
                        Log.d(WifiTestActivity.TAG, "wifi is on, so ignore insmodWifi and wifiStart");
                    }
                    break;
                case 2:
                    if (!WifiTestActivity.this.isWifiOn()) {
                        if (WifiTestActivity.this.wifiEut.down()) {
                            Log.d(WifiTestActivity.TAG, "wifiStop and rmmodWifi success");
                        } else {
                            Log.d(WifiTestActivity.TAG, "wifiStop and rmmodWifi failed");
                        }
                    } else {
                        Log.d(WifiTestActivity.TAG, "wifi is on, so ignore wifiStop and rmmodWifi");
                    }
                    WifiTestActivity.this.finish();
                    break;
                case 3:
                    try {
                        WifiTestActivity.this.wifiEut.setTxPower(1127);
                        WifiTestActivity.this.wifiEut.setTxPower(127);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$3FY3_nlG67U7bFAHHbotD2awQiM
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$1$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (Exception e) {
                        Log.e(WifiTestActivity.TAG, "set power failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$B5hq0DWAnw_6vETJO7N1rqKR1gk
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$0$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 4:
                    try {
                        WifiTestActivity.this.wifiEut.setTxPower(1088);
                        WifiTestActivity.this.wifiEut.setTxPower(72);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$sIhByk-QCz_RCfKbfhfHDnqLloY
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$3$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e2) {
                        Log.e(WifiTestActivity.TAG, "unset power failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$S6aHgee0Dt7DfmNJCBIJYra5G3M
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$2$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 5:
                    try {
                        WifiTestActivity.this.wifiEut.enableSleepless();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$hFNe090mNPUUg8Hj21yBCVDBvlg
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$5$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e3) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$oGEdBwt1IuR4zg9HN1eIwC2vTMY
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$4$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 6:
                    try {
                        WifiTestActivity.this.wifiEut.disableSleepless();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$r2JgCSDma-7IQbFp8M6YhIKzhQY
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$7$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e4) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$WVER8XFZ-dI7jKz7PuGObjhQ08o
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$6$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 7:
                    try {
                        final boolean status = WifiTestActivity.this.wifiEut.getSleeplessState();
                        Log.d(WifiTestActivity.TAG, "get lna state:" + status);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$2gE8CsvRWSi16-duAf8T4JPsJVk
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$9$WifiTestActivity$WTHandler(status);
                            }
                        });
                    } catch (EmException e5) {
                        Log.e(WifiTestActivity.TAG, "GET_LNA_STATUS Fail");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$VKPMhW9kcFfx2RflArnfxSkezi4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$8$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 9:
                    try {
                        WifiTestActivity.this.wifiEut.enableAdaptive();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$SRjUjLlDOHjDx8BDXh5pZG2-FqE
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$11$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e6) {
                        Log.e(WifiTestActivity.TAG, "enable adaptive failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$8_zcY52-JknCIFdecqOJ2pUk9Nw
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$10$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 10:
                    try {
                        WifiTestActivity.this.wifiEut.disableAdaptive();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$SGROWbzgkyJ-Q3kG3rlK5seaS1E
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$13$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e7) {
                        Log.e(WifiTestActivity.TAG, "disable adaptive failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$vd9sxZowo3Nq1Gzx6Db0syNMvOc
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$12$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 11:
                    try {
                        final boolean adaptiveStatus = WifiTestActivity.this.wifiEut.getAdaptiveState();
                        Log.d(WifiTestActivity.TAG, "get adaptive state: " + adaptiveStatus);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$2r8zMWwPQ3ZS2IOEX84hUvELj14
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$15$WifiTestActivity$WTHandler(adaptiveStatus);
                            }
                        });
                    } catch (EmException e8) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$wJXgJZAreoeWGYgJUhDsu6ar94U
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$14$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 12:
                    try {
                        WifiTestActivity.this.wifiEut.enableScan();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$6YJGpIn5XKEKodU6CFhKmKXS2tE
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$17$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e9) {
                        Log.e(WifiTestActivity.TAG, "set scan failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$Y6JDUsb6MCF6_YqrFSm66oyiYt8
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$16$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 13:
                    try {
                        WifiTestActivity.this.wifiEut.disableScan();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$5B-HWow4lkQfeiVg7aeJ3ZLNxMw
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$19$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e10) {
                        Log.e(WifiTestActivity.TAG, "set scan failed");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$bo4G5HOTfZ2okHO5mjLDfX05sm8
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$18$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 14:
                    try {
                        final boolean scanState = WifiTestActivity.this.wifiEut.getScanState();
                        Log.d(WifiTestActivity.TAG, "get scan state: " + scanState);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$idhjPtVQmBkwrCK2QpVIIhk7uHc
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$21$WifiTestActivity$WTHandler(scanState);
                            }
                        });
                    } catch (EmException e11) {
                        Log.e(WifiTestActivity.TAG, "get scan state fail");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$NzJajcbjRtY7ILI1_4Ibk1Ts6KE
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$20$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 15:
                    try {
                        WifiTestActivity.this.wifiEut.enableBeamforming();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$UoB-9uiOuxqwp_frvkP8UD0FK8E
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$26$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e12) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$Z5VXBU-DYcZbh_R7JP5cFI2fpRQ
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$24$WifiTestActivity$WTHandler();
                            }
                        });
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$m4u0eWpmt67FftCtM1bsBNRlO34
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$25$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 16:
                    try {
                        WifiTestActivity.this.wifiEut.disableBeamforming();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$lIkqXH6iciATPXtaVxVjgbQgckc
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$28$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e13) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$JaOlgCIQeyr45xE4_JR-IccHcis
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$27$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 17:
                    try {
                        final boolean beamformingStatus = WifiTestActivity.this.wifiEut.getBeamformingStatus();
                        Log.d(WifiTestActivity.TAG, "get beamforming status: " + beamformingStatus);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$hGDuFQGNQnC5LAzxFOZpEreD4XU
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$23$WifiTestActivity$WTHandler(beamformingStatus);
                            }
                        });
                    } catch (EmException e14) {
                        Log.e(WifiTestActivity.TAG, "get beamforming status fail");
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$gSEsOYEcIyq9gcqWL9neZrCEMkw
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$22$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 18:
                    try {
                        WifiTestActivity.this.wifiEut.enableStbcRx();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$YhLSBMZKOA2m1C-nSm_krMM-2Is
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$32$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e15) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$fXMCp7HYek5oGcWNCllmjZpG-Eg
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$31$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 19:
                    try {
                        WifiTestActivity.this.wifiEut.disableStbcRx();
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$7GmxGFdNIV760ipmic7yxta0e3s
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$34$WifiTestActivity$WTHandler();
                            }
                        });
                    } catch (EmException e16) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$kNQsovi1tEYxgfkxG6cL1_tey_E
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$33$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 20:
                    try {
                        final boolean stbcRxStatus = WifiTestActivity.this.wifiEut.getStbcRxStatus();
                        Log.d(WifiTestActivity.TAG, "get stbc rx status: " + stbcRxStatus);
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$P7Ycq-uZL9eBsjF2SPj9PctZqs0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$30$WifiTestActivity$WTHandler(stbcRxStatus);
                            }
                        });
                    } catch (EmException e17) {
                        WifiTestActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiTestActivity$WTHandler$8taGmKgEgKXwxEoq_mxSikdcgus
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$29$WifiTestActivity$WTHandler();
                            }
                        });
                        return;
                    }
                    break;
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.maxPower.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$1$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.maxPower.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$2$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.maxPower.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$3$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.maxPower.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$4$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.lnaPref.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$5$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.lnaPref.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$6$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.lnaPref.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$7$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.lnaPref.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$8$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.lnaPref.setEnabled(false);
        }

        public /* synthetic */ void lambda$handleMessage$9$WifiTestActivity$WTHandler(boolean status) {
            WifiTestActivity.this.lnaPref.setChecked(status);
        }

        public /* synthetic */ void lambda$handleMessage$10$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.wifiAdaptive.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$11$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.wifiAdaptive.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$12$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.wifiAdaptive.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$13$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.wifiAdaptive.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$14$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.wifiAdaptive.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$15$WifiTestActivity$WTHandler(boolean adaptiveStatus) {
            WifiTestActivity.this.wifiAdaptive.setChecked(adaptiveStatus);
        }

        public /* synthetic */ void lambda$handleMessage$16$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.scanOffPref.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$17$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.scanOffPref.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$18$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.scanOffPref.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$19$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.scanOffPref.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$20$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.scanOffPref.setEnabled(false);
        }

        public /* synthetic */ void lambda$handleMessage$21$WifiTestActivity$WTHandler(boolean scanState) {
            WifiTestActivity.this.scanOffPref.setChecked(scanState);
        }

        public /* synthetic */ void lambda$handleMessage$22$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setEnabled(false);
        }

        public /* synthetic */ void lambda$handleMessage$23$WifiTestActivity$WTHandler(boolean beamformingStatus) {
            WifiTestActivity.this.beamforming.setChecked(beamformingStatus);
        }

        public /* synthetic */ void lambda$handleMessage$24$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$25$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setChecked(false);
            Toast.makeText(WifiTestActivity.this, "Set failed", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$26$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$27$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setChecked(true);
            Toast.makeText(WifiTestActivity.this, "Set failed", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$28$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.beamforming.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$29$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.stbcRx.setEnabled(false);
        }

        public /* synthetic */ void lambda$handleMessage$30$WifiTestActivity$WTHandler(boolean stbcRxStatus) {
            WifiTestActivity.this.stbcRx.setChecked(stbcRxStatus);
        }

        public /* synthetic */ void lambda$handleMessage$31$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.stbcRx.setChecked(false);
            Toast.makeText(WifiTestActivity.this, "Set failed", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$32$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.stbcRx.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$33$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.stbcRx.setChecked(true);
            Toast.makeText(WifiTestActivity.this, "Set failed", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$34$WifiTestActivity$WTHandler() {
            WifiTestActivity.this.stbcRx.setChecked(false);
        }
    }

    private boolean checkWifiStatus() {
        WifiManager wifiManager = (WifiManager) getSystemService("wifi");
        return !wifiManager.isWifiEnabled();
    }
}
