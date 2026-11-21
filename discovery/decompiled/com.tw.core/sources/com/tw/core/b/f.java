package com.tw.core.b;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private static int cA = -1;
    private static String cB;
    private static String cC;

    private f() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static String gA() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            if (!TextUtils.isEmpty(cC)) {
                return cC;
            }
            try {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/1d35"));
                try {
                    cC = bufferedReader2.readLine();
                    String str = cC;
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

    public static String gB() throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            if (!TextUtils.isEmpty(cB)) {
                return cB;
            }
            try {
                bufferedReader2 = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/3305"));
                try {
                    cB = bufferedReader2.readLine();
                    String str = cB;
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

    public static String gr() {
        return DoFunApplication.nt().ad();
    }

    public static String gs() throws Throwable {
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

    public static String gt(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return TextUtils.isEmpty(telephonyManager.getSimOperator()) ? "0" : telephonyManager.getSimOperator();
    }

    public static String gu(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return TextUtils.isEmpty(telephonyManager.getSubscriberId()) ? "0" : telephonyManager.getSubscriberId();
    }

    public static String gv(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return TextUtils.isEmpty(telephonyManager.getNetworkOperatorName()) ? "0" : telephonyManager.getNetworkOperatorName();
    }

    public static String gw(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return TextUtils.isEmpty(telephonyManager.getDeviceId()) ? "0" : telephonyManager.getDeviceId();
    }

    public static String gx(Context context) {
        String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
        return TextUtils.isEmpty(simSerialNumber) ? "0" : simSerialNumber;
    }

    public static String gy(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return TextUtils.isEmpty(telephonyManager.getNetworkCountryIso()) ? "0" : telephonyManager.getNetworkCountryIso();
    }

    public static String gz() throws Throwable {
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
}
