package com.unisoc.engineermode.core.impl.hardware;

import android.util.Log;
import com.unisoc.engineermode.core.utils.SocketUtils;
import java.nio.charset.StandardCharsets;
import kotlin.UByte;

/* JADX INFO: loaded from: classes4.dex */
public class PhaseCheckParse {
    private AdaptBinder binder;
    private byte[] stream;
    private static String TAG = "PhaseCheckParse";
    private static int TYPE_WRITE_CHARGE_SWITCH = 6;
    private static int TYPE_GET_KERNEL_LOG_LEVEL = 11;
    private static int TYPE_SET_KERNEL_LOG_LEVEL = 12;
    private static int TYPE_WRITE_MIPI_SWITCH = 13;
    private static int TYPE_GET_CC_TEST_RESULT = 15;
    private static int TYPE_GET_CC_TEST_VOL = 19;
    private static int TYPE_GET_DELTANV_INFO = 20;
    private static int TYPE_READ_CHARGE_STATUS = 21;
    private static int TYPE_READ_CHARGE_LEVEL = 22;
    private static int TYPE_GET_CC_ENERGY_NEW_KERNEL = 27;
    private static int TYPE_GET_CC_VOL_NEW_KERNEL = 28;
    private static int TYPE_SET_CABC_MODE = 32;
    private static int BUF_SIZE = 4096;

    public PhaseCheckParse() {
        this.stream = new byte[300];
        if (!checkPhaseCheck()) {
            this.stream = null;
        }
        this.binder = new AdaptBinder();
        Log.e(TAG, "Get The service connect!");
    }

    private boolean checkPhaseCheck() {
        Log.d(TAG, " " + ((int) this.stream[0]) + ((int) this.stream[1]) + ((int) this.stream[2]) + ((int) this.stream[3]));
        byte[] bArr = this.stream;
        if (bArr[0] != 57 && bArr[0] != 53) {
            return false;
        }
        byte[] bArr2 = this.stream;
        return bArr2[1] == 48 && bArr2[2] == 80 && bArr2[3] == 83;
    }

