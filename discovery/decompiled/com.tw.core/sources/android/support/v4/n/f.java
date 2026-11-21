package android.support.v4.n;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
final class f implements Set {
    final /* synthetic */ h on;

    f(h hVar) {
        this.on = hVar;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection collection) {
        int iRT = this.on.rT();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            this.on.rW(entry.getKey(), entry.getValue());
        }
        return iRT != this.on.rT();
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.on.rQ();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        int iRU = this.on.rU(entry.getKey());
        if (iRU < 0) {
            return false;
        }
        return j.sw(this.on.rR(iRU, 1), entry.getValue());
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        return h.ss(this, obj);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        int iRT = this.on.rT() - 1;
        int iHashCode = 0;
        while (iRT >= 0) {
            Object objRR = this.on.rR(iRT, 0);
            Object objRR2 = this.on.rR(iRT, 1);
            iRT--;
            iHashCode += (objRR2 == null ? 0 : objRR2.hashCode()) ^ (objRR == null ? 0 : objRR.hashCode());
        }
        return iHashCode;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.on.rT() == 0;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new i(this.on);
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
        return this.on.rT();
    }

    @Override // java.util.Set, java.util.Collection
    /* JADX INFO: renamed from: sl, reason: merged with bridge method [inline-methods] */
    public boolean add(Map.Entry entry) {
        throw new UnsupportedOperationException();
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
