package com.tw.core.b;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.B;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static final String cu = c.class.getSimpleName();
    private static String cv;

    private c() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    public static int fS(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int fT(Context context) {
        WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String fU(Context context) {
        if (cv == null) {
            try {
                cv = String.valueOf(Configuration.class.getMethod("getForceRotation", (Class[]) null).invoke(null, (Object[]) null));
            } catch (Exception e) {
                e.printStackTrace();
                cv = String.valueOf(context.getResources().getConfiguration().orientation);
            }
            B.ci(cu, "sScreenOrientation = %s", cv);
        }
        return cv;
    }

    public static String fV(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.CHINA)).format(Math.sqrt(Math.pow(point.y / displayMetrics.ydpi, 2.0d) + Math.pow(point.x / displayMetrics.xdpi, 2.0d)));
    }

    public static int fW() {
        return DoFunApplication.nu().getResources().getDisplayMetrics().densityDpi;
    }
}
