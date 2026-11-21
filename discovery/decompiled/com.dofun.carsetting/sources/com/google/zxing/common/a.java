package com.google.zxing.common;

import java.util.Arrays;

/* JADX INFO: compiled from: BitArray.java */
/* JADX INFO: loaded from: classes.dex */
public final class a implements Cloneable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final int[] f539a = new int[0];
    private int[] b;
    private int c;

    public a() {
        this.c = 0;
        this.b = f539a;
    }

    a(int[] iArr, int i) {
        this.b = iArr;
        this.c = i;
    }

    public int a() {
        return this.c;
    }

    public int b() {
        return (this.c + 7) / 8;
    }

    private void b(int i) {
        if (i > this.b.length * 32) {
            int[] iArrC = c((int) Math.ceil(i / 0.75f));
            System.arraycopy(this.b, 0, iArrC, 0, this.b.length);
            this.b = iArrC;
        }
    }

    public boolean a(int i) {
        return (this.b[i / 32] & (1 << (i & 31))) != 0;
    }

    public void a(boolean z) {
        b(this.c + 1);
        if (z) {
            int[] iArr = this.b;
            int i = this.c / 32;
            iArr[i] = iArr[i] | (1 << (this.c & 31));
        }
        this.c++;
    }

    public void a(int i, int i2) {
        if (i2 < 0 || i2 > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        int i3 = this.c;
        b(i3 + i2);
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            if (((1 << i4) & i) != 0) {
                int[] iArr = this.b;
                int i5 = i3 / 32;
                iArr[i5] = iArr[i5] | (1 << (i3 & 31));
            }
            i3++;
        }
        this.c = i3;
    }

    public void a(a aVar) {
        int i = aVar.c;
        b(this.c + i);
        for (int i2 = 0; i2 < i; i2++) {
            a(aVar.a(i2));
        }
    }

    public void b(a aVar) {
        if (this.c != aVar.c) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i = 0; i < this.b.length; i++) {
            int[] iArr = this.b;
            iArr[i] = iArr[i] ^ aVar.b[i];
        }
    }

    public void a(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        int i5 = 0;
        while (i5 < i3) {
            int i6 = 0;
            int i7 = i4;
            for (int i8 = 0; i8 < 8; i8++) {
                if (a(i7)) {
                    i6 |= 1 << (7 - i8);
                }
                i7++;
            }
            bArr[i2 + i5] = (byte) i6;
            i5++;
            i4 = i7;
        }
    }

    private static int[] c(int i) {
        return new int[(i + 31) / 32];
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return this.c == aVar.c && Arrays.equals(this.b, aVar.b);
    }

    public int hashCode() {
        return (this.c * 31) + Arrays.hashCode(this.b);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.c + (this.c / 8) + 1);
        for (int i = 0; i < this.c; i++) {
            if ((i & 7) == 0) {
                sb.append(' ');
            }
            sb.append(a(i) ? 'X' : '.');
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public a clone() {
        return new a((int[]) this.b.clone(), this.c);
    }
}
