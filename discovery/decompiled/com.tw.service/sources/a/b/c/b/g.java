package a.b.c.b;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes.dex */
final class g implements Iterator {
    boolean mCanRemove = false;
    int mIndex;
    final int mOffset;
    int mSize;
    final /* synthetic */ l n;

    g(l lVar, int i) {
        this.n = lVar;
        this.mOffset = i;
        this.mSize = lVar.colGetSize();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.mIndex < this.mSize;
    }

    @Override // java.util.Iterator
    public Object next() {
        if (!(this.mIndex < this.mSize)) {
            throw new NoSuchElementException();
        }
        Object objColGetEntry = this.n.colGetEntry(this.mIndex, this.mOffset);
        this.mIndex++;
        this.mCanRemove = true;
        return objColGetEntry;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.mCanRemove) {
            throw new IllegalStateException();
        }
        this.mIndex--;
        this.mSize--;
        this.mCanRemove = false;
        this.n.colRemoveAt(this.mIndex);
    }
}
