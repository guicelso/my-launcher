package android.support.v4.n;

import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class g implements Iterator {
    boolean oo = false;
    int op;
    final int oq;
    int or;
    final /* synthetic */ h os;

    g(h hVar, int i) {
        this.os = hVar;
        this.oq = i;
        this.or = hVar.rT();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.op < this.or;
    }

    @Override // java.util.Iterator
    public Object next() {
        Object objRR = this.os.rR(this.op, this.oq);
        this.op++;
        this.oo = true;
        return objRR;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.oo) {
            throw new IllegalStateException();
        }
        this.op--;
        this.or--;
        this.oo = false;
        this.os.rX(this.op);
    }
}
