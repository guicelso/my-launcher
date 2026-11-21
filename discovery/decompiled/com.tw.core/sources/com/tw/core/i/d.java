package com.tw.core.i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tw.core.utils.InterfaceC0068v;

/* JADX INFO: loaded from: classes.dex */
class d extends BroadcastReceiver {
    final /* synthetic */ c jA;
    private InterfaceC0068v jz;

    private d(c cVar) {
        this.jA = cVar;
    }

    /* synthetic */ d(c cVar, d dVar) {
        this(cVar);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_ADDED")) {
            if (TextUtils.equals(intent.getAction(), "android.intent.action.PACKAGE_REMOVED")) {
            }
            return;
        }
        String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        if (this.jz != null) {
            this.jz.bY(schemeSpecificPart);
        }
    }

    public void oy(InterfaceC0068v interfaceC0068v) {
        this.jz = interfaceC0068v;
    }
}
