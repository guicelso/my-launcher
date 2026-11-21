package com.google.zxing.qrcode.encoder;

/* JADX INFO: compiled from: MaskUtil.java */
/* JADX INFO: loaded from: classes.dex */
final class d {
    static int a(b bVar) {
        return a(bVar, true) + a(bVar, false);
    }

    static int b(b bVar) {
        byte[][] bArrC = bVar.c();
        int iB = bVar.b();
        int iA = bVar.a();
        int i = 0;
        int i2 = 0;
        while (i < iA - 1) {
            byte[] bArr = bArrC[i];
            int i3 = i2;
            int i4 = 0;
            while (i4 < iB - 1) {
                byte b = bArr[i4];
                int i5 = i4 + 1;
                if (b == bArr[i5]) {
                    int i6 = i + 1;
                    if (b == bArrC[i6][i4] && b == bArrC[i6][i5]) {
                        i3++;
                    }
                }
                i4 = i5;
            }
            i++;
            i2 = i3;
        }
        return i2 * 3;
    }

    static int c(b bVar) {
        byte[][] bArrC = bVar.c();
        int iB = bVar.b();
        int iA = bVar.a();
        int i = 0;
        int i2 = 0;
        while (i < iA) {
            int i3 = i2;
            for (int i4 = 0; i4 < iB; i4++) {
                byte[] bArr = bArrC[i];
                int i5 = i4 + 6;
                if (i5 < iB && bArr[i4] == 1 && bArr[i4 + 1] == 0 && bArr[i4 + 2] == 1 && bArr[i4 + 3] == 1 && bArr[i4 + 4] == 1 && bArr[i4 + 5] == 0 && bArr[i5] == 1 && (a(bArr, i4 - 4, i4) || a(bArr, i4 + 7, i4 + 11))) {
                    i3++;
                }
                int i6 = i + 6;
                if (i6 < iA && bArrC[i][i4] == 1 && bArrC[i + 1][i4] == 0 && bArrC[i + 2][i4] == 1 && bArrC[i + 3][i4] == 1 && bArrC[i + 4][i4] == 1 && bArrC[i + 5][i4] == 0 && bArrC[i6][i4] == 1 && (a(bArrC, i4, i - 4, i) || a(bArrC, i4, i + 7, i + 11))) {
                    i3++;
                }
            }
            i++;
            i2 = i3;
        }
        return i2 * 40;
    }

    private static boolean a(byte[] bArr, int i, int i2) {
        if (i < 0 || bArr.length < i2) {
            return false;
        }
        while (i < i2) {
            if (bArr[i] == 1) {
                return false;
            }
            i++;
        }
        return true;
    }

    private static boolean a(byte[][] bArr, int i, int i2, int i3) {
        if (i2 < 0 || bArr.length < i3) {
            return false;
        }
        while (i2 < i3) {
            if (bArr[i2][i] == 1) {
                return false;
            }
            i2++;
        }
        return true;
    }

    static int d(b bVar) {
        byte[][] bArrC = bVar.c();
        int iB = bVar.b();
        int iA = bVar.a();
        int i = 0;
        int i2 = 0;
        while (i < iA) {
            byte[] bArr = bArrC[i];
            int i3 = i2;
            for (int i4 = 0; i4 < iB; i4++) {
                if (bArr[i4] == 1) {
                    i3++;
                }
            }
            i++;
            i2 = i3;
        }
        int iA2 = bVar.a() * bVar.b();
        return ((Math.abs((i2 * 2) - iA2) * 10) / iA2) * 10;
    }

    static boolean a(int i, int i2, int i3) {
        int i4;
        switch (i) {
            case 0:
                i4 = (i3 + i2) & 1;
                break;
            case 1:
                i4 = i3 & 1;
                break;
            case 2:
                i4 = i2 % 3;
                break;
            case 3:
                i4 = (i3 + i2) % 3;
                break;
            case 4:
                i4 = ((i3 / 2) + (i2 / 3)) & 1;
                break;
            case 5:
                int i5 = i3 * i2;
                i4 = (i5 & 1) + (i5 % 3);
                break;
            case 6:
                int i6 = i3 * i2;
                i4 = ((i6 & 1) + (i6 % 3)) & 1;
                break;
            case 7:
                i4 = (((i3 * i2) % 3) + ((i3 + i2) & 1)) & 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid mask pattern: " + i);
        }
        return i4 == 0;
    }

    private static int a(b bVar, boolean z) {
        int iA = z ? bVar.a() : bVar.b();
        int iB = z ? bVar.b() : bVar.a();
        byte[][] bArrC = bVar.c();
        int i = 0;
        for (int i2 = 0; i2 < iA; i2++) {
            int i3 = i;
            byte b = -1;
            int i4 = 0;
            for (int i5 = 0; i5 < iB; i5++) {
                byte b2 = z ? bArrC[i2][i5] : bArrC[i5][i2];
                if (b2 == b) {
                    i4++;
                } else {
                    if (i4 >= 5) {
                        i3 += (i4 - 5) + 3;
                    }
                    i4 = 1;
                    b = b2;
                }
            }
            if (i4 >= 5) {
                i3 += (i4 - 5) + 3;
            }
            i = i3;
        }
        return i;
    }
}
