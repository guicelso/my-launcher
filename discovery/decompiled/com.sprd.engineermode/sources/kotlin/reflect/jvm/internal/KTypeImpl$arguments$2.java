package kotlin.reflect.jvm.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "", "Lkotlin/reflect/KTypeProjection;", "invoke"}, k = 3, mv = {1, 1, 13})
final class KTypeImpl$arguments$2 extends Lambda implements Function0<List<? extends KTypeProjection>> {
    final /* synthetic */ KTypeImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KTypeImpl$arguments$2(KTypeImpl kTypeImpl) {
        super(0);
        this.this$0 = kTypeImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final List<? extends KTypeProjection> invoke() {
        List<TypeProjection> list;
        Iterable $receiver$iv;
        KTypeProjection kTypeProjectionInvariant;
        List<TypeProjection> arguments = this.this$0.getType().getArguments();
        if (arguments.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        final Lazy parameterizedTypeArguments = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends Type>>() { // from class: kotlin.reflect.jvm.internal.KTypeImpl$arguments$2$parameterizedTypeArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends Type> invoke() {
                return ReflectClassUtilKt.getParameterizedTypeArguments(this.this$0.this$0.getJavaType$kotlin_reflect_api());
            }
        });
        final KProperty kProperty = KTypeImpl.$$delegatedProperties[3];
        List<TypeProjection> $receiver$iv2 = arguments;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        final int i = 0;
        for (Object item$iv$iv : $receiver$iv2) {
            int index$iv$iv = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) item$iv$iv;
            if (typeProjection.isStarProjection()) {
                list = arguments;
                $receiver$iv = $receiver$iv2;
                kTypeProjectionInvariant = KTypeProjection.INSTANCE.getSTAR();
            } else {
                list = arguments;
                KotlinType type = typeProjection.getType();
                $receiver$iv = $receiver$iv2;
                Intrinsics.checkExpressionValueIsNotNull(type, "typeProjection.type");
                KTypeImpl type2 = new KTypeImpl(type, new Function0<Type>() { // from class: kotlin.reflect.jvm.internal.KTypeImpl$arguments$2$$special$$inlined$mapIndexed$lambda$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    @NotNull
                    public final Type invoke() {
                        Type argument;
                        Type javaType = this.this$0.getJavaType$kotlin_reflect_api();
                        if (javaType instanceof Class) {
                            Class<?> componentType = ((Class) javaType).isArray() ? ((Class) javaType).getComponentType() : Object.class;
                            Intrinsics.checkExpressionValueIsNotNull(componentType, "if (javaType.isArray) ja…Type else Any::class.java");
                            return componentType;
                        }
                        if (javaType instanceof GenericArrayType) {
                            if (i != 0) {
                                throw new KotlinReflectionInternalError("Array type has been queried for a non-0th argument: " + this.this$0);
                            }
                            Type genericComponentType = ((GenericArrayType) javaType).getGenericComponentType();
                            Intrinsics.checkExpressionValueIsNotNull(genericComponentType, "javaType.genericComponentType");
                            return genericComponentType;
                        }
                        if (javaType instanceof ParameterizedType) {
                            Lazy lazy = parameterizedTypeArguments;
                            KProperty kProperty2 = kProperty;
                            Type argument2 = (Type) ((List) lazy.getValue()).get(i);
                            if (argument2 instanceof WildcardType) {
                                Type[] lowerBounds = ((WildcardType) argument2).getLowerBounds();
                                Intrinsics.checkExpressionValueIsNotNull(lowerBounds, "argument.lowerBounds");
                                argument = (Type) ArraysKt.firstOrNull(lowerBounds);
                                if (argument == null) {
                                    Type[] upperBounds = ((WildcardType) argument2).getUpperBounds();
                                    Intrinsics.checkExpressionValueIsNotNull(upperBounds, "argument.upperBounds");
                                    argument = (Type) ArraysKt.first(upperBounds);
                                }
                            } else {
                                argument = argument2;
                            }
                            Intrinsics.checkExpressionValueIsNotNull(argument, "if (argument !is Wildcar…ument.upperBounds.first()");
                            return argument;
                        }
                        throw new KotlinReflectionInternalError("Non-generic type has been queried for arguments: " + this.this$0);
                    }
                });
                int i2 = KTypeImpl.WhenMappings.$EnumSwitchMapping$0[typeProjection.getProjectionKind().ordinal()];
                if (i2 == 1) {
                    kTypeProjectionInvariant = KTypeProjection.INSTANCE.invariant(type2);
                } else if (i2 == 2) {
                    kTypeProjectionInvariant = KTypeProjection.INSTANCE.contravariant(type2);
                } else {
                    if (i2 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    kTypeProjectionInvariant = KTypeProjection.INSTANCE.covariant(type2);
                }
            }
            destination$iv$iv.add(kTypeProjectionInvariant);
            i = index$iv$iv;
            arguments = list;
            $receiver$iv2 = $receiver$iv;
        }
        return (List) destination$iv$iv;
    }
}
