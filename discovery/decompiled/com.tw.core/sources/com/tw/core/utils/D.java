package com.tw.core.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* JADX INFO: loaded from: classes.dex */
public class D implements Callable {
    private File aq;
    private long ar;
    public String as;
    private File at;
    private HttpURLConnection au;

    public D(String str, File file) {
        this.as = str;
        this.at = file;
    }

    private void ct() {
        this.aq = cu(String.format("%s_%s", this.at.getAbsolutePath(), "bak"));
    }

    private synchronized void cv() {
    }

    private void cw() {
        this.at.delete();
        this.aq.renameTo(this.at);
    }

    private void cx() throws ProtocolException {
        this.au.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
        this.au.setRequestMethod("GET");
        this.au.setRequestProperty("Connection", "Keep-Alive");
        this.au.setRequestProperty("Accept-Encoding", "identity");
        this.au.setConnectTimeout(10000);
    }

    private FileOutputStream cy(URL url) {
        String headerField = this.au.getHeaderField("Accept-Ranges");
        B.ci("Downloader", "Accept-Ranges %s", headerField);
        if (TextUtils.isEmpty(headerField) || !headerField.startsWith("bytes")) {
            this.aq.delete();
            ct();
        }
        return new FileOutputStream(this.aq, true);
    }

    @Override // java.util.concurrent.Callable
    public Void call() throws IOException {
        FileOutputStream fileOutputStreamCy;
        try {
            if (this.at.exists()) {
                B.ci("Downloader", "本地下载好的文件名称 %s", this.at.getName());
                return null;
            }
            ct();
            long length = this.aq.length();
            B.ci("Downloader", "本地下载好的长度 %s", Long.valueOf(length));
            String strReplaceAll = URLEncoder.encode(this.as, "UTF-8").replaceAll("\\+", "%20").replaceAll("%3A", ":").replaceAll("%2F", MqttTopic.TOPIC_LEVEL_SEPARATOR);
            if (C0065s.aa) {
                C0065s.bM("Downloader", "mUrl =  " + this.as + "\nencodeurl " + strReplaceAll);
            }
            URL url = new URL(strReplaceAll);
            this.au = (HttpURLConnection) url.openConnection();
            cx();
            if (length > 0) {
                this.au.setRequestProperty("Range", "bytes=" + length + "-");
            }
            this.au.connect();
            int responseCode = this.au.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                this.au.disconnect();
                return null;
            }
            this.ar = this.au.getContentLength();
            if (B.ao) {
                B.ci("Downloader", "本次下载长度 %s 文件总长度 %s", Long.valueOf(this.ar), Long.valueOf(this.ar + length));
            }
            fileOutputStreamCy = cy(url);
            try {
                InputStream inputStream = this.au.getInputStream();
                byte[] bArr = new byte[8192];
                long jCurrentTimeMillis = System.currentTimeMillis();
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    fileOutputStreamCy.write(bArr, 0, i);
                    length += (long) i;
                    if (System.currentTimeMillis() - jCurrentTimeMillis > 50) {
                        jCurrentTimeMillis = System.currentTimeMillis();
                    }
                    if (B.ao) {
                        B.ci("Downloader", "下载进度 " + length, new Object[0]);
                    }
                }
                this.au.disconnect();
                fileOutputStreamCy.close();
                this.au = null;
                cw();
                if (C0065s.aa) {
                    C0065s.bM("Downloader", "下载完成 " + this.at);
                }
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                if (this.au != null) {
                    this.au.disconnect();
                    this.au = null;
                }
                if (fileOutputStreamCy != null) {
                    fileOutputStreamCy.close();
                }
            }
            cv();
            return null;
        } catch (Exception e2) {
            e = e2;
            fileOutputStreamCy = null;
        }
    }

    public File cu(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }
}
