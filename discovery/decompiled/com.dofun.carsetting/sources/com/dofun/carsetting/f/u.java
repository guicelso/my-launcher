package com.dofun.carsetting.f;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: compiled from: TWSetting.java */
/* JADX INFO: loaded from: classes.dex */
public class u extends TWUtil {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static u f232a = new u(17);
    private static int b = 0;

    public u() {
    }

    public u(int i) {
        super(i);
    }

    public static u a() {
        int i = b;
        b = i + 1;
        if (i == 0) {
            if (f232a.open(new short[]{257, 258, 259, 260, 262, 264, 265, 266, 267, 272, 274, 513, 516, 1025, 1026, 1028, 1029, 1030, 1539, 1541, 276, -25057}) != 0) {
                b--;
                return null;
            }
            f232a.start();
        }
        return f232a;
    }

    public void close() {
        if (b > 0) {
            int i = b - 1;
            b = i;
            if (i == 0) {
                stop();
                super.close();
            }
        }
    }

    public static int b() {
        u uVarA = a();
        int iWrite = uVarA.write(65521);
        uVarA.close();
        return iWrite;
    }

    public static String c() throws Throwable {
        String str = "";
        try {
            String str2 = SystemProperties.get("ro.systemtw.version");
            if (TextUtils.isEmpty(str2)) {
                str2 = SystemProperties.get("ro.tw.version");
            }
            if (str2 != null && str2.length() > 0) {
                str = str2;
            }
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/system/etc/version"));
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null && line.length() > 0) {
                        str = line;
                    }
                    bufferedReader2.close();
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
        }
        return TextUtils.isEmpty(str) ? str : str.substring(str.lastIndexOf("_") + 1);
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString + " ");
        }
        return sb.toString();
    }
}
