package com.tw.core.a;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private String cr;
    private int cs;
    private String ct;
    private String name;

    public g(String str, int i, String str2, String str3) {
        this.cr = str;
        this.cs = i;
        this.ct = str2;
        this.name = str3;
    }

    public int fA() {
        return this.cs;
    }

    public String fz() {
        return this.cr;
    }

    public String toString() {
        return "BaseAppInfo{pkgName='" + this.cr + "', verCode=" + this.cs + ", verName='" + this.ct + "', name='" + this.name + "'}";
    }
}
