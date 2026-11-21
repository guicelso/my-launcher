package com.dofun.carsetting.f;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ScreenUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class p {
    public static int[] a(Context context) {
        if (context == null) {
            return new int[2];
        }
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return new int[2];
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            windowManager.getDefaultDisplay().getSize(point);
        }
        return new int[]{point.x, point.y};
    }

    public static int[] b(Context context) {
        if (context == null) {
            return new int[2];
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    public static boolean c(Context context) {
        int[] iArrB = b(context);
        return (iArrB[0] == 0 || iArrB[1] == 0 || ((double) (iArrB[0] / iArrB[1])) < 2.0d) ? false : true;
    }

    public static boolean d(Context context) {
        Display[] displays = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays();
        if (displays != null && displays.length > 1) {
            for (Display display : displays) {
                if (a(display) == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int a(Display display) {
        try {
            Method declaredMethod = Class.forName("android.view.Display").getDeclaredMethod("getType", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(display, new Object[0])).intValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            Log.e("ScreenUtils", "getDisplayType: " + e.getMessage());
            return -1;
        }
    }
}
