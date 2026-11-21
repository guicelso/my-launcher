package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeCapabilities.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeCapabilitiesKt {
    public static final boolean isCustomTypeVariable(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object objUnwrap = receiver$0.unwrap();
        if (!(objUnwrap instanceof CustomTypeVariable)) {
            objUnwrap = null;
        }
        CustomTypeVariable customTypeVariable = (CustomTypeVariable) objUnwrap;
        if (customTypeVariable != null) {
            return customTypeVariable.isTypeVariable();
        }
        return false;
    }

    @Nullable
    public static final CustomTypeVariable getCustomTypeVariable(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object objUnwrap = receiver$0.unwrap();
        if (!(objUnwrap instanceof CustomTypeVariable)) {
            objUnwrap = null;
        }
        CustomTypeVariable it = (CustomTypeVariable) objUnwrap;
        if (it == null || !it.isTypeVariable()) {
            return null;
        }
        return it;
    }

    @NotNull
    public static final KotlinType getSubtypeRepresentative(@NotNull KotlinType receiver$0) {
        KotlinType subTypeRepresentative;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object objUnwrap = receiver$0.unwrap();
        if (!(objUnwrap instanceof SubtypingRepresentatives)) {
            objUnwrap = null;
        }
        SubtypingRepresentatives subtypingRepresentatives = (SubtypingRepresentatives) objUnwrap;
        return (subtypingRepresentatives == null || (subTypeRepresentative = subtypingRepresentatives.getSubTypeRepresentative()) == null) ? receiver$0 : subTypeRepresentative;
    }

    @NotNull
    public static final KotlinType getSupertypeRepresentative(@NotNull KotlinType receiver$0) {
        KotlinType superTypeRepresentative;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Object objUnwrap = receiver$0.unwrap();
        if (!(objUnwrap instanceof SubtypingRepresentatives)) {
            objUnwrap = null;
        }
        SubtypingRepresentatives subtypingRepresentatives = (SubtypingRepresentatives) objUnwrap;
        return (subtypingRepresentatives == null || (superTypeRepresentative = subtypingRepresentatives.getSuperTypeRepresentative()) == null) ? receiver$0 : superTypeRepresentative;
    }

    public static final boolean sameTypeConstructors(@NotNull KotlinType first, @NotNull KotlinType second) {
        Intrinsics.checkParameterIsNotNull(first, "first");
        Intrinsics.checkParameterIsNotNull(second, "second");
        Object objUnwrap = first.unwrap();
        if (!(objUnwrap instanceof SubtypingRepresentatives)) {
            objUnwrap = null;
        }
        SubtypingRepresentatives subtypingRepresentatives = (SubtypingRepresentatives) objUnwrap;
        if (!(subtypingRepresentatives != null ? subtypingRepresentatives.sameTypeConstructor(second) : false)) {
            Object objUnwrap2 = second.unwrap();
            if (!(objUnwrap2 instanceof SubtypingRepresentatives)) {
                objUnwrap2 = null;
            }
            SubtypingRepresentatives subtypingRepresentatives2 = (SubtypingRepresentatives) objUnwrap2;
            if (!(subtypingRepresentatives2 != null ? subtypingRepresentatives2.sameTypeConstructor(first) : false)) {
                return false;
            }
        }
        return true;
    }
}
