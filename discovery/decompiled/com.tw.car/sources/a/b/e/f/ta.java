package a.b.e.f;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ta {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static Method f382a;

    static {
        int i = Build.VERSION.SDK_INT;
        try {
            f382a = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
            if (f382a.isAccessible()) {
                return;
            }
            f382a.setAccessible(true);
        } catch (NoSuchMethodException unused) {
            Log.d("ViewUtils", "Could not find method computeFitSystemWindows. Oh well.");
        }
    }

    public static boolean a(View view) {
        return a.b.d.h.n.c(view) == 1;
    }
}
