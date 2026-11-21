package a.b.d.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class j {

    public interface a<T> {
        int a(T t);

        boolean b(T t);
    }

    public static <T> T e(T[] tArr, int i, a<T> aVar) {
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        T t = null;
        int i3 = Integer.MAX_VALUE;
        for (T t2 : tArr) {
            int iAbs = (Math.abs(aVar.a(t2) - i2) * 2) + (aVar.b(t2) == z ? 0 : 1);
            if (t == null || i3 > iAbs) {
                t = t2;
                i3 = iAbs;
            }
        }
        return t;
    }

    public Typeface a(Context context, a.b.d.b.b.c cVar, Resources resources, int i) {
        a.b.d.b.b.d dVar = (a.b.d.b.b.d) e(cVar.f53a, i, new i(this));
        if (dVar == null) {
            return null;
        }
        return c.b(context, resources, dVar.f, dVar.f54a, i);
    }

    public Typeface b(Context context, CancellationSignal cancellationSignal, a.b.d.e.e.c[] cVarArr, int i) throws Throwable {
        InputStream inputStreamOpenInputStream;
        InputStream inputStream = null;
        if (cVarArr.length < 1) {
            return null;
        }
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(((a.b.d.e.e.c) e(cVarArr, i, new h(this))).f96a);
            try {
                Typeface typefaceC = c(context, inputStreamOpenInputStream);
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException unused) {
                    }
                }
                return typefaceC;
            } catch (IOException unused2) {
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                inputStream = inputStreamOpenInputStream;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused4) {
                    }
                }
                throw th;
            }
        } catch (IOException unused5) {
            inputStreamOpenInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public Typeface c(Context context, InputStream inputStream) {
        File fileQ = a.b.a.a.q(context);
        if (fileQ == null) {
            return null;
        }
        try {
            if (a.b.a.a.g(fileQ, inputStream)) {
                return Typeface.createFromFile(fileQ.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileQ.delete();
        }
    }

    public Typeface d(Context context, Resources resources, int i, String str, int i2) {
        File fileQ = a.b.a.a.q(context);
        if (fileQ == null) {
            return null;
        }
        try {
            if (a.b.a.a.f(fileQ, resources, i)) {
                return Typeface.createFromFile(fileQ.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileQ.delete();
        }
    }
}
