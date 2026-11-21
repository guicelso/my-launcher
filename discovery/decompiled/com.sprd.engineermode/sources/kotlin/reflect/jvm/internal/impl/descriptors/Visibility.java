package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Visibility.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class Visibility {
    private final boolean isPublicAPI;

    @NotNull
    private final String name;

    public abstract boolean isVisible(@Nullable ReceiverValue receiverValue, @NotNull DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, @NotNull DeclarationDescriptor declarationDescriptor);

    protected Visibility(@NotNull String name, boolean isPublicAPI) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.name = name;
        this.isPublicAPI = isPublicAPI;
    }

    public final boolean isPublicAPI() {
        return this.isPublicAPI;
    }

    @Nullable
    protected Integer compareTo(@NotNull Visibility visibility) {
        Intrinsics.checkParameterIsNotNull(visibility, "visibility");
        return Visibilities.compareLocal(this, visibility);
    }

    @NotNull
    public String getDisplayName() {
        return this.name;
    }

    @NotNull
    public final String toString() {
        return getDisplayName();
    }

    @NotNull
    public Visibility normalize() {
        return this;
    }
}
