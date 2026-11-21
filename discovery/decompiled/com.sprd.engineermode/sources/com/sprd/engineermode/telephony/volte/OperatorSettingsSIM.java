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
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.sprd.engineermode.EMClickEditTextPreference;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.ArrayList;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: classes4.dex */
public class OperatorSettingsSIM extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String KEY_ACTIVATE_CFNL_ENABLE = "activate_cfnl_enable";
    private static final String KEY_ALERTING = "alerting";
    private static final String KEY_AUDIO_DYNAMIC_ENABLE = "audio_dynamic_resolution";
    private static final String KEY_AUDIO_RR = "audio_rr";
    private static final String KEY_AUDIO_RS = "audio_rs";
    private static final String KEY_AUDIO_RTCP_TIME_INTERNAL = "audio_rtcp_time_internal";
    private static final String KEY_AUDIO_RTP_TIMEOUT = "audio_rtp_timeout_timer";
    private static final String KEY_BSF_PORT = "bsf_port";
    private static final String KEY_BSF_URI = "bsf_uri";
    public static final String KEY_CHANNEL = "key_channel";
    private static final String KEY_CHANNEL_AWARE_MODE = "channel_aware_mode";
    private static final String KEY_CIPHER_ALGORITHM = "cipher_algorithm";
    private static final String KEY_CONFERENCE_URI = "conference_urI";
    private static final String KEY_DEFAULT_BITRATE = "default_encode_bitrate";
    private static final String KEY_DIRECT_ALERTING_ENABLE = "direct_alerting_enable";
    private static final String KEY_DNS_SRV_ENABLE = "dns_srv_enable";
    private static final String KEY_EVS_DEFAULT_BITRATE = "evs_io_default_bitrate";
    private static final String KEY_EVS_RATE_SET = "evs_rate_set";
    private static final String KEY_HTTPS_ENABLE = "https_enable";
    private static final String KEY_HTTP_PUT_MEDIA = "http_put_media";
    private static final String KEY_INITIAL_REGISTER_PANI = "initial_register_pani";
    private static final String KEY_INTEGRITY_ALGORITHM = "Integrity_algorithm";
    private static final String KEY_INTERNATIONAL_URL_FORMAT = "international_url_format";
    private static final String KEY_IPSEC_SWITCH = "ipsec_enable";
    private static final String KEY_IP_PRIORITY = "ip_priority";
    private static final String KEY_LOCAL_CALL_WAITING = "local_call_waiting";
    private static final String KEY_MAX_BANDWIDTH = "max_bandwidth";
    private static final String KEY_MAX_BITRATE = "max_bitrate";
    private static final String KEY_MID_CALL = "mid_call";
    private static final String KEY_MIN_BANDWIDTH = "min_bandwidth";
    private static final String KEY_MIN_BITRATE = "min_bitrate";
    private static final String KEY_MO_AMR_OA_MODE = "mo_amr_oa_mode";
    private static final String KEY_MTU = "mtu";
    private static final String KEY_MT_SESSION_TIMER = "mt_session_timer";
    private static final String KEY_MT_SESSION_TIMER_REFRESHER = "mt_session_timer_refresher";
    private static final String KEY_MWI_ENABLE = "mwi_enable";
    private static final String KEY_MWI_SUBSCRIBE_EXPIRE = "mwi_subscribe_expire";
    private static final String KEY_NAMED_TEL_EVENT_ENABLE = "named_tel_event_enable";
    private static final String KEY_NATIONAL_URL_FORMAT = "national_url_format";
    private static final String KEY_NB_DEFAULT_BITRATE = "nb_default_bitrate";
    private static final String KEY_NB_RATE_SET = "nb_rate_set";
    private static final String KEY_NO_ANSWER_TIMER = "no_answer_timer";
    private static final String KEY_PCSCF_PORT = "pcscf_port";
    private static final String KEY_PER_ALERTING = "pre_alerting";
    private static final String KEY_PRECONDITION_ENABLE = "precondition_enable";
    private static final String KEY_REG_EXPIRE = "register_expire";
    private static final String KEY_REG_SUBSCRIBE = "reg_subscribe_enable";
    private static final String KEY_REG_SUBSCRIB_EXPIRE = "reg_subscrib_expire";
    private static final String KEY_RELIABLE_180_ENABLE = "reliable_180_enable";
    private static final String KEY_RESOURCE_ALWAYS_READY = "resource_always_ready";
    private static final String KEY_RINGBACK_TIMER = "ringback_timer";
    private static final String KEY_RINGRING_TIMER = "ringing_timer";
    private static final String KEY_RSRVCC_ALERTING = "rsrvcc_alerting";
    private static final String KEY_RSRVCC_MID_CALL = "rsrvcc_mid_call";
    private static final String KEY_RSRVCC_SUPPORT = "rsrvcc_support";
    private static final String KEY_SESSION_TIMER = "session_timer";
    private static final String KEY_SESSION_TIMER_REFRESHER = "session_timer_refresher";
    private static final String KEY_SIP_HEADER_COMPACT = "sip_header_compact";
    private static final String KEY_SMS_OVER_IP = "sms_over_ip_enable";
    private static final String KEY_TCALL_TIMER = "tcall_timer";
    private static final String KEY_TEMERGREG_TIMER = "temergreg_timer";
    private static final String KEY_TREG_TIMER = "treg_timer";
    private static final String KEY_USSI_ENABLE = "ussi_enable";
    private static final String KEY_VIDEO_AUDIO_MERGE = "video_audio_merge";
    private static final String KEY_VIDEO_CODEC_TYPE = "video_codec_type";
    private static final String KEY_VIDEO_DYNAMIC_ENABLE = "video_dynamic_resolution";
    private static final String KEY_VIDEO_EARLY_MEDIA = "video_early_media";
    private static final String KEY_VIDEO_MAX_RESOLUTION = "video_max_resolution";
    private static final String KEY_VIDEO_RR = "video_rr";
    private static final String KEY_VIDEO_RS = "video_rs";
    private static final String KEY_VIDEO_UPGRADE_PRECONDITION = "video_upgrade_precondition";
    private static final String KEY_VOICE_CODE = "volte_voice_code";
    private static final String KEY_WB_DEFAULT_BITRATE = "wb_default_bitrate";
    private static final String KEY_WB_RATE_SET = "wb_rate_set";
    private static final String KEY_XCAP_AUID = "xcap_auid";
    private static final String KEY_XCAP_PORT = "xcap_port";
    private static final String KEY_XCAP_URI = "xcap_uri";
    private static final int MSG_GET_ACTIVATE_CFNL = 157;
    private static final int MSG_GET_ALERTING = 115;
    private static final int MSG_GET_AUDIO_DYNAMIC_ENABLE = 137;
    private static final int MSG_GET_AUDIO_RR = 81;
    private static final int MSG_GET_AUDIO_RS = 79;
    private static final int MSG_GET_AUDIO_RTCP_TIME = 77;
    private static final int MSG_GET_AUDIO_RTP_TIMEOUT = 75;
    private static final int MSG_GET_BSF_PORT = 143;
    private static final int MSG_GET_BSF_URI = 141;
    private static final int MSG_GET_CHANNEL_AWARE_MODE = 63;
    private static final int MSG_GET_CIPHER_ALGORITHM = 9;
    private static final int MSG_GET_CONFERENCE_URI = 99;
    private static final int MSG_GET_DEFAULT_BITRATE = 55;
    private static final int MSG_GET_DIRECT_ALERTING = 105;
    private static final int MSG_GET_DNS_SRV_ENABLE = 153;
    private static final int MSG_GET_EVS_DEFAULT_BITRATE = 57;
    private static final int MSG_GET_EVS_RATE_SET = 69;
    private static final int MSG_GET_HTTPS_ENABLE = 155;
    private static final int MSG_GET_HTTP_PUT_MEDIA = 159;
    private static final int MSG_GET_INITIAL_REGISTER_PANI = 23;
    private static final int MSG_GET_INTEGRITY_ALGORITHM = 7;
    private static final int MSG_GET_INTERNATIONAL_URL = 129;
    private static final int MSG_GET_IPSEC_STATE = 0;
    private static final int MSG_GET_IP_PRIORITY = 161;
    private static final int MSG_GET_LOCAL_CALL = 151;
    private static final int MSG_GET_MAX_BANDWIDTH = 49;
    private static final int MSG_GET_MAX_BITRATE = 53;
    private static final int MSG_GET_MID_CALL = 117;
    private static final int MSG_GET_MIN_BANDWIDTH = 47;
    private static final int MSG_GET_MIN_BITRATE = 51;
    private static final int MSG_GET_MO_AMR_OA = 71;
    private static final int MSG_GET_MTU = 29;
    private static final int MSG_GET_MT_SESSION_REFRESHER = 133;
    private static final int MSG_GET_MT_SESSION_TIMER = 97;
    private static final int MSG_GET_MWI_ENABLE = 13;
    private static final int MSG_GET_MWI_SUBSCRIBE_EXPIRE = 27;
    private static final int MSG_GET_NAMED_TEL_EVENT = 73;
    private static final int MSG_GET_NATIONAL_URL_FORMAT = 127;
    private static final int MSG_GET_NB_DEFAULT_BITRATE = 61;
    private static final int MSG_GET_NB_RATE_SET = 67;
    private static final int MSG_GET_NO_ANSWER_TIMER = 93;
    private static final int MSG_GET_PCSCFPORT = 31;
    private static final int MSG_GET_PER_ALERTING = 119;
    private static final int MSG_GET_PRECONDITION = 109;
    private static final int MSG_GET_REG_EXPIRE = 33;
    private static final int MSG_GET_REG_SUBSCRIBE_ENABLE = 11;
    private static final int MSG_GET_REG_SUBSCRIBE_EXPIRE = 25;
    private static final int MSG_GET_RELIABLE_180 = 103;
    private static final int MSG_GET_RESOURCE_ALWAYS = 107;
    private static final int MSG_GET_RINGBACK_TIMER = 91;
    private static final int MSG_GET_RINGRING_TIMER = 89;
    private static final int MSG_GET_RSRVCC_ALERTING = 123;
    private static final int MSG_GET_RSRVCC_MID_CALL = 125;
    private static final int MSG_GET_RSRVCC_SUPPORT = 121;
    private static final int MSG_GET_SESSION_TIMER = 95;
    private static final int MSG_GET_SESSION_TIMER_REFRESHER = 131;
    private static final int MSG_GET_SIP_HEADER_COMPACT = 17;
    private static final int MSG_GET_SMS_OVER_IP_ENABLE = 19;
    private static final int MSG_GET_TCALL_TIMER = 87;
    private static final int MSG_GET_TEMERGREG_TIMER = 37;
    private static final int MSG_GET_TREG_TIMER = 35;
    private static final int MSG_GET_USSI_ENABLE = 21;
    private static final int MSG_GET_VIDEO_AUDIO_MERGE = 135;
    private static final int MSG_GET_VIDEO_CODE_TYPE = 101;
    private static final int MSG_GET_VIDEO_DYNAMIC_ENABLE = 139;
    private static final int MSG_GET_VIDEO_EARLY_MEDIA = 113;
    private static final int MSG_GET_VIDEO_MAX_RESOLUTION = 163;
    private static final int MSG_GET_VIDEO_RR = 85;
    private static final int MSG_GET_VIDEO_RS = 83;
    private static final int MSG_GET_VIDEO_UPGRADE = 111;
    private static final int MSG_GET_VOICE_CODE_TYPE = 45;
    private static final int MSG_GET_WB_DEFAULT_BITRATE = 59;
    private static final int MSG_GET_WB_RATE_SET = 65;
    private static final int MSG_GET_XCAP_AUID = 149;
    private static final int MSG_GET_XCAP_PORT = 147;
    private static final int MSG_GET_XCAP_URI = 145;
    private static final int MSG_SET_ACTIVATE_CFNL = 158;
    private static final int MSG_SET_ALERTING = 116;
    private static final int MSG_SET_AUDIO_DYNAMIC_ENABLE = 138;
    private static final int MSG_SET_AUDIO_RR = 82;
    private static final int MSG_SET_AUDIO_RS = 80;
    private static final int MSG_SET_AUDIO_RTCP_TIME = 78;
    private static final int MSG_SET_AUDIO_RTP_TIMEOUT = 76;
    private static final int MSG_SET_BSF_PORT = 144;
    private static final int MSG_SET_BSF_URI = 142;
    private static final int MSG_SET_CHANNEL_AWARE_MODE = 64;
    private static final int MSG_SET_CIPHER_ALGORITHM = 10;
    private static final int MSG_SET_CONFERENCE_URI = 100;
    private static final int MSG_SET_DEFAULT_BITRATE = 56;
    private static final int MSG_SET_DIRECT_ALERTING = 106;
    private static final int MSG_SET_DNS_SRV_ENABLE = 154;
    private static final int MSG_SET_EVS_DEFAULT_BITRATE = 58;
    private static final int MSG_SET_EVS_RATE_SET = 70;
    private static final int MSG_SET_HTTPS_ENABLE = 156;
    private static final int MSG_SET_HTTP_PUT_MEDIA = 160;
    private static final int MSG_SET_INITIAL_REGISTER_PANI = 24;
    private static final int MSG_SET_INTEGRITY_ALGORITHM = 8;
    private static final int MSG_SET_INTERNATIONAL_URL = 130;
    private static final int MSG_SET_IPSEC_STATE = 1;
    private static final int MSG_SET_IP_PRIORITY = 162;
    private static final int MSG_SET_LOCAL_CALL = 152;
    private static final int MSG_SET_MAX_BANDWIDTH = 50;
    private static final int MSG_SET_MAX_BITRATE = 54;
    private static final int MSG_SET_MID_CALL = 118;
    private static final int MSG_SET_MIN_BANDWIDTH = 48;
    private static final int MSG_SET_MIN_BITRATE = 52;
    private static final int MSG_SET_MO_AMR_OA = 72;
    private static final int MSG_SET_MTU = 30;
    private static final int MSG_SET_MT_SESSION_REFRESHER = 134;
    private static final int MSG_SET_MT_SESSION_TIMER = 98;
    private static final int MSG_SET_MWI_ENABLE = 14;
    private static final int MSG_SET_MWI_SUBSCRIBE_EXPIRE = 28;
    private static final int MSG_SET_NAMED_TEL_EVENT = 74;
    private static final int MSG_SET_NATIONAL_URL_FORMAT = 128;
    private static final int MSG_SET_NB_DEFAULT_BITRATE = 62;
    private static final int MSG_SET_NB_RATE_SET = 68;
    private static final int MSG_SET_NO_ANSWER_TIMER = 94;
    private static final int MSG_SET_PCSCFPORT = 32;
    private static final int MSG_SET_PER_ALERTING = 120;
    private static final int MSG_SET_PRECONDITION = 110;
    private static final int MSG_SET_REG_EXPIRE = 34;
    private static final int MSG_SET_REG_SUBSCRIBE_ENABLE = 12;
    private static final int MSG_SET_REG_SUBSCRIBE_EXPIRE = 26;
    private static final int MSG_SET_RELIABLE_180 = 104;
    private static final int MSG_SET_RESOURCE_ALWAYS = 108;
    private static final int MSG_SET_RINGBACK_TIMER = 92;
    private static final int MSG_SET_RINGRING_TIMER = 90;
    private static final int MSG_SET_RSRVCC_ALERTING = 124;
    private static final int MSG_SET_RSRVCC_MID_CALL = 126;
    private static final int MSG_SET_RSRVCC_SUPPORT = 122;
    private static final int MSG_SET_SESSION_TIMER = 96;
    private static final int MSG_SET_SESSION_TIMER_REFRESHER = 132;
    private static final int MSG_SET_SIP_HEADER_COMPACT = 18;
    private static final int MSG_SET_SMS_OVER_IP_ENABLE = 20;
    private static final int MSG_SET_TCALL_TIMER = 88;
    private static final int MSG_SET_TEMERGREG_TIMER = 38;
    private static final int MSG_SET_TREG_TIMER = 36;
    private static final int MSG_SET_USSI_ENABLE = 22;
    private static final int MSG_SET_VIDEO_AUDIO_MERGE = 136;
    private static final int MSG_SET_VIDEO_CODE_TYPE = 102;
    private static final int MSG_SET_VIDEO_DYNAMIC_ENABLE = 140;
    private static final int MSG_SET_VIDEO_EARLY_MEDIA = 114;
    private static final int MSG_SET_VIDEO_MAX_RESOLUTION = 164;
    private static final int MSG_SET_VIDEO_RR = 86;
    private static final int MSG_SET_VIDEO_RS = 84;
    private static final int MSG_SET_VIDEO_UPGRADE = 112;
    private static final int MSG_SET_VOICE_CODE_TYPE = 46;
    private static final int MSG_SET_WB_DEFAULT_BITRATE = 60;
    private static final int MSG_SET_WB_RATE_SET = 66;
    private static final int MSG_SET_XCAP_AUID = 150;
    private static final int MSG_SET_XCAP_PORT = 148;
    private static final int MSG_SET_XCAP_URI = 146;
    private static final String TAG = "OperatorSettingsSIM";
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private TwoStatePreference mActivateCfnlSwitch;
    private AlertDialog mAlertDialog;
    private TwoStatePreference mAlertingSwitch;
    private TwoStatePreference mAudioDynamicEnable;
    private EditTextPreference mAudioRR;
    private EditTextPreference mAudioRS;
    private EditTextPreference mAudioRTCPTimeInterval;
    private EditTextPreference mAudioRTPTimeout;
    private EditTextPreference mBsfPort;
    private EMClickEditTextPreference mBsfURI;
    private ListPreference mChannelAwareMode;
    private Preference mCipherAlgorithm;
    private EMClickEditTextPreference mConferenceURI;
    private ListPreference mDefaultBitRate;
    private TwoStatePreference mDirectAlertingSwitch;
    private TwoStatePreference mDnsSrvEnableSwitch;
    private ListPreference mEvsDefaultBR;
    private Preference mEvsRateSet;
    private TwoStatePreference mHttpPutMediaSwitch;
    private TwoStatePreference mHttpsEnableSwitch;
    private ListPreference mIPPriority;
    private TwoStatePreference mIPsecSwitch;
    private TwoStatePreference mInitialRegisterPANI;
    private Preference mIntegrityAlgorithm;
    private ListPreference mInternationalURL;
    private IpsecHandler mIpsecHandler;
    private TwoStatePreference mLocalCallSwitch;
    private EditTextPreference mMTSessionTimer;
    private ListPreference mMTSessionTimerRefresher;
    private EditTextPreference mMTU;
    private TwoStatePreference mMWIEnable;
    private ListPreference mMaxBandWidth;
    private ListPreference mMaxBitRate;
    private TwoStatePreference mMidCallSwitch;
    private ListPreference mMinBandWidth;
    private ListPreference mMinBitRate;
    private ListPreference mMoAmrOaMode;
    private EditTextPreference mMwiSubscribExpire;
    private TwoStatePreference mNamedTelEvent;
    private ListPreference mNationalURL;
    private ListPreference mNbDefaultBR;
    private Preference mNbRateSet;
    private EditTextPreference mNoAnswerTimer;
    private EditTextPreference mPcscfPort;
    private TwoStatePreference mPreAlertingSwitch;
    private TwoStatePreference mPreconditionSwitch;
    private TwoStatePreference mRSRVCCAlertSwitch;
    private TwoStatePreference mRSRVCCMidSwitch;
    private TwoStatePreference mRSRVCCSupportSwitch;
    private EditTextPreference mRegExpire;
    private EditTextPreference mRegSubscribExpire;
    private TwoStatePreference mRegSubscribeEnable;
    private TwoStatePreference mReliable180Switch;
    private TwoStatePreference mResourceAlwaysSwitch;
    private EditTextPreference mRingbackTimer;
    private EditTextPreference mRingingTimer;
    private TwoStatePreference mSIPHeaderCompactForm;
    private TwoStatePreference mSMSoverIPEnable;
    private EditTextPreference mSessionTimer;
    private ListPreference mSessionTimerRefresher;
    private SharedPreferences mSharePref;
    private int mSim;
    private EditTextPreference mTcallTimer;
    private EditTextPreference mTemergregTimer;
    private EditTextPreference mTregTimer;
    private TwoStatePreference mUSSIEnable;
    private ListPreference mVideoAudioMerge;
    private TwoStatePreference mVideoDynamicEnable;
    private TwoStatePreference mVideoEarlySwitch;
    private ListPreference mVideoMaxResolution;
    private EditTextPreference mVideoRR;
    private EditTextPreference mVideoRs;
    private TwoStatePreference mVideoUpgradeSwitch;
    private Preference mVolteVideoCode;
    private ArrayList<CheckBox> mVolteViewList;
    private Preference mVolteVoiceCode;
    private ListPreference mWbDefaultBR;
    private Preference mWbRateSet;
    private EditTextPreference mXcapAuid;
    private EditTextPreference mXcapPort;
    private EMClickEditTextPreference mXcapURI;
    private Handler mUiThread = new Handler();
    private String mResp = null;
    private Context mContext = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSim = getIntent().getIntExtra("key_channel", 0);
        if (this.mSim == 0) {
            addPreferencesFromResource(R.xml.pref_volte_operator_settings0);
        } else {
            addPreferencesFromResource(R.xml.pref_volte_operator_settings1);
        }
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mIpsecHandler = new IpsecHandler(ht.getLooper());
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mSharePref.registerOnSharedPreferenceChangeListener(this);
        this.mIPsecSwitch = (TwoStatePreference) findPreference(KEY_IPSEC_SWITCH + this.mSim);
        this.mIPsecSwitch.setOnPreferenceChangeListener(this);
        this.mRegSubscribeEnable = (TwoStatePreference) findPreference(KEY_REG_SUBSCRIBE + this.mSim);
        this.mRegSubscribeEnable.setOnPreferenceChangeListener(this);
        this.mMWIEnable = (TwoStatePreference) findPreference(KEY_MWI_ENABLE + this.mSim);
        this.mMWIEnable.setOnPreferenceChangeListener(this);
        this.mSIPHeaderCompactForm = (TwoStatePreference) findPreference(KEY_SIP_HEADER_COMPACT + this.mSim);
        this.mSIPHeaderCompactForm.setOnPreferenceChangeListener(this);
        this.mSMSoverIPEnable = (TwoStatePreference) findPreference(KEY_SMS_OVER_IP + this.mSim);
        this.mSMSoverIPEnable.setOnPreferenceChangeListener(this);
        this.mUSSIEnable = (TwoStatePreference) findPreference(KEY_USSI_ENABLE + this.mSim);
        this.mUSSIEnable.setOnPreferenceChangeListener(this);
        this.mInitialRegisterPANI = (TwoStatePreference) findPreference(KEY_INITIAL_REGISTER_PANI + this.mSim);
        this.mInitialRegisterPANI.setOnPreferenceChangeListener(this);
        this.mNamedTelEvent = (TwoStatePreference) findPreference(KEY_NAMED_TEL_EVENT_ENABLE + this.mSim);
        this.mNamedTelEvent.setOnPreferenceChangeListener(this);
        this.mIntegrityAlgorithm = findPreference(KEY_INTEGRITY_ALGORITHM + this.mSim);
        this.mIntegrityAlgorithm.setOnPreferenceClickListener(this);
        this.mCipherAlgorithm = findPreference(KEY_CIPHER_ALGORITHM + this.mSim);
        this.mCipherAlgorithm.setOnPreferenceClickListener(this);
        this.mRegSubscribExpire = (EditTextPreference) findPreference(KEY_REG_SUBSCRIB_EXPIRE + this.mSim);
        this.mRegSubscribExpire.setOnPreferenceChangeListener(this);
        this.mMwiSubscribExpire = (EditTextPreference) findPreference(KEY_MWI_SUBSCRIBE_EXPIRE + this.mSim);
        this.mMwiSubscribExpire.setOnPreferenceChangeListener(this);
        this.mMTU = (EditTextPreference) findPreference(KEY_MTU + this.mSim);
        this.mMTU.setOnPreferenceChangeListener(this);
        this.mPcscfPort = (EditTextPreference) findPreference(KEY_PCSCF_PORT + this.mSim);
        this.mPcscfPort.setOnPreferenceChangeListener(this);
        this.mRegExpire = (EditTextPreference) findPreference(KEY_REG_EXPIRE + this.mSim);
        this.mRegExpire.setOnPreferenceChangeListener(this);
        this.mTregTimer = (EditTextPreference) findPreference(KEY_TREG_TIMER + this.mSim);
        this.mTregTimer.setOnPreferenceChangeListener(this);
        this.mTemergregTimer = (EditTextPreference) findPreference(KEY_TEMERGREG_TIMER + this.mSim);
        this.mTemergregTimer.setOnPreferenceChangeListener(this);
        this.mVolteVoiceCode = findPreference(KEY_VOICE_CODE + this.mSim);
        this.mVolteVoiceCode.setOnPreferenceClickListener(this);
        this.mMinBandWidth = (ListPreference) findPreference(KEY_MIN_BANDWIDTH + this.mSim);
        this.mMinBandWidth.setOnPreferenceChangeListener(this);
        this.mMaxBandWidth = (ListPreference) findPreference(KEY_MAX_BANDWIDTH + this.mSim);
        this.mMaxBandWidth.setOnPreferenceChangeListener(this);
        this.mMinBitRate = (ListPreference) findPreference(KEY_MIN_BITRATE + this.mSim);
        this.mMinBitRate.setOnPreferenceChangeListener(this);
        this.mMaxBitRate = (ListPreference) findPreference(KEY_MAX_BITRATE + this.mSim);
        this.mMaxBitRate.setOnPreferenceChangeListener(this);
        this.mDefaultBitRate = (ListPreference) findPreference(KEY_DEFAULT_BITRATE + this.mSim);
        this.mDefaultBitRate.setOnPreferenceChangeListener(this);
        this.mEvsDefaultBR = (ListPreference) findPreference(KEY_EVS_DEFAULT_BITRATE + this.mSim);
        this.mEvsDefaultBR.setOnPreferenceChangeListener(this);
        this.mWbDefaultBR = (ListPreference) findPreference(KEY_WB_DEFAULT_BITRATE + this.mSim);
        this.mWbDefaultBR.setOnPreferenceChangeListener(this);
        this.mNbDefaultBR = (ListPreference) findPreference(KEY_NB_DEFAULT_BITRATE + this.mSim);
        this.mNbDefaultBR.setOnPreferenceChangeListener(this);
        this.mChannelAwareMode = (ListPreference) findPreference(KEY_CHANNEL_AWARE_MODE + this.mSim);
        this.mChannelAwareMode.setOnPreferenceChangeListener(this);
        this.mEvsRateSet = findPreference(KEY_EVS_RATE_SET + this.mSim);
        this.mEvsRateSet.setOnPreferenceClickListener(this);
        this.mWbRateSet = findPreference(KEY_WB_RATE_SET + this.mSim);
        this.mWbRateSet.setOnPreferenceClickListener(this);
        this.mNbRateSet = findPreference(KEY_NB_RATE_SET + this.mSim);
        this.mNbRateSet.setOnPreferenceClickListener(this);
        this.mMoAmrOaMode = (ListPreference) findPreference(KEY_MO_AMR_OA_MODE + this.mSim);
        this.mMoAmrOaMode.setOnPreferenceChangeListener(this);
        this.mAudioRTPTimeout = (EditTextPreference) findPreference(KEY_AUDIO_RTP_TIMEOUT + this.mSim);
        this.mAudioRTPTimeout.setOnPreferenceChangeListener(this);
        this.mAudioRTCPTimeInterval = (EditTextPreference) findPreference(KEY_AUDIO_RTCP_TIME_INTERNAL + this.mSim);
        this.mAudioRTCPTimeInterval.setOnPreferenceChangeListener(this);
        this.mAudioRS = (EditTextPreference) findPreference(KEY_AUDIO_RS + this.mSim);
        this.mAudioRS.setOnPreferenceChangeListener(this);
        this.mAudioRR = (EditTextPreference) findPreference(KEY_AUDIO_RR + this.mSim);
        this.mAudioRR.setOnPreferenceChangeListener(this);
        this.mVideoRs = (EditTextPreference) findPreference(KEY_VIDEO_RS + this.mSim);
        this.mVideoRs.setOnPreferenceChangeListener(this);
        this.mVideoRR = (EditTextPreference) findPreference(KEY_VIDEO_RR + this.mSim);
        this.mVideoRR.setOnPreferenceChangeListener(this);
        this.mVideoDynamicEnable = (TwoStatePreference) findPreference(KEY_VIDEO_DYNAMIC_ENABLE + this.mSim);
        this.mVideoDynamicEnable.setOnPreferenceChangeListener(this);
        this.mAudioDynamicEnable = (TwoStatePreference) findPreference(KEY_AUDIO_DYNAMIC_ENABLE + this.mSim);
        this.mAudioDynamicEnable.setOnPreferenceChangeListener(this);
        this.mTcallTimer = (EditTextPreference) findPreference(KEY_TCALL_TIMER + this.mSim);
        this.mTcallTimer.setOnPreferenceChangeListener(this);
        this.mRingingTimer = (EditTextPreference) findPreference(KEY_RINGRING_TIMER + this.mSim);
        this.mRingingTimer.setOnPreferenceChangeListener(this);
        this.mRingbackTimer = (EditTextPreference) findPreference(KEY_RINGBACK_TIMER + this.mSim);
        this.mRingbackTimer.setOnPreferenceChangeListener(this);
        this.mNoAnswerTimer = (EditTextPreference) findPreference(KEY_NO_ANSWER_TIMER + this.mSim);
        this.mNoAnswerTimer.setOnPreferenceChangeListener(this);
        this.mSessionTimer = (EditTextPreference) findPreference(KEY_SESSION_TIMER + this.mSim);
        this.mSessionTimer.setOnPreferenceChangeListener(this);
        this.mMTSessionTimer = (EditTextPreference) findPreference(KEY_MT_SESSION_TIMER + this.mSim);
        this.mMTSessionTimer.setOnPreferenceChangeListener(this);
        this.mConferenceURI = (EMClickEditTextPreference) findPreference(KEY_CONFERENCE_URI + this.mSim);
        this.mConferenceURI.setOnPreferenceChangeListener(this);
        this.mVolteVideoCode = findPreference(KEY_VIDEO_CODEC_TYPE + this.mSim);
        this.mVolteVideoCode.setOnPreferenceClickListener(this);
        this.mVideoMaxResolution = (ListPreference) findPreference(KEY_VIDEO_MAX_RESOLUTION + this.mSim);
        this.mVideoMaxResolution.setOnPreferenceChangeListener(this);
        this.mReliable180Switch = (TwoStatePreference) findPreference(KEY_RELIABLE_180_ENABLE + this.mSim);
        this.mReliable180Switch.setOnPreferenceChangeListener(this);
        this.mDirectAlertingSwitch = (TwoStatePreference) findPreference(KEY_DIRECT_ALERTING_ENABLE + this.mSim);
        this.mDirectAlertingSwitch.setOnPreferenceChangeListener(this);
        this.mResourceAlwaysSwitch = (TwoStatePreference) findPreference(KEY_RESOURCE_ALWAYS_READY + this.mSim);
        this.mResourceAlwaysSwitch.setOnPreferenceChangeListener(this);
        this.mPreconditionSwitch = (TwoStatePreference) findPreference(KEY_PRECONDITION_ENABLE + this.mSim);
        this.mPreconditionSwitch.setOnPreferenceChangeListener(this);
        this.mVideoUpgradeSwitch = (TwoStatePreference) findPreference(KEY_VIDEO_UPGRADE_PRECONDITION + this.mSim);
        this.mVideoUpgradeSwitch.setOnPreferenceChangeListener(this);
        this.mVideoEarlySwitch = (TwoStatePreference) findPreference(KEY_VIDEO_EARLY_MEDIA + this.mSim);
        this.mVideoEarlySwitch.setOnPreferenceChangeListener(this);
        this.mAlertingSwitch = (TwoStatePreference) findPreference(KEY_ALERTING + this.mSim);
        this.mAlertingSwitch.setOnPreferenceChangeListener(this);
        this.mMidCallSwitch = (TwoStatePreference) findPreference(KEY_MID_CALL + this.mSim);
        this.mMidCallSwitch.setOnPreferenceChangeListener(this);
        this.mPreAlertingSwitch = (TwoStatePreference) findPreference(KEY_PER_ALERTING + this.mSim);
        this.mPreAlertingSwitch.setOnPreferenceChangeListener(this);
        this.mRSRVCCSupportSwitch = (TwoStatePreference) findPreference(KEY_RSRVCC_SUPPORT + this.mSim);
        this.mRSRVCCSupportSwitch.setOnPreferenceChangeListener(this);
        this.mRSRVCCAlertSwitch = (TwoStatePreference) findPreference(KEY_RSRVCC_ALERTING + this.mSim);
        this.mRSRVCCAlertSwitch.setOnPreferenceChangeListener(this);
        this.mRSRVCCMidSwitch = (TwoStatePreference) findPreference(KEY_RSRVCC_MID_CALL + this.mSim);
        this.mRSRVCCMidSwitch.setOnPreferenceChangeListener(this);
        this.mNationalURL = (ListPreference) findPreference(KEY_NATIONAL_URL_FORMAT + this.mSim);
        this.mNationalURL.setOnPreferenceChangeListener(this);
        this.mInternationalURL = (ListPreference) findPreference(KEY_INTERNATIONAL_URL_FORMAT + this.mSim);
        this.mInternationalURL.setOnPreferenceChangeListener(this);
        this.mSessionTimerRefresher = (ListPreference) findPreference(KEY_SESSION_TIMER_REFRESHER + this.mSim);
        this.mSessionTimerRefresher.setOnPreferenceChangeListener(this);
        this.mMTSessionTimerRefresher = (ListPreference) findPreference(KEY_MT_SESSION_TIMER_REFRESHER + this.mSim);
        this.mMTSessionTimerRefresher.setOnPreferenceChangeListener(this);
        this.mVideoAudioMerge = (ListPreference) findPreference(KEY_VIDEO_AUDIO_MERGE + this.mSim);
        this.mVideoAudioMerge.setOnPreferenceChangeListener(this);
        this.mBsfURI = (EMClickEditTextPreference) findPreference(KEY_BSF_URI + this.mSim);
        this.mBsfURI.setOnPreferenceChangeListener(this);
        this.mBsfPort = (EditTextPreference) findPreference(KEY_BSF_PORT + this.mSim);
        this.mBsfPort.setOnPreferenceChangeListener(this);
        this.mXcapURI = (EMClickEditTextPreference) findPreference(KEY_XCAP_URI + this.mSim);
        this.mXcapURI.setOnPreferenceChangeListener(this);
        this.mXcapPort = (EditTextPreference) findPreference(KEY_XCAP_PORT + this.mSim);
        this.mXcapPort.setOnPreferenceChangeListener(this);
        this.mXcapAuid = (EditTextPreference) findPreference(KEY_XCAP_AUID + this.mSim);
        this.mXcapAuid.setOnPreferenceChangeListener(this);
        this.mLocalCallSwitch = (TwoStatePreference) findPreference(KEY_LOCAL_CALL_WAITING + this.mSim);
        this.mLocalCallSwitch.setOnPreferenceChangeListener(this);
        this.mDnsSrvEnableSwitch = (TwoStatePreference) findPreference(KEY_DNS_SRV_ENABLE + this.mSim);
        this.mDnsSrvEnableSwitch.setOnPreferenceChangeListener(this);
        this.mHttpsEnableSwitch = (TwoStatePreference) findPreference(KEY_HTTPS_ENABLE + this.mSim);
        this.mHttpsEnableSwitch.setOnPreferenceChangeListener(this);
        this.mActivateCfnlSwitch = (TwoStatePreference) findPreference(KEY_ACTIVATE_CFNL_ENABLE + this.mSim);
        this.mActivateCfnlSwitch.setOnPreferenceChangeListener(this);
        this.mHttpPutMediaSwitch = (TwoStatePreference) findPreference(KEY_HTTP_PUT_MEDIA + this.mSim);
        this.mHttpPutMediaSwitch.setOnPreferenceChangeListener(this);
        this.mIPPriority = (ListPreference) findPreference(KEY_IP_PRIORITY + this.mSim);
        this.mIPPriority.setOnPreferenceChangeListener(this);
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    @Override // android.app.Activity
    public void onStart() {
        TwoStatePreference twoStatePreference = this.mIPsecSwitch;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            Message getIpsec = this.mIpsecHandler.obtainMessage(0);
            this.mIpsecHandler.sendMessage(getIpsec);
        }
        TwoStatePreference twoStatePreference2 = this.mRegSubscribeEnable;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message getRegSubscribe = this.mIpsecHandler.obtainMessage(11);
            this.mIpsecHandler.sendMessage(getRegSubscribe);
        }
        TwoStatePreference twoStatePreference3 = this.mMWIEnable;
        if (twoStatePreference3 != null && twoStatePreference3.isEnabled()) {
            Message getMWI = this.mIpsecHandler.obtainMessage(13);
            this.mIpsecHandler.sendMessage(getMWI);
        }
        TwoStatePreference twoStatePreference4 = this.mSIPHeaderCompactForm;
        if (twoStatePreference4 != null && twoStatePreference4.isEnabled()) {
            Message getSIPHeaderCompact = this.mIpsecHandler.obtainMessage(17);
            this.mIpsecHandler.sendMessage(getSIPHeaderCompact);
        }
        TwoStatePreference twoStatePreference5 = this.mSMSoverIPEnable;
        if (twoStatePreference5 != null && twoStatePreference5.isEnabled()) {
            Message getSMSoverIP = this.mIpsecHandler.obtainMessage(19);
            this.mIpsecHandler.sendMessage(getSMSoverIP);
        }
        TwoStatePreference twoStatePreference6 = this.mUSSIEnable;
        if (twoStatePreference6 != null && twoStatePreference6.isEnabled()) {
            Message getUSSI = this.mIpsecHandler.obtainMessage(21);
            this.mIpsecHandler.sendMessage(getUSSI);
        }
        TwoStatePreference twoStatePreference7 = this.mInitialRegisterPANI;
        if (twoStatePreference7 != null && twoStatePreference7.isEnabled()) {
            Message getInitialRegister = this.mIpsecHandler.obtainMessage(23);
            this.mIpsecHandler.sendMessage(getInitialRegister);
        }
        EditTextPreference editTextPreference = this.mRegSubscribExpire;
        if (editTextPreference != null && editTextPreference.isEnabled()) {
            Message getRegSubscribExpire = this.mIpsecHandler.obtainMessage(25);
            this.mIpsecHandler.sendMessage(getRegSubscribExpire);
        }
        EditTextPreference editTextPreference2 = this.mMwiSubscribExpire;
        if (editTextPreference2 != null && editTextPreference2.isEnabled()) {
            Message getMwiSubscribExpire = this.mIpsecHandler.obtainMessage(27);
            this.mIpsecHandler.sendMessage(getMwiSubscribExpire);
        }
        EditTextPreference editTextPreference3 = this.mMTU;
        if (editTextPreference3 != null && editTextPreference3.isEnabled()) {
            Message getMTU = this.mIpsecHandler.obtainMessage(29);
            this.mIpsecHandler.sendMessage(getMTU);
        }
        EditTextPreference editTextPreference4 = this.mPcscfPort;
        if (editTextPreference4 != null && editTextPreference4.isEnabled()) {
            Message getPcscfPort = this.mIpsecHandler.obtainMessage(31);
            this.mIpsecHandler.sendMessage(getPcscfPort);
        }
        EditTextPreference editTextPreference5 = this.mRegExpire;
        if (editTextPreference5 != null && editTextPreference5.isEnabled()) {
            Message getRegExpire = this.mIpsecHandler.obtainMessage(33);
            this.mIpsecHandler.sendMessage(getRegExpire);
        }
        EditTextPreference editTextPreference6 = this.mTregTimer;
        if (editTextPreference6 != null && editTextPreference6.isEnabled()) {
            Message getTregTimer = this.mIpsecHandler.obtainMessage(35);
            this.mIpsecHandler.sendMessage(getTregTimer);
        }
        EditTextPreference editTextPreference7 = this.mTemergregTimer;
        if (editTextPreference7 != null && editTextPreference7.isEnabled()) {
            Message getTemergregTimer = this.mIpsecHandler.obtainMessage(37);
            this.mIpsecHandler.sendMessage(getTemergregTimer);
        }
        ListPreference listPreference = this.mMinBandWidth;
        if (listPreference != null && listPreference.isEnabled()) {
            Message getMinBandWidth = this.mIpsecHandler.obtainMessage(47);
            this.mIpsecHandler.sendMessage(getMinBandWidth);
        }
        ListPreference listPreference2 = this.mMaxBandWidth;
        if (listPreference2 != null && listPreference2.isEnabled()) {
            Message getMaxBandWidth = this.mIpsecHandler.obtainMessage(49);
            this.mIpsecHandler.sendMessage(getMaxBandWidth);
        }
        ListPreference listPreference3 = this.mMinBitRate;
        if (listPreference3 != null && listPreference3.isEnabled()) {
            Message getMinBitRate = this.mIpsecHandler.obtainMessage(51);
            this.mIpsecHandler.sendMessage(getMinBitRate);
        }
        ListPreference listPreference4 = this.mMaxBitRate;
        if (listPreference4 != null && listPreference4.isEnabled()) {
            Message getMaxBitRate = this.mIpsecHandler.obtainMessage(53);
            this.mIpsecHandler.sendMessage(getMaxBitRate);
        }
        ListPreference listPreference5 = this.mDefaultBitRate;
        if (listPreference5 != null && listPreference5.isEnabled()) {
            Message getDefaultBitRate = this.mIpsecHandler.obtainMessage(55);
            this.mIpsecHandler.sendMessage(getDefaultBitRate);
        }
        ListPreference listPreference6 = this.mEvsDefaultBR;
        if (listPreference6 != null && listPreference6.isEnabled()) {
            Message getEvsDefaultBr = this.mIpsecHandler.obtainMessage(57);
            this.mIpsecHandler.sendMessage(getEvsDefaultBr);
        }
        ListPreference listPreference7 = this.mWbDefaultBR;
        if (listPreference7 != null && listPreference7.isEnabled()) {
            Message getWbDefaultBr = this.mIpsecHandler.obtainMessage(59);
            this.mIpsecHandler.sendMessage(getWbDefaultBr);
        }
        ListPreference listPreference8 = this.mNbDefaultBR;
        if (listPreference8 != null && listPreference8.isEnabled()) {
            Message getNbDefaultBr = this.mIpsecHandler.obtainMessage(61);
            this.mIpsecHandler.sendMessage(getNbDefaultBr);
        }
        ListPreference listPreference9 = this.mChannelAwareMode;
        if (listPreference9 != null && listPreference9.isEnabled()) {
            Message getAwareMode = this.mIpsecHandler.obtainMessage(63);
            this.mIpsecHandler.sendMessage(getAwareMode);
        }
        ListPreference listPreference10 = this.mMoAmrOaMode;
        if (listPreference10 != null && listPreference10.isEnabled()) {
            Message getMoAmrOaMode = this.mIpsecHandler.obtainMessage(71);
            this.mIpsecHandler.sendMessage(getMoAmrOaMode);
        }
        TwoStatePreference twoStatePreference8 = this.mNamedTelEvent;
        if (twoStatePreference8 != null && twoStatePreference8.isEnabled()) {
            Message getNamedTelEvent = this.mIpsecHandler.obtainMessage(73);
            this.mIpsecHandler.sendMessage(getNamedTelEvent);
        }
        EditTextPreference editTextPreference8 = this.mAudioRTPTimeout;
        if (editTextPreference8 != null && editTextPreference8.isEnabled()) {
            Message getudioRTPTimeout = this.mIpsecHandler.obtainMessage(75);
            this.mIpsecHandler.sendMessage(getudioRTPTimeout);
        }
        EditTextPreference editTextPreference9 = this.mAudioRTCPTimeInterval;
        if (editTextPreference9 != null && editTextPreference9.isEnabled()) {
            Message getAudioRTCPTimeInterval = this.mIpsecHandler.obtainMessage(77);
            this.mIpsecHandler.sendMessage(getAudioRTCPTimeInterval);
        }
        EditTextPreference editTextPreference10 = this.mAudioRS;
        if (editTextPreference10 != null && editTextPreference10.isEnabled()) {
            Message getAudioRS = this.mIpsecHandler.obtainMessage(79);
            this.mIpsecHandler.sendMessage(getAudioRS);
        }
        EditTextPreference editTextPreference11 = this.mAudioRR;
        if (editTextPreference11 != null && editTextPreference11.isEnabled()) {
            Message getAudioRR = this.mIpsecHandler.obtainMessage(81);
            this.mIpsecHandler.sendMessage(getAudioRR);
        }
        EditTextPreference editTextPreference12 = this.mVideoRs;
        if (editTextPreference12 != null && editTextPreference12.isEnabled()) {
            Message getVideoRs = this.mIpsecHandler.obtainMessage(83);
            this.mIpsecHandler.sendMessage(getVideoRs);
        }
        EditTextPreference editTextPreference13 = this.mVideoRR;
        if (editTextPreference13 != null && editTextPreference13.isEnabled()) {
            Message getVideoRR = this.mIpsecHandler.obtainMessage(85);
            this.mIpsecHandler.sendMessage(getVideoRR);
        }
        EditTextPreference editTextPreference14 = this.mTcallTimer;
        if (editTextPreference14 != null && editTextPreference14.isEnabled()) {
            Message getTcallTimer = this.mIpsecHandler.obtainMessage(87);
            this.mIpsecHandler.sendMessage(getTcallTimer);
        }
        EditTextPreference editTextPreference15 = this.mRingingTimer;
        if (editTextPreference15 != null && editTextPreference15.isEnabled()) {
            Message getRingingTimer = this.mIpsecHandler.obtainMessage(89);
            this.mIpsecHandler.sendMessage(getRingingTimer);
        }
        EditTextPreference editTextPreference16 = this.mRingbackTimer;
        if (editTextPreference16 != null && editTextPreference16.isEnabled()) {
            Message getRingbackTimer = this.mIpsecHandler.obtainMessage(91);
            this.mIpsecHandler.sendMessage(getRingbackTimer);
        }
        EditTextPreference editTextPreference17 = this.mNoAnswerTimer;
        if (editTextPreference17 != null && editTextPreference17.isEnabled()) {
            Message getNoAnswerTimer = this.mIpsecHandler.obtainMessage(93);
            this.mIpsecHandler.sendMessage(getNoAnswerTimer);
        }
        EditTextPreference editTextPreference18 = this.mSessionTimer;
        if (editTextPreference18 != null && editTextPreference18.isEnabled()) {
            Message getSessionTimer = this.mIpsecHandler.obtainMessage(95);
            this.mIpsecHandler.sendMessage(getSessionTimer);
        }
        EditTextPreference editTextPreference19 = this.mMTSessionTimer;
        if (editTextPreference19 != null && editTextPreference19.isEnabled()) {
            Message getMTSessionTimer = this.mIpsecHandler.obtainMessage(97);
            this.mIpsecHandler.sendMessage(getMTSessionTimer);
        }
        EMClickEditTextPreference eMClickEditTextPreference = this.mConferenceURI;
        if (eMClickEditTextPreference != null && eMClickEditTextPreference.isEnabled()) {
            Message getConferenceURI = this.mIpsecHandler.obtainMessage(99);
            this.mIpsecHandler.sendMessage(getConferenceURI);
        }
        TwoStatePreference twoStatePreference9 = this.mReliable180Switch;
        if (twoStatePreference9 != null && twoStatePreference9.isEnabled()) {
            Message getReliable180 = this.mIpsecHandler.obtainMessage(103);
            this.mIpsecHandler.sendMessage(getReliable180);
        }
        TwoStatePreference twoStatePreference10 = this.mDirectAlertingSwitch;
        if (twoStatePreference10 != null && twoStatePreference10.isEnabled()) {
            Message getDirectAlerting = this.mIpsecHandler.obtainMessage(105);
            this.mIpsecHandler.sendMessage(getDirectAlerting);
        }
        TwoStatePreference twoStatePreference11 = this.mResourceAlwaysSwitch;
        if (twoStatePreference11 != null && twoStatePreference11.isEnabled()) {
            Message getResourceAlways = this.mIpsecHandler.obtainMessage(107);
            this.mIpsecHandler.sendMessage(getResourceAlways);
        }
        TwoStatePreference twoStatePreference12 = this.mPreconditionSwitch;
        if (twoStatePreference12 != null && twoStatePreference12.isEnabled()) {
            Message getPrecondition = this.mIpsecHandler.obtainMessage(109);
            this.mIpsecHandler.sendMessage(getPrecondition);
        }
        TwoStatePreference twoStatePreference13 = this.mVideoUpgradeSwitch;
        if (twoStatePreference13 != null && twoStatePreference13.isEnabled()) {
            Message getVideoUpgrad = this.mIpsecHandler.obtainMessage(111);
            this.mIpsecHandler.sendMessage(getVideoUpgrad);
        }
        TwoStatePreference twoStatePreference14 = this.mVideoEarlySwitch;
        if (twoStatePreference14 != null && twoStatePreference14.isEnabled()) {
            Message getVideoEarly = this.mIpsecHandler.obtainMessage(113);
            this.mIpsecHandler.sendMessage(getVideoEarly);
        }
        TwoStatePreference twoStatePreference15 = this.mAlertingSwitch;
        if (twoStatePreference15 != null && twoStatePreference15.isEnabled()) {
            Message getAlerting = this.mIpsecHandler.obtainMessage(115);
            this.mIpsecHandler.sendMessage(getAlerting);
        }
        TwoStatePreference twoStatePreference16 = this.mMidCallSwitch;
        if (twoStatePreference16 != null && twoStatePreference16.isEnabled()) {
            Message getMidCall = this.mIpsecHandler.obtainMessage(117);
            this.mIpsecHandler.sendMessage(getMidCall);
        }
        TwoStatePreference twoStatePreference17 = this.mPreAlertingSwitch;
        if (twoStatePreference17 != null && twoStatePreference17.isEnabled()) {
            Message getPreAlerting = this.mIpsecHandler.obtainMessage(119);
            this.mIpsecHandler.sendMessage(getPreAlerting);
        }
        TwoStatePreference twoStatePreference18 = this.mRSRVCCSupportSwitch;
        if (twoStatePreference18 != null && twoStatePreference18.isEnabled()) {
            Message getRSRVCCSupport = this.mIpsecHandler.obtainMessage(MSG_GET_RSRVCC_SUPPORT);
            this.mIpsecHandler.sendMessage(getRSRVCCSupport);
        }
        TwoStatePreference twoStatePreference19 = this.mRSRVCCAlertSwitch;
        if (twoStatePreference19 != null && twoStatePreference19.isEnabled()) {
            Message getRSRVCCAlert = this.mIpsecHandler.obtainMessage(MSG_GET_RSRVCC_ALERTING);
            this.mIpsecHandler.sendMessage(getRSRVCCAlert);
        }
        TwoStatePreference twoStatePreference20 = this.mRSRVCCMidSwitch;
        if (twoStatePreference20 != null && twoStatePreference20.isEnabled()) {
            Message getRSRVCCMid = this.mIpsecHandler.obtainMessage(MSG_GET_RSRVCC_MID_CALL);
            this.mIpsecHandler.sendMessage(getRSRVCCMid);
        }
        ListPreference listPreference11 = this.mVideoMaxResolution;
        if (listPreference11 != null && listPreference11.isEnabled()) {
            Message getVideoMaxResolution = this.mIpsecHandler.obtainMessage(MSG_GET_VIDEO_MAX_RESOLUTION);
            this.mIpsecHandler.sendMessage(getVideoMaxResolution);
        }
        ListPreference listPreference12 = this.mNationalURL;
        if (listPreference12 != null && listPreference12.isEnabled()) {
            Message getNationalURL = this.mIpsecHandler.obtainMessage(127);
            this.mIpsecHandler.sendMessage(getNationalURL);
        }
        ListPreference listPreference13 = this.mInternationalURL;
        if (listPreference13 != null && listPreference13.isEnabled()) {
            Message getInternationalURL = this.mIpsecHandler.obtainMessage(129);
            this.mIpsecHandler.sendMessage(getInternationalURL);
        }
        ListPreference listPreference14 = this.mSessionTimerRefresher;
        if (listPreference14 != null && listPreference14.isEnabled()) {
            Message getSessionTimerRefresher = this.mIpsecHandler.obtainMessage(131);
            this.mIpsecHandler.sendMessage(getSessionTimerRefresher);
        }
        ListPreference listPreference15 = this.mMTSessionTimerRefresher;
        if (listPreference15 != null && listPreference15.isEnabled()) {
            Message getTimerRefresher = this.mIpsecHandler.obtainMessage(133);
            this.mIpsecHandler.sendMessage(getTimerRefresher);
        }
        ListPreference listPreference16 = this.mVideoAudioMerge;
        if (listPreference16 != null && listPreference16.isEnabled()) {
            Message getVideoAudioMerge = this.mIpsecHandler.obtainMessage(135);
            this.mIpsecHandler.sendMessage(getVideoAudioMerge);
        }
        TwoStatePreference twoStatePreference21 = this.mAudioDynamicEnable;
        if (twoStatePreference21 != null && twoStatePreference21.isEnabled()) {
            Message getAudioDynamic = this.mIpsecHandler.obtainMessage(137);
            this.mIpsecHandler.sendMessage(getAudioDynamic);
        }
        TwoStatePreference twoStatePreference22 = this.mVideoDynamicEnable;
        if (twoStatePreference22 != null && twoStatePreference22.isEnabled()) {
            Message getVideoDynamic = this.mIpsecHandler.obtainMessage(139);
            this.mIpsecHandler.sendMessage(getVideoDynamic);
        }
        EMClickEditTextPreference eMClickEditTextPreference2 = this.mBsfURI;
        if (eMClickEditTextPreference2 != null && eMClickEditTextPreference2.isEnabled()) {
            Message getBsfURI = this.mIpsecHandler.obtainMessage(141);
            this.mIpsecHandler.sendMessage(getBsfURI);
        }
        EditTextPreference editTextPreference20 = this.mBsfPort;
        if (editTextPreference20 != null && editTextPreference20.isEnabled()) {
            Message getBsfPort = this.mIpsecHandler.obtainMessage(143);
            this.mIpsecHandler.sendMessage(getBsfPort);
        }
        EMClickEditTextPreference eMClickEditTextPreference3 = this.mXcapURI;
        if (eMClickEditTextPreference3 != null && eMClickEditTextPreference3.isEnabled()) {
            Message getXcapURI = this.mIpsecHandler.obtainMessage(145);
            this.mIpsecHandler.sendMessage(getXcapURI);
        }
        EditTextPreference editTextPreference21 = this.mXcapPort;
        if (editTextPreference21 != null && editTextPreference21.isEnabled()) {
            Message getXcapPort = this.mIpsecHandler.obtainMessage(MSG_GET_XCAP_PORT);
            this.mIpsecHandler.sendMessage(getXcapPort);
        }
        EditTextPreference editTextPreference22 = this.mXcapAuid;
        if (editTextPreference22 != null && editTextPreference22.isEnabled()) {
            Message getXcapAuid = this.mIpsecHandler.obtainMessage(MSG_GET_XCAP_AUID);
            this.mIpsecHandler.sendMessage(getXcapAuid);
        }
        TwoStatePreference twoStatePreference23 = this.mLocalCallSwitch;
        if (twoStatePreference23 != null && twoStatePreference23.isEnabled()) {
            Message getLocalCall = this.mIpsecHandler.obtainMessage(151);
            this.mIpsecHandler.sendMessage(getLocalCall);
        }
        TwoStatePreference twoStatePreference24 = this.mDnsSrvEnableSwitch;
        if (twoStatePreference24 != null && twoStatePreference24.isEnabled()) {
            Message getDnsSrvEnable = this.mIpsecHandler.obtainMessage(153);
            this.mIpsecHandler.sendMessage(getDnsSrvEnable);
        }
        TwoStatePreference twoStatePreference25 = this.mHttpsEnableSwitch;
        if (twoStatePreference25 != null && twoStatePreference25.isEnabled()) {
            Message getHttpsEnable = this.mIpsecHandler.obtainMessage(155);
            this.mIpsecHandler.sendMessage(getHttpsEnable);
        }
        TwoStatePreference twoStatePreference26 = this.mActivateCfnlSwitch;
        if (twoStatePreference26 != null && twoStatePreference26.isEnabled()) {
            Message getActivateCfnl = this.mIpsecHandler.obtainMessage(157);
            this.mIpsecHandler.sendMessage(getActivateCfnl);
        }
        TwoStatePreference twoStatePreference27 = this.mHttpPutMediaSwitch;
        if (twoStatePreference27 != null && twoStatePreference27.isEnabled()) {
            Message getHttpPutMedia = this.mIpsecHandler.obtainMessage(159);
            this.mIpsecHandler.sendMessage(getHttpPutMedia);
        }
        ListPreference listPreference17 = this.mIPPriority;
        if (listPreference17 != null && listPreference17.isEnabled()) {
            Message getIPPriority = this.mIpsecHandler.obtainMessage(MSG_GET_IP_PRIORITY);
            this.mIpsecHandler.sendMessage(getIPPriority);
        }
        Message getVoiceCode = this.mIpsecHandler.obtainMessage(45);
        this.mIpsecHandler.sendMessage(getVoiceCode);
        Message getIntegrityAlgorithm = this.mIpsecHandler.obtainMessage(7);
        this.mIpsecHandler.sendMessage(getIntegrityAlgorithm);
        Message getCipherAlgorithm = this.mIpsecHandler.obtainMessage(9);
        this.mIpsecHandler.sendMessage(getCipherAlgorithm);
        Message getVideoCodeType = this.mIpsecHandler.obtainMessage(101);
        this.mIpsecHandler.sendMessage(getVideoCodeType);
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
        Log.d(TAG, "onPreferenceChange newValue: " + newValue);
        TwoStatePreference twoStatePreference = this.mIPsecSwitch;
        if (pref == twoStatePreference) {
            if (twoStatePreference.isChecked()) {
                Message setIpsecClose = this.mIpsecHandler.obtainMessage(1, "0");
                this.mIpsecHandler.sendMessage(setIpsecClose);
            } else {
                Message setIpsecOpen = this.mIpsecHandler.obtainMessage(1, "1");
                this.mIpsecHandler.sendMessage(setIpsecOpen);
            }
        } else {
            TwoStatePreference twoStatePreference2 = this.mRegSubscribeEnable;
            if (pref == twoStatePreference2) {
                if (twoStatePreference2.isChecked()) {
                    Message setRegSubscribeClose = this.mIpsecHandler.obtainMessage(12, "0");
                    this.mIpsecHandler.sendMessage(setRegSubscribeClose);
                } else {
                    Message setRegSubscribeOpen = this.mIpsecHandler.obtainMessage(12, "1");
                    this.mIpsecHandler.sendMessage(setRegSubscribeOpen);
                }
            } else {
                TwoStatePreference twoStatePreference3 = this.mMWIEnable;
                if (pref == twoStatePreference3) {
                    if (twoStatePreference3.isChecked()) {
                        Message setMWIClose = this.mIpsecHandler.obtainMessage(14, "0");
                        this.mIpsecHandler.sendMessage(setMWIClose);
                    } else {
                        Message setMWIOpen = this.mIpsecHandler.obtainMessage(14, "1");
                        this.mIpsecHandler.sendMessage(setMWIOpen);
                    }
                } else {
                    TwoStatePreference twoStatePreference4 = this.mSIPHeaderCompactForm;
                    if (pref == twoStatePreference4) {
                        if (twoStatePreference4.isChecked()) {
                            Message setSIPHeaderCompactClose = this.mIpsecHandler.obtainMessage(18, "0");
                            this.mIpsecHandler.sendMessage(setSIPHeaderCompactClose);
                        } else {
                            Message setSIPHeaderCompactOpen = this.mIpsecHandler.obtainMessage(18, "1");
                            this.mIpsecHandler.sendMessage(setSIPHeaderCompactOpen);
                        }
                    } else {
                        TwoStatePreference twoStatePreference5 = this.mSMSoverIPEnable;
                        if (pref == twoStatePreference5) {
                            if (twoStatePreference5.isChecked()) {
                                Message setSMSoverIPClose = this.mIpsecHandler.obtainMessage(20, "0");
                                this.mIpsecHandler.sendMessage(setSMSoverIPClose);
                            } else {
                                Message setSMSoverIPOpen = this.mIpsecHandler.obtainMessage(20, "1");
                                this.mIpsecHandler.sendMessage(setSMSoverIPOpen);
                            }
                        } else {
                            TwoStatePreference twoStatePreference6 = this.mUSSIEnable;
                            if (pref == twoStatePreference6) {
                                if (twoStatePreference6.isChecked()) {
                                    Message setUSSIClose = this.mIpsecHandler.obtainMessage(22, "0");
                                    this.mIpsecHandler.sendMessage(setUSSIClose);
                                } else {
                                    Message setUSSIOpen = this.mIpsecHandler.obtainMessage(22, "1");
                                    this.mIpsecHandler.sendMessage(setUSSIOpen);
                                }
                            } else {
                                TwoStatePreference twoStatePreference7 = this.mInitialRegisterPANI;
                                if (pref == twoStatePreference7) {
                                    if (twoStatePreference7.isChecked()) {
                                        Message setInitialRegisterClose = this.mIpsecHandler.obtainMessage(24, "0");
                                        this.mIpsecHandler.sendMessage(setInitialRegisterClose);
                                    } else {
                                        Message setInitialRegisterOpen = this.mIpsecHandler.obtainMessage(24, "1");
                                        this.mIpsecHandler.sendMessage(setInitialRegisterOpen);
                                    }
                                } else {
                                    TwoStatePreference twoStatePreference8 = this.mNamedTelEvent;
                                    if (pref == twoStatePreference8) {
                                        if (twoStatePreference8.isChecked()) {
                                            Message setNamedTelEventClose = this.mIpsecHandler.obtainMessage(74, "0");
                                            this.mIpsecHandler.sendMessage(setNamedTelEventClose);
                                        } else {
                                            Message setNamedTelEventOpen = this.mIpsecHandler.obtainMessage(74, "1");
                                            this.mIpsecHandler.sendMessage(setNamedTelEventOpen);
                                        }
                                    } else if (pref == this.mIntegrityAlgorithm) {
                                        Log.d(TAG, "mIntegrityAlgorithm");
                                        Message setIntegrityAlgorithm = this.mIpsecHandler.obtainMessage(8);
                                        this.mIpsecHandler.sendMessage(setIntegrityAlgorithm);
                                    } else if (pref == this.mCipherAlgorithm) {
                                        Log.d(TAG, "mCipherAlgorithm");
                                        Message setCipherAlgorithm = this.mIpsecHandler.obtainMessage(10);
                                        this.mIpsecHandler.sendMessage(setCipherAlgorithm);
                                    } else if (pref == this.mRegSubscribExpire) {
                                        Log.d(TAG, "mRegSubscribExpire");
                                        Message setRegSubscribExpire = this.mIpsecHandler.obtainMessage(26, newValue);
                                        this.mIpsecHandler.sendMessage(setRegSubscribExpire);
                                    } else if (pref == this.mMwiSubscribExpire) {
                                        Log.d(TAG, "mMwiSubscribExpire");
                                        Message setMwiSubscribExpire = this.mIpsecHandler.obtainMessage(28, newValue);
                                        this.mIpsecHandler.sendMessage(setMwiSubscribExpire);
                                    } else if (pref == this.mMTU) {
                                        Log.d(TAG, "mMTU");
                                        Message setMTU = this.mIpsecHandler.obtainMessage(30, newValue);
                                        this.mIpsecHandler.sendMessage(setMTU);
                                    } else if (pref == this.mPcscfPort) {
                                        Log.d(TAG, "mPcscfPort");
                                        Message setPcscfPort = this.mIpsecHandler.obtainMessage(32, newValue);
                                        this.mIpsecHandler.sendMessage(setPcscfPort);
                                    } else if (pref == this.mRegExpire) {
                                        Log.d(TAG, "mRegExpire");
                                        Message setRegExpire = this.mIpsecHandler.obtainMessage(34, newValue);
                                        this.mIpsecHandler.sendMessage(setRegExpire);
                                    } else if (pref == this.mTregTimer) {
                                        Log.d(TAG, "mTregTimer");
                                        Message setTregTimer = this.mIpsecHandler.obtainMessage(36, newValue);
                                        this.mIpsecHandler.sendMessage(setTregTimer);
                                    } else if (pref == this.mTemergregTimer) {
                                        Log.d(TAG, "mTemergregTimer");
                                        Message setTemergregTimer = this.mIpsecHandler.obtainMessage(38, newValue);
                                        this.mIpsecHandler.sendMessage(setTemergregTimer);
                                    } else {
                                        if (pref == this.mMinBandWidth) {
                                            Message setMinBandWidth = this.mIpsecHandler.obtainMessage(48, newValue);
                                            this.mIpsecHandler.sendMessage(setMinBandWidth);
                                            return false;
                                        }
                                        if (pref == this.mMaxBandWidth) {
                                            Message setMaxBandWidth = this.mIpsecHandler.obtainMessage(50, newValue);
                                            this.mIpsecHandler.sendMessage(setMaxBandWidth);
                                            return false;
                                        }
                                        if (pref == this.mMinBitRate) {
                                            Message setMinBitRate = this.mIpsecHandler.obtainMessage(52, newValue);
                                            this.mIpsecHandler.sendMessage(setMinBitRate);
                                            return false;
                                        }
                                        if (pref == this.mMaxBitRate) {
                                            Message setMaxBitRate = this.mIpsecHandler.obtainMessage(54, newValue);
                                            this.mIpsecHandler.sendMessage(setMaxBitRate);
                                            return false;
                                        }
                                        if (pref == this.mDefaultBitRate) {
                                            Message setDefaultBitRate = this.mIpsecHandler.obtainMessage(56, newValue);
                                            this.mIpsecHandler.sendMessage(setDefaultBitRate);
                                            return false;
                                        }
                                        if (pref == this.mEvsDefaultBR) {
                                            Message setEvsDefaultBr = this.mIpsecHandler.obtainMessage(58, newValue);
                                            this.mIpsecHandler.sendMessage(setEvsDefaultBr);
                                            return false;
                                        }
                                        if (pref == this.mWbDefaultBR) {
                                            Message setWbDefaultBr = this.mIpsecHandler.obtainMessage(60, newValue);
                                            this.mIpsecHandler.sendMessage(setWbDefaultBr);
                                            return false;
                                        }
                                        if (pref == this.mNbDefaultBR) {
                                            Message setNbDefaultBr = this.mIpsecHandler.obtainMessage(62, newValue);
                                            this.mIpsecHandler.sendMessage(setNbDefaultBr);
                                            return false;
                                        }
                                        if (pref == this.mChannelAwareMode) {
                                            Message setAwreMode = this.mIpsecHandler.obtainMessage(64, newValue);
                                            this.mIpsecHandler.sendMessage(setAwreMode);
                                            return false;
                                        }
                                        if (pref == this.mMoAmrOaMode) {
                                            Message setMoAmrOaMode = this.mIpsecHandler.obtainMessage(72, newValue);
                                            this.mIpsecHandler.sendMessage(setMoAmrOaMode);
                                            return false;
                                        }
                                        if (pref == this.mAudioRTPTimeout) {
                                            Log.d(TAG, "mAudioRTPTimeout");
                                            Message setAudioRTPTimeout = this.mIpsecHandler.obtainMessage(76, newValue);
                                            this.mIpsecHandler.sendMessage(setAudioRTPTimeout);
                                        } else if (pref == this.mAudioRTCPTimeInterval) {
                                            Log.d(TAG, "mAudioRTCPTimeInterval");
                                            Message setAudioRTCPTime = this.mIpsecHandler.obtainMessage(78, newValue);
                                            this.mIpsecHandler.sendMessage(setAudioRTCPTime);
                                        } else if (pref == this.mAudioRS) {
                                            Log.d(TAG, "mAudioRS");
                                            Message setAudioRS = this.mIpsecHandler.obtainMessage(80, newValue);
                                            this.mIpsecHandler.sendMessage(setAudioRS);
                                        } else if (pref == this.mAudioRR) {
                                            Log.d(TAG, "mAudioRR");
                                            Message setAudioRR = this.mIpsecHandler.obtainMessage(82, newValue);
                                            this.mIpsecHandler.sendMessage(setAudioRR);
                                        } else if (pref == this.mVideoRs) {
                                            Log.d(TAG, "mVideoRs");
                                            Message setVideoRs = this.mIpsecHandler.obtainMessage(84, newValue);
                                            this.mIpsecHandler.sendMessage(setVideoRs);
                                        } else if (pref == this.mVideoRR) {
                                            Log.d(TAG, "mVideoRR");
                                            Message setVideoRR = this.mIpsecHandler.obtainMessage(86, newValue);
                                            this.mIpsecHandler.sendMessage(setVideoRR);
                                        } else if (pref == this.mTcallTimer) {
                                            Log.d(TAG, "mTcallTimer");
                                            Message setTcallTimer = this.mIpsecHandler.obtainMessage(88, newValue);
                                            this.mIpsecHandler.sendMessage(setTcallTimer);
                                        } else if (pref == this.mRingingTimer) {
                                            Log.d(TAG, "mRingingTimer");
                                            Message setRingingTimer = this.mIpsecHandler.obtainMessage(90, newValue);
                                            this.mIpsecHandler.sendMessage(setRingingTimer);
                                        } else if (pref == this.mRingbackTimer) {
                                            Log.d(TAG, "mRingbackTimer");
                                            Message setRingbackTimer = this.mIpsecHandler.obtainMessage(92, newValue);
                                            this.mIpsecHandler.sendMessage(setRingbackTimer);
                                        } else if (pref == this.mNoAnswerTimer) {
                                            Log.d(TAG, "mNoAnswerTimer");
                                            Message setNoAnswerTimer = this.mIpsecHandler.obtainMessage(94, newValue);
                                            this.mIpsecHandler.sendMessage(setNoAnswerTimer);
                                        } else if (pref == this.mSessionTimer) {
                                            Log.d(TAG, "mSessionTimer");
                                            Message setSessionTimer = this.mIpsecHandler.obtainMessage(96, newValue);
                                            this.mIpsecHandler.sendMessage(setSessionTimer);
                                        } else if (pref == this.mMTSessionTimer) {
                                            Log.d(TAG, "mMTSessionTimer");
                                            Message setMTSessionTimer = this.mIpsecHandler.obtainMessage(98, newValue);
                                            this.mIpsecHandler.sendMessage(setMTSessionTimer);
                                        } else if (pref == this.mConferenceURI) {
                                            Log.d(TAG, "mConferenceURI");
                                            Message setConferenceURI = this.mIpsecHandler.obtainMessage(100, newValue);
                                            this.mIpsecHandler.sendMessage(setConferenceURI);
                                        } else {
                                            TwoStatePreference twoStatePreference9 = this.mReliable180Switch;
                                            if (pref == twoStatePreference9) {
                                                if (twoStatePreference9.isChecked()) {
                                                    Message setReliable180Close = this.mIpsecHandler.obtainMessage(104, "0");
                                                    this.mIpsecHandler.sendMessage(setReliable180Close);
                                                } else {
                                                    Message setReliable180Open = this.mIpsecHandler.obtainMessage(104, "1");
                                                    this.mIpsecHandler.sendMessage(setReliable180Open);
                                                }
                                            } else {
                                                TwoStatePreference twoStatePreference10 = this.mDirectAlertingSwitch;
                                                if (pref == twoStatePreference10) {
                                                    if (twoStatePreference10.isChecked()) {
                                                        Message setDirectAlertClose = this.mIpsecHandler.obtainMessage(106, "0");
                                                        this.mIpsecHandler.sendMessage(setDirectAlertClose);
                                                    } else {
                                                        Message setDirectAlertOpen = this.mIpsecHandler.obtainMessage(106, "1");
                                                        this.mIpsecHandler.sendMessage(setDirectAlertOpen);
                                                    }
                                                } else {
                                                    TwoStatePreference twoStatePreference11 = this.mResourceAlwaysSwitch;
                                                    if (pref == twoStatePreference11) {
                                                        if (twoStatePreference11.isChecked()) {
                                                            Message setResourceAlwaysClose = this.mIpsecHandler.obtainMessage(108, "0");
                                                            this.mIpsecHandler.sendMessage(setResourceAlwaysClose);
                                                        } else {
                                                            Message setResourceAlwaysOpen = this.mIpsecHandler.obtainMessage(108, "1");
                                                            this.mIpsecHandler.sendMessage(setResourceAlwaysOpen);
                                                        }
                                                    } else {
                                                        TwoStatePreference twoStatePreference12 = this.mPreconditionSwitch;
                                                        if (pref == twoStatePreference12) {
                                                            if (twoStatePreference12.isChecked()) {
                                                                Message setPreconditionClose = this.mIpsecHandler.obtainMessage(110, "0");
                                                                this.mIpsecHandler.sendMessage(setPreconditionClose);
                                                            } else {
                                                                Message setPreconditionOpen = this.mIpsecHandler.obtainMessage(110, "1");
                                                                this.mIpsecHandler.sendMessage(setPreconditionOpen);
                                                            }
                                                        } else {
                                                            TwoStatePreference twoStatePreference13 = this.mVideoUpgradeSwitch;
                                                            if (pref == twoStatePreference13) {
                                                                if (twoStatePreference13.isChecked()) {
                                                                    Message setVideoUpgradeClose = this.mIpsecHandler.obtainMessage(112, "0");
                                                                    this.mIpsecHandler.sendMessage(setVideoUpgradeClose);
                                                                } else {
                                                                    Message setVideoUpgradeOpen = this.mIpsecHandler.obtainMessage(112, "1");
                                                                    this.mIpsecHandler.sendMessage(setVideoUpgradeOpen);
                                                                }
                                                            } else {
                                                                TwoStatePreference twoStatePreference14 = this.mVideoEarlySwitch;
                                                                if (pref == twoStatePreference14) {
                                                                    if (twoStatePreference14.isChecked()) {
                                                                        Message setVideoEarlyClose = this.mIpsecHandler.obtainMessage(114, "0");
                                                                        this.mIpsecHandler.sendMessage(setVideoEarlyClose);
                                                                    } else {
                                                                        Message setVideoEarlyOpen = this.mIpsecHandler.obtainMessage(114, "1");
                                                                        this.mIpsecHandler.sendMessage(setVideoEarlyOpen);
                                                                    }
                                                                } else {
                                                                    TwoStatePreference twoStatePreference15 = this.mAlertingSwitch;
                                                                    if (pref == twoStatePreference15) {
                                                                        if (twoStatePreference15.isChecked()) {
                                                                            Message setAlertingClose = this.mIpsecHandler.obtainMessage(116, "0");
                                                                            this.mIpsecHandler.sendMessage(setAlertingClose);
                                                                        } else {
                                                                            Message setAlertingOpen = this.mIpsecHandler.obtainMessage(116, "1");
                                                                            this.mIpsecHandler.sendMessage(setAlertingOpen);
                                                                        }
                                                                    } else {
                                                                        TwoStatePreference twoStatePreference16 = this.mMidCallSwitch;
                                                                        if (pref == twoStatePreference16) {
                                                                            if (twoStatePreference16.isChecked()) {
                                                                                Message setMidCallClose = this.mIpsecHandler.obtainMessage(118, "0");
                                                                                this.mIpsecHandler.sendMessage(setMidCallClose);
                                                                            } else {
                                                                                Message setMidCallOpen = this.mIpsecHandler.obtainMessage(118, "1");
                                                                                this.mIpsecHandler.sendMessage(setMidCallOpen);
                                                                            }
                                                                        } else {
                                                                            TwoStatePreference twoStatePreference17 = this.mPreAlertingSwitch;
                                                                            if (pref == twoStatePreference17) {
                                                                                if (twoStatePreference17.isChecked()) {
                                                                                    Message setPreAlertClose = this.mIpsecHandler.obtainMessage(120, "0");
                                                                                    this.mIpsecHandler.sendMessage(setPreAlertClose);
                                                                                } else {
                                                                                    Message setPreAlertOpen = this.mIpsecHandler.obtainMessage(120, "1");
                                                                                    this.mIpsecHandler.sendMessage(setPreAlertOpen);
                                                                                }
                                                                            } else {
                                                                                TwoStatePreference twoStatePreference18 = this.mRSRVCCSupportSwitch;
                                                                                if (pref == twoStatePreference18) {
                                                                                    if (twoStatePreference18.isChecked()) {
                                                                                        Message setRSRVCCSupportClose = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_SUPPORT, "0");
                                                                                        this.mIpsecHandler.sendMessage(setRSRVCCSupportClose);
                                                                                    } else {
                                                                                        Message setRSRVCCSupportOpen = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_SUPPORT, "1");
                                                                                        this.mIpsecHandler.sendMessage(setRSRVCCSupportOpen);
                                                                                    }
                                                                                } else {
                                                                                    TwoStatePreference twoStatePreference19 = this.mRSRVCCAlertSwitch;
                                                                                    if (pref == twoStatePreference19) {
                                                                                        if (twoStatePreference19.isChecked()) {
                                                                                            Message setRSRVCCAlertClose = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_ALERTING, "0");
                                                                                            this.mIpsecHandler.sendMessage(setRSRVCCAlertClose);
                                                                                        } else {
                                                                                            Message setRSRVCCAlertOpen = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_ALERTING, "1");
                                                                                            this.mIpsecHandler.sendMessage(setRSRVCCAlertOpen);
                                                                                        }
                                                                                    } else {
                                                                                        TwoStatePreference twoStatePreference20 = this.mRSRVCCMidSwitch;
                                                                                        if (pref == twoStatePreference20) {
                                                                                            if (twoStatePreference20.isChecked()) {
                                                                                                Message setRSRVCCMidClose = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_MID_CALL, "0");
                                                                                                this.mIpsecHandler.sendMessage(setRSRVCCMidClose);
                                                                                            } else {
                                                                                                Message setRSRVCCMidOpen = this.mIpsecHandler.obtainMessage(MSG_SET_RSRVCC_MID_CALL, "1");
                                                                                                this.mIpsecHandler.sendMessage(setRSRVCCMidOpen);
                                                                                            }
                                                                                        } else {
                                                                                            if (pref == this.mVideoMaxResolution) {
                                                                                                Message setVideoMaxResolution = this.mIpsecHandler.obtainMessage(MSG_SET_VIDEO_MAX_RESOLUTION, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setVideoMaxResolution);
                                                                                                return false;
                                                                                            }
                                                                                            if (pref == this.mNationalURL) {
                                                                                                Message setNationalURL = this.mIpsecHandler.obtainMessage(128, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setNationalURL);
                                                                                                return false;
                                                                                            }
                                                                                            if (pref == this.mInternationalURL) {
                                                                                                Message setInternationalURL = this.mIpsecHandler.obtainMessage(130, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setInternationalURL);
                                                                                                return false;
                                                                                            }
                                                                                            if (pref == this.mSessionTimerRefresher) {
                                                                                                Message setTimerRefresher = this.mIpsecHandler.obtainMessage(132, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setTimerRefresher);
                                                                                                return false;
                                                                                            }
                                                                                            if (pref == this.mMTSessionTimerRefresher) {
                                                                                                Message setMTSession = this.mIpsecHandler.obtainMessage(134, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setMTSession);
                                                                                                return false;
                                                                                            }
                                                                                            if (pref == this.mVideoAudioMerge) {
                                                                                                Message setVideoAudioMerge = this.mIpsecHandler.obtainMessage(136, newValue);
                                                                                                this.mIpsecHandler.sendMessage(setVideoAudioMerge);
                                                                                                return false;
                                                                                            }
                                                                                            TwoStatePreference twoStatePreference21 = this.mAudioDynamicEnable;
                                                                                            if (pref == twoStatePreference21) {
                                                                                                if (twoStatePreference21.isChecked()) {
                                                                                                    Message setsetAudioDynamicClose = this.mIpsecHandler.obtainMessage(138, "0");
                                                                                                    this.mIpsecHandler.sendMessage(setsetAudioDynamicClose);
                                                                                                } else {
                                                                                                    Message setAudioDynamicOpen = this.mIpsecHandler.obtainMessage(138, "1");
                                                                                                    this.mIpsecHandler.sendMessage(setAudioDynamicOpen);
                                                                                                }
                                                                                            } else {
                                                                                                TwoStatePreference twoStatePreference22 = this.mVideoDynamicEnable;
                                                                                                if (pref == twoStatePreference22) {
                                                                                                    if (twoStatePreference22.isChecked()) {
                                                                                                        Message setVideoDynamicClose = this.mIpsecHandler.obtainMessage(140, "0");
                                                                                                        this.mIpsecHandler.sendMessage(setVideoDynamicClose);
                                                                                                    } else {
                                                                                                        Message setVideoDynamicOpen = this.mIpsecHandler.obtainMessage(140, "1");
                                                                                                        this.mIpsecHandler.sendMessage(setVideoDynamicOpen);
                                                                                                    }
                                                                                                } else if (pref == this.mBsfURI) {
                                                                                                    Log.d(TAG, "mBsfURI");
                                                                                                    Message setBsfURI = this.mIpsecHandler.obtainMessage(142, newValue);
                                                                                                    this.mIpsecHandler.sendMessage(setBsfURI);
                                                                                                } else if (pref == this.mBsfPort) {
                                                                                                    Log.d(TAG, "mBsfPort");
                                                                                                    Message setBsfPort = this.mIpsecHandler.obtainMessage(144, newValue);
                                                                                                    this.mIpsecHandler.sendMessage(setBsfPort);
                                                                                                } else if (pref == this.mXcapURI) {
                                                                                                    Log.d(TAG, "mXcapURI");
                                                                                                    Message setXcapURI = this.mIpsecHandler.obtainMessage(146, newValue);
                                                                                                    this.mIpsecHandler.sendMessage(setXcapURI);
                                                                                                } else if (pref == this.mXcapPort) {
                                                                                                    Log.d(TAG, "mXcapPort");
                                                                                                    Message setXcapPort = this.mIpsecHandler.obtainMessage(MSG_SET_XCAP_PORT, newValue);
                                                                                                    this.mIpsecHandler.sendMessage(setXcapPort);
                                                                                                } else if (pref == this.mXcapAuid) {
                                                                                                    Log.d(TAG, "mXcapAuid");
                                                                                                    Message setXcapAuid = this.mIpsecHandler.obtainMessage(MSG_SET_XCAP_AUID, newValue);
                                                                                                    this.mIpsecHandler.sendMessage(setXcapAuid);
                                                                                                } else {
                                                                                                    TwoStatePreference twoStatePreference23 = this.mLocalCallSwitch;
                                                                                                    if (pref == twoStatePreference23) {
                                                                                                        if (twoStatePreference23.isChecked()) {
                                                                                                            Message setLocalCallClose = this.mIpsecHandler.obtainMessage(152, "0");
                                                                                                            this.mIpsecHandler.sendMessage(setLocalCallClose);
                                                                                                        } else {
                                                                                                            Message setLocalCallOpen = this.mIpsecHandler.obtainMessage(152, "1");
                                                                                                            this.mIpsecHandler.sendMessage(setLocalCallOpen);
                                                                                                        }
                                                                                                    } else {
                                                                                                        TwoStatePreference twoStatePreference24 = this.mDnsSrvEnableSwitch;
                                                                                                        if (pref == twoStatePreference24) {
                                                                                                            if (twoStatePreference24.isChecked()) {
                                                                                                                Message setDnsSrvEnableClose = this.mIpsecHandler.obtainMessage(154, "0");
                                                                                                                this.mIpsecHandler.sendMessage(setDnsSrvEnableClose);
                                                                                                            } else {
                                                                                                                Message setDnsSrvEnableOpen = this.mIpsecHandler.obtainMessage(154, "1");
                                                                                                                this.mIpsecHandler.sendMessage(setDnsSrvEnableOpen);
                                                                                                            }
                                                                                                        } else {
                                                                                                            TwoStatePreference twoStatePreference25 = this.mHttpsEnableSwitch;
                                                                                                            if (pref == twoStatePreference25) {
                                                                                                                if (twoStatePreference25.isChecked()) {
                                                                                                                    Message setHttpsEnableClose = this.mIpsecHandler.obtainMessage(156, "0");
                                                                                                                    this.mIpsecHandler.sendMessage(setHttpsEnableClose);
                                                                                                                } else {
                                                                                                                    Message setHttpsEnableOpen = this.mIpsecHandler.obtainMessage(156, "1");
                                                                                                                    this.mIpsecHandler.sendMessage(setHttpsEnableOpen);
                                                                                                                }
                                                                                                            } else {
                                                                                                                TwoStatePreference twoStatePreference26 = this.mActivateCfnlSwitch;
                                                                                                                if (pref == twoStatePreference26) {
                                                                                                                    if (twoStatePreference26.isChecked()) {
                                                                                                                        Message setActivateCfnlClose = this.mIpsecHandler.obtainMessage(158, "0");
                                                                                                                        this.mIpsecHandler.sendMessage(setActivateCfnlClose);
                                                                                                                    } else {
                                                                                                                        Message setActivateCfnlOpen = this.mIpsecHandler.obtainMessage(158, "1");
                                                                                                                        this.mIpsecHandler.sendMessage(setActivateCfnlOpen);
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    TwoStatePreference twoStatePreference27 = this.mHttpPutMediaSwitch;
                                                                                                                    if (pref == twoStatePreference27) {
                                                                                                                        if (twoStatePreference27.isChecked()) {
                                                                                                                            Message setHttpPutMediaClose = this.mIpsecHandler.obtainMessage(160, "0");
                                                                                                                            this.mIpsecHandler.sendMessage(setHttpPutMediaClose);
                                                                                                                        } else {
                                                                                                                            Message setHttpPutMediaOpen = this.mIpsecHandler.obtainMessage(160, "1");
                                                                                                                            this.mIpsecHandler.sendMessage(setHttpPutMediaOpen);
                                                                                                                        }
                                                                                                                    } else if (pref == this.mIPPriority) {
                                                                                                                        Message setIPPriority = this.mIpsecHandler.obtainMessage(MSG_SET_IP_PRIORITY, newValue);
                                                                                                                        this.mIpsecHandler.sendMessage(setIPPriority);
                                                                                                                        return false;
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
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference pref) {
        if (pref == this.mIntegrityAlgorithm) {
            Log.d(TAG, "mIntegrityAlgorithm");
            Message setIntegrityAlgorithm = this.mIpsecHandler.obtainMessage(8);
            this.mIpsecHandler.sendMessage(setIntegrityAlgorithm);
            return true;
        }
        if (pref == this.mCipherAlgorithm) {
            Log.d(TAG, "mCipherAlgorithm");
            Message setCipherAlgorithm = this.mIpsecHandler.obtainMessage(10);
            this.mIpsecHandler.sendMessage(setCipherAlgorithm);
            return true;
        }
        if (pref == this.mVolteVoiceCode) {
            Message setVoiceCode = this.mIpsecHandler.obtainMessage(46);
            this.mIpsecHandler.sendMessage(setVoiceCode);
            return false;
        }
        if (pref == this.mEvsRateSet) {
            Message setEvsRateSet = this.mIpsecHandler.obtainMessage(70);
            this.mIpsecHandler.sendMessage(setEvsRateSet);
            return false;
        }
        if (pref == this.mWbRateSet) {
            Message setWbRateSet = this.mIpsecHandler.obtainMessage(66);
            this.mIpsecHandler.sendMessage(setWbRateSet);
            return false;
        }
        if (pref == this.mNbRateSet) {
            Message setNbRateSet = this.mIpsecHandler.obtainMessage(68);
            this.mIpsecHandler.sendMessage(setNbRateSet);
            return false;
        }
        if (pref == this.mVolteVideoCode) {
            Message setVideoCode = this.mIpsecHandler.obtainMessage(102);
            this.mIpsecHandler.sendMessage(setVideoCode);
            return false;
        }
        return true;
    }

    class IpsecHandler extends Handler {
        IpsecHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                try {
                    final String mGetIPsec = OperatorSettingsSIM.teleApi.volteOperatorSettings().getIpsecStatus(OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$IpsecHandler$WIIpK7B0gpL3RU6ELD2hHbPh1oI
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$handleMessage$0$OperatorSettingsSIM$IpsecHandler(mGetIPsec);
                        }
                    });
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            OperatorSettingsSIM.this.mIPsecSwitch.setEnabled(false);
                            OperatorSettingsSIM.this.mIPsecSwitch.setSummary(R.string.feature_abnormal);
                        }
                    });
                }
            }
            if (i == 1) {
                final String mSetIPsecValue = (String) msg.obj;
                Log.d(OperatorSettingsSIM.TAG, "MSG_SET_IPSEC_STATE mSetIPsecValue: " + mSetIPsecValue);
                String mSetIPsec = "AT+SPVOLTEENG=95,1,\"" + mSetIPsecValue + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setIpsecStatus(mSetIPsec, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            OperatorSettingsSIM.this.mIPsecSwitch.setChecked(mSetIPsecValue.contains("1"));
                        }
                    });
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    OperatorSettingsSIM.this.showFailToast();
                    return;
                }
            }
            switch (i) {
                case 7:
                    Log.d(OperatorSettingsSIM.TAG, "MSG_GET_INTEGRITY_ALGORITHM");
                    try {
                        String getIntegrityAlgorithm = OperatorSettingsSIM.teleApi.volteOperatorSettings().getIntegrityAlgorithm(OperatorSettingsSIM.this.mSim);
                        final int value = Integer.parseInt(getIntegrityAlgorithm);
                        final String[] arr = OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.integrity_algorithm_str);
                        final String values = (String) IntStream.range(0, arr.length).filter(new IntPredicate() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$IpsecHandler$Bq5Slg6WTyfrOp8mLLmMsJYsUKQ
                            @Override // java.util.function.IntPredicate
                            public final boolean test(int i2) {
                                return OperatorSettingsSIM.IpsecHandler.lambda$handleMessage$1(value, i2);
                            }
                        }).mapToObj(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$IpsecHandler$owSEvFEd_9T2Zp2OpZhtu6S7Fic
                            @Override // java.util.function.IntFunction
                            public final Object apply(int i2) {
                                return OperatorSettingsSIM.IpsecHandler.lambda$handleMessage$2(arr, i2);
                            }
                        }).collect(Collectors.joining("/"));
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$IpsecHandler$nfWN9Hw5BMr7YbS8YAznlSh5qQI
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$3$OperatorSettingsSIM$IpsecHandler(values);
                            }
                        });
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$IpsecHandler$NI0MzhfyHNeJnRg7kVcjdHXUvPg
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$4$OperatorSettingsSIM$IpsecHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 8:
                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_INTEGRITY_ALGORITHM");
                    View view = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                    LinearLayout line = (LinearLayout) view.findViewById(R.id.linearlayout_main);
                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                        OperatorSettingsSIM.this.mVolteViewList.clear();
                    } else {
                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                    }
                    for (int i2 = 0; i2 < 2; i2++) {
                        CheckBox checkbox = new CheckBox(OperatorSettingsSIM.this.mContext);
                        checkbox.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.integrity_algorithm_str)[i2]);
                        line.addView(checkbox);
                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox);
                    }
                    try {
                        String getIntegrityAlgorithm2 = OperatorSettingsSIM.teleApi.volteOperatorSettings().getIntegrityAlgorithm(OperatorSettingsSIM.this.mSim);
                        final int voiceCodeTypeValue = Integer.valueOf(getIntegrityAlgorithm2).intValue();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.21
                            @Override // java.lang.Runnable
                            public void run() {
                                for (int i3 = 0; i3 < OperatorSettingsSIM.this.mVolteViewList.size(); i3++) {
                                    if ((voiceCodeTypeValue & (1 << i3)) != 0) {
                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i3)).setChecked(true);
                                    } else {
                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i3)).setChecked(false);
                                    }
                                }
                            }
                        });
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    OperatorSettingsSIM.this.popDialog(view, 8);
                    break;
                case 9:
                    Log.d(OperatorSettingsSIM.TAG, "MSG_GET_CIPHER_ALGORITHM");
                    try {
                        String getCipherAlgorithm = OperatorSettingsSIM.teleApi.volteOperatorSettings().getCipherAlgorithm(OperatorSettingsSIM.this.mSim);
                        int value2 = Integer.valueOf(getCipherAlgorithm).intValue();
                        StringBuffer sb = new StringBuffer();
                        for (int i3 = 0; i3 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.cipher_algorithm_str).length; i3++) {
                            if (((1 << i3) & value2) != 0) {
                                if (!"".equals(sb.toString())) {
                                    sb.append("/");
                                }
                                sb.append(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.cipher_algorithm_str)[i3]);
                            }
                        }
                        final String summary = sb.toString();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.22
                            @Override // java.lang.Runnable
                            public void run() {
                                Log.d(OperatorSettingsSIM.TAG, "MSG_GET_CIPHER_ALGORITHM : " + summary);
                                OperatorSettingsSIM.this.mCipherAlgorithm.setSummary(summary);
                            }
                        });
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.23
                            @Override // java.lang.Runnable
                            public void run() {
                                OperatorSettingsSIM.this.mCipherAlgorithm.setEnabled(false);
                                OperatorSettingsSIM.this.mCipherAlgorithm.setSummary(R.string.feature_abnormal);
                            }
                        });
                        return;
                    }
                    break;
                case 10:
                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_CIPHER_ALGORITHM");
                    View view1 = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                    LinearLayout line1 = (LinearLayout) view1.findViewById(R.id.linearlayout_main);
                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                        OperatorSettingsSIM.this.mVolteViewList.clear();
                    } else {
                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                    }
                    for (int i4 = 0; i4 < 3; i4++) {
                        CheckBox checkbox2 = new CheckBox(OperatorSettingsSIM.this.mContext);
                        checkbox2.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.cipher_algorithm_str)[i4]);
                        line1.addView(checkbox2);
                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox2);
                    }
                    try {
                        String getCipherAlgorithm2 = OperatorSettingsSIM.teleApi.volteOperatorSettings().getCipherAlgorithm(OperatorSettingsSIM.this.mSim);
                        final int cipherAlgorithmValue = Integer.valueOf(getCipherAlgorithm2).intValue();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.24
                            @Override // java.lang.Runnable
                            public void run() {
                                for (int i5 = 0; i5 < OperatorSettingsSIM.this.mVolteViewList.size(); i5++) {
                                    if ((cipherAlgorithmValue & (1 << i5)) != 0) {
                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i5)).setChecked(true);
                                    } else {
                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i5)).setChecked(false);
                                    }
                                }
                            }
                        });
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                    OperatorSettingsSIM.this.popDialog(view1, 10);
                    break;
                case 11:
                    try {
                        final String mGetRegSubscribe = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRegSubscribe(OperatorSettingsSIM.this.mSim);
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.3
                            @Override // java.lang.Runnable
                            public void run() {
                                boolean open = mGetRegSubscribe.contains("1");
                                OperatorSettingsSIM.this.mRegSubscribeEnable.setChecked(open);
                                OperatorSettingsSIM.this.mRegSubscribeEnable.setEnabled(true);
                            }
                        });
                    } catch (Exception e7) {
                        e7.printStackTrace();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.4
                            @Override // java.lang.Runnable
                            public void run() {
                                OperatorSettingsSIM.this.mRegSubscribeEnable.setEnabled(false);
                                OperatorSettingsSIM.this.mRegSubscribeEnable.setSummary(R.string.feature_abnormal);
                            }
                        });
                        return;
                    }
                    break;
                case 12:
                    final String mSetRegSubscribeValue = (String) msg.obj;
                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_REG_SUBSCRIBE_ENABLE mSetRegSubscribeValue: " + mSetRegSubscribeValue);
                    String mSetRegSubscribe = "AT+SPVOLTEENG=30,1,\"" + mSetRegSubscribeValue + "\"";
                    try {
                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRegSubscribe(mSetRegSubscribe, OperatorSettingsSIM.this.mSim);
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.5
                            @Override // java.lang.Runnable
                            public void run() {
                                OperatorSettingsSIM.this.mRegSubscribeEnable.setChecked(mSetRegSubscribeValue.contains("1"));
                            }
                        });
                    } catch (Exception e8) {
                        e8.printStackTrace();
                        OperatorSettingsSIM.this.showFailToast();
                        return;
                    }
                    break;
                case 13:
                    try {
                        final String mGetMWIEnable = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMwiEnable(OperatorSettingsSIM.this.mSim);
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.6
                            @Override // java.lang.Runnable
                            public void run() {
                                boolean open = mGetMWIEnable.contains("1");
                                OperatorSettingsSIM.this.mMWIEnable.setChecked(open);
                                OperatorSettingsSIM.this.mMWIEnable.setEnabled(true);
                            }
                        });
                    } catch (Exception e9) {
                        e9.printStackTrace();
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.7
                            @Override // java.lang.Runnable
                            public void run() {
                                OperatorSettingsSIM.this.mMWIEnable.setEnabled(false);
                                OperatorSettingsSIM.this.mMWIEnable.setSummary(R.string.feature_abnormal);
                            }
                        });
                        return;
                    }
                    break;
                case 14:
                    final String mSetMWIEnableValue = (String) msg.obj;
                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_MWI_ENABLE mSetMWIEnableValue: " + mSetMWIEnableValue);
                    String mSetMWIEnable = "AT+SPVOLTEENG=33,1,\"" + mSetMWIEnableValue + "\"";
                    try {
                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMwiEnable(mSetMWIEnable, OperatorSettingsSIM.this.mSim);
                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.8
                            @Override // java.lang.Runnable
                            public void run() {
                                OperatorSettingsSIM.this.mMWIEnable.setChecked(mSetMWIEnableValue.contains("1"));
                            }
                        });
                    } catch (Exception e10) {
                        e10.printStackTrace();
                        OperatorSettingsSIM.this.showFailToast();
                        return;
                    }
                    break;
                default:
                    switch (i) {
                        case 17:
                            try {
                                final String mGetSIPHeaderCompact = OperatorSettingsSIM.teleApi.volteOperatorSettings().getSIPHeaderCompact(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.9
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        boolean open = mGetSIPHeaderCompact.contains("1");
                                        OperatorSettingsSIM.this.mSIPHeaderCompactForm.setChecked(open);
                                        OperatorSettingsSIM.this.mSIPHeaderCompactForm.setEnabled(true);
                                    }
                                });
                            } catch (Exception e11) {
                                e11.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.10
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mSIPHeaderCompactForm.setEnabled(false);
                                        OperatorSettingsSIM.this.mSIPHeaderCompactForm.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 18:
                            final String mSetSIPHeaderCompactValue = (String) msg.obj;
                            Log.d(OperatorSettingsSIM.TAG, "MSG_SET_SIP_HEADER_COMPACT mSetSIPHeaderCompactValue: " + mSetSIPHeaderCompactValue);
                            String mSetSIPHeaderCompact = "AT+SPVOLTEENG=44,1,\"" + mSetSIPHeaderCompactValue + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setSIPHeaderCompact(mSetSIPHeaderCompact, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.11
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mSIPHeaderCompactForm.setChecked(mSetSIPHeaderCompactValue.contains("1"));
                                    }
                                });
                            } catch (Exception e12) {
                                e12.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 19:
                            try {
                                final String mGetSMSoverIPEnable = OperatorSettingsSIM.teleApi.volteOperatorSettings().getSMSoverIPEnable(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.12
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        boolean open = mGetSMSoverIPEnable.contains("1");
                                        OperatorSettingsSIM.this.mSMSoverIPEnable.setChecked(open);
                                        OperatorSettingsSIM.this.mSMSoverIPEnable.setEnabled(true);
                                    }
                                });
                            } catch (Exception e13) {
                                e13.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.13
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mSMSoverIPEnable.setEnabled(false);
                                        OperatorSettingsSIM.this.mSMSoverIPEnable.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 20:
                            final String mSetSMSoverIPEnableValue = (String) msg.obj;
                            Log.d(OperatorSettingsSIM.TAG, "MSG_SET_SMS_OVER_IP_ENABLE mSetSMSoverIPEnableValue: " + mSetSMSoverIPEnableValue);
                            String mSetSMSoverIPEnable = "AT+CASIMS=" + mSetSMSoverIPEnableValue;
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setSMSoverIPEnable(mSetSMSoverIPEnable, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.14
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mSMSoverIPEnable.setChecked(mSetSMSoverIPEnableValue.contains("1"));
                                    }
                                });
                            } catch (Exception e14) {
                                e14.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 21:
                            try {
                                final String mGetUSSIEnable = OperatorSettingsSIM.teleApi.volteOperatorSettings().getUssiEnable(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.15
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        boolean open = mGetUSSIEnable.contains("1");
                                        OperatorSettingsSIM.this.mUSSIEnable.setChecked(open);
                                        OperatorSettingsSIM.this.mUSSIEnable.setEnabled(true);
                                    }
                                });
                            } catch (Exception e15) {
                                e15.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.16
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mUSSIEnable.setEnabled(false);
                                        OperatorSettingsSIM.this.mUSSIEnable.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 22:
                            final String mSetUSSIEnableValue = (String) msg.obj;
                            Log.d(OperatorSettingsSIM.TAG, "MSG_SET_USSI_ENABLE mSetUSSIEnableValue: " + mSetUSSIEnableValue);
                            String mSetUSSIEnable = "AT+SPVOLTEENG=41,1,\"" + mSetUSSIEnableValue + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setUssiEnable(mSetUSSIEnable, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.17
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mUSSIEnable.setChecked(mSetUSSIEnableValue.contains("1"));
                                    }
                                });
                            } catch (Exception e16) {
                                e16.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 23:
                            try {
                                final String mGetInitialRegisterPANI = OperatorSettingsSIM.teleApi.volteOperatorSettings().getInitialRegister(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.18
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        boolean open = mGetInitialRegisterPANI.contains("1");
                                        OperatorSettingsSIM.this.mInitialRegisterPANI.setChecked(open);
                                        OperatorSettingsSIM.this.mInitialRegisterPANI.setEnabled(true);
                                    }
                                });
                            } catch (Exception e17) {
                                e17.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.19
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mInitialRegisterPANI.setEnabled(false);
                                        OperatorSettingsSIM.this.mInitialRegisterPANI.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 24:
                            final String mSetInitialRegisterPANIValue = (String) msg.obj;
                            Log.d(OperatorSettingsSIM.TAG, "MSG_SET_INITIAL_REGISTER_PANI mSetInitialRegisterPANIValue: " + mSetInitialRegisterPANIValue);
                            String mSetInitialRegisterPANI = "AT+SPVOLTEENG=32,1,\"" + mSetInitialRegisterPANIValue + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setInitialRegister(mSetInitialRegisterPANI, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.20
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mInitialRegisterPANI.setChecked(mSetInitialRegisterPANIValue.contains("1"));
                                    }
                                });
                            } catch (Exception e18) {
                                e18.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 25:
                            try {
                                final String RegRegSubscribValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRegSubscrib(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.25
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mRegSubscribExpire.setEnabled(true);
                                        OperatorSettingsSIM.this.mRegSubscribExpire.setSummary(RegRegSubscribValue);
                                    }
                                });
                            } catch (Exception e19) {
                                e19.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.26
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mRegSubscribExpire.setEnabled(false);
                                        OperatorSettingsSIM.this.mRegSubscribExpire.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 26:
                            final String setRegSubscribe = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=31,1,\"" + setRegSubscribe + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setRegSubscrib(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.27
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_REG_SUBSCRIB_EXPIRE + OperatorSettingsSIM.this.mSim, setRegSubscribe);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mRegSubscribExpire.setSummary(setRegSubscribe);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e20) {
                                e20.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 27:
                            try {
                                final String MwiSubscribValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMwiSubscrib(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.28
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mMwiSubscribExpire.setSummary(MwiSubscribValue);
                                        OperatorSettingsSIM.this.mMwiSubscribExpire.setEnabled(true);
                                    }
                                });
                            } catch (Exception e21) {
                                e21.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.29
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mMwiSubscribExpire.setEnabled(false);
                                        OperatorSettingsSIM.this.mMwiSubscribExpire.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 28:
                            final String setMWISubscribe = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=35,1,\"" + setMWISubscribe + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setMwiSubscrib(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.30
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_MWI_SUBSCRIBE_EXPIRE + OperatorSettingsSIM.this.mSim, setMWISubscribe);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mMwiSubscribExpire.setSummary(setMWISubscribe);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e22) {
                                e22.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 29:
                            try {
                                final String MTUValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMtuValue(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.31
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mMTU.setSummary(MTUValue);
                                        OperatorSettingsSIM.this.mMTU.setEnabled(true);
                                    }
                                });
                            } catch (Exception e23) {
                                e23.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.32
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mMTU.setEnabled(false);
                                        OperatorSettingsSIM.this.mMTU.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 30:
                            final String setMTU = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=86,1,\"" + setMTU + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setMtuValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.33
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_MTU + OperatorSettingsSIM.this.mSim, setMTU);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mMTU.setSummary(setMTU);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e24) {
                                e24.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 31:
                            try {
                                final String PcscfPortValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getPcscfPort(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.34
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mPcscfPort.setSummary(PcscfPortValue);
                                        OperatorSettingsSIM.this.mPcscfPort.setEnabled(true);
                                    }
                                });
                            } catch (Exception e25) {
                                e25.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.35
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mPcscfPort.setEnabled(false);
                                        OperatorSettingsSIM.this.mPcscfPort.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 32:
                            final String setPcscfPort = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=87,1,\"" + setPcscfPort + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setPcscfPort(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.36
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_PCSCF_PORT + OperatorSettingsSIM.this.mSim, setPcscfPort);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mPcscfPort.setSummary(setPcscfPort);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e26) {
                                e26.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 33:
                            try {
                                final String RegExpireValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRegExpireValue(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.37
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mRegExpire.setSummary(RegExpireValue);
                                        OperatorSettingsSIM.this.mRegExpire.setEnabled(true);
                                    }
                                });
                            } catch (Exception e27) {
                                e27.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.38
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mRegExpire.setEnabled(false);
                                        OperatorSettingsSIM.this.mRegExpire.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 34:
                            final String setRegExpire = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=29,1,\"" + setRegExpire + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setRegExpireValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.39
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_REG_EXPIRE + OperatorSettingsSIM.this.mSim, setRegExpire);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mRegExpire.setSummary(setRegExpire);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e28) {
                                e28.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 35:
                            try {
                                final String TregTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getTregTimerValue(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.40
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mTregTimer.setSummary(TregTimerValue);
                                        OperatorSettingsSIM.this.mTregTimer.setEnabled(true);
                                    }
                                });
                            } catch (Exception e29) {
                                e29.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.41
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mTregTimer.setEnabled(false);
                                        OperatorSettingsSIM.this.mTregTimer.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 36:
                            final String setTregTimer = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=25,1,\"" + setTregTimer + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setTregTimerValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.42
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_TREG_TIMER + OperatorSettingsSIM.this.mSim, setTregTimer);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mTregTimer.setSummary(setTregTimer);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e30) {
                                e30.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        case 37:
                            try {
                                final String TemergregTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getTemergregValue(OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.43
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mTemergregTimer.setSummary(TemergregTimerValue);
                                        OperatorSettingsSIM.this.mTemergregTimer.setEnabled(true);
                                    }
                                });
                            } catch (Exception e31) {
                                e31.printStackTrace();
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.44
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        OperatorSettingsSIM.this.mTemergregTimer.setEnabled(false);
                                        OperatorSettingsSIM.this.mTemergregTimer.setSummary(R.string.feature_abnormal);
                                    }
                                });
                                return;
                            }
                            break;
                        case 38:
                            final String setTemergregTimer = (String) msg.obj;
                            OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=26,1,\"" + setTemergregTimer + "\"";
                            try {
                                OperatorSettingsSIM.teleApi.volteOperatorSettings().setTemergregValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.45
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                        editor.putString(OperatorSettingsSIM.KEY_TEMERGREG_TIMER + OperatorSettingsSIM.this.mSim, setTemergregTimer);
                                        editor.commit();
                                        OperatorSettingsSIM.this.mTemergregTimer.setSummary(setTemergregTimer);
                                        Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                    }
                                });
                            } catch (Exception e32) {
                                e32.printStackTrace();
                                OperatorSettingsSIM.this.showFailToast();
                                return;
                            }
                            break;
                        default:
                            switch (i) {
                                case 45:
                                    try {
                                        String getVoiceCodeType = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVoiceCodeType(OperatorSettingsSIM.this.mSim);
                                        int value3 = Integer.valueOf(getVoiceCodeType).intValue();
                                        String TypeValue = "";
                                        for (int i5 = 0; i5 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.volte_voice_code).length; i5++) {
                                            if (((1 << i5) & value3) != 0) {
                                                if (!"".equals(TypeValue)) {
                                                    TypeValue = TypeValue + "/";
                                                }
                                                TypeValue = TypeValue + OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.volte_voice_code)[i5];
                                            }
                                        }
                                        final String summary2 = TypeValue;
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.46
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVolteVoiceCode.setSummary(summary2);
                                            }
                                        });
                                    } catch (Exception e33) {
                                        e33.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.47
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVolteVoiceCode.setEnabled(false);
                                                OperatorSettingsSIM.this.mVolteVoiceCode.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                    }
                                    Message msgEvsRateSet = OperatorSettingsSIM.this.mIpsecHandler.obtainMessage(69);
                                    OperatorSettingsSIM.this.mIpsecHandler.sendMessage(msgEvsRateSet);
                                    break;
                                case 46:
                                    View view11 = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                    LinearLayout line11 = (LinearLayout) view11.findViewById(R.id.linearlayout_main);
                                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                                        OperatorSettingsSIM.this.mVolteViewList.clear();
                                    } else {
                                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                                    }
                                    for (int i6 = 0; i6 < 3; i6++) {
                                        CheckBox checkbox3 = new CheckBox(OperatorSettingsSIM.this.mContext);
                                        checkbox3.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.volte_voice_code)[i6]);
                                        line11.addView(checkbox3);
                                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox3);
                                    }
                                    try {
                                        String mVoiceCodeType = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVoiceCodeType(OperatorSettingsSIM.this.mSim);
                                        final int voiceCodeTypeValue2 = Integer.valueOf(mVoiceCodeType).intValue();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.48
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i7 = 0; i7 < OperatorSettingsSIM.this.mVolteViewList.size(); i7++) {
                                                    if ((voiceCodeTypeValue2 & (1 << i7)) != 0) {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i7)).setChecked(true);
                                                    } else {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i7)).setChecked(false);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e34) {
                                        e34.printStackTrace();
                                    }
                                    OperatorSettingsSIM.this.popDialog(view11, 46);
                                    break;
                                case 47:
                                    try {
                                        final String minBandWidthValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMinBandWidth(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.49
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBandWidth.setValueIndex(Integer.valueOf(minBandWidthValue).intValue());
                                                OperatorSettingsSIM.this.mMinBandWidth.setSummary(OperatorSettingsSIM.this.mMinBandWidth.getEntry());
                                            }
                                        });
                                    } catch (Exception e35) {
                                        e35.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.50
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBandWidth.setEnabled(false);
                                                OperatorSettingsSIM.this.mMinBandWidth.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 48:
                                    String setMinBandWidth = (String) msg.obj;
                                    final int setMinBandWidthValue = Integer.valueOf(setMinBandWidth).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=69,1,\"" + setMinBandWidthValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMinBandWidth(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.51
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBandWidth.setValueIndex(setMinBandWidthValue);
                                                OperatorSettingsSIM.this.mMinBandWidth.setSummary(OperatorSettingsSIM.this.mMinBandWidth.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e36) {
                                        e36.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 49:
                                    try {
                                        final String maxBandWidthValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMaxBandWidth(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.52
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBandWidth.setValueIndex(Integer.valueOf(maxBandWidthValue).intValue());
                                                OperatorSettingsSIM.this.mMaxBandWidth.setSummary(OperatorSettingsSIM.this.mMaxBandWidth.getEntry());
                                            }
                                        });
                                    } catch (Exception e37) {
                                        e37.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.53
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBandWidth.setEnabled(false);
                                                OperatorSettingsSIM.this.mMaxBandWidth.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 50:
                                    String setMaxBandWidth = (String) msg.obj;
                                    final int setMaxBandWidthValue = Integer.valueOf(setMaxBandWidth).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=70,1,\"" + setMaxBandWidthValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMaxBandWidth(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.54
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBandWidth.setValueIndex(setMaxBandWidthValue);
                                                OperatorSettingsSIM.this.mMaxBandWidth.setSummary(OperatorSettingsSIM.this.mMaxBandWidth.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e38) {
                                        e38.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 51:
                                    try {
                                        final String minBitRateValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMinBitRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.55
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBitRate.setValueIndex(Integer.valueOf(minBitRateValue).intValue());
                                                OperatorSettingsSIM.this.mMinBitRate.setSummary(OperatorSettingsSIM.this.mMinBitRate.getEntry());
                                            }
                                        });
                                    } catch (Exception e39) {
                                        e39.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.56
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBitRate.setEnabled(false);
                                                OperatorSettingsSIM.this.mMinBitRate.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 52:
                                    String setMinBitRate = (String) msg.obj;
                                    final int setMinBitRateValue = Integer.valueOf(setMinBitRate).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=71,1,\"" + setMinBitRateValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMinBitRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.57
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMinBitRate.setValueIndex(setMinBitRateValue);
                                                OperatorSettingsSIM.this.mMinBitRate.setSummary(OperatorSettingsSIM.this.mMinBitRate.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e40) {
                                        e40.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 53:
                                    try {
                                        final String maxBitRateValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMaxBitRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.58
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBitRate.setValueIndex(Integer.valueOf(maxBitRateValue).intValue());
                                                OperatorSettingsSIM.this.mMaxBitRate.setSummary(OperatorSettingsSIM.this.mMaxBitRate.getEntry());
                                            }
                                        });
                                    } catch (Exception e41) {
                                        e41.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.59
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBitRate.setEnabled(false);
                                                OperatorSettingsSIM.this.mMaxBitRate.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 54:
                                    String setMaxBitRate = (String) msg.obj;
                                    final int setMaxBitRateValue = Integer.valueOf(setMaxBitRate).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=72,1,\"" + setMaxBitRateValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMaxBitRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.60
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMaxBitRate.setValueIndex(setMaxBitRateValue);
                                                OperatorSettingsSIM.this.mMaxBitRate.setSummary(OperatorSettingsSIM.this.mMaxBitRate.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e42) {
                                        e42.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 55:
                                    try {
                                        final String defaultBitRateValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getDefaultBitRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.61
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDefaultBitRate.setValueIndex(Integer.valueOf(defaultBitRateValue).intValue());
                                                OperatorSettingsSIM.this.mDefaultBitRate.setSummary(OperatorSettingsSIM.this.mDefaultBitRate.getEntry());
                                            }
                                        });
                                    } catch (Exception e43) {
                                        e43.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.62
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDefaultBitRate.setEnabled(false);
                                                OperatorSettingsSIM.this.mDefaultBitRate.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 56:
                                    String setDefaultBitRate = (String) msg.obj;
                                    final int setDefaultBitRateValue = Integer.valueOf(setDefaultBitRate).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=77,1,\"" + setDefaultBitRateValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setDefaultBitRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.63
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDefaultBitRate.setValueIndex(setDefaultBitRateValue);
                                                OperatorSettingsSIM.this.mDefaultBitRate.setSummary(OperatorSettingsSIM.this.mDefaultBitRate.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e44) {
                                        e44.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 57:
                                    try {
                                        final String evsDefBrValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getEvsDefaultBitRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.68
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setValueIndex(Integer.valueOf(evsDefBrValue).intValue());
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setSummary(OperatorSettingsSIM.this.mEvsDefaultBR.getEntry());
                                            }
                                        });
                                    } catch (Exception e45) {
                                        e45.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.69
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setEnabled(false);
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 58:
                                    String setEvsDefaultBr = (String) msg.obj;
                                    final int setEvsDefaultBrValue = Integer.valueOf(setEvsDefaultBr).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=76,1,\"" + setEvsDefaultBrValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setEvsDefaultBitRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.70
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setValueIndex(setEvsDefaultBrValue);
                                                OperatorSettingsSIM.this.mEvsDefaultBR.setSummary(OperatorSettingsSIM.this.mEvsDefaultBR.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e46) {
                                        e46.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 59:
                                    try {
                                        final String wbDefBrValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getWbDefaultRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.75
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mWbDefaultBR.setValueIndex(Integer.valueOf(wbDefBrValue).intValue());
                                                OperatorSettingsSIM.this.mWbDefaultBR.setSummary(OperatorSettingsSIM.this.mWbDefaultBR.getEntry());
                                            }
                                        });
                                    } catch (Exception e47) {
                                        e47.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.76
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mWbDefaultBR.setEnabled(false);
                                                OperatorSettingsSIM.this.mWbDefaultBR.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 60:
                                    String setWbDefaultBr = (String) msg.obj;
                                    final int setWbDefaultBrValue = Integer.valueOf(setWbDefaultBr).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=75,1,\"" + setWbDefaultBrValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setWbDefaultRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.77
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mWbDefaultBR.setValueIndex(setWbDefaultBrValue);
                                                OperatorSettingsSIM.this.mWbDefaultBR.setSummary(OperatorSettingsSIM.this.mWbDefaultBR.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e48) {
                                        e48.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 61:
                                    try {
                                        final String nbDefBrValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNbDefaultRate(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.82
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNbDefaultBR.setValueIndex(Integer.valueOf(nbDefBrValue).intValue());
                                                OperatorSettingsSIM.this.mNbDefaultBR.setSummary(OperatorSettingsSIM.this.mNbDefaultBR.getEntry());
                                            }
                                        });
                                    } catch (Exception e49) {
                                        e49.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.83
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNbDefaultBR.setEnabled(false);
                                                OperatorSettingsSIM.this.mNbDefaultBR.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 62:
                                    String setNbDefaultBr = (String) msg.obj;
                                    final int setNbDefaultBrValue = Integer.valueOf(setNbDefaultBr).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=74,1,\"" + setNbDefaultBrValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setNbDefaultRate(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.84
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNbDefaultBR.setValueIndex(setNbDefaultBrValue);
                                                OperatorSettingsSIM.this.mNbDefaultBR.setSummary(OperatorSettingsSIM.this.mNbDefaultBR.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e50) {
                                        e50.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 63:
                                    try {
                                        String channelAwareMode = OperatorSettingsSIM.teleApi.volteOperatorSettings().getChannelAwarkMode(OperatorSettingsSIM.this.mSim);
                                        final int tempValue = OperatorSettingsSIM.this.changeValueToIndex(channelAwareMode);
                                        Log.d(OperatorSettingsSIM.TAG, "tempValue is " + tempValue);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.85
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mChannelAwareMode.setValueIndex(tempValue);
                                                OperatorSettingsSIM.this.mChannelAwareMode.setSummary(OperatorSettingsSIM.this.mChannelAwareMode.getEntry());
                                            }
                                        });
                                    } catch (Exception e51) {
                                        e51.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.86
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mChannelAwareMode.setEnabled(false);
                                                OperatorSettingsSIM.this.mChannelAwareMode.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 64:
                                    String setAwreMde = (String) msg.obj;
                                    final int awareModevalue = Integer.valueOf(setAwreMde).intValue();
                                    String tempChannelAware = OperatorSettingsSIM.this.changeIndexToValue(Integer.valueOf(setAwreMde).intValue());
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=73,1,\"" + tempChannelAware + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setChannelAwarkMode(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.87
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mChannelAwareMode.setValueIndex(awareModevalue);
                                                OperatorSettingsSIM.this.mChannelAwareMode.setSummary(OperatorSettingsSIM.this.mChannelAwareMode.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e52) {
                                        e52.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 65:
                                    try {
                                        String getWbRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getWbRate(OperatorSettingsSIM.this.mSim);
                                        int wb_rs_value = Integer.valueOf(getWbRateSet).intValue();
                                        String TypeValue2 = "";
                                        for (int i7 = 0; i7 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i7++) {
                                            if (((1 << i7) & wb_rs_value) != 0) {
                                                if (!"".equals(TypeValue2)) {
                                                    TypeValue2 = TypeValue2 + "/";
                                                }
                                                TypeValue2 = TypeValue2 + OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i7];
                                            }
                                        }
                                        final String summary3 = TypeValue2;
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.71
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mWbRateSet.setSummary(summary3);
                                            }
                                        });
                                    } catch (Exception e53) {
                                        e53.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.72
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mWbRateSet.setEnabled(false);
                                                OperatorSettingsSIM.this.mWbRateSet.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                    }
                                    Message msgNbRateSet = OperatorSettingsSIM.this.mIpsecHandler.obtainMessage(67);
                                    OperatorSettingsSIM.this.mIpsecHandler.sendMessage(msgNbRateSet);
                                    break;
                                case 66:
                                    View view2 = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                    LinearLayout line2 = (LinearLayout) view2.findViewById(R.id.linearlayout_main);
                                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                                        OperatorSettingsSIM.this.mVolteViewList.clear();
                                    } else {
                                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                                    }
                                    for (int i8 = 0; i8 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i8++) {
                                        CheckBox checkbox4 = new CheckBox(OperatorSettingsSIM.this.mContext);
                                        checkbox4.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i8]);
                                        line2.addView(checkbox4);
                                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox4);
                                    }
                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(OperatorSettingsSIM.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.73
                                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                for (int i9 = 0; i9 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i9++) {
                                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i9)).setChecked(true);
                                                }
                                                return;
                                            }
                                            for (int i10 = 0; i10 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i10++) {
                                                ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i10)).setChecked(false);
                                            }
                                        }
                                    });
                                    try {
                                        String mWbRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getWbRate(OperatorSettingsSIM.this.mSim);
                                        final int WbRateSetValue = Integer.valueOf(mWbRateSet).intValue();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.74
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i9 = 0; i9 < OperatorSettingsSIM.this.mVolteViewList.size(); i9++) {
                                                    if ((WbRateSetValue & (1 << i9)) != 0) {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i9)).setChecked(true);
                                                    } else {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i9)).setChecked(false);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e54) {
                                        e54.printStackTrace();
                                    }
                                    OperatorSettingsSIM.this.popDialog(view2, 66);
                                    break;
                                case 67:
                                    try {
                                        String getNbRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNbRate(OperatorSettingsSIM.this.mSim);
                                        int nb_rs_value = Integer.valueOf(getNbRateSet).intValue();
                                        String TypeValue3 = "";
                                        for (int i9 = 0; i9 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set).length; i9++) {
                                            if (((1 << i9) & nb_rs_value) != 0) {
                                                if (!"".equals(TypeValue3)) {
                                                    TypeValue3 = TypeValue3 + "/";
                                                }
                                                TypeValue3 = TypeValue3 + OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set)[i9];
                                            }
                                        }
                                        final String summary4 = TypeValue3;
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.78
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNbRateSet.setSummary(summary4);
                                            }
                                        });
                                    } catch (Exception e55) {
                                        e55.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.79
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNbRateSet.setEnabled(false);
                                                OperatorSettingsSIM.this.mNbRateSet.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 68:
                                    View view3 = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                    LinearLayout line3 = (LinearLayout) view3.findViewById(R.id.linearlayout_main);
                                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                                        OperatorSettingsSIM.this.mVolteViewList.clear();
                                    } else {
                                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                                    }
                                    for (int i10 = 0; i10 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set).length; i10++) {
                                        CheckBox checkbox5 = new CheckBox(OperatorSettingsSIM.this.mContext);
                                        checkbox5.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.nb_bitrate_set)[i10]);
                                        line3.addView(checkbox5);
                                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox5);
                                    }
                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(OperatorSettingsSIM.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.80
                                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                for (int i11 = 0; i11 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i11++) {
                                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i11)).setChecked(true);
                                                }
                                                return;
                                            }
                                            for (int i12 = 0; i12 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i12++) {
                                                ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i12)).setChecked(false);
                                            }
                                        }
                                    });
                                    try {
                                        String mNbRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNbRate(OperatorSettingsSIM.this.mSim);
                                        final int NbRateSetValue = Integer.valueOf(mNbRateSet).intValue();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.81
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i11 = 0; i11 < OperatorSettingsSIM.this.mVolteViewList.size(); i11++) {
                                                    if ((NbRateSetValue & (1 << i11)) != 0) {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i11)).setChecked(true);
                                                    } else {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i11)).setChecked(false);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e56) {
                                        e56.printStackTrace();
                                    }
                                    OperatorSettingsSIM.this.popDialog(view3, 68);
                                    break;
                                case 69:
                                    try {
                                        String getEvsRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getEvsRate(OperatorSettingsSIM.this.mSim);
                                        int evs_rs_value = Integer.valueOf(getEvsRateSet).intValue();
                                        String TypeValue4 = "";
                                        for (int i11 = 0; i11 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i11++) {
                                            if (((1 << i11) & evs_rs_value) != 0) {
                                                if (!"".equals(TypeValue4)) {
                                                    TypeValue4 = TypeValue4 + "/";
                                                }
                                                TypeValue4 = TypeValue4 + OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i11];
                                            }
                                        }
                                        final String summary5 = TypeValue4;
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.64
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mEvsRateSet.setSummary(summary5);
                                            }
                                        });
                                    } catch (Exception e57) {
                                        e57.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.65
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mEvsRateSet.setEnabled(false);
                                                OperatorSettingsSIM.this.mEvsRateSet.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                    }
                                    Message msgWbRateSet = OperatorSettingsSIM.this.mIpsecHandler.obtainMessage(65);
                                    OperatorSettingsSIM.this.mIpsecHandler.sendMessage(msgWbRateSet);
                                    break;
                                case 70:
                                    View view4 = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                    LinearLayout line4 = (LinearLayout) view4.findViewById(R.id.linearlayout_main);
                                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                                        OperatorSettingsSIM.this.mVolteViewList.clear();
                                    } else {
                                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                                    }
                                    for (int i12 = 0; i12 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set).length; i12++) {
                                        CheckBox checkbox6 = new CheckBox(OperatorSettingsSIM.this.mContext);
                                        checkbox6.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.evs_wb_bitrate_set)[i12]);
                                        line4.addView(checkbox6);
                                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox6);
                                    }
                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(OperatorSettingsSIM.this.mVolteViewList.size() - 1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.66
                                        @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                for (int i13 = 0; i13 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i13++) {
                                                    ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i13)).setChecked(true);
                                                }
                                                return;
                                            }
                                            for (int i14 = 0; i14 < OperatorSettingsSIM.this.mVolteViewList.size() - 1; i14++) {
                                                ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i14)).setChecked(false);
                                            }
                                        }
                                    });
                                    try {
                                        String mEvsRateSet = OperatorSettingsSIM.teleApi.volteOperatorSettings().getEvsRate(OperatorSettingsSIM.this.mSim);
                                        final int EvsRateSetValue = Integer.valueOf(mEvsRateSet).intValue();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.67
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i13 = 0; i13 < OperatorSettingsSIM.this.mVolteViewList.size(); i13++) {
                                                    if ((EvsRateSetValue & (1 << i13)) != 0) {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i13)).setChecked(true);
                                                    } else {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i13)).setChecked(false);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e58) {
                                        e58.printStackTrace();
                                    }
                                    OperatorSettingsSIM.this.popDialog(view4, 70);
                                    break;
                                case 71:
                                    try {
                                        final String moAmrOaValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMoAmrOa(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.88
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setValueIndex(Integer.valueOf(moAmrOaValue).intValue());
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setSummary(OperatorSettingsSIM.this.mMoAmrOaMode.getEntry());
                                            }
                                        });
                                    } catch (Exception e59) {
                                        e59.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.89
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setEnabled(false);
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 72:
                                    String setMoAmrOaMode = (String) msg.obj;
                                    final int setMoAmrOaModeValue = Integer.valueOf(setMoAmrOaMode).intValue();
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=78,1,\"" + setMoAmrOaModeValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMoAmrOa(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.90
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setValueIndex(setMoAmrOaModeValue);
                                                OperatorSettingsSIM.this.mMoAmrOaMode.setSummary(OperatorSettingsSIM.this.mMoAmrOaMode.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e60) {
                                        e60.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 73:
                                    try {
                                        final String mGetNamedTelEventValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNamedTelEvent(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.91
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetNamedTelEventValue.contains("1");
                                                OperatorSettingsSIM.this.mNamedTelEvent.setChecked(open);
                                                OperatorSettingsSIM.this.mNamedTelEvent.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e61) {
                                        e61.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.92
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNamedTelEvent.setEnabled(false);
                                                OperatorSettingsSIM.this.mNamedTelEvent.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 74:
                                    final String mSetNamedTelEventValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_NAMED_TEL_EVENT mSetNamedTelEventValue: " + mSetNamedTelEventValue);
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=79,1,\"" + mSetNamedTelEventValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setNamedTelEvent(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.93
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNamedTelEvent.setChecked(mSetNamedTelEventValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e62) {
                                        e62.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 75:
                                    try {
                                        final String mAudioRTPTimeoutValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAudioRTPTimeout(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.94
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRTPTimeout.setSummary(mAudioRTPTimeoutValue);
                                                OperatorSettingsSIM.this.mAudioRTPTimeout.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e63) {
                                        e63.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.95
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRTPTimeout.setEnabled(false);
                                                OperatorSettingsSIM.this.mAudioRTPTimeout.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 76:
                                    final String setAudioRTPTimeout = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=45,1,\"" + setAudioRTPTimeout + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAudioRTPTimeout(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.96
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_AUDIO_RTP_TIMEOUT + OperatorSettingsSIM.this.mSim, setAudioRTPTimeout);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mAudioRTPTimeout.setSummary(setAudioRTPTimeout);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e64) {
                                        e64.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 77:
                                    try {
                                        final String mAudioRTCPTimeValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAudioRTCPTime(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.97
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRTCPTimeInterval.setSummary(mAudioRTCPTimeValue);
                                                OperatorSettingsSIM.this.mAudioRTCPTimeInterval.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e65) {
                                        e65.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.98
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRTCPTimeInterval.setEnabled(false);
                                                OperatorSettingsSIM.this.mAudioRTCPTimeInterval.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 78:
                                    final String setAudioRTCPTimeInterval = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=47,1,\"" + setAudioRTCPTimeInterval + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAudioRTCPTime(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.99
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_AUDIO_RTCP_TIME_INTERNAL + OperatorSettingsSIM.this.mSim, setAudioRTCPTimeInterval);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mAudioRTCPTimeInterval.setSummary(setAudioRTCPTimeInterval);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e66) {
                                        e66.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 79:
                                    try {
                                        final String mAudioRSValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAudioRSValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.100
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRS.setSummary(mAudioRSValue);
                                                OperatorSettingsSIM.this.mAudioRS.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e67) {
                                        e67.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.101
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRS.setEnabled(false);
                                                OperatorSettingsSIM.this.mAudioRS.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 80:
                                    final String setAudioRS = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=89,1,\"" + setAudioRS + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAudioRSValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.102
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_AUDIO_RS + OperatorSettingsSIM.this.mSim, setAudioRS);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mAudioRS.setSummary(setAudioRS);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e68) {
                                        e68.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 81:
                                    try {
                                        final String mAudioRRValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAudioRRValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.103
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRR.setSummary(mAudioRRValue);
                                                OperatorSettingsSIM.this.mAudioRR.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e69) {
                                        e69.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.104
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioRR.setEnabled(false);
                                                OperatorSettingsSIM.this.mAudioRR.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 82:
                                    final String setAudioRR = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=90,1,\"" + setAudioRR + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAudioRRValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.105
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_AUDIO_RR + OperatorSettingsSIM.this.mSim, setAudioRR);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mAudioRR.setSummary(setAudioRR);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e70) {
                                        e70.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 83:
                                    try {
                                        final String setVideoRsValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoRsValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.106
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoRs.setSummary(setVideoRsValue);
                                                OperatorSettingsSIM.this.mVideoRs.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e71) {
                                        e71.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.107
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoRs.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoRs.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 84:
                                    final String setVideoRs = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=91,1,\"" + setVideoRs + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoRsValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.108
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_VIDEO_RS + OperatorSettingsSIM.this.mSim, setVideoRs);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mVideoRs.setSummary(setVideoRs);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e72) {
                                        e72.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 85:
                                    try {
                                        final String mVideoRRValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoRRValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.109
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoRR.setSummary(mVideoRRValue);
                                                OperatorSettingsSIM.this.mVideoRR.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e73) {
                                        e73.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.110
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoRR.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoRR.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 86:
                                    final String setVideoRR = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=92,1,\"" + setVideoRR + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoRRValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.111
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_VIDEO_RR + OperatorSettingsSIM.this.mSim, setVideoRR);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mVideoRR.setSummary(setVideoRR);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e74) {
                                        e74.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 87:
                                    try {
                                        final String mTcallTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getTcallTimerValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.112
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mTcallTimer.setSummary(mTcallTimerValue);
                                                OperatorSettingsSIM.this.mTcallTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e75) {
                                        e75.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.113
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mTcallTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mTcallTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 88:
                                    final String setTcallTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=23,1,\"" + setTcallTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setTcallTimerValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.114
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_TCALL_TIMER + OperatorSettingsSIM.this.mSim, setTcallTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mTcallTimer.setSummary(setTcallTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e76) {
                                        e76.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 89:
                                    try {
                                        final String mRingingTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRingingTimerValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.115
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRingingTimer.setSummary(mRingingTimerValue);
                                                OperatorSettingsSIM.this.mRingingTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e77) {
                                        e77.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.116
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRingingTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mRingingTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 90:
                                    final String setRingingTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=48,1,\"" + setRingingTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRingingTimerValue(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.117
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_RINGRING_TIMER + OperatorSettingsSIM.this.mSim, setRingingTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mRingingTimer.setSummary(setRingingTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e78) {
                                        e78.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 91:
                                    try {
                                        final String mRingbackTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRingbackTimer(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.118
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRingbackTimer.setSummary(mRingbackTimerValue);
                                                OperatorSettingsSIM.this.mRingbackTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e79) {
                                        e79.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.119
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRingbackTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mRingbackTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 92:
                                    final String setRingbackTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=49,1,\"" + setRingbackTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRingbackTimer(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.120
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_RINGBACK_TIMER + OperatorSettingsSIM.this.mSim, setRingbackTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mRingbackTimer.setSummary(setRingbackTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e80) {
                                        e80.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 93:
                                    try {
                                        final String mNoAnswerTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNoAnswerTimer(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.121
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNoAnswerTimer.setSummary(mNoAnswerTimerValue);
                                                OperatorSettingsSIM.this.mNoAnswerTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e81) {
                                        e81.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.122
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNoAnswerTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mNoAnswerTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 94:
                                    final String setNoAnswerTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=50,1,\"" + setNoAnswerTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setNoAnswerTimer(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.123
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_NO_ANSWER_TIMER + OperatorSettingsSIM.this.mSim, setNoAnswerTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mNoAnswerTimer.setSummary(setNoAnswerTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e82) {
                                        e82.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 95:
                                    try {
                                        final String mSessionTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getSessionTimer(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.124
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mSessionTimer.setSummary(mSessionTimerValue);
                                                OperatorSettingsSIM.this.mSessionTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e83) {
                                        e83.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.125
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mSessionTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mSessionTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 96:
                                    final String setSessionTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=37,1,\"" + setSessionTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setSessionTimer(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.126
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_SESSION_TIMER + OperatorSettingsSIM.this.mSim, setSessionTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mSessionTimer.setSummary(setSessionTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e84) {
                                        e84.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 97:
                                    try {
                                        final String mMTSessionTimerValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMTSessionTimer(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.127
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMTSessionTimer.setSummary(mMTSessionTimerValue);
                                                OperatorSettingsSIM.this.mMTSessionTimer.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e85) {
                                        e85.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.128
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMTSessionTimer.setEnabled(false);
                                                OperatorSettingsSIM.this.mMTSessionTimer.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 98:
                                    final String setMTSessionTimer = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=39,1,\"" + setMTSessionTimer + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMTSessionTimer(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.129
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_MT_SESSION_TIMER + OperatorSettingsSIM.this.mSim, setMTSessionTimer);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mMTSessionTimer.setSummary(setMTSessionTimer);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e86) {
                                        e86.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 99:
                                    try {
                                        final String mConferenceURIValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getConferenceURI(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.130
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mConferenceURI.setSummary(mConferenceURIValue);
                                                OperatorSettingsSIM.this.mConferenceURI.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e87) {
                                        e87.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.131
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mConferenceURI.setEnabled(false);
                                                OperatorSettingsSIM.this.mConferenceURI.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 100:
                                    final String setConferenceURI = (String) msg.obj;
                                    OperatorSettingsSIM.this.mResp = "AT+SPVOLTEENG=53,1,\"" + setConferenceURI + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setConferenceURI(OperatorSettingsSIM.this.mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.132
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_CONFERENCE_URI + OperatorSettingsSIM.this.mSim, setConferenceURI);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mConferenceURI.setSummary(setConferenceURI);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e88) {
                                        e88.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 101:
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_GET_VIDEO_CODE_TYPE");
                                    try {
                                        String getVIDEOCodeType = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoCodeType(OperatorSettingsSIM.this.mSim);
                                        int value4 = Integer.valueOf(getVIDEOCodeType).intValue();
                                        String TypeValue5 = "";
                                        for (int i13 = 0; i13 < OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.video_codec_str).length; i13++) {
                                            if (((1 << i13) & value4) != 0) {
                                                if (!"".equals(TypeValue5)) {
                                                    TypeValue5 = TypeValue5 + "/";
                                                }
                                                TypeValue5 = TypeValue5 + OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.video_codec_str)[i13];
                                            }
                                        }
                                        final String summary6 = TypeValue5;
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.133
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                Log.d(OperatorSettingsSIM.TAG, "MSG_GET_VIDEO_CODE_TYPE : " + summary6);
                                                OperatorSettingsSIM.this.mVolteVideoCode.setSummary(summary6);
                                            }
                                        });
                                    } catch (Exception e89) {
                                        e89.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.134
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVolteVideoCode.setEnabled(false);
                                                OperatorSettingsSIM.this.mVolteVideoCode.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 102:
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_VIDEO_CODE_TYPE");
                                    View viewVideo = LayoutInflater.from(OperatorSettingsSIM.this.mContext).inflate(R.layout.volte_voice_code, (ViewGroup) null);
                                    LinearLayout lineVideo = (LinearLayout) viewVideo.findViewById(R.id.linearlayout_main);
                                    if (OperatorSettingsSIM.this.mVolteViewList != null) {
                                        OperatorSettingsSIM.this.mVolteViewList.clear();
                                    } else {
                                        OperatorSettingsSIM.this.mVolteViewList = new ArrayList();
                                    }
                                    for (int i14 = 0; i14 < 2; i14++) {
                                        CheckBox checkbox7 = new CheckBox(OperatorSettingsSIM.this.mContext);
                                        checkbox7.setText(OperatorSettingsSIM.this.mContext.getResources().getStringArray(R.array.video_codec_str)[i14]);
                                        lineVideo.addView(checkbox7);
                                        OperatorSettingsSIM.this.mVolteViewList.add(checkbox7);
                                    }
                                    try {
                                        String mVoiceCodeType2 = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoCodeType(OperatorSettingsSIM.this.mSim);
                                        final int voiceCodeTypeValue3 = Integer.valueOf(mVoiceCodeType2).intValue();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.135
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                for (int i15 = 0; i15 < OperatorSettingsSIM.this.mVolteViewList.size(); i15++) {
                                                    if ((voiceCodeTypeValue3 & (1 << i15)) != 0) {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i15)).setChecked(true);
                                                    } else {
                                                        ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i15)).setChecked(false);
                                                    }
                                                }
                                            }
                                        });
                                    } catch (Exception e90) {
                                        e90.printStackTrace();
                                    }
                                    OperatorSettingsSIM.this.popDialog(viewVideo, 102);
                                    break;
                                case 103:
                                    try {
                                        final String mGetReliable180Value = OperatorSettingsSIM.teleApi.volteOperatorSettings().getReliable180(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.136
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetReliable180Value.contains("1");
                                                OperatorSettingsSIM.this.mReliable180Switch.setChecked(open);
                                                OperatorSettingsSIM.this.mReliable180Switch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e91) {
                                        e91.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.137
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mReliable180Switch.setEnabled(false);
                                                OperatorSettingsSIM.this.mReliable180Switch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 104:
                                    final String mSetReliable180Value = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_RELIABLE_180 mSetReliable180Value: " + mSetReliable180Value);
                                    String mResp = "AT+SPVOLTEENG=100,1,\"" + mSetReliable180Value + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setReliable180(mResp, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.138
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mReliable180Switch.setChecked(mSetReliable180Value.contains("1"));
                                            }
                                        });
                                    } catch (Exception e92) {
                                        e92.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 105:
                                    try {
                                        final String mGetDirectAlertingValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getDirectAlerting(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.139
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetDirectAlertingValue.contains("1");
                                                OperatorSettingsSIM.this.mDirectAlertingSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mDirectAlertingSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e93) {
                                        e93.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.140
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDirectAlertingSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mDirectAlertingSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 106:
                                    final String mSetDirectAlertingValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_DIRECT_ALERTING mSetDirectAlertingValue: " + mSetDirectAlertingValue);
                                    String mResp2 = "AT+SPVOLTEENG=99,1,\"" + mSetDirectAlertingValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setDirectAlerting(mResp2, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.141
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDirectAlertingSwitch.setChecked(mSetDirectAlertingValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e94) {
                                        e94.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 107:
                                    try {
                                        final String mGetResourceAlwaysValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getResourceAlways(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.142
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetResourceAlwaysValue.contains("1");
                                                OperatorSettingsSIM.this.mResourceAlwaysSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mResourceAlwaysSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e95) {
                                        e95.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.143
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mResourceAlwaysSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mResourceAlwaysSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 108:
                                    final String mSetResourceAlwaysValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_RESOURCE_ALWAYS mSetResourceAlwaysValue: " + mSetResourceAlwaysValue);
                                    String mSetResourceAlways = "AT+SPVOLTEENG=102,1,\"" + mSetResourceAlwaysValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setResourceAlways(mSetResourceAlways, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.144
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mResourceAlwaysSwitch.setChecked(mSetResourceAlwaysValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e96) {
                                        e96.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 109:
                                    try {
                                        final String mGetPreconditionValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getPrecondition(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.145
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetPreconditionValue.contains("1");
                                                OperatorSettingsSIM.this.mPreconditionSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mPreconditionSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e97) {
                                        e97.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.146
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mPreconditionSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mPreconditionSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 110:
                                    final String mSetPreconditionValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_PRECONDITION mSetPreconditionValue: " + mSetPreconditionValue);
                                    String mSetPrecondition = "AT+SPVOLTEENG=93,1,\"" + mSetPreconditionValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setPrecondition(mSetPrecondition, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.147
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mPreconditionSwitch.setChecked(mSetPreconditionValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e98) {
                                        e98.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 111:
                                    try {
                                        final String mGetVideoUpgradeValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoUpgrade(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.148
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetVideoUpgradeValue.contains("1");
                                                OperatorSettingsSIM.this.mVideoUpgradeSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mVideoUpgradeSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e99) {
                                        e99.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.149
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoUpgradeSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoUpgradeSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 112:
                                    final String mSetVideoUpgradeValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_VIDEO_UPGRADE mSetVideoUpgradeValue: " + mSetVideoUpgradeValue);
                                    String mSetVideoUpgrade = "AT+SPVOLTEENG=94,1,\"" + mSetVideoUpgradeValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoUpgrade(mSetVideoUpgrade, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.150
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoUpgradeSwitch.setChecked(mSetVideoUpgradeValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e100) {
                                        e100.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 113:
                                    try {
                                        final String mGetVideoEarlyValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoEarly(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.151
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetVideoEarlyValue.contains("1");
                                                OperatorSettingsSIM.this.mVideoEarlySwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mVideoEarlySwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e101) {
                                        e101.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.152
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoEarlySwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoEarlySwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 114:
                                    final String mSetVideoEarlyValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_VIDEO_EARLY_MEDIA mSetVideoEarlyValue: " + mSetVideoEarlyValue);
                                    String mSetVideoEarly = "AT+SPVOLTEENG=51,1,\"" + mSetVideoEarlyValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoEarly(mSetVideoEarly, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.153
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoEarlySwitch.setChecked(mSetVideoEarlyValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e102) {
                                        e102.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 115:
                                    try {
                                        final String mGetAlertingValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAlertingValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.154
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetAlertingValue.contains("1");
                                                OperatorSettingsSIM.this.mAlertingSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mAlertingSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e103) {
                                        e103.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.155
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAlertingSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mAlertingSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 116:
                                    final String mSetAlertingValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_ALERTING mSetAlertingValue: " + mSetAlertingValue);
                                    String mSetAlerting = "AT+SPVOLTEENG=80,1,\"" + mSetAlertingValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAlertingValue(mSetAlerting, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.156
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAlertingSwitch.setChecked(mSetAlertingValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e104) {
                                        e104.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 117:
                                    try {
                                        final String mGetMidCallSwitchValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMidCallSwitch(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.157
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetMidCallSwitchValue.contains("1");
                                                OperatorSettingsSIM.this.mMidCallSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mMidCallSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e105) {
                                        e105.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.158
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMidCallSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mMidCallSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 118:
                                    final String mSetMidCallSwitchValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_MID_CALL mSetMidCallSwitchValue: " + mSetMidCallSwitchValue);
                                    String mSetMidCallSwitch = "AT+SPVOLTEENG=81,1,\"" + mSetMidCallSwitchValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMidCallSwitch(mSetMidCallSwitch, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.159
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMidCallSwitch.setChecked(mSetMidCallSwitchValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e106) {
                                        e106.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 119:
                                    try {
                                        final String mGetPreAlertingSwitchValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getPreAlerting(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.160
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetPreAlertingSwitchValue.contains("1");
                                                OperatorSettingsSIM.this.mPreAlertingSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mPreAlertingSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e107) {
                                        e107.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.161
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mPreAlertingSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mPreAlertingSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 120:
                                    final String mSetPreAlertingSwitchValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_PER_ALERTING mSetPreAlertingSwitchValue: " + mSetPreAlertingSwitchValue);
                                    String mSetPreAlertingSwitch = "AT+SPVOLTEENG=82,1,\"" + mSetPreAlertingSwitchValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setPreAlerting(mSetPreAlertingSwitch, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.162
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mPreAlertingSwitch.setChecked(mSetPreAlertingSwitchValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e108) {
                                        e108.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_RSRVCC_SUPPORT /* 121 */:
                                    try {
                                        final String mGetRSRVCCSupportValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRSRVCCSupport(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.163
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetRSRVCCSupportValue.contains("1");
                                                OperatorSettingsSIM.this.mRSRVCCSupportSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mRSRVCCSupportSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e109) {
                                        e109.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.164
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCSupportSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mRSRVCCSupportSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_RSRVCC_SUPPORT /* 122 */:
                                    final String mSetRSRVCCSupportValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_RSRVCC_SUPPORT mSetRSRVCCSupportValue: " + mSetRSRVCCSupportValue);
                                    String mSetRSRVCCSupport = "AT+SPVOLTEENG=83,1,\"" + mSetRSRVCCSupportValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRSRVCCSupport(mSetRSRVCCSupport, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.165
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCSupportSwitch.setChecked(mSetRSRVCCSupportValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e110) {
                                        e110.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_RSRVCC_ALERTING /* 123 */:
                                    try {
                                        final String mGetRSRVCCAlertValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRSRVCCAlert(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.166
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetRSRVCCAlertValue.contains("1");
                                                OperatorSettingsSIM.this.mRSRVCCAlertSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mRSRVCCAlertSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e111) {
                                        e111.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.167
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCAlertSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mRSRVCCAlertSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_RSRVCC_ALERTING /* 124 */:
                                    final String mSetRSRVCCAlertValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_RSRVCC_ALERTING mSetRSRVCCAlertValue: " + mSetRSRVCCAlertValue);
                                    String mSetRSRVCCAlert = "AT+SPVOLTEENG=84,1,\"" + mSetRSRVCCAlertValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRSRVCCAlert(mSetRSRVCCAlert, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.168
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCAlertSwitch.setChecked(mSetRSRVCCAlertValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e112) {
                                        e112.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_RSRVCC_MID_CALL /* 125 */:
                                    try {
                                        final String mGetRSRVCCMidValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getRSRVCCMid(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.169
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetRSRVCCMidValue.contains("1");
                                                OperatorSettingsSIM.this.mRSRVCCMidSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mRSRVCCMidSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e113) {
                                        e113.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.170
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCMidSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mRSRVCCMidSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_RSRVCC_MID_CALL /* 126 */:
                                    final String mSetRSRVCCMidValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_RSRVCC_MID_CALL mSetRSRVCCMidValue: " + mSetRSRVCCMidValue);
                                    String mSetRSRVCCMid = "AT+SPVOLTEENG=85,1,\"" + mSetRSRVCCMidValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setRSRVCCMid(mSetRSRVCCMid, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.171
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mRSRVCCMidSwitch.setChecked(mSetRSRVCCMidValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e114) {
                                        e114.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 127:
                                    try {
                                        final String mNationalURLValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getNationalURL(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.175
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNationalURL.setValueIndex(Integer.valueOf(mNationalURLValue).intValue());
                                                OperatorSettingsSIM.this.mNationalURL.setSummary(OperatorSettingsSIM.this.mNationalURL.getEntry());
                                            }
                                        });
                                    } catch (Exception e115) {
                                        e115.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.176
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNationalURL.setEnabled(false);
                                                OperatorSettingsSIM.this.mNationalURL.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 128:
                                    String setNationalURL = (String) msg.obj;
                                    final int setNationalURLValue = Integer.valueOf(setNationalURL).intValue();
                                    String mResp3 = "AT+SPVOLTEENG=42,1,\"" + setNationalURLValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setNationalURL(mResp3, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.177
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mNationalURL.setValueIndex(setNationalURLValue);
                                                OperatorSettingsSIM.this.mNationalURL.setSummary(OperatorSettingsSIM.this.mNationalURL.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e116) {
                                        e116.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 129:
                                    try {
                                        final String mInternationalURLValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getInternationalURL(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.178
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mInternationalURL.setValueIndex(Integer.valueOf(mInternationalURLValue).intValue());
                                                OperatorSettingsSIM.this.mInternationalURL.setSummary(OperatorSettingsSIM.this.mInternationalURL.getEntry());
                                            }
                                        });
                                    } catch (Exception e117) {
                                        e117.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.179
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mInternationalURL.setEnabled(false);
                                                OperatorSettingsSIM.this.mInternationalURL.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 130:
                                    String setInternationalURL = (String) msg.obj;
                                    final int setInternationalURLValue = Integer.valueOf(setInternationalURL).intValue();
                                    String mResp4 = "AT+SPVOLTEENG=43,1,\"" + setInternationalURLValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setInternationalURL(mResp4, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.180
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mInternationalURL.setValueIndex(setInternationalURLValue);
                                                OperatorSettingsSIM.this.mInternationalURL.setSummary(OperatorSettingsSIM.this.mInternationalURL.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e118) {
                                        e118.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 131:
                                    try {
                                        final String mTimerRefresherValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getTimerRefresher(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.181
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setValueIndex(Integer.valueOf(mTimerRefresherValue).intValue());
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setSummary(OperatorSettingsSIM.this.mSessionTimerRefresher.getEntry());
                                            }
                                        });
                                    } catch (Exception e119) {
                                        e119.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.182
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setEnabled(false);
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 132:
                                    String setTimerRefresher = (String) msg.obj;
                                    final int setTimerRefresherValue = Integer.valueOf(setTimerRefresher).intValue();
                                    String mResp5 = "AT+SPVOLTEENG=38,1,\"" + setTimerRefresherValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setTimerRefresher(mResp5, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.183
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setValueIndex(setTimerRefresherValue);
                                                OperatorSettingsSIM.this.mSessionTimerRefresher.setSummary(OperatorSettingsSIM.this.mSessionTimerRefresher.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e120) {
                                        e120.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 133:
                                    try {
                                        final String mMTTimerRefresherValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getMTTimerRefresher(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.184
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setValueIndex(Integer.valueOf(mMTTimerRefresherValue).intValue());
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setSummary(OperatorSettingsSIM.this.mMTSessionTimerRefresher.getEntry());
                                            }
                                        });
                                    } catch (Exception e121) {
                                        e121.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.185
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setEnabled(false);
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 134:
                                    String setMTTimerRefresher = (String) msg.obj;
                                    final int setMTTimerRefresherValue = Integer.valueOf(setMTTimerRefresher).intValue();
                                    String mResp6 = "AT+SPVOLTEENG=40,1,\"" + setMTTimerRefresherValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setMTTimerRefresher(mResp6, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.186
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setValueIndex(setMTTimerRefresherValue);
                                                OperatorSettingsSIM.this.mMTSessionTimerRefresher.setSummary(OperatorSettingsSIM.this.mMTSessionTimerRefresher.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e122) {
                                        e122.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 135:
                                    try {
                                        final String mVideoAudioMergeValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoAudioMerge(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.187
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setValueIndex(Integer.valueOf(mVideoAudioMergeValue).intValue());
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setSummary(OperatorSettingsSIM.this.mVideoAudioMerge.getEntry());
                                            }
                                        });
                                    } catch (Exception e123) {
                                        e123.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.188
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 136:
                                    String setVideoAudioMerge = (String) msg.obj;
                                    final int setVideoAudioMergeValue = Integer.valueOf(setVideoAudioMerge).intValue();
                                    String mResp7 = "AT+SPVOLTEENG=54,1,\"" + setVideoAudioMergeValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoAudioMerge(mResp7, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.189
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setValueIndex(setVideoAudioMergeValue);
                                                OperatorSettingsSIM.this.mVideoAudioMerge.setSummary(OperatorSettingsSIM.this.mVideoAudioMerge.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e124) {
                                        e124.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 137:
                                    try {
                                        final String mGetAudioDynamicValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getAudioDynamic(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.190
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetAudioDynamicValue.contains("1");
                                                OperatorSettingsSIM.this.mAudioDynamicEnable.setChecked(open);
                                                OperatorSettingsSIM.this.mAudioDynamicEnable.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e125) {
                                        e125.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.191
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioDynamicEnable.setEnabled(false);
                                                OperatorSettingsSIM.this.mAudioDynamicEnable.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 138:
                                    final String mSetAudioDynamicValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_AUDIO_DYNAMIC_ENABLE mSetAudioDynamicValue: " + mSetAudioDynamicValue);
                                    String mSetAudioDynamic = "AT+SPVOLTEENG=104,1,\"" + mSetAudioDynamicValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setAudioDynamic(mSetAudioDynamic, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.192
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mAudioDynamicEnable.setChecked(mSetAudioDynamicValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e126) {
                                        e126.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 139:
                                    try {
                                        final String mGetVideoDynamicValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoDynamic(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.193
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetVideoDynamicValue.contains("1");
                                                OperatorSettingsSIM.this.mVideoDynamicEnable.setChecked(open);
                                                OperatorSettingsSIM.this.mVideoDynamicEnable.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e127) {
                                        e127.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.194
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoDynamicEnable.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoDynamicEnable.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 140:
                                    final String mSetVideoDynamicValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_VIDEO_DYNAMIC_ENABLE mSetVideoDynamicValue: " + mSetVideoDynamicValue);
                                    String mSetVideoDynamic = "AT+SPVOLTEENG=20,1,\"" + mSetVideoDynamicValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoDynamic(mSetVideoDynamic, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.195
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoDynamicEnable.setChecked(mSetVideoDynamicValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e128) {
                                        e128.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 141:
                                    try {
                                        final String mBsfURIValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getBsfURIValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.196
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mBsfURI.setSummary(mBsfURIValue);
                                                OperatorSettingsSIM.this.mBsfURI.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e129) {
                                        e129.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.197
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mBsfURI.setEnabled(false);
                                                OperatorSettingsSIM.this.mBsfURI.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 142:
                                    final String setBsfURI = (String) msg.obj;
                                    String mResp8 = "AT+SPVOLTEENG=60,1,\"" + setBsfURI + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setBsfURIValue(mResp8, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.198
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_BSF_URI + OperatorSettingsSIM.this.mSim, setBsfURI);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mBsfURI.setSummary(setBsfURI);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e130) {
                                        e130.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 143:
                                    try {
                                        final String mBsfPORTValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getBsfPORTValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.199
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mBsfPort.setSummary(mBsfPORTValue);
                                                OperatorSettingsSIM.this.mBsfPort.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e131) {
                                        e131.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.200
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mBsfPort.setEnabled(false);
                                                OperatorSettingsSIM.this.mBsfPort.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 144:
                                    final String setBsfPort = (String) msg.obj;
                                    String mResp9 = "AT+SPVOLTEENG=61,1,\"" + setBsfPort + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setBsfPORTValue(mResp9, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.201
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_BSF_PORT + OperatorSettingsSIM.this.mSim, setBsfPort);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mBsfPort.setSummary(setBsfPort);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e132) {
                                        e132.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 145:
                                    try {
                                        final String mXcapURIValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getXcapURIValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.202
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapURI.setSummary(mXcapURIValue);
                                                OperatorSettingsSIM.this.mXcapURI.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e133) {
                                        e133.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.203
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapURI.setEnabled(false);
                                                OperatorSettingsSIM.this.mXcapURI.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 146:
                                    final String setXcapURI = (String) msg.obj;
                                    String mResp10 = "AT+SPVOLTEENG=62,1,\"" + setXcapURI + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setXcapURIValue(mResp10, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.204
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_XCAP_URI + OperatorSettingsSIM.this.mSim, setXcapURI);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mXcapURI.setSummary(setXcapURI);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e134) {
                                        e134.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_XCAP_PORT /* 147 */:
                                    try {
                                        final String mXcapPortValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getXcapPortValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.205
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapPort.setSummary(mXcapPortValue);
                                                OperatorSettingsSIM.this.mXcapPort.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e135) {
                                        e135.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.206
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapPort.setEnabled(false);
                                                OperatorSettingsSIM.this.mXcapPort.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_XCAP_PORT /* 148 */:
                                    final String setXcapPort = (String) msg.obj;
                                    String mResp11 = "AT+SPVOLTEENG=63,1,\"" + setXcapPort + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setXcapPortValue(mResp11, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.207
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_XCAP_PORT + OperatorSettingsSIM.this.mSim, setXcapPort);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mXcapPort.setSummary(setXcapPort);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e136) {
                                        e136.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_XCAP_AUID /* 149 */:
                                    try {
                                        final String mXcapAuidValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getXcapAuidValue(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.208
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapAuid.setSummary(mXcapAuidValue);
                                                OperatorSettingsSIM.this.mXcapAuid.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e137) {
                                        e137.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.209
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mXcapAuid.setEnabled(false);
                                                OperatorSettingsSIM.this.mXcapAuid.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_XCAP_AUID /* 150 */:
                                    final String setXcapAuid = (String) msg.obj;
                                    String mResp12 = "AT+SPVOLTEENG=64,1,\"" + setXcapAuid + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setXcapAuidValue(mResp12, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.210
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                SharedPreferences.Editor editor = OperatorSettingsSIM.this.mSharePref.edit();
                                                editor.putString(OperatorSettingsSIM.KEY_XCAP_AUID + OperatorSettingsSIM.this.mSim, setXcapAuid);
                                                editor.commit();
                                                OperatorSettingsSIM.this.mXcapAuid.setSummary(setXcapAuid);
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, "Success", 0).show();
                                            }
                                        });
                                    } catch (Exception e138) {
                                        e138.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 151:
                                    try {
                                        final String mGetLocalCallSwitchValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getLocalCall(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.211
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetLocalCallSwitchValue.contains("1");
                                                OperatorSettingsSIM.this.mLocalCallSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mLocalCallSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e139) {
                                        e139.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.212
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mLocalCallSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mLocalCallSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 152:
                                    final String mSetLocalCallSwitchValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_LOCAL_CALL mSetLocalCallSwitchValue: " + mSetLocalCallSwitchValue);
                                    String mSetLocalCallSwitch = "AT+SPVOLTEENG=55,1,\"" + mSetLocalCallSwitchValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setLocalCall(mSetLocalCallSwitch, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.213
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mLocalCallSwitch.setChecked(mSetLocalCallSwitchValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e140) {
                                        e140.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 153:
                                    try {
                                        final String mGetDnsSrvEnableValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getDnsSrvEnable(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.214
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetDnsSrvEnableValue.contains("1");
                                                OperatorSettingsSIM.this.mDnsSrvEnableSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mDnsSrvEnableSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e141) {
                                        e141.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.215
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDnsSrvEnableSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mDnsSrvEnableSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 154:
                                    final String mSetDnsSrvEnableValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_DNS_SRV_ENABLE mSetDnsSrvEnableValue: " + mSetDnsSrvEnableValue);
                                    String mSetDnsSrvEnable = "AT+SPVOLTEENG=56,1,\"" + mSetDnsSrvEnableValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setDnsSrvEnable(mSetDnsSrvEnable, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.216
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mDnsSrvEnableSwitch.setChecked(mSetDnsSrvEnableValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e142) {
                                        e142.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 155:
                                    try {
                                        final String mGetHttpsEnableValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getHttpsEnable(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.217
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetHttpsEnableValue.contains("1");
                                                OperatorSettingsSIM.this.mHttpsEnableSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mHttpsEnableSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e143) {
                                        e143.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.218
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mHttpsEnableSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mHttpsEnableSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 156:
                                    final String mSetHttpsEnableValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_HTTPS_ENABLE mSetHttpsEnableValue: " + mSetHttpsEnableValue);
                                    String mSetHttpsEnable = "AT+SPVOLTEENG=58,1,\"" + mSetHttpsEnableValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setHttpsEnable(mSetHttpsEnable, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.219
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mHttpsEnableSwitch.setChecked(mSetHttpsEnableValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e144) {
                                        e144.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 157:
                                    try {
                                        final String mGetActivateCfnlValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getActivateCfnl(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.220
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetActivateCfnlValue.contains("1");
                                                OperatorSettingsSIM.this.mActivateCfnlSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mActivateCfnlSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e145) {
                                        e145.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.221
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mActivateCfnlSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mActivateCfnlSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 158:
                                    final String mSetActivateCfnlValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_ACTIVATE_CFNL mSetActivateCfnlValue: " + mSetActivateCfnlValue);
                                    String mSetActivateCfnl = "AT+SPVOLTEENG=103,1,\"" + mSetActivateCfnlValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setActivateCfnl(mSetActivateCfnl, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.222
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mActivateCfnlSwitch.setChecked(mSetActivateCfnlValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e146) {
                                        e146.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case 159:
                                    try {
                                        final String mGetHttpPutMediaValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getHttpPutMedia(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.223
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                boolean open = mGetHttpPutMediaValue.contains("1");
                                                OperatorSettingsSIM.this.mHttpPutMediaSwitch.setChecked(open);
                                                OperatorSettingsSIM.this.mHttpPutMediaSwitch.setEnabled(true);
                                            }
                                        });
                                    } catch (Exception e147) {
                                        e147.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.224
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mHttpPutMediaSwitch.setEnabled(false);
                                                OperatorSettingsSIM.this.mHttpPutMediaSwitch.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case 160:
                                    final String mSetHttpPutMediaValue = (String) msg.obj;
                                    Log.d(OperatorSettingsSIM.TAG, "MSG_SET_HTTP_PUT_MEDIA mSetHttpPutMediaValue: " + mSetHttpPutMediaValue);
                                    String mSetHttpPutMedia = "AT+SPVOLTEENG=59,1,\"" + mSetHttpPutMediaValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setHttpPutMedia(mSetHttpPutMedia, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.225
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mHttpPutMediaSwitch.setChecked(mSetHttpPutMediaValue.contains("1"));
                                            }
                                        });
                                    } catch (Exception e148) {
                                        e148.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_IP_PRIORITY /* 161 */:
                                    try {
                                        final String mIPPriorityValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getIPPriority(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.226
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mIPPriority.setValueIndex(Integer.valueOf(mIPPriorityValue).intValue());
                                                OperatorSettingsSIM.this.mIPPriority.setSummary(OperatorSettingsSIM.this.mIPPriority.getEntry());
                                            }
                                        });
                                    } catch (Exception e149) {
                                        e149.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.227
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mIPPriority.setEnabled(false);
                                                OperatorSettingsSIM.this.mIPPriority.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_IP_PRIORITY /* 162 */:
                                    String setIPPriority = (String) msg.obj;
                                    final int setIPPriorityValue = Integer.valueOf(setIPPriority).intValue();
                                    String mResp13 = "AT+SPVOLTEENG=57,1,\"" + setIPPriorityValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setIPPriority(mResp13, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.228
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mIPPriority.setValueIndex(setIPPriorityValue);
                                                OperatorSettingsSIM.this.mIPPriority.setSummary(OperatorSettingsSIM.this.mIPPriority.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e150) {
                                        e150.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_GET_VIDEO_MAX_RESOLUTION /* 163 */:
                                    try {
                                        final String mVideoMaxValue = OperatorSettingsSIM.teleApi.volteOperatorSettings().getVideoMaxResolution(OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.172
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setValueIndex(Integer.valueOf(mVideoMaxValue).intValue());
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setSummary(OperatorSettingsSIM.this.mVideoMaxResolution.getEntry());
                                            }
                                        });
                                    } catch (Exception e151) {
                                        e151.printStackTrace();
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.173
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setEnabled(false);
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setSummary(R.string.feature_abnormal);
                                            }
                                        });
                                        return;
                                    }
                                    break;
                                case OperatorSettingsSIM.MSG_SET_VIDEO_MAX_RESOLUTION /* 164 */:
                                    String setVideoMaxResolution = (String) msg.obj;
                                    final int setVideoMaxValue = Integer.valueOf(setVideoMaxResolution).intValue();
                                    String mResp14 = "AT+SPVOLTEENG=106,1,\"" + setVideoMaxValue + "\"";
                                    try {
                                        OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoMaxResolution(mResp14, OperatorSettingsSIM.this.mSim);
                                        OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.IpsecHandler.174
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setValueIndex(setVideoMaxValue);
                                                OperatorSettingsSIM.this.mVideoMaxResolution.setSummary(OperatorSettingsSIM.this.mVideoMaxResolution.getEntry());
                                                Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                                            }
                                        });
                                    } catch (Exception e152) {
                                        e152.printStackTrace();
                                        OperatorSettingsSIM.this.showFailToast();
                                        return;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }

        public /* synthetic */ void lambda$handleMessage$0$OperatorSettingsSIM$IpsecHandler(String mGetIPsec) {
            boolean open = mGetIPsec.contains("1");
            OperatorSettingsSIM.this.mIPsecSwitch.setChecked(open);
            OperatorSettingsSIM.this.mIPsecSwitch.setEnabled(true);
        }

        static /* synthetic */ boolean lambda$handleMessage$1(int value, int i) {
            return ((1 << i) & value) != 0;
        }

        static /* synthetic */ String lambda$handleMessage$2(String[] arr, int i) {
            return arr[i];
        }

        public /* synthetic */ void lambda$handleMessage$3$OperatorSettingsSIM$IpsecHandler(String values) {
            Log.d(OperatorSettingsSIM.TAG, "MSG_GET_INTEGRITY_ALGORITHM : " + values);
            OperatorSettingsSIM.this.mIntegrityAlgorithm.setSummary(values);
        }

        public /* synthetic */ void lambda$handleMessage$4$OperatorSettingsSIM$IpsecHandler() {
            OperatorSettingsSIM.this.mIntegrityAlgorithm.setEnabled(false);
            OperatorSettingsSIM.this.mIntegrityAlgorithm.setSummary(R.string.feature_abnormal);
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
    public void showFailToast() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(OperatorSettingsSIM.this.mContext, "Fail", 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void popDialog(View view, int dialogType) {
        int titleId_tmp;
        int arrayId_tmp;
        String para_tmp;
        Preference pref_tmp;
        if (dialogType == 8) {
            Log.d(TAG, "popDialog MSG_SET_INTEGRITY_ALGORITHM");
            titleId_tmp = R.string.Integrity_algorithm;
            arrayId_tmp = R.array.integrity_algorithm_str;
            para_tmp = "1";
            pref_tmp = this.mIntegrityAlgorithm;
        } else if (dialogType == 10) {
            Log.d(TAG, "popDialog MSG_SET_CIPHER_ALGORITHM");
            titleId_tmp = R.string.cipher_algorithm;
            arrayId_tmp = R.array.cipher_algorithm_str;
            para_tmp = "1";
            pref_tmp = this.mCipherAlgorithm;
        } else if (dialogType == 46) {
            titleId_tmp = R.string.volte_voice_code_type;
            arrayId_tmp = R.array.volte_voice_code;
            para_tmp = "1";
            pref_tmp = this.mVolteVoiceCode;
        } else if (dialogType == 70) {
            titleId_tmp = R.string.rate_set;
            arrayId_tmp = R.array.evs_wb_bitrate_set;
            para_tmp = "7";
            pref_tmp = this.mEvsRateSet;
        } else if (dialogType == 66) {
            titleId_tmp = R.string.rate_set;
            arrayId_tmp = R.array.evs_wb_bitrate_set;
            para_tmp = "9";
            pref_tmp = this.mWbRateSet;
        } else if (dialogType == 68) {
            titleId_tmp = R.string.rate_set;
            arrayId_tmp = R.array.nb_bitrate_set;
            para_tmp = "11";
            pref_tmp = this.mNbRateSet;
        } else if (dialogType == 102) {
            titleId_tmp = R.string.video_codec_type;
            arrayId_tmp = R.array.video_codec_str;
            para_tmp = "1";
            pref_tmp = this.mVolteVideoCode;
        } else {
            return;
        }
        int stringArrayId = arrayId_tmp;
        int diaTitleId = titleId_tmp;
        Preference preference = pref_tmp;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this.mContext).setView(view).setNegativeButton(getString(R.string.alertdialog_cancel), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertBuilder.setTitle(diaTitleId).setPositiveButton(getString(R.string.alertdialog_ok), new AnonymousClass3(dialogType, stringArrayId, preference));
        this.mAlertDialog = alertBuilder.create();
        this.mAlertDialog.setCanceledOnTouchOutside(false);
        this.mAlertDialog.show();
    }

    /* JADX INFO: renamed from: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM$3, reason: invalid class name */
    class AnonymousClass3 implements DialogInterface.OnClickListener {
        final /* synthetic */ int val$dialogType;
        final /* synthetic */ Preference val$preference;
        final /* synthetic */ int val$stringArrayId;

        AnonymousClass3(int i, int i2, Preference preference) {
            this.val$dialogType = i;
            this.val$stringArrayId = i2;
            this.val$preference = preference;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            int i = this.val$dialogType;
            int listSize = (i == 8 || i == 10 || i == 46 || i == 102) ? OperatorSettingsSIM.this.mVolteViewList.size() : OperatorSettingsSIM.this.mVolteViewList.size() - 1;
            final String[] arr = OperatorSettingsSIM.this.mContext.getResources().getStringArray(this.val$stringArrayId);
            final int[] tempInt = {0};
            final String typeValue = (String) IntStream.range(0, listSize).filter(new IntPredicate() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$3$aBlHxZsll3aEC2kajbHLMEzWeN0
                @Override // java.util.function.IntPredicate
                public final boolean test(int i2) {
                    return this.f$0.lambda$onClick$0$OperatorSettingsSIM$3(i2);
                }
            }).peek(new IntConsumer() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$3$QtTwwxjYlvR2Slc7tfG4_GAG1E0
                @Override // java.util.function.IntConsumer
                public final void accept(int i2) {
                    OperatorSettingsSIM.AnonymousClass3.lambda$onClick$1(tempInt, i2);
                }
            }).mapToObj(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$3$AfnO-eNR8aCSid5Q7HrZPvggsKQ
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return OperatorSettingsSIM.AnonymousClass3.lambda$onClick$2(arr, i2);
                }
            }).collect(Collectors.joining("/"));
            int value = tempInt[0];
            Log.d(OperatorSettingsSIM.TAG, "value=" + value + ",summary=" + typeValue);
            if (value == 0) {
                OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$3$S1EQUGUoHKXO4f9lKZN-usloGlQ
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClick$3$OperatorSettingsSIM$3();
                    }
                });
                return;
            }
            int i2 = this.val$dialogType;
            if (i2 == 8) {
                String cmd = "AT+SPVOLTEENG=96,1,\"" + value + "\"";
                Log.d(OperatorSettingsSIM.TAG, "MSG_SET_INTEGRITY_ALGORITHM cmd: " + cmd);
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setIntegrityAlgorithm(cmd, OperatorSettingsSIM.this.mSim);
                    Handler handler = OperatorSettingsSIM.this.mUiThread;
                    final Preference preference = this.val$preference;
                    handler.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$OperatorSettingsSIM$3$_v5l1rXJdSS7OekYL801pWW3bww
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onClick$4$OperatorSettingsSIM$3(preference, typeValue);
                        }
                    });
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (i2 == 10) {
                String cmd2 = "AT+SPVOLTEENG=97,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setCipherAlgorithm(cmd2, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            if (i2 == 46) {
                String cmd3 = "AT+SPVOLTEENG=65,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setVoiceCodeType(cmd3, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    return;
                }
            }
            if (i2 == 70) {
                String cmd4 = "AT+SPVOLTEENG=68,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setEvsRate(cmd4, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.3
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                    return;
                } catch (Exception e4) {
                    e4.printStackTrace();
                    return;
                }
            }
            if (i2 == 66) {
                String cmd5 = "AT+SPVOLTEENG=67,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setWbRate(cmd5, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.4
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                    return;
                } catch (Exception e5) {
                    e5.printStackTrace();
                    return;
                }
            }
            if (i2 == 68) {
                String cmd6 = "AT+SPVOLTEENG=66,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setNbRate(cmd6, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.5
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                    return;
                } catch (Exception e6) {
                    e6.printStackTrace();
                    return;
                }
            }
            if (i2 == 102) {
                String cmd7 = "AT+SPVOLTEENG=9,1,\"" + value + "\"";
                try {
                    OperatorSettingsSIM.teleApi.volteOperatorSettings().setVideoCodeType(cmd7, OperatorSettingsSIM.this.mSim);
                    OperatorSettingsSIM.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.OperatorSettingsSIM.3.6
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass3.this.val$preference.setSummary(typeValue);
                            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
                        }
                    });
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
        }

        public /* synthetic */ boolean lambda$onClick$0$OperatorSettingsSIM$3(int i) {
            return ((CheckBox) OperatorSettingsSIM.this.mVolteViewList.get(i)).isChecked();
        }

        static /* synthetic */ void lambda$onClick$1(int[] tempInt, int i) {
            tempInt[0] = tempInt[0] + (1 << i);
        }

        static /* synthetic */ String lambda$onClick$2(String[] arr, int i) {
            return arr[i];
        }

        public /* synthetic */ void lambda$onClick$3$OperatorSettingsSIM$3() {
            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.volte_set_error, 0).show();
        }

        public /* synthetic */ void lambda$onClick$4$OperatorSettingsSIM$3(Preference preference, String summary) {
            preference.setSummary(summary);
            Toast.makeText(OperatorSettingsSIM.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }
    }
}
