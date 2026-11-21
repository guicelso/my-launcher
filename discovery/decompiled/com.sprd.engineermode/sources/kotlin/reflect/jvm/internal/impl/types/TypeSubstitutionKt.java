package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeSubstitutionKt {
    @JvmOverloads
    @NotNull
    public static /* synthetic */ KotlinType replace$default(KotlinType kotlinType, List list, Annotations annotations, int i, Object obj) {
        if ((i & 1) != 0) {
            list = kotlinType.getArguments();
        }
        if ((i & 2) != 0) {
            annotations = kotlinType.getAnnotations();
        }
        return replace(kotlinType, (List<? extends TypeProjection>) list, annotations);
    }

    @JvmOverloads
    @NotNull
    public static final KotlinType replace(@NotNull KotlinType receiver$0, @NotNull List<? extends TypeProjection> newArguments, @NotNull Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(newArguments, "newArguments");
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        if ((newArguments.isEmpty() || newArguments == receiver$0.getArguments()) && newAnnotations == receiver$0.getAnnotations()) {
            return receiver$0;
        }
        UnwrappedType unwrapped = receiver$0.unwrap();
        if (unwrapped instanceof FlexibleType) {
            return KotlinTypeFactory.flexibleType(replace(((FlexibleType) unwrapped).getLowerBound(), newArguments, newAnnotations), replace(((FlexibleType) unwrapped).getUpperBound(), newArguments, newAnnotations));
        }
        if (unwrapped instanceof SimpleType) {
            return replace((SimpleType) unwrapped, newArguments, newAnnotations);
        }
        throw new NoWhenBranchMatchedException();
    }

    @JvmOverloads
    @NotNull
    public static /* synthetic */ SimpleType replace$default(SimpleType simpleType, List list, Annotations annotations, int i, Object obj) {
        if ((i & 1) != 0) {
            list = simpleType.getArguments();
        }
        if ((i & 2) != 0) {
            annotations = simpleType.getAnnotations();
        }
        return replace(simpleType, (List<? extends TypeProjection>) list, annotations);
    }

    @JvmOverloads
    @NotNull
    public static final SimpleType replace(@NotNull SimpleType receiver$0, @NotNull List<? extends TypeProjection> newArguments, @NotNull Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(newArguments, "newArguments");
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        if (newArguments.isEmpty() && newAnnotations == receiver$0.getAnnotations()) {
            return receiver$0;
        }
        if (newArguments.isEmpty()) {
            return receiver$0.replaceAnnotations(newAnnotations);
        }
        return KotlinTypeFactory.simpleType(newAnnotations, receiver$0.getConstructor(), newArguments, receiver$0.isMarkedNullable());
    }

    @NotNull
    public static final SimpleType asSimpleType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType unwrappedTypeUnwrap = receiver$0.unwrap();
        if (!(unwrappedTypeUnwrap instanceof SimpleType)) {
            unwrappedTypeUnwrap = null;
        }
        SimpleType simpleType = (SimpleType) unwrappedTypeUnwrap;
        if (simpleType != null) {
            return simpleType;
        }
        throw new IllegalStateException(("This is should be simple type: " + receiver$0).toString());
    }
}
