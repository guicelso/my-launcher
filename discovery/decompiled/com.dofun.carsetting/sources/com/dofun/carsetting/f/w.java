package com.dofun.carsetting.f;

import android.os.FileUtils;
import android.tw.john.TWUtil;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: compiled from: TwDataConfigUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class w {
    public static int a(String str) throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                if (!new File("/twdataconfig").exists()) {
                    return -2;
                }
                File file = new File("/twdataconfig/" + str);
                if (!file.exists()) {
                    return -1;
                }
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                try {
                    int i = Integer.parseInt(bufferedReader2.readLine());
                    try {
                        bufferedReader2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return i;
                } catch (Exception e2) {
                    bufferedReader = bufferedReader2;
                    e = e2;
                    Log.d("TwDataConfigUtils", Log.getStackTraceString(e));
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return -1;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e4) {
            e = e4;
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
        throw th;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0068 A[Catch: Exception -> 0x0092, TRY_ENTER, TryCatch #4 {Exception -> 0x0092, blocks: (B:10:0x004f, B:23:0x006b, B:26:0x008e, B:29:0x0094, B:22:0x0068), top: B:35:0x0002 }] */
    public static boolean a(TWUtil tWUtil, String str, int i) throws Throwable {
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    if (!new File("/twdataconfig").exists()) {
                        return false;
                    }
                    File file = new File("/twdataconfig/" + str);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file));
                    try {
                        bufferedWriter2.write(i + "\n");
                        bufferedWriter2.flush();
                        bufferedWriter2.close();
                    } catch (Exception e) {
                        e = e;
                        bufferedWriter = bufferedWriter2;
                        Log.d("TwDataConfigUtils", Log.getStackTraceString(e));
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        throw th;
                    }
                    FileUtils.setPermissions("/twdataconfig/" + str, 438, -1, -1);
                    tWUtil.write(40730, 1, 0, "sync");
                    return true;
                } catch (Exception e2) {
                    e = e2;
                }
                FileUtils.setPermissions("/twdataconfig/" + str, 438, -1, -1);
                tWUtil.write(40730, 1, 0, "sync");
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
            Log.d("TwDataConfigUtils", Log.getStackTraceString(e));
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (Exception e3) {
            Log.d("TwDataConfigUtils", Log.getStackTraceString(e3));
            return false;
        }
    }
}
