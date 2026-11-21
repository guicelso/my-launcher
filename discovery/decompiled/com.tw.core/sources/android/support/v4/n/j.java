package android.support.v4.n;

/* JADX INFO: loaded from: classes.dex */
class j {
    static final int[] oA = new int[0];
    static final long[] oB = new long[0];
    static final Object[] oC = new Object[0];

    j() {
    }

    static int sv(int[] iArr, int i, int i2) {
        int i3 = 0;
        int i4 = i - 1;
        while (i3 <= i4) {
            int i5 = (i3 + i4) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i2) {
                i3 = i5 + 1;
            } else {
                if (i6 <= i2) {
                    return i5;
                }
                i4 = i5 - 1;
            }
        }
        return ~i3;
    }

    public static boolean sw(Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj != null) {
            return obj.equals(obj2);
        }
        return false;
    }
}
