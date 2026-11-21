package com.sprd.engineermode;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;
import com.sprd.engineermode.gcf.OperatorActivity;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.utils.IATUtils;
import io.reactivex.annotations.SchedulerSupport;

/* JADX INFO: loaded from: classes4.dex */
public class EngineerModeActivity_2 extends PreferenceActivity {
    public static final String ACTION_DISABLE_SELECTION = "action.disable.selection";
    private static final String CURRENT_MODE = "current_mode";
    public static final String ERROR = "ERROR";
    private static final String KEY_CURRENT_MODE = "current_mode";
    private static final String KEY_GCF = "gcf";
    private static final String KEY_IOT = "iot";
    private static final String KEY_OPERATOR = "operator";
    public static final String ONE = "1,";
    private static final String TAG = "EngineerModeActivity_2";
    public static final String TWO = "2,";
    private static final String[] mItemNamesList = {"gcf_protocol", "gcf_rf", "gcf_sim", "gcf_volte", "iot_common", "iot_huawei", "iot_ericsson", "iot_nsn", "operator_cmcc", "operator_cucc", "operator_orange", "operator_telstra", "operator_vodafone", "operator_telcel", "operator_movistar", "operator_reliance", "operator_dt", "operator_mtn", "operator_claro", "operator_docomo", "operator_telefonica"};
    private Context mContext;
    private Preference mCurrentMode;
    private EngMode_two_Handler mEngMode_two_Handler;
    private Preference mGcf;
    private Preference mIot;
    private Preference mOperator;
    private int testValue1;
    private int testValue2;
    private String value1;
    private String value2;
    private String testMode = "+SPGCFIOTOPER: 0,0";
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.sprd.engineermode.EngineerModeActivity_2.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(EngineerModeActivity_2.TAG, "onReceive:action = " + action);
            if (action.equals(EngineerModeActivity_2.ACTION_DISABLE_SELECTION)) {
                EngineerModeActivity_2.this.disableSelection();
            }
        }
    };

    @Override // android.preference.PreferenceActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mEngMode_two_Handler = new EngMode_two_Handler(ht.getLooper());
        this.mContext = this;
        addPreferencesFromResource(R.xml.pref_engineermode_activity_two);
        this.mCurrentMode = findPreference("current_mode");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_DISABLE_SELECTION);
        registerReceiver(this.mBroadcastReceiver, filter);
    }

    @Override // android.app.Activity
    public void onStart() {
        String currentMode = IATUtils.sendATCmd(engconstents.ENG_AT_GET_GCF, "atchannel0");
        String mode = analyseMode(currentMode);
        Log.d(TAG, "currentMode is " + mode);
        this.mCurrentMode.setSummary(mode);
        if (!mode.equals(SchedulerSupport.NONE)) {
            disableSelection();
        }
        super.onStart();
    }

    private void enableSelection() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableSelection() {
    }

    private String analyseMode(String currentMode) {
        String[] result = currentMode.split("\n|:");
        if (result[1].contains(",")) {
            String[] values = result[1].trim().split(",");
            if (values[0].equals(values[1])) {
                if (values[0].equals("0")) {
                    return SchedulerSupport.NONE;
                }
                return getMode(values[0]);
            }
            if (values[0].equals("0")) {
                String setValue0 = IATUtils.sendATCmd("AT+SPGCFIOTOPER=1," + values[1], "atchannel0");
                if (setValue0.contains("OK")) {
                    Log.d(TAG, "setValue0 success!");
                }
                return getMode(values[1]);
            }
            if (!values[1].equals("0")) {
                Toast.makeText(this.mContext, "The mode in this version is not correct", 1).show();
                return ERROR;
            }
            String setValue1 = IATUtils.sendATCmd("AT+SPGCFIOTOPER=2," + values[0], "atchannel0");
            if (setValue1.contains("OK")) {
                Log.d(TAG, "setValue1 success!");
            }
            return getMode(values[0]);
        }
        Log.d(TAG, "please check the format of return value! ");
        return ERROR;
    }

    private String getMode(String values) {
        int i = Integer.parseInt(values.trim());
        if (i >= 30) {
            String mode = OperatorActivity.keys[i - 30];
            return mode;
        }
        if (i == 1) {
            return "gcf_protocol";
        }
        if (i == 2) {
            return "gcf_rf";
        }
        if (i == 3) {
            return "gcf_sim";
        }
        if (i == 4) {
            return "gcf_volte";
        }
        switch (i) {
            case 10:
                return "iot_common";
            case 11:
                return "iot_huawei";
            case 12:
                return "iot_ericsson";
            case 13:
                return "iot_nsn";
            default:
                return SchedulerSupport.NONE;
        }
    }

    public static void checkAtReturnResult(String result1, String result2, Context context) {
        Log.d(TAG, "checkAtReturnResultresult1: " + result1 + "result2: " + result2);
        if (result1.contains("OK") && result2.contains("OK")) {
            Toast.makeText(context, "success", 1).show();
            if (context instanceof OperatorActivity) {
                String result = IATUtils.sendATCmd(engconstents.ENG_AT_RESET, "atchannel0");
                if (result.contains("OK")) {
                    Toast.makeText(context, "modem resetting, please wait!", 1).show();
                }
                sendDisableSelectionMessage(context);
                confirmToReboot(context);
                return;
            }
            return;
        }
        if (result1.contains(ERROR) || result2.contains(ERROR)) {
            Toast.makeText(context, "not support command!", 1).show();
        } else {
            Toast.makeText(context, "fail", 1).show();
        }
    }

    public static void checkAtReturnResultForGcf(String result1, Context context) {
        Log.d(TAG, "checkAtReturnResultForGcf\tresult1: " + result1);
        if (!result1.contains("OK")) {
            if (result1.contains(ERROR)) {
                Toast.makeText(context, "not support command!", 1).show();
                return;
            } else {
                Toast.makeText(context, "fail", 1).show();
                return;
            }
        }
        Toast.makeText(context, "success", 1).show();
        if (context instanceof OperatorActivity) {
            String result = IATUtils.sendATCmd(engconstents.ENG_AT_RESET, "atchannel0");
            if (result.contains("OK")) {
                Toast.makeText(context, "modem resetting, please wait!", 1).show();
            }
            sendDisableSelectionMessage(context);
            confirmToReboot(context);
        }
    }

    private static void confirmToReboot(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage(R.string.orange_remaining).setCancelable(false).setPositiveButton(R.string.orange_remaining_ok, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.EngineerModeActivity_2.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PowerManager pm = (PowerManager) context.getSystemService("power");
                pm.reboot("switch_orange");
            }
        }).create();
        alertDialog.show();
    }

    public static void sendDisableSelectionMessage(Context context) {
        Intent intent = new Intent(ACTION_DISABLE_SELECTION);
        context.sendBroadcast(intent);
    }

    @Override // android.preference.PreferenceActivity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        Log.d(TAG, "key is " + key);
        if (key == null) {
            return false;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override // android.preference.PreferenceActivity, android.app.ListActivity, android.app.Activity
    public void onDestroy() {
        BroadcastReceiver broadcastReceiver = this.mBroadcastReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    private static class EngMode_two_Handler extends Handler {
        public EngMode_two_Handler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int i = msg.what;
        }
    }
}
