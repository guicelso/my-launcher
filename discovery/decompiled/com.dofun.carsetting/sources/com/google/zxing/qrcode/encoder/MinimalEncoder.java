package com.google.zxing.qrcode.encoder;

import android.support.v7.widget.ActivityChooserView;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class MinimalEncoder {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final String f549a;
    private final boolean b;
    private final com.google.zxing.common.c c;
    private final ErrorCorrectionLevel d;

    static boolean a(char c) {
        return c >= '0' && c <= '9';
    }

    private enum VersionSize {
        SMALL("version 1-9"),
        MEDIUM("version 10-26"),
        LARGE("version 27-40");

        private final String description;

        VersionSize(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    MinimalEncoder(String str, Charset charset, boolean z, ErrorCorrectionLevel errorCorrectionLevel) {
        this.f549a = str;
        this.b = z;
        this.c = new com.google.zxing.common.c(str, charset, -1);
        this.d = errorCorrectionLevel;
    }

    static b a(String str, com.google.zxing.qrcode.decoder.a aVar, Charset charset, boolean z, ErrorCorrectionLevel errorCorrectionLevel) {
        return new MinimalEncoder(str, charset, z, errorCorrectionLevel).a(aVar);
    }

    b a(com.google.zxing.qrcode.decoder.a aVar) throws WriterException {
        if (aVar == null) {
            com.google.zxing.qrcode.decoder.a[] aVarArr = {a(VersionSize.SMALL), a(VersionSize.MEDIUM), a(VersionSize.LARGE)};
            b[] bVarArr = {c(aVarArr[0]), c(aVarArr[1]), c(aVarArr[2])};
            int i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int i2 = -1;
            for (int i3 = 0; i3 < 3; i3++) {
                int iA = bVarArr[i3].a();
                if (c.a(iA, aVarArr[i3], this.d) && iA < i) {
                    i2 = i3;
                    i = iA;
                }
            }
            if (i2 < 0) {
                throw new WriterException("Data too big for any version");
            }
            return bVarArr[i2];
        }
        b bVarC = c(aVar);
        if (c.a(bVarC.a(), a(b(bVarC.b())), this.d)) {
            return bVarC;
        }
        throw new WriterException("Data too big for version" + aVar);
    }

    static VersionSize b(com.google.zxing.qrcode.decoder.a aVar) {
        if (aVar.a() <= 9) {
            return VersionSize.SMALL;
        }
        return aVar.a() <= 26 ? VersionSize.MEDIUM : VersionSize.LARGE;
    }

    static com.google.zxing.qrcode.decoder.a a(VersionSize versionSize) {
        switch (versionSize) {
            case SMALL:
                return com.google.zxing.qrcode.decoder.a.a(9);
            case MEDIUM:
                return com.google.zxing.qrcode.decoder.a.a(26);
            default:
                return com.google.zxing.qrcode.decoder.a.a(40);
        }
    }

    static boolean b(char c) {
        return c.a(String.valueOf(c));
    }

    static boolean c(char c) {
        return c.a(c) != -1;
    }

    boolean a(Mode mode, char c) {
        switch (mode) {
            case KANJI:
                return b(c);
            case ALPHANUMERIC:
                return c(c);
            case NUMERIC:
                return a(c);
            case BYTE:
                return true;
            default:
                return false;
        }
    }

    static int a(Mode mode) {
        if (mode == null) {
            return 0;
        }
        switch (mode) {
            case KANJI:
                return 0;
            case ALPHANUMERIC:
                return 1;
            case NUMERIC:
                return 2;
            case BYTE:
                return 3;
            default:
                throw new IllegalStateException("Illegal mode " + mode);
        }
    }

    void a(a[][][] aVarArr, int i, a aVar) {
        a[] aVarArr2 = aVarArr[i + aVar.e][aVar.d];
        int iA = a(aVar.b);
        if (aVarArr2[iA] == null || aVarArr2[iA].g > aVar.g) {
            aVarArr2[iA] = aVar;
        }
    }

    void a(com.google.zxing.qrcode.decoder.a aVar, a[][][] aVarArr, int i, a aVar2) {
        int i2;
        int iA = this.c.a();
        int iB = this.c.b();
        if (iB < 0 || !this.c.a(this.f549a.charAt(i), iB)) {
            iB = 0;
        } else {
            iA = iB + 1;
        }
        int i3 = iA;
        for (int i4 = iB; i4 < i3; i4++) {
            if (this.c.a(this.f549a.charAt(i), i4)) {
                a(aVarArr, i, new a(Mode.BYTE, i, i4, 1, aVar2, aVar));
            }
        }
        if (a(Mode.KANJI, this.f549a.charAt(i))) {
            a(aVarArr, i, new a(Mode.KANJI, i, 0, 1, aVar2, aVar));
        }
        int length = this.f549a.length();
        if (a(Mode.ALPHANUMERIC, this.f549a.charAt(i))) {
            int i5 = i + 1;
            a(aVarArr, i, new a(Mode.ALPHANUMERIC, i, 0, (i5 >= length || !a(Mode.ALPHANUMERIC, this.f549a.charAt(i5))) ? 1 : 2, aVar2, aVar));
        }
        if (a(Mode.NUMERIC, this.f549a.charAt(i))) {
            Mode mode = Mode.NUMERIC;
            int i6 = 0;
            int i7 = i + 1;
            if (i7 >= length || !a(Mode.NUMERIC, this.f549a.charAt(i7))) {
                i2 = 1;
            } else {
                int i8 = i + 2;
                i2 = (i8 >= length || !a(Mode.NUMERIC, this.f549a.charAt(i8))) ? 2 : 3;
            }
            a(aVarArr, i, new a(mode, i, i6, i2, aVar2, aVar));
        }
    }

    b c(com.google.zxing.qrcode.decoder.a aVar) throws WriterException {
        int length = this.f549a.length();
        a[][][] aVarArr = (a[][][]) Array.newInstance((Class<?>) a.class, length + 1, this.c.a(), 4);
        a(aVar, aVarArr, 0, null);
        for (int i = 1; i <= length; i++) {
            for (int i2 = 0; i2 < this.c.a(); i2++) {
                for (int i3 = 0; i3 < 4; i3++) {
                    if (aVarArr[i][i2][i3] != null && i < length) {
                        a(aVar, aVarArr, i, aVarArr[i][i2][i3]);
                    }
                }
            }
        }
        int i4 = -1;
        int i5 = Integer.MAX_VALUE;
        int i6 = -1;
        int i7 = 0;
        while (i7 < this.c.a()) {
            int i8 = i5;
            int i9 = i6;
            int i10 = i4;
            for (int i11 = 0; i11 < 4; i11++) {
                if (aVarArr[length][i7][i11] != null) {
                    a aVar2 = aVarArr[length][i7][i11];
                    if (aVar2.g < i8) {
                        i8 = aVar2.g;
                        i10 = i7;
                        i9 = i11;
                    }
                }
            }
            i7++;
            i4 = i10;
            i6 = i9;
            i5 = i8;
        }
        if (i4 < 0) {
            throw new WriterException("Internal error: failed to encode \"" + this.f549a + "\"");
        }
        return new b(aVar, aVarArr[length][i4][i6]);
    }

    private final class a {
        private final Mode b;
        private final int c;
        private final int d;
        private final int e;
        private final a f;
        private final int g;

        private a(Mode mode, int i, int i2, int i3, a aVar, com.google.zxing.qrcode.decoder.a aVar2) {
            this.b = mode;
            this.c = i;
            this.d = (mode == Mode.BYTE || aVar == null) ? i2 : aVar.d;
            this.e = i3;
            this.f = aVar;
            boolean z = false;
            int characterCountBits = aVar != null ? aVar.g : 0;
            if ((mode == Mode.BYTE && aVar == null && this.d != 0) || (aVar != null && this.d != aVar.d)) {
                z = true;
            }
            characterCountBits = (aVar == null || mode != aVar.b || z) ? characterCountBits + mode.getCharacterCountBits(aVar2) + 4 : characterCountBits;
            switch (mode) {
                case KANJI:
                    characterCountBits += 13;
                    break;
                case ALPHANUMERIC:
                    characterCountBits += i3 == 1 ? 6 : 11;
                    break;
                case NUMERIC:
                    characterCountBits += i3 != 1 ? i3 == 2 ? 7 : 10 : 4;
                    break;
                case BYTE:
                    characterCountBits += MinimalEncoder.this.c.a(MinimalEncoder.this.f549a.substring(i, i3 + i), i2).length * 8;
                    if (z) {
                        characterCountBits += 12;
                    }
                    break;
            }
            this.g = characterCountBits;
        }
    }

    final class b {
        private final List<a> b = new ArrayList();
        private final com.google.zxing.qrcode.decoder.a c;

        b(com.google.zxing.qrcode.decoder.a aVar, a aVar2) {
            int i;
            int i2;
            int i3 = 0;
            boolean z = false;
            while (true) {
                i = 1;
                if (aVar2 == null) {
                    break;
                }
                int i4 = i3 + aVar2.e;
                a aVar3 = aVar2.f;
                boolean z2 = (aVar2.b == Mode.BYTE && aVar3 == null && aVar2.d != 0) || !(aVar3 == null || aVar2.d == aVar3.d);
                z = z2 ? true : z;
                if (aVar3 == null || aVar3.b != aVar2.b || z2) {
                    this.b.add(0, new a(aVar2.b, aVar2.c, aVar2.d, i4));
                    i4 = 0;
                }
                if (z2) {
                    this.b.add(0, new a(Mode.ECI, aVar2.c, aVar2.d, 0));
                }
                aVar2 = aVar3;
                i3 = i4;
            }
            if (MinimalEncoder.this.b) {
                a aVar4 = this.b.get(0);
                if (aVar4 != null && aVar4.b != Mode.ECI && z) {
                    this.b.add(0, new a(Mode.ECI, 0, 0, 0));
                }
                this.b.add(this.b.get(0).b == Mode.ECI ? 1 : 0, new a(Mode.FNC1_FIRST_POSITION, 0, 0, 0));
            }
            int iA = aVar.a();
            switch (MinimalEncoder.b(aVar)) {
                case SMALL:
                    i2 = 9;
                    break;
                case MEDIUM:
                    i = 10;
                    i2 = 26;
                    break;
                default:
                    i = 27;
                    i2 = 40;
                    break;
            }
            int iA2 = a(aVar);
            while (iA < i2 && !c.a(iA2, com.google.zxing.qrcode.decoder.a.a(iA), MinimalEncoder.this.d)) {
                iA++;
            }
            while (iA > i && c.a(iA2, com.google.zxing.qrcode.decoder.a.a(iA - 1), MinimalEncoder.this.d)) {
                iA--;
            }
            this.c = com.google.zxing.qrcode.decoder.a.a(iA);
        }

        int a() {
            return a(this.c);
        }

        private int a(com.google.zxing.qrcode.decoder.a aVar) {
            Iterator<a> it = this.b.iterator();
            int iA = 0;
            while (it.hasNext()) {
                iA += it.next().a(aVar);
            }
            return iA;
        }

        void a(com.google.zxing.common.a aVar) {
            Iterator<a> it = this.b.iterator();
            while (it.hasNext()) {
                it.next().a(aVar);
            }
        }

        com.google.zxing.qrcode.decoder.a b() {
            return this.c;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            a aVar = null;
            for (a aVar2 : this.b) {
                if (aVar != null) {
                    sb.append(",");
                }
                sb.append(aVar2.toString());
                aVar = aVar2;
            }
            return sb.toString();
        }

        final class a {
            private final Mode b;
            private final int c;
            private final int d;
            private final int e;

            a(Mode mode, int i, int i2, int i3) {
                this.b = mode;
                this.c = i;
                this.d = i2;
                this.e = i3;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int a(com.google.zxing.qrcode.decoder.a aVar) {
                int i = 4;
                int characterCountBits = this.b.getCharacterCountBits(aVar) + 4;
                switch (this.b) {
                    case KANJI:
                        return characterCountBits + (this.e * 13);
                    case ALPHANUMERIC:
                        return characterCountBits + ((this.e / 2) * 11) + (this.e % 2 == 1 ? 6 : 0);
                    case NUMERIC:
                        int i2 = characterCountBits + ((this.e / 3) * 10);
                        int i3 = this.e % 3;
                        if (i3 != 1) {
                            i = i3 == 2 ? 7 : 0;
                        }
                        return i2 + i;
                    case BYTE:
                        return characterCountBits + (a() * 8);
                    case ECI:
                        return characterCountBits + 8;
                    default:
                        return characterCountBits;
                }
            }

            private int a() {
                if (this.b == Mode.BYTE) {
                    return MinimalEncoder.this.c.a(MinimalEncoder.this.f549a.substring(this.c, this.c + this.e), this.d).length;
                }
                return this.e;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void a(com.google.zxing.common.a aVar) {
                aVar.a(this.b.getBits(), 4);
                if (this.e > 0) {
                    aVar.a(a(), this.b.getCharacterCountBits(b.this.c));
                }
                if (this.b == Mode.ECI) {
                    aVar.a(MinimalEncoder.this.c.b(this.d), 8);
                } else if (this.e > 0) {
                    c.a(MinimalEncoder.this.f549a.substring(this.c, this.c + this.e), this.b, aVar, MinimalEncoder.this.c.a(this.d));
                }
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(this.b);
                sb.append('(');
                if (this.b == Mode.ECI) {
                    sb.append(MinimalEncoder.this.c.a(this.d).displayName());
                } else {
                    sb.append(a(MinimalEncoder.this.f549a.substring(this.c, this.c + this.e)));
                }
                sb.append(')');
                return sb.toString();
            }

            private String a(String str) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) < ' ' || str.charAt(i) > '~') {
                        sb.append('.');
                    } else {
                        sb.append(str.charAt(i));
                    }
                }
                return sb.toString();
            }
        }
    }
}
