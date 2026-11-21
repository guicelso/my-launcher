package com.sprd.engineermode.debuglog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class EMMTBFReceiver extends BroadcastReceiver {
    private static final String ACTION_CLEAR_ALL_LOG_FROM_MTBF = "com.sprd.engineermode.action.MTBF";
    private static final String ACTION_CLEAR_ALL_LOG_RESULT_FOR_MTBF = "com.sprd.engineermode.action.MTBFRSP";
    private static final int CLEAR_ALL_LOG = 0;
    private static final String KEY_PACKAGE_NAME = "PACKAGE NAME";
    private static final String KEY_RESULT = "RESULT";
    private static final String KEY_SETITEM = "SETITEM";
    private static final String RESULT_FAIL = "Fail";
    private static final String RESULT_OK = "Ok";
    private static final String SLOG_COMMAND_CLEAR = "slogctl clear";
    private static final int SLOG_COMMAND_RETURN_OK = 0;
    private static final String SLOG_PACKAGE_NAME = "com.sprd.engineermode";
    private static final String TAG = "EMMTBFReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (ACTION_CLEAR_ALL_LOG_FROM_MTBF.equals(intent.getAction())) {
            Log.d(TAG, "receive engapp MTBF action");
        }
        try {
            Process proc = Runtime.getRuntime().exec(SLOG_COMMAND_CLEAR);
            proc.waitFor();
            Log.d(TAG, "result is " + proc.exitValue());
            if (proc.exitValue() == 0) {
                sendMTBFResult(context, RESULT_OK);
            } else {
                sendMTBFResult(context, RESULT_FAIL);
            }
        } catch (IOException ioException) {
            Log.e(TAG, "Catch IOException.\n" + ioException.toString());
            sendMTBFResult(context, RESULT_FAIL);
        } catch (InterruptedException interruptException) {
            Log.e(TAG, "Catch InterruptedException.\n" + interruptException.toString());
            sendMTBFResult(context, RESULT_FAIL);
        } catch (Exception other) {
            Log.e(TAG, "Catch InterruptedException.\n" + other.toString());
            sendMTBFResult(context, RESULT_FAIL);
        }
    }

    private void sendMTBFResult(Context context, String result) {
        Log.d(TAG, "clear all log result for MTBF is " + result);
        Intent resultIntent = new Intent();
        resultIntent.setAction(ACTION_CLEAR_ALL_LOG_RESULT_FOR_MTBF);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PACKAGE_NAME, SLOG_PACKAGE_NAME);
        bundle.putInt(KEY_SETITEM, 0);
        bundle.putString(KEY_RESULT, result);
        resultIntent.putExtras(bundle);
        context.sendBroadcast(resultIntent);
    }
}
