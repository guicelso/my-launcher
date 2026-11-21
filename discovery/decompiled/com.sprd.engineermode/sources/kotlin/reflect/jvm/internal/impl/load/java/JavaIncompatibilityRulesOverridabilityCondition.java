package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class JavaIncompatibilityRulesOverridabilityCondition implements ExternalOverridabilityCondition {
    public static final Companion Companion = new Companion(null);

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        Intrinsics.checkParameterIsNotNull(superDescriptor, "superDescriptor");
        Intrinsics.checkParameterIsNotNull(subDescriptor, "subDescriptor");
        if (isIncompatibleInAccordanceWithBuiltInOverridabilityRules(superDescriptor, subDescriptor, subClassDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        if (Companion.doesJavaOverrideHaveIncompatibleValueParameterKinds(superDescriptor, subDescriptor)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    private final boolean isIncompatibleInAccordanceWithBuiltInOverridabilityRules(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        if (!(superDescriptor instanceof CallableMemberDescriptor) || !(subDescriptor instanceof FunctionDescriptor) || KotlinBuiltIns.isBuiltIn(subDescriptor)) {
            return false;
        }
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
        Name name = ((FunctionDescriptor) subDescriptor).getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "subDescriptor.name");
        if (!builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            BuiltinMethodsWithDifferentJvmName builtinMethodsWithDifferentJvmName = BuiltinMethodsWithDifferentJvmName.INSTANCE;
            Name name2 = ((FunctionDescriptor) subDescriptor).getName();
            Intrinsics.checkExpressionValueIsNotNull(name2, "subDescriptor.name");
            if (!builtinMethodsWithDifferentJvmName.getSameAsRenamedInJvmBuiltin(name2)) {
                return false;
            }
        }
        CallableMemberDescriptor overriddenBuiltin = SpecialBuiltinMembers.getOverriddenSpecialBuiltin((CallableMemberDescriptor) superDescriptor);
        boolean zIsHiddenToOvercomeSignatureClash = ((FunctionDescriptor) subDescriptor).isHiddenToOvercomeSignatureClash();
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) (!(superDescriptor instanceof FunctionDescriptor) ? null : superDescriptor);
        boolean isOneOfDescriptorsHidden = functionDescriptor == null || zIsHiddenToOvercomeSignatureClash != functionDescriptor.isHiddenToOvercomeSignatureClash();
        if (isOneOfDescriptorsHidden && (overriddenBuiltin == null || !((FunctionDescriptor) subDescriptor).isHiddenToOvercomeSignatureClash())) {
            return true;
        }
        if (!(subClassDescriptor instanceof JavaClassDescriptor) || ((FunctionDescriptor) subDescriptor).getInitialSignatureDescriptor() != null || overriddenBuiltin == null || SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(subClassDescriptor, overriddenBuiltin)) {
            return false;
        }
        if ((overriddenBuiltin instanceof FunctionDescriptor) && (superDescriptor instanceof FunctionDescriptor) && BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor) overriddenBuiltin) != null) {
            String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default((FunctionDescriptor) subDescriptor, false, false, 2, null);
            FunctionDescriptor original = ((FunctionDescriptor) superDescriptor).getOriginal();
            Intrinsics.checkExpressionValueIsNotNull(original, "superDescriptor.original");
            if (Intrinsics.areEqual(strComputeJvmDescriptor$default, MethodSignatureMappingKt.computeJvmDescriptor$default(original, false, false, 2, null))) {
                return false;
            }
        }
        return true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY;
    }

    /* JADX INFO: compiled from: JavaIncompatibilityRulesOverridabilityCondition.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final boolean doesJavaOverrideHaveIncompatibleValueParameterKinds(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor) {
            Intrinsics.checkParameterIsNotNull(superDescriptor, "superDescriptor");
            Intrinsics.checkParameterIsNotNull(subDescriptor, "subDescriptor");
            if (!(subDescriptor instanceof JavaMethodDescriptor) || !(superDescriptor instanceof FunctionDescriptor)) {
                return false;
            }
            boolean z = ((JavaMethodDescriptor) subDescriptor).getValueParameters().size() == ((FunctionDescriptor) superDescriptor).getValueParameters().size();
            if (_Assertions.ENABLED && !z) {
                throw new AssertionError("External overridability condition with CONFLICTS_ONLY should not be run with different value parameters size");
            }
            SimpleFunctionDescriptor original = ((JavaMethodDescriptor) subDescriptor).getOriginal();
            Intrinsics.checkExpressionValueIsNotNull(original, "subDescriptor.original");
            List<ValueParameterDescriptor> valueParameters = original.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "subDescriptor.original.valueParameters");
            FunctionDescriptor original2 = ((FunctionDescriptor) superDescriptor).getOriginal();
            Intrinsics.checkExpressionValueIsNotNull(original2, "superDescriptor.original");
            List<ValueParameterDescriptor> valueParameters2 = original2.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "superDescriptor.original.valueParameters");
            for (Pair pair : CollectionsKt.zip(valueParameters, valueParameters2)) {
                ValueParameterDescriptor subParameter = (ValueParameterDescriptor) pair.component1();
                ValueParameterDescriptor superParameter = (ValueParameterDescriptor) pair.component2();
                Intrinsics.checkExpressionValueIsNotNull(subParameter, "subParameter");
                boolean isSubPrimitive = mapValueParameterType((FunctionDescriptor) subDescriptor, subParameter) instanceof JvmType.Primitive;
                Intrinsics.checkExpressionValueIsNotNull(superParameter, "superParameter");
                boolean isSuperPrimitive = mapValueParameterType((FunctionDescriptor) superDescriptor, superParameter) instanceof JvmType.Primitive;
                if (isSubPrimitive != isSuperPrimitive) {
                    return true;
                }
            }
            return false;
        }

        private final JvmType mapValueParameterType(FunctionDescriptor f, ValueParameterDescriptor valueParameterDescriptor) {
            if (MethodSignatureMappingKt.forceSingleValueParameterBoxing(f) || isPrimitiveCompareTo(f)) {
                KotlinType type = valueParameterDescriptor.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "valueParameterDescriptor.type");
                return MethodSignatureMappingKt.mapToJvmType(TypeUtilsKt.makeNullable(type));
            }
            KotlinType type2 = valueParameterDescriptor.getType();
            Intrinsics.checkExpressionValueIsNotNull(type2, "valueParameterDescriptor.type");
            return MethodSignatureMappingKt.mapToJvmType(type2);
        }

        private final boolean isPrimitiveCompareTo(FunctionDescriptor f) {
            if (f.getValueParameters().size() != 1) {
                return false;
            }
            DeclarationDescriptor containingDeclaration = f.getContainingDeclaration();
            if (!(containingDeclaration instanceof ClassDescriptor)) {
                containingDeclaration = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
            if (classDescriptor == null) {
                return false;
            }
            List<ValueParameterDescriptor> valueParameters = f.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "f.valueParameters");
            Object objSingle = CollectionsKt.single((List<? extends Object>) valueParameters);
            Intrinsics.checkExpressionValueIsNotNull(objSingle, "f.valueParameters.single()");
            ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = ((ValueParameterDescriptor) objSingle).getType().getConstructor().mo381getDeclarationDescriptor();
            if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor)) {
                classifierDescriptorMo381getDeclarationDescriptor = null;
            }
            ClassDescriptor parameterClass = (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
            if (parameterClass != null) {
                return KotlinBuiltIns.isPrimitiveClass(classDescriptor) && Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), DescriptorUtilsKt.getFqNameSafe(parameterClass));
            }
            return false;
        }
    }
}
