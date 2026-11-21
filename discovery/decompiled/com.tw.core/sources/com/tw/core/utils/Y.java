package com.tw.core.utils;

import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class Y implements Runnable {
    final /* synthetic */ Map bd;
    final /* synthetic */ String be;
    final /* synthetic */ InterfaceC0059m bf;

    Y(Map map, String str, InterfaceC0059m interfaceC0059m) {
        this.bd = map;
        this.be = str;
        this.bf = interfaceC0059m;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        C0058l.aB("application/json", new JSONObject(this.bd).toString(), null, this.be, this.bf, false);
    }
}
