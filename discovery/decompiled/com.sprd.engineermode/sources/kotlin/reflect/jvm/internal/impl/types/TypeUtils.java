package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class TypeUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final SimpleType DONT_CARE = ErrorUtils.createErrorTypeWithCustomDebugName("DONT_CARE");
    public static final SimpleType CANT_INFER_FUNCTION_PARAM_TYPE = ErrorUtils.createErrorType("Cannot be inferred");

    @NotNull
    public static final SimpleType NO_EXPECTED_TYPE = new SpecialType("NO_EXPECTED_TYPE");
    public static final SimpleType UNIT_EXPECTED_TYPE = new SpecialType("UNIT_EXPECTED_TYPE");

    public static class SpecialType extends DelegatingSimpleType {
        private final String name;

        public SpecialType(String name) {
            this.name = name;
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.DelegatingSimpleType
        @NotNull
        protected SimpleType getDelegate() {
            throw new IllegalStateException(this.name);
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
        @NotNull
        public SimpleType replaceAnnotations(@NotNull Annotations newAnnotations) {
            throw new IllegalStateException(this.name);
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
        @NotNull
        public SimpleType makeNullableAsSpecified(boolean newNullability) {
            throw new IllegalStateException(this.name);
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.SimpleType
        @NotNull
        public String toString() {
            return this.name;
        }
    }

    public static boolean noExpectedType(@NotNull KotlinType type) {
        return type == NO_EXPECTED_TYPE || type == UNIT_EXPECTED_TYPE;
    }

    public static boolean isDontCarePlaceholder(@Nullable KotlinType type) {
        return type != null && type.getConstructor() == DONT_CARE.getConstructor();
    }

    @NotNull
    public static KotlinType makeNullable(@NotNull KotlinType type) {
        return makeNullableAsSpecified(type, true);
    }

    @NotNull
    public static KotlinType makeNotNullable(@NotNull KotlinType type) {
        return makeNullableAsSpecified(type, false);
    }

    @NotNull
    public static KotlinType makeNullableAsSpecified(@NotNull KotlinType type, boolean nullable) {
        return type.unwrap().makeNullableAsSpecified(nullable);
    }

    @NotNull
    public static KotlinType makeNullableIfNeeded(@NotNull KotlinType type, boolean nullable) {
        if (nullable) {
            return makeNullable(type);
        }
        return type;
    }

    @NotNull
    public static SimpleType makeUnsubstitutedType(ClassifierDescriptor classifierDescriptor, MemberScope unsubstitutedMemberScope) {
        if (ErrorUtils.isError(classifierDescriptor)) {
            return ErrorUtils.createErrorType("Unsubstituted type for " + classifierDescriptor);
        }
        TypeConstructor typeConstructor = classifierDescriptor.getTypeConstructor();
        List<TypeProjection> arguments = getDefaultTypeProjections(typeConstructor.getParameters());
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), typeConstructor, arguments, false, unsubstitutedMemberScope);
    }

    @NotNull
    public static List<TypeProjection> getDefaultTypeProjections(@NotNull List<TypeParameterDescriptor> parameters) {
        List<TypeProjection> result = new ArrayList<>(parameters.size());
        for (TypeParameterDescriptor parameterDescriptor : parameters) {
            result.add(new TypeProjectionImpl(parameterDescriptor.getDefaultType()));
        }
        return CollectionsKt.toList(result);
    }

    @NotNull
    public static List<KotlinType> getImmediateSupertypes(@NotNull KotlinType type) {
        TypeSubstitutor substitutor = TypeSubstitutor.create(type);
        Collection<KotlinType> originalSupertypes = type.getConstructor().getSupertypes();
        List<KotlinType> result = new ArrayList<>(originalSupertypes.size());
        for (KotlinType supertype : originalSupertypes) {
            KotlinType substitutedType = createSubstitutedSupertype(type, supertype, substitutor);
            if (substitutedType != null) {
                result.add(substitutedType);
            }
        }
        return result;
    }

    @Nullable
    public static KotlinType createSubstitutedSupertype(@NotNull KotlinType subType, @NotNull KotlinType superType, @NotNull TypeSubstitutor substitutor) {
        KotlinType substitutedType = substitutor.substitute(superType, Variance.INVARIANT);
        if (substitutedType != null) {
            return makeNullableIfNeeded(substitutedType, subType.isMarkedNullable());
        }
        return null;
    }

    public static boolean isNullableType(@NotNull KotlinType type) {
        if (type.isMarkedNullable()) {
            return true;
        }
        if (FlexibleTypesKt.isFlexible(type) && isNullableType(FlexibleTypesKt.asFlexibleType(type).getUpperBound())) {
            return true;
        }
        if (isTypeParameter(type)) {
            return hasNullableSuperType(type);
        }
        TypeConstructor constructor = type.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            for (KotlinType supertype : constructor.getSupertypes()) {
                if (isNullableType(supertype)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean acceptsNullable(@NotNull KotlinType type) {
        if (type.isMarkedNullable()) {
            return true;
        }
        return FlexibleTypesKt.isFlexible(type) && acceptsNullable(FlexibleTypesKt.asFlexibleType(type).getUpperBound());
    }

    public static boolean hasNullableSuperType(@NotNull KotlinType type) {
        if (type.getConstructor().mo381getDeclarationDescriptor() instanceof ClassDescriptor) {
            return false;
        }
        for (KotlinType supertype : getImmediateSupertypes(type)) {
            if (isNullableType(supertype)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static ClassDescriptor getClassDescriptor(@NotNull KotlinType type) {
        DeclarationDescriptor declarationDescriptor = type.getConstructor().mo381getDeclarationDescriptor();
        if (declarationDescriptor instanceof ClassDescriptor) {
            return (ClassDescriptor) declarationDescriptor;
        }
        return null;
    }

    public static boolean contains(@Nullable KotlinType type, @NotNull Function1<UnwrappedType, Boolean> isSpecialType) {
        if (type == null) {
            return false;
        }
        UnwrappedType unwrappedType = type.unwrap();
        if (isSpecialType.invoke(unwrappedType).booleanValue()) {
            return true;
        }
        FlexibleType flexibleType = unwrappedType instanceof FlexibleType ? (FlexibleType) unwrappedType : null;
        if (flexibleType != null && (contains(flexibleType.getLowerBound(), isSpecialType) || contains(flexibleType.getUpperBound(), isSpecialType))) {
            return true;
        }
        if ((unwrappedType instanceof DefinitelyNotNullType) && contains(((DefinitelyNotNullType) unwrappedType).getOriginal(), isSpecialType)) {
            return true;
        }
        TypeConstructor typeConstructor = type.getConstructor();
        if (typeConstructor instanceof IntersectionTypeConstructor) {
            IntersectionTypeConstructor intersectionTypeConstructor = (IntersectionTypeConstructor) typeConstructor;
            for (KotlinType supertype : intersectionTypeConstructor.getSupertypes()) {
                if (contains(supertype, isSpecialType)) {
                    return true;
                }
            }
            return false;
        }
        for (TypeProjection projection : type.getArguments()) {
            if (!projection.isStarProjection() && contains(projection.getType(), isSpecialType)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public static TypeProjection makeStarProjection(@NotNull TypeParameterDescriptor parameterDescriptor) {
        return new StarProjectionImpl(parameterDescriptor);
    }

    public static boolean isTypeParameter(@NotNull KotlinType type) {
        return getTypeParameterDescriptorOrNull(type) != null || (type.getConstructor() instanceof NewTypeVariableConstructor);
    }

    @Nullable
    public static TypeParameterDescriptor getTypeParameterDescriptorOrNull(@NotNull KotlinType type) {
        if (type.getConstructor().mo381getDeclarationDescriptor() instanceof TypeParameterDescriptor) {
            return (TypeParameterDescriptor) type.getConstructor().mo381getDeclarationDescriptor();
        }
        return null;
    }
}
