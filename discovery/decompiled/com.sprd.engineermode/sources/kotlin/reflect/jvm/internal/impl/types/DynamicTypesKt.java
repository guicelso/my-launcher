package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: dynamicTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DynamicTypesKt {
    public static final boolean isDynamic(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.unwrap() instanceof DynamicType;
    }
}
