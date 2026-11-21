package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: InlineClassAwareCaller.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a6\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\n\b\u0000\u0010\n*\u0004\u0018\u00010\u000b*\b\u0012\u0004\u0012\u0002H\n0\t2\u0006\u0010\u0007\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0000\u001a\u0018\u0010\u000e\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\u0018\u0010\u0011\u001a\u00020\u000f*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0007\u001a\u00020\u0002H\u0000\u001a\f\u0010\u0012\u001a\u00020\r*\u00020\u0002H\u0002\u001a\u0014\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u0004\u0018\u00010\u0014H\u0000\u001a\u0012\u0010\u0013\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0010*\u00020\u0001H\u0000\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0015"}, d2 = {"expectedReceiverType", "Lkotlin/reflect/jvm/internal/impl/types/KotlinType;", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "getExpectedReceiverType", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "coerceToExpectedReceiverType", "", "descriptor", "createInlineClassAwareCallerIfNeeded", "Lkotlin/reflect/jvm/internal/calls/Caller;", "M", "Ljava/lang/reflect/Member;", "isDefault", "", "getBoxMethod", "Ljava/lang/reflect/Method;", "Ljava/lang/Class;", "getUnboxMethod", "hasInlineClassReceiver", "toInlineClass", "Lkotlin/reflect/jvm/internal/impl/descriptors/DeclarationDescriptor;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
public final class InlineClassAwareCallerKt {
    @NotNull
    public static /* synthetic */ Caller createInlineClassAwareCallerIfNeeded$default(Caller caller, CallableMemberDescriptor callableMemberDescriptor, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return createInlineClassAwareCallerIfNeeded(caller, callableMemberDescriptor, z);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0075  */
    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <M extends Member> Caller<M> createInlineClassAwareCallerIfNeeded(@NotNull Caller<? extends M> receiver$0, @NotNull CallableMemberDescriptor descriptor, boolean isDefault) {
        boolean z;
        KotlinType returnType;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        boolean z2 = false;
        if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(descriptor)) {
            z2 = true;
        } else {
            Iterable valueParameters = descriptor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "descriptor.valueParameters");
            Iterable $receiver$iv = valueParameters;
            if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        ValueParameterDescriptor it2 = (ValueParameterDescriptor) element$iv;
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        KotlinType type = it2.getType();
                        Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                        if (InlineClassesUtilsKt.isInlineClassType(type)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z || (((returnType = descriptor.getReturnType()) != null && InlineClassesUtilsKt.isInlineClassType(returnType)) || (!(receiver$0 instanceof BoundCaller) && hasInlineClassReceiver(descriptor)))) {
                z2 = true;
            }
        }
        boolean needsInlineAwareCaller = z2;
        return needsInlineAwareCaller ? new InlineClassAwareCaller(descriptor, receiver$0, isDefault) : receiver$0;
    }

    private static final boolean hasInlineClassReceiver(@NotNull CallableMemberDescriptor $receiver) {
        KotlinType expectedReceiverType = getExpectedReceiverType($receiver);
        return expectedReceiverType != null && InlineClassesUtilsKt.isInlineClassType(expectedReceiverType);
    }

    @NotNull
    public static final Method getUnboxMethod(@NotNull Class<?> receiver$0, @NotNull CallableMemberDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        try {
            Method declaredMethod = receiver$0.getDeclaredMethod("unbox-impl", new Class[0]);
            Intrinsics.checkExpressionValueIsNotNull(declaredMethod, "getDeclaredMethod(\"unbox…FOR_INLINE_CLASS_MEMBERS)");
            return declaredMethod;
        } catch (NoSuchMethodException e) {
            throw new KotlinReflectionInternalError("No unbox method found in inline class: " + receiver$0 + " (calling " + descriptor + ')');
        }
    }

    @NotNull
    public static final Method getBoxMethod(@NotNull Class<?> receiver$0, @NotNull CallableMemberDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        try {
            Method declaredMethod = receiver$0.getDeclaredMethod("box-impl", getUnboxMethod(receiver$0, descriptor).getReturnType());
            Intrinsics.checkExpressionValueIsNotNull(declaredMethod, "getDeclaredMethod(\"box\" …d(descriptor).returnType)");
            return declaredMethod;
        } catch (NoSuchMethodException e) {
            throw new KotlinReflectionInternalError("No box method found in inline class: " + receiver$0 + " (calling " + descriptor + ')');
        }
    }

    @Nullable
    public static final Class<?> toInlineClass(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return toInlineClass(receiver$0.getConstructor().mo381getDeclarationDescriptor());
    }

    @Nullable
    public static final Class<?> toInlineClass(@Nullable DeclarationDescriptor $receiver) {
        if (($receiver instanceof ClassDescriptor) && ((ClassDescriptor) $receiver).isInline()) {
            Class<?> javaClass = UtilKt.toJavaClass((ClassDescriptor) $receiver);
            if (javaClass != null) {
                return javaClass;
            }
            throw new KotlinReflectionInternalError("Class object for the class " + ((ClassDescriptor) $receiver).getName() + " cannot be found (classId=" + DescriptorUtilsKt.getClassId((ClassifierDescriptor) $receiver) + ')');
        }
        return null;
    }

    private static final KotlinType getExpectedReceiverType(@NotNull CallableMemberDescriptor $receiver) {
        ReceiverParameterDescriptor extensionReceiver = $receiver.getExtensionReceiverParameter();
        ReceiverParameterDescriptor dispatchReceiver = $receiver.getDispatchReceiverParameter();
        if (extensionReceiver != null) {
            return extensionReceiver.getType();
        }
        if (dispatchReceiver == null) {
            return null;
        }
        if ($receiver instanceof ConstructorDescriptor) {
            return dispatchReceiver.getType();
        }
        DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        return classDescriptor != null ? classDescriptor.getDefaultType() : null;
    }

    @Nullable
    public static final Object coerceToExpectedReceiverType(@Nullable Object $receiver, @NotNull CallableMemberDescriptor descriptor) {
        KotlinType expectedReceiverType;
        Class<?> inlineClass;
        Method unboxMethod;
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        if (((descriptor instanceof PropertyDescriptor) && InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor) descriptor)) || (expectedReceiverType = getExpectedReceiverType(descriptor)) == null || (inlineClass = toInlineClass(expectedReceiverType)) == null || (unboxMethod = getUnboxMethod(inlineClass, descriptor)) == null) {
            return $receiver;
        }
        return unboxMethod.invoke($receiver, new Object[0]);
    }
}
