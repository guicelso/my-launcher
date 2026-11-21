package com.unisoc.engineermode.core.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes4.dex */
public class IATUtils {
    public static final String AT_CONNECT = "CONNECT";
    public static final String AT_FAIL = "AT FAILED";
    public static final String AT_OK = "OK";
    private static final String AT_SOCKET_NAME = "miscserver";
    private static final String TAG = "IATUtils";

    public static String sendAt(final String cmd, final String serverName) {
        if (!isModemATAvaliable()) {
            Log.d(TAG, "modem at not Aviable, return");
            return AT_FAIL;
        }
        ExecutorService exec = Executors.newFixedThreadPool(1);
        try {
            Future<String> future = exec.submit(new Callable() { // from class: com.unisoc.engineermode.core.utils.-$$Lambda$IATUtils$iriDXb1MMH8Xw_MLjeR6dQu7-nY
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return IATUtils.sendATCmd(cmd, serverName);
                }
            });
            String futureObj = future.get(2000L, TimeUnit.MILLISECONDS);
            exec.shutdown();
            return futureObj != null ? futureObj : AT_FAIL;
        } catch (TimeoutException ex) {
            Log.d(TAG, "modem at timeout", ex);
            return AT_FAIL;
        } catch (Exception e) {
            Log.d(TAG, "modem at exception", e);
            return AT_FAIL;
        }
    }

    private static int getPhoneIdByChannelName(String channelName) {
        return (!channelName.contains("atchannel0") && channelName.contains("atchannel1")) ? 1 : 0;
    }

    @SuppressLint({"DefaultLocale"})
    private static String getAtCmd(String cmd, int phoneId) {
        return String.format("%s sendAt %d %s", AT_SOCKET_NAME, Integer.valueOf(phoneId), cmd);
    }

    public static synchronized String sendATCmd(String cmd, int phoneId) {
        Log.d(TAG, "send AT: " + cmd);
        return HidlUtils.sendCmd(getAtCmd(cmd, phoneId));
    }

    public static synchronized String sendATCmd(String cmd, String channelName) {
        return sendATCmd(cmd, getPhoneIdByChannelName(channelName));
    }

    private static boolean isModemATAvaliable() throws IllegalAccessException, InvocationTargetException {
        String modemAssert = SystemPropertiesProxy.get("vendor.ril.modem.assert", "0");
        if (TextUtils.isEmpty(modemAssert) || modemAssert.equals("0")) {
            Log.d(TAG, "check modem at ok");
            return true;
        }
        SharedPreferences sp = CommonKt.appCtx.getSharedPreferences("com.sprd.engineermode_preferences", 0);
        if (modemAssert.contains("1") && sp.getBoolean("key_manualassert", true)) {
            Log.d(TAG, "checked modem at ok");
            return true;
        }
        Log.d(TAG, "check modem at fail");
        return false;
    }
}
