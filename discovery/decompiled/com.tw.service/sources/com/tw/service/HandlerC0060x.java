package com.tw.service;

import android.os.Handler;

/* JADX INFO: renamed from: com.tw.service.x, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class HandlerC0060x extends Handler {
    final /* synthetic */ TWService n;

    HandlerC0060x(TWService tWService) {
        this.n = tWService;
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 135361. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:99)
        */
    @Override // android.os.Handler
    public void handleMessage(android.os.Message r18) {
        /*
            Method dump skipped, instruction units count: 13536
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tw.service.HandlerC0060x.handleMessage(android.os.Message):void");
    }
}
