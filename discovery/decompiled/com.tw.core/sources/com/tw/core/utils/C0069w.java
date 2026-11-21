package com.tw.core.utils;

import android.content.pm.IPackageDeleteObserver;

/* JADX INFO: renamed from: com.tw.core.utils.w, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0069w extends IPackageDeleteObserver.Stub {
    private InterfaceC0053g al;

    public C0069w() {
    }

    public C0069w(InterfaceC0053g interfaceC0053g) {
        this.al = interfaceC0053g;
    }

    public void packageDeleted(String str, int i) {
        C0065s.bK("ApkController", "deletePackageName=" + str + "\nreturnCode=" + i);
        if (i == 1) {
            C0065s.bK("ApkController", "卸载成功=============");
            if (this.al != null) {
                this.al.K(str);
                return;
            }
            return;
        }
        C0065s.bK("ApkController", "卸载失败=============");
        if (this.al != null) {
            this.al.L(Integer.valueOf(i));
        }
    }
}
