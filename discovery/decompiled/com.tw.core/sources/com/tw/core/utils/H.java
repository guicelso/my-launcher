package com.tw.core.utils;

import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class H {
    public static List aB = new ArrayList();

    public static synchronized void cV() {
        if (aB.size() > 0) {
            ArrayList arrayList = new ArrayList(aB);
            aB.clear();
            String str = E.cz() + "/log/twcore.df";
            File file = new File(str);
            if (!file.exists()) {
                E.cM(str);
            } else if (E.cB(file) > 10485760) {
                E.cD(str, "", false);
            }
            E.cD(str, arrayList.toString(), true);
            arrayList.clear();
        }
    }

    public static void cW(String str, String str2) {
        try {
            aB.add(C0062p.bi(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "  " + str + ":" + str2 + "\n");
            Log.d(str, "log lenght:" + aB.size());
            if (aB.size() >= 200) {
                cV();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
