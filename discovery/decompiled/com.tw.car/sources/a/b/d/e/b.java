package a.b.d.e;

import android.content.Context;
import android.graphics.Typeface;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
class b implements Callable<f.c> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f88a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ a f89b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f90c;
    public final /* synthetic */ String d;

    public b(Context context, a aVar, int i, String str) {
        this.f88a = context;
        this.f89b = aVar;
        this.f90c = i;
        this.d = str;
    }

    @Override // java.util.concurrent.Callable
    public f.c call() throws Throwable {
        f.c cVarA = f.a(this.f88a, this.f89b, this.f90c);
        Typeface typeface = cVarA.f102a;
        if (typeface != null) {
            f.f94a.a(this.d, typeface);
        }
        return cVarA;
    }
}
