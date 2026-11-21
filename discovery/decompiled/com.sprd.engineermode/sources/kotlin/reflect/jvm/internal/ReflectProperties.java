package kotlin.reflect.jvm.internal;

import java.lang.ref.SoftReference;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class ReflectProperties {

    public static abstract class Val<T> {
        private static final Object NULL_VALUE = new Object() { // from class: kotlin.reflect.jvm.internal.ReflectProperties.Val.1
        };

        public abstract T invoke();

        public final T getValue(Object instance, Object metadata) {
            return invoke();
        }

        protected Object escape(T value) {
            return value == null ? NULL_VALUE : value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected T unescape(Object obj) {
            if (obj == NULL_VALUE) {
                return null;
            }
            return obj;
        }
    }

    public static class LazyVal<T> extends Val<T> {
        private final Function0<T> initializer;
        private Object value = null;

        public LazyVal(@NotNull Function0<T> initializer) {
            this.initializer = initializer;
        }

        @Override // kotlin.reflect.jvm.internal.ReflectProperties.Val
        public T invoke() {
            Object cached = this.value;
            if (cached != null) {
                return unescape(cached);
            }
            T result = this.initializer.invoke();
            this.value = escape(result);
            return result;
        }
    }

    public static class LazySoftVal<T> extends Val<T> {
        private final Function0<T> initializer;
        private SoftReference<Object> value;

        public LazySoftVal(@Nullable T initialValue, @NotNull Function0<T> initializer) {
            this.value = null;
            this.initializer = initializer;
            if (initialValue != null) {
                this.value = new SoftReference<>(escape(initialValue));
            }
        }

        @Override // kotlin.reflect.jvm.internal.ReflectProperties.Val
        public T invoke() {
            Object result;
            SoftReference<Object> cached = this.value;
            if (cached != null && (result = cached.get()) != null) {
                return unescape(result);
            }
            T result2 = this.initializer.invoke();
            this.value = new SoftReference<>(escape(result2));
            return result2;
        }
    }

    @NotNull
    public static <T> LazyVal<T> lazy(@NotNull Function0<T> initializer) {
        return new LazyVal<>(initializer);
    }

    @NotNull
    public static <T> LazySoftVal<T> lazySoft(@Nullable T initialValue, @NotNull Function0<T> initializer) {
        return new LazySoftVal<>(initialValue, initializer);
    }

    @NotNull
    public static <T> LazySoftVal<T> lazySoft(@NotNull Function0<T> initializer) {
        return lazySoft(null, initializer);
    }
}
