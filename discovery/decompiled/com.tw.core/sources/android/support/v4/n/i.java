package android.support.v4.n;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class i implements Iterator, Map.Entry {
    int ow;
    boolean ox = false;
    int oy = -1;
    final /* synthetic */ h oz;

    i(h hVar) {
        this.oz = hVar;
        this.ow = hVar.rT() - 1;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (!this.ox) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (j.sw(entry.getKey(), this.oz.rR(this.oy, 0))) {
            return j.sw(entry.getValue(), this.oz.rR(this.oy, 1));
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public Object getKey() {
        if (this.ox) {
            return this.oz.rR(this.oy, 0);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    @Override // java.util.Map.Entry
    public Object getValue() {
        if (this.ox) {
            return this.oz.rR(this.oy, 1);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.oy < this.ow;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        if (!this.ox) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }
        Object objRR = this.oz.rR(this.oy, 0);
        Object objRR2 = this.oz.rR(this.oy, 1);
        return (objRR2 != null ? objRR2.hashCode() : 0) ^ (objRR == null ? 0 : objRR.hashCode());
    }

    @Override // java.util.Iterator
    public Map.Entry next() {
        this.oy++;
        this.ox = true;
        return this;
    }

    @Override // java.util.Iterator
    public void remove() {
        if (!this.ox) {
            throw new IllegalStateException();
        }
        this.oz.rX(this.oy);
        this.oy--;
        this.ow--;
        this.ox = false;
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        if (this.ox) {
            return this.oz.rY(this.oy, obj);
        }
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
