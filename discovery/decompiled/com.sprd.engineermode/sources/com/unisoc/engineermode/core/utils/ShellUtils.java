package com.unisoc.engineermode.core.utils;

import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes4.dex */
public class ShellUtils {
    private static final String TAG = "ShellUtils";

    public static String execShellCmd(String cmd) throws Exception {
        StringBuilder ret = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            try {
                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                try {
                    BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
                    while (true) {
                        try {
                            String line = stdout.readLine();
                            String line2 = line;
                            if (line == null) {
                                String line3 = stderr.readLine();
                                line2 = line3;
                                if (line3 == null) {
                                    break;
                                }
                            }
                            if (!"".equals(line2)) {
                                ret.append(line2);
                                ret.append("\n");
                            }
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                $closeResource(th, stderr);
                                throw th2;
                            }
                        }
                    }
                    $closeResource(null, stderr);
                    $closeResource(null, stdout);
                    Log.d(TAG, cmd + ":" + ret.toString());
                    return ret.toString();
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        $closeResource(th3, stdout);
                        throw th4;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) throws Exception {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    public static boolean writeToFile(String fileName, String str) {
        File file = new File(fileName);
        if (!file.exists()) {
            Log.d(TAG, "the file is not exists");
            return false;
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            try {
                byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
                bos.write(bytes);
                $closeResource(null, bos);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    $closeResource(th, bos);
                    throw th2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Exception: " + e.toString());
            return false;
        }
    }
}
