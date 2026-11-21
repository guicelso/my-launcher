package a.b.e.e.c;

import android.content.Context;
import android.view.LayoutInflater;

/* JADX INFO: loaded from: classes.dex */
public abstract class b implements m {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f209b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Context f210c;
    public g d;
    public LayoutInflater e;
    public m.a f;
    public int g;
    public n h;

    public b(Context context, int i, int i2) {
        this.f209b = context;
        this.e = LayoutInflater.from(context);
        this.g = i2;
    }

    @Override // a.b.e.e.c.m
    public boolean e(g gVar, h hVar) {
        return false;
    }

    @Override // a.b.e.e.c.m
    public boolean h(g gVar, h hVar) {
        return false;
    }

    @Override // a.b.e.e.c.m
    public void k(m.a aVar) {
        this.f = aVar;
    }
}
