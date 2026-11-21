package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NewKotlinTypeCheckerKt {
    public static final boolean isClassType(@NotNull SimpleType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.getConstructor().mo381getDeclarationDescriptor() instanceof ClassDescriptor;
    }

    public static final boolean isSingleClassifierType(@NotNull SimpleType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return (KotlinTypeKt.isError(receiver$0) || (receiver$0.getConstructor().mo381getDeclarationDescriptor() instanceof TypeAliasDescriptor) || (receiver$0.getConstructor().mo381getDeclarationDescriptor() == null && !(receiver$0 instanceof CapturedType) && !(receiver$0 instanceof NewCapturedType) && !(receiver$0 instanceof DefinitelyNotNullType))) ? false : true;
    }

    public static final boolean isIntersectionType(@NotNull SimpleType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.getConstructor() instanceof IntersectionTypeConstructor;
    }
}
