package a.b.c.b;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class i implements Set {
    final /* synthetic */ l n;

    i(l lVar) {
        this.n = lVar;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.n.colClear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        return this.n.colIndexOfKey(obj) >= 0;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        Map mapColGetMap = this.n.colGetMap();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!mapColGetMap.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        return l.equalsSetHelper(this, obj);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        int iHashCode = 0;
        for (int iColGetSize = this.n.colGetSize() - 1; iColGetSize >= 0; iColGetSize--) {
            Object objColGetEntry = this.n.colGetEntry(iColGetSize, 0);
            iHashCode += objColGetEntry == null ? 0 : objColGetEntry.hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.n.colGetSize() == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new g(this.n, 0);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        int iColIndexOfKey = this.n.colIndexOfKey(obj);
        if (iColIndexOfKey < 0) {
            return false;
        }
        this.n.colRemoveAt(iColIndexOfKey);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection collection) {
        Map mapColGetMap = this.n.colGetMap();
        int size = mapColGetMap.size();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            mapColGetMap.remove(it.next());
        }
        return size != mapColGetMap.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection collection) {
        return l.retainAllHelper(this.n.colGetMap(), collection);
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.n.colGetSize();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.n.toArrayHelper(0);
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.n.toArrayHelper(objArr, 0);
    }
}
