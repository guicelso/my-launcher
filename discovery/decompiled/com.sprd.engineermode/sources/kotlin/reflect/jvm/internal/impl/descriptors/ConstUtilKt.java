package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ConstUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ConstUtilKt {
    public static final boolean canBeUsedForConstVal(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return ((KotlinBuiltIns.isPrimitiveType(receiver$0) || UnsignedTypes.INSTANCE.isUnsignedType(receiver$0)) && !TypeUtils.isNullableType(receiver$0)) || KotlinBuiltIns.isString(receiver$0);
    }
}
