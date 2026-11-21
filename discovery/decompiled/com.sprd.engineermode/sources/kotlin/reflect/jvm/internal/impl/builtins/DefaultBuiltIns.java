package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: DefaultBuiltIns.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DefaultBuiltIns extends KotlinBuiltIns {
    public static final Companion Companion = new Companion(null);
    private static final BuiltInsInitializer<DefaultBuiltIns> initializer = new BuiltInsInitializer<>(new Function0<DefaultBuiltIns>() { // from class: kotlin.reflect.jvm.internal.impl.builtins.DefaultBuiltIns$Companion$initializer$1
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final DefaultBuiltIns invoke() {
            return new DefaultBuiltIns(null);
        }
    });

    @NotNull
    public static final DefaultBuiltIns getInstance() {
        return Companion.getInstance();
    }

    private DefaultBuiltIns() {
        super(new LockBasedStorageManager());
        createBuiltInsModule();
    }

    public /* synthetic */ DefaultBuiltIns(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* JADX INFO: compiled from: DefaultBuiltIns.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final DefaultBuiltIns getInstance() {
            return (DefaultBuiltIns) DefaultBuiltIns.initializer.get();
        }
    }
}
