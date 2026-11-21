package com.tw.service.xt;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class TWTW extends TWUtil {
    private static final String TAG = "TWTW";
    private static TWTW mTW = new TWTW();
    private static int mCount = 0;
    public static int mModel = -1;
    public static int mCType = 0;
    public static int mAhd = -1;
    public static int mNaviType = 0;
    public static int mVoiceType = 0;

    public TWTW() {
    }

    public TWTW(int i) {
        super(i);
    }

    public static int getApkVersion(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(0)) {
            if (str.equals(packageInfo.packageName)) {
                return packageInfo.versionCode;
            }
        }
        return -1;
    }

    public static boolean getBit(byte b, int i) {
        return (b & (1 << i)) != 0;
    }

    public static int getBitvalue(byte b, int i) {
        return (b & (1 << i)) == 0 ? 0 : 1;
    }

    public static int getBitvalue(int i, int i2) {
        return (((byte) i) & (1 << i2)) == 0 ? 0 : 1;
    }

    public static String getSystemVersion() throws Throwable {
        String str = "";
        try {
            String str2 = SystemProperties.get("ro.tw.version");
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
        TextUtils.isEmpty(str);
        return str;
    }

    public static String getSystemVersionSuffix() throws Throwable {
        String systemVersion = getSystemVersion();
        return TextUtils.isEmpty(systemVersion) ? "" : systemVersion.substring(systemVersion.lastIndexOf("_") + 1);
    }

    public static int getValue(byte b, int i) {
        return (b & (1 << i)) == 0 ? 0 : 1;
    }

    public static int getValue(byte b, int i, int i2) {
        return (b >> i) & ((byte) (255 >> (8 - i2))) & 255;
    }

    public static TWTW open() {
        int i = mCount;
        mCount = i + 1;
        if (i == 0) {
            if (mTW.open(new short[]{513, 515, 516, 523, 769, 1281, 2560, 2561, 2562, 2563, -24804}) != 0) {
                mCount--;
                return null;
            }
            mTW.start();
        }
        return mTW;
    }

    public void close() {
        int i = mCount;
        if (i > 0) {
            int i2 = i - 1;
            mCount = i2;
            if (i2 == 0) {
                stop();
                super.close();
            }
        }
    }

    public int ssWrite(int i) {
        return ssWrite(i, 0, 0);
    }

    public int ssWrite(int i, int i2) {
        return ssWrite(i, i2, 0);
    }

    public int ssWrite(int i, int i2, int i3) {
        return write(1281, i, 2, new byte[]{(byte) i2, (byte) i3});
    }
}
