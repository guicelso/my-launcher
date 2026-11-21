package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.LongCompanionObject;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes4.dex */
public final class FlowableFlatMap<T, U> extends AbstractFlowableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends Publisher<? extends U>> mapper;
    final int maxConcurrency;

    public FlowableFlatMap(Flowable<T> source, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        super(source);
        this.mapper = mapper;
        this.delayErrors = delayErrors;
        this.maxConcurrency = maxConcurrency;
        this.bufferSize = bufferSize;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super U> s) {
        if (FlowableScalarXMap.tryScalarXMapSubscribe(this.source, s, this.mapper)) {
            return;
        }
        this.source.subscribe((FlowableSubscriber) subscribe(s, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize));
    }

    public static <T, U> FlowableSubscriber<T> subscribe(Subscriber<? super U> s, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        return new MergeSubscriber(s, mapper, delayErrors, maxConcurrency, bufferSize);
    }

    static final class MergeSubscriber<T, U> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final Subscriber<? super U> downstream;
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends Publisher<? extends U>> mapper;
        final int maxConcurrency;
        volatile SimplePlainQueue<U> queue;
        int scalarEmitted;
        final int scalarLimit;
        long uniqueId;
        Subscription upstream;
        static final InnerSubscriber<?, ?>[] EMPTY = new InnerSubscriber[0];
        static final InnerSubscriber<?, ?>[] CANCELLED = new InnerSubscriber[0];
        final AtomicThrowable errs = new AtomicThrowable();
        final AtomicReference<InnerSubscriber<?, ?>[]> subscribers = new AtomicReference<>();
        final AtomicLong requested = new AtomicLong();

        MergeSubscriber(Subscriber<? super U> actual, Function<? super T, ? extends Publisher<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
            this.downstream = actual;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.maxConcurrency = maxConcurrency;
            this.bufferSize = bufferSize;
            this.scalarLimit = Math.max(1, maxConcurrency >> 1);
            this.subscribers.lazySet(EMPTY);
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                if (!this.cancelled) {
                    int i = this.maxConcurrency;
                    if (i == Integer.MAX_VALUE) {
                        s.request(LongCompanionObject.MAX_VALUE);
                    } else {
                        s.request(i);
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onNext(T t) {
            if (this.done) {
                return;
            }
            try {
                Callable callable = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                if (callable instanceof Callable) {
                    try {
                        Object objCall = callable.call();
                        if (objCall != null) {
                            tryEmitScalar(objCall);
                            return;
                        }
                        if (this.maxConcurrency == Integer.MAX_VALUE || this.cancelled) {
                            return;
                        }
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                            return;
                        }
                        return;
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.errs.addThrowable(ex);
                        drain();
                        return;
                    }
                }
                long j = this.uniqueId;
                this.uniqueId = 1 + j;
                InnerSubscriber<T, U> inner = new InnerSubscriber<>(this, j);
                if (addInner(inner)) {
                    callable.subscribe(inner);
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.upstream.cancel();
                onError(e);
            }
        }

        boolean addInner(InnerSubscriber<T, U> innerSubscriber) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber[] innerSubscriberArr2;
            do {
                innerSubscriberArr = this.subscribers.get();
                if (innerSubscriberArr == CANCELLED) {
                    innerSubscriber.dispose();
                    return false;
                }
                int length = innerSubscriberArr.length;
                innerSubscriberArr2 = new InnerSubscriber[length + 1];
                System.arraycopy(innerSubscriberArr, 0, innerSubscriberArr2, 0, length);
                innerSubscriberArr2[length] = innerSubscriber;
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, (InnerSubscriber<?, ?>[]) innerSubscriberArr2));
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void removeInner(InnerSubscriber<T, U> inner) {
            InnerSubscriber<?, ?>[] innerSubscriberArr;
            InnerSubscriber<?, ?>[] b;
            do {
                innerSubscriberArr = this.subscribers.get();
                int n = innerSubscriberArr.length;
                if (n == 0) {
                    return;
                }
                int j = -1;
                for (int i = 0; i < n; i++) {
                    if (innerSubscriberArr[i] == inner) {
                        j = i;
                        break;
                    }
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = EMPTY;
                } else {
                    InnerSubscriber<?, ?>[] b2 = new InnerSubscriber[n - 1];
                    System.arraycopy(innerSubscriberArr, 0, b2, 0, j);
                    System.arraycopy(innerSubscriberArr, j + 1, b2, j, (n - j) - 1);
                    b = b2;
                }
            } while (!this.subscribers.compareAndSet(innerSubscriberArr, b));
        }

        SimpleQueue<U> getMainQueue() {
            SimplePlainQueue<U> q = this.queue;
            if (q == null) {
                int i = this.maxConcurrency;
                if (i == Integer.MAX_VALUE) {
                    q = new SpscLinkedArrayQueue(this.bufferSize);
                } else {
                    q = new SpscArrayQueue(i);
                }
                this.queue = q;
            }
            return q;
        }

        void tryEmitScalar(U value) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long r = this.requested.get();
                SimpleQueue<U> q = this.queue;
                if (r != 0 && (q == null || q.isEmpty())) {
                    this.downstream.onNext(value);
                    if (r != LongCompanionObject.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    if (this.maxConcurrency != Integer.MAX_VALUE && !this.cancelled) {
                        int i = this.scalarEmitted + 1;
                        this.scalarEmitted = i;
                        int i2 = this.scalarLimit;
                        if (i == i2) {
                            this.scalarEmitted = 0;
                            this.upstream.request(i2);
                        }
                    }
                } else {
                    if (q == null) {
                        q = getMainQueue();
                    }
                    if (!q.offer(value)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else if (!getMainQueue().offer(value)) {
                onError(new IllegalStateException("Scalar queue full?!"));
                return;
            } else if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        SimpleQueue<U> getInnerQueue(InnerSubscriber<T, U> inner) {
            SimpleQueue<U> q = inner.queue;
            if (q == null) {
                SimpleQueue<U> q2 = new SpscArrayQueue<>(this.bufferSize);
                inner.queue = q2;
                return q2;
            }
            return q;
        }

        void tryEmit(U value, InnerSubscriber<T, U> inner) {
            if (get() == 0 && compareAndSet(0, 1)) {
                long r = this.requested.get();
                SimpleQueue<U> q = inner.queue;
                if (r != 0 && (q == null || q.isEmpty())) {
                    this.downstream.onNext(value);
                    if (r != LongCompanionObject.MAX_VALUE) {
                        this.requested.decrementAndGet();
                    }
                    inner.requestMore(1L);
                } else {
                    if (q == null) {
                        q = getInnerQueue(inner);
                    }
                    if (!q.offer(value)) {
                        onError(new MissingBackpressureException("Inner queue full?!"));
                        return;
                    }
                }
                if (decrementAndGet() == 0) {
                    return;
                }
            } else {
                SimpleQueue<U> q2 = inner.queue;
                if (q2 == null) {
                    q2 = new SpscArrayQueue(this.bufferSize);
                    inner.queue = q2;
                }
                if (!q2.offer(value)) {
                    onError(new MissingBackpressureException("Inner queue full?!"));
                    return;
                } else if (getAndIncrement() != 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
            } else if (this.errs.addThrowable(t)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(t);
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            SimpleQueue<U> q;
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                disposeAll();
                if (getAndIncrement() == 0 && (q = this.queue) != null) {
                    q.clear();
                }
            }
        }

        void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* JADX WARN: Code duplicated, block: B:100:0x017a  */
        /* JADX WARN: Code duplicated, block: B:102:0x0189  */
        /* JADX WARN: Code duplicated, block: B:123:0x01d2  */
        /* JADX WARN: Code duplicated, block: B:125:0x01d6  */
        /* JADX WARN: Code duplicated, block: B:141:0x0117 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:146:0x00ea A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:148:0x0128 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:160:0x01e2 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:161:0x01cb A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:164:0x01d7 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:165:0x00f9 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:168:0x0166 A[EDGE_INSN: B:168:0x0166->B:96:0x0166 BREAK  A[LOOP:5: B:78:0x010f->B:86:0x0129], SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:169:0x0164 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:67:0x00e4  */
        /* JADX WARN: Code duplicated, block: B:70:0x00eb  */
        /* JADX WARN: Code duplicated, block: B:74:0x00f5  */
        /* JADX WARN: Code duplicated, block: B:77:0x0103  */
        /* JADX WARN: Code duplicated, block: B:83:0x011f  */
        /* JADX WARN: Code duplicated, block: B:86:0x0129 A[LOOP:5: B:78:0x010f->B:86:0x0129, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:98:0x016c A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:99:0x016e  */
        /* JADX WARN: Multi-variable type inference failed */
        void drainLoop() {
            boolean innerCompleted;
            long r;
            long replenishMain;
            boolean d;
            int index;
            int i;
            int j;
            long r2;
            InnerSubscriber<T, U> is;
            U o;
            SimpleQueue<U> q;
            boolean d2;
            SimplePlainQueue<U> svq;
            boolean innerDone;
            U o2;
            long produced;
            SimpleQueue<U> q2;
            long r3;
            Subscriber<? super U> child = this.downstream;
            int missed = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> svq2 = this.queue;
                long r4 = this.requested.get();
                boolean unbounded = r4 == LongCompanionObject.MAX_VALUE;
                long replenishMain2 = 0;
                long j2 = 1;
                if (svq2 != null) {
                    while (true) {
                        long scalarEmission = 0;
                        U o3 = null;
                        while (r4 != 0) {
                            U o4 = svq2.poll();
                            if (checkTerminate()) {
                                return;
                            }
                            if (o4 == null) {
                                o3 = o4;
                                break;
                            }
                            child.onNext(o4);
                            replenishMain2 += j2;
                            scalarEmission += j2;
                            r4 -= j2;
                            o3 = o4;
                        }
                        if (scalarEmission != 0) {
                            if (unbounded) {
                                r4 = LongCompanionObject.MAX_VALUE;
                            } else {
                                r4 = this.requested.addAndGet(-scalarEmission);
                            }
                        }
                        if (r4 == 0 || o3 == null) {
                            break;
                        } else {
                            j2 = 1;
                        }
                    }
                }
                boolean d3 = this.done;
                SimplePlainQueue<U> svq3 = this.queue;
                InnerSubscriber<?, ?>[] innerSubscriberArr = this.subscribers.get();
                int n = innerSubscriberArr.length;
                if (d3 && ((svq3 == null || svq3.isEmpty()) && n == 0)) {
                    Throwable ex = this.errs.terminate();
                    if (ex != ExceptionHelper.TERMINATED) {
                        if (ex == null) {
                            child.onComplete();
                            return;
                        } else {
                            child.onError(ex);
                            return;
                        }
                    }
                    return;
                }
                boolean innerCompleted2 = false;
                if (n != 0) {
                    long startId = this.lastId;
                    int index2 = this.lastIndex;
                    if (n > index2) {
                        innerCompleted = false;
                        r = r4;
                        if (innerSubscriberArr[index2].id == startId) {
                            replenishMain = replenishMain2;
                            index = index2;
                            d = d3;
                        }
                        int j3 = index;
                        i = 0;
                        j = j3;
                        while (true) {
                            if (i < n) {
                                innerCompleted2 = innerCompleted;
                                r2 = r;
                                replenishMain2 = replenishMain;
                                break;
                            }
                            if (checkTerminate()) {
                                return;
                            }
                            is = innerSubscriberArr[j];
                            o = null;
                            while (!checkTerminate()) {
                                q = is.queue;
                                if (q == null) {
                                    index = index;
                                    i = i;
                                    d2 = d;
                                    svq = svq3;
                                } else {
                                    d2 = d;
                                    o2 = o;
                                    svq = svq3;
                                    produced = 0;
                                    while (true) {
                                        q2 = q;
                                        if (r != 0) {
                                            o = o2;
                                            break;
                                        }
                                        try {
                                            o = q2.poll();
                                            if (o == null) {
                                                break;
                                            }
                                            child.onNext(o);
                                            if (checkTerminate()) {
                                                return;
                                            }
                                            r--;
                                            produced++;
                                            o2 = o;
                                            q = q2;
                                        } catch (Throwable ex2) {
                                            Exceptions.throwIfFatal(ex2);
                                            is.dispose();
                                            this.errs.addThrowable(ex2);
                                            if (!this.delayErrors) {
                                                this.upstream.cancel();
                                            }
                                            if (checkTerminate()) {
                                                return;
                                            }
                                            removeInner(is);
                                            index = index;
                                            i++;
                                            innerCompleted = true;
                                        }
                                    }
                                    if (produced != 0) {
                                        index = index;
                                        i = i;
                                    } else {
                                        if (unbounded) {
                                            r3 = LongCompanionObject.MAX_VALUE;
                                        } else {
                                            r3 = this.requested.addAndGet(-produced);
                                        }
                                        is.requestMore(produced);
                                        r = r3;
                                    }
                                    if (r == 0 && o != null) {
                                        index = index;
                                        i = i;
                                        svq3 = svq;
                                        d = d2;
                                    }
                                }
                                innerDone = is.done;
                                SimpleQueue<U> innerQueue = is.queue;
                                if (!innerDone && (innerQueue == null || innerQueue.isEmpty())) {
                                    removeInner(is);
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    replenishMain++;
                                    innerCompleted = true;
                                }
                                if (r == 0) {
                                    innerCompleted2 = innerCompleted;
                                    r2 = r;
                                    replenishMain2 = replenishMain;
                                    break;
                                } else {
                                    j++;
                                    if (j == n) {
                                        j = 0;
                                    }
                                    i++;
                                    index = index;
                                    svq3 = svq;
                                    d = d2;
                                }
                            }
                            return;
                        }
                        this.lastIndex = j;
                        this.lastId = innerSubscriberArr[j].id;
                    } else {
                        innerCompleted = false;
                        r = r4;
                    }
                    if (n <= index2) {
                        index2 = 0;
                    }
                    int j4 = index2;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= n) {
                            replenishMain = replenishMain2;
                            d = d3;
                            break;
                        }
                        replenishMain = replenishMain2;
                        d = d3;
                        if (innerSubscriberArr[j4].id == startId) {
                            break;
                        }
                        j4++;
                        if (j4 == n) {
                            j4 = 0;
                        }
                        i2++;
                        d3 = d;
                        replenishMain2 = replenishMain;
                    }
                    this.lastIndex = j4;
                    this.lastId = innerSubscriberArr[j4].id;
                    index = j4;
                    int j5 = index;
                    i = 0;
                    j = j5;
                    while (true) {
                        if (i < n) {
                            innerCompleted2 = innerCompleted;
                            r2 = r;
                            replenishMain2 = replenishMain;
                            break;
                        }
                        if (checkTerminate()) {
                            return;
                        }
                        is = innerSubscriberArr[j];
                        o = null;
                        while (!checkTerminate()) {
                            q = is.queue;
                            if (q == null) {
                                index = index;
                                i = i;
                                d2 = d;
                                svq = svq3;
                            } else {
                                d2 = d;
                                o2 = o;
                                svq = svq3;
                                produced = 0;
                                while (true) {
                                    q2 = q;
                                    if (r != 0) {
                                        o = o2;
                                        break;
                                    }
                                    o = q2.poll();
                                    if (o == null) {
                                        break;
                                        break;
                                    }
                                    child.onNext(o);
                                    if (checkTerminate()) {
                                        return;
                                    }
                                    r--;
                                    produced++;
                                    o2 = o;
                                    q = q2;
                                }
                                if (produced != 0) {
                                    index = index;
                                    i = i;
                                } else {
                                    if (unbounded) {
                                        r3 = this.requested.addAndGet(-produced);
                                    } else {
                                        r3 = LongCompanionObject.MAX_VALUE;
                                    }
                                    is.requestMore(produced);
                                    r = r3;
                                }
                                if (r == 0) {
                                }
                            }
                            innerDone = is.done;
                            SimpleQueue<U> innerQueue2 = is.queue;
                            if (!innerDone) {
                            }
                            if (r == 0) {
                                innerCompleted2 = innerCompleted;
                                r2 = r;
                                replenishMain2 = replenishMain;
                                break;
                            } else {
                                j++;
                                if (j == n) {
                                    j = 0;
                                }
                                i++;
                                index = index;
                                svq3 = svq;
                                d = d2;
                            }
                        }
                        return;
                    }
                    this.lastIndex = j;
                    this.lastId = innerSubscriberArr[j].id;
                }
                if (replenishMain2 != 0 && !this.cancelled) {
                    this.upstream.request(replenishMain2);
                }
                if (!innerCompleted2 && (missed = addAndGet(-missed)) == 0) {
                    return;
                }
            }
        }

        boolean checkTerminate() {
            if (this.cancelled) {
                clearScalarQueue();
                return true;
            }
            if (!this.delayErrors && this.errs.get() != null) {
                clearScalarQueue();
                Throwable ex = this.errs.terminate();
                if (ex != ExceptionHelper.TERMINATED) {
                    this.downstream.onError(ex);
                }
                return true;
            }
            return false;
        }

        void clearScalarQueue() {
            SimpleQueue<U> q = this.queue;
            if (q != null) {
                q.clear();
            }
        }

        void disposeAll() {
            InnerSubscriber<?, ?>[] a = this.subscribers.get();
            InnerSubscriber<?, ?>[] innerSubscriberArr = CANCELLED;
            if (a != innerSubscriberArr) {
                InnerSubscriber<?, ?>[] a2 = this.subscribers.getAndSet(innerSubscriberArr);
                InnerSubscriber<?, ?>[] a3 = a2;
                if (a3 != CANCELLED) {
                    for (InnerSubscriber<?, ?> inner : a3) {
                        inner.dispose();
                    }
                    Throwable ex = this.errs.terminate();
                    if (ex != null && ex != ExceptionHelper.TERMINATED) {
                        RxJavaPlugins.onError(ex);
                    }
                }
            }
        }

        void innerError(InnerSubscriber<T, U> inner, Throwable t) {
            if (this.errs.addThrowable(t)) {
                inner.done = true;
                if (!this.delayErrors) {
                    this.upstream.cancel();
                    for (InnerSubscriber<?, ?> a : this.subscribers.getAndSet(CANCELLED)) {
                        a.dispose();
                    }
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(t);
        }
    }

    static final class InnerSubscriber<T, U> extends AtomicReference<Subscription> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -4606175640614850599L;
        final int bufferSize;
        volatile boolean done;
        int fusionMode;
        final long id;
        final int limit;
        final MergeSubscriber<T, U> parent;
        long produced;
        volatile SimpleQueue<U> queue;

        InnerSubscriber(MergeSubscriber<T, U> parent, long id) {
            this.id = id;
            this.parent = parent;
            this.bufferSize = parent.bufferSize;
            this.limit = this.bufferSize >> 2;
        }

        @Override // io.reactivex.FlowableSubscriber
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.setOnce(this, s)) {
                if (s instanceof QueueSubscription) {
                    QueueSubscription<U> qs = (QueueSubscription) s;
                    int m = qs.requestFusion(7);
                    if (m == 1) {
                        this.fusionMode = m;
                        this.queue = qs;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (m == 2) {
                        this.fusionMode = m;
                        this.queue = qs;
                    }
                }
                s.request(this.bufferSize);
            }
        }

        public void onNext(U t) {
            if (this.fusionMode != 2) {
                this.parent.tryEmit(t, this);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable t) {
            lazySet(SubscriptionHelper.CANCELLED);
            this.parent.innerError(this, t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        void requestMore(long n) {
            if (this.fusionMode != 1) {
                long p = this.produced + n;
                if (p >= this.limit) {
                    this.produced = 0L;
                    get().request(p);
                } else {
                    this.produced = p;
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            SubscriptionHelper.cancel(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return get() == SubscriptionHelper.CANCELLED;
        }
    }
}
