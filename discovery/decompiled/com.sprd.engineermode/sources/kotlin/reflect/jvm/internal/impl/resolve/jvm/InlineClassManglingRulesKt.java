package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: inlineClassManglingRules.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class InlineClassManglingRulesKt {
    public static final boolean shouldHideConstructorDueToInlineClassTypeValueParameters(@NotNull CallableMemberDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        ClassConstructorDescriptor constructorDescriptor = (ClassConstructorDescriptor) (!(descriptor instanceof ClassConstructorDescriptor) ? null : descriptor);
        if (constructorDescriptor == null || Visibilities.isPrivate(constructorDescriptor.getVisibility())) {
            return false;
        }
        ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
        Intrinsics.checkExpressionValueIsNotNull(constructedClass, "constructorDescriptor.constructedClass");
        if (constructedClass.isInline() || DescriptorUtils.isSealedClass(constructorDescriptor.getConstructedClass())) {
            return false;
        }
        Iterable valueParameters = constructorDescriptor.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "constructorDescriptor.valueParameters");
        Iterable $receiver$iv = valueParameters;
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $receiver$iv) {
            ValueParameterDescriptor it = (ValueParameterDescriptor) element$iv;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            KotlinType type = it.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
            if (requiresFunctionNameMangling(type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isInlineClassThatRequiresMangling(@NotNull DeclarationDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return InlineClassesUtilsKt.isInlineClass(receiver$0) && !isDontMangleClass((ClassDescriptor) receiver$0);
    }

    public static final boolean isInlineClassThatRequiresMangling(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = receiver$0.getConstructor().mo381getDeclarationDescriptor();
        return classifierDescriptorMo381getDeclarationDescriptor != null && isInlineClassThatRequiresMangling(classifierDescriptorMo381getDeclarationDescriptor);
    }

    private static final boolean requiresFunctionNameMangling(@NotNull KotlinType $receiver) {
        return isInlineClassThatRequiresMangling($receiver) || isTypeParameterWithUpperBoundThatRequiresMangling($receiver);
    }

    private static final boolean isDontMangleClass(ClassDescriptor classDescriptor) {
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(classDescriptor), DescriptorUtils.RESULT_FQ_NAME);
    }

    private static final boolean isTypeParameterWithUpperBoundThatRequiresMangling(@NotNull KotlinType $receiver) {
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = $receiver.getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        TypeParameterDescriptor descriptor = (TypeParameterDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
        if (descriptor == null) {
            return false;
        }
        return requiresFunctionNameMangling(TypeSignatureMappingKt.getRepresentativeUpperBound(descriptor));
    }
}
