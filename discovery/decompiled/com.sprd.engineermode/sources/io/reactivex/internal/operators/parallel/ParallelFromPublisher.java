package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes4.dex */
public final class ParallelFromPublisher<T> extends ParallelFlowable<T> {
    final int parallelism;
    final int prefetch;
    final Publisher<? extends T> source;

    public ParallelFromPublisher(Publisher<? extends T> source, int parallelism, int prefetch) {
        this.source = source;
        this.parallelism = parallelism;
        this.prefetch = prefetch;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public int parallelism() {
        return this.parallelism;
    }

    @Override // io.reactivex.parallel.ParallelFlowable
    public void subscribe(Subscriber<? super T>[] subscribers) {
        if (!validate(subscribers)) {
            return;
        }
        this.source.subscribe(new ParallelDispatcher(subscribers, this.prefetch));
    }

    static final class ParallelDispatcher<T> extends AtomicInteger implements FlowableSubscriber<T> {
        private static final long serialVersionUID = -4470634016609963609L;
        volatile boolean cancelled;
        volatile boolean done;
        final long[] emissions;
        Throwable error;
        int index;
        final int limit;
        final int prefetch;
        int produced;
        SimpleQueue<T> queue;
        final AtomicLongArray requests;
        int sourceMode;
        final AtomicInteger subscriberCount = new AtomicInteger();
        final Subscriber<? super T>[] subscribers;
        Subscription upstream;

        ParallelDispatcher(Subscriber<? super T>[] subscribers, int prefetch) {
            this.subscribers = subscribers;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            int m = subscribers.length;
            this.requests = new AtomicLongArray(m + m + 1);
            this.requests.lazySet(m + m, m);
            this.emissions = new long[m];
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof QueueSubscription) {
                    QueueSubscription<T> qs = (QueueSubscription) s;
                    int m = qs.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.done = true;
                        setupSubscribers();
                        drain();
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qs;
                        setupSubscribers();
                        s.request(this.prefetch);
                        return;
                    }
                }
                this.queue = new SpscArrayQueue(this.prefetch);
                setupSubscribers();
                s.request(this.prefetch);
            }
        }

        void setupSubscribers() {
            Subscriber<? super T>[] subs = this.subscribers;
            int m = subs.length;
            for (int i = 0; i < m && !this.cancelled; i++) {
                this.subscriberCount.lazySet(i + 1);
                subs[i].onSubscribe(new RailSubscription(i, m));
            }
        }

        final class RailSubscription implements Subscription {
            final int j;
            final int m;

            RailSubscription(int j, int m) {
                this.j = j;
                this.m = m;
            }

            public void request(long n) {
                long r;
                long u;
                if (SubscriptionHelper.validate(n)) {
                    AtomicLongArray ra = ParallelDispatcher.this.requests;
                    do {
                        r = ra.get(this.j);
                        if (r == LongCompanionObject.MAX_VALUE) {
                            return;
                        } else {
                            u = BackpressureHelper.addCap(r, n);
                        }
                    } while (!ra.compareAndSet(this.j, r, u));
                    if (ParallelDispatcher.this.subscriberCount.get() == this.m) {
                        ParallelDispatcher.this.drain();
                    }
                }
            }

            public void cancel() {
                if (ParallelDispatcher.this.requests.compareAndSet(this.j + this.m, 0L, 1L)) {
                    ParallelDispatcher parallelDispatcher = ParallelDispatcher.this;
                    int i = this.m;
                    parallelDispatcher.cancel(i + i);
                }
            }
        }

        public void onNext(T t) {
            if (this.sourceMode == 0 && !this.queue.offer(t)) {
                this.upstream.cancel();
                onError(new MissingBackpressureException("Queue is full?"));
            } else {
                drain();
            }
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            drain();
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        void cancel(int m) {
            if (this.requests.decrementAndGet(m) == 0) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        void drainAsync() {
            int missed;
            Throwable ex;
            SimpleQueue<T> q = this.queue;
            Subscriber<? super T>[] a = this.subscribers;
            AtomicLongArray r = this.requests;
            long[] e = this.emissions;
            int n = e.length;
            int idx = this.index;
            int idx2 = this.produced;
            int missed2 = 1;
            int idx3 = idx;
            while (true) {
                int notReady = 0;
                int consumed = idx2;
                int idx4 = idx3;
                while (true) {
                    if (this.cancelled) {
                        q.clear();
                        return;
                    }
                    boolean d = this.done;
                    int i = 0;
                    if (d && (ex = this.error) != null) {
                        q.clear();
                        int length = a.length;
                        while (i < length) {
                            Subscriber<? super T> s = a[i];
                            s.onError(ex);
                            i++;
                        }
                        return;
                    }
                    boolean empty = q.isEmpty();
                    if (d && empty) {
                        int length2 = a.length;
                        while (i < length2) {
                            Subscriber<? super T> s2 = a[i];
                            s2.onComplete();
                            i++;
                        }
                        return;
                    }
                    if (!empty) {
                        long requestAtIndex = r.get(idx4);
                        long emissionAtIndex = e[idx4];
                        if (requestAtIndex == emissionAtIndex || r.get(n + idx4) != 0) {
                            r = r;
                            e = e;
                            notReady++;
                        } else {
                            try {
                                T v = q.poll();
                                if (v != null) {
                                    a[idx4].onNext(v);
                                    e[idx4] = emissionAtIndex + 1;
                                    consumed++;
                                    if (consumed == this.limit) {
                                        consumed = 0;
                                        this.upstream.request(consumed);
                                    }
                                    notReady = 0;
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                this.upstream.cancel();
                                int length3 = a.length;
                                while (i < length3) {
                                    Subscriber<? super T> s3 = a[i];
                                    s3.onError(th);
                                    i++;
                                }
                                return;
                            }
                        }
                        idx4++;
                        if (idx4 == n) {
                            idx4 = 0;
                        }
                        if (notReady == n) {
                            idx3 = idx4;
                            idx2 = consumed;
                            break;
                        } else {
                            r = r;
                            e = e;
                        }
                    }
                    r = r;
                    e = e;
                    idx3 = idx4;
                    idx2 = consumed;
                    break;
                }
                int w = get();
                if (w == missed2) {
                    this.index = idx3;
                    this.produced = idx2;
                    missed = addAndGet(-missed2);
                    if (missed == 0) {
                        return;
                    }
                } else {
                    missed = w;
                }
                missed2 = missed;
                r = r;
                e = e;
            }
        }

        void drainSync() {
            int missed;
            SimpleQueue<T> q = this.queue;
            Subscriber<? super T>[] a = this.subscribers;
            AtomicLongArray r = this.requests;
            long[] e = this.emissions;
            int n = e.length;
            int missed2 = 1;
            int w = this.index;
            while (true) {
                int notReady = 0;
                int idx = w;
                while (true) {
                    if (this.cancelled) {
                        q.clear();
                        return;
                    }
                    boolean empty = q.isEmpty();
                    if (empty) {
                        for (Subscriber<? super T> s : a) {
                            s.onComplete();
                        }
                        return;
                    }
                    long requestAtIndex = r.get(idx);
                    long emissionAtIndex = e[idx];
                    if (requestAtIndex != emissionAtIndex && r.get(n + idx) == 0) {
                        try {
                            T v = q.poll();
                            if (v == null) {
                                for (Subscriber<? super T> s2 : a) {
                                    s2.onComplete();
                                }
                                return;
                            }
                            a[idx].onNext(v);
                            e[idx] = emissionAtIndex + 1;
                            notReady = 0;
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            this.upstream.cancel();
                            int length = a.length;
                            int i = 0;
                            while (i < length) {
                                int i2 = length;
                                Subscriber<? super T> s3 = a[i];
                                s3.onError(th);
                                i++;
                                length = i2;
                            }
                            return;
                        }
                    } else {
                        notReady++;
                    }
                    idx++;
                    if (idx == n) {
                        idx = 0;
                    }
                    if (notReady == n) {
                        break;
                    } else {
                        q = q;
                    }
                }
                int w2 = get();
                if (w2 == missed2) {
                    this.index = idx;
                    missed = addAndGet(-missed2);
                    if (missed == 0) {
                        return;
                    }
                } else {
                    missed = w2;
                }
                missed2 = missed;
                w = idx;
                q = q;
            }
        }

        void drain() {
            if (getAndIncrement() != 0) {
                return;
            }
            if (this.sourceMode == 1) {
                drainSync();
            } else {
                drainAsync();
            }
        }
    }
}
