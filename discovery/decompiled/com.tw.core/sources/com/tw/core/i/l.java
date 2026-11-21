package com.tw.core.i;

import android.text.TextUtils;
import com.tw.core.utils.C0065s;
import com.tw.core.utils.E;
import com.tw.core.utils.InterfaceC0068v;

/* JADX INFO: loaded from: classes.dex */
class l implements InterfaceC0068v {
    final /* synthetic */ c jQ;
    final /* synthetic */ com.tw.core.a.e jR;
    final /* synthetic */ String jS;
    final /* synthetic */ String jT;

    l(c cVar, com.tw.core.a.e eVar, String str, String str2) {
        this.jQ = cVar;
        this.jR = eVar;
        this.jS = str;
        this.jT = str2;
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bX(int i, String str) {
        this.jQ.oz(this.jS, i);
        C0065s.bK("AppUpgradeMessageReceiv", "installFailed pkgName = " + str + ", code = " + i + ", apkFilePath = " + this.jT);
        E.cA(this.jT);
    }

    @Override // com.tw.core.utils.InterfaceC0068v
    public void bY(String str) {
        C0065s.bK("AppUpgradeMessageReceiv", "installSuccess pkgName = " + str);
        if (TextUtils.equals(str, this.jR.fz()) && com.tw.core.b.d.gd(this.jQ.jB, str) == this.jR.fA()) {
            this.jQ.oz(this.jS, 1);
        }
    }
}
