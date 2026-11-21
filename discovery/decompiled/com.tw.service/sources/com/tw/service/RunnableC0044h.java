package com.tw.service;

import android.app.ActivityManager;
import android.widget.Toast;
import java.util.Locale;

/* JADX INFO: renamed from: com.tw.service.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0044h implements Runnable {
    final /* synthetic */ RunnableC0045i be;

    RunnableC0044h(RunnableC0045i runnableC0045i) {
        this.be = runnableC0045i;
    }

    @Override // java.lang.Runnable
    public void run() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        this.be.Rc.n.tb.getMemoryInfo(memoryInfo);
        Toast.makeText(this.be.Rc.n, String.format(Locale.US, this.be.Rc.n.getString(R.string.release) + " %dMB " + this.be.Rc.n.getString(R.string.memory), Long.valueOf(((memoryInfo.availMem - this.be.vk.availMem) / 1024) / 1024)), 0).show();
    }
}
