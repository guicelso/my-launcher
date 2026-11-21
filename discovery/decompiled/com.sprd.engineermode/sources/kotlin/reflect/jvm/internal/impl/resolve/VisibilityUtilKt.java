package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: VisibilityUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class VisibilityUtilKt {
    @NotNull
    public static final CallableMemberDescriptor findMemberWithMaxVisibility(@NotNull Collection<? extends CallableMemberDescriptor> descriptors) {
        Intrinsics.checkParameterIsNotNull(descriptors, "descriptors");
        boolean z = !descriptors.isEmpty();
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
        CallableMemberDescriptor descriptor = (CallableMemberDescriptor) null;
        for (CallableMemberDescriptor candidate : descriptors) {
            if (descriptor == null) {
                descriptor = candidate;
            } else {
                Integer result = Visibilities.compare(descriptor.getVisibility(), candidate.getVisibility());
                if (result != null && result.intValue() < 0) {
                    descriptor = candidate;
                }
            }
        }
        if (descriptor == null) {
            Intrinsics.throwNpe();
        }
        return descriptor;
    }
}
