package c.b.b.f;

import android.tw.john.TWUtil;

/* JADX INFO: loaded from: classes.dex */
public class b extends TWUtil {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static b f481a = new b();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static int f482b = 0;

    public static b a() {
        int i = f482b;
        f482b = i + 1;
        if (i == 0) {
            if (f481a.open(new short[]{266, 1286, 1281, 1288, 267, 513, 514, 515, 517, 769, 523, 524, 1293, -24804, -24803, 2050, -13}) != 0) {
                f482b--;
                return null;
            }
            f481a.start();
        }
        return f481a;
    }

    public void close() {
        int i = f482b;
        if (i > 0) {
            int i2 = i - 1;
            f482b = i2;
            if (i2 == 0) {
                stop();
                super.close();
            }
        }
    }
}
