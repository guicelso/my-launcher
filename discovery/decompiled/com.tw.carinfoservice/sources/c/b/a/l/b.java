package c.b.a.l;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.tw.john.TWUtil;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class b extends TWUtil {
    public static String f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static b f448a = new b();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static int f449b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static int f450c = -1;
    public static int d = 0;
    public static int e = -1;
    public static boolean g = false;

    public static boolean a(byte b2, int i) {
        return (b2 & (1 << i)) != 0;
    }

    public static int b(byte b2, int i) {
        return (b2 & (1 << i)) == 0 ? 0 : 1;
    }

    public static int c(int i, int i2) {
        return (((byte) i) & (1 << i2)) == 0 ? 0 : 1;
    }

    public static int d(byte b2, int i, int i2) {
        return (b2 >> i) & ((byte) (255 >> (8 - i2))) & 255;
    }

    public static String e(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        for (PackageInfo packageInfo : context.getPackageManager().getInstalledPackages(0)) {
            if (str.equals(packageInfo.packageName)) {
                StringBuilder sbB = c.a.a.a.a.b("versionName:");
                sbB.append(packageInfo.versionName);
                Log.d("TWCar", sbB.toString());
                return packageInfo.versionName;
            }
        }
        return "";
    }

    public static b f() {
        int i = f449b;
        f449b = i + 1;
        if (i == 0) {
            if (f448a.open(new short[]{266, 267, 513, 514, 516, 517, 524, 1281, 1287, 1288, 1289, 1290, 1291, 1292, 1293, 1294, 524, 1297, 1298, -24804, -31471, -31462, -31482, -31481, -24832}) != 0) {
                f449b--;
                return null;
            }
            f448a.start();
        }
        return f448a;
    }

    public void close() {
        int i = f449b;
        if (i > 0) {
            int i2 = i - 1;
            f449b = i2;
            if (i2 == 0) {
                stop();
                super.close();
            }
        }
    }
}
