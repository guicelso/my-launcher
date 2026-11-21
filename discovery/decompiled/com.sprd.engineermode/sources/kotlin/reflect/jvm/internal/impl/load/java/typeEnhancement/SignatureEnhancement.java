package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.deprecation.DeprecationKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SignatureEnhancement {
    private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final Jsr305State jsr305State;

    public SignatureEnhancement(@NotNull AnnotationTypeQualifierResolver annotationTypeQualifierResolver, @NotNull Jsr305State jsr305State) {
        Intrinsics.checkParameterIsNotNull(annotationTypeQualifierResolver, "annotationTypeQualifierResolver");
        Intrinsics.checkParameterIsNotNull(jsr305State, "jsr305State");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver;
        this.jsr305State = jsr305State;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(@NotNull AnnotationDescriptor $receiver) {
        ConstantValue<?> constantValueFirstArgument = DescriptorUtilsKt.firstArgument($receiver);
        if (!(constantValueFirstArgument instanceof EnumValue)) {
            constantValueFirstArgument = null;
        }
        EnumValue enumValue = (EnumValue) constantValueFirstArgument;
        if (enumValue == null) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        switch (strAsString.hashCode()) {
            case 73135176:
                if (!strAsString.equals("MAYBE")) {
                    return null;
                }
                break;
            case 74175084:
                if (!strAsString.equals("NEVER")) {
                    return null;
                }
                break;
            case 433141802:
                if (strAsString.equals("UNKNOWN")) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, false, 2, null);
                }
                return null;
            case 1933739535:
                if (strAsString.equals("ALWAYS")) {
                    return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
                }
                return null;
            default:
                return null;
        }
        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
    }

    @Nullable
    public final NullabilityQualifierWithMigrationStatus extractNullability(@NotNull AnnotationDescriptor annotationDescriptor) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations;
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus it = extractNullabilityFromKnownAnnotations(annotationDescriptor);
        if (it != null) {
            return it;
        }
        AnnotationDescriptor typeQualifierAnnotation = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (typeQualifierAnnotation == null) {
            return null;
        }
        ReportLevel jsr305State = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
        if (jsr305State.isIgnore() || (nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations = extractNullabilityFromKnownAnnotations(typeQualifierAnnotation)) == null) {
            return null;
        }
        return NullabilityQualifierWithMigrationStatus.copy$default(nullabilityQualifierWithMigrationStatusExtractNullabilityFromKnownAnnotations, null, jsr305State.isWarning(), 1, null);
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor) {
        FqName annotationFqName = annotationDescriptor.getFqName();
        if (annotationFqName == null) {
            return null;
        }
        if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(annotationFqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
        }
        if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(annotationFqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
        }
        if (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
            return extractNullabilityTypeFromArgument(annotationDescriptor);
        }
        if (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, false, 2, null);
        }
        if (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION()) && this.jsr305State.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, null);
        }
        if (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
        }
        if (Intrinsics.areEqual(annotationFqName, JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(@NotNull LazyJavaResolverContext c, @NotNull Collection<? extends D> platformSignatures) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(platformSignatures, "platformSignatures");
        Collection<? extends D> $receiver$iv = platformSignatures;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            CallableMemberDescriptor it = (CallableMemberDescriptor) item$iv$iv;
            destination$iv$iv.add(enhanceSignature(it, c));
        }
        return (Collection) ((List) destination$iv$iv);
    }

    /* JADX WARN: Code duplicated, block: B:147:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:148:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:151:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:152:0x02f5  */
    /* JADX WARN: Code duplicated, block: B:156:0x0314 A[LOOP:2: B:154:0x030e->B:156:0x0314, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:159:0x034d  */
    /* JADX WARN: Code duplicated, block: B:161:0x0350  */
    /* JADX WARN: Code duplicated, block: B:46:0x00d7  */
    private final <D extends CallableMemberDescriptor> D enhanceSignature(@NotNull D d, LazyJavaResolverContext c) {
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl;
        PartEnhancementResult partEnhancementResultEnhance$default;
        PredefinedFunctionEnhancementInfo predefinedFunctionEnhancementInfo;
        AnnotationTypeQualifierResolver.QualifierApplicabilityType qualifierApplicabilityType;
        boolean z;
        boolean z2;
        boolean z3;
        boolean containsFunctionN;
        boolean z4;
        Pair<CallableDescriptor.UserDataKey<?>, ?> pair;
        KotlinType type;
        Iterable $receiver$iv;
        int $i$f$map;
        Collection destination$iv$iv;
        JavaCallableMemberDescriptor javaCallableMemberDescriptorEnhance;
        KotlinType type2;
        List<TypeEnhancementInfo> parametersInfo;
        PropertyGetterDescriptorImpl getter;
        if (!(d instanceof JavaCallableMemberDescriptor)) {
            return d;
        }
        if (((JavaCallableMemberDescriptor) d).getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            CallableMemberDescriptor original = ((JavaCallableMemberDescriptor) d).getOriginal();
            Intrinsics.checkExpressionValueIsNotNull(original, "original");
            if (original.getOverriddenDescriptors().size() == 1) {
                return d;
            }
        }
        LazyJavaResolverContext memberContext = ContextKt.copyWithNewDefaultTypeQualifiers(c, d.getAnnotations());
        if ((d instanceof JavaPropertyDescriptor) && (getter = ((JavaPropertyDescriptor) d).getGetter()) != null && !getter.isDefault()) {
            PropertyGetterDescriptorImpl getter2 = ((JavaPropertyDescriptor) d).getGetter();
            if (getter2 == null) {
                Intrinsics.throwNpe();
            }
            Intrinsics.checkExpressionValueIsNotNull(getter2, "getter!!");
            propertyGetterDescriptorImpl = getter2;
        } else {
            propertyGetterDescriptorImpl = d;
        }
        CallableMemberDescriptor annotationOwnerForMember = propertyGetterDescriptorImpl;
        if (((JavaCallableMemberDescriptor) d).getExtensionReceiverParameter() == null) {
            partEnhancementResultEnhance$default = null;
        } else {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) (!(annotationOwnerForMember instanceof FunctionDescriptor) ? null : annotationOwnerForMember);
            partEnhancementResultEnhance$default = SignatureParts.enhance$default(partsForValueParameter(d, functionDescriptor != null ? (ValueParameterDescriptor) functionDescriptor.getUserData(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER) : null, memberContext, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$receiverTypeEnhancement$1
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final KotlinType invoke(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    ReceiverParameterDescriptor extensionReceiverParameter = it.getExtensionReceiverParameter();
                    if (extensionReceiverParameter == null) {
                        Intrinsics.throwNpe();
                    }
                    Intrinsics.checkExpressionValueIsNotNull(extensionReceiverParameter, "it.extensionReceiverParameter!!");
                    KotlinType type3 = extensionReceiverParameter.getType();
                    Intrinsics.checkExpressionValueIsNotNull(type3, "it.extensionReceiverParameter!!.type");
                    return type3;
                }
            }), null, 1, null);
        }
        PartEnhancementResult receiverTypeEnhancement = partEnhancementResultEnhance$default;
        JavaMethodDescriptor $receiver = (JavaMethodDescriptor) (!(d instanceof JavaMethodDescriptor) ? null : d);
        if ($receiver != null) {
            SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
            DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
            if (containingDeclaration == null) {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            String signature = signatureBuildingComponents.signature((ClassDescriptor) containingDeclaration, MethodSignatureMappingKt.computeJvmDescriptor$default($receiver, false, false, 3, null));
            if (signature != null) {
                predefinedFunctionEnhancementInfo = PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE().get(signature);
            } else {
                predefinedFunctionEnhancementInfo = null;
            }
        } else {
            predefinedFunctionEnhancementInfo = null;
        }
        PredefinedFunctionEnhancementInfo predefinedEnhancementInfo = predefinedFunctionEnhancementInfo;
        if (predefinedEnhancementInfo != null) {
            boolean z5 = predefinedEnhancementInfo.getParametersInfo().size() == ((JavaCallableMemberDescriptor) d).getValueParameters().size();
            if (_Assertions.ENABLED && !z5) {
                throw new AssertionError("Predefined enhancement info for " + d + " has " + predefinedEnhancementInfo.getParametersInfo().size() + ", but " + ((JavaCallableMemberDescriptor) d).getValueParameters().size() + " expected");
            }
        }
        Iterable valueParameters = annotationOwnerForMember.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "annotationOwnerForMember.valueParameters");
        Iterable $receiver$iv2 = valueParameters;
        int $i$f$map2 = 0;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        Iterable $receiver$iv$iv = $receiver$iv2;
        for (Object item$iv$iv : $receiver$iv$iv) {
            final ValueParameterDescriptor p = (ValueParameterDescriptor) item$iv$iv;
            PartEnhancementResult enhancementResult = partsForValueParameter(d, p, memberContext, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1$enhancementResult$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final KotlinType invoke(@NotNull CallableMemberDescriptor it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    ValueParameterDescriptor valueParameterDescriptor = it.getValueParameters().get(p.getIndex());
                    Intrinsics.checkExpressionValueIsNotNull(valueParameterDescriptor, "it.valueParameters[p.index]");
                    KotlinType type3 = valueParameterDescriptor.getType();
                    Intrinsics.checkExpressionValueIsNotNull(type3, "it.valueParameters[p.index].type");
                    return type3;
                }
            }).enhance((predefinedEnhancementInfo == null || (parametersInfo = predefinedEnhancementInfo.getParametersInfo()) == null) ? null : (TypeEnhancementInfo) CollectionsKt.getOrNull(parametersInfo, p.getIndex()));
            if (enhancementResult.getWereChanges()) {
                type2 = enhancementResult.getType();
            } else {
                Intrinsics.checkExpressionValueIsNotNull(p, "p");
                type2 = p.getType();
                Intrinsics.checkExpressionValueIsNotNull(type2, "p.type");
            }
            KotlinType actualType = type2;
            Intrinsics.checkExpressionValueIsNotNull(p, "p");
            boolean hasDefaultValue = hasDefaultValueInAnnotation(p, actualType);
            boolean wereChanges = enhancementResult.getWereChanges() || hasDefaultValue != p.declaresDefaultValue();
            destination$iv$iv2.add(new ValueParameterEnhancementResult(enhancementResult.getType(), hasDefaultValue, wereChanges, enhancementResult.getContainsFunctionN()));
            $receiver$iv2 = $receiver$iv2;
            $i$f$map2 = $i$f$map2;
            $receiver$iv$iv = $receiver$iv$iv;
        }
        Iterable valueParameterEnhancements = (List) destination$iv$iv2;
        CallableMemberDescriptor callableMemberDescriptor = annotationOwnerForMember;
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) (!(d instanceof PropertyDescriptor) ? null : d);
        if (propertyDescriptor != null && JavaDescriptorUtilKt.isJavaField(propertyDescriptor)) {
            qualifierApplicabilityType = AnnotationTypeQualifierResolver.QualifierApplicabilityType.FIELD;
        } else {
            qualifierApplicabilityType = AnnotationTypeQualifierResolver.QualifierApplicabilityType.METHOD_RETURN_TYPE;
        }
        PartEnhancementResult returnTypeEnhancement = parts(d, callableMemberDescriptor, true, memberContext, qualifierApplicabilityType, new Function1<CallableMemberDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$returnTypeEnhancement$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final KotlinType invoke(@NotNull CallableMemberDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                KotlinType returnType = it.getReturnType();
                if (returnType == null) {
                    Intrinsics.throwNpe();
                }
                return returnType;
            }
        }).enhance(predefinedEnhancementInfo != null ? predefinedEnhancementInfo.getReturnTypeInfo() : null);
        if ((receiverTypeEnhancement == null || !receiverTypeEnhancement.getContainsFunctionN()) && !returnTypeEnhancement.getContainsFunctionN()) {
            Iterable $receiver$iv3 = valueParameterEnhancements;
            z = false;
            z2 = false;
            if (!($receiver$iv3 instanceof Collection) || !((Collection) $receiver$iv3).isEmpty()) {
                Iterator it = $receiver$iv3.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        if (((ValueParameterEnhancementResult) element$iv).getContainsFunctionN()) {
                            z3 = true;
                            break;
                        }
                    } else {
                        z3 = false;
                        break;
                    }
                }
            } else {
                z3 = false;
            }
            containsFunctionN = z3;
        } else {
            z = false;
            z2 = z;
        }
        if (receiverTypeEnhancement != null) {
            z4 = true;
            if (!receiverTypeEnhancement.getWereChanges()) {
            }
            if (containsFunctionN) {
                pair = TuplesKt.to(DeprecationKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionN(d));
            } else {
                pair = null;
            }
            JavaCallableMemberDescriptor javaCallableMemberDescriptor = (JavaCallableMemberDescriptor) d;
            if (receiverTypeEnhancement != null) {
                type = receiverTypeEnhancement.getType();
            } else {
                type = null;
            }
            $receiver$iv = valueParameterEnhancements;
            $i$f$map = 0;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv2 : $receiver$iv) {
                ValueParameterEnhancementResult it2 = (ValueParameterEnhancementResult) item$iv$iv2;
                destination$iv$iv.add(new ValueParameterData(it2.getType(), it2.getHasDefaultValue()));
                containsFunctionN = containsFunctionN;
                $receiver$iv = $receiver$iv;
                $i$f$map = $i$f$map;
            }
            javaCallableMemberDescriptorEnhance = javaCallableMemberDescriptor.enhance(type, (List) destination$iv$iv, returnTypeEnhancement.getType(), pair);
            if (javaCallableMemberDescriptorEnhance != null) {
                return javaCallableMemberDescriptorEnhance;
            }
            throw new TypeCastException("null cannot be cast to non-null type D");
        }
        z4 = true;
        if (!returnTypeEnhancement.getWereChanges()) {
            Iterable $receiver$iv4 = valueParameterEnhancements;
            if (!($receiver$iv4 instanceof Collection) || !((Collection) $receiver$iv4).isEmpty()) {
                for (Object element$iv2 : $receiver$iv4) {
                    if (((ValueParameterEnhancementResult) element$iv2).getWereChanges()) {
                        z = z4;
                        break;
                    }
                }
            }
            if (!z && !containsFunctionN) {
                return d;
            }
        }
        if (containsFunctionN) {
            pair = TuplesKt.to(DeprecationKt.getDEPRECATED_FUNCTION_KEY(), new DeprecationCausedByFunctionN(d));
        } else {
            pair = null;
        }
        JavaCallableMemberDescriptor javaCallableMemberDescriptor2 = (JavaCallableMemberDescriptor) d;
        if (receiverTypeEnhancement != null) {
            type = receiverTypeEnhancement.getType();
        } else {
            type = null;
        }
        $receiver$iv = valueParameterEnhancements;
        $i$f$map = 0;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        while (r18.hasNext()) {
            ValueParameterEnhancementResult it3 = (ValueParameterEnhancementResult) item$iv$iv2;
            destination$iv$iv.add(new ValueParameterData(it3.getType(), it3.getHasDefaultValue()));
            containsFunctionN = containsFunctionN;
            $receiver$iv = $receiver$iv;
            $i$f$map = $i$f$map;
        }
        javaCallableMemberDescriptorEnhance = javaCallableMemberDescriptor2.enhance(type, (List) destination$iv$iv, returnTypeEnhancement.getType(), pair);
        if (javaCallableMemberDescriptorEnhance != null) {
            return javaCallableMemberDescriptorEnhance;
        }
        throw new TypeCastException("null cannot be cast to non-null type D");
    }

    private final boolean hasDefaultValueInAnnotation(@NotNull ValueParameterDescriptor $receiver, KotlinType type) {
        boolean zDeclaresDefaultValue;
        AnnotationDefaultValue defaultValue = UtilKt.getDefaultValueFromAnnotation($receiver);
        if (defaultValue instanceof StringDefaultValue) {
            zDeclaresDefaultValue = UtilsKt.lexicalCastFrom(type, ((StringDefaultValue) defaultValue).getValue()) != null;
        } else if (Intrinsics.areEqual(defaultValue, NullDefaultValue.INSTANCE)) {
            zDeclaresDefaultValue = TypeUtils.acceptsNullable(type);
        } else if (defaultValue == null) {
            zDeclaresDefaultValue = $receiver.declaresDefaultValue();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return zDeclaresDefaultValue && $receiver.getOverriddenDescriptors().isEmpty();
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private final class SignatureParts {
        private final AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType;
        private final LazyJavaResolverContext containerContext;
        private final Collection<KotlinType> fromOverridden;
        private final KotlinType fromOverride;
        private final boolean isCovariant;
        final /* synthetic */ SignatureEnhancement this$0;
        private final Annotated typeContainer;

        /* JADX WARN: Multi-variable type inference failed */
        public SignatureParts(SignatureEnhancement $outer, @Nullable Annotated typeContainer, @NotNull KotlinType fromOverride, @NotNull Collection<? extends KotlinType> fromOverridden, boolean isCovariant, @NotNull LazyJavaResolverContext containerContext, @NotNull AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType) {
            Intrinsics.checkParameterIsNotNull(fromOverride, "fromOverride");
            Intrinsics.checkParameterIsNotNull(fromOverridden, "fromOverridden");
            Intrinsics.checkParameterIsNotNull(containerContext, "containerContext");
            Intrinsics.checkParameterIsNotNull(containerApplicabilityType, "containerApplicabilityType");
            this.this$0 = $outer;
            this.typeContainer = typeContainer;
            this.fromOverride = fromOverride;
            this.fromOverridden = fromOverridden;
            this.isCovariant = isCovariant;
            this.containerContext = containerContext;
            this.containerApplicabilityType = containerApplicabilityType;
        }

        private final boolean isForVarargParameter() {
            Object $receiver$iv = this.typeContainer;
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) (!($receiver$iv instanceof ValueParameterDescriptor) ? null : $receiver$iv);
            return (valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null;
        }

        @NotNull
        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                typeEnhancementInfo = (TypeEnhancementInfo) null;
            }
            return signatureParts.enhance(typeEnhancementInfo);
        }

        @NotNull
        public final PartEnhancementResult enhance(@Nullable final TypeEnhancementInfo predefined) {
            final Function1<Integer, JavaTypeQualifiers> function1ComputeIndexedQualifiersForOverride = computeIndexedQualifiersForOverride();
            Function1<Integer, JavaTypeQualifiers> function1 = predefined != null ? new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$$inlined$let$lambda$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                @NotNull
                public final JavaTypeQualifiers invoke(int index) {
                    JavaTypeQualifiers javaTypeQualifiers = predefined.getMap().get(Integer.valueOf(index));
                    return javaTypeQualifiers != null ? javaTypeQualifiers : (JavaTypeQualifiers) function1ComputeIndexedQualifiersForOverride.invoke(Integer.valueOf(index));
                }
            } : null;
            boolean containsFunctionN = TypeUtils.contains(this.fromOverride, new Function1<UnwrappedType, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$enhance$containsFunctionN$1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Boolean invoke(UnwrappedType unwrappedType) {
                    return Boolean.valueOf(invoke2(unwrappedType));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(UnwrappedType it) {
                    ClassifierDescriptor classifier = it.getConstructor().mo381getDeclarationDescriptor();
                    if (classifier == null) {
                        return false;
                    }
                    Intrinsics.checkExpressionValueIsNotNull(classifier, "it.constructor.declarati… ?: return@contains false");
                    return Intrinsics.areEqual(classifier.getName(), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME().shortName()) && Intrinsics.areEqual(DescriptorUtilsKt.fqNameOrNull(classifier), JavaToKotlinClassMap.INSTANCE.getFUNCTION_N_FQ_NAME());
                }
            });
            KotlinType enhanced = TypeEnhancementKt.enhance(this.fromOverride, function1 != null ? function1 : function1ComputeIndexedQualifiersForOverride);
            if (enhanced != null) {
                return new PartEnhancementResult(enhanced, true, containsFunctionN);
            }
            return new PartEnhancementResult(this.fromOverride, false, containsFunctionN);
        }

        private final JavaTypeQualifiers extractQualifiers(@NotNull KotlinType $receiver) {
            Pair pair;
            NullabilityQualifier nullabilityQualifier;
            MutabilityQualifier mutabilityQualifier;
            if (FlexibleTypesKt.isFlexible($receiver)) {
                FlexibleType it = FlexibleTypesKt.asFlexibleType($receiver);
                pair = new Pair(it.getLowerBound(), it.getUpperBound());
            } else {
                pair = new Pair($receiver, $receiver);
            }
            KotlinType lower = (KotlinType) pair.component1();
            KotlinType upper = (KotlinType) pair.component2();
            JavaToKotlinClassMap mapping = JavaToKotlinClassMap.INSTANCE;
            if (lower.isMarkedNullable()) {
                nullabilityQualifier = NullabilityQualifier.NULLABLE;
            } else {
                nullabilityQualifier = !upper.isMarkedNullable() ? NullabilityQualifier.NOT_NULL : null;
            }
            if (mapping.isReadOnly(lower)) {
                mutabilityQualifier = MutabilityQualifier.READ_ONLY;
            } else {
                mutabilityQualifier = mapping.isMutable(upper) ? MutabilityQualifier.MUTABLE : null;
            }
            return new JavaTypeQualifiers(nullabilityQualifier, mutabilityQualifier, $receiver.unwrap() instanceof NotNullTypeParameter, false, 8, null);
        }

        private final JavaTypeQualifiers extractQualifiersFromAnnotations(@NotNull KotlinType $receiver, boolean isHeadTypeConstructor, JavaTypeQualifiers defaultQualifiersForType) {
            final Annotations composedAnnotation;
            JavaTypeQualifiers defaultTypeQualifier;
            Annotated annotated;
            if (isHeadTypeConstructor && (annotated = this.typeContainer) != null) {
                composedAnnotation = AnnotationsKt.composeAnnotations(annotated.getAnnotations(), $receiver.getAnnotations());
            } else {
                composedAnnotation = $receiver.getAnnotations();
            }
            Function2<List<? extends FqName>, T, T> function2 = new Function2<List<? extends FqName>, T, T>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$1
                {
                    super(2);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(List<? extends FqName> list, Object obj) {
                    return invoke2((List<FqName>) list, obj);
                }

                @Nullable
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final <T> T invoke2(@NotNull List<FqName> receiver$0, @NotNull T qualifier) {
                    Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                    Intrinsics.checkParameterIsNotNull(qualifier, "qualifier");
                    List<FqName> $receiver$iv = receiver$0;
                    boolean z = false;
                    if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
                        for (Object element$iv : $receiver$iv) {
                            FqName it = (FqName) element$iv;
                            if (composedAnnotation.mo374findAnnotation(it) != null) {
                                z = true;
                                break;
                            }
                        }
                    }
                    if (z) {
                        return qualifier;
                    }
                    return null;
                }
            };
            SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2 uniqueNotNull$ = SignatureEnhancement$SignatureParts$extractQualifiersFromAnnotations$2.INSTANCE;
            if (isHeadTypeConstructor) {
                JavaTypeQualifiersByElementType defaultTypeQualifiers = this.containerContext.getDefaultTypeQualifiers();
                defaultTypeQualifier = defaultTypeQualifiers != null ? defaultTypeQualifiers.get(this.containerApplicabilityType) : null;
            } else {
                defaultTypeQualifier = defaultQualifiersForType;
            }
            NullabilityQualifierWithMigrationStatus nullabilityInfo = extractNullability(composedAnnotation);
            if (nullabilityInfo == null) {
                if (defaultTypeQualifier == null || defaultTypeQualifier.getNullability() == null) {
                    nullabilityInfo = null;
                } else {
                    nullabilityInfo = new NullabilityQualifierWithMigrationStatus(defaultTypeQualifier.getNullability(), defaultTypeQualifier.isNullabilityQualifierForWarning$descriptors_jvm());
                }
            }
            NullabilityQualifier qualifier = nullabilityInfo != null ? nullabilityInfo.getQualifier() : null;
            MutabilityQualifier mutabilityQualifier = (MutabilityQualifier) uniqueNotNull$.invoke(function2.invoke2(JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS(), MutabilityQualifier.READ_ONLY), function2.invoke2(JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS(), MutabilityQualifier.MUTABLE));
            boolean z = false;
            boolean z2 = (nullabilityInfo != null ? nullabilityInfo.getQualifier() : null) == NullabilityQualifier.NOT_NULL && TypeUtilsKt.isTypeParameter($receiver);
            if (nullabilityInfo != null && nullabilityInfo.isForWarningOnly()) {
                z = true;
            }
            return new JavaTypeQualifiers(qualifier, mutabilityQualifier, z2, z);
        }

        private final NullabilityQualifierWithMigrationStatus extractNullability(@NotNull Annotations $receiver) {
            Annotations $receiver$iv = $receiver;
            SignatureEnhancement signatureEnhancement = this.this$0;
            for (Object element$iv : $receiver$iv) {
                AnnotationDescriptor p1 = (AnnotationDescriptor) element$iv;
                NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatusExtractNullability = signatureEnhancement.extractNullability(p1);
                if (nullabilityQualifierWithMigrationStatusExtractNullability != null) {
                    return nullabilityQualifierWithMigrationStatusExtractNullability;
                }
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0077  */
        private final Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride() {
            boolean onlyHeadTypeConstructor;
            boolean z;
            Collection destination$iv$iv;
            boolean z2;
            Iterable $receiver$iv = this.fromOverridden;
            boolean z3 = false;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                KotlinType it = (KotlinType) item$iv$iv;
                destination$iv$iv2.add(toIndexed(it));
            }
            List indexedFromSupertypes = (List) destination$iv$iv2;
            List<TypeAndDefaultQualifiers> indexed = toIndexed(this.fromOverride);
            if (this.isCovariant) {
                Iterable $receiver$iv2 = this.fromOverridden;
                if (!($receiver$iv2 instanceof Collection) || !((Collection) $receiver$iv2).isEmpty()) {
                    Iterator it2 = $receiver$iv2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            Object element$iv = it2.next();
                            KotlinType it3 = (KotlinType) element$iv;
                            if (!KotlinTypeChecker.DEFAULT.equalTypes(it3, this.fromOverride)) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                } else {
                    z2 = false;
                }
                if (z2) {
                    onlyHeadTypeConstructor = true;
                } else {
                    onlyHeadTypeConstructor = false;
                }
            } else {
                onlyHeadTypeConstructor = false;
            }
            int treeSize = onlyHeadTypeConstructor ? 1 : indexed.size();
            final JavaTypeQualifiers[] javaTypeQualifiersArr = new JavaTypeQualifiers[treeSize];
            int length = javaTypeQualifiersArr.length;
            int i$iv = 0;
            while (i$iv < length) {
                int index = i$iv;
                boolean isHeadTypeConstructor = index == 0 ? true : z3;
                boolean z4 = isHeadTypeConstructor || !onlyHeadTypeConstructor;
                if (_Assertions.ENABLED) {
                    z = false;
                    if (!z4) {
                        throw new AssertionError("Only head type constructors should be computed");
                    }
                } else {
                    z = false;
                }
                TypeAndDefaultQualifiers typeAndDefaultQualifiers = indexed.get(index);
                List<TypeAndDefaultQualifiers> list = indexed;
                KotlinType qualifiers = typeAndDefaultQualifiers.component1();
                JavaTypeQualifiers defaultQualifiers = typeAndDefaultQualifiers.component2();
                Collection destination$iv$iv3 = new ArrayList();
                for (Object element$iv$iv$iv : indexedFromSupertypes) {
                    List indexedFromSupertypes2 = indexedFromSupertypes;
                    List it4 = (List) element$iv$iv$iv;
                    TypeAndDefaultQualifiers typeAndDefaultQualifiers2 = (TypeAndDefaultQualifiers) CollectionsKt.getOrNull(it4, index);
                    KotlinType type = typeAndDefaultQualifiers2 != null ? typeAndDefaultQualifiers2.getType() : null;
                    if (type != null) {
                        destination$iv$iv = destination$iv$iv3;
                        destination$iv$iv.add(type);
                    } else {
                        destination$iv$iv = destination$iv$iv3;
                    }
                    destination$iv$iv3 = destination$iv$iv;
                    indexedFromSupertypes = indexedFromSupertypes2;
                    onlyHeadTypeConstructor = onlyHeadTypeConstructor;
                }
                List indexedFromSupertypes3 = indexedFromSupertypes;
                List verticalSlice = (List) destination$iv$iv3;
                javaTypeQualifiersArr[i$iv] = computeQualifiersForOverride(qualifiers, verticalSlice, defaultQualifiers, isHeadTypeConstructor);
                i$iv++;
                z3 = z;
                indexed = list;
                indexedFromSupertypes = indexedFromSupertypes3;
                onlyHeadTypeConstructor = onlyHeadTypeConstructor;
            }
            return new Function1<Integer, JavaTypeQualifiers>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ JavaTypeQualifiers invoke(Integer num) {
                    return invoke(num.intValue());
                }

                @NotNull
                public final JavaTypeQualifiers invoke(int index2) {
                    JavaTypeQualifiers[] javaTypeQualifiersArr2 = javaTypeQualifiersArr;
                    return (index2 < 0 || index2 > ArraysKt.getLastIndex(javaTypeQualifiersArr2)) ? JavaTypeQualifiers.Companion.getNONE() : javaTypeQualifiersArr2[index2];
                }
            };
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(@NotNull KotlinType $receiver) {
            final ArrayList list = new ArrayList(1);
            new Function2<KotlinType, LazyJavaResolverContext, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$toIndexed$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType, LazyJavaResolverContext lazyJavaResolverContext) {
                    invoke2(kotlinType, lazyJavaResolverContext);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull KotlinType type, @NotNull LazyJavaResolverContext ownerContext) {
                    Intrinsics.checkParameterIsNotNull(type, "type");
                    Intrinsics.checkParameterIsNotNull(ownerContext, "ownerContext");
                    LazyJavaResolverContext c = ContextKt.copyWithNewDefaultTypeQualifiers(ownerContext, type.getAnnotations());
                    ArrayList arrayList = list;
                    JavaTypeQualifiersByElementType defaultTypeQualifiers = c.getDefaultTypeQualifiers();
                    arrayList.add(new TypeAndDefaultQualifiers(type, defaultTypeQualifiers != null ? defaultTypeQualifiers.get(AnnotationTypeQualifierResolver.QualifierApplicabilityType.TYPE_USE) : null));
                    for (TypeProjection arg : type.getArguments()) {
                        if (arg.isStarProjection()) {
                            ArrayList arrayList2 = list;
                            KotlinType type2 = arg.getType();
                            Intrinsics.checkExpressionValueIsNotNull(type2, "arg.type");
                            arrayList2.add(new TypeAndDefaultQualifiers(type2, null));
                        } else {
                            KotlinType type3 = arg.getType();
                            Intrinsics.checkExpressionValueIsNotNull(type3, "arg.type");
                            invoke2(type3, c);
                        }
                    }
                }
            }.invoke2($receiver, this.containerContext);
            return list;
        }

        /* JADX WARN: Code duplicated, block: B:53:0x0151  */
        /* JADX WARN: Code duplicated, block: B:54:0x0154  */
        /* JADX WARN: Code duplicated, block: B:90:0x01d2  */
        private final JavaTypeQualifiers computeQualifiersForOverride(@NotNull KotlinType $receiver, Collection<? extends KotlinType> $receiver$iv, JavaTypeQualifiers defaultQualifiersForType, boolean isHeadTypeConstructor) {
            NullabilityQualifier nullabilityQualifier;
            boolean isAnyNonNullTypeParameter;
            boolean z;
            boolean z2;
            Collection<? extends KotlinType> $receiver$iv2 = $receiver$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
            for (Object item$iv$iv : $receiver$iv2) {
                destination$iv$iv.add(extractQualifiers((KotlinType) item$iv$iv));
            }
            Iterable superQualifiers = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv$iv : superQualifiers) {
                MutabilityQualifier mutability = ((JavaTypeQualifiers) element$iv$iv$iv).getMutability();
                if (mutability != null) {
                    destination$iv$iv2.add(mutability);
                }
            }
            Set mutabilityFromSupertypes = CollectionsKt.toSet((List) destination$iv$iv2);
            Iterable $receiver$iv3 = superQualifiers;
            Collection destination$iv$iv3 = new ArrayList();
            for (Object element$iv$iv$iv2 : $receiver$iv3) {
                NullabilityQualifier nullability = ((JavaTypeQualifiers) element$iv$iv$iv2).getNullability();
                if (nullability != null) {
                    destination$iv$iv3.add(nullability);
                }
                $receiver$iv3 = $receiver$iv3;
            }
            Set nullabilityFromSupertypes = CollectionsKt.toSet((List) destination$iv$iv3);
            Collection destination$iv$iv4 = new ArrayList();
            for (Object element$iv$iv$iv3 : $receiver$iv) {
                NullabilityQualifier nullability2 = extractQualifiers(TypeWithEnhancementKt.unwrapEnhancement((KotlinType) element$iv$iv$iv3)).getNullability();
                if (nullability2 != null) {
                    destination$iv$iv4.add(nullability2);
                }
            }
            Set nullabilityFromSupertypesWithWarning = CollectionsKt.toSet((List) destination$iv$iv4);
            JavaTypeQualifiers own = extractQualifiersFromAnnotations($receiver, isHeadTypeConstructor, defaultQualifiersForType);
            JavaTypeQualifiers javaTypeQualifiers = own.isNullabilityQualifierForWarning$descriptors_jvm() ^ true ? own : null;
            NullabilityQualifier ownNullability = javaTypeQualifiers != null ? javaTypeQualifiers.getNullability() : null;
            NullabilityQualifier ownNullabilityForWarning = own.getNullability();
            boolean isCovariantPosition = this.isCovariant && isHeadTypeConstructor;
            NullabilityQualifier it = SignatureEnhancementKt.select(nullabilityFromSupertypes, ownNullability, isCovariantPosition);
            if (it != null) {
                if (isForVarargParameter() && isHeadTypeConstructor) {
                    z2 = it == NullabilityQualifier.NULLABLE;
                    if (z2) {
                        nullabilityQualifier = null;
                    } else {
                        nullabilityQualifier = it;
                    }
                }
                if (z2) {
                    nullabilityQualifier = it;
                } else {
                    nullabilityQualifier = null;
                }
            } else {
                nullabilityQualifier = null;
            }
            NullabilityQualifier nullability3 = nullabilityQualifier;
            MutabilityQualifier mutability2 = (MutabilityQualifier) SignatureEnhancementKt.select(mutabilityFromSupertypes, MutabilityQualifier.MUTABLE, MutabilityQualifier.READ_ONLY, own.getMutability(), isCovariantPosition);
            boolean canChange = ownNullabilityForWarning != ownNullability || (Intrinsics.areEqual(nullabilityFromSupertypesWithWarning, nullabilityFromSupertypes) ^ true);
            if (!own.isNotNullTypeParameter$descriptors_jvm()) {
                Iterable $receiver$iv4 = superQualifiers;
                if (!($receiver$iv4 instanceof Collection) || !((Collection) $receiver$iv4).isEmpty()) {
                    Iterator it2 = $receiver$iv4.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        Object element$iv = it2.next();
                        if (((JavaTypeQualifiers) element$iv).isNotNullTypeParameter$descriptors_jvm()) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
                if (!z) {
                    isAnyNonNullTypeParameter = false;
                }
                if (nullability3 == null || !canChange) {
                    return SignatureEnhancementKt.createJavaTypeQualifiers(nullability3, mutability2, nullability3 == null, isAnyNonNullTypeParameter);
                }
                NullabilityQualifier nullabilityWithWarning = SignatureEnhancementKt.select(nullabilityFromSupertypesWithWarning, ownNullabilityForWarning, isCovariantPosition);
                return SignatureEnhancementKt.createJavaTypeQualifiers(nullabilityWithWarning, mutability2, true, isAnyNonNullTypeParameter);
            }
            isAnyNonNullTypeParameter = true;
            if (nullability3 == null) {
            }
            return SignatureEnhancementKt.createJavaTypeQualifiers(nullability3, mutability2, nullability3 == null, isAnyNonNullTypeParameter);
        }
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private static class PartEnhancementResult {
        private final boolean containsFunctionN;

        @NotNull
        private final KotlinType type;
        private final boolean wereChanges;

        public PartEnhancementResult(@NotNull KotlinType type, boolean wereChanges, boolean containsFunctionN) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.type = type;
            this.wereChanges = wereChanges;
            this.containsFunctionN = containsFunctionN;
        }

        @NotNull
        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }
    }

    /* JADX INFO: compiled from: signatureEnhancement.kt */
    private static final class ValueParameterEnhancementResult extends PartEnhancementResult {
        private final boolean hasDefaultValue;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ValueParameterEnhancementResult(@NotNull KotlinType type, boolean hasDefaultValue, boolean wereChanges, boolean containsFunctionN) {
            super(type, wereChanges, containsFunctionN);
            Intrinsics.checkParameterIsNotNull(type, "type");
            this.hasDefaultValue = hasDefaultValue;
        }

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }
    }

    private final SignatureParts partsForValueParameter(@NotNull CallableMemberDescriptor $receiver, ValueParameterDescriptor parameterDescriptor, LazyJavaResolverContext methodContext, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        LazyJavaResolverContext lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers;
        return parts($receiver, parameterDescriptor, false, (parameterDescriptor == null || (lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(methodContext, parameterDescriptor.getAnnotations())) == null) ? methodContext : lazyJavaResolverContextCopyWithNewDefaultTypeQualifiers, AnnotationTypeQualifierResolver.QualifierApplicabilityType.VALUE_PARAMETER, function1);
    }

    private final SignatureParts parts(@NotNull CallableMemberDescriptor $receiver, Annotated typeContainer, boolean isCovariant, LazyJavaResolverContext containerContext, AnnotationTypeQualifierResolver.QualifierApplicabilityType containerApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        KotlinType kotlinTypeInvoke = function1.invoke($receiver);
        Iterable overriddenDescriptors = $receiver.getOverriddenDescriptors();
        Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "this.overriddenDescriptors");
        Iterable $receiver$iv = overriddenDescriptors;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            CallableMemberDescriptor it = (CallableMemberDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv.add(function1.invoke(it));
        }
        return new SignatureParts(this, typeContainer, kotlinTypeInvoke, (Collection) ((List) destination$iv$iv), isCovariant, ContextKt.copyWithNewDefaultTypeQualifiers(containerContext, function1.invoke($receiver).getAnnotations()), containerApplicabilityType);
    }
}
