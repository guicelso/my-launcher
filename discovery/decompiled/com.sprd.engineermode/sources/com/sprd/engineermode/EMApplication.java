package com.sprd.engineermode;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import com.sprd.engineermode.telephony.NotificationBroadcastReceiver;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class EMApplication extends Application {
    private static final String CLOSE_NOTICE = "After test,please close this notificaiton";
    private static final String NEW_MESSAGE = "Notice From EngineerMode!";
    private static final String NOTIFICATION_CHANNEL_ID = "mobileServiceMessages";
    private static final int NOTIFICATION_ID = 4387;
    private static final String TAG = "EMApplication";
    private static Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences preferences;
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        CoreApi.init(mContext);
        Log.d(TAG, "get engtest is " + this.teleApi.EngTestStatus().isEngTest());
        if (this.teleApi.EngTestStatus().isEngTest()) {
            initNotifiCation(mContext);
        }
        this.preferences = getSharedPreferences("charge_control", 0);
        this.editor = this.preferences.edit();
        this.editor.putBoolean("charge_func", false);
        this.editor.commit();
        this.preferences = getSharedPreferences("asdiv_control", 0);
        this.editor = this.preferences.edit();
        this.editor.putBoolean("lte_dpdt_switch1", false);
        this.editor.putBoolean("lte_dpdt_switch2", false);
        this.editor.putBoolean("gsm_dpdt_switch1", false);
        this.editor.putBoolean("gsm_dpdt_switch2", false);
        this.editor.putBoolean("lte_pri_tr_div_close", false);
        this.editor.putBoolean("lte_pri_tx_div_rx", false);
        this.editor.putBoolean("gsm_pri_tr_div_close", false);
        this.editor.putBoolean("gsm_pri_tx_div_rx", false);
        this.editor.commit();
    }

    private static void initNotifiCation(Context context) {
        NotificationManager mNotifiCation = (NotificationManager) context.getSystemService("notification");
        mNotifiCation.cancel(NOTIFICATION_ID);
        mNotifiCation.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID, context.getResources().getString(R.string.app_name), 2));
        RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
        mRemoteViews.setTextViewText(R.id.mt_notification, "Notice From EngineerMode!\n    After test,please close this notificaiton");
        Intent clickIntent = new Intent(context, (Class<?>) NotificationBroadcastReceiver.class);
        clickIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 134217728);
        mRemoteViews.setOnClickPendingIntent(R.id.bt_notification, pendingIntent);
        Notification notify = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID).setContent(mRemoteViews).setAutoCancel(true).setTicker(NEW_MESSAGE).setContentTitle(NEW_MESSAGE).setPriority(0).setOngoing(true).setSmallIcon(R.drawable.cg).build();
        mNotifiCation.notify(NOTIFICATION_ID, notify);
    }

    public static Context getContext() {
        return mContext;
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
    }
}
