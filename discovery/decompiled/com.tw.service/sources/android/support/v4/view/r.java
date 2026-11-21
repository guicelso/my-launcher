package android.support.v4.view;

import android.graphics.Paint;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowInsets;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public class r {
    static {
        new AtomicInteger(1);
    }

    public static D a(View view, D d) {
        int i = Build.VERSION.SDK_INT;
        WindowInsets windowInsets = (WindowInsets) D.a(d);
        WindowInsets windowInsetsOnApplyWindowInsets = view.onApplyWindowInsets(windowInsets);
        if (windowInsetsOnApplyWindowInsets != windowInsets) {
            windowInsets = new WindowInsets(windowInsetsOnApplyWindowInsets);
        }
        return D.wrap(windowInsets);
    }

    public static void a(View view, Paint paint) {
        int i = Build.VERSION.SDK_INT;
        view.setLayerPaint(paint);
    }

    public static void a(View view, C0027c c0027c) {
        view.setAccessibilityDelegate(c0027c == null ? null : c0027c.J());
    }

    public static void a(View view, j jVar) {
        int i = Build.VERSION.SDK_INT;
        if (jVar == null) {
            view.setOnApplyWindowInsetsListener(null);
        } else {
            view.setOnApplyWindowInsetsListener(new q(jVar));
        }
    }

    public static void a(View view, Runnable runnable) {
        int i = Build.VERSION.SDK_INT;
        view.postOnAnimation(runnable);
    }

    public static void a(View view, String str) {
        int i = Build.VERSION.SDK_INT;
        view.setTransitionName(str);
    }

    public static void b(View view, float f) {
        int i = Build.VERSION.SDK_INT;
        view.setElevation(f);
    }

    public static void b(View view, int i) {
        int i2 = Build.VERSION.SDK_INT;
        view.offsetLeftAndRight(i);
    }

    public static void c(View view, int i) {
        int i2 = Build.VERSION.SDK_INT;
        view.offsetTopAndBottom(i);
    }

    public static void d(View view, int i) {
        int i2 = Build.VERSION.SDK_INT;
        view.setImportantForAccessibility(i);
    }

    public static boolean d(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getFitsSystemWindows();
    }

    public static int e(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getImportantForAccessibility();
    }

    public static int f(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getLayoutDirection();
    }

    public static ViewParent g(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getParentForAccessibility();
    }

    public static String h(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getTransitionName();
    }

    public static float i(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.getZ();
    }

    public static boolean j(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.hasOverlappingRendering();
    }

    public static boolean k(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.isAttachedToWindow();
    }

    public static boolean l(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.isLaidOut();
    }

    public static boolean m(View view) {
        int i = Build.VERSION.SDK_INT;
        return view.isNestedScrollingEnabled();
    }

    public static void n(View view) {
        int i = Build.VERSION.SDK_INT;
        view.postInvalidateOnAnimation();
    }

    public static void o(View view) {
        int i = Build.VERSION.SDK_INT;
        view.stopNestedScroll();
    }
}
