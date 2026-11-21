package com.dofun.carsetting.f;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.support.v4.view.PointerIconCompat;
import android.sysprop.DisplayProperties;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.android.internal.app.LocalePicker;

/* JADX INFO: compiled from: DisplayUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class c {
    public static int a(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int b(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void a(Activity activity) {
        activity.getWindow().setFormat(-3);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
            return;
        }
        Window window2 = activity.getWindow();
        WindowManager.LayoutParams attributes = window2.getAttributes();
        attributes.flags = 67108864 | attributes.flags;
        attributes.flags |= 134217728;
        window2.setAttributes(attributes);
    }

    public static void b(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.clearFlags(67108864);
                window.getDecorView().setSystemUiVisibility(1280);
                window.addFlags(67108864);
            } else if (Build.VERSION.SDK_INT >= 19) {
                activity.getWindow().addFlags(67108864);
            }
        } catch (Exception unused) {
        }
    }

    public static void a(View view, Context context) {
        view.setPadding(view.getPaddingLeft(), c(context), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static int c(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void a(Context context, boolean z) {
        Settings.Global.putInt(context.getContentResolver(), "debug.force_rtl", z ? 1 : 0);
        DisplayProperties.debug_force_rtl(Boolean.valueOf(z));
        LocalePicker.updateLocales(context.getResources().getConfiguration().getLocales());
    }

    public static void a(boolean z) {
        try {
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.writeInterfaceToken("android.ui.ISurfaceComposer");
            parcelObtain.writeInt(z ? 1 : 0);
            ServiceManager.getService("SurfaceFlinger").transact(PointerIconCompat.TYPE_TEXT, parcelObtain, null, 0);
            parcelObtain.recycle();
        } catch (RemoteException unused) {
        }
    }
}
