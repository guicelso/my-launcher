package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: util.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ValueParameterData {
    private final boolean hasDefaultValue;

    @NotNull
    private final KotlinType type;

    public ValueParameterData(@NotNull KotlinType type, boolean hasDefaultValue) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
        this.hasDefaultValue = hasDefaultValue;
    }

    public final boolean getHasDefaultValue() {
        return this.hasDefaultValue;
    }

    @NotNull
    public final KotlinType getType() {
        return this.type;
    }
}
