package a.a.a.a;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class d extends e {
    private final Object mLock = new Object();
    private ExecutorService u = Executors.newFixedThreadPool(2);
    private volatile Handler v;

    @Override // a.a.a.a.e
    public void a(Runnable runnable) {
        this.u.execute(runnable);
    }

    @Override // a.a.a.a.e
    public void b(Runnable runnable) {
        if (this.v == null) {
            synchronized (this.mLock) {
                if (this.v == null) {
                    this.v = new Handler(Looper.getMainLooper());
                }
            }
        }
        this.v.post(runnable);
    }

    @Override // a.a.a.a.e
    public boolean e() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
