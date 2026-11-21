package com.tw.service;

import java.io.File;
import java.io.FileFilter;

/* JADX INFO: renamed from: com.tw.service.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0050n implements FileFilter {
    C0050n(RunnableC0053q runnableC0053q) {
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        String name = file.getName();
        return file.isFile() && !name.startsWith("vmd") && name.endsWith(".apk") && !name.endsWith(".tmp.apk");
    }
}
