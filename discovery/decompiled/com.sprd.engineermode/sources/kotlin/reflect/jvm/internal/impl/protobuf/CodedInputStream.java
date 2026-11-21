package kotlin.reflect.jvm.internal.impl.protobuf;

import com.android.fmradio.FmConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes4.dex */
public final class CodedInputStream {
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit;
    private boolean enableAliasing;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit;
    private RefillCallback refillCallback;
    private int sizeLimit;
    private int totalBytesRetired;

    private interface RefillCallback {
        void onRefill();
    }

    public static CodedInputStream newInstance(InputStream input) {
        return new CodedInputStream(input);
    }

    static CodedInputStream newInstance(LiteralByteString byteString) {
        CodedInputStream result = new CodedInputStream(byteString);
        try {
            result.pushLimit(byteString.size());
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        return this.lastTag;
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        int tagWireType = WireFormat.getTagWireType(tag);
        if (tagWireType == 0) {
            long value = readInt64();
            output.writeRawVarint32(tag);
            output.writeUInt64NoTag(value);
            return true;
        }
        if (tagWireType == 1) {
            long value2 = readRawLittleEndian64();
            output.writeRawVarint32(tag);
            output.writeFixed64NoTag(value2);
            return true;
        }
        if (tagWireType == 2) {
            ByteString value3 = readBytes();
            output.writeRawVarint32(tag);
            output.writeBytesNoTag(value3);
            return true;
        }
        if (tagWireType == 3) {
            output.writeRawVarint32(tag);
            skipMessage(output);
            int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
            checkLastTagWas(endtag);
            output.writeRawVarint32(endtag);
            return true;
        }
        if (tagWireType == 4) {
            return false;
        }
        if (tagWireType == 5) {
            int value4 = readRawLittleEndian32();
            output.writeRawVarint32(tag);
            output.writeFixed32NoTag(value4);
            return true;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag, output));
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint64() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        int i = this.bufferSize;
        int i2 = this.bufferPos;
        if (size <= i - i2 && size > 0) {
            String result = new String(this.buffer, i2, size, "UTF-8");
            this.bufferPos += size;
            return result;
        }
        if (size == 0) {
            return "";
        }
        return new String(readRawBytesSlowPath(size), "UTF-8");
    }

    public String readStringRequireUtf8() throws IOException {
        byte[] bytes;
        int size = readRawVarint32();
        int pos = this.bufferPos;
        if (size <= this.bufferSize - pos && size > 0) {
            bytes = this.buffer;
            this.bufferPos = pos + size;
        } else {
            if (size == 0) {
                return "";
            }
            bytes = readRawBytesSlowPath(size);
            pos = 0;
        }
        if (!Utf8.isValidUtf8(bytes, pos, pos + size)) {
            throw InvalidProtocolBufferException.invalidUtf8();
        }
        return new String(bytes, pos, size, "UTF-8");
    }

    public void readGroup(int fieldNumber, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int i = this.recursionDepth;
        if (i >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth = i + 1;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
    }

    public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        T result = parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
        return result;
    }

