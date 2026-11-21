package android.support.v4.n;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class d implements Collection {
    final /* synthetic */ h ol;

    d(h hVar) {
        this.ol = hVar;
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
        this.ol.rQ();
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        return this.ol.rV(obj) >= 0;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.ol.rT() == 0;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new g(this.ol, 1);
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        int iRV = this.ol.rV(obj);
        if (iRV < 0) {
            return false;
        }
        this.ol.rX(iRV);
        return true;
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection collection) {
        int i = 0;
        int iRT = this.ol.rT();
        boolean z = false;
        while (i < iRT) {
            if (collection.contains(this.ol.rR(i, 1))) {
                this.ol.rX(i);
                i--;
                iRT--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection collection) {
        int i = 0;
        int iRT = this.ol.rT();
        boolean z = false;
        while (i < iRT) {
            if (!collection.contains(this.ol.rR(i, 1))) {
                this.ol.rX(i);
                i--;
                iRT--;
                z = true;
            }
            i++;
        }
        return z;
    }

    @Override // java.util.Collection
    public int size() {
        return this.ol.rT();
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return this.ol.st(1);
    }

    @Override // java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.ol.su(objArr, 1);
    }
}
