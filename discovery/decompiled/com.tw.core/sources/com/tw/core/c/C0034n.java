package com.tw.core.c;

import java.io.File;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.tw.core.c.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0034n extends com.tw.core.i.b implements com.tw.core.h.d {
    private static final String eA = com.tw.core.utils.E.cz() + File.separator + "log";

    private void kE() {
        File file = new File(eA);
        if (com.tw.core.utils.E.cC(file)) {
            File[] fileArrListFiles = file.listFiles();
            ArrayList<File> arrayList = new ArrayList();
            for (File file2 : fileArrListFiles) {
                long jLastModified = file2.lastModified();
                com.tw.core.utils.B.ci("AppLogUploadManager", "file name %s lastModified %s current %s", file2.getName(), Long.valueOf(jLastModified), Long.valueOf(System.currentTimeMillis()));
                if (System.currentTimeMillis() - jLastModified >= 259200000) {
                    arrayList.add(file2);
                }
            }
            for (File file3 : arrayList) {
                com.tw.core.utils.B.ci("AppLogUploadManager", "deleteExpiredLog %s %s", file3.getName(), Boolean.valueOf(file3.delete()));
            }
        }
    }

    @Override // com.tw.core.h.d
    public void jb(com.tw.core.h.e eVar) {
        com.tw.core.utils.B.ci("AppLogUploadManager", "emqMessageChanged %s", eVar);
        try {
            kE();
            com.tw.core.f.h.ns(eVar.jl);
            File file = new File(eA, "twcore.df");
            if (file.exists()) {
                com.tw.core.utils.H.cV();
                File file2 = new File(file.getParent(), eVar.jl);
                if (!file2.exists()) {
                    com.tw.core.utils.E.cJ(file, eVar.jl);
                }
                if (file2.exists()) {
                    com.tw.core.f.g gVar = new com.tw.core.f.g(eVar.jl, file2);
                    gVar.nk(com.tw.core.d.a.hb);
                    gVar.nl();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tw.core.c.AbstractC0030j
    public String jd() {
        return null;
    }

    @Override // com.tw.core.h.d
    public String je() {
        return "0702";
    }

    @Override // com.tw.core.i.a
    public String jf() {
        return "";
    }

    @Override // com.tw.core.i.b, com.tw.core.e.c
    public void onCreate() {
        super.onCreate();
        com.tw.core.h.a.get().nK(this);
    }
}
