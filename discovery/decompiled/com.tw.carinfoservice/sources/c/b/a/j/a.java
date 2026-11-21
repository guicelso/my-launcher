package c.b.a.j;

import android.content.Context;
import c.b.a.h.a;
import c.b.a.m.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class a<V extends c.b.a.m.a, M extends c.b.a.h.a> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public V f426a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public M f427b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f428c;

    public a(Context context) {
        this.f428c = context;
        Context context2 = ((b) this).f428c;
        if (c.b.a.h.b.f0 == null) {
            c.b.a.h.b.f0 = new c.b.a.h.b(context2);
        }
        this.f427b = c.b.a.h.b.f0;
    }
}
