package c.b.a;

import android.tw.john.TWUtil;

/* JADX INFO: loaded from: classes.dex */
public class h extends TWUtil {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static h f451a = new h();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static int f452b = 0;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static int f453c = -1;
    public static String d;

    public static int a(byte b2, int i, int i2) {
        return (b2 >> i) & ((byte) (255 >> (8 - i2))) & 255;
    }

    public static h a() {
        int i = f452b;
        f452b = i + 1;
        if (i == 0) {
            if (f451a.open(new short[]{266, 1281, 1288, 267, 513, 524, 523, -24804}) != 0) {
                f452b--;
                return null;
            }
            f451a.start();
        }
        return f451a;
    }

    public static boolean a(byte b2, int i) {
        return (b2 & (1 << i)) != 0;
    }

    public static int b(byte b2, int i) {
        return (b2 & (1 << i)) == 0 ? 0 : 1;
    }

    public int a(int i, int i2, int i3) {
        return write(1281, i, 2, new byte[]{(byte) i2, (byte) i3});
    }

    public int a(int i, int i2, int i3, int i4) {
        return write(1281, i, 3, new byte[]{(byte) i2, (byte) i3, (byte) i4});
    }

    public void close() {
        int i = f452b;
        if (i > 0) {
            int i2 = i - 1;
            f452b = i2;
            if (i2 == 0) {
                stop();
                super.close();
            }
        }
    }
}
