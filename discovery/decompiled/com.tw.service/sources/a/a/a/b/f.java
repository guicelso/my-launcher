package a.a.a.b;

import java.util.Iterator;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes.dex */
public class f implements Iterator, h {
    private e B;
    private boolean D = true;
    final /* synthetic */ i n;

    /* synthetic */ f(i iVar, b bVar) {
        this.n = iVar;
    }

    @Override // a.a.a.b.h
    public void a(e eVar) {
        e eVar2 = this.B;
        if (eVar == eVar2) {
            this.B = eVar2.A;
            this.D = this.B == null;
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.D) {
            return this.n.mStart != null;
        }
        e eVar = this.B;
        return (eVar == null || eVar.mNext == null) ? false : true;
    }

    @Override // java.util.Iterator
    public Object next() {
        e eVar;
        if (this.D) {
            this.D = false;
            eVar = this.n.mStart;
        } else {
            e eVar2 = this.B;
            eVar = eVar2 != null ? eVar2.mNext : null;
        }
        this.B = eVar;
        return this.B;
    }
}
