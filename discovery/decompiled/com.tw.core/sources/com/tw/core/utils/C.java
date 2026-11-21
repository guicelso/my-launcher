package com.tw.core.utils;

import android.content.Context;
import android.text.TextUtils;
import cn.cardoor.user.bean.Token;
import com.tw.core.c.C0031k;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class C {
    public static void co(Context context) {
        new Thread(new ai(context)).start();
    }

    public static void cp(Context context) {
        C0064r.bB(context, "SP_WAKE_UP_TIME", C0066t.bN(System.currentTimeMillis(), C0066t.ac));
        C0064r.bA(context, "SP_WAKE_UP_NETWORK", com.tw.core.b.a.fE(context));
        C0048b.p().execute(new al());
    }

    public static void cq(Context context) {
        C0064r.bB(context, "SP_SHUT_DOWN_TIME", C0066t.bN(System.currentTimeMillis(), C0066t.ac));
        C0064r.bA(context, "SP_SHUT_DOWN_NETWORK", com.tw.core.b.a.fE(context));
        cr("0");
    }

    public static void cr(String str) {
        B.ci("ResultReportUtil", "reportAccState flags %s", str);
        if (com.tw.core.b.e.gh()) {
            Token tokenKz = C0031k.kt().kz();
            B.ci("ResultReportUtil", "reportAccState token %s", tokenKz);
            if (tokenKz == null || C0057k.am(tokenKz.getToken())) {
                return;
            }
            C0031k.kt().ky(new aj(str));
        }
    }

    public static String[] cs(String str) throws Throwable {
        String strCL = E.cL(E.cz() + File.separator + "apk.df");
        if (TextUtils.isEmpty(strCL)) {
            return null;
        }
        String[] strArrSplit = strCL.split("\n");
        for (String str2 : strArrSplit) {
            if (!TextUtils.isEmpty(str2)) {
                String[] strArrSplit2 = str2.split(",");
                if (strArrSplit2.length == 3) {
                    String str3 = strArrSplit2[0];
                    String str4 = strArrSplit2[1];
                    String str5 = strArrSplit2[2];
                    if (TextUtils.equals(str3, str)) {
                        return new String[]{str4, str5};
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }
}
