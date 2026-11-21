package com.tw.reverse;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class TWTW extends TWUtil {
    private static final String TAG = "TWTW";
    private static TWTW mTW = new TWTW(16);
    private static int mCount = 0;
    public static int mModel = -1;
    public static int mCType = 0;

    public TWTW() {
    }

    public TWTW(int clazz) {
        super(clazz);
    }

    public static TWTW open() {
        int i = mCount;
        mCount = i + 1;
        if (i == 0) {
            if (mTW.open(new short[]{267, 528, 258, 516, 260, 262, 263, 266, 274, 513, 514, 515, 523, 524, 769, 770, 772, 1028, 1281, 1285, 1286, 1287, 1288, 1289, 1294, -31482, -31481, -25088, -25071, -24816, -24805, -24804, -24803}) != 0) {
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

    public int ssWrite(int what, int arg1, int arg2) {
        return write(1281, what, 2, new byte[]{(byte) arg1, (byte) arg2});
    }

    public int ssWrite(int what) {
        return ssWrite(what, 0, 0);
    }

    public int ssWrite(int what, int arg1) {
        return ssWrite(what, arg1, 0);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0047 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:29:0x0048  */
    public static String getSystemVersion() {
        String systemVersion = "";
        try {
            String v1 = SystemProperties.get("ro.tw.version");
            if (v1 != null && v1.length() > 0) {
                systemVersion = v1;
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("/system/etc/version"));
                String v2 = br.readLine();
                if (v2 != null && v2.length() > 0) {
                    systemVersion = v2;
                }
            } catch (Exception e) {
                if (br != null) {
                }
                if (TextUtils.isEmpty(systemVersion)) {
                    return systemVersion;
                }
                int index = systemVersion.lastIndexOf("_") + 1;
                return systemVersion.substring(index);
            } catch (Throwable th) {
                if (br != null) {
                    br.close();
                }
                throw th;
            }
            br.close();
        } catch (Exception e2) {
        }
        if (TextUtils.isEmpty(systemVersion)) {
            return systemVersion;
        }
        int index2 = systemVersion.lastIndexOf("_") + 1;
        return systemVersion.substring(index2);
    }
}
