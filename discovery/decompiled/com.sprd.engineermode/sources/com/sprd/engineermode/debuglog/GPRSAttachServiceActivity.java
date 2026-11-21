package com.sprd.engineermode.debuglog;

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
import android.preference.PreferenceGroup;
import android.preference.TwoStatePreference;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes4.dex */
public class GPRSAttachServiceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int GET_GPRS = 1;
    private static final String PROP_ATTACH_ENABLE = "persist.vendor.sys.attach.enable";
    private static final int SET_GPRSATTACH = 2;
    private static final int SET_GPRSDETACH = 3;
    private static final String TAG = "GPRSAttachServiceActivity";
    private Context mContext;
    private GPRSHandler mGPRSHandler;
    private TwoStatePreference mGPRSSetSwitch;
    private int mPhoneCount;
    private int mSetSim;
    private TelephonyManager[] mTelephonyManager;
    private Handler mUiThread = new Handler();
    PreferenceGroup mPreGroup = null;
    private Toast toast = null;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mGPRSHandler = new GPRSHandler(ht.getLooper());
        this.mPhoneCount = TelephonyManagerProxy.getPhoneCount();
        Log.d(TAG, "mPhoneCount is " + this.mPhoneCount);
        this.mTelephonyManager = new TelephonyManager[this.mPhoneCount];
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        this.mPreGroup = getPreferenceScreen();
        this.mGPRSSetSwitch = new EMSwitchPreference(this, null);
        this.mGPRSSetSwitch.setTitle("SIM");
        this.mGPRSSetSwitch.setKey("SIM");
        this.mGPRSSetSwitch.setOnPreferenceChangeListener(this);
        this.mPreGroup.addPreference(this.mGPRSSetSwitch);
    }

    @Override // android.app.Activity
    protected void onStart() {
        this.mSetSim = SubscriptionManagerProxy.getDefaultDataPhoneId();
        Message mGPRSGetMessage = this.mGPRSHandler.obtainMessage(1, this.mSetSim, 0);
        this.mGPRSHandler.sendMessage(mGPRSGetMessage);
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onPause() {
        Toast toast = this.toast;
        if (toast != null) {
            toast.cancel();
        }
        super.onPause();
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mGPRSHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mGPRSHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    private void alertUnattachDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Only one sim is attached");
        alert.setCancelable(false);
        alert.setNeutralButton("Confirm", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.create();
        alert.show();
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == null) {
            return false;
        }
        String prefKey = preference.getKey();
        if (prefKey.equals("SIM")) {
            if (this.mGPRSSetSwitch.isChecked()) {
                Message mGPRSSetMessage = this.mGPRSHandler.obtainMessage(3, this.mSetSim, 0);
                this.mGPRSHandler.sendMessage(mGPRSSetMessage);
                return true;
            }
            Message mGPRSSetMessage2 = this.mGPRSHandler.obtainMessage(2, this.mSetSim, 0);
            this.mGPRSHandler.sendMessage(mGPRSSetMessage2);
            return true;
        }
        return true;
    }

    class GPRSHandler extends Handler {
        public GPRSHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws IllegalAccessException, InvocationTargetException {
            int mSetSim = msg.arg1;
            int i = msg.what;
            if (i == 1) {
                String serverName = "atchannel" + mSetSim;
                String responValue = IATUtils.sendATCmd(engconstents.ENG_AT_GPRS1, serverName);
                Log.d(GPRSAttachServiceActivity.TAG, "ATChannel is " + serverName + ", GET_GPRS is " + responValue);
                if (responValue == null || !responValue.contains("OK")) {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.3
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(false);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("detach");
                        }
                    });
                    return;
                }
                String[] str = responValue.split("\n");
                String str1 = str[0].split(":")[1].trim();
                if ("1".equals(str1)) {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(true);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("attach");
                        }
                    });
                    return;
                } else {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.2
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(false);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("detach");
                        }
                    });
                    return;
                }
            }
            if (i == 2) {
                SystemPropertiesProxy.set(GPRSAttachServiceActivity.PROP_ATTACH_ENABLE, "true");
                String serverName2 = "atchannel" + mSetSim;
                String responValue2 = IATUtils.sendATCmd("AT+CGATT=1", serverName2);
                Log.d(GPRSAttachServiceActivity.TAG, "ATChannel is " + serverName2 + ", SET_GPRSATTACH is " + responValue2);
                if (responValue2.contains("OK")) {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.6
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(true);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("attach");
                        }
                    });
                    return;
                } else {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.7
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(false);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("detach");
                        }
                    });
                    return;
                }
            }
            if (i == 3) {
                SystemPropertiesProxy.set(GPRSAttachServiceActivity.PROP_ATTACH_ENABLE, "false");
                GPRSAttachServiceActivity gPRSAttachServiceActivity = GPRSAttachServiceActivity.this;
                gPRSAttachServiceActivity.toast = Toast.makeText(gPRSAttachServiceActivity.mContext, "please wait 10s", 0);
                GPRSAttachServiceActivity.this.toast.show();
                String serverName3 = "atchannel" + mSetSim;
                String responValue3 = IATUtils.sendATCmd("AT+CGATT=0", serverName3);
                Log.d(GPRSAttachServiceActivity.TAG, "ATChannel is " + serverName3 + ", SET_GPRSDETACH is " + responValue3);
                if (responValue3.contains("OK")) {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.4
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(false);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("detach");
                        }
                    });
                } else {
                    GPRSAttachServiceActivity.this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.debuglog.GPRSAttachServiceActivity.GPRSHandler.5
                        @Override // java.lang.Runnable
                        public void run() {
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setChecked(true);
                            GPRSAttachServiceActivity.this.mGPRSSetSwitch.setSummary("attach");
                        }
                    });
                }
            }
        }
    }
}
