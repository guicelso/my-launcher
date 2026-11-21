package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: MultiTargetPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class MultiTargetPlatform implements Comparable<MultiTargetPlatform> {
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final ModuleDescriptor.Capability<MultiTargetPlatform> CAPABILITY = new ModuleDescriptor.Capability<>("MULTI_TARGET_PLATFORM");

    private MultiTargetPlatform() {
    }

    /* JADX INFO: compiled from: MultiTargetPlatform.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
