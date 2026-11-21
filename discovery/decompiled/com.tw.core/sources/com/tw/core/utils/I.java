package com.tw.core.utils;

import android.os.Looper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class I implements com.tw.core.e.b {
    private final Set aC = new HashSet();
    private String aD;
    private String aE;
    private String aF;
    private String aG;
    private TWUtil aH;

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void db(String str) {
        try {
            B.ci("TwSystemUtils", "mcuCallback %s", Integer.valueOf(this.aC.size()));
            if (this.aC.size() > 0) {
                Iterator it = this.aC.iterator();
                while (it.hasNext()) {
                    ((com.tw.core.e.d) it.next()).dS(str);
                }
                this.aC.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.e.b
    public String X() {
        return "TW";
    }

    @Override // com.tw.core.e.b
    public String Y(String str) {
        return SystemProperties.get("ro.fota.oem", str);
    }

    @Override // com.tw.core.e.b
    public String Z() {
        return SystemProperties.get("ro.com.google.gmsversion");
    }

    @Override // com.tw.core.e.b
    public void aa(com.tw.core.e.d dVar) {
        if (dVar != null) {
            this.aC.add(dVar);
        }
        if (!TextUtils.isEmpty(this.aG)) {
            db(this.aG);
            return;
        }
        B.ci("TwSystemUtils", "get mcuVersion by tw", new Object[0]);
        if (this.aH == null) {
            this.aH = new TWUtil();
            if (this.aH.open(new short[]{266}) == 0) {
                this.aH.start();
                am amVar = new am(this, Looper.getMainLooper());
                this.aH.addHandler("TwSystemUtils", amVar);
                amVar.sendEmptyMessageDelayed(0, 1000L);
            } else {
                this.aH.stop();
                this.aH.close();
                this.aH = null;
                db("unknow");
            }
        }
        if (this.aH != null) {
            this.aH.write(266, 255);
        }
    }

    @Override // com.tw.core.e.b
    public String ab() {
        TWUtil tWUtil = new TWUtil();
        if (tWUtil.open((short[]) null) != 0) {
            return "-1";
        }
        String str = String.format("%x", Integer.valueOf(tWUtil.write(65521)));
        tWUtil.close();
        return str;
    }

    @Override // com.tw.core.e.b
    public String ac() {
        return SystemProperties.get("ro.tw.version");
    }

    @Override // com.tw.core.e.b
    public String ad() throws Throwable {
        if (!TextUtils.isEmpty(this.aD)) {
            return this.aD;
        }
        String strCZ = cZ();
        String strDa = da();
        String strCX = cX();
        if (!TextUtils.equals(strCZ, strCX) || "00000000000000000000000000000000".equals(strCZ)) {
            if (!TextUtils.equals(strDa, strCX) || "00000000000000000000000000000000".equals(strDa)) {
                strCZ = cY();
                if (TextUtils.isEmpty(strCZ) || "00000000000000000000000000000000".equals(strCZ)) {
                    strCZ = strCX;
                }
            } else {
                strCZ = strDa;
            }
        }
        this.aD = strCZ;
        return this.aD;
    }

    @Override // com.tw.core.e.b
    public void ae(boolean z) {
        B.cn("netSystemWakeUp " + z, new Object[0]);
        SystemProperties.set("sys.tw.netsleep", z ? "1" : "0");
        ag(z);
    }

    @Override // com.tw.core.e.b
    public void af(boolean z) {
        B.cn("supportUSBPower " + z, new Object[0]);
        TWUtil tWUtil = new TWUtil();
        tWUtil.open((short[]) null);
        tWUtil.write(40730, 1, 0, "rwrite sys/class/sensor_i2c/usb " + (z ? 1 : 0));
        tWUtil.close();
    }

    @Override // com.tw.core.e.b
    public void ag(boolean z) {
        B.cn("switch5VPower " + z, new Object[0]);
        TWUtil tWUtil = new TWUtil();
        tWUtil.open((short[]) null);
        tWUtil.write(514, 8, z ? 1 : 0);
        tWUtil.close();
    }

    public String cX() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/cid"));
                try {
                    String line = bufferedReader.readLine();
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    return line;
                } catch (Exception e) {
                    if (bufferedReader == null) {
                        return "0";
                    }
                    bufferedReader.close();
                    return "0";
                } catch (Throwable th) {
                    bufferedReader2 = bufferedReader;
                    th = th;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                return "0";
            }
        } catch (Exception e3) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public String cY() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            try {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/0394"));
                try {
                    String line = bufferedReader2.readLine();
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return line;
                } catch (Exception e) {
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return null;
                } catch (Throwable th) {
                    bufferedReader = bufferedReader2;
                    th = th;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                return null;
            }
        } catch (Exception e3) {
            bufferedReader2 = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
    }

    public String cZ() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            if (!TextUtils.isEmpty(this.aE)) {
                return this.aE;
            }
            try {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/3305"));
                try {
                    this.aE = bufferedReader2.readLine();
                    String str = this.aE;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return str;
                } catch (Exception e) {
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return null;
                } catch (Throwable th) {
                    bufferedReader = bufferedReader2;
                    th = th;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                bufferedReader2 = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Exception e3) {
        }
    }

    public String da() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            if (!TextUtils.isEmpty(this.aF)) {
                return this.aF;
            }
            try {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/1d35"));
                try {
                    this.aF = bufferedReader2.readLine();
                    String str = this.aF;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return str;
                } catch (Exception e) {
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                    return null;
                } catch (Throwable th) {
                    bufferedReader = bufferedReader2;
                    th = th;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                bufferedReader2 = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
            }
        } catch (Exception e3) {
        }
    }
}
