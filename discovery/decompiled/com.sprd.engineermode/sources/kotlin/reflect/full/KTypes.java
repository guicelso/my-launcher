package kotlin.reflect.full;

import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KType;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0004\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0007\u001a\u0014\u0010\u0005\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001H\u0007¨\u0006\u0007"}, d2 = {"isSubtypeOf", "", "Lkotlin/reflect/KType;", "other", "isSupertypeOf", "withNullability", "nullable", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KTypes")
public final class KTypes {
    @SinceKotlin(version = "1.1")
    @NotNull
    public static final KType withNullability(@NotNull final KType receiver$0, boolean nullable) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0.isMarkedNullable()) {
            if (nullable) {
                return receiver$0;
            }
            KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(((KTypeImpl) receiver$0).getType());
            Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNotNullable, "TypeUtils.makeNotNullabl…(this as KTypeImpl).type)");
            return new KTypeImpl(kotlinTypeMakeNotNullable, new Function0<Type>() { // from class: kotlin.reflect.full.KTypes.withNullability.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final Type invoke() {
                    return ((KTypeImpl) receiver$0).getJavaType$kotlin_reflect_api();
                }
            });
        }
        KotlinType kotlinType = ((KTypeImpl) receiver$0).getType();
        if (FlexibleTypesKt.isFlexible(kotlinType)) {
            KotlinType kotlinTypeMakeNullableAsSpecified = TypeUtils.makeNullableAsSpecified(kotlinType, nullable);
            Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNullableAsSpecified, "TypeUtils.makeNullableAs…ied(kotlinType, nullable)");
            return new KTypeImpl(kotlinTypeMakeNullableAsSpecified, new Function0<Type>() { // from class: kotlin.reflect.full.KTypes.withNullability.2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                @NotNull
                public final Type invoke() {
                    return ((KTypeImpl) receiver$0).getJavaType$kotlin_reflect_api();
                }
            });
        }
        if (!nullable) {
            return receiver$0;
        }
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(kotlinType);
        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNullable, "TypeUtils.makeNullable(kotlinType)");
        return new KTypeImpl(kotlinTypeMakeNullable, new Function0<Type>() { // from class: kotlin.reflect.full.KTypes.withNullability.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Type invoke() {
                return ((KTypeImpl) receiver$0).getJavaType$kotlin_reflect_api();
            }
        });
    }

    @SinceKotlin(version = "1.1")
    public static final boolean isSubtypeOf(@NotNull KType receiver$0, @NotNull KType other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return TypeUtilsKt.isSubtypeOf(((KTypeImpl) receiver$0).getType(), ((KTypeImpl) other).getType());
    }

    @SinceKotlin(version = "1.1")
    public static final boolean isSupertypeOf(@NotNull KType receiver$0, @NotNull KType other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return isSubtypeOf(other, receiver$0);
    }
}
