package com.tw.core.model;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.tw.core.utils.C0065s;
import java.util.Timer;

/* JADX INFO: loaded from: classes.dex */
public class DameonService extends Service {
    private static final String hi = DameonService.class.getSimpleName();
    private Context hk;
    private com.tw.core.a hl;
    private Timer timer = new Timer();
    private com.tw.core.b hj = new f(this);
    private BroadcastReceiver hn = new g(this);
    private ServiceConnection hm = new h(this);

    /* JADX INFO: Access modifiers changed from: private */
    public void mi() {
        try {
            if (this.hm != null) {
                bindService(new Intent(this, (Class<?>) MainService.class), this.hm, 1);
            }
        } catch (Exception e) {
            C0065s.bL(hi, e);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.hj;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.hk = this;
        C0065s.bK(hi, "onCreate()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.hn, intentFilter);
        mi();
    }

    @Override // android.app.Service
    public void onDestroy() {
        C0065s.bK(hi, "onDestroy()");
        sendBroadcast(new Intent("com.tw.core.DESTROY_DAMEONSERVICE"));
        if (this.hn != null) {
            unregisterReceiver(this.hn);
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }
}
