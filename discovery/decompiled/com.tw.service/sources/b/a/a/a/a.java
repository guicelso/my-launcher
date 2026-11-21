package b.a.a.a;

import java.io.File;

/* JADX INFO: compiled from: outline */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String a(String str, Object obj, String str2) {
        return str + obj + str2;
    }

    public static String a(String str, String str2) {
        return str + str2;
    }

    public static String b(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static StringBuilder e(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static boolean f(String str) {
        return new File(str).exists();
    }
}
