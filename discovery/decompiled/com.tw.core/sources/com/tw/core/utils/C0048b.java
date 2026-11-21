package com.tw.core.utils;

import android.os.Looper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: renamed from: com.tw.core.utils.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0048b {
    public static volatile C0048b r;
    private Map q = new HashMap();

    private C0048b() {
    }

    public static ExecutorService p() {
        ExecutorService executorService = (ExecutorService) s().q().get(1);
        if (executorService != null) {
            return executorService;
        }
        ExecutorService executorServiceNewCachedThreadPool = Executors.newCachedThreadPool();
        s().q().put(1, executorServiceNewCachedThreadPool);
        return executorServiceNewCachedThreadPool;
    }

    public static boolean r() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static C0048b s() {
        if (r == null) {
            synchronized (C0048b.class) {
                if (r == null) {
                    r = new C0048b();
                }
            }
        }
        return r;
    }

    public Map q() {
        return this.q;
    }
}
