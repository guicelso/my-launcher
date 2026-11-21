package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KotlinType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class KotlinTypeKt {
    public static final boolean isNullable(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return TypeUtils.isNullableType(receiver$0);
    }

    public static final boolean isError(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType unwrapped = receiver$0.unwrap();
        return (unwrapped instanceof ErrorType) || ((unwrapped instanceof FlexibleType) && (((FlexibleType) unwrapped).getDelegate() instanceof ErrorType));
    }
}
