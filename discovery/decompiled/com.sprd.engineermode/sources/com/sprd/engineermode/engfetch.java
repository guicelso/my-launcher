package com.sprd.engineermode;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class engfetch {
    private static final String TAG = "engfetch";

    private static native void engf_close(int i);

    private static native int engf_getphasecheck(byte[] bArr, int i);

    private static native int engf_open(int i);

    private static native int engf_read(int i, byte[] bArr, int i2);

    private static native int engf_write(int i, byte[] bArr, int i2);

    static {
        System.loadLibrary("engappjni");
    }

    public void writeCmd(String cmd) {
        int sockid = engopen();
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        DataOutputStream outputBufferStream = new DataOutputStream(outputBuffer);
        String str = "CMD:" + cmd;
        try {
            outputBufferStream.writeBytes(str);
            engwrite(sockid, outputBuffer.toByteArray(), outputBuffer.toByteArray().length);
            Log.d(TAG, "write cmd '" + str + "'");
            engclose(sockid);
        } catch (IOException e) {
            Log.e(TAG, "writebytes error");
        }
    }

    public int engopen() {
        return engopen(0);
    }

    public int engopen(int type) {
        int result = engf_open(type);
        if (result >= 0) {
            return result;
        }
        return 0;
    }

    public void engclose(int fd) {
        engf_close(fd);
    }

    public int engwrite(int fd, byte[] data, int dataSize) {
        int result = engf_write(fd, data, dataSize);
        if (result >= 0) {
            return result;
        }
        return 0;
    }

    public int engread(int fd, byte[] data, int size) {
        int result = engf_read(fd, data, size);
        if (result >= 0) {
            return result;
        }
        return 0;
    }

    public int enggetphasecheck(byte[] data, int size) {
        return engf_getphasecheck(data, size);
    }
}
