package kotlin.reflect.jvm.internal.calls;

import android.support.v4.app.NotificationCompat;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: InlineClassAwareCaller.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\b\u0000\u0018\u0000*\f\b\u0000\u0010\u0001 \u0001*\u0004\u0018\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u001cB#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016¢\u0006\u0002\u0010\u001bR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001d"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller;", "M", "Ljava/lang/reflect/Member;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "caller", "isDefault", "", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/reflect/jvm/internal/calls/Caller;Z)V", "data", "Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "member", "getMember", "()Ljava/lang/reflect/Member;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", NotificationCompat.CATEGORY_CALL, "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "BoxUnboxData", "kotlin-reflect-api"}, k = 1, mv = {1, 1, 13})
public final class InlineClassAwareCaller<M extends Member> implements Caller<M> {
    private final Caller<M> caller;
    private final BoxUnboxData data;
    private final boolean isDefault;

    /* JADX WARN: Multi-variable type inference failed */
    public InlineClassAwareCaller(@NotNull CallableMemberDescriptor descriptor, @NotNull Caller<? extends M> caller, boolean isDefault) {
        BoxUnboxData boxUnboxData;
        int $i$a$1$run;
        Method unboxMethod;
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        Intrinsics.checkParameterIsNotNull(caller, "caller");
        this.caller = caller;
        this.isDefault = isDefault;
        InlineClassAwareCaller<M> inlineClassAwareCaller = this;
        int $i$a$1$run2 = 0;
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(returnType, "descriptor.returnType!!");
        Class<?> inlineClass = InlineClassAwareCallerKt.toInlineClass(returnType);
        Method box = inlineClass != null ? InlineClassAwareCallerKt.getBoxMethod(inlineClass, descriptor) : null;
        if (InlineClassesUtilsKt.isGetterOfUnderlyingPropertyOfInlineClass(descriptor)) {
            boxUnboxData = new BoxUnboxData(IntRange.INSTANCE.getEMPTY(), new Method[0], box);
        } else {
            Caller<M> caller2 = inlineClassAwareCaller.caller;
            int i = -1;
            if (!(caller2 instanceof CallerImpl.Method.BoundStatic)) {
                if (descriptor instanceof ConstructorDescriptor) {
                    if (!(caller2 instanceof BoundCaller)) {
                        i = 0;
                    }
                } else if (descriptor.getDispatchReceiverParameter() != null && !(inlineClassAwareCaller.caller instanceof BoundCaller)) {
                    DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
                    Intrinsics.checkExpressionValueIsNotNull(containingDeclaration, "descriptor.containingDeclaration");
                    if (!InlineClassesUtilsKt.isInlineClass(containingDeclaration)) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                } else {
                    i = 0;
                }
            }
            int shift = i;
            int extraArgumentsTail = inlineClassAwareCaller.isDefault ? 2 : 0;
            ArrayList kotlinParameterTypes = new ArrayList();
            ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
            KotlinType extensionReceiverType = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
            if (extensionReceiverType != null) {
                kotlinParameterTypes.add(extensionReceiverType);
            } else if (descriptor instanceof ConstructorDescriptor) {
                ClassDescriptor constructedClass = ((ConstructorDescriptor) descriptor).getConstructedClass();
                Intrinsics.checkExpressionValueIsNotNull(constructedClass, "descriptor.constructedClass");
                if (constructedClass.isInner()) {
                    DeclarationDescriptor containingDeclaration2 = constructedClass.getContainingDeclaration();
                    if (containingDeclaration2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                    kotlinParameterTypes.add(((ClassDescriptor) containingDeclaration2).getDefaultType());
                }
            } else {
                DeclarationDescriptor containingDeclaration3 = descriptor.getContainingDeclaration();
                Intrinsics.checkExpressionValueIsNotNull(containingDeclaration3, "descriptor.containingDeclaration");
                if ((containingDeclaration3 instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration3).isInline()) {
                    kotlinParameterTypes.add(((ClassDescriptor) containingDeclaration3).getDefaultType());
                }
            }
            Iterable valueParameters = descriptor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "descriptor.valueParameters");
            Iterable $receiver$iv = valueParameters;
            for (Object item$iv : $receiver$iv) {
                ValueParameterDescriptor p1 = (ValueParameterDescriptor) item$iv;
                kotlinParameterTypes.add(p1.getType());
            }
            ArrayList kotlinParameterTypes2 = kotlinParameterTypes;
            int expectedArgsSize = kotlinParameterTypes2.size() + shift + extraArgumentsTail;
            if (CallerKt.getArity(inlineClassAwareCaller) != expectedArgsSize) {
                throw new KotlinReflectionInternalError("Inconsistent number of parameters in the descriptor and Java reflection object: " + CallerKt.getArity(inlineClassAwareCaller) + " != " + expectedArgsSize + "\nCalling: " + descriptor + "\nParameter types: " + inlineClassAwareCaller.getParameterTypes() + ")\nDefault: " + inlineClassAwareCaller.isDefault);
            }
            int i$iv = 0;
            IntRange argumentRange = RangesKt.until(Math.max(shift, 0), kotlinParameterTypes2.size() + shift);
            Method[] methodArr = new Method[expectedArgsSize];
            int length = methodArr.length;
            while (i$iv < length) {
                int i2 = i$iv;
                if (argumentRange.contains(i2)) {
                    $i$a$1$run = $i$a$1$run2;
                    int $i$a$1$run3 = i2 - shift;
                    Class<?> inlineClass2 = InlineClassAwareCallerKt.toInlineClass((KotlinType) kotlinParameterTypes2.get($i$a$1$run3));
                    unboxMethod = inlineClass2 != null ? InlineClassAwareCallerKt.getUnboxMethod(inlineClass2, descriptor) : null;
                } else {
                    $i$a$1$run = $i$a$1$run2;
                    unboxMethod = null;
                }
                methodArr[i$iv] = unboxMethod;
                i$iv++;
                $i$a$1$run2 = $i$a$1$run;
            }
            boxUnboxData = new BoxUnboxData(argumentRange, methodArr, box);
        }
        this.data = boxUnboxData;
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    /* JADX INFO: renamed from: getMember */
    public M mo371getMember() {
        return (M) this.caller.mo371getMember();
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @NotNull
    public Type getReturnType() {
        return this.caller.getReturnType();
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @NotNull
    public List<Type> getParameterTypes() {
        return this.caller.getParameterTypes();
    }

    /* JADX INFO: compiled from: InlineClassAwareCaller.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\r\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0003H\u0086\u0002J\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005H\u0086\u0002¢\u0006\u0002\u0010\u000eJ\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0006H\u0086\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lkotlin/reflect/jvm/internal/calls/InlineClassAwareCaller$BoxUnboxData;", "", "argumentRange", "Lkotlin/ranges/IntRange;", "unbox", "", "Ljava/lang/reflect/Method;", "box", "(Lkotlin/ranges/IntRange;[Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getArgumentRange", "()Lkotlin/ranges/IntRange;", "getBox", "()Ljava/lang/reflect/Method;", "getUnbox", "()[Ljava/lang/reflect/Method;", "[Ljava/lang/reflect/Method;", "component1", "component2", "component3", "kotlin-reflect-api"}, k = 1, mv = {1, 1, 13})
    private static final class BoxUnboxData {

        @NotNull
        private final IntRange argumentRange;

        @Nullable
        private final Method box;

        @NotNull
        private final Method[] unbox;

        public BoxUnboxData(@NotNull IntRange argumentRange, @NotNull Method[] unbox, @Nullable Method box) {
            Intrinsics.checkParameterIsNotNull(argumentRange, "argumentRange");
            Intrinsics.checkParameterIsNotNull(unbox, "unbox");
            this.argumentRange = argumentRange;
            this.unbox = unbox;
            this.box = box;
        }

        @NotNull
        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IntRange getArgumentRange() {
            return this.argumentRange;
        }

        @NotNull
        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Method[] getUnbox() {
            return this.unbox;
        }

        @Nullable
        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Method getBox() {
            return this.box;
        }
    }

    @Override // kotlin.reflect.jvm.internal.calls.Caller
    @Nullable
    public Object call(@NotNull Object[] args) {
        Object objInvoke;
        Intrinsics.checkParameterIsNotNull(args, "args");
        BoxUnboxData boxUnboxData = this.data;
        IntRange range = boxUnboxData.getArgumentRange();
        Method[] unbox = boxUnboxData.getUnbox();
        Method box = boxUnboxData.getBox();
        Object[] unboxed = Arrays.copyOf(args, args.length);
        Intrinsics.checkExpressionValueIsNotNull(unboxed, "java.util.Arrays.copyOf(this, size)");
        if (unboxed == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        int index = range.getFirst();
        int last = range.getLast();
        if (index <= last) {
            while (true) {
                Method method = unbox[index];
                Object arg = args[index];
                unboxed[index] = (method == null || arg == null) ? arg : method.invoke(arg, new Object[0]);
                if (index == last) {
                    break;
                }
                index++;
            }
        }
        Object result = this.caller.call(unboxed);
        return (box == null || (objInvoke = box.invoke(null, result)) == null) ? result : objInvoke;
    }
}
