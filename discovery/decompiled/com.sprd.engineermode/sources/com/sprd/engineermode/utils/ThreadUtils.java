package com.sprd.engineermode.utils;

/* JADX INFO: loaded from: classes4.dex */
public class ThreadUtils {
    private static final String TAG = "ThreadUtils";

    public static void stopThread(Thread thread) {
        if (thread != null) {
            thread.interrupt();
        }
    }
}
