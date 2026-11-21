package com.sprd.engineermode.telephony;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class DiversityPrefActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int GET_STATUS = 0;
    private static final String LTEDIVERSITYRX = "lte_Div_RX";
    private static final String LTEPRIMARYRX = "lte_Pri_RX";
    private static final String LTEPRIMARYTX = "lte_Pri_TX";
    private static final String LTESCCTX = "lte_SCC_TX";
    private static final int SET_LTEDivRX_STATUS = 3;
    private static final int SET_LTEPrimRX_STATUS = 5;
    private static final int SET_LTEPrimTX_STATUS = 4;
    private static final int SET_LTESCCTX_STATUS = 2;
    private static final int SET_WDiv_STATUS = 1;
    private static final String TAG = "DiversityPrefActivity";
    private static final String WDIVERSITY = "w_Div";
    private int PARA1;
    private int PARA2;
    private String mATCmd;
    private String mATResponse;
    private Context mContext;
    private DivHandler mDivHandler;
    private ListPreference mLTEDiversityRX;
    private ListPreference mLTEPrimRX;
    private ListPreference mLTEPrimTX;
    private ListPreference mLTESCCTX;
    private ITelephonyApi.IRadioFunc.DualRfState mState;
    private ListPreference mWDiversity;
    private int type;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mDivHandler = new DivHandler(ht.getLooper());
        addPreferencesFromResource(R.xml.pref_diversity);
        this.mWDiversity = (ListPreference) findPreference(WDIVERSITY);
        this.mWDiversity.setOnPreferenceChangeListener(this);
        this.mLTESCCTX = (ListPreference) findPreference(LTESCCTX);
        this.mLTESCCTX.setOnPreferenceChangeListener(this);
        this.mLTEDiversityRX = (ListPreference) findPreference(LTEDIVERSITYRX);
        this.mLTEDiversityRX.setOnPreferenceChangeListener(this);
        this.mLTEPrimTX = (ListPreference) findPreference(LTEPRIMARYTX);
        this.mLTEPrimTX.setOnPreferenceChangeListener(this);
        this.mLTEPrimRX = (ListPreference) findPreference(LTEPRIMARYRX);
        this.mLTEPrimRX.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        Message getStatus = this.mDivHandler.obtainMessage(0);
        this.mDivHandler.sendMessage(getStatus);
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        if (pref == this.mWDiversity) {
            Message setmDiversitystatus = this.mDivHandler.obtainMessage(1, Integer.parseInt(String.valueOf(newValue)), 0);
            this.mDivHandler.sendMessage(setmDiversitystatus);
        } else if (pref == this.mLTESCCTX) {
            Message setmSCCTXstatus = this.mDivHandler.obtainMessage(2, Integer.parseInt(String.valueOf(newValue)), 0);
            this.mDivHandler.sendMessage(setmSCCTXstatus);
        } else if (pref == this.mLTEDiversityRX) {
            Message setmDiversityRXstatus = this.mDivHandler.obtainMessage(3, Integer.parseInt(String.valueOf(newValue)), 0);
            this.mDivHandler.sendMessage(setmDiversityRXstatus);
        } else if (pref == this.mLTEPrimTX) {
            Message setmPrimaryTXstatus = this.mDivHandler.obtainMessage(4, Integer.parseInt(String.valueOf(newValue)), 0);
            this.mDivHandler.sendMessage(setmPrimaryTXstatus);
        } else if (pref == this.mLTEPrimRX) {
            Log.d(TAG, "start KEY_mLTEPrimRX" + newValue);
            Message setmPrimaryRXstatus = this.mDivHandler.obtainMessage(5, Integer.parseInt(String.valueOf(newValue)), 0);
            this.mDivHandler.sendMessage(setmPrimaryRXstatus);
        }
        return true;
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        DivHandler divHandler = this.mDivHandler;
        if (divHandler != null) {
            divHandler.getLooper().quit();
            Log.d(TAG, "HandlerThread has quit");
        }
        super.onDestroy();
    }

    class DivHandler extends Handler {
        public DivHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                try {
                    DiversityPrefActivity.this.mState = DiversityPrefActivity.this.teleApi.radioFunc().getDualRfState();
                    DiversityPrefActivity diversityPrefActivity = DiversityPrefActivity.this;
                    diversityPrefActivity.initUI(diversityPrefActivity.mState);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 1) {
                DiversityPrefActivity.this.type = msg.arg1;
                Log.d(DiversityPrefActivity.TAG, "SET_WDiv_STATUS type=" + DiversityPrefActivity.this.type);
                DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(DiversityPrefActivity.TAG, "Run Set wDiv Status");
                        DiversityPrefActivity.this.mWDiversity.setSummary(DiversityPrefActivity.this.mWDiversity.getEntry());
                    }
                });
                if (DiversityPrefActivity.this.type == 0) {
                    DiversityPrefActivity.this.mState.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_ONLY;
                } else if (DiversityPrefActivity.this.type == 1) {
                    DiversityPrefActivity.this.mState.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_AND_DIVERSITY;
                } else {
                    DiversityPrefActivity.this.mState.wcdmaState = ITelephonyApi.IRadioFunc.WcdmaDualRfState.DIVERSITY_ONLY;
                }
                try {
                    DiversityPrefActivity.this.teleApi.radioFunc().setDualRfState(DiversityPrefActivity.this.mState);
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                DiversityPrefActivity.this.type = msg.arg1;
                Log.d(DiversityPrefActivity.TAG, "SET_LTESCCTX_STATUS type=" + DiversityPrefActivity.this.type);
                DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(DiversityPrefActivity.TAG, "Run Set mLTESCCTX Status");
                        DiversityPrefActivity.this.mLTESCCTX.setSummary(DiversityPrefActivity.this.mLTESCCTX.getEntry());
                    }
                });
                if (DiversityPrefActivity.this.type == 0) {
                    DiversityPrefActivity.this.mState.isLteSccTxOpened = true;
                } else if (DiversityPrefActivity.this.type == 1) {
                    DiversityPrefActivity.this.mState.isLteSccTxOpened = false;
                }
                try {
                    DiversityPrefActivity.this.teleApi.radioFunc().setDualRfState(DiversityPrefActivity.this.mState);
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 3) {
                DiversityPrefActivity.this.type = msg.arg1;
                Log.d(DiversityPrefActivity.TAG, "SET_LTEDivRX_STATUS type=" + DiversityPrefActivity.this.type);
                DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.6
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(DiversityPrefActivity.TAG, "Run Set mLTEDiversityRX Status");
                        DiversityPrefActivity.this.mLTEDiversityRX.setSummary(DiversityPrefActivity.this.mLTEDiversityRX.getEntry());
                    }
                });
                if (DiversityPrefActivity.this.type == 0) {
                    DiversityPrefActivity.this.mState.isLteDiversityRxOpened = true;
                } else if (DiversityPrefActivity.this.type == 1) {
                    DiversityPrefActivity.this.mState.isLteDiversityRxOpened = false;
                }
                try {
                    DiversityPrefActivity.this.teleApi.radioFunc().setDualRfState(DiversityPrefActivity.this.mState);
                    return;
                } catch (Exception e4) {
                    e4.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 4) {
                DiversityPrefActivity.this.type = msg.arg1;
                Log.d(DiversityPrefActivity.TAG, "SET_LTEPrimTX_STATUS type=" + DiversityPrefActivity.this.type);
                DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.8
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(DiversityPrefActivity.TAG, "Run Set mLTEPrimTX Status");
                        DiversityPrefActivity.this.mLTEPrimTX.setSummary(DiversityPrefActivity.this.mLTEPrimTX.getEntry());
                    }
                });
                if (DiversityPrefActivity.this.type == 0) {
                    DiversityPrefActivity.this.mState.isLtePrimaryTxOpened = true;
                } else if (DiversityPrefActivity.this.type == 1) {
                    DiversityPrefActivity.this.mState.isLtePrimaryTxOpened = false;
                }
                try {
                    DiversityPrefActivity.this.teleApi.radioFunc().setDualRfState(DiversityPrefActivity.this.mState);
                    return;
                } catch (Exception e5) {
                    e5.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.9
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 5) {
                DiversityPrefActivity.this.type = msg.arg1;
                Log.d(DiversityPrefActivity.TAG, "SET_LTEPrimRX_STATUS type=" + DiversityPrefActivity.this.type);
                DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.10
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(DiversityPrefActivity.TAG, "Run Set mLTEPrimRX Status");
                        DiversityPrefActivity.this.mLTEPrimRX.setSummary(DiversityPrefActivity.this.mLTEPrimRX.getEntry());
                    }
                });
                if (DiversityPrefActivity.this.type == 0) {
                    DiversityPrefActivity.this.mState.isLtePrimaryRxOpened = true;
                } else if (DiversityPrefActivity.this.type == 1) {
                    DiversityPrefActivity.this.mState.isLtePrimaryRxOpened = false;
                }
                try {
                    DiversityPrefActivity.this.teleApi.radioFunc().setDualRfState(DiversityPrefActivity.this.mState);
                } catch (Exception e6) {
                    e6.printStackTrace();
                    DiversityPrefActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.DivHandler.11
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(DiversityPrefActivity.this.mContext, "AT cmd send fail", 0).show();
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initUI(final ITelephonyApi.IRadioFunc.DualRfState state) {
        this.PARA1 &= -5;
        Log.d(TAG, "initUI() PARA1 = " + this.PARA1);
        if (this.mWDiversity != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    int i = AnonymousClass6.$SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[state.wcdmaState.ordinal()];
                    if (i == 1) {
                        DiversityPrefActivity.this.mWDiversity.setValueIndex(0);
                        DiversityPrefActivity.this.mWDiversity.setSummary(DiversityPrefActivity.this.mWDiversity.getEntry());
                        Log.d(DiversityPrefActivity.TAG, "mWDiversity= " + ((Object) DiversityPrefActivity.this.mWDiversity.getEntry()));
                        return;
                    }
                    if (i == 2) {
                        DiversityPrefActivity.this.mWDiversity.setValueIndex(1);
                        DiversityPrefActivity.this.mWDiversity.setSummary(DiversityPrefActivity.this.mWDiversity.getEntry());
                    } else if (i == 3) {
                        DiversityPrefActivity.this.mWDiversity.setValueIndex(2);
                        DiversityPrefActivity.this.mWDiversity.setSummary(DiversityPrefActivity.this.mWDiversity.getEntry());
                    }
                }
            });
        }
        if (this.mLTESCCTX != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    if (state.isLteSccTxOpened) {
                        DiversityPrefActivity.this.mLTESCCTX.setValueIndex(0);
                        DiversityPrefActivity.this.mLTESCCTX.setSummary(DiversityPrefActivity.this.mLTESCCTX.getEntry());
                    } else {
                        DiversityPrefActivity.this.mLTESCCTX.setValueIndex(1);
                        DiversityPrefActivity.this.mLTESCCTX.setSummary(DiversityPrefActivity.this.mLTESCCTX.getEntry());
                    }
                }
            });
        }
        if (this.mLTEDiversityRX != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    if (state.isLteDiversityRxOpened) {
                        DiversityPrefActivity.this.mLTEDiversityRX.setValueIndex(0);
                        DiversityPrefActivity.this.mLTEDiversityRX.setSummary(DiversityPrefActivity.this.mLTEDiversityRX.getEntry());
                    } else {
                        DiversityPrefActivity.this.mLTEDiversityRX.setValueIndex(1);
                        DiversityPrefActivity.this.mLTEDiversityRX.setSummary(DiversityPrefActivity.this.mLTEDiversityRX.getEntry());
                    }
                }
            });
        }
        if (this.mLTEPrimTX != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.4
                @Override // java.lang.Runnable
                public void run() {
                    if (state.isLtePrimaryTxOpened) {
                        DiversityPrefActivity.this.mLTEPrimTX.setValueIndex(0);
                        DiversityPrefActivity.this.mLTEPrimTX.setSummary(DiversityPrefActivity.this.mLTEPrimTX.getEntry());
                    } else {
                        DiversityPrefActivity.this.mLTEPrimTX.setValueIndex(1);
                        DiversityPrefActivity.this.mLTEPrimTX.setSummary(DiversityPrefActivity.this.mLTEPrimTX.getEntry());
                    }
                }
            });
        }
        if (this.mLTEPrimRX != null) {
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.DiversityPrefActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    if (state.isLtePrimaryRxOpened) {
                        DiversityPrefActivity.this.mLTEPrimRX.setValueIndex(0);
                        DiversityPrefActivity.this.mLTEPrimRX.setSummary(DiversityPrefActivity.this.mLTEPrimRX.getEntry());
                    } else {
                        DiversityPrefActivity.this.mLTEPrimRX.setValueIndex(1);
                        DiversityPrefActivity.this.mLTEPrimRX.setSummary(DiversityPrefActivity.this.mLTEPrimRX.getEntry());
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.sprd.engineermode.telephony.DiversityPrefActivity$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState = new int[ITelephonyApi.IRadioFunc.WcdmaDualRfState.values().length];

        static {
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.PRIMARY_AND_DIVERSITY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$unisoc$engineermode$core$intf$ITelephonyApi$IRadioFunc$WcdmaDualRfState[ITelephonyApi.IRadioFunc.WcdmaDualRfState.DIVERSITY_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
