package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: exceptionUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ExceptionUtilsKt {
    @NotNull
    public static final RuntimeException rethrow(@NotNull Throwable e) throws Throwable {
        Intrinsics.checkParameterIsNotNull(e, "e");
        throw e;
    }

    public static final boolean isProcessCanceledException(@NotNull Throwable receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Class<?> cls = receiver$0.getClass();
        while (!Intrinsics.areEqual(cls.getCanonicalName(), "com.intellij.openapi.progress.ProcessCanceledException")) {
            Class<? super Object> superclass = cls.getSuperclass();
            if (superclass == null) {
                return false;
            }
            cls = superclass;
        }
        return true;
    }
}
