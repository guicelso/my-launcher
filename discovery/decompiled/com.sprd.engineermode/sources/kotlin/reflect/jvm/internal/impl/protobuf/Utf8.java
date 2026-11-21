package kotlin.reflect.jvm.internal.impl.protobuf;

/* JADX INFO: loaded from: classes4.dex */
final class Utf8 {
    public static boolean isValidUtf8(byte[] bytes) {
        return isValidUtf8(bytes, 0, bytes.length);
    }

    public static boolean isValidUtf8(byte[] bytes, int index, int limit) {
        return partialIsValidUtf8(bytes, index, limit) == 0;
    }

    public static int partialIsValidUtf8(int state, byte[] bytes, int index, int limit) {
        int index2;
        if (state == 0) {
            index2 = index;
        } else {
            if (index >= limit) {
                return state;
            }
            int byte1 = (byte) state;
            if (byte1 < -32) {
                if (byte1 >= -62) {
                    index2 = index + 1;
                    if (bytes[index] > -65) {
                    }
                }
                return -1;
            }
            if (byte1 < -16) {
                int byte2 = (byte) (~(state >> 8));
                if (byte2 == 0) {
                    int index3 = index + 1;
                    byte2 = bytes[index];
                    if (index3 < limit) {
                        index = index3;
                    } else {
                        return incompleteStateFor(byte1, byte2);
                    }
                }
                if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                    index2 = index + 1;
                    if (bytes[index] > -65) {
                    }
                }
                return -1;
            }
            int byte3 = (byte) (~(state >> 8));
            int byte4 = 0;
            if (byte3 == 0) {
                int index4 = index + 1;
                byte3 = bytes[index];
                if (index4 < limit) {
                    index = index4;
                } else {
                    return incompleteStateFor(byte1, byte3);
                }
            } else {
                byte4 = (byte) (state >> 16);
            }
            if (byte4 == 0) {
                int index5 = index + 1;
                byte4 = bytes[index];
                if (index5 < limit) {
                    index = index5;
                } else {
                    return incompleteStateFor(byte1, byte3, byte4);
                }
            }
            if (byte3 <= -65 && (((byte1 << 28) + (byte3 + 112)) >> 30) == 0 && byte4 <= -65) {
                int index6 = index + 1;
                if (bytes[index] <= -65) {
                    index2 = index6;
                }
            }
            return -1;
        }
        return partialIsValidUtf8(bytes, index2, limit);
    }

    public static int partialIsValidUtf8(byte[] bytes, int index, int limit) {
        while (index < limit && bytes[index] >= 0) {
            index++;
        }
        if (index >= limit) {
            return 0;
        }
        return partialIsValidUtf8NonAscii(bytes, index, limit);
    }

    private static int partialIsValidUtf8NonAscii(byte[] bytes, int index, int limit) {
        while (index < limit) {
            int index2 = index + 1;
            int byte1 = bytes[index];
            if (byte1 >= 0) {
                index = index2;
            } else {
                if (byte1 < -32) {
                    if (index2 >= limit) {
                        return byte1;
                    }
                    if (byte1 >= -62) {
                        index = index2 + 1;
                        if (bytes[index2] > -65) {
                        }
                    }
                    return -1;
                }
                if (byte1 < -16) {
                    if (index2 >= limit - 1) {
                        return incompleteStateFor(bytes, index2, limit);
                    }
                    int index3 = index2 + 1;
                    int byte2 = bytes[index2];
                    if (byte2 <= -65 && ((byte1 != -32 || byte2 >= -96) && (byte1 != -19 || byte2 < -96))) {
                        index = index3 + 1;
                        if (bytes[index3] > -65) {
                        }
                    }
                    return -1;
                }
                int index4 = limit - 2;
                if (index2 >= index4) {
                    return incompleteStateFor(bytes, index2, limit);
                }
                int index5 = index2 + 1;
                int byte3 = bytes[index2];
                if (byte3 <= -65 && (((byte1 << 28) + (byte3 + 112)) >> 30) == 0) {
                    int index6 = index5 + 1;
                    if (bytes[index5] <= -65) {
                        index = index6 + 1;
                        if (bytes[index6] > -65) {
                        }
                    }
                }
                return -1;
            }
        }
        return 0;
    }

    private static int incompleteStateFor(int byte1) {
        if (byte1 > -12) {
            return -1;
        }
        return byte1;
    }

    private static int incompleteStateFor(int byte1, int byte2) {
        if (byte1 > -12 || byte2 > -65) {
            return -1;
        }
        return (byte2 << 8) ^ byte1;
    }

    private static int incompleteStateFor(int byte1, int byte2, int byte3) {
        if (byte1 > -12 || byte2 > -65 || byte3 > -65) {
            return -1;
        }
        return ((byte2 << 8) ^ byte1) ^ (byte3 << 16);
    }

    private static int incompleteStateFor(byte[] bytes, int index, int limit) {
        int byte1 = bytes[index - 1];
        int i = limit - index;
        if (i == 0) {
            return incompleteStateFor(byte1);
        }
        if (i == 1) {
            return incompleteStateFor(byte1, bytes[index]);
        }
        if (i == 2) {
            return incompleteStateFor(byte1, bytes[index], bytes[index + 1]);
        }
        throw new AssertionError();
    }
}
