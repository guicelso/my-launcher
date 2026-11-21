package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes4.dex */
public final class FlowablePublishMulticast<T, R> extends AbstractFlowableWithUpstream<T, R> {
    final boolean delayError;
    final int prefetch;
    final Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector;

    public FlowablePublishMulticast(Flowable<T> source, Function<? super Flowable<T>, ? extends Publisher<? extends R>> selector, int prefetch, boolean delayError) {
        super(source);
        this.selector = selector;
        this.prefetch = prefetch;
        this.delayError = delayError;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super R> s) {
        MulticastProcessor<T> mp = new MulticastProcessor<>(this.prefetch, this.delayError);
        try {
            Publisher<? extends R> other = (Publisher) ObjectHelper.requireNonNull(this.selector.apply(mp), "selector returned a null Publisher");
            OutputCanceller<R> out = new OutputCanceller<>(s, mp);
            other.subscribe(out);
            this.source.subscribe((FlowableSubscriber) mp);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptySubscription.error(ex, s);
        }
    }

    static final class OutputCanceller<R> implements FlowableSubscriber<R>, Subscription {
        final Subscriber<? super R> downstream;
        final MulticastProcessor<?> processor;
        Subscription upstream;

        OutputCanceller(Subscriber<? super R> actual, MulticastProcessor<?> processor) {
            this.downstream = actual;
            this.processor = processor;
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(R t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            this.downstream.onError(t);
            this.processor.dispose();
        }

        public void onComplete() {
            this.downstream.onComplete();
            this.processor.dispose();
        }

        public void request(long n) {
            this.upstream.request(n);
        }

        public void cancel() {
            this.upstream.cancel();
            this.processor.dispose();
        }
    }

    static final class MulticastProcessor<T> extends Flowable<T> implements FlowableSubscriber<T>, Disposable {
        static final MulticastSubscription[] EMPTY = new MulticastSubscription[0];
        static final MulticastSubscription[] TERMINATED = new MulticastSubscription[0];
        int consumed;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        volatile SimpleQueue<T> queue;
        int sourceMode;
        final AtomicInteger wip = new AtomicInteger();
        final AtomicReference<Subscription> upstream = new AtomicReference<>();
        final AtomicReference<MulticastSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);

        MulticastProcessor(int prefetch, boolean delayError) {
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            this.delayError = delayError;
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.setOnce(this.upstream, s)) {
                if (s instanceof QueueSubscription) {
                    QueueSubscription<T> qs = (QueueSubscription) s;
                    int m = qs.requestFusion(3);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qs;
                        this.done = true;
                        drain();
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qs;
                        QueueDrainHelper.request(s, this.prefetch);
                        return;
                    }
                }
                this.queue = QueueDrainHelper.createQueue(this.prefetch);
                QueueDrainHelper.request(s, this.prefetch);
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SimpleQueue<T> q;
            SubscriptionHelper.cancel(this.upstream);
            if (this.wip.getAndIncrement() == 0 && (q = this.queue) != null) {
                q.clear();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.upstream.get() == SubscriptionHelper.CANCELLED;
        }

