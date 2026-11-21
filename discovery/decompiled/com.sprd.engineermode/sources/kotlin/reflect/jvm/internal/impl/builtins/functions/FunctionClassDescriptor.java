package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: FunctionClassDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FunctionClassDescriptor extends AbstractClassDescriptor {
    private final int arity;
    private final PackageFragmentDescriptor containingDeclaration;

    @NotNull
    private final Kind functionKind;
    private final FunctionClassScope memberScope;
    private final List<TypeParameterDescriptor> parameters;
    private final StorageManager storageManager;
    private final FunctionTypeConstructor typeConstructor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionClassDescriptor(@NotNull StorageManager storageManager, @NotNull PackageFragmentDescriptor containingDeclaration, @NotNull Kind functionKind, int arity) {
        super(storageManager, functionKind.numberedClassName(arity));
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(containingDeclaration, "containingDeclaration");
        Intrinsics.checkParameterIsNotNull(functionKind, "functionKind");
        this.storageManager = storageManager;
        this.containingDeclaration = containingDeclaration;
        this.functionKind = functionKind;
        this.arity = arity;
        this.typeConstructor = new FunctionTypeConstructor();
        this.memberScope = new FunctionClassScope(this.storageManager, this);
        final ArrayList result = new ArrayList();
        Function2<Variance, String, Unit> function2 = new Function2<Variance, String, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Variance variance, String str) {
                invoke2(variance, str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull Variance variance, @NotNull String name) {
                Intrinsics.checkParameterIsNotNull(variance, "variance");
                Intrinsics.checkParameterIsNotNull(name, "name");
                result.add(TypeParameterDescriptorImpl.createWithDefaultBound(FunctionClassDescriptor.this, Annotations.Companion.getEMPTY(), false, variance, Name.identifier(name), result.size()));
            }
        };
        Iterable $receiver$iv = new IntRange(1, this.arity);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        Iterator<Integer> it = $receiver$iv.iterator();
        while (it.hasNext()) {
            int item$iv$iv = ((IntIterator) it).nextInt();
            Variance variance = Variance.IN_VARIANCE;
            StringBuilder sb = new StringBuilder();
            sb.append('P');
            sb.append(item$iv$iv);
            function2.invoke2(variance, sb.toString());
            destination$iv$iv.add(Unit.INSTANCE);
        }
        function2.invoke2(Variance.OUT_VARIANCE, "R");
        this.parameters = CollectionsKt.toList(result);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassDescriptor mo372getCompanionObjectDescriptor() {
        return (ClassDescriptor) getCompanionObjectDescriptor();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassConstructorDescriptor mo373getUnsubstitutedPrimaryConstructor() {
        return (ClassConstructorDescriptor) getUnsubstitutedPrimaryConstructor();
    }

    @NotNull
    public final Kind getFunctionKind() {
        return this.functionKind;
    }

    public final int getArity() {
        return this.arity;
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'Function' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:422)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:351)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: FunctionClassDescriptor.kt */
    public static final class Kind {
        private static final /* synthetic */ Kind[] $VALUES;
        public static final Companion Companion;
        public static final Kind Function;
        public static final Kind KFunction;
        public static final Kind KSuspendFunction;
        public static final Kind SuspendFunction;

        @NotNull
        private final String classNamePrefix;

        @NotNull
        private final FqName packageFqName;

        public static Kind valueOf(String str) {
            return (Kind) Enum.valueOf(Kind.class, str);
        }

        public static Kind[] values() {
            return (Kind[]) $VALUES.clone();
        }

        private Kind(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, FqName packageFqName, String classNamePrefix) {
            super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
            this.packageFqName = packageFqName;
            this.classNamePrefix = classNamePrefix;
        }

        @NotNull
        public final String getClassNamePrefix() {
            return this.classNamePrefix;
        }

        @NotNull
        public final FqName getPackageFqName() {
            return this.packageFqName;
        }

        static {
            FqName BUILT_INS_PACKAGE_FQ_NAME = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
            Intrinsics.checkExpressionValueIsNotNull(BUILT_INS_PACKAGE_FQ_NAME, "BUILT_INS_PACKAGE_FQ_NAME");
            Kind kind = new Kind("Function", 0, BUILT_INS_PACKAGE_FQ_NAME, "Function");
            Function = kind;
            FqName COROUTINES_PACKAGE_FQ_NAME_RELEASE = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
            Intrinsics.checkExpressionValueIsNotNull(COROUTINES_PACKAGE_FQ_NAME_RELEASE, "COROUTINES_PACKAGE_FQ_NAME_RELEASE");
            Kind kind2 = new Kind("SuspendFunction", 1, COROUTINES_PACKAGE_FQ_NAME_RELEASE, "SuspendFunction");
            SuspendFunction = kind2;
            Kind kind3 = new Kind("KFunction", 2, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KFunction");
            KFunction = kind3;
            Kind kind4 = new Kind("KSuspendFunction", 3, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), "KSuspendFunction");
            KSuspendFunction = kind4;
            $VALUES = new Kind[]{kind, kind2, kind3, kind4};
            Companion = new Companion(null);
        }

        @NotNull
        public final Name numberedClassName(int arity) {
            Name nameIdentifier = Name.identifier(this.classNamePrefix + arity);
            Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(\"$classNamePrefix$arity\")");
            return nameIdentifier;
        }

        /* JADX INFO: compiled from: FunctionClassDescriptor.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @Nullable
            public final Kind byClassNamePrefix(@NotNull FqName packageFqName, @NotNull String className) {
                Intrinsics.checkParameterIsNotNull(packageFqName, "packageFqName");
                Intrinsics.checkParameterIsNotNull(className, "className");
                for (Kind kind : Kind.values()) {
                    if (Intrinsics.areEqual(kind.getPackageFqName(), packageFqName) && StringsKt.startsWith$default(className, kind.getClassNamePrefix(), false, 2, (Object) null)) {
                        return kind;
                    }
                }
                return null;
            }
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor
    @NotNull
    public PackageFragmentDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public MemberScope.Empty getStaticScope() {
        return MemberScope.Empty.INSTANCE;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.typeConstructor;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public FunctionClassScope getUnsubstitutedMemberScope() {
        return this.memberScope;
    }

    @Nullable
    public Void getCompanionObjectDescriptor() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public List<ClassConstructorDescriptor> getConstructors() {
        return CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public ClassKind getKind() {
        return ClassKind.INTERFACE;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    @NotNull
    public Modality getModality() {
        return Modality.ABSTRACT;
    }

    @Nullable
    public Void getUnsubstitutedPrimaryConstructor() {
        return null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility, kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = Visibilities.PUBLIC;
        Intrinsics.checkExpressionValueIsNotNull(visibility, "Visibilities.PUBLIC");
        return visibility;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    public boolean isCompanionObject() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
    public boolean isInner() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    public boolean isData() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    public boolean isInline() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExpect() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isActual() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor
    public boolean isExternal() {
        return false;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkExpressionValueIsNotNull(sourceElement, "SourceElement.NO_SOURCE");
        return sourceElement;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
    @NotNull
    public List<ClassDescriptor> getSealedSubclasses() {
        return CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        return this.parameters;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FunctionClassDescriptor.kt */
    final class FunctionTypeConstructor extends AbstractClassTypeConstructor {

        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[Kind.values().length];
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;

            static {
                $EnumSwitchMapping$0[Kind.SuspendFunction.ordinal()] = 1;
                $EnumSwitchMapping$0[Kind.KSuspendFunction.ordinal()] = 2;
                $EnumSwitchMapping$1 = new int[Kind.values().length];
                $EnumSwitchMapping$1[Kind.KFunction.ordinal()] = 1;
                $EnumSwitchMapping$1[Kind.KSuspendFunction.ordinal()] = 2;
            }
        }

        public FunctionTypeConstructor() {
            super(FunctionClassDescriptor.this.storageManager);
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
        @NotNull
        protected Collection<KotlinType> computeSupertypes() {
            final ArrayList result = new ArrayList(2);
            Function2<PackageFragmentDescriptor, Name, Unit> function2 = new Function2<PackageFragmentDescriptor, Name, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor$FunctionTypeConstructor$computeSupertypes$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(PackageFragmentDescriptor packageFragmentDescriptor, Name name) {
                    invoke2(packageFragmentDescriptor, name);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull PackageFragmentDescriptor packageFragment, @NotNull Name name) {
                    Intrinsics.checkParameterIsNotNull(packageFragment, "packageFragment");
                    Intrinsics.checkParameterIsNotNull(name, "name");
                    ClassifierDescriptor contributedClassifier = packageFragment.getMemberScope().mo382getContributedClassifier(name, NoLookupLocation.FROM_BUILTINS);
                    if (!(contributedClassifier instanceof ClassDescriptor)) {
                        contributedClassifier = null;
                    }
                    ClassDescriptor descriptor = (ClassDescriptor) contributedClassifier;
                    if (descriptor == null) {
                        throw new IllegalStateException(("Class " + name + " not found in " + packageFragment).toString());
                    }
                    TypeConstructor typeConstructor = descriptor.getTypeConstructor();
                    Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "descriptor.typeConstructor");
                    Iterable $receiver$iv = CollectionsKt.takeLast(this.this$0.getParameters(), typeConstructor.getParameters().size());
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                    for (Object item$iv$iv : $receiver$iv) {
                        TypeParameterDescriptor it = (TypeParameterDescriptor) item$iv$iv;
                        destination$iv$iv.add(new TypeProjectionImpl(it.getDefaultType()));
                    }
                    List arguments = (List) destination$iv$iv;
                    result.add(KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), descriptor, arguments));
                }
            };
            int i = WhenMappings.$EnumSwitchMapping$0[FunctionClassDescriptor.this.getFunctionKind().ordinal()];
            if (i == 1) {
                FqName BUILT_INS_PACKAGE_FQ_NAME = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
                Intrinsics.checkExpressionValueIsNotNull(BUILT_INS_PACKAGE_FQ_NAME, "BUILT_INS_PACKAGE_FQ_NAME");
                BuiltInsPackageFragment builtInPackage = getBuiltInPackage(BUILT_INS_PACKAGE_FQ_NAME);
                Name nameIdentifier = Name.identifier("Function");
                Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(\"Function\")");
                function2.invoke2((PackageFragmentDescriptor) builtInPackage, nameIdentifier);
            } else if (i != 2) {
                PackageFragmentDescriptor packageFragmentDescriptor = FunctionClassDescriptor.this.containingDeclaration;
                Name nameIdentifier2 = Name.identifier(FunctionClassDescriptor.this.getFunctionKind().getClassNamePrefix());
                Intrinsics.checkExpressionValueIsNotNull(nameIdentifier2, "Name.identifier(functionKind.classNamePrefix)");
                function2.invoke2(packageFragmentDescriptor, nameIdentifier2);
            } else {
                PackageFragmentDescriptor packageFragmentDescriptor2 = FunctionClassDescriptor.this.containingDeclaration;
                Name nameIdentifier3 = Name.identifier("KFunction");
                Intrinsics.checkExpressionValueIsNotNull(nameIdentifier3, "Name.identifier(\"KFunction\")");
                function2.invoke2(packageFragmentDescriptor2, nameIdentifier3);
            }
            int i2 = WhenMappings.$EnumSwitchMapping$1[FunctionClassDescriptor.this.getFunctionKind().ordinal()];
            if (i2 == 1) {
                FqName BUILT_INS_PACKAGE_FQ_NAME2 = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME;
                Intrinsics.checkExpressionValueIsNotNull(BUILT_INS_PACKAGE_FQ_NAME2, "BUILT_INS_PACKAGE_FQ_NAME");
                function2.invoke2((PackageFragmentDescriptor) getBuiltInPackage(BUILT_INS_PACKAGE_FQ_NAME2), Kind.Function.numberedClassName(FunctionClassDescriptor.this.getArity()));
            } else if (i2 == 2) {
                FqName COROUTINES_PACKAGE_FQ_NAME_RELEASE = DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE;
                Intrinsics.checkExpressionValueIsNotNull(COROUTINES_PACKAGE_FQ_NAME_RELEASE, "COROUTINES_PACKAGE_FQ_NAME_RELEASE");
                function2.invoke2((PackageFragmentDescriptor) getBuiltInPackage(COROUTINES_PACKAGE_FQ_NAME_RELEASE), Kind.SuspendFunction.numberedClassName(FunctionClassDescriptor.this.getArity()));
            }
            return CollectionsKt.toList(result);
        }

        private final BuiltInsPackageFragment getBuiltInPackage(FqName fqName) {
            PackageViewDescriptor packageView = FunctionClassDescriptor.this.containingDeclaration.getContainingDeclaration().getPackage(fqName);
            Iterable $receiver$iv = packageView.getFragments();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv) {
                if (element$iv$iv instanceof BuiltInsPackageFragment) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            return (BuiltInsPackageFragment) CollectionsKt.first((List) destination$iv$iv);
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            return FunctionClassDescriptor.this.parameters;
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor, kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        @NotNull
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public FunctionClassDescriptor mo381getDeclarationDescriptor() {
            return FunctionClassDescriptor.this;
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
        public boolean isDenotable() {
            return true;
        }

        @NotNull
        public String toString() {
            return mo381getDeclarationDescriptor().toString();
        }

        @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
        @NotNull
        protected SupertypeLoopChecker getSupertypeLoopChecker() {
            return SupertypeLoopChecker.EMPTY.INSTANCE;
        }
    }

    @NotNull
    public String toString() {
        String strAsString = getName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "name.asString()");
        return strAsString;
    }
}
