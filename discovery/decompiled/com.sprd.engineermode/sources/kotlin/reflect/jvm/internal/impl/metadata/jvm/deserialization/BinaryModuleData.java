package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: BinaryModuleData.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class BinaryModuleData {

    @NotNull
    private final List<String> annotations;

    public BinaryModuleData(@NotNull List<String> annotations) {
        Intrinsics.checkParameterIsNotNull(annotations, "annotations");
        this.annotations = annotations;
    }
}
