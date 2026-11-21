package a.b.d.g;

import a.b.d.g.e.C0006e;
import a.b.d.g.e.b;
import a.b.d.g.e.c;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class a<K, V> extends f<K, V> implements Map<K, V> {
    public e<K, V> h;

    /* JADX INFO: renamed from: a.b.d.g.a$a, reason: collision with other inner class name */
    public class C0005a extends e<K, V> {
        public C0005a() {
        }

        @Override // a.b.d.g.e
        public Object a(int i, int i2) {
            return a.this.f136b[(i << 1) + i2];
        }

        @Override // a.b.d.g.e
        public int b(Object obj) {
            return a.this.d(obj);
        }
    }

    public a() {
    }

    public a(int i) {
        super(i);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        e<K, V> eVarJ = j();
        if (eVarJ.f123a == null) {
            eVarJ.f123a = eVarJ.new b();
        }
        return eVarJ.f123a;
    }

    public final e<K, V> j() {
        if (this.h == null) {
            this.h = new C0005a();
        }
        return this.h;
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        e<K, V> eVarJ = j();
        if (eVarJ.f124b == null) {
            eVarJ.f124b = eVarJ.new c();
        }
        return eVarJ.f124b;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        int size = map.size() + this.f137c;
        int i = this.f137c;
        int[] iArr = this.f135a;
        if (iArr.length < size) {
            Object[] objArr = this.f136b;
            a(size);
            if (this.f137c > 0) {
                System.arraycopy(iArr, 0, this.f135a, 0, i);
                System.arraycopy(objArr, 0, this.f136b, 0, i << 1);
            }
            f.b(iArr, objArr, i);
        }
        if (this.f137c != i) {
            throw new ConcurrentModificationException();
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        e<K, V> eVarJ = j();
        if (eVarJ.f125c == null) {
            eVarJ.f125c = eVarJ.new C0006e();
        }
        return eVarJ.f125c;
    }
}
