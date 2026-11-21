package a.b.d.e;

import android.content.Context;
import android.graphics.Typeface;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Callable<e.d> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Context f85a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ a f86b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ int f87c;
    public final /* synthetic */ String d;

    public b(Context context, a aVar, int i, String str) {
        this.f85a = context;
        this.f86b = aVar;
        this.f87c = i;
        this.d = str;
    }

    @Override // java.util.concurrent.Callable
    public e.d call() {
        e.d dVarB = e.b(this.f85a, this.f86b, this.f87c);
        Typeface typeface = dVarB.f99a;
        if (typeface != null) {
            e.f91a.b(this.d, typeface);
        }
        return dVarB;
    }
}
