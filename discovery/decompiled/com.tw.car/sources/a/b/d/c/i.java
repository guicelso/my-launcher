package a.b.d.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: Access modifiers changed from: private */
    interface a<T> {
        boolean a(T t);

        int b(T t);
    }

    public static <T> T a(T[] tArr, int i, a<T> aVar) {
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        T t = null;
        int i3 = Integer.MAX_VALUE;
        for (T t2 : tArr) {
            int iAbs = (Math.abs(aVar.b(t2) - i2) * 2) + (aVar.a(t2) == z ? 0 : 1);
            if (t == null || i3 > iAbs) {
                t = t2;
                i3 = iAbs;
            }
        }
        return t;
    }

    public a.b.d.e.f.b a(a.b.d.e.f.b[] bVarArr, int i) {
        return (a.b.d.e.f.b) a(bVarArr, i, new h(this));
    }

    public Typeface a(Context context, a.b.d.b.a.c cVar, Resources resources, int i) {
        a.b.d.b.a.d[] dVarArr = cVar.f56a;
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        a.b.d.b.a.d dVar = null;
        int i3 = Integer.MAX_VALUE;
        for (a.b.d.b.a.d dVar2 : dVarArr) {
            int iAbs = (Math.abs(dVar2.f58b - i2) * 2) + (dVar2.f59c == z ? 0 : 1);
            if (dVar == null || i3 > iAbs) {
                dVar = dVar2;
                i3 = iAbs;
            }
        }
        if (dVar == null) {
            return null;
        }
        return c.a(context, resources, dVar.f, dVar.f57a, i);
    }

    public Typeface a(Context context, Resources resources, int i, String str, int i2) {
        File fileA = a.b.b.a.e.a(context);
        if (fileA == null) {
            return null;
        }
        try {
            if (a.b.b.a.e.a(fileA, resources, i)) {
                return Typeface.createFromFile(fileA.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileA.delete();
        }
    }

    public Typeface a(Context context, InputStream inputStream) {
        File fileA = a.b.b.a.e.a(context);
        if (fileA == null) {
            return null;
        }
        try {
            if (a.b.b.a.e.a(fileA, inputStream)) {
                return Typeface.createFromFile(fileA.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            fileA.delete();
        }
    }

    public Typeface a(Context context, CancellationSignal cancellationSignal, a.b.d.e.f.b[] bVarArr, int i) throws Throwable {
        InputStream inputStreamOpenInputStream;
        InputStream inputStream = null;
        if (bVarArr.length < 1) {
            return null;
        }
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(a(bVarArr, i).f99a);
            try {
                Typeface typefaceA = a(context, inputStreamOpenInputStream);
                if (inputStreamOpenInputStream != null) {
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException unused) {
                    }
                }
                return typefaceA;
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
}
