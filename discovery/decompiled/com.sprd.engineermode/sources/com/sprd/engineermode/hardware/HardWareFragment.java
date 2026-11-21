package com.sprd.engineermode.hardware;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMLongClickPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IHardwareApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.CommonUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class HardWareFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int BATTERY_LOWER_LIMIT = 30;
    private static final int BATTERY_UPPER_LIMIT = 90;
    private static final String CAMERA_API = "camera";
    private static final String CAMERA_API_SWITCH = "api_switch";
    private static final String KEY_ASDIV = "asdiv_key";
    private static final String KEY_CAMERA_API = "camera_api";
    private static final String KEY_CAMERA_IDLE_SLEEP = "camera_idle_sleep";
    private static final String KEY_CAMERA_TEST_MODE = "camera_test_mode";
    private static final String KEY_FM_DSP = "fm_dsp";
    private static final String KEY_USB_CHARGE = "usb_charge";
    private static final int MSG_C2K_DPDT_SWITCH_1 = 6;
    private static final int MSG_C2K_DPDT_SWITCH_2 = 7;
    private static final int MSG_CLOSE_USB_CHARGE = 14;
    private static final int MSG_GET_RESET_SETTING = 15;
    private static final int MSG_GET_USB_CHARGE = 12;
    private static final int MSG_GSM_DPDT_SWITCH_1 = 2;
    private static final int MSG_GSM_DPDT_SWITCH_2 = 3;
    private static final int MSG_LTE_DPDT_SWITCH_1 = 0;
    private static final int MSG_LTE_DPDT_SWITCH_2 = 1;
    private static final int MSG_OPEN_USB_CHARGE = 13;
    private static final int MSG_SET_ASDIV_SETTING = 17;
    private static final int MSG_SET_RESET_SETTING = 16;
    private static final int MSG_WCDMA_DPDT_SWITCH_1 = 4;
    private static final int MSG_WCDMA_DPDT_SWITCH_2 = 5;
    private static final String SEND_POWER = "key_sendpower";
    private static final String TAG = "HardWareFragment";
    private SharedPreferences.Editor editor;
    private ListPreference mAsdivSetting;
    private AudioManager mAudioManager;
    private SwitchPreference mCameraIdleSleep;
    private SwitchPreference mCameraTestMode;
    private Context mContext;
    private int mCurrumentAntenna;
    private SwitchPreference mFMThroughDSP;
    private HardwareHandler mHWHandler;
    private BatteryReceiver mReceiver;
    private ListPreference mResetSetting;
    private Preference mSendPower;
    private SharedPreferences mSharePref;
    private TwoStatePreference mUsbCharge;
    private SharedPreferences preferences;
    private Handler mUiThread = new Handler();
    private IHardwareApi hwApi = CoreApi.getHardwareApi();
    private int mOpenLevel = 0;
    private boolean mBelowThirtyToCharge = true;
    private boolean mIsSuccess = true;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private BroadcastReceiver mUsbStateReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.hardware.HardWareFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CommonUtils.USBMANAGER_ACTION_USB_STATE)) {
                HardWareFragment.this.handleUsbStateChanged(intent.getExtras().getBoolean("connected"));
            }
        }
    };

    private class BatteryReceiver extends BroadcastReceiver {
        private BatteryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(HardWareFragment.TAG, "onReceive  action = " + action);
            if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                int extra_level = intent.getIntExtra("level", 0);
                HardWareFragment hardWareFragment = HardWareFragment.this;
                int i = 100;
                if (extra_level < 0) {
                    i = 0;
                } else if (extra_level <= 100) {
                    i = extra_level;
                }
                hardWareFragment.mOpenLevel = i;
                boolean pluged = intent.getIntExtra("plugged", 0) != 0;
                Log.i(HardWareFragment.TAG, "battery level = " + HardWareFragment.this.mOpenLevel + " pluged = " + pluged);
                HardWareFragment.this.handleUsbStateChanged(pluged);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUsbStateChanged(boolean connecting) {
        int message;
        if (!connecting) {
            this.mUsbCharge.setEnabled(false);
            this.mBelowThirtyToCharge = false;
            return;
        }
        this.mUsbCharge.setEnabled(true);
        try {
            Log.d(TAG, "handleUsbStateChanged  getChargeLevel(): " + this.hwApi.chargeApi().getChargeLevel().trim());
            if (this.preferences.getBoolean("charge_func", true)) {
                if (Integer.parseInt(this.hwApi.chargeApi().getChargeLevel().trim()) < 30) {
                    message = 13;
                    this.mBelowThirtyToCharge = true;
                } else if (Integer.parseInt(this.hwApi.chargeApi().getChargeLevel().trim()) > 90) {
                    this.mBelowThirtyToCharge = false;
                    message = 14;
                } else if (!this.mBelowThirtyToCharge) {
                    message = 14;
                } else {
                    message = 13;
                }
            } else {
                message = 13;
            }
            Message setUsbChargeState = this.mHWHandler.obtainMessage(message);
            this.mHWHandler.sendMessage(setUsbChargeState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_hardwaretab);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mHWHandler = new HardwareHandler(ht.getLooper());
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.mContext = getActivity();
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        EMLongClickPreference deSensePll = (EMLongClickPreference) findPreference("de_sense_pll");
        this.mUsbCharge = (TwoStatePreference) findPreference(KEY_USB_CHARGE);
        this.mUsbCharge.setOnPreferenceChangeListener(this);
        this.mAsdivSetting = (ListPreference) findPreference(KEY_ASDIV);
        this.mFMThroughDSP = (SwitchPreference) findPreference(KEY_FM_DSP);
        this.mFMThroughDSP.setOnPreferenceChangeListener(this);
        String mAudioDspExit = this.mAudioManager.getParameters("isAudioDspExist");
        Log.d(TAG, "FM_WITH_DSP isAudioDspExist: " + mAudioDspExit);
        if (mAudioDspExit == null || !mAudioDspExit.trim().equalsIgnoreCase("isAudioDspExist=1")) {
            this.mFMThroughDSP.setEnabled(false);
            this.mFMThroughDSP.setSummary(R.string.feature_not_support);
        }
        boolean isUser = SystemPropertiesProxy.get("ro.build.type").equalsIgnoreCase("user");
        Log.d(TAG, "isUser = " + isUser);
        if (isUser) {
            deSensePll.setEnabled(false);
        }
        this.mCameraIdleSleep = (SwitchPreference) findPreference(KEY_CAMERA_IDLE_SLEEP);
        this.mCameraIdleSleep.setOnPreferenceChangeListener(this);
        this.mCameraTestMode = (SwitchPreference) findPreference(KEY_CAMERA_TEST_MODE);
        this.mCameraTestMode.setOnPreferenceChangeListener(this);
        SharedPreferences mSharePref = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        mSharePref.registerOnSharedPreferenceChangeListener(this);
        this.mResetSetting = (ListPreference) findPreference("reset_setting");
        ListPreference listPreference = this.mResetSetting;
        listPreference.setSummary(listPreference.getEntry());
        this.mAsdivSetting = (ListPreference) findPreference(KEY_ASDIV);
        ListPreference listPreference2 = this.mAsdivSetting;
        listPreference2.setSummary(listPreference2.getEntry());
        this.mSendPower = findPreference(SEND_POWER);
        this.mSendPower.setOnPreferenceChangeListener(this);
        if (!this.teleApi.telephonyInfo().isSupportLte()) {
            this.mSendPower.setEnabled(false);
            this.mSendPower.setSummary(R.string.feature_not_support);
        }
        Log.d(TAG, "mSendPower setOnPreferenceChangeListener");
        this.mContext.registerReceiver(this.mUsbStateReceiver, new IntentFilter(CommonUtils.USBMANAGER_ACTION_USB_STATE));
        Log.d(TAG, "register BatteryReceiver");
        this.mReceiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.BATTERY_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, filter);
        this.preferences = this.mContext.getSharedPreferences("charge_control", 0);
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onStart() {
        TwoStatePreference twoStatePreference = this.mUsbCharge;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            this.mSharePref.getBoolean(KEY_USB_CHARGE, true);
            try {
                Log.d(TAG, "onStart getChargeLevel: " + this.hwApi.chargeApi().getChargeLevel());
                if (this.preferences.getBoolean("charge_func", true)) {
                    this.mUsbCharge.setChecked(true);
                    if (Integer.parseInt(this.hwApi.chargeApi().getChargeLevel().trim()) < 30) {
                        this.mBelowThirtyToCharge = true;
                        Message setUsbChargeState = this.mHWHandler.obtainMessage(13);
                        this.mHWHandler.sendMessage(setUsbChargeState);
                    } else if (Integer.parseInt(this.hwApi.chargeApi().getChargeLevel().trim()) > 90) {
                        Message setUsbChargeState2 = this.mHWHandler.obtainMessage(14);
                        this.mHWHandler.sendMessage(setUsbChargeState2);
                    }
                } else {
                    this.mUsbCharge.setChecked(false);
                    Message setUsbChargeState3 = this.mHWHandler.obtainMessage(13);
                    this.mHWHandler.sendMessage(setUsbChargeState3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SwitchPreference switchPreference = this.mCameraIdleSleep;
        if (switchPreference != null) {
            switchPreference.setChecked(SystemPropertiesProxy.getBoolean("persist.sys.camera.idlesleep", true));
        }
        if (this.mCameraTestMode != null) {
            boolean flag = SystemPropertiesProxy.get("persist.vendor.cam.isp.af.bypass", "0").equals("1");
            this.mCameraTestMode.setChecked(flag);
        }
        if (this.mResetSetting != null) {
            Message getUsbChargeState = this.mHWHandler.obtainMessage(15);
            this.mHWHandler.sendMessage(getUsbChargeState);
        }
        if (this.mFMThroughDSP != null) {
            String mDspStatus = this.mAudioManager.getParameters("FM_WITH_DSP");
            Log.d(TAG, "FM_WITH_DSP status: " + mDspStatus);
            this.mFMThroughDSP.setChecked(mDspStatus.trim().equalsIgnoreCase("FM_WITH_DSP=1"));
        }
        super.onStart();
    }

    boolean checkFileExists() {
        return new File("/system/bin/su").exists() || new File("/system/app/Superuser.apk").exists();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) throws IllegalAccessException, InvocationTargetException {
        int message;
        Log.d(TAG, "onPreferenceChange");
        TwoStatePreference twoStatePreference = this.mUsbCharge;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Message openUsbChargeState = this.mHWHandler.obtainMessage(13);
                this.mHWHandler.sendMessage(openUsbChargeState);
                this.editor = this.preferences.edit();
                this.editor.putBoolean("charge_func", false);
                this.editor.commit();
                this.mUsbCharge.setChecked(false);
            } else {
                try {
                    Log.d(TAG, "onPreferenceChange hwApi.chargeApi().getChargeLevel(): " + this.hwApi.chargeApi().getChargeLevel());
                    if (Integer.parseInt(this.hwApi.chargeApi().getChargeLevel().trim()) < 30) {
                        this.mBelowThirtyToCharge = true;
                        message = 13;
                    } else {
                        this.mBelowThirtyToCharge = false;
                        message = 14;
                    }
                    Message setUsbChargeState = this.mHWHandler.obtainMessage(message);
                    this.mHWHandler.sendMessage(setUsbChargeState);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.editor = this.preferences.edit();
                this.editor.putBoolean("charge_func", true);
                this.editor.commit();
                this.mUsbCharge.setChecked(true);
            }
        } else {
            SwitchPreference switchPreference = this.mCameraIdleSleep;
            if (pref == switchPreference) {
                if (switchPreference.isChecked()) {
                    SystemPropertiesProxy.set("persist.sys.camera.idlesleep", "0");
                    this.mCameraIdleSleep.setChecked(false);
                } else {
                    SystemPropertiesProxy.set("persist.sys.camera.idlesleep", "1");
                    this.mCameraIdleSleep.setChecked(true);
                }
                Log.d(TAG, "idlesleep=" + SystemPropertiesProxy.getBoolean("persist.sys.camera.idlesleep", true));
            } else {
                SwitchPreference switchPreference2 = this.mCameraTestMode;
                if (pref == switchPreference2) {
                    if (switchPreference2.isChecked()) {
                        SystemPropertiesProxy.set("persist.vendor.cam.isp.af.bypass", "0");
                        this.mCameraTestMode.setChecked(false);
                    } else {
                        SystemPropertiesProxy.set("persist.vendor.cam.isp.af.bypass", "1");
                        this.mCameraTestMode.setChecked(true);
                    }
                } else {
                    SwitchPreference switchPreference3 = this.mFMThroughDSP;
                    if (pref == switchPreference3) {
                        if (switchPreference3.isChecked()) {
                            this.mAudioManager.setParameters("FM_WITH_DSP=0");
                            this.mFMThroughDSP.setChecked(false);
                        } else {
                            this.mAudioManager.setParameters("FM_WITH_DSP=1");
                            this.mFMThroughDSP.setChecked(true);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override // android.preference.PreferenceFragment
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getKey().endsWith(SEND_POWER)) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.send_power)).setMessage(getString(R.string.send_power_dailog)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.HardWareFragment.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HardWareFragment.this.mContext, (Class<?>) SendPowerPrefActivity.class);
                    HardWareFragment.this.startActivity(intent);
                }
            }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.HardWareFragment.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("reset_setting")) {
            Message openUsbChargeState = this.mHWHandler.obtainMessage(16, Integer.parseInt(this.mResetSetting.getValue()), 0);
            this.mHWHandler.sendMessage(openUsbChargeState);
        } else if (key.equals(KEY_ASDIV)) {
            try {
                Message openAsdivSetting = this.mHWHandler.obtainMessage(17, Integer.parseInt(this.mAsdivSetting.getValue()), 0);
                this.mHWHandler.sendMessage(openAsdivSetting);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    class HardwareHandler extends Handler {
        public HardwareHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, InvocationTargetException {
            switch (msg.what) {
                case 12:
                    String chargeStatus = HardWareFragment.this.readFile("/sys/class/power_supply/usb/online");
                    Log.d(HardWareFragment.TAG, "usb charge status is " + chargeStatus);
                    if ("readError".equals(chargeStatus) || chargeStatus == null) {
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                HardWareFragment.this.mUsbCharge.setEnabled(false);
                                HardWareFragment.this.mUsbCharge.setSummary(R.string.feature_abnormal);
                            }
                        });
                    } else if (chargeStatus.contains("0")) {
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                            }
                        });
                    } else if (chargeStatus.contains("1")) {
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.2
                            @Override // java.lang.Runnable
                            public void run() {
                            }
                        });
                    } else {
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                HardWareFragment.this.mUsbCharge.setEnabled(false);
                                HardWareFragment.this.mUsbCharge.setSummary(R.string.feature_abnormal);
                            }
                        });
                    }
                    break;
                case 13:
                    try {
                        HardWareFragment.this.hwApi.chargeApi().openUsbCharge();
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                SharedPreferences.Editor editor = HardWareFragment.this.mSharePref.edit();
                                editor.putBoolean(HardWareFragment.KEY_USB_CHARGE, true);
                                editor.commit();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    break;
                case 14:
                    try {
                        HardWareFragment.this.hwApi.chargeApi().closeUsbCharge();
                        HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.HardwareHandler.6
                            @Override // java.lang.Runnable
                            public void run() {
                                SharedPreferences.Editor editor = HardWareFragment.this.mSharePref.edit();
                                editor.putBoolean(HardWareFragment.KEY_USB_CHARGE, false);
                                editor.commit();
                            }
                        });
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                    break;
                case 15:
                    final String reset = SystemPropertiesProxy.get("persist.vendor.eng.reset", "0");
                    Log.d(HardWareFragment.TAG, "get reset state: " + reset);
                    HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$HardWareFragment$HardwareHandler$W5c4_Xk2_-Ctc1XCGzpFidiwAOY
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$0$HardWareFragment$HardwareHandler(reset);
                        }
                    });
                    break;
                case 16:
                    final int asdivType = msg.arg1;
                    HardWareFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.-$$Lambda$HardWareFragment$HardwareHandler$Tnq6oLbxHcsdZzieFShGP2CysoI
                        @Override // java.lang.Runnable
                        public final void run() throws IllegalAccessException, InvocationTargetException {
                            this.f$0.lambda$handleMessage$1$HardWareFragment$HardwareHandler(asdivType);
                        }
                    });
                    break;
                case 17:
                    int asdivType2 = msg.arg1;
                    Log.d(HardWareFragment.TAG, "MSG_SET_ASDIV_SETTING  asdivType: " + asdivType2);
                    HardWareFragment.this.setAsdiv(asdivType2);
                    break;
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$HardWareFragment$HardwareHandler(String reset) {
            HardWareFragment.this.mResetSetting.setValue(reset);
            HardWareFragment.this.mResetSetting.setSummary(HardWareFragment.this.mResetSetting.getEntry());
        }

        public /* synthetic */ void lambda$handleMessage$1$HardWareFragment$HardwareHandler(int type) throws IllegalAccessException, InvocationTargetException {
            HardWareFragment.this.mResetSetting.setSummary(HardWareFragment.this.mResetSetting.getEntry());
            SystemPropertiesProxy.set("persist.vendor.eng.reset", String.valueOf(type));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAsdiv(final int mChooseAntenna) {
        this.mIsSuccess = true;
        try {
            this.hwApi.asdivApi().closeAllAntennas();
        } catch (Exception e) {
            e.printStackTrace();
            this.mIsSuccess = false;
        }
        switch (mChooseAntenna) {
            case 0:
                try {
                    this.hwApi.asdivApi().setLteToAntenna1();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 1:
                try {
                    this.hwApi.asdivApi().setLteToAntenna2();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 2:
                try {
                    this.hwApi.asdivApi().setGsmToAntenna1();
                } catch (Exception e4) {
                    e4.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 3:
                try {
                    this.hwApi.asdivApi().setGsmToAntenna2();
                } catch (Exception e5) {
                    e5.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 4:
                try {
                    this.hwApi.asdivApi().setWcdmaToAntenna1();
                } catch (Exception e6) {
                    e6.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 5:
                try {
                    this.hwApi.asdivApi().setWcdmaToAntenna2();
                } catch (Exception e7) {
                    e7.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 6:
                try {
                    this.hwApi.asdivApi().setC2kToAntenna1();
                } catch (Exception e8) {
                    e8.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
            case 7:
                try {
                    this.hwApi.asdivApi().setC2kToAntenna2();
                } catch (Exception e9) {
                    e9.printStackTrace();
                    this.mIsSuccess = false;
                }
                break;
        }
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.hardware.HardWareFragment.4
            @Override // java.lang.Runnable
            public void run() {
                if (HardWareFragment.this.mIsSuccess) {
                    HardWareFragment.this.mAsdivSetting.setValueIndex(mChooseAntenna);
                    HardWareFragment.this.mAsdivSetting.setSummary(HardWareFragment.this.mAsdivSetting.getEntry());
                    HardWareFragment.this.mCurrumentAntenna = mChooseAntenna;
                    return;
                }
                HardWareFragment.this.mAsdivSetting.setValueIndex(HardWareFragment.this.mCurrumentAntenna);
                Toast.makeText(HardWareFragment.this.mContext, "Unsupported", 0).show();
            }
        });
    }

    public String readFile(String path) {
        File file = new File(path);
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            try {
                try {
                    reader = new BufferedReader(new FileReader(file));
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "Read file error!!!");
                    sb.append("readError");
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.d(TAG, "read " + path + " value is " + sb.toString().trim());
            return sb.toString().trim();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onDestroy() {
        this.mContext.unregisterReceiver(this.mUsbStateReceiver);
        BatteryReceiver batteryReceiver = this.mReceiver;
        if (batteryReceiver != null) {
            this.mContext.unregisterReceiver(batteryReceiver);
            this.mReceiver = null;
        }
        super.onDestroy();
    }
}
