package com.sprd.engineermode.debuglog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class LockFrequencyActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {
    private static final int G_BAND_QUERY = 5;
    private static final String G_FREQUENCY_RESULT = "com.sprd.engineermode.action.GFREQUENCY";
    private static final String G_LOCKED_BAND = "g_locked_band";
    private static final int G_QUERY = 2;
    private static final String ISDUALMODE = "dual_mode";
    private static final String KEY_G_BAND_QUERY = "g_band_query";
    private static final String KEY_G_FREQUENCY = "g_frequency";
    private static final String KEY_G_QUERY = "g_frequency_query";
    private static final String KEY_G_QUERY_SIM = "g_frequency_sim";
    private static final String KEY_LTE_BAND_QUERY = "lte_band_query";
    private static final String KEY_LTE_QUERY = "lte_frequency_query";
    private static final String KEY_TD_BAND_QUERY = "t_band_query";
    private static final String KEY_TW_FREQUENCY = "tw_frequency";
    private static final String KEY_TW_QUERY = "tw_frequency_query";
    private static final String KEY_TW_QUERY_SIM = "tw_frequency_sim";
    private static final String KEY_W_BAND_QUERY = "w_band_query";
    private static final String LOCKED_BAND = "locked_band";
    private static final int LTE_BAND_QUERY = 6;
    private static final String MODEM_TYPE = "modemtype";
    private static final String NET_MODE = "net_mode";
    private static final String TAG = "LockFrequencyActivity";
    private static final int TD_BAND_QUERY = 3;
    private static final String TW_FREQUENCY_RESULT = "com.sprd.engineermode.action.TWFREQUENCY";
    private static final int TW_QUERY = 1;
    private static final int W_BAND_QUERY = 4;
    private Preference[] mFrePreference;
    private String mIntentAction;
    private String mLockBand;
    private LockFHandler mLockFHandler;
    private int mModemType;
    private String mNetMode;
    private int mPhoneCount;
    private PreferenceCategory mPreferenceCategory;
    private ProgressDialog mProgressDialog;
    private Handler mUiThread = new Handler();
    PreferenceGroup mPreGroup = null;
    private int mSIM = 0;
    private String mResult = null;
    private String mServerName = "atchannel0";
    private boolean mIsDualMode = false;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mFrePreference = new Preference[this.mPhoneCount];
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        for (int i = 0; i < this.mPhoneCount; i++) {
            this.mModemType = TelephonyManagerSprd.getModemType();
            int i2 = this.mModemType;
            if (i2 == 1) {
                this.mPreferenceCategory = new PreferenceCategory(this);
                this.mPreferenceCategory.setTitle("SIM" + i);
                this.mPreGroup.addPreference(this.mPreferenceCategory);
                this.mFrePreference[i] = new Preference(this);
                this.mFrePreference[i].setKey(KEY_TW_QUERY + i);
                this.mFrePreference[i].setTitle(R.string.frequency_set);
                this.mFrePreference[i].setSummary(R.string.click_query_set_frequency);
                this.mFrePreference[i].setOnPreferenceClickListener(this);
                this.mPreGroup.addPreference(this.mFrePreference[i]);
                this.mFrePreference[i].setEnabled(false);
            } else if (i2 == 2) {
                this.mPreferenceCategory = new PreferenceCategory(this);
                this.mPreferenceCategory.setTitle("SIM" + i);
                this.mPreGroup.addPreference(this.mPreferenceCategory);
                this.mFrePreference[i] = new Preference(this);
                this.mFrePreference[i].setKey(KEY_TW_QUERY + i);
                this.mFrePreference[i].setTitle(R.string.frequency_set);
                this.mFrePreference[i].setSummary(R.string.click_query_set_frequency);
                this.mFrePreference[i].setOnPreferenceClickListener(this);
                this.mPreGroup.addPreference(this.mFrePreference[i]);
                this.mFrePreference[i].setEnabled(false);
            } else if (i2 == 0) {
                this.mPreferenceCategory = new PreferenceCategory(this);
                this.mPreferenceCategory.setTitle("SIM" + i);
                this.mPreGroup.addPreference(this.mPreferenceCategory);
                this.mFrePreference[i] = new Preference(this);
                this.mFrePreference[i].setKey(KEY_G_QUERY + i);
                this.mFrePreference[i].setTitle(R.string.frequency_set);
                this.mFrePreference[i].setSummary(R.string.click_query_set_frequency);
                this.mFrePreference[i].setOnPreferenceClickListener(this);
                this.mPreGroup.addPreference(this.mFrePreference[i]);
                this.mFrePreference[i].setEnabled(false);
            } else if (i2 == 3) {
                this.mPreferenceCategory = new PreferenceCategory(this);
                this.mPreferenceCategory.setTitle("SIM" + i);
                this.mPreGroup.addPreference(this.mPreferenceCategory);
                this.mFrePreference[i] = new Preference(this);
                this.mFrePreference[i].setKey(KEY_G_QUERY + i);
                this.mFrePreference[i].setTitle(R.string.frequency_set);
                this.mFrePreference[i].setSummary(R.string.click_query_set_frequency);
                this.mFrePreference[i].setOnPreferenceClickListener(this);
                this.mPreGroup.addPreference(this.mFrePreference[i]);
                this.mFrePreference[i].setEnabled(false);
            }
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mLockFHandler = new LockFHandler(ht.getLooper());
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mLockFHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mLockFHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        String key = pref.getKey();
        Log.d(TAG, "key is " + key);
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (key.equals(KEY_TW_QUERY + i)) {
                Message twQuery = this.mLockFHandler.obtainMessage(1, Integer.valueOf(i));
                this.mLockFHandler.sendMessage(twQuery);
            } else {
                if (key.equals(KEY_G_QUERY + i)) {
                    Message gQuery = this.mLockFHandler.obtainMessage(2, Integer.valueOf(i));
                    this.mLockFHandler.sendMessage(gQuery);
                } else {
                    if (key.equals(KEY_TD_BAND_QUERY + i)) {
                        Message tBand = this.mLockFHandler.obtainMessage(3, Integer.valueOf(i));
                        this.mLockFHandler.sendMessage(tBand);
                    } else {
                        if (key.equals(KEY_W_BAND_QUERY + i)) {
                            Message wBand = this.mLockFHandler.obtainMessage(4, Integer.valueOf(i));
                            this.mLockFHandler.sendMessage(wBand);
                        } else {
                            if (key.equals(KEY_G_BAND_QUERY + i)) {
                                Message gBand = this.mLockFHandler.obtainMessage(5, Integer.valueOf(i));
                                this.mLockFHandler.sendMessage(gBand);
                            } else {
                                if (key.equals(KEY_LTE_BAND_QUERY + i)) {
                                    Message lteBand = this.mLockFHandler.obtainMessage(6, Integer.valueOf(i));
                                    this.mLockFHandler.sendMessage(lteBand);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isLocked(String resp) {
        if (resp == null) {
            return false;
        }
        String[] str = resp.split("\\:");
        String[] str1 = str[1].split("\\,");
        String[] str2 = str1[2].split("\n");
        String result = str2[0].trim();
        if (!result.contains("1")) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String queryWCDMABandLocked(String serverName) {
        String result = "";
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                String resp = IATUtils.sendATCmd("AT+SPFDDBAND=0,1", serverName);
                if (resp.contains("OK") && isLocked(resp)) {
                    result = "1";
                }
            } else if (i == 1) {
                String resp2 = IATUtils.sendATCmd("AT+SPFDDBAND=0,2", serverName);
                if (resp2.contains("OK") && isLocked(resp2)) {
                    result = result + ",2";
                }
            } else if (i == 2) {
                String resp3 = IATUtils.sendATCmd("AT+SPFDDBAND=0,5", serverName);
                if (resp3.contains("OK") && isLocked(resp3)) {
                    result = result + ",5";
                }
            } else if (i == 3) {
                String resp4 = IATUtils.sendATCmd("AT+SPFDDBAND=0,8", serverName);
                if (resp4.contains("OK") && isLocked(resp4)) {
                    result = result + ",8";
                }
            }
        }
        Log.d(TAG, "ATChannel is " + serverName + ",queryWCDMABandLocked is " + result);
        return result;
    }

    class LockFHandler extends Handler {
        public LockFHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String gRespLock;
            String gRespLock2;
            String gRespLock3;
            switch (msg.what) {
                case 1:
                    int sim = ((Integer) msg.obj).intValue();
                    LockFrequencyActivity.this.mServerName = "atchannel" + sim;
                    String responValue = IATUtils.sendATCmd("AT+SPFRQ?", LockFrequencyActivity.this.mServerName);
                    Log.d(LockFrequencyActivity.TAG, "<" + sim + ">Channel is " + LockFrequencyActivity.this.mServerName + ",TW_QUERY is " + responValue);
                    Intent intent = new Intent(LockFrequencyActivity.TW_FREQUENCY_RESULT);
                    Bundle bundle = new Bundle();
                    bundle.putString(LockFrequencyActivity.KEY_TW_FREQUENCY, responValue);
                    bundle.putInt(LockFrequencyActivity.KEY_TW_QUERY_SIM, sim);
                    intent.putExtras(bundle);
                    LockFrequencyActivity.this.startActivity(intent);
                    return;
                case 2:
                    int sim2 = ((Integer) msg.obj).intValue();
                    LockFrequencyActivity.this.mServerName = "atchannel" + sim2;
                    String responValue2 = IATUtils.sendATCmd("AT+SPFRQ?", LockFrequencyActivity.this.mServerName);
                    Log.d(LockFrequencyActivity.TAG, "<" + sim2 + ">Channel is " + LockFrequencyActivity.this.mServerName + ",G_QUERY is " + responValue2);
                    Intent intent2 = new Intent(LockFrequencyActivity.G_FREQUENCY_RESULT);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(LockFrequencyActivity.KEY_G_FREQUENCY, responValue2);
                    bundle2.putInt(LockFrequencyActivity.KEY_G_QUERY_SIM, sim2);
                    intent2.putExtras(bundle2);
                    LockFrequencyActivity.this.startActivity(intent2);
                    return;
                case 3:
                    LockFrequencyActivity.this.mSIM = ((Integer) msg.obj).intValue();
                    if (LockFrequencyActivity.this.mSIM == 0) {
                        LockFrequencyActivity.this.mServerName = "atchannel0";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.FIRBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 1) {
                        LockFrequencyActivity.this.mServerName = "atchannel1";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.SECBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 2) {
                        LockFrequencyActivity.this.mServerName = "atchannel2";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.TRIBANDMODESET";
                    }
                    LockFrequencyActivity.this.showProgressDialog();
                    String responValue3 = IATUtils.sendATCmd(engconstents.ENG_AT_NETMODE1, LockFrequencyActivity.this.mServerName);
                    Log.d(LockFrequencyActivity.TAG, "Dual-mode Result is " + responValue3);
                    if (responValue3.contains("OK")) {
                        String[] str = responValue3.split("\\:");
                        String[] str1 = str[1].split("\\,");
                        String result = str1[0].trim();
                        if (result.contains("2")) {
                            LockFrequencyActivity.this.mIsDualMode = true;
                            LockFrequencyActivity.this.mNetMode = "2";
                            gRespLock = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                            LockFrequencyActivity lockFrequencyActivity = LockFrequencyActivity.this;
                            lockFrequencyActivity.mLockBand = IATUtils.sendATCmd(engconstents.ENG_AT_TD_LOCKED_BAND, lockFrequencyActivity.mServerName);
                        } else if (result.contains("13")) {
                            LockFrequencyActivity.this.mIsDualMode = false;
                            LockFrequencyActivity.this.mNetMode = "13";
                            gRespLock = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                        } else {
                            if (result.contains("15")) {
                                LockFrequencyActivity.this.mIsDualMode = false;
                                LockFrequencyActivity.this.mNetMode = "15";
                                LockFrequencyActivity lockFrequencyActivity2 = LockFrequencyActivity.this;
                                lockFrequencyActivity2.mLockBand = IATUtils.sendATCmd(engconstents.ENG_AT_TD_LOCKED_BAND, lockFrequencyActivity2.mServerName);
                            }
                            gRespLock = null;
                        }
                    } else {
                        gRespLock = null;
                    }
                    LockFrequencyActivity.this.dismissProgressDialog();
                    Intent intent3 = new Intent(LockFrequencyActivity.this.mIntentAction);
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean(LockFrequencyActivity.ISDUALMODE, LockFrequencyActivity.this.mIsDualMode);
                    bundle3.putInt(LockFrequencyActivity.MODEM_TYPE, 1);
                    bundle3.putString(LockFrequencyActivity.NET_MODE, LockFrequencyActivity.this.mNetMode);
                    bundle3.putString(LockFrequencyActivity.G_LOCKED_BAND, gRespLock);
                    bundle3.putString(LockFrequencyActivity.LOCKED_BAND, LockFrequencyActivity.this.mLockBand);
                    intent3.putExtras(bundle3);
                    LockFrequencyActivity.this.startActivity(intent3);
                    LockFrequencyActivity.this.mIsDualMode = false;
                    Log.d(LockFrequencyActivity.TAG, "<" + LockFrequencyActivity.this.mSIM + ">Channel is " + LockFrequencyActivity.this.mServerName + ",TD_LOCK_BAND is " + LockFrequencyActivity.this.mLockBand);
                    return;
                case 4:
                    LockFrequencyActivity.this.mSIM = ((Integer) msg.obj).intValue();
                    if (LockFrequencyActivity.this.mSIM == 0) {
                        LockFrequencyActivity.this.mServerName = "atchannel0";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.FIRBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 1) {
                        LockFrequencyActivity.this.mServerName = "atchannel1";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.SECBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 2) {
                        LockFrequencyActivity.this.mServerName = "atchannel2";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.TRIBANDMODESET";
                    }
                    LockFrequencyActivity.this.showProgressDialog();
                    String responValue4 = IATUtils.sendATCmd(engconstents.ENG_AT_NETMODE1, LockFrequencyActivity.this.mServerName);
                    Log.d(LockFrequencyActivity.TAG, "<" + LockFrequencyActivity.this.mSIM + ">Dual-mode Result is " + responValue4);
                    if (responValue4.contains("OK")) {
                        String[] str2 = responValue4.split("\\:");
                        String[] str3 = str2[1].split("\\,");
                        String result2 = str3[0].trim();
                        if (result2.contains("2")) {
                            LockFrequencyActivity.this.mIsDualMode = true;
                            LockFrequencyActivity.this.mNetMode = "2";
                            gRespLock2 = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                            LockFrequencyActivity lockFrequencyActivity3 = LockFrequencyActivity.this;
                            lockFrequencyActivity3.mLockBand = lockFrequencyActivity3.queryWCDMABandLocked(lockFrequencyActivity3.mServerName);
                        } else if (result2.contains("13")) {
                            LockFrequencyActivity.this.mIsDualMode = false;
                            LockFrequencyActivity.this.mNetMode = "13";
                            gRespLock2 = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                        } else {
                            if (result2.contains("14")) {
                                LockFrequencyActivity.this.mIsDualMode = false;
                                LockFrequencyActivity.this.mNetMode = "14";
                                LockFrequencyActivity lockFrequencyActivity4 = LockFrequencyActivity.this;
                                lockFrequencyActivity4.mLockBand = lockFrequencyActivity4.queryWCDMABandLocked(lockFrequencyActivity4.mServerName);
                            }
                            gRespLock2 = null;
                        }
                    } else {
                        gRespLock2 = null;
                    }
                    LockFrequencyActivity.this.dismissProgressDialog();
                    Intent intent4 = new Intent(LockFrequencyActivity.this.mIntentAction);
                    Bundle bundle4 = new Bundle();
                    bundle4.putBoolean(LockFrequencyActivity.ISDUALMODE, LockFrequencyActivity.this.mIsDualMode);
                    bundle4.putInt(LockFrequencyActivity.MODEM_TYPE, 2);
                    bundle4.putString(LockFrequencyActivity.NET_MODE, LockFrequencyActivity.this.mNetMode);
                    bundle4.putString(LockFrequencyActivity.G_LOCKED_BAND, gRespLock2);
                    bundle4.putString(LockFrequencyActivity.LOCKED_BAND, LockFrequencyActivity.this.mLockBand);
                    intent4.putExtras(bundle4);
                    LockFrequencyActivity.this.startActivity(intent4);
                    LockFrequencyActivity.this.mIsDualMode = false;
                    Log.d(LockFrequencyActivity.TAG, "<" + LockFrequencyActivity.this.mSIM + ">Channel is " + LockFrequencyActivity.this.mServerName + ",W_LOCK_BAND is " + LockFrequencyActivity.this.mLockBand);
                    return;
                case 5:
                    LockFrequencyActivity.this.mSIM = ((Integer) msg.obj).intValue();
                    if (LockFrequencyActivity.this.mSIM == 0) {
                        LockFrequencyActivity.this.mServerName = "atchannel0";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.FIRBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 1) {
                        LockFrequencyActivity.this.mServerName = "atchannel1";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.SECBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 2) {
                        LockFrequencyActivity.this.mServerName = "atchannel2";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.TRIBANDMODESET";
                    }
                    LockFrequencyActivity.this.showProgressDialog();
                    LockFrequencyActivity lockFrequencyActivity5 = LockFrequencyActivity.this;
                    lockFrequencyActivity5.mLockBand = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, lockFrequencyActivity5.mServerName);
                    LockFrequencyActivity.this.dismissProgressDialog();
                    Intent intent5 = new Intent(LockFrequencyActivity.this.mIntentAction);
                    Bundle bundle5 = new Bundle();
                    bundle5.putBoolean(LockFrequencyActivity.ISDUALMODE, LockFrequencyActivity.this.mIsDualMode);
                    bundle5.putInt(LockFrequencyActivity.MODEM_TYPE, 0);
                    bundle5.putString(LockFrequencyActivity.NET_MODE, "13");
                    bundle5.putString(LockFrequencyActivity.G_LOCKED_BAND, LockFrequencyActivity.this.mLockBand);
                    intent5.putExtras(bundle5);
                    LockFrequencyActivity.this.startActivity(intent5);
                    Log.d(LockFrequencyActivity.TAG, "<" + LockFrequencyActivity.this.mSIM + ">Channel is " + LockFrequencyActivity.this.mServerName + ",G_LOCK_BAND is " + LockFrequencyActivity.this.mLockBand);
                    break;
                case 6:
                    LockFrequencyActivity.this.mSIM = ((Integer) msg.obj).intValue();
                    if (LockFrequencyActivity.this.mSIM == 0) {
                        LockFrequencyActivity.this.mServerName = "atchannel0";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.FIRBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 1) {
                        LockFrequencyActivity.this.mServerName = "atchannel1";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.SECBANDMODESET";
                    } else if (LockFrequencyActivity.this.mSIM == 2) {
                        LockFrequencyActivity.this.mServerName = "atchannel2";
                        LockFrequencyActivity.this.mIntentAction = "com.sprd.engineermode.action.TRIBANDMODESET";
                    }
                    LockFrequencyActivity.this.showProgressDialog();
                    String responValue5 = IATUtils.sendATCmd(engconstents.ENG_AT_NETMODE1, LockFrequencyActivity.this.mServerName);
                    Log.d(LockFrequencyActivity.TAG, "Dual-mode Result is " + responValue5);
                    LockFrequencyActivity.this.mNetMode = "";
                    if (!responValue5.contains("OK")) {
                        gRespLock3 = null;
                    } else {
                        String[] str4 = responValue5.split("\\:");
                        String[] str5 = str4[1].split("\\,");
                        String result3 = str5[0].trim();
                        if (result3.contains("2")) {
                            LockFrequencyActivity.this.mIsDualMode = true;
                            LockFrequencyActivity.this.mNetMode = "2";
                            gRespLock3 = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                            LockFrequencyActivity lockFrequencyActivity6 = LockFrequencyActivity.this;
                            lockFrequencyActivity6.mLockBand = IATUtils.sendATCmd(engconstents.ENG_AT_TD_LOCKED_BAND, lockFrequencyActivity6.mServerName);
                            Log.d(LockFrequencyActivity.TAG, engconstents.ENG_AT_TD_LOCKED_BAND + LockFrequencyActivity.this.mLockBand);
                            if (!gRespLock3.contains("OK") || !LockFrequencyActivity.this.mLockBand.contains("OK")) {
                                LockFrequencyActivity.this.dismissProgressDialog();
                                return;
                            }
                        } else if (result3.contains("13")) {
                            LockFrequencyActivity.this.mIsDualMode = false;
                            LockFrequencyActivity.this.mNetMode = "13";
                            gRespLock3 = IATUtils.sendATCmd(engconstents.ENG_AT_CURRENT_GSMBAND, LockFrequencyActivity.this.mServerName);
                            if (!gRespLock3.contains("OK")) {
                                LockFrequencyActivity.this.dismissProgressDialog();
                                return;
                            }
                        } else {
                            if (result3.contains("15")) {
                                LockFrequencyActivity.this.mIsDualMode = false;
                                LockFrequencyActivity.this.mNetMode = "15";
                                LockFrequencyActivity lockFrequencyActivity7 = LockFrequencyActivity.this;
                                lockFrequencyActivity7.mLockBand = IATUtils.sendATCmd(engconstents.ENG_AT_TD_LOCKED_BAND, lockFrequencyActivity7.mServerName);
                                if (!LockFrequencyActivity.this.mLockBand.contains("OK")) {
                                    LockFrequencyActivity.this.dismissProgressDialog();
                                    return;
                                }
                            }
                            gRespLock3 = null;
                        }
                        LockFrequencyActivity.this.dismissProgressDialog();
                    }
                    LockFrequencyActivity.this.dismissProgressDialog();
                    Intent intent6 = new Intent(LockFrequencyActivity.this.mIntentAction);
                    Bundle bundle6 = new Bundle();
                    bundle6.putBoolean(LockFrequencyActivity.ISDUALMODE, LockFrequencyActivity.this.mIsDualMode);
                    bundle6.putInt(LockFrequencyActivity.MODEM_TYPE, 1);
                    bundle6.putString(LockFrequencyActivity.NET_MODE, LockFrequencyActivity.this.mNetMode);
                    bundle6.putString(LockFrequencyActivity.G_LOCKED_BAND, gRespLock3);
                    bundle6.putString(LockFrequencyActivity.LOCKED_BAND, LockFrequencyActivity.this.mLockBand);
                    intent6.putExtras(bundle6);
                    LockFrequencyActivity.this.startActivity(intent6);
                    LockFrequencyActivity.this.mIsDualMode = false;
                    Log.d(LockFrequencyActivity.TAG, "<" + LockFrequencyActivity.this.mSIM + ">Channel is " + LockFrequencyActivity.this.mServerName + ",TD_LOCK_BAND is " + LockFrequencyActivity.this.mLockBand);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgressDialog() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.LockFrequencyActivity.1
            @Override // java.lang.Runnable
            public void run() {
                LockFrequencyActivity lockFrequencyActivity = LockFrequencyActivity.this;
                lockFrequencyActivity.mProgressDialog = ProgressDialog.show(lockFrequencyActivity, "Query...", "Please wait...", true, false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.LockFrequencyActivity.2
            @Override // java.lang.Runnable
            public void run() {
                if (LockFrequencyActivity.this.mProgressDialog != null) {
                    LockFrequencyActivity.this.mProgressDialog.dismiss();
                }
            }
        });
    }
}
