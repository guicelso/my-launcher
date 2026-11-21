package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: utfEncoding.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class UtfEncodingKt {
    @NotNull
    public static final byte[] stringsToBytes(@NotNull String[] strings) {
        int i;
        Intrinsics.checkParameterIsNotNull(strings, "strings");
        int sum$iv = 0;
        for (String str : strings) {
            sum$iv += str.length();
        }
        int resultLength = sum$iv;
        byte[] result = new byte[resultLength];
        int i2 = 0;
        for (String s : strings) {
            int length = s.length() - 1;
            if (length >= 0) {
                int si = 0;
                while (true) {
                    i = i2 + 1;
                    result[i2] = (byte) s.charAt(si);
                    if (si == length) {
                        break;
                    }
                    si++;
                    i2 = i;
                }
                i2 = i;
            }
        }
        boolean z = i2 == result.length;
        if (!_Assertions.ENABLED || z) {
            return result;
        }
        throw new AssertionError("Should have reached the end");
    }
}
