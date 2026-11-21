package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class KClassValue extends ConstantValue<ClassLiteralValue> {
    public static final Companion Companion = new Companion(null);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KClassValue(@NotNull ClassLiteralValue value) {
        super(value);
        Intrinsics.checkParameterIsNotNull(value, "value");
    }

    @NotNull
    public final ClassId getClassId() {
        return getValue().getClassId();
    }

    public final int getArrayDimensions() {
        return getValue().getArrayNestedness();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KClassValue(@NotNull ClassId classId, int arrayDimensions) {
        this(new ClassLiteralValue(classId, arrayDimensions));
        Intrinsics.checkParameterIsNotNull(classId, "classId");
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        Annotations empty = Annotations.Companion.getEMPTY();
        ClassDescriptor kClass = module.getBuiltIns().getKClass();
        Intrinsics.checkExpressionValueIsNotNull(kClass, "module.builtIns.kClass");
        return KotlinTypeFactory.simpleNotNullType(empty, kClass, CollectionsKt.listOf(new TypeProjectionImpl(getArgumentType(module))));
    }

    @NotNull
    public final KotlinType getArgumentType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        ClassDescriptor descriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, getClassId());
        if (descriptor == null) {
            SimpleType simpleTypeCreateErrorType = ErrorUtils.createErrorType("Unresolved type: " + getClassId() + " (arrayDimensions=" + getArrayDimensions() + ')');
            Intrinsics.checkExpressionValueIsNotNull(simpleTypeCreateErrorType, "ErrorUtils.createErrorTy…sions=$arrayDimensions)\")");
            return simpleTypeCreateErrorType;
        }
        SimpleType defaultType = descriptor.getDefaultType();
        Intrinsics.checkExpressionValueIsNotNull(defaultType, "descriptor.defaultType");
        KotlinType kotlinTypeReplaceArgumentsWithStarProjections = TypeUtilsKt.replaceArgumentsWithStarProjections(defaultType);
        int arrayDimensions = getArrayDimensions();
        for (int i = 0; i < arrayDimensions; i++) {
            Object type = module.getBuiltIns().getArrayType(Variance.INVARIANT, kotlinTypeReplaceArgumentsWithStarProjections);
            Intrinsics.checkExpressionValueIsNotNull(type, "module.builtIns.getArray…Variance.INVARIANT, type)");
            kotlinTypeReplaceArgumentsWithStarProjections = (KotlinType) type;
        }
        return kotlinTypeReplaceArgumentsWithStarProjections;
    }

    /* JADX INFO: compiled from: constantValues.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final ConstantValue<?> create(@NotNull KotlinType argumentType) {
            Intrinsics.checkParameterIsNotNull(argumentType, "argumentType");
            if (KotlinTypeKt.isError(argumentType)) {
                return null;
            }
            KotlinType type = argumentType;
            int arrayDimensions = 0;
            while (KotlinBuiltIns.isArray(type)) {
                KotlinType type2 = ((TypeProjection) CollectionsKt.single((List) type.getArguments())).getType();
                Intrinsics.checkExpressionValueIsNotNull(type2, "type.arguments.single().type");
                type = type2;
                arrayDimensions++;
            }
            ClassifierDescriptor descriptor = type.getConstructor().mo381getDeclarationDescriptor();
            if (descriptor instanceof ClassDescriptor) {
                ClassId classId = DescriptorUtilsKt.getClassId(descriptor);
                if (classId != null) {
                    return new KClassValue(classId, arrayDimensions);
                }
                return null;
            }
            if (!(descriptor instanceof TypeParameterDescriptor)) {
                return null;
            }
            ClassId classId2 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.any.toSafe());
            Intrinsics.checkExpressionValueIsNotNull(classId2, "ClassId.topLevel(KotlinB…ns.FQ_NAMES.any.toSafe())");
            return new KClassValue(classId2, 0);
        }
    }
}
