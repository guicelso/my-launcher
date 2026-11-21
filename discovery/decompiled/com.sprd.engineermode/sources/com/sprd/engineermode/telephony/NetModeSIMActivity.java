package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;

/* JADX INFO: loaded from: classes4.dex */
public class NetModeSIMActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String INTENT_CONTENT = "intent_content";
    private static final String KEY_PHONEID = "key_phoneid";
    private static final String KEY_SIM0_SELECT = "sim0_select";
    private static final String KEY_SIM1_SELECT = "sim1_select";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String PHONE_CHANNEL = "phonae_channel";
    private static final String TAG = "NetModeSIMActivity";
    private String intentContent;
    private int mPhoneCount;
    private int mPhoneId = 0;
    private Preference mSIM0Select;
    private Preference mSIM1Select;
    private TelephonyManager mTelephonyManager;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.intentContent = intent.getStringExtra(INTENT_CONTENT);
        addPreferencesFromResource(R.xml.pref_netmode_sim);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mSIM0Select = findPreference(KEY_SIM0_SELECT);
        this.mSIM0Select.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.telephony.NetModeSIMActivity.1
            @Override // android.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(Preference preference) {
                Log.d(NetModeSIMActivity.TAG, "mSIM0Select onPreferenceClick intentContent = " + NetModeSIMActivity.this.intentContent);
                if (NetModeSIMActivity.this.intentContent == null || !NetModeSIMActivity.this.intentContent.equals("netmode")) {
                    if (NetModeSIMActivity.this.intentContent != null && NetModeSIMActivity.this.intentContent.equals("bandselect")) {
                        if (SystemPropertiesProxy.get("persist.vendor.radio.engtest.enable").contains("true")) {
                            return false;
                        }
                        Intent intent2 = new Intent("com.sprd.engineermode.action.BANDMODESET");
                        intent2.putExtra("key_phoneid", 0);
                        NetModeSIMActivity.this.startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent("android.engineermode.action.FrequencyPointActivity");
                        intent3.putExtra(NetModeSIMActivity.PHONE_CHANNEL, "atchannel0");
                        NetModeSIMActivity.this.startActivity(intent3);
                    }
                } else {
                    Intent intent4 = new Intent("android.engineermode.action.LTEActivity");
                    intent4.putExtra("simindex", "0");
                    NetModeSIMActivity.this.startActivity(intent4);
                }
                return false;
            }
        });
        this.mSIM1Select = findPreference(KEY_SIM1_SELECT);
        this.mSIM1Select.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.telephony.NetModeSIMActivity.2
            @Override // android.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(Preference preference) {
                Log.d(NetModeSIMActivity.TAG, "mSIM1Select onPreferenceClick intentContent = " + NetModeSIMActivity.this.intentContent);
                if (NetModeSIMActivity.this.intentContent == null || !NetModeSIMActivity.this.intentContent.equals("netmode")) {
                    if (NetModeSIMActivity.this.intentContent != null && NetModeSIMActivity.this.intentContent.equals("bandselect")) {
                        if (SystemPropertiesProxy.get("persist.vendor.radio.engtest.enable").contains("true")) {
                            return false;
                        }
                        Intent intent2 = new Intent("com.sprd.engineermode.action.BANDMODESET");
                        intent2.putExtra("key_phoneid", 1);
                        NetModeSIMActivity.this.startActivity(intent2);
                    } else {
                        Intent intent3 = new Intent("android.engineermode.action.FrequencyPointActivity");
                        intent3.putExtra(NetModeSIMActivity.PHONE_CHANNEL, "atchannel1");
                        NetModeSIMActivity.this.startActivity(intent3);
                    }
                } else {
                    Intent intent4 = new Intent("android.engineermode.action.LTEActivity");
                    intent4.putExtra("simindex", "1");
                    NetModeSIMActivity.this.startActivity(intent4);
                }
                return false;
            }
        });
        getCardExitState();
    }

    private void getCardExitState() {
        Log.d(TAG, "mPhoneCount: " + this.mPhoneCount);
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mTelephonyManager = TelephonyManagerProxy.getService();
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null && telephonyManager.getSimState(i) == 5) {
                Log.d(TAG, "SIM " + i + " SIM_STATE_READY");
                if (i == 0) {
                    this.mSIM0Select.setEnabled(true);
                } else {
                    this.mSIM1Select.setEnabled(true);
                }
            } else {
                Log.d(TAG, "SIM " + i + " SIM_STATE_NOT_READY");
                if (i == 0) {
                    this.mSIM0Select.setEnabled(false);
                } else {
                    this.mSIM1Select.setEnabled(false);
                }
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(TAG, "onPreferenceChange");
        if (preference == this.mSIM0Select) {
            Log.d(TAG, "get mSIM0Select ");
            return true;
        }
        if (preference == this.mSIM1Select) {
            Log.d(TAG, "get mSIM1Select ");
            return true;
        }
        return true;
    }
}
