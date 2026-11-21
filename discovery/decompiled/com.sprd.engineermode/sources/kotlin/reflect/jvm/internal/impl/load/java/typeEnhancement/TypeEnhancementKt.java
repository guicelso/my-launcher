package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeEnhancementKt {
    private static final EnhancedTypeAnnotations ENHANCED_MUTABILITY_ANNOTATIONS;
    private static final EnhancedTypeAnnotations ENHANCED_NULLABILITY_ANNOTATIONS;

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[MutabilityQualifier.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            $EnumSwitchMapping$0[MutabilityQualifier.READ_ONLY.ordinal()] = 1;
            $EnumSwitchMapping$0[MutabilityQualifier.MUTABLE.ordinal()] = 2;
            $EnumSwitchMapping$1 = new int[NullabilityQualifier.values().length];
            $EnumSwitchMapping$1[NullabilityQualifier.NULLABLE.ordinal()] = 1;
            $EnumSwitchMapping$1[NullabilityQualifier.NOT_NULL.ordinal()] = 2;
        }
    }

    @Nullable
    public static final KotlinType enhance(@NotNull KotlinType receiver$0, @NotNull Function1<? super Integer, JavaTypeQualifiers> qualifiers) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(qualifiers, "qualifiers");
        return enhancePossiblyFlexible(receiver$0.unwrap(), qualifiers, 0).getTypeIfChanged();
    }

    public static final boolean hasEnhancedNullability(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Annotations annotations = receiver$0.getAnnotations();
        FqName fqName = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        return annotations.mo374findAnnotation(fqName) != null;
    }

    private static final Result enhancePossiblyFlexible(@NotNull UnwrappedType $receiver, Function1<? super Integer, JavaTypeQualifiers> function1, int index) {
        UnwrappedType type;
        boolean wereChanges = true;
        if (KotlinTypeKt.isError($receiver)) {
            return new Result($receiver, 1, false);
        }
        if ($receiver instanceof FlexibleType) {
            SimpleResult lowerResult = enhanceInflexible(((FlexibleType) $receiver).getLowerBound(), function1, index, TypeComponentPosition.FLEXIBLE_LOWER);
            SimpleResult upperResult = enhanceInflexible(((FlexibleType) $receiver).getUpperBound(), function1, index, TypeComponentPosition.FLEXIBLE_UPPER);
            boolean z = lowerResult.getSubtreeSize() == upperResult.getSubtreeSize();
            if (_Assertions.ENABLED && !z) {
                throw new AssertionError("Different tree sizes of bounds: lower = (" + ((FlexibleType) $receiver).getLowerBound() + ", " + lowerResult.getSubtreeSize() + "), upper = (" + ((FlexibleType) $receiver).getUpperBound() + ", " + upperResult.getSubtreeSize() + ')');
            }
            if (!lowerResult.getWereChanges() && !upperResult.getWereChanges()) {
                wereChanges = false;
            }
            KotlinType enhancement = TypeWithEnhancementKt.getEnhancement(lowerResult.getType());
            if (enhancement == null) {
                enhancement = TypeWithEnhancementKt.getEnhancement(upperResult.getType());
            }
            if (wereChanges) {
                type = TypeWithEnhancementKt.wrapEnhancement($receiver instanceof RawTypeImpl ? new RawTypeImpl(lowerResult.getType(), upperResult.getType()) : KotlinTypeFactory.flexibleType(lowerResult.getType(), upperResult.getType()), enhancement);
            } else {
                type = $receiver;
            }
            return new Result(type, lowerResult.getSubtreeSize(), wereChanges);
        }
        if ($receiver instanceof SimpleType) {
            return enhanceInflexible((SimpleType) $receiver, function1, index, TypeComponentPosition.INFLEXIBLE);
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final SimpleResult enhanceInflexible(@NotNull SimpleType $receiver, Function1<? super Integer, JavaTypeQualifiers> function1, int index, TypeComponentPosition position) {
        ClassifierDescriptor originalClass;
        int globalArgIndex;
        TypeProjection typeProjectionCreateProjection;
        Function1<? super Integer, JavaTypeQualifiers> function2 = function1;
        boolean shouldEnhance = shouldEnhance(position);
        if ((shouldEnhance || !$receiver.getArguments().isEmpty()) && (originalClass = $receiver.getConstructor().mo381getDeclarationDescriptor()) != null) {
            Intrinsics.checkExpressionValueIsNotNull(originalClass, "constructor.declarationD…pleResult(this, 1, false)");
            JavaTypeQualifiers effectiveQualifiers = function2.invoke(Integer.valueOf(index));
            EnhancementResult<ClassifierDescriptor> enhancementResultEnhanceMutability = enhanceMutability(originalClass, effectiveQualifiers, position);
            ClassifierDescriptor enhancedClassifier = enhancementResultEnhanceMutability.component1();
            Annotations enhancedMutabilityAnnotations = enhancementResultEnhanceMutability.component2();
            TypeConstructor typeConstructor = enhancedClassifier.getTypeConstructor();
            Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "enhancedClassifier.typeConstructor");
            int globalArgIndex2 = index + 1;
            boolean wereChanges = enhancedMutabilityAnnotations != null;
            Iterable $receiver$iv = $receiver.getArguments();
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            Iterable $receiver$iv$iv = $receiver$iv;
            int globalArgIndex3 = 0;
            int globalArgIndex4 = globalArgIndex2;
            for (Object item$iv$iv : $receiver$iv$iv) {
                int index$iv$iv = globalArgIndex3 + 1;
                if (globalArgIndex3 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                TypeProjection arg = (TypeProjection) item$iv$iv;
                if (arg.isStarProjection()) {
                    int globalArgIndex5 = globalArgIndex4 + 1;
                    TypeConstructor typeConstructor2 = enhancedClassifier.getTypeConstructor();
                    Intrinsics.checkExpressionValueIsNotNull(typeConstructor2, "enhancedClassifier.typeConstructor");
                    int localArgIndex = globalArgIndex3;
                    typeProjectionCreateProjection = TypeUtils.makeStarProjection(typeConstructor2.getParameters().get(localArgIndex));
                    globalArgIndex = globalArgIndex5;
                } else {
                    int localArgIndex2 = globalArgIndex3;
                    Result enhanced = enhancePossiblyFlexible(arg.getType().unwrap(), function2, globalArgIndex4);
                    wereChanges = wereChanges || enhanced.getWereChanges();
                    int globalArgIndex6 = globalArgIndex4 + enhanced.getSubtreeSize();
                    KotlinType type = enhanced.getType();
                    globalArgIndex = globalArgIndex6;
                    Variance projectionKind = arg.getProjectionKind();
                    Intrinsics.checkExpressionValueIsNotNull(projectionKind, "arg.projectionKind");
                    typeProjectionCreateProjection = TypeUtilsKt.createProjection(type, projectionKind, typeConstructor.getParameters().get(localArgIndex2));
                }
                destination$iv$iv.add(typeProjectionCreateProjection);
                function2 = function1;
                globalArgIndex4 = globalArgIndex;
                globalArgIndex3 = index$iv$iv;
                originalClass = originalClass;
                $receiver$iv$iv = $receiver$iv$iv;
            }
            List enhancedArguments = (List) destination$iv$iv;
            EnhancementResult<Boolean> enhancedNullability = getEnhancedNullability($receiver, effectiveQualifiers, position);
            boolean enhancedNullability2 = enhancedNullability.component1().booleanValue();
            Annotations enhancedNullabilityAnnotations = enhancedNullability.component2();
            boolean wereChanges2 = wereChanges || enhancedNullabilityAnnotations != null;
            int subtreeSize = globalArgIndex4 - index;
            if (!wereChanges2) {
                return new SimpleResult($receiver, subtreeSize, false);
            }
            Annotations newAnnotations = compositeAnnotationsOrSingle(CollectionsKt.filterNotNull(CollectionsKt.listOf((Object[]) new Annotations[]{$receiver.getAnnotations(), enhancedMutabilityAnnotations, enhancedNullabilityAnnotations})));
            SimpleType enhancedType = KotlinTypeFactory.simpleType(newAnnotations, typeConstructor, enhancedArguments, enhancedNullability2);
            SimpleType enhancement = effectiveQualifiers.isNotNullTypeParameter$descriptors_jvm() ? new NotNullTypeParameter(enhancedType) : enhancedType;
            boolean nullabilityForWarning = enhancedNullabilityAnnotations != null && effectiveQualifiers.isNullabilityQualifierForWarning$descriptors_jvm();
            SimpleType result = nullabilityForWarning ? TypeWithEnhancementKt.wrapEnhancement($receiver, enhancement) : enhancement;
            if (result != null) {
                return new SimpleResult((SimpleType) result, subtreeSize, true);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new SimpleResult($receiver, 1, false);
    }

    private static final Annotations compositeAnnotationsOrSingle(@NotNull List<? extends Annotations> list) {
        int size = list.size();
        if (size == 0) {
            throw new IllegalStateException("At least one Annotations object expected".toString());
        }
        if (size == 1) {
            return (Annotations) CollectionsKt.single((List) list);
        }
        return new CompositeAnnotations((List<? extends Annotations>) CollectionsKt.toList(list));
    }

    private static final boolean shouldEnhance(@NotNull TypeComponentPosition $receiver) {
        return $receiver != TypeComponentPosition.INFLEXIBLE;
    }

    private static final <T> EnhancementResult<T> noChange(T t) {
        return new EnhancementResult<>(t, null);
    }

    private static final <T> EnhancementResult<T> enhancedNullability(T t) {
        return new EnhancementResult<>(t, ENHANCED_NULLABILITY_ANNOTATIONS);
    }

    private static final <T> EnhancementResult<T> enhancedMutability(T t) {
        return new EnhancementResult<>(t, ENHANCED_MUTABILITY_ANNOTATIONS);
    }

    private static final EnhancementResult<ClassifierDescriptor> enhanceMutability(@NotNull ClassifierDescriptor $receiver, JavaTypeQualifiers qualifiers, TypeComponentPosition position) {
        if (shouldEnhance(position) && ($receiver instanceof ClassDescriptor)) {
            JavaToKotlinClassMap mapping = JavaToKotlinClassMap.INSTANCE;
            MutabilityQualifier mutability = qualifiers.getMutability();
            if (mutability != null) {
                int i = WhenMappings.$EnumSwitchMapping$0[mutability.ordinal()];
                if (i == 1) {
                    if (position == TypeComponentPosition.FLEXIBLE_LOWER && mapping.isMutable((ClassDescriptor) $receiver)) {
                        return enhancedMutability(mapping.convertMutableToReadOnly((ClassDescriptor) $receiver));
                    }
                } else if (i == 2 && position == TypeComponentPosition.FLEXIBLE_UPPER && mapping.isReadOnly((ClassDescriptor) $receiver)) {
                    return enhancedMutability(mapping.convertReadOnlyToMutable((ClassDescriptor) $receiver));
                }
            }
            return noChange($receiver);
        }
        return noChange($receiver);
    }

    private static final EnhancementResult<Boolean> getEnhancedNullability(@NotNull KotlinType $receiver, JavaTypeQualifiers qualifiers, TypeComponentPosition position) {
        if (!shouldEnhance(position)) {
            return noChange(Boolean.valueOf($receiver.isMarkedNullable()));
        }
        NullabilityQualifier nullability = qualifiers.getNullability();
        if (nullability != null) {
            int i = WhenMappings.$EnumSwitchMapping$1[nullability.ordinal()];
            if (i == 1) {
                return enhancedNullability(true);
            }
            if (i == 2) {
                return enhancedNullability(false);
            }
        }
        return noChange(Boolean.valueOf($receiver.isMarkedNullable()));
    }

    static {
        FqName fqName = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION");
        ENHANCED_NULLABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName);
        FqName fqName2 = JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "JvmAnnotationNames.ENHANCED_MUTABILITY_ANNOTATION");
        ENHANCED_MUTABILITY_ANNOTATIONS = new EnhancedTypeAnnotations(fqName2);
    }
}
