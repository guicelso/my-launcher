package com.tw.service;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.tw.john.InteractionController;
import android.tw.john.TWUtil;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class M implements TWUtil.IEvent {
    final /* synthetic */ TWService n;

    M(TWService tWService) {
        this.n = tWService;
    }

    /* JADX WARN: Code duplicated, block: B:75:0x01fa A[Catch: Exception -> 0x0666, TryCatch #7 {Exception -> 0x0666, blocks: (B:41:0x0113, B:57:0x0146, B:59:0x014f, B:61:0x0169, B:62:0x016f, B:64:0x017c, B:65:0x0182, B:67:0x018a, B:68:0x0196, B:70:0x01c0, B:72:0x01c8, B:73:0x01db, B:75:0x01fa, B:76:0x0213, B:78:0x021b, B:80:0x0250, B:82:0x0257, B:84:0x0260, B:86:0x026a, B:88:0x02b3, B:89:0x02c0, B:91:0x02df, B:85:0x0267, B:81:0x0254, B:92:0x02ef, B:94:0x02f7, B:96:0x02ff), top: B:207:0x0113 }] */
    /* JADX WARN: Not initialized variable reg: 17, insn: 0x066b: MOVE (r18 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY]), block:B:188:0x066b */
    public boolean pollEvent(int i, int i2, int i3, Object obj, Object obj2) throws Throwable {
        String str;
        String str2;
        String str3;
        boolean z;
        String str4 = "arg2=";
        try {
            if (i == 40733) {
                try {
                    Log.d("TWService", "what=0x9f1darg1=" + i2 + "arg2=" + i3);
                    if (!this.n.ua) {
                        return true;
                    }
                    this.n.pa.write(40730, 1, 0, "rwrite /sys/power/state on");
                    if (this.n.t().contains("XPH17")) {
                        SystemProperties.set("persist.usb.otg", "1");
                    } else {
                        SystemProperties.set("persist.usb.otg", "0");
                    }
                    if (this.n.Aa != 2) {
                        Log.e("TWService", "BUGBUG");
                    }
                    Log.i("TWService", "-SLEEP3");
                    if (i2 > 0 && this.n._a != i2) {
                        this.n._a = i2;
                        this.n.mHandler.sendMessageDelayed(this.n.mHandler.obtainMessage(65529, i2, 0), 1000L);
                        if (i2 == 1 || i2 == 2 || i2 == 3) {
                            this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.NET_WK").setFlags(32).putExtra("arg1", i2).putExtra("arg2", i3).putExtra("obj3", (String) obj), UserHandle.CURRENT);
                        }
                    }
                    this.n.mHandler.removeMessages(65530);
                    this.n.mHandler.sendEmptyMessageDelayed(65530, 60000L);
                    return true;
                } catch (Exception e) {
                    e = e;
                    str2 = "arg1=";
                }
            } else {
                str3 = "arg2=";
                if (i != 33282 && i != 514) {
                    return false;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("what=");
                sb.append(i);
                try {
                    sb.append("arg1=");
                    sb.append(i2);
                    str4 = str3;
                    try {
                        sb.append(str4);
                        sb.append(i3);
                        Log.d("TWService", sb.toString());
                        str3 = str4;
                        try {
                            if (i2 != 1) {
                                if (i2 != 4) {
                                    if (i2 != 5) {
                                        if (i2 == 6) {
                                            this.n.pa.write(514, i2, this.n.Aa);
                                            this.n.Ab = 0;
                                        } else if (i2 != 9) {
                                            if (i2 == 10) {
                                                this.n.pa.write(514, i2, this.n.Aa);
                                                this.n.Ab = 0;
                                            }
                                        } else if (this.n.Aa == 1) {
                                            this.n.Aa = 0;
                                            this.n.mHandler.removeMessages(65289);
                                            if (TWService.O(this.n)) {
                                                this.n.q(false);
                                            }
                                            this.n.Ia();
                                            if (this.n.Xa) {
                                                this.n.Xa = false;
                                            }
                                            if (this.n.Ya) {
                                                this.n.Ya = false;
                                                TWService.j(this.n, true);
                                            }
                                            ((TelephonyManager) this.n.getSystemService("phone")).setDataEnabled(true);
                                            TWService.b(0);
                                            Log.i("TWService", "-STANDBY");
                                            this.n.pa.write(514, 9);
                                            if (this.n.Va != 0 && !this.n.Ca) {
                                                this.n.pa.write(769, 192, this.n.Va);
                                            }
                                            TWService.I(this.n, this.n.Va);
                                            this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.DO_WAKEUP"), UserHandle.CURRENT);
                                        } else {
                                            Log.e("TWService", "BUGBUG");
                                        }
                                    } else if (this.n.ua) {
                                        this.n.ua = false;
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/power/state on");
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/reverse_irq 1");
                                        this.n.pa.write(40730, 1, 0, "rwrite /proc/usb_control start_usb");
                                        if (this.n.t().contains("XPH17")) {
                                            SystemProperties.set("persist.usb.otg", "1");
                                        } else {
                                            SystemProperties.set("persist.usb.otg", "0");
                                        }
                                        if (this.n.Aa == 2) {
                                            this.n.Aa = 0;
                                        } else {
                                            Log.e("TWService", "BUGBUG");
                                        }
                                        Log.i("TWService", "-SLEEP2");
                                        this.n.mHandler.postDelayed(new L(this), 1000L);
                                        this.n.mHandler.removeMessages(65529);
                                        this.n.mHandler.removeMessages(65530);
                                        this.n.Sa();
                                        this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.DO_WAKEUP"), UserHandle.CURRENT);
                                        if (SystemProperties.getInt("persist.tw.bt.module", 2) != 0) {
                                            this.n.pa.write(65296, 2, 1);
                                        }
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/usb 1");
                                        z = true;
                                        if (Settings.System.getInt(this.n.getContentResolver(), "SYSTEM_DORMANCY_SHOW_BA", 0) == 1) {
                                            this.n.mHandler.sendEmptyMessageDelayed(65321, 100L);
                                        }
                                        return z;
                                    }
                                } else if (!this.n.ua) {
                                    z = true;
                                    if (SystemProperties.getInt("sys.boot_completed", 0) == 1) {
                                        Log.i("TWService", "+SLEEP2");
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/reverse_irq 0");
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/class/sensor_i2c/usb 0");
                                        if (this.n.Aa == 0) {
                                            Log.i("TWService", "+SLEEP5");
                                            this.n.pa.write(40730, 1, 0, "killall com.ms.ms2160");
                                            this.n.Aa = 2;
                                            this.n.Va = this.n.mSource;
                                            this.n.va();
                                            this.n.Xa = ((WifiManager) this.n.getSystemService("wifi")).isWifiEnabled();
                                            Settings.System.putInt(this.n.getContentResolver(), "SYSTEM_WIFI", this.n.Xa ? 1 : 0);
                                            this.n.Ya = ((WifiManager) this.n.getSystemService("wifi")).isWifiApEnabled();
                                            Settings.System.putInt(this.n.getContentResolver(), "SYSTEM_WIFIAP", this.n.Ya ? 1 : 0);
                                            if (this.n.Ya) {
                                                TWService.j(this.n, false);
                                            }
                                            this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.DO_SHUTDOWN"), UserHandle.CURRENT);
                                            TWService.k(this.n, this.n.Va);
                                            TWService.L(this.n);
                                            TWService.M(this.n);
                                            this.n.pa.write(40730, 2);
                                            if (SystemProperties.getInt("persist.tw.kill", 0) == 0) {
                                                TWService.ia(this.n);
                                            }
                                        } else if (this.n.Aa == 1) {
                                            Log.i("TWService", "+SLEEP4");
                                            this.n.Aa = 2;
                                        } else {
                                            Log.e("TWService", "BUGBUG");
                                        }
                                        this.n.mHandler.removeMessages(65529);
                                        this.n.mHandler.removeMessages(65530);
                                        this.n.mHandler.removeMessages(65289);
                                        this.n.Za = -1;
                                        if ((this.n.Wa & 3) == 3) {
                                            try {
                                                ConnectivityManager connectivityManager = (ConnectivityManager) this.n.getSystemService("connectivity");
                                                if (connectivityManager.getNetworkInfo(connectivityManager.getActiveNetwork()).getType() == 0) {
                                                    this.n.Qa();
                                                }
                                            } catch (Exception unused) {
                                            }
                                        }
                                        this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.DO_SLEEP"), UserHandle.CURRENT);
                                        try {
                                            BluetoothAdapter.getDefaultAdapter().disable();
                                        } catch (Exception unused2) {
                                        }
                                        if ((this.n.Wa & 3) == 3) {
                                            try {
                                                Thread.sleep(200L);
                                            } catch (Exception unused3) {
                                            }
                                            Log.i("TWService", "netRun=" + this.n.Za);
                                        }
                                        if ((this.n.Wa & 3) != 3 || this.n.Za < 0) {
                                            try {
                                                Thread.sleep(200L);
                                            } catch (Exception unused4) {
                                            }
                                        }
                                        SystemProperties.set("sys.tw.forcepip", "0");
                                        if (this.n.t().contains("XPH17")) {
                                            SystemProperties.set("persist.usb.otg", "1");
                                        } else {
                                            SystemProperties.set("persist.usb.otg", "2");
                                        }
                                        if (SystemProperties.getInt("persist.tw.kill", 0) == 0) {
                                            TWService.ia(this.n);
                                        }
                                        Log.i("TWService", "+SLEEP");
                                        this.n.pa.write(514, 4);
                                        this.n.ua = true;
                                        this.n.pa.write(40730, 1, 0, "rwrite /proc/usb_control stop_usb");
                                        ((PowerManager) this.n.getSystemService("power")).goToSleep(SystemClock.uptimeMillis());
                                        this.n.pa.write(40730, 1, 0, "rwrite /sys/power/state mem");
                                    }
                                    return z;
                                }
                            } else if (this.n.Aa == 0 && SystemProperties.getInt("sys.boot_completed", 0) == 1) {
                                this.n.Aa = 1;
                                this.n.pa.write(40730, 1, 0, "killall com.ms.ms2160");
                                this.n.Va = this.n.mSource;
                                if ((this.n.Wa & 3) == 3) {
                                    ((TelephonyManager) this.n.getSystemService("phone")).setDataEnabled(false);
                                    this.n.mHandler.postDelayed(new J(this), 2000L);
                                }
                                this.n.va();
                                this.n.Xa = ((WifiManager) this.n.getSystemService("wifi")).isWifiEnabled();
                                Settings.System.putInt(this.n.getContentResolver(), "SYSTEM_WIFI", this.n.Xa ? 1 : 0);
                                this.n.Ya = ((WifiManager) this.n.getSystemService("wifi")).isWifiApEnabled();
                                Settings.System.putInt(this.n.getContentResolver(), "SYSTEM_WIFIAP", this.n.Ya ? 1 : 0);
                                if (this.n.Ya) {
                                    TWService.j(this.n, false);
                                }
                                this.n.sendBroadcastAsUser(new Intent("com.unisound.intent.action.DO_SHUTDOWN"), UserHandle.CURRENT);
                                Log.i("TWService", "+STANDBY2");
                                TWService.b(1);
                                this.n.pa.write(40720, 0, (this.n.Wa & 3) == 3 ? 7000 : 3000);
                                TWService.k(this.n, this.n.Va);
                                this.n.tb.forceStopPackage("com.zjinnova.zlink");
                                this.n.tb.forceStopPackage("com.huawei.nearby");
                                if ((this.n.Wa & 1) == 0 || this.n.Za < 0) {
                                    this.n.q(true);
                                }
                                InteractionController.sendKey(127, 0);
                                TWService.L(this.n);
                                TWService.M(this.n);
                                this.n.pa.write(40730, 2);
                                SystemProperties.set("sys.tw.forcepip", "0");
                                if (SystemProperties.getInt("persist.tw.kill", 0) == 0) {
                                    TWService.ia(this.n);
                                }
                            } else {
                                Log.e("TWService", "BUGBUG");
                            }
                            return true;
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        str2 = "arg1=";
                        str3 = str4;
                    }
                } catch (Exception e4) {
                    e = e4;
                    str2 = "arg1=";
                }
            }
            str3 = str4;
        } catch (Exception e5) {
            e = e5;
            str2 = str;
        }
        Log.e("TWService", Log.getStackTraceString(e) + "what=" + i + str2 + i2 + str3 + i3);
        return true;
    }
}
