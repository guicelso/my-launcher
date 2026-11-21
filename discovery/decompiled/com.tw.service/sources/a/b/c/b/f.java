package a.b.c.b;

import android.util.Log;
import java.io.Writer;

/* JADX INFO: loaded from: classes.dex */
public class f extends Writer {
    private StringBuilder mBuilder = new StringBuilder(128);
    private final String mTag;

    public f(String str) {
        this.mTag = str;
    }

    private void qb() {
        if (this.mBuilder.length() > 0) {
            Log.d(this.mTag, this.mBuilder.toString());
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        qb();
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        qb();
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == '\n') {
                qb();
            } else {
                this.mBuilder.append(c);
            }
        }
    }
}
