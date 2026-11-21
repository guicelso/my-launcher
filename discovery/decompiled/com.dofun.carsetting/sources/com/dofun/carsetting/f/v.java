package com.dofun.carsetting.f;

import android.tw.john.TWUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: ToolsUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class v {
    public static String a() throws Throwable {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/sys/class/block/mmcblk0/device/cid"));
                try {
                    String line = bufferedReader2.readLine();
                    bufferedReader2.close();
                    return line;
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader == null) {
                        return "0";
                    }
                    bufferedReader.close();
                    return "0";
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                return "0";
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:37:0x005e A[Catch: Exception -> 0x006a, TryCatch #3 {Exception -> 0x006a, blocks: (B:13:0x002f, B:37:0x005e, B:39:0x0063, B:42:0x006c, B:30:0x004d, B:32:0x0052), top: B:45:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0063 A[Catch: Exception -> 0x006a, TryCatch #3 {Exception -> 0x006a, blocks: (B:13:0x002f, B:37:0x005e, B:39:0x0063, B:42:0x006c, B:30:0x004d, B:32:0x0052), top: B:45:0x0002 }] */
    public static boolean a(ZipFile zipFile, String str, String str2) throws Throwable {
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        fileOutputStream2 = null;
        InputStream inputStream2 = null;
        try {
            try {
                inputStream = zipFile.getInputStream(zipFile.getEntry(str));
                try {
                    fileOutputStream = new FileOutputStream(str2 + str);
                    try {
                        byte[] bArr = new byte[1048576];
                        while (true) {
                            int i = inputStream.read(bArr);
                            if (i <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, i);
                        }
                        if (inputStream != null) {
                            inputStream.close();
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                        return true;
                    } catch (Exception e) {
                        e = e;
                        inputStream2 = inputStream;
                        try {
                            e.printStackTrace();
                            if (inputStream2 != null) {
                                inputStream2.close();
                                if (fileOutputStream != null) {
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                }
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            inputStream = inputStream2;
                            fileOutputStream2 = fileOutputStream;
                            if (inputStream != null) {
                                inputStream.close();
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.flush();
                                    fileOutputStream2.close();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream2 = fileOutputStream;
                        if (inputStream != null) {
                            inputStream.close();
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.flush();
                                fileOutputStream2.close();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    if (inputStream != null) {
                        inputStream.close();
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Exception e4) {
            e = e4;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
        }
    }

    public static void a(TWUtil tWUtil, boolean z) {
        tWUtil.write(40730, 1, 0, "mount -o remount,rw /vendor");
        tWUtil.write(40730, 1, 0, "mount -o remount,rw /");
        if (z) {
            tWUtil.write(40730, 1, 0, "cp -rf /system/etc/virtual_cam_cfg2.json /vendor/etc/virtual_cam_cfg.json");
            tWUtil.write(40730, 3, 0, "chmod  -R 777 /vendor/etc/virtual_cam_cfg.json");
        } else {
            tWUtil.write(40730, 1, 0, "cp -rf /system/etc/virtual_cam_cfg4.json /vendor/etc/virtual_cam_cfg.json");
            tWUtil.write(40730, 3, 0, "chmod  -R 777 /vendor/etc/virtual_cam_cfg.json");
        }
    }

    public static void a(TWUtil tWUtil, int i) {
        tWUtil.write(40730, 1, 0, "mount -o remount,rw /system_tw");
        File file = new File("/system_tw/etc/virtual_cam_cfg.json");
        File file2 = new File("/system_tw/etc/virtual_cam_cfg_close.json");
        if (i == 3 && file2.exists()) {
            tWUtil.write(40730, 1, 0, "mv /system_tw/etc/virtual_cam_cfg_close.json /system_tw/etc/virtual_cam_cfg.json");
        } else if (file.exists() && i != 3) {
            tWUtil.write(40730, 1, 0, "mv /system_tw/etc/virtual_cam_cfg.json /system_tw/etc/virtual_cam_cfg_close.json");
        }
        tWUtil.write(40730, 1, 0, "mount -o remount,ro /system_tw");
        tWUtil.write(40730, 1, 0, "sync");
    }
}
