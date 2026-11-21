package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Modality.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ModalityKt {
    public static final boolean isFinalClass(@NotNull ClassDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.getModality() == Modality.FINAL && receiver$0.getKind() != ClassKind.ENUM_CLASS;
    }
}