    public ByteString readBytes() throws IOException {
        int size = readRawVarint32();
        int i = this.bufferSize;
        int i2 = this.bufferPos;
        if (size <= i - i2 && size > 0) {
            ByteString result = (this.bufferIsImmutable && this.enableAliasing) ? new BoundedByteString(this.buffer, i2, size) : ByteString.copyFrom(this.buffer, this.bufferPos, size);
            this.bufferPos += size;
            return result;
        }
        if (size == 0) {
            return ByteString.EMPTY;
        }
        return new LiteralByteString(readRawBytesSlowPath(size));
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0090 A[PHI: r4
      0x0090: PHI (r4v8 'pos' int) = (r4v7 'pos' int), (r4v10 'pos' int), (r4v12 'pos' int) binds: [B:24:0x006f, B:28:0x007b, B:32:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0087, code lost:
    
        if (r2[r1] < 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int readRawVarint32() throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r8.bufferPos
            int r1 = r8.bufferSize
            if (r1 != r0) goto L8
            goto L8a
        L8:
            byte[] r2 = r8.buffer
            int r3 = r0 + 1
            r0 = r2[r0]
            r4 = r0
            if (r0 < 0) goto L14
            r8.bufferPos = r3
            return r4
        L14:
            int r1 = r1 - r3
            r0 = 9
            if (r1 >= r0) goto L1b
            goto L8a
        L1b:
            int r0 = r3 + 1
            r1 = r2[r3]
            int r1 = r1 << 7
            r1 = r1 ^ r4
            r3 = r1
            long r4 = (long) r1
            r6 = 0
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L32
            long r4 = (long) r3
            r6 = -128(0xffffffffffffff80, double:NaN)
            long r4 = r4 ^ r6
            int r1 = (int) r4
            r3 = r1
            r1 = r0
            goto L91
        L32:
            int r1 = r0 + 1
            r0 = r2[r0]
            int r0 = r0 << 14
            r0 = r0 ^ r3
            r3 = r0
            long r4 = (long) r0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 < 0) goto L46
            long r4 = (long) r3
            r6 = 16256(0x3f80, double:8.0315E-320)
            long r4 = r4 ^ r6
            int r0 = (int) r4
            r3 = r0
            goto L91
        L46:
            int r0 = r1 + 1
            r1 = r2[r1]
            int r1 = r1 << 21
            r1 = r1 ^ r3
            r3 = r1
            long r4 = (long) r1
            int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r1 >= 0) goto L5c
            long r4 = (long) r3
            r6 = -2080896(0xffffffffffe03f80, double:NaN)
            long r4 = r4 ^ r6
            int r1 = (int) r4
            r3 = r1
            r1 = r0
            goto L91
        L5c:
            int r1 = r0 + 1
            r0 = r2[r0]
            int r4 = r0 << 28
            r3 = r3 ^ r4
            long r4 = (long) r3
            r6 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r4 = r4 ^ r6
            int r3 = (int) r4
            if (r0 >= 0) goto L91
            int r4 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L90
            int r1 = r4 + 1
            r4 = r2[r4]
            if (r4 >= 0) goto L91
            int r4 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L90
            int r1 = r4 + 1
            r4 = r2[r4]
            if (r4 >= 0) goto L91
            int r4 = r1 + 1
            r1 = r2[r1]
            if (r1 >= 0) goto L90
        L8a:
            long r0 = r8.readRawVarint64SlowPath()
            int r0 = (int) r0
            return r0
        L90:
            r1 = r4
        L91:
            r8.bufferPos = r1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream.readRawVarint32():int");
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        if ((firstByte & 128) == 0) {
            return firstByte;
        }
        int result = firstByte & 127;
        int offset = 7;
        while (offset < 32) {
            int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result |= (b & 127) << offset;
            if ((b & 128) != 0) {
                offset += 7;
            } else {
                return result;
            }
        }
        while (offset < 64) {
            int b2 = input.read();
            if (b2 == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if ((b2 & 128) != 0) {
                offset += 7;
            } else {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00c4, code lost:
    
        if (r2[r1] < 0) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long readRawVarint64() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream.readRawVarint64():long");
    }

    long readRawVarint64SlowPath() throws IOException {
        long result = 0;
        for (int shift = 0; shift < 64; shift += 7) {
            byte b = readRawByte();
            result |= ((long) (b & ByteCompanionObject.MAX_VALUE)) << shift;
            if ((b & ByteCompanionObject.MIN_VALUE) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 4) {
            refillBuffer(4);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 4;
        return (buffer[pos] & UByte.MAX_VALUE) | ((buffer[pos + 1] & UByte.MAX_VALUE) << 8) | ((buffer[pos + 2] & UByte.MAX_VALUE) << 16) | ((buffer[pos + 3] & UByte.MAX_VALUE) << 24);
    }

    public long readRawLittleEndian64() throws IOException {
        int pos = this.bufferPos;
        if (this.bufferSize - pos < 8) {
            refillBuffer(8);
            pos = this.bufferPos;
        }
        byte[] buffer = this.buffer;
        this.bufferPos = pos + 8;
        return (((long) buffer[pos]) & 255) | ((((long) buffer[pos + 1]) & 255) << 8) | ((((long) buffer[pos + 2]) & 255) << 16) | ((((long) buffer[pos + 3]) & 255) << 24) | ((((long) buffer[pos + 4]) & 255) << 32) | ((((long) buffer[pos + 5]) & 255) << 40) | ((((long) buffer[pos + 6]) & 255) << 48) | ((((long) buffer[pos + 7]) & 255) << 56);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private CodedInputStream(InputStream input) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 64;
        this.sizeLimit = FmConstants.DEVICE_OUT_FM_HEADSET;
        this.refillCallback = null;
        this.buffer = new byte[4096];
        this.bufferSize = 0;
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(LiteralByteString byteString) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = 64;
        this.sizeLimit = FmConstants.DEVICE_OUT_FM_HEADSET;
        this.refillCallback = null;
        this.buffer = byteString.bytes;
        this.bufferPos = byteString.getOffsetIntoBytes();
        this.bufferSize = this.bufferPos + byteString.size();
        this.totalBytesRetired = -this.bufferPos;
        this.input = null;
        this.bufferIsImmutable = true;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        int byteLimit2 = byteLimit + this.totalBytesRetired + this.bufferPos;
        int oldLimit = this.currentLimit;
        if (byteLimit2 > oldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        this.currentLimit = byteLimit2;
        recomputeBufferSizeAfterLimit();
        return oldLimit;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int i = this.totalBytesRetired;
        int i2 = this.bufferSize;
        int bufferEnd = i + i2;
        int i3 = this.currentLimit;
        if (bufferEnd > i3) {
            this.bufferSizeAfterLimit = bufferEnd - i3;
            this.bufferSize = i2 - this.bufferSizeAfterLimit;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        int i = this.currentLimit;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        int currentAbsolutePosition = this.totalBytesRetired + this.bufferPos;
        return i - currentAbsolutePosition;
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !tryRefillBuffer(1);
    }

    private void ensureAvailable(int n) throws IOException {
        if (this.bufferSize - this.bufferPos < n) {
            refillBuffer(n);
        }
    }

    private void refillBuffer(int n) throws IOException {
        if (!tryRefillBuffer(n)) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    private boolean tryRefillBuffer(int n) throws IOException {
        int i = this.bufferPos;
        if (i + n <= this.bufferSize) {
            StringBuilder sb = new StringBuilder(77);
            sb.append("refillBuffer() called when ");
            sb.append(n);
            sb.append(" bytes were already available in buffer");
            throw new IllegalStateException(sb.toString());
        }
        if (this.totalBytesRetired + i + n > this.currentLimit) {
            return false;
        }
        RefillCallback refillCallback = this.refillCallback;
        if (refillCallback != null) {
            refillCallback.onRefill();
        }
        if (this.input != null) {
            int pos = this.bufferPos;
            if (pos > 0) {
                int i2 = this.bufferSize;
                if (i2 > pos) {
                    byte[] bArr = this.buffer;
                    System.arraycopy(bArr, pos, bArr, 0, i2 - pos);
                }
                this.totalBytesRetired += pos;
                this.bufferSize -= pos;
                this.bufferPos = 0;
            }
            InputStream inputStream = this.input;
            byte[] bArr2 = this.buffer;
            int i3 = this.bufferSize;
            int bytesRead = inputStream.read(bArr2, i3, bArr2.length - i3);
            if (bytesRead == 0 || bytesRead < -1 || bytesRead > this.buffer.length) {
                StringBuilder sb2 = new StringBuilder(102);
                sb2.append("InputStream#read(byte[]) returned invalid result: ");
                sb2.append(bytesRead);
                sb2.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb2.toString());
            }
            if (bytesRead > 0) {
                this.bufferSize += bytesRead;
                if ((this.totalBytesRetired + n) - this.sizeLimit > 0) {
                    throw InvalidProtocolBufferException.sizeLimitExceeded();
                }
                recomputeBufferSizeAfterLimit();
                if (this.bufferSize >= n) {
                    return true;
                }
                return tryRefillBuffer(n);
            }
        }
        return false;
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(1);
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    private byte[] readRawBytesSlowPath(int size) throws IOException {
        if (size <= 0) {
            if (size == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            throw InvalidProtocolBufferException.negativeSize();
        }
        int sizeLeft = this.totalBytesRetired;
        int i = this.bufferPos;
        int i2 = sizeLeft + i + size;
        int i3 = this.currentLimit;
        if (i2 > i3) {
            skipRawBytes((i3 - sizeLeft) - i);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (size < 4096) {
            byte[] bytes = new byte[size];
            int pos = this.bufferSize - i;
            System.arraycopy(this.buffer, i, bytes, 0, pos);
            this.bufferPos = this.bufferSize;
            ensureAvailable(size - pos);
            System.arraycopy(this.buffer, 0, bytes, pos, size - pos);
            this.bufferPos = size - pos;
            return bytes;
        }
        int originalBufferPos = this.bufferPos;
        int originalBufferSize = this.bufferSize;
        this.totalBytesRetired = sizeLeft + this.bufferSize;
        this.bufferPos = 0;
        this.bufferSize = 0;
        int sizeLeft2 = size - (originalBufferSize - originalBufferPos);
        List<byte[]> chunks = new ArrayList<>();
        while (sizeLeft2 > 0) {
            byte[] chunk = new byte[Math.min(sizeLeft2, 4096)];
            int pos2 = 0;
            while (pos2 < chunk.length) {
                InputStream inputStream = this.input;
                int n = inputStream == null ? -1 : inputStream.read(chunk, pos2, chunk.length - pos2);
                if (n == -1) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                }
                this.totalBytesRetired += n;
                pos2 += n;
            }
            sizeLeft2 -= chunk.length;
            chunks.add(chunk);
        }
        byte[] bytes2 = new byte[size];
        int pos3 = originalBufferSize - originalBufferPos;
        System.arraycopy(this.buffer, originalBufferPos, bytes2, 0, pos3);
        for (byte[] chunk2 : chunks) {
            System.arraycopy(chunk2, 0, bytes2, pos3, chunk2.length);
            pos3 += chunk2.length;
        }
        return bytes2;
    }

    public void skipRawBytes(int size) throws IOException {
        int i = this.bufferSize;
        int i2 = this.bufferPos;
        if (size <= i - i2 && size >= 0) {
            this.bufferPos = i2 + size;
        } else {
            skipRawBytesSlowPath(size);
        }
    }

    private void skipRawBytesSlowPath(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        int i = this.totalBytesRetired;
        int i2 = this.bufferPos;
        int i3 = i + i2 + size;
        int i4 = this.currentLimit;
        if (i3 > i4) {
            skipRawBytes((i4 - i) - i2);
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = this.bufferSize;
        int pos = i5 - i2;
        this.bufferPos = i5;
        refillBuffer(1);
        while (true) {
            int i6 = size - pos;
            int i7 = this.bufferSize;
            if (i6 > i7) {
                pos += i7;
                this.bufferPos = i7;
                refillBuffer(1);
            } else {
                this.bufferPos = size - pos;
                return;
            }
        }
    }
}
