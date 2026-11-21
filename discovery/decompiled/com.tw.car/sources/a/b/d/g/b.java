package a.b.d.g;

import a.b.d.g.f.b;
import a.b.d.g.f.c;
import a.b.d.g.f.e;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class b<K, V> extends g<K, V> implements Map<K, V> {
    public f<K, V> h;

    public b() {
    }

    public b(int i) {
        super(i);
    }

    public final f<K, V> b() {
        if (this.h == null) {
            this.h = new a(this);
        }
        return this.h;
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        f<K, V> fVarB = b();
        if (fVarB.f131a == null) {
            fVarB.f131a = fVarB.new b();
        }
        return fVarB.f131a;
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        f<K, V> fVarB = b();
        if (fVarB.f132b == null) {
            fVarB.f132b = fVarB.new c();
        }
        return fVarB.f132b;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        int size = map.size() + this.g;
        int i = this.g;
        int[] iArr = this.e;
        if (iArr.length < size) {
            Object[] objArr = this.f;
            a(size);
            if (this.g > 0) {
                System.arraycopy(iArr, 0, this.e, 0, i);
                System.arraycopy(objArr, 0, this.f, 0, i << 1);
            }
            g.a(iArr, objArr, i);
        }
        if (this.g != i) {
            throw new ConcurrentModificationException();
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public Collection<V> values() {
        f<K, V> fVarB = b();
        if (fVarB.f133c == null) {
            fVarB.f133c = fVarB.new e();
        }
        return fVarB.f133c;
    }
}
