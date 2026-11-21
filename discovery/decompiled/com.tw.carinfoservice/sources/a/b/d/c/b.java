package a.b.d.c;

import android.graphics.Path;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public char f67a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float[] f68b;

    public b(char c2, float[] fArr) {
        this.f67a = c2;
        this.f68b = fArr;
    }

    public b(b bVar) {
        this.f67a = bVar.f67a;
        float[] fArr = bVar.f68b;
        this.f68b = a.b.a.a.e(fArr, 0, fArr.length);
    }

    public static void a(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
        double d;
        double d2;
        double radians = Math.toRadians(f7);
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d3 = f;
        Double.isNaN(d3);
        double d4 = f2;
        Double.isNaN(d4);
        double d5 = (d4 * dSin) + (d3 * dCos);
        double d6 = d3;
        double d7 = f5;
        Double.isNaN(d7);
        double d8 = d5 / d7;
        double d9 = -f;
        Double.isNaN(d9);
        Double.isNaN(d4);
        double d10 = (d4 * dCos) + (d9 * dSin);
        double d11 = f6;
        Double.isNaN(d11);
        double d12 = d10 / d11;
        double d13 = d4;
        double d14 = f3;
        Double.isNaN(d14);
        double d15 = f4;
        Double.isNaN(d15);
        Double.isNaN(d7);
        double d16 = ((d15 * dSin) + (d14 * dCos)) / d7;
        double d17 = -f3;
        Double.isNaN(d17);
        Double.isNaN(d15);
        Double.isNaN(d11);
        double d18 = ((d15 * dCos) + (d17 * dSin)) / d11;
        double d19 = d8 - d16;
        double d20 = d12 - d18;
        double d21 = (d8 + d16) / 2.0d;
        double d22 = (d12 + d18) / 2.0d;
        double d23 = (d20 * d20) + (d19 * d19);
        if (d23 == 0.0d) {
            Log.w("PathParser", " Points are coincident");
            return;
        }
        double d24 = (1.0d / d23) - 0.25d;
        if (d24 < 0.0d) {
            Log.w("PathParser", "Points are too far apart " + d23);
            float fSqrt = (float) (Math.sqrt(d23) / 1.99999d);
            a(path, f, f2, f3, f4, f5 * fSqrt, f6 * fSqrt, f7, z, z2);
            return;
        }
        double dSqrt = Math.sqrt(d24);
        double d25 = d19 * dSqrt;
        double d26 = dSqrt * d20;
        if (z == z2) {
            d = d21 - d26;
            d2 = d22 + d25;
        } else {
            d = d21 + d26;
            d2 = d22 - d25;
        }
        double dAtan2 = Math.atan2(d12 - d2, d8 - d);
        double dAtan3 = Math.atan2(d18 - d2, d16 - d) - dAtan2;
        int i = 0;
        if (z2 != (dAtan3 >= 0.0d)) {
            dAtan3 = dAtan3 > 0.0d ? dAtan3 - 6.283185307179586d : dAtan3 + 6.283185307179586d;
        }
        Double.isNaN(d7);
        double d27 = d * d7;
        Double.isNaN(d11);
        double d28 = d2 * d11;
        double d29 = (d27 * dCos) - (d28 * dSin);
        double d30 = (d28 * dCos) + (d27 * dSin);
        int iCeil = (int) Math.ceil(Math.abs((dAtan3 * 4.0d) / 3.141592653589793d));
        double dCos2 = Math.cos(radians);
        double dSin2 = Math.sin(radians);
        double dCos3 = Math.cos(dAtan2);
        double dSin3 = Math.sin(dAtan2);
        Double.isNaN(d7);
        double d31 = -d7;
        double d32 = d31 * dCos2;
        Double.isNaN(d11);
        double d33 = d11 * dSin2;
        double d34 = (d32 * dSin3) - (d33 * dCos3);
        double d35 = d31 * dSin2;
        Double.isNaN(d11);
        double d36 = d11 * dCos2;
        double d37 = (dCos3 * d36) + (dSin3 * d35);
        double d38 = iCeil;
        Double.isNaN(d38);
        Double.isNaN(d38);
        double d39 = dAtan3 / d38;
        double d40 = dAtan2;
        while (i < iCeil) {
            double d41 = d40 + d39;
            double dSin4 = Math.sin(d41);
            double dCos4 = Math.cos(d41);
            Double.isNaN(d7);
            double d42 = d39;
            double d43 = (((d7 * dCos2) * dCos4) + d29) - (d33 * dSin4);
            Double.isNaN(d7);
            double d44 = d29;
            double d45 = (d36 * dSin4) + (d7 * dSin2 * dCos4) + d30;
            double d46 = (d32 * dSin4) - (d33 * dCos4);
            double d47 = (dCos4 * d36) + (dSin4 * d35);
            double d48 = d41 - d40;
            double dTan = Math.tan(d48 / 2.0d);
            double dSqrt2 = ((Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d) * Math.sin(d48)) / 3.0d;
            path.rLineTo(0.0f, 0.0f);
            path.cubicTo((float) ((d34 * dSqrt2) + d6), (float) ((d37 * dSqrt2) + d13), (float) (d43 - (dSqrt2 * d46)), (float) (d45 - (dSqrt2 * d47)), (float) d43, (float) d45);
            i++;
            d36 = d36;
            d35 = d35;
            iCeil = iCeil;
            dCos2 = dCos2;
            d40 = d41;
            d7 = d7;
            d37 = d47;
            d34 = d46;
            d6 = d43;
            d13 = d45;
            d39 = d42;
            d29 = d44;
        }
    }

    public static void b(b[] bVarArr, Path path) {
        int i;
        int i2;
        float[] fArr;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        float[] fArr2 = new float[6];
        char c2 = 'm';
        char c3 = 0;
        char c4 = 'm';
        int i3 = 0;
        while (i3 < bVarArr.length) {
            char c5 = bVarArr[i3].f67a;
            float[] fArr3 = bVarArr[i3].f68b;
            float f19 = fArr2[c3];
            float f20 = fArr2[1];
            float f21 = fArr2[2];
            float f22 = fArr2[3];
            float f23 = fArr2[4];
            float f24 = fArr2[5];
            switch (c5) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f23, f24);
                    f19 = f23;
                    f21 = f19;
                    f20 = f24;
                    f22 = f20;
                default:
                    i = 2;
                    break;
            }
            float f25 = f23;
            float f26 = f24;
            float f27 = f19;
            float f28 = f20;
            int i4 = 0;
            while (i4 < fArr3.length) {
                if (c5 != 'A') {
                    if (c5 != 'C') {
                        if (c5 == 'H') {
                            i2 = i4;
                            fArr = fArr3;
                            c5 = c5;
                            i3 = i3;
                            int i5 = i2 + 0;
                            path.lineTo(fArr[i5], f28);
                            f27 = fArr[i5];
                        } else if (c5 == 'Q') {
                            i2 = i4;
                            fArr = fArr3;
                            int i6 = i2 + 0;
                            int i7 = i2 + 1;
                            int i8 = i2 + 2;
                            int i9 = i2 + 3;
                            path.quadTo(fArr[i6], fArr[i7], fArr[i8], fArr[i9]);
                            f = fArr[i6];
                            f2 = fArr[i7];
                            f27 = fArr[i8];
                            f28 = fArr[i9];
                        } else if (c5 == 'V') {
                            i2 = i4;
                            fArr = fArr3;
                            c5 = c5;
                            i3 = i3;
                            int i10 = i2 + 0;
                            path.lineTo(f27, fArr[i10]);
                            f28 = fArr[i10];
                        } else if (c5 != 'a') {
                            if (c5 != 'c') {
                                if (c5 == 'h') {
                                    i2 = i4;
                                    int i11 = i2 + 0;
                                    path.rLineTo(fArr3[i11], 0.0f);
                                    f27 += fArr3[i11];
                                } else if (c5 != 'q') {
                                    if (c5 == 'v') {
                                        i2 = i4;
                                        int i12 = i2 + 0;
                                        path.rLineTo(0.0f, fArr3[i12]);
                                        f10 = fArr3[i12];
                                    } else if (c5 == 'L') {
                                        i2 = i4;
                                        int i13 = i2 + 0;
                                        int i14 = i2 + 1;
                                        path.lineTo(fArr3[i13], fArr3[i14]);
                                        f27 = fArr3[i13];
                                        f28 = fArr3[i14];
                                    } else if (c5 == 'M') {
                                        i2 = i4;
                                        int i15 = i2 + 0;
                                        float f29 = fArr3[i15];
                                        int i16 = i2 + 1;
                                        float f30 = fArr3[i16];
                                        if (i2 > 0) {
                                            path.lineTo(fArr3[i15], fArr3[i16]);
                                            f27 = f29;
                                            f28 = f30;
                                        } else {
                                            path.moveTo(fArr3[i15], fArr3[i16]);
                                            f25 = f29;
                                            f26 = f30;
                                            f27 = f25;
                                            f28 = f26;
                                        }
                                    } else if (c5 == 'S') {
                                        i2 = i4;
                                        float f31 = f28;
                                        float f32 = f27;
                                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                            f11 = (f31 * 2.0f) - f22;
                                            f12 = (f32 * 2.0f) - f21;
                                        } else {
                                            f12 = f32;
                                            f11 = f31;
                                        }
                                        int i17 = i2 + 0;
                                        int i18 = i2 + 1;
                                        int i19 = i2 + 2;
                                        int i20 = i2 + 3;
                                        path.cubicTo(f12, f11, fArr3[i17], fArr3[i18], fArr3[i19], fArr3[i20]);
                                        float f33 = fArr3[i17];
                                        float f34 = fArr3[i18];
                                        f9 = fArr3[i19];
                                        f8 = fArr3[i20];
                                        f21 = f33;
                                        f22 = f34;
                                        f27 = f9;
                                        f28 = f8;
                                    } else if (c5 == 'T') {
                                        i2 = i4;
                                        float f35 = f28;
                                        float f36 = f27;
                                        if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                            f13 = (f36 * 2.0f) - f21;
                                            f14 = (f35 * 2.0f) - f22;
                                        } else {
                                            f13 = f36;
                                            f14 = f35;
                                        }
                                        int i21 = i2 + 0;
                                        int i22 = i2 + 1;
                                        path.quadTo(f13, f14, fArr3[i21], fArr3[i22]);
                                        f22 = f14;
                                        f21 = f13;
                                        fArr = fArr3;
                                        c5 = c5;
                                        i3 = i3;
                                        f27 = fArr3[i21];
                                        f28 = fArr3[i22];
                                    } else if (c5 == 'l') {
                                        i2 = i4;
                                        int i23 = i2 + 0;
                                        int i24 = i2 + 1;
                                        path.rLineTo(fArr3[i23], fArr3[i24]);
                                        f27 += fArr3[i23];
                                        f10 = fArr3[i24];
                                    } else if (c5 == c2) {
                                        i2 = i4;
                                        int i25 = i2 + 0;
                                        f27 += fArr3[i25];
                                        int i26 = i2 + 1;
                                        f28 += fArr3[i26];
                                        if (i2 > 0) {
                                            path.rLineTo(fArr3[i25], fArr3[i26]);
                                        } else {
                                            path.rMoveTo(fArr3[i25], fArr3[i26]);
                                            f26 = f28;
                                            f25 = f27;
                                            f27 = f25;
                                            f28 = f26;
                                        }
                                    } else if (c5 != 's') {
                                        if (c5 == 't') {
                                            if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                                                f17 = f27 - f21;
                                                f18 = f28 - f22;
                                            } else {
                                                f18 = 0.0f;
                                                f17 = 0.0f;
                                            }
                                            int i27 = i4 + 0;
                                            int i28 = i4 + 1;
                                            path.rQuadTo(f17, f18, fArr3[i27], fArr3[i28]);
                                            float f37 = f17 + f27;
                                            float f38 = f18 + f28;
                                            f27 += fArr3[i27];
                                            f28 += fArr3[i28];
                                            f22 = f38;
                                            f21 = f37;
                                        }
                                        i2 = i4;
                                    } else {
                                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                                            f15 = f28 - f22;
                                            f16 = f27 - f21;
                                        } else {
                                            f16 = 0.0f;
                                            f15 = 0.0f;
                                        }
                                        int i29 = i4 + 0;
                                        int i30 = i4 + 1;
                                        int i31 = i4 + 2;
                                        int i32 = i4 + 3;
                                        i2 = i4;
                                        f3 = f28;
                                        float f39 = f27;
                                        path.rCubicTo(f16, f15, fArr3[i29], fArr3[i30], fArr3[i31], fArr3[i32]);
                                        f4 = fArr3[i29] + f39;
                                        f5 = fArr3[i30] + f3;
                                        f6 = f39 + fArr3[i31];
                                        f7 = fArr3[i32];
                                    }
                                    f28 += f10;
                                } else {
                                    i2 = i4;
                                    f3 = f28;
                                    float f40 = f27;
                                    int i33 = i2 + 0;
                                    int i34 = i2 + 1;
                                    int i35 = i2 + 2;
                                    int i36 = i2 + 3;
                                    path.rQuadTo(fArr3[i33], fArr3[i34], fArr3[i35], fArr3[i36]);
                                    f4 = fArr3[i33] + f40;
                                    f5 = fArr3[i34] + f3;
                                    float f41 = f40 + fArr3[i35];
                                    float f42 = fArr3[i36];
                                    f6 = f41;
                                    f7 = f42;
                                }
                                fArr = fArr3;
                                c5 = c5;
                                i3 = i3;
                            } else {
                                i2 = i4;
                                f3 = f28;
                                float f43 = f27;
                                int i37 = i2 + 2;
                                int i38 = i2 + 3;
                                int i39 = i2 + 4;
                                int i40 = i2 + 5;
                                path.rCubicTo(fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i37], fArr3[i38], fArr3[i39], fArr3[i40]);
                                f4 = fArr3[i37] + f43;
                                f5 = fArr3[i38] + f3;
                                f6 = f43 + fArr3[i39];
                                f7 = fArr3[i40];
                            }
                            f8 = f3 + f7;
                            f21 = f4;
                            f22 = f5;
                            f9 = f6;
                            f27 = f9;
                            f28 = f8;
                            fArr = fArr3;
                            c5 = c5;
                            i3 = i3;
                        } else {
                            i2 = i4;
                            float f44 = f28;
                            float f45 = f27;
                            int i41 = i2 + 5;
                            int i42 = i2 + 6;
                            fArr = fArr3;
                            a(path, f45, f44, fArr3[i41] + f45, fArr3[i42] + f44, fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i2 + 2], fArr3[i2 + 3] != 0.0f, fArr3[i2 + 4] != 0.0f);
                            f27 = f45 + fArr[i41];
                            f28 = f44 + fArr[i42];
                        }
                        i4 = i2 + i;
                        c4 = c5;
                        c5 = c4;
                        fArr3 = fArr;
                        i3 = i3;
                        c2 = 'm';
                    } else {
                        i2 = i4;
                        fArr = fArr3;
                        int i43 = i2 + 2;
                        int i44 = i2 + 3;
                        int i45 = i2 + 4;
                        int i46 = i2 + 5;
                        path.cubicTo(fArr[i2 + 0], fArr[i2 + 1], fArr[i43], fArr[i44], fArr[i45], fArr[i46]);
                        float f46 = fArr[i45];
                        float f47 = fArr[i46];
                        f = fArr[i43];
                        f27 = f46;
                        f28 = f47;
                        f2 = fArr[i44];
                    }
                    f21 = f;
                    f22 = f2;
                    i4 = i2 + i;
                    c4 = c5;
                    c5 = c4;
                    fArr3 = fArr;
                    i3 = i3;
                    c2 = 'm';
                } else {
                    i2 = i4;
                    fArr = fArr3;
                    int i47 = i2 + 5;
                    int i48 = i2 + 6;
                    a(path, f27, f28, fArr[i47], fArr[i48], fArr[i2 + 0], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3] != 0.0f, fArr[i2 + 4] != 0.0f);
                    f27 = fArr[i47];
                    f28 = fArr[i48];
                }
                f22 = f28;
                f21 = f27;
                i4 = i2 + i;
                c4 = c5;
                c5 = c4;
                fArr3 = fArr;
                i3 = i3;
                c2 = 'm';
            }
            int i49 = i3;
            fArr2[0] = f27;
            fArr2[1] = f28;
            fArr2[2] = f21;
            fArr2[3] = f22;
            fArr2[4] = f25;
            fArr2[5] = f26;
            i3 = i49 + 1;
            c4 = bVarArr[i49].f67a;
            c2 = 'm';
            c3 = 0;
        }
    }
}
