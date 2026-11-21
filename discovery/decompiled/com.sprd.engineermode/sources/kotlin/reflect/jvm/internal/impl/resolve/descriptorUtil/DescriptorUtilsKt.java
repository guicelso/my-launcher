package kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DescriptorUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorUtilsKt {
    private static final Name RETENTION_PARAMETER_NAME;

    static {
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(\"value\")");
        RETENTION_PARAMETER_NAME = nameIdentifier;
    }

    @NotNull
    public static final FqNameUnsafe getFqNameUnsafe(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        FqNameUnsafe fqName = DescriptorUtils.getFqName(receiver$0);
        Intrinsics.checkExpressionValueIsNotNull(fqName, "DescriptorUtils.getFqName(this)");
        return fqName;
    }

    @NotNull
    public static final FqName getFqNameSafe(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        FqName fqNameSafe = DescriptorUtils.getFqNameSafe(receiver$0);
        Intrinsics.checkExpressionValueIsNotNull(fqNameSafe, "DescriptorUtils.getFqNameSafe(this)");
        return fqNameSafe;
    }

    @NotNull
    public static final ModuleDescriptor getModule(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(receiver$0);
        Intrinsics.checkExpressionValueIsNotNull(containingModule, "DescriptorUtils.getContainingModule(this)");
        return containingModule;
    }

    @Nullable
    public static final ClassDescriptor resolveTopLevelClass(@NotNull ModuleDescriptor receiver$0, @NotNull FqName topLevelClassFqName, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(topLevelClassFqName, "topLevelClassFqName");
        Intrinsics.checkParameterIsNotNull(location, "location");
        boolean z = !topLevelClassFqName.isRoot();
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
        FqName fqNameParent = topLevelClassFqName.parent();
        Intrinsics.checkExpressionValueIsNotNull(fqNameParent, "topLevelClassFqName.parent()");
        MemberScope memberScope = receiver$0.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = topLevelClassFqName.shortName();
        Intrinsics.checkExpressionValueIsNotNull(nameShortName, "topLevelClassFqName.shortName()");
        ClassifierDescriptor contributedClassifier = memberScope.mo382getContributedClassifier(nameShortName, location);
        if (!(contributedClassifier instanceof ClassDescriptor)) {
            contributedClassifier = null;
        }
        return (ClassDescriptor) contributedClassifier;
    }

    @Nullable
    public static final ClassId getClassId(@Nullable ClassifierDescriptor $receiver) {
        DeclarationDescriptor owner;
        ClassId classId;
        if ($receiver == null || (owner = $receiver.getContainingDeclaration()) == null) {
            return null;
        }
        if (owner instanceof PackageFragmentDescriptor) {
            return new ClassId(((PackageFragmentDescriptor) owner).getFqName(), $receiver.getName());
        }
        if (!(owner instanceof ClassifierDescriptorWithTypeParameters) || (classId = getClassId((ClassifierDescriptor) owner)) == null) {
            return null;
        }
        return classId.createNestedClassId($receiver.getName());
    }

    @Nullable
    public static final ClassDescriptor getSuperClassNotAny(@NotNull ClassDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        for (KotlinType supertype : receiver$0.getDefaultType().getConstructor().getSupertypes()) {
            if (!KotlinBuiltIns.isAnyOrNullableAny(supertype)) {
                ClassifierDescriptor superClassifier = supertype.getConstructor().mo381getDeclarationDescriptor();
                if (DescriptorUtils.isClassOrEnumClass(superClassifier)) {
                    if (superClassifier != null) {
                        return (ClassDescriptor) superClassifier;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                }
            }
        }
        return null;
    }

    @NotNull
    public static final KotlinBuiltIns getBuiltIns(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return getModule(receiver$0).getBuiltIns();
    }

    public static final boolean declaresOrInheritsDefaultValue(@NotNull ValueParameterDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(receiver$0), new DFS.Neighbors<N>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.declaresOrInheritsDefaultValue.1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            @NotNull
            public final List<ValueParameterDescriptor> getNeighbors(ValueParameterDescriptor current) {
                Intrinsics.checkExpressionValueIsNotNull(current, "current");
                Iterable $receiver$iv = current.getOverriddenDescriptors();
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    ValueParameterDescriptor p1 = (ValueParameterDescriptor) item$iv$iv;
                    destination$iv$iv.add(p1.getOriginal());
                }
                return (List) destination$iv$iv;
            }
        }, AnonymousClass2.INSTANCE);
        Intrinsics.checkExpressionValueIsNotNull(boolIfAny, "DFS.ifAny(\n        listO…eclaresDefaultValue\n    )");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$declaresOrInheritsDefaultValue$2, reason: invalid class name */
    /* JADX INFO: compiled from: DescriptorUtils.kt */
    static final class AnonymousClass2 extends FunctionReference implements Function1<ValueParameterDescriptor, Boolean> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "declaresDefaultValue";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(ValueParameterDescriptor.class);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "declaresDefaultValue()Z";
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Boolean invoke(ValueParameterDescriptor valueParameterDescriptor) {
            return Boolean.valueOf(invoke2(valueParameterDescriptor));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2(@NotNull ValueParameterDescriptor p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return p1.declaresDefaultValue();
        }
    }

    @NotNull
    public static final Sequence<DeclarationDescriptor> getParentsWithSelf(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return SequencesKt.generateSequence(receiver$0, new Function1<DeclarationDescriptor, DeclarationDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$parentsWithSelf$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final DeclarationDescriptor invoke(@NotNull DeclarationDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.getContainingDeclaration();
            }
        });
    }

    @NotNull
    public static final Sequence<DeclarationDescriptor> getParents(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return SequencesKt.drop(getParentsWithSelf(receiver$0), 1);
    }

    @NotNull
    public static final CallableMemberDescriptor getPropertyIfAccessor(@NotNull CallableMemberDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (!(receiver$0 instanceof PropertyAccessorDescriptor)) {
            return receiver$0;
        }
        PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) receiver$0).getCorrespondingProperty();
        Intrinsics.checkExpressionValueIsNotNull(correspondingProperty, "correspondingProperty");
        return correspondingProperty;
    }

    @Nullable
    public static final FqName fqNameOrNull(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        FqNameUnsafe it = getFqNameUnsafe(receiver$0);
        if (!it.isSafe()) {
            it = null;
        }
        if (it != null) {
            return it.toSafe();
        }
        return null;
    }

    @Nullable
    public static /* synthetic */ CallableMemberDescriptor firstOverridden$default(CallableMemberDescriptor callableMemberDescriptor, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return firstOverridden(callableMemberDescriptor, z, function1);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor] */
    @Nullable
    public static final CallableMemberDescriptor firstOverridden(@NotNull CallableMemberDescriptor receiver$0, final boolean useOriginal, @NotNull final Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        final Ref.ObjectRef result = new Ref.ObjectRef();
        result.element = (CallableMemberDescriptor) 0;
        return (CallableMemberDescriptor) DFS.dfs(CollectionsKt.listOf(receiver$0), new DFS.Neighbors<N>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.firstOverridden.1
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.Neighbors
            @NotNull
            public final Iterable<CallableMemberDescriptor> getNeighbors(CallableMemberDescriptor current) {
                CallableMemberDescriptor descriptor;
                Collection<? extends CallableMemberDescriptor> collectionEmptyList;
                if (useOriginal) {
                    descriptor = current != null ? current.getOriginal() : null;
                } else {
                    descriptor = current;
                }
                if (descriptor == null || (collectionEmptyList = descriptor.getOverriddenDescriptors()) == null) {
                    collectionEmptyList = CollectionsKt.emptyList();
                }
                return collectionEmptyList;
            }
        }, new DFS.AbstractNodeHandler<CallableMemberDescriptor, CallableMemberDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.firstOverridden.2
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public boolean beforeChildren(@NotNull CallableMemberDescriptor current) {
                Intrinsics.checkParameterIsNotNull(current, "current");
                return ((CallableMemberDescriptor) result.element) == null;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.AbstractNodeHandler, kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            public void afterChildren(@NotNull CallableMemberDescriptor current) {
                Intrinsics.checkParameterIsNotNull(current, "current");
                if (((CallableMemberDescriptor) result.element) == null && ((Boolean) predicate.invoke(current)).booleanValue()) {
                    result.element = current;
                }
            }

            @Override // kotlin.reflect.jvm.internal.impl.utils.DFS.NodeHandler
            @Nullable
            public CallableMemberDescriptor result() {
                return (CallableMemberDescriptor) result.element;
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt$computeSealedSubclasses$1] */
    @NotNull
    public static final Collection<ClassDescriptor> computeSealedSubclasses(@NotNull final ClassDescriptor sealedClass) {
        Intrinsics.checkParameterIsNotNull(sealedClass, "sealedClass");
        if (sealedClass.getModality() != Modality.SEALED) {
            return CollectionsKt.emptyList();
        }
        final LinkedHashSet result = new LinkedHashSet();
        ?? r1 = new Function2<MemberScope, Boolean, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.computeSealedSubclasses.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(MemberScope memberScope, Boolean bool) {
                invoke(memberScope, bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(@NotNull MemberScope scope, boolean collectNested) {
                Intrinsics.checkParameterIsNotNull(scope, "scope");
                for (DeclarationDescriptor descriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(scope, DescriptorKindFilter.CLASSIFIERS, null, 2, null)) {
                    if (descriptor instanceof ClassDescriptor) {
                        if (DescriptorUtils.isDirectSubclass((ClassDescriptor) descriptor, sealedClass)) {
                            result.add(descriptor);
                        }
                        if (collectNested) {
                            MemberScope unsubstitutedInnerClassesScope = ((ClassDescriptor) descriptor).getUnsubstitutedInnerClassesScope();
                            Intrinsics.checkExpressionValueIsNotNull(unsubstitutedInnerClassesScope, "descriptor.unsubstitutedInnerClassesScope");
                            invoke(unsubstitutedInnerClassesScope, collectNested);
                        }
                    }
                }
            }
        };
        DeclarationDescriptor container = sealedClass.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(container, "sealedClass.containingDeclaration");
        if (container instanceof PackageFragmentDescriptor) {
            r1.invoke(((PackageFragmentDescriptor) container).getMemberScope(), false);
        }
        MemberScope unsubstitutedInnerClassesScope = sealedClass.getUnsubstitutedInnerClassesScope();
        Intrinsics.checkExpressionValueIsNotNull(unsubstitutedInnerClassesScope, "sealedClass.unsubstitutedInnerClassesScope");
        r1.invoke(unsubstitutedInnerClassesScope, true);
        return result;
    }

    @Nullable
    public static final ClassDescriptor getAnnotationClass(@NotNull AnnotationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getType().getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        return (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
    }

    @Nullable
    public static final ConstantValue<?> firstArgument(@NotNull AnnotationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return (ConstantValue) CollectionsKt.firstOrNull(receiver$0.getAllValueArguments().values());
    }
}
