package c.b.a.a;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static boolean a(Context context, String str) {
        return Build.VERSION.SDK_INT < 23 || a.b.d.b.a.a(context, str) == 0;
    }
}
