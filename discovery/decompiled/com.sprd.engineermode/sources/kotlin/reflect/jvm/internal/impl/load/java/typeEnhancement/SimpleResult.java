package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
final class SimpleResult extends Result {

    @NotNull
    private final SimpleType type;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleResult(@NotNull SimpleType type, int subtreeSize, boolean wereChanges) {
        super(type, subtreeSize, wereChanges);
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.Result
    @NotNull
    public SimpleType getType() {
        return this.type;
    }
}
