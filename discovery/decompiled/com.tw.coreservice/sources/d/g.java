package d;

import android.os.Looper;
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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class g {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static g f48a;

    public interface a {
        void a(String str);

        void b(String str);

        void c();
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00d6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x00d8 A[Catch: IOException -> 0x00d4, TryCatch #5 {IOException -> 0x00d4, blocks: (B:56:0x00d0, B:60:0x00d8, B:62:0x00dd), top: B:70:0x00d0 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x00dd A[Catch: IOException -> 0x00d4, TRY_LEAVE, TryCatch #5 {IOException -> 0x00d4, blocks: (B:56:0x00d0, B:60:0x00d8, B:62:0x00dd), top: B:70:0x00d0 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x00d0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static boolean a(String str, String str2) {
        Throwable th;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        Exception e;
        FileOutputStream fileOutputStream2;
        boolean z;
        Objects.requireNonNull(c());
        byte[] bArr = new byte[1024];
        HttpURLConnection httpURLConnection = null;
        FileOutputStream fileOutputStream3 = null;
        FileOutputStream fileOutputStream4 = null;
        InputStream inputStream2 = null;
        InputStream inputStream3 = null;
        InputStream inputStream4 = null;
        httpURLConnection = null;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setConnectTimeout(5000);
                httpURLConnection2.setReadTimeout(5000);
                httpURLConnection2.setRequestMethod("GET");
                c.c("g", "响应码 " + httpURLConnection2.getResponseCode(), new Object[0]);
                if (httpURLConnection2.getResponseCode() == 200) {
                    InputStream inputStream5 = httpURLConnection2.getInputStream();
                    try {
                        fileOutputStream2 = new FileOutputStream(d.a(str2));
                        while (true) {
                            try {
                                int i = inputStream5.read(bArr);
                                if (i <= 0) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, i);
                            } catch (Exception e2) {
                                e = e2;
                                fileOutputStream3 = fileOutputStream2;
                                fileOutputStream = fileOutputStream3;
                                inputStream3 = inputStream5;
                                InputStream inputStream6 = inputStream3;
                                httpURLConnection = httpURLConnection2;
                                inputStream = inputStream6;
                                try {
                                    c.d("g", e);
                                    if (httpURLConnection != null) {
                                        try {
                                            httpURLConnection.disconnect();
                                        } catch (IOException e3) {
                                            c.d("g", e3);
                                            return false;
                                        }
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (fileOutputStream == null) {
                                        return false;
                                    }
                                    fileOutputStream.close();
                                    return false;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (httpURLConnection != null) {
                                        try {
                                            httpURLConnection.disconnect();
                                            if (inputStream != null) {
                                                inputStream.close();
                                            }
                                            if (fileOutputStream != null) {
                                                fileOutputStream.close();
                                            }
                                        } catch (IOException e4) {
                                            c.d("g", e4);
                                            throw th;
                                        }
                                    } else {
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                fileOutputStream4 = fileOutputStream2;
                                fileOutputStream = fileOutputStream4;
                                inputStream4 = inputStream5;
                                InputStream inputStream7 = inputStream4;
                                httpURLConnection = httpURLConnection2;
                                inputStream = inputStream7;
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                } else {
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                }
                                throw th;
                            }
                        }
                        fileOutputStream2.flush();
                        c.c("g", "下載成功", new Object[0]);
                        inputStream2 = inputStream5;
                        z = true;
                    } catch (Exception e5) {
                        e = e5;
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } else {
                    fileOutputStream2 = null;
                    z = false;
                }
                try {
                    httpURLConnection2.disconnect();
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                } catch (IOException e6) {
                    c.d("g", e6);
                }
                return z;
            } catch (Exception e7) {
                e = e7;
                fileOutputStream = null;
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = null;
            }
        } catch (Exception e8) {
            e = e8;
            inputStream = null;
            fileOutputStream = null;
        } catch (Throwable th6) {
            th = th6;
            inputStream = null;
            fileOutputStream = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x00b8  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v5 */
    public static String b(String str, a aVar) {
        Exception e;
        HttpURLConnection httpURLConnection;
        Objects.requireNonNull(c());
        c.b("g", "getRequest %s", str);
        ?? r0 = 0;
        string = null;
        String string = null;
        try {
            try {
                httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.addRequestProperty("Origin-Flag", "car");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    if (httpURLConnection.getResponseCode() == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
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
                        aVar.b(stringBuffer.toString());
                        string = stringBuffer.toString();
                    } else {
                        aVar.a("" + httpURLConnection.getResponseCode());
                        string = "" + httpURLConnection.getResponseCode();
                    }
                } catch (Exception e2) {
                    e = e2;
                    aVar.a(e.getMessage());
                    e.printStackTrace();
                    if (httpURLConnection != null) {
                    }
                    aVar.c();
                    return string;
                }
            } catch (Throwable th) {
                r0 = str;
                th = th;
                if (r0 != 0) {
                    r0.disconnect();
                }
                aVar.c();
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            httpURLConnection = null;
        } catch (Throwable th2) {
            th = th2;
            if (r0 != 0) {
                r0.disconnect();
            }
            aVar.c();
            throw th;
        }
        httpURLConnection.disconnect();
        aVar.c();
        return string;
    }

    public static synchronized g c() {
        if (f48a == null) {
            f48a = new g();
        }
        return f48a;
    }

    public static String d(final Map<String, String> map, final String str, final a aVar) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return c().e(map, str, aVar);
        }
        Executors.newCachedThreadPool().execute(new Runnable() { // from class: d.f

            /* JADX INFO: renamed from: d, reason: collision with root package name */
            public final /* synthetic */ boolean f47d = false;

            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                g.c().e(map, str, aVar);
            }
        });
        return null;
    }

    public static void f(String str, Map<String, String> map, Map<String, File> map2, a aVar) {
        String string = UUID.randomUUID().toString();
        HttpURLConnection httpURLConnection = null;
        try {
            try {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                try {
                    httpURLConnection2.setReadTimeout(300000);
                    int i = 0;
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
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            sb.append("--");
                            sb.append(string);
                            sb.append("\r\n");
                            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"\r\n");
                            sb.append("Content-Type: text/plain; charset=UTF-8\r\n");
                            sb.append("Content-Transfer-Encoding: 8bit\r\n");
                            sb.append("\r\n");
                            sb.append(entry.getValue());
                            sb.append("\r\n");
                        }
                    }
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                    dataOutputStream.write(sb.toString().getBytes());
                    if (map2 != null) {
                        for (Map.Entry<String, File> entry2 : map2.entrySet()) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("--");
                            sb2.append(string);
                            sb2.append("\r\n");
                            sb2.append("Content-Disposition: form-data; name=\"" + entry2.getKey() + "\"; filename=\"" + entry2.getValue().getName() + "\"\r\n");
                            sb2.append("Content-Type: application/octet-stream; charset=UTF-8\r\n");
                            sb2.append("\r\n");
                            dataOutputStream.write(sb2.toString().getBytes());
                            FileInputStream fileInputStream = new FileInputStream(entry2.getValue());
                            byte[] bArr = new byte[1024];
                            int i2 = 0;
                            while (true) {
                                int i3 = fileInputStream.read(bArr);
                                if (i3 != -1) {
                                    dataOutputStream.write(bArr, i, i3);
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append("当前进度 ");
                                    i2 += i3;
                                    sb3.append(i2);
                                    c.b("g", sb3.toString(), new Object[0]);
                                    i = 0;
                                }
                            }
                            c.b("g", "写流完成", new Object[0]);
                            fileInputStream.close();
                            dataOutputStream.write("\r\n".getBytes());
                            i = 0;
                        }
                    }
                    dataOutputStream.write(("--" + string + "--\r\n").getBytes());
                    dataOutputStream.flush();
                    int responseCode = httpURLConnection2.getResponseCode();
                    c.b("g", "响应码 %s", Integer.valueOf(responseCode));
                    InputStream inputStream = httpURLConnection2.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb4 = new StringBuilder();
                    if (responseCode == 200 || responseCode == 204) {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            } else {
                                sb4.append(line);
                            }
                        }
                        aVar.b(sb4.toString());
                    } else {
                        aVar.a(httpURLConnection2.getResponseCode() + "错误");
                    }
                    dataOutputStream.close();
                    inputStream.close();
                    bufferedReader.close();
                    inputStreamReader.close();
                    httpURLConnection2.disconnect();
                } catch (Exception e) {
                    e = e;
                    httpURLConnection = httpURLConnection2;
                    e.printStackTrace();
                    aVar.a("上传失败");
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                } catch (Throwable th) {
                    th = th;
                    httpURLConnection = httpURLConnection2;
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    aVar.c();
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
            aVar.c();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:56:0x012e A[PHI: r2
      0x012e: PHI (r2v1 java.lang.String) = (r2v0 java.lang.String), (r2v3 java.lang.String) binds: [B:55:0x012c, B:32:0x00eb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:63:0x013e  */
    /* JADX WARN: Code duplicated, block: B:65:0x0143  */
    /* JADX WARN: Code duplicated, block: B:83:? A[SYNTHETIC] */
    public final String e(Map map, String str, a aVar) throws Throwable {
        BufferedReader bufferedReader;
        HttpURLConnection httpURLConnection;
        Exception e;
        InputStream inputStream;
        Throwable th;
        BufferedReader bufferedReader2;
        DataOutputStream dataOutputStream;
        String string = new JSONObject(map).toString();
        String str2 = "";
        InputStream inputStream2 = null;
        BufferedReader bufferedReader3 = null;
        InputStream inputStream3 = null;
        InputStream inputStream4 = null;
        inputStream2 = null;
        HttpURLConnection httpURLConnection2 = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                c.b("g", "address %s params %s", str, string);
                byte[] bytes = string.getBytes();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Origin-Flag", "car");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Content-Length", bytes.length + "");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                try {
                    dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    try {
                        dataOutputStream.write(string.getBytes("UTF-8"));
                        dataOutputStream.flush();
                        m.e(dataOutputStream);
                        if (httpURLConnection.getResponseCode() == 200) {
                            inputStream = httpURLConnection.getInputStream();
                            try {
                                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                String str3 = "";
                                while (true) {
                                    try {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        str3 = str3 + line;
                                    } catch (Exception e2) {
                                        e = e2;
                                        bufferedReader3 = bufferedReader;
                                        BufferedReader bufferedReader4 = bufferedReader3;
                                        inputStream4 = inputStream;
                                        bufferedReader2 = bufferedReader4;
                                        bufferedReader = bufferedReader2;
                                        inputStream = inputStream4;
                                        httpURLConnection2 = httpURLConnection;
                                        try {
                                            e.printStackTrace();
                                            if (aVar != null) {
                                                aVar.a("请求错误=" + e);
                                            }
                                            m.e(inputStream);
                                            m.e(bufferedReader);
                                            if (httpURLConnection2 != null) {
                                                httpURLConnection2.disconnect();
                                            }
                                            if (aVar != null) {
                                                aVar.c();
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            httpURLConnection = httpURLConnection2;
                                            inputStream2 = inputStream;
                                            th = th;
                                            m.e(inputStream2);
                                            m.e(bufferedReader);
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            if (aVar == null) {
                                                throw th;
                                            }
                                            aVar.c();
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        inputStream2 = inputStream;
                                        th = th;
                                        m.e(inputStream2);
                                        m.e(bufferedReader);
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                        }
                                        if (aVar == null) {
                                            throw th;
                                        }
                                        aVar.c();
                                        throw th;
                                    }
                                }
                                if (aVar != null) {
                                    aVar.b(str3);
                                }
                                inputStream.close();
                                inputStream3 = inputStream;
                                str2 = str3;
                            } catch (Exception e3) {
                                e = e3;
                            } catch (Throwable th4) {
                                th = th4;
                                bufferedReader = null;
                            }
                        } else {
                            if (aVar != null) {
                                aVar.a("连接不成功，错误码=" + httpURLConnection.getResponseCode());
                            }
                            bufferedReader = null;
                        }
                        m.e(inputStream3);
                        m.e(bufferedReader);
                        httpURLConnection.disconnect();
                        if (aVar != null) {
                            aVar.c();
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        m.e(dataOutputStream);
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    dataOutputStream = null;
                }
            } catch (Exception e4) {
                e = e4;
                bufferedReader2 = null;
            } catch (Throwable th7) {
                th = th7;
                bufferedReader = null;
                m.e(inputStream2);
                m.e(bufferedReader);
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (aVar == null) {
                    throw th;
                }
                aVar.c();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            inputStream = null;
            bufferedReader = null;
        } catch (Throwable th8) {
            th = th8;
            bufferedReader = null;
            httpURLConnection = null;
        }
        return str2;
    }
}
