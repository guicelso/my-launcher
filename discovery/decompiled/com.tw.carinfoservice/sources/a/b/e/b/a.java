package a.b.e.b;

import a.b.e.f.k0;
import a.b.e.f.n0;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import c.b.a.c;
import c.b.a.d;
import java.math.BigDecimal;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static d a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.tw.carinfoservice.CarServiceCallBack");
        return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof d)) ? new c(iBinder) : (d) iInterfaceQueryLocalInterface;
    }

    public static float b(float f, Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "SPEED_UNIT", 0) == 0) {
            return f;
        }
        double d = f;
        Double.isNaN(d);
        return new BigDecimal((float) (d * 0.2199692d)).setScale(2, 4).floatValue();
    }

    public static String c(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "SPEED_UNIT", 0) != 0 ? "gal" : "L";
    }

    public static String d(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "SPEED_UNIT", 0) != 0 ? "MI" : "KM";
    }

    public static float e(float f, Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "SPEED_UNIT", 0) == 0) {
            return f;
        }
        double d = f;
        Double.isNaN(d);
        return new BigDecimal((float) (d * 0.621371192237d)).setScale(2, 4).floatValue();
    }

    /* JADX WARN: Code duplicated, block: B:43:0x00a3  */
    public static float f(c.b.a.e.a aVar) {
        float f;
        float f2;
        float f3;
        double d;
        int i;
        int[] iArr = aVar.f;
        if (iArr == null) {
            return 0.0f;
        }
        int i2 = 0;
        if (iArr.length == 1) {
            f = iArr[0];
        } else if (aVar.G == 2) {
            String[] strArr = aVar.w;
            if (strArr == null || iArr.length != 2) {
                f = 0.0f;
            } else {
                if (strArr[0].equals("M")) {
                    int[] iArr2 = aVar.f;
                    d = iArr2[0];
                    i = iArr2[1];
                } else {
                    int[] iArr3 = aVar.f;
                    d = iArr3[1];
                    i = iArr3[0];
                }
                double d2 = i;
                Double.isNaN(d2);
                Double.isNaN(d2);
                Double.isNaN(d);
                f = (float) ((d2 * 0.01d) + d);
            }
        } else {
            String[] strArr2 = aVar.w;
            if (strArr2 == null) {
                f = 0.0f;
            } else if (strArr2[0].equals("M")) {
                f = 0.0f;
                int i3 = 0;
                while (true) {
                    int[] iArr4 = aVar.f;
                    if (i3 >= iArr4.length) {
                        break;
                    }
                    if ((iArr4.length - i3) - 1 != 0) {
                        f3 = iArr4[i3] & 255;
                        for (int i4 = 0; i4 < (aVar.f.length - i3) - 1; i4++) {
                            f3 *= 256.0f;
                        }
                    } else {
                        f3 = iArr4[i3] & 255;
                    }
                    f += f3;
                    i3++;
                }
            } else {
                float f4 = 0.0f;
                for (int length = aVar.f.length - 1; length >= 0; length--) {
                    if (length != 0) {
                        f2 = aVar.f[length] & 255;
                        for (int i5 = 0; i5 < length; i5++) {
                            f2 *= 256.0f;
                        }
                    } else {
                        f2 = aVar.f[length] & 255;
                    }
                    f4 += f2;
                }
                f = f4;
            }
        }
        double d3 = aVar.k;
        if (d3 == 0.0d && aVar.o == null) {
            double d4 = f;
            double d5 = aVar.j;
            Double.isNaN(d4);
            Double.isNaN(d4);
            return (float) (d4 + d5);
        }
        double[] dArr = aVar.o;
        if (dArr != null) {
            try {
                if (aVar.i != null) {
                    while (true) {
                        int[] iArr5 = aVar.i;
                        if (i2 < iArr5.length) {
                            if (iArr5[i2] == aVar.v) {
                                int[] iArr6 = aVar.q;
                                if (iArr6 != null) {
                                    int i6 = iArr6[i2];
                                    if (i6 == 0) {
                                        double d6 = f;
                                        double d7 = aVar.o[i2];
                                        Double.isNaN(d6);
                                        Double.isNaN(d6);
                                        return (float) ((d6 * d7) + aVar.n[i2]);
                                    }
                                    if (i6 == 1) {
                                        double d8 = aVar.o[i2];
                                        double d9 = f;
                                        Double.isNaN(d9);
                                        Double.isNaN(d9);
                                        return (float) ((d8 / d9) + aVar.n[i2]);
                                    }
                                    if (i6 == 2) {
                                        double d10 = f;
                                        double d11 = aVar.o[i2];
                                        Double.isNaN(d10);
                                        Double.isNaN(d10);
                                        return (float) (d10 + d11 + aVar.n[i2]);
                                    }
                                    if (i6 == 3) {
                                        double d12 = f;
                                        double d13 = aVar.o[i2];
                                        Double.isNaN(d12);
                                        Double.isNaN(d12);
                                        return (float) ((d12 - d13) + aVar.n[i2]);
                                    }
                                } else {
                                    int i7 = aVar.l;
                                    if (i7 == 0) {
                                        double d14 = f;
                                        double d15 = aVar.o[i2];
                                        Double.isNaN(d14);
                                        Double.isNaN(d14);
                                        return (float) ((d14 * d15) + aVar.n[i2]);
                                    }
                                    if (i7 == 1) {
                                        double d16 = aVar.o[i2];
                                        double d17 = f;
                                        Double.isNaN(d17);
                                        Double.isNaN(d17);
                                        return (float) ((d16 / d17) + aVar.n[i2]);
                                    }
                                    if (i7 == 2) {
                                        double d18 = f;
                                        double d19 = aVar.o[i2];
                                        Double.isNaN(d18);
                                        Double.isNaN(d18);
                                        return (float) (d18 + d19 + aVar.n[i2]);
                                    }
                                    if (i7 == 3) {
                                        double d20 = f;
                                        double d21 = aVar.o[i2];
                                        Double.isNaN(d20);
                                        Double.isNaN(d20);
                                        return (float) ((d20 - d21) + aVar.n[i2]);
                                    }
                                }
                            }
                            i2++;
                        }
                    }
                } else {
                    int[] iArr7 = aVar.q;
                    if (iArr7 != null) {
                        int i8 = aVar.v;
                        int i9 = iArr7[i8];
                        if (i9 == 0) {
                            double d22 = f;
                            double d23 = dArr[i8];
                            Double.isNaN(d22);
                            Double.isNaN(d22);
                            return (float) ((d22 * d23) + aVar.n[i8]);
                        }
                        if (i9 == 1) {
                            if (f == 0.0f) {
                                return f;
                            }
                            double d24 = dArr[i8];
                            double d25 = f;
                            Double.isNaN(d25);
                            Double.isNaN(d25);
                            return (float) ((d24 / d25) + aVar.n[i8]);
                        }
                        if (i9 == 2) {
                            double d26 = f;
                            double d27 = dArr[i8];
                            Double.isNaN(d26);
                            Double.isNaN(d26);
                            return (float) (d26 + d27 + aVar.n[i8]);
                        }
                        if (i9 == 3) {
                            double d28 = f;
                            double d29 = dArr[i8];
                            Double.isNaN(d28);
                            Double.isNaN(d28);
                            return (float) ((d28 - d29) + aVar.n[i8]);
                        }
                    } else {
                        int i10 = aVar.l;
                        if (i10 == 0) {
                            double d30 = f;
                            int i11 = aVar.v;
                            double d31 = dArr[i11];
                            Double.isNaN(d30);
                            Double.isNaN(d30);
                            return (float) ((d30 * d31) + aVar.n[i11]);
                        }
                        if (i10 == 1) {
                            if (f == 0.0f) {
                                return f;
                            }
                            int i12 = aVar.v;
                            double d32 = dArr[i12];
                            double d33 = f;
                            Double.isNaN(d33);
                            Double.isNaN(d33);
                            return (float) ((d32 / d33) + aVar.n[i12]);
                        }
                        if (i10 == 2) {
                            double d34 = f;
                            int i13 = aVar.v;
                            double d35 = dArr[i13];
                            Double.isNaN(d34);
                            Double.isNaN(d34);
                            return (float) (d34 + d35 + aVar.n[i13]);
                        }
                        if (i10 == 3) {
                            double d36 = f;
                            int i14 = aVar.v;
                            double d37 = dArr[i14];
                            Double.isNaN(d36);
                            Double.isNaN(d36);
                            return (float) ((d36 - d37) + aVar.n[i14]);
                        }
                    }
                }
            } catch (Exception unused) {
            }
        } else if (d3 != 0.0d) {
            int i15 = aVar.l;
            if (i15 == 0) {
                double d38 = f;
                Double.isNaN(d38);
                Double.isNaN(d38);
                return (float) ((d38 * d3) + aVar.j);
            }
            if (i15 == 1) {
                double d39 = f;
                Double.isNaN(d39);
                Double.isNaN(d39);
                return (float) ((d3 / d39) + aVar.j);
            }
            if (i15 == 2) {
                double d40 = f;
                Double.isNaN(d40);
                Double.isNaN(d40);
                return (float) (d40 + d3 + aVar.j);
            }
            if (i15 == 3) {
                double d41 = f;
                Double.isNaN(d41);
                Double.isNaN(d41);
                return (float) ((d41 - d3) + aVar.j);
            }
        }
        return f;
    }

    public static long g(Context context, String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        return Settings.System.getLong(context.getApplicationContext().getContentResolver(), str, j);
    }

    public static String[] h(Context context, String str) {
        int identifier;
        Resources resources = context.getResources();
        try {
            identifier = context.getResources().getIdentifier(str, "array", context.getPackageName());
            if (identifier == 0) {
                Log.e("ATBluetooth.ThemeHelper", "getIdentifier: no such resource was found. resName = " + str + ", type = array, pkgName = " + context.getPackageName());
            }
        } catch (Exception e) {
            Log.e("ATBluetooth.ThemeHelper", "getIdentifier error. resName = " + str + ", type = array, pkgName = " + context.getPackageName());
            e.printStackTrace();
            identifier = 0;
        }
        return resources.getStringArray(identifier);
    }

    public static float i(float f, Context context) {
        int i = Settings.System.getInt(context.getContentResolver(), "TEMP_MODE", 0);
        int i2 = Settings.System.getInt(context.getContentResolver(), "TEMP_CONVERT", 0);
        if (i == 1) {
            return i2 != 1 ? ((f * 9.0f) / 5.0f) + 32.0f : f;
        }
        if (i2 != 1) {
            return f;
        }
        double d = f - 32.0f;
        Double.isNaN(d);
        return (float) (d / 1.8d);
    }

    public static String j(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "TEMP_MODE", 0) != 1 ? "℃" : "℉";
    }

    public static int[] k(long j) {
        int i = ((int) (j / 86400000)) + 1;
        int[] iArr = new int[3];
        if (j >= 360) {
            iArr[2] = i / 360;
        }
        if (j >= 30) {
            iArr[1] = (i % 360) / 30;
        }
        iArr[0] = i % 30;
        return iArr;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002f  */
    /* JADX WARN: Code duplicated, block: B:27:0x0055  */
    public static int[] l(c.b.a.e.a aVar) {
        int[] iArrM = {0, 0};
        int[] iArr = aVar.f;
        int i = 0;
        if (iArr.length != 2) {
            int i2 = 0;
            while (true) {
                int[] iArr2 = aVar.g;
                if (i2 >= iArr2.length) {
                    break;
                }
                if (aVar.H == 1) {
                    if (iArr2[i2] == c.b.a.l.b.c(aVar.f[0], i2)) {
                        iArrM = m(aVar, i2);
                    }
                } else if (aVar.f[0] == iArr2[i2]) {
                    iArrM = m(aVar, i2);
                }
                i2++;
            }
        } else if (iArr[0] == 1) {
            while (true) {
                int[] iArr3 = aVar.g;
                if (i >= iArr3.length) {
                    break;
                }
                if (aVar.H == 1) {
                    if (iArr3[i] == c.b.a.l.b.c(aVar.f[1], i)) {
                        iArrM = m(aVar, i);
                    }
                } else if (aVar.f[1] == iArr3[i]) {
                    iArrM = m(aVar, i);
                }
                i++;
            }
        }
        return iArrM;
    }

    public static int[] m(c.b.a.e.a aVar, int i) {
        int iIntValue;
        String str;
        if (aVar.h[i].contains("-")) {
            iIntValue = Integer.valueOf(aVar.h[i].split("-")[0]).intValue();
            str = aVar.h[i].split("-")[1];
        } else {
            iIntValue = Integer.valueOf(aVar.h[i]).intValue();
            str = aVar.h[i];
        }
        return new int[]{iIntValue, Integer.valueOf(str).intValue()};
    }

    public static String n(c.b.a.e.a aVar) {
        try {
            if (aVar.i == null) {
                String[] strArr = aVar.p;
                return strArr != null ? strArr[aVar.v] : aVar.m;
            }
            for (int i = 0; i < aVar.i.length; i++) {
                Log.e("LS123", "getUnit: " + aVar.i[i] + "  " + aVar.v + "   " + aVar.p[i]);
                if (aVar.i[i] == aVar.v) {
                    return aVar.p[i];
                }
            }
            return " ";
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    public static InputConnection o(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection != null && editorInfo.hintText == null) {
            for (ViewParent parent = view.getParent(); parent instanceof View; parent = parent.getParent()) {
                if (parent instanceof n0) {
                    editorInfo.hintText = ((n0) parent).a();
                    break;
                }
            }
        }
        return inputConnection;
    }

    public static int p(int i, int i2) {
        int i3 = (((-i) * 240) / i2) + 240;
        if (i3 > 480) {
            return 480;
        }
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    public static int q(int i) {
        double d = i;
        Double.isNaN(d);
        int i2 = ((int) (d / (-19.2d))) + 240;
        if (i2 > 480) {
            return 480;
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    public static void r(View view, CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 26) {
            view.setTooltipText(charSequence);
            return;
        }
        k0 k0Var = k0.k;
        if (k0Var != null && k0Var.f295b == view) {
            k0.c(null);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            new k0(view, charSequence);
            return;
        }
        k0 k0Var2 = k0.l;
        if (k0Var2 != null && k0Var2.f295b == view) {
            k0Var2.b();
        }
        view.setOnLongClickListener(null);
        view.setLongClickable(false);
        view.setOnHoverListener(null);
    }

    public static int s(int i) {
        if (i == 0) {
            return 1;
        }
        if (i > 165) {
            return 0;
        }
        return ((i - 1) / 15) + 1;
    }

    public static int t(int i) {
        if (i == 0) {
            return 1;
        }
        if (i >= 5) {
            return 0;
        }
        return (i + 1) << 1;
    }

    public static int u(int i) {
        if (i == 1) {
            return 1;
        }
        return i << 1;
    }
}
