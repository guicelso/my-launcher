package com.tw.core.utils;

/* JADX INFO: loaded from: classes.dex */
final class al implements Runnable {
    al() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(10000L);
            C.cr("1");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
