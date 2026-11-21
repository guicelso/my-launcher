package a.b.d.g;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class f<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public f<K, V>.b f131a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public f<K, V>.c f132b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public f<K, V>.e f133c;

    final class a<T> implements Iterator<T> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int f134a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f135b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f136c;
        public boolean d = false;

        public a(int i) {
            this.f134a = i;
            this.f135b = ((a.b.d.g.a) f.this).d.g;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f136c < this.f135b;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!(this.f136c < this.f135b)) {
                throw new NoSuchElementException();
            }
            T t = (T) f.this.a(this.f136c, this.f134a);
            this.f136c++;
            this.d = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.d) {
                throw new IllegalStateException();
            }
            this.f136c--;
            this.f135b--;
            this.d = false;
            ((a.b.d.g.a) f.this).d.c(this.f136c);
        }
    }

    final class b implements Set<Map.Entry<K, V>> {
        public b() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int i = ((a.b.d.g.a) f.this).d.g;
            for (Map.Entry<K, V> entry : collection) {
                ((a.b.d.g.a) f.this).d.put(entry.getKey(), entry.getValue());
            }
            return i != ((a.b.d.g.a) f.this).d.g;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            f.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iA = f.this.a(entry.getKey());
            if (iA < 0) {
                return false;
            }
            return a.b.d.g.c.a(f.this.a(iA, 1), entry.getValue());
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0014  */
        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            boolean zA;
            for (Object obj : collection) {
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    int iA = f.this.a(entry.getKey());
                    if (iA < 0) {
                        zA = false;
                    } else {
                        zA = a.b.d.g.c.a(f.this.a(iA, 1), entry.getValue());
                    }
                } else {
                    zA = false;
                }
                if (!zA) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return f.a(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int i = ((a.b.d.g.a) f.this).d.g - 1; i >= 0; i--) {
                Object objA = f.this.a(i, 0);
                Object objA2 = f.this.a(i, 1);
                iHashCode += (objA == null ? 0 : objA.hashCode()) ^ (objA2 == null ? 0 : objA2.hashCode());
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ((a.b.d.g.a) f.this).d.g == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new d();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ((a.b.d.g.a) f.this).d.g;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }
    }

    final class c implements Set<K> {
        public c() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            f.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return ((a.b.d.g.a) f.this).d.a(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            a.b.d.g.b bVar = ((a.b.d.g.a) f.this).d;
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!bVar.containsKey(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return f.a(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int i = ((a.b.d.g.a) f.this).d.g - 1; i >= 0; i--) {
                Object objA = f.this.a(i, 0);
                iHashCode += objA == null ? 0 : objA.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return ((a.b.d.g.a) f.this).d.g == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new a(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int iA = ((a.b.d.g.a) f.this).d.a(obj);
            if (iA < 0) {
                return false;
            }
            ((a.b.d.g.a) f.this).d.c(iA);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            a.b.d.g.b bVar = ((a.b.d.g.a) f.this).d;
            int size = bVar.size();
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                bVar.remove(it.next());
            }
            return size != bVar.size();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            a.b.d.g.b bVar = ((a.b.d.g.a) f.this).d;
            int size = bVar.size();
            Iterator<K> it = bVar.keySet().iterator();
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                }
            }
            return size != bVar.size();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return ((a.b.d.g.a) f.this).d.g;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return f.this.a(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) f.this.a(tArr, 0);
        }
    }

    final class d implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f139a;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f141c = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f140b = -1;

        public d() {
            this.f139a = ((a.b.d.g.a) f.this).d.g - 1;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.f141c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a.b.d.g.c.a(entry.getKey(), f.this.a(this.f140b, 0)) && a.b.d.g.c.a(entry.getValue(), f.this.a(this.f140b, 1));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.f141c) {
                return (K) f.this.a(this.f140b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.f141c) {
                return (V) f.this.a(this.f140b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f140b < this.f139a;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (!this.f141c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object objA = f.this.a(this.f140b, 0);
            Object objA2 = f.this.a(this.f140b, 1);
            return (objA == null ? 0 : objA.hashCode()) ^ (objA2 != null ? objA2.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!(this.f140b < this.f139a)) {
                throw new NoSuchElementException();
            }
            this.f140b++;
            this.f141c = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.f141c) {
                throw new IllegalStateException();
            }
            ((a.b.d.g.a) f.this).d.c(this.f140b);
            this.f140b--;
            this.f139a--;
            this.f141c = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (!this.f141c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            f fVar = f.this;
            int i = (this.f140b << 1) + 1;
            Object[] objArr = ((a.b.d.g.a) fVar).d.f;
            V v2 = (V) objArr[i];
            objArr[i] = v;
            return v2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (!this.f141c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            sb.append(f.this.a(this.f140b, 0));
            sb.append("=");
            if (!this.f141c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            sb.append(f.this.a(this.f140b, 1));
            return sb.toString();
        }
    }

    final class e implements Collection<V> {
        public e() {
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            f.this.a();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return ((a.b.d.g.a) f.this).d.b(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            boolean z;
            Iterator<?> it = collection.iterator();
            do {
                z = true;
                if (!it.hasNext()) {
                    return true;
                }
                if (f.this.b(it.next()) < 0) {
                    z = false;
                }
            } while (z);
            return false;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return ((a.b.d.g.a) f.this).d.g == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new a(1);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int iB = ((a.b.d.g.a) f.this).d.b(obj);
            if (iB < 0) {
                return false;
            }
            ((a.b.d.g.a) f.this).d.c(iB);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = ((a.b.d.g.a) f.this).d.g;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (collection.contains(f.this.a(i2, 1))) {
                    ((a.b.d.g.a) f.this).d.c(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int i = ((a.b.d.g.a) f.this).d.g;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (!collection.contains(f.this.a(i2, 1))) {
                    ((a.b.d.g.a) f.this).d.c(i2);
                    i2--;
                    i--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return ((a.b.d.g.a) f.this).d.g;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return f.this.a(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) f.this.a(tArr, 1);
        }
    }

    public static <T> boolean a(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                return set.size() == set2.size() && set.containsAll(set2);
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public abstract int a(Object obj);

    public abstract Object a(int i, int i2);

    public abstract void a();

    public Object[] a(int i) {
        int i2 = ((a.b.d.g.a) this).d.g;
        Object[] objArr = new Object[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = a(i3, i);
        }
        return objArr;
    }

    public abstract int b(Object obj);

    public <T> T[] a(T[] tArr, int i) {
        int i2 = ((a.b.d.g.a) this).d.g;
        if (tArr.length < i2) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i2));
        }
        for (int i3 = 0; i3 < i2; i3++) {
            tArr[i3] = a(i3, i);
        }
        if (tArr.length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }
}
