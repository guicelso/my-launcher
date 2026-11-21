package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: CapturedTypeApproximation.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class CapturedTypeApproximationKt {

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[Variance.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            $EnumSwitchMapping$0[Variance.INVARIANT.ordinal()] = 1;
            $EnumSwitchMapping$0[Variance.IN_VARIANCE.ordinal()] = 2;
            $EnumSwitchMapping$0[Variance.OUT_VARIANCE.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[Variance.values().length];
            $EnumSwitchMapping$1[Variance.IN_VARIANCE.ordinal()] = 1;
            $EnumSwitchMapping$1[Variance.OUT_VARIANCE.ordinal()] = 2;
        }
    }

    private static final TypeProjection toTypeProjection(@NotNull final TypeArgument $receiver) {
        boolean zIsConsistent = $receiver.isConsistent();
        if (_Assertions.ENABLED && !zIsConsistent) {
            DescriptorRenderer descriptorRenderer = DescriptorRenderer.Companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                    invoke2(descriptorRendererOptions);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DescriptorRendererOptions receiver$0) {
                    Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                    receiver$0.setClassifierNamePolicy(ClassifierNamePolicy.FULLY_QUALIFIED.INSTANCE);
                }
            });
            throw new AssertionError("Only consistent enhanced type projection can be converted to type projection, but [" + descriptorRenderer.render($receiver.getTypeParameter()) + ": <" + descriptorRenderer.renderType($receiver.getInProjection()) + ", " + descriptorRenderer.renderType($receiver.getOutProjection()) + ">] was found");
        }
        Function1<Variance, Variance> function1 = new Function1<Variance, Variance>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt.toTypeProjection.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Variance invoke(@NotNull Variance variance) {
                Intrinsics.checkParameterIsNotNull(variance, "variance");
                return variance == $receiver.getTypeParameter().getVariance() ? Variance.INVARIANT : variance;
            }
        };
        if (Intrinsics.areEqual($receiver.getInProjection(), $receiver.getOutProjection())) {
            return new TypeProjectionImpl($receiver.getInProjection());
        }
        if (!KotlinBuiltIns.isNothing($receiver.getInProjection()) || $receiver.getTypeParameter().getVariance() == Variance.IN_VARIANCE) {
            return KotlinBuiltIns.isNullableAny($receiver.getOutProjection()) ? new TypeProjectionImpl(function1.invoke(Variance.IN_VARIANCE), $receiver.getInProjection()) : new TypeProjectionImpl(function1.invoke(Variance.OUT_VARIANCE), $receiver.getOutProjection());
        }
        return new TypeProjectionImpl(function1.invoke(Variance.OUT_VARIANCE), $receiver.getOutProjection());
    }

    private static final TypeArgument toTypeArgument(@NotNull TypeProjection $receiver, TypeParameterDescriptor typeParameter) {
        int i = WhenMappings.$EnumSwitchMapping$0[TypeSubstitutor.combine(typeParameter.getVariance(), $receiver).ordinal()];
        if (i == 1) {
            KotlinType type = $receiver.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "type");
            KotlinType type2 = $receiver.getType();
            Intrinsics.checkExpressionValueIsNotNull(type2, "type");
            return new TypeArgument(typeParameter, type, type2);
        }
        if (i == 2) {
            KotlinType type3 = $receiver.getType();
            Intrinsics.checkExpressionValueIsNotNull(type3, "type");
            SimpleType nullableAnyType = DescriptorUtilsKt.getBuiltIns(typeParameter).getNullableAnyType();
            Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "typeParameter.builtIns.nullableAnyType");
            return new TypeArgument(typeParameter, type3, nullableAnyType);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        SimpleType nothingType = DescriptorUtilsKt.getBuiltIns(typeParameter).getNothingType();
        Intrinsics.checkExpressionValueIsNotNull(nothingType, "typeParameter.builtIns.nothingType");
        KotlinType type4 = $receiver.getType();
        Intrinsics.checkExpressionValueIsNotNull(type4, "type");
        return new TypeArgument(typeParameter, nothingType, type4);
    }

    @Nullable
    public static final TypeProjection approximateCapturedTypesIfNecessary(@Nullable TypeProjection typeProjection, boolean approximateContravariant) {
        if (typeProjection == null) {
            return null;
        }
        if (typeProjection.isStarProjection()) {
            return typeProjection;
        }
        KotlinType type = typeProjection.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "typeProjection.type");
        if (!TypeUtils.contains(type, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt.approximateCapturedTypesIfNecessary.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(invoke2(unwrappedType));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(UnwrappedType it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                return CapturedTypeConstructorKt.isCaptured(it);
            }
        })) {
            return typeProjection;
        }
        Variance howThisTypeIsUsed = typeProjection.getProjectionKind();
        Intrinsics.checkExpressionValueIsNotNull(howThisTypeIsUsed, "typeProjection.projectionKind");
        if (howThisTypeIsUsed == Variance.OUT_VARIANCE) {
            return new TypeProjectionImpl(howThisTypeIsUsed, approximateCapturedTypes(type).getUpper());
        }
        if (approximateContravariant) {
            KotlinType approximation = approximateCapturedTypes(type).getLower();
            return new TypeProjectionImpl(howThisTypeIsUsed, approximation);
        }
        return substituteCapturedTypesWithProjections(typeProjection);
    }

    private static final TypeProjection substituteCapturedTypesWithProjections(TypeProjection typeProjection) {
        TypeSubstitutor typeSubstitutor = TypeSubstitutor.create(new TypeConstructorSubstitution() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$substituteCapturedTypesWithProjections$typeSubstitutor$1
            @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution
            @Nullable
            public TypeProjection get(@NotNull TypeConstructor key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                CapturedTypeConstructor capturedTypeConstructor = (CapturedTypeConstructor) (!(key instanceof CapturedTypeConstructor) ? null : key);
                if (capturedTypeConstructor == null) {
                    return null;
                }
                if (capturedTypeConstructor.getTypeProjection().isStarProjection()) {
                    return new TypeProjectionImpl(Variance.OUT_VARIANCE, capturedTypeConstructor.getTypeProjection().getType());
                }
                return capturedTypeConstructor.getTypeProjection();
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(typeSubstitutor, "TypeSubstitutor.create(o…ojection\n        }\n    })");
        return typeSubstitutor.substituteWithoutApproximation(typeProjection);
    }

    @NotNull
    public static final ApproximationBounds<KotlinType> approximateCapturedTypes(@NotNull final KotlinType type) {
        SimpleType simpleTypeReplaceTypeArguments;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (FlexibleTypesKt.isFlexible(type)) {
            ApproximationBounds<KotlinType> approximationBoundsApproximateCapturedTypes = approximateCapturedTypes(FlexibleTypesKt.lowerIfFlexible(type));
            ApproximationBounds<KotlinType> approximationBoundsApproximateCapturedTypes2 = approximateCapturedTypes(FlexibleTypesKt.upperIfFlexible(type));
            return new ApproximationBounds<>(TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(approximationBoundsApproximateCapturedTypes.getLower()), FlexibleTypesKt.upperIfFlexible(approximationBoundsApproximateCapturedTypes2.getLower())), type), TypeWithEnhancementKt.inheritEnhancement(KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(approximationBoundsApproximateCapturedTypes.getUpper()), FlexibleTypesKt.upperIfFlexible(approximationBoundsApproximateCapturedTypes2.getUpper())), type));
        }
        TypeConstructor typeConstructor = type.getConstructor();
        if (CapturedTypeConstructorKt.isCaptured(type)) {
            if (typeConstructor == null) {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor");
            }
            TypeProjection typeProjection = ((CapturedTypeConstructor) typeConstructor).getTypeProjection();
            Function1<KotlinType, KotlinType> function1 = new Function1<KotlinType, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt.approximateCapturedTypes.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final KotlinType invoke(@NotNull KotlinType receiver$0) {
                    Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                    KotlinType kotlinTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded(receiver$0, type.isMarkedNullable());
                    Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNullableIfNeeded, "TypeUtils.makeNullableIf…s, type.isMarkedNullable)");
                    return kotlinTypeMakeNullableIfNeeded;
                }
            };
            KotlinType type2 = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type2, "typeProjection.type");
            KotlinType bound = function1.invoke(type2);
            int i = WhenMappings.$EnumSwitchMapping$1[typeProjection.getProjectionKind().ordinal()];
            if (i == 1) {
                SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(type).getNullableAnyType();
                Intrinsics.checkExpressionValueIsNotNull(nullableAnyType, "type.builtIns.nullableAnyType");
                return new ApproximationBounds<>(bound, nullableAnyType);
            }
            if (i == 2) {
                SimpleType nothingType = TypeUtilsKt.getBuiltIns(type).getNothingType();
                Intrinsics.checkExpressionValueIsNotNull(nothingType, "type.builtIns.nothingType");
                return new ApproximationBounds<>(function1.invoke((KotlinType) nothingType), bound);
            }
            throw new AssertionError("Only nontrivial projections should have been captured, not: " + typeProjection);
        }
        if (type.getArguments().isEmpty() || type.getArguments().size() != typeConstructor.getParameters().size()) {
            return new ApproximationBounds<>(type, type);
        }
        ArrayList lowerBoundArguments = new ArrayList();
        ArrayList upperBoundArguments = new ArrayList();
        List<TypeProjection> arguments = type.getArguments();
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "typeConstructor.parameters");
        for (Pair pair : CollectionsKt.zip(arguments, parameters)) {
            TypeProjection typeProjection2 = (TypeProjection) pair.component1();
            TypeParameterDescriptor typeParameter = (TypeParameterDescriptor) pair.component2();
            Intrinsics.checkExpressionValueIsNotNull(typeParameter, "typeParameter");
            TypeArgument typeArgument = toTypeArgument(typeProjection2, typeParameter);
            if (typeProjection2.isStarProjection()) {
                lowerBoundArguments.add(typeArgument);
                upperBoundArguments.add(typeArgument);
            } else {
                ApproximationBounds<TypeArgument> approximationBoundsApproximateProjection = approximateProjection(typeArgument);
                TypeArgument lower = approximationBoundsApproximateProjection.component1();
                TypeArgument upper = approximationBoundsApproximateProjection.component2();
                lowerBoundArguments.add(lower);
                upperBoundArguments.add(upper);
            }
        }
        ArrayList $receiver$iv = lowerBoundArguments;
        boolean z = false;
        if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
            for (Object element$iv : $receiver$iv) {
                TypeArgument it = (TypeArgument) element$iv;
                if (!it.isConsistent()) {
                    z = true;
                    break;
                }
            }
        }
        boolean lowerBoundIsTrivial = z;
        if (lowerBoundIsTrivial) {
            SimpleType nothingType2 = TypeUtilsKt.getBuiltIns(type).getNothingType();
            Intrinsics.checkExpressionValueIsNotNull(nothingType2, "type.builtIns.nothingType");
            simpleTypeReplaceTypeArguments = nothingType2;
        } else {
            simpleTypeReplaceTypeArguments = replaceTypeArguments(type, lowerBoundArguments);
        }
        return new ApproximationBounds<>(simpleTypeReplaceTypeArguments, replaceTypeArguments(type, upperBoundArguments));
    }

    private static final KotlinType replaceTypeArguments(@NotNull KotlinType $receiver, List<TypeArgument> list) {
        boolean z = $receiver.getArguments().size() == list.size();
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Incorrect type arguments " + list);
        }
        List<TypeArgument> $receiver$iv = list;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            TypeArgument it = (TypeArgument) item$iv$iv;
            destination$iv$iv.add(toTypeProjection(it));
        }
        return TypeSubstitutionKt.replace$default($receiver, (List) destination$iv$iv, (Annotations) null, 2, (Object) null);
    }

    private static final ApproximationBounds<TypeArgument> approximateProjection(TypeArgument typeArgument) {
        ApproximationBounds<KotlinType> approximationBoundsApproximateCapturedTypes = approximateCapturedTypes(typeArgument.getInProjection());
        KotlinType inLower = approximationBoundsApproximateCapturedTypes.component1();
        KotlinType inUpper = approximationBoundsApproximateCapturedTypes.component2();
        ApproximationBounds<KotlinType> approximationBoundsApproximateCapturedTypes2 = approximateCapturedTypes(typeArgument.getOutProjection());
        KotlinType outLower = approximationBoundsApproximateCapturedTypes2.component1();
        KotlinType outUpper = approximationBoundsApproximateCapturedTypes2.component2();
        return new ApproximationBounds<>(new TypeArgument(typeArgument.getTypeParameter(), inUpper, outLower), new TypeArgument(typeArgument.getTypeParameter(), inLower, outUpper));
    }
}
