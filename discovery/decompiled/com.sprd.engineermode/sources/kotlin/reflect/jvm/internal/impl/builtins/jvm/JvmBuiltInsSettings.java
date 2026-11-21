package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: JvmBuiltInsSettings.kt */
/* JADX INFO: loaded from: classes4.dex */
public class JvmBuiltInsSettings implements AdditionalClassPartsProvider, PlatformDependentDeclarationFilter {

    @NotNull
    private static final Set<String> BLACK_LIST_CONSTRUCTOR_SIGNATURES;

    @NotNull
    private static final Set<String> BLACK_LIST_METHOD_SIGNATURES;

    @NotNull
    private static final Set<String> MUTABLE_METHOD_SIGNATURES;

    @NotNull
    private static final Set<String> WHITE_LIST_CONSTRUCTOR_SIGNATURES;

    @NotNull
    private static final Set<String> WHITE_LIST_METHOD_SIGNATURES;
    private final NotNullLazyValue cloneableType$delegate;
    private final Lazy isAdditionalBuiltInsFeatureSupported$delegate;
    private final JavaToKotlinClassMap j2kClassMap;
    private final CacheWithNotNullValues<FqName, ClassDescriptor> javaAnalogueClassesWithCustomSupertypeCache;
    private final KotlinType mockSerializableType;
    private final ModuleDescriptor moduleDescriptor;
    private final NotNullLazyValue notConsideredDeprecation$delegate;
    private final Lazy ownerModuleDescriptor$delegate;
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "ownerModuleDescriptor", "getOwnerModuleDescriptor()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "isAdditionalBuiltInsFeatureSupported", "isAdditionalBuiltInsFeatureSupported()Z")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "cloneableType", "getCloneableType()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "notConsideredDeprecation", "getNotConsideredDeprecation()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;"))};
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final Set<String> DROP_LIST_METHOD_SIGNATURES = SetsKt.plus(SignatureBuildingComponents.INSTANCE.inJavaUtil("Collection", "toArray()[Ljava/lang/Object;", "toArray([Ljava/lang/Object;)[Ljava/lang/Object;"), "java/lang/annotation/Annotation.annotationType()Ljava/lang/Class;");

    /* JADX INFO: compiled from: JvmBuiltInsSettings.kt */
    private enum JDKMemberStatus {
        BLACK_LIST,
        WHITE_LIST,
        NOT_CONSIDERED,
        DROP
    }

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[JDKMemberStatus.values().length];

        static {
            $EnumSwitchMapping$0[JDKMemberStatus.BLACK_LIST.ordinal()] = 1;
            $EnumSwitchMapping$0[JDKMemberStatus.NOT_CONSIDERED.ordinal()] = 2;
            $EnumSwitchMapping$0[JDKMemberStatus.DROP.ordinal()] = 3;
            $EnumSwitchMapping$0[JDKMemberStatus.WHITE_LIST.ordinal()] = 4;
        }
    }

    private final SimpleType getCloneableType() {
        return (SimpleType) StorageKt.getValue(this.cloneableType$delegate, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    private final Annotations getNotConsideredDeprecation() {
        return (Annotations) StorageKt.getValue(this.notConsideredDeprecation$delegate, this, (KProperty<?>) $$delegatedProperties[3]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ModuleDescriptor getOwnerModuleDescriptor() {
        Lazy lazy = this.ownerModuleDescriptor$delegate;
        KProperty kProperty = $$delegatedProperties[0];
        return (ModuleDescriptor) lazy.getValue();
    }

    private final boolean isAdditionalBuiltInsFeatureSupported() {
        Lazy lazy = this.isAdditionalBuiltInsFeatureSupported$delegate;
        KProperty kProperty = $$delegatedProperties[1];
        return ((Boolean) lazy.getValue()).booleanValue();
    }

    public JvmBuiltInsSettings(@NotNull ModuleDescriptor moduleDescriptor, @NotNull final StorageManager storageManager, @NotNull Function0<? extends ModuleDescriptor> deferredOwnerModuleDescriptor, @NotNull Function0<Boolean> isAdditionalBuiltInsFeatureSupported) {
        Intrinsics.checkParameterIsNotNull(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(deferredOwnerModuleDescriptor, "deferredOwnerModuleDescriptor");
        Intrinsics.checkParameterIsNotNull(isAdditionalBuiltInsFeatureSupported, "isAdditionalBuiltInsFeatureSupported");
        this.moduleDescriptor = moduleDescriptor;
        this.j2kClassMap = JavaToKotlinClassMap.INSTANCE;
        this.ownerModuleDescriptor$delegate = LazyKt.lazy(deferredOwnerModuleDescriptor);
        this.isAdditionalBuiltInsFeatureSupported$delegate = LazyKt.lazy(isAdditionalBuiltInsFeatureSupported);
        this.mockSerializableType = createMockJavaIoSerializableType(storageManager);
        this.cloneableType$delegate = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$cloneableType$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final SimpleType invoke() {
                return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.this$0.getOwnerModuleDescriptor(), JvmBuiltInClassDescriptorFactory.Companion.getCLONEABLE_CLASS_ID(), new NotFoundClasses(storageManager, this.this$0.getOwnerModuleDescriptor())).getDefaultType();
            }
        });
        this.javaAnalogueClassesWithCustomSupertypeCache = storageManager.createCacheWithNotNullValues();
        this.notConsideredDeprecation$delegate = storageManager.createLazyValue(new Function0<Annotations>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$notConsideredDeprecation$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Annotations invoke() {
                AnnotationDescriptor annotation = AnnotationUtilKt.createDeprecatedAnnotation$default(this.this$0.moduleDescriptor.getBuiltIns(), "This member is not fully supported by Kotlin compiler, so it may be absent or have different signature in next major version", null, null, 6, null);
                return Annotations.Companion.create(CollectionsKt.listOf(annotation));
            }
        });
    }

    private final KotlinType createMockJavaIoSerializableType(@NotNull StorageManager $receiver) {
        final ModuleDescriptor moduleDescriptor = this.moduleDescriptor;
        final FqName fqName = new FqName("java.io");
        PackageFragmentDescriptorImpl packageFragmentDescriptorImpl = new PackageFragmentDescriptorImpl(moduleDescriptor, fqName) { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$createMockJavaIoSerializableType$mockJavaIoPackageFragment$1
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor
            @NotNull
            public MemberScope.Empty getMemberScope() {
                return MemberScope.Empty.INSTANCE;
            }
        };
        List superTypes = CollectionsKt.listOf(new LazyWrappedType($receiver, new Function0<SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$createMockJavaIoSerializableType$superTypes$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final SimpleType invoke() {
                SimpleType anyType = this.this$0.moduleDescriptor.getBuiltIns().getAnyType();
                Intrinsics.checkExpressionValueIsNotNull(anyType, "moduleDescriptor.builtIns.anyType");
                return anyType;
            }
        }));
        ClassDescriptorImpl mockSerializableClass = new ClassDescriptorImpl(packageFragmentDescriptorImpl, Name.identifier("Serializable"), Modality.ABSTRACT, ClassKind.INTERFACE, superTypes, SourceElement.NO_SOURCE, false, $receiver);
        mockSerializableClass.initialize(MemberScope.Empty.INSTANCE, SetsKt.emptySet(), null);
        SimpleType defaultType = mockSerializableClass.getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(defaultType, "mockSerializableClass.defaultType");
        return defaultType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider
    @NotNull
    public Collection<KotlinType> getSupertypes(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkParameterIsNotNull(classDescriptor, "classDescriptor");
        FqNameUnsafe fqName = DescriptorUtilsKt.getFqNameUnsafe(classDescriptor);
        if (!Companion.isArrayOrPrimitiveArray(fqName)) {
            return Companion.isSerializableInJava(fqName) ? CollectionsKt.listOf(this.mockSerializableType) : CollectionsKt.emptyList();
        }
        SimpleType cloneableType = getCloneableType();
        Intrinsics.checkExpressionValueIsNotNull(cloneableType, "cloneableType");
        return CollectionsKt.listOf((Object[]) new KotlinType[]{cloneableType, this.mockSerializableType});
    }

    /* JADX WARN: Code duplicated, block: B:54:0x016a  */
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider
    @NotNull
    public Collection<SimpleFunctionDescriptor> getFunctions(@NotNull final Name name, @NotNull ClassDescriptor classDescriptor) {
        FunctionDescriptor functionDescriptorBuild;
        JvmBuiltInsSettings jvmBuiltInsSettings = this;
        ClassDescriptor classDescriptor2 = classDescriptor;
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(classDescriptor2, "classDescriptor");
        boolean z = false;
        if (Intrinsics.areEqual(name, CloneableClassScope.Companion.getCLONE_NAME$descriptors_jvm()) && (classDescriptor2 instanceof DeserializedClassDescriptor) && KotlinBuiltIns.isArrayOrPrimitiveArray(classDescriptor)) {
            Iterable functionList = ((DeserializedClassDescriptor) classDescriptor2).getClassProto().getFunctionList();
            Intrinsics.checkExpressionValueIsNotNull(functionList, "classDescriptor.classProto.functionList");
            Iterable $receiver$iv = functionList;
            if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                for (Object element$iv : $receiver$iv) {
                    ProtoBuf.Function functionProto = (ProtoBuf.Function) element$iv;
                    NameResolver nameResolver = ((DeserializedClassDescriptor) classDescriptor2).getC().getNameResolver();
                    Intrinsics.checkExpressionValueIsNotNull(functionProto, "functionProto");
                    if (Intrinsics.areEqual(NameResolverUtilKt.getName(nameResolver, functionProto.getName()), CloneableClassScope.Companion.getCLONE_NAME$descriptors_jvm())) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                return CollectionsKt.emptyList();
            }
            return CollectionsKt.listOf(jvmBuiltInsSettings.createCloneForArray((DeserializedClassDescriptor) classDescriptor2, (SimpleFunctionDescriptor) CollectionsKt.single(getCloneableType().getMemberScope().getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS))));
        }
        if (!isAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        Iterable $receiver$iv2 = jvmBuiltInsSettings.getAdditionalFunctions(classDescriptor2, new Function1<MemberScope, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.getFunctions.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<? extends SimpleFunctionDescriptor> invoke(@NotNull MemberScope it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS);
            }
        });
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv2) {
            SimpleFunctionDescriptor additionalMember = (SimpleFunctionDescriptor) element$iv$iv$iv;
            DeclarationDescriptor containingDeclaration = additionalMember.getContainingDeclaration();
            if (containingDeclaration != null) {
                FunctionDescriptor functionDescriptorSubstitute = additionalMember.substitute(MappingUtilKt.createMappedTypeParametersSubstitution((ClassDescriptor) containingDeclaration, classDescriptor2).buildSubstitutor());
                if (functionDescriptorSubstitute == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor");
                }
                SimpleFunctionDescriptor substitutedWithKotlinTypeParameters = (SimpleFunctionDescriptor) functionDescriptorSubstitute;
                FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = substitutedWithKotlinTypeParameters.newCopyBuilder();
                copyBuilderNewCopyBuilder.setOwner(classDescriptor2);
                copyBuilderNewCopyBuilder.setDispatchReceiverParameter(classDescriptor.getThisAsReceiverParameter());
                copyBuilderNewCopyBuilder.setPreserveSourceElement();
                JDKMemberStatus memberStatus = jvmBuiltInsSettings.getJdkMethodStatus(additionalMember);
                int i = WhenMappings.$EnumSwitchMapping$0[memberStatus.ordinal()];
                SimpleFunctionDescriptor simpleFunctionDescriptor = null;
                if (i == 1) {
                    if (!ModalityKt.isFinalClass(classDescriptor)) {
                        Intrinsics.checkExpressionValueIsNotNull(copyBuilderNewCopyBuilder.setHiddenForResolutionEverywhereBesideSupercalls(), "setHiddenForResolutionEverywhereBesideSupercalls()");
                        functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
                        if (functionDescriptorBuild == null) {
                            Intrinsics.throwNpe();
                        }
                        simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptorBuild;
                    }
                } else {
                    if (i == 2) {
                        Intrinsics.checkExpressionValueIsNotNull(copyBuilderNewCopyBuilder.setAdditionalAnnotations(getNotConsideredDeprecation()), "setAdditionalAnnotations(notConsideredDeprecation)");
                    } else if (i != 3) {
                        if (i != 4) {
                        }
                    }
                    functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
                    if (functionDescriptorBuild == null) {
                        Intrinsics.throwNpe();
                    }
                    simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptorBuild;
                }
                if (simpleFunctionDescriptor != null) {
                    destination$iv$iv.add(simpleFunctionDescriptor);
                }
                jvmBuiltInsSettings = this;
                classDescriptor2 = classDescriptor;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
        }
        return (List) destination$iv$iv;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider
    @NotNull
    public Set<Name> getFunctionsNames(@NotNull ClassDescriptor classDescriptor) {
        LazyJavaClassMemberScope unsubstitutedMemberScope;
        Set<Name> functionNames;
        Intrinsics.checkParameterIsNotNull(classDescriptor, "classDescriptor");
        if (!isAdditionalBuiltInsFeatureSupported()) {
            return SetsKt.emptySet();
        }
        LazyJavaClassDescriptor javaAnalogue = getJavaAnalogue(classDescriptor);
        return (javaAnalogue == null || (unsubstitutedMemberScope = javaAnalogue.getUnsubstitutedMemberScope()) == null || (functionNames = unsubstitutedMemberScope.getFunctionNames()) == null) ? SetsKt.emptySet() : functionNames;
    }

    private final Collection<SimpleFunctionDescriptor> getAdditionalFunctions(ClassDescriptor classDescriptor, Function1<? super MemberScope, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        boolean z;
        final LazyJavaClassDescriptor javaAnalogueDescriptor = getJavaAnalogue(classDescriptor);
        if (javaAnalogueDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        Collection kotlinClassDescriptors = this.j2kClassMap.mapPlatformClass(DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), FallbackBuiltIns.Companion.getInstance());
        final ClassDescriptor kotlinMutableClassIfContainer = (ClassDescriptor) CollectionsKt.lastOrNull(kotlinClassDescriptors);
        if (kotlinMutableClassIfContainer == null) {
            return CollectionsKt.emptyList();
        }
        SmartSet.Companion companion = SmartSet.Companion;
        Collection $receiver$iv = kotlinClassDescriptors;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            destination$iv$iv.add(DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) item$iv$iv));
        }
        SmartSet kotlinVersions = companion.create((Collection) ((List) destination$iv$iv));
        boolean isMutable = this.j2kClassMap.isMutable(classDescriptor);
        ClassDescriptor fakeJavaClassDescriptor = this.javaAnalogueClassesWithCustomSupertypeCache.computeIfAbsent(DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), new Function0<LazyJavaClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$getAdditionalFunctions$fakeJavaClassDescriptor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final LazyJavaClassDescriptor invoke() {
                LazyJavaClassDescriptor lazyJavaClassDescriptor = javaAnalogueDescriptor;
                JavaResolverCache javaResolverCache = JavaResolverCache.EMPTY;
                Intrinsics.checkExpressionValueIsNotNull(javaResolverCache, "JavaResolverCache.EMPTY");
                return lazyJavaClassDescriptor.copy$descriptors_jvm(javaResolverCache, kotlinMutableClassIfContainer);
            }
        });
        MemberScope scope = fakeJavaClassDescriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkExpressionValueIsNotNull(scope, "fakeJavaClassDescriptor.unsubstitutedMemberScope");
        Iterable $receiver$iv2 = function1.invoke(scope);
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $receiver$iv2) {
            LazyJavaClassDescriptor javaAnalogueDescriptor2 = javaAnalogueDescriptor;
            SimpleFunctionDescriptor analogueMember = (SimpleFunctionDescriptor) element$iv$iv;
            Collection kotlinClassDescriptors2 = kotlinClassDescriptors;
            ClassDescriptor kotlinMutableClassIfContainer2 = kotlinMutableClassIfContainer;
            boolean z2 = true;
            if (analogueMember.getKind() == CallableMemberDescriptor.Kind.DECLARATION && analogueMember.getVisibility().isPublicAPI() && !KotlinBuiltIns.isDeprecated(analogueMember)) {
                Iterable overriddenDescriptors = analogueMember.getOverriddenDescriptors();
                Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "analogueMember.overriddenDescriptors");
                Iterable $receiver$iv3 = overriddenDescriptors;
                if (($receiver$iv3 instanceof Collection) && ((Collection) $receiver$iv3).isEmpty()) {
                    z = false;
                } else {
                    Iterator it = $receiver$iv3.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        Object element$iv = it.next();
                        Iterable $receiver$iv4 = $receiver$iv3;
                        FunctionDescriptor it2 = (FunctionDescriptor) element$iv;
                        Iterator it3 = it;
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        DeclarationDescriptor containingDeclaration = it2.getContainingDeclaration();
                        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "it.containingDeclaration");
                        if (kotlinVersions.contains(DescriptorUtilsKt.getFqNameSafe(containingDeclaration))) {
                            z = true;
                            break;
                        }
                        $receiver$iv3 = $receiver$iv4;
                        it = it3;
                    }
                }
                if (z || isMutabilityViolation(analogueMember, isMutable)) {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            if (z2) {
                destination$iv$iv2.add(element$iv$iv);
            }
            javaAnalogueDescriptor = javaAnalogueDescriptor2;
            kotlinClassDescriptors = kotlinClassDescriptors2;
            kotlinMutableClassIfContainer = kotlinMutableClassIfContainer2;
        }
        return (List) destination$iv$iv2;
    }

    private final SimpleFunctionDescriptor createCloneForArray(DeserializedClassDescriptor arrayClassDescriptor, SimpleFunctionDescriptor cloneFromCloneable) {
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = cloneFromCloneable.newCopyBuilder();
        copyBuilderNewCopyBuilder.setOwner(arrayClassDescriptor);
        copyBuilderNewCopyBuilder.setVisibility(Visibilities.PUBLIC);
        copyBuilderNewCopyBuilder.setReturnType(arrayClassDescriptor.getDefaultType());
        copyBuilderNewCopyBuilder.setDispatchReceiverParameter(arrayClassDescriptor.getThisAsReceiverParameter());
        FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
        if (functionDescriptorBuild == null) {
            Intrinsics.throwNpe();
        }
        return (SimpleFunctionDescriptor) functionDescriptorBuild;
    }

    private final boolean isMutabilityViolation(@NotNull SimpleFunctionDescriptor $receiver, boolean isMutable) {
        DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
        if (containingDeclaration == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor owner = (ClassDescriptor) containingDeclaration;
        String jvmDescriptor = MethodSignatureMappingKt.computeJvmDescriptor$default($receiver, false, false, 3, null);
        if (MUTABLE_METHOD_SIGNATURES.contains(SignatureBuildingComponents.INSTANCE.signature(owner, jvmDescriptor)) ^ isMutable) {
            return true;
        }
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf($receiver), new DFS.Neighbors<N>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.isMutabilityViolation.1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            @NotNull
            public final Collection<? extends CallableMemberDescriptor> getNeighbors(CallableMemberDescriptor it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                CallableMemberDescriptor original = it.getOriginal();
                Intrinsics.checkExpressionValueIsNotNull(original, "it.original");
                return original.getOverriddenDescriptors();
            }
        }, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.isMutabilityViolation.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
                return Boolean.valueOf(invoke2(callableMemberDescriptor));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(CallableMemberDescriptor overridden) {
                Intrinsics.checkExpressionValueIsNotNull(overridden, "overridden");
                if (overridden.getKind() == CallableMemberDescriptor.Kind.DECLARATION) {
                    JavaToKotlinClassMap javaToKotlinClassMap = JvmBuiltInsSettings.this.j2kClassMap;
                    DeclarationDescriptor containingDeclaration2 = overridden.getContainingDeclaration();
                    if (containingDeclaration2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                    if (javaToKotlinClassMap.isMutable((ClassDescriptor) containingDeclaration2)) {
                        return true;
                    }
                }
                return false;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(boolIfAny, "DFS.ifAny<CallableMember…lassDescriptor)\n        }");
        return boolIfAny.booleanValue();
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$JDKMemberStatus] */
    private final JDKMemberStatus getJdkMethodStatus(@NotNull FunctionDescriptor $receiver) {
        DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
        if (containingDeclaration == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor owner = (ClassDescriptor) containingDeclaration;
        final String jvmDescriptor = MethodSignatureMappingKt.computeJvmDescriptor$default($receiver, false, false, 3, null);
        final Ref.ObjectRef result = new Ref.ObjectRef();
        result.element = (JDKMemberStatus) 0;
        Object objDfs = DFS.dfs(CollectionsKt.listOf(owner), new DFS.Neighbors<N>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.getJdkMethodStatus.1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            @NotNull
            public final List<LazyJavaClassDescriptor> getNeighbors(ClassDescriptor it) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                TypeConstructor typeConstructor = it.getTypeConstructor();
                Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "it.typeConstructor");
                Iterable supertypes = typeConstructor.getSupertypes();
                Intrinsics.checkExpressionValueIsNotNull(supertypes, "it.typeConstructor.supertypes");
                Iterable $receiver$iv = supertypes;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv$iv : $receiver$iv) {
                    ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = ((KotlinType) element$iv$iv$iv).getConstructor().mo381getDeclarationDescriptor();
                    ClassifierDescriptor original = classifierDescriptorMo381getDeclarationDescriptor != null ? classifierDescriptorMo381getDeclarationDescriptor.getOriginal() : null;
                    if (!(original instanceof ClassDescriptor)) {
                        original = null;
                    }
                    ClassDescriptor classDescriptor = (ClassDescriptor) original;
                    LazyJavaClassDescriptor javaAnalogue = classDescriptor != null ? JvmBuiltInsSettings.this.getJavaAnalogue(classDescriptor) : null;
                    if (javaAnalogue != null) {
                        destination$iv$iv.add(javaAnalogue);
                    }
                }
                return (List) destination$iv$iv;
            }
        }, new DFS.AbstractNodeHandler<ClassDescriptor, JDKMemberStatus>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.getJdkMethodStatus.2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v0, types: [T, kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$JDKMemberStatus] */
            /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$JDKMemberStatus] */
            /* JADX WARN: Type inference failed for: r2v2, types: [T, kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$JDKMemberStatus] */
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(@NotNull ClassDescriptor javaClassDescriptor) {
                Intrinsics.checkParameterIsNotNull(javaClassDescriptor, "javaClassDescriptor");
                String signature = SignatureBuildingComponents.INSTANCE.signature(javaClassDescriptor, jvmDescriptor);
                if (JvmBuiltInsSettings.Companion.getBLACK_LIST_METHOD_SIGNATURES().contains(signature)) {
                    result.element = JDKMemberStatus.BLACK_LIST;
                } else if (JvmBuiltInsSettings.Companion.getWHITE_LIST_METHOD_SIGNATURES().contains(signature)) {
                    result.element = JDKMemberStatus.WHITE_LIST;
                } else if (JvmBuiltInsSettings.Companion.getDROP_LIST_METHOD_SIGNATURES().contains(signature)) {
                    result.element = JDKMemberStatus.DROP;
                }
                return ((JDKMemberStatus) result.element) == null;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            @NotNull
            public JDKMemberStatus result() {
                JDKMemberStatus jDKMemberStatus = (JDKMemberStatus) result.element;
                return jDKMemberStatus != null ? jDKMemberStatus : JDKMemberStatus.NOT_CONSIDERED;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(objDfs, "DFS.dfs<ClassDescriptor,…CONSIDERED\n            })");
        return (JDKMemberStatus) objDfs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LazyJavaClassDescriptor getJavaAnalogue(@NotNull ClassDescriptor $receiver) {
        ClassId classIdMapKotlinToJava;
        FqName javaAnalogueFqName;
        if (KotlinBuiltIns.isAny($receiver) || !KotlinBuiltIns.isUnderKotlinPackage($receiver)) {
            return null;
        }
        FqNameUnsafe fqName = DescriptorUtilsKt.getFqNameUnsafe($receiver);
        if (!fqName.isSafe() || (classIdMapKotlinToJava = this.j2kClassMap.mapKotlinToJava(fqName)) == null || (javaAnalogueFqName = classIdMapKotlinToJava.asSingleFqName()) == null) {
            return null;
        }
        Intrinsics.checkExpressionValueIsNotNull(javaAnalogueFqName, "j2kClassMap.mapKotlinToJ…leFqName() ?: return null");
        ClassDescriptor classDescriptorResolveClassByFqName = DescriptorUtilKt.resolveClassByFqName(getOwnerModuleDescriptor(), javaAnalogueFqName, NoLookupLocation.FROM_BUILTINS);
        return (LazyJavaClassDescriptor) (classDescriptorResolveClassByFqName instanceof LazyJavaClassDescriptor ? classDescriptorResolveClassByFqName : null);
    }

    /* JADX WARN: Code duplicated, block: B:43:0x013c  */
    /* JADX WARN: Code duplicated, block: B:66:0x013f A[SYNTHETIC] */
    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider
    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors(@NotNull ClassDescriptor classDescriptor) {
        String str;
        ClassDescriptor defaultKotlinVersion;
        Function2<ConstructorDescriptor, ConstructorDescriptor, Boolean> function2;
        Iterable $receiver$iv$iv;
        int $i$f$filterTo;
        boolean z;
        JvmBuiltInsSettings jvmBuiltInsSettings = this;
        ClassDescriptor classDescriptor2 = classDescriptor;
        Intrinsics.checkParameterIsNotNull(classDescriptor2, "classDescriptor");
        if (classDescriptor.getKind() != ClassKind.CLASS || !isAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        LazyJavaClassDescriptor javaAnalogueDescriptor = getJavaAnalogue(classDescriptor);
        if (javaAnalogueDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        ClassDescriptor defaultKotlinVersion2 = JavaToKotlinClassMap.mapJavaToKotlin$default(jvmBuiltInsSettings.j2kClassMap, DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), FallbackBuiltIns.Companion.getInstance(), null, 4, null);
        if (defaultKotlinVersion2 == null) {
            return CollectionsKt.emptyList();
        }
        final TypeSubstitutor substitutor = MappingUtilKt.createMappedTypeParametersSubstitution(defaultKotlinVersion2, javaAnalogueDescriptor).buildSubstitutor();
        Function2<ConstructorDescriptor, ConstructorDescriptor, Boolean> function3 = new Function2<ConstructorDescriptor, ConstructorDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings.getConstructors.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(ConstructorDescriptor constructorDescriptor, ConstructorDescriptor constructorDescriptor2) {
                return Boolean.valueOf(invoke2(constructorDescriptor, constructorDescriptor2));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull ConstructorDescriptor receiver$0, @NotNull ConstructorDescriptor javaConstructor) {
                Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                Intrinsics.checkParameterIsNotNull(javaConstructor, "javaConstructor");
                return OverridingUtil.getBothWaysOverridability(receiver$0, javaConstructor.substitute(substitutor)) == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
            }
        };
        Iterable $receiver$iv = javaAnalogueDescriptor.getConstructors();
        int $i$f$filter = 0;
        Collection destination$iv$iv = new ArrayList();
        Iterable $receiver$iv$iv2 = $receiver$iv;
        int $i$f$filterTo2 = 0;
        Iterator it = $receiver$iv$iv2.iterator();
        while (true) {
            str = "javaConstructor";
            if (!it.hasNext()) {
                break;
            }
            Iterable $receiver$iv2 = $receiver$iv;
            Object element$iv$iv = it.next();
            int $i$f$filter2 = $i$f$filter;
            ClassConstructorDescriptor javaConstructor = (ClassConstructorDescriptor) element$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(javaConstructor, "javaConstructor");
            boolean z2 = true;
            if (javaConstructor.getVisibility().isPublicAPI()) {
                Iterable constructors = defaultKotlinVersion2.getConstructors();
                defaultKotlinVersion = defaultKotlinVersion2;
                Intrinsics.checkExpressionValueIsNotNull(constructors, "defaultKotlinVersion.constructors");
                Iterable $receiver$iv3 = constructors;
                if (($receiver$iv3 instanceof Collection) && ((Collection) $receiver$iv3).isEmpty()) {
                    z = true;
                } else {
                    Iterator it2 = $receiver$iv3.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = true;
                            break;
                        }
                        Object element$iv = it2.next();
                        Iterable $receiver$iv4 = $receiver$iv3;
                        ClassConstructorDescriptor it3 = (ClassConstructorDescriptor) element$iv;
                        Iterator it4 = it2;
                        Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                        if (function3.invoke2((ConstructorDescriptor) it3, (ConstructorDescriptor) javaConstructor)) {
                            z = false;
                            break;
                        }
                        $receiver$iv3 = $receiver$iv4;
                        it2 = it4;
                    }
                }
                if (!z || jvmBuiltInsSettings.isTrivialCopyConstructorFor(javaConstructor, classDescriptor2) || KotlinBuiltIns.isDeprecated(javaConstructor)) {
                    function2 = function3;
                    $receiver$iv$iv = $receiver$iv$iv2;
                    $i$f$filterTo = $i$f$filterTo2;
                } else {
                    function2 = function3;
                    $receiver$iv$iv = $receiver$iv$iv2;
                    $i$f$filterTo = $i$f$filterTo2;
                    if (BLACK_LIST_CONSTRUCTOR_SIGNATURES.contains(SignatureBuildingComponents.INSTANCE.signature(javaAnalogueDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(javaConstructor, false, false, 3, null)))) {
                    }
                    if (z2) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                    jvmBuiltInsSettings = this;
                    $receiver$iv = $receiver$iv2;
                    $i$f$filter = $i$f$filter2;
                    defaultKotlinVersion2 = defaultKotlinVersion;
                    function3 = function2;
                    $receiver$iv$iv2 = $receiver$iv$iv;
                    $i$f$filterTo2 = $i$f$filterTo;
                }
            } else {
                defaultKotlinVersion = defaultKotlinVersion2;
                function2 = function3;
                $receiver$iv$iv = $receiver$iv$iv2;
                $i$f$filterTo = $i$f$filterTo2;
            }
            z2 = false;
            if (z2) {
                destination$iv$iv.add(element$iv$iv);
            }
            jvmBuiltInsSettings = this;
            $receiver$iv = $receiver$iv2;
            $i$f$filter = $i$f$filter2;
            defaultKotlinVersion2 = defaultKotlinVersion;
            function3 = function2;
            $receiver$iv$iv2 = $receiver$iv$iv;
            $i$f$filterTo2 = $i$f$filterTo;
        }
        Iterable $receiver$iv5 = (List) destination$iv$iv;
        int $i$f$map = 0;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv5, 10));
        Iterable $receiver$iv$iv3 = $receiver$iv5;
        for (Object item$iv$iv : $receiver$iv$iv3) {
            ClassConstructorDescriptor javaConstructor2 = (ClassConstructorDescriptor) item$iv$iv;
            FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> copyBuilderNewCopyBuilder = javaConstructor2.newCopyBuilder();
            Iterable $receiver$iv6 = $receiver$iv5;
            copyBuilderNewCopyBuilder.setOwner(classDescriptor2);
            copyBuilderNewCopyBuilder.setReturnType(classDescriptor.getDefaultType());
            copyBuilderNewCopyBuilder.setPreserveSourceElement();
            copyBuilderNewCopyBuilder.setSubstitution(substitutor.getSubstitution());
            Set<String> set = WHITE_LIST_CONSTRUCTOR_SIGNATURES;
            int $i$f$map2 = $i$f$map;
            Intrinsics.checkExpressionValueIsNotNull(javaConstructor2, str);
            LazyJavaClassDescriptor javaAnalogueDescriptor2 = javaAnalogueDescriptor;
            TypeSubstitutor substitutor2 = substitutor;
            Iterable $receiver$iv$iv4 = $receiver$iv$iv3;
            String str2 = str;
            if (!set.contains(SignatureBuildingComponents.INSTANCE.signature(javaAnalogueDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(javaConstructor2, false, false, 3, null)))) {
                copyBuilderNewCopyBuilder.setAdditionalAnnotations(getNotConsideredDeprecation());
            }
            FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
            if (functionDescriptorBuild == null) {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
            }
            destination$iv$iv2.add((ClassConstructorDescriptor) functionDescriptorBuild);
            classDescriptor2 = classDescriptor;
            $receiver$iv5 = $receiver$iv6;
            $receiver$iv$iv3 = $receiver$iv$iv4;
            str = str2;
            $i$f$map = $i$f$map2;
            javaAnalogueDescriptor = javaAnalogueDescriptor2;
            substitutor = substitutor2;
        }
        return (List) destination$iv$iv2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter
    public boolean isFunctionAvailable(@NotNull ClassDescriptor classDescriptor, @NotNull SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkParameterIsNotNull(classDescriptor, "classDescriptor");
        Intrinsics.checkParameterIsNotNull(functionDescriptor, "functionDescriptor");
        LazyJavaClassDescriptor javaAnalogueClassDescriptor = getJavaAnalogue(classDescriptor);
        if (javaAnalogueClassDescriptor == null || !functionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME())) {
            return true;
        }
        if (!isAdditionalBuiltInsFeatureSupported()) {
            return false;
        }
        String jvmDescriptor = MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 3, null);
        LazyJavaClassMemberScope unsubstitutedMemberScope = javaAnalogueClassDescriptor.getUnsubstitutedMemberScope();
        Name name = functionDescriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "functionDescriptor.name");
        Iterable $receiver$iv = unsubstitutedMemberScope.getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS);
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $receiver$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv;
            if (Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmDescriptor$default(it, false, false, 3, null), jvmDescriptor)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isTrivialCopyConstructorFor(@NotNull ConstructorDescriptor $receiver, ClassDescriptor classDescriptor) {
        if ($receiver.getValueParameters().size() == 1) {
            List<ValueParameterDescriptor> valueParameters = $receiver.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "valueParameters");
            Object objSingle = CollectionsKt.single((List<? extends Object>) valueParameters);
            Intrinsics.checkExpressionValueIsNotNull(objSingle, "valueParameters.single()");
            ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = ((ValueParameterDescriptor) objSingle).getType().getConstructor().mo381getDeclarationDescriptor();
            if (Intrinsics.areEqual(classifierDescriptorMo381getDeclarationDescriptor != null ? DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptorMo381getDeclarationDescriptor) : null, DescriptorUtilsKt.getFqNameUnsafe(classDescriptor))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: JvmBuiltInsSettings.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean isSerializableInJava(@NotNull FqNameUnsafe fqName) {
            Intrinsics.checkParameterIsNotNull(fqName, "fqName");
            if (isArrayOrPrimitiveArray(fqName)) {
                return true;
            }
            ClassId javaClassId = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(fqName);
            if (javaClassId == null) {
                return false;
            }
            try {
                return Serializable.class.isAssignableFrom(Class.forName(javaClassId.asSingleFqName().asString()));
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isArrayOrPrimitiveArray(FqNameUnsafe fqName) {
            return Intrinsics.areEqual(fqName, KotlinBuiltIns.FQ_NAMES.array) || KotlinBuiltIns.isPrimitiveArray(fqName);
        }

        @NotNull
        public final Set<String> getDROP_LIST_METHOD_SIGNATURES() {
            return JvmBuiltInsSettings.DROP_LIST_METHOD_SIGNATURES;
        }

        @NotNull
        public final Set<String> getBLACK_LIST_METHOD_SIGNATURES() {
            return JvmBuiltInsSettings.BLACK_LIST_METHOD_SIGNATURES;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<String> buildPrimitiveValueMethodsSet() {
            SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
            Iterable $receiver$iv = CollectionsKt.listOf((Object[]) new JvmPrimitiveType[]{JvmPrimitiveType.BOOLEAN, JvmPrimitiveType.CHAR});
            Collection destination$iv = new LinkedHashSet();
            for (Object element$iv : $receiver$iv) {
                JvmPrimitiveType it = (JvmPrimitiveType) element$iv;
                String strAsString = it.getWrapperFqName().shortName().asString();
                Intrinsics.checkExpressionValueIsNotNull(strAsString, "it.wrapperFqName.shortName().asString()");
                Iterable list$iv = $receiver.inJavaLang(strAsString, it.getJavaKeywordName() + "Value()" + it.getDesc());
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            return (LinkedHashSet) destination$iv;
        }

        @NotNull
        public final Set<String> getWHITE_LIST_METHOD_SIGNATURES() {
            return JvmBuiltInsSettings.WHITE_LIST_METHOD_SIGNATURES;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Set<String> buildPrimitiveStringConstructorsSet() {
            SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
            Iterable $receiver$iv = CollectionsKt.listOf((Object[]) new JvmPrimitiveType[]{JvmPrimitiveType.BOOLEAN, JvmPrimitiveType.BYTE, JvmPrimitiveType.DOUBLE, JvmPrimitiveType.FLOAT, JvmPrimitiveType.BYTE, JvmPrimitiveType.INT, JvmPrimitiveType.LONG, JvmPrimitiveType.SHORT});
            Collection destination$iv = new LinkedHashSet();
            for (Object element$iv : $receiver$iv) {
                JvmPrimitiveType it = (JvmPrimitiveType) element$iv;
                String strAsString = it.getWrapperFqName().shortName().asString();
                Intrinsics.checkExpressionValueIsNotNull(strAsString, "it.wrapperFqName.shortName().asString()");
                String[] strArrConstructors = $receiver.constructors("Ljava/lang/String;");
                Iterable list$iv = $receiver.inJavaLang(strAsString, (String[]) Arrays.copyOf(strArrConstructors, strArrConstructors.length));
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            return (LinkedHashSet) destination$iv;
        }
    }

    static {
        SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
        BLACK_LIST_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(Companion.buildPrimitiveValueMethodsSet(), (Iterable) $receiver.inJavaUtil("List", "sort(Ljava/util/Comparator;)V")), (Iterable) $receiver.inJavaLang("String", "codePointAt(I)I", "codePointBefore(I)I", "codePointCount(II)I", "compareToIgnoreCase(Ljava/lang/String;)I", "concat(Ljava/lang/String;)Ljava/lang/String;", "contains(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/StringBuffer;)Z", "endsWith(Ljava/lang/String;)Z", "equalsIgnoreCase(Ljava/lang/String;)Z", "getBytes()[B", "getBytes(II[BI)V", "getBytes(Ljava/lang/String;)[B", "getBytes(Ljava/nio/charset/Charset;)[B", "getChars(II[CI)V", "indexOf(I)I", "indexOf(II)I", "indexOf(Ljava/lang/String;)I", "indexOf(Ljava/lang/String;I)I", "intern()Ljava/lang/String;", "isEmpty()Z", "lastIndexOf(I)I", "lastIndexOf(II)I", "lastIndexOf(Ljava/lang/String;)I", "lastIndexOf(Ljava/lang/String;I)I", "matches(Ljava/lang/String;)Z", "offsetByCodePoints(II)I", "regionMatches(ILjava/lang/String;II)Z", "regionMatches(ZILjava/lang/String;II)Z", "replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(CC)Ljava/lang/String;", "replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", "split(Ljava/lang/String;I)[Ljava/lang/String;", "split(Ljava/lang/String;)[Ljava/lang/String;", "startsWith(Ljava/lang/String;I)Z", "startsWith(Ljava/lang/String;)Z", "substring(II)Ljava/lang/String;", "substring(I)Ljava/lang/String;", "toCharArray()[C", "toLowerCase()Ljava/lang/String;", "toLowerCase(Ljava/util/Locale;)Ljava/lang/String;", "toUpperCase()Ljava/lang/String;", "toUpperCase(Ljava/util/Locale;)Ljava/lang/String;", "trim()Ljava/lang/String;", "isBlank()Z", "lines()Ljava/util/stream/Stream;", "repeat(I)Ljava/lang/String;")), (Iterable) $receiver.inJavaLang("Double", "isInfinite()Z", "isNaN()Z")), (Iterable) $receiver.inJavaLang("Float", "isInfinite()Z", "isNaN()Z")), (Iterable) $receiver.inJavaLang("Enum", "getDeclaringClass()Ljava/lang/Class;", "finalize()V"));
        SignatureBuildingComponents $receiver2 = SignatureBuildingComponents.INSTANCE;
        WHITE_LIST_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus((Set) $receiver2.inJavaLang("CharSequence", "codePoints()Ljava/util/stream/IntStream;", "chars()Ljava/util/stream/IntStream;"), (Iterable) $receiver2.inJavaUtil("Iterator", "forEachRemaining(Ljava/util/function/Consumer;)V")), (Iterable) $receiver2.inJavaLang("Iterable", "forEach(Ljava/util/function/Consumer;)V", "spliterator()Ljava/util/Spliterator;")), (Iterable) $receiver2.inJavaLang("Throwable", "setStackTrace([Ljava/lang/StackTraceElement;)V", "fillInStackTrace()Ljava/lang/Throwable;", "getLocalizedMessage()Ljava/lang/String;", "printStackTrace()V", "printStackTrace(Ljava/io/PrintStream;)V", "printStackTrace(Ljava/io/PrintWriter;)V", "getStackTrace()[Ljava/lang/StackTraceElement;", "initCause(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "getSuppressed()[Ljava/lang/Throwable;", "addSuppressed(Ljava/lang/Throwable;)V")), (Iterable) $receiver2.inJavaUtil("Collection", "spliterator()Ljava/util/Spliterator;", "parallelStream()Ljava/util/stream/Stream;", "stream()Ljava/util/stream/Stream;", "removeIf(Ljava/util/function/Predicate;)Z")), (Iterable) $receiver2.inJavaUtil("List", "replaceAll(Ljava/util/function/UnaryOperator;)V")), (Iterable) $receiver2.inJavaUtil("Map", "getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "forEach(Ljava/util/function/BiConsumer;)V", "replaceAll(Ljava/util/function/BiFunction;)V", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"));
        SignatureBuildingComponents $receiver3 = SignatureBuildingComponents.INSTANCE;
        MUTABLE_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus((Set) $receiver3.inJavaUtil("Collection", "removeIf(Ljava/util/function/Predicate;)Z"), (Iterable) $receiver3.inJavaUtil("List", "replaceAll(Ljava/util/function/UnaryOperator;)V", "sort(Ljava/util/Comparator;)V")), (Iterable) $receiver3.inJavaUtil("Map", "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "remove(Ljava/lang/Object;Ljava/lang/Object;)Z", "replaceAll(Ljava/util/function/BiFunction;)V", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z"));
        SignatureBuildingComponents $receiver4 = SignatureBuildingComponents.INSTANCE;
        Set setBuildPrimitiveStringConstructorsSet = Companion.buildPrimitiveStringConstructorsSet();
        String[] strArrConstructors = $receiver4.constructors("D");
        Set setPlus = SetsKt.plus(setBuildPrimitiveStringConstructorsSet, (Iterable) $receiver4.inJavaLang("Float", (String[]) Arrays.copyOf(strArrConstructors, strArrConstructors.length)));
        String[] strArrConstructors2 = $receiver4.constructors("[C", "[CII", "[III", "[BIILjava/lang/String;", "[BIILjava/nio/charset/Charset;", "[BLjava/lang/String;", "[BLjava/nio/charset/Charset;", "[BII", "[B", "Ljava/lang/StringBuffer;", "Ljava/lang/StringBuilder;");
        BLACK_LIST_CONSTRUCTOR_SIGNATURES = SetsKt.plus(setPlus, (Iterable) $receiver4.inJavaLang("String", (String[]) Arrays.copyOf(strArrConstructors2, strArrConstructors2.length)));
        SignatureBuildingComponents $receiver5 = SignatureBuildingComponents.INSTANCE;
        String[] strArrConstructors3 = $receiver5.constructors("Ljava/lang/String;Ljava/lang/Throwable;ZZ");
        WHITE_LIST_CONSTRUCTOR_SIGNATURES = $receiver5.inJavaLang("Throwable", (String[]) Arrays.copyOf(strArrConstructors3, strArrConstructors3.length));
    }
}
