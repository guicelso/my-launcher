package com.sprd.engineermode.telephony.volte;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.function.IntSupplier;

/* JADX INFO: loaded from: classes4.dex */
public class DevelopMentSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String KEY_DEV_FLAG1 = "dev_flag_1";
    private static final String KEY_DEV_FLAG2 = "dev_flag_2";
    private static final String KEY_DEV_FLAG3 = "dev_flag_3";
    private static final int MSG_GET_DEV_FLAG1 = 0;
    private static final int MSG_GET_DEV_FLAG2 = 2;
    private static final int MSG_GET_DEV_FLAG3 = 4;
    private static final int MSG_SET_DEV_FLAG1 = 1;
    private static final int MSG_SET_DEV_FLAG2 = 3;
    private static final int MSG_SET_DEV_FLAG3 = 5;
    private static final String TAG = "DevelopMentSettingsActivity";
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private EditTextPreference mDevFlag1;
    private EditTextPreference mDevFlag2;
    private EditTextPreference mDevFlag3;
    private VolteHandler mVolteHandler;
    private Context mContext = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_volte_develop_settings);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVolteHandler = new VolteHandler(ht.getLooper());
        this.mDevFlag1 = (EditTextPreference) findPreference(KEY_DEV_FLAG1);
        this.mDevFlag1.setOnPreferenceChangeListener(this);
        this.mDevFlag2 = (EditTextPreference) findPreference(KEY_DEV_FLAG2);
        this.mDevFlag2.setOnPreferenceChangeListener(this);
        this.mDevFlag3 = (EditTextPreference) findPreference(KEY_DEV_FLAG3);
        this.mDevFlag3.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    public void onStart() {
        EditTextPreference editTextPreference = this.mDevFlag1;
        if (editTextPreference != null && editTextPreference.isEnabled()) {
            Message getDevFlag1 = this.mVolteHandler.obtainMessage(0);
            this.mVolteHandler.sendMessage(getDevFlag1);
        }
        EditTextPreference editTextPreference2 = this.mDevFlag2;
        if (editTextPreference2 != null && editTextPreference2.isEnabled()) {
            Message getDevFlag2 = this.mVolteHandler.obtainMessage(2);
            this.mVolteHandler.sendMessage(getDevFlag2);
        }
        EditTextPreference editTextPreference3 = this.mDevFlag3;
        if (editTextPreference3 != null && editTextPreference3.isEnabled()) {
            Message getDevFlag3 = this.mVolteHandler.obtainMessage(4);
            this.mVolteHandler.sendMessage(getDevFlag3);
        }
        super.onStart();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        String prefkey = pref.getKey();
        Log.d(TAG, "prefkey: " + prefkey);
        if (prefkey.equals(KEY_DEV_FLAG1)) {
            Message setDevFlag1 = this.mVolteHandler.obtainMessage(1, newValue);
            this.mVolteHandler.sendMessage(setDevFlag1);
        } else if (prefkey.equals(KEY_DEV_FLAG2)) {
            Message setDevFlag2 = this.mVolteHandler.obtainMessage(3, newValue);
            this.mVolteHandler.sendMessage(setDevFlag2);
        } else if (prefkey.equals(KEY_DEV_FLAG3)) {
            Message setDevFlag3 = this.mVolteHandler.obtainMessage(5, newValue);
            this.mVolteHandler.sendMessage(setDevFlag3);
        }
        return true;
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
    public void getDevFlag(int index) {
        final EditTextPreference devFlagText;
        IntSupplier supplier;
        if (index == 1) {
            devFlagText = this.mDevFlag1;
            supplier = new IntSupplier() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$9U3VMW1AMYl8JHxg-r3EeakWfE0
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    return DevelopMentSettingsActivity.teleApi.volteDevelopSettings().getDevFlag1();
                }
            };
        } else if (index == 2) {
            devFlagText = this.mDevFlag2;
            supplier = new IntSupplier() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$61e1hBpkJ6lSC7-njvwn1r9MUlo
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    return DevelopMentSettingsActivity.teleApi.volteDevelopSettings().getDevFlag2();
                }
            };
        } else if (index == 3) {
            devFlagText = this.mDevFlag3;
            supplier = new IntSupplier() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$lqQkHgZujdSjxq40Bi2ovcXSu_k
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    return DevelopMentSettingsActivity.teleApi.volteDevelopSettings().getDevFlag3();
                }
            };
        } else {
            throw new RuntimeException("invalid index");
        }
        try {
            final int devFlag = supplier.getAsInt();
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$RfS7JshIOcxb2e-cqSCdHks2Xqw
                @Override // java.lang.Runnable
                public final void run() {
                    DevelopMentSettingsActivity.lambda$getDevFlag$3(devFlagText, devFlag);
                }
            });
        } catch (EmException e) {
            e.printStackTrace();
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$JyXyIuivKbIBFkttTDClzZQjff8
                @Override // java.lang.Runnable
                public final void run() {
                    DevelopMentSettingsActivity.lambda$getDevFlag$4(devFlagText);
                }
            });
        }
    }

    static /* synthetic */ void lambda$getDevFlag$3(EditTextPreference devFlagText, int devFlag) {
        devFlagText.setEnabled(true);
        devFlagText.setText(String.valueOf(devFlag));
        devFlagText.setSummary(String.valueOf(devFlag));
    }

    static /* synthetic */ void lambda$getDevFlag$4(EditTextPreference devFlagText) {
        devFlagText.setEnabled(false);
        devFlagText.setSummary(R.string.feature_abnormal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDevFlag(int index, final String value) {
        final EditTextPreference devFlagText;
        if (value.trim().equals("")) {
            return;
        }
        try {
            int flag = Integer.parseInt(value);
            try {
                if (index == 1) {
                    devFlagText = this.mDevFlag1;
                    teleApi.volteDevelopSettings().setDevFlag1(flag);
                } else if (index == 2) {
                    devFlagText = this.mDevFlag2;
                    teleApi.volteDevelopSettings().setDevFlag2(flag);
                } else if (index == 3) {
                    devFlagText = this.mDevFlag3;
                    teleApi.volteDevelopSettings().setDevFlag3(flag);
                } else {
                    throw new RuntimeException("invalid index");
                }
                this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$WNCKKisFVQJ11Vv3g9GqE_-46nI
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setDevFlag$5$DevelopMentSettingsActivity(devFlagText, value);
                    }
                });
            } catch (EmException e) {
                e.printStackTrace();
                showFailToast();
            }
        } catch (NumberFormatException e2) {
            Toast.makeText(this.mContext, "the number is invalid, should not greater than 2147483647(32 bits)", 0).show();
        }
    }

    public /* synthetic */ void lambda$setDevFlag$5$DevelopMentSettingsActivity(EditTextPreference devFlagText, String value) {
        devFlagText.setText(value);
        devFlagText.setSummary(value);
        Toast.makeText(this.mContext, "Success", 0).show();
    }

    class VolteHandler extends Handler {
        VolteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                DevelopMentSettingsActivity.this.getDevFlag(1);
                return;
            }
            if (i == 1) {
                DevelopMentSettingsActivity.this.setDevFlag(1, (String) msg.obj);
                return;
            }
            if (i == 2) {
                DevelopMentSettingsActivity.this.getDevFlag(2);
                return;
            }
            if (i == 3) {
                DevelopMentSettingsActivity.this.setDevFlag(2, (String) msg.obj);
            } else if (i == 4) {
                DevelopMentSettingsActivity.this.getDevFlag(3);
            } else if (i == 5) {
                DevelopMentSettingsActivity.this.setDevFlag(3, (String) msg.obj);
            }
        }
    }

    private void showFailToast() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$DevelopMentSettingsActivity$dLJxI4vdl4ULi4NuLWeFD7T9ifA
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFailToast$6$DevelopMentSettingsActivity();
            }
        });
    }

    public /* synthetic */ void lambda$showFailToast$6$DevelopMentSettingsActivity() {
        Toast.makeText(this.mContext, "Fail", 0).show();
    }
}
