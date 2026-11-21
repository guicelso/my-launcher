package com.tw.core.utils;

import android.os.Looper;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tw.core.utils.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0058l {
    private static final String K = C0058l.class.getSimpleName();
    private static C0058l L;

    private C0058l() {
    }

    public static String aA(Map map, Map map2, String str, InterfaceC0059m interfaceC0059m) {
        return ar(map, map2, str, interfaceC0059m, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:102:0x016d A[EDGE_INSN: B:102:0x016d->B:53:0x016d BREAK  A[LOOP:1: B:97:0x0139->B:39:0x013f], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:10:0x009d A[Catch: Exception -> 0x00c1, all -> 0x0157, TryCatch #10 {Exception -> 0x00c1, all -> 0x0157, blocks: (B:5:0x0012, B:7:0x001c, B:8:0x0048, B:10:0x009d, B:11:0x00a5, B:13:0x00ab, B:29:0x00fb, B:32:0x011c, B:34:0x0127, B:71:0x01e1, B:43:0x0153, B:44:0x0156), top: B:96:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:13:0x00ab A[Catch: Exception -> 0x00c1, all -> 0x0157, LOOP:0: B:11:0x00a5->B:13:0x00ab, LOOP_END, TRY_LEAVE, TryCatch #10 {Exception -> 0x00c1, all -> 0x0157, blocks: (B:5:0x0012, B:7:0x001c, B:8:0x0048, B:10:0x009d, B:11:0x00a5, B:13:0x00ab, B:29:0x00fb, B:32:0x011c, B:34:0x0127, B:71:0x01e1, B:43:0x0153, B:44:0x0156), top: B:96:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x00ce A[Catch: all -> 0x0211, TRY_LEAVE, TryCatch #0 {all -> 0x0211, blocks: (B:17:0x00c6, B:19:0x00ce), top: B:88:0x00c6 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:24:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:34:0x0127 A[Catch: Exception -> 0x00c1, all -> 0x0157, TRY_LEAVE, TryCatch #10 {Exception -> 0x00c1, all -> 0x0157, blocks: (B:5:0x0012, B:7:0x001c, B:8:0x0048, B:10:0x009d, B:11:0x00a5, B:13:0x00ab, B:29:0x00fb, B:32:0x011c, B:34:0x0127, B:71:0x01e1, B:43:0x0153, B:44:0x0156), top: B:96:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x013f A[Catch: Exception -> 0x01d8, all -> 0x020a, LOOP:1: B:97:0x0139->B:39:0x013f, LOOP_END, TRY_LEAVE, TryCatch #9 {Exception -> 0x01d8, all -> 0x020a, blocks: (B:37:0x0139, B:39:0x013f, B:54:0x016f, B:57:0x01b6, B:58:0x01b9, B:66:0x01d4), top: B:97:0x0139 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x016f A[Catch: Exception -> 0x01d8, all -> 0x020a, TRY_ENTER, TryCatch #9 {Exception -> 0x01d8, all -> 0x020a, blocks: (B:37:0x0139, B:39:0x013f, B:54:0x016f, B:57:0x01b6, B:58:0x01b9, B:66:0x01d4), top: B:97:0x0139 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:64:0x01cd  */
    /* JADX WARN: Code duplicated, block: B:65:0x01d2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:66:0x01d4 A[Catch: Exception -> 0x01d8, all -> 0x020a, TRY_ENTER, TRY_LEAVE, TryCatch #9 {Exception -> 0x01d8, all -> 0x020a, blocks: (B:37:0x0139, B:39:0x013f, B:54:0x016f, B:57:0x01b6, B:58:0x01b9, B:66:0x01d4), top: B:97:0x0139 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x01df A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:71:0x01e1 A[Catch: Exception -> 0x00c1, all -> 0x0157, TRY_ENTER, TRY_LEAVE, TryCatch #10 {Exception -> 0x00c1, all -> 0x0157, blocks: (B:5:0x0012, B:7:0x001c, B:8:0x0048, B:10:0x009d, B:11:0x00a5, B:13:0x00ab, B:29:0x00fb, B:32:0x011c, B:34:0x0127, B:71:0x01e1, B:43:0x0153, B:44:0x0156), top: B:96:0x0012 }] */
    /* JADX WARN: Code duplicated, block: B:87:0x0222  */
    public static String aB(String str, String str2, Map map, String str3, InterfaceC0059m interfaceC0059m, boolean z) throws Throwable {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        DataOutputStream dataOutputStream;
        InputStream inputStream2;
        String str4;
        BufferedReader bufferedReader;
        String str5;
        String line;
        String strAN;
        BufferedReader bufferedReader2 = null;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str3).openConnection();
            if (!z) {
                B.ci(K, "address %s params %s", str3, str2);
                byte[] bytes = str2.getBytes();
                httpURLConnection2.setConnectTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setRequestProperty("Content-Type", str);
                httpURLConnection2.setRequestProperty("Origin-Flag", "car");
                httpURLConnection2.setRequestProperty("Charset", "UTF-8");
                httpURLConnection2.setRequestProperty("Content-Length", bytes.length + "");
                if (map != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        httpURLConnection2.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setDoOutput(true);
                dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                dataOutputStream.write(str2.getBytes("UTF-8"));
                dataOutputStream.flush();
                C0062p.bb(dataOutputStream);
                if (httpURLConnection2.getResponseCode() == 200) {
                    inputStream = httpURLConnection2.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    str5 = "";
                    while (true) {
                        line = bufferedReader.readLine();
                        if (line != null) {
                            break;
                            break;
                        }
                        str5 = str5 + line;
                    }
                    if (z) {
                        String headerField = httpURLConnection2.getHeaderField("set-cookie");
                        B.ci(K, "cookie %s", headerField);
                        String strBZ = C0070x.bZ(headerField.replace("RESPONSEARS=", ""));
                        B.ci(K, "aesDecodeValue %s", strBZ);
                        strAN = C0060n.aN(str5, strBZ);
                        B.ci(K, "realData %s", strAN);
                        if (strAN != null) {
                            interfaceC0059m.aK(strAN);
                        }
                    } else if (interfaceC0059m != null) {
                        interfaceC0059m.aK(str5);
                    }
                    inputStream.close();
                    inputStream2 = inputStream;
                    str4 = str5;
                    bufferedReader2 = bufferedReader;
                } else if (interfaceC0059m != null) {
                    interfaceC0059m.aI("连接不成功，错误码=" + httpURLConnection2.getResponseCode());
                    inputStream2 = null;
                    str4 = "";
                } else {
                    inputStream2 = null;
                    str4 = "";
                }
                C0062p.bb(inputStream2);
                C0062p.bb(bufferedReader2);
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                if (interfaceC0059m != null) {
                    return str4;
                }
                interfaceC0059m.aJ();
                return str4;
            }
            try {
                String strAM = C0060n.aM(str2, C0060n.aL());
                if (strAM != null) {
                    httpURLConnection2.setRequestProperty("en-key", "2");
                    httpURLConnection2.setRequestProperty("Cookie", "ARS=" + C0070x.ca(C0060n.aL()));
                    str2 = strAM;
                } else {
                    z = false;
                }
                B.ci(K, "address %s params %s", str3, str2);
                byte[] bytes2 = str2.getBytes();
                httpURLConnection2.setConnectTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setRequestProperty("Content-Type", str);
                httpURLConnection2.setRequestProperty("Origin-Flag", "car");
                httpURLConnection2.setRequestProperty("Charset", "UTF-8");
                httpURLConnection2.setRequestProperty("Content-Length", bytes2.length + "");
                if (map != null) {
                    while (r5.hasNext()) {
                        httpURLConnection2.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setDoOutput(true);
                try {
                    dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                    try {
                        dataOutputStream.write(str2.getBytes("UTF-8"));
                        dataOutputStream.flush();
                        C0062p.bb(dataOutputStream);
                        if (httpURLConnection2.getResponseCode() == 200) {
                            inputStream = httpURLConnection2.getInputStream();
                            try {
                                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                str5 = "";
                                while (true) {
                                    try {
                                        line = bufferedReader.readLine();
                                        if (line != null) {
                                            break;
                                        }
                                        str5 = str5 + line;
                                    } catch (Exception e) {
                                        bufferedReader2 = bufferedReader;
                                        httpURLConnection = httpURLConnection2;
                                        e = e;
                                        try {
                                            e.printStackTrace();
                                            if (interfaceC0059m != null) {
                                                interfaceC0059m.aI("请求错误=" + e);
                                            }
                                            C0062p.bb(inputStream);
                                            C0062p.bb(bufferedReader2);
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            if (interfaceC0059m != null) {
                                                interfaceC0059m.aJ();
                                            }
                                            return "";
                                        } catch (Throwable th) {
                                            th = th;
                                        }
                                    } catch (Throwable th2) {
                                        bufferedReader2 = bufferedReader;
                                        httpURLConnection = httpURLConnection2;
                                        th = th2;
                                    }
                                }
                                if (z) {
                                    String headerField2 = httpURLConnection2.getHeaderField("set-cookie");
                                    B.ci(K, "cookie %s", headerField2);
                                    String strBZ2 = C0070x.bZ(headerField2.replace("RESPONSEARS=", ""));
                                    B.ci(K, "aesDecodeValue %s", strBZ2);
                                    strAN = C0060n.aN(str5, strBZ2);
                                    B.ci(K, "realData %s", strAN);
                                    if (strAN != null && interfaceC0059m != null) {
                                        interfaceC0059m.aK(strAN);
                                    }
                                } else if (interfaceC0059m != null) {
                                    interfaceC0059m.aK(str5);
                                }
                                inputStream.close();
                                inputStream2 = inputStream;
                                str4 = str5;
                                bufferedReader2 = bufferedReader;
                            } catch (Exception e2) {
                                httpURLConnection = httpURLConnection2;
                                e = e2;
                            } catch (Throwable th3) {
                                httpURLConnection = httpURLConnection2;
                                th = th3;
                            }
                        } else if (interfaceC0059m != null) {
                            interfaceC0059m.aI("连接不成功，错误码=" + httpURLConnection2.getResponseCode());
                            inputStream2 = null;
                            str4 = "";
                        } else {
                            inputStream2 = null;
                            str4 = "";
                        }
                        C0062p.bb(inputStream2);
                        C0062p.bb(bufferedReader2);
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        if (interfaceC0059m != null) {
                            return str4;
                        }
                        interfaceC0059m.aJ();
                        return str4;
                    } catch (Throwable th4) {
                        th = th4;
                        C0062p.bb(dataOutputStream);
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    dataOutputStream = null;
                }
            } catch (Exception e3) {
                inputStream = null;
                httpURLConnection = httpURLConnection2;
                e = e3;
                e.printStackTrace();
                if (interfaceC0059m != null) {
                    interfaceC0059m.aI("请求错误=" + e);
                }
                C0062p.bb(inputStream);
                C0062p.bb(bufferedReader2);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (interfaceC0059m != null) {
                    interfaceC0059m.aJ();
                }
                return "";
            } catch (Throwable th6) {
                inputStream = null;
                httpURLConnection = httpURLConnection2;
                th = th6;
            }
        } catch (Exception e4) {
            e = e4;
            inputStream = null;
            httpURLConnection = null;
        } catch (Throwable th7) {
            th = th7;
            inputStream = null;
            httpURLConnection = null;
        }
        C0062p.bb(inputStream);
        C0062p.bb(bufferedReader2);
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        if (interfaceC0059m != null) {
            interfaceC0059m.aJ();
        }
        throw th;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String aC(String str, Map map, Map map2, String str2, InterfaceC0059m interfaceC0059m, boolean z) {
        return aB(str, "application/json".equals(str) ? new JSONObject(map).toString() : ax(map), map2, str2, interfaceC0059m, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v59 */
    /* JADX WARN: Type inference failed for: r2v60 */
    /* JADX WARN: Type inference failed for: r2v7 */
    public static void aD(String str, Map map, Map map2, InterfaceC0059m interfaceC0059m) throws Throwable {
        String str2;
        String string = UUID.randomUUID().toString();
        ?? r2 = 0;
        HttpURLConnection httpURLConnection = null;
        try {
            try {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection2.setReadTimeout(300000);
                    httpURLConnection2.setChunkedStreamingMode(0);
                    httpURLConnection2.setConnectTimeout(300000);
                    httpURLConnection2.setDoInput(true);
                    httpURLConnection2.setDoOutput(true);
                    httpURLConnection2.setUseCaches(false);
                    httpURLConnection2.setRequestMethod("POST");
                    httpURLConnection2.setRequestProperty("connection", "keep-alive");
                    httpURLConnection2.setRequestProperty("Charsert", "UTF-8");
                    httpURLConnection2.setRequestProperty("Origin-Flag", "car");
                    httpURLConnection2.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + string);
                    StringBuilder sb = new StringBuilder();
                    if (map != null) {
                        for (Map.Entry entry : map.entrySet()) {
                            sb.append("--");
                            sb.append(string);
                            sb.append("\r\n");
                            sb.append("Content-Disposition: form-data; name=\"").append((String) entry.getKey()).append("\"").append("\r\n");
                            sb.append("Content-Type: text/plain; charset=").append("UTF-8").append("\r\n");
                            sb.append("Content-Transfer-Encoding: 8bit").append("\r\n");
                            sb.append("\r\n");
                            sb.append((String) entry.getValue());
                            sb.append("\r\n");
                        }
                    }
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                    dataOutputStream.write(sb.toString().getBytes());
                    if (map2 != null) {
                        for (Map.Entry entry2 : map2.entrySet()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("--");
                            sb2.append(string);
                            sb2.append("\r\n");
                            sb2.append("Content-Disposition: form-data; name=\"").append((String) entry2.getKey()).append("\"; filename=\"").append(((File) entry2.getValue()).getName()).append("\"").append("\r\n");
                            sb2.append("Content-Type: application/octet-stream; charset=").append("UTF-8").append("\r\n");
                            sb2.append("\r\n");
                            dataOutputStream.write(sb2.toString().getBytes());
                            FileInputStream fileInputStream = new FileInputStream((File) entry2.getValue());
                            byte[] bArr = new byte[1024];
                            int i = 0;
                            while (true) {
                                int i2 = fileInputStream.read(bArr);
                                if (i2 != -1) {
                                    dataOutputStream.write(bArr, 0, i2);
                                    i += i2;
                                    Log.d(K, "当前进度 " + i);
                                }
                            }
                            B.ci(K, "写流完成", new Object[0]);
                            fileInputStream.close();
                            dataOutputStream.write("\r\n".getBytes());
                        }
                    }
                    dataOutputStream.write(("--" + string + "--\r\n").getBytes());
                    dataOutputStream.flush();
                    int responseCode = httpURLConnection2.getResponseCode();
                    B.ci(K, "响应码 %s", Integer.valueOf(responseCode));
                    InputStream inputStream = httpURLConnection2.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb3 = new StringBuilder();
                    if (responseCode == 200 || responseCode == 204) {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            } else {
                                sb3.append(line);
                            }
                        }
                        String string2 = sb3.toString();
                        interfaceC0059m.aK(string2);
                        str2 = string2;
                    } else {
                        String str3 = httpURLConnection2.getResponseCode() + "错误";
                        interfaceC0059m.aI(str3);
                        str2 = str3;
                    }
                    dataOutputStream.close();
                    inputStream.close();
                    bufferedReader.close();
                    inputStreamReader.close();
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    interfaceC0059m.aJ();
                    r2 = str2;
                } catch (Exception e) {
                    httpURLConnection = httpURLConnection2;
                    e = e;
                    e.printStackTrace();
                    interfaceC0059m.aI("上传失败");
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    interfaceC0059m.aJ();
                    r2 = httpURLConnection;
                } catch (Throwable th) {
                    r2 = httpURLConnection2;
                    th = th;
                    if (r2 != 0) {
                        r2.disconnect();
                    }
                    interfaceC0059m.aJ();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static String an(Map map, String str, InterfaceC0059m interfaceC0059m) {
        return ar(map, null, str, interfaceC0059m, false);
    }

    public static void ao(String str, Map map, Map map2, InterfaceC0059m interfaceC0059m) throws Throwable {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            C0048b.p().execute(new aa(str, map, map2, interfaceC0059m));
        } else {
            aD(str, map, map2, interfaceC0059m);
        }
    }

    public static String ap(Map map, String str, InterfaceC0059m interfaceC0059m) {
        if (!C0048b.r()) {
            return aB("application/json", new JSONObject(map).toString(), null, str, interfaceC0059m, false);
        }
        C0048b.p().execute(new Y(map, str, interfaceC0059m));
        return null;
    }

    public static void aq() {
        try {
            TrustManager[] trustManagerArr = {new ab()};
            SSLContext sSLContext = SSLContext.getInstance(SSLSocketFactoryFactory.DEFAULT_PROTOCOL);
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new ac());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ar(Map map, Map map2, String str, InterfaceC0059m interfaceC0059m, boolean z) {
        if (!C0048b.r()) {
            return ay().aC("application/json", map, map2, str, interfaceC0059m, z);
        }
        C0048b.p().execute(new Z(map, map2, str, interfaceC0059m, z));
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0070 A[Catch: IOException -> 0x009e, TryCatch #8 {IOException -> 0x009e, blocks: (B:17:0x006b, B:19:0x0070, B:21:0x0075), top: B:67:0x006b }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0075 A[Catch: IOException -> 0x009e, TRY_LEAVE, TryCatch #8 {IOException -> 0x009e, blocks: (B:17:0x006b, B:19:0x0070, B:21:0x0075), top: B:67:0x006b }] */
    /* JADX WARN: Code duplicated, block: B:29:0x008d A[Catch: IOException -> 0x0097, TryCatch #0 {IOException -> 0x0097, blocks: (B:27:0x0088, B:29:0x008d, B:31:0x0092), top: B:63:0x0088 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0092 A[Catch: IOException -> 0x0097, TRY_LEAVE, TryCatch #0 {IOException -> 0x0097, blocks: (B:27:0x0088, B:29:0x008d, B:31:0x0092), top: B:63:0x0088 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x00ad A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:42:0x00af A[Catch: IOException -> 0x00b8, TryCatch #2 {IOException -> 0x00b8, blocks: (B:40:0x00aa, B:42:0x00af, B:44:0x00b4), top: B:65:0x00aa }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00b4 A[Catch: IOException -> 0x00b8, TRY_LEAVE, TryCatch #2 {IOException -> 0x00b8, blocks: (B:40:0x00aa, B:42:0x00af, B:44:0x00b4), top: B:65:0x00aa }] */
    /* JADX WARN: Code duplicated, block: B:65:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private boolean as(String str, String str2, String str3) throws Throwable {
        InputStream inputStream;
        HttpURLConnection httpURLConnection;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        boolean z;
        FileOutputStream fileOutputStream3 = null;
        fileOutputStream3 = null;
        inputStream = null;
        fileOutputStream3 = null;
        InputStream inputStream2 = null;
        byte[] bArr = new byte[1024];
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setConnectTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                httpURLConnection2.setReadTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                httpURLConnection2.setRequestMethod("GET");
                C0065s.bK(K, "响应码 " + httpURLConnection2.getResponseCode());
                if (httpURLConnection2.getResponseCode() == 200) {
                    inputStream = httpURLConnection2.getInputStream();
                    try {
                        fileOutputStream2 = new FileOutputStream(E.cG(str2, str3));
                        while (true) {
                            try {
                                int i = inputStream.read(bArr);
                                if (i <= 0) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, i);
                            } catch (Exception e) {
                                inputStream2 = inputStream;
                                httpURLConnection = httpURLConnection2;
                                e = e;
                                fileOutputStream = fileOutputStream2;
                                try {
                                    C0065s.bL(K, e);
                                    if (httpURLConnection != null) {
                                        try {
                                            httpURLConnection.disconnect();
                                            if (inputStream2 != null) {
                                                inputStream2.close();
                                            }
                                            if (fileOutputStream != null) {
                                                fileOutputStream.close();
                                            }
                                        } catch (IOException e2) {
                                            C0065s.bL(K, e2);
                                            return false;
                                        }
                                    } else {
                                        if (inputStream2 != null) {
                                            inputStream2.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                    }
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    inputStream = inputStream2;
                                    fileOutputStream3 = fileOutputStream;
                                    if (httpURLConnection != null) {
                                        try {
                                            httpURLConnection.disconnect();
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            if (fileOutputStream3 != null) {
                                                fileOutputStream3.close();
                                            }
                                        } catch (IOException e3) {
                                            C0065s.bL(K, e3);
                                            throw th;
                                        }
                                    } else {
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (fileOutputStream3 != null) {
                                            fileOutputStream3.close();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                fileOutputStream3 = fileOutputStream2;
                                httpURLConnection = httpURLConnection2;
                                th = th2;
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (fileOutputStream3 != null) {
                                        fileOutputStream3.close();
                                    }
                                } else {
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (fileOutputStream3 != null) {
                                        fileOutputStream3.close();
                                    }
                                }
                                throw th;
                            }
                        }
                        fileOutputStream2.flush();
                        C0065s.bK(K, "下載成功");
                        z = true;
                    } catch (Exception e4) {
                        httpURLConnection = httpURLConnection2;
                        e = e4;
                        fileOutputStream = null;
                        inputStream2 = inputStream;
                    } catch (Throwable th3) {
                        httpURLConnection = httpURLConnection2;
                        th = th3;
                    }
                } else {
                    fileOutputStream2 = null;
                    inputStream = null;
                    z = false;
                }
                if (httpURLConnection2 != null) {
                    try {
                        httpURLConnection2.disconnect();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                    } catch (IOException e5) {
                        C0065s.bL(K, e5);
                    }
                } else {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                }
                return z;
            } catch (Exception e6) {
                httpURLConnection = httpURLConnection2;
                e = e6;
                fileOutputStream = null;
            } catch (Throwable th4) {
                inputStream = null;
                httpURLConnection = httpURLConnection2;
                th = th4;
            }
        } catch (Exception e7) {
            e = e7;
            fileOutputStream = null;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            inputStream = null;
            httpURLConnection = null;
        }
    }

    public static boolean at(String str, String str2, String str3) {
        return ay().as(str, str2, str3);
    }

    public static String au(String str, InterfaceC0059m interfaceC0059m) {
        return ay().aw(str, null, interfaceC0059m);
    }

    public static String av(String str, Map map, InterfaceC0059m interfaceC0059m) {
        return ay().aw(str, map, interfaceC0059m);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:34:0x00a5  */
    private String aw(String str, Map map, InterfaceC0059m interfaceC0059m) throws Throwable {
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2 = null;
        B.ci(K, "getRequest %s", str);
        try {
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection3.setRequestMethod("GET");
                httpURLConnection3.addRequestProperty("Origin-Flag", "car");
                if (map != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        httpURLConnection3.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                httpURLConnection3.setReadTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                httpURLConnection3.setConnectTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
                if (httpURLConnection3.getResponseCode() != 200) {
                    if (interfaceC0059m != null) {
                        interfaceC0059m.aI("" + httpURLConnection3.getResponseCode());
                    }
                    String str2 = "" + httpURLConnection3.getResponseCode();
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    if (interfaceC0059m != null) {
                        interfaceC0059m.aJ();
                    }
                    return str2;
                }
                InputStream inputStream = httpURLConnection3.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuffer.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                if (interfaceC0059m != null) {
                    interfaceC0059m.aK(stringBuffer.toString());
                }
                String string = stringBuffer.toString();
                if (httpURLConnection3 != null) {
                    httpURLConnection3.disconnect();
                }
                if (interfaceC0059m != null) {
                    interfaceC0059m.aJ();
                }
                return string;
            } catch (Exception e) {
                httpURLConnection = httpURLConnection3;
                e = e;
                if (interfaceC0059m != null) {
                    try {
                        interfaceC0059m.aI(e.getMessage());
                    } catch (Throwable th) {
                        th = th;
                        httpURLConnection2 = httpURLConnection;
                        if (httpURLConnection2 != null) {
                            httpURLConnection2.disconnect();
                        }
                        if (interfaceC0059m != null) {
                            interfaceC0059m.aJ();
                        }
                        throw th;
                    }
                }
                C0065s.bL(K, e);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (interfaceC0059m != null) {
                    interfaceC0059m.aJ();
                }
                return null;
            } catch (Throwable th2) {
                httpURLConnection2 = httpURLConnection3;
                th = th2;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                if (interfaceC0059m != null) {
                    interfaceC0059m.aJ();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            httpURLConnection = null;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private String ax(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry : map.entrySet()) {
            stringBuffer.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized C0058l ay() {
        if (L == null) {
            L = new C0058l();
        }
        return L;
    }

    public static String az(JSONArray jSONArray, String str, InterfaceC0059m interfaceC0059m) {
        return aB("application/json", jSONArray.toString(), null, str, interfaceC0059m, false);
    }
}
