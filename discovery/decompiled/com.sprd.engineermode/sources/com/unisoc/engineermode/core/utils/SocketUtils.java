package com.unisoc.engineermode.core.utils;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes4.dex */
public class SocketUtils {
    private static final int AT_BUFFER_SIZE = 4096;
    public static final String OK = "OK";
    public static final String TAG = "SocketUtils";

    /* JADX INFO: Access modifiers changed from: private */
    public static void timeout(Object obj) {
        Log.w(TAG, "socket timeout");
        LocalSocket socket = (LocalSocket) obj;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendCmd(String socketName, String cmd) throws IOException {
        Thread thread = Thread.currentThread();
        String caller = Thread.currentThread().getStackTrace()[2].getClassName();
        String strcmd = String.format("[%s][%d]<%s>", caller, Long.valueOf(thread.getId()), cmd.replace('\n', '\\'));
        Log.d(TAG, socketName + " send cmd: " + strcmd);
        LocalSocket socketClient = new LocalSocket();
        LocalSocketAddress mSocketAddress = new LocalSocketAddress(socketName, LocalSocketAddress.Namespace.ABSTRACT);
        String result = null;
        try {
            socketClient.connect(mSocketAddress);
            OutputStream ops = null;
            InputStream ins = null;
            byte[] buf = new byte[4096];
            Watchdog wd = null;
            try {
                try {
                    try {
                        Log.i(TAG, strcmd + "connect " + socketName + " success");
                        ops = socketClient.getOutputStream();
                        ins = socketClient.getInputStream();
                        ops.write(cmd.getBytes(StandardCharsets.UTF_8));
                        ops.flush();
                        Log.d(TAG, strcmd + " write cmd and flush done");
                        wd = new Watchdog(socketName, cmd);
                        wd.setTimeoutCallback($$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE.INSTANCE, socketClient);
                        wd.wantEat();
                        int count = ins.read(buf, 0, 4096);
                        wd.feedFood();
                        Log.d(TAG, strcmd + " result read done");
                        result = "";
                        if (count != -1) {
                            byte[] temp = new byte[count];
                            System.arraycopy(buf, 0, temp, 0, count);
                            result = new String(temp, StandardCharsets.UTF_8);
                        } else {
                            Log.e(TAG, strcmd + " read failed");
                        }
                        Log.d(TAG, strcmd + "count = " + count + ", result is: " + result);
                        wd.feedFood();
                        ops.close();
                        ins.close();
                        socketClient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    if (wd != null) {
                        wd.feedFood();
                    }
                    if (ops != null) {
                        ops.close();
                    }
                    if (ins != null) {
                        ins.close();
                    }
                    socketClient.close();
                }
                Log.d(TAG, strcmd + "handle over and result is: " + result);
                return result;
            } catch (Throwable e3) {
                if (0 != 0) {
                    wd.feedFood();
                }
                if (0 != 0) {
                    try {
                        ops.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        throw e3;
                    }
                }
                if (0 != 0) {
                    ins.close();
                }
                socketClient.close();
                throw e3;
            }
        } catch (IOException e5) {
            Log.e(TAG, "connect to " + socketName + " failed", e5);
            try {
                socketClient.close();
            } catch (IOException e6) {
            }
            return null;
        }
    }

    public static boolean sendCmd(String socketName, byte[] cmd, byte[] result) {
        Log.d(TAG, socketName + " send byte cmd");
        LocalSocket socketClient = new LocalSocket();
        LocalSocketAddress mSocketAddress = new LocalSocketAddress(socketName, LocalSocketAddress.Namespace.ABSTRACT);
        try {
            socketClient.connect(mSocketAddress);
            Watchdog wd = null;
            try {
                try {
                    OutputStream ops = socketClient.getOutputStream();
                    try {
                        InputStream ins = socketClient.getInputStream();
                        try {
                            Log.i(TAG, "connect " + socketName + " success");
                            ops.write(cmd);
                            ops.flush();
                            Log.d(TAG, "write cmd and flush done");
                            Watchdog wd2 = new Watchdog(socketName, "byte command");
                            wd2.setTimeoutCallback($$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE.INSTANCE, socketClient);
                            wd2.wantEat();
                            int count = ins.read(result);
                            wd2.feedFood();
                            Log.d(TAG, "result read done, count=" + count);
                            $closeResource(null, ins);
                            $closeResource(null, ops);
                            wd2.feedFood();
                            try {
                                socketClient.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "cmd over and result len: " + result.length);
                            return true;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                if (ins != null) {
                                    $closeResource(th, ins);
                                }
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            if (ops != null) {
                                $closeResource(th3, ops);
                            }
                            throw th4;
                        }
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                    if (0 != 0) {
                        wd.feedFood();
                    }
                    try {
                        socketClient.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    return false;
                }
            } catch (Throwable th5) {
                if (0 != 0) {
                    wd.feedFood();
                }
                try {
                    socketClient.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                throw th5;
            }
        } catch (IOException e5) {
            Log.e(TAG, "connect to " + socketName + " failed", e5);
            try {
                socketClient.close();
            } catch (IOException e6) {
            }
            return false;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) throws Exception {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
        } catch (Throwable th) {
            x0.addSuppressed(th);
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0117 A[Catch: IOException -> 0x0113, all -> 0x0168, TryCatch #1 {IOException -> 0x0113, blocks: (B:28:0x010f, B:32:0x0117, B:34:0x011c), top: B:61:0x010f, outer: #4 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x011c A[Catch: IOException -> 0x0113, all -> 0x0168, TRY_LEAVE, TryCatch #1 {IOException -> 0x0113, blocks: (B:28:0x010f, B:32:0x0117, B:34:0x011c), top: B:61:0x010f, outer: #4 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x0145 A[Catch: IOException -> 0x0141, all -> 0x0168, TryCatch #2 {IOException -> 0x0141, blocks: (B:43:0x013d, B:47:0x0145, B:49:0x014a), top: B:63:0x013d, outer: #4 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x014a A[Catch: IOException -> 0x0141, all -> 0x0168, TRY_LEAVE, TryCatch #2 {IOException -> 0x0141, blocks: (B:43:0x013d, B:47:0x0145, B:49:0x014a), top: B:63:0x013d, outer: #4 }] */
    @Deprecated
    public static synchronized String sendCmdAndRecResult(String socketName, LocalSocketAddress.Namespace namespace, String strcmd) {
        String result;
        Log.d(TAG, socketName + " send cmd: " + strcmd);
        byte[] buf = new byte[255];
        Log.d(TAG, "set cmd: " + strcmd);
        LocalSocket socketClient = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            try {
                socketClient = new LocalSocket();
                LocalSocketAddress socketAddress = new LocalSocketAddress(socketName, namespace);
                if (!socketClient.isConnected()) {
                    Log.d(TAG, "isConnected...");
                    socketClient.connect(socketAddress);
                }
                Log.d(TAG, "socketClient connect is " + socketClient.isConnected());
                outputStream = socketClient.getOutputStream();
                if (outputStream != null) {
                    String cmd = strcmd + (char) 0;
                    outputStream.write(cmd.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
                inputStream = socketClient.getInputStream();
                int count = inputStream.read(buf, 0, 255);
                result = new String(buf, "utf-8");
                Log.d(TAG, "count = " + count + ", result is: " + result);
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        Log.d(TAG, "catch exception is " + e);
                        return null;
                    }
                }
                inputStream.close();
                socketClient.close();
            } catch (IOException e2) {
                Log.e(TAG, "Failed get output stream: " + e2.toString());
                if (outputStream == null) {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (socketClient != null) {
                        socketClient.close();
                    }
                    return null;
                }
                try {
                    outputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (socketClient != null) {
                        socketClient.close();
                    }
                    return null;
                } catch (IOException e3) {
                    Log.d(TAG, "catch exception is " + e3);
                    return null;
                }
            }
        } catch (Throwable e4) {
            if (outputStream == null) {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socketClient != null) {
                    socketClient.close();
                }
                throw e4;
            }
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socketClient != null) {
                    socketClient.close();
                }
                throw e4;
            } catch (IOException e5) {
                Log.d(TAG, "catch exception is " + e5);
                return null;
            }
        }
        return result;
    }

    public static synchronized String sendCmdNoCloseSocket(String socketName, LocalSocketAddress.Namespace namespace, String strcmd) {
        String result;
        byte[] buf = new byte[255];
        result = null;
        LocalSocket noCloseSocketClient = null;
        if (0 == 0) {
            try {
                Log.d(TAG, "noCloseSocketClient is null");
                noCloseSocketClient = new LocalSocket();
                LocalSocketAddress socketAddress = new LocalSocketAddress(socketName, namespace);
                noCloseSocketClient.connect(socketAddress);
            } catch (IOException e) {
                Log.e(TAG, "Failed get output stream: " + e);
            }
        }
        Log.d(TAG, "noCloseSocketClient connect is " + noCloseSocketClient.isConnected());
        OutputStream noCloseOutputStream = noCloseSocketClient.getOutputStream();
        if (noCloseOutputStream != null) {
            String cmd = strcmd + (char) 0;
            noCloseOutputStream.write(cmd.getBytes(StandardCharsets.UTF_8));
            noCloseOutputStream.flush();
        }
        InputStream noCloseInputStream = noCloseSocketClient.getInputStream();
        int count = noCloseInputStream.read(buf, 0, 255);
        result = new String(buf, "utf-8");
        Log.d(TAG, "count = " + count + ", result is: " + result);
        return result;
    }
}
