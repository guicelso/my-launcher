package com.dofun.carsetting.d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.dofun.carsetting.presenter.b;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: InternetModel.java */
/* JADX INFO: loaded from: classes.dex */
public class g<P extends com.dofun.carsetting.presenter.b> extends b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private WifiManager f198a;
    private ConnectivityManager b;
    private TelephonyManager c;
    private boolean d;
    private boolean e;
    private boolean f;
    private Context g;
    private WifiInfo h;
    private Map<String, com.dofun.carsetting.g.k> i;
    private BroadcastReceiver j;
    private Handler k;

    private g() {
        this.d = false;
        this.e = false;
        this.f = false;
        this.i = new ConcurrentHashMap();
        this.j = new BroadcastReceiver() { // from class: com.dofun.carsetting.d.g.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("wifi_state", 1);
                    Message messageObtainMessage = g.this.k.obtainMessage();
                    messageObtainMessage.what = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                    switch (intExtra) {
                        case 0:
                            messageObtainMessage.arg1 = 0;
                            break;
                        case 1:
                            messageObtainMessage.arg1 = 1;
                            break;
                        case 2:
                            messageObtainMessage.arg1 = 2;
                            break;
                        case 3:
                            messageObtainMessage.arg1 = 3;
                            break;
                    }
                    g.this.k.removeMessages(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
                    g.this.k.sendMessageDelayed(messageObtainMessage, 500L);
                    return;
                }
                if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                    Message messageObtainMessage2 = g.this.k.obtainMessage();
                    messageObtainMessage2.what = 65281;
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        g.this.f198a.getConnectionInfo();
                        messageObtainMessage2.arg1 = 1;
                        g.this.h = g.this.f198a.getConnectionInfo();
                    } else {
                        messageObtainMessage2.arg1 = 1;
                        messageObtainMessage2.obj = "";
                        g.this.h = null;
                    }
                    g.this.k.sendMessage(messageObtainMessage2);
                    return;
                }
                if ("android.net.wifi.RSSI_CHANGED".equals(intent.getAction())) {
                    WifiInfo connectionInfo = g.this.f198a.getConnectionInfo();
                    Message messageObtainMessage3 = g.this.k.obtainMessage();
                    messageObtainMessage3.what = 65282;
                    messageObtainMessage3.obj = connectionInfo;
                    g.this.k.sendMessage(messageObtainMessage3);
                    return;
                }
                if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    Message messageObtainMessage4 = g.this.k.obtainMessage();
                    messageObtainMessage4.what = 65283;
                    messageObtainMessage4.arg1 = Settings.System.getInt(g.this.g.getContentResolver(), "airplane_mode_on", 0);
                    g.this.f = messageObtainMessage4.arg1 != 0;
                    Iterator it = g.this.i.entrySet().iterator();
                    while (it.hasNext()) {
                        ((com.dofun.carsetting.g.k) ((Map.Entry) it.next()).getValue()).c(g.this.f);
                    }
                    g.this.k.sendMessage(messageObtainMessage4);
                    return;
                }
                if ("android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                    new ArrayList().clear();
                    List<ScanResult> scanResults = g.this.f198a.getScanResults();
                    Iterator it2 = g.this.i.entrySet().iterator();
                    while (it2.hasNext()) {
                        ((com.dofun.carsetting.g.k) ((Map.Entry) it2.next()).getValue()).a(scanResults);
                    }
                    return;
                }
                if ("android.net.wifi.supplicant.STATE_CHANGE".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("supplicantError", 0);
                    if (intExtra2 == 1) {
                        Iterator it3 = g.this.i.entrySet().iterator();
                        while (it3.hasNext()) {
                            ((com.dofun.carsetting.g.k) ((Map.Entry) it3.next()).getValue()).a(intExtra2);
                        }
                        return;
                    }
                    return;
                }
                if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
                    Message messageObtainMessage5 = g.this.k.obtainMessage();
                    messageObtainMessage5.what = 65284;
                    messageObtainMessage5.arg1 = intent.getIntExtra("wifi_state", 0);
                    g.this.k.sendMessage(messageObtainMessage5);
                    return;
                }
                if (!"android.net.wifi.WIFI_AP_STATE_CHANGED".equals(intent.getAction())) {
                    Iterator it4 = g.this.i.entrySet().iterator();
                    while (it4.hasNext()) {
                        ((com.dofun.carsetting.g.k) ((Map.Entry) it4.next()).getValue()).b(g.this.c());
                    }
                    return;
                }
                int intExtra3 = intent.getIntExtra("wifi_state", 14);
                if (intExtra3 == 11) {
                    g.this.e = false;
                } else {
                    if (intExtra3 != 13) {
                        return;
                    }
                    g.this.e = true;
                }
            }
        };
        this.k = new Handler(new Handler.Callback() { // from class: com.dofun.carsetting.d.g.2
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case MotionEventCompat.ACTION_POINTER_INDEX_MASK /* 65280 */:
                        for (Map.Entry entry : g.this.i.entrySet()) {
                            if (message.arg1 == 3) {
                                ((com.dofun.carsetting.g.k) entry.getValue()).a(true);
                            } else if (message.arg1 == 1) {
                                ((com.dofun.carsetting.g.k) entry.getValue()).a(false);
                            }
                        }
                        Log.d("InternetModel", "handleMessage: WIFI_STATE:" + message.arg1);
                        return false;
                    case 65281:
                        Iterator it = g.this.i.entrySet().iterator();
                        while (it.hasNext()) {
                            ((com.dofun.carsetting.g.k) ((Map.Entry) it.next()).getValue()).a(g.this.h);
                        }
                        return false;
                    case 65282:
                        WifiInfo connectionInfo = g.this.f198a.getConnectionInfo();
                        if (connectionInfo.getBSSID() != null) {
                            int rssi = connectionInfo.getRssi();
                            int iCalculateSignalLevel = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
                            Log.d("InternetModel", "handleMessage: RSSI_STATE:" + rssi + " strength:" + iCalculateSignalLevel);
                            Iterator it2 = g.this.i.entrySet().iterator();
                            while (it2.hasNext()) {
                                ((com.dofun.carsetting.g.k) ((Map.Entry) it2.next()).getValue()).a(connectionInfo.getSSID(), iCalculateSignalLevel);
                            }
                        }
                        return false;
                    case 65283:
                    case 65284:
                    default:
                        return false;
                }
            }
        });
    }

    /* JADX INFO: compiled from: InternetModel.java */
    private static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private static g f201a = new g();
    }

    public static g a() {
        return a.f201a;
    }

    public void a(Context context) {
        this.g = context.getApplicationContext();
        e();
    }

    public void a(String str, com.dofun.carsetting.g.k kVar) {
        this.i.put(str, kVar);
    }

    public void a(String str) {
        this.i.remove(str);
    }

    private void e() {
        this.f198a = (WifiManager) this.g.getApplicationContext().getSystemService("wifi");
        this.b = (ConnectivityManager) this.g.getSystemService("connectivity");
        this.c = (TelephonyManager) this.g.getSystemService("phone");
        f();
    }

    private void f() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
        intentFilter.addAction("com.notification");
        this.g.registerReceiver(this.j, intentFilter);
    }

    public void a(boolean z) {
        this.f198a.setWifiEnabled(z);
    }

    public int b() {
        return this.f198a.getWifiState();
    }

    public void b(boolean z) {
        this.c.setDataEnabled(z);
    }

    public boolean c() {
        return this.c.getDataEnabled();
    }

    public void c(boolean z) {
        this.b.setAirplaneMode(z);
    }

    public boolean d() {
        return this.f;
    }
}
