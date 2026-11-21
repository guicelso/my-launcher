package com.tw.core.utils;

import android.os.Build;
import android.os.SystemProperties;
import com.tw.core.app.DoFunApplication;

/* JADX INFO: renamed from: com.tw.core.utils.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0055i implements com.tw.core.e.b {
    @Override // com.tw.core.e.b
    public String X() {
        return "ZHKLD";
    }

    @Override // com.tw.core.e.b
    public String Y(String str) {
        return SystemProperties.get("ro.product.zh.name", "");
    }

    @Override // com.tw.core.e.b
    public String Z() {
        return null;
    }

    @Override // com.tw.core.e.b
    public void aa(com.tw.core.e.d dVar) {
        if (dVar != null) {
            dVar.dS("unknow");
        }
    }

    @Override // com.tw.core.e.b
    public String ab() {
        return "-1";
    }

    @Override // com.tw.core.e.b
    public String ac() {
        return SystemProperties.get("ro.product.version", "");
    }

    @Override // com.tw.core.e.b
    public String ad() {
        return K.df(Build.SERIAL, DoFunApplication.nt().X());
    }

    @Override // com.tw.core.e.b
    public void ae(boolean z) {
    }

    @Override // com.tw.core.e.b
    public void af(boolean z) {
    }

    @Override // com.tw.core.e.b
    public void ag(boolean z) {
    }
}
