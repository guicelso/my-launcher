package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPrimitiveType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaWildcardType;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JavaTypeResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class JavaTypeResolver {
    private final LazyJavaResolverContext c;
    private final TypeParameterResolver typeParameterResolver;

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver$argumentsMakeSenseOnlyForMutableContainer$1, reason: invalid class name */
    /* JADX INFO: compiled from: JavaTypeResolver.kt */
    static final class AnonymousClass1 extends Lambda implements Function1<JavaType, Boolean> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Boolean invoke(JavaType javaType) {
            return Boolean.valueOf(invoke2(javaType));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2(@Nullable JavaType $receiver) {
            JavaWildcardType it = (JavaWildcardType) (!($receiver instanceof JavaWildcardType) ? null : $receiver);
            return (it == null || it.getBound() == null || it.isExtends()) ? false : true;
        }
    }

    public JavaTypeResolver(@NotNull LazyJavaResolverContext c, @NotNull TypeParameterResolver typeParameterResolver) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(typeParameterResolver, "typeParameterResolver");
        this.c = c;
        this.typeParameterResolver = typeParameterResolver;
    }

    @NotNull
    public final KotlinType transformJavaType(@NotNull JavaType javaType, @NotNull JavaTypeAttributes attr) {
        KotlinType kotlinTypeTransformJavaType;
        Intrinsics.checkParameterIsNotNull(javaType, "javaType");
        Intrinsics.checkParameterIsNotNull(attr, "attr");
        if (javaType instanceof JavaPrimitiveType) {
            PrimitiveType primitiveType = ((JavaPrimitiveType) javaType).getType();
            SimpleType primitiveKotlinType = primitiveType != null ? this.c.getModule().getBuiltIns().getPrimitiveKotlinType(primitiveType) : this.c.getModule().getBuiltIns().getUnitType();
            Intrinsics.checkExpressionValueIsNotNull(primitiveKotlinType, "if (primitiveType != nul….module.builtIns.unitType");
            return primitiveKotlinType;
        }
        if (javaType instanceof JavaClassifierType) {
            return transformJavaClassifierType((JavaClassifierType) javaType, attr);
        }
        if (javaType instanceof JavaArrayType) {
            return transformArrayType$default(this, (JavaArrayType) javaType, attr, false, 4, null);
        }
        if (!(javaType instanceof JavaWildcardType)) {
            throw new UnsupportedOperationException("Unsupported type: " + javaType);
        }
        JavaType it = ((JavaWildcardType) javaType).getBound();
        if (it != null && (kotlinTypeTransformJavaType = transformJavaType(it, attr)) != null) {
            return kotlinTypeTransformJavaType;
        }
        SimpleType defaultBound = this.c.getModule().getBuiltIns().getDefaultBound();
        Intrinsics.checkExpressionValueIsNotNull(defaultBound, "c.module.builtIns.defaultBound");
        return defaultBound;
    }

    @NotNull
    public static /* synthetic */ KotlinType transformArrayType$default(JavaTypeResolver javaTypeResolver, JavaArrayType javaArrayType, JavaTypeAttributes javaTypeAttributes, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return javaTypeResolver.transformArrayType(javaArrayType, javaTypeAttributes, z);
    }

    @NotNull
    public final KotlinType transformArrayType(@NotNull JavaArrayType arrayType, @NotNull JavaTypeAttributes attr, boolean isVararg) {
        Intrinsics.checkParameterIsNotNull(arrayType, "arrayType");
        Intrinsics.checkParameterIsNotNull(attr, "attr");
        JavaType javaComponentType = arrayType.getComponentType();
        JavaPrimitiveType javaPrimitiveType = (JavaPrimitiveType) (!(javaComponentType instanceof JavaPrimitiveType) ? null : javaComponentType);
        PrimitiveType primitiveType = javaPrimitiveType != null ? javaPrimitiveType.getType() : null;
        if (primitiveType != null) {
            SimpleType jetType = this.c.getModule().getBuiltIns().getPrimitiveArrayKotlinType(primitiveType);
            Intrinsics.checkExpressionValueIsNotNull(jetType, "c.module.builtIns.getPri…KotlinType(primitiveType)");
            if (attr.isForAnnotationParameter()) {
                return jetType;
            }
            return KotlinTypeFactory.flexibleType(jetType, jetType.makeNullableAsSpecified(true));
        }
        KotlinType componentType = transformJavaType(javaComponentType, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, attr.isForAnnotationParameter(), null, 2, null));
        if (attr.isForAnnotationParameter()) {
            Variance projectionKind = isVararg ? Variance.OUT_VARIANCE : Variance.INVARIANT;
            SimpleType arrayType2 = this.c.getModule().getBuiltIns().getArrayType(projectionKind, componentType);
            Intrinsics.checkExpressionValueIsNotNull(arrayType2, "c.module.builtIns.getArr…ctionKind, componentType)");
            return arrayType2;
        }
        SimpleType arrayType3 = this.c.getModule().getBuiltIns().getArrayType(Variance.INVARIANT, componentType);
        Intrinsics.checkExpressionValueIsNotNull(arrayType3, "c.module.builtIns.getArr…INVARIANT, componentType)");
        return KotlinTypeFactory.flexibleType(arrayType3, this.c.getModule().getBuiltIns().getArrayType(Variance.OUT_VARIANCE, componentType).makeNullableAsSpecified(true));
    }

    private final KotlinType transformJavaClassifierType(final JavaClassifierType javaType, JavaTypeAttributes attr) {
        Function0<SimpleType> function0 = new Function0<SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver.transformJavaClassifierType.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final SimpleType invoke() {
                SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType("Unresolved java class " + javaType.getPresentableText());
                Intrinsics.checkExpressionValueIsNotNull(simpleTypeCreateErrorType, "ErrorUtils.createErrorTy…vaType.presentableText}\")");
                return simpleTypeCreateErrorType;
            }
        };
        boolean useFlexible = (attr.isForAnnotationParameter() || attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
        boolean isRaw = javaType.isRaw();
        if (isRaw || useFlexible) {
            SimpleType lower = computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND), null);
            if (lower == null) {
                return function0.invoke();
            }
            SimpleType upper = computeSimpleJavaClassifierType(javaType, attr.withFlexibility(JavaTypeFlexibility.FLEXIBLE_UPPER_BOUND), lower);
            if (upper == null) {
                return function0.invoke();
            }
            if (isRaw) {
                return new RawTypeImpl(lower, upper);
            }
            return KotlinTypeFactory.flexibleType(lower, upper);
        }
        SimpleType simpleTypeComputeSimpleJavaClassifierType = computeSimpleJavaClassifierType(javaType, attr, null);
        if (simpleTypeComputeSimpleJavaClassifierType == null) {
            simpleTypeComputeSimpleJavaClassifierType = function0.invoke();
        }
        return simpleTypeComputeSimpleJavaClassifierType;
    }

    private final SimpleType computeSimpleJavaClassifierType(JavaClassifierType javaType, JavaTypeAttributes attr, SimpleType lowerResult) {
        LazyJavaAnnotations annotations;
        if (lowerResult == null || (annotations = lowerResult.getAnnotations()) == null) {
            annotations = new LazyJavaAnnotations(this.c, javaType);
        }
        TypeConstructor constructor = computeTypeConstructor(javaType, attr);
        if (constructor == null) {
            return null;
        }
        boolean isNullable = isNullable(attr);
        if (Intrinsics.areEqual(lowerResult != null ? lowerResult.getConstructor() : null, constructor) && !javaType.isRaw() && isNullable) {
            return lowerResult.makeNullableAsSpecified(true);
        }
        return KotlinTypeFactory.simpleType(annotations, constructor, computeArguments(javaType, attr, constructor), isNullable);
    }

    private final TypeConstructor computeTypeConstructor(JavaClassifierType javaType, JavaTypeAttributes attr) {
        TypeConstructor typeConstructor;
        JavaClassifier classifier = javaType.getClassifier();
        if (classifier == null) {
            return createNotFoundClass(javaType);
        }
        if (classifier instanceof JavaClass) {
            FqName fqName = ((JavaClass) classifier).getFqName();
            if (fqName == null) {
                throw new AssertionError("Class type should have a FQ name: " + classifier);
            }
            ClassDescriptor classData = mapKotlinClass(javaType, attr, fqName);
            if (classData == null) {
                classData = this.c.getComponents().getModuleClassResolver().resolveClass((JavaClass) classifier);
            }
            return (classData == null || (typeConstructor = classData.getTypeConstructor()) == null) ? createNotFoundClass(javaType) : typeConstructor;
        }
        if (classifier instanceof JavaTypeParameter) {
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = this.typeParameterResolver.resolveTypeParameter((JavaTypeParameter) classifier);
            if (typeParameterDescriptorResolveTypeParameter != null) {
                return typeParameterDescriptorResolveTypeParameter.getTypeConstructor();
            }
            return null;
        }
        throw new IllegalStateException("Unknown classifier kind: " + classifier);
    }

    private final TypeConstructor createNotFoundClass(JavaClassifierType javaType) {
        ClassId classId = ClassId.topLevel(new FqName(javaType.getClassifierQualifiedName()));
        Intrinsics.checkExpressionValueIsNotNull(classId, "ClassId.topLevel(FqName(…classifierQualifiedName))");
        TypeConstructor typeConstructor = this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses().getClass(classId, CollectionsKt.listOf(0)).getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "c.components.deserialize…istOf(0)).typeConstructor");
        return typeConstructor;
    }

    private final ClassDescriptor mapKotlinClass(JavaClassifierType javaType, JavaTypeAttributes attr, FqName fqName) {
        if (attr.isForAnnotationParameter() && Intrinsics.areEqual(fqName, JavaTypeResolverKt.JAVA_LANG_CLASS_FQ_NAME)) {
            return this.c.getComponents().getReflectionTypes().getKClass();
        }
        JavaToKotlinClassMap javaToKotlin = JavaToKotlinClassMap.INSTANCE;
        ClassDescriptor kotlinDescriptor = JavaToKotlinClassMap.mapJavaToKotlin$default(javaToKotlin, fqName, this.c.getModule().getBuiltIns(), null, 4, null);
        if (kotlinDescriptor == null) {
            return null;
        }
        if (javaToKotlin.isReadOnly(kotlinDescriptor) && (attr.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || attr.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE || argumentsMakeSenseOnlyForMutableContainer(javaType, kotlinDescriptor))) {
            return javaToKotlin.convertReadOnlyToMutable(kotlinDescriptor);
        }
        return kotlinDescriptor;
    }

    private final boolean argumentsMakeSenseOnlyForMutableContainer(@NotNull JavaClassifierType $receiver, ClassDescriptor readOnlyContainer) {
        Variance mutableLastParameterVariance;
        AnonymousClass1 isSuperWildcard$ = AnonymousClass1.INSTANCE;
        if (!isSuperWildcard$.invoke2((JavaType) CollectionsKt.lastOrNull((List) $receiver.getTypeArguments()))) {
            return false;
        }
        TypeConstructor typeConstructor = JavaToKotlinClassMap.INSTANCE.convertReadOnlyToMutable(readOnlyContainer).getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "JavaToKotlinClassMap.con…         .typeConstructor");
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "JavaToKotlinClassMap.con…ypeConstructor.parameters");
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) CollectionsKt.lastOrNull((List) parameters);
        if (typeParameterDescriptor == null || (mutableLastParameterVariance = typeParameterDescriptor.getVariance()) == null) {
            return false;
        }
        Intrinsics.checkExpressionValueIsNotNull(mutableLastParameterVariance, "JavaToKotlinClassMap.con….variance ?: return false");
        return mutableLastParameterVariance != Variance.OUT_VARIANCE;
    }

    private final List<TypeProjection> computeArguments(JavaClassifierType javaType, final JavaTypeAttributes attr, final TypeConstructor constructor) {
        final boolean isRaw = javaType.isRaw();
        int i = 0;
        boolean eraseTypeParameters = isRaw || (javaType.getTypeArguments().isEmpty() && !constructor.getParameters().isEmpty());
        List<TypeParameterDescriptor> parameters = constructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "constructor.parameters");
        if (eraseTypeParameters) {
            List<TypeParameterDescriptor> $receiver$iv = parameters;
            int $i$f$map = 0;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                final TypeParameterDescriptor parameter = (TypeParameterDescriptor) item$iv$iv;
                int $i$a$1$map = i;
                boolean eraseTypeParameters2 = eraseTypeParameters;
                Iterable $receiver$iv2 = $receiver$iv;
                int $i$f$map2 = $i$f$map;
                LazyWrappedType erasedUpperBound = new LazyWrappedType(this.c.getStorageManager(), new Function0<KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver$computeArguments$$inlined$map$lambda$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    @NotNull
                    public final KotlinType invoke() {
                        TypeParameterDescriptor parameter2 = parameter;
                        Intrinsics.checkExpressionValueIsNotNull(parameter2, "parameter");
                        return JavaTypeResolverKt.getErasedUpperBound(parameter2, attr.getUpperBoundOfTypeParameter(), new Function0<KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver$computeArguments$$inlined$map$lambda$1.1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            @NotNull
                            public final KotlinType invoke() {
                                ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = constructor.mo381getDeclarationDescriptor();
                                if (classifierDescriptorMo381getDeclarationDescriptor == null) {
                                    Intrinsics.throwNpe();
                                }
                                Intrinsics.checkExpressionValueIsNotNull(classifierDescriptorMo381getDeclarationDescriptor, "constructor.declarationDescriptor!!");
                                SimpleType defaultType = classifierDescriptorMo381getDeclarationDescriptor.getDefaultType();
                                Intrinsics.checkExpressionValueIsNotNull(defaultType, "constructor.declarationDescriptor!!.defaultType");
                                return TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType);
                            }
                        });
                    }
                });
                RawSubstitution rawSubstitution = RawSubstitution.INSTANCE;
                Intrinsics.checkExpressionValueIsNotNull(parameter, "parameter");
                destination$iv$iv.add(rawSubstitution.computeProjection(parameter, isRaw ? attr : attr.withFlexibility(JavaTypeFlexibility.INFLEXIBLE), erasedUpperBound));
                i = $i$a$1$map;
                eraseTypeParameters = eraseTypeParameters2;
                $receiver$iv = $receiver$iv2;
                $i$f$map = $i$f$map2;
            }
            return CollectionsKt.toList((List) destination$iv$iv);
        }
        if (parameters.size() != javaType.getTypeArguments().size()) {
            List<TypeParameterDescriptor> $receiver$iv3 = parameters;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10));
            for (Object item$iv$iv2 : $receiver$iv3) {
                TypeParameterDescriptor p = (TypeParameterDescriptor) item$iv$iv2;
                Intrinsics.checkExpressionValueIsNotNull(p, "p");
                destination$iv$iv2.add(new TypeProjectionImpl(ErrorUtils.createErrorType(p.getName().asString())));
            }
            return CollectionsKt.toList((List) destination$iv$iv2);
        }
        Iterable $receiver$iv4 = CollectionsKt.withIndex(javaType.getTypeArguments());
        int $i$f$map3 = 0;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv4, 10));
        for (Object item$iv$iv3 : $receiver$iv4) {
            IndexedValue indexedArgument = (IndexedValue) item$iv$iv3;
            int i2 = indexedArgument.getIndex();
            JavaType javaTypeArgument = (JavaType) indexedArgument.component2();
            boolean z = i2 < parameters.size();
            if (_Assertions.ENABLED && !z) {
                throw new AssertionError("Argument index should be less then type parameters count, but " + i2 + " > " + parameters.size());
            }
            TypeParameterDescriptor parameter2 = parameters.get(i2);
            JavaTypeAttributes attributes$default = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null);
            Intrinsics.checkExpressionValueIsNotNull(parameter2, "parameter");
            destination$iv$iv3.add(transformToTypeProjection(javaTypeArgument, attributes$default, parameter2));
            $receiver$iv4 = $receiver$iv4;
            $i$f$map3 = $i$f$map3;
        }
        return CollectionsKt.toList((List) destination$iv$iv3);
    }

    private final TypeProjection transformToTypeProjection(JavaType javaType, JavaTypeAttributes attr, TypeParameterDescriptor typeParameter) {
        if (javaType instanceof JavaWildcardType) {
            JavaType bound = ((JavaWildcardType) javaType).getBound();
            Variance projectionKind = ((JavaWildcardType) javaType).isExtends() ? Variance.OUT_VARIANCE : Variance.IN_VARIANCE;
            if (bound == null || isConflictingArgumentFor(projectionKind, typeParameter)) {
                return JavaTypeResolverKt.makeStarProjection(typeParameter, attr);
            }
            return TypeUtilsKt.createProjection(transformJavaType(bound, JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null)), projectionKind, typeParameter);
        }
        return new TypeProjectionImpl(Variance.INVARIANT, transformJavaType(javaType, attr));
    }

    private final boolean isConflictingArgumentFor(@NotNull Variance $receiver, TypeParameterDescriptor typeParameter) {
        return (typeParameter.getVariance() == Variance.INVARIANT || $receiver == typeParameter.getVariance()) ? false : true;
    }

    private final boolean isNullable(@NotNull JavaTypeAttributes $receiver) {
        return ($receiver.getFlexibility() == JavaTypeFlexibility.FLEXIBLE_LOWER_BOUND || $receiver.isForAnnotationParameter() || $receiver.getHowThisTypeIsUsed() == TypeUsage.SUPERTYPE) ? false : true;
    }
}
