package a.b.d.e;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
class e implements Comparator<byte[]> {
    @Override // java.util.Comparator
    public int compare(byte[] bArr, byte[] bArr2) {
        int length;
        int length2;
        byte[] bArr3 = bArr;
        byte[] bArr4 = bArr2;
        if (bArr3.length == bArr4.length) {
            for (int i = 0; i < bArr3.length; i++) {
                if (bArr3[i] != bArr4[i]) {
                    length = bArr3[i];
                    length2 = bArr4[i];
                }
            }
            return 0;
        }
        length = bArr3.length;
        length2 = bArr4.length;
        return length - length2;
    }
}
