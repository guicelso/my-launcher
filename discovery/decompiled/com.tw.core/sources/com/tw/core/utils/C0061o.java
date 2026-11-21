package com.tw.core.utils;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import com.tw.core.app.DoFunApplication;
import com.tw.core.c.C0040t;

/* JADX INFO: renamed from: com.tw.core.utils.o, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0061o {
    public static String M;
    private static final String O = C0061o.class.getSimpleName();
    private static C0061o Q;
    private String P = "3082039130820279a003020102020900";
    private String N = "308203953082027da003020102020900";

    private C0061o() {
    }

    private void aP(Context context, String str, String str2, String str3, InterfaceC0053g interfaceC0053g) {
        String strX = DoFunApplication.nt().X();
        String strDO = S.dO(context, context.getPackageName());
        if ("TW".equals(strX)) {
            M = "TWCore-TW";
            if (this.P.equals(strDO)) {
                M = "TWCore-TW-TS8";
            } else if (this.N.equals(strDO)) {
                M = "TWCore-TW-T3";
            }
        } else {
            M = "TWCore-" + strX;
        }
        String str4 = com.tw.core.d.a.he + "cid=" + com.tw.core.b.f.gr() + "&strategyId=" + M + "&flag=" + str + "&plat=" + C0062p.bc() + "&androidVersion=" + Build.VERSION.RELEASE + "&systemVersion=" + com.tw.core.b.e.gg() + "&language=" + C0062p.aV(context);
        if (C0065s.aa) {
            C0065s.bK(O, "自升级 " + str4);
        }
        C0058l.au(str4, new ad(this, interfaceC0053g));
    }

    public static synchronized C0061o aQ() {
        if (Q == null) {
            Q = new C0061o();
        }
        return Q;
    }

    public void aR(Context context, String str, String str2, InterfaceC0053g interfaceC0053g) {
        double latitude;
        double longitude = -1.0d;
        Location locationLe = C0040t.le(DoFunApplication.nu());
        if (locationLe != null) {
            latitude = locationLe.getLatitude();
            longitude = locationLe.getLongitude();
        } else {
            latitude = -1.0d;
        }
        C0065s.bK(O, "mLatitude：" + latitude + "--mLongitude:" + longitude);
        aP(context, str, str2, C0071y.ce(latitude, longitude), interfaceC0053g);
    }
}
