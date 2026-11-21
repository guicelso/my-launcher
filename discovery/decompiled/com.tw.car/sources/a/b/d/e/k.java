package a.b.d.e;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class k {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public HandlerThread f114b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Handler f115c;
    public final int f;
    public final int g;
    public final String h;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f113a = new Object();
    public Handler.Callback e = new g(this);
    public int d = 0;

    public interface a<T> {
        void a(T t);
    }

    public k(String str, int i, int i2) {
        this.h = str;
        this.g = i;
        this.f = i2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public <T> T a(Callable<T> callable, int i) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionNewCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        b(new j(this, atomicReference, callable, reentrantLock, atomicBoolean, conditionNewCondition));
        reentrantLock.lock();
        try {
            if (!atomicBoolean.get()) {
                T t = (T) atomicReference.get();
                reentrantLock.unlock();
                return t;
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos(i);
            do {
                try {
                    nanos = conditionNewCondition.awaitNanos(nanos);
                } catch (InterruptedException unused) {
                }
                if (!atomicBoolean.get()) {
                    T t2 = (T) atomicReference.get();
                    reentrantLock.unlock();
                    return t2;
                }
            } while (nanos > 0);
            throw new InterruptedException("timeout");
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public void a() {
        synchronized (this.f113a) {
            if (this.f115c.hasMessages(1)) {
                return;
            }
            this.f114b.quit();
            this.f114b = null;
            this.f115c = null;
        }
    }

    public void a(Runnable runnable) {
        runnable.run();
        synchronized (this.f113a) {
            this.f115c.removeMessages(0);
            this.f115c.sendMessageDelayed(this.f115c.obtainMessage(0), this.f);
        }
    }

    public <T> void a(Callable<T> callable, a<T> aVar) {
        b(new i(this, callable, new Handler(), aVar));
    }

    public final void b(Runnable runnable) {
        synchronized (this.f113a) {
            if (this.f114b == null) {
                this.f114b = new HandlerThread(this.h, this.g);
                this.f114b.start();
                this.f115c = new Handler(this.f114b.getLooper(), this.e);
                this.d++;
            }
            this.f115c.removeMessages(0);
            this.f115c.sendMessage(this.f115c.obtainMessage(1, runnable));
        }
    }
}