        public void onNext(T t) {
            if (this.done) {
                return;
            }
            if (this.sourceMode == 0 && !this.queue.offer(t)) {
                this.upstream.get().cancel();
                onError(new MissingBackpressureException());
            } else {
                drain();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
            this.done = true;
            drain();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        boolean add(MulticastSubscription<T> s) {
            MulticastSubscription<T>[] current;
            MulticastSubscription<T>[] next;
            do {
                current = this.subscribers.get();
                if (current == TERMINATED) {
                    return false;
                }
                int n = current.length;
                next = new MulticastSubscription[n + 1];
                System.arraycopy(current, 0, next, 0, n);
                next[n] = s;
            } while (!this.subscribers.compareAndSet(current, next));
            return true;
        }

        void remove(MulticastSubscription<T> s) {
            MulticastSubscription<T>[] current;
            MulticastSubscription<T>[] next;
            do {
                current = this.subscribers.get();
                int n = current.length;
                if (n == 0) {
                    return;
                }
                int j = -1;
                for (int i = 0; i < n; i++) {
                    if (current[i] == s) {
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
                    MulticastSubscription<T>[] next2 = new MulticastSubscription[n - 1];
                    System.arraycopy(current, 0, next2, 0, j);
                    System.arraycopy(current, j + 1, next2, j, (n - j) - 1);
                    next = next2;
                }
            } while (!this.subscribers.compareAndSet(current, next));
        }

        @Override // io.reactivex.Flowable
        protected void subscribeActual(Subscriber<? super T> s) {
            MulticastSubscription<T> ms = new MulticastSubscription<>(s, this);
            s.onSubscribe(ms);
            if (add(ms)) {
                if (ms.isCancelled()) {
                    remove(ms);
                    return;
                } else {
                    drain();
                    return;
                }
            }
            Throwable ex = this.error;
            if (ex != null) {
                s.onError(ex);
            } else {
                s.onComplete();
            }
        }

        void drain() {
            Throwable ex;
            Throwable ex2;
            if (this.wip.getAndIncrement() != 0) {
                return;
            }
            SimpleQueue<T> q = this.queue;
            int upstreamConsumed = this.consumed;
            int localLimit = this.limit;
            boolean canRequest = this.sourceMode != 1;
            AtomicReference<MulticastSubscription<T>[]> subs = this.subscribers;
            MulticastSubscription<T>[] array = subs.get();
            int upstreamConsumed2 = 1;
            int upstreamConsumed3 = upstreamConsumed;
            while (true) {
                int n = array.length;
                if (q != null && n != 0) {
                    long r = Long.MAX_VALUE;
                    int n2 = n;
                    for (MulticastSubscription<T> ms : array) {
                        long u = ms.get() - ms.emitted;
                        if (u == Long.MIN_VALUE) {
                            n2--;
                        } else if (r > u) {
                            r = u;
                        }
                    }
                    if (n2 == 0) {
                        r = 0;
                    }
                    while (true) {
                        if (r != 0) {
                            if (isDisposed()) {
                                q.clear();
                                return;
                            }
                            boolean d = this.done;
                            if (d && !this.delayError && (ex2 = this.error) != null) {
                                errorAll(ex2);
                                return;
                            }
                            try {
                                T v = q.poll();
                                boolean empty = v == null;
                                if (d && empty) {
                                    Throwable ex3 = this.error;
                                    if (ex3 != null) {
                                        errorAll(ex3);
                                        return;
                                    } else {
                                        completeAll();
                                        return;
                                    }
                                }
                                if (!empty) {
                                    int length = array.length;
                                    boolean subscribersChange = false;
                                    int i = 0;
                                    while (i < length) {
                                        boolean d2 = d;
                                        MulticastSubscription<T> ms2 = array[i];
                                        long msr = ms2.get();
                                        if (msr != Long.MIN_VALUE) {
                                            if (msr != LongCompanionObject.MAX_VALUE) {
                                                ms2.emitted++;
                                            }
                                            ms2.downstream.onNext(v);
                                        } else {
                                            empty = empty;
                                            n2 = n2;
                                            subscribersChange = true;
                                        }
                                        i++;
                                        d = d2;
                                        n2 = n2;
                                        empty = empty;
                                    }
                                    int n3 = n2;
                                    r--;
                                    if (canRequest && (upstreamConsumed3 = upstreamConsumed3 + 1) == localLimit) {
                                        upstreamConsumed3 = 0;
                                        this.upstream.get().request(localLimit);
                                    }
                                    MulticastSubscription<T>[] freshArray = subs.get();
                                    if (subscribersChange || freshArray != array) {
                                        array = freshArray;
                                        upstreamConsumed3 = upstreamConsumed3;
                                    } else {
                                        n2 = n3;
                                    }
                                }
                            } catch (Throwable th) {
                                Exceptions.throwIfFatal(th);
                                SubscriptionHelper.cancel(this.upstream);
                                errorAll(th);
                                return;
                            }
                        }
                        if (r == 0) {
                            if (isDisposed()) {
                                q.clear();
                                return;
                            }
                            boolean d3 = this.done;
                            if (d3 && !this.delayError && (ex = this.error) != null) {
                                errorAll(ex);
                                return;
                            }
                            if (d3 && q.isEmpty()) {
                                Throwable ex4 = this.error;
                                if (ex4 != null) {
                                    errorAll(ex4);
                                    return;
                                } else {
                                    completeAll();
                                    return;
                                }
                            }
                        }
                        upstreamConsumed3 = upstreamConsumed3;
                    }
                }
                this.consumed = upstreamConsumed3;
                upstreamConsumed2 = this.wip.addAndGet(-upstreamConsumed2);
                if (upstreamConsumed2 != 0) {
                    if (q == null) {
                        q = this.queue;
                    }
                    MulticastSubscription<T>[] array2 = subs.get();
                    array = array2;
                } else {
                    return;
                }
            }
        }

        void errorAll(Throwable ex) {
            for (MulticastSubscription<T> ms : this.subscribers.getAndSet(TERMINATED)) {
                if (ms.get() != Long.MIN_VALUE) {
                    ms.downstream.onError(ex);
                }
            }
        }

        void completeAll() {
            for (MulticastSubscription<T> ms : this.subscribers.getAndSet(TERMINATED)) {
                if (ms.get() != Long.MIN_VALUE) {
                    ms.downstream.onComplete();
                }
            }
        }
    }

    static final class MulticastSubscription<T> extends AtomicLong implements Subscription {
        private static final long serialVersionUID = 8664815189257569791L;
        final Subscriber<? super T> downstream;
        long emitted;
        final MulticastProcessor<T> parent;

        MulticastSubscription(Subscriber<? super T> actual, MulticastProcessor<T> parent) {
            this.downstream = actual;
            this.parent = parent;
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.addCancel(this, n);
                this.parent.drain();
            }
        }

        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.drain();
            }
        }

        public boolean isCancelled() {
            return get() == Long.MIN_VALUE;
        }
    }
}
