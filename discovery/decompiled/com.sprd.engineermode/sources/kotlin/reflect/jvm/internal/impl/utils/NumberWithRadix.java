package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: numbers.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NumberWithRadix {

    @NotNull
    private final String number;
    private final int radix;

    @NotNull
    public final String component1() {
        return this.number;
    }

    public final int component2() {
        return this.radix;
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            if (obj instanceof NumberWithRadix) {
                NumberWithRadix numberWithRadix = (NumberWithRadix) obj;
                if (Intrinsics.areEqual(this.number, numberWithRadix.number)) {
                    if (this.radix == numberWithRadix.radix) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.number;
        return ((str != null ? str.hashCode() : 0) * 31) + this.radix;
    }

    @NotNull
    public String toString() {
        return "NumberWithRadix(number=" + this.number + ", radix=" + this.radix + ")";
    }

    public NumberWithRadix(@NotNull String number, int radix) {
        Intrinsics.checkParameterIsNotNull(number, "number");
        this.number = number;
        this.radix = radix;
    }
}
