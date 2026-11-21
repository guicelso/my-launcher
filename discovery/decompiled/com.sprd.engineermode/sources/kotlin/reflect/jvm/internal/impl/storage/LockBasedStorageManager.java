package kotlin.reflect.jvm.internal.impl.storage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.WrappedValues;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class LockBasedStorageManager implements StorageManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String debugText;
    private final ExceptionHandlingStrategy exceptionHandlingStrategy;
    protected final Lock lock;
    private static final String PACKAGE_NAME = StringsKt.substringBeforeLast(LockBasedStorageManager.class.getCanonicalName(), ".", "");
    public static final StorageManager NO_LOCKS = new LockBasedStorageManager("NO_LOCKS", ExceptionHandlingStrategy.THROW, NoLock.INSTANCE) { // from class: kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.1
        @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager
        @NotNull
        protected <T> RecursionDetectedResult<T> recursionDetectedDefault() {
            return RecursionDetectedResult.fallThrough();
        }
    };

    public interface ExceptionHandlingStrategy {
        public static final ExceptionHandlingStrategy THROW = new ExceptionHandlingStrategy() { // from class: kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.ExceptionHandlingStrategy.1
            @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.ExceptionHandlingStrategy
            @NotNull
            public RuntimeException handleException(@NotNull Throwable throwable) {
                throw ExceptionUtilsKt.rethrow(throwable);
            }
        };

        @NotNull
        RuntimeException handleException(@NotNull Throwable th);
    }

    private enum NotValue {
        NOT_COMPUTED,
        COMPUTING,
        RECURSION_WAS_DETECTED
    }

    private LockBasedStorageManager(@NotNull String debugText, @NotNull ExceptionHandlingStrategy exceptionHandlingStrategy, @NotNull Lock lock) {
        this.lock = lock;
        this.exceptionHandlingStrategy = exceptionHandlingStrategy;
        this.debugText = debugText;
    }

    public LockBasedStorageManager() {
        this(defaultDebugName(), ExceptionHandlingStrategy.THROW, new ReentrantLock());
    }

    private static String defaultDebugName() {
        return "<unknown creating class>";
    }

    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " (" + this.debugText + ")";
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(@NotNull Function1<? super K, ? extends V> compute) {
        return createMemoizedFunction(compute, createConcurrentHashMap());
    }

    @NotNull
    public <K, V> MemoizedFunctionToNotNull<K, V> createMemoizedFunction(@NotNull Function1<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map) {
        return new MapBasedMemoizedFunctionToNotNull(this, map, compute);
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(@NotNull Function1<? super K, ? extends V> compute) {
        return createMemoizedFunctionWithNullableValues(compute, createConcurrentHashMap());
    }

    @NotNull
    public <K, V> MemoizedFunctionToNullable<K, V> createMemoizedFunctionWithNullableValues(@NotNull Function1<? super K, ? extends V> compute, @NotNull ConcurrentMap<K, Object> map) {
        return new MapBasedMemoizedFunction(this, map, compute);
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <T> NotNullLazyValue<T> createLazyValue(@NotNull Function0<? extends T> computable) {
        return new LockBasedNotNullLazyValue(this, computable);
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <T> NotNullLazyValue<T> createRecursionTolerantLazyValue(@NotNull Function0<? extends T> computable, @NotNull final T onRecursiveCall) {
        return new LockBasedNotNullLazyValue<T>(this, computable) { // from class: kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.2
            @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.LockBasedLazyValue
            @NotNull
            protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
                return RecursionDetectedResult.value(onRecursiveCall);
            }
        };
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <T> NotNullLazyValue<T> createLazyValueWithPostCompute(@NotNull Function0<? extends T> computable, final Function1<? super Boolean, ? extends T> onRecursiveCall, @NotNull final Function1<? super T, Unit> postCompute) {
        return new LockBasedNotNullLazyValue<T>(this, computable) { // from class: kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.3
            @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.LockBasedLazyValue
            @NotNull
            protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
                Function1 function1 = onRecursiveCall;
                if (function1 == null) {
                    return super.recursionDetected(firstTime);
                }
                return RecursionDetectedResult.value(function1.invoke(Boolean.valueOf(firstTime)));
            }

            @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.LockBasedLazyValue
            protected void postCompute(@NotNull T value) {
                postCompute.invoke(value);
            }
        };
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <T> NullableLazyValue<T> createNullableLazyValue(@NotNull Function0<? extends T> computable) {
        return new LockBasedLazyValue(this, computable);
    }

    @NotNull
    private static <K> ConcurrentMap<K, Object> createConcurrentHashMap() {
        return new ConcurrentHashMap(3, 1.0f, 2);
    }

    @NotNull
    protected <T> RecursionDetectedResult<T> recursionDetectedDefault() {
        throw ((IllegalStateException) sanitizeStackTrace(new IllegalStateException("Recursive call in a lazy value under " + this)));
    }

    private static class RecursionDetectedResult<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean fallThrough;
        private final T value;

        @NotNull
        public static <T> RecursionDetectedResult<T> value(T value) {
            return new RecursionDetectedResult<>(value, false);
        }

        @NotNull
        public static <T> RecursionDetectedResult<T> fallThrough() {
            return new RecursionDetectedResult<>(null, true);
        }

        private RecursionDetectedResult(T value, boolean fallThrough) {
            this.value = value;
            this.fallThrough = fallThrough;
        }

        public T getValue() {
            return this.value;
        }

        public boolean isFallThrough() {
            return this.fallThrough;
        }

        public String toString() {
            return isFallThrough() ? "FALL_THROUGH" : String.valueOf(this.value);
        }
    }

    private static class LockBasedLazyValue<T> implements NullableLazyValue<T> {
        private final Function0<? extends T> computable;
        private final LockBasedStorageManager storageManager;

        @Nullable
        private volatile Object value = NotValue.NOT_COMPUTED;

        public LockBasedLazyValue(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            this.storageManager = storageManager;
            this.computable = computable;
        }

        public boolean isComputed() {
            return (this.value == NotValue.NOT_COMPUTED || this.value == NotValue.COMPUTING) ? false : true;
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0044 A[Catch: all -> 0x0096, TRY_ENTER, TryCatch #1 {all -> 0x0096, blocks: (B:7:0x0012, B:9:0x0019, B:12:0x0025, B:14:0x0029, B:16:0x0038, B:19:0x0044, B:21:0x0048, B:23:0x0053, B:24:0x0058, B:29:0x0071, B:31:0x0077, B:33:0x007d, B:34:0x0083, B:35:0x008d, B:36:0x008e, B:37:0x0095, B:25:0x005c), top: B:43:0x0012, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:21:0x0048 A[Catch: all -> 0x0096, TryCatch #1 {all -> 0x0096, blocks: (B:7:0x0012, B:9:0x0019, B:12:0x0025, B:14:0x0029, B:16:0x0038, B:19:0x0044, B:21:0x0048, B:23:0x0053, B:24:0x0058, B:29:0x0071, B:31:0x0077, B:33:0x007d, B:34:0x0083, B:35:0x008d, B:36:0x008e, B:37:0x0095, B:25:0x005c), top: B:43:0x0012, inners: #0 }] */
        /* JADX WARN: Code duplicated, block: B:23:0x0053 A[Catch: all -> 0x0096, TryCatch #1 {all -> 0x0096, blocks: (B:7:0x0012, B:9:0x0019, B:12:0x0025, B:14:0x0029, B:16:0x0038, B:19:0x0044, B:21:0x0048, B:23:0x0053, B:24:0x0058, B:29:0x0071, B:31:0x0077, B:33:0x007d, B:34:0x0083, B:35:0x008d, B:36:0x008e, B:37:0x0095, B:25:0x005c), top: B:43:0x0012, inners: #0 }] */
        @Override // kotlin.jvm.functions.Function0
        public T invoke() {
            RecursionDetectedResult<T> recursionDetectedResultRecursionDetected;
            T value;
            Object obj = this.value;
            if (!(obj instanceof NotValue)) {
                return (T) WrappedValues.unescapeThrowable(obj);
            }
            this.storageManager.lock.lock();
            try {
                Object obj2 = this.value;
                if (!(obj2 instanceof NotValue)) {
                    T t = (T) WrappedValues.unescapeThrowable(obj2);
                    this.storageManager.lock.unlock();
                    return t;
                }
                if (obj2 != NotValue.COMPUTING) {
                    if (obj2 == NotValue.RECURSION_WAS_DETECTED) {
                        recursionDetectedResultRecursionDetected = recursionDetected(false);
                        if (!recursionDetectedResultRecursionDetected.isFallThrough()) {
                            value = recursionDetectedResultRecursionDetected.getValue();
                        }
                    }
                    this.value = NotValue.COMPUTING;
                    T tInvoke = this.computable.invoke();
                    this.value = tInvoke;
                    postCompute(tInvoke);
                    this.storageManager.lock.unlock();
                    return tInvoke;
                }
                this.value = NotValue.RECURSION_WAS_DETECTED;
                RecursionDetectedResult<T> recursionDetectedResultRecursionDetected2 = recursionDetected(true);
                if (recursionDetectedResultRecursionDetected2.isFallThrough()) {
                    if (obj2 == NotValue.RECURSION_WAS_DETECTED) {
                        recursionDetectedResultRecursionDetected = recursionDetected(false);
                        if (!recursionDetectedResultRecursionDetected.isFallThrough()) {
                            value = recursionDetectedResultRecursionDetected.getValue();
                        }
                    }
                    this.value = NotValue.COMPUTING;
                    try {
                        T tInvoke2 = this.computable.invoke();
                        this.value = tInvoke2;
                        postCompute(tInvoke2);
                        this.storageManager.lock.unlock();
                        return tInvoke2;
                    } catch (Throwable th) {
                        if (ExceptionUtilsKt.isProcessCanceledException(th)) {
                            this.value = NotValue.NOT_COMPUTED;
                            throw th;
                        }
                        if (this.value == NotValue.COMPUTING) {
                            this.value = WrappedValues.escapeThrowable(th);
                        }
                        throw this.storageManager.exceptionHandlingStrategy.handleException(th);
                    }
                }
                value = recursionDetectedResultRecursionDetected2.getValue();
                this.storageManager.lock.unlock();
                return value;
            } catch (Throwable th2) {
                this.storageManager.lock.unlock();
                throw th2;
            }
        }

        @NotNull
        protected RecursionDetectedResult<T> recursionDetected(boolean firstTime) {
            return this.storageManager.recursionDetectedDefault();
        }

        protected void postCompute(T value) {
        }
    }

    private static class LockBasedNotNullLazyValue<T> extends LockBasedLazyValue<T> implements NotNullLazyValue<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public LockBasedNotNullLazyValue(@NotNull LockBasedStorageManager storageManager, @NotNull Function0<? extends T> computable) {
            super(storageManager, computable);
        }

        @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.LockBasedLazyValue, kotlin.jvm.functions.Function0
        @NotNull
        public T invoke() {
            return (T) super.invoke();
        }
    }

    private static class MapBasedMemoizedFunction<K, V> implements MemoizedFunctionToNullable<K, V> {
        private final ConcurrentMap<K, Object> cache;
        private final Function1<? super K, ? extends V> compute;
        private final LockBasedStorageManager storageManager;

        public MapBasedMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<K, Object> map, @NotNull Function1<? super K, ? extends V> compute) {
            this.storageManager = storageManager;
            this.cache = map;
            this.compute = compute;
        }

        @Override // kotlin.jvm.functions.Function1
        @Nullable
        public V invoke(K k) {
            Object obj = this.cache.get(k);
            if (obj != null && obj != NotValue.COMPUTING) {
                return (V) WrappedValues.unescapeExceptionOrNull(obj);
            }
            this.storageManager.lock.lock();
            try {
                Object obj2 = this.cache.get(k);
                if (obj2 == NotValue.COMPUTING) {
                    throw recursionDetected(k);
                }
                if (obj2 == null) {
                    AssertionError assertionErrorRaceCondition = null;
                    try {
                        this.cache.put(k, NotValue.COMPUTING);
                        V vInvoke = this.compute.invoke(k);
                        Object objPut = this.cache.put(k, WrappedValues.escapeNull(vInvoke));
                        if (objPut != NotValue.COMPUTING) {
                            assertionErrorRaceCondition = raceCondition(k, objPut);
                            throw assertionErrorRaceCondition;
                        }
                        this.storageManager.lock.unlock();
                        return vInvoke;
                    } catch (Throwable th) {
                        if (ExceptionUtilsKt.isProcessCanceledException(th)) {
                            this.cache.remove(k);
                            throw th;
                        }
                        if (th == assertionErrorRaceCondition) {
                            throw this.storageManager.exceptionHandlingStrategy.handleException(th);
                        }
                        Object objPut2 = this.cache.put(k, WrappedValues.escapeThrowable(th));
                        if (objPut2 == NotValue.COMPUTING) {
                            throw this.storageManager.exceptionHandlingStrategy.handleException(th);
                        }
                        throw raceCondition(k, objPut2);
                    }
                }
                V v = (V) WrappedValues.unescapeExceptionOrNull(obj2);
                this.storageManager.lock.unlock();
                return v;
            } catch (Throwable th2) {
                this.storageManager.lock.unlock();
                throw th2;
            }
        }

        @NotNull
        private AssertionError recursionDetected(K input) {
            return (AssertionError) LockBasedStorageManager.sanitizeStackTrace(new AssertionError("Recursion detected on input: " + input + " under " + this.storageManager));
        }

        @NotNull
        private AssertionError raceCondition(K input, Object oldValue) {
            return (AssertionError) LockBasedStorageManager.sanitizeStackTrace(new AssertionError("Race condition detected on input " + input + ". Old value is " + oldValue + " under " + this.storageManager));
        }

        protected LockBasedStorageManager getStorageManager() {
            return this.storageManager;
        }
    }

    private static class MapBasedMemoizedFunctionToNotNull<K, V> extends MapBasedMemoizedFunction<K, V> implements MemoizedFunctionToNotNull<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public MapBasedMemoizedFunctionToNotNull(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<K, Object> map, @NotNull Function1<? super K, ? extends V> compute) {
            super(storageManager, map, compute);
        }

        @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.MapBasedMemoizedFunction, kotlin.jvm.functions.Function1
        @NotNull
        public V invoke(K k) {
            return (V) super.invoke(k);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public static <T extends Throwable> T sanitizeStackTrace(@NotNull T throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int firstNonStorage = -1;
        for (int i = 0; i < size; i++) {
            if (!stackTrace[i].getClassName().startsWith(PACKAGE_NAME)) {
                firstNonStorage = i;
                break;
            }
        }
        List<StackTraceElement> list = Arrays.asList(stackTrace).subList(firstNonStorage, size);
        throwable.setStackTrace((StackTraceElement[]) list.toArray(new StackTraceElement[list.size()]));
        return throwable;
    }

    private static class CacheWithNullableValuesBasedOnMemoizedFunction<K, V> extends MapBasedMemoizedFunction<KeyWithComputation<K, V>, V> {
        private CacheWithNullableValuesBasedOnMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<KeyWithComputation<K, V>, Object> map) {
            super(storageManager, map, new Function1<KeyWithComputation<K, V>, V>() { // from class: kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.CacheWithNullableValuesBasedOnMemoizedFunction.1
                @Override // kotlin.jvm.functions.Function1
                public V invoke(KeyWithComputation<K, V> keyWithComputation) {
                    return (V) ((KeyWithComputation) keyWithComputation).computation.invoke();
                }
            });
        }

        @Nullable
        public V computeIfAbsent(K key, @NotNull Function0<? extends V> computation) {
            return invoke(new KeyWithComputation(key, computation));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.storage.StorageManager
    @NotNull
    public <K, V> CacheWithNotNullValues<K, V> createCacheWithNotNullValues() {
        return new CacheWithNotNullValuesBasedOnMemoizedFunction(createConcurrentHashMap());
    }

    private static class CacheWithNotNullValuesBasedOnMemoizedFunction<K, V> extends CacheWithNullableValuesBasedOnMemoizedFunction<K, V> implements CacheWithNotNullValues<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private CacheWithNotNullValuesBasedOnMemoizedFunction(@NotNull LockBasedStorageManager storageManager, @NotNull ConcurrentMap<KeyWithComputation<K, V>, Object> map) {
            super(map);
        }

        @Override // kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager.CacheWithNullableValuesBasedOnMemoizedFunction, kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues
        @NotNull
        public V computeIfAbsent(K k, @NotNull Function0<? extends V> function0) {
            return (V) super.computeIfAbsent(k, function0);
        }
    }

    private static class KeyWithComputation<K, V> {
        private final Function0<? extends V> computation;
        private final K key;

        public KeyWithComputation(K key, Function0<? extends V> computation) {
            this.key = key;
            this.computation = computation;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            KeyWithComputation<?, ?> that = (KeyWithComputation) o;
            if (this.key.equals(that.key)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.key.hashCode();
        }
    }
}
