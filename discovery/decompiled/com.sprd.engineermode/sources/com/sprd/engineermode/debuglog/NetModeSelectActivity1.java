package com.sprd.engineermode.debuglog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.telephony.NotificationBroadcastReceiver;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.telephony.TelephonyManagerSprd;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class NetModeSelectActivity1 extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String CHANGE_NETMODE_BY_EM = "persist.sys.cmccpolicy.disable";
    private static final String CLOSE_NOTICE = "After test,please close this notificaiton";
    private static final int DISMISS_PROGRESS_DIALOG = 0;
    private static final int GET_NETMODE = 2;
    private static final String KEY = "sim";
    private static final String KEY_CSFB = "csfb_key";
    private static final String KEY_FDD = "fdd_csfb_key";
    private static final String KEY_FDD_CSFB = "FDD_CSFB";
    private static final String KEY_LTE_CSFB = "CSFB";
    private static final String KEY_SVLTE = "svlte_key";
    private static final String KEY_TDD = "tdd_csfb_key";
    private static final String KEY_TDD_CSFB = "TDD_CSFB";
    private static final String KEY_TDD_SVLTE = "TDD_SVLTE";
    private static final String NEW_MESSAGE = "Notice From EngineerMode!";
    private static final String NOTIFICATION_CHANNEL_ID = "mobileServiceMessages";
    private static final int NOTIFICATION_ID = 4387;
    static final String PROPERTY_MULTI_SIM_CONFIG = "persist.radio.multisim.config";
    private static final int SET_NETMODE = 1;
    private static final int SHOW_PROGRESS_DIALOG = 1;
    private static final String TAG = "NetModeSelectActivity1";
    private TelephonyManagerSprd.RadioCapbility mCurrentRadioCapbility;
    private int mCurrentRadioFeatures;
    private ListPreference[] mListPreference;
    private NotificationManager mNotifiCation;
    private int mPhoneCount;
    private ProgressDialog mProgressDialog;
    private int mSecondRadioFeatures;
    private SharedPreferences mSharePref;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyManagerSprd mTelephonyManager;
    private int primaryPhoneId;
    private TelephonyManagerSprd.RadioCapbility mLastPrimaryRadio = null;
    private int mLastPrimaryValueIndex = 0;
    private int mNewPrimaryRadioFeature = -1;
    private int secondPhoneId = 0;
    private int mLastValueIndex = 0;
    private int mNewValueIndex = 0;
    private boolean isCurrentPrimary = true;
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
                NetModeSelectActivity1.this.dismissProgressDialog();
                NetModeSelectActivity1.this.updateListPreferenceUI();
            } else if (i == 1) {
                NetModeSelectActivity1.this.showProgressDialog();
            } else {
                Log.d(NetModeSelectActivity1.TAG, "LteMainHandler message is wrong!");
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
        if (this.isCurrentPrimary) {
            Log.d(TAG, "current is primary card");
            if (this.mRetValue < 0) {
                retrievePrefIndex(this.mLastPrimaryRadio, this.mLastPrimaryValueIndex);
                String switchFailedHint = getResources().getString(R.string.network_mode_switch_faided_hint);
                Toast.makeText(this, switchFailedHint, 0).show();
                return;
            } else {
                if (this.mCurrentRadioCapbility == TelephonyManagerSprd.RadioCapbility.CSFB) {
                    if (this.mNewPrimaryRadioFeature == 59) {
                        SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "false");
                    } else {
                        SystemPropertiesProxy.set(CHANGE_NETMODE_BY_EM, "true");
                    }
                }
                setSummary(this.mCurrentRadioCapbility);
                return;
            }
        }
        Log.d(TAG, "current is not primary card");
        if (this.mRetValue < 0) {
            this.mListPreference[this.secondPhoneId].setValueIndex(this.mLastValueIndex);
            ListPreference[] listPreferenceArr = this.mListPreference;
            int i = this.secondPhoneId;
            listPreferenceArr[i].setSummary(listPreferenceArr[i].getEntry());
            return;
        }
        this.mListPreference[this.secondPhoneId].setValueIndex(this.mNewValueIndex);
        ListPreference[] listPreferenceArr2 = this.mListPreference;
        int i2 = this.secondPhoneId;
        listPreferenceArr2[i2].setSummary(listPreferenceArr2[i2].getEntry());
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        Log.d(TAG, "mPhoneCount is " + this.mPhoneCount);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        PreferenceGroup preGroup = getPreferenceScreen();
        this.mSubscriptionManager = SubscriptionManager.from(this);
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mListPreference = new ListPreference[this.mPhoneCount];
        if (!this.teleApi.EngTestStatus().isEngTest()) {
            this.teleApi.EngTestStatus().set(1);
            initNotifiCation();
        }
        this.mCurrentRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
        this.mTelephonyManager = TelephonyManagerSprd.getInstance();
        for (int i = 0; i < this.mPhoneCount; i++) {
            String key = KEY + i;
            this.mListPreference[i] = new ListPreference(this);
            this.mListPreference[i].setEnabled(false);
            this.mListPreference[i].setTitle(key);
            this.mListPreference[i].setKey(key);
            this.mListPreference[i].setOnPreferenceChangeListener(this);
            preGroup.addPreference(this.mListPreference[i]);
            this.mListPreference[i].setEntries(R.array.network_mode_choices_gsm);
            this.mListPreference[i].setEntryValues(R.array.network_mode_gsm_values);
        }
    }

    @Override // android.app.Activity
    @SuppressLint({"MissingPermission"})
    protected void onResume() {
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.primaryPhoneId = SubscriptionManagerProxy.getDefaultDataPhoneId();
        this.secondPhoneId = Math.abs(this.primaryPhoneId - 1);
        this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType();
        if (this.mSubscriptionManager.getActiveSubscriptionInfoList() != null && this.mSubscriptionManager.getActiveSubscriptionInfoList().size() > 1) {
            this.mSecondRadioFeatures = this.mTelephonyManager.getPreferredNetworkType(Math.abs(this.primaryPhoneId - 1));
        }
        Log.d(TAG, "mCurrentRadioCapbility is " + this.mCurrentRadioCapbility.toString() + ", mCurrentRadioFeatures is " + this.mCurrentRadioFeatures + " mSecondRadioFeatures is " + this.mSecondRadioFeatures);
        for (int i = 0; i < this.mPhoneCount; i++) {
            TelephonyManager tm = TelephonyManagerProxy.getService();
            if (tm != null && tm.getSimState(i) == 5) {
                this.mListPreference[i].setEnabled(true);
                try {
                    if (this.primaryPhoneId == i) {
                        this.mListPreference[i].setEntries(R.array.list_entries_csfb_lw);
                        this.mListPreference[i].setEntryValues(R.array.list_entriesvalues_csfb_lw);
                        this.mListPreference[i].setValueIndex(changeValueToIndex("primary card"));
                        this.mListPreference[i].setSummary(this.mListPreference[i].getEntry());
                    } else {
                        this.mNewValueIndex = changeValueToIndex("second card");
                        this.mLastValueIndex = this.mNewValueIndex;
                        this.mListPreference[i].setEntries(R.array.network_mode_choices_lplusw);
                        this.mListPreference[i].setEntryValues(R.array.network_mode_choices_lplusw_values);
                        this.mListPreference[i].setValueIndex(this.mNewValueIndex);
                        this.mListPreference[i].setSummary(this.mListPreference[i].getEntry());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e(TAG, "ArrayIndexOutOfBoundsException: " + e);
                }
            } else {
                this.mListPreference[i].setEnabled(false);
            }
        }
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    private void initNotifiCation() {
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
    protected void onDestroy() {
        super.onDestroy();
    }

    private int changeValueToIndex(String PrefKey) {
        if (PrefKey.equals("primary card")) {
            int i = this.mCurrentRadioFeatures;
            if (i == 59) {
                return 0;
            }
            if (i == 56) {
                return 1;
            }
            if (i == 54) {
                return 2;
            }
            if (i == 55) {
                return 3;
            }
            if (i == 51) {
                return 4;
            }
            if (i == 52) {
                return 5;
            }
            if (i == 53) {
                return 6;
            }
            if (i == 57) {
                return 7;
            }
            if (i == 58) {
                return 8;
            }
            if (i == 60) {
                return 9;
            }
            if (i == 61) {
                return 10;
            }
            if (i == 64) {
                return 11;
            }
            if (i == 62) {
                return 12;
            }
            if (i != 63) {
                return 0;
            }
            return 13;
        }
        if (!PrefKey.equals("second card")) {
            return 0;
        }
        int i2 = this.mSecondRadioFeatures;
        if (i2 == 60) {
            return 2;
        }
        if (i2 == 61) {
            return 1;
        }
        if (i2 != 64 && i2 != 65) {
            return 2;
        }
        return 0;
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        Log.d(TAG, "onPreferenceChange and the key is " + key);
        try {
            if (key.contains("" + this.primaryPhoneId)) {
                Log.d(TAG, "get vSystemProperties is " + this.teleApi.EngTestStatus().isEngTest());
                this.mCurrentRadioCapbility = TelephonyManagerSprd.getRadioCapbility();
                this.mCurrentRadioFeatures = this.mTelephonyManager.getPreferredNetworkType();
                Log.d(TAG, "onPreferenceChange\nmCurrentRadioCapbility is " + this.mCurrentRadioCapbility + ", mCurrentRadioFeatures is " + this.mCurrentRadioFeatures);
                int setValueIndex = Integer.valueOf(newValue.toString()).intValue();
                int valueIndex = changeValueToIndex("primary card");
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    String key2 = listPreference.getKey();
                    TelephonyManagerSprd.RadioCapbility radioCapbility = TelephonyManagerSprd.RadioCapbility.CSFB;
                    if (setValueIndex != valueIndex) {
                        showAlertDialog(radioCapbility, setValueIndex, valueIndex);
                    }
                    Log.d(TAG, "mCurrentRadioCapbility is " + this.mCurrentRadioCapbility + ", changeradioCapbility is " + radioCapbility + ", setValueIndex is " + setValueIndex + ", valueIndex is " + valueIndex + ", key is " + key2);
                }
            } else {
                String re = newValue.toString();
                final int i = Integer.parseInt(re.trim());
                this.mLteMainHandler.sendEmptyMessage(1);
                new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NetModeSelectActivity1.this.isCurrentPrimary = false;
                        NetModeSelectActivity1.this.mNewValueIndex = i;
                        NetModeSelectActivity1 netModeSelectActivity1 = NetModeSelectActivity1.this;
                        netModeSelectActivity1.setSecondCardNetMode(netModeSelectActivity1.mNewValueIndex, NetModeSelectActivity1.this.secondPhoneId);
                        Log.d(NetModeSelectActivity1.TAG, "mRetValue is: " + NetModeSelectActivity1.this.mRetValue);
                        NetModeSelectActivity1.this.mLteMainHandler.sendEmptyMessage(0);
                    }
                }).start();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "ArrayIndexOutOfBoundsException: " + e);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSecondCardNetMode(int i, int phoneId) {
        Log.d(TAG, "i = " + i);
        if (i == 0) {
            this.mRetValue = this.mTelephonyManager.setPreferredNetworkType(phoneId, 64);
        } else if (i == 1) {
            this.mRetValue = this.mTelephonyManager.setPreferredNetworkType(phoneId, 61);
        } else if (i == 2) {
            this.mRetValue = this.mTelephonyManager.setPreferredNetworkType(phoneId, 60);
        }
    }

    public AlertDialog showAlertDialog(final TelephonyManagerSprd.RadioCapbility mRadio, int setValueIndex, final int valueIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int setRadioFeature = changeIndexToValue(mRadio, setValueIndex);
        Log.d(TAG, "setRadioFeature is " + setRadioFeature);
        builder.setMessage(getString(R.string.setting_dialog_con1)).setCancelable(false).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity1.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                NetModeSelectActivity1.this.mLastPrimaryRadio = mRadio;
                NetModeSelectActivity1.this.mLastPrimaryValueIndex = valueIndex;
                NetModeSelectActivity1.this.setRadioFeatures(setRadioFeature);
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity1.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                NetModeSelectActivity1.this.retrievePrefIndex(mRadio, valueIndex);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    private int changeIndexToValue(TelephonyManagerSprd.RadioCapbility radio, int setValueIndex) {
        if (!radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            return -1;
        }
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

    public void setRadioFeatures(final int setRadioFeature) {
        Log.d(TAG, "now setRadioFeatures: " + setRadioFeature);
        this.mLteMainHandler.sendEmptyMessage(1);
        new Thread(new Runnable() { // from class: com.sprd.engineermode.debuglog.NetModeSelectActivity1.4
            @Override // java.lang.Runnable
            public void run() {
                NetModeSelectActivity1.this.isCurrentPrimary = true;
                NetModeSelectActivity1.this.mNewPrimaryRadioFeature = setRadioFeature;
                NetModeSelectActivity1 netModeSelectActivity1 = NetModeSelectActivity1.this;
                netModeSelectActivity1.mRetValue = netModeSelectActivity1.mTelephonyManager.setPreferredNetworkType(setRadioFeature);
                NetModeSelectActivity1.this.mLteMainHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retrievePrefIndex(TelephonyManagerSprd.RadioCapbility radio, int lastValueIndex) {
        if (radio != null && radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB)) {
            this.mListPreference[this.primaryPhoneId].setValueIndex(lastValueIndex);
            SharedPreferences.Editor edit = this.mSharePref.edit();
            edit.putString(KEY_CSFB, this.mListPreference[this.primaryPhoneId].getValue());
            edit.commit();
        }
    }

    private void setSummary(TelephonyManagerSprd.RadioCapbility radio) {
        if (radio == null) {
            return;
        }
        if (radio.equals(TelephonyManagerSprd.RadioCapbility.CSFB) || radio.equals(TelephonyManagerSprd.RadioCapbility.FLW)) {
            Log.d(TAG, "primaryPhoneId = " + this.primaryPhoneId);
            ListPreference[] listPreferenceArr = this.mListPreference;
            int i = this.primaryPhoneId;
            listPreferenceArr[i].setSummary(listPreferenceArr[i].getEntry());
        }
    }
}
