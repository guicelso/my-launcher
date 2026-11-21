package com.sprd.engineermode.telephony.volte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class IPSecSettingActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_ENCRYPTION_ALGORITHM_0 = "volte_encryption_algorithm_0";
    private static final String KEY_ENCRYPTION_ALGORITHM_1 = "volte_encryption_algorithm_1";
    private static final String KEY_ENCRYPTION_ALGORITHM_2 = "volte_encryption_algorithm_2";
    private static final String KEY_ENCRY_ALGORITHM = "volte_encry_algorithm";
    private static final String KEY_IPSEC_SWITCH = "volte_ipsec_setting_switch";
    private static final String KEY_MAINTAIN_ALGORITHM_0 = "volte_maintain_algorithm_0";
    private static final String KEY_MAINTAIN_ALGORITHM_1 = "volte_maintain_algorithm_1";
    private static final String KEY_MAIN_ALGORITHM = "volte_main_algorithm";
    private static final int MSG_GET_IPSEC_STATE = 0;
    private static final int MSG_SET_ENCRYPTION_ALGORITHM_0 = 4;
    private static final int MSG_SET_ENCRYPTION_ALGORITHM_1 = 5;
    private static final int MSG_SET_ENCRYPTION_ALGORITHM_2 = 6;
    private static final int MSG_SET_IPSEC_STATE = 1;
    private static final int MSG_SET_MAINTAIN_ALGORITHM_0 = 2;
    private static final int MSG_SET_MAINTAIN_ALGORITHM_1 = 3;
    private static final String TAG = "IPSecSettingActivity";
    private CheckBoxPreference mEncryptionAlgorithm0;
    private CheckBoxPreference mEncryptionAlgorithm1;
    private CheckBoxPreference mEncryptionAlgorithm2;
    private TwoStatePreference mIPsecSwitch;
    private IpsecHandler mIpsecHandler;
    private CheckBoxPreference mMaintainAlgorithm0;
    private CheckBoxPreference mMaintainAlgorithm1;
    private SharedPreferences mSharePref;
    private Handler mUiThread = new Handler();
    private String mResp = null;
    private String mNewValue = null;
    private String mMainAlgorithmIndex = null;
    private String mEncryAlgorithmIndex = null;
    private Context mContext = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_volte_ipsec_setting);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mIpsecHandler = new IpsecHandler(ht.getLooper());
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mIPsecSwitch = (TwoStatePreference) findPreference(KEY_IPSEC_SWITCH);
        this.mIPsecSwitch.setOnPreferenceChangeListener(this);
        this.mMaintainAlgorithm0 = (CheckBoxPreference) findPreference(KEY_MAINTAIN_ALGORITHM_0);
        this.mMaintainAlgorithm0.setOnPreferenceChangeListener(this);
        this.mMaintainAlgorithm0.setEnabled(false);
        this.mMaintainAlgorithm1 = (CheckBoxPreference) findPreference(KEY_MAINTAIN_ALGORITHM_1);
        this.mMaintainAlgorithm1.setOnPreferenceChangeListener(this);
        this.mMaintainAlgorithm1.setEnabled(false);
        this.mEncryptionAlgorithm0 = (CheckBoxPreference) findPreference(KEY_ENCRYPTION_ALGORITHM_0);
        this.mEncryptionAlgorithm0.setOnPreferenceChangeListener(this);
        this.mEncryptionAlgorithm0.setEnabled(false);
        this.mEncryptionAlgorithm1 = (CheckBoxPreference) findPreference(KEY_ENCRYPTION_ALGORITHM_1);
        this.mEncryptionAlgorithm1.setOnPreferenceChangeListener(this);
        this.mEncryptionAlgorithm1.setEnabled(false);
        this.mEncryptionAlgorithm2 = (CheckBoxPreference) findPreference(KEY_ENCRYPTION_ALGORITHM_2);
        this.mEncryptionAlgorithm2.setOnPreferenceChangeListener(this);
        this.mEncryptionAlgorithm2.setEnabled(false);
    }

    @Override // android.app.Activity
    public void onStart() {
        TwoStatePreference twoStatePreference = this.mIPsecSwitch;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            Message getIpsec = this.mIpsecHandler.obtainMessage(0);
            this.mIpsecHandler.sendMessage(getIpsec);
        }
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        IpsecHandler ipsecHandler = this.mIpsecHandler;
        if (ipsecHandler != null) {
            ipsecHandler.getLooper().quit();
            Log.d(TAG, "HandlerThread has quit");
        }
        super.onDestroy();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        TwoStatePreference twoStatePreference = this.mIPsecSwitch;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Message setIpsecClose = this.mIpsecHandler.obtainMessage(1, "0");
                this.mIpsecHandler.sendMessage(setIpsecClose);
            } else {
                Message setIpsecOpen = this.mIpsecHandler.obtainMessage(1, "1");
                this.mIpsecHandler.sendMessage(setIpsecOpen);
            }
        }
        CheckBoxPreference checkBoxPreference = this.mMaintainAlgorithm0;
        if (pref == checkBoxPreference) {
            if (checkBoxPreference.isChecked()) {
                Message setMain0Close = this.mIpsecHandler.obtainMessage(2, "0");
                this.mIpsecHandler.sendMessage(setMain0Close);
            } else {
                Message setMain0Open = this.mIpsecHandler.obtainMessage(2, "1");
                this.mIpsecHandler.sendMessage(setMain0Open);
            }
        }
        CheckBoxPreference checkBoxPreference2 = this.mMaintainAlgorithm1;
        if (pref == checkBoxPreference2) {
            if (checkBoxPreference2.isChecked()) {
                Message setMain1Close = this.mIpsecHandler.obtainMessage(3, "0");
                this.mIpsecHandler.sendMessage(setMain1Close);
            } else {
                Message setMain1Open = this.mIpsecHandler.obtainMessage(3, "1");
                this.mIpsecHandler.sendMessage(setMain1Open);
            }
        }
        CheckBoxPreference checkBoxPreference3 = this.mEncryptionAlgorithm0;
        if (pref == checkBoxPreference3) {
            if (checkBoxPreference3.isChecked()) {
                Message setEncry0Close = this.mIpsecHandler.obtainMessage(4, "0");
                this.mIpsecHandler.sendMessage(setEncry0Close);
            } else {
                Message setEncry0Open = this.mIpsecHandler.obtainMessage(4, "1");
                this.mIpsecHandler.sendMessage(setEncry0Open);
            }
        }
        CheckBoxPreference checkBoxPreference4 = this.mEncryptionAlgorithm1;
        if (pref == checkBoxPreference4) {
            if (checkBoxPreference4.isChecked()) {
                Message setEncry1Close = this.mIpsecHandler.obtainMessage(5, "0");
                this.mIpsecHandler.sendMessage(setEncry1Close);
            } else {
                Message setEncry1Open = this.mIpsecHandler.obtainMessage(5, "1");
                this.mIpsecHandler.sendMessage(setEncry1Open);
            }
        }
        CheckBoxPreference checkBoxPreference5 = this.mEncryptionAlgorithm2;
        if (pref == checkBoxPreference5) {
            if (checkBoxPreference5.isChecked()) {
                Message setEncry2Close = this.mIpsecHandler.obtainMessage(6, "0");
                this.mIpsecHandler.sendMessage(setEncry2Close);
                return false;
            }
            Message setEncry2Open = this.mIpsecHandler.obtainMessage(6, "1");
            this.mIpsecHandler.sendMessage(setEncry2Open);
            return false;
        }
        return false;
    }

    class IpsecHandler extends Handler {
        public IpsecHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    IPSecSettingActivity iPSecSettingActivity = IPSecSettingActivity.this;
                    iPSecSettingActivity.mResp = iPSecSettingActivity.sendAt("AT+SPENGMDVOLTE=10,0", "atchannel0");
                    IPSecSettingActivity iPSecSettingActivity2 = IPSecSettingActivity.this;
                    iPSecSettingActivity2.mNewValue = iPSecSettingActivity2.anayResult(0, iPSecSettingActivity2.mResp);
                    if (IPSecSettingActivity.this.mNewValue.contains("FAILED")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.1
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mIPsecSwitch.setEnabled(false);
                                IPSecSettingActivity.this.mIPsecSwitch.setSummary(R.string.feature_abnormal);
                                IPSecSettingActivity.this.checkAlgorithm(IPSecSettingActivity.this.mNewValue, false);
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.2
                            @Override // java.lang.Runnable
                            public void run() {
                                boolean open = IPSecSettingActivity.this.mNewValue.split("\\,")[2].contains("1");
                                IPSecSettingActivity.this.mIPsecSwitch.setChecked(open);
                                if (open) {
                                    IPSecSettingActivity.this.checkAlgorithm(IPSecSettingActivity.this.mNewValue, true);
                                } else {
                                    IPSecSettingActivity.this.checkAlgorithm(IPSecSettingActivity.this.mNewValue, false);
                                }
                            }
                        });
                    }
                    break;
                case 1:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity3 = IPSecSettingActivity.this;
                    iPSecSettingActivity3.mResp = iPSecSettingActivity3.sendAt("AT+SPENGMDVOLTE=10,1," + IPSecSettingActivity.this.mNewValue, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mIPsecSwitch.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                IPSecSettingActivity.this.enabledAlgorithm(IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                    } else {
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
                case 2:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity4 = IPSecSettingActivity.this;
                    iPSecSettingActivity4.mEncryAlgorithmIndex = iPSecSettingActivity4.mSharePref.getString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, "0");
                    IPSecSettingActivity iPSecSettingActivity5 = IPSecSettingActivity.this;
                    iPSecSettingActivity5.mMainAlgorithmIndex = iPSecSettingActivity5.mSharePref.getString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, "0");
                    if (IPSecSettingActivity.this.mNewValue.contains("1")) {
                        IPSecSettingActivity iPSecSettingActivity6 = IPSecSettingActivity.this;
                        iPSecSettingActivity6.mMainAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity6.mMainAlgorithmIndex).intValue() + 1);
                    } else {
                        IPSecSettingActivity iPSecSettingActivity7 = IPSecSettingActivity.this;
                        iPSecSettingActivity7.mMainAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity7.mMainAlgorithmIndex).intValue() - 1);
                    }
                    IPSecSettingActivity iPSecSettingActivity8 = IPSecSettingActivity.this;
                    iPSecSettingActivity8.mResp = iPSecSettingActivity8.sendAt("AT+SPENGMDVOLTE=10,1,1," + IPSecSettingActivity.this.mMainAlgorithmIndex + "," + IPSecSettingActivity.this.mEncryAlgorithmIndex, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mMaintainAlgorithm0.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                SharedPreferences.Editor editor = IPSecSettingActivity.this.mSharePref.edit();
                                editor.putString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, IPSecSettingActivity.this.mMainAlgorithmIndex);
                                editor.commit();
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mMaintainAlgorithm0.setChecked(!IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
                case 3:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity9 = IPSecSettingActivity.this;
                    iPSecSettingActivity9.mEncryAlgorithmIndex = iPSecSettingActivity9.mSharePref.getString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, "0");
                    IPSecSettingActivity iPSecSettingActivity10 = IPSecSettingActivity.this;
                    iPSecSettingActivity10.mMainAlgorithmIndex = iPSecSettingActivity10.mSharePref.getString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, "0");
                    if (IPSecSettingActivity.this.mNewValue.contains("1")) {
                        IPSecSettingActivity iPSecSettingActivity11 = IPSecSettingActivity.this;
                        iPSecSettingActivity11.mMainAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity11.mMainAlgorithmIndex).intValue() + 2);
                    } else {
                        IPSecSettingActivity iPSecSettingActivity12 = IPSecSettingActivity.this;
                        iPSecSettingActivity12.mMainAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity12.mMainAlgorithmIndex).intValue() - 2);
                    }
                    IPSecSettingActivity iPSecSettingActivity13 = IPSecSettingActivity.this;
                    iPSecSettingActivity13.mResp = iPSecSettingActivity13.sendAt("AT+SPENGMDVOLTE=10,1,1," + IPSecSettingActivity.this.mMainAlgorithmIndex + "," + IPSecSettingActivity.this.mEncryAlgorithmIndex, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.6
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mMaintainAlgorithm1.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                SharedPreferences.Editor editor = IPSecSettingActivity.this.mSharePref.edit();
                                editor.putString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, IPSecSettingActivity.this.mMainAlgorithmIndex);
                                editor.commit();
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.7
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mMaintainAlgorithm0.setChecked(!IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
                case 4:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity14 = IPSecSettingActivity.this;
                    iPSecSettingActivity14.mMainAlgorithmIndex = iPSecSettingActivity14.mSharePref.getString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, "0");
                    IPSecSettingActivity iPSecSettingActivity15 = IPSecSettingActivity.this;
                    iPSecSettingActivity15.mEncryAlgorithmIndex = iPSecSettingActivity15.mSharePref.getString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, "0");
                    if (IPSecSettingActivity.this.mNewValue.contains("1")) {
                        IPSecSettingActivity iPSecSettingActivity16 = IPSecSettingActivity.this;
                        iPSecSettingActivity16.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity16.mEncryAlgorithmIndex).intValue() + 1);
                    } else {
                        IPSecSettingActivity iPSecSettingActivity17 = IPSecSettingActivity.this;
                        iPSecSettingActivity17.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity17.mEncryAlgorithmIndex).intValue() - 1);
                    }
                    IPSecSettingActivity iPSecSettingActivity18 = IPSecSettingActivity.this;
                    iPSecSettingActivity18.mResp = iPSecSettingActivity18.sendAt("AT+SPENGMDVOLTE=10,1,1," + IPSecSettingActivity.this.mMainAlgorithmIndex + "," + IPSecSettingActivity.this.mEncryAlgorithmIndex, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.8
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm0.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                SharedPreferences.Editor editor = IPSecSettingActivity.this.mSharePref.edit();
                                editor.putString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, IPSecSettingActivity.this.mEncryAlgorithmIndex);
                                editor.commit();
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.9
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm0.setChecked(!IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
                case 5:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity19 = IPSecSettingActivity.this;
                    iPSecSettingActivity19.mMainAlgorithmIndex = iPSecSettingActivity19.mSharePref.getString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, "0");
                    IPSecSettingActivity iPSecSettingActivity20 = IPSecSettingActivity.this;
                    iPSecSettingActivity20.mEncryAlgorithmIndex = iPSecSettingActivity20.mSharePref.getString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, "0");
                    if (IPSecSettingActivity.this.mNewValue.contains("1")) {
                        IPSecSettingActivity iPSecSettingActivity21 = IPSecSettingActivity.this;
                        iPSecSettingActivity21.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity21.mEncryAlgorithmIndex).intValue() + 2);
                    } else {
                        IPSecSettingActivity iPSecSettingActivity22 = IPSecSettingActivity.this;
                        iPSecSettingActivity22.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity22.mEncryAlgorithmIndex).intValue() - 2);
                    }
                    IPSecSettingActivity iPSecSettingActivity23 = IPSecSettingActivity.this;
                    iPSecSettingActivity23.mResp = iPSecSettingActivity23.sendAt("AT+SPENGMDVOLTE=10,1,1," + IPSecSettingActivity.this.mMainAlgorithmIndex + "," + IPSecSettingActivity.this.mEncryAlgorithmIndex, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.10
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm1.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                SharedPreferences.Editor editor = IPSecSettingActivity.this.mSharePref.edit();
                                editor.putString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, IPSecSettingActivity.this.mEncryAlgorithmIndex);
                                editor.commit();
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.11
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm1.setChecked(!IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
                case 6:
                    IPSecSettingActivity.this.mNewValue = (String) msg.obj;
                    IPSecSettingActivity iPSecSettingActivity24 = IPSecSettingActivity.this;
                    iPSecSettingActivity24.mMainAlgorithmIndex = iPSecSettingActivity24.mSharePref.getString(IPSecSettingActivity.KEY_MAIN_ALGORITHM, "0");
                    IPSecSettingActivity iPSecSettingActivity25 = IPSecSettingActivity.this;
                    iPSecSettingActivity25.mEncryAlgorithmIndex = iPSecSettingActivity25.mSharePref.getString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, "0");
                    if (IPSecSettingActivity.this.mNewValue.contains("1")) {
                        IPSecSettingActivity iPSecSettingActivity26 = IPSecSettingActivity.this;
                        iPSecSettingActivity26.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity26.mEncryAlgorithmIndex).intValue() + 4);
                    } else {
                        IPSecSettingActivity iPSecSettingActivity27 = IPSecSettingActivity.this;
                        iPSecSettingActivity27.mEncryAlgorithmIndex = Integer.toString(Integer.valueOf(iPSecSettingActivity27.mEncryAlgorithmIndex).intValue() - 4);
                    }
                    IPSecSettingActivity iPSecSettingActivity28 = IPSecSettingActivity.this;
                    iPSecSettingActivity28.mResp = iPSecSettingActivity28.sendAt("AT+SPENGMDVOLTE=10,1,1," + IPSecSettingActivity.this.mMainAlgorithmIndex + "," + IPSecSettingActivity.this.mEncryAlgorithmIndex, "atchannel0");
                    if (IPSecSettingActivity.this.mResp.contains("OK")) {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.12
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm2.setChecked(IPSecSettingActivity.this.mNewValue.contains("1"));
                                SharedPreferences.Editor editor = IPSecSettingActivity.this.mSharePref.edit();
                                editor.putString(IPSecSettingActivity.KEY_ENCRY_ALGORITHM, IPSecSettingActivity.this.mEncryAlgorithmIndex);
                                editor.commit();
                            }
                        });
                    } else {
                        IPSecSettingActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.IPSecSettingActivity.IpsecHandler.13
                            @Override // java.lang.Runnable
                            public void run() {
                                IPSecSettingActivity.this.mEncryptionAlgorithm2.setChecked(!IPSecSettingActivity.this.mNewValue.contains("1"));
                            }
                        });
                        Toast.makeText(IPSecSettingActivity.this.mContext, "Fail", 0).show();
                    }
                    break;
            }
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public String anayResult(int mesg, String result) {
        if (!result.contains("OK")) {
            return "FAILED";
        }
        if (mesg != 0) {
            return null;
        }
        String res = result.split("\\:")[1].split("\n")[0].trim();
        Log.d(TAG, "MSG_GET_IPSEC_STATE anayResult is " + res);
        return res;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAlgorithm(String value, boolean isEnabled) {
        if (!value.contains("FAILED")) {
            String mainAlgorithm = value.split("\\,")[3];
            String encryptionAlgorithm = value.split("\\,")[4];
            boolean mMA0Ischecked = (Integer.valueOf(mainAlgorithm.trim()).intValue() & 1) > 0;
            boolean mMA1Ischecked = ((Integer.valueOf(mainAlgorithm.trim()).intValue() >> 1) & 1) > 0;
            boolean mEA0Ischecked = (Integer.valueOf(encryptionAlgorithm.trim()).intValue() & 1) > 0;
            boolean mEA1Ischecked = ((Integer.valueOf(encryptionAlgorithm.trim()).intValue() >> 1) & 1) > 0;
            boolean mEA2Ischecked = ((Integer.valueOf(encryptionAlgorithm.trim()).intValue() >> 2) & 1) > 0;
            enabledAlgorithm(isEnabled);
            this.mMaintainAlgorithm0.setChecked(mMA0Ischecked);
            this.mMaintainAlgorithm1.setChecked(mMA1Ischecked);
            this.mEncryptionAlgorithm0.setChecked(mEA0Ischecked);
            this.mEncryptionAlgorithm1.setChecked(mEA1Ischecked);
            this.mEncryptionAlgorithm2.setChecked(mEA2Ischecked);
            SharedPreferences.Editor editor = this.mSharePref.edit();
            editor.putString(KEY_MAIN_ALGORITHM, mainAlgorithm);
            editor.putString(KEY_ENCRY_ALGORITHM, encryptionAlgorithm);
            editor.commit();
            return;
        }
        enabledAlgorithm(false);
        this.mMaintainAlgorithm0.setChecked(false);
        this.mMaintainAlgorithm1.setChecked(false);
        this.mEncryptionAlgorithm0.setChecked(false);
        this.mEncryptionAlgorithm1.setChecked(false);
        this.mEncryptionAlgorithm2.setChecked(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enabledAlgorithm(boolean isEnabled) {
        this.mMaintainAlgorithm0.setEnabled(isEnabled);
        this.mMaintainAlgorithm1.setEnabled(isEnabled);
        this.mEncryptionAlgorithm0.setEnabled(isEnabled);
        this.mEncryptionAlgorithm1.setEnabled(isEnabled);
        this.mEncryptionAlgorithm2.setEnabled(isEnabled);
    }
}
