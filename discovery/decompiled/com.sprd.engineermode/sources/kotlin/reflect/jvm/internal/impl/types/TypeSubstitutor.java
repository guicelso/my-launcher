package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.FilteredAnnotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.reflect.jvm.internal.impl.utils.ExceptionUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class TypeSubstitutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final TypeSubstitutor EMPTY = create(TypeSubstitution.EMPTY);

    @NotNull
    private final TypeSubstitution substitution;

    private enum VarianceConflictType {
        NO_CONFLICT,
        IN_IN_OUT_POSITION,
        OUT_IN_IN_POSITION
    }

    private static final class SubstitutionException extends Exception {
        public SubstitutionException(String message) {
            super(message);
        }
    }

    @NotNull
    public static TypeSubstitutor create(@NotNull TypeSubstitution substitution) {
        return new TypeSubstitutor(substitution);
    }

    @NotNull
    public static TypeSubstitutor createChainedSubstitutor(@NotNull TypeSubstitution first, @NotNull TypeSubstitution second) {
        return create(DisjointKeysUnionTypeSubstitution.create(first, second));
    }

    @NotNull
    public static TypeSubstitutor create(@NotNull KotlinType context) {
        return create(TypeConstructorSubstitution.create(context.getConstructor(), context.getArguments()));
    }

    protected TypeSubstitutor(@NotNull TypeSubstitution substitution) {
        this.substitution = substitution;
    }

    public boolean isEmpty() {
        return this.substitution.isEmpty();
    }

    @NotNull
    public TypeSubstitution getSubstitution() {
        return this.substitution;
    }

    @NotNull
    public KotlinType safeSubstitute(@NotNull KotlinType type, @NotNull Variance howThisTypeIsUsed) {
        if (isEmpty()) {
            return type;
        }
        try {
            return unsafeSubstitute(new TypeProjectionImpl(howThisTypeIsUsed, type), 0).getType();
        } catch (SubstitutionException e) {
            return ErrorUtils.createErrorType(e.getMessage());
        }
    }

    @Nullable
    public KotlinType substitute(@NotNull KotlinType type, @NotNull Variance howThisTypeIsUsed) {
        TypeProjection projection = substitute(new TypeProjectionImpl(howThisTypeIsUsed, getSubstitution().prepareTopLevelType(type, howThisTypeIsUsed)));
        if (projection == null) {
            return null;
        }
        return projection.getType();
    }

    @Nullable
    public TypeProjection substitute(@NotNull TypeProjection typeProjection) {
        TypeProjection substitutedTypeProjection = substituteWithoutApproximation(typeProjection);
        if (!this.substitution.approximateCapturedTypes() && !this.substitution.approximateContravariantCapturedTypes()) {
            return substitutedTypeProjection;
        }
        return CapturedTypeApproximationKt.approximateCapturedTypesIfNecessary(substitutedTypeProjection, this.substitution.approximateContravariantCapturedTypes());
    }

    @Nullable
    public TypeProjection substituteWithoutApproximation(@NotNull TypeProjection typeProjection) {
        if (isEmpty()) {
            return typeProjection;
        }
        try {
            return unsafeSubstitute(typeProjection, 0);
        } catch (SubstitutionException e) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @NotNull
    private TypeProjection unsafeSubstitute(@NotNull TypeProjection originalProjection, int recursionDepth) throws SubstitutionException {
        KotlinType substitutedType;
        assertRecursionDepth(recursionDepth, originalProjection, this.substitution);
        if (originalProjection.isStarProjection()) {
            return originalProjection;
        }
        KotlinType type = originalProjection.getType();
        if (type instanceof TypeWithEnhancement) {
            KotlinType origin = ((TypeWithEnhancement) type).getOrigin();
            KotlinType enhancement = ((TypeWithEnhancement) type).getEnhancement();
            TypeProjection substitution = unsafeSubstitute(new TypeProjectionImpl(originalProjection.getProjectionKind(), origin), recursionDepth + 1);
            KotlinType substitutedEnhancement = substitute(enhancement, originalProjection.getProjectionKind());
            KotlinType resultingType = TypeWithEnhancementKt.wrapEnhancement(substitution.getType().unwrap(), substitutedEnhancement);
            return new TypeProjectionImpl(substitution.getProjectionKind(), resultingType);
        }
        if (DynamicTypesKt.isDynamic(type) || (type.unwrap() instanceof RawType)) {
            return originalProjection;
        }
        TypeProjection replacement = this.substitution.mo384get(type);
        Variance originalProjectionKind = originalProjection.getProjectionKind();
        if (replacement == null && FlexibleTypesKt.isFlexible(type) && !TypeCapabilitiesKt.isCustomTypeVariable(type)) {
            FlexibleType flexibleType = FlexibleTypesKt.asFlexibleType(type);
            TypeProjection substitutedLower = unsafeSubstitute(new TypeProjectionImpl(originalProjectionKind, flexibleType.getLowerBound()), recursionDepth + 1);
            TypeProjection substitutedUpper = unsafeSubstitute(new TypeProjectionImpl(originalProjectionKind, flexibleType.getUpperBound()), recursionDepth + 1);
            Variance substitutedProjectionKind = substitutedLower.getProjectionKind();
            if (substitutedLower.getType() == flexibleType.getLowerBound() && substitutedUpper.getType() == flexibleType.getUpperBound()) {
                return originalProjection;
            }
            KotlinType substitutedFlexibleType = KotlinTypeFactory.flexibleType(TypeSubstitutionKt.asSimpleType(substitutedLower.getType()), TypeSubstitutionKt.asSimpleType(substitutedUpper.getType()));
            return new TypeProjectionImpl(substitutedProjectionKind, substitutedFlexibleType);
        }
        if (KotlinBuiltIns.isNothing(type) || KotlinTypeKt.isError(type)) {
            return originalProjection;
        }
        if (replacement != null) {
            VarianceConflictType varianceConflict = conflictType(originalProjectionKind, replacement.getProjectionKind());
            boolean allowVarianceConflict = CapturedTypeConstructorKt.isCaptured(type);
            if (!allowVarianceConflict) {
                int i = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[varianceConflict.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        return new TypeProjectionImpl(Variance.OUT_VARIANCE, type.getConstructor().getBuiltIns().getNullableAnyType());
                    }
                } else {
                    throw new SubstitutionException("Out-projection in in-position");
                }
            }
            CustomTypeVariable typeVariable = TypeCapabilitiesKt.getCustomTypeVariable(type);
            if (replacement.isStarProjection()) {
                return replacement;
            }
            if (typeVariable != null) {
                substitutedType = typeVariable.substitutionResult(replacement.getType());
            } else {
                KotlinType substitutedType2 = replacement.getType();
                substitutedType = TypeUtils.makeNullableIfNeeded(substitutedType2, type.isMarkedNullable());
            }
            if (!type.getAnnotations().isEmpty()) {
                Annotations typeAnnotations = filterOutUnsafeVariance(this.substitution.filterAnnotations(type.getAnnotations()));
                substitutedType = TypeUtilsKt.replaceAnnotations(substitutedType, new CompositeAnnotations(substitutedType.getAnnotations(), typeAnnotations));
            }
            Variance resultingProjectionKind = varianceConflict == VarianceConflictType.NO_CONFLICT ? combine(originalProjectionKind, replacement.getProjectionKind()) : originalProjectionKind;
            return new TypeProjectionImpl(resultingProjectionKind, substitutedType);
        }
        return substituteCompoundType(originalProjection, recursionDepth);
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType = new int[VarianceConflictType.values().length];

        static {
            try {
                $SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[VarianceConflictType.OUT_IN_IN_POSITION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[VarianceConflictType.IN_IN_OUT_POSITION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[VarianceConflictType.NO_CONFLICT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @NotNull
    private static Annotations filterOutUnsafeVariance(@NotNull Annotations annotations) {
        return !annotations.hasAnnotation(KotlinBuiltIns.FQ_NAMES.unsafeVariance) ? annotations : new FilteredAnnotations(annotations, new Function1<FqName, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor.1
            @Override // kotlin.jvm.functions.Function1
            public Boolean invoke(@NotNull FqName name) {
                return Boolean.valueOf(!name.equals(KotlinBuiltIns.FQ_NAMES.unsafeVariance));
            }
        });
    }

    private TypeProjection substituteCompoundType(TypeProjection originalProjection, int recursionDepth) throws SubstitutionException {
        KotlinType type = originalProjection.getType();
        Variance projectionKind = originalProjection.getProjectionKind();
        if (type.getConstructor().mo381getDeclarationDescriptor() instanceof TypeParameterDescriptor) {
            return originalProjection;
        }
        KotlinType substitutedAbbreviation = null;
        SimpleType abbreviation = SpecialTypesKt.getAbbreviation(type);
        if (abbreviation != null) {
            substitutedAbbreviation = substitute(abbreviation, Variance.INVARIANT);
        }
        List<TypeProjection> substitutedArguments = substituteTypeArguments(type.getConstructor().getParameters(), type.getArguments(), recursionDepth);
        KotlinType substitutedType = TypeSubstitutionKt.replace(type, substitutedArguments, this.substitution.filterAnnotations(type.getAnnotations()));
        if ((substitutedType instanceof SimpleType) && (substitutedAbbreviation instanceof SimpleType)) {
            substitutedType = SpecialTypesKt.withAbbreviation((SimpleType) substitutedType, (SimpleType) substitutedAbbreviation);
        }
        return new TypeProjectionImpl(projectionKind, substitutedType);
    }

    private List<TypeProjection> substituteTypeArguments(List<TypeParameterDescriptor> typeParameters, List<TypeProjection> typeArguments, int recursionDepth) throws SubstitutionException {
        List<TypeProjection> substitutedArguments = new ArrayList<>(typeParameters.size());
        boolean wereChanges = false;
        for (int i = 0; i < typeParameters.size(); i++) {
            TypeParameterDescriptor typeParameter = typeParameters.get(i);
            TypeProjection typeArgument = typeArguments.get(i);
            TypeProjection substitutedTypeArgument = unsafeSubstitute(typeArgument, recursionDepth + 1);
            int i2 = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$types$TypeSubstitutor$VarianceConflictType[conflictType(typeParameter.getVariance(), substitutedTypeArgument.getProjectionKind()).ordinal()];
            if (i2 != 1 && i2 != 2) {
                if (i2 == 3 && typeParameter.getVariance() != Variance.INVARIANT && !substitutedTypeArgument.isStarProjection()) {
                    substitutedTypeArgument = new TypeProjectionImpl(Variance.INVARIANT, substitutedTypeArgument.getType());
                }
            } else {
                substitutedTypeArgument = TypeUtils.makeStarProjection(typeParameter);
            }
            if (substitutedTypeArgument != typeArgument) {
                wereChanges = true;
            }
            substitutedArguments.add(substitutedTypeArgument);
        }
        return !wereChanges ? typeArguments : substitutedArguments;
    }

    @NotNull
    public static Variance combine(@NotNull Variance typeParameterVariance, @NotNull TypeProjection typeProjection) {
        return typeProjection.isStarProjection() ? Variance.OUT_VARIANCE : combine(typeParameterVariance, typeProjection.getProjectionKind());
    }

    @NotNull
    public static Variance combine(@NotNull Variance typeParameterVariance, @NotNull Variance projectionKind) {
        if (typeParameterVariance == Variance.INVARIANT) {
            return projectionKind;
        }
        if (projectionKind == Variance.INVARIANT) {
            return typeParameterVariance;
        }
        if (typeParameterVariance == projectionKind) {
            return projectionKind;
        }
        throw new AssertionError("Variance conflict: type parameter variance '" + typeParameterVariance + "' and projection kind '" + projectionKind + "' cannot be combined");
    }

    private static VarianceConflictType conflictType(Variance position, Variance argument) {
        if (position == Variance.IN_VARIANCE && argument == Variance.OUT_VARIANCE) {
            return VarianceConflictType.OUT_IN_IN_POSITION;
        }
        if (position == Variance.OUT_VARIANCE && argument == Variance.IN_VARIANCE) {
            return VarianceConflictType.IN_IN_OUT_POSITION;
        }
        return VarianceConflictType.NO_CONFLICT;
    }

    private static void assertRecursionDepth(int recursionDepth, TypeProjection projection, TypeSubstitution substitution) {
        if (recursionDepth > 100) {
            throw new IllegalStateException("Recursion too deep. Most likely infinite loop while substituting " + safeToString(projection) + "; substitution: " + safeToString(substitution));
        }
    }

    private static String safeToString(Object o) {
        try {
            return o.toString();
        } catch (Throwable e) {
            if (ExceptionUtilsKt.isProcessCanceledException(e)) {
                throw ((RuntimeException) e);
            }
            return "[Exception while computing toString(): " + e + "]";
        }
    }
}
