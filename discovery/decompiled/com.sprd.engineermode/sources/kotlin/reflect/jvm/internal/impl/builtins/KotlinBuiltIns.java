package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class KotlinBuiltIns {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MemoizedFunctionToNotNull<Name, ClassDescriptor> builtInClassesByName;
    private ModuleDescriptorImpl builtInsModule;
    private final NotNullLazyValue<PackageFragments> packageFragments;
    private final NotNullLazyValue<Primitives> primitives;
    private final StorageManager storageManager;
    public static final Name BUILT_INS_PACKAGE_NAME = Name.identifier("kotlin");
    public static final FqName BUILT_INS_PACKAGE_FQ_NAME = FqName.topLevel(BUILT_INS_PACKAGE_NAME);
    private static final FqName ANNOTATION_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("annotation"));
    public static final FqName COLLECTIONS_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("collections"));
    public static final FqName RANGES_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("ranges"));
    public static final FqName TEXT_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("text"));
    public static final Set<FqName> BUILT_INS_PACKAGE_FQ_NAMES = SetsKt.setOf((Object[]) new FqName[]{BUILT_INS_PACKAGE_FQ_NAME, COLLECTIONS_PACKAGE_FQ_NAME, RANGES_PACKAGE_FQ_NAME, ANNOTATION_PACKAGE_FQ_NAME, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("internal")), DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE});
    public static final FqNames FQ_NAMES = new FqNames();
    public static final Name BUILTINS_MODULE_NAME = Name.special("<built-ins module>");

    protected KotlinBuiltIns(@NotNull StorageManager storageManager) {
        this.storageManager = storageManager;
        this.packageFragments = storageManager.createLazyValue(new Function0<PackageFragments>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.1
            @Override // kotlin.jvm.functions.Function0
            public PackageFragments invoke() {
                PackageFragmentProvider provider = KotlinBuiltIns.this.builtInsModule.getPackageFragmentProvider();
                Map<FqName, PackageFragmentDescriptor> nameToFragment = new LinkedHashMap<>();
                PackageFragmentDescriptor kotlin2 = KotlinBuiltIns.this.createPackage(provider, nameToFragment, KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME);
                KotlinBuiltIns.this.createPackage(provider, null, DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE);
                PackageFragmentDescriptor kotlinCollections = KotlinBuiltIns.this.createPackage(provider, nameToFragment, KotlinBuiltIns.COLLECTIONS_PACKAGE_FQ_NAME);
                KotlinBuiltIns.this.createPackage(provider, nameToFragment, KotlinBuiltIns.RANGES_PACKAGE_FQ_NAME);
                PackageFragmentDescriptor kotlinAnnotation = KotlinBuiltIns.this.createPackage(provider, nameToFragment, KotlinBuiltIns.ANNOTATION_PACKAGE_FQ_NAME);
                Set<PackageFragmentDescriptor> allImportedByDefault = new LinkedHashSet<>(nameToFragment.values());
                return new PackageFragments(kotlin2, kotlinCollections, kotlinAnnotation, allImportedByDefault);
            }
        });
        this.primitives = storageManager.createLazyValue(new Function0<Primitives>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.2
            @Override // kotlin.jvm.functions.Function0
            public Primitives invoke() {
                Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType = new EnumMap<>(PrimitiveType.class);
                Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType = new HashMap<>();
                Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType = new HashMap<>();
                PrimitiveType[] arr$ = PrimitiveType.values();
                for (PrimitiveType primitive : arr$) {
                    SimpleType type = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitive.getTypeName().asString());
                    SimpleType arrayType = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitive.getArrayTypeName().asString());
                    primitiveTypeToArrayKotlinType.put(primitive, arrayType);
                    primitiveKotlinTypeToKotlinArrayType.put(type, arrayType);
                    kotlinArrayTypeToPrimitiveKotlinType.put(arrayType, type);
                }
                return new Primitives(primitiveTypeToArrayKotlinType, primitiveKotlinTypeToKotlinArrayType, kotlinArrayTypeToPrimitiveKotlinType);
            }
        });
        this.builtInClassesByName = storageManager.createMemoizedFunction(new Function1<Name, ClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.3
            @Override // kotlin.jvm.functions.Function1
            public ClassDescriptor invoke(Name name) {
                return KotlinBuiltIns.getBuiltInClassByName(name, KotlinBuiltIns.this.getBuiltInsPackageFragment());
            }
        });
    }

    protected void createBuiltInsModule() {
        this.builtInsModule = new ModuleDescriptorImpl(BUILTINS_MODULE_NAME, this.storageManager, this, null);
        this.builtInsModule.initialize(BuiltInsLoader.Companion.getInstance().createPackageFragmentProvider(this.storageManager, this.builtInsModule, getClassDescriptorFactories(), getPlatformDependentDeclarationFilter(), getAdditionalClassPartsProvider()));
        ModuleDescriptorImpl moduleDescriptorImpl = this.builtInsModule;
        moduleDescriptorImpl.setDependencies(moduleDescriptorImpl);
    }

    @NotNull
    protected AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        return AdditionalClassPartsProvider.None.INSTANCE;
    }

    @NotNull
    protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        return PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE;
    }

    @NotNull
    protected Iterable<ClassDescriptorFactory> getClassDescriptorFactories() {
        return Collections.singletonList(new BuiltInFictitiousFunctionClassFactory(this.storageManager, this.builtInsModule));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public PackageFragmentDescriptor createPackage(@NotNull PackageFragmentProvider fragmentProvider, @Nullable Map<FqName, PackageFragmentDescriptor> packageNameToPackageFragment, @NotNull final FqName packageFqName) {
        PackageFragmentDescriptor result;
        final List<PackageFragmentDescriptor> packageFragments = fragmentProvider.getPackageFragments(packageFqName);
        if (packageFragments.isEmpty()) {
            result = new EmptyPackageFragmentDescriptor(this.builtInsModule, packageFqName);
        } else {
            result = packageFragments.size() == 1 ? packageFragments.iterator().next() : new PackageFragmentDescriptorImpl(this.builtInsModule, packageFqName) { // from class: kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.5
                @Override // kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor
                @NotNull
                public MemberScope getMemberScope() {
                    return new ChainedMemberScope("built-in package " + packageFqName, CollectionsKt.map(packageFragments, new Function1<PackageFragmentDescriptor, MemberScope>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.5.1
                        @Override // kotlin.jvm.functions.Function1
                        public MemberScope invoke(PackageFragmentDescriptor descriptor) {
                            return descriptor.getMemberScope();
                        }
                    }));
                }
            };
        }
        if (packageNameToPackageFragment != null) {
            packageNameToPackageFragment.put(packageFqName, result);
        }
        return result;
    }

    @NotNull
    protected StorageManager getStorageManager() {
        return this.storageManager;
    }

    private static class Primitives {
        public final Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType;
        public final Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType;
        public final Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType;

        private Primitives(@NotNull Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType, @NotNull Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType, @NotNull Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType) {
            this.primitiveTypeToArrayKotlinType = primitiveTypeToArrayKotlinType;
            this.primitiveKotlinTypeToKotlinArrayType = primitiveKotlinTypeToKotlinArrayType;
            this.kotlinArrayTypeToPrimitiveKotlinType = kotlinArrayTypeToPrimitiveKotlinType;
        }
    }

    private static class PackageFragments {
        public final Set<PackageFragmentDescriptor> allImportedByDefaultBuiltInsPackageFragments;
        public final PackageFragmentDescriptor annotationPackageFragment;
        public final PackageFragmentDescriptor builtInsPackageFragment;
        public final PackageFragmentDescriptor collectionsPackageFragment;

        private PackageFragments(@NotNull PackageFragmentDescriptor builtInsPackageFragment, @NotNull PackageFragmentDescriptor collectionsPackageFragment, @NotNull PackageFragmentDescriptor annotationPackageFragment, @NotNull Set<PackageFragmentDescriptor> allImportedByDefaultBuiltInsPackageFragments) {
            this.builtInsPackageFragment = builtInsPackageFragment;
            this.collectionsPackageFragment = collectionsPackageFragment;
            this.annotationPackageFragment = annotationPackageFragment;
            this.allImportedByDefaultBuiltInsPackageFragments = allImportedByDefaultBuiltInsPackageFragments;
        }
    }

    public static class FqNames {
        public final FqNameUnsafe any = fqNameUnsafe("Any");
        public final FqNameUnsafe nothing = fqNameUnsafe("Nothing");
        public final FqNameUnsafe cloneable = fqNameUnsafe("Cloneable");
        public final FqName suppress = fqName("Suppress");
        public final FqNameUnsafe unit = fqNameUnsafe("Unit");
        public final FqNameUnsafe charSequence = fqNameUnsafe("CharSequence");
        public final FqNameUnsafe string = fqNameUnsafe("String");
        public final FqNameUnsafe array = fqNameUnsafe("Array");
        public final FqNameUnsafe _boolean = fqNameUnsafe("Boolean");
        public final FqNameUnsafe _char = fqNameUnsafe("Char");
        public final FqNameUnsafe _byte = fqNameUnsafe("Byte");
        public final FqNameUnsafe _short = fqNameUnsafe("Short");
        public final FqNameUnsafe _int = fqNameUnsafe("Int");
        public final FqNameUnsafe _long = fqNameUnsafe("Long");
        public final FqNameUnsafe _float = fqNameUnsafe("Float");
        public final FqNameUnsafe _double = fqNameUnsafe("Double");
        public final FqNameUnsafe number = fqNameUnsafe("Number");
        public final FqNameUnsafe _enum = fqNameUnsafe("Enum");
        public final FqNameUnsafe functionSupertype = fqNameUnsafe("Function");
        public final FqName throwable = fqName("Throwable");
        public final FqName comparable = fqName("Comparable");
        public final FqNameUnsafe charRange = rangesFqName("CharRange");
        public final FqNameUnsafe intRange = rangesFqName("IntRange");
        public final FqNameUnsafe longRange = rangesFqName("LongRange");
        public final FqName deprecated = fqName("Deprecated");
        public final FqName deprecationLevel = fqName("DeprecationLevel");
        public final FqName replaceWith = fqName("ReplaceWith");
        public final FqName extensionFunctionType = fqName("ExtensionFunctionType");
        public final FqName parameterName = fqName("ParameterName");
        public final FqName annotation = fqName("Annotation");
        public final FqName target = annotationName("Target");
        public final FqName annotationTarget = annotationName("AnnotationTarget");
        public final FqName annotationRetention = annotationName("AnnotationRetention");
        public final FqName retention = annotationName("Retention");
        public final FqName repeatable = annotationName("Repeatable");
        public final FqName mustBeDocumented = annotationName("MustBeDocumented");
        public final FqName unsafeVariance = fqName("UnsafeVariance");
        public final FqName publishedApi = fqName("PublishedApi");
        public final FqName iterator = collectionsFqName("Iterator");
        public final FqName iterable = collectionsFqName("Iterable");
        public final FqName collection = collectionsFqName("Collection");
        public final FqName list = collectionsFqName("List");
        public final FqName listIterator = collectionsFqName("ListIterator");
        public final FqName set = collectionsFqName("Set");
        public final FqName map = collectionsFqName("Map");
        public final FqName mapEntry = this.map.child(Name.identifier("Entry"));
        public final FqName mutableIterator = collectionsFqName("MutableIterator");
        public final FqName mutableIterable = collectionsFqName("MutableIterable");
        public final FqName mutableCollection = collectionsFqName("MutableCollection");
        public final FqName mutableList = collectionsFqName("MutableList");
        public final FqName mutableListIterator = collectionsFqName("MutableListIterator");
        public final FqName mutableSet = collectionsFqName("MutableSet");
        public final FqName mutableMap = collectionsFqName("MutableMap");
        public final FqName mutableMapEntry = this.mutableMap.child(Name.identifier("MutableEntry"));
        public final FqNameUnsafe kClass = reflect("KClass");
        public final FqNameUnsafe kCallable = reflect("KCallable");
        public final FqNameUnsafe kProperty0 = reflect("KProperty0");
        public final FqNameUnsafe kProperty1 = reflect("KProperty1");
        public final FqNameUnsafe kProperty2 = reflect("KProperty2");
        public final FqNameUnsafe kMutableProperty0 = reflect("KMutableProperty0");
        public final FqNameUnsafe kMutableProperty1 = reflect("KMutableProperty1");
        public final FqNameUnsafe kMutableProperty2 = reflect("KMutableProperty2");
        public final ClassId kProperty = ClassId.topLevel(reflect("KProperty").toSafe());
        public final FqName uByteFqName = fqName("UByte");
        public final FqName uShortFqName = fqName("UShort");
        public final FqName uIntFqName = fqName("UInt");
        public final FqName uLongFqName = fqName("ULong");
        public final ClassId uByte = ClassId.topLevel(this.uByteFqName);
        public final ClassId uShort = ClassId.topLevel(this.uShortFqName);
        public final ClassId uInt = ClassId.topLevel(this.uIntFqName);
        public final ClassId uLong = ClassId.topLevel(this.uLongFqName);
        public final Set<Name> primitiveTypeShortNames = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
        public final Set<Name> primitiveArrayTypeShortNames = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
        public final Map<FqNameUnsafe, PrimitiveType> fqNameToPrimitiveType = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
        public final Map<FqNameUnsafe, PrimitiveType> arrayClassFqNameToPrimitiveType = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);

        public FqNames() {
            PrimitiveType[] arr$ = PrimitiveType.values();
            for (PrimitiveType primitiveType : arr$) {
                this.primitiveTypeShortNames.add(primitiveType.getTypeName());
                this.primitiveArrayTypeShortNames.add(primitiveType.getArrayTypeName());
                this.fqNameToPrimitiveType.put(fqNameUnsafe(primitiveType.getTypeName().asString()), primitiveType);
                this.arrayClassFqNameToPrimitiveType.put(fqNameUnsafe(primitiveType.getArrayTypeName().asString()), primitiveType);
            }
        }

        @NotNull
        private static FqNameUnsafe fqNameUnsafe(@NotNull String simpleName) {
            return fqName(simpleName).toUnsafe();
        }

        @NotNull
        private static FqName fqName(@NotNull String simpleName) {
            return KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
        }

        @NotNull
        private static FqName collectionsFqName(@NotNull String simpleName) {
            return KotlinBuiltIns.COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
        }

        @NotNull
        private static FqNameUnsafe rangesFqName(@NotNull String simpleName) {
            return KotlinBuiltIns.RANGES_PACKAGE_FQ_NAME.child(Name.identifier(simpleName)).toUnsafe();
        }

        @NotNull
        private static FqNameUnsafe reflect(@NotNull String simpleName) {
            return ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME().child(Name.identifier(simpleName)).toUnsafe();
        }

        @NotNull
        private static FqName annotationName(@NotNull String simpleName) {
            return KotlinBuiltIns.ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(simpleName));
        }
    }

    @NotNull
    public ModuleDescriptorImpl getBuiltInsModule() {
        return this.builtInsModule;
    }

    @NotNull
    public PackageFragmentDescriptor getBuiltInsPackageFragment() {
        return this.packageFragments.invoke().builtInsPackageFragment;
    }

    public static boolean isBuiltIn(@NotNull DeclarationDescriptor descriptor) {
        return DescriptorUtils.getParentOfType(descriptor, BuiltInsPackageFragment.class, false) != null;
    }

    public static boolean isUnderKotlinPackage(@NotNull DeclarationDescriptor descriptor) {
        for (DeclarationDescriptor current = descriptor; current != null; current = current.getContainingDeclaration()) {
            if (current instanceof PackageFragmentDescriptor) {
                return ((PackageFragmentDescriptor) current).getFqName().startsWith(BUILT_INS_PACKAGE_NAME);
            }
        }
        return false;
    }

    @NotNull
    public ClassDescriptor getBuiltInClassByName(@NotNull Name simpleName) {
        return this.builtInClassesByName.invoke(simpleName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public static ClassDescriptor getBuiltInClassByName(@NotNull Name simpleName, @NotNull PackageFragmentDescriptor packageFragment) {
        ClassDescriptor classDescriptor = getBuiltInClassByNameNullable(simpleName, packageFragment);
        if (classDescriptor == null) {
            throw new AssertionError("Built-in class " + packageFragment.getFqName().child(simpleName).asString() + " is not found");
        }
        return classDescriptor;
    }

    @Nullable
    public ClassDescriptor getBuiltInClassByFqNameNullable(@NotNull FqName fqName) {
        return DescriptorUtilKt.resolveClassByFqName(this.builtInsModule, fqName, NoLookupLocation.FROM_BUILTINS);
    }

    @NotNull
    public ClassDescriptor getBuiltInClassByFqName(@NotNull FqName fqName) {
        ClassDescriptor descriptor = getBuiltInClassByFqNameNullable(fqName);
        return descriptor;
    }

    @Nullable
    private static ClassDescriptor getBuiltInClassByNameNullable(@NotNull Name simpleName, @NotNull PackageFragmentDescriptor packageFragment) {
        ClassifierDescriptor classifier = packageFragment.getMemberScope().mo382getContributedClassifier(simpleName, NoLookupLocation.FROM_BUILTINS);
        return (ClassDescriptor) classifier;
    }

    @NotNull
    private ClassDescriptor getBuiltInClassByName(@NotNull String simpleName) {
        return getBuiltInClassByName(Name.identifier(simpleName));
    }

    @NotNull
    private static ClassDescriptor getBuiltInClassByName(@NotNull String simpleName, PackageFragmentDescriptor packageFragment) {
        return getBuiltInClassByName(Name.identifier(simpleName), packageFragment);
    }

    @NotNull
    public ClassDescriptor getAny() {
        return getBuiltInClassByName("Any");
    }

    @NotNull
    public ClassDescriptor getNothing() {
        return getBuiltInClassByName("Nothing");
    }

    @NotNull
    private ClassDescriptor getPrimitiveClassDescriptor(@NotNull PrimitiveType type) {
        return getBuiltInClassByName(type.getTypeName().asString());
    }

    @NotNull
    public ClassDescriptor getArray() {
        return getBuiltInClassByName("Array");
    }

    @NotNull
    public ClassDescriptor getNumber() {
        return getBuiltInClassByName("Number");
    }

    @NotNull
    public ClassDescriptor getUnit() {
        return getBuiltInClassByName("Unit");
    }

    @NotNull
    public static String getFunctionName(int parameterCount) {
        return "Function" + parameterCount;
    }

    @NotNull
    public static ClassId getFunctionClassId(int parameterCount) {
        return new ClassId(BUILT_INS_PACKAGE_FQ_NAME, Name.identifier(getFunctionName(parameterCount)));
    }

    @NotNull
    public ClassDescriptor getFunction(int parameterCount) {
        return getBuiltInClassByName(getFunctionName(parameterCount));
    }

    @NotNull
    public ClassDescriptor getSuspendFunction(int parameterCount) {
        Name name = Name.identifier(FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix() + parameterCount);
        return getBuiltInClassByFqName(DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(name));
    }

    @NotNull
    public ClassDescriptor getString() {
        return getBuiltInClassByName("String");
    }

    @NotNull
    public ClassDescriptor getKClass() {
        return getBuiltInClassByFqName(FQ_NAMES.kClass.toSafe());
    }

    @NotNull
    private ClassDescriptor getCollectionClassByName(@NotNull String simpleName) {
        return getBuiltInClassByName(simpleName, this.packageFragments.invoke().collectionsPackageFragment);
    }

    @NotNull
    public ClassDescriptor getCollection() {
        return getCollectionClassByName("Collection");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NotNull
    public SimpleType getBuiltInTypeByClassName(@NotNull String classSimpleName) {
        return getBuiltInClassByName(classSimpleName).getDefaultType();
    }

    @NotNull
    public SimpleType getNothingType() {
        return getNothing().getDefaultType();
    }

    @NotNull
    public SimpleType getNullableNothingType() {
        return getNothingType().makeNullableAsSpecified(true);
    }

    @NotNull
    public SimpleType getAnyType() {
        return getAny().getDefaultType();
    }

    @NotNull
    public SimpleType getNullableAnyType() {
        return getAnyType().makeNullableAsSpecified(true);
    }

    @NotNull
    public SimpleType getDefaultBound() {
        return getNullableAnyType();
    }

    @NotNull
    public SimpleType getPrimitiveKotlinType(@NotNull PrimitiveType type) {
        return getPrimitiveClassDescriptor(type).getDefaultType();
    }

    @NotNull
    public SimpleType getByteType() {
        return getPrimitiveKotlinType(PrimitiveType.BYTE);
    }

    @NotNull
    public SimpleType getShortType() {
        return getPrimitiveKotlinType(PrimitiveType.SHORT);
    }

    @NotNull
    public SimpleType getIntType() {
        return getPrimitiveKotlinType(PrimitiveType.INT);
    }

    @NotNull
    public SimpleType getLongType() {
        return getPrimitiveKotlinType(PrimitiveType.LONG);
    }

    @NotNull
    public SimpleType getFloatType() {
        return getPrimitiveKotlinType(PrimitiveType.FLOAT);
    }

    @NotNull
    public SimpleType getDoubleType() {
        return getPrimitiveKotlinType(PrimitiveType.DOUBLE);
    }

    @NotNull
    public SimpleType getCharType() {
        return getPrimitiveKotlinType(PrimitiveType.CHAR);
    }

    @NotNull
    public SimpleType getBooleanType() {
        return getPrimitiveKotlinType(PrimitiveType.BOOLEAN);
    }

    @NotNull
    public SimpleType getUnitType() {
        return getUnit().getDefaultType();
    }

    @NotNull
    public SimpleType getStringType() {
        return getString().getDefaultType();
    }

    @NotNull
    public KotlinType getArrayElementType(@NotNull KotlinType arrayType) {
        KotlinType unsignedType;
        if (isArray(arrayType)) {
            if (arrayType.getArguments().size() != 1) {
                throw new IllegalStateException();
            }
            return arrayType.getArguments().get(0).getType();
        }
        KotlinType notNullArrayType = TypeUtils.makeNotNullable(arrayType);
        KotlinType primitiveType = this.primitives.invoke().kotlinArrayTypeToPrimitiveKotlinType.get(notNullArrayType);
        if (primitiveType != null) {
            return primitiveType;
        }
        ModuleDescriptor module = DescriptorUtils.getContainingModuleOrNull(notNullArrayType);
        if (module != null && (unsignedType = getElementTypeForUnsignedArray(notNullArrayType, module)) != null) {
            return unsignedType;
        }
        throw new IllegalStateException("not array: " + arrayType);
    }

    @Nullable
    private static KotlinType getElementTypeForUnsignedArray(@NotNull KotlinType notNullArrayType, @NotNull ModuleDescriptor module) {
        ClassId arrayClassId;
        ClassId elementClassId;
        ClassDescriptor elementClassDescriptor;
        ClassifierDescriptor descriptor = notNullArrayType.getConstructor().mo381getDeclarationDescriptor();
        if (descriptor == null || !UnsignedTypes.INSTANCE.isShortNameOfUnsignedArray(descriptor.getName()) || (arrayClassId = DescriptorUtilsKt.getClassId(descriptor)) == null || (elementClassId = UnsignedTypes.INSTANCE.getUnsignedClassIdByArrayClassId(arrayClassId)) == null || (elementClassDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, elementClassId)) == null) {
            return null;
        }
        return elementClassDescriptor.getDefaultType();
    }

    @NotNull
    public SimpleType getPrimitiveArrayKotlinType(@NotNull PrimitiveType primitiveType) {
        return this.primitives.invoke().primitiveTypeToArrayKotlinType.get(primitiveType);
    }

    @Nullable
    public SimpleType getPrimitiveArrayKotlinTypeByPrimitiveKotlinType(@NotNull KotlinType kotlinType) {
        ModuleDescriptor module;
        SimpleType primitiveArray = this.primitives.invoke().primitiveKotlinTypeToKotlinArrayType.get(kotlinType);
        if (primitiveArray != null) {
            return primitiveArray;
        }
        if (!UnsignedTypes.INSTANCE.isUnsignedType(kotlinType) || TypeUtils.isNullableType(kotlinType) || (module = DescriptorUtils.getContainingModuleOrNull(kotlinType)) == null) {
            return null;
        }
        ClassId unsignedClassId = DescriptorUtilsKt.getClassId(kotlinType.getConstructor().mo381getDeclarationDescriptor());
        ClassId arrayClassId = UnsignedTypes.INSTANCE.getUnsignedArrayClassIdByUnsignedClassId(unsignedClassId);
        ClassDescriptor arrayClassDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, arrayClassId);
        if (arrayClassDescriptor == null) {
            return null;
        }
        return arrayClassDescriptor.getDefaultType();
    }

    public static boolean isPrimitiveArray(@NotNull FqNameUnsafe arrayFqName) {
        return FQ_NAMES.arrayClassFqNameToPrimitiveType.get(arrayFqName) != null;
    }

    @Nullable
    public static PrimitiveType getPrimitiveType(@NotNull DeclarationDescriptor descriptor) {
        if (FQ_NAMES.primitiveTypeShortNames.contains(descriptor.getName())) {
            return FQ_NAMES.fqNameToPrimitiveType.get(DescriptorUtils.getFqName(descriptor));
        }
        return null;
    }

    @Nullable
    public static PrimitiveType getPrimitiveArrayType(@NotNull DeclarationDescriptor descriptor) {
        if (FQ_NAMES.primitiveArrayTypeShortNames.contains(descriptor.getName())) {
            return FQ_NAMES.arrayClassFqNameToPrimitiveType.get(DescriptorUtils.getFqName(descriptor));
        }
        return null;
    }

    @NotNull
    public SimpleType getArrayType(@NotNull Variance projectionType, @NotNull KotlinType argument) {
        List<TypeProjectionImpl> types = Collections.singletonList(new TypeProjectionImpl(projectionType, argument));
        return KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), getArray(), types);
    }

    public static boolean isArray(@NotNull KotlinType type) {
        return isConstructedFromGivenClass(type, FQ_NAMES.array);
    }

    public static boolean isArrayOrPrimitiveArray(@NotNull ClassDescriptor descriptor) {
        return classFqNameEquals(descriptor, FQ_NAMES.array) || getPrimitiveArrayType(descriptor) != null;
    }

    public static boolean isPrimitiveArray(@NotNull KotlinType type) {
        ClassifierDescriptor descriptor = type.getConstructor().mo381getDeclarationDescriptor();
        return (descriptor == null || getPrimitiveArrayType(descriptor) == null) ? false : true;
    }

    public static boolean isPrimitiveType(@NotNull KotlinType type) {
        return !type.isMarkedNullable() && isPrimitiveTypeOrNullablePrimitiveType(type);
    }

    public static boolean isPrimitiveTypeOrNullablePrimitiveType(@NotNull KotlinType type) {
        ClassifierDescriptor descriptor = type.getConstructor().mo381getDeclarationDescriptor();
        return (descriptor instanceof ClassDescriptor) && isPrimitiveClass((ClassDescriptor) descriptor);
    }

    public static boolean isPrimitiveClass(@NotNull ClassDescriptor descriptor) {
        return getPrimitiveType(descriptor) != null;
    }

    public static boolean isConstructedFromGivenClass(@NotNull KotlinType type, @NotNull FqNameUnsafe fqName) {
        ClassifierDescriptor descriptor = type.getConstructor().mo381getDeclarationDescriptor();
        return (descriptor instanceof ClassDescriptor) && classFqNameEquals(descriptor, fqName);
    }

    private static boolean classFqNameEquals(@NotNull ClassifierDescriptor descriptor, @NotNull FqNameUnsafe fqName) {
        return descriptor.getName().equals(fqName.shortName()) && fqName.equals(DescriptorUtils.getFqName(descriptor));
    }

    private static boolean isNotNullConstructedFromGivenClass(@NotNull KotlinType type, @NotNull FqNameUnsafe fqName) {
        return !type.isMarkedNullable() && isConstructedFromGivenClass(type, fqName);
    }

    public static boolean isSpecialClassWithNoSupertypes(@NotNull ClassDescriptor descriptor) {
        return classFqNameEquals(descriptor, FQ_NAMES.any) || classFqNameEquals(descriptor, FQ_NAMES.nothing);
    }

    public static boolean isAny(@NotNull ClassDescriptor descriptor) {
        return classFqNameEquals(descriptor, FQ_NAMES.any);
    }

    public static boolean isBoolean(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._boolean);
    }

    public static boolean isChar(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._char);
    }

    public static boolean isInt(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._int);
    }

    public static boolean isByte(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._byte);
    }

    public static boolean isLong(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._long);
    }

    public static boolean isShort(@NotNull KotlinType type) {
        return isConstructedFromGivenClassAndNotNullable(type, FQ_NAMES._short);
    }

    public static boolean isFloat(@NotNull KotlinType type) {
        return isFloatOrNullableFloat(type) && !type.isMarkedNullable();
    }

    public static boolean isFloatOrNullableFloat(@NotNull KotlinType type) {
        return isConstructedFromGivenClass(type, FQ_NAMES._float);
    }

    public static boolean isDouble(@NotNull KotlinType type) {
        return isDoubleOrNullableDouble(type) && !type.isMarkedNullable();
    }

    public static boolean isDoubleOrNullableDouble(@NotNull KotlinType type) {
        return isConstructedFromGivenClass(type, FQ_NAMES._double);
    }

    private static boolean isConstructedFromGivenClassAndNotNullable(@NotNull KotlinType type, @NotNull FqNameUnsafe fqName) {
        return isConstructedFromGivenClass(type, fqName) && !type.isMarkedNullable();
    }

    public static boolean isNothing(@NotNull KotlinType type) {
        return isNothingOrNullableNothing(type) && !TypeUtils.isNullableType(type);
    }

    public static boolean isNothingOrNullableNothing(@NotNull KotlinType type) {
        return isConstructedFromGivenClass(type, FQ_NAMES.nothing);
    }

    public static boolean isAnyOrNullableAny(@NotNull KotlinType type) {
        return isConstructedFromGivenClass(type, FQ_NAMES.any);
    }

    public static boolean isNullableAny(@NotNull KotlinType type) {
        return isAnyOrNullableAny(type) && type.isMarkedNullable();
    }

    public static boolean isDefaultBound(@NotNull KotlinType type) {
        return isNullableAny(type);
    }

    public static boolean isUnit(@NotNull KotlinType type) {
        return isNotNullConstructedFromGivenClass(type, FQ_NAMES.unit);
    }

    public static boolean isString(@Nullable KotlinType type) {
        return type != null && isNotNullConstructedFromGivenClass(type, FQ_NAMES.string);
    }

    public static boolean isKClass(@NotNull ClassDescriptor descriptor) {
        return classFqNameEquals(descriptor, FQ_NAMES.kClass);
    }

    public static boolean isDeprecated(@NotNull DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor.getOriginal().getAnnotations().hasAnnotation(FQ_NAMES.deprecated)) {
            return true;
        }
        if (!(declarationDescriptor instanceof PropertyDescriptor)) {
            return false;
        }
        boolean isVar = ((PropertyDescriptor) declarationDescriptor).isVar();
        PropertyGetterDescriptor getter = ((PropertyDescriptor) declarationDescriptor).getGetter();
        PropertySetterDescriptor setter = ((PropertyDescriptor) declarationDescriptor).getSetter();
        if (getter != null && isDeprecated(getter)) {
            if (!isVar) {
                return true;
            }
            if (setter != null && isDeprecated(setter)) {
                return true;
            }
        }
        return false;
    }

    public static FqName getPrimitiveFqName(@NotNull PrimitiveType primitiveType) {
        return BUILT_INS_PACKAGE_FQ_NAME.child(primitiveType.getTypeName());
    }
}
