package com.tw.service.xt.utils;

import android.os.Build;
import com.tw.service.xt.TWTW;

/* JADX INFO: loaded from: classes.dex */
public class DeviceUtils {
    public static boolean isLightFromMCU() {
        return isTS9() || isTS10() || isTS18() || isT5Q() || isTS7();
    }

    public static boolean isT5Q() {
        return "QUAD-CORE T507".equals(Build.MODEL);
    }

    public static boolean isT7() {
        return "QUAD-CORE t7 p1".equals(Build.MODEL);
    }

    public static boolean isTS10() {
        return "ums512_1h10_Natv".equals(Build.MODEL);
    }

    public static boolean isTS18() {
        String str = Build.MODEL;
        return TWTW.getSystemVersion().startsWith("TS18.1.2") || TWTW.getSystemVersion().startsWith("TS18.2.2") || "uis8581a2h10".equals(str) || "s9863a1h10_Natv".equals(str);
    }

    public static boolean isTS7() {
        return "sp7731e_1h10_native".equals(Build.MODEL);
    }

    public static boolean isTS9() {
        return "sp9853i_1h10_vmm".equals(Build.MODEL);
    }
}
