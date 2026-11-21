package a.b.c.b;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class j implements Iterator, Map.Entry {
    int mEnd;
    boolean mEntryValid = false;
    int mIndex = -1;
    final /* synthetic */ l n;

    j(l lVar) {
        this.n = lVar;
        this.mEnd = lVar.colGetSize() - 1;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return e.a(entry.getKey(), this.n.colGetEntry(this.mIndex, 0)) && e.a(entry.getValue(), this.n.colGetEntry(this.mIndex, 1));
    }

    @Override // java.util.Map.Entry
    public Object getKey() {
        if (this.mEntryValid) {
            return this.n.colGetEntry(this.mIndex, 0);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    @Override // java.util.Map.Entry
    public Object getValue() {
        if (this.mEntryValid) {
            return this.n.colGetEntry(this.mIndex, 1);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mIndex < this.mEnd;
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        Object objColGetEntry = this.n.colGetEntry(this.mIndex, 0);
        Object objColGetEntry2 = this.n.colGetEntry(this.mIndex, 1);
        return (objColGetEntry == null ? 0 : objColGetEntry.hashCode()) ^ (objColGetEntry2 != null ? objColGetEntry2.hashCode() : 0);
    }

    @Override // java.util.Iterator
    public Object next() {
        if (!(this.mIndex < this.mEnd)) {
            throw new NoSuchElementException();
        }
        this.mIndex++;
        this.mEntryValid = true;
        return this;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.mEntryValid) {
            throw new IllegalStateException();
        }
        this.n.colRemoveAt(this.mIndex);
        this.mIndex--;
        this.mEnd--;
        this.mEntryValid = false;
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        if (this.mEntryValid) {
            return this.n.colSetValue(this.mIndex, obj);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        sb.append(this.n.colGetEntry(this.mIndex, 0));
        sb.append("=");
        if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        sb.append(this.n.colGetEntry(this.mIndex, 1));
        return sb.toString();
    }
}
