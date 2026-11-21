package com.tw.core.i;

import android.content.Context;
import com.tw.core.utils.C0048b;

/* JADX INFO: loaded from: classes.dex */
public abstract class f implements com.tw.core.h.d, com.tw.core.notification.c, com.tw.core.e.c {
    public Context jB;
    public a jC;

    public f(Context context, a aVar) {
        this.jB = context;
        this.jC = aVar;
    }

    @Override // com.tw.core.e.c
    public void jE(boolean z) {
    }

    @Override // com.tw.core.e.c
    public void jF() {
    }

    @Override // com.tw.core.e.c
    public void jj() {
        com.tw.core.h.a.get().nL(this);
    }

    public void ok(String str, String str2) {
        com.tw.core.notification.a.get().oD(str);
        com.tw.core.notification.b.get().oH(str);
    }

    @Override // com.tw.core.e.c
    public void onCreate() {
        com.tw.core.h.a.get().nK(this);
    }

    public abstract String oq();

    public void oz(String str, int i) {
        C0048b.p().execute(new m(this, i, str));
    }
}
