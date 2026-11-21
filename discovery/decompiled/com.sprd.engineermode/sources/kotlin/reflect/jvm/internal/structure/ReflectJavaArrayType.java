package kotlin.reflect.jvm.internal.structure;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ReflectJavaArrayType.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lkotlin/reflect/jvm/internal/structure/ReflectJavaArrayType;", "Lkotlin/reflect/jvm/internal/structure/ReflectJavaType;", "Lkotlin/reflect/jvm/internal/impl/load/java/structure/JavaArrayType;", "reflectType", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Type;)V", "componentType", "getComponentType", "()Lkotlin/reflect/jvm/internal/structure/ReflectJavaType;", "getReflectType", "()Ljava/lang/reflect/Type;", "descriptors.runtime"}, k = 1, mv = {1, 1, 13})
public final class ReflectJavaArrayType extends ReflectJavaType implements JavaArrayType {

    @NotNull
    private final ReflectJavaType componentType;

    @NotNull
    private final Type reflectType;

    public ReflectJavaArrayType(@NotNull Type reflectType) {
        ReflectJavaType reflectJavaTypeCreate;
        Intrinsics.checkParameterIsNotNull(reflectType, "reflectType");
        this.reflectType = reflectType;
        Type $receiver = getReflectType();
        if ($receiver instanceof GenericArrayType) {
            ReflectJavaType.Companion factory = ReflectJavaType.INSTANCE;
            Type genericComponentType = ((GenericArrayType) $receiver).getGenericComponentType();
            Intrinsics.checkExpressionValueIsNotNull(genericComponentType, "genericComponentType");
            reflectJavaTypeCreate = factory.create(genericComponentType);
        } else {
            if (!($receiver instanceof Class) || !((Class) $receiver).isArray()) {
                throw new IllegalArgumentException("Not an array type (" + getReflectType().getClass() + "): " + getReflectType());
            }
            ReflectJavaType.Companion factory2 = ReflectJavaType.INSTANCE;
            Class<?> componentType = ((Class) $receiver).getComponentType();
            Intrinsics.checkExpressionValueIsNotNull(componentType, "getComponentType()");
            reflectJavaTypeCreate = factory2.create(componentType);
        }
        this.componentType = reflectJavaTypeCreate;
    }

    @Override // kotlin.reflect.jvm.internal.structure.ReflectJavaType
    @NotNull
    protected Type getReflectType() {
        return this.reflectType;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType
    @NotNull
    public ReflectJavaType getComponentType() {
        return this.componentType;
    }
}
