package com.tw.core.f;

import com.tw.core.utils.C0058l;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class h {
    public static void nq(String str, boolean z) {
        C0058l.an(Collections.emptyMap(), com.tw.core.d.a.gV + "?switchId=" + str + "&switchStatus=" + z, new o());
    }

    public static void nr(String str) {
        C0058l.an(Collections.emptyMap(), com.tw.core.d.a.gY + "?logId=" + str, new m());
    }

    public static void ns(String str) {
        C0058l.an(Collections.emptyMap(), com.tw.core.d.a.gU + "?logId=" + str, new n());
    }
}
