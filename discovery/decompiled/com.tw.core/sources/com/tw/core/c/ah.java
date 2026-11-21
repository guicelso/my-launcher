package com.tw.core.c;

import com.tw.core.utils.C0064r;

/* JADX INFO: loaded from: classes.dex */
class ah implements com.tw.core.h.c {
    final /* synthetic */ ag gI;

    ah(ag agVar) {
        this.gI = agVar;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: mb, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        if (bool.booleanValue()) {
            C0064r.bB(this.gI.gG.fA, "contact_data", this.gI.gG.fx.toString());
        }
    }
}
