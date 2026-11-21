package com.sprd.engineermode.connectivity.BT;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.provider.Settings;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.impl.nonpublic.SettingsProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IConnectivityApi;
import com.unisoc.engineermode.core.utils.SocketUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class BTActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private static final String CMD_BT_EUT_CLOSE = "eng bt dut_mode_configure 0";
    private static final String CMD_BT_EUT_OPEN = "eng bt dut_mode_configure 1";
    private static final String CMD_BT_EUT_STATUS = "eng bt eut_status";
    private static final String CMD_BT_SHARED_PATH = "eng bt set_rf_path 2";
    private static final String CMD_BT_SINGLE_PATH = "eng bt set_rf_path 1";
    private static final String CONTROLLER_BQB_DISABLED = "controller bqb disabled";
    private static final String CONTROLLER_BQB_ENABLED = "controller bqb enabled";
    private static final String KEY_BQB_MODE = "bqb_mode";
    private static final String KEY_BT_EUT = "bt_eut";
    private static final String KEY_BT_NOSIGNAL_TEST = "bt_nosignal_test";
    private static final String KEY_BT_RF_PATH = "bt_rf_path";
    private static final String KEY_CONTROLLER_BQB = "controller_bqb_mode";
    private static final String KEY_NO_SSP = "no_ssp";
    private static final int MESSAGE_CONTROLLER_BQB_ONOFF = 10;
    private static final int MESSAGE_CONTROLLER_BQB_TRIGGER = 11;
    private static final int MSG_BT_OFF = 0;
    private static final int MSG_BT_ON = 1;
    private static final int MSG_GET_EUT_STATUS = 2;
    private static final int MSG_GET_RF_PATH = 7;
    private static final int MSG_SET_BQB_OFF = 6;
    private static final int MSG_SET_BQB_ON = 5;
    private static final int MSG_SET_EUT_OFF = 4;
    private static final int MSG_SET_EUT_ON = 3;
    private static final int MSG_SET_SHARED_PATH = 9;
    private static final int MSG_SET_SINGLE_PATH = 8;
    private static final String PERSIST_BT_NO_SSP = "persist.sys.bt.non.ssp";
    private static final String PERSIST_BT_RF_PATH = "persist.sys.bt.rf.path";
    private static final String PROCESS_NAME = "wcnd_eng ";
    private static final String PROPERTY_HCITOOLS_SERVER = "bluetooth.hcitools.server";
    private static final String PROPERTY_HCITOOLS_SOCKET = "bluetooth.hcitools.socket";
    private static final String SOCKET_NAME = "hidl_common_socket";
    private static final String TAG = "BTActivity";
    private static final boolean isSprdBoard;
    public static SharedPreferences.Editor mRfPathEditor;
    public static SharedPreferences mRfPathPref;
    private BluetoothAdapter mAdapter;
    private Preference mBTNosignalTest;
    private TwoStatePreference mBtEUT;
    private BTHandler mBtHandler;
    private TwoStatePreference mBtRfPath;
    private String mCmdRes;
    private Preference mControllerBqb;
    private TwoStatePreference mSsp;
    private Handler mUIThread = new Handler();
    private boolean mControllerBqbState = false;
    private IConnectivityApi.IBtEut btEut = CoreApi.getConnectivityApi().btEut();
    private IConnectivityApi.IWcndEngControl wcndEngCtl = CoreApi.getConnectivityApi().wcndEngControl();
    private Runnable runnable = new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!BTActivity.this.wcndEngCtl.isWcndEngRunning()) {
                    BTActivity.this.mBtHandler.postDelayed(this, 500L);
                } else {
                    Log.d(BTActivity.TAG, "isWcndEngRunning INIT_TEST_STATUS");
                    if (BTActivity.this.mBtEUT != null && BTActivity.this.mBtEUT.isEnabled()) {
                        Message get_bt_eut_status = BTActivity.this.mBtHandler.obtainMessage(2);
                        BTActivity.this.mBtHandler.sendMessage(get_bt_eut_status);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    static {
        isSprdBoard = SystemPropertiesProxy.getBoolean("ro.modem.wcn.enable", false) || SystemPropertiesProxy.getBoolean("ro.vendor.modem.wcn.enable", false);
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_bt);
        mRfPathPref = getSharedPreferences("rf_path_pref", 0);
        mRfPathEditor = mRfPathPref.edit();
        this.mBtRfPath = (TwoStatePreference) findPreference(KEY_BT_RF_PATH);
        this.mBtRfPath.setOnPreferenceChangeListener(this);
        if (!Const.isMarlin3() && !Const.isMarlin3E()) {
            getPreferenceScreen().removePreference(this.mBtRfPath);
        }
        this.mBtEUT = (TwoStatePreference) findPreference(KEY_BT_EUT);
        this.mBtEUT.setOnPreferenceChangeListener(this);
        if (SystemPropertiesProxy.get("ro.build.display.id", EnvironmentCompat.MEDIA_UNKNOWN).contains("9630")) {
            this.mBtEUT.setEnabled(false);
            this.mBtEUT.setSummary(R.string.feature_not_support);
        }
        this.mControllerBqb = findPreference(KEY_CONTROLLER_BQB);
        this.mControllerBqb.setOnPreferenceClickListener(this);
        this.mSsp = (TwoStatePreference) findPreference(KEY_NO_SSP);
        this.mSsp.setOnPreferenceChangeListener(this);
        this.mBTNosignalTest = findPreference(KEY_BT_NOSIGNAL_TEST);
        this.mBTNosignalTest.setOnPreferenceClickListener(this);
        if (!isSprdBoard) {
            this.mSsp.setEnabled(false);
            this.mBTNosignalTest.setEnabled(false);
        }
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mBtHandler = new BTHandler(ht.getLooper());
    }

    @Override // android.app.Activity
    protected void onStart() throws IllegalAccessException, InvocationTargetException {
        TwoStatePreference twoStatePreference = this.mSsp;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            String sspStatus = SystemPropertiesProxy.get(PERSIST_BT_NO_SSP, "");
            Log.d(TAG, "sspStatus is " + sspStatus);
            if ("open".equals(sspStatus)) {
                this.mSsp.setChecked(true);
            }
            if ("close".equals(sspStatus)) {
                this.mSsp.setChecked(false);
            }
        }
        TwoStatePreference twoStatePreference2 = this.mBtRfPath;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message get_rf_path = this.mBtHandler.obtainMessage(7);
            this.mBtHandler.sendMessage(get_rf_path);
        }
        if (this.mAdapter.isEnabled()) {
            this.mControllerBqb.setEnabled(false);
            if (SystemPropertiesProxy.get(PROPERTY_HCITOOLS_SERVER, "stopped").equals("stopped")) {
                Log.e(TAG, "HCI SERVER did not start");
            } else {
                int socket_port = Integer.parseInt(SystemPropertiesProxy.get(PROPERTY_HCITOOLS_SOCKET, "0"));
                Log.d(TAG, "bluetooth.hcitools.socket: " + socket_port);
                if (socket_port == 0) {
                    Log.e(TAG, "unknow socket");
                }
            }
        } else {
            this.mControllerBqb.setEnabled(true);
            Message msg = this.mBtHandler.obtainMessage(11);
            this.mBtHandler.sendMessage(msg);
        }
        try {
            if (!this.wcndEngCtl.isWcndEngRunning()) {
                Log.d(TAG, "startWcndEng");
                this.wcndEngCtl.startWcndEng();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mBtHandler.postDelayed(this.runnable, 500L);
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        this.mBtHandler.removeCallbacks(this.runnable);
        try {
            if (this.wcndEngCtl.isWcndEngRunning()) {
                Log.d(TAG, "onDestroy isWcndEngRunning");
                this.wcndEngCtl.stopWcndEng();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object value) throws IllegalAccessException, InvocationTargetException {
        if (pref.getKey().equals(KEY_NO_SSP)) {
            if (this.mSsp.isChecked()) {
                SystemPropertiesProxy.set(PERSIST_BT_NO_SSP, "close");
                this.mSsp.setChecked(false);
            } else {
                SystemPropertiesProxy.set(PERSIST_BT_NO_SSP, "open");
                this.mSsp.setChecked(true);
            }
        } else if (pref.getKey().equals(KEY_BT_EUT)) {
            boolean isBleScanOpen = Settings.Global.getInt(getContentResolver(), SettingsProxy.Global.BLE_SCAN_ALWAYS_AVAILABLE, 0) == 1;
            if (isBleScanOpen) {
                Toast.makeText(this, getString(R.string.operate_bt_eut_hint), 1).show();
                return false;
            }
            this.mBtEUT.setEnabled(false);
            if (this.mBtEUT.isChecked()) {
                Message close_eut = this.mBtHandler.obtainMessage(4);
                this.mBtHandler.sendMessage(close_eut);
            } else {
                Message close_eut2 = this.mBtHandler.obtainMessage(3);
                this.mBtHandler.sendMessage(close_eut2);
            }
        } else if (pref.getKey().equals(KEY_BT_RF_PATH)) {
            this.mBtRfPath.setEnabled(false);
            if (this.mBtRfPath.isChecked()) {
                Message single_path = this.mBtHandler.obtainMessage(8);
                this.mBtHandler.sendMessage(single_path);
            } else {
                Message shared_path = this.mBtHandler.obtainMessage(9);
                this.mBtHandler.sendMessage(shared_path);
            }
        }
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref.getKey().equals(KEY_BT_NOSIGNAL_TEST)) {
            if (this.mBtEUT.isChecked()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.alert_bt_test)).setMessage(getString(R.string.alert_close_bt_eut)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                alertDialog.show();
                return false;
            }
            Intent intent = new Intent(this, (Class<?>) BTNOSIGNALActivity.class);
            startActivity(intent);
            return false;
        }
        if (pref.getKey().equals(KEY_CONTROLLER_BQB)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Controller BQB State Changed ");
            sb.append(this.mControllerBqbState);
            sb.append(" -> ");
            sb.append(!this.mControllerBqbState);
            Log.d(TAG, sb.toString());
            Message msg = this.mBtHandler.obtainMessage(10);
            this.mBtHandler.sendMessage(msg);
            return false;
        }
        return false;
    }

    class BTHandler extends Handler {
        public BTHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, InvocationTargetException {
            boolean isSuccess;
            switch (msg.what) {
                case 2:
                    if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_EUT_STATUS, 2)) {
                        String[] str = BTActivity.this.mCmdRes.split("\\:");
                        String status = str[1].trim();
                        if (status.equals("1")) {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setChecked(true);
                                }
                            });
                        } else {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setChecked(false);
                                }
                            });
                        }
                    } else {
                        Log.d(BTActivity.TAG, "GET_BT_EUT_STATUS Fail");
                        Toast.makeText(BTActivity.this, "GET_BT_EUT_STATUS Fail", 0).show();
                    }
                    break;
                case 3:
                    if (!Const.isMarlin3() && !Const.isMarlin3E()) {
                        if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_EUT_OPEN, 3)) {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setEnabled(true);
                                    BTActivity.this.mBtEUT.setChecked(true);
                                }
                            });
                        } else {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setEnabled(true);
                                    BTActivity.this.mBtEUT.setChecked(false);
                                }
                            });
                        }
                    } else {
                        int pathState = BTActivity.mRfPathPref.getInt("rf_path_state", 1);
                        if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_EUT_OPEN, 3)) {
                            if (pathState == 2) {
                                if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_SHARED_PATH, 9)) {
                                    isSuccess = true;
                                } else {
                                    isSuccess = false;
                                }
                            } else if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_SINGLE_PATH, 8)) {
                                isSuccess = true;
                            } else {
                                isSuccess = false;
                            }
                        } else {
                            isSuccess = false;
                        }
                        if (isSuccess) {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setEnabled(true);
                                    BTActivity.this.mBtEUT.setChecked(true);
                                }
                            });
                        } else {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    BTActivity.this.mBtEUT.setEnabled(true);
                                    BTActivity.this.mBtEUT.setChecked(false);
                                }
                            });
                        }
                    }
                    break;
                case 4:
                    if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_EUT_CLOSE, 4)) {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.7
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtEUT.setEnabled(true);
                                BTActivity.this.mBtEUT.setChecked(false);
                            }
                        });
                    } else {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.8
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtEUT.setEnabled(true);
                                BTActivity.this.mBtEUT.setChecked(true);
                            }
                        });
                    }
                    break;
                case 7:
                    int mRfPathState = BTActivity.mRfPathPref.getInt("rf_path_state", 1);
                    if (mRfPathState == 2) {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.9
                            @Override // java.lang.Runnable
                            public void run() throws IllegalAccessException, InvocationTargetException {
                                BTActivity.this.mBtRfPath.setChecked(true);
                                if (Const.isMarlin3() || Const.isMarlin3E()) {
                                    SystemPropertiesProxy.set(BTActivity.PERSIST_BT_RF_PATH, "2");
                                }
                            }
                        });
                    } else {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.10
                            @Override // java.lang.Runnable
                            public void run() throws IllegalAccessException, InvocationTargetException {
                                BTActivity.this.mBtRfPath.setChecked(false);
                                if (Const.isMarlin3() || Const.isMarlin3E()) {
                                    SystemPropertiesProxy.set(BTActivity.PERSIST_BT_RF_PATH, "1");
                                }
                            }
                        });
                    }
                    break;
                case 8:
                    if (Const.isMarlin3() || Const.isMarlin3E()) {
                        BTActivity.this.mBtRfPath.setEnabled(true);
                        BTActivity.this.mBtRfPath.setChecked(false);
                        BTActivity.mRfPathEditor.putInt("rf_path_state", 1);
                        BTActivity.mRfPathEditor.apply();
                        SystemPropertiesProxy.set(BTActivity.PERSIST_BT_RF_PATH, "1");
                        Log.d(BTActivity.TAG, "**mBtRfPath enabled true checked false 1**");
                    } else if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_SINGLE_PATH, 8)) {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.11
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtRfPath.setEnabled(true);
                                BTActivity.this.mBtRfPath.setChecked(false);
                            }
                        });
                        BTActivity.mRfPathEditor.putInt("rf_path_state", 1);
                        BTActivity.mRfPathEditor.apply();
                    } else {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.12
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtRfPath.setEnabled(true);
                                BTActivity.this.mBtRfPath.setChecked(true);
                            }
                        });
                    }
                    break;
                case 9:
                    if (Const.isMarlin3() || Const.isMarlin3E()) {
                        BTActivity.this.mBtRfPath.setEnabled(true);
                        BTActivity.this.mBtRfPath.setChecked(true);
                        BTActivity.mRfPathEditor.putInt("rf_path_state", 2);
                        BTActivity.mRfPathEditor.apply();
                        SystemPropertiesProxy.set(BTActivity.PERSIST_BT_RF_PATH, "2");
                        Log.d(BTActivity.TAG, "**mBtRfPath enabled true checked true 2**");
                    } else if (BTActivity.this.sendCmd(BTActivity.SOCKET_NAME, BTActivity.CMD_BT_SHARED_PATH, 9)) {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.13
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtRfPath.setEnabled(true);
                                BTActivity.this.mBtRfPath.setChecked(true);
                            }
                        });
                        BTActivity.mRfPathEditor.putInt("rf_path_state", 2);
                        BTActivity.mRfPathEditor.apply();
                    } else {
                        BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.14
                            @Override // java.lang.Runnable
                            public void run() {
                                BTActivity.this.mBtRfPath.setEnabled(true);
                                BTActivity.this.mBtRfPath.setChecked(false);
                            }
                        });
                    }
                    break;
                case 10:
                    try {
                        BTActivity.this.mControllerBqbState = BTActivity.this.btEut.controllerBqbEnable(BTActivity.this.mControllerBqbState ? false : true);
                        if (BTActivity.this.mControllerBqbState) {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.15
                                @Override // java.lang.Runnable
                                public void run() {
                                    Log.d(BTActivity.TAG, "CONTROLLER BQB ENABLE");
                                    BTActivity.this.mControllerBqb.setSummary(BTActivity.CONTROLLER_BQB_ENABLED);
                                }
                            });
                        } else {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.16
                                @Override // java.lang.Runnable
                                public void run() {
                                    Log.d(BTActivity.TAG, "CONTROLLER BQB DISABLE");
                                    BTActivity.this.mControllerBqb.setSummary(BTActivity.CONTROLLER_BQB_DISABLED);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    break;
                case 11:
                    try {
                        BTActivity.this.mControllerBqbState = BTActivity.this.btEut.getControllerBqbState();
                        if (BTActivity.this.mControllerBqbState) {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.17
                                @Override // java.lang.Runnable
                                public void run() {
                                    Log.d(BTActivity.TAG, "CONTROLLER BQB ENABLE");
                                    BTActivity.this.mControllerBqb.setSummary(BTActivity.CONTROLLER_BQB_ENABLED);
                                }
                            });
                        } else {
                            BTActivity.this.mUIThread.post(new Runnable() { // from class: com.sprd.engineermode.connectivity.BT.BTActivity.BTHandler.18
                                @Override // java.lang.Runnable
                                public void run() {
                                    Log.d(BTActivity.TAG, "CONTROLLER BQB DISABLE");
                                    BTActivity.this.mControllerBqb.setSummary(BTActivity.CONTROLLER_BQB_DISABLED);
                                }
                            });
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendCmd(String socketName, String cmd, int msg) {
        Log.d(TAG, "connect socket is " + socketName + ", cmd is " + cmd);
        LocalSocketAddress.Namespace namespace = LocalSocketAddress.Namespace.ABSTRACT;
        StringBuilder sb = new StringBuilder();
        sb.append(PROCESS_NAME);
        sb.append(cmd);
        String res = SocketUtils.sendCmdNoCloseSocket(socketName, namespace, sb.toString());
        this.mCmdRes = res;
        return analyRes(msg, res);
    }

    private boolean analyRes(int msg, String result) {
        Log.d(TAG, "analyRes msg is " + msg + ", result is " + result);
        if (msg == 0) {
            if (result == null || !result.contains("bt_status=0")) {
                return false;
            }
            return true;
        }
        if (msg == 1) {
            if (result == null || !result.contains("bt_status=1")) {
                return false;
            }
            return true;
        }
        if (msg == 2 || msg == 3 || msg == 4) {
            if (result == null || !result.contains("OK")) {
                return false;
            }
            return true;
        }
        if ((msg != 8 && msg != 9) || result == null || !result.contains("OK")) {
            return false;
        }
        return true;
    }
}
