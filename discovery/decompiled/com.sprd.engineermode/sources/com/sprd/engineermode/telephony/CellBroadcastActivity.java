package com.sprd.engineermode.telephony;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.EMSwitchPreference;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CellBroadcastActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final int GET_CELL_BROADCAST = 0;
    private static final int SET_CELL_BROADCAST = 1;
    private static final int STATUS_OFF = 1;
    private static final int STATUS_ON = 0;
    private static final String TAG = "AppSettingsPrefActivity";
    private AppSettingHandler mAppSettingHandler;
    private EMSwitchPreference[] mCellBroadcast = null;
    private Handler mUiThread = new Handler() { // from class: com.sprd.engineermode.telephony.CellBroadcastActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int simindex = msg.arg1;
            if (msg.what == 0) {
                boolean isCheck = msg.arg2 == 1;
                Log.d(CellBroadcastActivity.TAG, "setui sim" + simindex + NotificationCompat.CATEGORY_STATUS + String.valueOf(isCheck));
                CellBroadcastActivity.this.mCellBroadcast[simindex].setChecked(isCheck);
            }
        }
    };
    private String mStrTmp = null;

    private class AppSettingHandler extends Handler {
        public AppSettingHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int simindex = msg.arg1;
            int i = msg.what;
            if (i != 0) {
                if (i == 1) {
                    CellBroadcastActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_SETCB + msg.arg2, "atchannel" + String.valueOf(simindex));
                    if (CellBroadcastActivity.this.mStrTmp.contains("OK")) {
                        CellBroadcastActivity.this.mUiThread.post(CellBroadcastActivity.this.new AppATSuccessRunnable());
                        return;
                    } else {
                        CellBroadcastActivity.this.mUiThread.post(CellBroadcastActivity.this.new AppATFailRunnable(msg));
                        return;
                    }
                }
                return;
            }
            CellBroadcastActivity.this.mStrTmp = IATUtils.sendATCmd(engconstents.ENG_AT_GETCB, "atchannel" + String.valueOf(simindex));
            if (CellBroadcastActivity.this.mStrTmp.contains("OK")) {
                String[] str1 = CellBroadcastActivity.this.mStrTmp.split(":");
                Log.d(CellBroadcastActivity.TAG, str1[1]);
                String[] str2 = str1[1].split(",");
                Log.d(CellBroadcastActivity.TAG, str2[0]);
                if (str2[0] != null && str2[0].contains("0")) {
                    Log.d(CellBroadcastActivity.TAG, "get sim" + simindex + "status on");
                    CellBroadcastActivity.this.mUiThread.sendMessage(CellBroadcastActivity.this.mUiThread.obtainMessage(msg.what, simindex, 1));
                    return;
                }
                Log.d(CellBroadcastActivity.TAG, "get sim" + simindex + "status off");
                CellBroadcastActivity.this.mUiThread.sendMessage(CellBroadcastActivity.this.mUiThread.obtainMessage(msg.what, simindex, 0));
            }
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(this));
        PreferenceGroup preGroup = getPreferenceScreen();
        this.mCellBroadcast = new EMSwitchPreference[TelephonyManagerProxy.getPhoneCount()];
        for (int i = 0; i < TelephonyManagerProxy.getPhoneCount(); i++) {
            this.mCellBroadcast[i] = new EMSwitchPreference(this, null);
            this.mCellBroadcast[i].setTitle("Sim" + i);
            this.mCellBroadcast[i].setOnPreferenceChangeListener(this);
            TelephonyManager telMgr = TelephonyManagerProxy.getService();
            if (telMgr == null || telMgr.getSimState(i) != 5) {
                this.mCellBroadcast[i].setEnabled(false);
            }
            preGroup.addPreference(this.mCellBroadcast[i]);
        }
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mAppSettingHandler = new AppSettingHandler(ht.getLooper());
        for (int i2 = 0; i2 < TelephonyManagerProxy.getPhoneCount(); i2++) {
            EMSwitchPreference[] eMSwitchPreferenceArr = this.mCellBroadcast;
            if (eMSwitchPreferenceArr[i2] != null && eMSwitchPreferenceArr[i2].isEnabled()) {
                Message cellBroadcast = this.mAppSettingHandler.obtainMessage(0, i2, 0);
                this.mAppSettingHandler.sendMessage(cellBroadcast);
            }
        }
    }

    @Override // android.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        int i = 0;
        while (true) {
            EMSwitchPreference[] eMSwitchPreferenceArr = this.mCellBroadcast;
            if (i >= eMSwitchPreferenceArr.length) {
                return true;
            }
            if (eMSwitchPreferenceArr[i] == preference) {
                int statusWant = 0;
                if (eMSwitchPreferenceArr[i].isChecked()) {
                    statusWant = 1;
                }
                Message m = this.mAppSettingHandler.obtainMessage(1, i, statusWant, this.mCellBroadcast[i]);
                this.mAppSettingHandler.sendMessage(m);
            }
            i++;
        }
    }

    class AppATFailRunnable implements Runnable {
        private int arg1;
        private Object obj;

        public AppATFailRunnable(Message msg) {
            this.arg1 = 0;
            this.obj = null;
            this.arg1 = msg.arg1;
            this.obj = msg.obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(CellBroadcastActivity.this, "Fail", 0).show();
            for (EMSwitchPreference cellBroadcast : CellBroadcastActivity.this.mCellBroadcast) {
                if (cellBroadcast == this.obj) {
                    cellBroadcast.setChecked(this.arg1 == 1);
                }
            }
        }
    }

    class AppATSuccessRunnable implements Runnable {
        AppATSuccessRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Toast.makeText(CellBroadcastActivity.this, "Success", 0).show();
        }
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    protected void onDestroy() {
        if (this.mAppSettingHandler != null) {
            Log.d(TAG, "HandlerThread has quit");
            this.mAppSettingHandler.getLooper().quit();
        }
        super.onDestroy();
    }

    @Override // android.preference.PreferenceActivity, android.app.Activity
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
