package com.tw.service;

import android.bluetooth.BluetoothAdapter;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class N implements Runnable {
    final /* synthetic */ O Rc;

    N(O o) {
        this.Rc = o;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d("TWService", "BOOT_COMPLETED2");
        if (TWService.O(this.Rc.n)) {
            this.Rc.n.q(false);
        }
        this.Rc.n.Ia();
        if (this.Rc.n.Zb) {
            TWService.Na(this.Rc.n);
        }
        Settings.System.getInt(this.Rc.n.getContentResolver(), "SYSTEM_WIFI", 0);
        if (Settings.System.getInt(this.Rc.n.getContentResolver(), "SYSTEM_WIFIAP", 0) == 1 && !((WifiManager) this.Rc.n.getSystemService("wifi")).isWifiApEnabled()) {
            TWService.j(this.Rc.n, true);
        }
        if (SystemProperties.getInt("persist.tw.bt.module", 2) == 0) {
            try {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                defaultAdapter.enable();
                if (defaultAdapter.isEnabled()) {
                    defaultAdapter.setScanMode(23);
                }
            } catch (Exception unused) {
            }
        }
        ((TelephonyManager) this.Rc.n.getSystemService("phone")).setDataEnabled(true);
        if (!this.Rc.n.Mb) {
            this.Rc.n.Ba();
            this.Rc.n.Ha();
        }
        if (this.Rc.n.t().contains("BT-TRS")) {
            this.Rc.n.pa.write(40730, 1, 0, "cmd overlay enable-exclusive com.tw.bt.send.theme");
        }
        this.Rc.n.Ca();
    }
}
