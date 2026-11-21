package a.b.c.b;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class h implements Set {
    final /* synthetic */ l n;

    h(l lVar) {
        this.n = lVar;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection collection) {
        int iColGetSize = this.n.colGetSize();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            this.n.colPut(entry.getKey(), entry.getValue());
        }
        return iColGetSize != this.n.colGetSize();
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.n.colClear();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        int iColIndexOfKey = this.n.colIndexOfKey(entry.getKey());
        if (iColIndexOfKey < 0) {
            return false;
        }
        return e.a(this.n.colGetEntry(iColIndexOfKey, 1), entry.getValue());
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        boolean zA;
        for (Object obj : collection) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                int iColIndexOfKey = this.n.colIndexOfKey(entry.getKey());
                if (iColIndexOfKey < 0) {
                    zA = false;
                } else {
                    zA = e.a(this.n.colGetEntry(iColIndexOfKey, 1), entry.getValue());
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
        return l.equalsSetHelper(this, obj);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        int iHashCode = 0;
        for (int iColGetSize = this.n.colGetSize() - 1; iColGetSize >= 0; iColGetSize--) {
            Object objColGetEntry = this.n.colGetEntry(iColGetSize, 0);
            Object objColGetEntry2 = this.n.colGetEntry(iColGetSize, 1);
            iHashCode += (objColGetEntry == null ? 0 : objColGetEntry.hashCode()) ^ (objColGetEntry2 == null ? 0 : objColGetEntry2.hashCode());
        }
        return iHashCode;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.n.colGetSize() == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new j(this.n);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.n.colGetSize();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        throw new UnsupportedOperationException();
    }
}
