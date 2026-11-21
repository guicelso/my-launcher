package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UtilKt {
    @NotNull
    public static final List<ValueParameterDescriptor> copyValueParameters(@NotNull Collection<ValueParameterData> newValueParametersTypes, @NotNull Collection<? extends ValueParameterDescriptor> oldValueParameters, @NotNull CallableDescriptor newOwner) {
        Intrinsics.checkParameterIsNotNull(newValueParametersTypes, "newValueParametersTypes");
        Intrinsics.checkParameterIsNotNull(oldValueParameters, "oldValueParameters");
        Intrinsics.checkParameterIsNotNull(newOwner, "newOwner");
        boolean z = false;
        boolean z2 = newValueParametersTypes.size() == oldValueParameters.size();
        if (_Assertions.ENABLED && !z2) {
            throw new AssertionError("Different value parameters sizes: Enhanced = " + newValueParametersTypes.size() + ", Old = " + oldValueParameters.size());
        }
        Iterable $receiver$iv = CollectionsKt.zip(newValueParametersTypes, oldValueParameters);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        Collection destination$iv$iv2 = destination$iv$iv;
        for (Object item$iv$iv : $receiver$iv) {
            Pair $newParameter_oldParameter = (Pair) item$iv$iv;
            boolean z3 = z;
            ValueParameterData newParameter = (ValueParameterData) $newParameter_oldParameter.component1();
            ValueParameterDescriptor oldParameter = (ValueParameterDescriptor) $newParameter_oldParameter.component2();
            int index = oldParameter.getIndex();
            Annotations annotations = oldParameter.getAnnotations();
            Name name = oldParameter.getName();
            Intrinsics.checkExpressionValueIsNotNull(name, "oldParameter.name");
            KotlinType type = newParameter.getType();
            boolean hasDefaultValue = newParameter.getHasDefaultValue();
            boolean zIsCrossinline = oldParameter.isCrossinline();
            boolean zIsNoinline = oldParameter.isNoinline();
            KotlinType arrayElementType = oldParameter.getVarargElementType() != null ? DescriptorUtilsKt.getModule(newOwner).getBuiltIns().getArrayElementType(newParameter.getType()) : null;
            SourceElement source = oldParameter.getSource();
            Intrinsics.checkExpressionValueIsNotNull(source, "oldParameter.source");
            Collection destination$iv$iv3 = destination$iv$iv2;
            destination$iv$iv3.add(new ValueParameterDescriptorImpl(newOwner, null, index, annotations, name, type, hasDefaultValue, zIsCrossinline, zIsNoinline, arrayElementType, source));
            destination$iv$iv2 = destination$iv$iv3;
            z = z3;
        }
        return (List) destination$iv$iv2;
    }

    @Nullable
    public static final LazyJavaStaticClassScope getParentJavaStaticClassScope(@NotNull ClassDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassDescriptor superClassDescriptor = DescriptorUtilsKt.getSuperClassNotAny(receiver$0);
        if (superClassDescriptor == null) {
            return null;
        }
        MemberScope staticScope = superClassDescriptor.getStaticScope();
        Intrinsics.checkExpressionValueIsNotNull(staticScope, "superClassDescriptor.staticScope");
        return !(staticScope instanceof LazyJavaStaticClassScope) ? getParentJavaStaticClassScope(superClassDescriptor) : (LazyJavaStaticClassScope) staticScope;
    }

    @Nullable
    public static final JvmClassName getImplClassNameForDeserialized(@NotNull DeserializedMemberDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        DeserializedContainerSource containerSource = receiver$0.getContainerSource();
        if (!(containerSource instanceof JvmPackagePartSource)) {
            containerSource = null;
        }
        JvmPackagePartSource jvmPackagePartSource = (JvmPackagePartSource) containerSource;
        if (jvmPackagePartSource != null) {
            return jvmPackagePartSource.getClassName();
        }
        return null;
    }

    @Nullable
    public static final AnnotationDefaultValue getDefaultValueFromAnnotation(@NotNull ValueParameterDescriptor receiver$0) {
        Object $receiver$iv;
        String it;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Annotations annotations = receiver$0.getAnnotations();
        FqName fqName = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME");
        AnnotationDescriptor annotationDescriptorMo374findAnnotation = annotations.mo374findAnnotation(fqName);
        if (annotationDescriptorMo374findAnnotation != null && ($receiver$iv = DescriptorUtilsKt.firstArgument(annotationDescriptorMo374findAnnotation)) != null) {
            StringValue stringValue = (StringValue) (!($receiver$iv instanceof StringValue) ? null : $receiver$iv);
            if (stringValue != null && (it = stringValue.getValue()) != null) {
                return new StringDefaultValue(it);
            }
        }
        Annotations annotations2 = receiver$0.getAnnotations();
        FqName fqName2 = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "JvmAnnotationNames.DEFAULT_NULL_FQ_NAME");
        if (annotations2.hasAnnotation(fqName2)) {
            return NullDefaultValue.INSTANCE;
        }
        return null;
    }
}
