package com.google.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: ReedSolomonEncoder.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final a f545a;
    private final List<b> b = new ArrayList();

    public c(a aVar) {
        this.f545a = aVar;
        this.b.add(new b(aVar, new int[]{1}));
    }

    private b a(int i) {
        if (i >= this.b.size()) {
            b bVarB = this.b.get(this.b.size() - 1);
            for (int size = this.b.size(); size <= i; size++) {
                bVarB = bVarB.b(new b(this.f545a, new int[]{1, this.f545a.a((size - 1) + this.f545a.b())}));
                this.b.add(bVarB);
            }
        }
        return this.b.get(i);
    }

    public void a(int[] iArr, int i) {
        if (i == 0) {
            throw new IllegalArgumentException("No error correction bytes");
        }
        int length = iArr.length - i;
        if (length <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
        }
        b bVarA = a(i);
        int[] iArr2 = new int[length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        int[] iArrA = new b(this.f545a, iArr2).a(i, 1).c(bVarA)[1].a();
        int length2 = i - iArrA.length;
        for (int i2 = 0; i2 < length2; i2++) {
            iArr[length + i2] = 0;
        }
        System.arraycopy(iArrA, 0, iArr, length + length2, iArrA.length);
    }
}
