package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: typeSignatureMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeSignatureMappingKt {
    private static final <T> T boxTypeIfNeeded(@NotNull JvmTypeFactory<T> jvmTypeFactory, T t, boolean needBoxedType) {
        return needBoxedType ? jvmTypeFactory.boxType(t) : t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v21, types: [T, java.lang.Object] */
    @NotNull
    public static final <T> T mapType(@NotNull KotlinType kotlinType, @NotNull JvmTypeFactory<T> factory, @NotNull TypeMappingMode mode, @NotNull TypeMappingConfiguration<? extends T> typeMappingConfiguration, @Nullable JvmDescriptorTypeWriter<T> jvmDescriptorTypeWriter, @NotNull Function3<? super KotlinType, ? super T, ? super TypeMappingMode, Unit> writeGenericType, boolean z) {
        T predefinedTypeForClass;
        ClassDescriptor classDescriptor;
        KotlinType kotlinTypeComputeExpandedTypeForInlineClass;
        Object objMapType;
        Intrinsics.checkParameterIsNotNull(kotlinType, "kotlinType");
        Intrinsics.checkParameterIsNotNull(factory, "factory");
        Intrinsics.checkParameterIsNotNull(mode, "mode");
        Intrinsics.checkParameterIsNotNull(typeMappingConfiguration, "typeMappingConfiguration");
        Intrinsics.checkParameterIsNotNull(writeGenericType, "writeGenericType");
        KotlinType kotlinTypePreprocessType = typeMappingConfiguration.preprocessType(kotlinType);
        if (kotlinTypePreprocessType != null) {
            return (T) mapType(kotlinTypePreprocessType, factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType, z);
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return (T) mapType(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(kotlinType, typeMappingConfiguration.releaseCoroutines()), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType, z);
        }
        Object objMapBuiltInType = mapBuiltInType(kotlinType, factory, mode);
        if (objMapBuiltInType != null) {
            ?? r3 = (Object) boxTypeIfNeeded(factory, objMapBuiltInType, mode.getNeedPrimitiveBoxing());
            writeGenericType.invoke(kotlinType, r3, mode);
            return r3;
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            return (T) mapType(TypeUtilsKt.replaceArgumentsWithStarProjections(typeMappingConfiguration.commonSupertype(((IntersectionTypeConstructor) constructor).getSupertypes())), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType, z);
        }
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = constructor.mo381getDeclarationDescriptor();
        if (classifierDescriptorMo381getDeclarationDescriptor == null) {
            throw new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
        }
        Intrinsics.checkExpressionValueIsNotNull(classifierDescriptorMo381getDeclarationDescriptor, "constructor.declarationD…structor of $kotlinType\")");
        if (ErrorUtils.isError(classifierDescriptorMo381getDeclarationDescriptor)) {
            T t = (T) factory.createObjectType("error/NonExistentClass");
            typeMappingConfiguration.processErrorType(kotlinType, (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor);
            if (jvmDescriptorTypeWriter != 0) {
                jvmDescriptorTypeWriter.writeClass(t);
            }
            return t;
        }
        if ((classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor) && KotlinBuiltIns.isArray(kotlinType)) {
            if (kotlinType.getArguments().size() != 1) {
                throw new UnsupportedOperationException("arrays must have one type argument");
            }
            TypeProjection typeProjection = kotlinType.getArguments().get(0);
            KotlinType type = typeProjection.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "memberProjection.type");
            if (typeProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                objMapType = factory.createObjectType("java/lang/Object");
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                    jvmDescriptorTypeWriter.writeClass(objMapType);
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            } else {
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayType();
                }
                Variance projectionKind = typeProjection.getProjectionKind();
                Intrinsics.checkExpressionValueIsNotNull(projectionKind, "memberProjection.projectionKind");
                objMapType = mapType(type, factory, mode.toGenericArgumentMode(projectionKind), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType, z);
                if (jvmDescriptorTypeWriter != 0) {
                    jvmDescriptorTypeWriter.writeArrayEnd();
                }
            }
            return (T) factory.createFromString("[" + factory.toString(objMapType));
        }
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor)) {
            if (classifierDescriptorMo381getDeclarationDescriptor instanceof TypeParameterDescriptor) {
                T t2 = (T) mapType(getRepresentativeUpperBound((TypeParameterDescriptor) classifierDescriptorMo381getDeclarationDescriptor), factory, mode, typeMappingConfiguration, null, FunctionsKt.getDO_NOTHING_3(), z);
                if (jvmDescriptorTypeWriter != 0) {
                    Name name = classifierDescriptorMo381getDeclarationDescriptor.getName();
                    Intrinsics.checkExpressionValueIsNotNull(name, "descriptor.getName()");
                    jvmDescriptorTypeWriter.writeTypeVariable(name, t2);
                }
                return t2;
            }
            throw new UnsupportedOperationException("Unknown type " + kotlinType);
        }
        if (((ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor).isInline() && !mode.getNeedInlineClassWrapping() && (kotlinTypeComputeExpandedTypeForInlineClass = computeExpandedTypeForInlineClass(kotlinType)) != null) {
            return (T) mapType(kotlinTypeComputeExpandedTypeForInlineClass, factory, mode.wrapInlineClassesMode(), typeMappingConfiguration, jvmDescriptorTypeWriter, writeGenericType, z);
        }
        if (mode.isForAnnotationParameter() && KotlinBuiltIns.isKClass((ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor)) {
            predefinedTypeForClass = (Object) factory.getJavaLangClassType();
        } else {
            ClassDescriptor original = ((ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor).getOriginal();
            Intrinsics.checkExpressionValueIsNotNull(original, "descriptor.original");
            predefinedTypeForClass = typeMappingConfiguration.getPredefinedTypeForClass(original);
            if (predefinedTypeForClass == null) {
                if (((ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor).getKind() == ClassKind.ENUM_ENTRY) {
                    DeclarationDescriptor containingDeclaration = ((ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor).getContainingDeclaration();
                    if (containingDeclaration == null) {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                    classDescriptor = (ClassDescriptor) containingDeclaration;
                } else {
                    classDescriptor = (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
                }
                ClassDescriptor original2 = classDescriptor.getOriginal();
                Intrinsics.checkExpressionValueIsNotNull(original2, "enumClassIfEnumEntry.original");
                predefinedTypeForClass = (Object) factory.createObjectType(computeInternalName(original2, typeMappingConfiguration, z));
            }
        }
        writeGenericType.invoke(kotlinType, predefinedTypeForClass, mode);
        return predefinedTypeForClass;
    }

    public static final boolean hasVoidReturnType(@NotNull CallableDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        if (descriptor instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            Intrinsics.throwNpe();
        }
        if (KotlinBuiltIns.isUnit(returnType)) {
            KotlinType returnType2 = descriptor.getReturnType();
            if (returnType2 == null) {
                Intrinsics.throwNpe();
            }
            if (!TypeUtils.isNullableType(returnType2) && !(descriptor instanceof PropertyGetterDescriptor)) {
                return true;
            }
        }
        return false;
    }

    private static final String continuationInternalName(boolean releaseCoroutines) {
        FqName fqName = releaseCoroutines ? DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_RELEASE : DescriptorUtils.CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL;
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(ClassId.topLevel(fqName));
        Intrinsics.checkExpressionValueIsNotNull(jvmClassNameByClassId, "JvmClassName.byClassId(ClassId.topLevel(fqName))");
        String internalName = jvmClassNameByClassId.getInternalName();
        Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byClassId(C…vel(fqName)).internalName");
        return internalName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T> T mapBuiltInType(KotlinType kotlinType, JvmTypeFactory<T> jvmTypeFactory, TypeMappingMode typeMappingMode) {
        ClassId classIdMapKotlinToJava;
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = kotlinType.getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
        if (classDescriptor == null) {
            return null;
        }
        boolean z = false;
        if (classDescriptor == SuspendFunctionTypesKt.getFAKE_CONTINUATION_CLASS_DESCRIPTOR_EXPERIMENTAL()) {
            return jvmTypeFactory.createObjectType(continuationInternalName(false));
        }
        if (Intrinsics.areEqual(classDescriptor, SuspendFunctionTypesKt.getFAKE_CONTINUATION_CLASS_DESCRIPTOR_RELEASE())) {
            return jvmTypeFactory.createObjectType(continuationInternalName(true));
        }
        PrimitiveType primitiveType = KotlinBuiltIns.getPrimitiveType(classDescriptor);
        if (primitiveType != null) {
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(primitiveType);
            Intrinsics.checkExpressionValueIsNotNull(jvmPrimitiveType, "JvmPrimitiveType.get(primitiveType)");
            String desc = jvmPrimitiveType.getDesc();
            Intrinsics.checkExpressionValueIsNotNull(desc, "JvmPrimitiveType.get(primitiveType).desc");
            return (T) boxTypeIfNeeded(jvmTypeFactory, jvmTypeFactory.createFromString(desc), TypeUtils.isNullableType(kotlinType) || TypeEnhancementKt.hasEnhancedNullability(kotlinType));
        }
        PrimitiveType primitiveArrayType = KotlinBuiltIns.getPrimitiveArrayType(classDescriptor);
        if (primitiveArrayType != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            JvmPrimitiveType jvmPrimitiveType2 = JvmPrimitiveType.get(primitiveArrayType);
            Intrinsics.checkExpressionValueIsNotNull(jvmPrimitiveType2, "JvmPrimitiveType.get(arrayElementType)");
            sb.append(jvmPrimitiveType2.getDesc());
            return jvmTypeFactory.createFromString(sb.toString());
        }
        if (!KotlinBuiltIns.isUnderKotlinPackage(classDescriptor) || (classIdMapKotlinToJava = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(DescriptorUtilsKt.getFqNameUnsafe(classDescriptor))) == null) {
            return null;
        }
        if (!typeMappingMode.getKotlinCollectionsToJavaCollections()) {
            List<JavaToKotlinClassMap.PlatformMutabilityMapping> mutabilityMappings = JavaToKotlinClassMap.INSTANCE.getMutabilityMappings();
            if (!(mutabilityMappings instanceof Collection) || !mutabilityMappings.isEmpty()) {
                Iterator<T> it = mutabilityMappings.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((JavaToKotlinClassMap.PlatformMutabilityMapping) it.next()).getJavaClass(), classIdMapKotlinToJava)) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                return null;
            }
        }
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(classIdMapKotlinToJava);
        Intrinsics.checkExpressionValueIsNotNull(jvmClassNameByClassId, "JvmClassName.byClassId(classId)");
        String internalName = jvmClassNameByClassId.getInternalName();
        Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byClassId(classId).internalName");
        return jvmTypeFactory.createObjectType(internalName);
    }

    @Nullable
    public static final KotlinType computeExpandedTypeForInlineClass(@NotNull KotlinType inlineClassType) {
        Intrinsics.checkParameterIsNotNull(inlineClassType, "inlineClassType");
        return computeExpandedTypeInner(inlineClassType, new HashSet());
    }

    @Nullable
    public static final KotlinType computeExpandedTypeInner(@NotNull KotlinType kotlinType, @NotNull HashSet<ClassifierDescriptor> visitedClassifiers) {
        KotlinType expandedUnderlyingType;
        KotlinType expandedUpperBound;
        Intrinsics.checkParameterIsNotNull(kotlinType, "kotlinType");
        Intrinsics.checkParameterIsNotNull(visitedClassifiers, "visitedClassifiers");
        ClassifierDescriptor classifier = kotlinType.getConstructor().mo381getDeclarationDescriptor();
        if (classifier != null) {
            Intrinsics.checkExpressionValueIsNotNull(classifier, "kotlinType.constructor.d…n expected: $kotlinType\")");
            if (!visitedClassifiers.add(classifier)) {
                return null;
            }
            if (classifier instanceof TypeParameterDescriptor) {
                KotlinType expandedUpperBound2 = computeExpandedTypeInner(getRepresentativeUpperBound((TypeParameterDescriptor) classifier), visitedClassifiers);
                if (expandedUpperBound2 == null) {
                    return null;
                }
                if (KotlinTypeKt.isNullable(expandedUpperBound2) || !kotlinType.isMarkedNullable()) {
                    expandedUpperBound = expandedUpperBound2;
                } else {
                    expandedUpperBound = TypeUtilsKt.makeNullable(expandedUpperBound2);
                }
                return expandedUpperBound;
            }
            if ((classifier instanceof ClassDescriptor) && ((ClassDescriptor) classifier).isInline()) {
                KotlinType underlyingType = InlineClassesUtilsKt.substitutedUnderlyingType(kotlinType);
                if (underlyingType == null || (expandedUnderlyingType = computeExpandedTypeInner(underlyingType, visitedClassifiers)) == null) {
                    return null;
                }
                if (!KotlinTypeKt.isNullable(kotlinType)) {
                    return expandedUnderlyingType;
                }
                if (KotlinTypeKt.isNullable(expandedUnderlyingType) || KotlinBuiltIns.isPrimitiveType(expandedUnderlyingType)) {
                    return kotlinType;
                }
                KotlinType inlineClassBoxType = TypeUtilsKt.makeNullable(expandedUnderlyingType);
                return inlineClassBoxType;
            }
            return kotlinType;
        }
        throw new AssertionError("Type with a declaration expected: " + kotlinType);
    }

    @NotNull
    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return computeInternalName(classDescriptor, typeMappingConfiguration, z);
    }

    @NotNull
    public static final String computeInternalName(@NotNull ClassDescriptor klass, @NotNull TypeMappingConfiguration<?> typeMappingConfiguration, boolean isIrBackend) {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        Intrinsics.checkParameterIsNotNull(typeMappingConfiguration, "typeMappingConfiguration");
        DeclarationDescriptor container = klass.getContainingDeclaration();
        if (isIrBackend) {
            container = getContainer(container);
        }
        Name nameSafeIdentifier = SpecialNames.safeIdentifier(klass.getName());
        Intrinsics.checkExpressionValueIsNotNull(nameSafeIdentifier, "SpecialNames.safeIdentifier(klass.name)");
        String name = nameSafeIdentifier.getIdentifier();
        Intrinsics.checkExpressionValueIsNotNull(name, "SpecialNames.safeIdentifier(klass.name).identifier");
        if (container instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) container).getFqName();
            if (fqName.isRoot()) {
                return name;
            }
            StringBuilder sb = new StringBuilder();
            String strAsString = fqName.asString();
            Intrinsics.checkExpressionValueIsNotNull(strAsString, "fqName.asString()");
            sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null));
            sb.append('/');
            sb.append(name);
            return sb.toString();
        }
        ClassDescriptor containerClass = (ClassDescriptor) (!(container instanceof ClassDescriptor) ? null : container);
        if (containerClass == null) {
            throw new IllegalArgumentException("Unexpected container: " + container + " for " + klass);
        }
        String containerInternalName = typeMappingConfiguration.getPredefinedInternalNameForClass(containerClass);
        if (containerInternalName == null) {
            containerInternalName = computeInternalName(containerClass, typeMappingConfiguration, isIrBackend);
        }
        return containerInternalName + Typography.dollar + name;
    }

    private static final DeclarationDescriptor getContainer(DeclarationDescriptor container) {
        DeclarationDescriptor declarationDescriptor = (ClassDescriptor) (!(container instanceof ClassDescriptor) ? null : container);
        if (declarationDescriptor == null) {
            declarationDescriptor = (PackageFragmentDescriptor) (!(container instanceof PackageFragmentDescriptor) ? null : container);
        }
        DeclarationDescriptor declarationDescriptor2 = declarationDescriptor;
        if (declarationDescriptor2 != null) {
            return declarationDescriptor2;
        }
        if (container != null) {
            return getContainer(container.getContainingDeclaration());
        }
        return null;
    }

    @NotNull
    public static final KotlinType getRepresentativeUpperBound(@NotNull TypeParameterDescriptor descriptor) {
        Object element$iv;
        boolean z;
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        List<KotlinType> upperBounds = descriptor.getUpperBounds();
        Intrinsics.checkExpressionValueIsNotNull(upperBounds, "descriptor.upperBounds");
        boolean z2 = !upperBounds.isEmpty();
        if (_Assertions.ENABLED && !z2) {
            throw new AssertionError("Upper bounds should not be empty: " + descriptor);
        }
        List<KotlinType> $receiver$iv = upperBounds;
        Iterator it = $receiver$iv.iterator();
        do {
            if (it.hasNext()) {
                element$iv = it.next();
                KotlinType it2 = (KotlinType) element$iv;
                ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = it2.getConstructor().mo381getDeclarationDescriptor();
                ClassDescriptor classDescriptor = (ClassDescriptor) (classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor ? classifierDescriptorMo381getDeclarationDescriptor : null);
                if (classDescriptor == null) {
                    z = false;
                } else {
                    z = (classDescriptor.getKind() == ClassKind.INTERFACE || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
                }
            } else {
                element$iv = null;
                break;
            }
        } while (!z);
        KotlinType kotlinType = (KotlinType) element$iv;
        if (kotlinType != null) {
            return kotlinType;
        }
        Object objFirst = CollectionsKt.first((List<? extends Object>) upperBounds);
        Intrinsics.checkExpressionValueIsNotNull(objFirst, "upperBounds.first()");
        return (KotlinType) objFirst;
    }
}
