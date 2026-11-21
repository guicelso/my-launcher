package a.b.c.b;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class c extends l {
    final /* synthetic */ d n;

    c(d dVar) {
        this.n = dVar;
    }

    @Override // a.b.c.b.l
    protected void colClear() {
        this.n.clear();
    }

    @Override // a.b.c.b.l
    protected Object colGetEntry(int i, int i2) {
        return this.n.mArray[i];
    }

    @Override // a.b.c.b.l
    protected Map colGetMap() {
        throw new UnsupportedOperationException("not a map");
    }

    @Override // a.b.c.b.l
    protected int colGetSize() {
        return this.n.mSize;
    }

    @Override // a.b.c.b.l
    protected int colIndexOfKey(Object obj) {
        return this.n.indexOf(obj);
    }

    @Override // a.b.c.b.l
    protected int colIndexOfValue(Object obj) {
        return this.n.indexOf(obj);
    }

    @Override // a.b.c.b.l
    protected void colPut(Object obj, Object obj2) {
        this.n.add(obj);
    }

    @Override // a.b.c.b.l
    protected void colRemoveAt(int i) {
        this.n.removeAt(i);
    }

    @Override // a.b.c.b.l
    protected Object colSetValue(int i, Object obj) {
        throw new UnsupportedOperationException("not a map");
    }
}
