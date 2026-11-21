package com.google.zxing.common;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: ECIEncoderSet.java */
/* JADX INFO: loaded from: classes.dex */
public final class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static final /* synthetic */ boolean f541a = !c.class.desiredAssertionStatus();
    private static final List<CharsetEncoder> b = new ArrayList();
    private final CharsetEncoder[] c;
    private final int d;

    static {
        for (String str : new String[]{"IBM437", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4", "ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-8859-10", "ISO-8859-11", "ISO-8859-13", "ISO-8859-14", "ISO-8859-15", "ISO-8859-16", "windows-1250", "windows-1251", "windows-1252", "windows-1256", "Shift_JIS"}) {
            if (CharacterSetECI.getCharacterSetECIByName(str) != null) {
                try {
                    b.add(Charset.forName(str).newEncoder());
                } catch (UnsupportedCharsetException unused) {
                }
            }
        }
    }

    public c(String str, Charset charset, int i) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        arrayList.add(StandardCharsets.ISO_8859_1.newEncoder());
        boolean z2 = charset != null && charset.name().startsWith("UTF");
        for (int i2 = 0; i2 < str.length(); i2++) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                CharsetEncoder charsetEncoder = (CharsetEncoder) it.next();
                char cCharAt = str.charAt(i2);
                if (cCharAt == i || charsetEncoder.canEncode(cCharAt)) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                for (CharsetEncoder charsetEncoder2 : b) {
                    if (charsetEncoder2.canEncode(str.charAt(i2))) {
                        arrayList.add(charsetEncoder2);
                        z = true;
                        break;
                    }
                }
            }
            if (!z) {
                z2 = true;
            }
        }
        if (arrayList.size() == 1 && !z2) {
            this.c = new CharsetEncoder[]{(CharsetEncoder) arrayList.get(0)};
        } else {
            this.c = new CharsetEncoder[arrayList.size() + 2];
            Iterator it2 = arrayList.iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                this.c[i3] = (CharsetEncoder) it2.next();
                i3++;
            }
            this.c[i3] = StandardCharsets.UTF_8.newEncoder();
            this.c[i3 + 1] = StandardCharsets.UTF_16BE.newEncoder();
        }
        int i4 = -1;
        if (charset != null) {
            for (int i5 = 0; i5 < this.c.length; i5++) {
                if (this.c[i5] != null && charset.name().equals(this.c[i5].charset().name())) {
                    i4 = i5;
                    break;
                }
            }
        }
        this.d = i4;
        if (!f541a && !this.c[0].charset().equals(StandardCharsets.ISO_8859_1)) {
            throw new AssertionError();
        }
    }

    public int a() {
        return this.c.length;
    }

    public Charset a(int i) {
        if (f541a || i < a()) {
            return this.c[i].charset();
        }
        throw new AssertionError();
    }

    public int b(int i) {
        return CharacterSetECI.getCharacterSetECI(this.c[i].charset()).getValue();
    }

    public int b() {
        return this.d;
    }

    public boolean a(char c, int i) {
        if (!f541a && i >= a()) {
            throw new AssertionError();
        }
        return this.c[i].canEncode("" + c);
    }

    public byte[] a(String str, int i) {
        if (f541a || i < a()) {
            return str.getBytes(this.c[i].charset());
        }
        throw new AssertionError();
    }
}
