package a.b.d.g;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class e<K, V> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public e<K, V>.b f123a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public e<K, V>.c f124b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public e<K, V>.C0006e f125c;

    public final class a<T> implements Iterator<T> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int f126a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f127b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f128c;
        public boolean d = false;

        public a(int i) {
            this.f126a = i;
            this.f127b = a.b.d.g.a.this.f137c;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f128c < this.f127b;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T t = (T) e.this.a(this.f128c, this.f126a);
            this.f128c++;
            this.d = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.d) {
                throw new IllegalStateException();
            }
            int i = this.f128c - 1;
            this.f128c = i;
            this.f127b--;
            this.d = false;
            a.b.d.g.a.this.h(i);
        }
    }

    public final class b implements Set<Map.Entry<K, V>> {
        public b() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int i = a.b.d.g.a.this.f137c;
            for (Map.Entry<K, V> entry : collection) {
                a.b.d.g.a.this.put(entry.getKey(), entry.getValue());
            }
            return i != a.b.d.g.a.this.f137c;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            a.b.d.g.a.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iB = e.this.b(entry.getKey());
            if (iB < 0) {
                return false;
            }
            return a.b.d.g.b.c(e.this.a(iB, 1), entry.getValue());
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return e.c(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int i = a.b.d.g.a.this.f137c - 1; i >= 0; i--) {
                Object objA = e.this.a(i, 0);
                Object objA2 = e.this.a(i, 1);
                iHashCode += (objA == null ? 0 : objA.hashCode()) ^ (objA2 == null ? 0 : objA2.hashCode());
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return a.b.d.g.a.this.f137c == 0;
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
            return a.b.d.g.a.this.f137c;
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

    public final class c implements Set<K> {
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
            a.b.d.g.a.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return a.b.d.g.a.this.d(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            a.b.d.g.a aVar = a.b.d.g.a.this;
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!aVar.containsKey(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return e.c(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int i = a.b.d.g.a.this.f137c - 1; i >= 0; i--) {
                Object objA = e.this.a(i, 0);
                iHashCode += objA == null ? 0 : objA.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return a.b.d.g.a.this.f137c == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new a(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int iD = a.b.d.g.a.this.d(obj);
            if (iD < 0) {
                return false;
            }
            a.b.d.g.a.this.h(iD);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            a.b.d.g.a aVar = a.b.d.g.a.this;
            int size = aVar.size();
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                aVar.remove(it.next());
            }
            return size != aVar.size();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            a.b.d.g.a aVar = a.b.d.g.a.this;
            int size = aVar.size();
            Iterator<K> it = aVar.keySet().iterator();
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                }
            }
            return size != aVar.size();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return a.b.d.g.a.this.f137c;
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return e.this.d(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) e.this.e(tArr, 0);
        }
    }

    public final class d implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public int f131a;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f133c = false;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public int f132b = -1;

        public d() {
            this.f131a = a.b.d.g.a.this.f137c - 1;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.f133c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a.b.d.g.b.c(entry.getKey(), e.this.a(this.f132b, 0)) && a.b.d.g.b.c(entry.getValue(), e.this.a(this.f132b, 1));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.f133c) {
                return (K) e.this.a(this.f132b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.f133c) {
                return (V) e.this.a(this.f132b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f132b < this.f131a;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (!this.f133c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object objA = e.this.a(this.f132b, 0);
            Object objA2 = e.this.a(this.f132b, 1);
            return (objA == null ? 0 : objA.hashCode()) ^ (objA2 != null ? objA2.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.f132b++;
            this.f133c = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.f133c) {
                throw new IllegalStateException();
            }
            a.b.d.g.a.this.h(this.f132b);
            this.f132b--;
            this.f131a--;
            this.f133c = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (!this.f133c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            e eVar = e.this;
            int i = (this.f132b << 1) + 1;
            Object[] objArr = a.b.d.g.a.this.f136b;
            V v2 = (V) objArr[i];
            objArr[i] = v;
            return v2;
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /* JADX INFO: renamed from: a.b.d.g.e$e, reason: collision with other inner class name */
    public final class C0006e implements Collection<V> {
        public C0006e() {
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
            a.b.d.g.a.this.clear();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return a.b.d.g.a.this.f(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return a.b.d.g.a.this.f137c == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new a(1);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int iF = a.b.d.g.a.this.f(obj);
            if (iF < 0) {
                return false;
            }
            a.b.d.g.a.this.h(iF);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int i = a.b.d.g.a.this.f137c;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (collection.contains(e.this.a(i2, 1))) {
                    a.b.d.g.a.this.h(i2);
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
            int i = a.b.d.g.a.this.f137c;
            int i2 = 0;
            boolean z = false;
            while (i2 < i) {
                if (!collection.contains(e.this.a(i2, 1))) {
                    a.b.d.g.a.this.h(i2);
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
            return a.b.d.g.a.this.f137c;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return e.this.d(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) e.this.e(tArr, 1);
        }
    }

    public static <T> boolean c(Set<T> set, Object obj) {
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

    public abstract Object a(int i, int i2);

    public abstract int b(Object obj);

    public Object[] d(int i) {
        int i2 = a.b.d.g.a.this.f137c;
        Object[] objArr = new Object[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = a(i3, i);
        }
        return objArr;
    }

    public <T> T[] e(T[] tArr, int i) {
        int i2 = a.b.d.g.a.this.f137c;
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
