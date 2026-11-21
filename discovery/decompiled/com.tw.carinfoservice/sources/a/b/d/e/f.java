package a.b.d.e;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public HandlerThread f102b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Handler f103c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f101a = new Object();
    public Handler.Callback e = new a();
    public int d = 0;

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                f fVar = f.this;
                synchronized (fVar.f101a) {
                    if (!fVar.f103c.hasMessages(1)) {
                        fVar.f102b.quit();
                        fVar.f102b = null;
                        fVar.f103c = null;
                    }
                }
                return true;
            }
            if (i != 1) {
                return true;
            }
            f fVar2 = f.this;
            Runnable runnable = (Runnable) message.obj;
            Objects.requireNonNull(fVar2);
            runnable.run();
            synchronized (fVar2.f101a) {
                fVar2.f103c.removeMessages(0);
                Handler handler = fVar2.f103c;
                handler.sendMessageDelayed(handler.obtainMessage(0), 10000);
            }
            return true;
        }
    }

    public class b implements Runnable {

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final /* synthetic */ AtomicReference f105b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final /* synthetic */ Callable f106c;
        public final /* synthetic */ ReentrantLock d;
        public final /* synthetic */ AtomicBoolean e;
        public final /* synthetic */ Condition f;

        public b(f fVar, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
            this.f105b = atomicReference;
            this.f106c = callable;
            this.d = reentrantLock;
            this.e = atomicBoolean;
            this.f = condition;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.f105b.set(this.f106c.call());
            } catch (Exception unused) {
            }
            this.d.lock();
            try {
                this.e.set(false);
                this.f.signal();
            } finally {
                this.d.unlock();
            }
        }
    }

    public interface c<T> {
        void a(T t);
    }

    public f(String str, int i, int i2) {
    }

    public final void a(Runnable runnable) {
        synchronized (this.f101a) {
            if (this.f102b == null) {
                HandlerThread handlerThread = new HandlerThread("fonts", 10);
                this.f102b = handlerThread;
                handlerThread.start();
                this.f103c = new Handler(this.f102b.getLooper(), this.e);
                this.d++;
            }
            this.f103c.removeMessages(0);
            Handler handler = this.f103c;
            handler.sendMessage(handler.obtainMessage(1, runnable));
        }
    }

    public <T> T b(Callable<T> callable, int i) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditionNewCondition = reentrantLock.newCondition();
        AtomicReference atomicReference = new AtomicReference();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        a(new b(this, atomicReference, callable, reentrantLock, atomicBoolean, conditionNewCondition));
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
}
