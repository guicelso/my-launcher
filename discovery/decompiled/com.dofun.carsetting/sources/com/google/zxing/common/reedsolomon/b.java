package com.google.zxing.common.reedsolomon;

/* JADX INFO: compiled from: GenericGFPoly.java */
/* JADX INFO: loaded from: classes.dex */
final class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final a f544a;
    private final int[] b;

    b(a aVar, int[] iArr) {
        if (iArr.length == 0) {
            throw new IllegalArgumentException();
        }
        this.f544a = aVar;
        int length = iArr.length;
        if (length > 1 && iArr[0] == 0) {
            int i = 1;
            while (i < length && iArr[i] == 0) {
                i++;
            }
            if (i == length) {
                this.b = new int[]{0};
                return;
            } else {
                this.b = new int[length - i];
                System.arraycopy(iArr, i, this.b, 0, this.b.length);
                return;
            }
        }
        this.b = iArr;
    }

    int[] a() {
        return this.b;
    }

    int b() {
        return this.b.length - 1;
    }

    boolean c() {
        return this.b[0] == 0;
    }

    int a(int i) {
        return this.b[(this.b.length - 1) - i];
    }

    b a(b bVar) {
        if (!this.f544a.equals(bVar.f544a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (c()) {
            return bVar;
        }
        if (bVar.c()) {
            return this;
        }
        int[] iArr = this.b;
        int[] iArr2 = bVar.b;
        if (iArr.length > iArr2.length) {
            iArr = iArr2;
            iArr2 = iArr;
        }
        int[] iArr3 = new int[iArr2.length];
        int length = iArr2.length - iArr.length;
        System.arraycopy(iArr2, 0, iArr3, 0, length);
        for (int i = length; i < iArr2.length; i++) {
            iArr3[i] = a.b(iArr[i - length], iArr2[i]);
        }
        return new b(this.f544a, iArr3);
    }

    b b(b bVar) {
        if (!this.f544a.equals(bVar.f544a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (c() || bVar.c()) {
            return this.f544a.a();
        }
        int[] iArr = this.b;
        int length = iArr.length;
        int[] iArr2 = bVar.b;
        int length2 = iArr2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            for (int i3 = 0; i3 < length2; i3++) {
                int i4 = i + i3;
                iArr3[i4] = a.b(iArr3[i4], this.f544a.c(i2, iArr2[i3]));
            }
        }
        return new b(this.f544a, iArr3);
    }

    b a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException();
        }
        if (i2 == 0) {
            return this.f544a.a();
        }
        int length = this.b.length;
        int[] iArr = new int[i + length];
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.f544a.c(this.b[i3], i2);
        }
        return new b(this.f544a, iArr);
    }

    b[] c(b bVar) {
        if (!this.f544a.equals(bVar.f544a)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (bVar.c()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        b bVarA = this.f544a.a();
        int iC = this.f544a.c(bVar.a(bVar.b()));
        b bVarA2 = bVarA;
        b bVarA3 = this;
        while (bVarA3.b() >= bVar.b() && !bVarA3.c()) {
            int iB = bVarA3.b() - bVar.b();
            int iC2 = this.f544a.c(bVarA3.a(bVarA3.b()), iC);
            b bVarA4 = bVar.a(iB, iC2);
            bVarA2 = bVarA2.a(this.f544a.a(iB, iC2));
            bVarA3 = bVarA3.a(bVarA4);
        }
        return new b[]{bVarA2, bVarA3};
    }

    public String toString() {
        if (c()) {
            return "0";
        }
        StringBuilder sb = new StringBuilder(b() * 8);
        for (int iB = b(); iB >= 0; iB--) {
            int iA = a(iB);
            if (iA != 0) {
                if (iA < 0) {
                    if (iB == b()) {
                        sb.append("-");
                    } else {
                        sb.append(" - ");
                    }
                    iA = -iA;
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (iB == 0 || iA != 1) {
                    int iB2 = this.f544a.b(iA);
                    if (iB2 == 0) {
                        sb.append('1');
                    } else if (iB2 == 1) {
                        sb.append('a');
                    } else {
                        sb.append("a^");
                        sb.append(iB2);
                    }
                }
                if (iB != 0) {
                    if (iB == 1) {
                        sb.append('x');
                    } else {
                        sb.append("x^");
                        sb.append(iB);
                    }
                }
            }
        }
        return sb.toString();
    }
}
