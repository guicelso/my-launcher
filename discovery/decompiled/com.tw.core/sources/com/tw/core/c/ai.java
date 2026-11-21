package com.tw.core.c;

import com.tw.core.utils.C0064r;

/* JADX INFO: loaded from: classes.dex */
class ai implements com.tw.core.h.c {
    final /* synthetic */ B gJ;

    ai(B b) {
        this.gJ = b;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: mc, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        if (!bool.booleanValue() || C0064r.by(this.gJ.fD, "key_request_imei_state", false)) {
            return;
        }
        C0064r.bA(this.gJ.fD, "key_request_imei_state", true);
    }
}
