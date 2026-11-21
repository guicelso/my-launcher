package com.tw.core.utils;

/* JADX INFO: renamed from: com.tw.core.utils.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0052f {
    public final String A;
    public final long B;
    public final int z;

    public C0052f(String str, long j, int i) {
        this.A = str;
        this.B = j;
        this.z = i;
    }

    public String toString() {
        return "AppUsageStats{pkgName='" + this.A + "', usageTime=" + this.B + ", launchCount=" + this.z + '}';
    }
}
