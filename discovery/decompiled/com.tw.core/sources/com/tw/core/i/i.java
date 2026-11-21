package com.tw.core.i;

import com.tw.core.utils.C0065s;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
class i implements com.tw.core.h.c {
    final /* synthetic */ b jJ;
    final /* synthetic */ String jK;

    i(b bVar, String str) {
        this.jJ = bVar;
        this.jK = str;
    }

    @Override // com.tw.core.h.c
    /* JADX INFO: renamed from: oB, reason: merged with bridge method [inline-methods] */
    public void lT(Boolean bool) {
        C0065s.bK("BaseDataReport", "report mReportContent result = " + bool);
        if (bool.booleanValue()) {
            return;
        }
        this.jJ.kr(Arrays.asList(this.jK));
    }
}
