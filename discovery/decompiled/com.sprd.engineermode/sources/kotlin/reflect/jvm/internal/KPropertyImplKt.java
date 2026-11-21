package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.calls.InternalUnderlyingValOfInlineClass;
import kotlin.reflect.jvm.internal.calls.ThrowingCaller;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: KPropertyImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\b*\u00020\nH\u0002\"\"\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"boundReceiver", "", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "getBoundReceiver", "(Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;)Ljava/lang/Object;", "computeCallerForAccessor", "Lkotlin/reflect/jvm/internal/calls/Caller;", "isGetter", "", "isJvmFieldPropertyInCompanionObject", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
public final class KPropertyImplKt {
    @Nullable
    public static final Object getBoundReceiver(@NotNull KPropertyImpl.Accessor<?, ?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.getProperty().getBoundReceiver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Caller<?> computeCallerForAccessor(@NotNull final KPropertyImpl.Accessor<?, ?> accessor, final boolean isGetter) {
        JvmFunctionSignature.KotlinFunction signature;
        Method method;
        Caller boundInstance;
        JvmProtoBuf.JvmMethodSignature setter;
        Method unboxMethod;
        if (KDeclarationContainerImpl.INSTANCE.getLOCAL_PROPERTY_SIGNATURE$kotlin_reflect_api().matches(accessor.getProperty().getSignature())) {
            return ThrowingCaller.INSTANCE;
        }
        final AnonymousClass1 isJvmStaticProperty$ = new AnonymousClass1(accessor);
        final AnonymousClass2 isNotNullProperty$ = new AnonymousClass2(accessor);
        Function1<Field, CallerImpl<? extends Field>> function1 = new Function1<Field, CallerImpl<? extends Field>>() { // from class: kotlin.reflect.jvm.internal.KPropertyImplKt.computeCallerForAccessor.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CallerImpl<Field> invoke(@NotNull Field field) {
                Intrinsics.checkParameterIsNotNull(field, "field");
                if (KPropertyImplKt.isJvmFieldPropertyInCompanionObject(accessor.getProperty().getDescriptor()) || !Modifier.isStatic(field.getModifiers())) {
                    if (isGetter) {
                        return accessor.isBound() ? new CallerImpl.FieldGetter.BoundInstance(field, KPropertyImplKt.getBoundReceiver(accessor)) : new CallerImpl.FieldGetter.Instance(field);
                    }
                    return accessor.isBound() ? new CallerImpl.FieldSetter.BoundInstance(field, isNotNullProperty$.invoke2(), KPropertyImplKt.getBoundReceiver(accessor)) : new CallerImpl.FieldSetter.Instance(field, isNotNullProperty$.invoke2());
                }
                if (!isJvmStaticProperty$.invoke2()) {
                    return isGetter ? new CallerImpl.FieldGetter.Static(field) : new CallerImpl.FieldSetter.Static(field, isNotNullProperty$.invoke2());
                }
                if (isGetter) {
                    return accessor.isBound() ? new CallerImpl.FieldGetter.BoundJvmStaticInObject(field) : new CallerImpl.FieldGetter.JvmStaticInObject(field);
                }
                return accessor.isBound() ? new CallerImpl.FieldSetter.BoundJvmStaticInObject(field, isNotNullProperty$.invoke2()) : new CallerImpl.FieldSetter.JvmStaticInObject(field, isNotNullProperty$.invoke2());
            }
        };
        JvmPropertySignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapPropertySignature(accessor.getProperty().getDescriptor());
        if (jvmSignature instanceof JvmPropertySignature.KotlinProperty) {
            JvmProtoBuf.JvmPropertySignature $receiver = ((JvmPropertySignature.KotlinProperty) jvmSignature).getSignature();
            if (isGetter) {
                setter = $receiver.hasGetter() ? $receiver.getGetter() : null;
            } else {
                setter = $receiver.hasSetter() ? $receiver.getSetter() : null;
            }
            JvmProtoBuf.JvmMethodSignature accessorSignature = setter;
            Method accessor2 = accessorSignature != null ? accessor.getProperty().getContainer().findMethodBySignature(((JvmPropertySignature.KotlinProperty) jvmSignature).getNameResolver().getString(accessorSignature.getName()), ((JvmPropertySignature.KotlinProperty) jvmSignature).getNameResolver().getString(accessorSignature.getDesc())) : null;
            if (accessor2 == null) {
                if (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(accessor.getProperty().getDescriptor()) && Intrinsics.areEqual(accessor.getProperty().getDescriptor().getVisibility(), Visibilities.INTERNAL)) {
                    Class<?> inlineClass = InlineClassAwareCallerKt.toInlineClass(accessor.getProperty().getDescriptor().getContainingDeclaration());
                    if (inlineClass == null || (unboxMethod = InlineClassAwareCallerKt.getUnboxMethod(inlineClass, accessor.getProperty().getDescriptor())) == null) {
                        throw new KotlinReflectionInternalError("Underlying property of inline class " + accessor.getProperty() + " should have a field");
                    }
                    boundInstance = accessor.isBound() ? new InternalUnderlyingValOfInlineClass.Bound(unboxMethod, getBoundReceiver(accessor)) : new InternalUnderlyingValOfInlineClass.Unbound(unboxMethod);
                } else {
                    Field javaField = accessor.getProperty().getJavaField();
                    if (javaField == null) {
                        throw new KotlinReflectionInternalError("No accessors or field is found for property " + accessor.getProperty());
                    }
                    boundInstance = function1.invoke(javaField);
                }
            } else if (!Modifier.isStatic(accessor2.getModifiers())) {
                boundInstance = accessor.isBound() ? new CallerImpl.Method.BoundInstance(accessor2, getBoundReceiver(accessor)) : new CallerImpl.Method.Instance(accessor2);
            } else if (isJvmStaticProperty$.invoke2()) {
                boundInstance = accessor.isBound() ? new CallerImpl.Method.BoundJvmStaticInObject(accessor2) : new CallerImpl.Method.JvmStaticInObject(accessor2);
            } else {
                boundInstance = accessor.isBound() ? new CallerImpl.Method.BoundStatic(accessor2, getBoundReceiver(accessor)) : new CallerImpl.Method.Static(accessor2);
            }
        } else if (jvmSignature instanceof JvmPropertySignature.JavaField) {
            boundInstance = function1.invoke(((JvmPropertySignature.JavaField) jvmSignature).getField());
        } else if (jvmSignature instanceof JvmPropertySignature.JavaMethodProperty) {
            if (isGetter) {
                method = ((JvmPropertySignature.JavaMethodProperty) jvmSignature).getGetterMethod();
            } else {
                method = ((JvmPropertySignature.JavaMethodProperty) jvmSignature).getSetterMethod();
                if (method == null) {
                    throw new KotlinReflectionInternalError("No source found for setter of Java method property: " + ((JvmPropertySignature.JavaMethodProperty) jvmSignature).getGetterMethod());
                }
            }
            boundInstance = accessor.isBound() ? new CallerImpl.Method.BoundInstance(method, getBoundReceiver(accessor)) : new CallerImpl.Method.Instance(method);
        } else {
            if (jvmSignature instanceof JvmPropertySignature.MappedKotlinProperty) {
                if (isGetter) {
                    signature = ((JvmPropertySignature.MappedKotlinProperty) jvmSignature).getGetterSignature();
                } else {
                    signature = ((JvmPropertySignature.MappedKotlinProperty) jvmSignature).getSetterSignature();
                    if (signature == null) {
                        throw new KotlinReflectionInternalError("No setter found for property " + accessor.getProperty());
                    }
                }
                Method accessor3 = accessor.getProperty().getContainer().findMethodBySignature(signature.getMethodName(), signature.getMethodDesc());
                if (accessor3 == null) {
                    throw new KotlinReflectionInternalError("No accessor found for property " + accessor.getProperty());
                }
                boolean z = !Modifier.isStatic(accessor3.getModifiers());
                if (!_Assertions.ENABLED || z) {
                    return accessor.isBound() ? new CallerImpl.Method.BoundInstance(accessor3, getBoundReceiver(accessor)) : new CallerImpl.Method.Instance(accessor3);
                }
                throw new AssertionError("Mapped property cannot have a static accessor: " + accessor.getProperty());
            }
            throw new NoWhenBranchMatchedException();
        }
        return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded$default(boundInstance, accessor.getDescriptor(), false, 2, null);
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.KPropertyImplKt$computeCallerForAccessor$1, reason: invalid class name */
    /* JADX INFO: compiled from: KPropertyImpl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"isJvmStaticProperty", "", "invoke"}, k = 3, mv = {1, 1, 13})
    static final class AnonymousClass1 extends Lambda implements Function0<Boolean> {
        final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(KPropertyImpl.Accessor accessor) {
            super(0);
            this.$this_computeCallerForAccessor = accessor;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Boolean invoke() {
            return Boolean.valueOf(invoke2());
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2() {
            return this.$this_computeCallerForAccessor.getProperty().getDescriptor().getAnnotations().hasAnnotation(UtilKt.getJVM_STATIC());
        }
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.KPropertyImplKt$computeCallerForAccessor$2, reason: invalid class name */
    /* JADX INFO: compiled from: KPropertyImpl.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"isNotNullProperty", "", "invoke"}, k = 3, mv = {1, 1, 13})
    static final class AnonymousClass2 extends Lambda implements Function0<Boolean> {
        final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(KPropertyImpl.Accessor accessor) {
            super(0);
            this.$this_computeCallerForAccessor = accessor;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Boolean invoke() {
            return Boolean.valueOf(invoke2());
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2() {
            return !TypeUtils.isNullableType(this.$this_computeCallerForAccessor.getProperty().getDescriptor().getType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJvmFieldPropertyInCompanionObject(@NotNull PropertyDescriptor $receiver) {
        DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "containingDeclaration");
        if (!DescriptorUtils.isCompanionObject(containingDeclaration)) {
            return false;
        }
        DeclarationDescriptor outerClass = containingDeclaration.getContainingDeclaration();
        if (DescriptorUtils.isInterface(outerClass) || DescriptorUtils.isAnnotationClass(outerClass)) {
            return ($receiver instanceof DeserializedPropertyDescriptor) && JvmProtoBufUtil.isMovedFromInterfaceCompanion(((DeserializedPropertyDescriptor) $receiver).getProto());
        }
        return true;
    }
}
