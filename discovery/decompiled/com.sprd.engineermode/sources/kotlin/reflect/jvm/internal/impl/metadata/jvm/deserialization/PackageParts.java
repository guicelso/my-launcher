package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class PackageParts {

    @NotNull
    private final Set<String> metadataParts;

    @NotNull
    private final String packageFqName;
    private final LinkedHashMap<String, String> packageParts;

    public PackageParts(@NotNull String packageFqName) {
        Intrinsics.checkParameterIsNotNull(packageFqName, "packageFqName");
        this.packageFqName = packageFqName;
        this.packageParts = new LinkedHashMap<>();
        this.metadataParts = new LinkedHashSet();
    }

    @NotNull
    public final Set<String> getParts() {
        Set<String> setKeySet = this.packageParts.keySet();
        Intrinsics.checkExpressionValueIsNotNull(setKeySet, "packageParts.keys");
        return setKeySet;
    }

    public final void addPart(@NotNull String partInternalName, @Nullable String facadeInternalName) {
        Intrinsics.checkParameterIsNotNull(partInternalName, "partInternalName");
        this.packageParts.put(partInternalName, facadeInternalName);
    }

    public final void addMetadataPart(@NotNull String shortName) {
        Intrinsics.checkParameterIsNotNull(shortName, "shortName");
        Set<String> set = this.metadataParts;
        if (set == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableSet<kotlin.String>");
        }
        TypeIntrinsics.asMutableSet(set).add(shortName);
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof PackageParts) && Intrinsics.areEqual(((PackageParts) other).packageFqName, this.packageFqName) && Intrinsics.areEqual(((PackageParts) other).packageParts, this.packageParts) && Intrinsics.areEqual(((PackageParts) other).metadataParts, this.metadataParts);
    }

    public int hashCode() {
        return (((this.packageFqName.hashCode() * 31) + this.packageParts.hashCode()) * 31) + this.metadataParts.hashCode();
    }

    @NotNull
    public String toString() {
        return SetsKt.plus((Set) getParts(), (Iterable) this.metadataParts).toString();
    }
}
