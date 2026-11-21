package com.sprd.engineermode.connectivity.wifi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.IConnectivityApi;

/* JADX INFO: loaded from: classes4.dex */
public class WifiEUTActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final int DEINIT_TEST_STATUS = 3;
    private static final int DISABLED_POWER_SAVE = 0;
    private static final int ENABLED_POWER_SAVE = 1;
    private static final int INIT_TEST_STATUS = 2;
    private static final String INSMODE_RES = "insmode_result";
    private static final String KEY_POWER_SAVE_MODE = "wifi_power_save_mode";
    private static final String KEY_WIFI_REG = "wifi_reg_wr";
    private static final String KEY_WIFI_RX = "wifi_rx";
    private static final String KEY_WIFI_TX = "wifi_tx";
    private static final String TAG = "WifiTestActivity";
    private EMSwitchPreference mDisPowerSaveMode;
    private ProgressDialog mProgress;
    private WTHandler mWTHandler;
    private Preference mWifi_REG;
    private Preference mWifi_RX;
    private Preference mWifi_TX;
    private Handler mUiThread = new Handler();
    private boolean mInsmodeSuccess = false;
    private IConnectivityApi.IWcndEngControl wcndEngCtl = CoreApi.getConnectivityApi().wcndEngControl();
    private Runnable runnable = new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.WifiEUTActivity.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!WifiEUTActivity.this.wcndEngCtl.isWcndEngRunning()) {
                    WifiEUTActivity.this.mWTHandler.postDelayed(this, 500L);
                } else {
                    Log.d(WifiEUTActivity.TAG, "isWcndEngRunning INIT_TEST_STATUS");
                    Message initStatus = WifiEUTActivity.this.mWTHandler.obtainMessage(2);
                    WifiEUTActivity.this.mWTHandler.sendMessage(initStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_wifi_test);
        this.mDisPowerSaveMode = (EMSwitchPreference) findPreference(KEY_POWER_SAVE_MODE);
        this.mDisPowerSaveMode.setOnPreferenceChangeListener(this);
        this.mDisPowerSaveMode.setEnabled(false);
        this.mWifi_TX = findPreference(KEY_WIFI_TX);
        this.mWifi_TX.setOnPreferenceClickListener(this);
        this.mWifi_TX.setEnabled(false);
        this.mWifi_RX = findPreference(KEY_WIFI_RX);
        this.mWifi_RX.setOnPreferenceClickListener(this);
        this.mWifi_RX.setEnabled(false);
        this.mWifi_REG = findPreference(KEY_WIFI_REG);
        this.mWifi_REG.setOnPreferenceClickListener(this);
        this.mWifi_REG.setEnabled(false);
        this.mProgress = ProgressDialog.show(this, "Wifi Initing...", "Please wait...", true, true);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mWTHandler = new WTHandler(ht.getLooper());
        try {
            if (!this.wcndEngCtl.isWcndEngRunning()) {
                Log.d(TAG, "startWcndEng");
                this.wcndEngCtl.startWcndEng();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mWTHandler.postDelayed(this.runnable, 500L);
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed() mProgress=" + this.mProgress);
        ProgressDialog progressDialog = this.mProgress;
        if (progressDialog != null) {
            progressDialog.dismiss();
            finish();
        } else {
            Message deInitStatus = this.mWTHandler.obtainMessage(3);
            this.mWTHandler.sendMessage(deInitStatus);
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.mWTHandler.removeCallbacks(this.runnable);
        try {
            if (this.wcndEngCtl.isWcndEngRunning()) {
                this.wcndEngCtl.stopWcndEng();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) {
        Message switchMsg;
        if (pref.getKey().equals(KEY_POWER_SAVE_MODE)) {
            if (this.mDisPowerSaveMode.isChecked()) {
                switchMsg = this.mWTHandler.obtainMessage(0);
            } else {
                switchMsg = this.mWTHandler.obtainMessage(1);
            }
            this.mWTHandler.sendMessage(switchMsg);
        }
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        Intent intent = new Intent();
        intent.putExtra(INSMODE_RES, this.mInsmodeSuccess);
        Log.d(TAG, "WifiEUTActivity insmode result is " + this.mInsmodeSuccess);
        if (pref.getKey().equals(KEY_WIFI_TX)) {
            intent.setClassName("com.sprd.engineermode", "com.sprd.engineermode.connectivity.wifi.WifiTXActivity");
        } else if (pref.getKey().equals(KEY_WIFI_RX)) {
            intent.setClassName("com.sprd.engineermode", "com.sprd.engineermode.connectivity.wifi.WifiRXActivity");
        } else if (pref.getKey().equals(KEY_WIFI_REG)) {
            intent.setClassName("com.sprd.engineermode", "com.sprd.engineermode.connectivity.wifi.WifiREGWRActivity");
        }
        startActivity(intent);
        return false;
    }

    class WTHandler extends Handler {
        public WTHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                try {
                    WifiEUTHelper.getHelper().disablePowerSave();
                    WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$I_ij9AKlrt6vsOWEFNUo2L0yTgo
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$5$WifiEUTActivity$WTHandler();
                        }
                    });
                    return;
                } catch (EmException e) {
                    WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$V3TQVUP3EGRzzOzfVYKPQ4nSxTs
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$4$WifiEUTActivity$WTHandler();
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                try {
                    WifiEUTHelper.getHelper().enablePowerSave();
                    WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$sJIWfrIaIg60tCPaDlhKTGK_mm4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$7$WifiEUTActivity$WTHandler();
                        }
                    });
                    return;
                } catch (EmException e2) {
                    WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$kui2iuWcK1C-JGZEz4lQMcb4Qgk
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$6$WifiEUTActivity$WTHandler();
                        }
                    });
                    return;
                }
            }
            if (i != 2) {
                if (i == 3) {
                    if (WifiEUTHelper.getHelper().stop() && WifiEUTHelper.getHelper().removeMod()) {
                        WifiEUTActivity.this.mInsmodeSuccess = false;
                    } else {
                        Toast.makeText(WifiEUTActivity.this, "Rmmod /system/lib/modules/sprdwl.ko or Stop Wifi Fail", 0).show();
                    }
                    WifiEUTActivity.this.finish();
                    return;
                }
                return;
            }
            if (WifiEUTHelper.getHelper().insMod() && WifiEUTHelper.getHelper().start()) {
                WifiEUTActivity.this.mInsmodeSuccess = true;
                WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$kdV2kPRFDzsxaV7OXjp23n00PoA
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$handleMessage$0$WifiEUTActivity$WTHandler();
                    }
                });
                if (Const.isMarlin()) {
                    try {
                        boolean status = WifiEUTHelper.getHelper().getPowerSaveState();
                        if (status) {
                            WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$ceV6VWGi-_Y-7yJV4YzHIDqmhD4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$handleMessage$2$WifiEUTActivity$WTHandler();
                                }
                            });
                            return;
                        } else {
                            WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$VX7Lza7nxE0oNI2WTR7eAxO1N0Y
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$handleMessage$3$WifiEUTActivity$WTHandler();
                                }
                            });
                            return;
                        }
                    } catch (EmException e3) {
                        WifiEUTActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.wifi.-$$Lambda$WifiEUTActivity$WTHandler$ZbNpKyaI5WS99PnCuhXsGX5Sxlw
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$1$WifiEUTActivity$WTHandler();
                            }
                        });
                        return;
                    }
                }
                return;
            }
            Toast.makeText(WifiEUTActivity.this, "Insmod /system/lib/modules/sprdwl.ko or Start Wifi Fail", 0).show();
            WifiEUTActivity.this.finish();
        }

        public /* synthetic */ void lambda$handleMessage$0$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mProgress.dismiss();
            WifiEUTActivity.this.mProgress = null;
            WifiEUTActivity.this.mWifi_TX.setEnabled(true);
            WifiEUTActivity.this.mWifi_RX.setEnabled(true);
            WifiEUTActivity.this.mWifi_REG.setEnabled(true);
            if (Const.isMarlin()) {
                WifiEUTActivity.this.mDisPowerSaveMode.setEnabled(true);
            } else {
                WifiEUTActivity.this.mDisPowerSaveMode.setEnabled(false);
            }
        }

        public /* synthetic */ void lambda$handleMessage$1$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setEnabled(false);
            Toast.makeText(WifiEUTActivity.this, "GET_Power_Save_Mode Fail", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$2$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(true);
        }

        public /* synthetic */ void lambda$handleMessage$3$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$4$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(false);
            Toast.makeText(WifiEUTActivity.this, "DISABLED_POWER_SAVE Fail", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$5$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(false);
        }

        public /* synthetic */ void lambda$handleMessage$6$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(true);
            Toast.makeText(WifiEUTActivity.this, "ENABLED_POWER_SAVE Fail", 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$7$WifiEUTActivity$WTHandler() {
            WifiEUTActivity.this.mDisPowerSaveMode.setChecked(true);
        }
    }
}
