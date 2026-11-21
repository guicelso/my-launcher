package com.tw.service;

import android.app.ActivityManager;

/* JADX INFO: renamed from: com.tw.service.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class RunnableC0045i implements Runnable {
    final /* synthetic */ HandlerC0060x Rc;
    final /* synthetic */ ActivityManager.MemoryInfo vk;

    RunnableC0045i(HandlerC0060x handlerC0060x, ActivityManager.MemoryInfo memoryInfo) {
        this.Rc = handlerC0060x;
        this.vk = memoryInfo;
    }

    @Override // java.lang.Runnable
    public void run() {
        System.gc();
        this.Rc.n.mHandler.post(new RunnableC0044h(this));
    }
}
