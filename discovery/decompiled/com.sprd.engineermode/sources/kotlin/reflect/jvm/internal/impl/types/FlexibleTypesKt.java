package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: flexibleTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FlexibleTypesKt {
    public static final boolean isFlexible(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.unwrap() instanceof FlexibleType;
    }

    @NotNull
    public static final FlexibleType asFlexibleType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType unwrappedTypeUnwrap = receiver$0.unwrap();
        if (unwrappedTypeUnwrap != null) {
            return (FlexibleType) unwrappedTypeUnwrap;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
    }

    @NotNull
    public static final SimpleType lowerIfFlexible(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType $receiver = receiver$0.unwrap();
        if ($receiver instanceof FlexibleType) {
            return ((FlexibleType) $receiver).getLowerBound();
        }
        if ($receiver instanceof SimpleType) {
            return (SimpleType) $receiver;
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public static final SimpleType upperIfFlexible(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType $receiver = receiver$0.unwrap();
        if ($receiver instanceof FlexibleType) {
            return ((FlexibleType) $receiver).getUpperBound();
        }
        if ($receiver instanceof SimpleType) {
            return (SimpleType) $receiver;
        }
        throw new NoWhenBranchMatchedException();
    }
}
