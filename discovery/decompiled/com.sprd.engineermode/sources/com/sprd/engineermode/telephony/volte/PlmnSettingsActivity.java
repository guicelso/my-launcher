package com.sprd.engineermode.telephony.volte;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.exception.EmException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes4.dex */
public class PlmnSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String KEY_PLMN_FLAG1 = "test_plmn_1";
    private static final String KEY_PLMN_FLAG2 = "test_plmn_2";
    private static final int MSG_GET_PLMN_FLAG1 = 0;
    private static final int MSG_GET_PLMN_FLAG2 = 2;
    private static final int MSG_SET_PLMN_FLAG1 = 1;
    private static final int MSG_SET_PLMN_FLAG2 = 3;
    private static final String TAG = "PlmnSettingsActivity";
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private EditTextPreference mPlmnFlag1;
    private EditTextPreference mPlmnFlag2;
    private SharedPreferences mSharePref;
    private VolteHandler mVolteHandler;
    private Context mContext = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_plmn_settings);
        this.mContext = this;
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mSharePref.registerOnSharedPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVolteHandler = new VolteHandler(ht.getLooper());
        this.mPlmnFlag1 = (EditTextPreference) findPreference(KEY_PLMN_FLAG1);
        this.mPlmnFlag1.setOnPreferenceChangeListener(this);
        this.mPlmnFlag2 = (EditTextPreference) findPreference(KEY_PLMN_FLAG2);
        this.mPlmnFlag2.setOnPreferenceChangeListener(this);
    }

    @Override // android.app.Activity
    public void onStart() {
        EditTextPreference editTextPreference = this.mPlmnFlag1;
        if (editTextPreference != null && editTextPreference.isEnabled()) {
            Message getPlmnFlag1 = this.mVolteHandler.obtainMessage(0);
            this.mVolteHandler.sendMessage(getPlmnFlag1);
        }
        EditTextPreference editTextPreference2 = this.mPlmnFlag2;
        if (editTextPreference2 != null && editTextPreference2.isEnabled()) {
            Message getPlmnFlag2 = this.mVolteHandler.obtainMessage(2);
            this.mVolteHandler.sendMessage(getPlmnFlag2);
        }
        super.onStart();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        String prefkey = pref.getKey();
        Log.d(TAG, "prefkey: " + prefkey);
        if (prefkey.equals(KEY_PLMN_FLAG1)) {
            Message setPlmnFlag1 = this.mVolteHandler.obtainMessage(1, newValue);
            this.mVolteHandler.sendMessage(setPlmnFlag1);
        } else if (prefkey.equals(KEY_PLMN_FLAG2)) {
            Message setPlmnFlag2 = this.mVolteHandler.obtainMessage(3, newValue);
            this.mVolteHandler.sendMessage(setPlmnFlag2);
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

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPlmn(int index) {
        final EditTextPreference plmnText;
        Supplier<String> supplier;
        if (index == 1) {
            plmnText = this.mPlmnFlag1;
            supplier = new Supplier() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$qYUGAR_AMPJtl4zHyBovP1s1PeI
                @Override // java.util.function.Supplier
                public final Object get() {
                    return PlmnSettingsActivity.teleApi.voltePlmnSettings().getPlmn1();
                }
            };
        } else if (index == 2) {
            plmnText = this.mPlmnFlag2;
            supplier = new Supplier() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$jJrhgDPY0i00Ot85dj8psHDlpzI
                @Override // java.util.function.Supplier
                public final Object get() {
                    return PlmnSettingsActivity.teleApi.voltePlmnSettings().getPlmn2();
                }
            };
        } else {
            throw new RuntimeException("invalid index");
        }
        try {
            final String plmn = supplier.get();
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$ViyOt38kr7h3X9g650HUCdNf-pA
                @Override // java.lang.Runnable
                public final void run() {
                    PlmnSettingsActivity.lambda$getPlmn$2(plmnText, plmn);
                }
            });
        } catch (EmException e) {
            e.printStackTrace();
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$G9Ks3klf8QQfos1Aa3s1vbs5cbk
                @Override // java.lang.Runnable
                public final void run() {
                    PlmnSettingsActivity.lambda$getPlmn$3(plmnText);
                }
            });
        }
    }

    static /* synthetic */ void lambda$getPlmn$2(EditTextPreference plmnText, String plmn) {
        plmnText.setEnabled(true);
        plmnText.setSummary(plmn);
    }

    static /* synthetic */ void lambda$getPlmn$3(EditTextPreference plmnText) {
        plmnText.setEnabled(false);
        plmnText.setSummary(R.string.feature_abnormal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlmn(int index, final String plmn) {
        final EditTextPreference plmnText;
        Consumer<String> func;
        if (plmn.trim().equals("")) {
            return;
        }
        if (index == 1) {
            plmnText = this.mPlmnFlag1;
            func = new Consumer() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$6JUTAM6QRldjUuQiti-SU-tRbJM
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PlmnSettingsActivity.teleApi.voltePlmnSettings().setPlmn1((String) obj);
                }
            };
        } else if (index == 2) {
            plmnText = this.mPlmnFlag2;
            func = new Consumer() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$8PIBosDumsyzOKC8CSwFKXlE14w
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PlmnSettingsActivity.teleApi.voltePlmnSettings().setPlmn2((String) obj);
                }
            };
        } else {
            throw new RuntimeException("invalid index");
        }
        try {
            func.accept(plmn);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$eE6oNkAOa1vzR2RHump_o0Eu2sk
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setPlmn$6$PlmnSettingsActivity(plmn, plmnText);
                }
            });
        } catch (EmException e) {
            e.printStackTrace();
            showFailToast();
        }
    }

    public /* synthetic */ void lambda$setPlmn$6$PlmnSettingsActivity(String plmn, EditTextPreference plmnText) {
        SharedPreferences.Editor editor = this.mSharePref.edit();
        editor.putString(KEY_PLMN_FLAG1, plmn);
        editor.commit();
        plmnText.setSummary(plmn);
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
                PlmnSettingsActivity.this.getPlmn(1);
                return;
            }
            if (i == 1) {
                PlmnSettingsActivity.this.setPlmn(1, (String) msg.obj);
            } else if (i == 2) {
                PlmnSettingsActivity.this.getPlmn(2);
            } else if (i == 3) {
                PlmnSettingsActivity.this.setPlmn(2, (String) msg.obj);
            }
        }
    }

    private void showFailToast() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.-$$Lambda$PlmnSettingsActivity$pHRIYtJ49AeWbd-vyfEAAB21tXk
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFailToast$7$PlmnSettingsActivity();
            }
        });
    }

    public /* synthetic */ void lambda$showFailToast$7$PlmnSettingsActivity() {
        Toast.makeText(this.mContext, "Fail", 0).show();
    }
}
