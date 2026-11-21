package a.b.d.c;

import a.b.e.b.j;
import android.graphics.Path;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public char f75a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float[] f76b;

    public b(char c2, float[] fArr) {
        this.f75a = c2;
        this.f76b = fArr;
    }

    public b(b bVar) {
        this.f75a = bVar.f75a;
        float[] fArr = bVar.f76b;
        this.f76b = a.b.b.a.e.a(fArr, 0, fArr.length);
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

    public void a(b bVar, b bVar2, float f) {
        int i = 0;
        while (true) {
            float[] fArr = bVar.f76b;
            if (i >= fArr.length) {
                return;
            }
            this.f76b[i] = (bVar2.f76b[i] * f) + ((1.0f - f) * fArr[i]);
            i++;
        }
    }

    public static void a(b[] bVarArr, Path path) {
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
        float[] fArr2 = new float[6];
        char c2 = 'm';
        char c3 = 0;
        char c4 = 'm';
        int i3 = 0;
        while (i3 < bVarArr.length) {
            char c5 = bVarArr[i3].f75a;
            float[] fArr3 = bVarArr[i3].f76b;
            float f16 = fArr2[c3];
            float f17 = fArr2[1];
            float f18 = fArr2[2];
            float f19 = fArr2[3];
            float f20 = fArr2[4];
            float f21 = fArr2[5];
            switch (c5) {
                case j.AppCompatTheme_dropdownListPreferredItemHeight /* 65 */:
                case j.AppCompatTheme_textAppearanceListItem /* 97 */:
                    i = 7;
                    break;
                case j.AppCompatTheme_editTextColor /* 67 */:
                case j.AppCompatTheme_textAppearanceListItemSmall /* 99 */:
                    i = 6;
                    break;
                case j.AppCompatTheme_listDividerAlertDialog /* 72 */:
                case j.AppCompatTheme_ratingBarStyle /* 86 */:
                case j.AppCompatTheme_textColorAlertDialogListItem /* 104 */:
                case j.AppCompatTheme_windowMinWidthMajor /* 118 */:
                    i = 1;
                    break;
                case j.AppCompatTheme_panelMenuListTheme /* 81 */:
                case j.AppCompatTheme_popupMenuStyle /* 83 */:
                case j.AppCompatTheme_windowActionModeOverlay /* 113 */:
                case j.AppCompatTheme_windowFixedHeightMinor /* 115 */:
                    i = 4;
                    break;
                case j.AppCompatTheme_seekBarStyle /* 90 */:
                case 'z':
                    path.close();
                    path.moveTo(f20, f21);
                    f16 = f20;
                    f18 = f16;
                    f17 = f21;
                    f19 = f17;
                default:
                    i = 2;
                    break;
            }
            float f22 = f20;
            float f23 = f21;
            int i4 = 0;
            float f24 = f17;
            float f25 = f16;
            char c6 = c4;
            float f26 = f19;
            float f27 = f25;
            while (i4 < fArr3.length) {
                if (c5 != 'A') {
                    if (c5 == 'C') {
                        i2 = i4;
                        fArr = fArr3;
                        c5 = c5;
                        i3 = i3;
                        int i5 = i2 + 2;
                        int i6 = i2 + 3;
                        int i7 = i2 + 4;
                        int i8 = i2 + 5;
                        path.cubicTo(fArr[i2 + 0], fArr[i2 + 1], fArr[i5], fArr[i6], fArr[i7], fArr[i8]);
                        f27 = fArr[i7];
                        f24 = fArr[i8];
                        float f28 = fArr[i5];
                        f26 = fArr[i6];
                        f18 = f28;
                    } else if (c5 == 'H') {
                        i2 = i4;
                        fArr = fArr3;
                        c5 = c5;
                        i3 = i3;
                        int i9 = i2 + 0;
                        path.lineTo(fArr[i9], f24);
                        f27 = fArr[i9];
                    } else if (c5 == 'Q') {
                        i2 = i4;
                        fArr = fArr3;
                        c5 = c5;
                        i3 = i3;
                        int i10 = i2 + 0;
                        int i11 = i2 + 1;
                        int i12 = i2 + 2;
                        int i13 = i2 + 3;
                        path.quadTo(fArr[i10], fArr[i11], fArr[i12], fArr[i13]);
                        float f29 = fArr[i10];
                        f26 = fArr[i11];
                        float f30 = fArr[i12];
                        f24 = fArr[i13];
                        f18 = f29;
                        f27 = f30;
                    } else if (c5 == 'V') {
                        i2 = i4;
                        fArr = fArr3;
                        c5 = c5;
                        i3 = i3;
                        int i14 = i2 + 0;
                        path.lineTo(f27, fArr[i14]);
                        f24 = fArr[i14];
                    } else if (c5 != 'a') {
                        if (c5 != 'c') {
                            if (c5 == 'h') {
                                i2 = i4;
                                int i15 = i2 + 0;
                                path.rLineTo(fArr3[i15], 0.0f);
                                f27 += fArr3[i15];
                            } else if (c5 != 'q') {
                                if (c5 == 'v') {
                                    f8 = f27;
                                    i2 = i4;
                                    int i16 = i2 + 0;
                                    path.rLineTo(0.0f, fArr3[i16]);
                                    f9 = fArr3[i16];
                                } else if (c5 != 'L') {
                                    if (c5 == 'M') {
                                        i2 = i4;
                                        int i17 = i2 + 0;
                                        float f31 = fArr3[i17];
                                        int i18 = i2 + 1;
                                        f6 = fArr3[i18];
                                        if (i2 > 0) {
                                            path.lineTo(fArr3[i17], fArr3[i18]);
                                            f27 = f31;
                                            f24 = f6;
                                        } else {
                                            path.moveTo(fArr3[i17], fArr3[i18]);
                                            f22 = f31;
                                            f23 = f6;
                                        }
                                    } else if (c5 == 'S') {
                                        float f32 = f27;
                                        float f33 = f24;
                                        i2 = i4;
                                        if (c6 == 'c' || c6 == 's' || c6 == 'C' || c6 == 'S') {
                                            f10 = (f32 * 2.0f) - f18;
                                            f11 = (f33 * 2.0f) - f26;
                                        } else {
                                            f11 = f33;
                                            f10 = f32;
                                        }
                                        int i19 = i2 + 0;
                                        int i20 = i2 + 1;
                                        int i21 = i2 + 2;
                                        int i22 = i2 + 3;
                                        path.cubicTo(f10, f11, fArr3[i19], fArr3[i20], fArr3[i21], fArr3[i22]);
                                        float f34 = fArr3[i19];
                                        f26 = fArr3[i20];
                                        f7 = fArr3[i21];
                                        f6 = fArr3[i22];
                                        f18 = f34;
                                        f27 = f7;
                                        f24 = f6;
                                    } else if (c5 == 'T') {
                                        float f35 = f27;
                                        float f36 = f24;
                                        i2 = i4;
                                        if (c6 == 'q' || c6 == 't' || c6 == 'Q' || c6 == 'T') {
                                            f35 = (f35 * 2.0f) - f18;
                                            f36 = (f36 * 2.0f) - f26;
                                        }
                                        int i23 = i2 + 0;
                                        int i24 = i2 + 1;
                                        path.quadTo(f35, f36, fArr3[i23], fArr3[i24]);
                                        f27 = fArr3[i23];
                                        f24 = fArr3[i24];
                                        f26 = f36;
                                        fArr = fArr3;
                                        c5 = c5;
                                        i3 = i3;
                                        f18 = f35;
                                    } else if (c5 == 'l') {
                                        i2 = i4;
                                        int i25 = i2 + 0;
                                        int i26 = i2 + 1;
                                        path.rLineTo(fArr3[i25], fArr3[i26]);
                                        float f37 = f27 + fArr3[i25];
                                        f9 = fArr3[i26];
                                        f8 = f37;
                                    } else if (c5 == c2) {
                                        i2 = i4;
                                        int i27 = i2 + 0;
                                        f27 += fArr3[i27];
                                        int i28 = i2 + 1;
                                        f24 += fArr3[i28];
                                        if (i2 > 0) {
                                            path.rLineTo(fArr3[i27], fArr3[i28]);
                                        } else {
                                            path.rMoveTo(fArr3[i27], fArr3[i28]);
                                            f22 = f27;
                                            f23 = f24;
                                        }
                                    } else if (c5 != 's') {
                                        if (c5 == 't') {
                                            if (c6 == 'q' || c6 == 't' || c6 == 'Q' || c6 == 'T') {
                                                f14 = f27 - f18;
                                                f15 = f24 - f26;
                                            } else {
                                                f15 = 0.0f;
                                                f14 = 0.0f;
                                            }
                                            int i29 = i4 + 0;
                                            int i30 = i4 + 1;
                                            path.rQuadTo(f14, f15, fArr3[i29], fArr3[i30]);
                                            float f38 = f14 + f27;
                                            f26 = f15 + f24;
                                            f27 += fArr3[i29];
                                            f24 += fArr3[i30];
                                            f18 = f38;
                                        }
                                        i2 = i4;
                                    } else {
                                        if (c6 == 'c' || c6 == 's' || c6 == 'C' || c6 == 'S') {
                                            f12 = f27 - f18;
                                            f13 = f24 - f26;
                                        } else {
                                            f12 = 0.0f;
                                            f13 = 0.0f;
                                        }
                                        int i31 = i4 + 0;
                                        int i32 = i4 + 1;
                                        int i33 = i4 + 2;
                                        int i34 = i4 + 3;
                                        float f39 = f27;
                                        f = f24;
                                        i2 = i4;
                                        path.rCubicTo(f12, f13, fArr3[i31], fArr3[i32], fArr3[i33], fArr3[i34]);
                                        f2 = fArr3[i31] + f39;
                                        f3 = fArr3[i32] + f;
                                        f4 = f39 + fArr3[i33];
                                        f5 = fArr3[i34];
                                    }
                                    f27 = f22;
                                    f24 = f23;
                                } else {
                                    i2 = i4;
                                    int i35 = i2 + 0;
                                    int i36 = i2 + 1;
                                    path.lineTo(fArr3[i35], fArr3[i36]);
                                    f27 = fArr3[i35];
                                    f24 = fArr3[i36];
                                }
                                f24 += f9;
                                f27 = f8;
                            } else {
                                float f40 = f27;
                                f = f24;
                                i2 = i4;
                                int i37 = i2 + 0;
                                int i38 = i2 + 1;
                                int i39 = i2 + 2;
                                int i40 = i2 + 3;
                                path.rQuadTo(fArr3[i37], fArr3[i38], fArr3[i39], fArr3[i40]);
                                f2 = fArr3[i37] + f40;
                                f3 = fArr3[i38] + f;
                                f4 = f40 + fArr3[i39];
                                f5 = fArr3[i40];
                            }
                            fArr = fArr3;
                            c5 = c5;
                            i3 = i3;
                        } else {
                            float f41 = f27;
                            f = f24;
                            i2 = i4;
                            int i41 = i2 + 2;
                            int i42 = i2 + 3;
                            int i43 = i2 + 4;
                            int i44 = i2 + 5;
                            path.rCubicTo(fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i41], fArr3[i42], fArr3[i43], fArr3[i44]);
                            f2 = fArr3[i41] + f41;
                            f3 = fArr3[i42] + f;
                            f4 = f41 + fArr3[i43];
                            f5 = fArr3[i44];
                        }
                        f6 = f5 + f;
                        f18 = f2;
                        f26 = f3;
                        f7 = f4;
                        f27 = f7;
                        f24 = f6;
                        fArr = fArr3;
                        c5 = c5;
                        i3 = i3;
                    } else {
                        float f42 = f27;
                        float f43 = f24;
                        i2 = i4;
                        int i45 = i2 + 5;
                        int i46 = i2 + 6;
                        fArr = fArr3;
                        a(path, f42, f43, fArr3[i45] + f42, fArr3[i46] + f43, fArr3[i2 + 0], fArr3[i2 + 1], fArr3[i2 + 2], fArr3[i2 + 3] != 0.0f, fArr3[i2 + 4] != 0.0f);
                        f27 = f42 + fArr[i45];
                        f24 = f43 + fArr[i46];
                    }
                    i4 = i2 + i;
                    c2 = 'm';
                    c6 = c5;
                    c5 = c6;
                    fArr3 = fArr;
                    i3 = i3;
                } else {
                    i2 = i4;
                    fArr = fArr3;
                    int i47 = i2 + 5;
                    int i48 = i2 + 6;
                    a(path, f27, f24, fArr[i47], fArr[i48], fArr[i2 + 0], fArr[i2 + 1], fArr[i2 + 2], fArr[i2 + 3] != 0.0f, fArr[i2 + 4] != 0.0f);
                    f27 = fArr[i47];
                    f24 = fArr[i48];
                }
                f18 = f27;
                f26 = f24;
                i4 = i2 + i;
                c2 = 'm';
                c6 = c5;
                c5 = c6;
                fArr3 = fArr;
                i3 = i3;
            }
            int i49 = i3;
            fArr2[0] = f27;
            fArr2[1] = f24;
            fArr2[2] = f18;
            fArr2[3] = f26;
            fArr2[4] = f22;
            fArr2[5] = f23;
            c4 = bVarArr[i49].f75a;
            i3 = i49 + 1;
            c2 = 'm';
            c3 = 0;
        }
    }
}
