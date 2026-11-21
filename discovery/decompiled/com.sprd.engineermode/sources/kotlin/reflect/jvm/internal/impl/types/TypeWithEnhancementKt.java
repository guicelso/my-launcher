package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeWithEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeWithEnhancementKt {
    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static final KotlinType getEnhancement(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0 instanceof TypeWithEnhancement) {
            return ((TypeWithEnhancement) receiver$0).getEnhancement();
        }
        return null;
    }

    @NotNull
    public static final KotlinType unwrapEnhancement(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KotlinType enhancement = getEnhancement(receiver$0);
        return enhancement != null ? enhancement : receiver$0;
    }

    @NotNull
    public static final UnwrappedType inheritEnhancement(@NotNull UnwrappedType receiver$0, @NotNull KotlinType origin) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(origin, "origin");
        return wrapEnhancement(receiver$0, getEnhancement(origin));
    }

    @NotNull
    public static final UnwrappedType wrapEnhancement(@NotNull UnwrappedType receiver$0, @Nullable KotlinType enhancement) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (enhancement == null) {
            return receiver$0;
        }
        if (receiver$0 instanceof SimpleType) {
            return new SimpleTypeWithEnhancement((SimpleType) receiver$0, enhancement);
        }
        if (receiver$0 instanceof FlexibleType) {
            return new FlexibleTypeWithEnhancement((FlexibleType) receiver$0, enhancement);
        }
        throw new NoWhenBranchMatchedException();
    }
}
