package a.b.e.e.a;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: renamed from: a.b.e.e.a.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0009b implements t {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f208a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Context f209b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public k f210c;
    public LayoutInflater d;
    public t.a e;
    public int f;
    public u g;

    public AbstractC0009b(Context context, int i, int i2) {
        this.f208a = context;
        this.d = LayoutInflater.from(context);
        this.f = i2;
    }

    public abstract View a(n nVar, View view, ViewGroup viewGroup);

    @Override // a.b.e.e.a.t
    public void a(t.a aVar) {
        this.e = aVar;
    }

    @Override // a.b.e.e.a.t
    public boolean a(k kVar, n nVar) {
        return false;
    }

    @Override // a.b.e.e.a.t
    public boolean b(k kVar, n nVar) {
        return false;
    }
}
