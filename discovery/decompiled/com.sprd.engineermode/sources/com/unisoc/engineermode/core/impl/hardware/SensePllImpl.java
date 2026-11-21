package com.unisoc.engineermode.core.impl.hardware;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.intf.ISensePll;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: SensePllImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u000fH\u0002J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/unisoc/engineermode/core/impl/hardware/SensePllImpl;", "Lcom/unisoc/engineermode/core/intf/ISensePll;", "()V", "CMD_ERROR", "", "TAG", "connectSocket", "Landroid/net/LocalSocket;", "read", "address", "count", "", "sendCmdAndResult", "cmd", "startCmdService", "", "stopCmdService", "write", "data", "name"}, k = 1, mv = {1, 1, 13})
public final class SensePllImpl implements ISensePll {
    private static final String CMD_ERROR = "cmd_error";
    public static final SensePllImpl INSTANCE = new SensePllImpl();
    private static final String TAG = "SENSEPLL";

    private SensePllImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.ISensePll
    @NotNull
    public String read(@NotNull String address, int count) throws Exception {
        Intrinsics.checkParameterIsNotNull(address, "address");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {Integer.valueOf(count), address};
        String readCmd = String.format("lookat -l %d 0x%s", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(readCmd, "java.lang.String.format(format, *args)");
        Log.d(TAG, "readCmd->" + readCmd);
        String readPllResult = sendCmdAndResult(readCmd);
        Log.d(TAG, "result->" + readPllResult);
        if (!StringsKt.contains$default((CharSequence) readPllResult, (CharSequence) "VALUE", false, 2, (Object) null)) {
            throw new OperationFailedException(ErrorCode.CMD_EXEC_ERROR, readPllResult);
        }
        return readPllResult;
    }

    @Override // com.unisoc.engineermode.core.intf.ISensePll
    public void write(@NotNull String address, @NotNull String data) throws Exception {
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(data, "data");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {data, address};
        String writeCmd = String.format("lookat -s 0x%s 0x%s", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(writeCmd, "java.lang.String.format(format, *args)");
        Log.d(TAG, "writeCmd->" + writeCmd);
        String result = sendCmdAndResult(writeCmd);
        Log.d(TAG, "write result->" + result);
        if (!StringsKt.contains$default((CharSequence) result, (CharSequence) "Result", false, 2, (Object) null)) {
            throw new OperationFailedException(ErrorCode.CMD_EXEC_ERROR, result);
        }
    }

    private final LocalSocket connectSocket() {
        LocalSocket socketClient = new LocalSocket();
        try {
            socketClient.connect(new LocalSocketAddress("cmd_skt", LocalSocketAddress.Namespace.ABSTRACT));
            return socketClient;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socketClient.close();
                return null;
            } catch (IOException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    private final String sendCmdAndResult(String cmd) throws IllegalAccessException, OperationFailedException, InvocationTargetException {
        OutputStream os = (OutputStream) null;
        InputStream ins = (InputStream) null;
        byte[] buffer = new byte[1024];
        startCmdService();
        LocalSocket socketClient = connectSocket();
        try {
            if (socketClient == null) {
                stopCmdService();
                throw new OperationFailedException(ErrorCode.SOCKET_CONN_FAILED);
            }
            try {
                OutputStream os2 = socketClient.getOutputStream();
                if (os2 != null) {
                    String cmmand = cmd + (char) 0;
                    Charset charset = StandardCharsets.UTF_8;
                    Intrinsics.checkExpressionValueIsNotNull(charset, "StandardCharsets.UTF_8");
                    if (cmmand == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    byte[] bytes = cmmand.getBytes(charset);
                    Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
                    os2.write(bytes);
                    os2.flush();
                }
                InputStream ins2 = socketClient.getInputStream();
                if (ins2 == null) {
                    Intrinsics.throwNpe();
                }
                int count = ins2.read(buffer, 0, 1024);
                String result = new String(buffer, Charsets.UTF_8);
                Log.d(TAG, "count is " + count + ",result is " + result);
                try {
                    ins2.close();
                    if (os2 != null) {
                        os2.close();
                    }
                    socketClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopCmdService();
                return result;
            } catch (IOException e2) {
                Log.d(TAG, "Failed get cmd output: " + e2);
                e2.printStackTrace();
                throw new OperationFailedException(ErrorCode.CMD_EXEC_ERROR, e2.getMessage());
            }
        } catch (Throwable th) {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    stopCmdService();
                    throw th;
                }
            }
            if (os != null) {
                os.close();
            }
            socketClient.close();
            stopCmdService();
            throw th;
        }
    }

    private final void startCmdService() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set("persist.sys.cmdservice.enable", "enable");
        String status = SystemPropertiesProxy.get("persist.sys.cmdservice.enable", "");
        Log.d(TAG, "status:" + status);
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void stopCmdService() throws IllegalAccessException, InvocationTargetException {
        SystemPropertiesProxy.set("persist.sys.cmdservice.enable", "disable");
        String disable = SystemPropertiesProxy.get("persist.sys.cmdservice.enable", "");
        Log.d(TAG, "disable:" + disable);
    }
}
