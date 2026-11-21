package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class MethodSignatureMappingKt {
    @NotNull
    public static final String computeJvmDescriptor(@NotNull FunctionDescriptor receiver$0, boolean withReturnType, boolean withName) {
        String strAsString;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        StringBuilder $receiver = new StringBuilder();
        if (withName) {
            if (receiver$0 instanceof ConstructorDescriptor) {
                strAsString = "<init>";
            } else {
                strAsString = receiver$0.getName().asString();
                Intrinsics.checkExpressionValueIsNotNull(strAsString, "name.asString()");
            }
            $receiver.append(strAsString);
        }
        $receiver.append("(");
        for (ValueParameterDescriptor parameter : receiver$0.getValueParameters()) {
            Intrinsics.checkExpressionValueIsNotNull(parameter, "parameter");
            KotlinType type = parameter.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "parameter.type");
            appendErasedType($receiver, type);
        }
        $receiver.append(")");
        if (withReturnType) {
            if (TypeSignatureMappingKt.hasVoidReturnType(receiver$0)) {
                $receiver.append("V");
            } else {
                KotlinType returnType = receiver$0.getReturnType();
                if (returnType == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(returnType, "returnType!!");
                appendErasedType($receiver, returnType);
            }
        }
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public static /* synthetic */ String computeJvmDescriptor$default(FunctionDescriptor functionDescriptor, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return computeJvmDescriptor(functionDescriptor, z, z2);
    }

    public static final boolean forceSingleValueParameterBoxing(@NotNull CallableDescriptor f) {
        FunctionDescriptor overridden;
        Intrinsics.checkParameterIsNotNull(f, "f");
        if (!(f instanceof FunctionDescriptor) || ((FunctionDescriptor) f).getValueParameters().size() != 1 || SpecialBuiltinMembers.isFromJavaOrBuiltins((CallableMemberDescriptor) f) || (!Intrinsics.areEqual(((FunctionDescriptor) f).getName().asString(), "remove"))) {
            return false;
        }
        FunctionDescriptor original = ((FunctionDescriptor) f).getOriginal();
        Intrinsics.checkExpressionValueIsNotNull(original, "f.original");
        List<ValueParameterDescriptor> valueParameters = original.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "f.original.valueParameters");
        Object objSingle = CollectionsKt.single((List<? extends Object>) valueParameters);
        Intrinsics.checkExpressionValueIsNotNull(objSingle, "f.original.valueParameters.single()");
        KotlinType type = ((ValueParameterDescriptor) objSingle).getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "f.original.valueParameters.single().type");
        JvmType jvmTypeMapToJvmType = mapToJvmType(type);
        if (!(jvmTypeMapToJvmType instanceof JvmType.Primitive)) {
            jvmTypeMapToJvmType = null;
        }
        JvmType.Primitive primitive = (JvmType.Primitive) jvmTypeMapToJvmType;
        if ((primitive != null ? primitive.getJvmPrimitiveType() : null) != JvmPrimitiveType.INT || (overridden = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FunctionDescriptor) f)) == null) {
            return false;
        }
        FunctionDescriptor original2 = overridden.getOriginal();
        Intrinsics.checkExpressionValueIsNotNull(original2, "overridden.original");
        List<ValueParameterDescriptor> valueParameters2 = original2.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "overridden.original.valueParameters");
        Object objSingle2 = CollectionsKt.single((List<? extends Object>) valueParameters2);
        Intrinsics.checkExpressionValueIsNotNull(objSingle2, "overridden.original.valueParameters.single()");
        KotlinType type2 = ((ValueParameterDescriptor) objSingle2).getType();
        Intrinsics.checkExpressionValueIsNotNull(type2, "overridden.original.valueParameters.single().type");
        JvmType overriddenParameterType = mapToJvmType(type2);
        DeclarationDescriptor containingDeclaration = overridden.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "overridden.containingDeclaration");
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(containingDeclaration), KotlinBuiltIns.FQ_NAMES.mutableCollection.toUnsafe()) && (overriddenParameterType instanceof JvmType.Object) && Intrinsics.areEqual(((JvmType.Object) overriddenParameterType).getInternalName(), "java/lang/Object");
    }

    @Nullable
    public static final String computeJvmSignature(@NotNull CallableDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
        if (DescriptorUtils.isLocal(receiver$0)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = receiver$0.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        if (classDescriptor == null) {
            return null;
        }
        Name name = classDescriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "classDescriptor.name");
        if (name.isSpecial()) {
            return null;
        }
        CallableDescriptor original = receiver$0.getOriginal();
        if (!(original instanceof SimpleFunctionDescriptor)) {
            original = null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) original;
        if (simpleFunctionDescriptor != null) {
            return $receiver.signature(classDescriptor, computeJvmDescriptor$default(simpleFunctionDescriptor, false, false, 3, null));
        }
        return null;
    }

    @NotNull
    public static final String getInternalName(@NotNull ClassDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = DescriptorUtilsKt.getFqNameSafe(receiver$0).toUnsafe();
        Intrinsics.checkExpressionValueIsNotNull(unsafe, "fqNameSafe.toUnsafe()");
        ClassId it = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (it != null) {
            JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(it);
            Intrinsics.checkExpressionValueIsNotNull(jvmClassNameByClassId, "JvmClassName.byClassId(it)");
            String internalName = jvmClassNameByClassId.getInternalName();
            Intrinsics.checkExpressionValueIsNotNull(internalName, "JvmClassName.byClassId(it).internalName");
            return internalName;
        }
        return TypeSignatureMappingKt.computeInternalName$default(receiver$0, null, false, 2, null);
    }

    private static final void appendErasedType(@NotNull StringBuilder $receiver, KotlinType type) {
        $receiver.append(mapToJvmType(type));
    }

    @NotNull
    public static final JvmType mapToJvmType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return (JvmType) TypeSignatureMappingKt.mapType(receiver$0, JvmTypeFactoryImpl.INSTANCE, TypeMappingMode.DEFAULT, TypeMappingConfigurationImpl.INSTANCE, null, (32 & 32) != 0 ? FunctionsKt.getDO_NOTHING_3() : null, false);
    }
}
