package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IntersectionType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class IntersectionTypeKt {
    @NotNull
    public static final UnwrappedType intersectTypes(@NotNull List<? extends UnwrappedType> types) {
        SimpleType lowerBound;
        Intrinsics.checkParameterIsNotNull(types, "types");
        int size = types.size();
        if (size == 0) {
            throw new IllegalStateException("Expected some types".toString());
        }
        if (size == 1) {
            return (UnwrappedType) CollectionsKt.single((List) types);
        }
        boolean hasErrorType = false;
        List<? extends UnwrappedType> $receiver$iv = types;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        boolean hasFlexibleTypes = false;
        for (Object item$iv$iv : $receiver$iv) {
            UnwrappedType it = (UnwrappedType) item$iv$iv;
            hasErrorType = hasErrorType || KotlinTypeKt.isError(it);
            if (it instanceof SimpleType) {
                lowerBound = (SimpleType) it;
            } else if (it instanceof FlexibleType) {
                if (DynamicTypesKt.isDynamic(it)) {
                    return it;
                }
                hasFlexibleTypes = true;
                lowerBound = ((FlexibleType) it).getLowerBound();
            } else {
                throw new NoWhenBranchMatchedException();
            }
            destination$iv$iv.add(lowerBound);
        }
        List lowerBounds = (List) destination$iv$iv;
        if (hasErrorType) {
            SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType("Intersection of error types: " + types);
            Intrinsics.checkExpressionValueIsNotNull(simpleTypeCreateErrorType, "ErrorUtils.createErrorTy… of error types: $types\")");
            return simpleTypeCreateErrorType;
        }
        if (!hasFlexibleTypes) {
            return TypeIntersector.INSTANCE.intersectTypes$descriptors(lowerBounds);
        }
        List<? extends UnwrappedType> $receiver$iv2 = types;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        for (Object item$iv$iv2 : $receiver$iv2) {
            destination$iv$iv2.add(FlexibleTypesKt.upperIfFlexible((UnwrappedType) item$iv$iv2));
        }
        List upperBounds = (List) destination$iv$iv2;
        return KotlinTypeFactory.flexibleType(TypeIntersector.INSTANCE.intersectTypes$descriptors(lowerBounds), TypeIntersector.INSTANCE.intersectTypes$descriptors(upperBounds));
    }
}
