package com.tw.service;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class U implements com.tw.service.a.a {
    final /* synthetic */ String Ak;
    final /* synthetic */ TWService n;

    U(TWService tWService, String str) {
        this.n = tWService;
        this.Ak = str;
    }

    @Override // com.tw.service.a.a
    public void a(String str) throws Throwable {
        Log.e("HYH", "TWService->onCreate:themeName=" + str);
        if (TextUtils.isEmpty(str)) {
            boolean zEquals = TextUtils.equals("0000", this.Ak);
            if (TextUtils.equals("1000", this.Ak)) {
                zEquals = true;
            }
            if (zEquals || String.format("%04X", Integer.valueOf(this.n.pa.write(65521))).equals(this.Ak)) {
                return;
            }
            this.n.mHandler.post(new T(this));
            return;
        }
        this.n.Eb = true;
        com.tw.service.a.f.getInstance().a(this.n.getApplicationContext(), this.n.pa, str);
        this.n.fb = com.tw.service.a.f.getInstance().qa();
        com.tw.service.a.f.getInstance().pa();
        ka kaVar = new ka(this.n, true);
        com.tw.service.a.f.getInstance().a(new Q(this, kaVar));
        this.n.mHandler.post(new S(this, kaVar));
    }
}
