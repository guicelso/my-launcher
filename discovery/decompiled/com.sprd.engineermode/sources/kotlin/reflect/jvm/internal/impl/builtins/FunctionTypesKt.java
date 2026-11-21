package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.List;
import kotlin.TuplesKt;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.BuiltInAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: functionTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FunctionTypesKt {
    public static final boolean isFunctionType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        return (classifierDescriptorMo381getDeclarationDescriptor != null ? getFunctionalClassKind(classifierDescriptorMo381getDeclarationDescriptor) : null) == FunctionClassDescriptor.Kind.Function;
    }

    public static final boolean isSuspendFunctionType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        return (classifierDescriptorMo381getDeclarationDescriptor != null ? getFunctionalClassKind(classifierDescriptorMo381getDeclarationDescriptor) : null) == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinFunctionalType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        FunctionClassDescriptor.Kind kind = classifierDescriptorMo381getDeclarationDescriptor != null ? getFunctionalClassKind(classifierDescriptorMo381getDeclarationDescriptor) : null;
        return kind == FunctionClassDescriptor.Kind.Function || kind == FunctionClassDescriptor.Kind.SuspendFunction;
    }

    public static final boolean isBuiltinExtensionFunctionalType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return isBuiltinFunctionalType(receiver$0) && isTypeAnnotatedWithExtensionFunctionType(receiver$0);
    }

    private static final boolean isTypeAnnotatedWithExtensionFunctionType(@NotNull KotlinType $receiver) {
        Annotations annotations = $receiver.getAnnotations();
        FqName fqName = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        return annotations.mo374findAnnotation(fqName) != null;
    }

    @Nullable
    public static final FunctionClassDescriptor.Kind getFunctionalClassKind(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if ((receiver$0 instanceof ClassDescriptor) && KotlinBuiltIns.isUnderKotlinPackage(receiver$0)) {
            return getFunctionalClassKind(DescriptorUtilsKt.getFqNameUnsafe(receiver$0));
        }
        return null;
    }

    private static final FunctionClassDescriptor.Kind getFunctionalClassKind(@NotNull FqNameUnsafe $receiver) {
        if (!$receiver.isSafe() || $receiver.isRoot()) {
            return null;
        }
        BuiltInFictitiousFunctionClassFactory.Companion companion = BuiltInFictitiousFunctionClassFactory.Companion;
        String strAsString = $receiver.shortName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "shortName().asString()");
        FqName fqNameParent = $receiver.toSafe().parent();
        Intrinsics.checkExpressionValueIsNotNull(fqNameParent, "toSafe().parent()");
        return companion.getFunctionalClassKind(strAsString, fqNameParent);
    }

    @Nullable
    public static final KotlinType getReceiverTypeFromFunctionType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        boolean zIsBuiltinFunctionalType = isBuiltinFunctionalType(receiver$0);
        if (!_Assertions.ENABLED || zIsBuiltinFunctionalType) {
            if (isTypeAnnotatedWithExtensionFunctionType(receiver$0)) {
                return ((TypeProjection) CollectionsKt.first((List) receiver$0.getArguments())).getType();
            }
            return null;
        }
        throw new AssertionError("Not a function type: " + receiver$0);
    }

    @NotNull
    public static final KotlinType getReturnTypeFromFunctionType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        boolean zIsBuiltinFunctionalType = isBuiltinFunctionalType(receiver$0);
        if (!_Assertions.ENABLED || zIsBuiltinFunctionalType) {
            KotlinType type = ((TypeProjection) CollectionsKt.last((List) receiver$0.getArguments())).getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "arguments.last().type");
            return type;
        }
        throw new AssertionError("Not a function type: " + receiver$0);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    @NotNull
    public static final List<TypeProjection> getValueParameterTypesFromFunctionType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        boolean zIsBuiltinFunctionalType = isBuiltinFunctionalType(receiver$0);
        if (_Assertions.ENABLED && !zIsBuiltinFunctionalType) {
            throw new AssertionError("Not a function type: " + receiver$0);
        }
        List<TypeProjection> arguments = receiver$0.getArguments();
        ?? IsBuiltinExtensionFunctionalType = isBuiltinExtensionFunctionalType(receiver$0);
        int size = arguments.size() - 1;
        boolean z = IsBuiltinExtensionFunctionalType <= size;
        if (!_Assertions.ENABLED || z) {
            return arguments.subList(IsBuiltinExtensionFunctionalType == true ? 1 : 0, size);
        }
        throw new AssertionError("Not an exact function type: " + receiver$0);
    }

    @Nullable
    public static final Name extractParameterNameFromFunctionTypeArgument(@NotNull KotlinType receiver$0) {
        String it;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Annotations annotations = receiver$0.getAnnotations();
        FqName fqName = KotlinBuiltIns.FQ_NAMES.parameterName;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.parameterName");
        AnnotationDescriptor annotation = annotations.mo374findAnnotation(fqName);
        if (annotation == null) {
            return null;
        }
        Object objSingleOrNull = CollectionsKt.singleOrNull(annotation.getAllValueArguments().values());
        if (!(objSingleOrNull instanceof StringValue)) {
            objSingleOrNull = null;
        }
        StringValue stringValue = (StringValue) objSingleOrNull;
        if (stringValue != null && (it = stringValue.getValue()) != null) {
            if (!Name.isValidIdentifier(it)) {
                it = null;
            }
            if (it != null) {
                String name = it;
                return Name.identifier(name);
            }
        }
        return null;
    }

    @NotNull
    public static final List<TypeProjection> getFunctionTypeArgumentProjections(@Nullable KotlinType receiverType, @NotNull List<? extends KotlinType> parameterTypes, @Nullable List<Name> list, @NotNull KotlinType returnType, @NotNull KotlinBuiltIns builtIns) {
        Name name;
        KotlinType typeToUse;
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        boolean z = false;
        ArrayList arguments = new ArrayList(parameterTypes.size() + (receiverType != null ? 1 : 0) + 1);
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(arguments, receiverType != null ? TypeUtilsKt.asTypeProjection(receiverType) : null);
        List<? extends KotlinType> $receiver$iv = parameterTypes;
        int index$iv = 0;
        for (Object item$iv : $receiver$iv) {
            ArrayList arrayList = arguments;
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            KotlinType type = (KotlinType) item$iv;
            if (list == 0 || (name = list.get(index$iv)) == null || name.isSpecial()) {
                name = null;
            }
            if (name != null) {
                FqName fqName = KotlinBuiltIns.FQ_NAMES.parameterName;
                Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.parameterName");
                Name nameIdentifier = Name.identifier("name");
                String strAsString = name.asString();
                Intrinsics.checkExpressionValueIsNotNull(strAsString, "name.asString()");
                BuiltInAnnotationDescriptor parameterNameAnnotation = new BuiltInAnnotationDescriptor(builtIns, fqName, MapsKt.mapOf(TuplesKt.to(nameIdentifier, new StringValue(strAsString))));
                typeToUse = TypeUtilsKt.replaceAnnotations(type, Annotations.Companion.create(CollectionsKt.plus(type.getAnnotations(), parameterNameAnnotation)));
            } else {
                typeToUse = type;
            }
            arrayList.add(TypeUtilsKt.asTypeProjection(typeToUse));
            index$iv = index$iv2;
            $receiver$iv = $receiver$iv;
            z = z;
        }
        arguments.add(TypeUtilsKt.asTypeProjection(returnType));
        return arguments;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0063  */
    @JvmOverloads
    @NotNull
    public static final SimpleType createFunctionType(@NotNull KotlinBuiltIns builtIns, @NotNull Annotations annotations, @Nullable KotlinType receiverType, @NotNull List<? extends KotlinType> parameterTypes, @Nullable List<Name> list, @NotNull KotlinType returnType, boolean suspendFunction) {
        Annotations typeAnnotations;
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        Intrinsics.checkParameterIsNotNull(parameterTypes, "parameterTypes");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        List<TypeProjection> functionTypeArgumentProjections = getFunctionTypeArgumentProjections(receiverType, parameterTypes, list, returnType, builtIns);
        int size = parameterTypes.size();
        int parameterCount = receiverType == null ? size : size + 1;
        ClassDescriptor classDescriptor = suspendFunction ? builtIns.getSuspendFunction(parameterCount) : builtIns.getFunction(parameterCount);
        Intrinsics.checkExpressionValueIsNotNull(classDescriptor, "if (suspendFunction) bui…tFunction(parameterCount)");
        if (receiverType != null) {
            FqName fqName = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
            Intrinsics.checkExpressionValueIsNotNull(fqName, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
            if (annotations.mo374findAnnotation(fqName) != null) {
                typeAnnotations = annotations;
            } else {
                Annotations.Companion companion = Annotations.Companion;
                FqName fqName2 = KotlinBuiltIns.FQ_NAMES.extensionFunctionType;
                Intrinsics.checkExpressionValueIsNotNull(fqName2, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
                typeAnnotations = companion.create(CollectionsKt.plus(annotations, new BuiltInAnnotationDescriptor(builtIns, fqName2, MapsKt.emptyMap())));
            }
        } else {
            typeAnnotations = annotations;
        }
        return KotlinTypeFactory.simpleNotNullType(typeAnnotations, classDescriptor, functionTypeArgumentProjections);
    }
}
