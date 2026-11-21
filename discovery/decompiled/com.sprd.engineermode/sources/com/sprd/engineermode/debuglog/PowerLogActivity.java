package com.sprd.engineermode.debuglog;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class PowerLogActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    private static final String KEY_ALARM_DEBUG_LOG = "persist.sys.alarm.debug";
    private static final String KEY_POWERHAL_DEBUG_ENABLE = "persist.vendor.power.debug_d";
    private static final String KEY_POWER_CONTROLLER_BGCLEAN_ENABLE = "persist.sys.pwctl.bgclean";
    private static final String KEY_POWER_CONTROLLER_ENABLE = "persist.sys.pwctl.enable";
    private static final String KEY_POWER_CONTROLLER_GPS_ENABLE = "persist.sys.pwctl.gps";
    private static final String KEY_POWER_CONTROLLER_GPS_ONLY_IN_SAVER_ENABLE = "persist.sys.pwctl.gps.onlysave";
    private static final String KEY_POWER_CONTROLLER_NETWORK_ENABLE = "persist.sys.pwctl.appidle";
    private static final String KEY_POWER_CONTROLLER_ONLY_IN_SAVER_ENABLE = "persist.sys.pwctl.onlysave";
    private static final String KEY_POWER_CONTROLLER_WAKELOCK_ENABLE = "persist.sys.pwctl.wl";
    private static final String KEY_POWER_DEBUG_LOG = "persist.sys.power.fw.debug";
    private static final String SWITCH_ALARM_LOG = "sprd_alarm_log";
    private static final String SWITCH_BATTERY_LOG = "sprd_battery_log";
    private static final String SWITCH_BATTERY_SAVER_LOG = "sprd_battery_saver_log";
    private static final String SWITCH_DISPLAY_LOG = "sprd_display_log";
    private static final String SWITCH_DOZE_LOG = "sprd_doze_log";
    private static final String SWITCH_POWERHAL_ENABLE = "sprd_power_hal_debug_switch";
    private static final String SWITCH_POWER_CONTROLLER_BGCLEAN_SWITCH = "sprd_power_controller_bg_clean_switch";
    private static final String SWITCH_POWER_CONTROLLER_GPS_ONLY_IN_SAVER_SWITCH = "sprd_power_controller_gps_only_saver_switch";
    private static final String SWITCH_POWER_CONTROLLER_GPS_SWITCH = "sprd_power_controller_gps_switch";
    private static final String SWITCH_POWER_CONTROLLER_LOG = "sprd_power_controller_log";
    private static final String SWITCH_POWER_CONTROLLER_NETWORK_SWITCH = "sprd_power_controller_network_switch";
    private static final String SWITCH_POWER_CONTROLLER_ONLY_IN_SAVER_SWITCH = "sprd_power_controller_only_saver_switch";
    private static final String SWITCH_POWER_CONTROLLER_SWITCH = "sprd_power_controller_switch";
    private static final String SWITCH_POWER_CONTROLLER_WAKELOCK_SWITCH = "sprd_power_controller_wakelock_switch";
    private static final String SWITCH_POWER_GURU_LOG = "sprd_power_guru_log";
    private static final String SWITCH_POWER_HINT_LOG = "sprd_power_hint_log";
    private static final String SWITCH_WAKELOCK_LOG = "sprd_wakelock_log";
    private static String TAG = "PowerLogActivity";
    private TwoStatePreference mAlarm;
    private TwoStatePreference mBattery;
    private TwoStatePreference mBatterySaver;
    private TwoStatePreference mDisplay;
    private TwoStatePreference mDoze;
    private TwoStatePreference mPowerController;
    private TwoStatePreference mPowerControllerBgCleanSwitch;
    private TwoStatePreference mPowerControllerGPSOnlyInSaverSwitch;
    private TwoStatePreference mPowerControllerGPSSwitch;
    private TwoStatePreference mPowerControllerNetworkSwitch;
    private TwoStatePreference mPowerControllerOnlyInSaverSwitch;
    private TwoStatePreference mPowerControllerSwitch;
    private TwoStatePreference mPowerControllerWakelockSwitch;
    private TwoStatePreference mPowerGuru;
    private TwoStatePreference mPowerHalSwitch;
    private TwoStatePreference mPowerHint;
    private TwoStatePreference mWakeLock;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.power_log);
        this.mBattery = (TwoStatePreference) findPreference(SWITCH_BATTERY_LOG);
        this.mBattery.setOnPreferenceClickListener(this);
        this.mDoze = (TwoStatePreference) findPreference(SWITCH_DOZE_LOG);
        this.mDoze.setOnPreferenceClickListener(this);
        this.mDisplay = (TwoStatePreference) findPreference(SWITCH_DISPLAY_LOG);
        this.mDisplay.setOnPreferenceClickListener(this);
        this.mPowerGuru = (TwoStatePreference) findPreference(SWITCH_POWER_GURU_LOG);
        this.mPowerGuru.setOnPreferenceClickListener(this);
        this.mPowerHint = (TwoStatePreference) findPreference(SWITCH_POWER_HINT_LOG);
        this.mPowerHint.setOnPreferenceClickListener(this);
        this.mWakeLock = (TwoStatePreference) findPreference(SWITCH_WAKELOCK_LOG);
        this.mWakeLock.setOnPreferenceClickListener(this);
        this.mPowerController = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_LOG);
        this.mPowerController.setOnPreferenceClickListener(this);
        this.mBatterySaver = (TwoStatePreference) findPreference(SWITCH_BATTERY_SAVER_LOG);
        this.mBatterySaver.setOnPreferenceClickListener(this);
        this.mAlarm = (TwoStatePreference) findPreference(SWITCH_ALARM_LOG);
        this.mAlarm.setOnPreferenceClickListener(this);
        this.mPowerControllerSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_SWITCH);
        this.mPowerControllerSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerGPSSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_GPS_SWITCH);
        this.mPowerControllerGPSSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerNetworkSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_NETWORK_SWITCH);
        this.mPowerControllerNetworkSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerBgCleanSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_BGCLEAN_SWITCH);
        this.mPowerControllerBgCleanSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerWakelockSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_WAKELOCK_SWITCH);
        this.mPowerControllerWakelockSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerOnlyInSaverSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_ONLY_IN_SAVER_SWITCH);
        this.mPowerControllerOnlyInSaverSwitch.setOnPreferenceClickListener(this);
        this.mPowerControllerGPSOnlyInSaverSwitch = (TwoStatePreference) findPreference(SWITCH_POWER_CONTROLLER_GPS_ONLY_IN_SAVER_SWITCH);
        this.mPowerControllerGPSOnlyInSaverSwitch.setOnPreferenceClickListener(this);
        this.mPowerHalSwitch = (TwoStatePreference) findPreference(SWITCH_POWERHAL_ENABLE);
        this.mPowerHalSwitch.setOnPreferenceClickListener(this);
        if (this.mPowerControllerSwitch.isChecked()) {
            this.mPowerControllerGPSSwitch.setEnabled(true);
            this.mPowerControllerNetworkSwitch.setEnabled(true);
            this.mPowerControllerBgCleanSwitch.setEnabled(true);
            this.mPowerControllerWakelockSwitch.setEnabled(true);
            this.mPowerControllerOnlyInSaverSwitch.setEnabled(true);
            this.mPowerControllerGPSOnlyInSaverSwitch.setEnabled(true);
        } else {
            this.mPowerControllerGPSSwitch.setEnabled(false);
            this.mPowerControllerNetworkSwitch.setEnabled(false);
            this.mPowerControllerBgCleanSwitch.setEnabled(false);
            this.mPowerControllerWakelockSwitch.setEnabled(false);
            this.mPowerControllerOnlyInSaverSwitch.setEnabled(false);
            this.mPowerControllerGPSOnlyInSaverSwitch.setEnabled(false);
        }
        Toast.makeText(this, getResources().getString(R.string.sprd_log_prompt), 0).show();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) throws IllegalAccessException, InvocationTargetException {
        TwoStatePreference twoStatePreference = this.mBattery;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Log.d(TAG, "open Battery log");
                setProperty(true, "battery");
            } else {
                Log.d(TAG, "off Battery log");
                setProperty(false, "battery");
            }
        } else {
            TwoStatePreference twoStatePreference2 = this.mDoze;
            if (pref == twoStatePreference2) {
                if (twoStatePreference2.isChecked()) {
                    Log.d(TAG, "open doze log");
                    setProperty(true, "doze");
                } else {
                    Log.d(TAG, "off doze log");
                    setProperty(false, "doze");
                }
            } else {
                TwoStatePreference twoStatePreference3 = this.mDisplay;
                if (pref == twoStatePreference3) {
                    if (twoStatePreference3.isChecked()) {
                        Log.d(TAG, "open display log");
                        setProperty(true, "display");
                    } else {
                        Log.d(TAG, "off display log");
                        setProperty(false, "display");
                    }
                } else {
                    TwoStatePreference twoStatePreference4 = this.mPowerGuru;
                    if (pref == twoStatePreference4) {
                        if (twoStatePreference4.isChecked()) {
                            Log.d(TAG, "open powerguru log");
                            setProperty(true, "guru");
                        } else {
                            Log.d(TAG, "off powerguru log");
                            setProperty(false, "guru");
                        }
                    } else {
                        TwoStatePreference twoStatePreference5 = this.mPowerHint;
                        if (pref == twoStatePreference5) {
                            if (twoStatePreference5.isChecked()) {
                                Log.d(TAG, "open powerhint log");
                                setProperty(true, "hint");
                            } else {
                                Log.d(TAG, "off powerhint log");
                                setProperty(false, "hint");
                            }
                        } else {
                            TwoStatePreference twoStatePreference6 = this.mWakeLock;
                            if (pref == twoStatePreference6) {
                                if (twoStatePreference6.isChecked()) {
                                    Log.d(TAG, "open wakelock log");
                                    setProperty(true, "wakelock");
                                } else {
                                    Log.d(TAG, "off wakelock log");
                                    setProperty(false, "wakelock");
                                }
                            } else {
                                TwoStatePreference twoStatePreference7 = this.mPowerController;
                                if (pref == twoStatePreference7) {
                                    if (twoStatePreference7.isChecked()) {
                                        Log.d(TAG, "open power controller log");
                                        setProperty(true, "controller");
                                    } else {
                                        Log.d(TAG, "off power controller log");
                                        setProperty(false, "controller");
                                    }
                                } else {
                                    TwoStatePreference twoStatePreference8 = this.mBatterySaver;
                                    if (pref == twoStatePreference8) {
                                        if (twoStatePreference8.isChecked()) {
                                            Log.d(TAG, "open battery saver log");
                                            setProperty(true, "saver");
                                        } else {
                                            Log.d(TAG, "off battery saver log");
                                            setProperty(false, "saver");
                                        }
                                    } else {
                                        TwoStatePreference twoStatePreference9 = this.mPowerHalSwitch;
                                        if (pref == twoStatePreference9) {
                                            if (twoStatePreference9.isChecked()) {
                                                Log.d(TAG, "open power hal log");
                                                setSwitchValue(KEY_POWERHAL_DEBUG_ENABLE, true);
                                            } else {
                                                Log.d(TAG, "off power hal log");
                                                setSwitchValue(KEY_POWERHAL_DEBUG_ENABLE, false);
                                            }
                                        } else {
                                            TwoStatePreference twoStatePreference10 = this.mAlarm;
                                            if (pref == twoStatePreference10) {
                                                if (twoStatePreference10.isChecked()) {
                                                    Log.d(TAG, "open alarm log");
                                                    setAlarmDebugLogEnable("1");
                                                } else {
                                                    Log.d(TAG, "off alarm log");
                                                    setAlarmDebugLogEnable("0");
                                                }
                                            } else {
                                                TwoStatePreference twoStatePreference11 = this.mPowerControllerSwitch;
                                                if (pref == twoStatePreference11) {
                                                    if (twoStatePreference11.isChecked()) {
                                                        Log.d(TAG, "open power controller switch");
                                                        setSwitchValue(KEY_POWER_CONTROLLER_ENABLE, true);
                                                        this.mPowerControllerGPSSwitch.setEnabled(true);
                                                        this.mPowerControllerNetworkSwitch.setEnabled(true);
                                                        this.mPowerControllerBgCleanSwitch.setEnabled(true);
                                                        this.mPowerControllerWakelockSwitch.setEnabled(true);
                                                        this.mPowerControllerOnlyInSaverSwitch.setEnabled(true);
                                                        this.mPowerControllerGPSOnlyInSaverSwitch.setEnabled(true);
                                                    } else {
                                                        Log.d(TAG, "close power controller switch");
                                                        setSwitchValue(KEY_POWER_CONTROLLER_ENABLE, false);
                                                        this.mPowerControllerGPSSwitch.setEnabled(false);
                                                        this.mPowerControllerNetworkSwitch.setEnabled(false);
                                                        this.mPowerControllerBgCleanSwitch.setEnabled(false);
                                                        this.mPowerControllerWakelockSwitch.setEnabled(false);
                                                        this.mPowerControllerOnlyInSaverSwitch.setEnabled(false);
                                                        this.mPowerControllerGPSOnlyInSaverSwitch.setEnabled(false);
                                                    }
                                                } else {
                                                    TwoStatePreference twoStatePreference12 = this.mPowerControllerGPSSwitch;
                                                    if (pref == twoStatePreference12) {
                                                        if (twoStatePreference12.isChecked()) {
                                                            setSwitchValue(KEY_POWER_CONTROLLER_GPS_ENABLE, true);
                                                            Log.d(TAG, "open power controller GPS switch");
                                                        } else {
                                                            setSwitchValue(KEY_POWER_CONTROLLER_GPS_ENABLE, false);
                                                            Log.d(TAG, "close power controller GPS switch");
                                                        }
                                                    } else {
                                                        TwoStatePreference twoStatePreference13 = this.mPowerControllerNetworkSwitch;
                                                        if (pref == twoStatePreference13) {
                                                            if (twoStatePreference13.isChecked()) {
                                                                setSwitchValue(KEY_POWER_CONTROLLER_NETWORK_ENABLE, true);
                                                                Log.d(TAG, "open power controller network switch");
                                                            } else {
                                                                setSwitchValue(KEY_POWER_CONTROLLER_NETWORK_ENABLE, false);
                                                                Log.d(TAG, "close power controller network switch");
                                                            }
                                                        } else {
                                                            TwoStatePreference twoStatePreference14 = this.mPowerControllerBgCleanSwitch;
                                                            if (pref == twoStatePreference14) {
                                                                if (twoStatePreference14.isChecked()) {
                                                                    setSwitchValue(KEY_POWER_CONTROLLER_BGCLEAN_ENABLE, true);
                                                                    Log.d(TAG, "open power controller bgClean switch");
                                                                } else {
                                                                    setSwitchValue(KEY_POWER_CONTROLLER_BGCLEAN_ENABLE, false);
                                                                    Log.d(TAG, "close power controller bgClean switch");
                                                                }
                                                            } else {
                                                                TwoStatePreference twoStatePreference15 = this.mPowerControllerWakelockSwitch;
                                                                if (pref == twoStatePreference15) {
                                                                    if (twoStatePreference15.isChecked()) {
                                                                        setSwitchValue(KEY_POWER_CONTROLLER_WAKELOCK_ENABLE, true);
                                                                        Log.d(TAG, "open power controller wakelock switch");
                                                                    } else {
                                                                        setSwitchValue(KEY_POWER_CONTROLLER_WAKELOCK_ENABLE, false);
                                                                        Log.d(TAG, "close power controller wakelock switch");
                                                                    }
                                                                } else {
                                                                    TwoStatePreference twoStatePreference16 = this.mPowerControllerOnlyInSaverSwitch;
                                                                    if (pref == twoStatePreference16) {
                                                                        if (twoStatePreference16.isChecked()) {
                                                                            setSwitchValue(KEY_POWER_CONTROLLER_ONLY_IN_SAVER_ENABLE, true);
                                                                            Log.d(TAG, "open power controller only in saver switch");
                                                                        } else {
                                                                            setSwitchValue(KEY_POWER_CONTROLLER_ONLY_IN_SAVER_ENABLE, false);
                                                                            Log.d(TAG, "close power controller only in saver switch");
                                                                        }
                                                                    } else {
                                                                        TwoStatePreference twoStatePreference17 = this.mPowerControllerGPSOnlyInSaverSwitch;
                                                                        if (pref == twoStatePreference17) {
                                                                            if (twoStatePreference17.isChecked()) {
                                                                                setSwitchValue(KEY_POWER_CONTROLLER_GPS_ONLY_IN_SAVER_ENABLE, true);
                                                                                Log.d(TAG, "open power controller GPS only in saver switch");
                                                                            } else {
                                                                                setSwitchValue(KEY_POWER_CONTROLLER_GPS_ONLY_IN_SAVER_ENABLE, false);
                                                                                Log.d(TAG, "close power controller GPS only in saver switch");
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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

    public void setProperty(boolean enabled, String debugtype) throws IllegalAccessException, InvocationTargetException {
        if (enabled) {
            if (getPowerDebugLog().equals("null")) {
                setPersistValue(setfirstPersistValue() + debugtype);
                return;
            }
            setPersistValue(getPowerDebugLog() + "," + debugtype);
            return;
        }
        if (getPowerDebugLog().equals(debugtype)) {
            setPersistValue("null");
        } else {
            setPersistValue(deletePowerDebugLog(debugtype));
        }
    }

    private void setSwitchValue(String value, boolean enabled) throws IllegalAccessException, InvocationTargetException {
        if (enabled) {
            SystemPropertiesProxy.set(value, "1");
        } else {
            SystemPropertiesProxy.set(value, "0");
        }
    }

    public String getPowerDebugLog() {
        return SystemPropertiesProxy.get(KEY_POWER_DEBUG_LOG, "null");
    }

    public String setfirstPersistValue() {
        String value = getPowerDebugLog();
        StringBuilder stringBuilder = new StringBuilder(value);
        String stringBuilderNew = stringBuilder.toString();
        if (!stringBuilderNew.equals("null")) {
            return null;
        }
        String valueNew = stringBuilderNew.replace("null", "");
        return valueNew;
    }

    public String deletePowerDebugLog(String debugtpye) {
        String value = getPowerDebugLog();
        StringBuilder stringBuilder = new StringBuilder(value);
        String stringBuilderNew = stringBuilder.toString();
        if (!stringBuilderNew.contains("," + debugtpye + ",")) {
            if (!stringBuilderNew.contains("," + debugtpye)) {
                if (!stringBuilderNew.contains(debugtpye + ",")) {
                    return null;
                }
                String valueNew = stringBuilderNew.replace(debugtpye + ",", "");
                return valueNew;
            }
        }
        String valueNew2 = stringBuilderNew.replace("," + debugtpye, "");
        return valueNew2;
    }

    public void setPersistValue(String debugtype) throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(KEY_POWER_DEBUG_LOG, debugtype);
    }

    public void setAlarmDebugLogEnable(String value) throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set(KEY_ALARM_DEBUG_LOG, value);
    }
}
