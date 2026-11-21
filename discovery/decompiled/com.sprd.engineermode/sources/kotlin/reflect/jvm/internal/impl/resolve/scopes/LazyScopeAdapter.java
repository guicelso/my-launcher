package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: LazyScopeAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class LazyScopeAdapter extends AbstractScopeAdapter {
    private final NotNullLazyValue<MemberScope> scope;

    /* JADX WARN: Multi-variable type inference failed */
    public LazyScopeAdapter(@NotNull NotNullLazyValue<? extends MemberScope> scope) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        this.scope = scope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.AbstractScopeAdapter
    @NotNull
    protected MemberScope getWorkerScope() {
        return this.scope.invoke();
    }
}
