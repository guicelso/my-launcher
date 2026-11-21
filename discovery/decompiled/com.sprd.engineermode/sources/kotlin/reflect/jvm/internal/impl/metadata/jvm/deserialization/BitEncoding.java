package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.UByte;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public class BitEncoding {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean FORCE_8TO7_ENCODING;

    static {
        String use8to7;
        try {
            use8to7 = System.getProperty("kotlin.jvm.serialization.use8to7");
        } catch (SecurityException e) {
            use8to7 = null;
        }
        FORCE_8TO7_ENCODING = "true".equals(use8to7);
    }

    private BitEncoding() {
    }

    private static void addModuloByte(@NotNull byte[] data, int increment) {
        int n = data.length;
        for (int i = 0; i < n; i++) {
            data[i] = (byte) ((data[i] + increment) & 127);
        }
    }

    @NotNull
    public static byte[] decodeBytes(@NotNull String[] data) {
        if (data.length > 0 && !data[0].isEmpty()) {
            char possibleMarker = data[0].charAt(0);
            if (possibleMarker == 0) {
                return UtfEncodingKt.stringsToBytes(dropMarker(data));
            }
            if (possibleMarker == 65535) {
                data = dropMarker(data);
            }
        }
        byte[] bytes = combineStringArrayIntoBytes(data);
        addModuloByte(bytes, 127);
        return decode7to8(bytes);
    }

    @NotNull
    private static String[] dropMarker(@NotNull String[] data) {
        String[] result = (String[]) data.clone();
        result[0] = result[0].substring(1);
        return result;
    }

    @NotNull
    private static byte[] combineStringArrayIntoBytes(@NotNull String[] data) {
        int resultLength = 0;
        for (String str : data) {
            resultLength += str.length();
        }
        byte[] result = new byte[resultLength];
        int p = 0;
        for (String s : data) {
            int i = 0;
            int n = s.length();
            while (i < n) {
                result[p] = (byte) s.charAt(i);
                i++;
                p++;
            }
        }
        return result;
    }

    @NotNull
    private static byte[] decode7to8(@NotNull byte[] data) {
        int resultLength = (data.length * 7) / 8;
        byte[] result = new byte[resultLength];
        int byteIndex = 0;
        int bit = 0;
        for (int i = 0; i < resultLength; i++) {
            int firstPart = (data[byteIndex] & UByte.MAX_VALUE) >>> bit;
            byteIndex++;
            int secondPart = (data[byteIndex] & ((1 << (bit + 1)) - 1)) << (7 - bit);
            result[i] = (byte) (firstPart + secondPart);
            if (bit == 6) {
                byteIndex++;
                bit = 0;
            } else {
                bit++;
            }
        }
        return result;
    }
}
