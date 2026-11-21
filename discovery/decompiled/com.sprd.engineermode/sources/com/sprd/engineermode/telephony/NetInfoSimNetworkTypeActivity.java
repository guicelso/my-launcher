package com.sprd.engineermode.telephony;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.ServiceStateProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class NetInfoSimNetworkTypeActivity extends PreferenceActivity {
    static final boolean DEBUG = true;
    private static final String KEY_FDD_CSFB = "FDD_CSFB";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String KEY_TDD_CSFB = "TDD_CSFB";
    private static final String KEY_TDD_SVLTE = "TDD_SVLTE";
    private static final int LTE_CHANGE = 0;
    private static final String LTE_INDEX = "LteIndex";
    private static final int NETWORK_CDMA1X = 6;
    private static final int NETWORK_CDMA1X_DO = 200;
    private static final int NETWORK_CDMA1X_LTE = 201;
    private static final int NETWORK_CDMADO = 5;
    private static final int NETWORK_GSM = 1;
    private static final int NETWORK_LTE = 4;
    private static final String NETWORK_STAT_CHANGE = "com.sprd.network.NETWORK_STAT_CHANGE";
    private static final int NETWORK_TDSCDMA = 2;
    private static final String NETWORK_TYPE = "NetWorkType";
    private static final int NETWORK_TYPE_LTE_CA = 19;
    private static final int NETWORK_UNKNOW = 0;
    private static final int NETWORK_WCDMA = 3;
    private static final String PREF_INDEX = "PrefenceIndex";
    private static final String TAG = "NetInfoSimNetworkTypeActivity";
    private int mDataNetType;
    private PhoneStateListener mPhoneStateListener;
    private int mPrefenceIndex;
    private PreferenceCategory mPreferenceCategory;
    private ArrayList<Preference> mSIMPref;
    private ServiceState mServiceState;
    private int mSimIndex;
    private TelephonyManager mTelephonyManager;
    private PreferenceGroup mPreGroup = null;
    private boolean mListening = false;
    private int mNetWorkType = 0;
    private int[] PrefenceName = null;
    private int[] PreferenceNameGSM = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_outfield_information};
    private int[] PreferenceNameGSMSupportLte = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_adjacent_cell_4g, R.string.netinfo_outfield_information};
    private int[] PreferenceName1xSupportLte = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell};
    private int[] PreferenceNameDoSupportLte = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_4g};
    private String[] PreferenceIndexGSM = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex4"};
    private String[] PreferenceIndexGSMSupportLte = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex3", "PrefenceIndex4"};
    private String[] PreferenceIndex1xSupportLte = {"PrefenceIndex0", "PrefenceIndex1"};
    private String[] PreferenceIndexDoSupportLte = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2"};
    private int[] PreferenceNameCDMA = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_2g, R.string.netinfo_outfield_information};
    private int[] PreferenceNameCDMASupportLte = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_2g, R.string.netinfo_adjacent_cell_4g, R.string.netinfo_outfield_information};
    private String[] PreferenceIndexCDMA = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex4"};
    private String[] PreferenceIndexCDMASupportLte = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex3", "PrefenceIndex4"};
    private int[] PreferenceNameLTE = {R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_2g, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_outfield_information};
    private String[] PreferenceIndexLTE = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex3", "PrefenceIndex4"};
    private int[] PreferenceNameLTE1X = {R.string.netinfo_server_cell_lte, R.string.netinfo_neighbour_cell_lte, R.string.netinfo_adjacent_cell_2g_lte, R.string.netinfo_adjacent_cell_3g_lte, R.string.netinfo_outfield_information_lte, R.string.netinfo_server_cell_1x, R.string.netinfo_neighbour_cell_1x};
    private String[] PreferenceIndexLTE1X = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex3", "PrefenceIndex4", "PrefenceIndex5", "PrefenceIndex6"};
    private int[] PreferenceNameDO1X = {R.string.netinfo_server_cell_do, R.string.netinfo_neighbour_cell_do, R.string.netinfo_adjacent_cell_4g, R.string.netinfo_server_cell_1x, R.string.netinfo_neighbour_cell_1x};
    private String[] PreferenceIndexDO1X = {"PrefenceIndex0", "PrefenceIndex1", "PrefenceIndex2", "PrefenceIndex3", "PrefenceIndex4"};
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private boolean isSupportSVLTE = TelephonyManagerSprd.RadioCapbility.TDD_SVLTE.equals(TelephonyManagerSprd.getRadioCapbility());
    private boolean isSupportLTE = this.teleApi.telephonyInfo().isSupportLte();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
            this.PrefenceName = new int[]{R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_outfield_information};
        } else {
            this.PrefenceName = new int[]{R.string.netinfo_server_cell, R.string.netinfo_neighbour_cell, R.string.netinfo_adjacent_cell_3g, R.string.netinfo_adjacent_cell_4g, R.string.netinfo_outfield_information};
        }
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        NetTypeDisplay();
        this.mServiceState = new ServiceState();
        this.mTelephonyManager = TelephonyManagerProxy.getService();
        Intent intent = getIntent();
        this.mSimIndex = intent.getIntExtra("simindex", -1);
        this.mPhoneStateListener = getPhoneStateListener(this.mSimIndex);
        listenForPhoneState(DEBUG);
        this.mDataNetType = 0;
    }

    @Override // android.app.Activity
    protected void onResume() throws IllegalAccessException, InvocationTargetException {
        super.onResume();
        if (this.mSimIndex != -1) {
            setTitle("SIM" + this.mSimIndex);
            updateDataNetType(this.mSimIndex);
        } else {
            finish();
        }
        Log.d(TAG, "onResume SimIndex=" + this.mSimIndex);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        listenForPhoneState(false);
    }

    void listenForPhoneState(boolean start) {
        if (start) {
            if (!this.mListening && this.mPhoneStateListener != null) {
                Log.d(TAG, "start listen");
                this.mTelephonyManager.listen(this.mPhoneStateListener, 193);
                this.mListening = DEBUG;
                return;
            }
            return;
        }
        if (this.mPhoneStateListener != null) {
            Log.d(TAG, "stop listen");
            this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
            this.mListening = false;
        }
    }

    private void NetTypeDisplay() {
        this.mPreferenceCategory = new PreferenceCategory(this);
        this.mPreGroup.addPreference(this.mPreferenceCategory);
        this.mSIMPref = new ArrayList<>();
        for (int i = 0; i < this.PrefenceName.length; i++) {
            Preference pref = new Preference(this);
            pref.setTitle(this.PrefenceName[i]);
            pref.setKey(PREF_INDEX + i);
            this.mSIMPref.add(pref);
            this.mPreGroup.addPreference(pref);
        }
    }

    public int slotIdToSubId(int phoneId) {
        SubscriptionInfo mSubscriptionInfo = SubscriptionManagerProxy.getActiveSubscriptionInfoForSimSlotIndex(phoneId);
        if (mSubscriptionInfo == null) {
            Log.d(TAG, "mSubscriptionInfo is null");
            int subId = SubscriptionManager.getDefaultSubscriptionId();
            return subId;
        }
        int subId2 = mSubscriptionInfo.getSubscriptionId();
        return subId2;
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        MyPhoneStateListener(int subId) {
            setSubId(subId);
        }

        private void setSubId(int subId) {
            try {
                getClass().getSuperclass().getField("mSubId").set(this, Integer.valueOf(subId));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
            }
        }
    }

    private PhoneStateListener getPhoneStateListener(final int phoneId) {
        int subId = slotIdToSubId(phoneId);
        return new MyPhoneStateListener(subId) { // from class: com.sprd.engineermode.telephony.NetInfoSimNetworkTypeActivity.1
            @Override // android.telephony.PhoneStateListener
            public void onServiceStateChanged(ServiceState state) throws IllegalAccessException, InvocationTargetException {
                Log.d(NetInfoSimNetworkTypeActivity.TAG, "onServiceStateChanged on phoneId" + phoneId + "state=" + state.getState());
                NetInfoSimNetworkTypeActivity.this.mServiceState = state;
                NetInfoSimNetworkTypeActivity netInfoSimNetworkTypeActivity = NetInfoSimNetworkTypeActivity.this;
                netInfoSimNetworkTypeActivity.mDataNetType = ServiceStateProxy.getDataNetworkType(netInfoSimNetworkTypeActivity.mServiceState);
                NetInfoSimNetworkTypeActivity.this.updateDataNetType(phoneId);
            }

            @Override // android.telephony.PhoneStateListener
            public void onDataConnectionStateChanged(int state, int networkType) throws IllegalAccessException, InvocationTargetException {
                Log.d(NetInfoSimNetworkTypeActivity.TAG, "onDataConnectionStateChanged: state=" + state + " type=" + networkType + " phoneId=" + phoneId);
                if (NetInfoSimNetworkTypeActivity.this.isSupportSVLTE || networkType != 0) {
                    NetInfoSimNetworkTypeActivity.this.mDataNetType = networkType;
                } else {
                    NetInfoSimNetworkTypeActivity netInfoSimNetworkTypeActivity = NetInfoSimNetworkTypeActivity.this;
                    netInfoSimNetworkTypeActivity.mDataNetType = ServiceStateProxy.getDataNetworkType(netInfoSimNetworkTypeActivity.mServiceState);
                }
                NetInfoSimNetworkTypeActivity.this.updateDataNetType(phoneId);
            }
        };
    }

    private void reNetTypeDisplay(String type) {
        this.mSIMPref.clear();
        this.mPreGroup.removeAll();
        this.mPreGroup.addPreference(this.mPreferenceCategory);
        if ("GSM".equals(type)) {
            if (this.isSupportLTE) {
                Log.d(TAG, "NetType SupportLTE, current NetType is GSM!");
                for (int i = 0; i < this.PreferenceNameGSMSupportLte.length; i++) {
                    Preference preference = new Preference(this);
                    preference.setTitle(this.PreferenceNameGSMSupportLte[i]);
                    preference.setKey(this.PreferenceIndexGSMSupportLte[i]);
                    preference.setEnabled(DEBUG);
                    this.mSIMPref.add(preference);
                    this.mPreGroup.addPreference(preference);
                }
                return;
            }
            Log.d(TAG, "NetType not SupportLTE, current NetType is GSM!");
            for (int i2 = 0; i2 < this.PreferenceNameGSM.length; i2++) {
                Preference preference2 = new Preference(this);
                preference2.setTitle(this.PreferenceNameGSM[i2]);
                preference2.setKey(this.PreferenceIndexGSM[i2]);
                preference2.setEnabled(DEBUG);
                this.mSIMPref.add(preference2);
                this.mPreGroup.addPreference(preference2);
            }
            return;
        }
        if ("CDMA 1X".equals(type)) {
            Log.d(TAG, "NetType SupportLTE, current NetType is CDMA 1X!");
            for (int i3 = 0; i3 < this.PreferenceName1xSupportLte.length; i3++) {
                Preference preference3 = new Preference(this);
                preference3.setTitle(this.PreferenceName1xSupportLte[i3]);
                preference3.setKey(this.PreferenceIndex1xSupportLte[i3]);
                preference3.setEnabled(DEBUG);
                this.mSIMPref.add(preference3);
                this.mPreGroup.addPreference(preference3);
            }
            return;
        }
        if ("CDMA DO".equals(type)) {
            Log.d(TAG, "NetType SupportLTE, current NetType is CDMA DO!");
            for (int i4 = 0; i4 < this.PreferenceNameDoSupportLte.length; i4++) {
                Preference preference4 = new Preference(this);
                preference4.setTitle(this.PreferenceNameDoSupportLte[i4]);
                preference4.setKey(this.PreferenceIndexDoSupportLte[i4]);
                preference4.setEnabled(DEBUG);
                this.mSIMPref.add(preference4);
                this.mPreGroup.addPreference(preference4);
            }
            return;
        }
        if ("TD-SCDMA".equals(type) || "WCDMA".equals(type)) {
            if (this.isSupportLTE) {
                Log.d(TAG, "NetType SupportLTE, current NetType is TD-SCDMA/WCDMA!");
                for (int i5 = 0; i5 < this.PreferenceNameCDMASupportLte.length; i5++) {
                    Preference preference5 = new Preference(this);
                    preference5.setTitle(this.PreferenceNameCDMASupportLte[i5]);
                    preference5.setKey(this.PreferenceIndexCDMASupportLte[i5]);
                    preference5.setEnabled(DEBUG);
                    this.mSIMPref.add(preference5);
                    this.mPreGroup.addPreference(preference5);
                }
                return;
            }
            Log.d(TAG, "NetType not SupportLTE, current NetType is TD-SCDMA/WCDMA!");
            for (int i6 = 0; i6 < this.PreferenceNameCDMA.length; i6++) {
                Preference preference6 = new Preference(this);
                preference6.setTitle(this.PreferenceNameCDMA[i6]);
                preference6.setKey(this.PreferenceIndexCDMA[i6]);
                preference6.setEnabled(DEBUG);
                this.mSIMPref.add(preference6);
                this.mPreGroup.addPreference(preference6);
            }
            return;
        }
        if ("LTE".equals(type)) {
            Log.d(TAG, "current NetType is LTE!");
            for (int i7 = 0; i7 < this.PreferenceNameLTE.length; i7++) {
                Preference preference7 = new Preference(this);
                preference7.setTitle(this.PreferenceNameLTE[i7]);
                preference7.setKey(this.PreferenceIndexLTE[i7]);
                preference7.setEnabled(DEBUG);
                this.mSIMPref.add(preference7);
                this.mPreGroup.addPreference(preference7);
            }
            return;
        }
        if ("LTE_1X".equals(type)) {
            Log.d(TAG, "current NetType is LTE_1X!");
            for (int i8 = 0; i8 < this.PreferenceNameLTE1X.length; i8++) {
                Preference preference8 = new Preference(this);
                preference8.setTitle(this.PreferenceNameLTE1X[i8]);
                preference8.setKey(this.PreferenceIndexLTE1X[i8]);
                preference8.setEnabled(DEBUG);
                this.mSIMPref.add(preference8);
                this.mPreGroup.addPreference(preference8);
            }
            return;
        }
        if ("DO_1X".equals(type)) {
            Log.d(TAG, "current NetType is LTE_DO!");
            for (int i9 = 0; i9 < this.PreferenceNameDO1X.length; i9++) {
                Preference preference9 = new Preference(this);
                preference9.setTitle(this.PreferenceNameDO1X[i9]);
                preference9.setKey(this.PreferenceIndexDO1X[i9]);
                preference9.setEnabled(DEBUG);
                this.mSIMPref.add(preference9);
                this.mPreGroup.addPreference(preference9);
            }
            return;
        }
        if ("UNKNOW".equals(type)) {
            Log.d(TAG, "current NetType is UNKNOW!");
            if (TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WG || TelephonyManagerSprd.getRadioCapbility() == TelephonyManagerSprd.RadioCapbility.WW) {
                Log.d(TAG, "getRadioCapbility result is WG/WW!");
                for (int i10 = 0; i10 < this.PreferenceNameCDMA.length; i10++) {
                    Preference preference10 = new Preference(this);
                    preference10.setTitle(this.PreferenceNameCDMA[i10]);
                    preference10.setKey(this.PreferenceIndexCDMA[i10]);
                    preference10.setEnabled(false);
                    this.mSIMPref.add(preference10);
                    this.mPreGroup.addPreference(preference10);
                }
                return;
            }
            Log.d(TAG, "getRadioCapbility result is not WG/WW!");
            for (int i11 = 0; i11 < this.PreferenceNameLTE.length; i11++) {
                Preference preference11 = new Preference(this);
                preference11.setTitle(this.PreferenceNameLTE[i11]);
                preference11.setKey(this.PreferenceIndexLTE[i11]);
                preference11.setEnabled(false);
                this.mSIMPref.add(preference11);
                this.mPreGroup.addPreference(preference11);
            }
            return;
        }
        Log.d(TAG, "NetType is incorrect!");
    }

    private void showNwCap(int netType, int phoneId) {
        if (netType == TelephonyManagerProxy.NETWORK_TYPE_LTE_CA) {
            netType = 13;
        }
        if (netType == 200) {
            this.mPreferenceCategory.setTitle("DO and 1X");
            this.mNetWorkType = 200;
            Log.d(TAG, "Networktype is DO and 1X");
            reNetTypeDisplay("DO_1X");
        } else if (netType != NETWORK_CDMA1X_LTE) {
            switch (netType) {
                case 1:
                case 2:
                case 4:
                case 11:
                case 16:
                    this.mPreferenceCategory.setTitle("GSM");
                    this.mNetWorkType = 1;
                    Log.d(TAG, "Networktype is GSM");
                    reNetTypeDisplay("GSM");
                    break;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    this.mNetWorkType = check3GNetWork(this.mSimIndex);
                    int i = this.mNetWorkType;
                    if (i == 2) {
                        this.mPreferenceCategory.setTitle("TD-SCDMA");
                        Log.d(TAG, "Networktype is TDSCDMA");
                        reNetTypeDisplay("TD-SCDMA");
                    } else if (i == 3) {
                        this.mPreferenceCategory.setTitle("WCDMA");
                        Log.d(TAG, "Networktype is WCDMA");
                        reNetTypeDisplay("WCDMA");
                    } else if (i == 5) {
                        this.mPreferenceCategory.setTitle("CDMA DO");
                        Log.d(TAG, "Networktype is CDMA DO");
                        reNetTypeDisplay("CDMA DO");
                    }
                    break;
                case 7:
                    this.mPreferenceCategory.setTitle("CDMA 1X");
                    this.mNetWorkType = 6;
                    Log.d(TAG, "Networktype is CDMA 1X");
                    reNetTypeDisplay("CDMA 1X");
                    break;
                case 13:
                    this.mPreferenceCategory.setTitle("LTE");
                    this.mNetWorkType = 4;
                    Log.d(TAG, "Networktype is LTE");
                    reNetTypeDisplay("LTE");
                    break;
                default:
                    this.mPreferenceCategory.setTitle(R.string.unknow_server);
                    this.mNetWorkType = 0;
                    Log.d(TAG, "modem type is UnKnow");
                    reNetTypeDisplay("UNKNOW");
                    break;
            }
        } else {
            this.mPreferenceCategory.setTitle("LTE and 1X");
            this.mNetWorkType = NETWORK_CDMA1X_LTE;
            Log.d(TAG, "Networktype is LTE and 1X");
            reNetTypeDisplay("LTE_1X");
        }
        sendNetWorkStatBroadcast(this.mNetWorkType, phoneId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDataNetType(int phoneId) throws IllegalAccessException, InvocationTargetException {
        int[] subId = SubscriptionManagerProxy.getSubId(phoneId);
        if (subId != null) {
            Log.d(TAG, "updateDataNetType phoneId = " + phoneId + ", subId = " + subId[0]);
            int netType = TelephonyManagerProxy.getNetworkType(subId[0]);
            if (TelephonyManagerProxy.getCurrentPhoneTypeForSlot(phoneId) == 2) {
                int csNetType = TelephonyManagerProxy.getVoiceNetworkType(subId[0]);
                Log.d(TAG, "updateDataNetType csNetType: " + csNetType + " psNetType: " + netType);
                if (netType == 0) {
                    netType = csNetType;
                } else if (csNetType == 7 && (netType == 13 || netType == TelephonyManagerProxy.NETWORK_TYPE_LTE_CA)) {
                    netType = NETWORK_CDMA1X_LTE;
                } else {
                    netType = 200;
                }
            } else {
                if (SubscriptionManagerProxy.isValidSubscriptionId(subId[0]) && netType == 0) {
                    netType = TelephonyManagerProxy.getVoiceNetworkType(subId[0]);
                }
                Log.d(TAG, "netType = " + netType + ", typeName = " + TelephonyManagerProxy.getNetworkTypeName(netType));
                if (netType == 0) {
                    netType = this.mDataNetType;
                }
            }
            showNwCap(netType, phoneId);
        }
    }

    private int check3GNetWork(int mSimIndex) {
        String mServerName = "atchannel" + mSimIndex;
        String mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_COPS, mServerName);
        Log.d(TAG, "check3GNetWork mStrTmp: " + mStrTmp);
        if (mStrTmp.contains("OK")) {
            Log.d(TAG, mStrTmp);
            String[] strs = mStrTmp.split("\n");
            Log.d(TAG, strs[0]);
            return get3GNetWork(strs[0]);
        }
        return 3;
    }

    public int get3GNetWork(String atResult) {
        Resources mResources = getResources();
        String[] tdsCdmaPlmnList = mResources.getStringArray(R.array.simPlmnTdsCdma);
        StringBuilder strBuilder = new StringBuilder();
        for (String plmn : tdsCdmaPlmnList) {
            strBuilder.append("([\\D]*");
            strBuilder.append(plmn);
            strBuilder.append("[\\D]*)|");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);
        Pattern pattern = Pattern.compile(strBuilder.toString());
        Matcher matcher = pattern.matcher(atResult);
        if (matcher.find()) {
            return 2;
        }
        strBuilder.setLength(0);
        String[] cdmaPlmnList = mResources.getStringArray(R.array.simPlmnCdma);
        for (String plmn2 : cdmaPlmnList) {
            strBuilder.append("([\\D]*");
            strBuilder.append(plmn2);
            strBuilder.append("[\\D]*)|");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);
        Pattern pattern2 = Pattern.compile(strBuilder.toString());
        Matcher matcher2 = pattern2.matcher(atResult);
        if (matcher2.find()) {
            return 5;
        }
        return 3;
    }

    private void sendNetWorkStatBroadcast(int netWorkType, int phoneId) {
        Log.d(TAG, "sendNetWorkStatBroadcast : " + netWorkType);
        Intent intent = new Intent(NETWORK_STAT_CHANGE);
        intent.putExtra(NETWORK_TYPE, netWorkType);
        intent.putExtra("simindex", phoneId);
        sendBroadcast(intent);
    }

    private boolean isShowLteCap() {
        if (this.mPrefenceIndex < 5) {
            return DEBUG;
        }
        return false;
    }

    private boolean isShowDoCap() {
        if (this.mPrefenceIndex < 3) {
            return DEBUG;
        }
        return false;
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        this.mPrefenceIndex = Integer.valueOf(key.substring(13, 14)).intValue();
        int mSubId = slotIdToSubId(this.mSimIndex);
        Log.d(TAG, "PrefIndex: " + this.mPrefenceIndex + "  simindex: " + this.mSimIndex + "  SubId: " + mSubId);
        Bundle data = new Bundle();
        data.putInt(PREF_INDEX, this.mPrefenceIndex);
        data.putInt("simindex", this.mSimIndex);
        data.putInt("SubId", mSubId);
        int i = this.mNetWorkType;
        if (i != 200) {
            if (i != NETWORK_CDMA1X_LTE) {
                switch (i) {
                    case 1:
                        Intent mIntent = new Intent("android.engineermode.action.GSMSHOW");
                        mIntent.putExtras(data);
                        startActivity(mIntent);
                        break;
                    case 2:
                        Intent mIntent2 = new Intent("android.engineermode.action.TDSCDMASHOW");
                        mIntent2.putExtras(data);
                        startActivity(mIntent2);
                        break;
                    case 3:
                        Intent mIntent3 = new Intent("android.engineermode.action.WCDMASHOW");
                        mIntent3.putExtras(data);
                        startActivity(mIntent3);
                        break;
                    case 4:
                        Intent mIntent4 = new Intent("android.engineermode.action.LTESHOW");
                        mIntent4.putExtras(data);
                        startActivity(mIntent4);
                        break;
                    case 5:
                        Intent mIntent5 = new Intent("android.engineermode.action.CDMADOSHOW");
                        mIntent5.putExtras(data);
                        startActivity(mIntent5);
                        break;
                    case 6:
                        Intent mIntent6 = new Intent("android.engineermode.action.CDMA1XSHOW");
                        mIntent6.putExtras(data);
                        startActivity(mIntent6);
                        break;
                }
            } else if (isShowLteCap()) {
                Intent mIntent7 = new Intent("android.engineermode.action.LTESHOW");
                mIntent7.putExtras(data);
                startActivity(mIntent7);
            } else {
                Intent mIntent8 = new Intent("android.engineermode.action.CDMA1XSHOW");
                data.putInt(PREF_INDEX, this.mPrefenceIndex - 5);
                mIntent8.putExtras(data);
                startActivity(mIntent8);
            }
        } else if (isShowDoCap()) {
            Intent mIntent9 = new Intent("android.engineermode.action.CDMADOSHOW");
            mIntent9.putExtras(data);
            startActivity(mIntent9);
        } else {
            Intent mIntent10 = new Intent("android.engineermode.action.CDMA1XSHOW");
            data.putInt(PREF_INDEX, this.mPrefenceIndex - 3);
            mIntent10.putExtras(data);
            startActivity(mIntent10);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
