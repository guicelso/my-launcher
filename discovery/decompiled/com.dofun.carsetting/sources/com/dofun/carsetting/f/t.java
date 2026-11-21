package com.dofun.carsetting.f;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: SupportCPVersion.java */
/* JADX INFO: loaded from: classes.dex */
public class t {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static t f231a;
    private static int c;
    private String b = "";
    private a.a.a.a.a.a d;

    public static t a() {
        if (f231a == null) {
            f231a = new t();
        }
        return f231a;
    }

    public String a(Context context, String str) {
        try {
            String strA = "";
            String strB = b();
            Log.d("SupportCPVersion", "getCp2Version():" + strB);
            if (strB != null) {
                Log.d("SupportCPVersion", " temp = " + strB);
                if (strB.startsWith("Platform")) {
                    Matcher matcher = Pattern.compile("PlatformVersion:(\\S+)ProjectVersion:(\\S+)HWVersion:(\\S+)").matcher(strB.replaceAll("\\s+", ""));
                    if (!matcher.matches()) {
                        Log.e("SupportCPVersion", "Regex did not match on cp2 version: ");
                    } else {
                        String strGroup = matcher.group(3);
                        String strSubstring = strGroup.substring(strGroup.indexOf("modem") + "modem".length());
                        String strSubstring2 = strSubstring.substring(10);
                        strA = matcher.group(1) + "|" + matcher.group(2) + "|" + strSubstring.substring(0, 10) + " " + strSubstring2;
                    }
                } else if (strB.startsWith("WCN_VER")) {
                    Matcher matcher2 = Pattern.compile("WCN_VER:(.*)~(.*)~(.*)~(.*)").matcher(strB);
                    Log.d("SupportCPVersion", "temp length=" + strB.length());
                    strA = a(matcher2, strB);
                } else {
                    Log.e("SupportCPVersion", "cp2 version is error");
                }
            }
            if (!TextUtils.isEmpty(strA)) {
                this.b = strA;
            }
            Log.d("SupportCPVersion", "cp2 = " + strA);
        } catch (Exception e) {
            Log.d("SupportCPVersion", "Exceptipon:" + e.toString());
        }
        return this.b;
    }

    private String a(Matcher matcher, String str) {
        StringBuilder sb = new StringBuilder("");
        if (matcher.matches()) {
            int i = 0;
            while (i < matcher.groupCount() - 1) {
                int i2 = i + 1;
                String strGroup = matcher.group(i2);
                if (strGroup.contains("Version:")) {
                    Log.d("SupportCPVersion", "index of : = " + strGroup.indexOf(58));
                    sb.append(strGroup.substring(strGroup.indexOf(58) + 1, strGroup.length()));
                } else {
                    sb.append(strGroup);
                }
                if (i != matcher.groupCount() - 2) {
                    sb.append("|");
                }
                i = i2;
            }
            Log.d("SupportCPVersion", " base wcn_ver cp2 info : " + sb.toString());
            Log.d("SupportCPVersion", " group 4  info :" + matcher.group(4) + "length () =" + matcher.group(4).length());
            if (!TextUtils.isEmpty(matcher.group(matcher.groupCount()).replaceAll("\\s+", "")) && str.charAt(c - 2) != '~') {
                sb.append("|");
                sb.append(matcher.group(matcher.groupCount()));
            }
            Log.d("SupportCPVersion", " wcn_ver cp2 info : " + sb.toString());
        }
        return sb.toString();
    }

    private a.a.a.a.a.a c() {
        return a.a.a.a.a.a.a();
    }

    private String a(String str) {
        try {
            return this.d.b(str);
        } catch (RemoteException unused) {
            return "";
        }
    }

    public String b() {
        Log.d("SupportCPVersion", "start getting the cp2 info by hidl");
        try {
            this.d = c();
            if (this.d == null) {
                Log.d("SupportCPVersion", "mIConnmgr == null");
                return "";
            }
            String strA = a("wcn at+spatgetcp2info ");
            c = strA.length();
            Log.d("SupportCPVersion", " has got the cp2 info ");
            return strA;
        } catch (RemoteException unused) {
            Log.d("SupportCPVersion", "remoteexception");
            return "";
        } catch (NoSuchElementException unused2) {
            return "";
        }
    }
}
