package com.sprd.engineermode.debuglog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.LocalSocketAddress;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.hardware.PhaseCheckParse;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.IDebugLogApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import com.unisoc.engineermode.core.utils.ShellUtils;
import com.unisoc.engineermode.core.utils.SocketUtils;
import io.reactivex.annotations.SchedulerSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DebugLogFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    private static final String CABC_PATH = "/sys/module/dpu_r4p0/parameters/cabc_disable";
    private static final int CLOSE_AVS_LOG = 2;
    private static final int CLOSE_USER_MODE = 5;
    private static final int FALLBACK_SCREEN_TIMEOUT_VALUE = 30000;
    private static final int GET_AVS_LOG = 0;
    private static final int GET_JEITA_TYPE = 16;
    private static final int GET_LVDS_DUMP_TYPE = 15;
    private static final int GET_USER_MODE = 3;
    private static final String HARDWARE_VERSION_KEYWORD = "vendor.sys.hardware.version";
    private static final String KEY_AGPS_LOG = "agps_log";
    private static final String KEY_AP_VERSION = "ap_version";
    private static final String KEY_AT_DIAG = "at_diag";
    private static final String KEY_AVS_LOG = "avs_log";
    private static final String KEY_CABC_TEST = "cabc_test";
    private static final String KEY_CMD_TYPE = "cmd_type";
    private static final String KEY_CP2_VERSION = "cp2_version";
    private static final String KEY_DSP_VERSION = "dsp_version";
    private static final String KEY_GPS_CONFIG = "gps_config";
    private static final String KEY_GPS_VERSION = "gps_version";
    private static final String KEY_HARDWARE_VERSION = "hardware_version";
    private static final String KEY_HARDWARE_VERSION2 = "hardware_version2";
    private static final String KEY_JEITA_CONTROL = "jeita_control";
    private static final String KEY_KERNEL_LOG_LEVEL_SWITCH = "log_level_switch";
    private static final String KEY_LVDS_DUMP = "lvds_dump";
    private static final String KEY_LVDS_DUMP_GROUP = "persist.sys.lvds.dump";
    private static final String KEY_MODEM_VERSION = "modem_version";
    private static final String KEY_PRECHECKOUT_TEST = "precheck_test";
    private static final String KEY_PS_VERSION = "ps_version";
    private static final String KEY_RPSCPU_CHECK_X86_SOCK_FLOWS = "/proc/sys/net/core/rps_sock_flow_entries";
    private static final String KEY_RPSCPU_HOTPLUG_PATH = "/sys/devices/system/cpu/cpuhotplug/dynamic_load_disable";
    private static final String KEY_RPSSWITCH_ENABLED = "rps_switch";
    private static final String KEY_SCREEN_OFF = "switch_screen_off_never";
    private static final String KEY_SYSTEM_SETTINGS = "system_settings";
    private static final String KEY_SYSTEM_UPDATE = "system_update";
    private static final String KEY_TP_VERSION = "tp_version";
    private static final String KEY_USB_DEBUG_SWITCH = "switch_usbdebug";
    private static final String KEY_USEER_MODE = "switch_user_mode";
    private static final String KEY_WATCH_DOG = "switch_watch_dog";
    private static final int OPEN_AVS_LOG = 1;
    private static final int OPEN_USER_MODE = 4;
    private static final String PROCESS_NAME = "wcnd ";
    private static final int SCREEN_OFF_NEVER_TIME = 360000000;
    public static final String SCREEN_OFF_TIME = "screen_off_time";
    private static final int SET_JEITA_TYPE = 17;
    private static final int SET_LVDS_DUMP_TYPE = 14;
    private static final String SOCKET_NAME = "hidl_common_socket";
    private static final String TAG = "DebugLogFragment";
    private static final String WATCH_DOG_PATH = "/dev/sctl_pm";
    public static SharedPreferences mDataTestPref;
    private Preference mAGpsLog;
    private Preference mAPVersion;
    private TwoStatePreference mAVSLogSwitch;
    private TwoStatePreference mAtDiag;
    private SwitchPreference mCabcTestSwitch;
    private Context mContext;
    private DEGHandler mDEGHandler;
    private Preference mGpsConfig;
    private int mGroupIndex;
    private Preference mHardwareVersion;
    private Preference mHardwareVersion2;
    private TwoStatePreference mJeitaControl;
    private TwoStatePreference mKernelLogLevel;
    private Preference mLVDSDump;
    private int mLVDSDumpIndex;
    private Preference mPreCheckTest;
    private SharedPreferences mPrefs;
    private TwoStatePreference mRpsSwitch;
    private TwoStatePreference mScreenOff;
    private long mSettingsTimeout;
    private Preference mSystemSet;
    private Preference mSystemUpdate;
    private TwoStatePreference mUsbDebug;
    private TwoStatePreference mUserMode;
    private TwoStatePreference mWatchDog;
    private Handler mUiThread = new Handler();
    private String[] mDumpGroup = {"Close LVDS DUMP", "Open LVDS DUMP upstream", "Open LVDS DUMP downstream", "Open LVDS DUMP upstream and downstream"};
    private int mRpsSwitchChipIndex = 0;
    private IDebugLogApi debugLogApi = CoreApi.getDebugLogApi();

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    @SuppressLint({"HandlerLeak"})
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_debuglogtab);
        this.mContext = getActivity();
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mDEGHandler = new DEGHandler(ht.getLooper());
        this.mGpsConfig = findPreference(KEY_GPS_CONFIG);
        this.mGpsConfig.setOnPreferenceClickListener(this);
        this.mAGpsLog = findPreference(KEY_AGPS_LOG);
        this.mAGpsLog.setOnPreferenceClickListener(this);
        this.mGpsConfig.setEnabled(false);
        this.mAGpsLog.setEnabled(false);
        this.mSystemSet = findPreference(KEY_SYSTEM_SETTINGS);
        this.mPreCheckTest = findPreference(KEY_PRECHECKOUT_TEST);
        this.mPreCheckTest.setOnPreferenceClickListener(this);
        this.mAVSLogSwitch = (TwoStatePreference) findPreference(KEY_AVS_LOG);
        this.mAVSLogSwitch.setOnPreferenceChangeListener(this);
        this.mUsbDebug = (TwoStatePreference) findPreference(KEY_USB_DEBUG_SWITCH);
        this.mUsbDebug.setOnPreferenceChangeListener(this);
        this.mUserMode = (TwoStatePreference) findPreference(KEY_USEER_MODE);
        this.mUserMode.setOnPreferenceChangeListener(this);
        this.mUserMode.setEnabled(false);
        this.mUserMode.setSummary(R.string.feature_not_support);
        this.mScreenOff = (TwoStatePreference) findPreference(KEY_SCREEN_OFF);
        this.mScreenOff.setOnPreferenceChangeListener(this);
        this.mWatchDog = (TwoStatePreference) findPreference(KEY_WATCH_DOG);
        this.mWatchDog.setOnPreferenceChangeListener(this);
        this.mSystemUpdate = findPreference(KEY_SYSTEM_UPDATE);
        this.mKernelLogLevel = (TwoStatePreference) findPreference(KEY_KERNEL_LOG_LEVEL_SWITCH);
        this.mKernelLogLevel.setOnPreferenceChangeListener(this);
        this.mAtDiag = (TwoStatePreference) findPreference(KEY_AT_DIAG);
        this.mAtDiag.setOnPreferenceChangeListener(this);
        this.mHardwareVersion = findPreference(KEY_HARDWARE_VERSION);
        this.mHardwareVersion2 = findPreference(KEY_HARDWARE_VERSION2);
        this.mAPVersion = findPreference(KEY_AP_VERSION);
        if (Const.isUser()) {
            this.mAPVersion.setSummary(SystemPropertiesProxy.get("ro.build.description"));
        } else {
            this.mAPVersion.setSummary(Build.DISPLAY);
        }
        Preference psVersion = findPreference(KEY_PS_VERSION);
        psVersion.setOnPreferenceClickListener(this);
        Preference dspVersion = findPreference(KEY_DSP_VERSION);
        dspVersion.setOnPreferenceClickListener(this);
        Preference cp2Version = findPreference(KEY_CP2_VERSION);
        cp2Version.setOnPreferenceClickListener(this);
        Preference tpVersion = findPreference(KEY_TP_VERSION);
        tpVersion.setOnPreferenceClickListener(this);
        Preference modemVersion = findPreference(KEY_MODEM_VERSION);
        modemVersion.setOnPreferenceClickListener(this);
        this.mRpsSwitch = (TwoStatePreference) findPreference(KEY_RPSSWITCH_ENABLED);
        this.mRpsSwitch.setOnPreferenceChangeListener(this);
        this.mCabcTestSwitch = (SwitchPreference) findPreference(KEY_CABC_TEST);
        this.mCabcTestSwitch.setOnPreferenceChangeListener(this);
        if (SystemPropertiesProxy.get("ro.board.platform").equals("sp9850ka")) {
            this.mRpsSwitchChipIndex = 1;
        } else if (SystemPropertiesProxy.get("ro.board.platform").equals("sp9853i")) {
            this.mRpsSwitchChipIndex = 2;
        } else if (SystemPropertiesProxy.get("ro.board.platform").equals("sc9850kh")) {
            this.mRpsSwitchChipIndex = 3;
        } else if (SystemPropertiesProxy.get("ro.board.platform").equals("sp9863a")) {
            this.mRpsSwitchChipIndex = 4;
        } else {
            this.mRpsSwitch.setEnabled(false);
            this.mRpsSwitch.setSummary(R.string.feature_not_support);
            this.mRpsSwitch = null;
            this.mRpsSwitchChipIndex = 0;
        }
        if (this.mRpsSwitch != null) {
            if (allCpuOnline()) {
                this.mRpsSwitch.setChecked(true);
                this.mRpsSwitch.setSummary("On");
            } else {
                this.mRpsSwitch.setChecked(false);
                this.mRpsSwitch.setSummary("Off");
            }
        }
        if (this.mRpsSwitch != null) {
            mDataTestPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int mDataTestState = mDataTestPref.getInt("test_mode_state", 0);
            if (mDataTestState != 1) {
                this.mRpsSwitch.setEnabled(true);
            } else {
                this.mRpsSwitch.setEnabled(false);
            }
        }
        this.mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        long screenTime = this.mPrefs.getLong(SCREEN_OFF_TIME, -1L);
        this.mSettingsTimeout = Settings.System.getLong(getActivity().getContentResolver(), "screen_off_timeout", 30000L);
        if (screenTime == -1 || (screenTime != -1 && this.mSettingsTimeout != screenTime)) {
            saveScreenTime();
        }
        this.mLVDSDump = findPreference(KEY_LVDS_DUMP);
        String mLVDSDumpStr = SystemPropertiesProxy.get(KEY_LVDS_DUMP_GROUP, "");
        if (!mLVDSDumpStr.equals("")) {
            this.mLVDSDump.setSummary(mLVDSDumpStr);
        } else {
            this.mLVDSDump.setSummary("Close LVDS DUMP");
        }
        this.mLVDSDump.setOnPreferenceClickListener(this);
        this.mJeitaControl = (TwoStatePreference) findPreference(KEY_JEITA_CONTROL);
        this.mJeitaControl.setOnPreferenceChangeListener(this);
        getPreferenceScreen().removePreference(findPreference(KEY_USEER_MODE));
        getPreferenceScreen().removePreference(findPreference(KEY_AVS_LOG));
        getPreferenceScreen().removePreference(findPreference(KEY_GPS_CONFIG));
        getPreferenceScreen().removePreference(findPreference(KEY_AGPS_LOG));
        getPreferenceScreen().removePreference(findPreference(KEY_KERNEL_LOG_LEVEL_SWITCH));
    }

    public void saveScreenTime() {
        if (((int) this.mSettingsTimeout) == SCREEN_OFF_NEVER_TIME) {
            return;
        }
        SharedPreferences.Editor editor = this.mPrefs.edit();
        editor.putLong(SCREEN_OFF_TIME, this.mSettingsTimeout);
        editor.apply();
    }

    public int getKernelLogLevelState() {
        int logLevelState = new PhaseCheckParse().getKernelLogLevelState();
        if (logLevelState == 1) {
            return 1;
        }
        return 0;
    }

    private boolean allCpuOnline() {
        int i = this.mRpsSwitchChipIndex;
        if (i == 1) {
            String strValue = readFile(KEY_RPSCPU_HOTPLUG_PATH);
            String strValue2 = readFile(KEY_RPSCPU_CHECK_X86_SOCK_FLOWS);
            return ("0".equals(strValue) || !"1".equals(strValue) || "0".equals(strValue2) || "readError".equals(strValue2)) ? false : true;
        }
        if (i != 2 && i != 3 && i != 4) {
            return false;
        }
        String strValue3 = readFile(KEY_RPSCPU_CHECK_X86_SOCK_FLOWS);
        return ("0".equals(strValue3) || "readError".equals(strValue3)) ? false : true;
    }

    public boolean setKernelLogLevelState(int state) {
        return new PhaseCheckParse().setKernelLogLevelState(state);
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onStart() throws Exception {
        int state = getKernelLogLevelState();
        Log.d(TAG, "kernel log level state value is: " + state);
        if (state == 1) {
            this.mKernelLogLevel.setChecked(true);
        } else {
            this.mKernelLogLevel.setChecked(false);
        }
        if (!checkApkExist("com.sprd.systemupdate")) {
            this.mSystemUpdate.setEnabled(false);
            this.mSystemUpdate.setSummary(R.string.apk_not_exist);
        }
        if (SystemPropertiesProxy.get("ro.board.board.customer", SchedulerSupport.NONE).equalsIgnoreCase("cgmobile")) {
            this.mHardwareVersion.setSummary(SystemPropertiesProxy.get("ro.product.hardware", "P1"));
        } else {
            this.mHardwareVersion.setSummary(SystemPropertiesProxy.get("ro.product.hardware", "UNISOC"));
        }
        Preference preference = this.mHardwareVersion2;
        if (preference != null) {
            preference.setSummary(SystemPropertiesProxy.get(HARDWARE_VERSION_KEYWORD, "unsupported"));
        }
        TwoStatePreference twoStatePreference = this.mAVSLogSwitch;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            Message getAvslog = this.mDEGHandler.obtainMessage(0);
            this.mDEGHandler.sendMessage(getAvslog);
        }
        TwoStatePreference twoStatePreference2 = this.mJeitaControl;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message getJeitaStatus = this.mDEGHandler.obtainMessage(16);
            this.mDEGHandler.sendMessage(getJeitaStatus);
        }
        TwoStatePreference twoStatePreference3 = this.mUserMode;
        if (twoStatePreference3 != null && twoStatePreference3.isEnabled()) {
            Message getUserMode = this.mDEGHandler.obtainMessage(3);
            this.mDEGHandler.sendMessage(getUserMode);
        }
        if (!this.mScreenOff.isChecked()) {
            this.mScreenOff.setSummary(R.string.close_screen_off_never);
        } else {
            this.mScreenOff.setSummary((CharSequence) null);
        }
        Preference preference2 = this.mLVDSDump;
        if (preference2 != null && preference2.isEnabled()) {
            Message mLVDSDumpType = this.mDEGHandler.obtainMessage(15);
            this.mDEGHandler.sendMessage(mLVDSDumpType);
        }
        TwoStatePreference twoStatePreference4 = this.mAtDiag;
        if (twoStatePreference4 != null && twoStatePreference4.isEnabled()) {
            this.mAtDiag.setChecked(this.debugLogApi.atDiagApi().isAtDiagEnabled());
        }
        String cabcState = ShellUtils.execShellCmd("cat /sys/module/dpu_r4p0/parameters/cabc_disable");
        this.mCabcTestSwitch.setChecked("0".equals(cabcState.trim()));
        this.mUsbDebug.setChecked(Settings.Global.getInt(getActivity().getContentResolver(), "adb_enabled", 0) != 0);
        super.onStart();
    }

    @Override // android.app.Fragment
    public void onResume() {
        long mCurrentTime = Settings.System.getLong(getActivity().getContentResolver(), "screen_off_timeout", 30000L);
        if (((int) mCurrentTime) != SCREEN_OFF_NEVER_TIME) {
            this.mScreenOff.setChecked(false);
            this.mScreenOff.setSummary(R.string.close_screen_off_never);
        }
        this.mSystemSet.setEnabled(true);
        this.mSystemSet.setSummary("");
        Log.d(TAG, "sysdump:" + SystemPropertiesProxy.get("persist.vendor.sysdump", "off"));
        if (!Const.isUser()) {
            this.mWatchDog.setChecked(false);
            this.mWatchDog.setEnabled(false);
        } else {
            this.mWatchDog.setEnabled(true);
            this.mWatchDog.setChecked("on".equals(SystemPropertiesProxy.get("persist.vendor.modem.spwdt", "on")));
        }
        super.onResume();
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onDestroy() {
        DEGHandler dEGHandler = this.mDEGHandler;
        if (dEGHandler != null) {
            dEGHandler.getLooper().quit();
            Log.d(TAG, "HandlerThread has quit");
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref == this.mPreCheckTest) {
            startActivity(new Intent(getActivity(), (Class<?>) PreCheckApkActivity.class));
            return true;
        }
        if (pref == this.mGpsConfig) {
            Intent intent = new Intent();
            intent.setAction("android.settings.GPS_CONFIG");
            startActivity(intent);
            return true;
        }
        if (pref == this.mAGpsLog) {
            Intent intent2 = new Intent();
            intent2.setAction("android.settings.AGPS_LOG_SHOW");
            startActivity(intent2);
            return true;
        }
        if (pref.getKey().equals(KEY_MODEM_VERSION)) {
            Intent intent3 = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_CMD_TYPE, KEY_MODEM_VERSION);
            intent3.putExtras(bundle);
            intent3.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent3);
            return true;
        }
        if (pref.getKey().equals(KEY_PS_VERSION)) {
            Intent intent4 = new Intent();
            Bundle bundle2 = new Bundle();
            bundle2.putString(KEY_CMD_TYPE, KEY_PS_VERSION);
            intent4.putExtras(bundle2);
            intent4.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent4);
            return true;
        }
        if (pref.getKey().equals(KEY_DSP_VERSION)) {
            Intent intent5 = new Intent();
            Bundle bundle3 = new Bundle();
            bundle3.putString(KEY_CMD_TYPE, KEY_DSP_VERSION);
            intent5.putExtras(bundle3);
            intent5.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent5);
            return true;
        }
        if (pref.getKey().equals(KEY_CP2_VERSION)) {
            Intent intent6 = new Intent();
            Bundle bundle4 = new Bundle();
            bundle4.putString(KEY_CMD_TYPE, KEY_CP2_VERSION);
            intent6.putExtras(bundle4);
            intent6.setFlags(262144);
            intent6.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent6);
            return true;
        }
        if (pref.getKey().equals(KEY_GPS_VERSION)) {
            Intent intent7 = new Intent();
            Bundle bundle5 = new Bundle();
            bundle5.putString(KEY_CMD_TYPE, KEY_GPS_VERSION);
            intent7.putExtras(bundle5);
            intent7.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent7);
            return true;
        }
        if (pref.getKey().equals(KEY_TP_VERSION)) {
            Intent intent8 = new Intent();
            Bundle bundle6 = new Bundle();
            bundle6.putString(KEY_CMD_TYPE, KEY_TP_VERSION);
            intent8.putExtras(bundle6);
            intent8.setClass(this.mContext, VersionInfoActivity.class);
            startActivity(intent8);
            return true;
        }
        if (pref == this.mLVDSDump) {
            popDialog();
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String sendAt(String cmd, String servername) {
        String res = IATUtils.sendATCmd(cmd, servername);
        Log.d(TAG, "ATCmd is " + cmd + ", result is " + res);
        if (res != null) {
            return res;
        }
        return "FAILED";
    }

    private void popDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this.mContext);
        this.mGroupIndex = getLVDSSingleChoiceIndex(SystemPropertiesProxy.get(KEY_LVDS_DUMP_GROUP, ""));
        alertBuilder.setTitle(getString(R.string.lvds_dump)).setSingleChoiceItems(this.mDumpGroup, this.mGroupIndex, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                if (which >= 0) {
                    DebugLogFragment.this.mGroupIndex = which;
                    Log.d(DebugLogFragment.TAG, "mGroupIndex: " + DebugLogFragment.this.mGroupIndex);
                    Message mLVDSDumpType = DebugLogFragment.this.mDEGHandler.obtainMessage(14, Integer.valueOf(DebugLogFragment.this.mGroupIndex));
                    DebugLogFragment.this.mDEGHandler.sendMessage(mLVDSDumpType);
                    DebugLogFragment.this.mLVDSDump.setSummary(DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mGroupIndex]);
                    SystemPropertiesProxy.set(DebugLogFragment.KEY_LVDS_DUMP_GROUP, DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mGroupIndex]);
                }
                dialog.dismiss();
            }
        });
        final AlertDialog mAlertDialog = alertBuilder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create();
        mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.2
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == 4 && event.getAction() == 1) {
                    mAlertDialog.dismiss();
                    return false;
                }
                return false;
            }
        });
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object objValue) throws IllegalAccessException, InvocationTargetException {
        TwoStatePreference twoStatePreference = this.mKernelLogLevel;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                boolean retValueOn = setKernelLogLevelState(0);
                Log.d(TAG, "retValueOn =: " + retValueOn);
            } else {
                boolean retValueOff = setKernelLogLevelState(1);
                Log.d(TAG, "retValueOff =: " + retValueOff);
            }
            return true;
        }
        TwoStatePreference twoStatePreference2 = this.mAVSLogSwitch;
        if (pref == twoStatePreference2) {
            if (!twoStatePreference2.isChecked()) {
                Message openAvslog = this.mDEGHandler.obtainMessage(1);
                this.mDEGHandler.sendMessage(openAvslog);
            } else {
                Message closeAvslog = this.mDEGHandler.obtainMessage(2);
                this.mDEGHandler.sendMessage(closeAvslog);
            }
        } else {
            TwoStatePreference twoStatePreference3 = this.mUserMode;
            if (pref == twoStatePreference3) {
                if (!twoStatePreference3.isChecked()) {
                    Message openUserMode = this.mDEGHandler.obtainMessage(4);
                    this.mDEGHandler.sendMessage(openUserMode);
                } else {
                    Message closeUserMode = this.mDEGHandler.obtainMessage(5);
                    this.mDEGHandler.sendMessage(closeUserMode);
                }
            } else {
                TwoStatePreference twoStatePreference4 = this.mScreenOff;
                if (pref == twoStatePreference4) {
                    if (!twoStatePreference4.isChecked()) {
                        this.mSettingsTimeout = Settings.System.getLong(getActivity().getContentResolver(), "screen_off_timeout", 30000L);
                        saveScreenTime();
                        Settings.System.putInt(getActivity().getContentResolver(), "screen_off_timeout", SCREEN_OFF_NEVER_TIME);
                        this.mScreenOff.setSummary((CharSequence) null);
                    } else {
                        Settings.System.putInt(getActivity().getContentResolver(), "screen_off_timeout", (int) this.mSettingsTimeout);
                        this.mScreenOff.setSummary(R.string.close_screen_off_never);
                    }
                } else {
                    TwoStatePreference twoStatePreference5 = this.mWatchDog;
                    if (pref == twoStatePreference5) {
                        if (twoStatePreference5.isChecked()) {
                            SystemPropertiesProxy.set("persist.vendor.modem.spwdt", "off");
                            ShellUtils.writeToFile(WATCH_DOG_PATH, "watchdog off");
                        } else {
                            SystemPropertiesProxy.set("persist.vendor.modem.spwdt", "on");
                            ShellUtils.writeToFile(WATCH_DOG_PATH, "watchdog on");
                        }
                    } else {
                        TwoStatePreference twoStatePreference6 = this.mRpsSwitch;
                        if (pref == twoStatePreference6) {
                            if (twoStatePreference6.isChecked()) {
                                SystemPropertiesProxy.set("ctl.start", "vendor.rps_off");
                                this.mRpsSwitch.setSummary("Off");
                                Log.d(TAG, "close rps now.");
                            } else {
                                SystemPropertiesProxy.set("ctl.start", "vendor.rps_on");
                                this.mRpsSwitch.setSummary("On");
                                Log.d(TAG, "open rps now.");
                            }
                        } else {
                            TwoStatePreference twoStatePreference7 = this.mUsbDebug;
                            if (pref == twoStatePreference7) {
                                if (!twoStatePreference7.isChecked()) {
                                    openDebugMode();
                                } else {
                                    closeDebugMode();
                                }
                                return true;
                            }
                            TwoStatePreference twoStatePreference8 = this.mJeitaControl;
                            if (pref == twoStatePreference8) {
                                Message setJeitaType = this.mDEGHandler.obtainMessage(17, twoStatePreference8.isChecked() ? "0" : "1");
                                this.mDEGHandler.sendMessage(setJeitaType);
                                return true;
                            }
                            TwoStatePreference twoStatePreference9 = this.mAtDiag;
                            if (pref == twoStatePreference9) {
                                if (twoStatePreference9.isChecked()) {
                                    try {
                                        this.debugLogApi.atDiagApi().closeAtDiag();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        this.debugLogApi.atDiagApi().openAtDiag();
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            } else {
                                SwitchPreference switchPreference = this.mCabcTestSwitch;
                                if (pref == switchPreference) {
                                    boolean enableCabc = !switchPreference.isChecked();
                                    try {
                                        this.debugLogApi.cabcApi().set(enableCabc);
                                        this.mCabcTestSwitch.setChecked(enableCabc);
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void openDebugMode() {
        Settings.Global.putInt(getActivity().getContentResolver(), "development_settings_enabled", 1);
        Settings.Global.putInt(getActivity().getContentResolver(), "adb_enabled", 1);
    }

    private void closeDebugMode() {
        Settings.Global.putInt(getActivity().getContentResolver(), "adb_enabled", 0);
        Settings.Global.putInt(getActivity().getContentResolver(), "development_settings_enabled", 0);
    }

    class DEGHandler extends Handler {
        public DEGHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                String avsStatus = DebugLogFragment.this.readFile("/sys/power/avs_log");
                Log.d(DebugLogFragment.TAG, "AVS Log status is " + avsStatus);
                if ("readError".equals(avsStatus) || avsStatus == null) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setEnabled(false);
                            DebugLogFragment.this.mAVSLogSwitch.setSummary(R.string.feature_abnormal);
                        }
                    });
                }
                if (avsStatus.contains("1")) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(true);
                        }
                    });
                    return;
                } else if (avsStatus.contains("0")) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(false);
                        }
                    });
                    return;
                } else {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setEnabled(false);
                            DebugLogFragment.this.mAVSLogSwitch.setSummary(R.string.feature_abnormal);
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                String res = DebugLogFragment.this.execShellStr("echo 1 > /sys/power/avs_log");
                String curstatus = DebugLogFragment.this.readFile("/sys/power/avs_log");
                Log.d(DebugLogFragment.TAG, "openavslog result is " + res + ", curstatus is " + curstatus);
                if (curstatus.contains("1")) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(true);
                        }
                    });
                    return;
                } else {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(false);
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                String clores = DebugLogFragment.this.execShellStr("echo 0 > /sys/power/avs_log");
                String curstatus1 = DebugLogFragment.this.readFile("/sys/power/avs_log");
                Log.d(DebugLogFragment.TAG, "closeavslog result is " + clores + ", curstatus is " + curstatus1);
                if (curstatus1.contains("0")) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(false);
                        }
                    });
                    return;
                } else {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.8
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mAVSLogSwitch.setChecked(true);
                        }
                    });
                    return;
                }
            }
            if (i == 3) {
                String res2 = SocketUtils.sendCmdAndRecResult(DebugLogFragment.SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn at+cp2_enter_user?");
                Log.d(DebugLogFragment.TAG, "UserMode status is " + res2);
                if (res2 == null || (!res2.contains("OK") && !res2.contains("Ok") && !res2.contains("oK") && !res2.contains("ok"))) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.11
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setEnabled(false);
                            DebugLogFragment.this.mUserMode.setSummary(R.string.feature_abnormal);
                        }
                    });
                    return;
                } else if (res2.contains("0")) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.9
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setChecked(false);
                        }
                    });
                    return;
                } else {
                    if (res2.contains("1")) {
                        DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.10
                            @Override // java.lang.Runnable
                            public void run() {
                                DebugLogFragment.this.mUserMode.setChecked(true);
                            }
                        });
                        return;
                    }
                    return;
                }
            }
            if (i == 4) {
                String res3 = SocketUtils.sendCmdAndRecResult(DebugLogFragment.SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn at+cp2_enter_user=1");
                Log.d(DebugLogFragment.TAG, "open UserMode Res is " + res3);
                if (res3 == null || (!res3.contains("OK") && !res3.contains("Ok") && !res3.contains("oK") && !res3.contains("ok"))) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.13
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setChecked(false);
                        }
                    });
                    return;
                } else {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.12
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setChecked(true);
                        }
                    });
                    return;
                }
            }
            if (i == 5) {
                String res4 = SocketUtils.sendCmdAndRecResult(DebugLogFragment.SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT, "wcnd wcn at+cp2_enter_user=0");
                Log.d(DebugLogFragment.TAG, "close UserMode Res is " + res4);
                if (res4 == null || (!res4.contains("OK") && !res4.contains("Ok") && !res4.contains("oK") && !res4.contains("ok"))) {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.15
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setChecked(true);
                        }
                    });
                    return;
                } else {
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.14
                        @Override // java.lang.Runnable
                        public void run() {
                            DebugLogFragment.this.mUserMode.setChecked(false);
                        }
                    });
                    return;
                }
            }
            switch (i) {
                case 14:
                    DebugLogFragment.this.mLVDSDumpIndex = ((Integer) msg.obj).intValue();
                    String mRespSetLVDS = DebugLogFragment.this.sendAt(engconstents.ENG_SET_AT_LVDS_DUMP + DebugLogFragment.this.mLVDSDumpIndex, "atchannel0");
                    Log.d(DebugLogFragment.TAG, "SET_LVDS_DUMP_TYPE mRespSetLVDS = " + mRespSetLVDS);
                    if (mRespSetLVDS == null || !mRespSetLVDS.contains("OK")) {
                        DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.17
                            @Override // java.lang.Runnable
                            public void run() {
                                Toast.makeText(DebugLogFragment.this.mContext, "Set LVDS Dump type failed!", 0).show();
                            }
                        });
                    } else {
                        DebugLogFragment.this.AlertDialogShow();
                        DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.16
                            @Override // java.lang.Runnable
                            public void run() throws IllegalAccessException, InvocationTargetException {
                                DebugLogFragment.this.mLVDSDump.setSummary(DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mLVDSDumpIndex]);
                                SystemPropertiesProxy.set(DebugLogFragment.KEY_LVDS_DUMP_GROUP, DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mLVDSDumpIndex]);
                            }
                        });
                    }
                    break;
                case 15:
                    String mRespGetLVDS = DebugLogFragment.this.sendAt(engconstents.ENG_GET_AT_LVDS_DUMP, "atchannel0");
                    Log.d(DebugLogFragment.TAG, "GET_LVDS_DUMP_TYPE mRespGetLVDS = " + mRespGetLVDS);
                    if (mRespGetLVDS == null || !mRespGetLVDS.contains("LVDSDUMP")) {
                        DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.19
                            @Override // java.lang.Runnable
                            public void run() {
                                DebugLogFragment.this.mLVDSDump.setEnabled(false);
                            }
                        });
                    } else {
                        try {
                            if (mRespGetLVDS.contains("OK")) {
                                DebugLogFragment.this.mLVDSDumpIndex = Integer.parseInt(mRespGetLVDS.split("\n")[0].split(":")[1]);
                            } else {
                                DebugLogFragment.this.mLVDSDumpIndex = Integer.parseInt(mRespGetLVDS.split(":")[1]);
                            }
                            Log.d(DebugLogFragment.TAG, "GET_LVDS_DUMP_TYPE mLVDSDumpIndex = " + DebugLogFragment.this.mLVDSDumpIndex);
                            DebugLogFragment.this.mGroupIndex = DebugLogFragment.this.mLVDSDumpIndex;
                            DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.DEGHandler.18
                                @Override // java.lang.Runnable
                                public void run() throws IllegalAccessException, InvocationTargetException {
                                    DebugLogFragment.this.mLVDSDump.setEnabled(true);
                                    DebugLogFragment.this.mLVDSDump.setSummary(DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mLVDSDumpIndex]);
                                    SystemPropertiesProxy.set(DebugLogFragment.KEY_LVDS_DUMP_GROUP, DebugLogFragment.this.mDumpGroup[DebugLogFragment.this.mLVDSDumpIndex]);
                                }
                            });
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Log.d(DebugLogFragment.TAG, "ArrayIndexOutOfBoundsException:" + e);
                            return;
                        } catch (NullPointerException e2) {
                            Log.d(DebugLogFragment.TAG, "NullPointerException:" + e2);
                            return;
                        } catch (NumberFormatException e3) {
                            Log.d(DebugLogFragment.TAG, "NumberFormatException: " + e3);
                            return;
                        }
                    }
                    break;
                case 16:
                    final String jeitaStatus = DebugLogFragment.this.debugLogApi.jeitaControlApi().getJeita();
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.-$$Lambda$DebugLogFragment$DEGHandler$Zxs9mbK3rFPuDK4aRqIIDR3bqyU
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$0$DebugLogFragment$DEGHandler(jeitaStatus);
                        }
                    });
                    break;
                case 17:
                    final String setJeitaValue = (String) msg.obj;
                    DebugLogFragment.this.debugLogApi.jeitaControlApi().setJeita(setJeitaValue);
                    DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.-$$Lambda$DebugLogFragment$DEGHandler$ZEc-eiUONmGqNZ1joQNsaAP5M6M
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$1$DebugLogFragment$DEGHandler(setJeitaValue);
                        }
                    });
                    break;
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$DebugLogFragment$DEGHandler(String jeitaStatus) {
            DebugLogFragment.this.mJeitaControl.setChecked(!"0".equals(jeitaStatus));
        }

        public /* synthetic */ void lambda$handleMessage$1$DebugLogFragment$DEGHandler(String setJeitaValue) {
            DebugLogFragment.this.mJeitaControl.setChecked(!"0".equals(setJeitaValue));
        }
    }

    public void AlertDialogShow() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.reboot_setting_prompt)).setCancelable(false).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PowerManager pm = (PowerManager) DebugLogFragment.this.mContext.getSystemService("power");
                pm.reboot("LVDS dump");
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                DebugLogFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.DebugLogFragment.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                });
            }
        }).create();
        alertDialog.show();
    }

    private int getLVDSSingleChoiceIndex(String itemName) {
        if (itemName.equals("Close LVDS DUMP")) {
            return 0;
        }
        if (itemName.equals("Open LVDS DUMP upstream")) {
            return 1;
        }
        if (itemName.equals("Open LVDS DUMP downstream")) {
            return 2;
        }
        return itemName.equals("Open LVDS DUMP upstream and downstream") ? 3 : 0;
    }

    private boolean isConnected() {
        Activity act = getActivity();
        if (act == null) {
            return false;
        }
        ConnectivityManager connectManager = (ConnectivityManager) act.getSystemService("connectivity");
        NetworkInfo netinfo = connectManager.getActiveNetworkInfo();
        if (netinfo == null) {
            return false;
        }
        if (netinfo.getType() != 0 && netinfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public String readFile(String path) {
        File file = new File(path);
        String str = new String("");
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
                        str = str + line;
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "Read file error!!!");
                    str = "readError";
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Log.d(TAG, "read " + path + " value is " + str.trim());
            return str.trim();
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

    public String execShellStr(String cmd) {
        String[] cmdStrings = {"sh", "-c", cmd};
        StringBuffer retString = new StringBuffer("");
        try {
            Process process = Runtime.getRuntime().exec(cmdStrings);
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"), 7777);
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"), 7777);
            while (true) {
                String line = stdout.readLine();
                String line2 = line;
                if (line == null) {
                    String line3 = stderr.readLine();
                    line2 = line3;
                    if (line3 == null) {
                        break;
                    }
                }
                if ("" != line2) {
                    retString.append(line2);
                    retString.append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, cmd + ":" + retString.toString() + "");
        return retString.toString();
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService("activity");
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    private int getSingleChoiceIndex(String itemName) {
        if (itemName.equals("CSSLAB")) {
            return 0;
        }
        if (itemName.equals("CSFLAB")) {
            return 1;
        }
        if (itemName.equals("CSFT")) {
            return 2;
        }
        return itemName.equals("Beta") ? 3 : 0;
    }

    public boolean checkApkExist(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            getActivity().getPackageManager().getApplicationInfo(packageName, 8192);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
