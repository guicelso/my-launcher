package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.preference.TwoStatePreference;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMLongClickListPreference;
import com.sprd.engineermode.EMLongClickPreference;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.R;
import com.sprd.engineermode.debuglog.BandModeSetActivity;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class TelephonyFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    private static final String AUTO_ANSWER = "persist.radio.call.autoanswer";
    private static final String C2K_CONFIG_CHAP = "147";
    private static final String C2K_CONFIG_CTCC = "143";
    private static final String C2K_CONFIG_DEFAULT = "141";
    private static final String C2K_CONFIG_EXTENDED2 = "149";
    private static final String C2K_CONFIG_ROAMING = "145";
    private static final int CLOSE_AUTO_ANSWER = 3;
    private static final int CLOSE_DATA_SERVICES_PRE = 12;
    private static final int CLOSE_SIM_TRACE = 24;
    private static final String DDR_SWITCH_STATUS = "persist.vendor.sys.ddr.status";
    private static final String DORM_TIME = "Dormancy time";
    private static final String DSDA_CONTROL = "persist.radio.dsda.wl.ignore";
    private static final int FAST_DORMANCY_CLOSE = 0;
    private static final int FAST_DORMANCY_DEFAULT_VALUE = 8;
    private static final int FAST_DORMANCY_OPEN = 1;
    private static final int GET_AUTO_ANSWER = 26;
    private static final int GET_C2K_CONFIG = 41;
    private static final int GET_EVDO_STATUS = 39;
    private static final int GET_MEID_SWITCH = 43;
    private static final int GET_MOS_STATUS = 35;
    private static final int GET_NXP_STATUS = 34;
    private static final int GET_SIM_TRACE_STATUS = 22;
    private static final int GET_VIDEOTYPE = 10;
    private static final int GET_WPREFER_PLUS_STATUS = 16;
    private static final String INTENT_CONTENT = "intent_content";
    private static final String KEY_AUTO_ANSWER = "auto_answer";
    private static final String KEY_AUTO_AUTH = "auto_auth";
    private static final String KEY_C2K_CONFIG = "c2k_configuration";
    public static final String KEY_CHANNEL = "key_channel";
    private static final String KEY_CLEAR_PRIOR_INFORMATION = "clear_prior_information";
    public static final String KEY_CSFB_GSM = "csfb2gsm_delay";
    private static final String KEY_DATA_SERVICE_PREFERRED = "dataservicepreferred";
    public static final String KEY_DATA_SWITCH = "data_switch";
    private static final String KEY_DISABLE_EVDO = "disable_evdo";
    private static final String KEY_DM_SWITCH = "dm_switch";
    private static final String KEY_DNS_FILTER = "dns_filter";
    private static final String KEY_DSDS_MODE = "dsds_mode";
    private static final String KEY_DUALVOLTE_ENHANCED_SWITCH = "volte_dsda_enhanced";
    public static final String KEY_DUALVOLTE_SWITCH = "dualVoLTE_switch";
    private static final String KEY_FAST_DORMANCY = "fastdormancy";
    private static final String KEY_FAST_DORMANCY_VALUE = "fastdormancy_value";
    private static final String KEY_GPRS_SET = "gprs_set";
    public static final String KEY_IMS_SWITCH = "ims_switch";
    private static final String KEY_Image_SWITCH = "imageswitch";
    private static final String KEY_LOAD_APN = "load_apn";
    private static final String KEY_LTE_IMPEDE_DATA = "lte_impede_data";
    private static final String KEY_LTE_MODEM_SET = "lte_modem_set";
    private static final String KEY_LTE_MODE_SET = "lte_set";
    private static final String KEY_MEID_SWITCH = "meid_switch";
    private static final String KEY_MODE_SWITCH = "mode_switch";
    private static final String KEY_MOS_SWITCH = "mos_switch";
    private static final String KEY_NETINFO_STATI = "netinfo_statistics";
    private static final String KEY_NETMODE_SELECT = "network_mode";
    private static final String KEY_NET_INFO_RECORD = "net_info_record";
    private static final String KEY_OPERATOR_SETTING_0 = "operater_setting_0";
    private static final String KEY_OPERATOR_SETTING_1 = "operater_setting_1";
    public static final String KEY_PHONEID = "key_phoneid";
    private static final String KEY_QOSSWITCH = "qos_switch";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String KEY_SIM_TRACE = "sim_trace";
    private static final String KEY_SINGLE_DUAL_SIM_MODE_SWITCH = "single_dual_sim_mode_switch";
    private static final String KEY_SPRD_ILOG = "sprd_ilog";
    private static final String KEY_SUPPLSERVICEQUERY = "supplementary_service_query";
    private static final String KEY_UE_CAT_SUPPORT = "cat3_4";
    private static final String KEY_UPLMNCON = "uplmn_control";
    private static final String KEY_USB_ACTIVE = "switch_for_usb_active";
    private static final String KEY_VIDEOTYPE = "video_type";
    private static final String KEY_VOLTE_SETTING = "volte_setting";
    private static final String KEY_VOLTE_SETTING_APC = "volte_setting_apc";
    public static final String KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH = "vowifi_lab_sim_allowed_switch";
    public static final String KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH = "vowifi_white_list_allowed_switch";
    private static final String KEY_VT_OPTION_PREFERRED = "VT_option";
    private static final String KEY_WHITELIST_INGORE_SWITCH = "whitelist_ignore_switch";
    private static final int LTE_BAND_QUERY = 19;
    private static final int LTE_GET_DATA_IMPEDE = 20;
    private static final int LTE_SET_DATA_IMPEDE = 21;
    private static final String NET_MODE = "net_mode";
    private static final int NXP_COUNT = 27;
    private static final int OPEN_AUTO_ANSWER = 2;
    private static final int OPEN_DATA_SERVICES_PRE = 11;
    private static final int OPEN_SIM_TRACE = 23;
    private static final int SET_C2K_CONFIG = 42;
    private static final int SET_CFU = 1;
    private static final int SET_DSDS_MODE = 38;
    private static final int SET_EVDO_STATUS = 40;
    private static final int SET_FAST_DORMANCY = 8;
    private static final int SET_MEID_SWITCH = 44;
    private static final int SET_MODE_SWITCH = 25;
    private static final int SET_MOS_STATUS = 36;
    private static final int SET_NXP_STATUS = 37;
    private static final int SET_SINGLE_DUAL_MODE_SWITCH = 27;
    private static final int SET_VIDEOTYPE = 9;
    private static final int SET_WPREFER_PLUS_STATUS = 17;
    private static final String SWITCH_FOR_DM = "dm_switch";
    private static final String SWITCH_FOR_USB_ACTIVE = "switch_for_usb_active";
    private static final String TAG = "TelephonyFragment";
    private static final String W_PREFER_PLUS = "w_prefer_plus";
    private SharedPreferences.Editor editor;
    private String mATCmd;
    private String mATResponse;
    private SwitchPreference mAutoAnswer;
    private TwoStatePreference mAutoAuth;
    private Preference mBandSelect;
    private Preference[] mBandSelectSim;
    private ListPreference mC2kConfig;
    private Context mContext;
    private Preference mCsfb2gsm;
    private TelephonyManagerSprd.RadioCapbility mCurrentRadioCapbility;
    private TwoStatePreference mDNSFilter;
    private EMSwitchPreference mDataServicePreferred;
    private EMSwitchPreference mDataSwitch;
    private EMSwitchPreference mDdrSwitch;
    private EMSwitchPreference mDisableEvdo;
    private EMSwitchPreference mDmSwitch;
    private ListPreference mDsdsMode;
    private EMSwitchPreference mDualVolteEnhancedSwitch;
    private EMSwitchPreference mDualVolteSwitch;
    private EMSwitchPreference mFastdormancy;
    private EMLongClickPreference mGPRSSet;
    private Preference mImageSwitchPreference;
    private EMSwitchPreference mImsSwitch;
    private boolean[] mIsCardExist;
    private EMSwitchPreference mLTEDateImpede;
    private EMSwitchPreference mLoadAPNSwitch;
    private EMSwitchPreference mMeidSwitch;
    private int mMesarg;
    private ListPreference mModeSwitch;
    private int[] mModemType;
    private EMSwitchPreference mMosSwitch;
    private Preference mNVItemList;
    private Preference mNetModeSelect;
    private EMLongClickPreference mNetinfoStatistics;
    private PreferenceScreen mNxp;
    private CheckBoxPreference[] mNxpList;
    private Preference mOperatorSetting0;
    private Preference mOperatorSetting1;
    private int mPhoneCount;
    private ProgressDialog mProgressDialog;
    private SwitchPreference mQosSwitch;
    private int mSIM;
    private SharedPreferences mSharePref;
    private TwoStatePreference mSimTrace;
    private ListPreference mSingleDualSIMModeSwitch;
    private SwitchPreference mSprdIlogSwitch;
    private String mStrTmp;
    private EMLongClickListPreference mSupplementaryServiceQuery;
    private boolean mSupportDM;
    private TELHandler mTELHandler;
    private TelephonyManagerSprd mTelephonyManagerSprd;
    private Preference mUEQuery;
    private TwoStatePreference mUplmnCon;
    private TwoStatePreference mUsbActive;
    private EMSwitchPreference mVTOptionPreference;
    private ListPreference mVideoType;
    private Preference mVolteSetting;
    private Preference mVolteSettingApc;
    private EMSwitchPreference mVolteSwitch;
    private EMSwitchPreference mVowifiLabSimAllowedSwitch;
    private EMSwitchPreference mVowifiWhiteListAllowedSwitch;
    private EMSwitchPreference mWhiteListIgnoreSwitch;
    private EMSwitchPreference mWpreferplus;
    private int nxp_index;
    private SharedPreferences preferences;
    private String[] status;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private Handler mUiThread = new Handler();
    private boolean isSupportLTE = this.teleApi.telephonyInfo().isSupportLte();
    private boolean isSupportCSFB = this.teleApi.telephonyInfo().isSupportCsfb();
    private boolean isSupportC2K = this.teleApi.telephonyInfo().isSupportC2k();
    private TelephonyManagerSprd.RadioCapbility nowModeName = TelephonyManagerSprd.getRadioCapbility();
    private String nowDsdsModeName = SystemPropertiesProxy.get("persist.vendor.radio.modem.config", "");
    private String originalDsdsMode = SystemPropertiesProxy.get("persist.vendor.radio.modem.capability", "");
    private String nowSingelDualSIMModeName = "1";
    private boolean isLplusG = this.teleApi.telephonyInfo().isSupportLplusG();
    private boolean isLplusW = this.teleApi.telephonyInfo().isSupportLplusW();
    private boolean disableNetmode = this.teleApi.telephonyInfo().isSupportNetwork();
    private boolean autoAnswerOpen = SystemPropertiesProxy.get(AUTO_ANSWER).equals("1");
    private String mFirstDormancySet = null;
    private int mPhoneId = 0;
    private int mCardCount = 0;
    private boolean mVolteChecked = false;
    private boolean mVowifiLabSimAllowedChecked = false;
    private boolean mVowifiWhiteListAllowedChecked = false;
    private boolean mSmsChecked = false;
    private boolean getNxpStatus = false;
    private boolean mDualVolteChecked = false;
    private boolean mDualVolteEnhancedChecked = false;
    private boolean mSupportVT = SystemPropertiesProxy.getBoolean("persist.vendor.sys.volte.enable", false);
    private BroadcastReceiver mMobileReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TelephonyFragment.TAG, "action = " + action);
            if (action.startsWith("android.intent.action.AIRPLANE_MODE")) {
                if (!TelephonyFragment.this.disableNetmode) {
                    TelephonyFragment.this.updateModeType();
                }
            } else if (action.startsWith(TelephonyFragment.ACTION_SIM_STATE_CHANGED)) {
                TelephonyFragment.this.getCardExitState();
                TelephonyFragment.this.setCardExitState();
            }
        }
    };
    private Preference.OnPreferenceClickListener clickListenser = new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.17
        @Override // android.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            Log.d(TelephonyFragment.TAG, "clickListenser");
            for (int i = 0; i < 27; i++) {
                if (preference == TelephonyFragment.this.mNxpList[i]) {
                    Message msg = TelephonyFragment.this.mTELHandler.obtainMessage(37, i + 1, 0);
                    TelephonyFragment.this.mTELHandler.sendMessage(msg);
                    break;
                }
            }
            return false;
        }
    };

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mTELHandler = new TELHandler(ht.getLooper());
        addPreferencesFromResource(R.xml.pref_telephonytab);
        PreferenceScreen prefSet = getPreferenceScreen();
        this.mContext = getActivity();
        this.preferences = this.mContext.getSharedPreferences("fd", 0);
        this.editor = this.preferences.edit();
        this.mAutoAnswer = (SwitchPreference) prefSet.findPreference(KEY_AUTO_ANSWER);
        this.mAutoAnswer.setOnPreferenceChangeListener(this);
        this.mAutoAnswer.setChecked(this.autoAnswerOpen);
        this.mQosSwitch = (SwitchPreference) prefSet.findPreference(KEY_QOSSWITCH);
        this.mQosSwitch.setOnPreferenceChangeListener(this);
        this.mBandSelect = (PreferenceScreen) findPreference("band_select");
        this.mGPRSSet = (EMLongClickPreference) findPreference(KEY_GPRS_SET);
        this.mGPRSSet.setEnabled(true);
        this.mSimTrace = (TwoStatePreference) findPreference(KEY_SIM_TRACE);
        this.mSimTrace.setOnPreferenceChangeListener(this);
        this.mUsbActive = (TwoStatePreference) findPreference("switch_for_usb_active");
        this.mUsbActive.setOnPreferenceChangeListener(this);
        this.mSprdIlogSwitch = (SwitchPreference) findPreference(KEY_SPRD_ILOG);
        this.mSprdIlogSwitch.setEnabled(false);
        this.mSprdIlogSwitch.setOnPreferenceChangeListener(this);
        this.mFastdormancy = (EMSwitchPreference) findPreference(KEY_FAST_DORMANCY);
        this.mFastdormancy.setOnPreferenceChangeListener(this);
        this.mSupplementaryServiceQuery = (EMLongClickListPreference) findPreference(KEY_SUPPLSERVICEQUERY);
        this.mNetModeSelect = findPreference(KEY_NETMODE_SELECT);
        this.mImageSwitchPreference = findPreference(KEY_Image_SWITCH);
        this.mDataServicePreferred = (EMSwitchPreference) findPreference(KEY_DATA_SERVICE_PREFERRED);
        this.mDataServicePreferred.setOnPreferenceChangeListener(this);
        this.mVTOptionPreference = (EMSwitchPreference) findPreference(KEY_VT_OPTION_PREFERRED);
        this.mVTOptionPreference.setOnPreferenceChangeListener(this);
        this.mLTEDateImpede = (EMSwitchPreference) findPreference(KEY_LTE_IMPEDE_DATA);
        this.mLTEDateImpede.setOnPreferenceChangeListener(this);
        this.mNetinfoStatistics = (EMLongClickPreference) findPreference(KEY_NETINFO_STATI);
        this.mDNSFilter = (TwoStatePreference) findPreference(KEY_DNS_FILTER);
        this.mDNSFilter.setOnPreferenceChangeListener(this);
        this.mModeSwitch = (ListPreference) findPreference(KEY_MODE_SWITCH);
        getPreferenceScreen().removePreference(this.mModeSwitch);
        this.mDsdsMode = (ListPreference) findPreference(KEY_DSDS_MODE);
        if (this.originalDsdsMode.equals("TL_LF_TD_W_G,TL_LF_TD_W_G") || this.originalDsdsMode.equals("TL_LF_W_G,TL_LF_W_G")) {
            this.mDsdsMode.setEntries(R.array.dsds_mode_ll);
            this.mDsdsMode.setEntryValues(R.array.dsds_mode_value_ll);
        }
        if (!this.originalDsdsMode.equals("TL_LF_TD_W_G,TL_LF_TD_W_G") && !this.originalDsdsMode.equals("TL_LF_W_G,TL_LF_W_G")) {
            this.mDsdsMode.setEnabled(false);
            this.mDsdsMode.setSummary(R.string.feature_not_support);
        } else {
            this.mDsdsMode.setEnabled(true);
        }
        this.mSingleDualSIMModeSwitch = (ListPreference) findPreference(KEY_SINGLE_DUAL_SIM_MODE_SWITCH);
        this.mNVItemList = findPreference("nv_item_list");
        this.mNVItemList.setEnabled(true);
        this.mVolteSetting = findPreference(KEY_VOLTE_SETTING);
        this.mVolteSettingApc = findPreference(KEY_VOLTE_SETTING_APC);
        if (!SystemPropertiesProxy.get("persist.vendor.sys.volte.enable").equals("true") && this.isSupportLTE) {
            this.mVolteSetting.setEnabled(false);
            this.mVolteSettingApc.setEnabled(false);
            this.mVolteSetting.setSummary(R.string.feature_not_support);
            this.mVolteSettingApc.setSummary(R.string.feature_not_support);
        } else {
            this.mVolteSetting.setEnabled(true);
            this.mVolteSettingApc.setEnabled(true);
        }
        updateVolteSettings();
        this.mWhiteListIgnoreSwitch = (EMSwitchPreference) findPreference(KEY_WHITELIST_INGORE_SWITCH);
        this.mWhiteListIgnoreSwitch.setOnPreferenceChangeListener(this);
        this.mVowifiLabSimAllowedSwitch = (EMSwitchPreference) findPreference(KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH);
        this.mVowifiLabSimAllowedSwitch.setOnPreferenceChangeListener(this);
        this.mVowifiWhiteListAllowedSwitch = (EMSwitchPreference) findPreference(KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH);
        this.mVowifiWhiteListAllowedSwitch.setOnPreferenceChangeListener(this);
        this.mDisableEvdo = (EMSwitchPreference) findPreference(KEY_DISABLE_EVDO);
        this.mDisableEvdo.setOnPreferenceChangeListener(this);
        if (!this.isSupportC2K) {
            this.mDisableEvdo.setEnabled(false);
        }
        this.mImsSwitch = (EMSwitchPreference) findPreference(KEY_IMS_SWITCH);
        this.mImsSwitch.setOnPreferenceChangeListener(this);
        if (!this.isSupportLTE) {
            this.mImsSwitch.setEnabled(false);
            this.mImsSwitch.setSummary(R.string.feature_not_support);
        } else {
            this.mImsSwitch.setEnabled(true);
        }
        if (SystemPropertiesProxy.get("persist.sys.support.vt").isEmpty()) {
            this.mVTOptionPreference.setChecked(true);
        } else {
            this.mVTOptionPreference.setChecked(SystemPropertiesProxy.get("persist.sys.support.vt").equals("true"));
        }
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        int i = this.mPhoneCount;
        this.mModemType = new int[i];
        this.mBandSelectSim = new Preference[i];
        this.mIsCardExist = new boolean[i];
        this.mTelephonyManagerSprd = TelephonyManagerSprd.getInstance();
        getCardExitState();
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            this.mModemType[i2] = TelephonyManagerSprd.getModemType();
            this.mBandSelectSim[i2] = new Preference(this.mContext);
            int[] iArr = this.mModemType;
            if (iArr[i2] == 1) {
                this.mNetModeSelect.setSummary(R.string.input_cmcc_card);
                Log.d(TAG, "modem type is TDSCDMA");
            } else if (iArr[i2] == 2) {
                this.mNetModeSelect.setSummary(R.string.input_cucc_card);
                Log.d(TAG, "modem type is WCDMA");
            }
            this.mBandSelectSim[i2].setTitle("SIM" + i2);
            this.mBandSelectSim[i2].setKey("SIM" + i2);
        }
        int i3 = TelephonyManagerSprd.getModemType();
        if (i3 == 0) {
            Log.d(TAG, "Modem Type is GSM, remove net mode select and vt option");
            this.mNetModeSelect.setEnabled(false);
            this.mNetModeSelect.setSummary(R.string.feature_not_support);
            this.mNetinfoStatistics.setEnabled(false);
            this.mNetinfoStatistics.setSummary(R.string.feature_not_support);
        }
        if (this.disableNetmode) {
            this.mNetModeSelect.setEnabled(false);
            this.mNetModeSelect.setSummary(R.string.feature_not_support);
        }
        if (!isAppInstalled(this.mContext, "com.android.regionconfig")) {
            getPreferenceScreen().removePreference(this.mImageSwitchPreference);
        }
        this.mCurrentRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
        if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.TDD_CSFB || this.isSupportCSFB) {
            this.mVTOptionPreference.setEnabled(false);
            prefSet.removePreference(this.mVTOptionPreference);
        }
        registerReceiver();
        this.mGPRSSet.isEnabled();
        this.mWpreferplus = (EMSwitchPreference) findPreference(W_PREFER_PLUS);
        this.mWpreferplus.setOnPreferenceChangeListener(this);
        if (!this.teleApi.telephonyInfo().isSupportWcdma()) {
            this.mWpreferplus.setEnabled(false);
            this.mWpreferplus.setSummary(R.string.feature_not_support);
            this.mWpreferplus = null;
        }
        this.mVideoType = (ListPreference) findPreference(KEY_VIDEOTYPE);
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mSharePref.registerOnSharedPreferenceChangeListener(this);
        if (!this.isSupportLTE) {
            this.mLTEDateImpede.setEnabled(false);
            this.mLTEDateImpede.setSummary(R.string.feature_not_support);
        }
        this.mCsfb2gsm = findPreference(KEY_CSFB_GSM);
        if (!this.isSupportLTE) {
            this.mCsfb2gsm.setEnabled(false);
            this.mCsfb2gsm.setSummary(R.string.feature_not_support);
        }
        this.mLoadAPNSwitch = (EMSwitchPreference) findPreference(KEY_LOAD_APN);
        this.mLoadAPNSwitch.setOnPreferenceChangeListener(this);
        this.mNxp = (PreferenceScreen) findPreference("nxp_list");
        this.mNxpList = new CheckBoxPreference[27];
        for (int i4 = 0; i4 < 27; i4++) {
            this.mNxpList[i4] = new CheckBoxPreference(this.mContext);
            this.mNxpList[i4].setTitle(getResources().getStringArray(R.array.nxp_list)[i4]);
            this.mNxp.addPreference(this.mNxpList[i4]);
            this.mNxp.setKey("nxp" + i4);
            this.mNxpList[i4].setOnPreferenceClickListener(this.clickListenser);
        }
        this.mNxp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.1
            @Override // android.preference.Preference.OnPreferenceClickListener
            public boolean onPreferenceClick(Preference preference) {
                if (TelephonyFragment.this.mNxp != null) {
                    TelephonyFragment.this.getNxpStatus = true;
                    Message simTraceSta = TelephonyFragment.this.mTELHandler.obtainMessage(34);
                    TelephonyFragment.this.mTELHandler.sendMessage(simTraceSta);
                }
                return true;
            }
        });
        this.mMosSwitch = (EMSwitchPreference) findPreference(KEY_MOS_SWITCH);
        this.mMosSwitch.setOnPreferenceChangeListener(this);
        if (!this.isSupportLTE) {
            this.mMosSwitch.setEnabled(false);
            this.mMosSwitch.setSummary(R.string.feature_not_support);
            this.mNxp.setSummary(this.mContext.getString(R.string.feature_not_support));
            this.mNxp.setEnabled(false);
        } else {
            this.mMosSwitch.setEnabled(true);
        }
        this.mMeidSwitch = (EMSwitchPreference) findPreference(KEY_MEID_SWITCH);
        this.mMeidSwitch.setOnPreferenceChangeListener(this);
        this.mDmSwitch = (EMSwitchPreference) findPreference("dm_switch");
        this.mDmSwitch.setOnPreferenceChangeListener(this);
        this.mSupportDM = getResources().getBoolean(R.bool.is_support_dm_switch);
        Log.d(TAG, "mSupportDM = " + this.mSupportDM);
        if (!this.mSupportDM) {
            this.mDmSwitch.setEnabled(false);
            this.mDmSwitch.setSummary(this.mContext.getString(R.string.feature_not_support));
        }
        this.mUEQuery = findPreference(KEY_UE_CAT_SUPPORT);
        if (this.originalDsdsMode.equals("W_G,G") || this.originalDsdsMode.equals("W_G,W_G")) {
            this.mUEQuery.setEnabled(false);
            this.mUEQuery.setSummary(this.mContext.getString(R.string.feature_not_support));
        }
        try {
            this.mOperatorSetting0 = findPreference(KEY_OPERATOR_SETTING_0);
            this.mOperatorSetting0.setOnPreferenceChangeListener(this);
            this.mOperatorSetting1 = findPreference(KEY_OPERATOR_SETTING_1);
            this.mOperatorSetting1.setOnPreferenceChangeListener(this);
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException" + e);
        }
        this.mUplmnCon = (TwoStatePreference) findPreference(KEY_UPLMNCON);
        this.mUplmnCon.setOnPreferenceChangeListener(this);
        this.mC2kConfig = (ListPreference) findPreference(KEY_C2K_CONFIG);
        this.mC2kConfig.setOnPreferenceChangeListener(this);
        if (!this.isSupportC2K) {
            this.mC2kConfig.setEnabled(false);
        }
        getPreferenceScreen().removePreference(findPreference(KEY_LTE_IMPEDE_DATA));
        getPreferenceScreen().removePreference(findPreference(KEY_SPRD_ILOG));
    }

    private void updateVolteSettings() {
        new Thread(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.2
            @Override // java.lang.Runnable
            public void run() {
                final boolean mApcFuncSupport = TelephonyFragment.this.teleApi.telephonyInfo().isSupportAPCFunc();
                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (mApcFuncSupport) {
                            TelephonyFragment.this.getPreferenceScreen().removePreference(TelephonyFragment.this.mVolteSetting);
                        } else {
                            TelephonyFragment.this.getPreferenceScreen().removePreference(TelephonyFragment.this.mVolteSettingApc);
                        }
                    }
                });
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCardExitState() {
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (TelephonyManagerProxy.getSimState(i) == 5) {
                this.mIsCardExist[i] = true;
            } else {
                this.mIsCardExist[i] = false;
            }
            if (this.mIsCardExist[i]) {
                this.mGPRSSet.setEnabled(true);
                this.mPhoneId = i;
                this.mCardCount++;
            }
            Log.d(TAG, "mIsCardExist[" + i + "] = " + this.mIsCardExist[i] + " mCardCount = " + this.mCardCount);
        }
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCardExitState() {
        boolean isAirplane = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
        boolean[] simCardAvailable = new boolean[this.mPhoneCount];
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (this.mIsCardExist[i]) {
                if (isAirplane || !TelephonyManagerSprd.isSimStandby(i, this.mContext)) {
                    this.mBandSelectSim[i].setSummary(this.mContext.getString(R.string.open_card_warn));
                    this.mBandSelectSim[i].setEnabled(false);
                    simCardAvailable[i] = false;
                    if (BandModeSetActivity.BandModeSetActivityInstance != null) {
                        BandModeSetActivity.BandModeSetActivityInstance.finish();
                    }
                } else {
                    this.mBandSelectSim[i].setSummary((CharSequence) null);
                    this.mBandSelectSim[i].setEnabled(true);
                    simCardAvailable[i] = true;
                }
            } else {
                this.mBandSelectSim[i].setSummary(this.mContext.getString(R.string.input_sim__warn));
                this.mBandSelectSim[i].setEnabled(false);
                simCardAvailable[i] = false;
                if (BandModeSetActivity.BandModeSetActivityInstance != null) {
                    BandModeSetActivity.BandModeSetActivityInstance.finish();
                }
            }
        }
        boolean allSimCardAvailable = false;
        for (int i2 = 0; i2 < this.mPhoneCount; i2++) {
            allSimCardAvailable |= simCardAvailable[i2];
        }
        if (!allSimCardAvailable) {
            if (LTEActivity.LTEActivityInstance != null) {
                LTEActivity.LTEActivityInstance.finish();
            }
            this.mNetModeSelect.setEnabled(false);
            return;
        }
        this.mNetModeSelect.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateModeType() {
        boolean isEnable;
        CharSequence summary;
        int primaryCard = this.mTelephonyManagerSprd.getPrimaryCard();
        boolean isStandby = TelephonyManagerSprd.isSimStandby(primaryCard, this.mContext);
        boolean isAirplane = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
        Log.d(TAG, "isSupportLTE = " + this.isSupportLTE + " isStandby = " + isStandby + " isAirplane = " + isAirplane);
        if (this.isSupportLTE) {
            if (TelephonyManagerProxy.getPhoneCount() == 1) {
                if (this.mCardCount != 0) {
                    if (!isStandby || isAirplane) {
                        isEnable = false;
                        summary = this.mContext.getString(R.string.open_card_warn);
                    } else {
                        isEnable = true;
                        summary = null;
                    }
                } else {
                    isEnable = false;
                    summary = this.mContext.getString(R.string.input_sim__warn);
                }
            } else if (this.mCardCount == 0) {
                isEnable = false;
                summary = this.mContext.getString(R.string.input_sim__warn);
            } else if (!isStandby || isAirplane) {
                isEnable = false;
                summary = this.mContext.getString(R.string.open_card_warn);
            } else {
                isEnable = true;
                summary = null;
            }
            if (!this.originalDsdsMode.equals("W_G,G") && !this.originalDsdsMode.equals("W_G,W_G")) {
                this.mNetModeSelect.setEnabled(isEnable);
                this.mNetModeSelect.setSummary(summary);
            }
        }
        setCardExitState();
    }

    public void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.AIRPLANE_MODE");
        filter.addAction(ACTION_SIM_STATE_CHANGED);
        this.mContext.registerReceiver(this.mMobileReceiver, filter);
    }

    public void unregisterReceiver() {
        try {
            this.mContext.unregisterReceiver(this.mMobileReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    private int changeValueToIndex(TelephonyManagerSprd.RadioCapbility PrefKey) {
        if (TelephonyManagerSprd.RadioCapbility.TDD_CSFB.equals(PrefKey)) {
            return 0;
        }
        if (TelephonyManagerSprd.RadioCapbility.FDD_CSFB.equals(PrefKey)) {
            return 1;
        }
        if (!TelephonyManagerSprd.RadioCapbility.CSFB.equals(PrefKey)) {
            return 0;
        }
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int changeSingleDualSIMModeValueToIndex(String PrefKey) {
        if ("1".equals(PrefKey) || !"2".equals(PrefKey)) {
            return 0;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int changeDsdsModeValueToIndex(String dsdsMode) {
        int valueIndex = 0;
        if ("TL_LF_TD_W_G,G".equals(dsdsMode) || "TL_LF_W_G,G".equals(dsdsMode)) {
            valueIndex = 0;
        } else if ("TL_LF_TD_W_G,W_G".equals(dsdsMode) || "TL_LF_W_G,W_G".equals(dsdsMode)) {
            valueIndex = 1;
        } else if ("TL_LF_TD_W_G,TL_LF_TD_W_G".equals(dsdsMode) || "TL_LF_W_G,TL_LF_W_G".equals(dsdsMode)) {
            valueIndex = 2;
        }
        Log.d(TAG, "changeDsdsModeValueToIndex valueIndex: " + valueIndex);
        return valueIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDsdsMode(int value) throws IllegalAccessException, InvocationTargetException {
        if (value == 0) {
            if (this.originalDsdsMode.contains("TL_LF_W_G")) {
                SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_W_G,G");
                SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "6,10");
                return;
            } else if (this.originalDsdsMode.contains("TL_TD_G")) {
                SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_TD_G,G");
                SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "7,10");
                return;
            } else {
                SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_TD_W_G,G");
                SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "9,10");
                return;
            }
        }
        if (value != 1) {
            if (value == 2) {
                if (this.originalDsdsMode.contains("TL_LF_TD_W_G,TL_LF_TD_W_G")) {
                    SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_TD_W_G,TL_LF_TD_W_G");
                    SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "9,9");
                    return;
                } else {
                    if (this.originalDsdsMode.contains("TL_LF_W_G,TL_LF_W_G")) {
                        SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_W_G,TL_LF_W_G");
                        SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "6,6");
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (!this.originalDsdsMode.contains("TL_LF_TD_W_G")) {
            if (this.originalDsdsMode.contains("TL_LF_W_G")) {
                SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_W_G,W_G");
                SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "6,255");
                return;
            } else {
                SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_TD_W_G,W_G");
                SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "9,255");
                return;
            }
        }
        SystemPropertiesProxy.set("persist.vendor.radio.modem.config", "TL_LF_TD_W_G,W_G");
        SystemPropertiesProxy.set("persist.vendor.radio.modem.workmode", "9,255");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSingeDualSIMModeSwitch(int value) throws IllegalAccessException, InvocationTargetException {
        if (value == 0) {
            SystemPropertiesProxy.set("persist.radio.multisim.config", "ssss");
        } else if (value == 1) {
            SystemPropertiesProxy.set("persist.radio.multisim.config", "dsds");
        }
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onStart() {
        this.getNxpStatus = false;
        if (SystemPropertiesProxy.get("persist.vendor.radio.fd.disable", "").equals("0")) {
            this.mFastdormancy.setSummary(this.mContext.getString(R.string.Default_value));
            this.mFastdormancy.setChecked(true);
            this.editor.putString("fd_open", "true");
            this.editor.commit();
        } else if (SystemPropertiesProxy.get("persist.vendor.radio.fd.disable", "").equals("1") && this.preferences.getString("fd_open", "").equals("true")) {
            this.mFastdormancy.setSummary(this.preferences.getString(DORM_TIME, "null") + "s");
            this.mFastdormancy.setChecked(true);
        }
        String cfuValue = this.mSharePref.getString(KEY_SUPPLSERVICEQUERY, this.teleApi.callFunc().getCfu());
        if (cfuValue.equals("0")) {
            this.mSupplementaryServiceQuery.setValue("0");
        } else if (cfuValue.equals("1")) {
            this.mSupplementaryServiceQuery.setValue("1");
        } else if (cfuValue.equals("2")) {
            this.mSupplementaryServiceQuery.setValue("2");
        }
        if (!this.mSupportVT) {
            this.mVideoType.setEnabled(false);
        } else {
            this.mVideoType.setEnabled(true);
            ListPreference listPreference = this.mVideoType;
            listPreference.setSummary(listPreference.getEntry());
        }
        if (this.mWpreferplus != null) {
            this.mTELHandler.sendEmptyMessage(16);
        }
        if (this.mSimTrace != null) {
            Message simTraceSta = this.mTELHandler.obtainMessage(22);
            this.mTELHandler.sendMessage(simTraceSta);
        }
        if (this.mUsbActive != null) {
            int usbActiveSta = Settings.Global.getInt(this.mContext.getContentResolver(), "switch_for_usb_active", 0);
            Log.d(TAG, "Usb active status is: " + usbActiveSta);
            if (usbActiveSta != 1) {
                this.mUsbActive.setChecked(false);
            } else {
                this.mUsbActive.setChecked(true);
            }
        }
        if (this.isSupportLTE) {
            this.mTELHandler.sendEmptyMessage(20);
        }
        ListPreference listPreference2 = this.mModeSwitch;
        if (listPreference2 != null && listPreference2.isEnabled()) {
            this.mModeSwitch.setValueIndex(changeValueToIndex(this.nowModeName));
            ListPreference listPreference3 = this.mModeSwitch;
            listPreference3.setSummary(listPreference3.getEntry());
        }
        ListPreference listPreference4 = this.mDsdsMode;
        if (listPreference4 != null && listPreference4.isEnabled()) {
            this.mDsdsMode.setValueIndex(changeDsdsModeValueToIndex(this.nowDsdsModeName));
            ListPreference listPreference5 = this.mDsdsMode;
            listPreference5.setSummary(listPreference5.getEntry());
        }
        ListPreference listPreference6 = this.mSingleDualSIMModeSwitch;
        if (listPreference6 != null) {
            listPreference6.setValueIndex(changeSingleDualSIMModeValueToIndex(this.nowSingelDualSIMModeName));
            ListPreference listPreference7 = this.mSingleDualSIMModeSwitch;
            listPreference7.setSummary(listPreference7.getEntry());
        }
        if (this.mDNSFilter != null) {
            if (!SystemPropertiesProxy.getBoolean("sys.engineermode.dns", false)) {
                this.mDNSFilter.setChecked(false);
            } else {
                this.mDNSFilter.setChecked(true);
            }
        }
        if (this.mNxp != null) {
            Message simTraceSta2 = this.mTELHandler.obtainMessage(34);
            this.mTELHandler.sendMessage(simTraceSta2);
        }
        if (!SystemPropertiesProxy.get(DSDA_CONTROL).equals("true")) {
            this.mWhiteListIgnoreSwitch.setChecked(false);
        } else {
            this.mWhiteListIgnoreSwitch.setChecked(true);
        }
        Log.d(TAG, "onStart");
        if (!this.disableNetmode) {
            updateModeType();
        }
        if (this.isSupportLTE && this.mMosSwitch != null) {
            Message simTraceSta3 = this.mTELHandler.obtainMessage(35);
            this.mTELHandler.sendMessage(simTraceSta3);
        }
        if (this.mMeidSwitch != null) {
            Message simTraceSta4 = this.mTELHandler.obtainMessage(43);
            this.mTELHandler.sendMessage(simTraceSta4);
        }
        if (this.mSupportDM && this.mDmSwitch != null) {
            int dmSwitch = Settings.Global.getInt(this.mContext.getContentResolver(), "dm_switch", 1);
            Log.d(TAG, "ctcc dm status is: " + dmSwitch);
            if (1 != dmSwitch) {
                this.mDmSwitch.setChecked(false);
                this.mDmSwitch.setSummary("Close");
            } else {
                this.mDmSwitch.setChecked(true);
                this.mDmSwitch.setSummary("Open");
            }
        }
        TwoStatePreference twoStatePreference = this.mUplmnCon;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            this.mUplmnCon.setChecked(SystemPropertiesProxy.getBoolean("persist.sys.uplmn", false));
        }
        if (this.mDisableEvdo != null) {
            Message mEvdoStatus = this.mTELHandler.obtainMessage(39);
            this.mTELHandler.sendMessage(mEvdoStatus);
        }
        ListPreference listPreference8 = this.mC2kConfig;
        if (listPreference8 != null && listPreference8.isEnabled()) {
            Message getC2kConfig = this.mTELHandler.obtainMessage(41);
            this.mTELHandler.sendMessage(getC2kConfig);
        }
        super.onStart();
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onDestroy() {
        TELHandler tELHandler = this.mTELHandler;
        if (tELHandler != null) {
            tELHandler.getLooper().quit();
            Log.d(TAG, "HandlerThread has quit");
        }
        unregisterReceiver();
        super.onDestroy();
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_SUPPLSERVICEQUERY)) {
            Message mSupplService = this.mTELHandler.obtainMessage(1, sharedPreferences.getString(key, ""));
            this.mTELHandler.sendMessage(mSupplService);
            return;
        }
        if (key.equals(KEY_VIDEOTYPE)) {
            ListPreference listPreference = this.mVideoType;
            listPreference.setSummary(listPreference.getEntry());
            Message mSupplService2 = this.mTELHandler.obtainMessage(9, sharedPreferences.getString(key, ""));
            this.mTELHandler.sendMessage(mSupplService2);
            return;
        }
        if (key.equals(KEY_DSDS_MODE)) {
            final String re = sharedPreferences.getString(key, "");
            if (Integer.parseInt(re) != changeDsdsModeValueToIndex(this.nowDsdsModeName) && isAdded()) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.dsds_mode)).setMessage(getString(R.string.mode_switch_waring)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.5
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        Message mSetMode = TelephonyFragment.this.mTELHandler.obtainMessage(38, re);
                        TelephonyFragment.this.mTELHandler.sendMessage(mSetMode);
                    }
                }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TelephonyFragment.this.mDsdsMode.setValueIndex(TelephonyFragment.this.changeDsdsModeValueToIndex(TelephonyFragment.this.nowDsdsModeName));
                            }
                        });
                    }
                }).create();
                alertDialog.setCancelable(false);
                alertDialog.show();
                return;
            }
            return;
        }
        if (key.equals(KEY_SINGLE_DUAL_SIM_MODE_SWITCH)) {
            final String re2 = sharedPreferences.getString(key, "");
            if (Integer.parseInt(re2) != changeSingleDualSIMModeValueToIndex(this.nowSingelDualSIMModeName) && isAdded()) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.single_dual_sim_mode_switch)).setMessage(getString(R.string.mode_switch_waring)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.7
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        Message mSetMode = TelephonyFragment.this.mTELHandler.obtainMessage(27, re2);
                        TelephonyFragment.this.mTELHandler.sendMessage(mSetMode);
                    }
                }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.6
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TelephonyFragment.this.mSingleDualSIMModeSwitch.setValueIndex(TelephonyFragment.this.changeSingleDualSIMModeValueToIndex(TelephonyFragment.this.nowSingelDualSIMModeName));
                            }
                        });
                    }
                }).create();
                alertDialog2.setCancelable(false);
                alertDialog2.show();
            }
        }
    }

    @Override // android.app.Fragment
    public void onResume() throws IllegalAccessException, InvocationTargetException {
        super.onResume();
        this.mFirstDormancySet = this.preferences.getString("first_dormancy", "true");
        EMSwitchPreference eMSwitchPreference = this.mVowifiLabSimAllowedSwitch;
        if (eMSwitchPreference != null && eMSwitchPreference.isEnabled()) {
            if (SystemPropertiesProxy.get("persist.sys.vowifi.lab.sim", "false").equals("true")) {
                this.mVowifiLabSimAllowedSwitch.setChecked(true);
                this.mVowifiLabSimAllowedSwitch.setSummary("Open");
            } else {
                this.mVowifiLabSimAllowedSwitch.setChecked(false);
                this.mVowifiLabSimAllowedSwitch.setSummary("Close");
            }
        }
        EMSwitchPreference eMSwitchPreference2 = this.mVowifiWhiteListAllowedSwitch;
        if (eMSwitchPreference2 != null && eMSwitchPreference2.isEnabled()) {
            if (SystemPropertiesProxy.get("persist.sys.vowifi.whitelist", "false").equals("true")) {
                this.mVowifiWhiteListAllowedSwitch.setChecked(true);
                this.mVowifiWhiteListAllowedSwitch.setSummary("Open");
            } else {
                this.mVowifiWhiteListAllowedSwitch.setChecked(false);
                this.mVowifiWhiteListAllowedSwitch.setSummary("Close");
            }
        }
        EMSwitchPreference eMSwitchPreference3 = this.mImsSwitch;
        if (eMSwitchPreference3 != null && eMSwitchPreference3.isEnabled()) {
            if (SystemPropertiesProxy.get("persist.sys.ims.visibility", "false").equals("true")) {
                this.mImsSwitch.setChecked(true);
                this.mImsSwitch.setSummary("Open");
            } else {
                this.mImsSwitch.setChecked(false);
                this.mImsSwitch.setSummary("Close");
            }
        }
        String sKeyBack = this.preferences.getString("key_back", "false");
        if (sKeyBack.equals("true")) {
            this.mFastdormancy.setChecked(false);
            this.editor.putString("key_back", "false");
            this.editor.commit();
        } else {
            String sDefault = this.preferences.getString("default", "");
            if (sDefault.equals("true")) {
                SystemPropertiesProxy.set("persist.vendor.radio.fd.disable", "0");
                Message defaultValueMessage = this.mTELHandler.obtainMessage(8, 1, 0, "8");
                this.mTELHandler.sendMessage(defaultValueMessage);
                this.editor.putString("fd_open", "true");
                this.editor.commit();
                this.mFastdormancy.setSummary(this.mContext.getString(R.string.Default_value));
            } else if (sDefault.equals("false")) {
                this.editor.putString("default", "");
                this.editor.commit();
                String definite_value = this.preferences.getString("definite_value", "");
                if (definite_value == null || definite_value.length() == 0) {
                    Toast.makeText(this.mContext, "Please input between 1~65535", 0).show();
                    this.mFastdormancy.setChecked(false);
                    return;
                }
                int value = Integer.valueOf(definite_value).intValue();
                if (value > 65535 || value <= 0) {
                    Toast.makeText(this.mContext, "Please input between 1~65535", 0).show();
                    this.mFastdormancy.setChecked(false);
                    return;
                } else {
                    SystemPropertiesProxy.set("persist.vendor.radio.fd.disable", "1");
                    Message fastDormancy = this.mTELHandler.obtainMessage(8, 1, 0, definite_value);
                    this.mTELHandler.sendMessage(fastDormancy);
                    this.editor.putString("fd_open", "true");
                    this.editor.commit();
                }
            }
            if (this.mFirstDormancySet.equals("true")) {
                Message defaultValueMessage2 = this.mTELHandler.obtainMessage(8, 1, 0, "8");
                this.mTELHandler.sendMessage(defaultValueMessage2);
            }
        }
        EMSwitchPreference eMSwitchPreference4 = this.mLoadAPNSwitch;
        if (eMSwitchPreference4 != null) {
            eMSwitchPreference4.setChecked(SystemPropertiesProxy.getBoolean("persist.sys.loaded.apn", false));
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) throws IllegalAccessException, InvocationTargetException {
        String string;
        SwitchPreference switchPreference = this.mAutoAnswer;
        if (preference == switchPreference) {
            if (!switchPreference.isChecked()) {
                this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(2));
            } else {
                this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(3));
            }
        } else {
            TwoStatePreference twoStatePreference = this.mSimTrace;
            if (preference == twoStatePreference) {
                if (!twoStatePreference.isChecked()) {
                    this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(23));
                } else {
                    this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(24));
                }
            } else {
                EMSwitchPreference eMSwitchPreference = this.mWpreferplus;
                if (preference == eMSwitchPreference) {
                    if (eMSwitchPreference.isChecked()) {
                        this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(17, 0, 0, null));
                    } else {
                        this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(17, 1, 0, null));
                    }
                } else {
                    TwoStatePreference twoStatePreference2 = this.mDNSFilter;
                    if (preference == twoStatePreference2) {
                        if (twoStatePreference2.isChecked()) {
                            this.teleApi.dnsFilter().set(0);
                            SystemPropertiesProxy.set("sys.engineermode.dns", "0");
                        } else {
                            this.teleApi.dnsFilter().set(1);
                            SystemPropertiesProxy.set("sys.engineermode.dns", "1");
                        }
                    } else {
                        SwitchPreference switchPreference2 = this.mQosSwitch;
                        if (preference == switchPreference2) {
                            if (switchPreference2.isChecked()) {
                                SystemPropertiesProxy.set("persist.vendor.sys.qosstate", "0");
                                Toast.makeText(this.mContext, "Success", 0).show();
                            } else {
                                SystemPropertiesProxy.set("persist.vendor.sys.qosstate", "1");
                                Toast.makeText(this.mContext, "Success", 0).show();
                            }
                        } else {
                            TwoStatePreference twoStatePreference3 = this.mUsbActive;
                            if (preference == twoStatePreference3) {
                                if (twoStatePreference3.isChecked()) {
                                    Settings.Global.putInt(this.mContext.getContentResolver(), "switch_for_usb_active", 0);
                                    Toast.makeText(this.mContext, "Close Success", 0).show();
                                } else {
                                    Settings.Global.putInt(this.mContext.getContentResolver(), "switch_for_usb_active", 1);
                                    Toast.makeText(this.mContext, "Open Success", 0).show();
                                }
                            } else if (preference == this.mSprdIlogSwitch) {
                                Toast.makeText(this.mContext, "Success", 0).show();
                            } else {
                                EMSwitchPreference eMSwitchPreference2 = this.mFastdormancy;
                                if (preference == eMSwitchPreference2) {
                                    if (eMSwitchPreference2.isChecked()) {
                                        SystemPropertiesProxy.set("persist.vendor.radio.fd.disable", "1");
                                        this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(8, 0, 0, null));
                                        this.editor.putString("fd_open", "false");
                                        this.editor.commit();
                                    } else {
                                        if (this.mFastdormancy.getSummary() == null || this.mFastdormancy.getSummary().toString().compareTo("65535") == 0) {
                                            string = "1~65535(s)";
                                        } else {
                                            string = this.mFastdormancy.getSummary().toString();
                                        }
                                        this.editor.putString("summary", string);
                                        this.editor.commit();
                                        this.mContext.startActivity(new Intent("android.engineermode.action.FDActivity"));
                                    }
                                } else {
                                    EMSwitchPreference eMSwitchPreference3 = this.mDataServicePreferred;
                                    if (preference == eMSwitchPreference3) {
                                        if (!eMSwitchPreference3.isChecked()) {
                                            this.mTELHandler.sendEmptyMessage(11);
                                        } else {
                                            this.mTELHandler.sendEmptyMessage(12);
                                        }
                                    } else if (preference == this.mVTOptionPreference) {
                                        String string2 = getResources().getString(R.string.vtoption_on_message);
                                        if (this.mVTOptionPreference.isChecked()) {
                                            string2 = getResources().getString(R.string.vtoption_off_message);
                                        }
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle(getResources().getString(R.string.confirm));
                                        builder.setMessage(string2);
                                        builder.setNegativeButton(getResources().getString(R.string.vt_dialog_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.8
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public void onClick(DialogInterface dialog, int which) {
                                                TelephonyFragment.this.mVTOptionPreference.setChecked(!TelephonyFragment.this.mVTOptionPreference.isChecked());
                                                Log.d(TelephonyFragment.TAG, "setNegativeButton, mVTOptionPreference: " + TelephonyFragment.this.mVTOptionPreference.isChecked());
                                            }
                                        });
                                        builder.setPositiveButton(getResources().getString(R.string.vt_dialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.9
                                            @Override // android.content.DialogInterface.OnClickListener
                                            public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                                                SystemPropertiesProxy.set("persist.sys.support.vt", TelephonyFragment.this.mVTOptionPreference.isChecked() ? "true" : "false");
                                                Log.d(TelephonyFragment.TAG, "setPositiveButton,VT open: " + TelephonyFragment.this.mVTOptionPreference.isChecked());
                                                TelephonyFragment.this.rebootDevice("VTOption");
                                            }
                                        });
                                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.10
                                            @Override // android.content.DialogInterface.OnCancelListener
                                            public void onCancel(DialogInterface arg0) {
                                                TelephonyFragment.this.mVTOptionPreference.setChecked(!TelephonyFragment.this.mVTOptionPreference.isChecked());
                                                Log.d(TelephonyFragment.TAG, "Cancel Button, mVTOptionPreference: " + TelephonyFragment.this.mVTOptionPreference.isChecked());
                                            }
                                        });
                                        builder.show();
                                    } else {
                                        EMSwitchPreference eMSwitchPreference4 = this.mLTEDateImpede;
                                        if (preference == eMSwitchPreference4) {
                                            this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(21, !eMSwitchPreference4.isChecked() ? 1 : 0, 0));
                                        } else {
                                            EMSwitchPreference eMSwitchPreference5 = this.mWhiteListIgnoreSwitch;
                                            if (preference == eMSwitchPreference5) {
                                                if (eMSwitchPreference5.isChecked()) {
                                                    this.mWhiteListIgnoreSwitch.setChecked(false);
                                                    this.mDualVolteChecked = false;
                                                    this.mWhiteListIgnoreSwitch.setSummary("Close");
                                                    AlertDialogShow(KEY_WHITELIST_INGORE_SWITCH);
                                                } else {
                                                    this.mWhiteListIgnoreSwitch.setChecked(true);
                                                    this.mDualVolteChecked = true;
                                                    this.mWhiteListIgnoreSwitch.setSummary("Open");
                                                    AlertDialogShow(KEY_WHITELIST_INGORE_SWITCH);
                                                }
                                            } else {
                                                EMSwitchPreference eMSwitchPreference6 = this.mVowifiLabSimAllowedSwitch;
                                                if (preference == eMSwitchPreference6) {
                                                    if (eMSwitchPreference6.isChecked()) {
                                                        this.mVowifiLabSimAllowedSwitch.setChecked(false);
                                                        this.mVowifiLabSimAllowedChecked = false;
                                                        this.mVowifiLabSimAllowedSwitch.setSummary("Close");
                                                        AlertDialogShow(KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH);
                                                    } else {
                                                        this.mVowifiLabSimAllowedSwitch.setChecked(true);
                                                        this.mVowifiLabSimAllowedChecked = true;
                                                        this.mVowifiLabSimAllowedSwitch.setSummary("Open");
                                                        AlertDialogShow(KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH);
                                                    }
                                                } else {
                                                    EMSwitchPreference eMSwitchPreference7 = this.mVowifiWhiteListAllowedSwitch;
                                                    if (preference == eMSwitchPreference7) {
                                                        if (eMSwitchPreference7.isChecked()) {
                                                            this.mVowifiWhiteListAllowedSwitch.setChecked(false);
                                                            this.mVowifiWhiteListAllowedChecked = false;
                                                            this.mVowifiWhiteListAllowedSwitch.setSummary("Close");
                                                            AlertDialogShow(KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH);
                                                        } else {
                                                            this.mVowifiWhiteListAllowedSwitch.setChecked(true);
                                                            this.mVowifiWhiteListAllowedChecked = true;
                                                            this.mVowifiWhiteListAllowedSwitch.setSummary("Open");
                                                            AlertDialogShow(KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH);
                                                        }
                                                    } else {
                                                        EMSwitchPreference eMSwitchPreference8 = this.mDisableEvdo;
                                                        if (preference == eMSwitchPreference8) {
                                                            if (eMSwitchPreference8.isChecked()) {
                                                                this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(40, 0));
                                                            } else {
                                                                this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(40, 1));
                                                            }
                                                        } else {
                                                            EMSwitchPreference eMSwitchPreference9 = this.mImsSwitch;
                                                            if (preference == eMSwitchPreference9) {
                                                                if (eMSwitchPreference9.isChecked()) {
                                                                    this.mImsSwitch.setChecked(false);
                                                                    SystemPropertiesProxy.set("persist.sys.ims.visibility", "false");
                                                                    this.mImsSwitch.setSummary("Close");
                                                                } else {
                                                                    this.mImsSwitch.setChecked(true);
                                                                    SystemPropertiesProxy.set("persist.sys.ims.visibility", "true");
                                                                    this.mImsSwitch.setSummary("Open");
                                                                }
                                                            } else {
                                                                EMSwitchPreference eMSwitchPreference10 = this.mLoadAPNSwitch;
                                                                if (preference == eMSwitchPreference10) {
                                                                    if (eMSwitchPreference10.isChecked()) {
                                                                        SystemPropertiesProxy.set("persist.sys.loaded.apn", "false");
                                                                    } else {
                                                                        SystemPropertiesProxy.set("persist.sys.loaded.apn", "true");
                                                                    }
                                                                } else {
                                                                    EMSwitchPreference eMSwitchPreference11 = this.mMosSwitch;
                                                                    if (preference == eMSwitchPreference11) {
                                                                        int i = !eMSwitchPreference11.isChecked() ? 1 : 0;
                                                                        Log.d(TAG, "set_flag is " + i);
                                                                        this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(36, i, 0));
                                                                    } else {
                                                                        TwoStatePreference twoStatePreference4 = this.mUplmnCon;
                                                                        if (preference == twoStatePreference4) {
                                                                            if (!twoStatePreference4.isChecked()) {
                                                                                SystemPropertiesProxy.set("persist.sys.uplmn", "1");
                                                                            } else {
                                                                                SystemPropertiesProxy.set("persist.sys.uplmn", "0");
                                                                            }
                                                                        } else if (preference == this.mC2kConfig) {
                                                                            this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(42, obj));
                                                                        } else {
                                                                            EMSwitchPreference eMSwitchPreference12 = this.mMeidSwitch;
                                                                            if (preference == eMSwitchPreference12) {
                                                                                int i2 = !eMSwitchPreference12.isChecked() ? 1 : 0;
                                                                                if (this.mMeidSwitch.isChecked()) {
                                                                                    Log.d(TAG, "set close meid_flag is " + i2);
                                                                                    this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(44, 0));
                                                                                } else {
                                                                                    Log.d(TAG, "set open meid_flag is " + i2);
                                                                                    this.mTELHandler.sendMessage(this.mTELHandler.obtainMessage(44, 1));
                                                                                }
                                                                            } else {
                                                                                EMSwitchPreference eMSwitchPreference13 = this.mDmSwitch;
                                                                                if (preference != eMSwitchPreference13) {
                                                                                    return false;
                                                                                }
                                                                                if (eMSwitchPreference13.isChecked()) {
                                                                                    Settings.Global.putInt(this.mContext.getContentResolver(), "dm_switch", 0);
                                                                                    this.mDmSwitch.setChecked(false);
                                                                                    this.mDmSwitch.setSummary("Close");
                                                                                    Toast.makeText(this.mContext, "Close Success", 0).show();
                                                                                } else {
                                                                                    Settings.Global.putInt(this.mContext.getContentResolver(), "dm_switch", 1);
                                                                                    this.mDmSwitch.setChecked(true);
                                                                                    this.mDmSwitch.setSummary("Open");
                                                                                    Toast.makeText(this.mContext, "Open Success", 0).show();
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
        }
        return true;
    }

    private void ShowVolteEnhancedAlertDialog(boolean isSwitchChecked) {
        String warningMessage;
        if (isSwitchChecked) {
            warningMessage = getString(R.string.dsda_enhanced_open);
        } else {
            warningMessage = getString(R.string.dsda_enhanced_closed);
        }
        AlertDialog alertDialog = new AlertDialog.Builder(this.mContext).setTitle(getString(R.string.volte_dsda_enhanced)).setMessage(warningMessage).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.11
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
        Log.d(TAG, "alertDialog.show");
    }

    private void checkEngTest() {
        if (this.teleApi.EngTestStatus().isEngTest()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this.mContext).setTitle(getString(R.string.bandselect_warning)).setMessage(getString(R.string.bandselect_warning_message)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.13
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.12
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
        }
    }

    @Override // android.preference.PreferenceFragment
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d(TAG, "onPreferenceTreeClick() preference.getKey():" + preference.getKey() + " preferenceScreen.getKey():" + preferenceScreen.getKey());
        if (key == null) {
            return false;
        }
        if (key.equals("band_select")) {
            Log.d(TAG, "mCardCount: " + this.mCardCount);
            if (this.mCardCount == 0) {
                Toast.makeText(this.mContext, getString(R.string.insert_sim_card_prompt), 1).show();
            } else {
                Intent intent = new Intent("com.sprd.engineermode.action.BANDMODESET");
                int primaryCardId = this.mTelephonyManagerSprd.getPrimaryCard();
                Log.d(TAG, "set KEY_PHONEID, primaryCardId=:" + primaryCardId);
                intent.putExtra(KEY_PHONEID, primaryCardId);
                startActivity(intent);
            }
        } else if (key.equals("net_info")) {
            this.mContext.startActivity(new Intent("android.engineermode.action.SIMSELECT_NETINFO"));
        } else if (key.equals("nv_item_list")) {
            String ATresult = IATUtils.sendAt("AT", "atchannel0");
            if (ATresult.contains("OK")) {
                this.mContext.startActivity(new Intent(this.mContext, (Class<?>) NVItemListActivity.class));
            } else if (ATresult.contains(IATUtils.AT_FAIL)) {
                Toast.makeText(this.mContext, "sorry, the modem is not alive!", 1).show();
            }
        } else if (key.equals(KEY_GPRS_SET)) {
            boolean bContinue = false;
            for (int i = 0; i < this.mPhoneCount; i++) {
                if (TelephonyManagerProxy.getSimState(i) == 5) {
                    bContinue = true;
                    break;
                }
            }
            if (bContinue) {
                this.mContext.startActivity(new Intent("android.engineermode.action.GPRSSET"));
            } else {
                this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.14
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(TelephonyFragment.this.mContext, TelephonyFragment.this.getString(R.string.sim_invalid), 0).show();
                    }
                });
            }
        } else if (key.equals(KEY_NETMODE_SELECT)) {
            if (this.isSupportLTE && !this.isLplusW) {
                AlertDialog alertDialog = new AlertDialog.Builder(this.mContext).setTitle(getString(R.string.network_mode)).setMessage(getString(R.string.switch_mode_warn_message)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.16
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        if (TelephonyFragment.this.isLplusG) {
                            Intent intent2 = new Intent("android.engineermode.action.LTEActivity");
                            intent2.putExtra("simindex", TelephonyFragment.this.mPhoneId);
                            TelephonyFragment.this.mContext.startActivity(intent2);
                        } else {
                            Intent intent3 = new Intent("android.engineermode.action.NetModeSIMActivity");
                            intent3.putExtra(TelephonyFragment.INTENT_CONTENT, "netmode");
                            TelephonyFragment.this.mContext.startActivity(intent3);
                        }
                    }
                }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.15
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
                alertDialog.show();
            } else if (this.isSupportC2K) {
                Intent intent2 = new Intent("android.engineermode.action.NetModeSIMActivity");
                intent2.putExtra(INTENT_CONTENT, "netmode");
                this.mContext.startActivity(intent2);
            } else if (this.isLplusW) {
                Intent intent3 = new Intent("android.engineermode.action.NetModeSelection1");
                intent3.putExtra("simindex", 0);
                this.mContext.startActivity(intent3);
            } else {
                Intent intent4 = new Intent("android.engineermode.action.NetModeSelection");
                intent4.putExtra("simindex", 0);
                this.mContext.startActivity(intent4);
            }
        } else if (key.equals(KEY_NETINFO_STATI)) {
            int i2 = this.mPhoneCount;
            if (i2 > 1) {
                this.mContext.startActivity(new Intent("android.engineermode.action.SIMSELECT_NETSTATI"));
            } else if (i2 == 1) {
                Intent intent5 = new Intent("android.engineermode.action.NETINFOSTATI");
                intent5.putExtra("simindex", 0);
                this.mContext.startActivity(intent5);
            }
        } else if (key.equals(KEY_OPERATOR_SETTING_0)) {
            Log.d(TAG, "mOperatorSetting0");
            Intent intent0 = new Intent("com.sprd.engineermode.action.OPERATORSETTINGSSIM");
            intent0.putExtra("key_channel", 0);
            startActivity(intent0);
        } else if (key.equals(KEY_OPERATOR_SETTING_1)) {
            Log.d(TAG, "mOperatorSetting1");
            Intent intent1 = new Intent("com.sprd.engineermode.action.OPERATORSETTINGSSIM");
            intent1.putExtra("key_channel", 1);
            startActivity(intent1);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    class TELHandler extends Handler {
        public TELHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Exception {
            int i = message.what;
            if (i == 1) {
                try {
                    TelephonyFragment.this.teleApi.callFunc().setCfu((String) message.obj);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (i == 2) {
                Log.d(TelephonyFragment.TAG, "OPEN_AUTO_ANSWER");
                try {
                    TelephonyFragment.this.teleApi.callFunc().openAutoAnswer();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                Log.d(TelephonyFragment.TAG, "open auto answer, set vowifi prop, value is: " + SystemPropertiesProxy.getBoolean(TelephonyFragment.AUTO_ANSWER, false));
                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.12
                    @Override // java.lang.Runnable
                    public void run() {
                        TelephonyFragment.this.mAutoAnswer.setChecked(true);
                        Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                    }
                });
                return;
            }
            if (i == 3) {
                Log.d(TelephonyFragment.TAG, "CLOSE_AUTO_ANSWER");
                try {
                    TelephonyFragment.this.teleApi.callFunc().closeAutoAnswer();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                Log.d(TelephonyFragment.TAG, "close auto answer, set vowifi prop, value is: " + SystemPropertiesProxy.getBoolean(TelephonyFragment.AUTO_ANSWER, false));
                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.13
                    @Override // java.lang.Runnable
                    public void run() {
                        TelephonyFragment.this.mAutoAnswer.setChecked(false);
                        Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                    }
                });
                return;
            }
            if (i == 8) {
                final int i2 = message.arg1;
                int i3 = 0;
                try {
                    if (i2 == 0) {
                        TelephonyFragment.this.teleApi.fastDormancy().close();
                    } else {
                        if (message.obj != null) {
                            i3 = Integer.parseInt((String) message.obj);
                        }
                        TelephonyFragment.this.teleApi.fastDormancy().open(i3);
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.14
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                        }
                    });
                }
                final String strValueOf = String.valueOf(i3);
                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.15
                    @Override // java.lang.Runnable
                    public void run() {
                        if (TelephonyFragment.this.mFirstDormancySet.equals("true")) {
                            TelephonyFragment.this.editor.putString("first_dormancy", "false");
                            return;
                        }
                        Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                        String defaultValue = TelephonyFragment.this.preferences.getString("default", "");
                        if (defaultValue.equals("true")) {
                            TelephonyFragment.this.mFastdormancy.setSummary(TelephonyFragment.this.mContext.getString(R.string.Default_value));
                            TelephonyFragment.this.editor.putString("default", "");
                            TelephonyFragment.this.editor.commit();
                            return;
                        }
                        if (i2 == 1) {
                            TelephonyFragment.this.mFastdormancy.setSummary(strValueOf + "s");
                        } else {
                            TelephonyFragment.this.mFastdormancy.setSummary(strValueOf);
                        }
                        TelephonyFragment.this.editor.putString(TelephonyFragment.DORM_TIME, strValueOf);
                        TelephonyFragment.this.editor.commit();
                    }
                });
                return;
            }
            if (i == 9) {
                try {
                    TelephonyFragment.this.teleApi.videoType().set(Integer.parseInt((String) message.obj));
                    return;
                } catch (Exception e5) {
                    e5.printStackTrace();
                    return;
                }
            }
            if (i == 11) {
                try {
                    TelephonyFragment.this.teleApi.dataServicePrefer().open();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.17
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mDataServicePreferred.setChecked(true);
                            Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } catch (Exception e6) {
                    e6.printStackTrace();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.16
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mDataServicePreferred.setChecked(false);
                            Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 12) {
                try {
                    TelephonyFragment.this.teleApi.dataServicePrefer().close();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.19
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mDataServicePreferred.setChecked(false);
                            Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } catch (Exception e7) {
                    e7.printStackTrace();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.18
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mDataServicePreferred.setChecked(true);
                            Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i == 16) {
                try {
                    TelephonyFragment.this.mStrTmp = TelephonyFragment.this.teleApi.wcdmaPrefer().getWcdmaPrefer();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
                if (TelephonyFragment.this.mStrTmp == null || !TelephonyFragment.this.mStrTmp.contains("1")) {
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.11
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mWpreferplus.setChecked(false);
                        }
                    });
                    return;
                } else {
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.10
                        @Override // java.lang.Runnable
                        public void run() {
                            TelephonyFragment.this.mWpreferplus.setChecked(true);
                        }
                    });
                    return;
                }
            }
            if (i == 17) {
                TelephonyFragment.this.mMesarg = message.arg1;
                try {
                    if (TelephonyFragment.this.mMesarg == 1) {
                        TelephonyFragment.this.teleApi.wcdmaPrefer().openWcdmaPrefer();
                    } else {
                        TelephonyFragment.this.teleApi.wcdmaPrefer().closeWcdmaPrefer();
                    }
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (TelephonyFragment.this.mMesarg == 1) {
                                TelephonyFragment.this.mWpreferplus.setChecked(true);
                            } else {
                                TelephonyFragment.this.mWpreferplus.setChecked(false);
                            }
                            Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } catch (Exception e9) {
                    e9.printStackTrace();
                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (TelephonyFragment.this.mMesarg == 1) {
                                TelephonyFragment.this.mWpreferplus.setChecked(false);
                            } else {
                                TelephonyFragment.this.mWpreferplus.setChecked(true);
                            }
                            Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                        }
                    });
                    return;
                }
            }
            if (i != 27) {
                switch (i) {
                    case 20:
                        try {
                            TelephonyFragment.this.mATCmd = engconstents.ENG_AT_GET_LTE_DATA_IMPEDE;
                            Log.d(TelephonyFragment.TAG, "LTE_GET_DATA_IMPEDE  mATCmd: " + TelephonyFragment.this.mATCmd);
                            TelephonyFragment.this.mATResponse = IATUtils.sendATCmd(TelephonyFragment.this.mATCmd, "atchannel0");
                            Log.d(TelephonyFragment.TAG, "LTE_GET_DATA_IMPEDE  mATResponse: " + TelephonyFragment.this.mATResponse);
                            if (TelephonyFragment.this.mATResponse == null || !TelephonyFragment.this.mATResponse.contains("OK")) {
                                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.22
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        TelephonyFragment.this.mLTEDateImpede.setEnabled(false);
                                    }
                                });
                            } else {
                                String strSubstring = TelephonyFragment.this.mATResponse.split("\n")[0].split(":")[1].trim().substring(2);
                                int i4 = Integer.parseInt(strSubstring, 16);
                                Log.d(TelephonyFragment.TAG, "atResponse: " + strSubstring + "value: " + i4);
                                if ((i4 & 1) > 0) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.20
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mLTEDateImpede.setChecked(true);
                                        }
                                    });
                                } else {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.21
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mLTEDateImpede.setChecked(false);
                                        }
                                    });
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e10) {
                            e10.printStackTrace();
                            return;
                        }
                        break;
                    case 21:
                        int i5 = message.arg1;
                        TelephonyFragment.this.mATCmd = "AT+SPLTECFG=";
                        Log.d(TelephonyFragment.TAG, "LTE_SET_DATA_IMPEDE  mATCmd: " + TelephonyFragment.this.mATCmd);
                        TelephonyFragment.this.mATResponse = IATUtils.sendATCmd(TelephonyFragment.this.mATCmd + i5 + ",1", "atchannel0");
                        StringBuilder sb = new StringBuilder();
                        sb.append("LTE_SET_DATA_IMPEDE  mATResponse: ");
                        sb.append(TelephonyFragment.this.mATResponse);
                        Log.d(TelephonyFragment.TAG, sb.toString());
                        if (TelephonyFragment.this.mATResponse == null || !TelephonyFragment.this.mATResponse.contains("OK")) {
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.24
                                @Override // java.lang.Runnable
                                public void run() {
                                    Toast.makeText(TelephonyFragment.this.mContext, "Failed", 0).show();
                                }
                            });
                        } else {
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.23
                                @Override // java.lang.Runnable
                                public void run() {
                                    Toast.makeText(TelephonyFragment.this.mContext, "Success", 0).show();
                                }
                            });
                        }
                        break;
                    case 22:
                        boolean z = false;
                        try {
                            z = TelephonyFragment.this.teleApi.simTrace().get();
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                        if (z) {
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(true);
                                }
                            });
                        } else {
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(false);
                                }
                            });
                        }
                        break;
                    case 23:
                        try {
                            TelephonyFragment.this.teleApi.simTrace().open();
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(true);
                                }
                            });
                        } catch (Exception e12) {
                            e12.printStackTrace();
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(false);
                                    Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                                }
                            });
                            return;
                        }
                        break;
                    case 24:
                        try {
                            TelephonyFragment.this.teleApi.simTrace().close();
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(false);
                                }
                            });
                        } catch (Exception e13) {
                            e13.printStackTrace();
                            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    TelephonyFragment.this.mSimTrace.setChecked(true);
                                    Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                                }
                            });
                            return;
                        }
                        break;
                    default:
                        switch (i) {
                            case 34:
                                TelephonyFragment.this.mATCmd = engconstents.ENG_AT_GET_NXP;
                                Log.d(TelephonyFragment.TAG, "GET_NXP_STATUS mATCmd: " + TelephonyFragment.this.mATCmd);
                                TelephonyFragment telephonyFragment = TelephonyFragment.this;
                                telephonyFragment.mATResponse = IATUtils.sendATCmd(telephonyFragment.mATCmd, "atchannel1");
                                Log.d(TelephonyFragment.TAG, "GET_NXP_STATUS mATResponse: " + TelephonyFragment.this.mATResponse);
                                if (TelephonyFragment.this.mATResponse == null) {
                                    TelephonyFragment.this.mATResponse = "";
                                }
                                if ((TelephonyFragment.this.mATResponse.length() - TelephonyFragment.this.mATResponse.replaceAll("255", "").length()) / "255".length() == 27) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.25
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mNxp.setEnabled(false);
                                            TelephonyFragment.this.mNxp.setSummary(R.string.feature_not_support);
                                        }
                                    });
                                } else if (!TelephonyFragment.this.mATResponse.contains("OK")) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.28
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (TelephonyFragment.this.getNxpStatus) {
                                                Toast.makeText(TelephonyFragment.this.mContext, "get nxp status failed", 0).show();
                                            }
                                            TelephonyFragment.this.mNxp.setEnabled(false);
                                            TelephonyFragment.this.mNxp.setSummary(R.string.feature_not_support);
                                        }
                                    });
                                } else {
                                    TelephonyFragment telephonyFragment2 = TelephonyFragment.this;
                                    telephonyFragment2.status = telephonyFragment2.analysisResult(telephonyFragment2.mATResponse);
                                    Log.d(TelephonyFragment.TAG, "status size is" + TelephonyFragment.this.status.length);
                                    if (TelephonyFragment.this.status.length < 28) {
                                        TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.26
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                TelephonyFragment.this.mNxp.setEnabled(false);
                                                TelephonyFragment.this.mNxp.setSummary(R.string.feature_not_support);
                                            }
                                        });
                                    } else {
                                        TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.27
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i6 = 0; i6 < 27; i6++) {
                                                    if (TelephonyFragment.this.status[i6 + 1].contains("1")) {
                                                        TelephonyFragment.this.mNxpList[i6].setChecked(true);
                                                    } else if (TelephonyFragment.this.status[i6 + 1].contains("0")) {
                                                        TelephonyFragment.this.mNxpList[i6].setChecked(false);
                                                    } else {
                                                        TelephonyFragment.this.mNxpList[i6].setEnabled(false);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                break;
                            case 35:
                                TelephonyFragment.this.mATCmd = engconstents.ENG_AT_GET_MOS_STATUS;
                                Log.d(TelephonyFragment.TAG, "GET_MOS_STATUS mATCmd: " + TelephonyFragment.this.mATCmd);
                                TelephonyFragment telephonyFragment3 = TelephonyFragment.this;
                                telephonyFragment3.mATResponse = IATUtils.sendATCmd(telephonyFragment3.mATCmd, "atchannel1");
                                Log.d(TelephonyFragment.TAG, "GET_MOS_STATUS mATResponse: " + TelephonyFragment.this.mATResponse);
                                if (TelephonyFragment.this.mATResponse == null || !TelephonyFragment.this.mATResponse.contains("OK")) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.34
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Log.d(TelephonyFragment.TAG, "GET_MOS_STATUS Failed");
                                            TelephonyFragment.this.mMosSwitch.setSummary(TelephonyFragment.this.mContext.getString(R.string.feature_not_support));
                                            TelephonyFragment.this.mMosSwitch.setEnabled(false);
                                        }
                                    });
                                } else {
                                    String[] strArrSplit = TelephonyFragment.this.mATResponse.split("\n");
                                    final String strSubstring2 = strArrSplit[0].substring(strArrSplit[0].length() - 2);
                                    Log.d(TelephonyFragment.TAG, strSubstring2);
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.33
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (strSubstring2.contains("1")) {
                                                TelephonyFragment.this.mMosSwitch.setChecked(true);
                                                TelephonyFragment.this.mMosSwitch.setSummary(R.string.mos_test_mode);
                                            } else {
                                                TelephonyFragment.this.mMosSwitch.setChecked(false);
                                                TelephonyFragment.this.mMosSwitch.setSummary(R.string.not_mos_test_mode);
                                            }
                                        }
                                    });
                                }
                                break;
                            case 36:
                                final int i6 = message.arg1;
                                TelephonyFragment.this.mATCmd = engconstents.ENG_AT_SET_MOS_STATUS;
                                Log.d(TelephonyFragment.TAG, "SET_MOS_STATUS mATCmd: " + TelephonyFragment.this.mATCmd);
                                TelephonyFragment.this.mATResponse = IATUtils.sendATCmd(TelephonyFragment.this.mATCmd + "," + i6, "atchannel1");
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("SET_MOS_STATUS mATResponse: ");
                                sb2.append(TelephonyFragment.this.mATResponse);
                                Log.d(TelephonyFragment.TAG, sb2.toString());
                                if (TelephonyFragment.this.mATResponse == null || !TelephonyFragment.this.mATResponse.contains("OK")) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.36
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "set mos status failed", 0).show();
                                            TelephonyFragment.this.mMosSwitch.setChecked(TelephonyFragment.this.mMosSwitch.isChecked() ? false : true);
                                        }
                                    });
                                } else {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.35
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "set mos status success", 0).show();
                                            int i7 = i6;
                                            if (i7 == 0) {
                                                TelephonyFragment.this.mMosSwitch.setSummary(TelephonyFragment.this.getString(R.string.not_mos_test_mode));
                                            } else if (1 == i7) {
                                                TelephonyFragment.this.mMosSwitch.setSummary(TelephonyFragment.this.getString(R.string.mos_test_mode));
                                            }
                                        }
                                    });
                                }
                                break;
                            case 37:
                                TelephonyFragment.this.nxp_index = message.arg1;
                                TelephonyFragment.this.mATCmd = engconstents.ENG_AT_SET_NXP_STATUS;
                                Log.d(TelephonyFragment.TAG, "SET_NXP_STATUS mATCmd: " + TelephonyFragment.this.mATCmd);
                                boolean zIsChecked = TelephonyFragment.this.mNxpList[TelephonyFragment.this.nxp_index - 1].isChecked();
                                TelephonyFragment.this.mATResponse = IATUtils.sendATCmd(TelephonyFragment.this.mATCmd + "," + TelephonyFragment.this.nxp_index + "," + (zIsChecked ? 1 : 0), "atchannel1");
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("SET_NXP_STATUS mATResponse: ");
                                sb3.append(TelephonyFragment.this.mATResponse);
                                Log.d(TelephonyFragment.TAG, sb3.toString());
                                if (TelephonyFragment.this.mATResponse == null || !TelephonyFragment.this.mATResponse.contains("OK")) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.39
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "set mos status failed", 0).show();
                                        }
                                    });
                                } else if (zIsChecked) {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.37
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mNxpList[TelephonyFragment.this.nxp_index - 1].setChecked(true);
                                        }
                                    });
                                } else {
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.38
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mNxpList[TelephonyFragment.this.nxp_index - 1].setChecked(false);
                                        }
                                    });
                                }
                                break;
                            case 38:
                                TelephonyFragment.this.setDsdsMode(Integer.parseInt((String) message.obj));
                                TelephonyFragment.this.nowDsdsModeName = SystemPropertiesProxy.get("persist.vendor.radio.modem.config", "");
                                TelephonyFragment.this.rebootDevice("setDsdsMode");
                                break;
                            case 39:
                                try {
                                    TelephonyFragment.this.mATResponse = TelephonyFragment.this.teleApi.controlEvdo().getEvdoStatus();
                                    if (TelephonyFragment.this.mATResponse == null) {
                                        TelephonyFragment.this.mATResponse = "";
                                    }
                                    final String strTrim = TelephonyFragment.this.mATResponse.split("\n")[0].trim();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.40
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (strTrim.contains("1")) {
                                                TelephonyFragment.this.mDisableEvdo.setChecked(true);
                                                TelephonyFragment.this.mDisableEvdo.setSummary(R.string.status_opened);
                                            } else {
                                                TelephonyFragment.this.mDisableEvdo.setChecked(false);
                                                TelephonyFragment.this.mDisableEvdo.setSummary(R.string.status_closed);
                                            }
                                        }
                                    });
                                } catch (Exception e14) {
                                    e14.printStackTrace();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.41
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mDisableEvdo.setSummary(TelephonyFragment.this.mContext.getString(R.string.feature_not_support));
                                            TelephonyFragment.this.mDisableEvdo.setEnabled(false);
                                        }
                                    });
                                    return;
                                }
                                break;
                            case 40:
                                final int iIntValue = ((Integer) message.obj).intValue();
                                try {
                                    if (iIntValue == 0) {
                                        TelephonyFragment.this.teleApi.controlEvdo().closeEvdo();
                                    } else {
                                        TelephonyFragment.this.teleApi.controlEvdo().openEvdo();
                                    }
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.42
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            int i7 = iIntValue;
                                            if (i7 == 0) {
                                                TelephonyFragment.this.mDisableEvdo.setSummary(TelephonyFragment.this.getString(R.string.status_closed));
                                            } else if (1 == i7) {
                                                TelephonyFragment.this.mDisableEvdo.setSummary(TelephonyFragment.this.getString(R.string.status_opened));
                                            }
                                            TelephonyFragment.this.mTELHandler.postDelayed(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.42.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    TelephonyFragment.this.rebootDevice("evdoDisableSwitch");
                                                }
                                            }, 2000L);
                                        }
                                    });
                                } catch (Exception e15) {
                                    e15.printStackTrace();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.43
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "set failed", 0).show();
                                            TelephonyFragment.this.mDisableEvdo.setChecked(!TelephonyFragment.this.mDisableEvdo.isChecked());
                                        }
                                    });
                                    return;
                                }
                                break;
                            case 41:
                                try {
                                    String str = TelephonyFragment.this.teleApi.c2kConfig().getC2kConfig().split(":")[1];
                                    final String strTrim2 = (str.contains(",") ? str.split(",")[0] : str.split("\n")[0]).trim();
                                    Log.d(TelephonyFragment.TAG, "GET_C2K_CONFIG getValue: " + strTrim2);
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.44
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mC2kConfig.setValueIndex(TelephonyFragment.this.c2kConfigToIndex(strTrim2));
                                            TelephonyFragment.this.mC2kConfig.setSummary(TelephonyFragment.this.mC2kConfig.getEntry());
                                        }
                                    });
                                } catch (Exception e16) {
                                    e16.printStackTrace();
                                    return;
                                }
                                break;
                            case 42:
                                final int i7 = Integer.parseInt((String) message.obj);
                                Log.d(TelephonyFragment.TAG, "SET_C2K_CONFIG c2kConfigValue: " + i7);
                                try {
                                    TelephonyFragment.this.teleApi.c2kConfig().setC2kConfig(TelephonyFragment.this.indexToC2kConfig(i7));
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.45
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mC2kConfig.setValueIndex(i7);
                                            TelephonyFragment.this.mC2kConfig.setSummary(TelephonyFragment.this.mC2kConfig.getEntry());
                                        }
                                    });
                                } catch (Exception e17) {
                                    e17.printStackTrace();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.46
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "Fail", 0).show();
                                        }
                                    });
                                    return;
                                }
                                break;
                            case 43:
                                try {
                                    TelephonyFragment.this.mATResponse = TelephonyFragment.this.teleApi.controlMeidSwitch().getMeidStatus();
                                    if (TelephonyFragment.this.mATResponse == null) {
                                        TelephonyFragment.this.mATResponse = "";
                                    }
                                    String[] strArrSplit2 = TelephonyFragment.this.mATResponse.split("\n");
                                    String strSubstring3 = "";
                                    if (strArrSplit2[0].length() > 1) {
                                        strSubstring3 = strArrSplit2[0].substring(strArrSplit2[0].length() - 2);
                                    }
                                    final String str2 = strSubstring3;
                                    Log.d(TelephonyFragment.TAG, " GET_MEID_SWITCH s = " + str2);
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.29
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            if (str2.contains("1")) {
                                                TelephonyFragment.this.mMeidSwitch.setChecked(true);
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_opened));
                                            } else {
                                                TelephonyFragment.this.mMeidSwitch.setChecked(false);
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_closed));
                                            }
                                        }
                                    });
                                } catch (Exception e18) {
                                    e18.printStackTrace();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.30
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            TelephonyFragment.this.mMeidSwitch.setChecked(true);
                                            TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_opened));
                                            TelephonyFragment.this.mMeidSwitch.setEnabled(false);
                                        }
                                    });
                                    return;
                                }
                                break;
                            case 44:
                                final int iIntValue2 = ((Integer) message.obj).intValue();
                                Log.d(TelephonyFragment.TAG, "SET_MEID_SWITCH set_flag: " + iIntValue2);
                                try {
                                    if (iIntValue2 == 0) {
                                        TelephonyFragment.this.teleApi.controlMeidSwitch().closeMeidSwitch();
                                    } else {
                                        TelephonyFragment.this.teleApi.controlMeidSwitch().openMeidSwitch();
                                    }
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.31
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            int i8 = iIntValue2;
                                            if (i8 == 0) {
                                                TelephonyFragment.this.mMeidSwitch.setChecked(false);
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_closed));
                                            } else if (1 == i8) {
                                                TelephonyFragment.this.mMeidSwitch.setChecked(true);
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_opened));
                                            }
                                        }
                                    });
                                } catch (Exception e19) {
                                    e19.printStackTrace();
                                    TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.32
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(TelephonyFragment.this.mContext, "set failed", 0).show();
                                            TelephonyFragment.this.mMeidSwitch.setChecked(!TelephonyFragment.this.mMeidSwitch.isChecked());
                                            if (iIntValue2 == 0) {
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_opened));
                                            } else {
                                                TelephonyFragment.this.mMeidSwitch.setSummary(TelephonyFragment.this.getString(R.string.status_closed));
                                            }
                                        }
                                    });
                                    return;
                                }
                                break;
                        }
                        break;
                }
                return;
            }
            String str3 = (String) message.obj;
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(SubscriptionManagerProxy.SIM_SLOT_INDEX, Integer.valueOf(SubscriptionManagerProxy.INVALID_SIM_SLOT_INDEX));
            TelephonyFragment.this.mContext.getContentResolver().update(SubscriptionManagerProxy.CONTENT_URI, contentValues, null, null);
            TelephonyFragment.this.setSingeDualSIMModeSwitch(Integer.parseInt(str3));
            TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.TELHandler.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e20) {
                    }
                    TelephonyFragment.this.rebootDevice("setSingleDualMode");
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String indexToC2kConfig(int value) {
        if (value == 0) {
            return C2K_CONFIG_DEFAULT;
        }
        if (value == 1) {
            return C2K_CONFIG_CTCC;
        }
        if (value == 2) {
            return C2K_CONFIG_ROAMING;
        }
        if (value == 3) {
            return C2K_CONFIG_CHAP;
        }
        if (value != 4) {
            return C2K_CONFIG_DEFAULT;
        }
        return C2K_CONFIG_EXTENDED2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int c2kConfigToIndex(String str) {
        if (str.contains(C2K_CONFIG_DEFAULT)) {
            return 0;
        }
        if (str.contains(C2K_CONFIG_CTCC)) {
            return 1;
        }
        if (str.contains(C2K_CONFIG_ROAMING)) {
            return 2;
        }
        if (str.contains(C2K_CONFIG_CHAP)) {
            return 3;
        }
        return str.contains(C2K_CONFIG_EXTENDED2) ? 4 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] analysisResult(String result) {
        String[] str = result.split("\n");
        return str[0].split(",");
    }

    @Override // android.preference.PreferenceFragment, android.app.Fragment
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void AlertDialogShow(final String key) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setMessage(getString(R.string.volte_switch_waring)).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.19
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                if (key.equals(TelephonyFragment.KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH)) {
                    if (TelephonyFragment.this.mVowifiLabSimAllowedChecked) {
                        SystemPropertiesProxy.set("persist.sys.vowifi.lab.sim", "true");
                        SystemPropertiesProxy.set("persist.sys.vowifi.whitelist", "false");
                    } else {
                        SystemPropertiesProxy.set("persist.sys.vowifi.lab.sim", "false");
                    }
                    TelephonyFragment.this.rebootDevice("setVowifiLabSimAllowedSwitch");
                    return;
                }
                if (key.equals(TelephonyFragment.KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH)) {
                    if (TelephonyFragment.this.mVowifiWhiteListAllowedChecked) {
                        SystemPropertiesProxy.set("persist.sys.vowifi.whitelist", "true");
                        SystemPropertiesProxy.set("persist.sys.vowifi.lab.sim", "false");
                    } else {
                        SystemPropertiesProxy.set("persist.sys.vowifi.whitelist", "false");
                    }
                    TelephonyFragment.this.rebootDevice("setVowifiWhiteListAllowedSwitch");
                    return;
                }
                if (key.equals(TelephonyFragment.KEY_WHITELIST_INGORE_SWITCH)) {
                    if (TelephonyFragment.this.mDualVolteChecked) {
                        SystemPropertiesProxy.set(TelephonyFragment.DSDA_CONTROL, "true");
                    } else {
                        SystemPropertiesProxy.set(TelephonyFragment.DSDA_CONTROL, "false");
                    }
                    TelephonyFragment.this.mWhiteListIgnoreSwitch.setChecked(TelephonyFragment.this.mDualVolteChecked);
                    TelephonyFragment.this.rebootDevice("switch_orange");
                    return;
                }
                if (key.equals(TelephonyFragment.KEY_DISABLE_EVDO)) {
                    TelephonyFragment.this.rebootDevice("evdoDisableSwitch");
                }
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.18
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                TelephonyFragment.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.TelephonyFragment.18.1
                    @Override // java.lang.Runnable
                    public void run() throws IllegalAccessException, InvocationTargetException {
                        if (key.equals(TelephonyFragment.KEY_VOWIFI_LAB_SIM_ALLOWED_SWITCH)) {
                            if (TelephonyFragment.this.mVowifiLabSimAllowedChecked) {
                                TelephonyFragment.this.mVowifiLabSimAllowedSwitch.setChecked(false);
                                TelephonyFragment.this.mVowifiLabSimAllowedChecked = false;
                                TelephonyFragment.this.mVowifiLabSimAllowedSwitch.setSummary("Close");
                                return;
                            } else {
                                TelephonyFragment.this.mVowifiLabSimAllowedSwitch.setChecked(true);
                                TelephonyFragment.this.mVowifiLabSimAllowedChecked = true;
                                TelephonyFragment.this.mVowifiLabSimAllowedSwitch.setSummary("Open");
                                return;
                            }
                        }
                        if (key.equals(TelephonyFragment.KEY_VOWIFI_WHITE_LIST_ALLOWED_SWITCH)) {
                            if (TelephonyFragment.this.mVowifiWhiteListAllowedChecked) {
                                TelephonyFragment.this.mVowifiWhiteListAllowedSwitch.setChecked(false);
                                TelephonyFragment.this.mVowifiWhiteListAllowedChecked = false;
                                TelephonyFragment.this.mVowifiWhiteListAllowedSwitch.setSummary("Close");
                                return;
                            } else {
                                TelephonyFragment.this.mVowifiWhiteListAllowedSwitch.setChecked(true);
                                TelephonyFragment.this.mVowifiWhiteListAllowedChecked = true;
                                TelephonyFragment.this.mVowifiWhiteListAllowedSwitch.setSummary("Open");
                                return;
                            }
                        }
                        if (key.equals(TelephonyFragment.KEY_WHITELIST_INGORE_SWITCH)) {
                            if (!TelephonyFragment.this.mDualVolteChecked) {
                                SystemPropertiesProxy.set(TelephonyFragment.DSDA_CONTROL, "true");
                                TelephonyFragment.this.mWhiteListIgnoreSwitch.setChecked(true);
                                TelephonyFragment.this.mDualVolteChecked = true;
                                TelephonyFragment.this.mWhiteListIgnoreSwitch.setSummary("Open");
                                return;
                            }
                            SystemPropertiesProxy.set(TelephonyFragment.DSDA_CONTROL, "false");
                            TelephonyFragment.this.mWhiteListIgnoreSwitch.setChecked(false);
                            TelephonyFragment.this.mDualVolteChecked = false;
                            TelephonyFragment.this.mWhiteListIgnoreSwitch.setSummary("close");
                        }
                    }
                });
            }
        }).create();
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebootDevice(String reasonStr) {
        PowerManager pm = (PowerManager) this.mContext.getSystemService("power");
        pm.reboot(reasonStr);
    }

    private boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "package not found: " + packagename);
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        return true;
    }
}
