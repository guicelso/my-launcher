package kotlin.reflect.jvm.internal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KClassImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "Lkotlin/reflect/jvm/internal/KTypeImpl;", "T", "", "invoke"}, k = 3, mv = {1, 1, 13})
final class KClassImpl$Data$supertypes$2 extends Lambda implements Function0<List<? extends KTypeImpl>> {
    final /* synthetic */ KClassImpl.Data this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KClassImpl$Data$supertypes$2(KClassImpl.Data data) {
        super(0);
        this.this$0 = data;
    }

    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final List<? extends KTypeImpl> invoke() {
        TypeConstructor typeConstructor = this.this$0.getDescriptor().getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "descriptor.typeConstructor");
        Collection<KotlinType> $receiver$iv = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "descriptor.typeConstructor.supertypes");
        ArrayList result = new ArrayList($receiver$iv.size());
        for (Object item$iv : $receiver$iv) {
            final KotlinType kotlinType = (KotlinType) item$iv;
            Intrinsics.checkExpressionValueIsNotNull(kotlinType, "kotlinType");
            result.add(new KTypeImpl(kotlinType, new Function0<Type>() { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$supertypes$2$$special$$inlined$mapTo$lambda$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final Type invoke() {
                    ClassifierDescriptor superClass = kotlinType.getConstructor().mo381getDeclarationDescriptor();
                    if (!(superClass instanceof ClassDescriptor)) {
                        throw new KotlinReflectionInternalError("Supertype not a class: " + superClass);
                    }
                    Class<?> javaClass = UtilKt.toJavaClass((ClassDescriptor) superClass);
                    if (javaClass == null) {
                        throw new KotlinReflectionInternalError("Unsupported superclass of " + this.this$0 + ": " + superClass);
                    }
                    if (Intrinsics.areEqual(KClassImpl.this.getJClass().getSuperclass(), javaClass)) {
                        Type genericSuperclass = KClassImpl.this.getJClass().getGenericSuperclass();
                        Intrinsics.checkExpressionValueIsNotNull(genericSuperclass, "jClass.genericSuperclass");
                        return genericSuperclass;
                    }
                    Class<?>[] interfaces = KClassImpl.this.getJClass().getInterfaces();
                    Intrinsics.checkExpressionValueIsNotNull(interfaces, "jClass.interfaces");
                    int index = ArraysKt.indexOf(interfaces, javaClass);
                    if (index < 0) {
                        throw new KotlinReflectionInternalError("No superclass of " + this.this$0 + " in Java reflection for " + superClass);
                    }
                    Type type = KClassImpl.this.getJClass().getGenericInterfaces()[index];
                    Intrinsics.checkExpressionValueIsNotNull(type, "jClass.genericInterfaces[index]");
                    return type;
                }
            }));
        }
        if (!KotlinBuiltIns.isSpecialClassWithNoSupertypes(this.this$0.getDescriptor())) {
            ArrayList $receiver$iv2 = result;
            boolean z = true;
            if (!($receiver$iv2 instanceof Collection) || !$receiver$iv2.isEmpty()) {
                for (Object element$iv : $receiver$iv2) {
                    KTypeImpl it = (KTypeImpl) element$iv;
                    ClassDescriptor classDescriptorForType = DescriptorUtils.getClassDescriptorForType(it.getType());
                    Intrinsics.checkExpressionValueIsNotNull(classDescriptorForType, "DescriptorUtils.getClassDescriptorForType(it.type)");
                    ClassKind classKind = classDescriptorForType.getKind();
                    Intrinsics.checkExpressionValueIsNotNull(classKind, "DescriptorUtils.getClass…ptorForType(it.type).kind");
                    if (!(classKind == ClassKind.INTERFACE || classKind == ClassKind.ANNOTATION_CLASS)) {
                        z = false;
                        break;
                    }
                }
            }
            if (z) {
                SimpleType anyType = DescriptorUtilsKt.getBuiltIns(this.this$0.getDescriptor()).getAnyType();
                Intrinsics.checkExpressionValueIsNotNull(anyType, "descriptor.builtIns.anyType");
                result.add(new KTypeImpl(anyType, new Function0<Class<Object>>() { // from class: kotlin.reflect.jvm.internal.KClassImpl$Data$supertypes$2.3
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    @NotNull
                    public final Class<Object> invoke() {
                        return Object.class;
                    }
                }));
            }
        }
        return CollectionsKt.compact(result);
    }
}
