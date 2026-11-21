package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

/* JADX INFO: compiled from: StringUtils.java */
/* JADX INFO: loaded from: classes.dex */
public final class d {
    public static final Charset b;
    private static final Charset d;
    private static final boolean e;
    private static final Charset c = Charset.defaultCharset();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Charset f542a = Charset.forName("SJIS");

    static {
        Charset charsetForName;
        try {
            charsetForName = Charset.forName("GB2312");
        } catch (UnsupportedCharsetException unused) {
            charsetForName = null;
        }
        b = charsetForName;
        d = Charset.forName("EUC_JP");
        e = f542a.equals(c) || d.equals(c);
    }
}
