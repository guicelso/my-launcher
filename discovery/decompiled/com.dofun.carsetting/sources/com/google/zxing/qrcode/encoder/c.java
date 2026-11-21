package com.google.zxing.qrcode.encoder;

import android.support.v7.widget.ActivityChooserView;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: Encoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {
    private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static final Charset f557a = StandardCharsets.ISO_8859_1;

    private static int a(b bVar) {
        return d.a(bVar) + d.b(bVar) + d.c(bVar) + d.d(bVar);
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0148  */
    public static f a(String str, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> map) throws WriterException {
        com.google.zxing.qrcode.decoder.a aVarA;
        Mode mode;
        com.google.zxing.qrcode.decoder.a aVarB;
        com.google.zxing.common.a aVar;
        CharacterSetECI characterSetECI;
        int iA;
        boolean z = map != null && map.containsKey(EncodeHintType.GS1_FORMAT) && Boolean.parseBoolean(map.get(EncodeHintType.GS1_FORMAT).toString());
        boolean z2 = map != null && map.containsKey(EncodeHintType.QR_COMPACT) && Boolean.parseBoolean(map.get(EncodeHintType.QR_COMPACT).toString());
        Charset charsetForName = f557a;
        boolean z3 = map != null && map.containsKey(EncodeHintType.CHARACTER_SET);
        if (z3) {
            charsetForName = Charset.forName(map.get(EncodeHintType.CHARACTER_SET).toString());
        }
        if (z2) {
            mode = Mode.BYTE;
            if (charsetForName.equals(f557a)) {
                charsetForName = null;
            }
            MinimalEncoder.b bVarA = MinimalEncoder.a(str, null, charsetForName, z, errorCorrectionLevel);
            aVar = new com.google.zxing.common.a();
            bVarA.a(aVar);
            aVarB = bVarA.b();
        } else {
            Mode modeA = a(str, charsetForName);
            com.google.zxing.common.a aVar2 = new com.google.zxing.common.a();
            if (modeA == Mode.BYTE && z3 && (characterSetECI = CharacterSetECI.getCharacterSetECI(charsetForName)) != null) {
                a(characterSetECI, aVar2);
            }
            if (z) {
                a(Mode.FNC1_FIRST_POSITION, aVar2);
            }
            a(modeA, aVar2);
            com.google.zxing.common.a aVar3 = new com.google.zxing.common.a();
            a(str, modeA, aVar3, charsetForName);
            if (map != null && map.containsKey(EncodeHintType.QR_VERSION)) {
                aVarA = com.google.zxing.qrcode.decoder.a.a(Integer.parseInt(map.get(EncodeHintType.QR_VERSION).toString()));
                if (!a(a(modeA, aVar2, aVar3, aVarA), aVarA, errorCorrectionLevel)) {
                    throw new WriterException("Data too big for requested version");
                }
            } else {
                aVarA = a(errorCorrectionLevel, modeA, aVar2, aVar3);
            }
            com.google.zxing.common.a aVar4 = new com.google.zxing.common.a();
            aVar4.a(aVar2);
            a(modeA == Mode.BYTE ? aVar3.b() : str.length(), aVarA, modeA, aVar4);
            aVar4.a(aVar3);
            mode = modeA;
            aVarB = aVarA;
            aVar = aVar4;
        }
        com.google.zxing.qrcode.decoder.a.b bVarA2 = aVarB.a(errorCorrectionLevel);
        int iB = aVarB.b() - bVarA2.c();
        a(iB, aVar);
        com.google.zxing.common.a aVarA2 = a(aVar, aVarB.b(), iB, bVarA2.b());
        f fVar = new f();
        fVar.a(errorCorrectionLevel);
        fVar.a(mode);
        fVar.a(aVarB);
        int iC = aVarB.c();
        b bVar = new b(iC, iC);
        if (map == null || !map.containsKey(EncodeHintType.QR_MASK_PATTERN)) {
            iA = -1;
        } else {
            iA = Integer.parseInt(map.get(EncodeHintType.QR_MASK_PATTERN).toString());
            if (!f.b(iA)) {
                iA = -1;
            }
        }
        if (iA == -1) {
            iA = a(aVarA2, errorCorrectionLevel, aVarB, bVar);
        }
        fVar.a(iA);
        e.a(aVarA2, errorCorrectionLevel, aVarB, iA, bVar);
        fVar.a(bVar);
        return fVar;
    }

    private static com.google.zxing.qrcode.decoder.a a(ErrorCorrectionLevel errorCorrectionLevel, Mode mode, com.google.zxing.common.a aVar, com.google.zxing.common.a aVar2) {
        return a(a(mode, aVar, aVar2, a(a(mode, aVar, aVar2, com.google.zxing.qrcode.decoder.a.a(1)), errorCorrectionLevel)), errorCorrectionLevel);
    }

    private static int a(Mode mode, com.google.zxing.common.a aVar, com.google.zxing.common.a aVar2, com.google.zxing.qrcode.decoder.a aVar3) {
        return aVar.a() + mode.getCharacterCountBits(aVar3) + aVar2.a();
    }

    static int a(int i) {
        if (i < b.length) {
            return b[i];
        }
        return -1;
    }

    private static Mode a(String str, Charset charset) {
        if (com.google.zxing.common.d.f542a.equals(charset) && a(str)) {
            return Mode.KANJI;
        }
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt >= '0' && cCharAt <= '9') {
                z2 = true;
            } else {
                if (a(cCharAt) == -1) {
                    return Mode.BYTE;
                }
                z = true;
            }
        }
        if (z) {
            return Mode.ALPHANUMERIC;
        }
        if (z2) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    static boolean a(String str) {
        byte[] bytes = str.getBytes(com.google.zxing.common.d.f542a);
        int length = bytes.length;
        if (length % 2 != 0) {
            return false;
        }
        for (int i = 0; i < length; i += 2) {
            int i2 = bytes[i] & 255;
            if ((i2 < 129 || i2 > 159) && (i2 < 224 || i2 > 235)) {
                return false;
            }
        }
        return true;
    }

    private static int a(com.google.zxing.common.a aVar, ErrorCorrectionLevel errorCorrectionLevel, com.google.zxing.qrcode.decoder.a aVar2, b bVar) throws WriterException {
        int i = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int i2 = -1;
        for (int i3 = 0; i3 < 8; i3++) {
            e.a(aVar, errorCorrectionLevel, aVar2, i3, bVar);
            int iA = a(bVar);
            if (iA < i) {
                i2 = i3;
                i = iA;
            }
        }
        return i2;
    }

    private static com.google.zxing.qrcode.decoder.a a(int i, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i2 = 1; i2 <= 40; i2++) {
            com.google.zxing.qrcode.decoder.a aVarA = com.google.zxing.qrcode.decoder.a.a(i2);
            if (a(i, aVarA, errorCorrectionLevel)) {
                return aVarA;
            }
        }
        throw new WriterException("Data too big");
    }

    static boolean a(int i, com.google.zxing.qrcode.decoder.a aVar, ErrorCorrectionLevel errorCorrectionLevel) {
        return aVar.b() - aVar.a(errorCorrectionLevel).c() >= (i + 7) / 8;
    }

    static void a(int i, com.google.zxing.common.a aVar) throws WriterException {
        int i2 = i * 8;
        if (aVar.a() > i2) {
            throw new WriterException("data bits cannot fit in the QR Code" + aVar.a() + " > " + i2);
        }
        for (int i3 = 0; i3 < 4 && aVar.a() < i2; i3++) {
            aVar.a(false);
        }
        int iA = aVar.a() & 7;
        if (iA > 0) {
            while (iA < 8) {
                aVar.a(false);
                iA++;
            }
        }
        int iB = i - aVar.b();
        for (int i4 = 0; i4 < iB; i4++) {
            aVar.a((i4 & 1) == 0 ? 236 : 17, 8);
        }
        if (aVar.a() != i2) {
            throw new WriterException("Bits size does not equal capacity");
        }
    }

    static void a(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2) throws WriterException {
        if (i4 >= i3) {
            throw new WriterException("Block ID too large");
        }
        int i5 = i % i3;
        int i6 = i3 - i5;
        int i7 = i / i3;
        int i8 = i7 + 1;
        int i9 = i2 / i3;
        int i10 = i9 + 1;
        int i11 = i7 - i9;
        int i12 = i8 - i10;
        if (i11 != i12) {
            throw new WriterException("EC bytes mismatch");
        }
        if (i3 != i6 + i5) {
            throw new WriterException("RS blocks mismatch");
        }
        if (i != ((i9 + i11) * i6) + ((i10 + i12) * i5)) {
            throw new WriterException("Total bytes mismatch");
        }
        if (i4 < i6) {
            iArr[0] = i9;
            iArr2[0] = i11;
        } else {
            iArr[0] = i10;
            iArr2[0] = i12;
        }
    }

    static com.google.zxing.common.a a(com.google.zxing.common.a aVar, int i, int i2, int i3) throws WriterException {
        if (aVar.b() != i2) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        ArrayList arrayList = new ArrayList(i3);
        int i4 = 0;
        int iMax = 0;
        int iMax2 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            int[] iArr = new int[1];
            int[] iArr2 = new int[1];
            a(i, i2, i3, i5, iArr, iArr2);
            int i6 = iArr[0];
            byte[] bArr = new byte[i6];
            aVar.a(i4 * 8, bArr, 0, i6);
            byte[] bArrA = a(bArr, iArr2[0]);
            arrayList.add(new a(bArr, bArrA));
            iMax = Math.max(iMax, i6);
            iMax2 = Math.max(iMax2, bArrA.length);
            i4 += iArr[0];
        }
        if (i2 != i4) {
            throw new WriterException("Data bytes does not match offset");
        }
        com.google.zxing.common.a aVar2 = new com.google.zxing.common.a();
        for (int i7 = 0; i7 < iMax; i7++) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                byte[] bArrA2 = ((a) it.next()).a();
                if (i7 < bArrA2.length) {
                    aVar2.a(bArrA2[i7], 8);
                }
            }
        }
        for (int i8 = 0; i8 < iMax2; i8++) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                byte[] bArrB = ((a) it2.next()).b();
                if (i8 < bArrB.length) {
                    aVar2.a(bArrB[i8], 8);
                }
            }
        }
        if (i == aVar2.b()) {
            return aVar2;
        }
        throw new WriterException("Interleaving error: " + i + " and " + aVar2.b() + " differ.");
    }

    static byte[] a(byte[] bArr, int i) {
        int length = bArr.length;
        int[] iArr = new int[length + i];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = bArr[i2] & 255;
        }
        new com.google.zxing.common.reedsolomon.c(com.google.zxing.common.reedsolomon.a.e).a(iArr, i);
        byte[] bArr2 = new byte[i];
        for (int i3 = 0; i3 < i; i3++) {
            bArr2[i3] = (byte) iArr[length + i3];
        }
        return bArr2;
    }

    static void a(Mode mode, com.google.zxing.common.a aVar) {
        aVar.a(mode.getBits(), 4);
    }

    static void a(int i, com.google.zxing.qrcode.decoder.a aVar, Mode mode, com.google.zxing.common.a aVar2) throws WriterException {
        int characterCountBits = mode.getCharacterCountBits(aVar);
        int i2 = 1 << characterCountBits;
        if (i >= i2) {
            throw new WriterException(i + " is bigger than " + (i2 - 1));
        }
        aVar2.a(i, characterCountBits);
    }

    static void a(String str, Mode mode, com.google.zxing.common.a aVar, Charset charset) {
        switch (mode) {
            case NUMERIC:
                a((CharSequence) str, aVar);
                return;
            case ALPHANUMERIC:
                b(str, aVar);
                return;
            case BYTE:
                a(str, aVar, charset);
                return;
            case KANJI:
                a(str, aVar);
                return;
            default:
                throw new WriterException("Invalid mode: " + mode);
        }
    }

    static void a(CharSequence charSequence, com.google.zxing.common.a aVar) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int iCharAt = charSequence.charAt(i) - '0';
            int i2 = i + 2;
            if (i2 < length) {
                aVar.a((iCharAt * 100) + ((charSequence.charAt(i + 1) - '0') * 10) + (charSequence.charAt(i2) - '0'), 10);
                i += 3;
            } else {
                i++;
                if (i < length) {
                    aVar.a((iCharAt * 10) + (charSequence.charAt(i) - '0'), 7);
                    i = i2;
                } else {
                    aVar.a(iCharAt, 4);
                }
            }
        }
    }

    static void b(CharSequence charSequence, com.google.zxing.common.a aVar) throws WriterException {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            int iA = a(charSequence.charAt(i));
            if (iA == -1) {
                throw new WriterException();
            }
            int i2 = i + 1;
            if (i2 < length) {
                int iA2 = a(charSequence.charAt(i2));
                if (iA2 == -1) {
                    throw new WriterException();
                }
                aVar.a((iA * 45) + iA2, 11);
                i += 2;
            } else {
                aVar.a(iA, 6);
                i = i2;
            }
        }
    }

    static void a(String str, com.google.zxing.common.a aVar, Charset charset) {
        for (byte b2 : str.getBytes(charset)) {
            aVar.a(b2, 8);
        }
    }

    static void a(String str, com.google.zxing.common.a aVar) throws WriterException {
        byte[] bytes = str.getBytes(com.google.zxing.common.d.f542a);
        if (bytes.length % 2 != 0) {
            throw new WriterException("Kanji byte size not even");
        }
        int length = bytes.length - 1;
        for (int i = 0; i < length; i += 2) {
            int i2 = ((bytes[i] & 255) << 8) | (bytes[i + 1] & 255);
            int i3 = (i2 < 33088 || i2 > 40956) ? (i2 < 57408 || i2 > 60351) ? -1 : i2 - 49472 : i2 - 33088;
            if (i3 == -1) {
                throw new WriterException("Invalid byte sequence");
            }
            aVar.a(((i3 >> 8) * 192) + (i3 & 255), 13);
        }
    }

    private static void a(CharacterSetECI characterSetECI, com.google.zxing.common.a aVar) {
        aVar.a(Mode.ECI.getBits(), 4);
        aVar.a(characterSetECI.getValue(), 8);
    }
}
