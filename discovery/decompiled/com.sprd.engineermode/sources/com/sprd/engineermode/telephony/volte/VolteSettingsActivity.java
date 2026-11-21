package com.sprd.engineermode.telephony.volte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.common.Const;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes4.dex */
public class VolteSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int EDITADRESS_LENGTH_MAX = 91;
    private static final int GET_FORCE_MT_SESSION_TIMER = 57;
    private static final int GET_SESSION_TIMER = 55;
    private static final String KEY_CHANNEL_AWARE_BANDWIDTH = "channel_aware_bandwidth";
    private static final String KEY_CHANNEL_AWARE_MODE = "channel_aware_mode";
    private static final String KEY_CUSTOM_IMPI_SETTING = "volte_impi_impu_sub_setting";
    private static final String KEY_DEFAULT_BITRATE = "default_encode_bitrate";
    private static final String KEY_DOMAIN_SETTING = "domain_setting";
    private static final String KEY_EVS_DEFAULT_BITRATE = "evs_io_default_bitrate";
    private static final String KEY_EVS_RATE_SET = "evs_rate_set";
    private static final String KEY_EXPIRES_TIMER_SETTING = "volte_expires_time_setting";
    private static final String KEY_FORCE_MT_SESSION_TIMER = "force_mt_session_timer";
    private static final String KEY_IMPI_SETTING = "impi_setting";
    private static final String KEY_IMPU_SETTING = "impu_setting";
    private static final String KEY_IP_SETTING = "volte_ip_setting";
    private static final String KEY_MAX_BANDWIDTH = "max_bandwidth";
    private static final String KEY_MAX_BITRATE = "max_bitrate";
    private static final String KEY_MIN_BANDWIDTH = "min_bandwidth";
    private static final String KEY_MIN_BITRATE = "min_bitrate";
    private static final String KEY_MUT_LENGTH = "volte_mut_length";
    private static final String KEY_NB_DEFAULT_BITRATE = "nb_default_bitrate";
    private static final String KEY_NB_RATE_SET = "nb_rate_set";
    private static final String KEY_PCSCF_SET = "pcscf_set";
    private static final String KEY_PQOS_TIMER_SETTING = "volte_pqos_time_setting";
    private static final String KEY_PRECONDITION_SETTING = "volte_precondition_setting_switch";
    private static final String KEY_SESSION_TIMER = "session_timer";
    private static final String KEY_SIGCOMP_ALGORITHM = "volte_sigcomp_algorithm";
    private static final String KEY_SIGCOMP_SETTING = "volte_sigcomp_setting_switch";
    private static final String KEY_SUBSCRIBE_SETTING = "volte_subscribe_setting_switch";
    private static final String KEY_TCALL_TIMER_SETTING = "volte_tcall_time_setting";
    private static final String KEY_TQOS_TIMER_SETTING = "volte_tqos_time_setting";
    private static final String KEY_TREG_TIMER_SETTING = "volte_treg_time_setting";
    private static final String KEY_VOICE_CODE = "volte_voice_code";
    private static final String KEY_VOICE_CODE_SPEED = "volte_voice_code_speed";
    private static final String KEY_VOICE_CODE_WAY = "volte_voice_code_way";
    private static final String KEY_VOICE_NB_CODE_SPEED = "volte_voice_nb_code_speed";
    private static final String KEY_VOICE_WB_CODE_SPEED = "volte_voice_nb_code_speed";
    private static final String KEY_VOLTE_CODE_WAY = "volte_code_way";
    private static final String KEY_WB_DEFAULT_BITRATE = "wb_default_bitrate";
    private static final String KEY_WB_RATE_SET = "wb_rate_set";
    private static final int MSG_GET_CHANNEL_AWARE_BANDWIDTH = 53;
    private static final int MSG_GET_CHANNEL_AWARE_MODE = 51;
    private static final int MSG_GET_DEFAULT_BITRATE = 37;
    private static final int MSG_GET_EVS_DEFAULT_BITRATE = 39;
    private static final int MSG_GET_EVS_RATE_SET = 45;
    private static final int MSG_GET_EXPIRES_TIME = 12;
    private static final int MSG_GET_IP_STATE = 3;
    private static final int MSG_GET_MAX_BANDWIDTH = 31;
    private static final int MSG_GET_MAX_BITRATE = 35;
    private static final int MSG_GET_MIN_BANDWIDTH = 29;
    private static final int MSG_GET_MIN_BITRATE = 33;
    private static final int MSG_GET_MUT_LENGTH = 5;
    private static final int MSG_GET_NB_DEFAULT_BITRATE = 43;
    private static final int MSG_GET_NB_RATE_SET = 49;
    private static final int MSG_GET_PQOS_TIMER = 92;
    private static final int MSG_GET_PRECONDITION_STATE = 17;
    private static final int MSG_GET_SIGCOMP_STATE = 9;
    private static final int MSG_GET_SUBSCRIB_STATE = 7;
    private static final int MSG_GET_TCALL_TIMER = 21;
    private static final int MSG_GET_TQOS_TIMER = 19;
    private static final int MSG_GET_TREG_TIMER = 23;
    private static final int MSG_GET_VOICE_CODE_TYPE = 27;
    private static final int MSG_GET_VOICE_CODE_WAY = 14;
    private static final int MSG_GET_WB_DEFAULT_BITRATE = 41;
    private static final int MSG_GET_WB_RATE_SET = 47;
    private static final int MSG_SET_CHANNEL_AWARE_BANDWIDTH = 54;
    private static final int MSG_SET_CHANNEL_AWARE_MODE = 52;
    private static final int MSG_SET_DEFAULT_BITRATE = 38;
    private static final int MSG_SET_DOMAIN = 2;
    private static final int MSG_SET_EVS_DEFAULT_BITRATE = 40;
    private static final int MSG_SET_EVS_RATE_SET = 46;
    private static final int MSG_SET_EXPIRES_TIME = 13;
    private static final int MSG_SET_IMPI = 0;
    private static final int MSG_SET_IMPU = 1;
    private static final int MSG_SET_IP_STATE = 4;
    private static final int MSG_SET_MAX_BANDWIDTH = 32;
    private static final int MSG_SET_MAX_BITRATE = 36;
    private static final int MSG_SET_MIN_BANDWIDTH = 30;
    private static final int MSG_SET_MIN_BITRATE = 34;
    private static final int MSG_SET_MUT_LENGTH = 6;
    private static final int MSG_SET_NB_DEFAULT_BITRATE = 44;
    private static final int MSG_SET_NB_RATE_SET = 50;
    private static final int MSG_SET_PQOS_TIMER = 93;
    private static final int MSG_SET_PRECONDITION_STATE = 18;
    private static final int MSG_SET_SIGCOMP_INDEX = 11;
    private static final int MSG_SET_SIGCOMP_STATE = 10;
    private static final int MSG_SET_SUBSCRIB_STATE = 8;
    private static final int MSG_SET_TCALL_TIMER = 22;
    private static final int MSG_SET_TQOS_TIMER = 20;
    private static final int MSG_SET_TREG_TIMER = 24;
    private static final int MSG_SET_VOICE_CODE_SPEED = 16;
    private static final int MSG_SET_VOICE_CODE_TYPE = 28;
    private static final int MSG_SET_VOICE_CODE_WAY = 15;
    private static final int MSG_SET_WB_DEFAULT_BITRATE = 42;
    private static final int MSG_SET_WB_RATE_SET = 48;
    private static final int SET_FORCE_MT_SESSION_TIMER = 58;
    private static final int SET_SESSION_TIMER = 56;
    private static final String TAG = "VolteSettingsActivity";
    private AlertDialog mAlertDialog;
    private ListPreference mChannelAwareBD;
    private ListPreference mChannelAwareMode;
    private ListPreference mDefaultBitRate;
    private ListPreference mEvsDefaultBR;
    private Preference mEvsRateSet;
    private EditTextPreference mExpiresTimer;
    private EditTextPreference mForceMtSessionTimer;
    private PreferenceGroup mIMPISettingScreen;
    private ListPreference mIPSetting;
    private EditTextPreference mMUTLength;
    private ListPreference mMaxBandWidth;
    private ListPreference mMaxBitRate;
    private ListPreference mMinBandWidth;
    private ListPreference mMinBitRate;
    private ListPreference mNbDefaultBR;
    private Preference mNbRateSet;
    private TwoStatePreference mPcscfSwitch;
    private EditTextPreference mPqosTimer;
    private TwoStatePreference mPreconditionSwitch;
    private EditTextPreference mSessionTimer;
    private SharedPreferences mSharePref;
    private ListPreference mSigcompSetting;
    private TwoStatePreference mSigcompSwitch;
    private TwoStatePreference mSubscribeSwitch;
    private EditTextPreference mTcallTimer;
    private EditTextPreference mTqosTimer;
    private EditTextPreference mTregTimer;
    private ListPreference mVoiceCodeSpeed;
    private ListPreference mVoiceCodeWay;
    private PreferenceScreen mVolteCode;
    private VolteHandler mVolteHandler;
    private ArrayList<CheckBox> mVolteViewList;
    private Preference mVolteVoiceCode;
    private ListPreference mWbDefaultBR;
    private Preference mWbRateSet;
    private int mPhoneCount = 0;
    private String mResp = null;
    private int mPhoneId = 0;
    private Context mContext = null;
    private String mVoiceWayIndex = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Const.isSupportEVS()) {
            addPreferencesFromResource(R.xml.pref_volte_setting_evs);
        } else {
            addPreferencesFromResource(R.xml.pref_volte_setting);
        }
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mContext = this;
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mSharePref.registerOnSharedPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVolteHandler = new VolteHandler(ht.getLooper());
        this.mIPSetting = (ListPreference) findPreference(KEY_IP_SETTING);
        this.mIPSetting.setOnPreferenceChangeListener(this);
        this.mMUTLength = (EditTextPreference) findPreference(KEY_MUT_LENGTH);
        this.mMUTLength.setOnPreferenceChangeListener(this);
        this.mSubscribeSwitch = (TwoStatePreference) findPreference(KEY_SUBSCRIBE_SETTING);
        this.mSubscribeSwitch.setOnPreferenceChangeListener(this);
        this.mSigcompSwitch = (TwoStatePreference) findPreference(KEY_SIGCOMP_SETTING);
        this.mSigcompSwitch.setOnPreferenceChangeListener(this);
        this.mSigcompSetting = (ListPreference) findPreference(KEY_SIGCOMP_ALGORITHM);
        this.mSigcompSetting.setOnPreferenceChangeListener(this);
        this.mSigcompSetting.setEnabled(false);
        this.mExpiresTimer = (EditTextPreference) findPreference(KEY_EXPIRES_TIMER_SETTING);
        this.mExpiresTimer.setOnPreferenceChangeListener(this);
        this.mPreconditionSwitch = (TwoStatePreference) findPreference(KEY_PRECONDITION_SETTING);
        this.mPreconditionSwitch.setOnPreferenceChangeListener(this);
        this.mTqosTimer = (EditTextPreference) findPreference(KEY_TQOS_TIMER_SETTING);
        this.mTqosTimer.setOnPreferenceChangeListener(this);
        this.mTcallTimer = (EditTextPreference) findPreference(KEY_TCALL_TIMER_SETTING);
        this.mTcallTimer.setOnPreferenceChangeListener(this);
        this.mTregTimer = (EditTextPreference) findPreference(KEY_TREG_TIMER_SETTING);
        this.mTregTimer.setOnPreferenceChangeListener(this);
        this.mPqosTimer = (EditTextPreference) findPreference(KEY_PQOS_TIMER_SETTING);
        this.mPqosTimer.setOnPreferenceChangeListener(this);
        this.mPcscfSwitch = (TwoStatePreference) findPreference(KEY_PCSCF_SET);
        this.mPcscfSwitch.setOnPreferenceChangeListener(this);
        this.mSessionTimer = (EditTextPreference) findPreference(KEY_SESSION_TIMER);
        this.mSessionTimer.setOnPreferenceChangeListener(this);
        this.mForceMtSessionTimer = (EditTextPreference) findPreference(KEY_FORCE_MT_SESSION_TIMER);
        this.mForceMtSessionTimer.setOnPreferenceChangeListener(this);
        if (Const.isSupportEVS()) {
            this.mVolteCode = (PreferenceScreen) findPreference(KEY_VOLTE_CODE_WAY);
            this.mVolteCode.setOnPreferenceClickListener(this);
            this.mVolteVoiceCode = findPreference(KEY_VOICE_CODE);
            this.mVolteVoiceCode.setOnPreferenceClickListener(this);
            this.mMinBandWidth = (ListPreference) findPreference(KEY_MIN_BANDWIDTH);
            this.mMinBandWidth.setOnPreferenceChangeListener(this);
            this.mMaxBandWidth = (ListPreference) findPreference(KEY_MAX_BANDWIDTH);
            this.mMaxBandWidth.setOnPreferenceChangeListener(this);
            this.mMinBitRate = (ListPreference) findPreference(KEY_MIN_BITRATE);
            this.mMinBitRate.setOnPreferenceChangeListener(this);
            this.mMaxBitRate = (ListPreference) findPreference(KEY_MAX_BITRATE);
            this.mMaxBitRate.setOnPreferenceChangeListener(this);
            this.mDefaultBitRate = (ListPreference) findPreference(KEY_DEFAULT_BITRATE);
            this.mDefaultBitRate.setOnPreferenceChangeListener(this);
            this.mEvsDefaultBR = (ListPreference) findPreference(KEY_EVS_DEFAULT_BITRATE);
            this.mEvsDefaultBR.setOnPreferenceChangeListener(this);
            this.mWbDefaultBR = (ListPreference) findPreference(KEY_WB_DEFAULT_BITRATE);
            this.mWbDefaultBR.setOnPreferenceChangeListener(this);
            this.mNbDefaultBR = (ListPreference) findPreference(KEY_NB_DEFAULT_BITRATE);
            this.mNbDefaultBR.setOnPreferenceChangeListener(this);
            this.mEvsRateSet = findPreference(KEY_EVS_RATE_SET);
            this.mEvsRateSet.setOnPreferenceClickListener(this);
            this.mWbRateSet = findPreference(KEY_WB_RATE_SET);
            this.mWbRateSet.setOnPreferenceClickListener(this);
            this.mNbRateSet = findPreference(KEY_NB_RATE_SET);
            this.mNbRateSet.setOnPreferenceClickListener(this);
            this.mChannelAwareMode = (ListPreference) findPreference(KEY_CHANNEL_AWARE_MODE);
            this.mChannelAwareMode.setOnPreferenceChangeListener(this);
            this.mChannelAwareBD = (ListPreference) findPreference(KEY_CHANNEL_AWARE_BANDWIDTH);
            this.mChannelAwareBD.setOnPreferenceChangeListener(this);
        } else {
            this.mVoiceCodeWay = (ListPreference) findPreference(KEY_VOICE_CODE_WAY);
            this.mVoiceCodeWay.setOnPreferenceChangeListener(this);
            this.mVoiceCodeSpeed = (ListPreference) findPreference(KEY_VOICE_CODE_SPEED);
            this.mVoiceCodeSpeed.setOnPreferenceChangeListener(this);
        }
        this.mIMPISettingScreen = (PreferenceGroup) findPreference(KEY_CUSTOM_IMPI_SETTING);
        for (int i = 0; i < this.mPhoneCount; i++) {
            PreferenceCategory prefCategory = new PreferenceCategory(this);
            prefCategory.setTitle("SIM" + i);
            this.mIMPISettingScreen.addPreference(prefCategory);
            EditTextPreference impisetting = new EditTextPreference(this);
            impisetting.setKey(KEY_IMPI_SETTING + i);
            impisetting.setTitle("IMPI Setting");
            impisetting.setDialogTitle("IMPI Setting");
            impisetting.setOnPreferenceChangeListener(this);
            if (this.mSharePref.getString(KEY_IMPI_SETTING + i, null) == null) {
                impisetting.setSummary(R.string.input);
            } else {
                impisetting.setSummary(this.mSharePref.getString(KEY_IMPI_SETTING + i, null));
            }
            this.mIMPISettingScreen.addPreference(impisetting);
            EditTextPreference impusetting = new EditTextPreference(this);
            impusetting.setKey(KEY_IMPU_SETTING + i);
            impusetting.setTitle("IMPU Setting");
            impusetting.setOnPreferenceChangeListener(this);
            if (this.mSharePref.getString(KEY_IMPU_SETTING + i, null) == null) {
                impusetting.setSummary(R.string.input);
            } else {
                impusetting.setSummary(this.mSharePref.getString(KEY_IMPU_SETTING + i, null));
            }
            this.mIMPISettingScreen.addPreference(impusetting);
            EditTextPreference domain = new EditTextPreference(this);
            domain.setKey(KEY_DOMAIN_SETTING + i);
            domain.setTitle("Domain Setting");
            domain.setOnPreferenceChangeListener(this);
            if (this.mSharePref.getString(KEY_DOMAIN_SETTING + i, null) == null) {
                domain.setSummary(R.string.input);
            } else {
                domain.setSummary(this.mSharePref.getString(KEY_DOMAIN_SETTING + i, null));
            }
            this.mIMPISettingScreen.addPreference(domain);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        ListPreference listPreference = this.mIPSetting;
        if (listPreference != null && listPreference.isEnabled()) {
            Message getIPState = this.mVolteHandler.obtainMessage(3);
            this.mVolteHandler.sendMessage(getIPState);
        }
        EditTextPreference editTextPreference = this.mMUTLength;
        if (editTextPreference != null && editTextPreference.isEnabled()) {
            Message getMutLength = this.mVolteHandler.obtainMessage(5);
            this.mVolteHandler.sendMessage(getMutLength);
        }
        TwoStatePreference twoStatePreference = this.mSubscribeSwitch;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            Message getSubscribeState = this.mVolteHandler.obtainMessage(7);
            this.mVolteHandler.sendMessage(getSubscribeState);
        }
        TwoStatePreference twoStatePreference2 = this.mSigcompSwitch;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message getSigcompSwitch = this.mVolteHandler.obtainMessage(9);
            this.mVolteHandler.sendMessage(getSigcompSwitch);
        }
        EditTextPreference editTextPreference2 = this.mExpiresTimer;
        if (editTextPreference2 != null && editTextPreference2.isEnabled()) {
            Message getExpirestime = this.mVolteHandler.obtainMessage(12);
            this.mVolteHandler.sendMessage(getExpirestime);
        }
        ListPreference listPreference2 = this.mVoiceCodeWay;
        if (listPreference2 != null && listPreference2.isEnabled()) {
            Message getVoiceCodeWay = this.mVolteHandler.obtainMessage(14);
            this.mVolteHandler.sendMessage(getVoiceCodeWay);
        }
        TwoStatePreference twoStatePreference3 = this.mPreconditionSwitch;
        if (twoStatePreference3 != null && twoStatePreference3.isEnabled()) {
            Message getPrecondition = this.mVolteHandler.obtainMessage(17);
            this.mVolteHandler.sendMessage(getPrecondition);
        }
        EditTextPreference editTextPreference3 = this.mTqosTimer;
        if (editTextPreference3 != null && editTextPreference3.isEnabled()) {
            Message getTqosTimer = this.mVolteHandler.obtainMessage(19);
            this.mVolteHandler.sendMessage(getTqosTimer);
        }
        EditTextPreference editTextPreference4 = this.mTcallTimer;
        if (editTextPreference4 != null && editTextPreference4.isEnabled()) {
            Message getTcallTimer = this.mVolteHandler.obtainMessage(21);
            this.mVolteHandler.sendMessage(getTcallTimer);
        }
        EditTextPreference editTextPreference5 = this.mTregTimer;
        if (editTextPreference5 != null && editTextPreference5.isEnabled()) {
            Message getTregTimer = this.mVolteHandler.obtainMessage(23);
            this.mVolteHandler.sendMessage(getTregTimer);
        }
        EditTextPreference editTextPreference6 = this.mPqosTimer;
        if (editTextPreference6 != null && editTextPreference6.isEnabled()) {
            Message getPqosTimer = this.mVolteHandler.obtainMessage(92);
            this.mVolteHandler.sendMessage(getPqosTimer);
        }
        if (this.mPcscfSwitch != null) {
            String pcscfAddress = SystemPropertiesProxy.get("persist.vendor.sys.volte.pcscf");
            Log.d(TAG, "onStart pcscfAddress is: " + pcscfAddress);
            if ("".equals(pcscfAddress)) {
                this.mPcscfSwitch.setChecked(false);
                this.mPcscfSwitch.setSummary(getString(R.string.pcscf_dynamic));
            } else {
                this.mPcscfSwitch.setChecked(true);
                this.mPcscfSwitch.setSummary(getString(R.string.pcscf_static) + ": " + pcscfAddress.trim());
            }
        }
        ListPreference listPreference3 = this.mMinBandWidth;
        if (listPreference3 != null && listPreference3.isEnabled()) {
            Message getMinBandWidth = this.mVolteHandler.obtainMessage(29);
            this.mVolteHandler.sendMessage(getMinBandWidth);
        }
        ListPreference listPreference4 = this.mMaxBandWidth;
        if (listPreference4 != null && listPreference4.isEnabled()) {
            Message getMaxBandWidth = this.mVolteHandler.obtainMessage(31);
            this.mVolteHandler.sendMessage(getMaxBandWidth);
        }
        ListPreference listPreference5 = this.mMinBitRate;
        if (listPreference5 != null && listPreference5.isEnabled()) {
            Message getMinBitRate = this.mVolteHandler.obtainMessage(33);
            this.mVolteHandler.sendMessage(getMinBitRate);
        }
        ListPreference listPreference6 = this.mMaxBitRate;
        if (listPreference6 != null && listPreference6.isEnabled()) {
            Message getMaxBitRate = this.mVolteHandler.obtainMessage(35);
            this.mVolteHandler.sendMessage(getMaxBitRate);
        }
        ListPreference listPreference7 = this.mDefaultBitRate;
        if (listPreference7 != null && listPreference7.isEnabled()) {
            Message getDefaultBitRate = this.mVolteHandler.obtainMessage(37);
            this.mVolteHandler.sendMessage(getDefaultBitRate);
        }
        ListPreference listPreference8 = this.mEvsDefaultBR;
        if (listPreference8 != null && listPreference8.isEnabled()) {
            Message getEvsDefaultBr = this.mVolteHandler.obtainMessage(39);
            this.mVolteHandler.sendMessage(getEvsDefaultBr);
        }
        ListPreference listPreference9 = this.mWbDefaultBR;
        if (listPreference9 != null && listPreference9.isEnabled()) {
            Message getWbDefaultBr = this.mVolteHandler.obtainMessage(41);
            this.mVolteHandler.sendMessage(getWbDefaultBr);
        }
        ListPreference listPreference10 = this.mNbDefaultBR;
        if (listPreference10 != null && listPreference10.isEnabled()) {
            Message getNbDefaultBr = this.mVolteHandler.obtainMessage(43);
            this.mVolteHandler.sendMessage(getNbDefaultBr);
        }
        ListPreference listPreference11 = this.mChannelAwareMode;
        if (listPreference11 != null && listPreference11.isEnabled()) {
            Message getAwareMode = this.mVolteHandler.obtainMessage(51);
            this.mVolteHandler.sendMessage(getAwareMode);
        }
        ListPreference listPreference12 = this.mChannelAwareBD;
        if (listPreference12 != null && listPreference12.isEnabled()) {
            Message getAwareBd = this.mVolteHandler.obtainMessage(53);
            this.mVolteHandler.sendMessage(getAwareBd);
        }
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.mSessionTimer != null) {
            Message getTimer = this.mVolteHandler.obtainMessage(55);
            this.mVolteHandler.sendMessage(getTimer);
        }
        if (this.mForceMtSessionTimer != null) {
            Message getTimer2 = this.mVolteHandler.obtainMessage(57);
            this.mVolteHandler.sendMessage(getTimer2);
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        VolteHandler volteHandler = this.mVolteHandler;
        if (volteHandler != null) {
            volteHandler.getLooper().quit();
            Log.d(TAG, "HandlerThread has quit");
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editDialog(Context context) {
        final EditText inputServer = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.pcscf_static_input));
        builder.setView(inputServer);
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.alertdialog_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$60fTuYK9M5Nd1nEXzQjA7jFXGfI
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$editDialog$0$VolteSettingsActivity(dialogInterface, i);
            }
        });
        builder.setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$waeD0wrG8wLc6glc0MnsV7KIOm0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws IllegalAccessException, InvocationTargetException {
                this.f$0.lambda$editDialog$1$VolteSettingsActivity(inputServer, dialogInterface, i);
            }
        });
        builder.show();
    }

    public /* synthetic */ void lambda$editDialog$0$VolteSettingsActivity(DialogInterface dialog, int which) {
        this.mPcscfSwitch.setChecked(false);
    }

    public /* synthetic */ void lambda$editDialog$1$VolteSettingsActivity(EditText inputServer, DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
        String editAdress = inputServer.getText().toString();
        Log.d(TAG, "Edit input: " + editAdress);
        if (editAdress.length() != 0) {
            if (editAdress.getBytes(StandardCharsets.UTF_8).length > 91) {
                Toast.makeText(this.mContext, "The length of the input value can not exceed 91", 0).show();
                this.mPcscfSwitch.setChecked(false);
                return;
            } else {
                SystemPropertiesProxy.set("persist.vendor.sys.volte.pcscf", editAdress);
                PowerManager pm = (PowerManager) getSystemService("power");
                pm.reboot("pcscfset");
                return;
            }
        }
        this.mPcscfSwitch.setChecked(false);
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        String prefkey = pref.getKey();
        if (prefkey.equals(KEY_IP_SETTING)) {
            Message setIPState = this.mVolteHandler.obtainMessage(4, newValue);
            this.mVolteHandler.sendMessage(setIPState);
            return false;
        }
        if (prefkey.equals(KEY_MUT_LENGTH)) {
            Message setMutLength = this.mVolteHandler.obtainMessage(6, newValue);
            this.mVolteHandler.sendMessage(setMutLength);
            return false;
        }
        if (prefkey.equals(KEY_SUBSCRIBE_SETTING)) {
            if (this.mSubscribeSwitch.isChecked()) {
                Message setSubscribeClose = this.mVolteHandler.obtainMessage(8, "0");
                this.mVolteHandler.sendMessage(setSubscribeClose);
            } else {
                Message setSubscribeOpen = this.mVolteHandler.obtainMessage(8, "1");
                this.mVolteHandler.sendMessage(setSubscribeOpen);
            }
            return false;
        }
        if (prefkey.equals(KEY_SIGCOMP_SETTING)) {
            if (this.mSigcompSwitch.isChecked()) {
                Message setSigcompClose = this.mVolteHandler.obtainMessage(10, "0");
                this.mVolteHandler.sendMessage(setSigcompClose);
            } else {
                Message setSigcompOpen = this.mVolteHandler.obtainMessage(10, "1");
                this.mVolteHandler.sendMessage(setSigcompOpen);
            }
            return false;
        }
        if (prefkey.equals(KEY_SIGCOMP_ALGORITHM)) {
            Message setSigcomp = this.mVolteHandler.obtainMessage(11, newValue);
            this.mVolteHandler.sendMessage(setSigcomp);
            return false;
        }
        if (prefkey.equals(KEY_EXPIRES_TIMER_SETTING)) {
            Message setExpirestime = this.mVolteHandler.obtainMessage(13, newValue);
            this.mVolteHandler.sendMessage(setExpirestime);
            return false;
        }
        if (prefkey.equals(KEY_VOICE_CODE_WAY)) {
            Message setCodeWay = this.mVolteHandler.obtainMessage(15, newValue);
            this.mVolteHandler.sendMessage(setCodeWay);
            return false;
        }
        if (prefkey.equals(KEY_VOICE_CODE_SPEED)) {
            Message setCodeSpeed = this.mVolteHandler.obtainMessage(16, newValue);
            this.mVolteHandler.sendMessage(setCodeSpeed);
            return false;
        }
        if (prefkey.equals(KEY_PRECONDITION_SETTING)) {
            if (this.mPreconditionSwitch.isChecked()) {
                Message setPreClose = this.mVolteHandler.obtainMessage(18, "0");
                this.mVolteHandler.sendMessage(setPreClose);
            } else {
                Message setPreOpen = this.mVolteHandler.obtainMessage(18, "1");
                this.mVolteHandler.sendMessage(setPreOpen);
            }
            return false;
        }
        if (prefkey.equals(KEY_TQOS_TIMER_SETTING)) {
            Message setTqos = this.mVolteHandler.obtainMessage(20, newValue);
            this.mVolteHandler.sendMessage(setTqos);
            return false;
        }
        if (prefkey.equals(KEY_TCALL_TIMER_SETTING)) {
            Message setTcall = this.mVolteHandler.obtainMessage(22, newValue);
            this.mVolteHandler.sendMessage(setTcall);
            return false;
        }
        if (prefkey.equals(KEY_TREG_TIMER_SETTING)) {
            Message setTreg = this.mVolteHandler.obtainMessage(24, newValue);
            this.mVolteHandler.sendMessage(setTreg);
            return false;
        }
        if (prefkey.equals(KEY_PQOS_TIMER_SETTING)) {
            Message setPqos = this.mVolteHandler.obtainMessage(93, newValue);
            this.mVolteHandler.sendMessage(setPqos);
            return false;
        }
        if (pref == this.mSessionTimer) {
            if ("".equals(newValue.toString())) {
                Toast.makeText(this.mContext, "warning:empty value", 0).show();
                return false;
            }
            Message m = this.mVolteHandler.obtainMessage(56, newValue);
            this.mVolteHandler.sendMessage(m);
        }
        if (pref == this.mForceMtSessionTimer) {
            if ("".equals(newValue.toString())) {
                Toast.makeText(this.mContext, "warning:empty value", 0).show();
                return false;
            }
            Message m2 = this.mVolteHandler.obtainMessage(58, newValue);
            this.mVolteHandler.sendMessage(m2);
        }
        for (int i = 0; i < this.mPhoneCount; i++) {
            if (prefkey.equals(KEY_IMPI_SETTING + i)) {
                Message setIMPI = this.mVolteHandler.obtainMessage(0, i, 0, newValue);
                this.mVolteHandler.sendMessage(setIMPI);
                return false;
            }
            if (prefkey.equals(KEY_IMPU_SETTING + i)) {
                Message setIMPU = this.mVolteHandler.obtainMessage(1, i, 0, newValue);
                this.mVolteHandler.sendMessage(setIMPU);
                return false;
            }
            if (prefkey.equals(KEY_DOMAIN_SETTING + i)) {
                Message setDomain = this.mVolteHandler.obtainMessage(2, i, 0, newValue);
                this.mVolteHandler.sendMessage(setDomain);
                return false;
            }
        }
        if (prefkey.equals(KEY_PCSCF_SET)) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("P-CSCF").setCancelable(false).setMessage(getString(R.string.mode_switch_waring)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) throws IllegalAccessException, InvocationTargetException {
                    String currentSatus = SystemPropertiesProxy.get("persist.vendor.sys.volte.pcscf");
                    if (!"".equals(currentSatus)) {
                        SystemPropertiesProxy.set("persist.vendor.sys.volte.pcscf", "");
                        PowerManager pm = (PowerManager) VolteSettingsActivity.this.getSystemService("power");
                        pm.reboot("pcscfset");
                    } else {
                        VolteSettingsActivity volteSettingsActivity = VolteSettingsActivity.this;
                        volteSettingsActivity.editDialog(volteSettingsActivity);
                    }
                }
            }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    VolteSettingsActivity.this.mPcscfSwitch.setChecked(!"".equals(SystemPropertiesProxy.get("persist.vendor.sys.volte.pcscf")));
                }
            }).create();
            alertDialog.show();
            return true;
        }
        if (prefkey.equals(KEY_MIN_BANDWIDTH)) {
            Message setMinBandWidth = this.mVolteHandler.obtainMessage(30, newValue);
            this.mVolteHandler.sendMessage(setMinBandWidth);
            return false;
        }
        if (prefkey.equals(KEY_MAX_BANDWIDTH)) {
            Message setMaxBandWidth = this.mVolteHandler.obtainMessage(32, newValue);
            this.mVolteHandler.sendMessage(setMaxBandWidth);
            return false;
        }
        if (prefkey.equals(KEY_MIN_BITRATE)) {
            Message setMinBitRate = this.mVolteHandler.obtainMessage(34, newValue);
            this.mVolteHandler.sendMessage(setMinBitRate);
            return false;
        }
        if (prefkey.equals(KEY_MAX_BITRATE)) {
            Message setMaxBitRate = this.mVolteHandler.obtainMessage(36, newValue);
            this.mVolteHandler.sendMessage(setMaxBitRate);
            return false;
        }
        if (prefkey.equals(KEY_DEFAULT_BITRATE)) {
            Message setDefaultBitRate = this.mVolteHandler.obtainMessage(38, newValue);
            this.mVolteHandler.sendMessage(setDefaultBitRate);
            return false;
        }
        if (prefkey.equals(KEY_EVS_DEFAULT_BITRATE)) {
            Message setEvsDefaultBr = this.mVolteHandler.obtainMessage(40, newValue);
            this.mVolteHandler.sendMessage(setEvsDefaultBr);
            return false;
        }
        if (prefkey.equals(KEY_WB_DEFAULT_BITRATE)) {
            Message setWbDefaultBr = this.mVolteHandler.obtainMessage(42, newValue);
            this.mVolteHandler.sendMessage(setWbDefaultBr);
            return false;
        }
        if (prefkey.equals(KEY_NB_DEFAULT_BITRATE)) {
            Message setNbDefaultBr = this.mVolteHandler.obtainMessage(44, newValue);
            this.mVolteHandler.sendMessage(setNbDefaultBr);
            return false;
        }
        if (prefkey.equals(KEY_CHANNEL_AWARE_MODE)) {
            Message setAwreMode = this.mVolteHandler.obtainMessage(52, newValue);
            this.mVolteHandler.sendMessage(setAwreMode);
            return false;
        }
        if (!prefkey.equals(KEY_CHANNEL_AWARE_BANDWIDTH)) {
            return true;
        }
        Message setAwreBandWidth = this.mVolteHandler.obtainMessage(54, newValue);
        this.mVolteHandler.sendMessage(setAwreBandWidth);
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref == this.mVolteCode) {
            Message getVoiceCode = this.mVolteHandler.obtainMessage(27);
            this.mVolteHandler.sendMessage(getVoiceCode);
            return false;
        }
        if (pref == this.mVolteVoiceCode) {
            Message setVoiceCode = this.mVolteHandler.obtainMessage(28);
            this.mVolteHandler.sendMessage(setVoiceCode);
            return false;
        }
        if (pref == this.mEvsRateSet) {
            Message setEvsRateSet = this.mVolteHandler.obtainMessage(46);
            this.mVolteHandler.sendMessage(setEvsRateSet);
            return false;
        }
        if (pref == this.mWbRateSet) {
            Message setWbRateSet = this.mVolteHandler.obtainMessage(48);
            this.mVolteHandler.sendMessage(setWbRateSet);
            return false;
        }
        if (pref == this.mNbRateSet) {
            Message setNbRateSet = this.mVolteHandler.obtainMessage(50);
            this.mVolteHandler.sendMessage(setNbRateSet);
            return false;
        }
        return true;
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    class VolteHandler extends Handler {
        public VolteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String setVoiceSpeed;
            int i = msg.what;
            if (i == 92) {
                VolteSettingsActivity volteSettingsActivity = VolteSettingsActivity.this;
                volteSettingsActivity.mResp = volteSettingsActivity.sendAt(engconstents.ENG_AT_GET_PQOS, "atchannel0");
                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                    int index = VolteSettingsActivity.this.mResp.indexOf("25,0,");
                    if (index > 0) {
                        final String time = VolteSettingsActivity.this.mResp.substring(index + 5, index + 6);
                        VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.84
                            @Override // java.lang.Runnable
                            public void run() {
                                VolteSettingsActivity.this.mPqosTimer.setSummary(time + "s");
                            }
                        });
                        return;
                    }
                    return;
                }
                VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.85
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.d(VolteSettingsActivity.TAG, "GET_SESSION_TIMER Failed");
                        VolteSettingsActivity.this.mPqosTimer.setSummary(VolteSettingsActivity.this.mContext.getString(R.string.feature_not_support));
                        VolteSettingsActivity.this.mPqosTimer.setEnabled(false);
                    }
                });
                return;
            }
            if (i != 93) {
                switch (i) {
                    case 0:
                        VolteSettingsActivity.this.mPhoneId = msg.arg1;
                        final String impiValue = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity2 = VolteSettingsActivity.this;
                        volteSettingsActivity2.mResp = volteSettingsActivity2.sendAt(engconstents.ENG_VOLTE_IMPI + impiValue, "atchannel" + VolteSettingsActivity.this.mPhoneId);
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mIMPISettingScreen.findPreference(VolteSettingsActivity.KEY_IMPI_SETTING + VolteSettingsActivity.this.mPhoneId).setSummary(impiValue);
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_IMPI_SETTING + VolteSettingsActivity.this.mPhoneId, impiValue);
                                    editor.commit();
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Success", 0).show();
                                }
                            });
                        }
                        break;
                    case 1:
                        VolteSettingsActivity.this.mPhoneId = msg.arg1;
                        final String impuValue = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity3 = VolteSettingsActivity.this;
                        volteSettingsActivity3.mResp = volteSettingsActivity3.sendAt(engconstents.ENG_VOLTE_IMPU + impuValue, "atchannel" + VolteSettingsActivity.this.mPhoneId);
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mIMPISettingScreen.findPreference(VolteSettingsActivity.KEY_IMPU_SETTING + VolteSettingsActivity.this.mPhoneId).setSummary(impuValue);
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_IMPU_SETTING + VolteSettingsActivity.this.mPhoneId, impuValue);
                                    editor.commit();
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Success", 0).show();
                                }
                            });
                        }
                        break;
                    case 2:
                        VolteSettingsActivity.this.mPhoneId = msg.arg1;
                        final String domainValue = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity4 = VolteSettingsActivity.this;
                        volteSettingsActivity4.mResp = volteSettingsActivity4.sendAt(engconstents.ENG_VOLTE_DOMAIN + domainValue, "atchannel" + VolteSettingsActivity.this.mPhoneId);
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mIMPISettingScreen.findPreference(VolteSettingsActivity.KEY_DOMAIN_SETTING + VolteSettingsActivity.this.mPhoneId).setSummary(domainValue);
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_DOMAIN_SETTING + VolteSettingsActivity.this.mPhoneId, domainValue);
                                    editor.commit();
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Success", 0).show();
                                }
                            });
                        }
                        break;
                    case 3:
                        VolteSettingsActivity volteSettingsActivity5 = VolteSettingsActivity.this;
                        volteSettingsActivity5.mResp = volteSettingsActivity5.sendAt("AT+SPENGMDVOLTE=4,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity6 = VolteSettingsActivity.this;
                        final String stateValue = volteSettingsActivity6.anayResult(3, volteSettingsActivity6.mResp);
                        if (stateValue.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.4
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mIPSetting.setEnabled(false);
                                    VolteSettingsActivity.this.mIPSetting.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.5
                                @Override // java.lang.Runnable
                                public void run() {
                                    int val = Integer.valueOf(stateValue).intValue();
                                    if (val > 0) {
                                        val--;
                                    }
                                    VolteSettingsActivity.this.mIPSetting.setValueIndex(val);
                                    VolteSettingsActivity.this.mIPSetting.setSummary(VolteSettingsActivity.this.mIPSetting.getEntry());
                                }
                            });
                        }
                        break;
                    case 4:
                        String setIpState = (String) msg.obj;
                        final int setIpStateValue = Integer.valueOf(setIpState).intValue() + 1;
                        VolteSettingsActivity volteSettingsActivity7 = VolteSettingsActivity.this;
                        volteSettingsActivity7.mResp = volteSettingsActivity7.sendAt("AT+SPENGMDVOLTE=4,1," + setIpStateValue, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.6
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mIPSetting.setValueIndex(setIpStateValue - 1);
                                    VolteSettingsActivity.this.mIPSetting.setSummary(VolteSettingsActivity.this.mIPSetting.getEntry());
                                }
                            });
                        }
                        break;
                    case 5:
                        VolteSettingsActivity volteSettingsActivity8 = VolteSettingsActivity.this;
                        volteSettingsActivity8.mResp = volteSettingsActivity8.sendAt("AT+SPENGMDVOLTE=5,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity9 = VolteSettingsActivity.this;
                        final String lengthValue = volteSettingsActivity9.anayResult(5, volteSettingsActivity9.mResp);
                        if (lengthValue.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.7
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mMUTLength.setEnabled(false);
                                    VolteSettingsActivity.this.mMUTLength.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mMUTLength.setSummary(lengthValue);
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_MUT_LENGTH, lengthValue);
                                    editor.commit();
                                }
                            });
                        }
                        break;
                    case 6:
                        final String setMutLength = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity10 = VolteSettingsActivity.this;
                        volteSettingsActivity10.mResp = volteSettingsActivity10.sendAt("AT+SPENGMDVOLTE=5,1," + setMutLength, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.9
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_MUT_LENGTH, setMutLength);
                                    editor.commit();
                                    VolteSettingsActivity.this.mMUTLength.setSummary(setMutLength);
                                }
                            });
                        }
                        break;
                    case 7:
                        VolteSettingsActivity volteSettingsActivity11 = VolteSettingsActivity.this;
                        volteSettingsActivity11.mResp = volteSettingsActivity11.sendAt("AT+SPENGMDVOLTE=8,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity12 = VolteSettingsActivity.this;
                        final String getSubscrib = volteSettingsActivity12.anayResult(7, volteSettingsActivity12.mResp);
                        if (getSubscrib.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.10
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSubscribeSwitch.setEnabled(false);
                                    VolteSettingsActivity.this.mSubscribeSwitch.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.11
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSubscribeSwitch.setChecked(getSubscrib.contains("1"));
                                }
                            });
                        }
                        break;
                    case 8:
                        final String setSubscrib = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity13 = VolteSettingsActivity.this;
                        volteSettingsActivity13.mResp = volteSettingsActivity13.sendAt("AT+SPENGMDVOLTE=8,1," + setSubscrib, "atchannel0");
                        if (VolteSettingsActivity.this.mResp.contains("OK")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.12
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSubscribeSwitch.setChecked(setSubscrib.contains("1"));
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.13
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSubscribeSwitch.setChecked(!setSubscrib.contains("1"));
                                }
                            });
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        }
                        break;
                    case 9:
                        VolteSettingsActivity volteSettingsActivity14 = VolteSettingsActivity.this;
                        volteSettingsActivity14.mResp = volteSettingsActivity14.sendAt("AT+SPENGMDVOLTE=9,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity15 = VolteSettingsActivity.this;
                        final String getSigcomp = volteSettingsActivity15.anayResult(9, volteSettingsActivity15.mResp);
                        if (getSigcomp.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.14
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSigcompSwitch.setEnabled(false);
                                    VolteSettingsActivity.this.mSigcompSwitch.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.15
                                @Override // java.lang.Runnable
                                public void run() {
                                    boolean open = getSigcomp.split("\\,")[0].contains("1");
                                    VolteSettingsActivity.this.mSigcompSwitch.setChecked(open);
                                    if (open) {
                                        VolteSettingsActivity.this.mSigcompSetting.setEnabled(true);
                                        SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                        editor.putString(VolteSettingsActivity.KEY_SIGCOMP_ALGORITHM, getSigcomp.split("\\,")[1]);
                                        editor.commit();
                                        VolteSettingsActivity.this.mSigcompSetting.setValueIndex(Integer.valueOf(getSigcomp.split("\\,")[1].trim()).intValue());
                                        VolteSettingsActivity.this.mSigcompSetting.setSummary(VolteSettingsActivity.this.mSigcompSetting.getEntry());
                                        return;
                                    }
                                    VolteSettingsActivity.this.mSigcompSetting.setEnabled(false);
                                }
                            });
                        }
                        break;
                    case 10:
                        final String setSigcomp = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity16 = VolteSettingsActivity.this;
                        volteSettingsActivity16.mResp = volteSettingsActivity16.sendAt("AT+SPENGMDVOLTE=9,1," + setSigcomp, "atchannel0");
                        if (VolteSettingsActivity.this.mResp.contains("OK")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.16
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSigcompSwitch.setChecked(setSigcomp.contains("1"));
                                    VolteSettingsActivity.this.mSigcompSetting.setEnabled(true);
                                    if (setSigcomp.contains("0")) {
                                        VolteSettingsActivity.this.mSigcompSetting.setEnabled(false);
                                    }
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.17
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mSigcompSwitch.setChecked(!setSigcomp.contains("1"));
                                }
                            });
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        }
                        break;
                    case 11:
                        final String setSigcompIndex = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity17 = VolteSettingsActivity.this;
                        volteSettingsActivity17.mResp = volteSettingsActivity17.sendAt("AT+SPENGMDVOLTE=9,1,1," + setSigcompIndex, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.18
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_SIGCOMP_ALGORITHM, setSigcompIndex);
                                    editor.commit();
                                    VolteSettingsActivity.this.mSigcompSetting.setValueIndex(Integer.valueOf(setSigcompIndex).intValue());
                                    VolteSettingsActivity.this.mSigcompSetting.setSummary(VolteSettingsActivity.this.mSigcompSetting.getEntry());
                                }
                            });
                        }
                        break;
                    case 12:
                        VolteSettingsActivity volteSettingsActivity18 = VolteSettingsActivity.this;
                        volteSettingsActivity18.mResp = volteSettingsActivity18.sendAt("AT+SPENGMDVOLTE=11,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity19 = VolteSettingsActivity.this;
                        final String getExpiresTime = volteSettingsActivity19.anayResult(12, volteSettingsActivity19.mResp);
                        if (getExpiresTime.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.19
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mExpiresTimer.setEnabled(false);
                                    VolteSettingsActivity.this.mExpiresTimer.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.20
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mExpiresTimer.setEnabled(true);
                                    VolteSettingsActivity.this.mExpiresTimer.setSummary(getExpiresTime);
                                }
                            });
                        }
                        break;
                    case 13:
                        final String setExpires = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity20 = VolteSettingsActivity.this;
                        volteSettingsActivity20.mResp = volteSettingsActivity20.sendAt("AT+SPENGMDVOLTE=11,1," + setExpires, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.21
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_EXPIRES_TIMER_SETTING, setExpires);
                                    editor.commit();
                                    VolteSettingsActivity.this.mExpiresTimer.setSummary(setExpires);
                                }
                            });
                        }
                        break;
                    case 14:
                        VolteSettingsActivity volteSettingsActivity21 = VolteSettingsActivity.this;
                        volteSettingsActivity21.mResp = volteSettingsActivity21.sendAt("AT+SPENGMDVOLTE=12,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity22 = VolteSettingsActivity.this;
                        final String getVoiceCodeWay = volteSettingsActivity22.anayResult(14, volteSettingsActivity22.mResp);
                        if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.23
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mVoiceCodeWay.setEnabled(false);
                                    VolteSettingsActivity.this.mVoiceCodeWay.setSummary(R.string.feature_abnormal);
                                    VolteSettingsActivity.this.mVoiceCodeSpeed.setEnabled(false);
                                    VolteSettingsActivity.this.mVoiceCodeSpeed.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.22
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                                    editor.putString(VolteSettingsActivity.KEY_VOICE_CODE_WAY, getVoiceCodeWay.split("\\,")[1]);
                                    editor.commit();
                                    if (getVoiceCodeWay.split("\\,")[1].contains("1")) {
                                        VolteSettingsActivity.this.mVoiceCodeWay.setValueIndex(1);
                                        VolteSettingsActivity.this.mVoiceCodeWay.setSummary(VolteSettingsActivity.this.mVoiceCodeWay.getEntry());
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEntries(R.array.volte_voice_wb_code_speed);
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEntryValues(R.array.volte_voice_wb_code_speed_value);
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEnabled(true);
                                        int setWbValueIndex = Integer.valueOf(getVoiceCodeWay.split("\\,")[2].trim()).intValue();
                                        if (setWbValueIndex == 254) {
                                            VolteSettingsActivity.this.mVoiceCodeSpeed.setValueIndex(9);
                                        } else {
                                            VolteSettingsActivity.this.mVoiceCodeSpeed.setValueIndex(setWbValueIndex);
                                        }
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setSummary(VolteSettingsActivity.this.mVoiceCodeSpeed.getEntry());
                                        editor.putString("volte_voice_nb_code_speed", getVoiceCodeWay.split("\\,")[2]);
                                        editor.commit();
                                        return;
                                    }
                                    if (getVoiceCodeWay.split("\\,")[1].contains("0")) {
                                        VolteSettingsActivity.this.mVoiceCodeWay.setValueIndex(0);
                                        VolteSettingsActivity.this.mVoiceCodeWay.setSummary(VolteSettingsActivity.this.mVoiceCodeWay.getEntry());
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEntries(R.array.volte_voice_nb_code_speed);
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEntryValues(R.array.volte_voice_nb_code_speed_value);
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEnabled(true);
                                        int setNbValueIndex = Integer.valueOf(getVoiceCodeWay.split("\\,")[2].trim()).intValue();
                                        if (setNbValueIndex == 254) {
                                            VolteSettingsActivity.this.mVoiceCodeSpeed.setValueIndex(8);
                                        } else {
                                            VolteSettingsActivity.this.mVoiceCodeSpeed.setValueIndex(setNbValueIndex);
                                        }
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setSummary(VolteSettingsActivity.this.mVoiceCodeSpeed.getEntry());
                                        editor.putString("volte_voice_nb_code_speed", getVoiceCodeWay.split("\\,")[2]);
                                        editor.commit();
                                        return;
                                    }
                                    if (getVoiceCodeWay.split("\\,")[1].contains("3")) {
                                        VolteSettingsActivity.this.mVoiceCodeWay.setValueIndex(2);
                                        VolteSettingsActivity.this.mVoiceCodeWay.setSummary(VolteSettingsActivity.this.mVoiceCodeWay.getEntry());
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setSummary(VolteSettingsActivity.this.mVoiceCodeSpeed.getEntry());
                                        VolteSettingsActivity.this.mVoiceCodeSpeed.setEnabled(false);
                                    }
                                }
                            });
                        }
                        break;
                    case 15:
                        String setVoiceCodeWay = (String) msg.obj;
                        if (setVoiceCodeWay.equals("0")) {
                            VolteSettingsActivity volteSettingsActivity23 = VolteSettingsActivity.this;
                            volteSettingsActivity23.mResp = volteSettingsActivity23.sendAt("AT+SPENGMDVOLTE=12,1,0,254", "atchannel0");
                        } else if (setVoiceCodeWay.equals("1")) {
                            VolteSettingsActivity volteSettingsActivity24 = VolteSettingsActivity.this;
                            volteSettingsActivity24.mResp = volteSettingsActivity24.sendAt("AT+SPENGMDVOLTE=12,1,1,254", "atchannel0");
                        } else if (setVoiceCodeWay.equals("2")) {
                            VolteSettingsActivity volteSettingsActivity25 = VolteSettingsActivity.this;
                            volteSettingsActivity25.mResp = volteSettingsActivity25.sendAt("AT+SPENGMDVOLTE=12,1,3", "atchannel0");
                        }
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        }
                        Message getVoiceCodeWays = VolteSettingsActivity.this.mVolteHandler.obtainMessage(14);
                        VolteSettingsActivity.this.mVolteHandler.sendMessage(getVoiceCodeWays);
                        break;
                    case 16:
                        final String setVoiceCodeSpeed = (String) msg.obj;
                        Log.d(VolteSettingsActivity.TAG, "set Voice CodeSpeed: " + setVoiceCodeSpeed);
                        VolteSettingsActivity volteSettingsActivity26 = VolteSettingsActivity.this;
                        volteSettingsActivity26.mVoiceWayIndex = volteSettingsActivity26.mSharePref.getString(VolteSettingsActivity.KEY_VOICE_CODE_WAY, "0");
                        if (("0".equals(VolteSettingsActivity.this.mVoiceWayIndex) && "8".equals(setVoiceCodeSpeed)) || ("1".equals(VolteSettingsActivity.this.mVoiceWayIndex) && "9".equals(setVoiceCodeSpeed))) {
                            setVoiceSpeed = "254";
                        } else {
                            setVoiceSpeed = setVoiceCodeSpeed;
                        }
                        VolteSettingsActivity volteSettingsActivity27 = VolteSettingsActivity.this;
                        volteSettingsActivity27.mResp = volteSettingsActivity27.sendAt("AT+SPENGMDVOLTE=12,1," + VolteSettingsActivity.this.mVoiceWayIndex + "," + setVoiceSpeed, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.24
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mVoiceCodeSpeed.setValueIndex(Integer.valueOf(setVoiceCodeSpeed).intValue());
                                    VolteSettingsActivity.this.mVoiceCodeSpeed.setSummary(VolteSettingsActivity.this.mVoiceCodeSpeed.getEntry());
                                }
                            });
                            SharedPreferences.Editor editor = VolteSettingsActivity.this.mSharePref.edit();
                            if (VolteSettingsActivity.this.mVoiceWayIndex.contains("1")) {
                                editor.putString("volte_voice_nb_code_speed", setVoiceSpeed);
                            } else {
                                editor.putString("volte_voice_nb_code_speed", setVoiceSpeed);
                            }
                            editor.commit();
                        }
                        break;
                    case 17:
                        VolteSettingsActivity volteSettingsActivity28 = VolteSettingsActivity.this;
                        volteSettingsActivity28.mResp = volteSettingsActivity28.sendAt("AT+SPENGMDVOLTE=13,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity29 = VolteSettingsActivity.this;
                        final String getPreconditionStatue = volteSettingsActivity29.anayResult(17, volteSettingsActivity29.mResp);
                        if (getPreconditionStatue.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.71
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mPreconditionSwitch.setEnabled(false);
                                    VolteSettingsActivity.this.mPreconditionSwitch.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.72
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mPreconditionSwitch.setChecked(getPreconditionStatue.contains("1"));
                                }
                            });
                        }
                        break;
                    case 18:
                        final String setPreconditionStatue = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity30 = VolteSettingsActivity.this;
                        volteSettingsActivity30.mResp = volteSettingsActivity30.sendAt("AT+SPENGMDVOLTE=13,1," + setPreconditionStatue, "atchannel0");
                        if (VolteSettingsActivity.this.mResp.contains("OK")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.73
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mPreconditionSwitch.setChecked(setPreconditionStatue.contains("1"));
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.74
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mPreconditionSwitch.setChecked(!setPreconditionStatue.contains("1"));
                                }
                            });
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        }
                        break;
                    case 19:
                        VolteSettingsActivity volteSettingsActivity31 = VolteSettingsActivity.this;
                        volteSettingsActivity31.mResp = volteSettingsActivity31.sendAt("AT+SPENGMDVOLTE=14,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity32 = VolteSettingsActivity.this;
                        final String getTqosTimer = volteSettingsActivity32.anayResult(19, volteSettingsActivity32.mResp);
                        if (getTqosTimer.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.75
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTqosTimer.setEnabled(false);
                                    VolteSettingsActivity.this.mTqosTimer.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.76
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTqosTimer.setEnabled(true);
                                    VolteSettingsActivity.this.mTqosTimer.setSummary(getTqosTimer);
                                }
                            });
                        }
                        break;
                    case 20:
                        final String setTqosTimer = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity33 = VolteSettingsActivity.this;
                        volteSettingsActivity33.mResp = volteSettingsActivity33.sendAt("AT+SPENGMDVOLTE=14,1," + setTqosTimer, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.77
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor2 = VolteSettingsActivity.this.mSharePref.edit();
                                    editor2.putString(VolteSettingsActivity.KEY_TQOS_TIMER_SETTING, setTqosTimer);
                                    editor2.commit();
                                    VolteSettingsActivity.this.mTqosTimer.setSummary(setTqosTimer);
                                }
                            });
                        }
                        break;
                    case 21:
                        VolteSettingsActivity volteSettingsActivity34 = VolteSettingsActivity.this;
                        volteSettingsActivity34.mResp = volteSettingsActivity34.sendAt("AT+SPENGMDVOLTE=15,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity35 = VolteSettingsActivity.this;
                        final String getTcallTimer = volteSettingsActivity35.anayResult(21, volteSettingsActivity35.mResp);
                        if (getTcallTimer.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.78
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTcallTimer.setEnabled(false);
                                    VolteSettingsActivity.this.mTcallTimer.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.79
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTcallTimer.setEnabled(true);
                                    VolteSettingsActivity.this.mTcallTimer.setSummary(getTcallTimer);
                                }
                            });
                        }
                        break;
                    case 22:
                        final String setTcallTimer = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity36 = VolteSettingsActivity.this;
                        volteSettingsActivity36.mResp = volteSettingsActivity36.sendAt("AT+SPENGMDVOLTE=15,1," + setTcallTimer, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.80
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor2 = VolteSettingsActivity.this.mSharePref.edit();
                                    editor2.putString(VolteSettingsActivity.KEY_TCALL_TIMER_SETTING, setTcallTimer);
                                    editor2.commit();
                                    VolteSettingsActivity.this.mTcallTimer.setSummary(setTcallTimer);
                                }
                            });
                        }
                        break;
                    case 23:
                        VolteSettingsActivity volteSettingsActivity37 = VolteSettingsActivity.this;
                        volteSettingsActivity37.mResp = volteSettingsActivity37.sendAt("AT+SPENGMDVOLTE=16,0", "atchannel0");
                        VolteSettingsActivity volteSettingsActivity38 = VolteSettingsActivity.this;
                        final String getTrgeTimer = volteSettingsActivity38.anayResult(23, volteSettingsActivity38.mResp);
                        if (getTrgeTimer.contains("FAILED")) {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.81
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTregTimer.setEnabled(false);
                                    VolteSettingsActivity.this.mTregTimer.setSummary(R.string.feature_abnormal);
                                }
                            });
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.82
                                @Override // java.lang.Runnable
                                public void run() {
                                    VolteSettingsActivity.this.mTregTimer.setEnabled(true);
                                    VolteSettingsActivity.this.mTregTimer.setSummary(getTrgeTimer);
                                }
                            });
                        }
                        break;
                    case 24:
                        final String setTrgeTimer = (String) msg.obj;
                        VolteSettingsActivity volteSettingsActivity39 = VolteSettingsActivity.this;
                        volteSettingsActivity39.mResp = volteSettingsActivity39.sendAt("AT+SPENGMDVOLTE=16,1," + setTrgeTimer, "atchannel0");
                        if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                        } else {
                            VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.83
                                @Override // java.lang.Runnable
                                public void run() {
                                    SharedPreferences.Editor editor2 = VolteSettingsActivity.this.mSharePref.edit();
                                    editor2.putString(VolteSettingsActivity.KEY_TCALL_TIMER_SETTING, setTrgeTimer);
                                    editor2.commit();
                                    VolteSettingsActivity.this.mTregTimer.setSummary(setTrgeTimer);
                                }
                            });
                        }
                        break;
                    default:
                        switch (i) {
                            case 27:
                                VolteSettingsActivity volteSettingsActivity40 = VolteSettingsActivity.this;
                                volteSettingsActivity40.mResp = volteSettingsActivity40.sendAt(engconstents.ENG_AT_GET_EVS, "atchannel0");
                                VolteSettingsActivity volteSettingsActivity41 = VolteSettingsActivity.this;
                                String getVoiceCodeType = volteSettingsActivity41.anayResult(27, volteSettingsActivity41.mResp);
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$VolteHandler$s4OrO9G4hZIJ5uGYCZgYROuKelo
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.f$0.lambda$handleMessage$3$VolteSettingsActivity$VolteHandler();
                                        }
                                    });
                                } else {
                                    final int value = Integer.parseInt(getVoiceCodeType);
                                    final String[] arr = VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.volte_voice_code);
                                    final String summary = (String) IntStream.range(0, arr.length).filter(new IntPredicate() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$VolteHandler$2082DpF070_Oy4vYc_IdEBlxVps
                                        @Override // java.util.function.IntPredicate
                                        public final boolean test(int i2) {
                                            return VolteSettingsActivity.VolteHandler.lambda$handleMessage$0(value, i2);
                                        }
                                    }).mapToObj(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$VolteHandler$eIW-PJTyTT0uHXtvwC4iZVOgEeA
                                        @Override // java.util.function.IntFunction
                                        public final Object apply(int i2) {
                                            return VolteSettingsActivity.VolteHandler.lambda$handleMessage$1(arr, i2);
                                        }
                                    }).collect(Collectors.joining("/"));
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$VolteHandler$S3Bu29g9vk8XsnqvLtGLxMKr5kY
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            this.f$0.lambda$handleMessage$2$VolteSettingsActivity$VolteHandler(summary);
                                        }
                                    });
                                }
                                Message msgEvsRateSet = VolteSettingsActivity.this.mVolteHandler.obtainMessage(45);
                                VolteSettingsActivity.this.mVolteHandler.sendMessage(msgEvsRateSet);
                                break;
                            case 28:
                                View view = LayoutInflater.from(VolteSettingsActivity.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                LinearLayout line = (LinearLayout) view.findViewById(R.id.linearlayout_main);
                                if (VolteSettingsActivity.this.mVolteViewList == null) {
                                    VolteSettingsActivity.this.mVolteViewList = new ArrayList();
                                } else {
                                    VolteSettingsActivity.this.mVolteViewList.clear();
                                }
                                for (int i2 = 0; i2 < 3; i2++) {
                                    CheckBox checkbox = new CheckBox(VolteSettingsActivity.this.mContext);
                                    checkbox.setText(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.volte_voice_code)[i2]);
                                    line.addView(checkbox);
                                    VolteSettingsActivity.this.mVolteViewList.add(checkbox);
                                }
                                VolteSettingsActivity volteSettingsActivity42 = VolteSettingsActivity.this;
                                volteSettingsActivity42.mResp = volteSettingsActivity42.sendAt(engconstents.ENG_AT_GET_EVS, "atchannel0");
                                VolteSettingsActivity volteSettingsActivity43 = VolteSettingsActivity.this;
                                String getVoiceCodeType2 = volteSettingsActivity43.anayResult(27, volteSettingsActivity43.mResp);
                                if (VolteSettingsActivity.this.mResp != null && VolteSettingsActivity.this.mResp.contains("OK")) {
                                    final int voiceCodeTypeValue = Integer.valueOf(getVoiceCodeType2).intValue();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.25
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            for (int i3 = 0; i3 < VolteSettingsActivity.this.mVolteViewList.size(); i3++) {
                                                if ((voiceCodeTypeValue & (1 << i3)) != 0) {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i3)).setChecked(true);
                                                } else {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i3)).setChecked(false);
                                                }
                                            }
                                        }
                                    });
                                }
                                VolteSettingsActivity.this.popDialog(view, 28);
                                break;
                            case 29:
                                String mResult_min = VolteSettingsActivity.this.sendAt("AT+SPENGMDVOLTE=12,0,2", "atchannel0");
                                final String minBandWidthValue = VolteSettingsActivity.this.anayResult(29, mResult_min);
                                if (minBandWidthValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.26
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBandWidth.setEnabled(false);
                                            VolteSettingsActivity.this.mMinBandWidth.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.27
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBandWidth.setValueIndex(Integer.valueOf(minBandWidthValue).intValue());
                                            VolteSettingsActivity.this.mMinBandWidth.setSummary(VolteSettingsActivity.this.mMinBandWidth.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 30:
                                String setMinBandWidth = (String) msg.obj;
                                final int setMinBandWidthValue = Integer.valueOf(setMinBandWidth).intValue();
                                VolteSettingsActivity volteSettingsActivity44 = VolteSettingsActivity.this;
                                volteSettingsActivity44.mResp = volteSettingsActivity44.sendAt("AT+SPENGMDVOLTE=12,1,2," + setMinBandWidthValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.28
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBandWidth.setValueIndex(setMinBandWidthValue);
                                            VolteSettingsActivity.this.mMinBandWidth.setSummary(VolteSettingsActivity.this.mMinBandWidth.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 31:
                                String mResult_max = VolteSettingsActivity.this.sendAt("AT+SPENGMDVOLTE=12,0,3", "atchannel0");
                                final String maxBandWidthValue = VolteSettingsActivity.this.anayResult(31, mResult_max);
                                if (maxBandWidthValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.29
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBandWidth.setEnabled(false);
                                            VolteSettingsActivity.this.mMaxBandWidth.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.30
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBandWidth.setValueIndex(Integer.valueOf(maxBandWidthValue).intValue());
                                            VolteSettingsActivity.this.mMaxBandWidth.setSummary(VolteSettingsActivity.this.mMaxBandWidth.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 32:
                                String setMaxBandWidth = (String) msg.obj;
                                final int setMaxBandWidthValue = Integer.valueOf(setMaxBandWidth).intValue();
                                VolteSettingsActivity volteSettingsActivity45 = VolteSettingsActivity.this;
                                volteSettingsActivity45.mResp = volteSettingsActivity45.sendAt("AT+SPENGMDVOLTE=12,1,3," + setMaxBandWidthValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.31
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBandWidth.setValueIndex(setMaxBandWidthValue);
                                            VolteSettingsActivity.this.mMaxBandWidth.setSummary(VolteSettingsActivity.this.mMaxBandWidth.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 33:
                                VolteSettingsActivity volteSettingsActivity46 = VolteSettingsActivity.this;
                                volteSettingsActivity46.mResp = volteSettingsActivity46.sendAt("AT+SPENGMDVOLTE=12,0,4", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity47 = VolteSettingsActivity.this;
                                final String minBitRateValue = volteSettingsActivity47.anayResult(33, volteSettingsActivity47.mResp);
                                if (minBitRateValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.32
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBitRate.setEnabled(false);
                                            VolteSettingsActivity.this.mMinBitRate.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.33
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBitRate.setValueIndex(Integer.valueOf(minBitRateValue).intValue());
                                            VolteSettingsActivity.this.mMinBitRate.setSummary(VolteSettingsActivity.this.mMinBitRate.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 34:
                                String setMinBitRate = (String) msg.obj;
                                final int setMinBitRateValue = Integer.valueOf(setMinBitRate).intValue();
                                VolteSettingsActivity volteSettingsActivity48 = VolteSettingsActivity.this;
                                volteSettingsActivity48.mResp = volteSettingsActivity48.sendAt("AT+SPENGMDVOLTE=12,1,4," + setMinBitRateValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.34
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMinBitRate.setValueIndex(setMinBitRateValue);
                                            VolteSettingsActivity.this.mMinBitRate.setSummary(VolteSettingsActivity.this.mMinBitRate.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 35:
                                VolteSettingsActivity volteSettingsActivity49 = VolteSettingsActivity.this;
                                volteSettingsActivity49.mResp = volteSettingsActivity49.sendAt("AT+SPENGMDVOLTE=12,0,5", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity50 = VolteSettingsActivity.this;
                                final String maxBitRateValue = volteSettingsActivity50.anayResult(35, volteSettingsActivity50.mResp);
                                if (maxBitRateValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.35
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBitRate.setEnabled(false);
                                            VolteSettingsActivity.this.mMaxBitRate.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.36
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBitRate.setValueIndex(Integer.valueOf(maxBitRateValue).intValue());
                                            VolteSettingsActivity.this.mMaxBitRate.setSummary(VolteSettingsActivity.this.mMaxBitRate.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 36:
                                String setMaxBitRate = (String) msg.obj;
                                final int setMaxBitRateValue = Integer.valueOf(setMaxBitRate).intValue();
                                VolteSettingsActivity volteSettingsActivity51 = VolteSettingsActivity.this;
                                volteSettingsActivity51.mResp = volteSettingsActivity51.sendAt("AT+SPENGMDVOLTE=12,1,5," + setMaxBitRateValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.37
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mMaxBitRate.setValueIndex(setMaxBitRateValue);
                                            VolteSettingsActivity.this.mMaxBitRate.setSummary(VolteSettingsActivity.this.mMaxBitRate.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 37:
                                VolteSettingsActivity volteSettingsActivity52 = VolteSettingsActivity.this;
                                volteSettingsActivity52.mResp = volteSettingsActivity52.sendAt("AT+SPENGMDVOLTE=12,0,6", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity53 = VolteSettingsActivity.this;
                                final String defaultBitRateValue = volteSettingsActivity53.anayResult(37, volteSettingsActivity53.mResp);
                                if (defaultBitRateValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.38
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mDefaultBitRate.setEnabled(false);
                                            VolteSettingsActivity.this.mDefaultBitRate.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.39
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mDefaultBitRate.setValueIndex(Integer.valueOf(defaultBitRateValue).intValue());
                                            VolteSettingsActivity.this.mDefaultBitRate.setSummary(VolteSettingsActivity.this.mDefaultBitRate.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 38:
                                String setDefaultBitRate = (String) msg.obj;
                                final int setDefaultBitRateValue = Integer.valueOf(setDefaultBitRate).intValue();
                                VolteSettingsActivity volteSettingsActivity54 = VolteSettingsActivity.this;
                                volteSettingsActivity54.mResp = volteSettingsActivity54.sendAt("AT+SPENGMDVOLTE=12,1,6," + setDefaultBitRateValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.40
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mDefaultBitRate.setValueIndex(setDefaultBitRateValue);
                                            VolteSettingsActivity.this.mDefaultBitRate.setSummary(VolteSettingsActivity.this.mDefaultBitRate.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 39:
                                VolteSettingsActivity volteSettingsActivity55 = VolteSettingsActivity.this;
                                volteSettingsActivity55.mResp = volteSettingsActivity55.sendAt("AT+SPENGMDVOLTE=12,0,8", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity56 = VolteSettingsActivity.this;
                                final String evsDefBrValue = volteSettingsActivity56.anayResult(39, volteSettingsActivity56.mResp);
                                if (evsDefBrValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.45
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mEvsDefaultBR.setEnabled(false);
                                            VolteSettingsActivity.this.mEvsDefaultBR.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.46
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mEvsDefaultBR.setValueIndex(Integer.valueOf(evsDefBrValue).intValue());
                                            VolteSettingsActivity.this.mEvsDefaultBR.setSummary(VolteSettingsActivity.this.mEvsDefaultBR.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 40:
                                String setEvsDefaultBr = (String) msg.obj;
                                final int setEvsDefaultBrValue = Integer.valueOf(setEvsDefaultBr).intValue();
                                VolteSettingsActivity volteSettingsActivity57 = VolteSettingsActivity.this;
                                volteSettingsActivity57.mResp = volteSettingsActivity57.sendAt("AT+SPENGMDVOLTE=12,1,8," + setEvsDefaultBrValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.47
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mEvsDefaultBR.setValueIndex(setEvsDefaultBrValue);
                                            VolteSettingsActivity.this.mEvsDefaultBR.setSummary(VolteSettingsActivity.this.mEvsDefaultBR.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 41:
                                VolteSettingsActivity volteSettingsActivity58 = VolteSettingsActivity.this;
                                volteSettingsActivity58.mResp = volteSettingsActivity58.sendAt("AT+SPENGMDVOLTE=12,0,10", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity59 = VolteSettingsActivity.this;
                                final String wbDefBrValue = volteSettingsActivity59.anayResult(41, volteSettingsActivity59.mResp);
                                if (wbDefBrValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.52
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mWbDefaultBR.setEnabled(false);
                                            VolteSettingsActivity.this.mWbDefaultBR.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.53
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mWbDefaultBR.setValueIndex(Integer.valueOf(wbDefBrValue).intValue());
                                            VolteSettingsActivity.this.mWbDefaultBR.setSummary(VolteSettingsActivity.this.mWbDefaultBR.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 42:
                                String setWbDefaultBr = (String) msg.obj;
                                final int setWbDefaultBrValue = Integer.valueOf(setWbDefaultBr).intValue();
                                VolteSettingsActivity volteSettingsActivity60 = VolteSettingsActivity.this;
                                volteSettingsActivity60.mResp = volteSettingsActivity60.sendAt("AT+SPENGMDVOLTE=12,1,10," + setWbDefaultBrValue, "atchannel0");
                                if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                                    Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.54
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mWbDefaultBR.setValueIndex(setWbDefaultBrValue);
                                            VolteSettingsActivity.this.mWbDefaultBR.setSummary(VolteSettingsActivity.this.mWbDefaultBR.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 43:
                                VolteSettingsActivity volteSettingsActivity61 = VolteSettingsActivity.this;
                                volteSettingsActivity61.mResp = volteSettingsActivity61.sendAt("AT+SPENGMDVOLTE=12,0,12", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity62 = VolteSettingsActivity.this;
                                final String nbDefBrValue = volteSettingsActivity62.anayResult(43, volteSettingsActivity62.mResp);
                                if (nbDefBrValue.contains("FAILED")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.59
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mNbDefaultBR.setEnabled(false);
                                            VolteSettingsActivity.this.mNbDefaultBR.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.60
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mNbDefaultBR.setValueIndex(Integer.valueOf(nbDefBrValue).intValue());
                                            VolteSettingsActivity.this.mNbDefaultBR.setSummary(VolteSettingsActivity.this.mNbDefaultBR.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 44:
                                String setNbDefaultBr = (String) msg.obj;
                                final int setNbDefaultBrValue = Integer.valueOf(setNbDefaultBr).intValue();
                                VolteSettingsActivity volteSettingsActivity63 = VolteSettingsActivity.this;
                                volteSettingsActivity63.mResp = volteSettingsActivity63.sendAt("AT+SPENGMDVOLTE=12,1,12," + setNbDefaultBrValue, "atchannel0");
                                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.61
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mNbDefaultBR.setValueIndex(setNbDefaultBrValue);
                                            VolteSettingsActivity.this.mNbDefaultBR.setSummary(VolteSettingsActivity.this.mNbDefaultBR.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.62
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                        }
                                    });
                                }
                                break;
                            case 45:
                                VolteSettingsActivity volteSettingsActivity64 = VolteSettingsActivity.this;
                                volteSettingsActivity64.mResp = volteSettingsActivity64.sendAt("AT+SPENGMDVOLTE=12,0,7", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity65 = VolteSettingsActivity.this;
                                String getEvsRateSet = volteSettingsActivity65.anayResult(45, volteSettingsActivity65.mResp);
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.42
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mEvsRateSet.setEnabled(false);
                                            VolteSettingsActivity.this.mEvsRateSet.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    int evs_rs_value = Integer.valueOf(getEvsRateSet).intValue();
                                    StringBuffer typeValue = new StringBuffer();
                                    for (int i3 = 0; i3 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i3++) {
                                        if (((1 << i3) & evs_rs_value) != 0) {
                                            if (!"".equals(typeValue.toString())) {
                                                typeValue.append("/");
                                            }
                                            typeValue.append(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i3]);
                                        }
                                    }
                                    final String summary2 = typeValue.toString();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.41
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mEvsRateSet.setSummary(summary2);
                                        }
                                    });
                                }
                                Message msgWbRateSet = VolteSettingsActivity.this.mVolteHandler.obtainMessage(47);
                                VolteSettingsActivity.this.mVolteHandler.sendMessage(msgWbRateSet);
                                break;
                            case 46:
                                View view2 = LayoutInflater.from(VolteSettingsActivity.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                LinearLayout line2 = (LinearLayout) view2.findViewById(R.id.linearlayout_main);
                                if (VolteSettingsActivity.this.mVolteViewList == null) {
                                    VolteSettingsActivity.this.mVolteViewList = new ArrayList();
                                } else {
                                    VolteSettingsActivity.this.mVolteViewList.clear();
                                }
                                for (int i4 = 0; i4 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i4++) {
                                    CheckBox checkbox2 = new CheckBox(VolteSettingsActivity.this.mContext);
                                    checkbox2.setText(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i4]);
                                    line2.addView(checkbox2);
                                    VolteSettingsActivity.this.mVolteViewList.add(checkbox2);
                                }
                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(VolteSettingsActivity.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.43
                                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            for (int i5 = 0; i5 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i5++) {
                                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i5)).setChecked(true);
                                            }
                                            return;
                                        }
                                        for (int i6 = 0; i6 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i6++) {
                                            ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i6)).setChecked(false);
                                        }
                                    }
                                });
                                VolteSettingsActivity volteSettingsActivity66 = VolteSettingsActivity.this;
                                volteSettingsActivity66.mResp = volteSettingsActivity66.sendAt("AT+SPENGMDVOLTE=12,0,7", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity67 = VolteSettingsActivity.this;
                                String getEvsRateSet2 = volteSettingsActivity67.anayResult(45, volteSettingsActivity67.mResp);
                                if (VolteSettingsActivity.this.mResp != null && VolteSettingsActivity.this.mResp.contains("OK")) {
                                    final int EvsRateSetValue = Integer.valueOf(getEvsRateSet2).intValue();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.44
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            for (int i5 = 0; i5 < VolteSettingsActivity.this.mVolteViewList.size(); i5++) {
                                                if ((EvsRateSetValue & (1 << i5)) != 0) {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i5)).setChecked(true);
                                                } else {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i5)).setChecked(false);
                                                }
                                            }
                                        }
                                    });
                                }
                                VolteSettingsActivity.this.popDialog(view2, 46);
                                break;
                            case 47:
                                VolteSettingsActivity volteSettingsActivity68 = VolteSettingsActivity.this;
                                volteSettingsActivity68.mResp = volteSettingsActivity68.sendAt("AT+SPENGMDVOLTE=12,0,9", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity69 = VolteSettingsActivity.this;
                                String getWbRateSet = volteSettingsActivity69.anayResult(47, volteSettingsActivity69.mResp);
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.49
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mWbRateSet.setEnabled(false);
                                            VolteSettingsActivity.this.mWbRateSet.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    int wb_rs_value = Integer.valueOf(getWbRateSet).intValue();
                                    StringBuffer typeValue2 = new StringBuffer();
                                    for (int i5 = 0; i5 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i5++) {
                                        if (((1 << i5) & wb_rs_value) != 0) {
                                            if (!"".equals(typeValue2.toString())) {
                                                typeValue2.append("/");
                                            }
                                            typeValue2.append(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i5]);
                                        }
                                    }
                                    final String summary3 = typeValue2.toString();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.48
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mWbRateSet.setSummary(summary3);
                                        }
                                    });
                                }
                                Message msgNbRateSet = VolteSettingsActivity.this.mVolteHandler.obtainMessage(49);
                                VolteSettingsActivity.this.mVolteHandler.sendMessage(msgNbRateSet);
                                break;
                            case 48:
                                View view3 = LayoutInflater.from(VolteSettingsActivity.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                LinearLayout line3 = (LinearLayout) view3.findViewById(R.id.linearlayout_main);
                                if (VolteSettingsActivity.this.mVolteViewList == null) {
                                    VolteSettingsActivity.this.mVolteViewList = new ArrayList();
                                } else {
                                    VolteSettingsActivity.this.mVolteViewList.clear();
                                }
                                for (int i6 = 0; i6 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i6++) {
                                    CheckBox checkbox3 = new CheckBox(VolteSettingsActivity.this.mContext);
                                    checkbox3.setText(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i6]);
                                    line3.addView(checkbox3);
                                    VolteSettingsActivity.this.mVolteViewList.add(checkbox3);
                                }
                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(VolteSettingsActivity.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.50
                                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            for (int i7 = 0; i7 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i7++) {
                                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i7)).setChecked(true);
                                            }
                                            return;
                                        }
                                        for (int i8 = 0; i8 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i8++) {
                                            ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i8)).setChecked(false);
                                        }
                                    }
                                });
                                VolteSettingsActivity volteSettingsActivity70 = VolteSettingsActivity.this;
                                volteSettingsActivity70.mResp = volteSettingsActivity70.sendAt("AT+SPENGMDVOLTE=12,0,9", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity71 = VolteSettingsActivity.this;
                                String getWbRateSet2 = volteSettingsActivity71.anayResult(47, volteSettingsActivity71.mResp);
                                if (VolteSettingsActivity.this.mResp != null && VolteSettingsActivity.this.mResp.contains("OK")) {
                                    final int WbRateSetValue = Integer.valueOf(getWbRateSet2).intValue();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.51
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            for (int i7 = 0; i7 < VolteSettingsActivity.this.mVolteViewList.size(); i7++) {
                                                if ((WbRateSetValue & (1 << i7)) != 0) {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i7)).setChecked(true);
                                                } else {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i7)).setChecked(false);
                                                }
                                            }
                                        }
                                    });
                                }
                                VolteSettingsActivity.this.popDialog(view3, 48);
                                break;
                            case 49:
                                VolteSettingsActivity volteSettingsActivity72 = VolteSettingsActivity.this;
                                volteSettingsActivity72.mResp = volteSettingsActivity72.sendAt("AT+SPENGMDVOLTE=12,0,11", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity73 = VolteSettingsActivity.this;
                                String getNbRateSet = volteSettingsActivity73.anayResult(49, volteSettingsActivity73.mResp);
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.56
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mNbRateSet.setEnabled(false);
                                            VolteSettingsActivity.this.mNbRateSet.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    int nb_rs_value = Integer.valueOf(getNbRateSet).intValue();
                                    StringBuffer typeValue3 = new StringBuffer();
                                    for (int i7 = 0; i7 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set).length; i7++) {
                                        if (((1 << i7) & nb_rs_value) != 0) {
                                            if (!"".equals(typeValue3.toString())) {
                                                typeValue3.append("/");
                                            }
                                            typeValue3.append(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set)[i7]);
                                        }
                                    }
                                    final String summary4 = typeValue3.toString();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.55
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mNbRateSet.setSummary(summary4);
                                        }
                                    });
                                }
                                break;
                            case 50:
                                View view4 = LayoutInflater.from(VolteSettingsActivity.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                LinearLayout line4 = (LinearLayout) view4.findViewById(R.id.linearlayout_main);
                                if (VolteSettingsActivity.this.mVolteViewList == null) {
                                    VolteSettingsActivity.this.mVolteViewList = new ArrayList();
                                } else {
                                    VolteSettingsActivity.this.mVolteViewList.clear();
                                }
                                for (int i8 = 0; i8 < VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set).length; i8++) {
                                    CheckBox checkbox4 = new CheckBox(VolteSettingsActivity.this.mContext);
                                    checkbox4.setText(VolteSettingsActivity.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set)[i8]);
                                    line4.addView(checkbox4);
                                    VolteSettingsActivity.this.mVolteViewList.add(checkbox4);
                                }
                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(VolteSettingsActivity.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.57
                                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            for (int i9 = 0; i9 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i9++) {
                                                ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i9)).setChecked(true);
                                            }
                                            return;
                                        }
                                        for (int i10 = 0; i10 < VolteSettingsActivity.this.mVolteViewList.size() - 1; i10++) {
                                            ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i10)).setChecked(false);
                                        }
                                    }
                                });
                                VolteSettingsActivity volteSettingsActivity74 = VolteSettingsActivity.this;
                                volteSettingsActivity74.mResp = volteSettingsActivity74.sendAt("AT+SPENGMDVOLTE=12,0,11", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity75 = VolteSettingsActivity.this;
                                String getNbRateSet2 = volteSettingsActivity75.anayResult(49, volteSettingsActivity75.mResp);
                                if (VolteSettingsActivity.this.mResp != null && VolteSettingsActivity.this.mResp.contains("OK")) {
                                    final int NbRateSetValue = Integer.valueOf(getNbRateSet2).intValue();
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.58
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            for (int i9 = 0; i9 < VolteSettingsActivity.this.mVolteViewList.size(); i9++) {
                                                if ((NbRateSetValue & (1 << i9)) != 0) {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i9)).setChecked(true);
                                                } else {
                                                    ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i9)).setChecked(false);
                                                }
                                            }
                                        }
                                    });
                                }
                                VolteSettingsActivity.this.popDialog(view4, 50);
                                break;
                            case 51:
                                VolteSettingsActivity volteSettingsActivity76 = VolteSettingsActivity.this;
                                volteSettingsActivity76.mResp = volteSettingsActivity76.sendAt("AT+SPENGMDVOLTE=12,0,13", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity77 = VolteSettingsActivity.this;
                                String channelAwareMode = volteSettingsActivity77.anayResult(51, volteSettingsActivity77.mResp);
                                final int tempValue = VolteSettingsActivity.this.changeValueToIndex(channelAwareMode);
                                Log.d(VolteSettingsActivity.TAG, "tempValue is " + tempValue);
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.64
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mChannelAwareMode.setEnabled(false);
                                            VolteSettingsActivity.this.mChannelAwareMode.setSummary(R.string.feature_abnormal);
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.63
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mChannelAwareMode.setValueIndex(tempValue);
                                            VolteSettingsActivity.this.mChannelAwareMode.setSummary(VolteSettingsActivity.this.mChannelAwareMode.getEntry());
                                        }
                                    });
                                }
                                break;
                            case 52:
                                String setAwreMde = (String) msg.obj;
                                final int awareModevalue = Integer.valueOf(setAwreMde).intValue();
                                String tempChannelAware = VolteSettingsActivity.this.changeIndexToValue(Integer.valueOf(setAwreMde).intValue());
                                VolteSettingsActivity volteSettingsActivity78 = VolteSettingsActivity.this;
                                volteSettingsActivity78.mResp = volteSettingsActivity78.sendAt("AT+SPENGMDVOLTE=12,1,13," + tempChannelAware, "atchannel0");
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.66
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.65
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mChannelAwareMode.setValueIndex(awareModevalue);
                                            VolteSettingsActivity.this.mChannelAwareMode.setSummary(VolteSettingsActivity.this.mChannelAwareMode.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 53:
                                VolteSettingsActivity volteSettingsActivity79 = VolteSettingsActivity.this;
                                volteSettingsActivity79.mResp = volteSettingsActivity79.sendAt("AT+SPENGMDVOLTE=12,0,14", "atchannel0");
                                VolteSettingsActivity volteSettingsActivity80 = VolteSettingsActivity.this;
                                String channelAwareBandwidthValue = volteSettingsActivity80.anayResult(53, volteSettingsActivity80.mResp);
                                try {
                                    final int tempBandwidthValue = Integer.valueOf(channelAwareBandwidthValue).intValue();
                                    if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                        VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.68
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                VolteSettingsActivity.this.mChannelAwareBD.setEnabled(false);
                                                VolteSettingsActivity.this.mChannelAwareBD.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                    } else {
                                        VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.67
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                ListPreference listPreference = VolteSettingsActivity.this.mChannelAwareBD;
                                                int i9 = tempBandwidthValue;
                                                if (i9 > 0) {
                                                    i9--;
                                                }
                                                listPreference.setValueIndex(i9);
                                                VolteSettingsActivity.this.mChannelAwareBD.setSummary(VolteSettingsActivity.this.mChannelAwareBD.getEntry());
                                            }
                                        });
                                    }
                                } catch (NumberFormatException ex) {
                                    Log.d(VolteSettingsActivity.TAG, "NumberFormatException: " + ex);
                                    return;
                                }
                                break;
                            case 54:
                                String setAwreBandwidth = (String) msg.obj;
                                final int setNAwreBandwidthValue = Integer.valueOf(setAwreBandwidth).intValue() + 1;
                                VolteSettingsActivity volteSettingsActivity81 = VolteSettingsActivity.this;
                                volteSettingsActivity81.mResp = volteSettingsActivity81.sendAt("AT+SPENGMDVOLTE=12,1,14," + setNAwreBandwidthValue, "atchannel0");
                                if (VolteSettingsActivity.this.mResp == null || !VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.70
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.69
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mChannelAwareBD.setValueIndex(setNAwreBandwidthValue - 1);
                                            VolteSettingsActivity.this.mChannelAwareBD.setSummary(VolteSettingsActivity.this.mChannelAwareBD.getEntry());
                                            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                        }
                                    });
                                }
                                break;
                            case 55:
                                VolteSettingsActivity volteSettingsActivity82 = VolteSettingsActivity.this;
                                volteSettingsActivity82.mResp = volteSettingsActivity82.sendAt(engconstents.ENG_GET_SESSION_TIMER, "atchannel0");
                                Log.d(VolteSettingsActivity.TAG, "GET_SESSION_TIMER  mATResponse: " + VolteSettingsActivity.this.mResp);
                                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                                    String[] str = VolteSettingsActivity.this.mResp.split("\"");
                                    if (str.length > 1) {
                                        final String time2 = str[1].equals("") ? "0" : str[1];
                                        VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.87
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                VolteSettingsActivity.this.mSessionTimer.setSummary(time2 + "s");
                                            }
                                        });
                                    }
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.88
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Log.d(VolteSettingsActivity.TAG, "GET_SESSION_TIMER Failed");
                                            VolteSettingsActivity.this.mSessionTimer.setSummary(VolteSettingsActivity.this.mContext.getString(R.string.feature_not_support));
                                            VolteSettingsActivity.this.mSessionTimer.setEnabled(false);
                                        }
                                    });
                                }
                                break;
                            case 56:
                                final int time3 = Integer.valueOf(msg.obj.toString()).intValue();
                                VolteSettingsActivity volteSettingsActivity83 = VolteSettingsActivity.this;
                                volteSettingsActivity83.mResp = volteSettingsActivity83.sendAt("AT+SPENGMDVOLTE=23,1,\"" + time3 + "\"", "atchannel0");
                                StringBuilder sb = new StringBuilder();
                                sb.append("SET_SESSION_TIMER  mATResponse: ");
                                sb.append(VolteSettingsActivity.this.mResp);
                                Log.d(VolteSettingsActivity.TAG, sb.toString());
                                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.89
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mSessionTimer.setSummary(time3 + "s");
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.90
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(VolteSettingsActivity.this.mContext, "SET_SESSION_TIMER Failed", 0).show();
                                        }
                                    });
                                }
                                break;
                            case 57:
                                VolteSettingsActivity volteSettingsActivity84 = VolteSettingsActivity.this;
                                volteSettingsActivity84.mResp = volteSettingsActivity84.sendAt(engconstents.ENG_GET_FORCE_MT_SESSION_TIMER, "atchannel0");
                                Log.d(VolteSettingsActivity.TAG, "GET_FORCE_MT_SESSION_TIMER  mATResponse: " + VolteSettingsActivity.this.mResp);
                                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                                    String[] str2 = VolteSettingsActivity.this.mResp.split("\"");
                                    if (str2.length > 1) {
                                        final String[] strr = str2[1].split(";");
                                        VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.91
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                VolteSettingsActivity.this.mForceMtSessionTimer.setSummary(strr[0] + "s");
                                            }
                                        });
                                    }
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.92
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Log.d(VolteSettingsActivity.TAG, "GET_FORCE_MT_SESSION_TIMER Failed");
                                            VolteSettingsActivity.this.mForceMtSessionTimer.setSummary(VolteSettingsActivity.this.mContext.getString(R.string.feature_not_support));
                                            VolteSettingsActivity.this.mForceMtSessionTimer.setEnabled(false);
                                        }
                                    });
                                }
                                break;
                            case 58:
                                final int force_mt_time = Integer.valueOf(msg.obj.toString()).intValue();
                                VolteSettingsActivity volteSettingsActivity85 = VolteSettingsActivity.this;
                                volteSettingsActivity85.mResp = volteSettingsActivity85.sendAt("AT+SPENGMDVOLTE=2,1,\"" + force_mt_time + "\"", "atchannel0");
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("SET_FORCE_MT_SESSION_TIMER  mATResponse: ");
                                sb2.append(VolteSettingsActivity.this.mResp);
                                Log.d(VolteSettingsActivity.TAG, sb2.toString());
                                if (VolteSettingsActivity.this.mResp.contains("OK")) {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.93
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            VolteSettingsActivity.this.mForceMtSessionTimer.setSummary(force_mt_time + "s");
                                        }
                                    });
                                } else {
                                    VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.94
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            Toast.makeText(VolteSettingsActivity.this.mContext, "SET_FORCE_MT_SESSION_TIMER Failed", 0).show();
                                        }
                                    });
                                }
                                break;
                        }
                        break;
                }
                return;
            }
            final String setPqosTimer = (String) msg.obj;
            VolteSettingsActivity volteSettingsActivity86 = VolteSettingsActivity.this;
            volteSettingsActivity86.mResp = volteSettingsActivity86.sendAt(engconstents.ENG_AT_SET_PQOS + setPqosTimer, "atchannel0");
            if (!VolteSettingsActivity.this.mResp.contains("OK")) {
                Toast.makeText(VolteSettingsActivity.this.mContext, "Fail", 0).show();
            } else {
                VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.VolteHandler.86
                    @Override // java.lang.Runnable
                    public void run() {
                        VolteSettingsActivity.this.mPqosTimer.setSummary(setPqosTimer + "s");
                    }
                });
            }
        }

        static /* synthetic */ boolean lambda$handleMessage$0(int value, int i) {
            return ((1 << i) & value) != 0;
        }

        static /* synthetic */ String lambda$handleMessage$1(String[] arr, int i) {
            return arr[i];
        }

        public /* synthetic */ void lambda$handleMessage$2$VolteSettingsActivity$VolteHandler(String summary) {
            VolteSettingsActivity.this.mVolteVoiceCode.setSummary(summary);
        }

        public /* synthetic */ void lambda$handleMessage$3$VolteSettingsActivity$VolteHandler() {
            VolteSettingsActivity.this.mVolteVoiceCode.setEnabled(false);
            VolteSettingsActivity.this.mVolteVoiceCode.setSummary(R.string.feature_abnormal);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int changeValueToIndex(String stringValue) {
        if (stringValue.contains("255")) {
            return 0;
        }
        if (stringValue.contains("0")) {
            return 1;
        }
        if (stringValue.contains("2")) {
            return 2;
        }
        if (stringValue.contains("3")) {
            return 3;
        }
        if (stringValue.contains("5")) {
            return 4;
        }
        if (!stringValue.contains("7")) {
            return 0;
        }
        return 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String changeIndexToValue(int setValueIndex) {
        if (setValueIndex == 0) {
            return "255";
        }
        if (setValueIndex == 1) {
            return "0";
        }
        if (setValueIndex == 2) {
            return "2";
        }
        if (setValueIndex == 3) {
            return "3";
        }
        if (setValueIndex == 4) {
            return "5";
        }
        if (setValueIndex != 5) {
            return null;
        }
        return "7";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popDialog(View view, int dialogType) {
        int arrayId_tmp;
        int titleId_tmp;
        String para_tmp;
        Preference pref_tmp;
        if (dialogType == 28) {
            Preference pref_tmp2 = this.mVolteVoiceCode;
            arrayId_tmp = R.array.volte_voice_code;
            titleId_tmp = R.string.volte_voice_code_type;
            para_tmp = "1";
            pref_tmp = pref_tmp2;
        } else if (dialogType == 46) {
            Preference pref_tmp3 = this.mEvsRateSet;
            arrayId_tmp = R.array.evs_wb_bitrate_set;
            titleId_tmp = R.string.rate_set;
            para_tmp = "7";
            pref_tmp = pref_tmp3;
        } else if (dialogType == 48) {
            Preference pref_tmp4 = this.mWbRateSet;
            arrayId_tmp = R.array.evs_wb_bitrate_set;
            titleId_tmp = R.string.rate_set;
            para_tmp = "9";
            pref_tmp = pref_tmp4;
        } else {
            if (dialogType != 50) {
                return;
            }
            Preference pref_tmp5 = this.mNbRateSet;
            arrayId_tmp = R.array.nb_bitrate_set;
            titleId_tmp = R.string.rate_set;
            para_tmp = "11";
            pref_tmp = pref_tmp5;
        }
        int stringArrayId = arrayId_tmp;
        int diaTitleId = titleId_tmp;
        String type = para_tmp;
        Preference preference = pref_tmp;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this.mContext).setView(view).setNegativeButton(getString(R.string.alertdialog_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.VolteSettingsActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.setTitle(diaTitleId).setPositiveButton(getString(R.string.alertdialog_ok), new AnonymousClass4(dialogType, stringArrayId, type, preference));
        this.mAlertDialog = alertBuilder.create();
        this.mAlertDialog.setCanceledOnTouchOutside(false);
        this.mAlertDialog.show();
    }

    /* JADX INFO: renamed from: com.sprd.engineermode.telephony.volte.VolteSettingsActivity$4, reason: invalid class name */
    class AnonymousClass4 implements DialogInterface.OnClickListener {
        final /* synthetic */ int val$dialogType;
        final /* synthetic */ Preference val$preference;
        final /* synthetic */ int val$stringArrayId;
        final /* synthetic */ String val$type;

        AnonymousClass4(int i, int i2, String str, Preference preference) {
            this.val$dialogType = i;
            this.val$stringArrayId = i2;
            this.val$type = str;
            this.val$preference = preference;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            int listSize = this.val$dialogType == 28 ? VolteSettingsActivity.this.mVolteViewList.size() : VolteSettingsActivity.this.mVolteViewList.size() - 1;
            final String[] arr = VolteSettingsActivity.this.mContext.getResources().getStringArray(this.val$stringArrayId);
            final int[] tempInt = {0};
            final String typeValue = (String) IntStream.range(0, listSize).filter(new IntPredicate() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$4$bGBxWPt1iOvLjhJnfl5oqTPbJRs
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return this.f$0.lambda$onClick$0$VolteSettingsActivity$4(i);
                }
            }).peek(new IntConsumer() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$4$BQ-Eqj9Lq6Rcrjqji9dZHzuYl54
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    VolteSettingsActivity.AnonymousClass4.lambda$onClick$1(tempInt, i);
                }
            }).mapToObj(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$4$P18mOidpii1hDQ7OFt8QaKSPhKQ
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return VolteSettingsActivity.AnonymousClass4.lambda$onClick$2(arr, i);
                }
            }).collect(Collectors.joining("/"));
            int value = tempInt[0];
            if (value == 0) {
                VolteSettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$4$-gnDANB3ijpeRBMJtOHzsKraMNo
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClick$3$VolteSettingsActivity$4();
                    }
                });
                return;
            }
            String cmd = "AT+SPENGMDVOLTE=12,1," + this.val$type + "," + value;
            String atRsp = VolteSettingsActivity.this.sendAt(cmd, "atchannel0");
            if (atRsp.contains("OK")) {
                Handler handler = VolteSettingsActivity.this.mUiThread;
                final Preference preference = this.val$preference;
                handler.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$VolteSettingsActivity$4$9-M1wwbKFjRlC6E6rGUCvG-f7tk
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClick$4$VolteSettingsActivity$4(preference, typeValue);
                    }
                });
            }
        }

        public /* synthetic */ boolean lambda$onClick$0$VolteSettingsActivity$4(int i) {
            return ((CheckBox) VolteSettingsActivity.this.mVolteViewList.get(i)).isChecked();
        }

        static /* synthetic */ void lambda$onClick$1(int[] tempInt, int i) {
            tempInt[0] = tempInt[0] + (1 << i);
        }

        static /* synthetic */ String lambda$onClick$2(String[] arr, int i) {
            return arr[i];
        }

        public /* synthetic */ void lambda$onClick$3$VolteSettingsActivity$4() {
            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.volte_set_error, 0).show();
        }

        public /* synthetic */ void lambda$onClick$4$VolteSettingsActivity$4(Preference preference, String summary) {
            preference.setSummary(summary);
            Toast.makeText(VolteSettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
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
        switch (mesg) {
            case 3:
                String res = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_IP_STATE anayResult is " + res);
                return res;
            case 5:
                String res2 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_MUT_LENGTH anayResult is " + res2);
                return res2;
            case 7:
                String res3 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_SUBSCRIB_STATE anayResult is " + res3);
                return res3;
            case 9:
                String res4 = result.split("\\:")[1].trim().split("\n")[0].substring(4);
                Log.d(TAG, "MSG_GET_SIGCOMP_STATE anayResult is " + res4);
                return res4;
            case 12:
                String res5 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_EXPIRES_TIME anayResult is " + res5);
                return res5;
            case 14:
                String res6 = result.split("\\:")[1].split("\n")[0].substring(4);
                Log.d(TAG, "MSG_GET_VOICE_CODE_WAY anayResult is " + res6);
                return res6;
            case 17:
                String res7 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_PRECONDITION_STATE anayResult is " + res7);
                return res7;
            case 19:
                String res8 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_TQOS_TIMER anayResult is " + res8);
                return res8;
            case 21:
                String res9 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_TCALL_TIMER anayResult is " + res9);
                return res9;
            case 23:
                String res10 = result.split("\\:")[1].split("\\,")[2].split("\n")[0].trim();
                Log.d(TAG, "MSG_GET_TREG_TIMER anayResult is " + res10);
                return res10;
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 41:
            case 43:
            case 45:
            case 47:
            case 49:
                String res11 = result.split("\\:")[1].split("\n")[0].split(",")[3].trim();
                Log.d(TAG, "MSG_GET anayResult is " + res11);
                return res11;
            case 51:
            case 53:
                String res12 = result.split("\\:")[1].split("\n")[0].split(",")[3].trim();
                Log.d(TAG, "MSG_GET _CHANNEL_AWARE anayResult is " + res12);
                return res12;
            default:
                return null;
        }
    }
}
