package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class StrictEqualityTypeChecker {
    public static final StrictEqualityTypeChecker INSTANCE = new StrictEqualityTypeChecker();

    private StrictEqualityTypeChecker() {
    }

    public final boolean strictEqualTypes(@NotNull UnwrappedType a, @NotNull UnwrappedType b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (a == b) {
            return true;
        }
        if ((a instanceof SimpleType) && (b instanceof SimpleType)) {
            return strictEqualTypes((SimpleType) a, (SimpleType) b);
        }
        if ((a instanceof FlexibleType) && (b instanceof FlexibleType)) {
            return strictEqualTypes(((FlexibleType) a).getLowerBound(), ((FlexibleType) b).getLowerBound()) && strictEqualTypes(((FlexibleType) a).getUpperBound(), ((FlexibleType) b).getUpperBound());
        }
        return false;
    }

    public final boolean strictEqualTypes(@NotNull SimpleType a, @NotNull SimpleType b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (a.isMarkedNullable() != b.isMarkedNullable() || SpecialTypesKt.isDefinitelyNotNullType(a) != SpecialTypesKt.isDefinitelyNotNullType(b) || (!Intrinsics.areEqual(a.getConstructor(), b.getConstructor())) || a.getArguments().size() != b.getArguments().size()) {
            return false;
        }
        if (a.getArguments() == b.getArguments()) {
            return true;
        }
        int size = a.getArguments().size();
        for (int i = 0; i < size; i++) {
            TypeProjection aArg = a.getArguments().get(i);
            TypeProjection bArg = b.getArguments().get(i);
            if (aArg.isStarProjection() != bArg.isStarProjection()) {
                return false;
            }
            if (!aArg.isStarProjection() && (aArg.getProjectionKind() != bArg.getProjectionKind() || !strictEqualTypes(aArg.getType().unwrap(), bArg.getType().unwrap()))) {
                return false;
            }
        }
        return true;
    }
}
