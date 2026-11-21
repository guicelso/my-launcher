package a.b.d.c;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final j f69a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final a.b.d.g.d<String, Typeface> f70b;

    /* JADX WARN: Code duplicated, block: B:18:0x0032  */
    /* JADX WARN: Code duplicated, block: B:20:0x0036  */
    /* JADX WARN: Code duplicated, block: B:21:0x003c  */
    static {
        j jVar;
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            jVar = new g();
        } else if (i >= 26) {
            jVar = new f();
        } else if (i >= 24) {
            Method method = e.f73c;
            if (method == null) {
                Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
            }
            if (method != null) {
                jVar = new e();
            } else if (i >= 21) {
                jVar = new d();
            } else {
                jVar = new j();
            }
        } else if (i >= 21) {
            jVar = new d();
        } else {
            jVar = new j();
        }
        f69a = jVar;
        f70b = new a.b.d.g.d<>(16);
    }

    public static Typeface a(Context context, a.b.d.b.b.b bVar, Resources resources, int i, int i2, a.b.d.b.b.g gVar, Handler handler, boolean z) {
        Typeface typefaceA;
        if (bVar instanceof a.b.d.b.b.e) {
            a.b.d.b.b.e eVar = (a.b.d.b.b.e) bVar;
            boolean z2 = !z ? gVar != null : eVar.f59c != 0;
            int i3 = z ? eVar.f58b : -1;
            a.b.d.e.a aVar = eVar.f57a;
            a.b.d.g.d<String, Typeface> dVar = a.b.d.e.e.f91a;
            String str = aVar.e + "-" + i2;
            typefaceA = a.b.d.e.e.f91a.a(str);
            if (typefaceA != null) {
                if (gVar != null) {
                    gVar.c(typefaceA);
                }
            } else if (z2 && i3 == -1) {
                a.b.d.e.e.d dVarB = a.b.d.e.e.b(context, aVar, i2);
                if (gVar != null) {
                    int i4 = dVarB.f100b;
                    if (i4 == 0) {
                        gVar.b(dVarB.f99a, handler);
                    } else {
                        gVar.a(i4, handler);
                    }
                }
                typefaceA = dVarB.f99a;
            } else {
                a.b.d.e.b bVar2 = new a.b.d.e.b(context, aVar, i2, str);
                typefaceA = null;
                if (z2) {
                    try {
                        typefaceA = ((a.b.d.e.e.d) a.b.d.e.e.f92b.b(bVar2, i3)).f99a;
                    } catch (InterruptedException unused) {
                    }
                } else {
                    a.b.d.e.c cVar = gVar == null ? null : new a.b.d.e.c(gVar, handler);
                    synchronized (a.b.d.e.e.f93c) {
                        a.b.d.g.f<String, ArrayList<a.b.d.e.f.c<a.b.d.e.e.d>>> fVar = a.b.d.e.e.d;
                        if (!(fVar.d(str) >= 0)) {
                            if (cVar != null) {
                                ArrayList<a.b.d.e.f.c<a.b.d.e.e.d>> arrayList = new ArrayList<>();
                                arrayList.add(cVar);
                                fVar.put(str, arrayList);
                            }
                            a.b.d.e.f fVar2 = a.b.d.e.e.f92b;
                            a.b.d.e.d dVar2 = new a.b.d.e.d(str);
                            Objects.requireNonNull(fVar2);
                            fVar2.a(new a.b.d.e.g(fVar2, bVar2, new Handler(), dVar2));
                        } else if (cVar != null) {
                            fVar.get(str).add(cVar);
                        }
                    }
                }
            }
        } else {
            typefaceA = f69a.a(context, (a.b.d.b.b.c) bVar, resources, i2);
            if (gVar != null) {
                if (typefaceA != null) {
                    gVar.b(typefaceA, handler);
                } else {
                    gVar.a(-3, handler);
                }
            }
        }
        if (typefaceA != null) {
            f70b.b(c(resources, i, i2), typefaceA);
        }
        return typefaceA;
    }

    public static Typeface b(Context context, Resources resources, int i, String str, int i2) {
        Typeface typefaceD = f69a.d(context, resources, i, str, i2);
        if (typefaceD != null) {
            f70b.b(c(resources, i, i2), typefaceD);
        }
        return typefaceD;
    }

    public static String c(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2;
    }
}
