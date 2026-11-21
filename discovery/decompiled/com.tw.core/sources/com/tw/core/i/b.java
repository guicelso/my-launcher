package com.tw.core.i;

import android.text.TextUtils;
import com.tw.core.c.AbstractC0030j;
import com.tw.core.utils.C0048b;
import com.tw.core.utils.C0065s;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class b extends AbstractC0030j implements a, com.tw.core.e.c, com.tw.core.h.f {
    private Object jw = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    public void of() {
        C0065s.bK("BaseDataReport", "reportOfflineData");
        synchronized (this.jw) {
            List<String> listKp = kp(oi());
            if (C0065s.aa) {
                C0065s.bK("BaseDataReport", "reportOfflineData readData = " + listKp);
            }
            if (listKp != null && listKp.size() > 0) {
                ArrayList arrayList = new ArrayList();
                AtomicInteger atomicInteger = new AtomicInteger();
                for (String str : listKp) {
                    com.tw.core.h.b.nN().nO(str, jf(), com.tw.core.h.b.nN().iV, new h(this, atomicInteger, arrayList, str, listKp));
                }
            }
        }
    }

    public void jB() {
    }

    public void jC() {
    }

    public void jE(boolean z) {
    }

    public void jF() {
    }

    public void jG() {
    }

    public void jH() {
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected long jT() {
        return 5242880L;
    }

    @Override // com.tw.core.c.AbstractC0030j
    protected boolean jV() {
        return false;
    }

    public void jc(boolean z, String str) {
        C0065s.bK("BaseDataReport", "connected reportOfflineData");
        C0048b.p().execute(new g(this));
    }

    public void jj() {
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jw(String str) {
        return str;
    }

    @Override // com.tw.core.h.f
    public void jy(Throwable th) {
    }

    @Override // com.tw.core.i.a
    public void od(String str) {
        oe(str, null);
    }

    public void oe(String str, com.tw.core.h.c cVar) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strReplaceAll = str.replaceAll("\n", "").replaceAll("\r", "");
        if (cVar != null) {
            com.tw.core.h.b.nN().nO(strReplaceAll, jf(), com.tw.core.h.b.nN().iV, cVar);
        } else {
            com.tw.core.h.b.nN().nO(strReplaceAll, jf(), com.tw.core.h.b.nN().iV, new i(this, strReplaceAll));
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: og, reason: merged with bridge method [inline-methods] */
    public boolean jM(String str) {
        return !TextUtils.isEmpty(str);
    }

    @Override // com.tw.core.c.AbstractC0030j
    /* JADX INFO: renamed from: oh, reason: merged with bridge method [inline-methods] */
    public String jL(String str) {
        return str;
    }

    public int oi() {
        return 500;
    }

    public void onCreate() {
    }
}
