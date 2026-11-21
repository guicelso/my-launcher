package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class WebSocketFrame {
    public static final int frameLengthOverhead = 6;
    private boolean closeFlag;
    private boolean fin;
    private byte opcode;
    private byte[] payload;

    public WebSocketFrame(byte b, boolean z, byte[] bArr) {
        this.closeFlag = false;
        this.opcode = b;
        this.fin = z;
        this.payload = bArr;
    }

    public WebSocketFrame(InputStream inputStream) throws IOException {
        int i = 2;
        this.closeFlag = false;
        setFinAndOpCode((byte) inputStream.read());
        if (this.opcode != 2) {
            if (this.opcode != 8) {
                throw new IOException("Invalid Frame: Opcode: " + ((int) this.opcode));
            }
            this.closeFlag = true;
            return;
        }
        byte b = (byte) inputStream.read();
        boolean z = (b & 128) != 0;
        int i2 = (byte) (b & 127);
        if (i2 == 127) {
            i = 8;
        } else if (i2 != 126) {
            i = 0;
        }
        i2 = i > 0 ? 0 : i2;
        while (true) {
            i--;
            if (i < 0) {
                break;
            } else {
                i2 |= (((byte) inputStream.read()) & 255) << (i * 8);
            }
        }
        byte[] bArr = null;
        if (z) {
            bArr = new byte[4];
            inputStream.read(bArr, 0, 4);
        }
        this.payload = new byte[i2];
        int i3 = i2;
        int i4 = 0;
        while (i4 != i2) {
            int i5 = inputStream.read(this.payload, i4, i3);
            i4 += i5;
            i3 -= i5;
        }
        if (z) {
            for (int i6 = 0; i6 < this.payload.length; i6++) {
                byte[] bArr2 = this.payload;
                bArr2[i6] = (byte) (bArr2[i6] ^ bArr[i6 % 4]);
            }
        }
    }

    public WebSocketFrame(byte[] bArr) {
        this.closeFlag = false;
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        setFinAndOpCode(byteBufferWrap.get());
        byte b = byteBufferWrap.get();
        boolean z = (b & 128) != 0;
        int i = (byte) (b & 127);
        int i2 = i != 127 ? i != 126 ? 0 : 2 : 8;
        while (true) {
            i2--;
            if (i2 <= 0) {
                break;
            } else {
                i |= (byteBufferWrap.get() & 255) << (i2 * 8);
            }
        }
        byte[] bArr2 = null;
        if (z) {
            bArr2 = new byte[4];
            byteBufferWrap.get(bArr2, 0, 4);
        }
        this.payload = new byte[i];
        byteBufferWrap.get(this.payload, 0, i);
        if (z) {
            for (int i3 = 0; i3 < this.payload.length; i3++) {
                byte[] bArr3 = this.payload;
                bArr3[i3] = (byte) (bArr3[i3] ^ bArr2[i3 % 4]);
            }
        }
    }

    public static void appendFinAndOpCode(ByteBuffer byteBuffer, byte b, boolean z) {
        byteBuffer.put((byte) ((z ? (byte) 128 : (byte) 0) | (b & 15)));
    }

    private static void appendLength(ByteBuffer byteBuffer, int i, boolean z) {
        if (i < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte b = (byte) (!z ? 0 : -128);
        if (i <= 65535) {
            if (i < 126) {
                byteBuffer.put((byte) (b | i));
                return;
            }
            byteBuffer.put((byte) (b | 126));
            byteBuffer.put((byte) (i >> 8));
            byteBuffer.put((byte) (i & 255));
            return;
        }
        byteBuffer.put((byte) (b | 127));
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) ((i >> 24) & 255));
        byteBuffer.put((byte) ((i >> 16) & 255));
        byteBuffer.put((byte) ((i >> 8) & 255));
        byteBuffer.put((byte) (i & 255));
    }

    public static void appendLengthAndMask(ByteBuffer byteBuffer, int i, byte[] bArr) {
        if (bArr == null) {
            appendLength(byteBuffer, i, false);
        } else {
            appendLength(byteBuffer, i, true);
            byteBuffer.put(bArr);
        }
    }

    public static byte[] generateMaskingKey() {
        SecureRandom secureRandom = new SecureRandom();
        return new byte[]{(byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255)};
    }

    private void setFinAndOpCode(byte b) {
        this.fin = (b & 128) != 0;
        this.opcode = (byte) (b & 15);
    }

    public byte[] encodeFrame() {
        int length = this.payload.length + 6;
        if (this.payload.length > 65535) {
            length += 8;
        } else if (this.payload.length >= 126) {
            length += 2;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        appendFinAndOpCode(byteBufferAllocate, this.opcode, this.fin);
        byte[] bArrGenerateMaskingKey = generateMaskingKey();
        appendLengthAndMask(byteBufferAllocate, this.payload.length, bArrGenerateMaskingKey);
        for (int i = 0; i < this.payload.length; i++) {
            byte[] bArr = this.payload;
            byte b = (byte) (bArr[i] ^ bArrGenerateMaskingKey[i % 4]);
            bArr[i] = b;
            byteBufferAllocate.put(b);
        }
        byteBufferAllocate.flip();
        return byteBufferAllocate.array();
    }

    public byte getOpcode() {
        return this.opcode;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public boolean isCloseFlag() {
        return this.closeFlag;
    }

    public boolean isFin() {
        return this.fin;
    }
}