    public String getDeltaNVInfo() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_DELTANV_INFO, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "getDeltaNVInfo retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public boolean writeChargeSwitch(int value) {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        data.writeInt(value);
        this.binder.transact(TYPE_WRITE_CHARGE_SWITCH, data, reply, 0);
        Log.e(TAG, "writeChargeSwitch data = " + reply.readString());
        data.recycle();
        return true;
    }

    public String readChargeState() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_READ_CHARGE_STATUS, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "readChargeState retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public String readChargeLevel() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_READ_CHARGE_LEVEL, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "readChargeLevel retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public boolean writeMIPISwitch(int value) {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        data.writeInt(value);
        this.binder.transact(TYPE_WRITE_MIPI_SWITCH, data, reply, 0);
        Log.e(TAG, "writeMIPISwitch data = " + reply.readString());
        data.recycle();
        return true;
    }

    public String getCcTestSwitch() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_CC_TEST_RESULT, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "getCcTestSwitch retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public String getCcTestVoltage() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_CC_TEST_VOL, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "getCcTestVoltage retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public String getCcEnergyNewKernel() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_CC_ENERGY_NEW_KERNEL, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "getCcEnergyNewKernel retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public String getCcVoltagesNewKernel() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_CC_VOL_NEW_KERNEL, data, reply, 0);
        String retValue = reply.readString();
        Log.e(TAG, "getCcVoltagesNewKernel retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public int getKernelLogLevelState() {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        this.binder.transact(TYPE_GET_KERNEL_LOG_LEVEL, data, reply, 0);
        int retValue = reply.readInt();
        Log.e(TAG, "get kernel log level state retValue is : " + retValue);
        data.recycle();
        return retValue;
    }

    public boolean setKernelLogLevelState(int state) {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        data.writeInt(state);
        this.binder.transact(TYPE_SET_KERNEL_LOG_LEVEL, data, reply, 0);
        int retValue = reply.readInt();
        Log.e(TAG, "set kernel log level state retValue is : " + retValue);
        data.recycle();
        return retValue == 1;
    }

    public boolean setCabc(int value) {
        Parcel data = new Parcel();
        Parcel reply = new Parcel();
        data.writeInt(value);
        this.binder.transact(TYPE_SET_CABC_MODE, data, reply, 0);
        int retValue = reply.readInt();
        Log.e(TAG, "setCabc data = " + reply.readString());
        data.recycle();
        return 1 == retValue;
    }

    private static class AdaptParcel {
        int code;
        byte[] data;
        int dataSize;
        int replySize;

        private AdaptParcel() {
        }
    }

    static class AdaptBinder {
        private static final String SOCKET_NAME = "phasecheck_srv";
        private AdaptParcel mAdpt = new AdaptParcel();

        AdaptBinder() {
            this.mAdpt.data = new byte[PhaseCheckParse.BUF_SIZE];
            AdaptParcel adaptParcel = this.mAdpt;
            adaptParcel.code = 0;
            adaptParcel.dataSize = 0;
            adaptParcel.replySize = 0;
        }

        private void int2byte(byte[] dst, int offset, int value) {
            dst[offset + 3] = (byte) ((value >> 24) & 255);
            dst[offset + 2] = (byte) ((value >> 16) & 255);
            dst[offset + 1] = (byte) ((value >> 8) & 255);
            dst[offset] = (byte) (value & 255);
        }

        int byte2Int(byte[] bytes, int off) {
            int b0 = bytes[off] & UByte.MAX_VALUE;
            int b1 = bytes[off + 1] & UByte.MAX_VALUE;
            int b2 = bytes[off + 2] & UByte.MAX_VALUE;
            int b3 = bytes[off + 3] & UByte.MAX_VALUE;
            return (b1 << 8) | b0 | (b2 << 16) | (b3 << 24);
        }

        synchronized void sendCmdAndRecResult(AdaptParcel adpt) {
            Log.d(PhaseCheckParse.TAG, "send cmd");
            byte[] buf = new byte[PhaseCheckParse.BUF_SIZE];
            int2byte(buf, 0, adpt.code);
            int2byte(buf, 4, adpt.dataSize);
            int2byte(buf, 8, adpt.replySize);
            System.arraycopy(adpt.data, 0, buf, 12, adpt.dataSize + adpt.replySize);
            Log.d(PhaseCheckParse.TAG, "code = " + adpt.code);
            Log.d(PhaseCheckParse.TAG, "dataSize = " + adpt.dataSize);
            Log.d(PhaseCheckParse.TAG, "replySize = " + adpt.replySize);
            if (!SocketUtils.sendCmd(SOCKET_NAME, buf, buf)) {
                Log.e(PhaseCheckParse.TAG, "send command failed");
                return;
            }
            adpt.code = byte2Int(buf, 0);
            adpt.dataSize = byte2Int(buf, 4);
            adpt.replySize = byte2Int(buf, 8);
            Log.d(PhaseCheckParse.TAG, "code = " + adpt.code);
            Log.d(PhaseCheckParse.TAG, "dataSize = " + adpt.dataSize);
            Log.d(PhaseCheckParse.TAG, "replySize =  " + adpt.replySize);
            System.arraycopy(buf, 12, adpt.data, 0, adpt.dataSize + adpt.replySize);
        }

        private void convertParcel(AdaptParcel adpt, int code, Parcel data, Parcel reply) {
            data.setDataPosition(0);
            reply.setDataPosition(0);
            data.writeByteArrayInternal(adpt.data, 0, adpt.dataSize);
            reply.writeByteArrayInternal(adpt.data, adpt.dataSize, adpt.replySize);
            Log.e(PhaseCheckParse.TAG, "convertParcel: dataSize = " + data.dataSize() + ", replySize = " + reply.dataSize());
            data.setDataPosition(0);
            reply.setDataPosition(0);
        }

        private void convertAdaptParcel(int code, Parcel data, Parcel reply) {
            AdaptParcel adaptParcel = this.mAdpt;
            if (adaptParcel == null) {
                Log.e(PhaseCheckParse.TAG, "convertAdaptParcel2: mAdpt == null!");
                return;
            }
            adaptParcel.code = code;
            data.setDataPosition(0);
            reply.setDataPosition(0);
            data.logArray();
            byte[] bData = new byte[data.dataSize()];
            data.readByteArray(bData);
            for (int i = 0; i < data.dataSize(); i++) {
                this.mAdpt.data[i] = bData[i];
            }
            int i2 = reply.dataSize();
            byte[] bReply = new byte[i2];
            reply.readByteArray(bReply);
            for (int i3 = 0; i3 < reply.dataSize(); i3++) {
                this.mAdpt.data[data.dataSize() + i3] = bReply[i3];
            }
            this.mAdpt.dataSize = data.dataSize();
            this.mAdpt.replySize = reply.dataSize();
            Log.e(PhaseCheckParse.TAG, "convertAdaptParcel2: dataSize = " + data.dataSize() + ", replySize = " + reply.dataSize());
            data.setDataPosition(0);
            reply.setDataPosition(0);
        }

        void transact(int code, Parcel data, Parcel reply, int flags) {
            Log.e(PhaseCheckParse.TAG, "transact start....");
            convertAdaptParcel(code, data, reply);
            sendCmdAndRecResult(this.mAdpt);
            convertParcel(this.mAdpt, code, data, reply);
            Log.e(PhaseCheckParse.TAG, "transact end....");
        }
    }

    private class Parcel {
        private byte[] mData;
        private int mDataSize;
        private int mPos;

        private Parcel() {
            this.mData = new byte[PhaseCheckParse.BUF_SIZE];
            this.mPos = 0;
            this.mDataSize = 0;
        }

        void writeByteArrayInternal(byte[] b, int offset, int len) {
            if (len == 0) {
                return;
            }
            System.arraycopy(b, offset, this.mData, this.mPos, len);
            this.mPos += len;
            this.mDataSize += len;
        }

        void readByteArray(byte[] val) {
            System.arraycopy(this.mData, this.mPos, val, 0, val.length);
            this.mPos += val.length;
        }

        int dataSize() {
            return this.mDataSize;
        }

        void writeInt(int i) {
            Log.d(PhaseCheckParse.TAG, "writeInt i=" + i);
            byte[] bArr = this.mData;
            int i2 = this.mPos;
            bArr[i2 + 3] = (byte) ((i >> 24) & 255);
            bArr[i2 + 2] = (byte) ((i >> 16) & 255);
            bArr[i2 + 1] = (byte) ((i >> 8) & 255);
            bArr[i2] = (byte) (i & 255);
            this.mPos = i2 + 4;
            this.mDataSize += 4;
        }

        int readInt() {
            byte[] bArr = this.mData;
            int i = this.mPos;
            int b0 = bArr[i] & UByte.MAX_VALUE;
            int b1 = bArr[i + 1] & UByte.MAX_VALUE;
            int b2 = bArr[i + 2] & UByte.MAX_VALUE;
            int b3 = bArr[i + 3] & UByte.MAX_VALUE;
            this.mPos = i + 4;
            return (b1 << 8) | b0 | (b2 << 16) | (b3 << 24);
        }

        void setDataPosition(int i) {
            this.mPos = i;
        }

        String readString() {
            int nNum = readInt();
            byte[] b = new byte[nNum];
            Log.d(PhaseCheckParse.TAG, "readString num = " + nNum);
            readByteArray(b);
            return new String(b, StandardCharsets.UTF_8);
        }

        void recycle() {
            reset();
        }

        void reset() {
            this.mPos = 0;
            this.mDataSize = 0;
        }

        void logArray() {
            Log.e(PhaseCheckParse.TAG, "array length = " + this.mData.length);
            for (int i = 0; i < this.mData.length && i <= 19; i++) {
                Log.e(PhaseCheckParse.TAG, "Parcel LogArray : (" + i + ") = " + ((int) this.mData[i]));
            }
        }
    }
}
