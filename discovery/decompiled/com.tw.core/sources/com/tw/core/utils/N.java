package com.tw.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* JADX INFO: loaded from: classes.dex */
public class N {
    private static final String aM = N.class.getSimpleName();

    public static File dv(String str, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            B.cj(aM, "文件不存在", new Object[0]);
            return null;
        }
        File file2 = new File(str2);
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            B.ci(aM, "开始压缩", new Object[0]);
            ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(file2), new CRC32()));
            dy(zipOutputStream, "DFLog", file);
            zipOutputStream.flush();
            zipOutputStream.close();
            B.ci(aM, "结束压缩 耗时 %s", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
            return file2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dw(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes());
        gZIPOutputStream.close();
        return byteArrayOutputStream.toString("ISO-8859-1");
    }

    public static String dx(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
        byte[] bArr = new byte[256];
        while (true) {
            int i = gZIPInputStream.read(bArr);
            if (i < 0) {
                return byteArrayOutputStream.toString();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }

    private static void dy(ZipOutputStream zipOutputStream, String str, File file) {
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            String str2 = str + MqttTopic.TOPIC_LEVEL_SEPARATOR;
            try {
                zipOutputStream.putNextEntry(new ZipEntry(str2));
                for (File file2 : fileArrListFiles) {
                    dy(zipOutputStream, str2 + file2.getName(), file2);
                }
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            zipOutputStream.putNextEntry(new ZipEntry(str));
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    fileInputStream.close();
                    return;
                }
                zipOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
