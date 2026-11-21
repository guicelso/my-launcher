package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DescriptorEquivalenceForOverrides.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorEquivalenceForOverrides {
    public static final DescriptorEquivalenceForOverrides INSTANCE = new DescriptorEquivalenceForOverrides();

    private DescriptorEquivalenceForOverrides() {
    }

    public final boolean areEquivalent(@Nullable DeclarationDescriptor a, @Nullable DeclarationDescriptor b) {
        if ((a instanceof ClassDescriptor) && (b instanceof ClassDescriptor)) {
            return areClassesEquivalent((ClassDescriptor) a, (ClassDescriptor) b);
        }
        if ((a instanceof TypeParameterDescriptor) && (b instanceof TypeParameterDescriptor)) {
            return areTypeParametersEquivalent$default(this, (TypeParameterDescriptor) a, (TypeParameterDescriptor) b, null, 4, null);
        }
        if ((a instanceof CallableDescriptor) && (b instanceof CallableDescriptor)) {
            return areCallableDescriptorsEquivalent$default(this, (CallableDescriptor) a, (CallableDescriptor) b, false, 4, null);
        }
        return ((a instanceof PackageFragmentDescriptor) && (b instanceof PackageFragmentDescriptor)) ? Intrinsics.areEqual(((PackageFragmentDescriptor) a).getFqName(), ((PackageFragmentDescriptor) b).getFqName()) : Intrinsics.areEqual(a, b);
    }

    private final boolean areClassesEquivalent(ClassDescriptor a, ClassDescriptor b) {
        return Intrinsics.areEqual(a.getTypeConstructor(), b.getTypeConstructor());
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean areTypeParametersEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            function2 = new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides.areTypeParametersEquivalent.1
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                    return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final boolean invoke2(@Nullable DeclarationDescriptor $noName_0, @Nullable DeclarationDescriptor $noName_1) {
                    return false;
                }
            };
        }
        return descriptorEquivalenceForOverrides.areTypeParametersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean areTypeParametersEquivalent(TypeParameterDescriptor a, TypeParameterDescriptor b, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> function2) {
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        return !Intrinsics.areEqual(a.getContainingDeclaration(), b.getContainingDeclaration()) && ownersEquivalent(a, b, function2) && a.getIndex() == b.getIndex();
    }

    public static /* synthetic */ boolean areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return descriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent(callableDescriptor, callableDescriptor2, z);
    }

    public final boolean areCallableDescriptorsEquivalent(@NotNull final CallableDescriptor a, @NotNull final CallableDescriptor b, boolean ignoreReturnType) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (Intrinsics.areEqual(a, b)) {
            return true;
        }
        if ((!Intrinsics.areEqual(a.getName(), b.getName())) || Intrinsics.areEqual(a.getContainingDeclaration(), b.getContainingDeclaration()) || DescriptorUtils.isLocal(a) || DescriptorUtils.isLocal(b) || !ownersEquivalent(a, b, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent.1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@Nullable DeclarationDescriptor $noName_0, @Nullable DeclarationDescriptor $noName_1) {
                return false;
            }
        })) {
            return false;
        }
        OverridingUtil overridingUtil = OverridingUtil.createWithEqualityAxioms(new KotlinTypeChecker.TypeConstructorEquality() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1
            @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality
            /* JADX INFO: renamed from: invoke, reason: merged with bridge method [inline-methods] */
            public final boolean equals(@NotNull TypeConstructor c1, @NotNull TypeConstructor c2) {
                Intrinsics.checkParameterIsNotNull(c1, "c1");
                Intrinsics.checkParameterIsNotNull(c2, "c2");
                if (Intrinsics.areEqual(c1, c2)) {
                    return true;
                }
                ClassifierDescriptor d1 = c1.mo381getDeclarationDescriptor();
                ClassifierDescriptor d2 = c2.mo381getDeclarationDescriptor();
                if ((d1 instanceof TypeParameterDescriptor) && (d2 instanceof TypeParameterDescriptor)) {
                    return DescriptorEquivalenceForOverrides.INSTANCE.areTypeParametersEquivalent((TypeParameterDescriptor) d1, (TypeParameterDescriptor) d2, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1.1
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public /* bridge */ /* synthetic */ Boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                            return Boolean.valueOf(invoke2(declarationDescriptor, declarationDescriptor2));
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final boolean invoke2(@Nullable DeclarationDescriptor x, @Nullable DeclarationDescriptor y) {
                            return Intrinsics.areEqual(x, a) && Intrinsics.areEqual(y, b);
                        }
                    });
                }
                return false;
            }
        });
        Intrinsics.checkExpressionValueIsNotNull(overridingUtil, "OverridingUtil.createWit…= a && y == b})\n        }");
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIsOverridableBy = overridingUtil.isOverridableBy(a, b, null, !ignoreReturnType);
        Intrinsics.checkExpressionValueIsNotNull(overrideCompatibilityInfoIsOverridableBy, "overridingUtil.isOverrid… null, !ignoreReturnType)");
        if (overrideCompatibilityInfoIsOverridableBy.getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIsOverridableBy2 = overridingUtil.isOverridableBy(b, a, null, !ignoreReturnType);
            Intrinsics.checkExpressionValueIsNotNull(overrideCompatibilityInfoIsOverridableBy2, "overridingUtil.isOverrid… null, !ignoreReturnType)");
            if (overrideCompatibilityInfoIsOverridableBy2.getResult() == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                return true;
            }
        }
        return false;
    }

    private final boolean ownersEquivalent(DeclarationDescriptor a, DeclarationDescriptor b, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> function2) {
        DeclarationDescriptor aOwner = a.getContainingDeclaration();
        DeclarationDescriptor bOwner = b.getContainingDeclaration();
        if ((aOwner instanceof CallableMemberDescriptor) || (bOwner instanceof CallableMemberDescriptor)) {
            return function2.invoke(aOwner, bOwner).booleanValue();
        }
        return areEquivalent(aOwner, bOwner);
    }
}
