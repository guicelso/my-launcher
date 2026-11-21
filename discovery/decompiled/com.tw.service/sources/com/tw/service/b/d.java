package com.tw.service.b;

import android.content.ComponentName;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.FileReader;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static ComponentName Yk;

    /* JADX WARN: Code duplicated, block: B:28:0x004a  */
    /* JADX WARN: Code duplicated, block: B:32:0x0052  */
    /* JADX WARN: Code duplicated, block: B:47:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static ComponentName getCustomPipAppComponentName() throws Throwable {
        String str;
        String line;
        String line2;
        ComponentName componentName = Yk;
        if (componentName != null && !TextUtils.isEmpty(componentName.getPackageName())) {
            return Yk;
        }
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/data/tw/custom_pip_app_name"));
            try {
                try {
                    line2 = bufferedReader2.readLine();
                    try {
                        line = bufferedReader2.readLine();
                        try {
                            bufferedReader2.close();
                        } catch (Exception unused) {
                        }
                    } catch (Exception unused2) {
                        str = line2;
                        bufferedReader = bufferedReader2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception unused3) {
                            }
                        }
                        line = "";
                        line2 = str;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused5) {
                bufferedReader = bufferedReader2;
                str = "";
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                line = "";
                line2 = str;
                if (TextUtils.isEmpty(line2)) {
                    line2 = "";
                }
                Yk = new ComponentName(line2, TextUtils.isEmpty(line) ? "" : line);
                return Yk;
            }
        } catch (Exception unused6) {
        } catch (Throwable th2) {
            th = th2;
        }
        if (TextUtils.isEmpty(line2)) {
            line2 = "";
        }
        Yk = new ComponentName(line2, TextUtils.isEmpty(line) ? "" : line);
        return Yk;
    }
}
