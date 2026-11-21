package com.tw.service;

import android.bluetooth.BluetoothAdapter;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/* JADX INFO: loaded from: classes.dex */
class K implements Runnable {
    final /* synthetic */ L be;

    K(L l) {
        this.be = l;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        if (TWService.O(this.be.Rc.n)) {
            this.be.Rc.n.q(false);
        }
        this.be.Rc.n.Ia();
        if (this.be.Rc.n.Xa) {
            this.be.Rc.n.Xa = false;
        }
        if (this.be.Rc.n.Ya) {
            this.be.Rc.n.Ya = false;
            TWService.j(this.be.Rc.n, true);
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
        ((TelephonyManager) this.be.Rc.n.getSystemService("phone")).setDataEnabled(true);
        if (this.be.Rc.n.Va != 0 && !this.be.Rc.n.Ca) {
            this.be.Rc.n.pa.write(769, 192, this.be.Rc.n.Va);
        }
        if (Settings.System.getInt(this.be.Rc.n.getContentResolver(), "SYSTEM_DORMANCY_SHOW_BA", 0) != 1) {
            if (this.be.Rc.n.Va != 0 && !this.be.Rc.n.Ca) {
                this.be.Rc.n.pa.write(769, 192, this.be.Rc.n.Va);
            }
            TWService tWService = this.be.Rc.n;
            TWService.I(tWService, tWService.Va);
        }
    }
}
