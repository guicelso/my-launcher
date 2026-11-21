package a.b.d.g;

import java.util.ConcurrentModificationException;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: loaded from: classes.dex */
public class a<K, V> extends f<K, V> {
    public final /* synthetic */ b d;

    public a(b bVar) {
        this.d = bVar;
    }

    @Override // a.b.d.g.f
    public int a(Object obj) {
        return this.d.a(obj);
    }

    @Override // a.b.d.g.f
    public Object a(int i, int i2) {
        return this.d.f[(i << 1) + i2];
    }

    @Override // a.b.d.g.f
    public void a() {
        b bVar = this.d;
        int i = bVar.g;
        if (i > 0) {
            int[] iArr = bVar.e;
            Object[] objArr = bVar.f;
            bVar.e = c.f122a;
            bVar.f = c.f124c;
            bVar.g = 0;
            g.a(iArr, objArr, i);
        }
        if (bVar.g > 0) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // a.b.d.g.f
    public int b(Object obj) {
        return this.d.b(obj);
    }
}
