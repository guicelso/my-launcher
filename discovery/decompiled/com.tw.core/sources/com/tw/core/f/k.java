package com.tw.core.f;

import java.io.File;
import java.io.FileFilter;

/* JADX INFO: loaded from: classes.dex */
class k implements FileFilter {
    final /* synthetic */ e iw;

    k(e eVar) {
        this.iw = eVar;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return (a.mY().mZ(file.getName()) || file.getName().endsWith("__UPLOADING.zip") || file.getName().endsWith("__COMPRESS")) ? false : true;
    }
}
