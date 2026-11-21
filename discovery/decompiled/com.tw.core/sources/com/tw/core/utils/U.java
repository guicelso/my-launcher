package com.tw.core.utils;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class U implements com.tw.core.e.d {
    final /* synthetic */ Context aW;

    U(Context context) {
        this.aW = context;
    }

    @Override // com.tw.core.e.d
    public void dS(String str) throws Throwable {
        B.ci(C0047a.o, "carInit onBackMcuVersion : %s", str);
        Map mapM = C0047a.m(this.aW, str);
        if (mapM != null) {
            C0058l.an(mapM, com.tw.core.d.a.gK, new V(this, this.aW, str, mapM));
        }
    }
}
