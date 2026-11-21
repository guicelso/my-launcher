package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
final class TypeAndDefaultQualifiers {

    @Nullable
    private final JavaTypeQualifiers defaultQualifiers;

    @NotNull
    private final KotlinType type;

    @NotNull
    public final KotlinType component1() {
        return this.type;
    }

    @Nullable
    public final JavaTypeQualifiers component2() {
        return this.defaultQualifiers;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeAndDefaultQualifiers)) {
            return false;
        }
        TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers) obj;
        return Intrinsics.areEqual(this.type, typeAndDefaultQualifiers.type) && Intrinsics.areEqual(this.defaultQualifiers, typeAndDefaultQualifiers.defaultQualifiers);
    }

    public int hashCode() {
        KotlinType kotlinType = this.type;
        int iHashCode = (kotlinType != null ? kotlinType.hashCode() : 0) * 31;
        JavaTypeQualifiers javaTypeQualifiers = this.defaultQualifiers;
        return iHashCode + (javaTypeQualifiers != null ? javaTypeQualifiers.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.type + ", defaultQualifiers=" + this.defaultQualifiers + ")";
    }

    public TypeAndDefaultQualifiers(@NotNull KotlinType type, @Nullable JavaTypeQualifiers defaultQualifiers) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.type = type;
        this.defaultQualifiers = defaultQualifiers;
    }

    @NotNull
    public final KotlinType getType() {
        return this.type;
    }
}
