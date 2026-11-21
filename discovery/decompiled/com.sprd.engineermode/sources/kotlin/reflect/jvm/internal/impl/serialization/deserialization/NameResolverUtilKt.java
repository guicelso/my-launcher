package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: NameResolverUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NameResolverUtilKt {
    @NotNull
    public static final ClassId getClassId(@NotNull NameResolver receiver$0, int index) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassId classIdFromString = ClassId.fromString(receiver$0.getQualifiedClassName(index), receiver$0.isLocalClassName(index));
        Intrinsics.checkExpressionValueIsNotNull(classIdFromString, "ClassId.fromString(getQu… isLocalClassName(index))");
        return classIdFromString;
    }

    @NotNull
    public static final Name getName(@NotNull NameResolver receiver$0, int index) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Name nameGuessByFirstCharacter = Name.guessByFirstCharacter(receiver$0.getString(index));
        Intrinsics.checkExpressionValueIsNotNull(nameGuessByFirstCharacter, "Name.guessByFirstCharacter(getString(index))");
        return nameGuessByFirstCharacter;
    }
}
