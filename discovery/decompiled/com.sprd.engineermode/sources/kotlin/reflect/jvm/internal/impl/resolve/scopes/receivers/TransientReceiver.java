package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class TransientReceiver extends AbstractReceiverValue {
    public TransientReceiver(@NotNull KotlinType type) {
        this(type, null);
    }

    private TransientReceiver(@NotNull KotlinType type, @Nullable ReceiverValue original) {
        super(type, original);
    }

    public String toString() {
        return "{Transient} : " + getType();
    }
}
