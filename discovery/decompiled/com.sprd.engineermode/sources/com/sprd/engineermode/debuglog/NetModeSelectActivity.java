package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SettingsProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class NetModeSelectActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int GET_NETMODE = 2;
    private static final String KEY = "sim";
    static final String PROPERTY_MULTI_SIM_CONFIG = "persist.radio.multisim.config";
    private static final int SET_NETMODE = 1;
    private static final String TAG = "NetModeSelectActivity";
    private Context mContext;
    private int mLastValue;
    private ListPreference[] mListPreference;
    private NMODEHandler mNMODEHandler;
    private int mPhoneCount;
    private SharedPreferences mSharePref;
    private SubscriptionManager mSubscriptionManager;
    private NetModeSelectHelper[] mSelectHelper = null;
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i == 2) {
                    int sim = msg.arg1;
                    NetModeSelectActivity.this.mLastValue = msg.arg2;
                    NetModeSelectActivity.this.mListPreference[sim].setValue(String.valueOf(NetModeSelectActivity.this.mLastValue));
                    NetModeSelectActivity.this.mListPreference[sim].setSummary(NetModeSelectActivity.this.mListPreference[sim].getEntry());
                    return;
                }
                return;
            }
            int sim2 = msg.arg1;
            int isSuccess = msg.arg2;
            if (isSuccess == 1) {
                if (NetModeSelectActivity.this.mSelectHelper[sim2].getModemType() == 1) {
                    NetModeSelectActivity.this.mListPreference[sim2].setSummary(NetModeSelectActivity.this.mListPreference[sim2].getEntry());
                    NetModeSelectActivity.this.finish();
                    Toast.makeText(NetModeSelectActivity.this, "success,modem reset...", 0).show();
                    return;
                } else {
                    NetModeSelectActivity.this.mListPreference[sim2].setSummary(NetModeSelectActivity.this.mListPreference[sim2].getEntry());
                    Toast.makeText(NetModeSelectActivity.this, "success", 0).show();
                    return;
                }
            }
            String backupString = (String) msg.obj;
            NetModeSelectActivity.this.mListPreference[sim2].setValue(backupString);
            NetModeSelectActivity.this.mListPreference[sim2].setSummary(NetModeSelectActivity.this.mListPreference[sim2].getEntry());
            SharedPreferences.Editor edit = NetModeSelectActivity.this.mSharePref.edit();
            edit.putString(NetModeSelectActivity.KEY + sim2, backupString);
            edit.commit();
            Toast.makeText(NetModeSelectActivity.this, "fail", 0).show();
        }
    };

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mNMODEHandler = new NMODEHandler(ht.getLooper());
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        Log.d(TAG, "mPhoneCount is " + this.mPhoneCount);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        PreferenceGroup preGroup = getPreferenceScreen();
        this.mSubscriptionManager = SubscriptionManager.from(this);
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        int i = this.mPhoneCount;
        this.mListPreference = new ListPreference[i];
        this.mSelectHelper = new NetModeSelectHelper[i];
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            String key = KEY + i2;
            this.mListPreference[i2] = new ListPreference(this);
            this.mListPreference[i2].setEnabled(false);
            this.mListPreference[i2].setTitle(key);
            this.mListPreference[i2].setKey(key);
            this.mListPreference[i2].setOnPreferenceChangeListener(this);
            preGroup.addPreference(this.mListPreference[i2]);
            this.mListPreference[i2].setEntries(R.array.network_mode_choices_gsm);
            this.mListPreference[i2].setEntryValues(R.array.network_mode_gsm_values);
        }
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity.2
            @Override // java.lang.Runnable
            public void run() {
                for (int i3 = 0; i3 < NetModeSelectActivity.this.mPhoneCount; i3++) {
                    NetModeSelectActivity.this.mSelectHelper[i3] = NetModeSelectHelper.getNetModeSelectHelper(i3, NetModeSelectActivity.this.mContext);
                }
                NetModeSelectActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        for (int i4 = 0; i4 < NetModeSelectActivity.this.mPhoneCount; i4++) {
                            TelephonyManager tm = TelephonyManagerProxy.getService();
                            if (NetModeSelectActivity.this.mSelectHelper[i4] != null) {
                                if (tm != null && tm.getSimState(i4) == 5) {
                                    NetModeSelectActivity.this.mListPreference[i4].setEnabled(true);
                                    Log.d(NetModeSelectActivity.TAG, "mSelectHelper[" + i4 + "], getModemType(): " + NetModeSelectActivity.this.mSelectHelper[i4].getModemType());
                                    if (NetModeSelectActivity.this.mSelectHelper[i4].getModemType() != 1) {
                                        if (NetModeSelectActivity.this.mSelectHelper[i4].getModemType() == 2) {
                                            NetModeSelectActivity.this.mListPreference[i4].setEntries(R.array.network_mode_choices_wcdma);
                                            NetModeSelectActivity.this.mListPreference[i4].setEntryValues(R.array.network_mode_wcdma_values);
                                        } else {
                                            NetModeSelectActivity.this.mListPreference[i4].setEntries(R.array.network_mode_choices_gsm);
                                            NetModeSelectActivity.this.mListPreference[i4].setEntryValues(R.array.network_mode_gsm_values);
                                        }
                                    } else {
                                        NetModeSelectActivity.this.mListPreference[i4].setEntries(R.array.network_mode_choices_td);
                                        NetModeSelectActivity.this.mListPreference[i4].setEntryValues(R.array.network_mode_td_values);
                                    }
                                    if (NetModeSelectActivity.this.mListPreference[i4].isEnabled()) {
                                        Log.d(NetModeSelectActivity.TAG, "sim " + i4 + " is enable");
                                        Message netMode = NetModeSelectActivity.this.mNMODEHandler.obtainMessage(2, i4, 0);
                                        NetModeSelectActivity.this.mNMODEHandler.sendMessage(netMode);
                                    }
                                } else {
                                    NetModeSelectActivity.this.mListPreference[i4].setEnabled(false);
                                }
                            }
                        }
                    }
                });
            }
        }).start();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mNMODEHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mNMODEHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "onPreferenceChange and the key is " + key);
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (key.equals(KEY + i)) {
                String re = newValue.toString();
                Message mNetMode = this.mNMODEHandler.obtainMessage(1, i, 0, re);
                this.mNMODEHandler.sendMessage(mNetMode);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean refreshSettingProvider(int sim, int value) {
        boolean hasRefreshed;
        boolean hasRefreshed2;
        boolean hasRefreshed3;
        boolean hasRefreshed4;
        boolean hasRefreshed5;
        boolean hasRefreshed6;
        int subId = slotIdToSubId(sim);
        if (value == 0) {
            Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 3);
            try {
                int newvalue = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
                if (newvalue == 3) {
                    hasRefreshed = true;
                } else {
                    hasRefreshed = false;
                }
                Log.d(TAG, "NetMode Set Auto and Refresh Setting Provider " + newvalue);
                return hasRefreshed;
            } catch (Settings.SettingNotFoundException e) {
                Log.d(TAG, "catch exception");
                return false;
            }
        }
        if (value == 1) {
            Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 1);
            try {
                int newvalue2 = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
                if (newvalue2 == 1) {
                    hasRefreshed2 = true;
                } else {
                    hasRefreshed2 = false;
                }
                Log.d(TAG, "NetMode Set GSM Only and Refresh Setting Provider " + newvalue2);
                return hasRefreshed2;
            } catch (Settings.SettingNotFoundException e2) {
                Log.d(TAG, "catch exception");
                return false;
            }
        }
        if (value != 2) {
            if (value == 3) {
                Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 0);
                try {
                    int newvalue3 = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
                    if (newvalue3 == 0) {
                        hasRefreshed4 = true;
                    } else {
                        hasRefreshed4 = false;
                    }
                    Log.d(TAG, "NetMode Set WCDMA Prefered and Refresh Setting Provider " + newvalue3);
                    return hasRefreshed4;
                } catch (Settings.SettingNotFoundException e3) {
                    Log.d(TAG, "catch exception");
                    return false;
                }
            }
            if (value == 4) {
                Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 2);
                try {
                    int newvalue4 = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
                    if (newvalue4 == 2) {
                        hasRefreshed5 = true;
                    } else {
                        hasRefreshed5 = false;
                    }
                    Log.d(TAG, "NetMode Set TD Only and Refresh Setting Provider " + newvalue4);
                    return hasRefreshed5;
                } catch (Settings.SettingNotFoundException e4) {
                    Log.d(TAG, "catch exception");
                    return false;
                }
            }
            if (value != 5) {
                return false;
            }
            Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 0);
            try {
                int newvalue5 = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
                if (newvalue5 == 0) {
                    hasRefreshed6 = true;
                } else {
                    hasRefreshed6 = false;
                }
                Log.d(TAG, "NetMode Set TD Prefered and Refresh Setting Provider " + newvalue5);
                return hasRefreshed6;
            } catch (Settings.SettingNotFoundException e5) {
                Log.d(TAG, "catch exception");
                return false;
            }
        }
        Settings.Global.putInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId), 2);
        try {
            int newvalue6 = Settings.Global.getInt(getContentResolver(), getSetting(SettingsProxy.Global.PREFERRED_NETWORK_MODE, subId));
            if (newvalue6 == 2) {
                hasRefreshed3 = true;
            } else {
                hasRefreshed3 = false;
            }
            Log.d(TAG, "NetMode Set WCDMA Only and Refresh Setting Provider " + newvalue6);
            return hasRefreshed3;
        } catch (Settings.SettingNotFoundException e6) {
            Log.d(TAG, "catch exception");
            return false;
        }
    }

    public int slotIdToSubId(int phoneId) {
        SubscriptionInfo mSubscriptionInfo = SubscriptionManagerProxy.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
        if (mSubscriptionInfo != null) {
            int subId = mSubscriptionInfo.getSubscriptionId();
            return subId;
        }
        int subId2 = SubscriptionManager.getDefaultSubscriptionId();
        return subId2;
    }

    public String getSetting(String defaultSetting, int phoneId) {
        if (isMultiSimEnabledEx()) {
            return defaultSetting + phoneId;
        }
        return defaultSetting;
    }

    private boolean isMultiSimEnabledEx() {
        String multiSimConfig = SystemPropertiesProxy.get(PROPERTY_MULTI_SIM_CONFIG);
        return multiSimConfig.equals("dsds") || multiSimConfig.equals("dsda") || multiSimConfig.equals("tsts");
    }

    class NMODEHandler extends Handler {
        public NMODEHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i != 1) {
                if (i == 2) {
                    int sim = msg.arg1;
                    if (NetModeSelectActivity.this.mSelectHelper[sim] != null) {
                        int netMode = NetModeSelectActivity.this.mSelectHelper[sim].getNetMode();
                        if (netMode != -1) {
                            NetModeSelectActivity.this.mUiThread.sendMessage(NetModeSelectActivity.this.mUiThread.obtainMessage(2, sim, netMode));
                        }
                        Log.d(NetModeSelectActivity.TAG, "GET_NETMODE  sim = " + sim + " netMode =" + netMode);
                        return;
                    }
                    return;
                }
                return;
            }
            int sim2 = msg.arg1;
            String valueStr = (String) msg.obj;
            int value = Integer.valueOf(valueStr.trim()).intValue();
            if (NetModeSelectActivity.this.mSelectHelper[sim2].setNetMode(value)) {
                if (NetModeSelectActivity.this.refreshSettingProvider(sim2, value)) {
                    if ((value == 1 && (NetModeSelectActivity.this.mLastValue == 4 || NetModeSelectActivity.this.mLastValue == 5)) || value == 4 || value == 5) {
                        IATUtils.sendATCmd(engconstents.ENG_AT_RESET, sim2);
                    }
                    NetModeSelectActivity.this.mLastValue = value;
                    NetModeSelectActivity.this.mUiThread.sendMessage(NetModeSelectActivity.this.mUiThread.obtainMessage(1, sim2, 1, null));
                    return;
                }
                NetModeSelectActivity.this.mUiThread.sendMessage(NetModeSelectActivity.this.mUiThread.obtainMessage(1, sim2, 0, String.valueOf(NetModeSelectActivity.this.mLastValue)));
                return;
            }
            NetModeSelectActivity.this.mUiThread.sendMessage(NetModeSelectActivity.this.mUiThread.obtainMessage(1, sim2, 0, String.valueOf(NetModeSelectActivity.this.mLastValue)));
        }
    }
}
