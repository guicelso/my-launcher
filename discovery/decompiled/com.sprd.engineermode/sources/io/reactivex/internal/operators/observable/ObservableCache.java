package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes4.dex */
public final class ObservableCache<T> extends AbstractObservableWithUpstream<T, T> implements Observer<T> {
    static final CacheDisposable[] EMPTY = new CacheDisposable[0];
    static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
    final int capacityHint;
    volatile boolean done;
    Throwable error;
    final Node<T> head;
    final AtomicReference<CacheDisposable<T>[]> observers;
    final AtomicBoolean once;
    volatile long size;
    Node<T> tail;
    int tailOffset;

    public ObservableCache(Observable<T> source, int capacityHint) {
        super(source);
        this.capacityHint = capacityHint;
        this.once = new AtomicBoolean();
        Node<T> n = new Node<>(capacityHint);
        this.head = n;
        this.tail = n;
        this.observers = new AtomicReference<>(EMPTY);
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super T> t) {
        CacheDisposable<T> consumer = new CacheDisposable<>(t, this);
        t.onSubscribe(consumer);
        add(consumer);
        if (!this.once.get() && this.once.compareAndSet(false, true)) {
            this.source.subscribe(this);
        } else {
            replay(consumer);
        }
    }

    boolean isConnected() {
        return this.once.get();
    }

    boolean hasObservers() {
        return this.observers.get().length != 0;
    }

    long cachedEventCount() {
        return this.size;
    }

    void add(CacheDisposable<T> consumer) {
        CacheDisposable<T>[] current;
        CacheDisposable<T>[] next;
        do {
            current = this.observers.get();
            if (current == TERMINATED) {
                return;
            }
            int n = current.length;
            next = new CacheDisposable[n + 1];
            System.arraycopy(current, 0, next, 0, n);
            next[n] = consumer;
        } while (!this.observers.compareAndSet(current, next));
    }

    void remove(CacheDisposable<T> consumer) {
        CacheDisposable<T>[] current;
        CacheDisposable<T>[] next;
        do {
            current = this.observers.get();
            int n = current.length;
            if (n == 0) {
                return;
            }
            int j = -1;
            for (int i = 0; i < n; i++) {
                if (current[i] == consumer) {
                    j = i;
                    break;
                }
            }
            if (j < 0) {
                return;
            }
            if (n == 1) {
                next = EMPTY;
            } else {
                CacheDisposable<T>[] next2 = new CacheDisposable[n - 1];
                System.arraycopy(current, 0, next2, 0, j);
                System.arraycopy(current, j + 1, next2, j, (n - j) - 1);
                next = next2;
            }
        } while (!this.observers.compareAndSet(current, next));
    }

    void replay(CacheDisposable<T> cacheDisposable) {
        if (cacheDisposable.getAndIncrement() != 0) {
            return;
        }
        int iAddAndGet = 1;
        long j = cacheDisposable.index;
        int i = cacheDisposable.offset;
        Node<T> node = cacheDisposable.node;
        Observer<? super T> observer = cacheDisposable.downstream;
        int i2 = this.capacityHint;
        while (!cacheDisposable.disposed) {
            boolean z = this.done;
            boolean z2 = this.size == j;
            if (z && z2) {
                cacheDisposable.node = null;
                Throwable th = this.error;
                if (th != null) {
                    observer.onError(th);
                    return;
                } else {
                    observer.onComplete();
                    return;
                }
            }
            if (!z2) {
                if (i == i2) {
                    node = node.next;
                    i = 0;
                }
                observer.onNext(node.values[i]);
                i++;
                j++;
            } else {
                cacheDisposable.index = j;
                cacheDisposable.offset = i;
                cacheDisposable.node = node;
                iAddAndGet = cacheDisposable.addAndGet(-iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }
        cacheDisposable.node = null;
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable d) {
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        int tailOffset = this.tailOffset;
        if (tailOffset == this.capacityHint) {
            Node<T> n = new Node<>(tailOffset);
            n.values[0] = t;
            this.tailOffset = 1;
            this.tail.next = n;
            this.tail = n;
        } else {
            this.tail.values[tailOffset] = t;
            this.tailOffset = tailOffset + 1;
        }
        this.size++;
        for (CacheDisposable<T> consumer : this.observers.get()) {
            replay(consumer);
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable t) {
        this.error = t;
        this.done = true;
        for (CacheDisposable<T> consumer : this.observers.getAndSet(TERMINATED)) {
            replay(consumer);
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        this.done = true;
        for (CacheDisposable<T> consumer : this.observers.getAndSet(TERMINATED)) {
            replay(consumer);
        }
    }

    static final class CacheDisposable<T> extends AtomicInteger implements Disposable {
        private static final long serialVersionUID = 6770240836423125754L;
        volatile boolean disposed;
        final Observer<? super T> downstream;
        long index;
        Node<T> node;
        int offset;
        final ObservableCache<T> parent;

        CacheDisposable(Observer<? super T> downstream, ObservableCache<T> parent) {
            this.downstream = downstream;
            this.parent = parent;
            this.node = parent.head;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.parent.remove(this);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }

    static final class Node<T> {
        volatile Node<T> next;
        final T[] values;

        Node(int i) {
            this.values = (T[]) new Object[i];
        }
    }
}
