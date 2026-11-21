package com.sprd.engineermode.telephony;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import com.sprd.engineermode.EMApplication;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationBroadcastReceiver";
    private static final String message = "please restart the phone or set airplane mode to sync the network in settings";
    private Context mAppContext;
    private int NOTIFICATION_ID = 4387;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("notificationId", -1);
        this.mAppContext = EMApplication.getContext();
        Log.d(TAG, "type is" + type);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (type == this.NOTIFICATION_ID) {
            Log.d(TAG, "run here");
            this.teleApi.EngTestStatus().set(0);
            Log.d(TAG, "get vSystemProperties is " + this.teleApi.EngTestStatus().isEngTest());
            notificationManager.cancel(this.NOTIFICATION_ID);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mAppContext);
            builder.setMessage(message).setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.telephony.NotificationBroadcastReceiver.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int id) {
                    NotificationBroadcastReceiver.this.mAppContext = null;
                }
            });
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.getWindow().setType(2003);
            alert.show();
        }
    }
}
