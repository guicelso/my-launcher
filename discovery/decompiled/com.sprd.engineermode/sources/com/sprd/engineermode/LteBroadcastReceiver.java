package com.sprd.engineermode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: classes4.dex */
public class LteBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "LteBroadcastReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        boolean isLTEReady = intent.getBooleanExtra("lte", false);
        editor.putBoolean("LTEReady", isLTEReady);
        editor.commit();
    }
}
