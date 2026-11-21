package com.unisoc.engineermode.core.utils;

import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes4.dex */
public class Watchdog {
    public static final String TAG = "WATCHDOG";
    private static final int WATCHDOG_TIME = 180;
    private String cmd;
    private Consumer<Object> consumer;
    private Object consumerObj;
    private volatile int food = 0;
    private Future<?> future;
    private String server;

    public Watchdog(String s, String c) {
        this.server = s;
        this.cmd = c;
    }

    void wantEat() {
        if (this.cmd == null) {
            Log.d(TAG, "invalid watching  " + this.server + " [" + this.cmd + "]");
            return;
        }
        this.food = 0;
        ExecutorService es = Executors.newSingleThreadExecutor();
        this.future = es.submit(new Runnable() { // from class: com.unisoc.engineermode.core.utils.-$$Lambda$Watchdog$V7vX-iOgmeEGt_vaLapAOgx7tG8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.run();
            }
        });
        es.shutdown();
    }

    public void feedFood() {
        this.food = 1;
        try {
            if (this.future != null) {
                this.future.cancel(true);
            }
        } catch (Exception e) {
            Log.w(TAG, "feedFood error" + e);
        }
    }

    public void setTimeoutCallback(Consumer<Object> callback, Object obj) {
        this.consumer = callback;
        this.consumerObj = obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void run() {
        Consumer<Object> consumer;
        for (int count = 0; count < WATCHDOG_TIME; count++) {
            try {
                if (1 == this.food) {
                    return;
                }
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                return;
            }
        }
        if (this.food == 0 && (consumer = this.consumer) != null) {
            consumer.accept(this.consumerObj);
        }
    }
}
