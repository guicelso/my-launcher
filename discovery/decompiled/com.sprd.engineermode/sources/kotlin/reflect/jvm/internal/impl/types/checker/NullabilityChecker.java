package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NullabilityChecker {
    public static final NullabilityChecker INSTANCE = new NullabilityChecker();

    private NullabilityChecker() {
    }

    public final boolean isPossibleSubtype(@NotNull TypeCheckerContext context, @NotNull SimpleType subType, @NotNull SimpleType superType) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return runIsPossibleSubtype(context, subType, superType);
    }

    public final boolean isSubtypeOfAny(@NotNull UnwrappedType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        return hasNotNullSupertype(new TypeCheckerContext(false, false, 2, null), FlexibleTypesKt.lowerIfFlexible(type), TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE);
    }

    private final boolean runIsPossibleSubtype(@NotNull TypeCheckerContext $receiver, SimpleType subType, SimpleType superType) {
        boolean z = NewKotlinTypeCheckerKt.isIntersectionType(subType) || NewKotlinTypeCheckerKt.isSingleClassifierType(subType) || $receiver.isAllowedTypeVariable(subType);
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Not singleClassifierType superType: " + superType);
        }
        boolean z2 = NewKotlinTypeCheckerKt.isSingleClassifierType(superType) || $receiver.isAllowedTypeVariable(superType);
        if (_Assertions.ENABLED && !z2) {
            throw new AssertionError("Not singleClassifierType superType: " + superType);
        }
        if (superType.isMarkedNullable() || SpecialTypesKt.isDefinitelyNotNullType(subType) || hasNotNullSupertype($receiver, subType, TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
            return true;
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(superType) || hasNotNullSupertype($receiver, superType, TypeCheckerContext.SupertypesPolicy.UpperIfFlexible.INSTANCE) || NewKotlinTypeCheckerKt.isClassType(subType)) {
            return false;
        }
        return hasPathByNotMarkedNullableNodes($receiver, subType, superType.getConstructor());
    }

    private final boolean hasNotNullSupertype(@NotNull TypeCheckerContext $receiver, SimpleType type, TypeCheckerContext.SupertypesPolicy supertypesPolicy) {
        if ((NewKotlinTypeCheckerKt.isClassType(type) && !type.isMarkedNullable()) || SpecialTypesKt.isDefinitelyNotNullType(type)) {
            return true;
        }
        $receiver.initialize();
        ArrayDeque deque$iv = $receiver.supertypesDeque;
        if (deque$iv == null) {
            Intrinsics.throwNpe();
        }
        Set visitedSupertypes$iv = $receiver.supertypesSet;
        if (visitedSupertypes$iv == null) {
            Intrinsics.throwNpe();
        }
        deque$iv.push(type);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleType current = (SimpleType) deque$iv.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (visitedSupertypes$iv.add(current)) {
                TypeCheckerContext.SupertypesPolicy.None none = current.isMarkedNullable() ? TypeCheckerContext.SupertypesPolicy.None.INSTANCE : supertypesPolicy;
                TypeCheckerContext.SupertypesPolicy it$iv = none;
                if (!(!Intrinsics.areEqual(it$iv, TypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    none = null;
                }
                if (none != null) {
                    TypeCheckerContext.SupertypesPolicy policy$iv = none;
                    for (KotlinType supertype : current.getConstructor().getSupertypes()) {
                        Intrinsics.checkExpressionValueIsNotNull(supertype, "supertype");
                        SimpleType newType$iv = policy$iv.mo385transformType(supertype);
                        if ((NewKotlinTypeCheckerKt.isClassType(newType$iv) && !newType$iv.isMarkedNullable()) || SpecialTypesKt.isDefinitelyNotNullType(newType$iv)) {
                            $receiver.clear();
                            return true;
                        }
                        deque$iv.add(newType$iv);
                    }
                } else {
                    continue;
                }
            }
        }
        $receiver.clear();
        return false;
    }

    private final boolean hasPathByNotMarkedNullableNodes(@NotNull TypeCheckerContext $receiver, SimpleType start, TypeConstructor end) {
        if (!start.isMarkedNullable() && Intrinsics.areEqual(start.getConstructor(), end)) {
            return true;
        }
        $receiver.initialize();
        ArrayDeque deque$iv = $receiver.supertypesDeque;
        if (deque$iv == null) {
            Intrinsics.throwNpe();
        }
        Set visitedSupertypes$iv = $receiver.supertypesSet;
        if (visitedSupertypes$iv == null) {
            Intrinsics.throwNpe();
        }
        deque$iv.push(start);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleType current = (SimpleType) deque$iv.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (visitedSupertypes$iv.add(current)) {
                TypeCheckerContext.SupertypesPolicy it$iv = current.isMarkedNullable() ? TypeCheckerContext.SupertypesPolicy.None.INSTANCE : TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                if (!(!Intrinsics.areEqual(it$iv, TypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    it$iv = null;
                }
                if (it$iv != null) {
                    TypeCheckerContext.SupertypesPolicy policy$iv = it$iv;
                    for (KotlinType supertype : current.getConstructor().getSupertypes()) {
                        Intrinsics.checkExpressionValueIsNotNull(supertype, "supertype");
                        SimpleType newType$iv = policy$iv.mo385transformType(supertype);
                        if (!newType$iv.isMarkedNullable() && Intrinsics.areEqual(newType$iv.getConstructor(), end)) {
                            $receiver.clear();
                            return true;
                        }
                        deque$iv.add(newType$iv);
                    }
                } else {
                    continue;
                }
            }
        }
        $receiver.clear();
        return false;
    }
}
