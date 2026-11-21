package a.b.c.b;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class k implements Collection {
    final /* synthetic */ l n;

    k(l lVar) {
        this.n = lVar;
    }

    @Override // java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public void clear() {
        this.n.colClear();
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        return this.n.colIndexOfValue(obj) >= 0;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection collection) {
        boolean z;
        Iterator it = collection.iterator();
        do {
            z = true;
            if (!it.hasNext()) {
                return true;
            }
            if (this.n.colIndexOfValue(it.next()) < 0) {
                z = false;
            }
        } while (z);
        return false;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.n.colGetSize() == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new g(this.n, 1);
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        int iColIndexOfValue = this.n.colIndexOfValue(obj);
        if (iColIndexOfValue < 0) {
            return false;
        }
        this.n.colRemoveAt(iColIndexOfValue);
        return true;
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection collection) {
        int iColGetSize = this.n.colGetSize();
        int i = 0;
        boolean z = false;
        while (i < iColGetSize) {
            if (collection.contains(this.n.colGetEntry(i, 1))) {
                this.n.colRemoveAt(i);
                i--;
                iColGetSize--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection collection) {
        int iColGetSize = this.n.colGetSize();
        int i = 0;
        boolean z = false;
        while (i < iColGetSize) {
            if (!collection.contains(this.n.colGetEntry(i, 1))) {
                this.n.colRemoveAt(i);
                i--;
                iColGetSize--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public int size() {
        return this.n.colGetSize();
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return this.n.toArrayHelper(1);
    }

    @Override // java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.n.toArrayHelper(objArr, 1);
    }
}
