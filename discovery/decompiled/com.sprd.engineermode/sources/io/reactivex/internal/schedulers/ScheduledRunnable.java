package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* JADX INFO: loaded from: classes4.dex */
public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Runnable, Callable<Object>, Disposable {
    static final int FUTURE_INDEX = 1;
    static final int PARENT_INDEX = 0;
    static final int THREAD_INDEX = 2;
    private static final long serialVersionUID = -6120223772001106981L;
    final Runnable actual;
    static final Object PARENT_DISPOSED = new Object();
    static final Object SYNC_DISPOSED = new Object();
    static final Object ASYNC_DISPOSED = new Object();
    static final Object DONE = new Object();

    public ScheduledRunnable(Runnable actual, DisposableContainer parent) {
        super(3);
        this.actual = actual;
        lazySet(0, parent);
    }

    @Override // java.util.concurrent.Callable
    public Object call() {
        run();
        return null;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object o;
        Object obj;
        lazySet(2, Thread.currentThread());
        try {
            this.actual.run();
        } catch (Throwable e) {
            try {
                RxJavaPlugins.onError(e);
            } finally {
                lazySet(2, null);
                Object o2 = get(0);
                if (o2 != PARENT_DISPOSED && compareAndSet(0, o2, DONE) && o2 != null) {
                    ((DisposableContainer) o2).delete(this);
                }
                do {
                    o = get(1);
                    if (o == SYNC_DISPOSED || o == ASYNC_DISPOSED) {
                        break;
                    }
                } while (!compareAndSet(1, o, DONE));
            }
        }
        lazySet(2, null);
        Object o3 = get(0);
        if (o3 != PARENT_DISPOSED && compareAndSet(0, o3, DONE) && o3 != null) {
            ((DisposableContainer) o3).delete(this);
        }
        do {
            o = get(1);
            Object obj2 = SYNC_DISPOSED;
            if (o == obj2) {
                return;
            } else {
                if (o == obj) {
                    return;
                }
            }
        } while (!compareAndSet(1, o, DONE));
    }

    public void setFuture(Future<?> f) {
        Object o;
        do {
            o = get(1);
            if (o == DONE) {
                return;
            }
            if (o == SYNC_DISPOSED) {
                f.cancel(false);
                return;
            } else if (o == ASYNC_DISPOSED) {
                f.cancel(true);
                return;
            }
        } while (!compareAndSet(1, o, f));
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Object o;
        Object obj;
        while (true) {
            Object o2 = get(1);
            if (o2 == DONE || o2 == SYNC_DISPOSED || o2 == ASYNC_DISPOSED) {
                break;
            }
            boolean async = get(2) != Thread.currentThread();
            if (compareAndSet(1, o2, async ? ASYNC_DISPOSED : SYNC_DISPOSED)) {
                if (o2 == null) {
                    break;
                }
                ((Future) o2).cancel(async);
                break;
            }
        }
        do {
            o = get(0);
            if (o == DONE || o == (obj = PARENT_DISPOSED) || o == null) {
                return;
            }
        } while (!compareAndSet(0, o, obj));
        ((DisposableContainer) o).delete(this);
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        Object o = get(0);
        return o == PARENT_DISPOSED || o == DONE;
    }
}
