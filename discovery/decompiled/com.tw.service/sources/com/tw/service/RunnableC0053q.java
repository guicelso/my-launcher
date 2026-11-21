package com.tw.service;

import java.io.File;

/* JADX INFO: renamed from: com.tw.service.q, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0053q implements Runnable {
    final /* synthetic */ TWService n;

    RunnableC0053q(TWService tWService) {
        this.n = tWService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            File[] fileArrListFiles = new File("/sdcard/CarChooseAPK").listFiles(new C0050n(this));
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    this.n.pa.write(40730, 1, 0, "/system/bin/pm install -r " + file.getAbsolutePath());
                }
                this.n.mHandler.postDelayed(new RunnableC0051o(this), 7000L);
            }
        } catch (Exception unused) {
        }
        try {
            File[] fileArrListFiles2 = new File("/sdcard/.apk").listFiles(new C0052p(this));
            if (fileArrListFiles2 != null) {
                for (File file2 : fileArrListFiles2) {
                    if (this.n.j(file2.getAbsolutePath())) {
                        this.n.pa.write(40730, 1, 0, "/system/bin/pm install -r " + file2.getAbsolutePath());
                    }
                }
            }
        } catch (Exception unused2) {
        }
        this.n.pa.write(40730, 3, 0, "touch /data/preinstall");
        this.n.Ha = true;
        this.n.mHandler.sendEmptyMessageDelayed(65300, 4000L);
    }
}
