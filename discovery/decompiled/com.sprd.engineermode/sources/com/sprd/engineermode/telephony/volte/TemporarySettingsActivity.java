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
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes4.dex */
public class TemporarySettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int IMPI_LENGTH_MAX = 127;
    private static final String KEY_DOMAIN_SETTING = "domain_setting";
    private static final String KEY_IMPI_SETTING = "impi_setting";
    private static final String KEY_IMPU_SETTING = "impu_setting";
    private static final String KEY_PCSCF_SETTING = "pcscf_setting";
    private static final int MSG_SET_DOMAIN = 2;
    private static final int MSG_SET_IMPI = 0;
    private static final int MSG_SET_IMPU = 1;
    private static final int MSG_SET_PCSCF = 3;
    private static final int PCSCF_LENGTH_MAX = 91;
    private static final String TAG = "TemporarySettingsActivity";
    private static ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private PreferenceGroup mIMPISettingScreen;
    private SharedPreferences mSharePref;
    private VolteHandler mVolteHandler;
    private int mPhoneCount = 0;
    private int mPhoneId = 0;
    private Context mContext = null;
    private Handler mUiThread = new Handler();

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_volte_temporary_settings);
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        this.mContext = this;
        this.mSharePref = PreferenceManager.getDefaultSharedPreferences(this);
        this.mSharePref.registerOnSharedPreferenceChangeListener(this);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mVolteHandler = new VolteHandler(ht.getLooper());
        Log.d(TAG, "");
        this.mIMPISettingScreen = getPreferenceScreen();
        Log.d(TAG, "onCreate mPhoneCount: " + this.mPhoneCount);
        for (int i = 0; i < this.mPhoneCount; i++) {
            PreferenceCategory prefCategory = new PreferenceCategory(this);
            prefCategory.setTitle("SIM" + i);
            this.mIMPISettingScreen.addPreference(prefCategory);
            EditTextPreference impisetting = new EditTextPreference(this);
            impisetting.setKey(KEY_IMPI_SETTING + i);
            impisetting.setTitle("IMPI Setting");
            impisetting.setDialogTitle("IMPI Setting");
            impisetting.setText("");
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
            impusetting.setDialogTitle("IMPU Setting");
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
            domain.setDialogTitle("Domain Setting");
            domain.setOnPreferenceChangeListener(this);
            if (this.mSharePref.getString(KEY_DOMAIN_SETTING + i, null) == null) {
                domain.setSummary(R.string.input);
            } else {
                domain.setSummary(this.mSharePref.getString(KEY_DOMAIN_SETTING + i, null));
            }
            this.mIMPISettingScreen.addPreference(domain);
            EditTextPreference pcscf = new EditTextPreference(this);
            pcscf.setKey(KEY_PCSCF_SETTING + i);
            pcscf.setTitle("P-CSCF");
            pcscf.setDialogTitle("P-CSCF");
            pcscf.setOnPreferenceChangeListener(this);
            if (this.mSharePref.getString(KEY_PCSCF_SETTING + i, null) == null) {
                pcscf.setSummary(R.string.input);
            } else {
                pcscf.setSummary(this.mSharePref.getString(KEY_PCSCF_SETTING + i, null));
            }
            this.mIMPISettingScreen.addPreference(pcscf);
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

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        String prefkey = pref.getKey();
        Log.d(TAG, "onPreferenceChange prefkey: " + prefkey);
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
            if (prefkey.equals(KEY_PCSCF_SETTING + i)) {
                Message setPcscf = this.mVolteHandler.obtainMessage(3, i, 0, newValue);
                this.mVolteHandler.sendMessage(setPcscf);
                return false;
            }
        }
        return true;
    }

    class VolteHandler extends Handler {
        public VolteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 0) {
                Log.d(TemporarySettingsActivity.TAG, "MSG_SET_IMPI");
                TemporarySettingsActivity.this.mPhoneId = msg.arg1;
                final String impiValue = (String) msg.obj;
                if (impiValue != null && impiValue.length() != 0 && impiValue.getBytes(StandardCharsets.UTF_8).length > 127) {
                    Toast.makeText(TemporarySettingsActivity.this.mContext, "The length of the input value can not exceed 127", 0).show();
                    return;
                }
                try {
                    String mImpiCmd = "AT+IMPI=\"" + impiValue + "\"";
                    TemporarySettingsActivity.teleApi.volteTemporarySettings().setImpi(mImpiCmd, TemporarySettingsActivity.this.mPhoneId);
                    TemporarySettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.VolteHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TemporarySettingsActivity.this.mIMPISettingScreen.findPreference(TemporarySettingsActivity.KEY_IMPI_SETTING + TemporarySettingsActivity.this.mPhoneId).setSummary(impiValue);
                            SharedPreferences.Editor editor = TemporarySettingsActivity.this.mSharePref.edit();
                            editor.putString(TemporarySettingsActivity.KEY_IMPI_SETTING + TemporarySettingsActivity.this.mPhoneId, impiValue);
                            editor.commit();
                            Toast.makeText(TemporarySettingsActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    TemporarySettingsActivity.this.showFailToast();
                    return;
                }
            }
            if (i == 1) {
                Log.d(TemporarySettingsActivity.TAG, "MSG_SET_IMPU");
                TemporarySettingsActivity.this.mPhoneId = msg.arg1;
                final String impuValue = (String) msg.obj;
                if (impuValue != null && impuValue.length() != 0 && impuValue.length() > 127) {
                    Toast.makeText(TemporarySettingsActivity.this.mContext, "The length of the input value can not exceed 127", 0).show();
                    return;
                }
                try {
                    String mImpuCmd = "AT+IMPU=\"" + impuValue + "\"";
                    TemporarySettingsActivity.teleApi.volteTemporarySettings().setImpu(mImpuCmd, TemporarySettingsActivity.this.mPhoneId);
                    TemporarySettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.VolteHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            TemporarySettingsActivity.this.mIMPISettingScreen.findPreference(TemporarySettingsActivity.KEY_IMPU_SETTING + TemporarySettingsActivity.this.mPhoneId).setSummary(impuValue);
                            SharedPreferences.Editor editor = TemporarySettingsActivity.this.mSharePref.edit();
                            editor.putString(TemporarySettingsActivity.KEY_IMPU_SETTING + TemporarySettingsActivity.this.mPhoneId, impuValue);
                            editor.commit();
                            Toast.makeText(TemporarySettingsActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    TemporarySettingsActivity.this.showFailToast();
                    return;
                }
            }
            if (i != 2) {
                if (i == 3) {
                    TemporarySettingsActivity.this.mPhoneId = msg.arg1;
                    final String pcscfValue = (String) msg.obj;
                    Log.d(TemporarySettingsActivity.TAG, "MSG_SET_PCSCF pcscfValue: " + pcscfValue);
                    if (pcscfValue != null && pcscfValue.length() != 0) {
                        if (pcscfValue.getBytes().length <= 91) {
                            try {
                                TemporarySettingsActivity.teleApi.volteTemporarySettings().setPcscf(pcscfValue);
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                            TemporarySettingsActivity temporarySettingsActivity = TemporarySettingsActivity.this;
                            temporarySettingsActivity.rebootDialog(temporarySettingsActivity);
                        } else {
                            Toast.makeText(TemporarySettingsActivity.this.mContext, "The length of the input value can not exceed 91", 0).show();
                            return;
                        }
                    }
                    TemporarySettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.VolteHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            TemporarySettingsActivity.this.mIMPISettingScreen.findPreference(TemporarySettingsActivity.KEY_PCSCF_SETTING + TemporarySettingsActivity.this.mPhoneId).setSummary(pcscfValue);
                            SharedPreferences.Editor editor = TemporarySettingsActivity.this.mSharePref.edit();
                            editor.putString(TemporarySettingsActivity.KEY_PCSCF_SETTING + TemporarySettingsActivity.this.mPhoneId, pcscfValue);
                            editor.commit();
                            Toast.makeText(TemporarySettingsActivity.this.mContext, "Success", 0).show();
                        }
                    });
                    return;
                }
                return;
            }
            Log.d(TemporarySettingsActivity.TAG, "MSG_SET_DOMAIN");
            TemporarySettingsActivity.this.mPhoneId = msg.arg1;
            final String domainValue = (String) msg.obj;
            if (domainValue != null && domainValue.length() != 0 && domainValue.getBytes().length > 127) {
                Toast.makeText(TemporarySettingsActivity.this.mContext, "The length of the input value can not exceed 127", 0).show();
                return;
            }
            try {
                String mDomainCmd = "AT+DOMAIN=\"" + domainValue + "\"";
                TemporarySettingsActivity.teleApi.volteTemporarySettings().setDomain(mDomainCmd, TemporarySettingsActivity.this.mPhoneId);
                TemporarySettingsActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.VolteHandler.3
                    @Override // java.lang.Runnable
                    public void run() {
                        TemporarySettingsActivity.this.mIMPISettingScreen.findPreference(TemporarySettingsActivity.KEY_DOMAIN_SETTING + TemporarySettingsActivity.this.mPhoneId).setSummary(domainValue);
                        SharedPreferences.Editor editor = TemporarySettingsActivity.this.mSharePref.edit();
                        editor.putString(TemporarySettingsActivity.KEY_DOMAIN_SETTING + TemporarySettingsActivity.this.mPhoneId, domainValue);
                        editor.commit();
                        Toast.makeText(TemporarySettingsActivity.this.mContext, "Success", 0).show();
                    }
                });
            } catch (Exception e4) {
                e4.printStackTrace();
                TemporarySettingsActivity.this.showFailToast();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailToast() {
        this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(TemporarySettingsActivity.this.mContext, "Fail", 0).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebootDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("P-CSCF").setCancelable(false).setMessage(getString(R.string.mode_switch_waring)).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PowerManager pm = (PowerManager) TemporarySettingsActivity.this.getSystemService("power");
                pm.reboot("pcscfset");
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.volte.TemporarySettingsActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }
}
