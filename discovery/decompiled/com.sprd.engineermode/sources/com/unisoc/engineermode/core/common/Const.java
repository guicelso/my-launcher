package com.unisoc.engineermode.core.common;

import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class Const {
    private static String TAG = "Const";
    public static boolean DEBUG = true;

    public static boolean isBoardISharkL210c10() throws IllegalAccessException, InvocationTargetException {
        String board = SystemPropertiesProxy.get("ro.product.board", EnvironmentCompat.MEDIA_UNKNOWN);
        Log.d(TAG, "isBoardISharkL210c10 board = " + board);
        return board.equals("sp9853i_10c10_vmm") || board.equals("sp9832e_10c10_32b");
    }

    public static boolean isSupportEVS() throws IllegalAccessException, InvocationTargetException {
        String board = SystemPropertiesProxy.get("ro.boot.hardware", EnvironmentCompat.MEDIA_UNKNOWN);
        Log.d(TAG, "isBoardISharkL210c10 board = " + board);
        if ((board != null && board.contains("9850")) || board.contains("9853i") || board.contains("sp9832e") || board.contains("s9863a")) {
            return true;
        }
        return false;
    }

    public static boolean isWPlusG() throws IllegalAccessException, InvocationTargetException {
        String netMode = SystemPropertiesProxy.get("persist.vendor.radio.modem.capability", EnvironmentCompat.MEDIA_UNKNOWN);
        return netMode.equals("W_G,G") || netMode.equals("W_G,W_G");
    }

    public static boolean isGSIVersion() {
        String[] gsiTags = {"generic_arm_a", "generic_x86_64_a", "generic_arm64_a"};
        return Arrays.asList(gsiTags).contains(SystemPropertiesProxy.get("ro.board.device"));
    }

    public static boolean isBoardCAT4() {
        boolean mBoard = SystemPropertiesProxy.get("ro.vendor.wcn.hardware.board", EnvironmentCompat.MEDIA_UNKNOWN).contains("sharkle");
        Log.d(TAG, "isBoardCAT4 = " + mBoard);
        return mBoard;
    }

    public static boolean isMarlin3() {
        return SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("marlin3");
    }

    public static boolean isMarlin3Lite() {
        try {
            return SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("marlin3_lite");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMarlin3E() {
        try {
            return SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equalsIgnoreCase("marlin3e");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isMarlin2() {
        return SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("marlin2") || SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("sharkle") || SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("sharkl2") || SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("pike2") || SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("sharkl3");
    }

    public static boolean isMarlin() {
        return SystemPropertiesProxy.get("ro.vendor.modem.wcn.enable").equals("1");
    }

    public static boolean isSr2351() {
        return SystemPropertiesProxy.get("ro.vendor.wcn.hardware.product").equals("sr2351");
    }

    public static boolean isUser() {
        return SystemPropertiesProxy.get("ro.build.type").equalsIgnoreCase("user");
    }

    public static boolean isHisense() {
        try {
            return SystemPropertiesProxy.get("ro.product.vendor.name").equals("ums312_20c10_ctcc_nosec");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
