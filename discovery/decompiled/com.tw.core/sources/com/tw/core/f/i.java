package com.tw.core.f;

import com.tw.core.utils.B;
import java.io.File;
import java.io.FileFilter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class i implements FileFilter {
    final /* synthetic */ b it;
    final /* synthetic */ List iu;

    i(b bVar, List list) {
        this.it = bVar;
        this.iu = list;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        long jLastModified = file.lastModified();
        B.ci("BaseLogCollector", "file name %s lastModified %s current %s", file.getName(), Long.valueOf(jLastModified), Long.valueOf(System.currentTimeMillis()));
        if (System.currentTimeMillis() - jLastModified < 259200000) {
            return true;
        }
        this.iu.add(file);
        return false;
    }
}
