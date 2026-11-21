package com.unisoc.engineermode.core.utils;

import android.util.Log;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import java.lang.reflect.InvocationTargetException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: CmdUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0003J\b\u0010\b\u001a\u00020\u0007H\u0003J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/unisoc/engineermode/core/utils/CmdUtils;", "", "()V", "CMD_SRV_SOCKET_NAME", "", "TAG", "disableCmdservice", "", "enableCmdservice", "run", "cmd", "name"}, k = 1, mv = {1, 1, 13})
public final class CmdUtils {
    private static final String CMD_SRV_SOCKET_NAME = "cmd_skt";
    public static final CmdUtils INSTANCE = new CmdUtils();
    private static final String TAG = "CmdUtils";

    private CmdUtils() {
    }

    @JvmStatic
    @Nullable
    public static final synchronized String run(@NotNull String cmd) {
        String result;
        Intrinsics.checkParameterIsNotNull(cmd, "cmd");
        Log.d(TAG, "run cmd, cmd is " + cmd);
        enableCmdservice();
        result = SocketUtils.sendCmd(CMD_SRV_SOCKET_NAME, cmd + '\n');
        disableCmdservice();
        Log.d(TAG, "run cmd, result is " + result);
        return result;
    }

    @JvmStatic
    private static final void enableCmdservice() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set("persist.sys.cmdservice.enable", "enable");
    }

    @JvmStatic
    private static final void disableCmdservice() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set("persist.sys.cmdservice.enable", "disable");
    }
}
