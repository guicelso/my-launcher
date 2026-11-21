package com.tw.core.utils;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: renamed from: com.tw.core.utils.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0056j {
    private static int[] I = new int[256];
    private static int J;

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ (-306674912) : i2 >>> 1;
            }
            I[i] = i2;
        }
        J = -1;
    }

    public static String ah(File file) throws Throwable {
        FileInputStream fileInputStream;
        int i;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[10240];
                do {
                    i = fileInputStream.read(bArr);
                    if (i > 0) {
                        messageDigest.update(bArr, 0, i);
                    }
                } while (i >= 10240);
                String strAk = ak(ai(messageDigest.digest()), 0);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                    }
                }
                return strAk;
            } catch (FileNotFoundException e2) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                    }
                }
                return null;
            } catch (IOException e4) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                    }
                }
                return null;
            } catch (NoSuchAlgorithmException e6) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e7) {
                    }
                }
                return null;
            } catch (Throwable th) {
                fileInputStream2 = fileInputStream;
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e8) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e9) {
            fileInputStream = null;
        } catch (IOException e10) {
            fileInputStream = null;
        } catch (NoSuchAlgorithmException e11) {
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String ai(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String aj(String str) {
        File file = new File(str);
        if (file.exists()) {
            return ah(file);
        }
        Log.e("VerifyFile", "文件不存在！" + str);
        return null;
    }

    public static String ak(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            switch (i) {
                case MqttConnectOptions.MQTT_VERSION_DEFAULT /* 0 */:
                    return str.toLowerCase();
                case Logger.SEVERE /* 1 */:
                    return str.toUpperCase();
                default:
                    return str;
            }
        } catch (Exception e) {
            return str;
        }
    }
}
