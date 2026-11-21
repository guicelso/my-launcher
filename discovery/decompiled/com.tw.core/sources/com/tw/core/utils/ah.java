package com.tw.core.utils;

import android.content.pm.IPackageInstallObserver;

/* JADX INFO: loaded from: classes.dex */
final class ah extends IPackageInstallObserver.Stub {
    final /* synthetic */ InterfaceC0068v bv;

    ah(InterfaceC0068v interfaceC0068v) {
        this.bv = interfaceC0068v;
    }

    @Override // android.content.pm.IPackageInstallObserver
    public void packageInstalled(String str, int i) {
        C0065s.bK("ApkController", "installPackage packageName=" + str + "\nreturnCode=" + i);
        C0067u.bT(null, str, i, this.bv);
    }
}
