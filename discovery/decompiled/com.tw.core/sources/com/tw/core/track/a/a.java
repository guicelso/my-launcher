package com.tw.core.track.a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static boolean cY = false;

    private static HttpsURLConnection hR(String str, String str2, String str3) throws ProtocolException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) hX("https://yingyan.baidu.com/api/v3/" + str, str2, str3).openConnection();
        hV(httpsURLConnection, str3);
        httpsURLConnection.setHostnameVerifier(new c());
        return httpsURLConnection;
    }

    private static String hS(int i, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":").append(String.valueOf(i)).append(",").append("\"message\":").append("\"").append(str).append("\"}");
        return sb.toString();
    }

    private static String hT(IOException iOException) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":").append(-1).append(",").append("\"message\":").append("\"").append(com.tw.core.track.b.a.ih(iOException)).append("\"}");
        return sb.toString();
    }

    private static String hU(InputStream inputStream) {
        StringBuffer stringBuffer = new StringBuffer("");
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuffer.append(line);
                    } catch (Throwable th) {
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw th;
                            }
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    String strHT = hT(e2);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            return strHT;
                        }
                    }
                    return strHT;
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return stringBuffer.toString();
                }
            }
        }
        return stringBuffer.toString();
    }

    private static void hV(HttpURLConnection httpURLConnection, String str) throws ProtocolException {
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        httpURLConnection.setRequestMethod(str);
        httpURLConnection.setConnectTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
        httpURLConnection.setReadTimeout(DisconnectedBufferOptions.DISCONNECTED_BUFFER_SIZE_DEFAULT);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
    }

    private static HttpURLConnection hW(String str, String str2, String str3) throws ProtocolException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) hX("http://yingyan.baidu.com/api/v3/" + str, str2, str3).openConnection();
        hV(httpURLConnection, str3);
        return httpURLConnection;
    }

    private static URL hX(String str, String str2, String str3) {
        return "GET".equalsIgnoreCase(str3) ? new URL(str + "?" + str2) : new URL(str);
    }

    private static String hY(HttpURLConnection httpURLConnection) {
        InputStream inputStream = null;
        try {
            try {
                int responseCode = httpURLConnection.getResponseCode();
                if (200 != responseCode) {
                    return hS(responseCode, httpURLConnection.getResponseMessage());
                }
                InputStream inputStream2 = httpURLConnection.getInputStream();
                String strHU = hU(inputStream2);
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return strHU;
            } catch (IOException e2) {
                String strHT = hT(e2);
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                return strHT;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0037 A[Catch: IOException -> 0x003b, TRY_LEAVE, TryCatch #6 {IOException -> 0x003b, blocks: (B:9:0x0032, B:11:0x0037), top: B:53:0x0032 }] */
    /* JADX WARN: Code duplicated, block: B:27:0x0060 A[Catch: IOException -> 0x0064, TRY_LEAVE, TryCatch #2 {IOException -> 0x0064, blocks: (B:25:0x005b, B:27:0x0060), top: B:48:0x005b }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0072 A[Catch: IOException -> 0x0076, TRY_LEAVE, TryCatch #0 {IOException -> 0x0076, blocks: (B:34:0x006d, B:36:0x0072), top: B:46:0x006d }] */
    /* JADX WARN: Multi-variable type inference failed */
    private static String hZ(HttpURLConnection httpURLConnection, String str) throws Throwable {
        OutputStream outputStream;
        InputStream inputStream = null;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        try {
            if (httpURLConnection == null) {
                return "";
            }
            try {
                outputStream = httpURLConnection.getOutputStream();
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(str);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    httpURLConnection.connect();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (200 != responseCode) {
                        String strHS = hS(responseCode, httpURLConnection.getResponseMessage());
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return strHS;
                    }
                    InputStream inputStream2 = httpURLConnection.getInputStream();
                    String strHU = hU(inputStream2);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    return strHU;
                } catch (IOException e3) {
                    e = e3;
                    String strHT = hT(e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                            if (0 != 0) {
                                (objArr2 == true ? 1 : 0).close();
                            }
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            return strHT;
                        }
                    } else if (0 != 0) {
                        (objArr2 == true ? 1 : 0).close();
                    }
                    return strHT;
                }
            } catch (IOException e5) {
                e = e5;
                outputStream = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    try {
                        (objArr == true ? 1 : 0).close();
                        if (0 != 0) {
                            inputStream.close();
                        }
                    } catch (IOException e6) {
                        e6.printStackTrace();
                        throw th;
                    }
                } else if (0 != 0) {
                    inputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String ia(String str, String str2, String str3) {
        try {
            HttpURLConnection httpURLConnectionHR = cY ? hR(str, str2, str3) : hW(str, str2, str3);
            if (str3.equals("POST")) {
                return hZ(httpURLConnectionHR, str2);
            }
            if (str3.equals("GET")) {
                return hY(httpURLConnectionHR);
            }
            return null;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"status\":").append(-1).append(",").append("\"message\":").append("\"").append(com.tw.core.track.b.a.ih(e)).append("\"}");
            return sb.toString();
        }
    }
}
