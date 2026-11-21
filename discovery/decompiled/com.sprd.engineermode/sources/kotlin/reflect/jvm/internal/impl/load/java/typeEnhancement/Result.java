package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: typeEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
class Result {
    private final int subtreeSize;

    @NotNull
    private final KotlinType type;
    private final boolean wereChanges;

    public Result(@NotNull KotlinType type, int subtreeSize, boolean wereChanges) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
        this.subtreeSize = subtreeSize;
        this.wereChanges = wereChanges;
    }

    public final int getSubtreeSize() {
        return this.subtreeSize;
    }

    @NotNull
    public KotlinType getType() {
        return this.type;
    }

    public final boolean getWereChanges() {
        return this.wereChanges;
    }

    @Nullable
    public final KotlinType getTypeIfChanged() {
        KotlinType type = getType();
        if (this.wereChanges) {
            return type;
        }
        return null;
    }
}
