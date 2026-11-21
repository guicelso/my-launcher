package android.support.v4.n;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class b implements Set {
    final /* synthetic */ h od;

    b(h hVar) {
        this.od = hVar;
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
        this.od.rQ();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        return this.od.rU(obj) >= 0;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        return h.sm(this.od.rS(), collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        return h.ss(this, obj);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        int iHashCode = 0;
        for (int iRT = this.od.rT() - 1; iRT >= 0; iRT--) {
            Object objRR = this.od.rR(iRT, 0);
            iHashCode += objRR == null ? 0 : objRR.hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.od.rT() == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new g(this.od, 0);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        int iRU = this.od.rU(obj);
        if (iRU < 0) {
            return false;
        }
        this.od.rX(iRU);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection collection) {
        return h.sn(this.od.rS(), collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection collection) {
        return h.so(this.od.rS(), collection);
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.od.rT();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.od.st(0);
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.od.su(objArr, 0);
    }
}
