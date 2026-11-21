package com.dofun.carsetting.f;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: compiled from: FastXmlSerializer.java */
/* JADX INFO: loaded from: classes.dex */
public class d implements XmlSerializer {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String[] f220a = {"&#0;", "&#1;", "&#2;", "&#3;", "&#4;", "&#5;", "&#6;", "&#7;", "&#8;", "&#9;", "&#10;", "&#11;", "&#12;", "&#13;", "&#14;", "&#15;", "&#16;", "&#17;", "&#18;", "&#19;", "&#20;", "&#21;", "&#22;", "&#23;", "&#24;", "&#25;", "&#26;", "&#27;", "&#28;", "&#29;", "&#30;", "&#31;", null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};
    private static String b = "                                                              ";
    private final int c;
    private final char[] d;
    private int e;
    private Writer f;
    private OutputStream g;
    private CharsetEncoder h;
    private ByteBuffer i;
    private boolean j;
    private boolean k;
    private int l;
    private boolean m;

    public d() {
        this(32768);
    }

    public d(int i) {
        this.j = false;
        this.l = 0;
        this.m = true;
        this.c = i <= 0 ? 32768 : i;
        this.d = new char[this.c];
        this.i = ByteBuffer.allocate(this.c);
    }

    private void a(char c) throws IOException {
        int i = this.e;
        if (i >= this.c - 1) {
            flush();
            i = this.e;
        }
        this.d[i] = c;
        this.e = i + 1;
    }

    private void a(String str, int i, int i2) throws IOException {
        if (i2 > this.c) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = this.c + i;
                a(str, i, i4 < i3 ? this.c : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.e;
        if (i5 + i2 > this.c) {
            flush();
            i5 = this.e;
        }
        str.getChars(i, i + i2, this.d, i5);
        this.e = i5 + i2;
    }

    private void a(char[] cArr, int i, int i2) throws IOException {
        if (i2 > this.c) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = this.c + i;
                a(cArr, i, i4 < i3 ? this.c : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.e;
        if (i5 + i2 > this.c) {
            flush();
            i5 = this.e;
        }
        System.arraycopy(cArr, i, this.d, i5, i2);
        this.e = i5 + i2;
    }

    private void a(String str) throws IOException {
        a(str, 0, str.length());
    }

    private void a(int i) throws IOException {
        int length = i * 4;
        if (length > b.length()) {
            length = b.length();
        }
        a(b, 0, length);
    }

    private void b(String str) throws IOException {
        String str2;
        int length = str.length();
        char length2 = (char) f220a.length;
        String[] strArr = f220a;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt < length2 && (str2 = strArr[cCharAt]) != null) {
                if (i2 < i) {
                    a(str, i2, i - i2);
                }
                i2 = i + 1;
                a(str2);
            }
            i++;
        }
        if (i2 < i) {
            a(str, i2, i - i2);
        }
    }

    private void b(char[] cArr, int i, int i2) throws IOException {
        String str;
        char length = (char) f220a.length;
        String[] strArr = f220a;
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            char c = cArr[i];
            if (c < length && (str = strArr[c]) != null) {
                if (i4 < i) {
                    a(cArr, i4, i - i4);
                }
                i4 = i + 1;
                a(str);
            }
            i++;
        }
        if (i4 < i) {
            a(cArr, i4, i - i4);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        a(' ');
        if (str != null) {
            a(str);
            a(':');
        }
        a(str2);
        a("=\"");
        b(str3);
        a('\"');
        this.m = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException {
        this.l--;
        if (this.k) {
            a(" />\n");
        } else {
            if (this.j && this.m) {
                a(this.l);
            }
            a("</");
            if (str != null) {
                a(str);
                a(':');
            }
            a(str2);
            a(">\n");
        }
        this.m = true;
        this.k = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) {
        throw new UnsupportedOperationException();
    }

    private void a() throws IOException {
        int iPosition = this.i.position();
        if (iPosition > 0) {
            this.i.flip();
            this.g.write(this.i.array(), 0, iPosition);
            this.i.clear();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        if (this.e > 0) {
            if (this.g != null) {
                CharBuffer charBufferWrap = CharBuffer.wrap(this.d, 0, this.e);
                CoderResult coderResultEncode = this.h.encode(charBufferWrap, this.i, true);
                while (!coderResultEncode.isError()) {
                    if (coderResultEncode.isOverflow()) {
                        a();
                        coderResultEncode = this.h.encode(charBufferWrap, this.i, true);
                    } else {
                        a();
                        this.g.flush();
                    }
                }
                throw new IOException(coderResultEncode.toString());
            }
            this.f.write(this.d, 0, this.e);
            this.f.flush();
            this.e = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) {
        if (str.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            this.j = true;
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        try {
            this.h = Charset.forName(str).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.g = outputStream;
        } catch (IllegalCharsetNameException e) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e));
        } catch (UnsupportedCharsetException e2) {
            throw ((UnsupportedEncodingException) new UnsupportedEncodingException(str).initCause(e2));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) {
        this.f = writer;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='");
        sb.append(bool.booleanValue() ? "yes" : "no");
        sb.append("' ?>\n");
        a(sb.toString());
        this.m = true;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException {
        if (this.k) {
            a(">\n");
        }
        if (this.j) {
            a(this.l);
        }
        this.l++;
        a('<');
        if (str != null) {
            a(str);
            a(':');
        }
        a(str2);
        this.k = true;
        this.m = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IOException {
        if (this.k) {
            a(">");
            this.k = false;
        }
        b(cArr, i, i2);
        if (this.j) {
            this.m = cArr[(i + i2) - 1] == '\n';
        }
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException {
        boolean z = false;
        if (this.k) {
            a(">");
            this.k = false;
        }
        b(str);
        if (this.j) {
            if (str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
                z = true;
            }
            this.m = z;
        }
        return this;
    }
}
