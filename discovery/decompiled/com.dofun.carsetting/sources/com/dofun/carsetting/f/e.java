package com.dofun.carsetting.f;

import android.support.graphics.drawable.PathInterpolatorCompat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: HttpUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class e {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static e f221a;

    /* JADX INFO: compiled from: HttpUtils.java */
    public interface a {
        void a(String str);

        void b(String str);
    }

    public static synchronized e a() {
        if (f221a == null) {
            f221a = new e();
        }
        return f221a;
    }

    public static void a(Map<String, String> map, String str, a aVar) {
        a();
        b(map, str, aVar);
    }

    private static void b(final Map<String, String> map, final String str, final a aVar) {
        new Thread(new Runnable() { // from class: com.dofun.carsetting.f.e.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    String string = new JSONObject(e.a(map)).toString();
                    byte[] bytes = string.getBytes();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    httpURLConnection.setConnectTimeout(PathInterpolatorCompat.MAX_NUM_POINTS);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Length", bytes.length + "");
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    try {
                        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                        dataOutputStream.writeBytes(string);
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (Exception e) {
                        g.a().a("HttpUtils", "" + e);
                    }
                    if (httpURLConnection.getResponseCode() == 200) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        String str2 = "";
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line != null) {
                                str2 = str2 + line;
                            } else {
                                aVar.a(str2);
                                return;
                            }
                        }
                    } else {
                        aVar.b(httpURLConnection.getResponseCode() + "错误");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    aVar.b("网络请求 " + e2);
                }
            }
        }).start();
    }

    public static Map<String, String> a(Map<String, String> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), URLEncoder.encode(entry.getValue()));
        }
        return map2;
    }
}
