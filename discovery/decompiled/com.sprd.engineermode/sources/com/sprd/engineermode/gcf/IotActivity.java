package com.sprd.engineermode.gcf;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import com.sprd.engineermode.EngineerModeActivity_2;
import com.sprd.engineermode.R;
import com.unisoc.engineermode.core.utils.IATUtils;

/* JADX INFO: loaded from: classes4.dex */
public class IotActivity extends PreferenceActivity {
    private static final String KEY_COMMON = "iot_common";
    private static final String KEY_ERICSSON = "iot_ericsson";
    private static final String KEY_HUAWEI = "iot_huawei";
    private static final String KEY_NSN = "iot_nsn";
    private static final int SET_IOT_COMMON = 1;
    private static final int SET_IOT_ERICSSON = 3;
    private static final int SET_IOT_HUAWEI = 2;
    private static final int SET_IOT_NSN = 4;
    private static final String TAG = "IotActivity";
    private String mATCmd;
    private String mAtCmd1;
    private Context mContext;
    private IotHandler mIotHandler;
    private String mStrTmp;
    private String mStrTmp1;

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mIotHandler = new IotHandler(ht.getLooper());
        addPreferencesFromResource(R.xml.pref_iot_activity);
        this.mContext = this;
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d(TAG, "key is " + key);
        if (key == null) {
            return false;
        }
        if (key.equals(KEY_COMMON)) {
            Message mIotCommon = this.mIotHandler.obtainMessage(1, key);
            this.mIotHandler.sendMessage(mIotCommon);
        } else if (key.equals(KEY_HUAWEI)) {
            Message mIotHuawei = this.mIotHandler.obtainMessage(2, key);
            this.mIotHandler.sendMessage(mIotHuawei);
        } else if (key.equals(KEY_ERICSSON)) {
            Message mIotEricsson = this.mIotHandler.obtainMessage(3, key);
            this.mIotHandler.sendMessage(mIotEricsson);
        } else if (key.equals(KEY_NSN)) {
            Message mIotNsn = this.mIotHandler.obtainMessage(4, key);
            this.mIotHandler.sendMessage(mIotNsn);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    class IotHandler extends Handler {
        public IotHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String keyName = (String) msg.obj;
            Log.d(IotActivity.TAG, "keyName is " + keyName);
            int i = msg.what;
            if (i == 1) {
                IotActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,10";
                IotActivity.this.mAtCmd1 = "AT+SPGCFIOTOPER=2,10";
                Log.d(IotActivity.TAG, "SET_IOT_COMMON");
                IotActivity iotActivity = IotActivity.this;
                iotActivity.mStrTmp = IATUtils.sendATCmd(iotActivity.mATCmd, "atchannel0");
                IotActivity iotActivity2 = IotActivity.this;
                iotActivity2.mStrTmp1 = IATUtils.sendATCmd(iotActivity2.mAtCmd1, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResult(IotActivity.this.mStrTmp, IotActivity.this.mStrTmp1, IotActivity.this.mContext);
                return;
            }
            if (i == 2) {
                IotActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,11";
                IotActivity.this.mAtCmd1 = "AT+SPGCFIOTOPER=2,11";
                Log.d(IotActivity.TAG, "SET_IOT_HUAWEI");
                IotActivity iotActivity3 = IotActivity.this;
                iotActivity3.mStrTmp = IATUtils.sendATCmd(iotActivity3.mATCmd, "atchannel0");
                IotActivity iotActivity4 = IotActivity.this;
                iotActivity4.mStrTmp1 = IATUtils.sendATCmd(iotActivity4.mAtCmd1, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResult(IotActivity.this.mStrTmp, IotActivity.this.mStrTmp1, IotActivity.this.mContext);
                return;
            }
            if (i == 3) {
                IotActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,12";
                IotActivity.this.mAtCmd1 = "AT+SPGCFIOTOPER=2,12";
                Log.d(IotActivity.TAG, "SET_IOT_ERICSSON");
                IotActivity iotActivity5 = IotActivity.this;
                iotActivity5.mStrTmp = IATUtils.sendATCmd(iotActivity5.mATCmd, "atchannel0");
                IotActivity iotActivity6 = IotActivity.this;
                iotActivity6.mStrTmp1 = IATUtils.sendATCmd(iotActivity6.mAtCmd1, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResult(IotActivity.this.mStrTmp, IotActivity.this.mStrTmp1, IotActivity.this.mContext);
                return;
            }
            if (i == 4) {
                IotActivity.this.mATCmd = "AT+SPGCFIOTOPER=1,13";
                IotActivity.this.mAtCmd1 = "AT+SPGCFIOTOPER=2,13";
                Log.d(IotActivity.TAG, "SET_IOT_NSN");
                IotActivity iotActivity7 = IotActivity.this;
                iotActivity7.mStrTmp = IATUtils.sendATCmd(iotActivity7.mATCmd, "atchannel0");
                IotActivity iotActivity8 = IotActivity.this;
                iotActivity8.mStrTmp1 = IATUtils.sendATCmd(iotActivity8.mAtCmd1, "atchannel0");
                EngineerModeActivity_2.checkAtReturnResult(IotActivity.this.mStrTmp, IotActivity.this.mStrTmp1, IotActivity.this.mContext);
            }
        }
    }
}
