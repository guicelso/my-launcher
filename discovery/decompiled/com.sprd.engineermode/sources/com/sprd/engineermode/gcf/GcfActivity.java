package com.sprd.engineermode.gcf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sprd.engineermode.EMSwitchPreference;
import com.sprd.engineermode.EngineerModeActivity_2;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.impl.nonpublic.ImsManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class GcfActivity extends PreferenceActivity {
    private static final String CFU_CONTROL = "persist.sys.callforwarding";
    private static final String CURRENT_MODE = "current_mode";
    private static final int DELAY_SECONDS = 100;
    private static final String KEY_GCF_PROTOCAL = "gcf_protocol";
    private static final String KEY_GCF_RF = "gcf_rf";
    private static final String KEY_GCF_SIM = "gcf_sim";
    private static final String KEY_GCF_VOLTE = "gcf_volte";
    private static final String KEY_MBSFN_SWITCH = "mbsfn_switch";
    private static final int SET_GCF_PROTOCAL = 1;
    private static final int SET_GCF_RF = 2;
    private static final int SET_GCF_SIM = 3;
    private static final int SET_GCF_VOLTE = 4;
    private static final String TAG = "GcfActivity";
    private static final int TEST_OVER = 5;
    private String mATCmd;
    private String mAtCmd1;
    private Context mContext;
    private GcfHandler mGcfHandler;
    private Preference mGcfProtocal;
    private Preference mGcfRf;
    private Preference mGcfSim;
    private Preference mGcgVolte;
    private EMSwitchPreference mMbsfnSwitch;
    private String mStrTmp;
    private String mStrTmp1;
    private TelephonyManager mTelephonyManager;
    private String resultInfo = "";

    static /* synthetic */ String access$084(GcfActivity x0, Object x1) {
        String str = x0.resultInfo + x1;
        x0.resultInfo = str;
        return str;
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mGcfHandler = new GcfHandler(ht.getLooper());
        this.mContext = this;
        this.mTelephonyManager = TelephonyManagerProxy.INSTANCE.getTelephonyManager();
        addPreferencesFromResource(R.xml.pref_gcf_activity);
        this.mGcfProtocal = findPreference(KEY_GCF_PROTOCAL);
        this.mGcfRf = findPreference(KEY_GCF_RF);
        this.mGcfSim = findPreference(KEY_GCF_SIM);
        this.mGcgVolte = findPreference(KEY_GCF_VOLTE);
        this.mMbsfnSwitch = (EMSwitchPreference) findPreference(KEY_MBSFN_SWITCH);
        if (SystemPropertiesProxy.get("persist.sys.mbsfn.open", "false").equals("true")) {
            this.mMbsfnSwitch.setChecked(true);
        } else {
            this.mMbsfnSwitch.setChecked(false);
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) throws IllegalAccessException, InvocationTargetException {
        String key = preference.getKey();
        Log.d(TAG, "key is " + key);
        if (key == null) {
            return false;
        }
        if (key.equals(KEY_GCF_PROTOCAL)) {
            this.resultInfo = "";
            Message mGcfProtocal = this.mGcfHandler.obtainMessage(1, key);
            this.mGcfHandler.sendMessage(mGcfProtocal);
        } else if (key.equals(KEY_GCF_RF)) {
            this.resultInfo = "";
            Message mGcfRf = this.mGcfHandler.obtainMessage(2, key);
            this.mGcfHandler.sendMessage(mGcfRf);
        } else if (key.equals(KEY_GCF_SIM)) {
            this.resultInfo = "";
            Message mGcfSim = this.mGcfHandler.obtainMessage(3, key);
            this.mGcfHandler.sendMessage(mGcfSim);
        } else if (key.equals(KEY_GCF_VOLTE)) {
            this.resultInfo = "";
            Message mGcfVolte = this.mGcfHandler.obtainMessage(4, key);
            this.mGcfHandler.sendMessage(mGcfVolte);
        } else if (key.equals(KEY_MBSFN_SWITCH)) {
            if (this.mMbsfnSwitch.isChecked()) {
                String result = IATUtils.sendATCmd("at+cembmscfg=1", "atchannel0");
                Log.d(TAG, "send ENG_AT_MBSFN 1 result: " + result);
                if (result != null && result.contains("OK")) {
                    SystemPropertiesProxy.set("persist.sys.mbsfn.open", "true");
                    this.mMbsfnSwitch.setChecked(true);
                } else {
                    SystemPropertiesProxy.set("persist.sys.mbsfn.open", "false");
                    this.mMbsfnSwitch.setChecked(false);
                }
            } else {
                String result2 = IATUtils.sendATCmd("at+cembmscfg=0", "atchannel0");
                Log.d(TAG, "send ENG_AT_MBSFN 0 result: " + result2);
                if (result2 != null && result2.contains("OK")) {
                    SystemPropertiesProxy.set("persist.sys.mbsfn.open", "false");
                    this.mMbsfnSwitch.setChecked(false);
                } else {
                    SystemPropertiesProxy.set("persist.sys.mbsfn.open", "true");
                    this.mMbsfnSwitch.setChecked(true);
                }
            }
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    class GcfHandler extends Handler {
        public GcfHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, InvocationTargetException {
            String keyName = (String) msg.obj;
            Log.d(GcfActivity.TAG, "keyName is " + keyName);
            int i = msg.what;
            if (i == 1) {
                SystemPropertiesProxy.set(GcfActivity.CFU_CONTROL, "0");
                GcfActivity.this.resultInfo = "always_not_query is :Success\n";
                SystemPropertiesProxy.set("persist.vendor.sys.modemreset", "1");
                GcfActivity.access$084(GcfActivity.this, "modem_reset is:Success\n");
                GcfActivity.this.mTelephonyManager.setDataEnabled(false);
                GcfActivity.access$084(GcfActivity.this, "Close data connect is:Success\n");
                ImsManagerProxy.INSTANCE.setEnhanced4gLteModeSetting(GcfActivity.this, false);
                GcfActivity.access$084(GcfActivity.this, "Close volte is:Success\n");
                SharepreferenceUtils.saveInfo(GcfActivity.this, GcfActivity.CURRENT_MODE, keyName);
                GcfActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,1";
                Log.d(GcfActivity.TAG, "SET_GCF_PROTOCAL");
                GcfActivity gcfActivity = GcfActivity.this;
                gcfActivity.mStrTmp = IATUtils.sendATCmd(gcfActivity.mATCmd, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResultForGcf(GcfActivity.this.mStrTmp, GcfActivity.this.mContext);
                Message mTestOver = GcfActivity.this.mGcfHandler.obtainMessage(5, keyName);
                GcfActivity.this.mGcfHandler.sendMessageDelayed(mTestOver, 100L);
                return;
            }
            if (i == 2) {
                SystemPropertiesProxy.set(GcfActivity.CFU_CONTROL, "0");
                GcfActivity.this.resultInfo = "always_not_query is :Success\n";
                SystemPropertiesProxy.set("persist.vendor.sys.modemreset", "1");
                GcfActivity.access$084(GcfActivity.this, "modem_reset is:Success\n");
                SharepreferenceUtils.saveInfo(GcfActivity.this, GcfActivity.CURRENT_MODE, keyName);
                GcfActivity.this.mATCmd = "AT+SPAUTO=1";
                Log.d(GcfActivity.TAG, "OPEN_AUTO_ANSWER");
                GcfActivity gcfActivity2 = GcfActivity.this;
                gcfActivity2.mStrTmp = IATUtils.sendATCmd(gcfActivity2.mATCmd, "atchannel0");
                if (GcfActivity.this.mStrTmp.contains("OK")) {
                    GcfActivity.access$084(GcfActivity.this, "auto answer is:Success\n");
                } else {
                    GcfActivity.access$084(GcfActivity.this, "auto answer is:Fail\n");
                }
                GcfActivity.this.mTelephonyManager.setDataEnabled(false);
                GcfActivity.access$084(GcfActivity.this, "Close data connect is:Success\n");
                GcfActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,2";
                Log.d(GcfActivity.TAG, "SET_GCF_RF");
                GcfActivity gcfActivity3 = GcfActivity.this;
                gcfActivity3.mStrTmp = IATUtils.sendATCmd(gcfActivity3.mATCmd, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResultForGcf(GcfActivity.this.mStrTmp, GcfActivity.this.mContext);
                Message mTestOverRf = GcfActivity.this.mGcfHandler.obtainMessage(5, keyName);
                GcfActivity.this.mGcfHandler.sendMessageDelayed(mTestOverRf, 100L);
                return;
            }
            if (i == 3) {
                SystemPropertiesProxy.set(GcfActivity.CFU_CONTROL, "0");
                GcfActivity.this.resultInfo = "always_not_query is :Success\n";
                SystemPropertiesProxy.set("persist.vendor.sys.modemreset", "1");
                GcfActivity.access$084(GcfActivity.this, "modem_reset is:Success\n");
                ImsManagerProxy.INSTANCE.setEnhanced4gLteModeSetting(GcfActivity.this, false);
                GcfActivity.access$084(GcfActivity.this, "Close volte is:Success\n");
                SharepreferenceUtils.saveInfo(GcfActivity.this, GcfActivity.CURRENT_MODE, keyName);
                GcfActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,3";
                Log.d(GcfActivity.TAG, "SET_GCF_SIM");
                GcfActivity gcfActivity4 = GcfActivity.this;
                gcfActivity4.mStrTmp = IATUtils.sendATCmd(gcfActivity4.mATCmd, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResultForGcf(GcfActivity.this.mStrTmp, GcfActivity.this.mContext);
                Message mTestOverSim = GcfActivity.this.mGcfHandler.obtainMessage(5, keyName);
                GcfActivity.this.mGcfHandler.sendMessageDelayed(mTestOverSim, 100L);
                return;
            }
            if (i != 4) {
                if (i == 5) {
                    showDialog(GcfActivity.this.resultInfo, keyName);
                    return;
                }
                return;
            }
            TelephonyManagerProxy telephonyManagerProxy = TelephonyManagerProxy.INSTANCE;
            TelephonyManagerProxy.setNetworkRoaming(false);
            GcfActivity.this.resultInfo = "Close data roaming is :Success\n";
            ImsManagerProxy.INSTANCE.setEnhanced4gLteModeSetting(GcfActivity.this, true);
            GcfActivity.access$084(GcfActivity.this, "Open volte is:Success\n");
            SystemPropertiesProxy.set(GcfActivity.CFU_CONTROL, "0");
            GcfActivity.access$084(GcfActivity.this, "always_not_query is :Success\n");
            SharepreferenceUtils.saveInfo(GcfActivity.this, GcfActivity.CURRENT_MODE, keyName);
            GcfActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,4";
            Log.d(GcfActivity.TAG, "SET_GCF_VOLTE");
            GcfActivity gcfActivity5 = GcfActivity.this;
            gcfActivity5.mStrTmp = IATUtils.sendATCmd(gcfActivity5.mATCmd, "atchannel0");
            EngineerModeActivity_2.checkAtReturnResultForGcf(GcfActivity.this.mStrTmp, GcfActivity.this.mContext);
            Message mTestOverVolte = GcfActivity.this.mGcfHandler.obtainMessage(5, keyName);
            GcfActivity.this.mGcfHandler.sendMessageDelayed(mTestOverVolte, 100L);
        }

        private void showDialog(String resultInfo, String testTitle) {
            Log.d(GcfActivity.TAG, "resultInfo is " + resultInfo + "testTitle is" + testTitle);
            AlertDialog alertDialog = new AlertDialog.Builder(GcfActivity.this).setTitle(testTitle).setCancelable(false).setMessage(resultInfo).setPositiveButton(R.string.alertdialog_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.gcf.GcfActivity.GcfHandler.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create();
            alertDialog.show();
        }
    }
}
