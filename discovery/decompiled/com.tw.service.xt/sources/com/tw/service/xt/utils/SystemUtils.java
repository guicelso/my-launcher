package com.tw.service.xt.utils;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class SystemUtils {
    private static final String TAG = "SystemUtils";

    public static String getApplicationVersion() throws Throwable {
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
        } catch (Exception e) {
            Log.e(TAG, "getApplicationVersion Exception= " + Log.getStackTraceString(e));
        }
        return TextUtils.isEmpty(str) ? str : str.substring(0, str.indexOf("_"));
    }

    public static String[] getBufferedReaderAppName(String str) throws Throwable {
        String[] strArr = new String[2];
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/" + str + "_name"));
                try {
                    strArr[0] = bufferedReader2.readLine();
                    strArr[1] = bufferedReader2.readLine();
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
            } catch (Exception e) {
                Log.e(TAG, "getCurrentPackageName Exception= " + Log.getStackTraceString(e));
            }
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
        return strArr;
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
        } catch (Exception e) {
            Log.e(TAG, "getSystemVersion Exception= " + Log.getStackTraceString(e));
        }
        return TextUtils.isEmpty(str) ? str : str.substring(str.lastIndexOf("_") + 1);
    }
}
