package com.tw.core.f;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private static volatile f im;
    private final ExecutorService ik = Executors.newCachedThreadPool();
    private final ExecutorService il = Executors.newCachedThreadPool();

    public static f nh() {
        if (im == null) {
            synchronized (f.class) {
                if (im == null) {
                    im = new f();
                }
            }
        }
        return im;
    }

    public void ni(b bVar) {
        this.ik.execute(bVar);
    }

    public void nj(g gVar) {
        this.il.execute(gVar);
    }
}
