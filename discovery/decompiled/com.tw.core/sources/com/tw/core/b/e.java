package com.tw.core.b;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static final String cz = gq();
    private static DecimalFormat cy = new DecimalFormat("#0");
    private static DecimalFormat cx = new DecimalFormat("#0.#");

    public static long ge() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return (long) (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()) * 9.766E-4d * 9.766E-4d);
    }

    public static String gf() {
        return TextUtils.isEmpty(Build.VERSION.RELEASE) ? "0" : Build.VERSION.RELEASE;
    }

    public static String gg() {
        return DoFunApplication.nt().ac();
    }

    public static boolean gh() {
        String strAV = C0062p.aV(DoFunApplication.nu());
        B.ci("SystemInfoTools", "gmsVersion %s %s", cz, strAV);
        if (TextUtils.isEmpty(cz)) {
            return "zh_CN".equals(strAV);
        }
        return false;
    }

    public static long gi() {
        if (!gp()) {
            return -1L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (long) (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()) * 9.766E-4d * 9.766E-4d);
    }

    public static long gj() {
        if (!gp()) {
            return -1L;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static String[] gk() {
        String[] strArr = {"", ""};
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] strArrSplit = bufferedReader.readLine().split("\\s+");
            for (int i = 2; i < strArrSplit.length; i++) {
                strArr[0] = strArr[0] + strArrSplit[i] + " ";
            }
            strArr[1] = strArr[1] + bufferedReader.readLine().split("\\s+")[2];
            bufferedReader.close();
        } catch (IOException e) {
        }
        return strArr;
    }

    public static String gl() {
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder("/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq").start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            str = "N/A";
        }
        return str.trim();
    }

    public static boolean gm() {
        return TextUtils.equals("SP9832A", Build.MODEL);
    }

    public static boolean gn() {
        return TextUtils.equals("sp7731e_1h10_native", Build.MODEL);
    }

    public static boolean go(Context context) {
        String strBz = C0064r.bz(context, "key_gms_version", "-1");
        String strGq = gq();
        B.ci("SystemInfoTools", "saveGmsVersion %s gmsVersion %s", strBz, strGq);
        if ("-1".equals(strBz)) {
            return false;
        }
        if (TextUtils.isEmpty(strBz) && !TextUtils.isEmpty(strGq)) {
            return true;
        }
        if (TextUtils.isEmpty(strBz)) {
            return false;
        }
        return TextUtils.isEmpty(strGq);
    }

    public static boolean gp() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String gq() {
        return DoFunApplication.nt().Z();
    }
}
