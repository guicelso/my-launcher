package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class LTEActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CHANGE_NETMODE_BY_EM = "persist.sys.cmccpolicy.disable";
    private static final String CLOSE_NOTICE = "After test,please close this notificaiton";
    private static final int DISMISS_PROGRESS_DIALOG = 0;
    private static final String KEY_CSFB = "csfb_key";
    private static final String KEY_FDD = "fdd_csfb_key";
    private static final String KEY_FDD_CSFB = "FDD_CSFB";
    private static final String KEY_LTE_CSFB = "CSFB";
    private static final String KEY_SIM_INDEX = "simindex";
    private static final String KEY_SVLTE = "svlte_key";
    private static final String KEY_TDD = "tdd_csfb_key";
    private static final String KEY_TDD_CSFB = "TDD_CSFB";
    private static final String KEY_TDD_SVLTE = "TDD_SVLTE";
    private static final String KEY_TDD_TDD = "TDD_TDD";
    private static final String KEY_TD_TD = "TD_TD";
    private static final String KEY_TLTWCG = "tltwcg_key";
    private static final String KEY_TLTWCG_TLTWCG = "TLTWCG_TLTWCG";
    public static LTEActivity LTEActivityInstance = null;
    private static final String NEW_MESSAGE = "Notice From EngineerMode!";
    private static final String NOTIFICATION_CHANNEL_ID = "mobileServiceMessages";
    private static final int NOTIFICATION_ID = 4387;
    private static final int SHOW_PROGRESS_DIALOG = 1;
    private static final String TAG = "LTEActivity";
    private TelephonyManagerSprd.RadioCapbility mCurrentRadioCapbility;
    private int mCurrentRadioFeatures;
    private ListPreference mListPreferenceCSFB;
    private ListPreference mListPreferenceFDD;
    private ListPreference mListPreferenceSVLTE;
    private ListPreference mListPreferenceTDD;
    private ListPreference mListPreferenceTLTWCG;
    private NotificationManager mNotifiCation;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharePref;
    private TelephonyManagerSprd mTelephonyManager;
    private String simId;
    private TelephonyManagerSprd.RadioCapbility mLastRadio = null;
    private int mLastValueIndex = 0;
    private boolean isSupportTDD = TelephonyManagerSprd.RadioCapbility.TDD_CSFB.equals(TelephonyManagerSprd.getRadioCapbility());
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private int mRetValue = 0;
    private LteMainHandler mLteMainHandler = new LteMainHandler();

    public class LteMainHandler extends Handler {
        public LteMainHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, InvocationTargetException {
            int i = msg.what;
            if (i == 0) {
                LTEActivity.this.dismissProgressDialog();
                LTEActivity.this.updateListPreferenceUI();
            } else if (i == 1) {
                LTEActivity.this.showProgressDialog();
            } else {
                Log.d(LTEActivity.TAG, "LteMainHandler message is wrong!");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgressDialog() {
        String progressDialogHint = getResources().getString(R.string.network_mode_switch_hint);
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage(progressDialogHint);
        this.mProgressDialog.setProgressStyle(0);
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setCanceledOnTouchOutside(false);
        this.mProgressDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        if (this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateListPreferenceUI() throws IllegalAccessException, InvocationTargetException {
        if (this.mRetValue < 0) {
            retrievePrefIndex(this.mLastRadio, this.mLastValueIndex);
            String switchFailedHint = getResources().getString(R.string.network_mode_switch_faided_hint);
            Toast.makeText(this, switchFailedHint, 0).show();
            return;
        }
        if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.TDD_CSFB) {
            if (this.mCurrentRadioFeatures == 57) {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "false");
            } else {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "true");
            }
            this.mListPreferenceTDD.setValueIndex(changeValueToIndex(KEY_TDD));
            ListPreference listPreference = this.mListPreferenceTDD;
            listPreference.setSummary(listPreference.getEntry());
            return;
        }
        if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.FDD_CSFB || SystemPropertiesProxy.get("ro.product.name", "").contains("oversea") || SystemPropertiesProxy.get("ro.build.display.id", "").contains("oversea")) {
            if (this.mCurrentRadioFeatures == 56) {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "false");
            } else {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "true");
            }
            this.mListPreferenceFDD.setValueIndex(changeValueToIndex(KEY_FDD));
            ListPreference listPreference2 = this.mListPreferenceFDD;
            listPreference2.setSummary(listPreference2.getEntry());
            return;
        }
        if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.CSFB || this.mCurrentRadioCapbility.toString().equals(KEY_TD_TD) || this.mCurrentRadioCapbility.toString().equals(KEY_TDD_TDD)) {
            if (this.mCurrentRadioFeatures == 59) {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "false");
            } else {
                SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "true");
            }
            this.mListPreferenceCSFB.setValueIndex(changeValueToIndex(KEY_CSFB));
            ListPreference listPreference3 = this.mListPreferenceCSFB;
            listPreference3.setSummary(listPreference3.getEntry());
            return;
        }
        if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.TLTWCG_TLTWCG) {
            this.mListPreferenceTLTWCG.setValueIndex(changeValueToIndex(KEY_TLTWCG));
            ListPreference listPreference4 = this.mListPreferenceTLTWCG;
            listPreference4.setSummary(listPreference4.getEntry());
        } else {
            this.mListPreferenceCSFB.setValueIndex(changeValueToIndex(KEY_CSFB));
            ListPreference listPreference5 = this.mListPreferenceCSFB;
            listPreference5.setSummary(listPreference5.getEntry());
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LTEActivityInstance = this;
        Intent intent = getIntent();
        this.simId = intent.getStringExtra("simindex");
        Log.d(TAG, "onCreate getIntExtra simId = " + this.simId);
        String str = this.simId;
        if (str == null || str.trim().equals("")) {
            this.simId = "0";
        }
        addPreferencesFromResource(R.xml.pref_lte);
        this.mListPreferenceSVLTE = (ListPreference) findPreference(KEY_SVLTE);
        this.mListPreferenceFDD = (ListPreference) findPreference(KEY_FDD);
        this.mListPreferenceTDD = (ListPreference) findPreference(KEY_TDD);
        this.mListPreferenceCSFB = (ListPreference) findPreference(KEY_CSFB);
        this.mListPreferenceTLTWCG = (ListPreference) findPreference(KEY_TLTWCG);
        this.mListPreferenceSVLTE.setOnPreferenceChangeListener(this);
        this.mListPreferenceFDD.setOnPreferenceChangeListener(this);
        this.mListPreferenceTDD.setOnPreferenceChangeListener(this);
        this.mListPreferenceCSFB.setOnPreferenceChangeListener(this);
        this.mListPreferenceTLTWCG.setOnPreferenceChangeListener(this);
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mTelephonyManager = TelephonyManagerSprd.getInstance();
        new NetworkTypeGainAsyncTask().execute(new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initNotifiCation() {
        this.mNotifiCation = (NotificationManager) getSystemService("notification");
        this.mNotifiCation.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, getResources().getString(R.string.app_name), 2));
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        mRemoteViews.setTextViewText(R.id.mt_notification, "Notice From EngineerMode!\n    After test,please close this notificaiton");
        Intent clickIntent = new Intent(this, (Class<?>) NotificationBroadcastReceiver.class);
        clickIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, 134217728);
        mRemoteViews.setOnClickPendingIntent(R.id.bt_notification, pendingIntent);
        Notification notify = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setTicker(NEW_MESSAGE).setContent(mRemoteViews).setAutoCancel(true).setContentTitle(NEW_MESSAGE).setPriority(0).setOngoing(true).setSmallIcon(R.drawable.cg).build();
        this.mNotifiCation.notify(NOTIFICATION_ID, notify);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int changeValueToIndex(String PrefKey) {
        int i;
        if (PrefKey.equals(KEY_FDD)) {
            int i2 = this.mCurrentRadioFeatures;
            if (i2 == 54) {
                return 0;
            }
            if (i2 == 55) {
                return 1;
            }
            if (i2 == 51) {
                return 2;
            }
            if (i2 == 52) {
                return 3;
            }
            if (i2 == 53) {
                return 4;
            }
            if (i2 == 56) {
                return 5;
            }
            if (i2 == 60) {
                return 6;
            }
            if (i2 == 61) {
                return 7;
            }
            if (i2 != 64) {
                return 5;
            }
            return 8;
        }
        if (PrefKey.equals(KEY_TDD)) {
            int i3 = this.mCurrentRadioFeatures;
            if (i3 == 57) {
                return 0;
            }
            if (i3 == 51) {
                return 1;
            }
            if (i3 == 52) {
                return 2;
            }
            if (i3 == 53) {
                return 3;
            }
            if (i3 == 58) {
                return 4;
            }
            if (i3 == 60) {
                if (this.isSupportTDD) {
                    return 2;
                }
                return 5;
            }
            if (i3 == 62) {
                if (this.isSupportTDD) {
                    return 3;
                }
                return 6;
            }
            if (i3 != 63) {
                return 0;
            }
            if (this.isSupportTDD) {
                return 4;
            }
            return 7;
        }
        if (PrefKey.equals(KEY_CSFB)) {
            int valueIndex = SystemPropertiesProxy.get("ro.build.product", "null").contains("sp9853i") ? 1 : 0;
            int i4 = this.mCurrentRadioFeatures;
            if (i4 == 59) {
                return 0;
            }
            if (i4 == 56) {
                return 1;
            }
            if (i4 == 54) {
                return 2;
            }
            if (i4 == 55) {
                return 3;
            }
            if (i4 == 51) {
                return 4;
            }
            if (i4 == 52) {
                return 5;
            }
            if (i4 == 53) {
                return 6;
            }
            if (i4 == 57) {
                return 7;
            }
            if (i4 == 58) {
                return 8;
            }
            if (i4 == 60) {
                return 9;
            }
            if (i4 == 61) {
                return 10;
            }
            if (i4 == 64) {
                return 11;
            }
            if (i4 == 62) {
                return 12;
            }
            if (i4 == 63) {
                return 13;
            }
            return valueIndex;
        }
        if (!PrefKey.equals(KEY_TLTWCG) || (i = this.mCurrentRadioFeatures) == 60) {
            return 0;
        }
        if (i == 61) {
            return 1;
        }
        if (i == 64) {
            return 2;
        }
        if (i == 53) {
            return 3;
        }
        if (i == 59) {
            return 4;
        }
        if (i == 72) {
            return 5;
        }
        if (i == 73) {
            return 6;
        }
        if (i == 74) {
            return 7;
        }
        if (i == 67) {
            return 8;
        }
        if (i != 65) {
            return 0;
        }
        return 9;
    }

    private int changeIndexToValue(TelephonyManagerSprd.RadioCapbility radio, int setValueIndex) {
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.FDD_CSFB)) {
            switch (setValueIndex) {
                case 0:
                    return 54;
                case 1:
                    return 55;
                case 2:
                    return 51;
                case 3:
                    return 52;
                case 4:
                    return 53;
                case 5:
                    return 56;
                case 6:
                    return 60;
                case 7:
                    return 61;
                case 8:
                    return 64;
                default:
                    return -1;
            }
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_CSFB)) {
            switch (setValueIndex) {
                case 0:
                    return 57;
                case 1:
                    return 51;
                case 2:
                    if (this.isSupportTDD) {
                        return 60;
                    }
                    return 52;
                case 3:
                    if (this.isSupportTDD) {
                        return 62;
                    }
                    return 53;
                case 4:
                    if (this.isSupportTDD) {
                        return 63;
                    }
                    return 58;
                case 5:
                    return 60;
                case 6:
                    return 62;
                case 7:
                    return 63;
                default:
                    return -1;
            }
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            switch (setValueIndex) {
                case 0:
                    return 59;
                case 1:
                    return 56;
                case 2:
                    return 54;
                case 3:
                    return 55;
                case 4:
                    return 51;
                case 5:
                    return 52;
                case 6:
                    return 53;
                case 7:
                    return 57;
                case 8:
                    return 58;
                case 9:
                    return 60;
                case 10:
                    return 61;
                case 11:
                    return 64;
                case 12:
                    return 62;
                case 13:
                    return 63;
                default:
                    return -1;
            }
        }
        if (!radio.equals(TelephonyManagerSprd.RadioCapbility.TLTWCG_TLTWCG)) {
            return -1;
        }
        switch (setValueIndex) {
            case 0:
                return 60;
            case 1:
                return 61;
            case 2:
                return 64;
            case 3:
                return 53;
            case 4:
                return 59;
            case 5:
                return 72;
            case 6:
                return 73;
            case 7:
                return 74;
            case 8:
                return 67;
            case 9:
                return 65;
            default:
                return -1;
        }
    }

    private void setSummary(TelephonyManagerSprd.RadioCapbility radio) {
        if (radio == null) {
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_SVLTE)) {
            ListPreference listPreference = this.mListPreferenceSVLTE;
            listPreference.setSummary(listPreference.getEntry());
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.FDD_CSFB)) {
            ListPreference listPreference2 = this.mListPreferenceFDD;
            listPreference2.setSummary(listPreference2.getEntry());
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_CSFB)) {
            ListPreference listPreference3 = this.mListPreferenceTDD;
            listPreference3.setSummary(listPreference3.getEntry());
        } else if (radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            ListPreference listPreference4 = this.mListPreferenceCSFB;
            listPreference4.setSummary(listPreference4.getEntry());
        } else if (radio.equals(TelephonyManagerSprd.RadioCapbility.TLTWCG_TLTWCG)) {
            this.mListPreferenceCSFB.setSummary(this.mListPreferenceTLTWCG.getEntry());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retrievePrefIndex(TelephonyManagerSprd.RadioCapbility radio, int lastValueIndex) {
        if (radio == null) {
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.FDD_CSFB)) {
            this.mListPreferenceFDD.setValueIndex(lastValueIndex);
            SharedPreferences.Editor edit = this.mSharePref.edit();
            edit.putString(KEY_FDD, this.mListPreferenceFDD.getValue());
            edit.commit();
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_CSFB)) {
            this.mListPreferenceTDD.setValueIndex(lastValueIndex);
            SharedPreferences.Editor edit2 = this.mSharePref.edit();
            edit2.putString(KEY_TDD, this.mListPreferenceTDD.getValue());
            edit2.commit();
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            this.mListPreferenceCSFB.setValueIndex(lastValueIndex);
            SharedPreferences.Editor edit3 = this.mSharePref.edit();
            edit3.putString(KEY_CSFB, this.mListPreferenceCSFB.getValue());
            edit3.commit();
        }
    }

    private void retrieveCapbility(TelephonyManagerSprd.RadioCapbility radio) {
        if (radio == null) {
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_SVLTE)) {
            this.mListPreferenceSVLTE.setValue(null);
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.FDD_CSFB)) {
            this.mListPreferenceFDD.setValue(null);
        } else if (radio.equals(TelephonyManagerSprd.RadioCapbility.TDD_CSFB)) {
            this.mListPreferenceTDD.setValue(null);
        } else if (radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            this.mListPreferenceCSFB.setValue(null);
        }
    }

    public AlertDialog showAlertDialog(final TelephonyManagerSprd.RadioCapbility mRadio, int setValueIndex, final int valueIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int setRadioFeature = changeIndexToValue(mRadio, setValueIndex);
        Log.d(TAG, "setRadioFeature is " + setRadioFeature);
        builder.setMessage(getString(R.string.setting_dialog_con1)).setCancelable(false).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.LTEActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (!LTEActivity.this.teleApi.EngTestStatus().isEngTest()) {
                    LTEActivity.this.teleApi.EngTestStatus().set(1);
                    Log.d(LTEActivity.TAG, "get vSystemProperties is " + LTEActivity.this.teleApi.EngTestStatus().isEngTest());
                    LTEActivity.this.initNotifiCation();
                }
                LTEActivity.this.mLastRadio = mRadio;
                LTEActivity.this.mLastValueIndex = valueIndex;
                LTEActivity.this.setRadioFeatures(setRadioFeature);
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.LTEActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                LTEActivity.this.retrievePrefIndex(mRadio, valueIndex);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public void setRadioFeatures(final int setRadioFeature) {
        Log.d(TAG, "now setRadioFeatures: " + setRadioFeature);
        final int simIndex = Integer.parseInt(this.simId);
        this.mLteMainHandler.sendEmptyMessage(1);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.telephony.LTEActivity.3
            @Override // java.lang.Runnable
            public void run() {
                if (LTEActivity.this.teleApi.telephonyInfo().isSupportLplusG()) {
                    LTEActivity lTEActivity = LTEActivity.this;
                    lTEActivity.mRetValue = lTEActivity.mTelephonyManager.setPreferredNetworkType(setRadioFeature);
                } else {
                    LTEActivity lTEActivity2 = LTEActivity.this;
                    lTEActivity2.mRetValue = lTEActivity2.mTelephonyManager.setPreferredNetworkType(simIndex, setRadioFeature);
                }
                Log.d(LTEActivity.TAG, "mRetValue is: " + LTEActivity.this.mRetValue);
                LTEActivity.this.mCurrentRadioFeatures = setRadioFeature;
                Log.d(LTEActivity.TAG, "mCurrentRadioFeatures is: " + LTEActivity.this.mCurrentRadioFeatures);
                LTEActivity.this.mLteMainHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        TelephonyManagerSprd.RadioCapbility radioCapbility;
        int valueIndex = 0;
        Log.d(TAG, "get vSystemProperties is " + this.teleApi.EngTestStatus().isEngTest());
        this.mCurrentRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
        if (!this.teleApi.EngTestStatus().isEngTest()) {
            this.teleApi.EngTestStatus().set(1);
            if (this.teleApi.telephonyInfo().isSupportLplusG()) {
                this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType();
            } else {
                this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType(Integer.parseInt(this.simId));
            }
            this.teleApi.EngTestStatus().set(0);
        } else if (this.teleApi.telephonyInfo().isSupportLplusG()) {
            this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType();
        } else {
            this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType(Integer.parseInt(this.simId));
        }
        Log.d(TAG, " onPreferenceChange\nmCurrentRadioCapbility is " + this.mCurrentRadioCapbility + ", mCurrentRadioFeatures is " + this.mCurrentRadioFeatures);
        int setValueIndex = Integer.valueOf(newValue.toString()).intValue();
        if (this.mCurrentRadioCapbility.toString().equals(KEY_FDD_CSFB) || SystemPropertiesProxy.get("ro.product.name", "").contains("oversea") || SystemPropertiesProxy.get("ro.build.display.id", "").contains("oversea")) {
            valueIndex = changeValueToIndex(KEY_FDD);
        } else if (this.mCurrentRadioCapbility.toString().equals(KEY_TDD_CSFB)) {
            valueIndex = changeValueToIndex(KEY_TDD);
        } else if (this.mCurrentRadioCapbility.toString().equals(KEY_LTE_CSFB) || this.mCurrentRadioCapbility.toString().equals(KEY_TD_TD) || this.mCurrentRadioCapbility.toString().equals(KEY_TDD_TDD)) {
            if (this.mCurrentRadioCapbility.toString().equals(KEY_TD_TD) && isChangeValueToTDSCDMA(this.mCurrentRadioCapbility.toString(), setValueIndex)) {
                Toast.makeText(this, getResources().getString(R.string.network_mode_switch_faided_hint), 0).show();
                return false;
            }
            valueIndex = changeValueToIndex(KEY_CSFB);
        } else if (this.mCurrentRadioCapbility.toString().equals(KEY_TLTWCG_TLTWCG)) {
            valueIndex = changeValueToIndex(KEY_TLTWCG);
        }
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            String key = listPreference.getKey();
            if (key.equals(KEY_FDD)) {
                radioCapbility = TelephonyManagerSprd.RadioCapbility.FDD_CSFB;
            } else if (key.equals(KEY_CSFB)) {
                radioCapbility = TelephonyManagerSprd.RadioCapbility.CSFB;
            } else if (key.equals(KEY_TLTWCG)) {
                radioCapbility = TelephonyManagerSprd.RadioCapbility.TLTWCG_TLTWCG;
            } else {
                radioCapbility = TelephonyManagerSprd.RadioCapbility.TDD_CSFB;
            }
            if (setValueIndex != valueIndex) {
                showAlertDialog(radioCapbility, setValueIndex, valueIndex);
            }
            Log.d(TAG, "mCurrentRadioCapbility is " + this.mCurrentRadioCapbility + ", changeradioCapbility is " + radioCapbility + ", setValueIndex is " + setValueIndex + ", valueIndex is " + valueIndex + ", key is " + key);
        }
        return true;
    }

    private boolean isChangeValueToTDSCDMA(String mCurRadioCapbility, int mCurValueIndex) {
        if (mCurRadioCapbility.equals(KEY_TD_TD)) {
            return mCurValueIndex == 7 || mCurValueIndex == 8 || mCurValueIndex == 12 || mCurValueIndex == 13;
        }
        return false;
    }

    private class NetworkTypeGainAsyncTask extends AsyncTask<String, Void, Boolean> {
        public NetworkTypeGainAsyncTask() {
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(String... params) {
            Log.d(LTEActivity.TAG, "NetworkTypeGainAsyncTask doInBackground");
            LTEActivity.this.mCurrentRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
            if (!LTEActivity.this.teleApi.EngTestStatus().isEngTest()) {
                LTEActivity.this.teleApi.EngTestStatus().set(1);
                if (LTEActivity.this.teleApi.telephonyInfo().isSupportLplusG()) {
                    LTEActivity lTEActivity = LTEActivity.this;
                    lTEActivity.mCurrentRadioFeatures = lTEActivity.mTelephonyManager.getPreferredNetworkType();
                } else {
                    LTEActivity lTEActivity2 = LTEActivity.this;
                    lTEActivity2.mCurrentRadioFeatures = lTEActivity2.mTelephonyManager.getPreferredNetworkType(Integer.parseInt(LTEActivity.this.simId));
                }
                LTEActivity.this.teleApi.EngTestStatus().set(0);
            } else if (LTEActivity.this.teleApi.telephonyInfo().isSupportLplusG()) {
                LTEActivity lTEActivity3 = LTEActivity.this;
                lTEActivity3.mCurrentRadioFeatures = lTEActivity3.mTelephonyManager.getPreferredNetworkType();
            } else {
                LTEActivity lTEActivity4 = LTEActivity.this;
                lTEActivity4.mCurrentRadioFeatures = lTEActivity4.mTelephonyManager.getPreferredNetworkType(Integer.parseInt(LTEActivity.this.simId));
            }
            Log.d(LTEActivity.TAG, "onCreate mCurrentRadioCapbility is " + LTEActivity.this.mCurrentRadioCapbility.toString() + ", mCurrentRadioFeatures is " + LTEActivity.this.mCurrentRadioFeatures);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean result) {
            Log.d(LTEActivity.TAG, "NetworkTypeGainAsyncTask onPostExecute");
            PreferenceScreen prefSet = LTEActivity.this.getPreferenceScreen();
            if (LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_TDD_SVLTE)) {
                prefSet.removePreference(LTEActivity.this.mListPreferenceFDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceCSFB);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTLTWCG);
                LTEActivity.this.mListPreferenceSVLTE.setValueIndex(LTEActivity.this.changeValueToIndex(LTEActivity.KEY_SVLTE));
                LTEActivity.this.mListPreferenceSVLTE.setSummary(LTEActivity.this.mListPreferenceSVLTE.getEntry());
                LTEActivity.this.mListPreferenceFDD.setValue(null);
                LTEActivity.this.mListPreferenceTDD.setValue(null);
                LTEActivity.this.mListPreferenceCSFB.setValue(null);
                LTEActivity.this.mListPreferenceTLTWCG.setValue(null);
                return;
            }
            if (LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_FDD_CSFB) || SystemPropertiesProxy.get("ro.product.name", "").contains("oversea") || SystemPropertiesProxy.get("ro.build.display.id", "").contains("oversea")) {
                prefSet.removePreference(LTEActivity.this.mListPreferenceSVLTE);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceCSFB);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTLTWCG);
                LTEActivity.this.mListPreferenceFDD.setValueIndex(LTEActivity.this.changeValueToIndex(LTEActivity.KEY_FDD));
                LTEActivity.this.mListPreferenceFDD.setSummary(LTEActivity.this.mListPreferenceFDD.getEntry());
                LTEActivity.this.mListPreferenceSVLTE.setValue(null);
                LTEActivity.this.mListPreferenceTDD.setValue(null);
                LTEActivity.this.mListPreferenceCSFB.setValue(null);
                LTEActivity.this.mListPreferenceTLTWCG.setValue(null);
                return;
            }
            if (LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_TDD_CSFB)) {
                prefSet.removePreference(LTEActivity.this.mListPreferenceSVLTE);
                prefSet.removePreference(LTEActivity.this.mListPreferenceFDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceCSFB);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTLTWCG);
                LTEActivity.this.mListPreferenceTDD.setEntries(R.array.list_entries_tdd_csfb_change);
                LTEActivity.this.mListPreferenceTDD.setEntryValues(R.array.list_entriesvalues_tdd_csfb_change);
                LTEActivity.this.mListPreferenceTDD.setValueIndex(LTEActivity.this.changeValueToIndex(LTEActivity.KEY_TDD));
                LTEActivity.this.mListPreferenceTDD.setSummary(LTEActivity.this.mListPreferenceTDD.getEntry());
                LTEActivity.this.mListPreferenceSVLTE.setValue(null);
                LTEActivity.this.mListPreferenceFDD.setValue(null);
                LTEActivity.this.mListPreferenceCSFB.setValue(null);
                LTEActivity.this.mListPreferenceTLTWCG.setValue(null);
                return;
            }
            if (LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_LTE_CSFB) || LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_TD_TD) || LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_TDD_TDD)) {
                prefSet.removePreference(LTEActivity.this.mListPreferenceSVLTE);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceFDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTLTWCG);
                LTEActivity.this.mListPreferenceCSFB.setValueIndex(LTEActivity.this.changeValueToIndex(LTEActivity.KEY_CSFB));
                LTEActivity.this.mListPreferenceCSFB.setSummary(LTEActivity.this.mListPreferenceCSFB.getEntry());
                LTEActivity.this.mListPreferenceSVLTE.setValue(null);
                LTEActivity.this.mListPreferenceTDD.setValue(null);
                LTEActivity.this.mListPreferenceFDD.setValue(null);
                LTEActivity.this.mListPreferenceTLTWCG.setValue(null);
                return;
            }
            if (LTEActivity.this.mCurrentRadioCapbility.toString().equals(LTEActivity.KEY_TLTWCG_TLTWCG)) {
                prefSet.removePreference(LTEActivity.this.mListPreferenceSVLTE);
                prefSet.removePreference(LTEActivity.this.mListPreferenceTDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceFDD);
                prefSet.removePreference(LTEActivity.this.mListPreferenceCSFB);
                LTEActivity.this.mListPreferenceTLTWCG.setValueIndex(LTEActivity.this.changeValueToIndex(LTEActivity.KEY_TLTWCG));
                LTEActivity.this.mListPreferenceTLTWCG.setSummary(LTEActivity.this.mListPreferenceTLTWCG.getEntry());
                LTEActivity.this.mListPreferenceSVLTE.setValue(null);
                LTEActivity.this.mListPreferenceTDD.setValue(null);
                LTEActivity.this.mListPreferenceFDD.setValue(null);
                LTEActivity.this.mListPreferenceCSFB.setValue(null);
                return;
            }
            LTEActivity.this.mListPreferenceSVLTE.setValue(null);
            LTEActivity.this.mListPreferenceFDD.setValue(null);
            LTEActivity.this.mListPreferenceTDD.setValue(null);
            LTEActivity.this.mListPreferenceTLTWCG.setValue(null);
        }
    }
}
