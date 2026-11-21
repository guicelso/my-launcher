package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeUtilsKt {
    @NotNull
    public static final KotlinBuiltIns getBuiltIns(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KotlinBuiltIns builtIns = receiver$0.getConstructor().getBuiltIns();
        Intrinsics.checkExpressionValueIsNotNull(builtIns, "constructor.builtIns");
        return builtIns;
    }

    @NotNull
    public static final KotlinType makeNullable(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(receiver$0);
        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNullable, "TypeUtils.makeNullable(this)");
        return kotlinTypeMakeNullable;
    }

    @NotNull
    public static final KotlinType makeNotNullable(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(receiver$0);
        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNotNullable, "TypeUtils.makeNotNullable(this)");
        return kotlinTypeMakeNotNullable;
    }

    public static final boolean isAnyOrNullableAny(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return KotlinBuiltIns.isAnyOrNullableAny(receiver$0);
    }

    public static final boolean isTypeParameter(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return TypeUtils.isTypeParameter(receiver$0);
    }

    public static final boolean isSubtypeOf(@NotNull KotlinType receiver$0, @NotNull KotlinType superType) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(receiver$0, superType);
    }

    @NotNull
    public static final KotlinType replaceAnnotations(@NotNull KotlinType receiver$0, @NotNull Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        return (receiver$0.getAnnotations().isEmpty() && newAnnotations.isEmpty()) ? receiver$0 : receiver$0.unwrap().replaceAnnotations(newAnnotations);
    }

    @NotNull
    public static final TypeProjection createProjection(@NotNull KotlinType type, @NotNull Variance projectionKind, @Nullable TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(projectionKind, "projectionKind");
        return new TypeProjectionImpl((typeParameterDescriptor != null ? typeParameterDescriptor.getVariance() : null) == projectionKind ? Variance.INVARIANT : projectionKind, type);
    }

    @NotNull
    public static final TypeProjection asTypeProjection(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return new TypeProjectionImpl(receiver$0);
    }

    public static final boolean contains(@NotNull KotlinType receiver$0, @NotNull Function1<? super UnwrappedType, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        return TypeUtils.contains(receiver$0, predicate);
    }

    @NotNull
    public static final KotlinType replaceArgumentsWithStarProjections(@NotNull KotlinType receiver$0) {
        SimpleType simpleTypeFlexibleType;
        boolean z;
        boolean z2;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType unwrapped$iv = receiver$0.unwrap();
        if (unwrapped$iv instanceof FlexibleType) {
            SimpleType $receiver$iv$iv = ((FlexibleType) unwrapped$iv).getLowerBound();
            if ($receiver$iv$iv.getConstructor().getParameters().isEmpty() || $receiver$iv$iv.getConstructor().mo381getDeclarationDescriptor() == null) {
                z = false;
                z2 = false;
            } else {
                Iterable parameters = $receiver$iv$iv.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters, "constructor.parameters");
                Iterable $receiver$iv$iv$iv = parameters;
                z = false;
                Collection destination$iv$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv$iv$iv, 10));
                z2 = false;
                for (Object item$iv$iv$iv$iv : $receiver$iv$iv$iv) {
                    TypeParameterDescriptor p1 = (TypeParameterDescriptor) item$iv$iv$iv$iv;
                    destination$iv$iv$iv$iv.add(new StarProjectionImpl(p1));
                }
                List newArguments$iv$iv = (List) destination$iv$iv$iv$iv;
                $receiver$iv$iv = TypeSubstitutionKt.replace$default($receiver$iv$iv, newArguments$iv$iv, (Annotations) null, 2, (Object) null);
            }
            SimpleType $receiver$iv$iv2 = ((FlexibleType) unwrapped$iv).getUpperBound();
            if (!$receiver$iv$iv2.getConstructor().getParameters().isEmpty() && $receiver$iv$iv2.getConstructor().mo381getDeclarationDescriptor() != null) {
                Iterable parameters2 = $receiver$iv$iv2.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters2, "constructor.parameters");
                Iterable $receiver$iv$iv$iv2 = parameters2;
                Collection destination$iv$iv$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv$iv$iv2, 10));
                for (Object item$iv$iv$iv$iv2 : $receiver$iv$iv$iv2) {
                    TypeParameterDescriptor p2 = (TypeParameterDescriptor) item$iv$iv$iv$iv2;
                    destination$iv$iv$iv$iv2.add(new StarProjectionImpl(p2));
                }
                List newArguments$iv$iv2 = (List) destination$iv$iv$iv$iv2;
                $receiver$iv$iv2 = TypeSubstitutionKt.replace$default($receiver$iv$iv2, newArguments$iv$iv2, (Annotations) null, 2, (Object) null);
            }
            simpleTypeFlexibleType = KotlinTypeFactory.flexibleType($receiver$iv$iv, $receiver$iv$iv2);
        } else {
            if (!(unwrapped$iv instanceof SimpleType)) {
                throw new NoWhenBranchMatchedException();
            }
            SimpleType $receiver$iv$iv3 = (SimpleType) unwrapped$iv;
            if (!$receiver$iv$iv3.getConstructor().getParameters().isEmpty() && $receiver$iv$iv3.getConstructor().mo381getDeclarationDescriptor() != null) {
                Iterable parameters3 = $receiver$iv$iv3.getConstructor().getParameters();
                Intrinsics.checkExpressionValueIsNotNull(parameters3, "constructor.parameters");
                Iterable $receiver$iv$iv$iv3 = parameters3;
                Collection destination$iv$iv$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv$iv$iv3, 10));
                for (Object item$iv$iv$iv$iv3 : $receiver$iv$iv$iv3) {
                    TypeParameterDescriptor p3 = (TypeParameterDescriptor) item$iv$iv$iv$iv3;
                    destination$iv$iv$iv$iv3.add(new StarProjectionImpl(p3));
                }
                List newArguments$iv$iv3 = (List) destination$iv$iv$iv$iv3;
                $receiver$iv$iv3 = TypeSubstitutionKt.replace$default($receiver$iv$iv3, newArguments$iv$iv3, (Annotations) null, 2, (Object) null);
            }
            simpleTypeFlexibleType = $receiver$iv$iv3;
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrapped$iv);
    }

    public static final boolean canHaveUndefinedNullability(@NotNull UnwrappedType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return (receiver$0.getConstructor() instanceof NewTypeVariableConstructor) || (receiver$0.getConstructor().mo381getDeclarationDescriptor() instanceof TypeParameterDescriptor) || (receiver$0 instanceof NewCapturedType);
    }
}
