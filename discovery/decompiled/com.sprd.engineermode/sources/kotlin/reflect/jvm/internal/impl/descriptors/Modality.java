package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: Modality.kt */
/* JADX INFO: loaded from: classes4.dex */
public enum Modality {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT;

    public static final Companion Companion = new Companion(null);

    /* JADX INFO: compiled from: Modality.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Modality convertFromFlags(boolean z, boolean open) {
            if (z) {
                return Modality.ABSTRACT;
            }
            return open ? Modality.OPEN : Modality.FINAL;
        }
    }
}
