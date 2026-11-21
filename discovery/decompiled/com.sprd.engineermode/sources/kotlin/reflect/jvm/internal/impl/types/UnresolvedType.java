package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ErrorType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UnresolvedType extends ErrorType {

    @NotNull
    private final String presentableName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnresolvedType(@NotNull String presentableName, @NotNull TypeConstructor constructor, @NotNull MemberScope memberScope, @NotNull List<? extends TypeProjection> arguments, boolean isMarkedNullable) {
        super(constructor, memberScope, arguments, isMarkedNullable);
        Intrinsics.checkParameterIsNotNull(presentableName, "presentableName");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        Intrinsics.checkParameterIsNotNull(memberScope, "memberScope");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        this.presentableName = presentableName;
    }

    @NotNull
    public final String getPresentableName() {
        return this.presentableName;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.ErrorType, kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return new UnresolvedType(this.presentableName, getConstructor(), getMemberScope(), getArguments(), newNullability);
    }
}
