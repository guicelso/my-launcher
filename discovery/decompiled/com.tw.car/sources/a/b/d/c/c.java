package a.b.d.c;

import a.b.d.b.a.j;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final i f77a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final a.b.d.g.e<String, Typeface> f78b;

    /* JADX WARN: Code duplicated, block: B:19:0x0034  */
    /* JADX WARN: Code duplicated, block: B:21:0x003a  */
    /* JADX WARN: Code duplicated, block: B:22:0x0040  */
    static {
        i iVar;
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            iVar = new g();
        } else if (i >= 26) {
            iVar = new f();
        } else if (i >= 24) {
            if (e.f81c == null) {
                Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
            }
            if (e.f81c != null) {
                iVar = new e();
            } else if (Build.VERSION.SDK_INT >= 21) {
                iVar = new d();
            } else {
                iVar = new i();
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            iVar = new d();
        } else {
            iVar = new i();
        }
        f77a = iVar;
        f78b = new a.b.d.g.e<>(16);
    }

    public static Typeface a(Context context, a.b.d.b.a.b bVar, Resources resources, int i, int i2, j jVar, Handler handler, boolean z) {
        Typeface typefaceA;
        if (bVar instanceof a.b.d.b.a.e) {
            a.b.d.b.a.e eVar = (a.b.d.b.a.e) bVar;
            boolean z2 = false;
            if (!z ? jVar == null : eVar.f62c == 0) {
                z2 = true;
            }
            typefaceA = a.b.d.e.f.a(context, eVar.f60a, jVar, handler, z2, z ? eVar.f61b : -1, i2);
        } else {
            typefaceA = f77a.a(context, (a.b.d.b.a.c) bVar, resources, i2);
            if (jVar != null) {
                if (typefaceA != null) {
                    jVar.a(typefaceA, handler);
                } else {
                    jVar.a(-3, handler);
                }
            }
        }
        if (typefaceA != null) {
            f78b.a(a(resources, i, i2), typefaceA);
        }
        return typefaceA;
    }

    public static Typeface a(Context context, Resources resources, int i, String str, int i2) {
        Typeface typefaceA = f77a.a(context, resources, i, str, i2);
        if (typefaceA != null) {
            f78b.a(a(resources, i, i2), typefaceA);
        }
        return typefaceA;
    }

    public static String a(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2;
    }
}
