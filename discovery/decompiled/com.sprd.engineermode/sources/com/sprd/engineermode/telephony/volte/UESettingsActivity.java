package com.sprd.engineermode.telephony.volte;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.Arrays;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes4.dex */
public class UESettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_MAX_BANDWIDTH = "max_bandwidth";
    private static final String KEY_MAX_BITRATE = "max_bitrate";
    private static final String KEY_SMS_PDU = "key_sms_pdu";
    private static final String KEY_SPRD_VOLTE = "sprd_volte";
    private static final String KEY_VIDEO_CALL_ENABLE = "video_call_enable";
    private static final String KEY_VIDEO_CODE = "volte_video_code";
    private static final String KEY_VIDEO_CONF_ENABLE = "video_conference_enable";
    private static final String KEY_VOICE_CODE = "volte_voice_code";
    private static final int MSG_GET_MAX_BANDWIDTH = 31;
    private static final int MSG_GET_MAX_BITRATE = 33;
    private static final int MSG_GET_SMS_PDU = 40;
    private static final int MSG_GET_SPRD_VOLTE = 38;
    private static final int MSG_GET_VIDEO_CALL_ENABLE = 42;
    private static final int MSG_GET_VIDEO_CODE_TYPE = 36;
    private static final int MSG_GET_VIDEO_CONFERENCE_ENABLE = 44;
    private static final int MSG_GET_VOICE_CODE_TYPE = 27;
    private static final int MSG_SET_MAX_BANDWIDTH = 32;
    private static final int MSG_SET_MAX_BITRATE = 34;
    private static final int MSG_SET_SMS_PDU = 39;
    private static final int MSG_SET_SPRD_VOLTE = 37;
    private static final int MSG_SET_VIDEO_CALL_ENABLE = 41;
    private static final int MSG_SET_VIDEO_CODE_TYPE = 35;
    private static final int MSG_SET_VIDEO_CONFERENCE_ENABLE = 43;
    private static final int MSG_SET_VOICE_CODE_TYPE = 28;
    private static final String TAG = "UESettingsActivity";
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private static ITelephonyApi.IVolteUeSettings volteUeApi = CoreApi.getTelephonyApi().volteUeSettings();
    private ListPreference mMaxBandWidth;
    private ListPreference mMaxBitRate;
    private ListPreference mSmsPduformat;
    private EditTextPreference mSprdVolte;
    private TwoStatePreference mVideoCallEnable;
    private TwoStatePreference mVideoConferenceEnable;
    private VolteHandler mVolteHandler;
    private MultiSelectListPreference mVolteVideoCode;
    private MultiSelectListPreference mVolteVoiceCode;
    private Context mContext = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_volte_ue_settings);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVolteHandler = new VolteHandler(ht.getLooper());
        initVoiceCodecListPref();
        initVideoCodecListPref();
        initBandwidthListPref();
        initBitrateListPref();
        initSmsPduListPref();
        this.mVideoCallEnable = (TwoStatePreference) findPreference(KEY_VIDEO_CALL_ENABLE);
        this.mVideoCallEnable.setOnPreferenceChangeListener(this);
        this.mVideoConferenceEnable = (TwoStatePreference) findPreference(KEY_VIDEO_CONF_ENABLE);
        this.mVideoConferenceEnable.setOnPreferenceChangeListener(this);
        this.mSprdVolte = (EditTextPreference) findPreference(KEY_SPRD_VOLTE);
        this.mSprdVolte.setOnPreferenceChangeListener(this);
    }

    private void initVoiceCodecListPref() {
        this.mVolteVoiceCode = (MultiSelectListPreference) findPreference(KEY_VOICE_CODE);
        this.mVolteVoiceCode.setOnPreferenceChangeListener(this);
        this.mVolteVoiceCode.setEntries(R.array.volte_voice_code);
        this.mVolteVoiceCode.setEntryValues((CharSequence[]) Arrays.stream(ITelephonyApi.IVolteUeSettings.VoiceCodec.values()).map($$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ.INSTANCE).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$cgW9xTei46lGjXFVl4x-zeD7sAk
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return UESettingsActivity.lambda$initVoiceCodecListPref$0(i);
            }
        }));
    }

    static /* synthetic */ String[] lambda$initVoiceCodecListPref$0(int x$0) {
        return new String[x$0];
    }

    private void initVideoCodecListPref() {
        this.mVolteVideoCode = (MultiSelectListPreference) findPreference(KEY_VIDEO_CODE);
        this.mVolteVideoCode.setOnPreferenceChangeListener(this);
        this.mVolteVideoCode.setEntries(R.array.video_codec_str);
        this.mVolteVideoCode.setEntryValues((CharSequence[]) Arrays.stream(ITelephonyApi.IVolteUeSettings.VideoCodec.values()).map($$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k.INSTANCE).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$-gXzld9gXb2JmDi9JiPge3x0ckA
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return UESettingsActivity.lambda$initVideoCodecListPref$1(i);
            }
        }));
    }

    static /* synthetic */ String[] lambda$initVideoCodecListPref$1(int x$0) {
        return new String[x$0];
    }

    private void initBandwidthListPref() {
        this.mMaxBandWidth = (ListPreference) findPreference(KEY_MAX_BANDWIDTH);
        this.mMaxBandWidth.setOnPreferenceChangeListener(this);
        this.mMaxBandWidth.setEntries(R.array.evs_primary_bandwidth);
        this.mMaxBandWidth.setEntryValues((CharSequence[]) Arrays.stream(ITelephonyApi.IVolteUeSettings.Bandwidth.values()).map(new Function() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$eUkN0S2eOPiKXM04s3wdmsBaZ8c
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ITelephonyApi.IVolteUeSettings.Bandwidth) obj).name();
            }
        }).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$Yi6fpcK-pl5YEMHQcZh_1oL5RFU
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return UESettingsActivity.lambda$initBandwidthListPref$2(i);
            }
        }));
    }

    static /* synthetic */ String[] lambda$initBandwidthListPref$2(int x$0) {
        return new String[x$0];
    }

    private void initBitrateListPref() {
        this.mMaxBitRate = (ListPreference) findPreference(KEY_MAX_BITRATE);
        this.mMaxBitRate.setOnPreferenceChangeListener(this);
        this.mMaxBitRate.setEntries(R.array.evs_primary_bitrate);
        this.mMaxBitRate.setEntryValues((CharSequence[]) Arrays.stream(ITelephonyApi.IVolteUeSettings.Bitrate.values()).map(new Function() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$KbLxTBCj-H_RXZgBaxV6QYVdhas
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ITelephonyApi.IVolteUeSettings.Bitrate) obj).name();
            }
        }).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$X0olBv_m8zJ1nWL8xmj3qKcU1qU
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return UESettingsActivity.lambda$initBitrateListPref$3(i);
            }
        }));
    }

    static /* synthetic */ String[] lambda$initBitrateListPref$3(int x$0) {
        return new String[x$0];
    }

    private void initSmsPduListPref() {
        this.mSmsPduformat = (ListPreference) findPreference(KEY_SMS_PDU);
        this.mSmsPduformat.setOnPreferenceChangeListener(this);
        this.mSmsPduformat.setEntries(R.array.sms_pdu_str);
        this.mSmsPduformat.setEntryValues((CharSequence[]) Arrays.stream(ITelephonyApi.IVolteUeSettings.SmsPdu.values()).map(new Function() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$1ZPUdS51NcDPkQGGSGOoSZxl0hY
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ITelephonyApi.IVolteUeSettings.SmsPdu) obj).name();
            }
        }).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$DhqchaS6jEjOE8rW_zLcFFZ65Po
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return UESettingsActivity.lambda$initSmsPduListPref$4(i);
            }
        }));
    }

    static /* synthetic */ String[] lambda$initSmsPduListPref$4(int x$0) {
        return new String[x$0];
    }

    @Override // android.app.Activity
    public void onStart() {
        ListPreference listPreference = this.mMaxBandWidth;
        if (listPreference != null && listPreference.isEnabled()) {
            Message getMaxBandWidth = this.mVolteHandler.obtainMessage(31);
            this.mVolteHandler.sendMessage(getMaxBandWidth);
        }
        ListPreference listPreference2 = this.mMaxBitRate;
        if (listPreference2 != null && listPreference2.isEnabled()) {
            Message getMaxBitRate = this.mVolteHandler.obtainMessage(33);
            this.mVolteHandler.sendMessage(getMaxBitRate);
        }
        ListPreference listPreference3 = this.mSmsPduformat;
        if (listPreference3 != null && listPreference3.isEnabled()) {
            Message msg = this.mVolteHandler.obtainMessage(40);
            this.mVolteHandler.sendMessage(msg);
        }
        TwoStatePreference twoStatePreference = this.mVideoCallEnable;
        if (twoStatePreference != null && twoStatePreference.isEnabled()) {
            Message getVideoCallEnable = this.mVolteHandler.obtainMessage(42);
            this.mVolteHandler.sendMessage(getVideoCallEnable);
        }
        TwoStatePreference twoStatePreference2 = this.mVideoConferenceEnable;
        if (twoStatePreference2 != null && twoStatePreference2.isEnabled()) {
            Message getVideoConference = this.mVolteHandler.obtainMessage(44);
            this.mVolteHandler.sendMessage(getVideoConference);
        }
        Message getVoiceCodeType = this.mVolteHandler.obtainMessage(27);
        this.mVolteHandler.sendMessage(getVoiceCodeType);
        Message getVideoCodeType = this.mVolteHandler.obtainMessage(36);
        this.mVolteHandler.sendMessage(getVideoCodeType);
        EditTextPreference editTextPreference = this.mSprdVolte;
        if (editTextPreference != null && editTextPreference.isEnabled()) {
            Message msg2 = this.mVolteHandler.obtainMessage(38);
            this.mVolteHandler.sendMessage(msg2);
        }
        super.onStart();
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

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        String prefkey = pref.getKey();
        Log.d(TAG, "prefkey: " + prefkey);
        if (prefkey.equals(KEY_VOICE_CODE)) {
            VolteHandler volteHandler = this.mVolteHandler;
            volteHandler.sendMessage(volteHandler.obtainMessage(28, newValue));
            return false;
        }
        if (prefkey.equals(KEY_VIDEO_CODE)) {
            VolteHandler volteHandler2 = this.mVolteHandler;
            volteHandler2.sendMessage(volteHandler2.obtainMessage(35, newValue));
            return false;
        }
        if (prefkey.equals(KEY_MAX_BANDWIDTH)) {
            VolteHandler volteHandler3 = this.mVolteHandler;
            volteHandler3.sendMessage(volteHandler3.obtainMessage(32, newValue));
            return false;
        }
        if (prefkey.equals(KEY_MAX_BITRATE)) {
            VolteHandler volteHandler4 = this.mVolteHandler;
            volteHandler4.sendMessage(volteHandler4.obtainMessage(34, newValue));
            return false;
        }
        if (prefkey.equals(KEY_SMS_PDU)) {
            VolteHandler volteHandler5 = this.mVolteHandler;
            volteHandler5.sendMessage(volteHandler5.obtainMessage(39, newValue));
            return false;
        }
        if (prefkey.equals(KEY_SPRD_VOLTE)) {
            Bundle bundle = new Bundle();
            bundle.putString("old", this.mSprdVolte.getText());
            bundle.putString("new", (String) newValue);
            VolteHandler volteHandler6 = this.mVolteHandler;
            volteHandler6.sendMessage(volteHandler6.obtainMessage(37, bundle));
            return false;
        }
        TwoStatePreference twoStatePreference = this.mVideoCallEnable;
        if (pref == twoStatePreference) {
            Message msg = this.mVolteHandler.obtainMessage(41, Boolean.valueOf(!twoStatePreference.isChecked()));
            this.mVolteHandler.sendMessage(msg);
        } else {
            TwoStatePreference twoStatePreference2 = this.mVideoConferenceEnable;
            if (pref == twoStatePreference2) {
                Message msg2 = this.mVolteHandler.obtainMessage(43, Boolean.valueOf(!twoStatePreference2.isChecked()));
                this.mVolteHandler.sendMessage(msg2);
            }
        }
        return true;
    }

    class VolteHandler extends Handler {
        VolteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 27:
                    Log.d(UESettingsActivity.TAG, "MSG_GET_VOICE_CODE_TYPE");
                    try {
                        final ITelephonyApi.IVolteUeSettings.VoiceCodec[] voiceCodes = UESettingsActivity.volteUeApi.getVoiceCodecType();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$Wl8ziREnkq67RhMOWlO3799mTB4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$1$UESettingsActivity$VolteHandler(voiceCodes);
                            }
                        });
                    } catch (EmException e) {
                        e.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$zr4NtX2SKrwiRH3pqXyqNylx13A
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$2$UESettingsActivity$VolteHandler();
                            }
                        });
                    }
                    break;
                case 28:
                    Log.d(UESettingsActivity.TAG, "MSG_SET_VOICE_CODE_TYPE");
                    final Set<String> values = (Set) msg.obj;
                    try {
                        UESettingsActivity.volteUeApi.setVoiceCodecType((ITelephonyApi.IVolteUeSettings.VoiceCodec[]) values.stream().map(new Function() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$8ui6pdndbnWvnn2MbkF2B7HDKIM
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ITelephonyApi.IVolteUeSettings.VoiceCodec.valueOf((String) obj);
                            }
                        }).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$9S_jGhFqN038WohwfpZNBAer-vI
                            @Override // java.util.function.IntFunction
                            public final Object apply(int i) {
                                return UESettingsActivity.VolteHandler.lambda$handleMessage$3(i);
                            }
                        }));
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$6P1bu8uvf3PKBOv0l-M5XqxZx4I
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$5$UESettingsActivity$VolteHandler(values);
                            }
                        });
                    } catch (EmException e2) {
                        e2.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 31:
                    try {
                        final ITelephonyApi.IVolteUeSettings.Bandwidth bw = UESettingsActivity.volteUeApi.getMaxBandWidth();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$4W_HiCp29haWuEOLDnIO2w6EmeQ
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$12$UESettingsActivity$VolteHandler(bw);
                            }
                        });
                    } catch (EmException e3) {
                        e3.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$LARecMqL_JeOwasFbrmVZRl2kBU
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$13$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 32:
                    try {
                        final String bw2 = (String) msg.obj;
                        UESettingsActivity.volteUeApi.setMaxBandWidth(ITelephonyApi.IVolteUeSettings.Bandwidth.valueOf(bw2));
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$DoE6DfL6KVBW2Ltnd4lZpl3Im68
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$14$UESettingsActivity$VolteHandler(bw2);
                            }
                        });
                    } catch (EmException e4) {
                        e4.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 33:
                    try {
                        final ITelephonyApi.IVolteUeSettings.Bitrate br = UESettingsActivity.volteUeApi.getMaxBitRate();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$UThNZDrCwjea-fR2oKeiN_wesWg
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$15$UESettingsActivity$VolteHandler(br);
                            }
                        });
                    } catch (EmException e5) {
                        e5.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$3gJwyXN6pt1_248sgBtFrvmCDkU
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$16$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 34:
                    try {
                        final String br2 = (String) msg.obj;
                        UESettingsActivity.volteUeApi.setMaxBitRate(ITelephonyApi.IVolteUeSettings.Bitrate.valueOf(br2));
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$68ccuc2Ni5at95gKsl9yw2SSXJ8
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$17$UESettingsActivity$VolteHandler(br2);
                            }
                        });
                    } catch (EmException e6) {
                        e6.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 35:
                    Log.d(UESettingsActivity.TAG, "MSG_SET_VIDEO_CODE_TYPE");
                    final Set<String> videoValues = (Set) msg.obj;
                    try {
                        UESettingsActivity.volteUeApi.setVideoCodecType((ITelephonyApi.IVolteUeSettings.VideoCodec[]) videoValues.stream().map(new Function() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$N7C-72C3vXmdHL9wazLqjgRI-8c
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ITelephonyApi.IVolteUeSettings.VideoCodec.valueOf((String) obj);
                            }
                        }).toArray(new IntFunction() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$D4PFxXZhwxwDiOUQweui4y7A5Q8
                            @Override // java.util.function.IntFunction
                            public final Object apply(int i) {
                                return UESettingsActivity.VolteHandler.lambda$handleMessage$9(i);
                            }
                        }));
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$fArCl8AkT0GDmEnjTbrmnLHMeMw
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$11$UESettingsActivity$VolteHandler(videoValues);
                            }
                        });
                    } catch (EmException e7) {
                        e7.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 36:
                    Log.d(UESettingsActivity.TAG, "MSG_GET_VIDEO_CODE_TYPE");
                    try {
                        final ITelephonyApi.IVolteUeSettings.VideoCodec[] videoCodes = UESettingsActivity.volteUeApi.getVideoCodecType();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$H6zrEIxhBtn2U46ld7L62vnv_Uo
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$7$UESettingsActivity$VolteHandler(videoCodes);
                            }
                        });
                    } catch (EmException e8) {
                        e8.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$bilAY6qWuNZVUe6YBHqTNGa68_E
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$8$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 37:
                    Bundle bundle = (Bundle) msg.obj;
                    String currentValue = bundle.getString("old");
                    final String newValue = bundle.getString("new");
                    Log.d(UESettingsActivity.TAG, String.format("MSG_SET_SPRD_VOLTE current=%s, new=%s", currentValue, newValue));
                    try {
                        UESettingsActivity.volteUeApi.setSprdVolte(newValue);
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$9_sccbphmBsFPToer6JYom6AwJA
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$23$UESettingsActivity$VolteHandler(newValue);
                            }
                        });
                    } catch (EmException e9) {
                        e9.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 38:
                    try {
                        final String volte = UESettingsActivity.teleApi.volteUeSettings().getSprdVolte();
                        Log.d(UESettingsActivity.TAG, "get volte " + volte);
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$LtcDoTe02aM95ILoNPmWn0-AC2s
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$21$UESettingsActivity$VolteHandler(volte);
                            }
                        });
                    } catch (EmException e10) {
                        e10.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$CgbYwjCwaicXpoz5bPdHg8pDVy4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$22$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 39:
                    try {
                        final String sp = (String) msg.obj;
                        UESettingsActivity.volteUeApi.setSmsPdu(ITelephonyApi.IVolteUeSettings.SmsPdu.valueOf(sp));
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$g1U2T9I1r-xqvp_wRLJ-IxrgF-M
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$20$UESettingsActivity$VolteHandler(sp);
                            }
                        });
                    } catch (EmException e11) {
                        e11.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 40:
                    try {
                        final ITelephonyApi.IVolteUeSettings.SmsPdu sp2 = UESettingsActivity.volteUeApi.getSmsPdu();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$ZAlVF1e1Ud7OeP85jH4hOLW0e50
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$18$UESettingsActivity$VolteHandler(sp2);
                            }
                        });
                    } catch (EmException e12) {
                        e12.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$uFndQ2FvlzID4naXxvQMDVSdoHc
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$19$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 41:
                    try {
                        final boolean state = ((Boolean) msg.obj).booleanValue();
                        UESettingsActivity.volteUeApi.setVideoCallState(state);
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$ySXvCGAMWI57NS_on0ApiwJmvo8
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$26$UESettingsActivity$VolteHandler(state);
                            }
                        });
                    } catch (EmException e13) {
                        e13.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 42:
                    try {
                        final boolean enabled = UESettingsActivity.volteUeApi.getVideoCallState();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$-KjsFtZKterpbTNF6RnXPOcL1lo
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$24$UESettingsActivity$VolteHandler(enabled);
                            }
                        });
                    } catch (EmException e14) {
                        e14.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$5ib8KePqM6JmRyEHBhXmAXpVr1I
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$25$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
                case 43:
                    try {
                        final boolean state2 = ((Boolean) msg.obj).booleanValue();
                        UESettingsActivity.volteUeApi.setVideoConferenceState(state2);
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$vA-lW6hXCNTDDcbay5vrG3mT8To
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$29$UESettingsActivity$VolteHandler(state2);
                            }
                        });
                    } catch (EmException e15) {
                        e15.printStackTrace();
                        showFailToast();
                        return;
                    }
                    break;
                case 44:
                    try {
                        final boolean enabled2 = UESettingsActivity.volteUeApi.getVideoConferenceState();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$yKgHNLmxIEQjNoPCnOHM8GTDemk
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$27$UESettingsActivity$VolteHandler(enabled2);
                            }
                        });
                    } catch (EmException e16) {
                        e16.printStackTrace();
                        UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$rH0SuG-uqBhmLaNsWWPF-IfUOO8
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleMessage$28$UESettingsActivity$VolteHandler();
                            }
                        });
                        return;
                    }
                    break;
            }
        }

        public /* synthetic */ void lambda$handleMessage$1$UESettingsActivity$VolteHandler(ITelephonyApi.IVolteUeSettings.VoiceCodec[] voiceCodes) {
            UESettingsActivity.this.mVolteVoiceCode.setValues((Set) Arrays.stream(voiceCodes).map($$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ.INSTANCE).collect(Collectors.toSet()));
            UESettingsActivity.this.mVolteVoiceCode.setSummary((CharSequence) Arrays.stream(voiceCodes).map($$Lambda$IaTXTxr1U3etuDswdHyhMQHwQ.INSTANCE).reduce(new BinaryOperator() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$MI1ar6JZ8YVv3xzL72Vqa-qsruU
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UESettingsActivity.VolteHandler.lambda$handleMessage$0((String) obj, (String) obj2);
                }
            }).orElse(""));
        }

        static /* synthetic */ String lambda$handleMessage$0(String t, String u) {
            return t + "/" + u;
        }

        public /* synthetic */ void lambda$handleMessage$2$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mVolteVoiceCode.setEnabled(false);
            UESettingsActivity.this.mVolteVoiceCode.setSummary(R.string.feature_abnormal);
        }

        static /* synthetic */ ITelephonyApi.IVolteUeSettings.VoiceCodec[] lambda$handleMessage$3(int x$0) {
            return new ITelephonyApi.IVolteUeSettings.VoiceCodec[x$0];
        }

        public /* synthetic */ void lambda$handleMessage$5$UESettingsActivity$VolteHandler(Set values) {
            UESettingsActivity.this.mVolteVoiceCode.setValues(values);
            UESettingsActivity.this.mVolteVoiceCode.setSummary((CharSequence) values.stream().reduce(new BinaryOperator() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$2pJ9FryOnxcBdvItKN1KMZaWRNw
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UESettingsActivity.VolteHandler.lambda$handleMessage$4((String) obj, (String) obj2);
                }
            }).orElse(""));
            Toast.makeText(UESettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }

        static /* synthetic */ String lambda$handleMessage$4(String t, String u) {
            return t + "/" + u;
        }

        public /* synthetic */ void lambda$handleMessage$7$UESettingsActivity$VolteHandler(ITelephonyApi.IVolteUeSettings.VideoCodec[] videoCodes) {
            UESettingsActivity.this.mVolteVideoCode.setValues((Set) Arrays.stream(videoCodes).map($$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k.INSTANCE).collect(Collectors.toSet()));
            UESettingsActivity.this.mVolteVideoCode.setSummary((CharSequence) Arrays.stream(videoCodes).map($$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k.INSTANCE).reduce(new BinaryOperator() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$IQ09Godbi6CgLmk95s5aXzONYpI
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UESettingsActivity.VolteHandler.lambda$handleMessage$6((String) obj, (String) obj2);
                }
            }).orElse(""));
        }

        static /* synthetic */ String lambda$handleMessage$6(String t, String u) {
            return t + "/" + u;
        }

        public /* synthetic */ void lambda$handleMessage$8$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mVolteVideoCode.setEnabled(false);
            UESettingsActivity.this.mVolteVideoCode.setSummary(R.string.feature_abnormal);
        }

        static /* synthetic */ ITelephonyApi.IVolteUeSettings.VideoCodec[] lambda$handleMessage$9(int x$0) {
            return new ITelephonyApi.IVolteUeSettings.VideoCodec[x$0];
        }

        public /* synthetic */ void lambda$handleMessage$11$UESettingsActivity$VolteHandler(Set videoValues) {
            UESettingsActivity.this.mVolteVideoCode.setValues(videoValues);
            UESettingsActivity.this.mVolteVideoCode.setSummary((CharSequence) videoValues.stream().reduce(new BinaryOperator() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$vG_dVkYJYEAtiiV1tmEWJddH8GE
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return UESettingsActivity.VolteHandler.lambda$handleMessage$10((String) obj, (String) obj2);
                }
            }).orElse(""));
            Toast.makeText(UESettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }

        static /* synthetic */ String lambda$handleMessage$10(String t, String u) {
            return t + "/" + u;
        }

        public /* synthetic */ void lambda$handleMessage$12$UESettingsActivity$VolteHandler(ITelephonyApi.IVolteUeSettings.Bandwidth bw) {
            UESettingsActivity.this.mMaxBandWidth.setValue(bw.name());
            UESettingsActivity.this.mMaxBandWidth.setSummary(UESettingsActivity.this.mMaxBandWidth.getEntry());
        }

        public /* synthetic */ void lambda$handleMessage$13$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mMaxBandWidth.setEnabled(false);
            UESettingsActivity.this.mMaxBandWidth.setSummary(R.string.feature_abnormal);
        }

        public /* synthetic */ void lambda$handleMessage$14$UESettingsActivity$VolteHandler(String bw) {
            UESettingsActivity.this.mMaxBandWidth.setValue(bw);
            UESettingsActivity.this.mMaxBandWidth.setSummary(UESettingsActivity.this.mMaxBandWidth.getEntry());
            Toast.makeText(UESettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$15$UESettingsActivity$VolteHandler(ITelephonyApi.IVolteUeSettings.Bitrate br) {
            UESettingsActivity.this.mMaxBitRate.setValue(br.name());
            UESettingsActivity.this.mMaxBitRate.setSummary(UESettingsActivity.this.mMaxBitRate.getEntry());
        }

        public /* synthetic */ void lambda$handleMessage$16$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mMaxBitRate.setEnabled(false);
            UESettingsActivity.this.mMaxBitRate.setSummary(R.string.feature_abnormal);
        }

        public /* synthetic */ void lambda$handleMessage$17$UESettingsActivity$VolteHandler(String br) {
            UESettingsActivity.this.mMaxBitRate.setValue(br);
            UESettingsActivity.this.mMaxBitRate.setSummary(UESettingsActivity.this.mMaxBitRate.getEntry());
            Toast.makeText(UESettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$18$UESettingsActivity$VolteHandler(ITelephonyApi.IVolteUeSettings.SmsPdu sp) {
            UESettingsActivity.this.mSmsPduformat.setValue(sp.name());
            UESettingsActivity.this.mSmsPduformat.setSummary(UESettingsActivity.this.mSmsPduformat.getEntry());
        }

        public /* synthetic */ void lambda$handleMessage$19$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mSmsPduformat.setEnabled(false);
            UESettingsActivity.this.mSmsPduformat.setSummary(R.string.feature_abnormal);
        }

        public /* synthetic */ void lambda$handleMessage$20$UESettingsActivity$VolteHandler(String sp) {
            UESettingsActivity.this.mSmsPduformat.setValue(sp);
            UESettingsActivity.this.mSmsPduformat.setSummary(UESettingsActivity.this.mSmsPduformat.getEntry());
            Toast.makeText(UESettingsActivity.this.mContext, R.string.audio_codec_setting_hint, 0).show();
        }

        public /* synthetic */ void lambda$handleMessage$21$UESettingsActivity$VolteHandler(String volte) {
            if (!volte.equals("")) {
                UESettingsActivity.this.mSprdVolte.setText(volte);
                UESettingsActivity.this.mSprdVolte.setSummary(volte);
            }
        }

        public /* synthetic */ void lambda$handleMessage$22$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mSprdVolte.setSummary("");
        }

        public /* synthetic */ void lambda$handleMessage$23$UESettingsActivity$VolteHandler(String newValue) {
            UESettingsActivity.this.mSprdVolte.setText(newValue);
            UESettingsActivity.this.mSprdVolte.setSummary(newValue);
        }

        public /* synthetic */ void lambda$handleMessage$24$UESettingsActivity$VolteHandler(boolean enabled) {
            UESettingsActivity.this.mVideoCallEnable.setChecked(enabled);
        }

        public /* synthetic */ void lambda$handleMessage$25$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mVideoCallEnable.setEnabled(false);
            UESettingsActivity.this.mVideoCallEnable.setSummary(R.string.feature_abnormal);
        }

        public /* synthetic */ void lambda$handleMessage$26$UESettingsActivity$VolteHandler(boolean state) {
            UESettingsActivity.this.mVideoCallEnable.setChecked(state);
        }

        public /* synthetic */ void lambda$handleMessage$27$UESettingsActivity$VolteHandler(boolean enabled) {
            UESettingsActivity.this.mVideoConferenceEnable.setChecked(enabled);
        }

        public /* synthetic */ void lambda$handleMessage$28$UESettingsActivity$VolteHandler() {
            UESettingsActivity.this.mVideoConferenceEnable.setEnabled(false);
            UESettingsActivity.this.mVideoConferenceEnable.setSummary(R.string.feature_abnormal);
        }

        public /* synthetic */ void lambda$handleMessage$29$UESettingsActivity$VolteHandler(boolean state) {
            UESettingsActivity.this.mVideoConferenceEnable.setChecked(state);
        }

        private void showFailToast() {
            UESettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$UESettingsActivity$VolteHandler$e43JcwGOWXDIkf9BxW96Bh2KvY0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showFailToast$30$UESettingsActivity$VolteHandler();
                }
            });
        }

        public /* synthetic */ void lambda$showFailToast$30$UESettingsActivity$VolteHandler() {
            Toast.makeText(UESettingsActivity.this.mContext, "Fail", 0).show();
        }
    }
}
