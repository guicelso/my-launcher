package com.unisoc.engineermode.core.utils;

/* JADX INFO: loaded from: classes4.dex */
public class HidlUtils {
    private static String HIDL_SOCKET_NAME = "hidl_common_socket";

    public static synchronized String sendCmd(String cmd) {
        return SocketUtils.sendCmd(HIDL_SOCKET_NAME, cmd);
    }
}
