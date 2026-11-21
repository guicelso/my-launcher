package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeCapabilitiesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class TypeCheckingProcedure {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final TypeCheckingProcedureCallbacks constraints;

    @Nullable
    public static KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        return findCorrespondingSupertype(subtype, supertype, new TypeCheckerProcedureCallbacksImpl());
    }

    @Nullable
    public static KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype, @NotNull TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        return UtilsKt.findCorrespondingSupertype(subtype, supertype, typeCheckingProcedureCallbacks);
    }

    @NotNull
    private static KotlinType getOutType(@NotNull TypeParameterDescriptor parameter, @NotNull TypeProjection argument) {
        boolean isInProjected = argument.getProjectionKind() == Variance.IN_VARIANCE || parameter.getVariance() == Variance.IN_VARIANCE;
        return isInProjected ? DescriptorUtilsKt.getBuiltIns(parameter).getNullableAnyType() : argument.getType();
    }

    @NotNull
    private static KotlinType getInType(@NotNull TypeParameterDescriptor parameter, @NotNull TypeProjection argument) {
        boolean isOutProjected = argument.getProjectionKind() == Variance.OUT_VARIANCE || parameter.getVariance() == Variance.OUT_VARIANCE;
        return isOutProjected ? DescriptorUtilsKt.getBuiltIns(parameter).getNothingType() : argument.getType();
    }

    public TypeCheckingProcedure(TypeCheckingProcedureCallbacks constraints) {
        this.constraints = constraints;
    }

    public boolean equalTypes(@NotNull KotlinType type1, @NotNull KotlinType type2) {
        if (type1 == type2) {
            return true;
        }
        if (FlexibleTypesKt.isFlexible(type1)) {
            if (FlexibleTypesKt.isFlexible(type2)) {
                return !KotlinTypeKt.isError(type1) && !KotlinTypeKt.isError(type2) && isSubtypeOf(type1, type2) && isSubtypeOf(type2, type1);
            }
            return heterogeneousEquivalence(type2, type1);
        }
        if (FlexibleTypesKt.isFlexible(type2)) {
            return heterogeneousEquivalence(type1, type2);
        }
        if (type1.isMarkedNullable() != type2.isMarkedNullable()) {
            return false;
        }
        if (type1.isMarkedNullable()) {
            return this.constraints.assertEqualTypes(TypeUtils.makeNotNullable(type1), TypeUtils.makeNotNullable(type2), this);
        }
        TypeConstructor constructor1 = type1.getConstructor();
        TypeConstructor constructor2 = type2.getConstructor();
        if (!this.constraints.assertEqualTypeConstructors(constructor1, constructor2)) {
            return false;
        }
        List<TypeProjection> type1Arguments = type1.getArguments();
        List<TypeProjection> type2Arguments = type2.getArguments();
        if (type1Arguments.size() != type2Arguments.size()) {
            return false;
        }
        for (int i = 0; i < type1Arguments.size(); i++) {
            TypeProjection typeProjection1 = type1Arguments.get(i);
            TypeProjection typeProjection2 = type2Arguments.get(i);
            if (!typeProjection1.isStarProjection() || !typeProjection2.isStarProjection()) {
                TypeParameterDescriptor typeParameter1 = constructor1.getParameters().get(i);
                TypeParameterDescriptor typeParameter2 = constructor2.getParameters().get(i);
                if (!capture(typeProjection1, typeProjection2, typeParameter1) && (getEffectiveProjectionKind(typeParameter1, typeProjection1) != getEffectiveProjectionKind(typeParameter2, typeProjection2) || !this.constraints.assertEqualTypes(typeProjection1.getType(), typeProjection2.getType(), this))) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean heterogeneousEquivalence(KotlinType inflexibleType, KotlinType flexibleType) {
        return isSubtypeOf(FlexibleTypesKt.asFlexibleType(flexibleType).getLowerBound(), inflexibleType) && isSubtypeOf(inflexibleType, FlexibleTypesKt.asFlexibleType(flexibleType).getUpperBound());
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$types$Variance = new int[Variance.values().length];

        static {
            try {
                $SwitchMap$org$jetbrains$kotlin$types$Variance[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$Variance[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$Variance[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum EnrichedProjectionKind {
        IN,
        OUT,
        INV,
        STAR;

        @NotNull
        public static EnrichedProjectionKind fromVariance(@NotNull Variance variance) {
            int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$types$Variance[variance.ordinal()];
            if (i == 1) {
                return INV;
            }
            if (i == 2) {
                return IN;
            }
            if (i == 3) {
                return OUT;
            }
            throw new IllegalStateException("Unknown variance");
        }
    }

    public static EnrichedProjectionKind getEffectiveProjectionKind(@NotNull TypeParameterDescriptor typeParameter, @NotNull TypeProjection typeArgument) {
        Variance a = typeParameter.getVariance();
        Variance b = typeArgument.getProjectionKind();
        if (b == Variance.INVARIANT) {
            a = b;
            b = a;
        }
        Variance t = Variance.IN_VARIANCE;
        if (a == t && b == Variance.OUT_VARIANCE) {
            return EnrichedProjectionKind.STAR;
        }
        if (a == Variance.OUT_VARIANCE && b == Variance.IN_VARIANCE) {
            return EnrichedProjectionKind.STAR;
        }
        return EnrichedProjectionKind.fromVariance(b);
    }

    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (TypeCapabilitiesKt.sameTypeConstructors(subtype, supertype)) {
            return !subtype.isMarkedNullable() || supertype.isMarkedNullable();
        }
        KotlinType subtypeRepresentative = TypeCapabilitiesKt.getSubtypeRepresentative(subtype);
        KotlinType supertypeRepresentative = TypeCapabilitiesKt.getSupertypeRepresentative(supertype);
        if (subtypeRepresentative != subtype || supertypeRepresentative != supertype) {
            return isSubtypeOf(subtypeRepresentative, supertypeRepresentative);
        }
        return isSubtypeOfForRepresentatives(subtype, supertype);
    }

    private boolean isSubtypeOfForRepresentatives(KotlinType subtype, KotlinType supertype) {
        if (KotlinTypeKt.isError(subtype) || KotlinTypeKt.isError(supertype)) {
            return true;
        }
        if (!supertype.isMarkedNullable() && subtype.isMarkedNullable()) {
            return false;
        }
        if (KotlinBuiltIns.isNothingOrNullableNothing(subtype)) {
            return true;
        }
        KotlinType closestSupertype = findCorrespondingSupertype(subtype, supertype, this.constraints);
        if (closestSupertype == null) {
            return this.constraints.noCorrespondingSupertype(subtype, supertype);
        }
        if (supertype.isMarkedNullable() || !closestSupertype.isMarkedNullable()) {
            return checkSubtypeForTheSameConstructor(closestSupertype, supertype);
        }
        return false;
    }

    private boolean checkSubtypeForTheSameConstructor(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        TypeConstructor constructor = subtype.getConstructor();
        List<TypeProjection> subArguments = subtype.getArguments();
        List<TypeProjection> superArguments = supertype.getArguments();
        boolean z = false;
        if (subArguments.size() != superArguments.size()) {
            return false;
        }
        List<TypeParameterDescriptor> parameters = constructor.getParameters();
        int i = 0;
        while (true) {
            boolean argumentIsErrorType = true;
            if (i >= parameters.size()) {
                return true;
            }
            TypeParameterDescriptor parameter = parameters.get(i);
            TypeProjection superArgument = superArguments.get(i);
            TypeProjection subArgument = subArguments.get(i);
            if (!superArgument.isStarProjection() && !capture(subArgument, superArgument, parameter)) {
                if (!KotlinTypeKt.isError(subArgument.getType()) && !KotlinTypeKt.isError(superArgument.getType())) {
                    argumentIsErrorType = z;
                }
                if (!argumentIsErrorType && parameter.getVariance() == Variance.INVARIANT && subArgument.getProjectionKind() == Variance.INVARIANT && superArgument.getProjectionKind() == Variance.INVARIANT) {
                    if (!this.constraints.assertEqualTypes(subArgument.getType(), superArgument.getType(), this)) {
                        return z;
                    }
                } else {
                    KotlinType superOut = getOutType(parameter, superArgument);
                    KotlinType subOut = getOutType(parameter, subArgument);
                    if (!this.constraints.assertSubtype(subOut, superOut, this)) {
                        return z;
                    }
                    KotlinType superIn = getInType(parameter, superArgument);
                    KotlinType subIn = getInType(parameter, subArgument);
                    if (superArgument.getProjectionKind() != Variance.OUT_VARIANCE) {
                        if (!this.constraints.assertSubtype(superIn, subIn, this)) {
                            return false;
                        }
                        z = false;
                    } else {
                        z = false;
                    }
                }
            }
            i++;
        }
    }

    private boolean capture(@NotNull TypeProjection subtypeArgumentProjection, @NotNull TypeProjection supertypeArgumentProjection, @NotNull TypeParameterDescriptor parameter) {
        if (parameter.getVariance() == Variance.INVARIANT && subtypeArgumentProjection.getProjectionKind() != Variance.INVARIANT && supertypeArgumentProjection.getProjectionKind() == Variance.INVARIANT) {
            return this.constraints.capture(supertypeArgumentProjection.getType(), subtypeArgumentProjection);
        }
        return false;
    }
}
