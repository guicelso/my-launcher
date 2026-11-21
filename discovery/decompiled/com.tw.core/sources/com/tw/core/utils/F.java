package com.tw.core.utils;

/* JADX INFO: loaded from: classes.dex */
public class F {
    public static final char[] ay = "0123456789ABCDEF".toCharArray();
    private static char[] ax = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int cP(byte[] bArr, int i) {
        if (i == 1) {
            return bArr[0] & 255;
        }
        if (i == 2) {
            return ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        }
        if (i == 4) {
            return ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255);
        }
        if (i == 6) {
            return ((bArr[0] & 255) << 40) | ((bArr[0] & 255) << 32) | ((bArr[2] & 255) << 24) | ((bArr[3] & 255) << 16) | ((bArr[4] & 255) << 8) | (bArr[5] & 255);
        }
        if (i == 8) {
            return ((bArr[0] & 255) << 56) | ((bArr[1] & 255) << 48) | ((bArr[2] & 255) << 40) | ((bArr[3] & 255) << 32) | ((bArr[4] & 255) << 24) | ((bArr[5] & 255) << 16) | ((bArr[6] & 255) << 8) | (bArr[7] & 255);
        }
        return -1;
    }

    public static byte[] cQ(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (cR(charArray[i2 + 1]) | (cR(charArray[i2]) << 4));
        }
        return bArr;
    }

    public static byte cR(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
