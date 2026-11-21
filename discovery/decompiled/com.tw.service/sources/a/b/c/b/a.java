package a.b.c.b;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class a extends l {
    final /* synthetic */ b n;

    a(b bVar) {
        this.n = bVar;
    }

    @Override // a.b.c.b.l
    protected void colClear() {
        this.n.clear();
    }

    @Override // a.b.c.b.l
    protected Object colGetEntry(int i, int i2) {
        return this.n.mArray[(i << 1) + i2];
    }

    @Override // a.b.c.b.l
    protected Map colGetMap() {
        return this.n;
    }

    @Override // a.b.c.b.l
    protected int colGetSize() {
        return this.n.mSize;
    }

    @Override // a.b.c.b.l
    protected int colIndexOfKey(Object obj) {
        return this.n.indexOfKey(obj);
    }

    @Override // a.b.c.b.l
    protected int colIndexOfValue(Object obj) {
        return this.n.indexOfValue(obj);
    }

    @Override // a.b.c.b.l
    protected void colPut(Object obj, Object obj2) {
        this.n.put(obj, obj2);
    }

    @Override // a.b.c.b.l
    protected void colRemoveAt(int i) {
        this.n.removeAt(i);
    }

    @Override // a.b.c.b.l
    protected Object colSetValue(int i, Object obj) {
        int i2 = (i << 1) + 1;
        Object[] objArr = this.n.mArray;
        Object obj2 = objArr[i2];
        objArr[i2] = obj;
        return obj2;
    }
}
