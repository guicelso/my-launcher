package com.dofun.carsetting.f;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/* JADX INFO: compiled from: ReviseFileUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static HashMap<String, String> f229a = new HashMap<>();
    private static final String b = Environment.getExternalStorageDirectory().getPath() + "";

    public static String a() {
        return (String) a("PLATFORMDISPLAYSETTINGS").first;
    }

    public static String b() {
        return (String) a("PLATFORMDISPLAYSETTINGS").second;
    }

    public static String c() {
        return (String) a("MEMORY_INFO").first;
    }

    public static String d() {
        return (String) a("MEMORY_INFO").second;
    }

    public static String e() {
        return (String) a("CPUINFOSETTINGS").first;
    }

    public static String f() {
        return (String) a("memory_display_ts18").first;
    }

    public static String g() {
        return (String) a("STORAGEDISPLAYSETTINGS").first;
    }

    public static String h() {
        return (String) a("STORAGEDISPLAYSETTINGS").second;
    }

    private static Pair<String, String> a(String str) {
        String str2 = "0";
        String str3 = "";
        StringBuilder sb = new StringBuilder("");
        String str4 = new File("/twdataconfig/.VersionSettings").exists() ? "/twdataconfig" : b;
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                FileInputStream fileInputStream = new FileInputStream(str4 + "/.VersionSettings");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                fileInputStream.close();
                bufferedReader.close();
            } catch (Exception e) {
                Log.e("ReviseFileUtils", Log.getStackTraceString(e));
            }
        }
        try {
            if (!TextUtils.isEmpty(sb.toString())) {
                String[] strArrSplit = sb.toString().split("\\+");
                for (String str5 : strArrSplit) {
                    String[] strArrSplit2 = str5.split("\\=");
                    if (strArrSplit2.length == 2) {
                        f229a.put(strArrSplit2[0], strArrSplit2[1]);
                    } else if (strArrSplit2.length == 1) {
                        f229a.put(strArrSplit2[0], " ");
                    }
                }
                str2 = f229a.get(str);
                str3 = f229a.get(str + "_TEXT");
                if (str2 == null) {
                    str2 = "0";
                }
                if (str3 == null) {
                    str3 = "";
                }
            }
            return new Pair<>(str2, str3);
        } catch (Exception unused) {
            return new Pair<>("0", "");
        }
    }

    public static boolean i() {
        File file = new File("/twdataconfig/.VersionSettings");
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("/");
        sb.append(".VersionSettings");
        return file.exists() || new File(sb.toString()).exists();
    }
}
